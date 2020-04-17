package org.adichatz.tool;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.generation.ManifestChanger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

public class MenuResources {
	private List<ResourcesBundle> resourcesBundles = new ArrayList<ResourcesBundle>();

	public List<ResourcesBundle> getResourcesBundles() {
		return resourcesBundles;
	}

	public void computeResourcesBundles() {
		Collection<AdiPluginResources> pluginResources = AdichatzApplication.getInstance().getPluginMap().values();
		for (AdiPluginResources pluginResource : pluginResources.toArray(new AdiPluginResources[pluginResources.size()])) {
			Bundle bundle = Platform.getBundle(pluginResource.getPluginName());
			ManifestChanger manifestChanger = new ManifestChanger(bundle.getEntry("META-INF/MANIFEST.MF"));
			try {
				if (manifestChanger.hasValue(Constants.REQUIRE_BUNDLE, GeneratorConstants.GENERATOR_BUNDLE)) {
					if (null != bundle) {
						URL scenarioPath = bundle
								.getEntry(EngineConstants.XML_FILES_PATH.concat("/").concat(GeneratorConstants.SCENARIO_FILE));
						if (null != scenarioPath) {
							boolean hasBundle = hasChildren(bundle, EngineConstants.RESOURCE_MESSAGE_DIR, "*.properties");
							boolean hasImage = hasChildren(bundle, "resources/icons", "*.*");

							boolean hasCache = null != manifestChanger.getValue(IScenarioConstants.ADICHATZ_CONNECTOR_VERSION);
							if (hasBundle || hasImage || hasCache)
								resourcesBundles.add(new ResourcesBundle(pluginResource, hasBundle, hasImage, hasCache));
						}
					}
				}
			} catch (IOException | CoreException e) {
				logError(e);
			}
		}
	}

	private boolean hasChildren(Bundle bundle, String path, String filePattern) {
		Enumeration<URL> children = bundle.findEntries(path, filePattern, true);
		if (null == children)
			return false;
		return children.hasMoreElements();
	}
}
