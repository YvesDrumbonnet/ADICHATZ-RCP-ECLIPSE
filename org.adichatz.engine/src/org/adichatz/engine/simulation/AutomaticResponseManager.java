package org.adichatz.engine.simulation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class AutomaticResponseManager {
	private AutomaticResponse[] automaticResponses;

	private int result;

	private int waiting;

	private int skipControl;

	private int controlIndex;

	public AutomaticResponseManager(AutomaticResponse[] automaticResponses, int result, int waiting) {
		this.automaticResponses = null == automaticResponses ? new AutomaticResponse[0] : automaticResponses;
		this.result = result;
		this.waiting = waiting;
	}

	private void addChildren(List<Control> children, Composite parent) {
		for (Control control : parent.getChildren()) {
			if (controlIndex++ > skipControl)
				children.add(control);
			if (control instanceof Composite)
				addChildren(children, (Composite) control);
		}
	}

	public int computeResult(Composite parent, int skipControl) {
		this.skipControl = skipControl;
		List<Control> children = new ArrayList<>();
		addChildren(children, parent);
		int index = 0;
		for (Control control : children) {
			if (control.getClass().getName().equals(automaticResponses[index].getControlClassName())) {
				if (control instanceof Text) {
					((Text) control).setText((String) automaticResponses[index++].getResponse());
				} else if (control instanceof Button && Boolean.TRUE.equals(automaticResponses[index++].getResponse())) {
					Button button = (Button) control;
					if (button.isVisible() && button.isEnabled())
						button.notifyListeners(SWT.Selection, null);
				}
				if (parent.isDisposed() || index == automaticResponses.length)
					break;
			}
		}
		return result;
	}

	public int getWaiting() {
		return waiting;
	}
}
