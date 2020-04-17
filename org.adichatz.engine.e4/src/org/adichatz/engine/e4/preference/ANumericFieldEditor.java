package org.adichatz.engine.e4.preference;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.NumericText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class ANumericFieldEditor extends AFieldEditor {

	protected NumericText numericText;

	protected ANumericFieldEditor(String id, final APreferencePage preferencePage, String labelText, Composite parent,
			String pattern) {
		super(id);
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		toolkit.createLabel(parent, labelText.concat(0 < labelText.indexOf(':') ? "" : ":"));
		numericText = toolkit.createNumericText(parent, SWT.NONE);
		numericText.addStandardListeners();
		numericText.setFormatter(pattern);
		numericText.getControl().addModifyListener((e) -> {
			if (numericText.isDoit())
				preferencePage.setDirty(true);
		});
	}
}
