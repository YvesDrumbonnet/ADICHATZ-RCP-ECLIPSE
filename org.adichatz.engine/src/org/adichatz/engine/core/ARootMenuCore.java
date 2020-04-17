package org.adichatz.engine.core;

import org.adichatz.engine.controller.menu.MenuController;

public abstract class ARootMenuCore extends AMenuCore {
	protected Object eclipseContext;

	protected MenuController rootMenuController;

	protected ARootMenuCore(String pluginKey, MenuController rootController) {
		super(pluginKey, null);
		this.rootMenuController = rootController;
	}

	public abstract void createChildren();

	public Object getEclipseContext() {
		return eclipseContext;
	}

	public void setEclipseContext(Object eclipseContext) {
		this.eclipseContext = eclipseContext;
	}

	public MenuController getRootMenuController() {
		return rootMenuController;
	}
}
