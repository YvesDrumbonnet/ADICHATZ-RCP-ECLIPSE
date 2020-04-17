package org.adichatz.css.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.runtime.RuntimeLog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.css.core.dom.CSSStylableElement;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.dom.properties.providers.AbstractCSSPropertyHandlerProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.css2.CSSPropertyBackgroundSWTHandler;
import org.eclipse.e4.ui.css.swt.properties.css2.CSSPropertyFontSWTHandler;
import org.eclipse.e4.ui.css.swt.properties.css2.CSSPropertyTextSWTHandler;
import org.w3c.dom.css.CSSStyleDeclaration;

@SuppressWarnings("restriction")
public class AdiRegistryCSSPropertyHandlerProvider extends AbstractCSSPropertyHandlerProvider {
	private static final String ATTR_COMPOSITE = "composite";

	private static final String ATTR_ADAPTER = "adapter";

	private static final String ATTR_NAME = "name";

	private static final String ATTR_PROPERTY_NAME = "property-name";

	private static final String ATTR_HANDLER = "handler";

	private static final String PROPERTY_HANDLERS_EXTPOINT = "org.eclipse.e4.ui.css.core.propertyHandler";

	private IExtensionRegistry registry;

	private Map<String, Map<String, ICSSPropertyHandler>> propertyHandlerMap = new HashMap<>();

	public AdiRegistryCSSPropertyHandlerProvider(IExtensionRegistry registry) {
		this.registry = registry;
		configure(PROPERTY_HANDLERS_EXTPOINT);
	}

	/** @return true if some extensions were found */
	protected boolean configure(String extensionPointId) {
		IExtensionPoint extPoint = registry.getExtensionPoint(extensionPointId);
		if (extPoint == null) {
			return false;
		}
		IExtension[] extensions = extPoint.getExtensions();
		if (extensions.length == 0) {
			return false;
		}
		Map<String, Map<String, ICSSPropertyHandler>> handlersMap = new HashMap<>();
		for (IExtension e : extensions) {
			for (IConfigurationElement ce : e.getConfigurationElements()) {
				if (ATTR_HANDLER.equals(ce.getName())) {
					registerPropertyHandler(handlersMap, ce);
				}
			}
		}
		propertyHandlerMap.putAll(handlersMap);
		return true;
	}

	private void registerPropertyHandler(Map<String, Map<String, ICSSPropertyHandler>> handlersMap, IConfigurationElement ce) {
		// a single handler may implement a number of properties
		String name = ce.getAttribute(ATTR_COMPOSITE);
		String adapter = ce.getAttribute(ATTR_ADAPTER);
		IConfigurationElement[] children = ce.getChildren();
		String[] names = new String[children.length];
		for (int i = 0; i < children.length; i++) {
			if (children[i].getName().equals(ATTR_PROPERTY_NAME)) {
				names[i] = children[i].getAttribute(ATTR_NAME);
			}

		}
		try {
			Map<String, ICSSPropertyHandler> adaptersMap = handlersMap.get(adapter);
			if (adaptersMap == null) {
				handlersMap.put(adapter, adaptersMap = new HashMap<>());
			}
			if (!adaptersMap.containsKey(name)) {
				Object t = ce.createExecutableExtension(ATTR_HANDLER);
				if (chechPropertyHandler(t)) {
					for (int i = 0; i < names.length; i++) {
						adaptersMap.put(names[i], (ICSSPropertyHandler) t);
					}
				}
			}
		} catch (CoreException e1) {
			logError("invalid property handler for " + name + ": " + e1);
		}
	}

	@Override
	public Collection<ICSSPropertyHandler> getCSSPropertyHandlers(String property) throws Exception {
		List<ICSSPropertyHandler> handlers = new ArrayList<>();
		for (Map<String, ICSSPropertyHandler> perElement : propertyHandlerMap.values()) {
			ICSSPropertyHandler h = perElement.get(property);
			if (h != null) {
				handlers.add(h);
			}
		}
		return handlers;
	}

	@Override
	protected CSSStyleDeclaration getDefaultCSSStyleDeclaration(CSSEngine engine, CSSStylableElement stylableElement,
			CSSStyleDeclaration newStyle, String pseudoE) throws Exception {
		if (stylableElement.getDefaultStyleDeclaration(pseudoE) != null) {
			return stylableElement.getDefaultStyleDeclaration(pseudoE);
		}
		if (newStyle != null) {
			StringBuilder builder = null;
			int length = newStyle.getLength();
			for (int i = 0; i < length; i++) {
				String propertyName = newStyle.item(i);
				String[] compositePropertiesNames = engine.getCSSCompositePropertiesNames(propertyName);
				if (compositePropertiesNames != null) {
					for (String compositePropertyName : compositePropertiesNames) {
						propertyName = compositePropertyName;
						String s = getCSSPropertyStyle(engine, stylableElement, propertyName, pseudoE);
						if (s != null) {
							if (builder == null) {
								builder = new StringBuilder();
							}
							builder.append(s);
						}
					}
				} else {
					String s = getCSSPropertyStyle(engine, stylableElement, propertyName, pseudoE);
					if (s != null) {
						if (builder == null) {
							builder = new StringBuilder();
						}
						builder.append(s);
					}
				}
			}
			if (builder != null) {
				CSSStyleDeclaration defaultStyleDeclaration = engine.parseStyleDeclaration(builder.toString());
				stylableElement.setDefaultStyleDeclaration(pseudoE, defaultStyleDeclaration);
				return defaultStyleDeclaration;
			}
		}
		return stylableElement.getDefaultStyleDeclaration(pseudoE);
	}

	@Override
	public Collection<ICSSPropertyHandler> getCSSPropertyHandlers(Object element, String property) throws Exception {
		List<ICSSPropertyHandler> handlers = new ArrayList<>();
		Class<?> clazz = element.getClass();
		while (clazz != Object.class) {
			if (propertyHandlerMap.containsKey(clazz.getName())) {
				ICSSPropertyHandler handler = propertyHandlerMap.get(clazz.getName()).get(property);
				if (handler != null) {
					handlers.add(handler);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return handlers;
	}

	@Override
	public Collection<String> getCSSProperties(Object element) {
		// don't include deprecated elements
		Set<String> properties = new HashSet<>();
		Class<?> clazz = element.getClass();
		while (clazz != Object.class) {
			Map<String, ICSSPropertyHandler> handlerMap = propertyHandlerMap.get(clazz.getName());
			if (handlerMap != null) {
				properties.addAll(handlerMap.keySet());
			}
			clazz = clazz.getSuperclass();
		}
		return properties;
	}

	protected void logError(String message) {
		// we log as an error to ensure it's shown
		RuntimeLog.log(new Status(IStatus.ERROR, "org.eclipse.e4.ui.css.core", message));
	}

	private boolean chechPropertyHandler(Object handler) {
		if (!(handler instanceof ICSSPropertyHandler))
			return false;
		if (handler instanceof IAdiPropertyHandler)
			return true;
		if (handler instanceof CSSPropertyBackgroundSWTHandler || handler instanceof CSSPropertyTextSWTHandler
				|| handler instanceof CSSPropertyFontSWTHandler)
			return false;
		return true;

	}
}