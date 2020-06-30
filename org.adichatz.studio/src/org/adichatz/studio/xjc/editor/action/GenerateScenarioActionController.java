package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.collection.ManagedToolBarController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.field.CheckBoxController;
import org.adichatz.engine.controller.field.FileTextController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiMessageDialog;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.indigo.controller.LegacyFormPageController;
import org.adichatz.engine.indigo.editor.AdiFormPage;
import org.adichatz.engine.query.NativeQueryManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.widgets.FileText;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.GenerationUnitWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.ErroneousFilesFormDialog;
import org.adichatz.studio.util.GenerationContentProvider;
import org.adichatz.studio.util.GenerationLabelProvider;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.util.ScenarioTreeElement;
import org.adichatz.studio.wizard.WizardUtil;
import org.adichatz.studio.xjc.controller.GenerationTreeController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.editor.ExternalResourcesFormEditor;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.miginfocom.swt.MigLayout;

public class GenerateScenarioActionController extends ActionController {

	private Display display;

	private ContainerCheckedTreeViewer checkViewer;

	private Set<GenerationEnum> generationEnums = new HashSet<GenerationEnum>();

	private ScenarioTreeElement rootElement;

	private Runnable generationRunnable;

	private ScenarioFormEditor editor;

	private ScenarioResources scenarioResources;

	private LegacyFormPageController currentPage;

	private FileText scenarioCustomizationField;

	private ScenarioTreeWrapper scenarioTree;

	private GenerationScenarioWrapper generationScenario;

	private Map<String, Boolean> flagMap = new HashMap<String, Boolean>();

	private GenerationScenarioWrapper missingGS;

	private AdiFormToolkit toolkit;

	private Button generateRcpPluginEntities = null;

	private ConfirmFormDialog confirmFormDialog;

