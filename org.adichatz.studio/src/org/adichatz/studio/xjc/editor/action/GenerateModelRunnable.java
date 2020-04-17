package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.PluginEntityTreeGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ComponentGeneration;
import org.adichatz.scenario.generation.ModelComponentGeneration;
import org.adichatz.scenario.generation.PersistenceManagerGenerator;
import org.adichatz.scenario.util.JBossStandaloneReader;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.custom.StandaloneFileChangeDialog;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

@SuppressWarnings("restriction")
public class GenerateModelRunnable implements Runnable {
	private GenerationScenarioWrapper generationScenario;

	private Map<String, Boolean> flagMap;

	private ScenarioTreeWrapper scenarioTree;

	private JBossStandaloneReader jbossStandaloneReader;

	private ScenarioResources scenarioResources;

	private ConfirmFormDialog confirmFormDialog;

	private ModelPartType modelPart;

	private ModelComponentGeneration modelComponentGeneration;

	private String customizationScenarioFileName;

	public GenerateModelRunnable(ScenarioFormEditor editor, GenerationScenarioWrapper generationScenario,
			String customizationScenarioFileName, Map<String, Boolean> flagMap) {
		this.flagMap = flagMap;
		this.generationScenario = generationScenario;
		this.scenarioTree = (ScenarioTreeWrapper) editor.getTreeWrapper(false, false);
		this.scenarioResources = editor.getScenarioResources();
		this.customizationScenarioFileName = customizationScenarioFileName;
	}

