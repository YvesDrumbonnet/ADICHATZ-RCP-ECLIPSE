package org.adichatz.css.resources;

import org.adichatz.engine.controller.AControlController;
import org.eclipse.e4.ui.css.core.dom.properties.Gradient;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.e4.ui.css.swt.properties.GradientBackgroundListener;
import org.eclipse.e4.ui.css.swt.properties.css2.CSSPropertyBackgroundSWTHandler;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class AdiCSSPropertyBackgroundSWTHandler extends CSSPropertyBackgroundSWTHandler implements IAdiPropertyHandler {
	@Override
	public void applyCSSPropertyBackgroundColor(Object element, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		Widget widget = (Widget) ((WidgetElement) element).getNativeWidget();
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color newColor = (Color) engine.convert(value, Color.class, widget.getDisplay());
			if (widget instanceof CTabItem) {
				CTabFolder folder = ((CTabItem) widget).getParent();
				if ("selected".equals(pseudo)) {
					// tab folder selection manages gradients
					CSSSWTColorHelper.setSelectionBackground(folder, newColor);
				} else {
					setBackground(folder, newColor);
				}
			} else if (widget instanceof Control) {
				GradientBackgroundListener.remove((Control) widget);
				setBackground((Control) widget, newColor);
				CompositeElement.setBackgroundOverriddenByCSSMarker(widget);
			}
		} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
			Gradient grad = (Gradient) engine.convert(value, Gradient.class, widget.getDisplay());
			if (grad == null) {
				return; // warn?
			}
			if (widget instanceof CTabItem) {
				CTabFolder folder = ((CTabItem) widget).getParent();
				Color[] colors = CSSSWTColorHelper.getSWTColors(grad, folder.getDisplay(), engine);
				int[] percents = CSSSWTColorHelper.getPercents(grad);

				if ("selected".equals(pseudo)) {
					folder.setSelectionBackground(colors, percents, true);
				} else {
					folder.setBackground(colors, percents, true);
				}

			} else if (widget instanceof Control) {
				GradientBackgroundListener.handle((Control) widget, grad);
				CompositeElement.setBackgroundOverriddenByCSSMarker(widget);
			}
		}
	}

	private static void setBackground(Control control, Color newColor) {
		if (null == control.getData(AControlController.ADI_CSS_BACKGROUND))
			CSSSWTColorHelper.setBackground(control, newColor);
	}
}
