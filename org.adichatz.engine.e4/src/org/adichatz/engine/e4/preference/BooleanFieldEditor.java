package org.adichatz.engine.e4.preference;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class BooleanFieldEditor extends AFieldEditor {

	private Button button;

	private boolean defaultValue;

	public BooleanFieldEditor(String id, boolean defaultValue, final APreferencePage preferencePage, String labelText,
			Composite parent) {
		super(id);
		this.defaultValue = defaultValue;
		button = toolkit.createButton(parent, labelText, SWT.CHECK);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preferencePage.setDirty(true);
			}
		});
	}

	@Override
	public Boolean getValue() {
		return button.getSelection();
	}

	@Override
	public void setValue(IEclipsePreferences eclipsePreferences) {
		button.setSelection(eclipsePreferences.getBoolean(id, defaultValue));
	}

	@Override
	public boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force) {
		Boolean currentValue = eclipsePreferences.getBoolean(id, defaultValue);
		Boolean fieldValue = getValue();
		if (currentValue.equals(fieldValue))
			return force;

		eclipsePreferences.putBoolean(id, fieldValue);
		return true;
	}

	@Override
	public Button getControl() {
		return button;
	}
}
