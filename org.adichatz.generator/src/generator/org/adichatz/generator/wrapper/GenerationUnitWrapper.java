package org.adichatz.generator.wrapper;

import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.scenario.ScenarioResources;

public class GenerationUnitWrapper extends GenerationUnitType {

	private PluginEntity pluginEntity;

	public GenerationUnitWrapper() {
	}

	public GenerationUnitWrapper(GenerationEnum generationEnum) {
		this.type = generationEnum;
	}

	public PluginEntity getPluginEntity(PluginEntityWrapper pluginEntityType, ScenarioResources scenarioResources) {
		if (null == pluginEntity) {
			pluginEntity = scenarioResources.getPluginResources().getPluginEntity(pluginEntityType.getEntityURI());
			if (null == pluginEntity)
				logError("No entity for '" + pluginEntityType.getEntityURI() + "'!");
		}
		return pluginEntity;
	}

}
