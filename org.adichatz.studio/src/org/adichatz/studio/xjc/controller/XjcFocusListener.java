package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.controller.AFieldController;
import org.adichatz.studio.xjc.editor.XjcFieldViewPart;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

public class XjcFocusListener implements FocusListener {
	private static AFieldController focusedController;

	public static AFieldController getFocusedController() {
		return focusedController;
	}

	private AFieldController controller;

	public XjcFocusListener(AFieldController controller, Class<?> containerClass, String methodName) {
		this.controller = controller;
	}

	public XjcFocusListener(AFieldController controller) {
		this.controller = controller;
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (null == XjcFieldViewPart.getInstance())
			focusedController = null;
		else {
			if (!controller.equals(focusedController)) {
				focusedController = controller;
				XjcFieldViewPart.getInstance().displayHelp(controller, true);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		focusedController = null;
	}

}
