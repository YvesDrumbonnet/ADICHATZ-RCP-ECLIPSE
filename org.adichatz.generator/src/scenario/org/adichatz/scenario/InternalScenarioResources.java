package org.adichatz.scenario;

public class InternalScenarioResources extends ScenarioResources {
	/**
	 * Gets the single instance of ScenarioResources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @return single instance of ScenarioResources
	 */
	public static ScenarioResources getInstance(String pluginName) {
		ScenarioResources scenarioResources = scenarioResourcesMap.get(pluginName);
		if (null != scenarioResources && scenarioResources.isSynchronized())
			return scenarioResources;
		scenarioResources = new InternalScenarioResources(pluginName);
		scenarioResourcesMap.put(pluginName, scenarioResources);
		return scenarioResources;
	}

	protected InternalScenarioResources(String bundleName) {
		super(bundleName);
	}

	@Override
	public void loadScenarioParameters() {
	}
}
