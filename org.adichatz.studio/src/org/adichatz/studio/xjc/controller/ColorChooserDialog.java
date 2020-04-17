package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.e4.ui.css.core.impl.dom.CSSStyleDeclarationImpl;
import org.eclipse.e4.ui.css.core.impl.dom.parsers.CSSParserImpl;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;

import net.miginfocom.swt.MigLayout;

@SuppressWarnings("restriction")
public class ColorChooserDialog extends AChooserDialog {

	/*
	 * S T A T I C
	 */
	private static Set<String> COLOR_PROPERTIES = new HashSet<>();
	static {
		COLOR_PROPERTIES.add("background-color");
		COLOR_PROPERTIES.add("color");
	}
	/*
	 * end S T A T I C
	 */

	private Button[] swtButtons = new Button[36];

	private Button[] formButtons = new Button[13];

	private int index;

	private Map<String, SelectorLeave> selectorLeavesMap = new HashMap<>();

	private Display display;

	public ColorChooserDialog(Shell shell, AChooserController chooserController) {
		super(shell, AdichatzApplication.getInstance().getFormToolkit(), "colorChooser", "xjc/color.png", chooserController);
		display = shell.getDisplay();
	}

	protected IConfirmContent getConfirmContent() {
		return new IConfirmContent() {

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[grow,fill][]"));
				CTabFolder folder = new CTabFolder(parent, SWT.FLAT | SWT.TOP);
				toolkit.paintBordersFor(folder);
				toolkit.adapt(folder, true, true);
				folder.setLayoutData("w 50:n:n, h 50:n:n");
				resultText = toolkit.createText(parent, null, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
				String currentValue = (String) chooserController.getValue();
				resultText.setText(null == currentValue ? "" : currentValue);
				resultText.setLayoutData("h 100:100:n");

				createSWTItem(folder);
				createFormItem(folder);
				createCssColor(folder);
				createPersoColor(folder);

				folder.setSelection(0);
			}

			private void createPersoColor(CTabFolder folder) {
				CTabItem cssItem = createItem(folder, "perso.color");
				Composite cssComposite = (Composite) cssItem.getControl();
				cssComposite.setLayout(new MigLayout("wrap 1, ins 0", "al center, grow,fill", "20[grow,fill]"));
				Hyperlink hyperlink = toolkit.createHyperlink(cssComposite, resourceBundle.getString("perso.color"), SWT.NONE);
				hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						ColorDialog colorDialog = new ColorDialog(folder.getShell());
						String currentValue = resultText.getText();
						if (null != currentValue) {
							String startValue = isColumn ? "return #COLOR(#" : "#COLOR(#";
							if (currentValue.startsWith(startValue)) {
								int start = startValue.length();
								String hexaString = currentValue.substring(start, start + 6);
								try {
									RGB rgb = EngineTools.getRGBFromHexa(hexaString);
									colorDialog.setRGB(rgb);
								} catch (Exception ex) {
								}
							}
						}
						RGB rgb = colorDialog.open();
						if (null != rgb) {
							String hex = String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue);
							resultText.setText(getReturn("#COLOR(".concat(hex).concat(")")));
						}
					}
				});
			}

			private void createCssColor(CTabFolder folder) {
				Display display = folder.getDisplay();
				CTabItem cssItem = createItem(folder, "css.item");
				Composite cssComposite = (Composite) cssItem.getControl();
				cssComposite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
				setSelectorTree();
				TreeViewer treeViewer = new TreeViewer(cssComposite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
				treeViewer.getTree().setLayoutData("h 200:200:n");
				treeViewer.addSelectionChangedListener((e) -> {
					Object selectedObject = ((StructuredSelection) e.getSelection()).getFirstElement();
					if (selectedObject instanceof CSSColor) {
						CSSColor cssColor = ((CSSColor) selectedObject);
						resultText.setText(getCssValue(cssColor.selector, cssColor.property));
					}
				});
				SelectorLeave[] selectorLeaves = getSelectorLeaves();
				treeViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof SelectorLeave)
							return ((SelectorLeave) element).selector;
						else
							return ((CSSColor) element).property;
					}

					@Override
					public Image getImage(Object element) {
						if (element instanceof CSSColor) {
							Image image = new Image(display, 16, 16);
							GC gc = new GC(image);
							gc.setBackground(((CSSColor) element).getColor());
							gc.fillRectangle(0, 0, 16, 16);
							gc.drawRectangle(0, 0, 16, 16);
							gc.dispose();
							return image;
						}
						return null;
					}
				});
				treeViewer.setContentProvider(new ITreeContentProvider() {

					@Override
					public Object[] getElements(Object inputElement) {
						return selectorLeaves;
					}

					@Override
					public Object[] getChildren(Object parentElement) {
						if (parentElement instanceof SelectorLeave)
							return ((SelectorLeave) parentElement).cssColorMap.values().toArray();
						return null;
					}

					@Override
					public Object getParent(Object element) {
						return null;
					}

					@Override
					public boolean hasChildren(Object element) {
						return element instanceof SelectorLeave;
					}
				});
				treeViewer.setInput(selectorLeavesMap.values());
				String startValue = isColumn ? "return #CSSCOLOR(" : "#CSSCOLOR(";
				String currentValue = resultText.getText();
				if (null != currentValue && currentValue.startsWith(startValue)) {
					for (SelectorLeave selectorLeave : selectorLeavesMap.values())
						for (CSSColor cssColor : selectorLeave.cssColorMap.values()) {
							if (currentValue.equals(getCssValue(selectorLeave.selector, cssColor.property))) {
								TreePath treePath = new TreePath(new Object[] { selectorLeave, cssColor });
								TreeSelection treeSelection = new TreeSelection(new TreePath[] { treePath });
								treeViewer.setSelection(treeSelection);
							}
						}
				}
				treeViewer.expandAll();
			}

			private String getCssValue(String selector, String cssValue) {
				return getReturn("#CSSCOLOR(".concat(selector).concat(",").concat(cssValue).concat(")"));
			}

			private void createFormItem(CTabFolder folder) {
				CTabItem formsItem = createItem(folder, "forms.item");
				Composite formsComposite = (Composite) formsItem.getControl();
				index = 0;
				createFormButton(formsComposite, IFormColors.BORDER, "BORDER");
				createFormButton(formsComposite, IFormColors.TITLE, "TITLE");
				createFormButton(formsComposite, IFormColors.TB_BG, "TB_BG");
				createFormButton(formsComposite, IFormColors.TB_BORDER, "TB_BORDER");
				createFormButton(formsComposite, IFormColors.SEPARATOR, "SEPARATOR");
				createFormButton(formsComposite, IFormColors.TB_TOGGLE, "TB_TOGGLE");
				createFormButton(formsComposite, IFormColors.TB_TOGGLE_HOVER, "TB_TOGGLE_HOVER");
				createFormButton(formsComposite, IFormColors.H_BOTTOM_KEYLINE1, "H_BOTTOM_KEYLINE1");
				createFormButton(formsComposite, IFormColors.H_BOTTOM_KEYLINE2, "H_BOTTOM_KEYLINE2");
				createFormButton(formsComposite, IFormColors.H_GRADIENT_START, "H_GRADIENT_START");
				createFormButton(formsComposite, IFormColors.H_GRADIENT_END, "H_GRADIENT_END");
				createFormButton(formsComposite, IFormColors.H_HOVER_FULL, "H_HOVER_FULL");
				createFormButton(formsComposite, IFormColors.H_HOVER_LIGHT, "H_HOVER_LIGHT");
			}

			private void createSWTItem(CTabFolder folder) {
				CTabItem swtItem = createItem(folder, "swt.item");
				Composite swtComposite = (Composite) swtItem.getControl();
				index = 0;
				createSWTButton(swtComposite, "BLACK", SWT.COLOR_BLACK);
				createSWTButton(swtComposite, "BLUE", SWT.COLOR_BLUE);
				createSWTButton(swtComposite, "CYAN", SWT.COLOR_CYAN);
				createSWTButton(swtComposite, "DARK_BLUE", SWT.COLOR_DARK_BLUE);
				createSWTButton(swtComposite, "DARK_CYAN", SWT.COLOR_DARK_CYAN);
				createSWTButton(swtComposite, "DARK_GRAY", SWT.COLOR_DARK_GRAY);
				createSWTButton(swtComposite, "DARK_GREEN", SWT.COLOR_DARK_GREEN);
				createSWTButton(swtComposite, "DARK_MAGENTA", SWT.COLOR_DARK_MAGENTA);
				createSWTButton(swtComposite, "DARK_RED", SWT.COLOR_DARK_RED);
				createSWTButton(swtComposite, "DARK_YELLOW", SWT.COLOR_DARK_YELLOW);
				createSWTButton(swtComposite, "GRAY", SWT.COLOR_GRAY);
				createSWTButton(swtComposite, "GREEN", SWT.COLOR_GREEN);
				createSWTButton(swtComposite, "MAGENTA", SWT.COLOR_MAGENTA);
				createSWTButton(swtComposite, "RED", SWT.COLOR_RED);
				createSWTButton(swtComposite, "WHITE", SWT.COLOR_WHITE);
				createSWTButton(swtComposite, "YELLOW", SWT.COLOR_YELLOW);
				Button button = createSWTButton(swtComposite, "INFO_BACKGROUND", SWT.COLOR_INFO_BACKGROUND);
				button.setLayoutData("newline");
				createSWTButton(swtComposite, "INFO_FOREGROUND", SWT.COLOR_INFO_FOREGROUND);
				createSWTButton(swtComposite, "LINK_FOREGROUND", SWT.COLOR_LINK_FOREGROUND);
				createSWTButton(swtComposite, "LIST_BACKGROUND", SWT.COLOR_LIST_BACKGROUND);
				createSWTButton(swtComposite, "LIST_FOREGROUND", SWT.COLOR_LIST_FOREGROUND);
				createSWTButton(swtComposite, "LIST_SELECTION", SWT.COLOR_LIST_SELECTION);
				createSWTButton(swtComposite, "LIST_SELECTION_TEXT", SWT.COLOR_LIST_SELECTION_TEXT);
				button = createSWTButton(swtComposite, "TITLE_BACKGROUND", SWT.COLOR_TITLE_BACKGROUND);
				button.setLayoutData("newline");
				createSWTButton(swtComposite, "TITLE_FOREGROUND", SWT.COLOR_TITLE_FOREGROUND);
				createSWTButton(swtComposite, "TITLE_INACTIVE_BACKGROUND", SWT.COLOR_TITLE_INACTIVE_BACKGROUND);
				createSWTButton(swtComposite, "TITLE_INACTIVE_BACKGROUND_GRADIENT", SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);
				createSWTButton(swtComposite, "TITLE_INACTIVE_FOREGROUND", SWT.COLOR_TITLE_INACTIVE_FOREGROUND);
				button = createSWTButton(swtComposite, "TRANSPARENT", SWT.COLOR_TRANSPARENT);
				button.setLayoutData("newline");
				button = createSWTButton(swtComposite, "TRANSPARENT", SWT.COLOR_WIDGET_BACKGROUND);
				button.setLayoutData("newline");
				createSWTButton(swtComposite, "WIDGET_BORDER", SWT.COLOR_WIDGET_BORDER);
				createSWTButton(swtComposite, "WIDGET_DARK_SHADOW", SWT.COLOR_WIDGET_DARK_SHADOW);
				createSWTButton(swtComposite, "WIDGET_FOREGROUND", SWT.COLOR_WIDGET_FOREGROUND);
				createSWTButton(swtComposite, "WIDGET_HIGHLIGHT_SHADOW", SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
				createSWTButton(swtComposite, "WIDGET_LIGHT_SHADOW", SWT.COLOR_WIDGET_LIGHT_SHADOW);
				createSWTButton(swtComposite, "WIDGET_NORMAL_SHADOW", SWT.COLOR_WIDGET_NORMAL_SHADOW);
			}

			private SelectorLeave[] getSelectorLeaves() {
				List<SelectorLeave> selectorLeaves = new ArrayList<SelectorLeave>(selectorLeavesMap.values());
				Collections.sort(selectorLeaves, new Comparator<SelectorLeave>() {

					@Override
					public int compare(SelectorLeave o1, SelectorLeave o2) {
						return o1.selector.compareTo(o2.selector);
					}
				});
				return selectorLeaves.toArray(new SelectorLeave[selectorLeaves.size()]);
			}

			private void setSelectorTree() {
				try {
					URL url = FileLocator.resolve(new URL(EngineConstants.DEFAULT_STYLE_SHEET_URI));
					InputStream stream = url.openStream();
					InputSource source = new InputSource();
					source.setByteStream(stream);
					source.setURI(url.toString());
					CSSStyleSheet stylesheet = new CSSParserImpl().parseStyleSheet(source);

					CSSRuleList ruleList = stylesheet.getCssRules();
					for (int i = 0; i < ruleList.getLength(); i++) {
						CSSRule rule = ruleList.item(i);
						if (rule instanceof CSSStyleRule) {
							CSSStyleRule styleRule = (CSSStyleRule) rule;
							CSSStyleDeclarationImpl styleDeclaration = (CSSStyleDeclarationImpl) styleRule.getStyle();
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
										SelectorLeave selectorLeave = selectorLeavesMap.get(selector);
										if (null == selectorLeave) {
											selectorLeave = new SelectorLeave(selector);
											selectorLeavesMap.put(selector, selectorLeave);
										}
										CSSColor cssColor = selectorLeave.cssColorMap.get(property);
										if (null == cssColor) {
											cssColor = new CSSColor(selector, property, cssValue);
											selectorLeave.cssColorMap.put(property, cssColor);
										}
									}
								}
							}
						}
					}

				} catch (IOException e) {
					logError("File '" + EngineConstants.DEFAULT_STYLE_SHEET_URI
							+ "' cannot be found?! Check if 2 Bundles/Projects have same name.");
				}
			}

			private Button createSWTButton(final Composite parent, String text, int swtColor) {
				return createButton(parent, "swt.".concat(text), parent.getDisplay().getSystemColor(swtColor), "SWT.COLOR_" + text,
						swtButtons);
			}

			private Button createFormButton(final Composite parent, String key, String text) {
				return createButton(parent, "forms." + text, toolkit.getColors().getColor(key), "IFormColors." + text, formButtons);
			}

			private Button createButton(final Composite parent, String text, Color color, String resultString, Button[] buttons) {
				Button button = toolkit.createButton(parent, resourceBundle.getString(text), SWT.CHECK);
				String currentValue = resultText.getText();
				if (null != currentValue && getSWTValue(resultString).equals(currentValue))
					button.setSelection(true);
				Image image = new Image(parent.getDisplay(), 16, 16);
				GC gc = new GC(image);
				gc.setBackground(color);
				gc.fillRectangle(0, 0, 16, 16);
				gc.drawRectangle(0, 0, 16, 16);
				gc.dispose();
				button.setImage(image);
				button.setAlignment(SWT.LEFT);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						for (Button btn : swtButtons)
							if (null != btn)
								btn.setSelection(false);
						for (Button btn : formButtons)
							if (null != btn)
								btn.setSelection(false);
						button.setSelection(true);
						resultText.setText(getSWTValue(resultString));
					}
				});
				buttons[index++] = button;
				return button;
			}

			private String getSWTValue(String swtColor) {
				return getReturn("#COLOR(".concat(swtColor).concat(")"));
			}

			/**
			 * Ok pressed.
			 * 
			 * @param complementControls
			 *            the complement controls
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
				chooserController.broadCastedSetValue(resultText.getText());
			}

			private CTabItem createItem(CTabFolder folder, String itemTitle) {
				CTabItem item = new CTabItem(folder, SWT.NONE);
				item.setText(resourceBundle.getString(itemTitle));
				Composite composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(folder);

				item.setControl(composite);
				composite.setLayout(new MigLayout("wrap 4, ins 0", "grow,fill"));
				composite.setLayoutData("w 0:n:n, h 0:n:n");
				return item;
			}
		};
	}

	@Override
	public boolean close() {
		for (SelectorLeave selectorLeave : selectorLeavesMap.values())
			for (CSSColor cssColor : selectorLeave.cssColorMap.values())
				if (null != cssColor.color)
					cssColor.color.dispose();
		return super.close();
	}

	/*
	 * I N N E R C L A S S E S
	 */
	class SelectorLeave {
		private String selector;

		private Map<String, CSSColor> cssColorMap = new HashMap<>();

		private SelectorLeave(String selector) {
			this.selector = selector;
		}
	}

	class CSSColor {
		private String selector;

		private String property;

		private CSSValue cssValue;

		private Color color;

		private CSSColor(String selector, String property, CSSValue cssValue) {
			this.selector = selector;
			this.property = property;
			this.cssValue = cssValue;
		}

		private Color getColor() {
			if (null == color)
				color = CSSSWTColorHelper.getSWTColor((RGBColor) cssValue, display);
			return color;
		}
	}

}
