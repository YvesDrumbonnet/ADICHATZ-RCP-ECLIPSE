package org.adichatz.studio;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.ScenarioPluginResources;
import org.adichatz.scenario.ScenarioResources;

public class StudioResources extends ScenarioResources {
	public StudioResources() {
		super(GeneratorConstants.STUDIO_BUNDLE);
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @return the plugin resources
	 */
	public AdiPluginResources getPluginResources() {
		if (null == pluginResources) {
			pluginResources = new ScenarioPluginResources(this);
			if (null == AdichatzApplication.getInstance().getApplicationPluginResources())
				AdichatzApplication.getInstance().setApplicationPluginResources(pluginResources);
		}
		return pluginResources;
	}

}
