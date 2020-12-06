package org.adichatz.engine.e4.preference;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Control;

public abstract class AFieldEditor {
	protected String id;

	protected AdiFormToolkit toolkit;

	protected AFieldEditor(String id) {
		this.id = id;
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
	}

	public String getId() {
		return id;
	}

	public abstract Object getValue();

	public abstract void setValue(IEclipsePreferences eclipsePreferences);

	public abstract boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force);

	public abstract Control getControl();
}
