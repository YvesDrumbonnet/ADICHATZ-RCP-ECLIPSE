package org.adichatz.studio;

import org.adichatz.studio.util.StudioUtil;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.widgets.Composite;

public class CustomFileTextFieldEditor extends FileTextFieldEditor {
	public CustomFileTextFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}

	protected void valueChanged() {
		boolean oldState = valid;
		if (null == fileText) {
			clearErrorMessage();
			valid = true;

		} else {
			String fileName = fileText.getValue();
			if (null == fileName) {
				clearErrorMessage();
				valid = true;
			} else {
				Object validation = StudioUtil.validateCustomizationFile(fileName, null);
				if (null != validation && validation instanceof String) {
					showErrorMessage((String) validation);
					valid = false;
				} else {
					valid = true;
					clearErrorMessage();
				}
			}
		}
		fireStateChanged(FieldEditor.IS_VALID, oldState, valid);
	}

}
