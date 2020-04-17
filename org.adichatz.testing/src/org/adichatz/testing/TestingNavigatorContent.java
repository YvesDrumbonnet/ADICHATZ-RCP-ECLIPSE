package org.adichatz.testing;

import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.menu.ANodeController;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.controller.utils.INavigator;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ARootMenuCore;
import org.adichatz.engine.e4.part.ANavigator;
import org.adichatz.engine.e4.part.AdiInputPart;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.E4SimulationTools;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.testing.xjc.AdichatzTestingTree;
import org.adichatz.testing.xjc.SuiteType;
import org.adichatz.testing.xjc.TestFileType;
import org.adichatz.testing.xjc.TestNodeType;
import org.adichatz.testing.xjc.TestType;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.swt.graphics.Image;

public class TestingNavigatorContent extends ARootMenuCore {
	private AdichatzTestingTree testingTree;

	private MenuController testingMenuController;

	public TestingNavigatorContent(MenuController rootMenuController) {
		super("org.adichatz.testing", rootMenuController);
	}

	/**
	 * Creates children (menu or item) for menu GroupNavigatorContent
	 */
	public void createChildren() {
		TestingTools.init();
		testingTree = TestingTools.getTestingTree(false);
		String label = TestingTools.getFromTestingBundle("testing.root.menu");
		coreController = new MenuController(pluginResources, this, "testingNavigatorContent", label, null);

		testingMenuController = new MenuController(pluginResources, this, "testingNavigatorMenu", label, null) {
			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_ADI_TEST.png");
			}
		};
		if (testingTree.isExpanded())
			testingMenuController.setExpanded(true);
		coreController.getChildren().add(testingMenuController);

