package org.adichatz.engine.widgets.supplement;

import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.field.HyperlinkController;

public abstract class AHyperlinkRunnable extends AAdiRunnable {
	private AFieldController linkedController;

	public AHyperlinkRunnable(HyperlinkController hyperlinkController) {
		super(hyperlinkController);
	}

	public AFieldController getLinkedController() {
		if (null == linkedController)
			for (AWidgetController childController : controller.getParentController().getChildControllers())
				if (childController instanceof AFieldController
						&& controller.equals(((AFieldController) childController).getLinkedController())) {
					linkedController = (AFieldController) childController;
					break;
				}
		return linkedController;
	}

}
