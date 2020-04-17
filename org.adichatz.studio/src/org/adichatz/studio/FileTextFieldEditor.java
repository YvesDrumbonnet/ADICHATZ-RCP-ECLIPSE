package org.adichatz.studio;

import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.widgets.FileText;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class FileTextFieldEditor extends FieldEditor {
	/**
	 * The label control.
	 */
	protected Label label;

	protected FileText fileText;

	protected String filterPath;

	protected String filterExtension;

	protected boolean valid = true;

	protected String oldValue;

	public FileTextFieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		if (null != fileText) {
			GridData gd = new GridData();
			gd.horizontalSpan = numColumns - 1;
			gd.horizontalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace = true;
			fileText.setLayoutData(gd);
		}
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		if (null == fileText) {
			// Create label
			label = new Label(parent, SWT.LEFT);
			label.setFont(parent.getFont());
			label.setText(getLabelText());
			label.addDisposeListener((e) -> {
				label = null;
			});

			// Create fileText
			fileText = new FileText(parent, AdiSWT.DELETE_BUTTON);
			fileText.setFont(parent.getFont());
			if (null != filterExtension)
				fileText.setFilterExtension(filterExtension);
			if (null != filterPath)
				fileText.setFilterPath(filterPath);
			fileText.addModifyListener((e) -> {
				valueChanged();
			});
			fileText.addDisposeListener((e) -> {
				fileText = null;
			});

		} else {
			checkParent(label, parent);
			checkParent(fileText, parent);
		}
	}

	@Override
	protected void doLoad() {
		if (fileText != null) {
			String value = getPreferenceStore().getString(getPreferenceName());
			fileText.setValue(value);
		}
	}

	@Override
	protected void doLoadDefault() {
		if (fileText != null) {
			String value = getPreferenceStore().getDefaultString(getPreferenceName());
			fileText.setValue(value);
		}
		valueChanged();
	}

	@Override
	protected void doStore() {
		getPreferenceStore().setValue(getPreferenceName(), null == fileText.getValue() ? "" : fileText.getValue());
	}

	@Override
	public int getNumberOfControls() {
		return 2;
	}

	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
		if (null != fileText)
			fileText.setFilterPath(filterPath);
	}

	public void setFilterExtension(String filterExtension) {
		this.filterExtension = filterExtension;
		if (null != fileText)
			fileText.setFilterExtension(filterExtension);
	}

	protected void valueChanged() {
		valid = true;
		fireValueChanged(getPreferenceName(), oldValue, fileText.getValue());
	}

	@Override
	public boolean isValid() {
		return valid;
	}
}
