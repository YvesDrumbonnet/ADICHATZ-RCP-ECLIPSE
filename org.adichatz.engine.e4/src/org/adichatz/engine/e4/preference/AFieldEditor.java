package org.adichatz.engine.e4.preference;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Control;

public abstract class AFieldEditor {
	protected String id;

	protected AFieldEditor(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public abstract Object getValue();

	public abstract void setValue(IEclipsePreferences eclipsePreferences);

	public abstract boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force);

	public abstract Control getControl();
}
