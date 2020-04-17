package org.adichatz.engine.e4.resource;

import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.e4.part.MultiBoundedPart;

public abstract class APartNavigation {
	protected MultiBoundedPart boundePart;

	private String name;

	private String path;

	public APartNavigation(MultiBoundedPart boundePart, String path, String name) {
		this.boundePart = boundePart;
		this.path = path;
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	protected boolean isValid(AWidgetController controller) {
		return null != controller && controller.isValid();
	}

	public abstract boolean isEnabled();

	public abstract void run();

}
