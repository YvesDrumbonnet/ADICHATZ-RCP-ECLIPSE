package org.adichatz.testing;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import java.text.SimpleDateFormat;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FiltersMatcher;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.utils.INavigator;
import org.adichatz.engine.e4.part.ANavigator;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.resource.E4SimulationTools;
import org.adichatz.engine.xjc.MenuPathType;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.testing.xjc.AdichatzTestingTree;
import org.adichatz.testing.xjc.ClassLoadersType;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls
 */
public class TestingActivator implements BundleActivator {
	@Override
	public void start(BundleContext context) throws Exception {
		// "#?#": gencode package must be different from plugin package.
		new AdiPluginResources(TestingTools.TESTING_BUNDLE, TestingTools.TESTING_BUNDLE, "#?#", getClass().getClassLoader());
		initPreferenceManager();
		// Add a Runnable to EarlyStartupHooks. Always called before end of
		// start process
		// Could be called after ApplicationEvent.EVENT_TYPE.POST_START_UP
		// process.
		AdichatzApplication.getInstance().getEarlyStartupHooks().add(() -> {
			MenuPathType menuPath = new MenuPathType();
			menuPath.setId("testingNavigatorContentId");
			menuPath.setAdiResourceURI("bundleclass://org.adichatz.testing/org.adichatz.testing.TestingNavigatorContent");
			AdichatzTestingTree testingTree = TestingTools.getTestingTree(true);
			if (!testingTree.getSuiteOrTestFile().isEmpty()) {
				String filters = InstanceScope.INSTANCE.getNode(TestingTools.TESTING_BUNDLE).get(TestingTools.NAVIGATOR_FILTERS,
						"");
				FiltersMatcher filterMatchers = new FiltersMatcher(filters);
				for (NavigatorType navigator : AdichatzApplication.getInstance().getConfigTree().getRcpConfiguration()
						.getNavigators().getNavigator()) {
					if (filterMatchers.evaluate(navigator.getId()))
						navigator.getMenuPath().add(menuPath);
				}
				ClassLoadersType classLoaders = testingTree.getClassLoaders();
				AdiPluginResources applicationPluginResources = AdichatzApplication.getInstance().getApplicationPluginResources();
				if (!classLoaders.getAdiPluginName().contains(applicationPluginResources.getPluginName()))
					classLoaders.getAdiPluginName().add(applicationPluginResources.getPluginName());
				MStackElement element = E4SimulationTools.getSelectedNavigator();
				if (null != element && element instanceof MPart && ((MPart) element).getObject() instanceof ANavigator) {
					((INavigator) ((MPart) element).getObject()).refreshNavigator();
				}
				String testRunnerURI = EngineTools.isEmpty(testingTree.getApplicationTestRunerURI())
						? "bundleclass://org.adichatz.testing/org.adichatz.testing.TestNGRunner"
						: testingTree.getApplicationTestRunerURI();
				try {
					TestingTools.APPLICATION_TEST_RUNNER = ((ITestRunner) ReflectionTools.instantiateURI(testRunnerURI,
							new Class[] {}, new Object[] {}));
					TestingTools.APPLICATION_TEST_RUNNER.runTesting(testingTree, false);
				} catch (Exception e) {
					logError(e);
				}
			}
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

	/**
	 * Inits the preference manager.
	 */
	private void initPreferenceManager() {
		final IEclipsePreferences testingNode = InstanceScope.INSTANCE.getNode(TestingTools.TESTING_BUNDLE);
		Runnable injectValuesRunnable = () -> {
			TestingTools.DATE_FORMAT = new SimpleDateFormat(
					testingNode.get(TestingTools.DATE_PATTERN, new SimpleDateFormat().toPattern()));
		};
		Runnable initDefaultRunnable = () -> {
			testingNode.putBoolean(TestingTools.OPEN_MANAGER_PART, false);
			testingNode.putBoolean(TestingTools.OPEN_MANAGER_PART_ON_FAIL, true);
			String pattern = new SimpleDateFormat().toPattern();
			if (pattern.endsWith("mm"))
				pattern = pattern.concat(":ss");
			if (pattern.endsWith("ss"))
				pattern = pattern.concat(".SSS");
			testingNode.put(TestingTools.DATE_PATTERN, pattern);
			testingNode.put(TestingTools.NAVIGATOR_FILTERS, "");
		};
		AdiPreferenceManager preferenceManager = new AdiPreferenceManager(TestingTools.TESTING_BUNDLE,
				getFromTestingBundle("testing.preference.group"),
				AdichatzApplication.getInstance().getImageDescriptor(TestingTools.TESTING_BUNDLE, "IMG_ADI_TEST.png"),
				TestingPreferencePage.class.getName(), injectValuesRunnable, initDefaultRunnable, null, null);
		AdiPreferenceManager.initPreferenceNode(preferenceManager);
	}
}
