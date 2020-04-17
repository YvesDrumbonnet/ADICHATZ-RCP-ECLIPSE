package org.adichatz.css.resources;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.internal.forms.css.dom.SectionElement;
import org.eclipse.ui.internal.forms.css.properties.css2.CSSPropertyTitleFormsHandler;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class CSSPropertySectionHandler extends CSSPropertyTitleFormsHandler implements IElementProvider {
	private static final String BACKGROUND_COLOR_PROPERTY = "background-color"; //$NON-NLS-1$

	private static final String COLOR_PROPERTY = "color"; //$NON-NLS-1$

	private static final String TOGGLE_COLOR_PROPERTY = "toggle-color"; //$NON-NLS-1$

	private static final String ACTIVE_TOGGLE_COLOR_PROPERTY = "active-toggle-color"; //$NON-NLS-1$

	private static final String FOREGROUND_COLOR_TITLEBAR_PROPERTY = "foreground-color-titlebar"; //$NON-NLS-1$

	@Override
	protected void applyCSSProperty(Control control, String property, CSSValue value, String pseudo, CSSEngine engine)
			throws Exception {
		super.applyCSSProperty(control, property, value, pseudo, engine);
		if (control instanceof Section) {
			Section section = (Section) control;
			if (BACKGROUND_COLOR_PROPERTY.equalsIgnoreCase(property)) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					section.setBackground(newColor);
					if (null != section.getClient())
						section.getClient().setBackground(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				}
			} else if (COLOR_PROPERTY.equalsIgnoreCase(property)) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					section.setForeground(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				}
			} else if (FOREGROUND_COLOR_TITLEBAR_PROPERTY.equalsIgnoreCase(property)) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					section.setTitleBarForeground(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				}
			} else if (TOGGLE_COLOR_PROPERTY.equalsIgnoreCase(property)) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					section.setToggleColor(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				}
			} else if (ACTIVE_TOGGLE_COLOR_PROPERTY.equalsIgnoreCase(property)) {
				if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
					section.setActiveToggleColor(newColor);
				} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				}
			}
		}
	}

	@Override
	public Element getElement(Object element, CSSEngine engine) {
		return new SectionElement((Section) element, engine);
	}
}