		final MenuController toolMenuController = new MenuController(pluginResources, this, "#testing.tool#",
				getFromTestingBundle("testing.tool"), null) {
			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_TESTING_TOOLS.png");
			}
		};
		testingMenuController.getChildren().add(toolMenuController);

		ItemController item;
		item = new ItemController(pluginResources, this, "#testing.result.manager#", getFromTestingBundle("testing.result.manager"),
				null) {
			@Override
			public void handleActivate() {
				TestingTools.activeTestManagerPart();
			}

			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_ADI_TEST.png");
			}
		};
		toolMenuController.getChildren().add(item);
		if (1 < testingTree.getSuiteOrTestFile().size()) {
			item = new ItemController(pluginResources, this, "#testing.all.suites_and_files#",
					getFromTestingBundle("testing.all.suites_and_files"), null) {
				@Override
				public void handleActivate() {
					TestingTools.APPLICATION_TEST_RUNNER.runTesting(testingTree, true);
				}

				@Override
				public Image getImage() {
					return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_SUITES_XML.png");
				}
			};
			toolMenuController.getChildren().add(item);
		}
		item = new ItemController(pluginResources, this, "#testing.display.register.editor#",
				getFromTestingBundle("testing.display.register.editor"), null) {
			@Override
			public void handleActivate() {
				MPart part = (MPart) E4SimulationTools.getSelectedEditor();
				if (part instanceof AdiInputPart && null != part.getObject() && part.getObject() instanceof BoundedPart) {
					EngineE4Util.displayRegisterTree((BoundedPart) part.getObject());
				} else
					logWarning(getFromTestingBundle("testing.no.editor.register"));
			}

			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_DISPLAY_REGISTER.png");
			}
		};
		toolMenuController.getChildren().add(item);

		item = new ItemController(pluginResources, this, "#testing.display.register.outline#",
				getFromTestingBundle("testing.display.register.outline"), null) {
			@Override
			public void handleActivate() {
				MStackElement editor = E4SimulationTools.getSelectedEditor();
				if (editor instanceof AdiInputPart) {
					E4SimulationTools.handleActivateEditor((AdiInputPart) editor); // Reactivate current editor to avoid default outline
					AContainerCore containerCore = E4SimulationTools.getActiveOutlineContainerCore();
					if (null != containerCore) {
						EngineE4Util.displayRegisterTree(containerCore.getController());
					} else
						logWarning(getFromTestingBundle("testing.no.outline.register"));
				} else
					logWarning(getFromTestingBundle("testing.no.outline.register"));
			}

			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_DISPLAY_REGISTER.png");
			}
		};
		toolMenuController.getChildren().add(item);

		item = new ItemController(pluginResources, this, "#testing.display.register.navigator#",
				getFromTestingBundle("testing.display.register.navigator"), null) {
			@Override
			public void handleActivate() {
				MStackElement navigator = E4SimulationTools.getSelectedNavigator();
				if (null != navigator && navigator instanceof MPart && ((MPart) navigator).getObject() instanceof ANavigator)
					EngineE4Util.displayNavigatorTree(navigator.getElementId());
				else
					logWarning(getFromTestingBundle("testing.no.navigator.register"));
			}

			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_DISPLAY_REGISTER.png");
			}
		};
		toolMenuController.getChildren().add(item);

		item = new ItemController(pluginResources, this, "#testing.refresh.menu#", getFromTestingBundle("testing.refresh.menu"),
				null) {
			@Override
			public void handleActivate() {
				INavigator navigator = (INavigator) ((MPart) E4SimulationTools.getSelectedNavigator()).getObject();
				for (ANodeController nodeController : testingMenuController.getChildren()
						.toArray(new ANodeController[testingMenuController.getChildren().size()])) {
					if (!nodeController.equals(toolMenuController))
						navigator.removeMenuController(testingMenuController, nodeController);
				}
				testingTree = TestingTools.getTestingTree(true);
				refreshTesting();
				navigator.refreshMenuController(testingMenuController);
			}

			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(TestingTools.TESTING_BUNDLE, "IMG_TEST_REFRESH.png");
			}
		};
		toolMenuController.getChildren().add(item);

		refreshTesting();
	}

	private void refreshTesting() {
		ItemController item;
		for (TestNodeType node : testingTree.getSuiteOrTestFile())
			if (node instanceof SuiteType) {
				final SuiteType suite = (SuiteType) node;
				String label = TestingTools.getLabel(suite);
				String toolTipText = null == label ? label : suite.getToolTipText();
				final MenuController menu = new MenuController(pluginResources, this, suite.getId(), label, toolTipText) {
					@Override
					public void handleActivate() {
						TestingTools.APPLICATION_TEST_RUNNER.runSuite(suite);
					};

					@Override
					public Image getImage() {
						return TestingTools.getRegistry("suiteImage");
					}
				};
				if (suite.isExpanded())
					menu.setExpanded(true);
				if (1 < suite.getTest().size()) {
					item = new ItemController(pluginResources, this, suite.getId().concat("#all.tests#"),
							getFromTestingBundle("testing.all.tests"), null) {
						@Override
						public void handleActivate() {
							TestingTools.APPLICATION_TEST_RUNNER.runSuite(suite);
						}

						@Override
						public Image getImage() {
							return TestingTools.getRegistry("runSuiteImage");
						}
					};
					menu.getChildren().add(item);
				}
				for (TestType test : suite.getTest()) {
					addTestItem(menu, test);
				}
				testingMenuController.getChildren().add(menu);
			} else if (node instanceof TestFileType)
				addTestFileItem(testingMenuController, (TestFileType) node);
			else if (node instanceof TestType)
				addTestItem(testingMenuController, (TestType) node);
	}

	private void addTestFileItem(MenuController menuController, TestFileType testFile) {
		final String testFileName = testFile.getFileName();
		final String label = TestingTools.getLabel(testFile);
		String toolTipText = null == testFile.getToolTipText() ? testFileName : testFile.getToolTipText();
		ItemController item = new ItemController(pluginResources, this, testFile.getId(), label, toolTipText) {
			public void handleActivate() {
				TestingTools.APPLICATION_TEST_RUNNER.runFile(label, testFileName, testFile.isForceOpenManagerPart());
			}

			@Override
			public Image getImage() {
				return TestingTools.getRegistry("runFileImage");
			}
		};
		menuController.getChildren().add(item);
	}

	private void addTestItem(MenuController menuController, final TestType test) {
		String label = TestingTools.getLabel(test);
		String toolTipText = null == test.getToolTipText() ? test.getTestURI() : test.getToolTipText();
		ItemController item = new ItemController(pluginResources, this, test.getId(), label, toolTipText) {
			@Override
			public void handleActivate() {
				TestingTools.APPLICATION_TEST_RUNNER.runClasses(getFromTestingBundle("testing.test", TestingTools.getLabel(test)),
						TestingTools.getRegistry("runTestImage"), test.isForceOpenManagerPart(), test.getTestURI());
			}

			@Override
			public Image getImage() {
				return TestingTools.getRegistry("runTestImage");
			}
		};
		menuController.getChildren().add(item);
	}
}
