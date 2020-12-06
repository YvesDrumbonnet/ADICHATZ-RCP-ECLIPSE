package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.core.ControllerCore;

public class OutlineHyperlinkController extends HyperlinkController {
	public static String ENABLED_FLAG = "#ALREADY_DISABLED#";

	public OutlineHyperlinkController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (null != hyperlink && !hyperlink.isDisposed() && enabled != hyperlink.isEnabled())
			hyperlink.setData(ENABLED_FLAG, enabled ? null : "true");
		super.setEnabled(enabled);
	}
}
