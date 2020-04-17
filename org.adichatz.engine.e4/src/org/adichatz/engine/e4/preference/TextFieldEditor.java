package org.adichatz.engine.e4.preference;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class TextFieldEditor extends AFieldEditor {

	protected Text text;

	protected String defaultValue;

	public TextFieldEditor(String id, String defaultValue, final APreferencePage preferencePage, String labelText,
			Composite parent) {
		super(id);
		this.defaultValue = defaultValue;
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		toolkit.createLabel(parent, labelText.concat(0 < labelText.indexOf(':') ? "" : ":"));
		text = toolkit.createText(parent, "");
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				preferencePage.setDirty(true);
			}
		});
	}

	@Override
	public String getValue() {
		return text.getText();
	}

	@Override
	public void setValue(IEclipsePreferences eclipsePreferences) {
		text.setText(eclipsePreferences.get(id, defaultValue));
	}

	@Override
	public boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force) {
		String currentValue = eclipsePreferences.get(id, defaultValue);
		String fieldValue = getValue();
		if (currentValue.equals(fieldValue))
			return force;

		eclipsePreferences.put(id, fieldValue);
		return true;
	}

	@Override
	public Text getControl() {
		return text;
	}
}
