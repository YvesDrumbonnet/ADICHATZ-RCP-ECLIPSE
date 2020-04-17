package org.adichatz.engine.simulation;

import org.eclipse.swt.widgets.Control;

public class AutomaticResponse {
	private String controlClassName;

	private Object response;

	public AutomaticResponse(Class<? extends Control> controlClass, Object response) {
		this.controlClassName = controlClass.getName();
		this.response = response;
	}

	public String getControlClassName() {
		return controlClassName;
	}

	public Object getResponse() {
		return response;
	}

}
