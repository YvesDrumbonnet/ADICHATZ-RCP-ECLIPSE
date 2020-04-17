package org.adichatz.tool;

import org.adichatz.engine.common.AdiPluginResources;

/**
 * The Class ResourcesBundle.
 */
public class ResourcesBundle {

	/** The plugin resources. */
	AdiPluginResources pluginResources;

	/** The has bundle. */
	boolean hasBundle;

	/** The has image. */
	boolean hasImage;

	/** The has cache. */
	boolean hasCache;

	/**
	 * Instantiates a new resources bundle.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param menuPaths
	 *            the menu paths
	 * @param hasBundle
	 *            the has bundle
	 * @param hasImage
	 *            the has image
	 * @param hasCache
	 *            the has cache
	 */
	public ResourcesBundle(AdiPluginResources pluginResources, boolean hasBundle, boolean hasImage, boolean hasCache) {
		this.pluginResources = pluginResources;
		this.hasBundle = hasBundle;
		this.hasImage = hasImage;
		this.hasCache = hasCache;
	}
}
