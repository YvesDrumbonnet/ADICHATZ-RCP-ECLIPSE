/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 ******************************************************************************/
package org.adichatz.css.theme;

import static org.adichatz.css.theme.ThemeUtil.getFromPublicBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import javax.inject.Inject;

import org.adichatz.css.resources.AdiRegistryCSSPropertyHandlerProvider;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.engine.CSSErrorHandler;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.prefs.BackingStoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class ThemeProcessor.
 *
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class ThemeProcessor {

	/** The context. */
	@Inject
	private IEclipseContext context;

	/** The display. */
	@Inject
	private Display display;

	/**
	 * Subscribe css theme changed.
	 *
	 * @param event
	 *            the event
	 */
	@Inject
	@Optional
	private void subscribeCssThemeChanged(@EventTopic(IThemeEngine.Events.THEME_CHANGED) Event event) {
		if (isApplication())
			AReskinManager.getInstance().addTheme(event.getProperty(IThemeEngine.Events.THEME_ENGINE),
					event.getProperty(IThemeEngine.Events.THEME));
	}

	/**
	 * Checks if is application.
	 *
	 * @return true, if is application
	 */
	private boolean isApplication() {
		return AdichatzApplication.getInstance() instanceof E4AdichatzApplication;
	}

	/**
	 * Process.
	 *
	 * @param registry
	 *            the registry
	 */
	@Execute
	public void process(IExtensionRegistry registry) {
		if (isApplication()) {
			String postReskinClassName = AdichatzApplication.getInstance().popContextValue(EngineConstants.POST_RESKIN_CLASS_NAME);
			if (EngineTools.isEmpty(postReskinClassName))
				new AdiReskinManager(display);
			else
				E4AdichatzApplication.getInstance().getApplicationPluginResources().getGencodePath()
						.instantiateClass(postReskinClassName, new Class[] {}, new Object[] {});

			// Initializes theme preferences
			AdiThemeEngine engine = new AdiThemeEngine(display);
			display.setData("org.eclipse.e4.ui.css.swt.theme", engine);

			AdiRegistryCSSPropertyHandlerProvider registryCSSPropertyHandlerProvider = new AdiRegistryCSSPropertyHandlerProvider(
					RegistryFactory.getRegistry());
			CSSEngine cssEngine = new CSSSWTEngineImpl(display, false) {
				@Override
				protected void initializeCSSPropertyHandlers() {
					propertyHandlerProviders.add(registryCSSPropertyHandlerProvider);
				}
			};
			cssEngine.setErrorHandler(new CSSErrorHandler() {
				@Override
				public void error(Exception e) {
					logError(e.getMessage(), e);
				}
			});
			WidgetElement.setEngine(display, cssEngine);

			engine.addCSSEngine(cssEngine);
			context.set(IThemeEngine.class, engine);

			IEclipsePreferences themeNode = InstanceScope.INSTANCE.getNode(ThemeUtil.THEME_PREFERENCE_PREFS);
			IConfigurationElement[] configs = registry.getConfigurationElementsFor(EngineConstants.ADICHATZ_THEME_POINT);
			AdiPreferenceManager preferenceManager = new AdiPreferenceManager(ThemeUtil.THEME_PREFERENCE_PREFS,
					getFromPublicBundle("theme.title"),
					AdichatzApplication.getInstance().getImageDescriptor(EngineConstants.ENGINE_E4_BUNDLE, "IMG_THEME.png"),
					ThemePreferencePage.class.getName(), null, () -> {
						boolean first = true;
						String themeId = null;
						for (IConfigurationElement config : configs) {
							// First found theme is default theme
							if ("theme".equals(config.getName()) && first) {
								String version = config.getAttribute("os_version");
								themeId = config.getAttribute("id").concat(null == version ? "" : version);
								first = false;
								break;
							}
						}
						if (null == themeId)
							logError(getFromPublicBundle("theme.not.defined"));
						else {
							themeNode.put(EngineE4Util.THEME_ID, themeId);
							try {
								themeNode.flush();
							} catch (BackingStoreException e) {
								logError(e);
							}
						}
					}, null, context);
			AdiPreferenceManager.initPreferenceNode(preferenceManager);
			String prefThemeId = themeNode.get(EngineE4Util.THEME_ID, null);
			ITheme preferedTheme = null;
			String defaultBaseSheetURI = null == prefThemeId ? EngineConstants.DEFAULT_STYLE_SHEET_URI : "";
			for (IConfigurationElement config : configs) {
				if ("theme".equals(config.getName())) {
					String version = config.getAttribute("os_version");
					String themeId = config.getAttribute("id").concat(null == version ? "" : version);
					String basestylesheeturi = config.getAttribute("basestylesheeturi");
					String contributorName = config.getContributor().getName();
					if (!basestylesheeturi.startsWith("platform:/plugin/"))
						basestylesheeturi = "platform:/plugin/" + contributorName + "/" + basestylesheeturi;
					String label = config.getAttribute("label");
					if (null == prefThemeId && EngineConstants.CSS_THEME_BUNDLE.equals(contributorName)
							&& defaultBaseSheetURI.equals(basestylesheeturi)) {
						prefThemeId = themeId;
						themeNode.put(EngineE4Util.THEME_ID, themeId);
						try {
							themeNode.flush();
						} catch (BackingStoreException e) {
							logError(e);
						}
					}
					ITheme theme = engine.registerTheme(themeId, label, basestylesheeturi, version);
					if (themeId.equals(prefThemeId))
						preferedTheme = theme;
				}
			}
			if (null != preferedTheme) {
				engine.setTheme(preferedTheme, false);
				context.set("cssTheme" /* E4Application.THEME_ID */, prefThemeId);
			}
		}
	}
}
