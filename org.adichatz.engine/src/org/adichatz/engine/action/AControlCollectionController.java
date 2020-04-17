package org.adichatz.engine.action;

import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Control;

public abstract class AControlCollectionController extends ACollectionController {

	public AControlCollectionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public abstract Control getControl();

	public void setCssBackground(String selector, String property) {
		Color color = AReskinManager.getInstance().getColor(selector, property, AControlController.ADI_CSS_BACKGROUND,
				getControl());
		setBackground(color);
	}

	public void setCssForeground(String selector, String property) {
		Color color = AReskinManager.getInstance().getColor(selector, property, AControlController.ADI_CSS_FOREGROUND,
				getControl());
		setForeground(color);
	}

	public void setCssFont(String selector) {
		Font font = AReskinManager.getInstance().getFont(selector, getControl());
		setFont(font);
	}

	public void setBackground(Color color) {
		getControl().setBackground(color);
		if (null == color)
			getControl().setData(AControlController.ADI_CSS_BACKGROUND, null);
		else
			getControl().setData(AControlController.ADI_CSS_BACKGROUND, "true");
	}

	public void setForeground(Color color) {
		getControl().setForeground(color);
		if (null == color)
			getControl().setData(AControlController.ADI_CSS_FOREGROUND, null);
		else
			getControl().setData(AControlController.ADI_CSS_FOREGROUND, "true");
	}

	public void setFont(Font font) {
		getControl().setFont(font);
		if (null == font)
			getControl().setData(AControlController.ADI_CSS_FONT, null);
		else
			getControl().setData(AControlController.ADI_CSS_FONT, "true");
	}
}
