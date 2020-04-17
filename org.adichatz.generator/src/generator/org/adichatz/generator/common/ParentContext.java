package org.adichatz.generator.common;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.scenario.ScenarioInput;

public class ParentContext {
	/** The current plugin entity. */
	private PluginEntity pluginEntity;

	private Map<String, String> bundleTranslation;

	private ScenarioInput scenarioInput;

	private String subPackage;

	private String treeId;

	public ParentContext(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		bundleTranslation = new HashMap<String, String>();

		pluginEntity = scenarioInput.getPluginEntity();
		treeId = scenarioInput.getTreeId();
		subPackage = scenarioInput.getSubPackage();
		bundleTranslation.putAll(scenarioInput.getBundleTranslation());
	}

	public void retrievePreviousContext() {
		scenarioInput.setPluginEntity(pluginEntity);
		scenarioInput.setTreeId(treeId);
		scenarioInput.setSubPackage(subPackage);
		scenarioInput.setBundleTranslation(bundleTranslation);
	}
}
