package org.adichatz.scenario.util;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.common.ResourceBundleManager;
import org.adichatz.engine.data.GencodePath;

public class PlainPluginResources extends AdiPluginResources {

	public PlainPluginResources(String pluginHome, String pluginName, String pluginPackage, GencodePath gencodePath) {
		super(pluginName, pluginPackage, gencodePath);
		this.pluginHome = pluginHome;
		resourceBundleManager = new ResourceBundleManager(this);
		imageManager = new ImageManager(this);
	}
}
