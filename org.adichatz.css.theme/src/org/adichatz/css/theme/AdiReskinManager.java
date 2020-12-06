/*******************************************************************************
* Copyright © Adichatz (2007-2020) - www.adichatz.org
*
* yves@adichatz.org
*
* This software is a computer program whose purpose is to build easily
* Eclipse RCP applications using JPA in a JEE or JSE context.
*
* This software is governed by the CeCILL-C license under French law and
* abiding by the rules of distribution of free software.  You can  use,
* modify and/ or redistribute the software under the terms of the CeCILL
* license as circulated by CEA, CNRS and INRIA at the following URL
* "http://www.cecill.info".
*
* As a counterpart to the access to the source code and  rights to copy,
* modify and redistribute granted by the license, users are provided only
* with a limited warranty  and the software's author,  the holder of the
* economic rights,  and the successive licensors  have only  limited
* liability.
*
* In this respect, the user's attention is drawn to the risks associated
* with loading,  using,  modifying and/or developing or reproducing the
* software by the user in light of its specific status of free software,
* that may mean  that it is complicated to manipulate,  and  that  also
* therefore means  that it is reserved for developers  and  experienced
* professionals having in-depth computer knowledge. Users are therefore
* encouraged to load and test the software's suitability as regards their
* requirements in conditions enabling the security of their systems and/or
* data to be ensured and,  more generally, to use and operate it in the
* same conditions as regards security.
*
* The fact that you are presently reading this means that you have had
* knowledge of the CeCILL license and that you accept its terms.
*
*
********************************************************************************
*
* Copyright © Adichatz (2007-2020) - www.adichatz.org
*
* yves@adichatz.org
*
* Ce logiciel est un programme informatique servant à construire rapidement des
* applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
*
* Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
* respectant les principes de diffusion des logiciels libres. Vous pouvez
* utiliser, modifier et/ou redistribuer ce programme sous les conditions
* de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
* sur le site "http://www.cecill.info".
*
* En contrepartie de l'accessibilité au code source et des droits de copie,
* de modification et de redistribution accordés par cette licence, il n'est
* offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
* seule une responsabilité restreinte pèse sur l'auteur du programme,  le
* titulaire des droits patrimoniaux et les concédants successifs.
*
* A cet égard  l'attention de l'utilisateur est attirée sur les risques
* associés au chargement,  à l'utilisation,  à la modification et/ou au
* développement et à la reproduction du logiciel par l'utilisateur étant
* donné sa spécificité de logiciel libre, qui peut le rendre complexe à
* manipuler et qui le réserve donc à des développeurs et des professionnels
* avertis possédant  des  connaissances  informatiques approfondies.  Les
* utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
* logiciel à leurs besoins dans des conditions permettant d'assurer la
* sécurité de leurs systèmes et ou de leurs données et, plus généralement,
* à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
*
* Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
* pris connaissance de la licence CeCILL, et que vous en avez accepté les
* termes.
*******************************************************************************/
package org.adichatz.css.theme;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.controller.utils.IPostReskinListener;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.renderer.AdiGroupStrategy;
import org.adichatz.engine.renderer.AdiPShelfRenderer;
import org.adichatz.engine.renderer.BasicTableRenderer;
import org.adichatz.engine.tabular.ABasicTabularStatusBar;
import org.eclipse.e4.ui.css.core.dom.properties.css2.CSS2FontPropertiesImpl;
import org.eclipse.e4.ui.css.core.impl.dom.CSSStyleDeclarationImpl;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTFontHelper;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PaletteShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.stylesheets.StyleSheetList;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiReskinManager.
 *
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class AdiReskinManager extends AReskinManager {
	/** The skinned widget map. */
	private Map<Object, SkinnedWidget> skinnedWidgetMap = new HashMap<>(); // Map of SkinnedWidgets indexed by hashCode

	/** The active theme. */
	private ITheme activeTheme;

	private Map<String, CSSStyle> cssStyleMap;

	private AdiFormToolkit toolkit;

	private Display display;

	/**
	 * Instantiates a new post reskin engine.
	 */
	public AdiReskinManager(Display display) {
		THIS = this;
		this.display = display;
	}

	@Override
	public RGB getRGB(String selector, String property) {
		if (null != cssStyleMap) {
			CSSStyle cssStyle = cssStyleMap.get(selector);
			if (null != cssStyle)
				return cssStyle.getRGB(property);
		}
		return null;
	}

	public Color getColor(String selector, String property, Object element) {
		return getColor(selector, property, null, element);
	}

	/**
	 * Gets the background color.
	 *
	 * @param selector
	 *            the selector
	 * @param widget
	 *            the widget
	 * @return the background color
	 */
	@Override
	public Color getColor(String selector, String property, String colorType, Object element) {
		if (null != cssStyleMap) {
			CSSStyle cssStyle = cssStyleMap.get(selector);
			if (null != cssStyle) {
				Color color = cssStyle.getColor(property);
				if (null != element) {
					SkinnedWidget skinnedWidget = getSkinnedWidget(element);
					if (null != colorType) {
						switch (colorType) {
						case AControlController.ADI_CSS_BACKGROUND:
							skinnedWidget.backgroundSelector = selector;
							skinnedWidget.backgroundProperty = property;
							skinnedWidget.backgroundColor = color;
							break;
						case AControlController.ADI_CSS_FOREGROUND:
							skinnedWidget.foregroundSelector = selector;
							skinnedWidget.foregroundProperty = property;
							skinnedWidget.foregroundColor = color;
							break;
						default:
							break;
						}
					}
				}
				return color;
			}
		}
		return null;
	}

	/**
	 * Gets the font.
	 *
	 * @param selector
	 *            the selector
	 * @param widgetController
	 *            the widget controller
	 * @return the font
	 */
	@Override
	public Font getFont(String selector, Object element) {
		if (null != cssStyleMap) {
			CSSStyle cssStyle = cssStyleMap.get(selector);
			Widget widget = getWidget(element);
			if (null != widget && null != cssStyle) {
				Font font = cssStyle.getFont();
				if (null != element) {
					SkinnedWidget skinnedWidget = getSkinnedWidget(element);
					skinnedWidget.fontSelector = selector;
					skinnedWidget.font = font;
				}
				return font;
			}
		}
		return null;
	}

	/**
	 * Gets the widget.
	 *
	 * @param uiElement
	 *            the ui element
	 * @return the widget
	 */
	private Widget getWidget(Object uiElement) {
		Widget widget = null;
		if (uiElement instanceof Widget)
			widget = (Widget) uiElement;
		else if (uiElement instanceof GridViewerColumn)
			widget = ((GridViewerColumn) uiElement).getColumn();
		else if (uiElement instanceof TableViewerColumn)
			widget = ((TableViewerColumn) uiElement).getColumn();
		else if (uiElement instanceof TreeViewerColumn)
			widget = ((TreeViewerColumn) uiElement).getColumn();
		return widget;
	}

	/**
	 * Post reskin.
	 */
	@Override
	public void postReskin() {
		BasicTableRenderer.setDefaultForeground();
		Set<ASetController> setControllers = new HashSet<>();
		for (IPostReskinListener listener : postReskinListeners)
			listener.postReskin();
		for (SkinnedWidget skinnedWidget : skinnedWidgetMap.values()) {
			Object element = skinnedWidget.element;
			if (element instanceof PGroup) {
				PGroup pgroup = (PGroup) element;
				((AdiGroupStrategy) pgroup.getStrategy()).setColors(pgroup);
			} else if (element instanceof PShelf && ((PShelf) element).getRenderer() instanceof AdiPShelfRenderer) {
				((AdiPShelfRenderer) ((PShelf) element).getRenderer()).initializeColors();
			} else if (element instanceof Table || element instanceof Grid) {
				Object setController = ((Composite) element).getData(ASetController.SET_CONTROLLER);
				if (null != setController)
					setControllers.add((ASetController) setController);
			}
		}
		if (null != activeTheme) {
			for (Map.Entry<Object, SkinnedWidget> entry : skinnedWidgetMap.entrySet()) {
				SkinnedWidget skinnedWidget = entry.getValue();
				Object element = skinnedWidget.element;
				if (element instanceof Control) {
					// background, foreground, font coded in axml file e.g.  #CSSCOLOR(#adichatz-common, error-foreground-color)
					Control control = (Control) element;
					Color background = skinnedWidget.backgroundColor;
					if (null != background)
						control.setBackground(background);
					Color foreground = skinnedWidget.foregroundColor;
					if (null != foreground)
						control.setForeground(foreground);
					Font font = skinnedWidget.font;
					if (null != font)
						control.setFont(font);
					if (control instanceof PGroup && ((PGroup) control).getStrategy() instanceof AdiGroupStrategy)
						((AdiGroupStrategy) ((PGroup) control).getStrategy()).setColors((PGroup) control);
					else if (control instanceof Section) { // Title bar of Section is not painted
						control.notifyListeners(SWT.Resize, null);
					} else if (control instanceof PShelf && ((PShelf) control).getRenderer() instanceof PaletteShelfRenderer) {
						((PaletteShelfRenderer) ((PShelf) control).getRenderer())
								.setShadeColor(getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "button_background", control));
						notifyPaint(control);
					} else if (control instanceof ScrolledForm)
						toolkit.decorateFormHeading(((ScrolledForm) control).getForm());
					else if (control instanceof ABasicTabularStatusBar)
						((ABasicTabularStatusBar) control).setReskinBackground();
				}
			}
			for (ASetController setController : setControllers) {
				IStructuredContentProvider contentProvider = (IStructuredContentProvider) setController.getViewer()
						.getContentProvider();
				setController.setBoldFont();
				if (null != contentProvider) {
					Object[] elements = contentProvider.getElements(setController.getViewer().getInput());
					if (null != elements && 0 < elements.length)
						setController.getViewer().update(elements, null);
					if (setController instanceof ATabularController) {
						ATabularController<?> tabularController = (ATabularController<?>) setController;
						if (null != tabularController.getTableRenderer()) {
							tabularController.getTableRenderer().initResources();
							tabularController.getTableRenderer().postRefreshRendering();
						}
					}
				}
			}
		}
	}

	private void notifyPaint(Control control) {
		// Notify a new Paint listener
		Event e = new Event();
		e.gc = GC.win32_new(control, new GCData());
		e.widget = control;
		control.notifyListeners(SWT.Paint, e);
	}

	/**
	 * Gets the skinned widget.
	 *
	 * @param element
	 *            the element
	 * @return the skinned widget
	 */
	private SkinnedWidget getSkinnedWidget(Object element) {
		SkinnedWidget skinnedWidget = skinnedWidgetMap.get(element);
		if (null == skinnedWidget)
			skinnedWidget = new SkinnedWidget(element);
		return skinnedWidget;
	}

	@Override
	public void addSkinnedWidget(Widget widget) {
		new SkinnedWidget(widget);
	}

	@Override
	public void removeBackgroundSkinnedWidget(Widget widget) {
		SkinnedWidget skinnedWidget = skinnedWidgetMap.get(widget);
		if (null != skinnedWidget)
			skinnedWidget.backgroundColor = null;
	}

	@Override
	public void removeForegroundSkinnedWidget(Widget widget) {
		SkinnedWidget skinnedWidget = skinnedWidgetMap.get(widget);
		if (null != skinnedWidget)
			skinnedWidget.foregroundColor = null;
	}

	@Override
	public void removeFontSkinnedWidget(Widget widget) {
		SkinnedWidget skinnedWidget = skinnedWidgetMap.get(widget);
		if (null != skinnedWidget)
			skinnedWidget.font = null;
	}

	/**
	 * Adds the theme.
	 *
	 * @param themeEngine
	 *            the theme engine
	 * @param theme
	 *            the theme
	 */
	@Override
	public void addTheme(Object themeEngineParam, Object themeParam) {
		AdiThemeEngine themeEngine = (AdiThemeEngine) themeEngineParam;
		ITheme theme = (ITheme) themeParam;
		if (theme.equals(activeTheme))
			return;
		cssStyleMap = new HashMap<String, CSSStyle>();

		StyleSheetList styleSheetList = themeEngine.getCSSEngines().iterator().next().getDocumentCSS().getStyleSheets();
		for (int k = 0; k < styleSheetList.getLength(); k++) {
			CSSStyleSheet stylesheet = (CSSStyleSheet) styleSheetList.item(k);
			CSSRuleList ruleList = stylesheet.getCssRules();
			for (int i = 0; i < ruleList.getLength(); i++) {
				CSSRule rule = ruleList.item(i);
				if (rule instanceof CSSStyleRule) {
					CSSStyleRule styleRule = (CSSStyleRule) rule;
					CSSStyleDeclarationImpl styleDeclaration = (CSSStyleDeclarationImpl) styleRule.getStyle();
					CSSStyle cssStyle;
					CSSValue fontFamily = null;
					CSSValue fontSize = null;
					CSSValue fontStyle = null;
					CSSValue fontWeight = null;
					CSSValue fontVariant = null;
					String selectorText = styleRule.getSelectorText().replace("*.", ".").replace("*#", "#");
					StringTokenizer tokenizer = new StringTokenizer(selectorText, ",");
					Set<String> selectors = new HashSet<>();
					String firstSelector = null;
					while (tokenizer.hasMoreElements()) {
						String selector = tokenizer.nextToken().trim();
						selectors.add(selector);
						if (null == firstSelector)
							firstSelector = selector;
					}
					for (int j = 0; j < styleDeclaration.getLength(); j++) {
						String property = styleDeclaration.item(j);
						CSSValue cssValue = styleDeclaration.getPropertyCSSValue(property);
						if (cssValue instanceof RGBColor) {
							for (String selector : selectors) {
								cssStyle = getCssStyle(selector);
								cssStyle.rgbColorMap.put(property, cssValue);
								cssStyle.colorMap.remove(property);
							}
						} else {
							switch (property) {
							case "font-family":
								fontFamily = cssValue instanceof CSSValueList ? ((CSSValueList) cssValue).item(0) : cssValue;
								break;
							case "font-size":
								fontSize = cssValue;
								break;
							case "font-style":
								fontStyle = cssValue;
								break;
							case "font-weight":
								fontWeight = cssValue;
								break;
							case "font-variant":
								fontVariant = cssValue;
								break;
							default:
								break;
							}
						}
					}
					if ((null != fontFamily && fontFamily instanceof CSSPrimitiveValue)
							|| (null != fontSize && fontSize instanceof CSSPrimitiveValue)
							|| (null != fontStyle && fontStyle instanceof CSSPrimitiveValue)
							|| (null != fontWeight && fontWeight instanceof CSSPrimitiveValue)
							|| (null != fontVariant && fontVariant instanceof CSSPrimitiveValue)) {
						CSS2FontPropertiesImpl fontProperties = new CSS2FontPropertiesImpl();
						if (null != fontFamily && fontFamily instanceof CSSPrimitiveValue)
							fontProperties.setFamily((CSSPrimitiveValue) fontFamily);
						if (null != fontSize && fontSize instanceof CSSPrimitiveValue)
							fontProperties.setSize((CSSPrimitiveValue) fontSize);
						if (null != fontStyle && fontStyle instanceof CSSPrimitiveValue)
							fontProperties.setStyle((CSSPrimitiveValue) fontStyle);
						if (null != fontWeight && fontWeight instanceof CSSPrimitiveValue)
							fontProperties.setWeight((CSSPrimitiveValue) fontWeight);
						if (null != fontVariant && fontVariant instanceof CSSPrimitiveValue)
							fontProperties.setVariant((CSSPrimitiveValue) fontVariant);
						for (String selector : selectors) {
							cssStyle = getCssStyle(selector);
							cssStyle.fontProperties = fontProperties;
							cssStyle.font = null;
						}
					}
				}
			}
		}

		activeTheme = theme;
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		toolkit.dispose();
		toolkit = new AdiFormToolkit(display);
		AdichatzApplication.getInstance().getApplContext().put(AdiFormToolkit.class.getName(), toolkit);
		FormColors formColors = toolkit.getColors();
		CSSStyle formsStyle = cssStyleMap.get("#adichatz-forms");
		if (null != formsStyle) {
			formColors.setBackground(new Color(display, formsStyle.getRGB("background-color")));
			formColors.setForeground(new Color(display, formsStyle.getRGB("foreground-color")));
			createColor(formColors, IFormColors.H_GRADIENT_END, formsStyle, "org-eclipse-ui-forms-H_GRADIENT_END");
			createColor(formColors, IFormColors.H_GRADIENT_START, formsStyle, "org-eclipse-ui-forms-H_GRADIENT_START");
			createColor(formColors, IFormColors.H_BOTTOM_KEYLINE1, formsStyle, "org-eclipse-ui-forms-H_BOTTOM_KEYLINE1");
			createColor(formColors, IFormColors.H_BOTTOM_KEYLINE2, formsStyle, "org-eclipse-ui-forms-H_BOTTOM_KEYLINE2");
			createColor(formColors, IFormColors.H_HOVER_LIGHT, formsStyle, "org-eclipse-ui-forms-H_H_HOVER_LIGHT");
			createColor(formColors, IFormColors.H_HOVER_FULL, formsStyle, "org-eclipse-ui-forms-H_H_HOVER_FULL");
			createColor(formColors, IFormColors.TB_BG, formsStyle, "org-eclipse-ui-forms-TB_BG");
			createColor(formColors, IFormColors.BORDER, formsStyle, "org-eclipse-ui-forms-BORDER");
			createColor(formColors, IFormColors.TB_TOGGLE, formsStyle, "org-eclipse-ui-forms-TB_TOGGLE");
			createColor(formColors, IFormColors.TB_TOGGLE_HOVER, formsStyle, "org-eclipse-ui-forms-TB_TOGGLE_HOVER");
			createColor(formColors, IFormColors.TITLE, formsStyle, "org-eclipse-ui-forms-TITLE");
			createColor(formColors, IFormColors.SEPARATOR, formsStyle, "org-eclipse-ui-forms-SEPARATOR");
			// Add TB_BORDER deprecate but still used in MasterDetailBlock (see CacheManagerPart)
			createColor(formColors, IFormColors.TB_BORDER, formsStyle, "org-eclipse-ui-forms-H_BOTTOM_KEYLINE2");
		}
		for (SkinnedWidget skinnedWidget : skinnedWidgetMap.values()) {
			skinnedWidget.disposeResource();
			if (null != skinnedWidget.backgroundSelector)
				getColor(skinnedWidget.backgroundSelector, skinnedWidget.backgroundProperty, AControlController.ADI_CSS_BACKGROUND,
						skinnedWidget.element);
			if (null != skinnedWidget.foregroundSelector)
				getColor(skinnedWidget.foregroundSelector, skinnedWidget.foregroundProperty, AControlController.ADI_CSS_FOREGROUND,
						skinnedWidget.element);
			if (null != skinnedWidget.fontSelector)
				getFont(skinnedWidget.fontSelector, skinnedWidget.element);
		}
	}

	private void createColor(FormColors formColors, String formKey, CSSStyle formsStyle, String cssKey) {
		RGB rgb;
		rgb = formsStyle.getRGB(cssKey);
		if (null != rgb)
			formColors.createColor(formKey, rgb);
		else
			logError("Invalid color key '" + cssKey + "' for selector '#adichatz-forms'!");
	}

	/**
	 * Gets the css style.
	 *
	 * @param selector
	 *            the selector
	 * @return the css style
	 */
	private CSSStyle getCssStyle(String selector) {
		CSSStyle cssStyle = cssStyleMap.get(selector);
		if (null == cssStyle) {
			cssStyle = new CSSStyle();
			cssStyleMap.put(selector, cssStyle);
		}
		return cssStyle;
	}

	/*
	 * I N N E R - C L A S S E S
	 */

	/**
	 * The Class SkinnedWidget.
	 *
	 * @author Yves Drumbonnet
	 */
	private class SkinnedWidget {

		/** The element. */
		private Object element;

		/** The background selector. */
		private String backgroundSelector;

		/** The foreground selector. */
		private String foregroundSelector;

		/** The background property. */
		private String backgroundProperty;

		/** The foreground property. */
		private String foregroundProperty;

		/** The font selector. */
		private String fontSelector;

		private Color backgroundColor;

		private Color foregroundColor;

		/** The font. */
		private Font font;

		/**
		 * Instantiates a new skinned widget.
		 *
		 * @param element
		 *            the element
		 */
		private SkinnedWidget(Object element) {
			this.element = element;
			skinnedWidgetMap.put(element, this);
			Widget widget = null;
			if (element instanceof Widget) {
				widget = (Widget) element;
			} else if (element instanceof TableViewerColumn)
				widget = ((TableViewerColumn) element).getColumn();
			else if (element instanceof GridViewerColumn)
				widget = ((GridViewerColumn) element).getColumn();
			if (null != widget)
				widget.addDisposeListener((e) -> {
					skinnedWidgetMap.remove(element);
				});
		}

		/**
		 * Dispose.
		 */
		private void disposeResource() {
		}
	}

	/**
	 * The Class CSSStyle.
	 *
	 * @author Yves Drumbonnet
	 */
	public class CSSStyle {
		Map<String, CSSValue> rgbColorMap = new HashMap<>();

		Map<String, Color> colorMap = new HashMap<>();

		/** The font properties. */
		CSS2FontPropertiesImpl fontProperties;

		/** The font. */
		Font font;

		private Color getColor(String property) {
			Color color = colorMap.get(property);
			CSSValue rgbColor = rgbColorMap.get(property);
			if (null == color && null != rgbColor)
				color = CSSSWTColorHelper.getSWTColor(rgbColor, display);
			return color;
		}

		private RGB getRGB(String property) {
			Color color = colorMap.get(property);
			if (null != color)
				return color.getRGB();
			CSSValue rgbColor = rgbColorMap.get(property);
			if (null != rgbColor)
				return CSSSWTColorHelper.getRGBA(rgbColor).rgb;
			return null;
		}

		/**
		 * Gets the font.
		 *
		 * @return the font
		 */
		private Font getFont() {
			if (null == font && null != fontProperties) {
				FontData fontData = CSSSWTFontHelper.getFontData(fontProperties, null);
				font = new Font(display, fontData);
			}
			return font;
		}
	}
}
