package org.adichatz.engine.e4.preference;

import static org.adichatz.engine.common.LogBroker.logError;

import java.text.ParseException;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class IntegerFieldEditor extends ANumericFieldEditor {

	int defaultValue;

	public IntegerFieldEditor(String id, int defaultValue, final APreferencePage preferencePage, String labelText, Composite parent,
			String pattern) {
		super(id, preferencePage, labelText, parent, pattern);
		this.defaultValue = defaultValue;
	}

	@Override
	public Integer getValue() {
		return numericText.getValue().intValue();
	}

	@Override
	public void setValue(IEclipsePreferences eclipsePreferences) {
		try {
			numericText.setValue(eclipsePreferences.getInt(id, defaultValue));
		} catch (ParseException e) {
			logError(e);
		}
	}

	@Override
	public boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force) {

		int currentValue = eclipsePreferences.getInt(id, defaultValue);
		int fieldValue = getValue();
		if (currentValue == fieldValue)
			return force;

		eclipsePreferences.putInt(id, fieldValue);
		return true;
	}

	@Override
	public Text getControl() {
		return numericText.getControl();
	}
}
