package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.miginfocom.swt.MigLayout;;

public class CodeTextController extends TextController {
	public CodeTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		Composite composite = new Composite(parentController.getComposite(), SWT.NONE);
		composite.setLayout(new MigLayout("ins 0, wrap 1", "grow,fill", "grow,fill"));
		composite.setLayoutData("newline, span 2, push, grow");
		control = toolkit.createText(composite, null, style);
		control.setLayoutData("h 64:64:n, w 64:64:n");
		control.addFocusListener(new XjcFocusListener(this));
	}

	@Override
	protected void reflow() {
	}
}
