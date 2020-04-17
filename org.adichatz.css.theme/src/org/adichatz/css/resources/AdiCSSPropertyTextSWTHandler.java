package org.adichatz.css.resources;

import org.adichatz.engine.controller.AControlController;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.e4.ui.css.swt.properties.css2.CSSPropertyTextSWTHandler;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class AdiCSSPropertyTextSWTHandler extends CSSPropertyTextSWTHandler implements IAdiPropertyHandler {
	@Override
	public void applyCSSPropertyColor(Object element, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		Widget widget = (Widget) element;
		Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());

		if (newColor != null && newColor.isDisposed() || value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE) {
			return;
		}

		if (widget instanceof CTabItem) {
			CTabFolder folder = ((CTabItem) widget).getParent();
			if ("selected".equals(pseudo)) {
				CSSSWTColorHelper.setSelectionForeground(folder, newColor);
			} else {
				setForeground(folder, newColor);
			}
		} else if (widget instanceof Control) {
			setForeground((Control) widget, newColor);
		}
	}

	private static void setForeground(Control control, Color newColor) {
		if (null == control.getData(AControlController.ADI_CSS_FOREGROUND))
			CSSSWTColorHelper.setForeground(control, newColor);
	}
}
