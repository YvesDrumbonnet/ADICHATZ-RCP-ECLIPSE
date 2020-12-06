package org.adichatz.studio.xjc.controller;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.function.BooleanSupplier;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.field.FileTextController;
import org.adichatz.engine.controller.nebula.GridController;
import org.adichatz.engine.controller.nebula.PGroupToolItemController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.studio.util.StudioUtil;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.editor.ExternalResourcesFormEditor;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

public class CustomFileTextController extends FileTextController {

	private GridController<?> customGridController;

	private MenuManagerController contextMenu;

	public CustomFileTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		super.createControl();
		customGridController = (GridController<?>) genCode.getFromRegister("customsGrid");
		fileText.getText().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				selectCustomFile();
			}
		});
		addValidator(new AValidator(this, "invalidFile") {
			@Override
			public int validate() {
				ControllerCore core = triggeringController.getGenCode();
				((PGroupToolItemController) core.getFromRegister("mergeScenario")).setEnabled(false);
				Object validation = selectCustomFile();
				if (null != validation) {
					if (validation instanceof ScenarioTreeWrapper) {
						ScenarioTreeWrapper customScenarioTree = (ScenarioTreeWrapper) validation;
						if (null == ((ScenarioTreeWrapper) validation).getCustomPostAction())
							EngineTools.openDialog(MessageDialog.INFORMATION,
									getFromStudioBundle("scenario.custom.no.action.title"),
									getFromStudioBundle("scenario.custom.no.action.message"));
						else {
							if (null != customScenarioTree.getGenerationScenario()) {
								XjcBindingService bindingService = (XjcBindingService) triggeringController.getParentController()
										.getBindingService();
								ScenarioFormEditor editor = (ScenarioFormEditor) bindingService.getEditor();
								ScenarioResources scenarioResources = editor.getScenarioResources();
								GenerationScenarioWrapper generationScenario = scenarioResources.getGenerationScenario();
								if (null != generationScenario)
									for (PluginEntityType customPE : customScenarioTree.getGenerationScenario().getPluginEntity())
										((PluginEntityWrapper) customPE).setScenarioResources(scenarioResources);
							}
							((PGroupToolItemController) core.getFromRegister("mergeScenario")).setEnabled(true);
						}
					} else if (validation instanceof String)
						return setLevel(IMessageProvider.ERROR, (String) validation);
				}
				return setLevel(IMessageProvider.NONE, null);
			}
		});

		// Create context menu on controller
		contextMenu = new MenuManagerController("ftContextMenu", this, genCode, fileText, fileText.getText()) {
			@Override
			public void menuAboutToShow() {
				super.menuAboutToShow();
				// Creates control for ActionController openFileACT
				ActionController openFileACT = new ActionController("openFile", contextMenu, genCode) {
					@Override
					public void initialize() {
						BooleanSupplier evaluatorENABLED;
						evaluatorENABLED = new BooleanSupplier() {
							public boolean getAsBoolean() {
								return null != fileText.getValue();
							}
						};
						addAccessibility(
								new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.ENABLED, evaluatorENABLED));
						super.initialize();
					}

					@Override
					public void createControl() {
						if (isValid()) {
							action = new AAction() {
								@Override
								public void runAction() {
									ExternalResourcesFormEditor.openExternalFile(fileText.getValue());
								}
							};
							action.setActionController(this);
						}
						super.createControl();
						if (isValid()) {
							getControl().setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_OPEN_FILE"));
							getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio",
									"adichatzStudio", "scenario.custom.openFile"));
						}
					}
				};
				openFileACT.initialize();
				if (openFileACT.isValid()) {
					openFileACT.createControl();
					contextMenu.getControl().add(openFileACT.getControl());
				}
				// Creates control for ActionController copyFileNameACT
				ActionController copyFileNameACT = new ActionController("copyFileName", contextMenu, genCode) {
					@Override
					public void initialize() {
						BooleanSupplier evaluatorENABLED;
						evaluatorENABLED = new BooleanSupplier() {
							public boolean getAsBoolean() {
								return null != fileText.getValue();
							}
						};
						addAccessibility(
								new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.ENABLED, evaluatorENABLED));
						super.initialize();
					}

					@Override
					public void createControl() {
						if (isValid()) {
							action = new AAction() {
								@Override
								public void runAction() {
									EngineTools.copyToBuffer(fileText.getValue());
								}
							};
							action.setActionController(this);
						}
						super.createControl();
						if (isValid()) {
							getControl().setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_COPY"));
							getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio",
									"adichatzStudio", "scenario.custom.copyFileName"));
						}
					}
				};
				copyFileNameACT.initialize();
				if (copyFileNameACT.isValid()) {
					copyFileNameACT.createControl();
					contextMenu.getControl().add(copyFileNameACT.getControl());
				}
			}
		};
		contextMenu.createControl();

	}

	private Object selectCustomFile() {
		customGridController.getViewer().setSelection(StructuredSelection.EMPTY);
		Object customResult = StudioUtil.validateCustomizationFile(fileText.getValue(), genCode);
		if (customResult instanceof ScenarioTreeWrapper) {
			return customResult;
		}
		return customResult;
	}

	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		getValidation().validate();
	}
}