	@Override
	public void run() {
		try {
			ComponentGeneration componentGeneration = scenarioResources.getComponentGeneration();
			componentGeneration.setGenerationScenario(generationScenario);

			ScenarioTreeWrapper customizationScenarioTree = null;
			if (flagMap.get("mergeCustomization") && null != customizationScenarioFileName) {
				customizationScenarioTree = ScenarioUtil.getCustomScenarioTree(scenarioResources,
						customizationScenarioFileName);
				scenarioResources.getScenarioTree().mergeCustomization(scenarioResources, customizationScenarioTree,
						IPluginEntityScenario.INIT);
				if (null != customizationScenarioTree.getPathElements()
						&& !customizationScenarioTree.getPathElements().getPathElement().isEmpty())
					scenarioResources.loadParams(true);
			}

			if (flagMap.get("generateRcpPluginEntities")) { // No model part only RCP part with plugin entities
				ScenarioInput scenarioInput = new ScenarioInput(scenarioResources, EngineConstants.PLUGIN_ENTITY_TREE,
						"", false, true, true, false);
				new PluginEntityTreeGenerator(scenarioInput);
				scenarioResources.loadScenarioParameters();
				scenarioResources.incrementalBuildProject();
			} else {
				modelComponentGeneration = componentGeneration.getModelComponentGeneration();
				GenerationScenarioWrapper generationScenario = (GenerationScenarioWrapper) scenarioTree
						.getGenerationScenario();
				modelPart = generationScenario.getModelPart();

				if (flagMap.get("addDriverModule"))
					modelComponentGeneration.addDriverModule();
				if (flagMap.get("addDriverInStandalone"))
					getJBossStandaloneReader(scenarioResources).addDriver();
				if (flagMap.get("addDatasourceInStandalone"))
					getJBossStandaloneReader(scenarioResources).addDatasource();
				if (flagMap.get("addDriverInStandalone") || flagMap.get("addDatasourceInStandalone")
						|| flagMap.get("addDatasourceInStandalone")) {
					final Display display = Display.getDefault();
					display.syncExec(() -> {
						new StandaloneFileChangeDialog(display.getActiveShell(),
								AdichatzApplication.getInstance().getFormToolkit(),
								getJBossStandaloneReader(scenarioResources)).open();
					});
				}

				if (flagMap.get("procurePojo")) {
					final Display display = Display.getDefault();
					switch (modelPart.getModelProcurement()) {
					case HIBERNATE:
						modelComponentGeneration.generatePojo(modelPart.getConnectorDataSource());
						break;
					case FOLDER:
						display.syncExec(() -> {
							confirmFormDialog = new ConfirmFormDialog(display.getActiveShell(),
									AdichatzApplication.getInstance().getFormToolkit(),
									getFromStudioBundle("scenario.pojo.folder.choose"), null, getConfirmContent()) {
								@Override
								protected void createButtonsForButtonBar(Composite parent) {
									super.createButtonsForButtonBar(parent);
									getButton(IDialogConstants.OK_ID).setEnabled(false);
								}
							};
							confirmFormDialog.open();
						});
						break;
					default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
						break;
					}
				}
				if (scenarioResources.getModelFileNames().isEmpty())
					Display.getDefault().asyncExec(() -> {
						logWarning(getFromStudioBundle("scenario.no.model.file.warning"));
						EngineTools.openDialog(MessageDialog.INFORMATION,
								getFromStudioBundle("scenario.no.model.file.title"),
								getFromStudioBundle("scenario.no.model.file.message"));
					});
				else {
					if (flagMap.get("addComponentsRewrite")) {
						List<PluginEntityWrapper> workPluginEntities = new ArrayList<>(
								modelComponentGeneration.getWorkPluginEntityMap(generationScenario).values());
						scenarioTree.addModelResources(scenarioResources);
						modelComponentGeneration.generateModelResources(workPluginEntities);
						if (null != customizationScenarioTree) {
							scenarioTree.mergeCustomization(scenarioResources, customizationScenarioTree,
									IPluginEntityScenario.MODEL_PART);
							((GenerationScenarioWrapper) scenarioTree.getGenerationScenario()).mergeCustomization(
									customizationScenarioTree.getGenerationScenario(),
									scenarioResources.getPluginName(), IPluginEntityScenario.ENTITY);
						}
						generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.ENTITY);
						generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.QUERY);
						scenarioResources.rewritePojos(rewrited(workPluginEntities, customizationScenarioTree), true,
								true);
					}

					if (flagMap.get("generateMetaModel")) {
						modelComponentGeneration.generateMetaModel(scenarioTree, null,
								flagMap.get("generateOnlyChange"));
					}

					// If change on Pojo, meta model, Ejb or callback and pluginEntityTree exists
					if ((flagMap.get("procurePojo") || flagMap.get("generateMetaModel") || flagMap.get("generateEjb")
							|| flagMap.get("generateCallback")) && scenarioResources.hasPluginEnittyTree())
						new PersistenceManagerGenerator().generate(scenarioResources);
					if (flagMap.get("generateEjb")) {
						if (flagMap.get("generatePersistenceXML"))
							modelComponentGeneration.createPersistencesXmlFile();
						modelComponentGeneration.createEJBJar(flagMap.get("deployEjb"));
					}
				}
			}
			scenarioResources.setImpactModel(null);
			ScenarioUtil.createXmlFile(scenarioTree.getXmlFile(), scenarioTree);
			IFile scenarioFile = scenarioResources.getXmlFolder().getFile(GeneratorConstants.SCENARIO_FILE);
			ScenarioUtil.createXmlFile(scenarioFile, scenarioTree, generationScenario);
			if (scenarioResources.hasPluginEnittyTree())
				scenarioResources.refreshPluginEntityTree();
			componentGeneration.buildBuildPropertiesFile();
			componentGeneration.refreshProject();
		} catch (IOException | CoreException e) {
			logError(e);
		}
	}

	private List<PluginEntityWrapper> rewrited(List<PluginEntityWrapper> workPluginEntities,
			ScenarioTreeWrapper customizationScenarioTree) {
		if (null != customizationScenarioTree && null != customizationScenarioTree.getGenerationScenario()) {
			for (PluginEntityWrapper workPE : workPluginEntities)
				for (PluginEntityType customPE : customizationScenarioTree.getGenerationScenario().getPluginEntity()) {
					String[] workKeys = EngineTools.getInstanceKeys(workPE.getEntityURI());
					String[] customKeys = EngineTools.getInstanceKeys(customPE.getEntityURI());
					if (EngineTools.isEmpty(customKeys[0]) || ".".equals(customKeys[0]))
						customKeys[0] = workKeys[0];
					if (EngineTools.isEmpty(customKeys[1]) || ".".equals(customKeys[1]))
						customKeys[1] = workKeys[1];
					if (Arrays.equals(workKeys, customKeys))
						workPE.mergeCustomization(customPE, false, true);
				}
		}
		return workPluginEntities;
	}

	private JBossStandaloneReader getJBossStandaloneReader(ScenarioResources scenarioResources) {
		if (null == jbossStandaloneReader) {
			jbossStandaloneReader = ScenarioUtil.getJBossStandaloneReader(modelPart, scenarioResources);
		}
		return jbossStandaloneReader;
	}

	private IConfirmContent getConfirmContent() {
		return new IConfirmContent() {
			private TreeViewer treeViewer;

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit,
					List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap 2, ins 0", "grow,fill", "[][grow,fill]"));
				final List<IJavaProject> javaProjects = new ArrayList<IJavaProject>();
				for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
					if (project.isOpen())
						try {
							IJavaProject javaProject = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
							if (null != javaProject)
								javaProjects.add(javaProject);
						} catch (CoreException e) {
							logError(e);
						}
				}
				treeViewer = new TreeViewer(parent, SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				treeViewer.getTree().setLayoutData("h 300!, w 300!");
				treeViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof IPackageFragmentRoot)
							return ((IPackageFragmentRoot) element).getResource().getProjectRelativePath().toString();
						return ((IJavaElement) element).getElementName();
					}

					@Override
					public Image getImage(Object element) {
						if (element instanceof IJavaProject)
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_JAVA_PROJECT.png");
						else if (element instanceof IPackageFragmentRoot)
							return JavaPlugin.getImageDescriptorRegistry()
									.get(JavaPluginImages.DESC_OBJS_PACKFRAG_ROOT);
						else if (element instanceof IJavaElement)
							return JavaPlugin.getImageDescriptorRegistry().get(JavaPluginImages.DESC_OBJS_PACKAGE);
						else
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_ERROR.png");
					}
				});
				treeViewer.setContentProvider(new ITreeContentProvider() {
					@Override
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					}

					@Override
					public void dispose() {
					}

					@Override
					public boolean hasChildren(Object element) {
						return !(element instanceof IPackageFragment);
						// return null != getChildren(element);
					}

					@Override
					public Object getParent(Object element) {
						return null;
					}

					@Override
					public Object[] getElements(Object inputElement) {
						return javaProjects.toArray();
					}

					@Override
					public Object[] getChildren(Object parentElement) {
						try {
							if (parentElement instanceof IJavaProject) {
								List<Object> sourceFolders = new ArrayList<Object>();
								for (IPackageFragmentRoot packageFragmentRoot : ((IJavaProject) parentElement)
										.getPackageFragmentRoots())
									if (IPackageFragmentRoot.K_SOURCE == packageFragmentRoot.getKind())
										sourceFolders.add(packageFragmentRoot);
								return sourceFolders.toArray();
							} else if (parentElement instanceof IPackageFragmentRoot) {
								List<IJavaElement> javaElements = new ArrayList<IJavaElement>();
								for (IJavaElement javaElement : ((IPackageFragmentRoot) parentElement).getChildren()) {
									if (javaElement instanceof IPackageFragment) {
										if (hasClassFile((IPackageFragment) javaElement))
											javaElements.add(javaElement);
									}
								}
								return javaElements.toArray();
							}
						} catch (JavaModelException e) {
							logError(e);
						}
						return null;
					}
				});
				treeViewer.setInput(javaProjects);
				treeViewer.expandToLevel(1);
				final TableViewer tableViewer = new TableViewer(parent, SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				tableViewer.getTable().setLayoutData("h 300!, w 300!");
				tableViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						return ((ICompilationUnit) element).getElementName();
					}

					@Override
					public Image getImage(Object element) {
						return JavaPlugin.getImageDescriptorRegistry().get(JavaPluginImages.DESC_OBJS_CLASS);
					}
				});
				final IStructuredContentProvider contentProvider = new IStructuredContentProvider() {
					@Override
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					}

					@Override
					public void dispose() {
					}

					@Override
					public Object[] getElements(Object inputElement) {
						if (null != inputElement) {
							try {
								return ((IPackageFragment) inputElement).getCompilationUnits();
							} catch (JavaModelException e) {
								logError(e);
							}
						}
						return new ICompilationUnit[0];
					}
				};
				tableViewer.setContentProvider(contentProvider);
				treeViewer.addSelectionChangedListener((event) -> {
					Object selectedObject = null;
					if (!event.getSelection().isEmpty()) {
						selectedObject = ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();
						if (!(selectedObject instanceof IPackageFragment))
							selectedObject = null;
						confirmFormDialog.getButton(IDialogConstants.OK_ID)
								.setEnabled(selectedObject instanceof IPackageFragment);
					}
					tableViewer.setInput(selectedObject);
					tableViewer.refresh();
				});
			}

			boolean hasClassFile(IPackageFragment packageFragment) {
				try {
					if (0 < packageFragment.getCompilationUnits().length) {
						for (ICompilationUnit compilationUnit : packageFragment.getCompilationUnits()) {
							if (GeneratorUtil.hasAnnotation(compilationUnit, "Entity")
									|| GeneratorUtil.hasAnnotation(compilationUnit, "Embeddable"))
								return true;
						}
					}
					for (IJavaElement javaElement : packageFragment.getChildren())
						if (javaElement instanceof IPackageFragment && hasClassFile((IPackageFragment) javaElement))
							return true;
				} catch (JavaModelException e) {
					logError(e);
				}
				return false;

			}

			@Override
			public void okPressed(List<Control> complementControls) {
				modelComponentGeneration.beforePojoProcurement(false);
				IPackageFragment srcPackageFragment = (IPackageFragment) ((IStructuredSelection) treeViewer
						.getSelection()).getFirstElement();
				try {
					IPackageFragmentRoot packageFragmentRoot = scenarioResources.getJavaProject()
							.getPackageFragmentRoot(scenarioResources.getProject().getFolder("src"));
					IPackageFragment targetPackageFragment = packageFragmentRoot
							.getPackageFragment(modelPart.getModelPackageName());
					scenarioResources.getJavaProject().getJavaModel().copy(srcPackageFragment.getChildren(),
							new IJavaElement[] { targetPackageFragment }, null, null, true, null);
					logInfo(getFromGeneratorBundle("scenario.copy.pojos", targetPackageFragment.getElementName()));
				} catch (JavaModelException e) {
					logError(e);
				}

			}
		};
	}
}