	public GenerateScenarioActionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		// Initialize default value for flagMap
		flagMap.put("deleteUnusedResources", false);
	}

	@Override
	public void createControl() {
		action = new AAction() {
			private Group initJbossGroup;

			@Override
			public void runAction() {
				initJbossGroup = null;
				XjcBindingService bindingService = (XjcBindingService) parentController.getBindingService();
				editor = (ScenarioFormEditor) bindingService.getEditor();
				scenarioResources = editor.getScenarioResources();
				display = editor.getSite().getWorkbenchWindow().getShell().getDisplay();

				String title = "";
				currentPage = ((AdiFormPage) editor.getSelectedPage()).getFormPageController();
				scenarioResources.loadScenarioParameters(); // Parameters could have changed
				scenarioTree = (ScenarioTreeWrapper) editor.getTreeWrapper(false, false);
				generationScenario = (GenerationScenarioWrapper) scenarioTree.getGenerationScenario();
				if ("generationPage".equals(currentPage.getRegisterId())) {
					title = getFromStudioBundle("scenario.generation.what");
					GenerationTreeController generationTree = (GenerationTreeController) getFromRootRegister("generationTree");
					checkViewer = (ContainerCheckedTreeViewer) generationTree.getViewer();
					rootElement = new ScenarioTreeElement(null,
							new XjcTreeElement(generationTree.getTreeManager(), null, generationScenario, null, false));
					ScenarioTreeElement parentElement = rootElement;
					generationEnums.clear();
					boolean empty = true;
					for (Object element : checkViewer.getCheckedElements()) {
						XjcTreeElement treeElement = (XjcTreeElement) element;
						Object bean = treeElement.getEntity().getBean();
						if (!(bean instanceof GenerationUnitWrapper) && !(bean instanceof PluginEntityWrapper))
							continue;
						if (bean instanceof GenerationUnitWrapper) {
							if (treeElement.getParentElement().getEntity().getBean() instanceof GenerationScenarioWrapper) {
								generationEnums.add(((GenerationUnitType) bean).getType());
								new ScenarioTreeElement(rootElement, treeElement);
							} else if (!generationEnums.contains(((GenerationUnitType) bean).getType())) {
								new ScenarioTreeElement(parentElement, treeElement);
							}
							empty = false;
						} else {
							if (bean instanceof PluginEntityWrapper)
								parentElement = rootElement;
							parentElement = new ScenarioTreeElement(parentElement, treeElement);
						}
					}
					if (empty) {
						EngineTools.openDialog(MessageDialog.WARNING, getFromStudioBundle("scenario.generation.warning"),
								getFromStudioBundle("scenario.generation.nothing.todo"));
						return;
					}
				} else if ("modelPage".equals(currentPage.getRegisterId())) {
					title = getFromStudioBundle("scenario.generation.model");
				}
				final IConfirmContent confirmContent = new IConfirmContent() {
					private Composite parent;

					@Override
					public void okPressed(List<Control> complementControls) {
						parent.setEnabled(false);
						GenerationTreeController generationTree = (GenerationTreeController) getFromRootRegister("generationTree");
						if (null != generationTree)
							generationTree.setExpandedURIs();
						launchGeneration();
					}

					@Override
					public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
						this.parent = parent;
						GenerateScenarioActionController.this.toolkit = toolkit;
						parent.setLayout(new MigLayout("wrap", "grow,fill"));
						ModelPartType modelPart = generationScenario.getModelPart();
						if ("generationPage".equals(currentPage.getRegisterId())) {
							TreeViewer treeViewer = new TreeViewer(parent);
							Tree tree = treeViewer.getTree();
							tree.setLayoutData("wmin 500, hmax 400, gap top 20");
							treeViewer.setContentProvider(new GenerationContentProvider());
							treeViewer.setLabelProvider(new GenerationLabelProvider(checkViewer.getLabelProvider()));
							treeViewer.setInput(rootElement);
							treeViewer.expandAll();
							treeViewer.refresh();
						} else if ("modelPage".equals(currentPage.getRegisterId())) {
							if (null == scenarioResources.getScenarioTree().getGenerationScenario().getModelPart()) {
								confirmNewRcp(parent, true);
							} else {
								flagMap.put("mergeCustomization", false);
								flagMap.put("generateRcpPluginEntities", false);
								flagMap.put("procurePojo", false);
								flagMap.put("addComponentsRewrite", false);
								flagMap.put("generateMetaModel", false);
								flagMap.put("generateEjb", false);
								flagMap.put("generatePersistenceXML", false);
								flagMap.put("generateCallback", false);
								flagMap.put("addDriverModule", false);
								flagMap.put("addDriverInStandalone", false);
								flagMap.put("addDatasourceInStandalone", false);
								ATabularContentProvider<?> contentProvider = (ATabularContentProvider<?>) ((ASetController) getRootCore()
										.getFromRegister("entitiesTable")).getViewer().getContentProvider();
								boolean noPojos = contentProvider.getQueryManager().getQueryResult().getQueryResultList().isEmpty();

								if (!generationScenario.getPluginEntity().isEmpty())
									createCustomScenarioFile(parent, toolkit);

								boolean hasModelPart = ((CheckBoxController) getFromRootRegister("modelPart")).getValue();
								String connectorVersion;
								ConnectorTreeWrapper connectorTree = null;
								if (hasModelPart) {
									connectorVersion = modelPart.getConnectorASVersion();
									if (IScenarioConstants.JSE != connectorVersion)
										connectorTree = ScenarioUtil
												.getConnectorTree(scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI));
									DatasourceWrapper datasource = connectorTree.getDatasource(modelPart.getConnectorDataSource());
									if (null == datasource) {
										String message = getFromGeneratorBundle("no.datasource.error",
												modelPart.getConnectorDataSource());
										EngineTools.openDialog(MessageDialog.ERROR, getFromGeneratorBundle("jdbc.connection.error"),
												message);
										throw new RuntimeException(message);
									}
									int check71Installation = !IScenarioConstants.JSE.equals(connectorVersion)
											? connectorTree.checkJboss71Iinstallation(
													connectorVersion, datasource, modelPart.getJndi(), scenarioResources)
											: 0;
									if (0 != (check71Installation & IScenarioConstants.MISSING_DRIVER_MODULE)) {
										String module = datasource.getDriverClassName();
										module = module.substring(0, module.lastIndexOf('.'));
										createButton(createInitJbossGroup(parent, toolkit), "addDriverModule",
												"scenario.add.driver.module", true, module);
									}
									if (0 != (check71Installation & IScenarioConstants.MISSING_DRIVER_STANDALONE))
										createButton(createInitJbossGroup(parent, toolkit), "addDriverInStandalone",
												"scenario.add.driver.standalone", true, datasource.getDriver());
									if (0 != (check71Installation & IScenarioConstants.MISSING_DATASOURCE_STANDALONE))
										createButton(createInitJbossGroup(parent, toolkit), "addDatasourceInStandalone",
												"scenario.add.datasource.standalone", true, modelPart.getJndi());
									if (!connectorTree.testConnection(display.getActiveShell(), modelPart.getConnectorDataSource(),
											false))
										throw new AdichatzErrorException(null);

									String procureLabel = null;
									switch (modelPart.getModelProcurement()) {
									case HIBERNATE:
										procureLabel = "scenario.generate.pojo";
										break;
									case FOLDER:
										procureLabel = "scenario.folder.pojo";
										break;
									}
									Object customFileName = ((FileTextController) getFromRootRegister("customFileName")).getValue();
									boolean mergeCustom = null != customFileName && new File((String) customFileName).exists();
									final Button mergeCustomization = createButton(parent, "mergeCustomization",
											"scenario.merge.customization", "gap top 20", noPojos && mergeCustom);
									if (!mergeCustom)
										mergeCustomization.setEnabled(false);

									final Button procurePojo = createButton(parent, "procurePojo", procureLabel, "gap top 20",
											noPojos);

									final Button addComponentsRewrite = createButton(parent, "addComponentsRewrite",
											"scenario.add.components.rewrite", "gap top 20", noPojos);

									// generateMetaModel Button
									final Button generateMetaModel = createButton(parent, "generateMetaModel",
											"scenario.generate.metaModel", "gap top 20",
											noPojos || scenarioResources.isImpactModel());
									Composite metaModelComposite = toolkit.createComposite(parent);
									metaModelComposite.setLayout(new MigLayout("wrap 2, ins 0", "[]20[]"));
									metaModelComposite.setLayoutData("gap 20 0 0 20");
									final Button generateOnlyChange = createButton(metaModelComposite, SWT.RADIO,
											"generateOnlyChange", "scenario.generate.metaModel.onlyChanges", null, true);
									final Button generateAllPluginEntities = toolkit.createButton(metaModelComposite,
											getFromStudioBundle("scenario.generate.metaModel.all"), SWT.RADIO);

									procurePojo.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
											if (procurePojo.getSelection()) {
												addComponentsRewrite.setSelection(true);
												addComponentsRewrite.notifyListeners(SWT.Selection, null);
												generateMetaModel.setSelection(true);
												generateMetaModel.notifyListeners(SWT.Selection, null);
											}
										};
									});

									generateMetaModel.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
											boolean enabled = generateMetaModel.getSelection();
											generateOnlyChange.setEnabled(enabled);
											generateAllPluginEntities.setEnabled(enabled);
											generateOnlyChange.setSelection(!noPojos);
											generateAllPluginEntities.setSelection(noPojos);
										};
									});
									generateMetaModel.notifyListeners(SWT.Selection, null);

									if (IScenarioConstants.JSE.equals(connectorVersion)) {
										createButton(parent, "generateCallback", "scenario.generate.callback", false);
									} else {
										final Button generateEjb = createButton(parent, "generateEjb", "scenario.generate.ejb",
												true);
										final Button generatePersistenceXML = createButton(parent, "generatePersistenceXML",
												"scenario.generate.persistence.xml", "gap left 20", false);
										final Button deployEjb = createButton(parent, "deployEjb", "scenario.deploy.ejb",
												"gap left 20", false);
										generateEjb.addSelectionListener(new SelectionAdapter() {
											@Override
											public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
												if (!generateEjb.getSelection()) {
													generatePersistenceXML.setEnabled(false);
													deployEjb.setEnabled(false);
												} else {
													generatePersistenceXML.setEnabled(true);
													String ejbDeployDir = ScenarioUtil
															.getConnectorTree(
																	scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI))
															.getEjbDeployDir(
																	generationScenario.getModelPart().getConnectorASVersion());
													deployEjb.setEnabled(
															null != ejbDeployDir && new File(ejbDeployDir).isDirectory());
												}
											};
										});
										generateEjb.notifyListeners(SWT.Selection, null);
										if (deployEjb.isEnabled()) {
											deployEjb.setSelection(true);
											deployEjb.notifyListeners(SWT.Selection, null);
										}
									}
								}
							}
						}
					}

					private Composite createInitJbossGroup(Composite parent, FormToolkit toolkit) {
						if (null == initJbossGroup) {
							initJbossGroup = new Group(parent, SWT.None);
							initJbossGroup.setText(getFromStudioBundle("scenario.JBossinitialization",
									scenarioTree.getGenerationScenario().getModelPart().getConnectorASVersion()));
							toolkit.adapt(initJbossGroup);
							initJbossGroup.setFont(JFaceResources.getBannerFont());
							initJbossGroup.setBackground(toolkit.getColors().getColor(IFormColors.H_GRADIENT_START));
							initJbossGroup.setLayout(new MigLayout("wrap 1"));
						}
						return initJbossGroup;
					}

				};
				try {
					confirmFormDialog = new ConfirmFormDialog(display.getActiveShell(),
							AdichatzApplication.getInstance().getFormToolkit(), title,
							getControl().getImageDescriptor().createImage(), confirmContent);
					confirmFormDialog.open();
				} catch (AdichatzErrorException e) {
				}
			}
		};
		((ManagedToolBarController) parentController).getToolBarManager().add(action);
		action.setActionController(this);
	}

	private void addMissingGU(PluginEntityType pluginEntity, PluginEntityWrapper missingPE, GenerationEnum generationEnum) {
		IPluginEntityScenario pluginEntityScenario = scenarioResources.getPluginEntityScenario();
		GenerationUnitType generationUnit = ((PluginEntityWrapper) pluginEntity).getGenerationUnit(generationEnum);
		if (null != generationUnit) {
			String treeId = pluginEntityScenario.getTreeId(pluginEntity, generationEnum);
			String subPackage = pluginEntityScenario.getSubPackage(pluginEntity, generationEnum);
			if (!scenarioResources.getXmlFile(subPackage, treeId, false).exists()) {
				missingPE.getGenerationUnit().add(generationUnit);
			}
		}
	}

	private List<String> getApplicationFileNames(IProject project, boolean exist) {
		List<String> fileNames = new ArrayList<String>();
		for (String fileName : ScenarioTreeWrapper.CONFIG_FILE_NAMES) {
			boolean existFile = project.getFile(fileName).exists();
			if (existFile != exist)
				fileNames.add(fileName);
		}
		String srcPathName = "src/" + scenarioResources.getPluginPackage().replace('.', '/');
		for (String fileName : ScenarioTreeWrapper.APPLICATION_FILE_NAMES) {
			boolean existFile = project.getFile(srcPathName + "/" + fileName).exists();
			if (existFile != exist)
				fileNames.add(fileName);
		}
		return fileNames;
	}

	private void launchGeneration() {
		final GenerationScenarioWrapper generationScenario = new GenerationScenarioWrapper();
		generationScenario.setDeleteUnusedResources(flagMap.get("deleteUnusedResources"));
		scenarioResources.reinitManifestChanger();

		if ("generationPage".equals(currentPage.getRegisterId())) {
			List<GenerationUnitType> generationUnits = new ArrayList<GenerationUnitType>();
			List<PluginEntityType> pluginEntities = new ArrayList<PluginEntityType>();
			extractChildren(rootElement, generationUnits, pluginEntities);
			generationScenario.getGenerationUnit().addAll(generationUnits);
			generationScenario.getPluginEntity().addAll(pluginEntities);
			generationRunnable = new GenerateScenarioRunnable(generationScenario, editor.getScenarioResources());
		} else if ("modelPage".equals(currentPage.getRegisterId()))
			if (null != scenarioTree.getGenerationScenario().getModelPart()) {
				FileTextController customFileTextController = (FileTextController) getFromRootRegister("customFileName");
				String customFileName = null;
				if (null != customFileTextController && null != customFileTextController.getControl())
					customFileName = (String) customFileTextController.getValue();
				else if (null != scenarioCustomizationField)
					customFileName = scenarioCustomizationField.getValue();
				generationRunnable = new GenerateModelRunnable(editor, generationScenario, customFileName, flagMap);
			} else {
				generationRunnable = new GenerateRcpRunnable(editor, missingGS,
						null != scenarioCustomizationField ? scenarioCustomizationField.getValue() : null, flagMap);
			}
		else
			throw new RuntimeException(getFromStudioBundle("scenario.invalid.action"));
		runJob();
	}

	public void runJob() {
		String pluginName = scenarioResources.getPluginName();
		String generating = getFromStudioBundle("studio.generation.launching", pluginName);
		Job[] jobs = Job.getJobManager().find(null);
		for (Job job : jobs)
			if (generating.equals(job.getName())) {
				EngineTools.openDialog(MessageDialog.ERROR, getFromStudioBundle("scenario.generate.not.possible"),
						getFromStudioBundle("scenario.generate.job.already.exist", pluginName));
				return;
			}
		Job job = new Job(generating) {
			@SuppressWarnings("unchecked")
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (!display.isDisposed())
					display.syncExec(() -> {
						editor.setGenerating(true);
					});
				editor.setInsideFileChanged(true);
				// Runs generation process
				generationRunnable.run();
				editor.setInsideFileChanged(false);
				try {
					if (!display.isDisposed())
						display.syncExec(() -> {
							if ("modelPage".equals(currentPage.getRegisterId())) {
								TableController<?> entitiesTable = (TableController<?>) getFromRootRegister("entitiesTable");
								if (null != entitiesTable) { // Could be null when launching generation during editor closing.
									((XjcEntity<GenerationScenarioType>) ((NativeQueryManager<?>) entitiesTable.getQueryManager())
											.getParentEntity()).setBean(scenarioTree.getGenerationScenario());
									entitiesTable.refresh();
								}
							}
							editor.postRefresh();
							if ("generationPage".equals(currentPage.getRegisterId()))
								((GenerationTreeController) getFromRootRegister("generationTree")).expandedURIs();
							AdiMessageDialog messageDialog = new AdiMessageDialog(display, MessageDialog.NONE, null,
									getFromStudioBundle("scenario.generate.complete.title"),
									getFromStudioBundle("scenario.generate.complete.message", scenarioResources.getPluginName()));
							messageDialog.getShell().setImage(AdichatzApplication.getInstance()
									.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_GENERATE_SCENARIO.png"));
							messageDialog.open();
							new ErroneousFilesFormDialog(null, display, scenarioResources);
							editor.setEditable(true);
							scenarioResources.setImpactModel(false);
							scenarioResources.setImpactRcp(false);
						});

				} finally {
					if (!editor.getComposite().isDisposed()) // editor could be closed before end of process
						editor.getComposite().getDisplay().syncExec(() -> {
							editor.getComposite().setEnabled(true);
						});
				}
				return Status.OK_STATUS;
			}
		};
		job.setName(generating);
		editor.getComposite().setEnabled(false);
		job.schedule(); // start as soon as possible
	}

	private void extractChildren(ScenarioTreeElement parentElement, List<GenerationUnitType> generationUnits,
			List<PluginEntityType> pluginEntities) {
		for (ScenarioTreeElement treeElement : parentElement.getChildren())
			if (treeElement.getBean() instanceof GenerationUnitType)
				generationUnits.add((GenerationUnitType) treeElement.getBean());
			else if (treeElement.getBean() instanceof PluginEntityWrapper) {
				PluginEntityWrapper pluginEntity = new PluginEntityWrapper(scenarioResources);
				ScenarioUtil.copyExtendedFields((PluginEntityWrapper) treeElement.getBean(), pluginEntity);
				pluginEntities.add(pluginEntity);
				extractChildren(treeElement, pluginEntity.getGenerationUnit(), null);
			}
	}

	private AWidgetController getFromRootRegister(String key) {
		return getRootCore().getFromRegister(key);
	}

	public void runRcpGeneration(ScenarioResources scenarioResources, boolean application) {
		this.scenarioResources = scenarioResources;
		XjcBindingService bindingService = (XjcBindingService) parentController.getBindingService();
		editor = (ScenarioFormEditor) bindingService.getEditor();
		currentPage = ((AdiFormPage) editor.getSelectedPage()).getFormPageController();
		scenarioResources = editor.getScenarioResources();
		display = editor.getSite().getWorkbenchWindow().getShell().getDisplay();
		scenarioTree = scenarioResources.getScenarioTree();
		generationScenario = (GenerationScenarioWrapper) scenarioTree.getGenerationScenario();
		scenarioResources.setImpactRcp(true);

		final IConfirmContent confirmContent = new IConfirmContent() {
			private Composite parent;

			@Override
			public void okPressed(List<Control> complementControls) {
				parent.setEnabled(false);
				GenerationTreeController generationTree = (GenerationTreeController) getFromRootRegister("generationTree");
				if (null != generationTree)
					generationTree.setExpandedURIs();
				generationRunnable = new GenerateRcpRunnable(editor, missingGS,
						null != scenarioCustomizationField ? scenarioCustomizationField.getValue() : null, flagMap);
				runJob();
			}

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				this.parent = parent;
				GenerateScenarioActionController.this.toolkit = toolkit;
				parent.setLayout(new MigLayout("wrap", "grow,fill"));
				flagMap.put("mergeCustomization", false);
				flagMap.put("generateRcpPluginEntities", false);
				flagMap.put("intialProcess", false);
				confirmNewRcp(parent, false);
			}
		};

		String title = application ? getFromStudioBundle("scenario.generation.rcp")
				: getFromStudioBundle("scenario.generate.rcp.application");
		try {
			confirmFormDialog = new ConfirmFormDialog(display.getActiveShell(), AdichatzApplication.getInstance().getFormToolkit(),
					title, getControl().getImageDescriptor().createImage(), confirmContent);
			confirmFormDialog.open();
		} catch (AdichatzErrorException e) {
		}
		scenarioResources.setImpactRcp(false);
	}

	public void confirmNewRcp(Composite parent, boolean addRcpPluginEntities) {
		createCustomScenarioFile(parent, toolkit);
		flagMap.put("generateRcpPart", false);
		if (null != scenarioCustomizationField) {
			Object customFileName = scenarioCustomizationField.getValue();
			if (null != customFileName && new File((String) customFileName).exists())
				createButton(parent, "mergeCustomization", "scenario.merge.customization", "gap top 20", true);
		}
		// if (addRcpPluginEntities)
		generateRcpPluginEntities = createButton(parent, "generateRcpPluginEntities", "scenario.generate.rcp.pluginEntities",
				"gap top 10", true);
		if (!scenarioTree.getGenerationScenario().getPluginEntity().isEmpty() && !scenarioResources.hasPluginEnittyTree())
			scenarioResources.setImpactRcp(true);
		if (!scenarioResources.hasRcpPart() && scenarioResources.isImpactRcp()) {
			IProject project = scenarioResources.getProject();

			final Button generateRcpPart = createButton(parent, "generateRcpPart", "scenario.has.rcp.part", "gap top 20", true);
			Button initializeRcpPart = createButton(parent, "initializeRcpPart", "scenario.generate.initialize.rcp", "gap left 20",
					scenarioResources.isImpactRcp());
			Button generateApplication = createButton(parent, "generateApplication", "scenario.generate.rcp.application",
					"gap left 20", !getApplicationFileNames(project, true).isEmpty());

			flagMap.put("hasNavigator", true);
			// look for navigator XML file
			boolean hasXmlNavigatorFile = scenarioResources.hasNavigator();

			Button generateNavigator = createButton(parent, "generateNavigator", "scenario.generate.navigator", "gap left 20",
					!hasXmlNavigatorFile);

			// Compute missing RCP components
			boolean addMissingComponent = true;
			for (GenerationUnitType generationUnit : generationScenario.getGenerationUnit())
				if (GenerationEnum.DETAIL == generationUnit.getType()) {
					addMissingComponent = false;
					break;
				}
			if (addMissingComponent)
				flagMap.put("intialProcess", true);
			else {
				missingGS = new GenerationScenarioWrapper();
				for (PluginEntityType pluginEntity : generationScenario.getPluginEntity()) {
					PluginEntityWrapper missingPE = new PluginEntityWrapper(pluginEntity);
					addMissingGU(pluginEntity, missingPE, GenerationEnum.DETAIL);
					addMissingGU(pluginEntity, missingPE, GenerationEnum.TABLE);
					addMissingGU(pluginEntity, missingPE, GenerationEnum.EDITOR);
					addMissingGU(pluginEntity, missingPE, GenerationEnum.MESSAGE_BUNDLE);
					if (!missingPE.getGenerationUnit().isEmpty()) {
						missingGS.getPluginEntity().add(missingPE);
						addMissingComponent = true;
					}
				}
			}
			final Button missingComponents = addMissingComponent
					? createButton(parent, "missingComponents", "scenario.missing.components", "gap left 20", true)
					: null;

			generateRcpPluginEntities.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					Button okButton = null;
					if (null != confirmFormDialog)
						okButton = confirmFormDialog.getButton(IDialogConstants.OK_ID);
					if (!generateRcpPluginEntities.getSelection()) {
						if (null != okButton)
							okButton.setEnabled(false);
						generateRcpPart.setEnabled(false);
						initializeRcpPart.setEnabled(false);
						generateApplication.setEnabled(false);
						generateNavigator.setEnabled(false);
						if (null != missingComponents)
							missingComponents.setEnabled(false);
					} else {
						if (null != okButton)
							okButton.setEnabled(true);
						generateRcpPart.setEnabled(true);
						initializeRcpPart.setEnabled(true);
						generateApplication.setEnabled(true);
						generateNavigator.setEnabled(true);
						if (null != missingComponents)
							missingComponents.setEnabled(true);
					}
				};
			});
			generateRcpPluginEntities.notifyListeners(SWT.Selection, null);
			generateRcpPart.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					if (!generateRcpPart.getSelection()) {
						initializeRcpPart.setEnabled(false);
						generateApplication.setEnabled(false);
						generateNavigator.setEnabled(false);
						if (null != missingComponents)
							missingComponents.setEnabled(false);
					} else {
						initializeRcpPart.setEnabled(true);
						generateApplication.setEnabled(true);
						generateNavigator.setEnabled(true);
						if (null != missingComponents)
							missingComponents.setEnabled(true);
					}
				};
			});
			generateRcpPart.notifyListeners(SWT.Selection, null);
		}
	}

	private void createSeparator(Composite parent) {
		Composite separator = toolkit.createCompositeSeparator(parent);
		separator.setLayoutData("hmax 2, gap 0 0 10 10");
	}

	private Button createButton(Composite parent, int style, final String key, String text, String layoutData, boolean value,
			Object... variables) {
		final Button button = toolkit.createButton(parent, getFromStudioBundle(text, variables), style);
		button.setData(key);
		button.setSelection(value);
		button.setLayoutData(layoutData);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				flagMap.put(key, button.getSelection());
			}
		});
		button.setBackground(button.getParent().getBackground());
		flagMap.put(key, value);
		return button;
	}

	private Button createButton(Composite parent, String key, String text, String layoutData, boolean value, Object... variables) {
		return createButton(parent, SWT.CHECK, key, text, layoutData, value, variables);
	}

	private Button createButton(Composite parent, final String key, String text, boolean value, Object... variables) {
		return createButton(parent, SWT.CHECK, key, text, null, value, variables);
	}

	private void createCustomScenarioFile(Composite parent, AdiFormToolkit toolkit) {
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new MigLayout("wrap 3, ins 0", "[][grow,fill]2[]"));
		toolkit.createLabel(composite, getFromStudioBundle("scenario.custom.label"));
		scenarioCustomizationField = toolkit.createFileText(composite, AdiSWT.DELETE_BUTTON);
		scenarioCustomizationField.setFilterExtension("*.xml");
		List<PluginEntityType> pluginEntities = scenarioResources.getScenarioTree().getGenerationScenario().getPluginEntity();
		if (!pluginEntities.isEmpty()) {
			String pluginName = EngineTools.getInstanceKeys(pluginEntities.get(0).getEntityURI())[0];
			ScenarioTreeWrapper scenarioTree;
			if (pluginName.isEmpty()) {
				scenarioTree = scenarioResources.getScenarioTree();
			} else {
				ScenarioResources modelSR = ScenarioResources.getInstance(pluginName, null);
				scenarioTree = modelSR.getScenarioTree();
			}
			DatasourceType datasource = ScenarioUtil.getDatasource(scenarioResources,
					scenarioTree.getGenerationScenario().getModelPart().getConnectorDataSource());
			String customizationFile = ScenarioUtil.evalLocation(scenarioResources.getBuffer(), datasource.getCustomizationFile());
			if (!EngineTools.isEmpty(customizationFile))
				scenarioCustomizationField.setValue(customizationFile);
		}
		MenuManager menuManager = new MenuManager("Custom FileText menu");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				if (!EngineTools.isEmpty(scenarioCustomizationField.getValue())) {
					Action openFileAction = new Action(getFromStudioBundle("scenario.custom.openFile"),
							AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_OPEN_FILE")) {
						@Override
						public void run() {
							ExternalResourcesFormEditor.openExternalFile(scenarioCustomizationField.getValue());
						}
					};
					mgr.add(openFileAction);
					Action copyFileNameAction = new Action(getFromStudioBundle("scenario.custom.copyFileName"),
							AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_COPY")) {
						@Override
						public void run() {
							EngineTools.copyToBuffer(scenarioCustomizationField.getValue());
						}
					};
					mgr.add(copyFileNameAction);
				}
			}
		});
		scenarioCustomizationField.setMenu(menuManager.createContextMenu(scenarioCustomizationField));
		scenarioCustomizationField.getText().setMenu(menuManager.createContextMenu(scenarioCustomizationField.getText()));

		String defaultCustomFile = StudioRcpPlugin.getDefault().getPreferenceStore()
				.getString(IStudioConstants.CUSTOMIZATION_FILE_MODEL);
		if (!EngineTools.isEmpty(defaultCustomFile))
			scenarioCustomizationField.setValue(defaultCustomFile);
		WizardUtil.createHelp(composite, "scenario.custom.toolTip", "scenario.custom.specify", "scenario.custom.help");

		createSeparator(parent);
	}

}
