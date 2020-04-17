package org.adichatz.studio.command;

import static org.adichatz.engine.common.EngineTools.isEmpty;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.collection.GroupController;
import org.adichatz.engine.controller.field.FileTextController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.controller.nebula.PGroupToolItemController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiMessageDialog;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.FileText;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ActionWhenTypeEnum;
import org.adichatz.generator.xjc.AddWhenEnum;
import org.adichatz.generator.xjc.CustomGenerationUnitType;
import org.adichatz.generator.xjc.CustomPostActionType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ComponentGeneration;
import org.adichatz.scenario.generation.ModelComponentGeneration;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import net.miginfocom.swt.MigLayout;

public class CustomScenarioMergeItem extends PGroupToolItemController {
	private static Image GENERATION_UNIT_IMAGE = AdichatzApplication.getInstance().getImage("org.adichatz.studio",
			"IMG_GENERATE_JAVA.png");

	private AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();

	private List<PluginEntityWrapper> workPluginEntities;

	private ScenarioResources scenarioResources;

	private GenerationScenarioWrapper customGenerationScenario;

	private FileTextController scenarioFileNameController;

	private CheckboxTableViewer customGenerationUnitTV;

	private List<Button> buttons = new ArrayList<>();

	public CustomScenarioMergeItem(String id, PGroupController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void run() {
		XjcBindingService bindingService = (XjcBindingService) getBindingService();
		ScenarioFormEditor editor = (ScenarioFormEditor) bindingService.getEditor();
		scenarioResources = editor.getScenarioResources();
		scenarioFileNameController = (FileTextController) genCode.getFromRegister("scenarioFileName");
		String fileName = scenarioFileNameController.getControl().getValue();
		fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		String title = getFromStudioBundle("scenario.custom.generateScenario.file", fileName, scenarioResources.getPluginName());
		new ConfirmFormDialog(null, toolkit, title,
				AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_GENERATE_SCENARIO.png"),
				getConfirmContent()) {
			protected void createButtonsForButtonBar(Composite parent) {
				Button deselectButton = createButton(parent, 99, getFromGeneratorBundle("deselect.all"), false);
				deselectButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						for (Button button : buttons)
							button.setSelection(false);
					}
				});
				Button selectButton = createButton(parent, 99, getFromGeneratorBundle("select.all"), false);
				selectButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						for (Button button : buttons)
							button.setSelection(true);
					}
				});
				super.createButtonsForButtonBar(parent);
			};
		}.open();
	}

	private IConfirmContent getConfirmContent() {
		return new IConfirmContent() {
			private Button integrateInScenarioFileBTN;

			private Button rewritePojosBTN;

			private Button actionResourcesBTN;

			private Button generateEJBBTN;

			private Button generatePersistenceFileBTN;

			private Button deployEJBBTN;

			private boolean applicationServer;

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				ScenarioTreeWrapper customScenarioTree = getCustomScenarioTree();
				CustomPostActionType postAction = customScenarioTree.getCustomPostAction();
				customGenerationScenario = (GenerationScenarioWrapper) customScenarioTree.getGenerationScenario();
				parent.setLayout(new MigLayout("wrap 1", "[grow, fill]"));
				integrateInScenarioFileBTN = createButton(parent, getFromStudioBundle("scenario.custom.integration"), true);
				integrateInScenarioFileBTN.setEnabled(null != postAction);
				if (null != postAction) {
					if (scenarioResources.hasModelPart())
						rewritePojosBTN = createButton(parent, getFromStudioBundle("scenario.custom.rewrite.pojos"), true);
					actionResourcesBTN = createButton(parent, getFromStudioBundle("scenario.custom.generate.action.resources"),
							true);
					List<CustomGenerationUnitType> customGenerationUnits = postAction.getCustomGenerationUnit();
					if (!customGenerationUnits.isEmpty()) {
						customGenerationUnitTV = CheckboxTableViewer.newCheckList(parent, SWT.NONE);
						customGenerationUnitTV.getTable().setLayoutData("wmin 500, hmax 400, gap top 10");
						customGenerationUnitTV.setContentProvider(ArrayContentProvider.getInstance());
						customGenerationUnitTV.setLabelProvider(new ColumnLabelProvider() {
							@Override
							public String getText(Object element) {
								CustomGenerationUnitType cgu = (CustomGenerationUnitType) element;
								if (!isEmpty(cgu.getEntityRegex()) && !isEmpty(cgu.getPropertyRegex())) {
									return getFromStudioBundle("scenario.custom.generate.unit.regex", cgu.getType(),
											cgu.getEntityRegex(), cgu.getPropertyRegex());
								} else if (!isEmpty(cgu.getEntityRegex()))
									return getFromStudioBundle("scenario.custom.generate.unit.entity.regex", cgu.getType(),
											cgu.getEntityRegex());
								else if (!isEmpty(cgu.getPropertyRegex()))
									return getFromStudioBundle("scenario.custom.generate.unit.property.regex", cgu.getType(),
											cgu.getPropertyRegex());
								else
									return getFromStudioBundle("scenario.custom.generate.unit.all", cgu.getType());
							}

							@Override
							public Image getImage(Object element) {
								return GENERATION_UNIT_IMAGE;
							}
						});
						customGenerationUnitTV.setInput(customGenerationUnits);
						customGenerationUnitTV.setAllChecked(true);
					}
					applicationServer = false;
					if (null != scenarioResources.getGenerationScenario().getModelPart()) {
						applicationServer = !IScenarioConstants.JSE
								.equals(scenarioResources.getGenerationScenario().getModelPart().getConnectorASVersion());
					}
					if (applicationServer && postAction.isGenerateEJB()) {
						generateEJBBTN = createButton(parent, getFromStudioBundle("scenario.generate.ejb"), true);
						generatePersistenceFileBTN = createButton(parent, getFromStudioBundle("scenario.generate.persistence.xml"),
								true);
					}
					if (applicationServer && postAction.isDeployEJB()) {
						deployEJBBTN = createButton(parent, getFromStudioBundle("scenario.deploy.ejb"), true);
						String ejbDeployDir = ScenarioUtil
								.getConnectorTree(scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI))
								.getEjbDeployDir(scenarioResources.getGenerationScenario().getModelPart().getConnectorASVersion());
						if (null == ejbDeployDir || !new File(ejbDeployDir).isDirectory()) {
							deployEJBBTN.setSelection(false);
							deployEJBBTN.setEnabled(false);
						}
					}
				}
			}

			@Override
			public void okPressed(List<Control> complementControls) {
				if (integrateInScenarioFileBTN.getSelection())
					integrateInsideScenarioFile();
				if (null != rewritePojosBTN && rewritePojosBTN.getSelection()) {
					scenarioResources.getScenarioTree().processAdditionalLibraries(scenarioResources, AddWhenEnum.MODEL);
					try {
						List<PluginEntityWrapper> pluginEntities = new ArrayList<>();
						for (PluginEntityType pluginEntity : scenarioResources.getGenerationScenario().getPluginEntity())
							pluginEntities.add((PluginEntityWrapper) pluginEntity);
						// launch rewriters
						scenarioResources.rewritePojos(pluginEntities, true, true);
					} catch (IOException | CoreException e) {
						logError(e);
					}
				}

				if (actionResourcesBTN.getSelection()) {
					if (scenarioResources.hasModelPart())
						scenarioResources.getScenarioTree().actionResource(scenarioResources,
								ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
					if (scenarioResources.hasRcpPart()) {
						scenarioResources.getScenarioTree().actionResource(scenarioResources, ActionWhenTypeEnum.WHEN_BUILDING_UI);
						scenarioResources.getScenarioTree().processAdditionalLibraries(scenarioResources, AddWhenEnum.UI);
					}

				}
				ComponentGeneration componentGeneration = scenarioResources.getComponentGeneration();
				GenerationScenarioWrapper workGenerationScenario = new GenerationScenarioWrapper();
				GenerationScenarioWrapper generationScenario = scenarioResources.getGenerationScenario();
				if (null != customGenerationUnitTV) {
					for (Object checkedElement : customGenerationUnitTV.getCheckedElements()) {
						CustomGenerationUnitType cgu = (CustomGenerationUnitType) checkedElement;
						if (GenerationEnum.NAVIGATOR == cgu.getType() && scenarioResources.hasRcpPart()) {
							for (GenerationUnitType generationUnit : generationScenario.getGenerationUnit())
								if (GenerationEnum.NAVIGATOR == generationUnit.getType() && ScenarioUtil
										.checkStringFilter(generationUnit.getAdiResourceURI(), cgu.getEntityRegex())) {
									workGenerationScenario.getGenerationUnit().add(generationUnit);
								}
						} else if (checkType(cgu.getType())) {
							if (isEmpty(cgu.getEntityRegex()) && isEmpty(cgu.getPropertyRegex())) {
								for (GenerationUnitType gu : generationScenario.getGenerationUnit())
									if (cgu.getType() == gu.getType())
										workGenerationScenario.getGenerationUnit().add(gu);
							} else {
								List<PluginEntityWrapper> pluginEntities = getPluginEntities(cgu.getEntityRegex(),
										cgu.getPropertyRegex());
								GenerationScenarioWrapper currentGenerationScenario = scenarioResources.getGenerationScenario();
								for (PluginEntityWrapper pluginEntity : pluginEntities) {
									PluginEntityWrapper pew = workGenerationScenario.getPluginEntity(pluginEntity.getEntityURI());
									if (null == pew) {
										pew = new PluginEntityWrapper();
										pew.setEntityURI(pluginEntity.getEntityURI());
										workGenerationScenario.addPluginEntity(pew);
									}
									PluginEntityWrapper currentPE = currentGenerationScenario
											.getPluginEntity(pluginEntity.getEntityURI());
									if (null != currentPE) {
										GenerationUnitType customGU = currentPE.getGenerationUnit(cgu.getType());
										if (null != customGU)
											pew.getGenerationUnit().add(customGU);
									}
								}
							}
						}
					}
					componentGeneration.generateAll(workGenerationScenario, true);
				}
				if (scenarioResources.hasModelPart()) {
					ModelComponentGeneration modelComponentGeneration = componentGeneration.getModelComponentGeneration();
					if (generatePersistenceFileBTN.getSelection())
						modelComponentGeneration.createPersistencesXmlFile();
					if (applicationServer && generateEJBBTN.getSelection()) {
						if (actionResourcesBTN.getSelection())
							scenarioResources.getScenarioTree().actionResource(scenarioResources,
									ActionWhenTypeEnum.WHEN_BUILDING_EJB_JAR);
						modelComponentGeneration.createEJBJar(false);
					}
					if (applicationServer && deployEJBBTN.getSelection()) {
						String ejbDeployDir = ScenarioUtil
								.getConnectorTree(scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI))
								.getEjbDeployDir(scenarioResources.getGenerationScenario().getModelPart().getConnectorASVersion());
						logInfo(getFromGeneratorBundle("jpa.deployingEjbJar", ejbDeployDir));
						final String sourceFileName = scenarioResources.getProjectEJBJarFileName();
						final String destinationFileName = ejbDeployDir + "/" + scenarioResources.getEjbJarFileName();
						FileUtil.copyFile(sourceFileName, destinationFileName, true);
					}
				}
				scenarioResources.marshalScenarioFile();
				AdiMessageDialog messageDialog = new AdiMessageDialog(integrateInScenarioFileBTN.getDisplay(), MessageDialog.NONE,
						null, getFromStudioBundle("scenario.generate.complete.title"),
						getFromStudioBundle("scenario.generate.complete.message", scenarioResources.getPluginName()));
				messageDialog.getShell().setImage(
						AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_GENERATE_SCENARIO.png"));
				messageDialog.open();
			}

			private List<PluginEntityWrapper> getPluginEntities(String entityRegex, String propertyRegex) {
				List<PluginEntityWrapper> pluginEntities = new ArrayList<>();
				for (PluginEntityType pluginEntity : scenarioResources.getScenarioTree().getGenerationScenario()
						.getPluginEntity()) {
					PluginEntityWrapper pew = (PluginEntityWrapper) pluginEntity;
					if (isEmpty(entityRegex) || ScenarioUtil.checkStringFilter(pew.getEntityId(), entityRegex)) {
						if (isEmpty(propertyRegex) || ScenarioUtil.checkPropertiesFilter(pew.getBeanClass(), propertyRegex)) {
							pluginEntities.add(pew);
						}
					}
				}

				return pluginEntities;
			}

			private Button createButton(Composite parent, final String text, boolean value) {
				final Button button = toolkit.createButton(parent, text, SWT.CHECK);
				button.setSelection(value);
				button.setBackground(button.getParent().getBackground());
				buttons.add(button);
				return button;
			}

			private boolean checkType(GenerationEnum type) {
				if (scenarioResources.hasModelPart() && scenarioResources.hasRcpPart())
					return true;
				if (scenarioResources.hasModelPart())
					switch (type) {
					case ENTITY:
					case QUERY:
						return true;
					default:
						break;
					}
				if (scenarioResources.hasRcpPart())
					switch (type) {
					case DETAIL:
					case EDITOR:
					case MESSAGE_BUNDLE:
					case TABLE:
						return true;
					default:
						break;
					}
				return false;
			}

			private void integrateInsideScenarioFile() {
				scenarioFileNameController.getValidation().validate(); // reload scenario tree.

				ScenarioTreeWrapper customScenarioTree = getCustomScenarioTree();

				int parts = IPluginEntityScenario.INIT;
				if (scenarioResources.hasModelPart()) {
					parts |= IPluginEntityScenario.MODEL_PART | IPluginEntityScenario.ENTITY;
				}
				if (scenarioResources.hasRcpPart()) {
					parts |= IPluginEntityScenario.RCP_PART;
				}
				scenarioResources.getScenarioTree().mergeCustomization(scenarioResources, customScenarioTree, parts);
				FileText fileText = (FileText) genCode.getFromRegister("scenarioFileName").getControl();
				scenarioResources.getScenarioTree().addCustomizedScenario(fileText.getValue());
				scenarioResources.loadParams(true);

				workPluginEntities = new ArrayList<>();
				Map<MultiKey, PluginEntityType> pluginEntityMap = new HashMap<>();
				for (PluginEntityType pluginEntity : scenarioResources.getGenerationScenario().getPluginEntity()) {
					Object[] entityKeys = EngineTools.getInstanceKeys(pluginEntity.getEntityURI());
					pluginEntityMap.put(new MultiKey(entityKeys), pluginEntity);
					pluginEntityMap.put(new MultiKey(entityKeys[1], entityKeys[2]), pluginEntity);
					pluginEntityMap.put(new MultiKey(entityKeys[2]), pluginEntity);
				}

				for (PluginEntityType customPE : customGenerationScenario.getPluginEntity()) {
					String[] customEntityKeys = EngineTools.getInstanceKeys(customPE.getEntityURI());
					List<Object> keys = new ArrayList<>(3);
					for (String key : customEntityKeys)
						if (!isEmpty(key) && !".".equals(key))
							keys.add(key);
					PluginEntityWrapper pew = (PluginEntityWrapper) pluginEntityMap.get(new MultiKey(keys.toArray()));
					if (null != pew) {
						customPE.setEntityURI(pew.getEntityURI());
						workPluginEntities.add(pew);
					}
					((PluginEntityWrapper) customPE).clearUnitMap();
				}
				scenarioResources.marshalScenarioFile();
				scenarioResources.refresh();
			}

			private ScenarioTreeWrapper getCustomScenarioTree() {
				GroupController parentGroupController = (GroupController) genCode.getFromRegister("customScenarioGroup");
				ScenarioTreeWrapper customScenarioTree = (ScenarioTreeWrapper) parentGroupController.getEntity().getBean();
				return customScenarioTree;
			}
		};
	}
}
