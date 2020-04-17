package org.adichatz.css.theme;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;

import org.adichatz.engine.common.EngineConstants;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ThemeUtil {

	public static String THEME_PREFERENCE_PREFS = "org.adichatz.theme";

	/** The engine rbm. */
	private static PropertyResourceBundle cssThemeRB;

	/**
	 * Gets the engine RBM.
	 *
	 * @return the engine RBM
	 */
	private static PropertyResourceBundle getEngineRB() {
		if (null == cssThemeRB) {
			Bundle cssBundle = Platform.getBundle(EngineConstants.CSS_THEME_BUNDLE);
			URL url = cssBundle.getEntry("plugin_".concat(EngineConstants.LANGUAGE).concat(".properties"));
			if (null == url)
				url = cssBundle.getEntry("plugin.properties");
			InputStream stream;
			try {
				stream = url.openStream();
				cssThemeRB = new PropertyResourceBundle(stream);
				stream.close();
			} catch (IOException e) {
				logError(e);
			}
		}
		return cssThemeRB;
	}

	public static String getFromPublicBundle(String key) {
		return getEngineRB().getString(key);
	}
}
