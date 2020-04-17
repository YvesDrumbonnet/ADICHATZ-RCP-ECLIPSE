package org.adichatz.engine.e4.preference;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

public class CComboFieldEditor extends AFieldEditor {

	protected ComboViewer comboViewer;

	protected String defaultValue;

	public CComboFieldEditor(String id, String defaultValue, final APreferencePage preferencePage, String labelText,
			Composite parent) {
		super(id);
		this.defaultValue = defaultValue;
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		toolkit.createLabel(parent, labelText.concat(0 < labelText.indexOf(':') ? "" : ":"));
		CCombo ccombo = toolkit.createCCombo(parent, SWT.BORDER);
		ccombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preferencePage.setDirty(true);
			}
		});
		comboViewer = new ComboViewer(ccombo);
		comboViewer.setContentProvider(new ArrayContentProvider());
		setLabelProvider();
	}

	@Override
	public String getValue() {
		if (null == comboViewer.getSelection() || comboViewer.getSelection().isEmpty())
			return null;
		Object element = ((StructuredSelection) comboViewer.getSelection()).getFirstElement();
		return ((LabelProvider) comboViewer.getLabelProvider()).getText(element);
	}

	@Override
	public void setValue(IEclipsePreferences eclipsePreferences) {
		String defaultId = eclipsePreferences.get(id, defaultValue);
		if (null != defaultId)
			comboViewer.setSelection(new StructuredSelection(defaultId));
	}

	@Override
	public boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force) {
		String currentValue = eclipsePreferences.get(id, defaultValue);
		String fieldValue = getValue();
		if (fieldValue.equals(currentValue))
			return force;
		eclipsePreferences.put(id, fieldValue);
		return true;
	}

	@Override
	public CCombo getControl() {
		return comboViewer.getCCombo();
	}

	public ComboViewer getComboViewer() {
		return comboViewer;
	}

	protected void setLabelProvider() {
		comboViewer.setLabelProvider(new LabelProvider());
	}

}
