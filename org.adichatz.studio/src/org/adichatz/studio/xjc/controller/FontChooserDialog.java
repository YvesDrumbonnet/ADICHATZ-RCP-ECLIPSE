package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.e4.ui.css.core.dom.properties.css2.CSS2FontPropertiesImpl;
import org.eclipse.e4.ui.css.core.impl.dom.CSSStyleDeclarationImpl;
import org.eclipse.e4.ui.css.core.impl.dom.parsers.CSSParserImpl;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTFontHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.RGBColor;

import net.miginfocom.swt.MigLayout;

@SuppressWarnings("restriction")
public class FontChooserDialog extends AChooserDialog {

	private Map<String, CSS2FontPropertiesImpl> selectorFontMap = new HashMap<>();

	public FontChooserDialog(Shell shell, AChooserController chooserController) {
		super(shell, AdichatzApplication.getInstance().getFormToolkit(), "fontChooser", "xjc/font.png", chooserController);
		confirmContent = getConfirmContent();
		isColumn = chooserController.getEntity().getBean() instanceof ColumnFieldType;
	}

	protected IConfirmContent getConfirmContent() {
		return new IConfirmContent() {

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[grow,fill][grow,fill]"));
				CTabFolder folder = new CTabFolder(parent, SWT.FLAT | SWT.TOP);
				toolkit.paintBordersFor(folder);
				toolkit.adapt(folder, true, true);
				folder.setLayoutData("w 50:n:n, h 50:n:n");
				resultText = toolkit.createText(parent, null, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
				resultText.setLayoutData("h 100:100:n");
				String value = (String) chooserController.getValue();
				resultText.setText(null == value ? "" : value);

				createFormItem(folder);
				createCssFont(folder);

				folder.setSelection(0);
			}

			private void createCssFont(CTabFolder folder) {
				CTabItem cssItem = createItem(folder, "css.item");
				Composite cssComposite = (Composite) cssItem.getControl();
				cssComposite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
				setSelectorTree();
				TableViewer tableViewer = new TableViewer(cssComposite,
						SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
				tableViewer.getTable().setLayoutData("h 100:100:n");
				tableViewer.addSelectionChangedListener((e) -> {
					Object selectedObject = ((StructuredSelection) e.getSelection()).getFirstElement();
					if (selectedObject instanceof Map.Entry)
						resultText.setText(getCssValue(selectedObject));
				});
				tableViewer.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						Map.Entry<String, CSS2FontPropertiesImpl> entry = (Map.Entry<String, CSS2FontPropertiesImpl>) element;
						CSS2FontPropertiesImpl cssFont = entry.getValue();
						String values = getValues(cssFont.getFamily(), cssFont.getSize(), cssFont.getStyle(), cssFont.getWeight(),
								cssFont.getVariant());
						return entry.getKey().concat(": ").concat(values);
					}

					@Override
					public Font getFont(Object element) {
						CSS2FontPropertiesImpl cssFont = ((Map.Entry<String, CSS2FontPropertiesImpl>) element).getValue();
						return new Font(tableViewer.getTable().getDisplay(), CSSSWTFontHelper.getFontData(cssFont, null));
					}

					private String getValues(CSSPrimitiveValue... cssValues) {
						StringBuffer valuesSB = new StringBuffer();
						for (CSSPrimitiveValue cssValue : cssValues) {
							if (null != cssValue) {
								if (0 < valuesSB.length())
									valuesSB.append(", ");
								valuesSB.append(cssValue.getCssText());
							}
						}
						if (0 < valuesSB.length()) {
							valuesSB.insert(0, " (");
							valuesSB.append(")");
						}
						return valuesSB.toString();
					}
				});
				tableViewer.setContentProvider(new IStructuredContentProvider() {
					@Override
					public Object[] getElements(Object inputElement) {
						return selectorFontMap.entrySet().toArray();
					}
				});
				tableViewer.setInput(selectorFontMap.entrySet().toArray());
				String startValue = isColumn ? "return #CSSFONT(" : "#CSSFONT(";
				String currentValue = resultText.getText();
				if (null != currentValue && currentValue.startsWith(startValue))
					for (Map.Entry<String, CSS2FontPropertiesImpl> entry : selectorFontMap.entrySet()) {
						if (getCssValue(entry).equals(currentValue)) {
							tableViewer.setSelection(new StructuredSelection(entry));
							break;
						}
					}
			}

			private void createFormItem(CTabFolder folder) {
				CTabItem formsItem = createItem(folder, "forms.item");
				Composite formsComposite = (Composite) formsItem.getControl();
				createHyperlink(formsComposite, JFaceResources.BANNER_FONT, "BANNER_FONT");
				createHyperlink(formsComposite, JFaceResources.DEFAULT_FONT, "DEFAULT_FONT");
				createHyperlink(formsComposite, JFaceResources.DIALOG_FONT, "DIALOG_FONT");
				createHyperlink(formsComposite, JFaceResources.HEADER_FONT, "HEADER_FONT");
				createHyperlink(formsComposite, JFaceResources.TEXT_FONT, "TEXT_FONT");
			}

			private String getCssValue(Object selectedObject) {
				return getReturn("#CSSFONT(" + ((Map.Entry<String, CSS2FontPropertiesImpl>) selectedObject).getKey() + ")");
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
							CSSValue fontFamily = null;
							CSSValue fontSize = null;
							CSSValue fontStyle = null;
							CSSValue fontWeight = null;
							CSSValue fontVariant = null;
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
								if (!(cssValue instanceof RGBColor)) {
									switch (property) {
									case "font-family":
										fontFamily = cssValue instanceof CSSValueList ? ((CSSValueList) cssValue).item(0)
												: cssValue;
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
									CSS2FontPropertiesImpl cssFont = selectorFontMap.get(selector);
									if (null == cssFont)
										selectorFontMap.put(selector, fontProperties);
								}
							}
						}
					}

				} catch (IOException e) {
					logError("File '" + EngineConstants.DEFAULT_STYLE_SHEET_URI
							+ "' cannot be found?! Check if 2 Bundles/Projects have same name.");
				}
			}

			private List<Element> getCSSElements(Element parentElement, String tagName) {
				NodeList nodeList = parentElement.getElementsByTagName(tagName);
				int length = nodeList.getLength();
				List<Element> elements = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					Node node = nodeList.item(i);
					if (node instanceof Element)
						elements.add((Element) node);
				}
				return elements;
			}

			private Hyperlink createHyperlink(final Composite parent, String key, String text) {
				Hyperlink hyperlink = toolkit.createHyperlink(parent, resourceBundle.getString(key) + ".", SWT.NONE);
				hyperlink.setFont(JFaceResources.getFont(key));
				hyperlink.setToolTipText("JFaceResources." + key);
				if (resultText.getText().equals(getReturn(getFormValue(text))))
					hyperlink.setBackground(toolkit.getColors().getInactiveBackground());
				else
					hyperlink.setBackground(toolkit.getColors().getBackground());
				hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						resultText.setText(getReturn(getFormValue(text)));
					}
				});
				return hyperlink;
			}

			private String getFormValue(String value) {
				return "#FONT(".concat("JFaceResources.").concat(value).concat(")");
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
				composite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill"));
				composite.setLayoutData("w 0:n:n, h 0:n:n");
				return item;
			}
		};
	}

	@Override
	public boolean close() {
		return super.close();
	}
}
