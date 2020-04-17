package org.adichatz.engine.extra;

import java.util.List;

import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IConfirmContent {
	public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls);

	public void okPressed(List<Control> complementControls);
}
