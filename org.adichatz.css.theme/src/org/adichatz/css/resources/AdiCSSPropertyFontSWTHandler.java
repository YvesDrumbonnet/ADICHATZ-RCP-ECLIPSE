package org.adichatz.css.resources;

import org.adichatz.engine.controller.AControlController;
import org.eclipse.e4.ui.css.core.dom.properties.css2.CSS2FontProperties;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTFontHelper;
import org.eclipse.e4.ui.css.swt.helpers.SWTElementHelpers;
import org.eclipse.e4.ui.css.swt.properties.css2.CSSPropertyFontSWTHandler;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

@SuppressWarnings("restriction")
public class AdiCSSPropertyFontSWTHandler extends CSSPropertyFontSWTHandler implements IAdiPropertyHandler {
	private static void updateChildrenFonts(CTabFolder folder, Font font) {
		for (CTabItem item : folder.getItems()) {
			CSSSWTFontHelper.setFont(item, font);
		}
	}

	@Override
	public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine) throws Exception {
		final Widget widget = SWTElementHelpers.getWidget(element);
		if (widget == null || widget instanceof CTabItem) {
			return;
		}
		CSS2FontProperties fontProperties = CSSSWTFontHelper.getCSS2FontProperties(widget, engine.getCSSElementContext(widget));
		if (fontProperties == null) {
			return;
		}
		Font font = (Font) engine.convert(fontProperties, Font.class, widget);
		setFont(widget, font);
	}

	private static void setFont(Widget widget, Font font) {
		if (null == widget.getData(AControlController.ADI_CSS_FONT)) {
			if (widget instanceof CTabItem) {
				CTabItem item = (CTabItem) widget;
				CSSSWTFontHelper.setFont(item, font);
			} else if (widget instanceof CTabFolder) {
				CTabFolder folder = (CTabFolder) widget;
				CSSSWTFontHelper.setFont(folder, font);
				updateChildrenFonts(folder, font);
			} else if (widget instanceof Control) {
				Control control = (Control) widget;
				final boolean isLayoutDeferred = (control instanceof Composite) && ((Composite) control).isLayoutDeferred();
				if (isLayoutDeferred) {
					control.setRedraw(false);
				}
				try {
					CSSSWTFontHelper.setFont(control, font);
				} finally {
					if (isLayoutDeferred) {
						control.setRedraw(true);
					}
				}
			}

		}
	}
}