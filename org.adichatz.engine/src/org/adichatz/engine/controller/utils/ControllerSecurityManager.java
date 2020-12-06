package org.adichatz.engine.controller.utils;

import org.adichatz.engine.controller.AController;

public class ControllerSecurityManager {
	protected String entityURI;

	protected AController controller;

	protected String[] roles;

	public ControllerSecurityManager(String entityURI, AController controller, String... roles) {
		this.entityURI = entityURI;
		this.controller = controller;
		this.roles = roles;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		return true;
	}

	public boolean isValid() {
		return true;
	}
}
