package org.adichatz.engine.e4.resource;

import org.adichatz.engine.controller.menu.MenuController;

public class MenuPath {
	private MenuController rootController;

	private String menuURI;

	private MenuController menuController;

	public MenuPath(MenuController rootController, String menuURI, MenuController menuController) {
		this.rootController = rootController;
		this.menuURI = menuURI;
		this.menuController = menuController;
	}

	public MenuController getRootController() {
		return rootController;
	}

	public String getMenuURI() {
		return menuURI;
	}

	public MenuController getMenuController() {
		return menuController;
	}

}
