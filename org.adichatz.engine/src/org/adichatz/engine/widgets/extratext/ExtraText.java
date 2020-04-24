/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe �
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement,
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.engine.widgets.extratext;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.engine.common.LogBroker.logError;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.xml.sax.SAXException;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtraText.
 * 
 * This class is derived from work of Alonso Dominguez
 * 
 * @see http://www.javacodegeeks.com/2012/07/richtext-editor-component-for-swt-based.html
 * 
 * @author Yves Drumbonnet
 */
public class ExtraText extends Composite {

	/** The cached styles. */
	private List<StyleRange> cachedStyles = Collections.synchronizedList(new LinkedList<StyleRange>());

	/** The tool bar. */
	private ToolBar toolBar;

	/** The styled text. */
	private StyledText styledText;

	/** The bold item. */
	private ToolItem boldItem;

	/** The italic item. */
	private ToolItem italicItem;

	/** The strike through item. */
	private ToolItem strikeThroughItem;

	/** The underline item. */
	private ToolItem underlineItem;

	/** The paste item. */
	private ToolItem pasteItem;

	/** The hyperlink item. */
	private ToolItem hyperlinkItem;

	/**
	 * Instantiates a new rich text.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public ExtraText(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}

	/**
	 * Inits the components.
	 */
	private void initComponents() {
		MigLayout migLayout = new MigLayout("wrap 1, ins 0", "grow,fill", "[]0[grow,fill]");
		setLayout(migLayout);

		createToolBar(this);

		styledText = new StyledText(this, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);

		final ToolTip toolTip = new ToolTip(styledText.getShell(), SWT.BALLOON);
		styledText.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent event) {
				toolTip.setVisible(false);
				if ((event.stateMask & SWT.MOD1) != 0) {
					try {
						linkActivated(getHyperlink(event));
					} catch (IllegalArgumentException e) {
						// no character under event.x, event.y
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent event) {
				String message = getHyperlink(event);
				if (null != message) {
					toolTip.setMessage(message);
					toolTip.setVisible(true);
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent event) {
				toolTip.setVisible(false);
				try {
					linkActivated(getHyperlink(event));
				} catch (IllegalArgumentException e) {
					// no character under event.x, event.y
				}
			}
		});
		styledText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				handleKeyReleased(e);
			}
		});
		styledText.addExtendedModifyListener((event) -> {
			handleExtendedModified(event);
			handleTextSelected();
		});
		styledText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				updateStyleButtons();
			}
		});
		styledText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				handleTextSelected();
			}
		});
	}

	/**
	 * Gets the styled text.
	 * 
	 * @return the styled text
	 */
	public StyledText getStyledText() {
		return styledText;
	}

	/**
	 * Obtain an HTML formatted text from the component contents.
	 * 
	 * @return an HTML formatted text
	 */
	public String getFormattedText() {
		String plainText = styledText.getText();
		if (0 == plainText.length())
			return null;

		RichStringBuilder builder = new RichStringBuilder();
		builder.append("<html>");
		Integer[] lineBreaks = getLineBreaks();

		int brIdx = 0;
		int start = 0;
		int end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());

		while (start <= end) {
			builder.startParagraph();
			StyleRange[] ranges = styledText.getStyleRanges(start, (end - start));
			if (ranges != null && ranges.length > 0) {
				for (StyleRange range : ranges) {
					if (start < range.start)
						builder.append(plainText.substring(start, range.start));
					addFormattedText(plainText.substring(range.start, range.start + range.length), builder, range);
					start = (range.start + range.length);
				}
			}
			if (start < end) {
				builder.append(plainText.substring(start, end));
			}
			start = end + styledText.getLineDelimiter().length();
			end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());
			builder.endParagraph();
		}
		return builder.append("</html>").toString();
	}

	/**
	 * Obtain an HTML formatted text from the component contents. Set replacement range and value.
	 * 
	 * @param replacementRange
	 *            the replacement range
	 * @param replacementValue
	 *            the replacement value
	 * @param deletedCharacters
	 *            the deleted characters
	 * @return the formatted text
	 */
	public String getFormattedText(StyleRange replacementRange, String replacementValue, int deletedCharacters) {
		String plainText = styledText.getText();
		RichStringBuilder builder = new RichStringBuilder();
		builder.append("<html>");
		Integer[] lineBreaks = getLineBreaks();

		int brIdx = 0;
		int start = 0;
		int end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());

		boolean replaced = false;

		while (start <= end) {
			builder.startParagraph();
			StyleRange[] ranges = styledText.getStyleRanges(start, (end - start));
			if (ranges != null && ranges.length > 0) {
				for (StyleRange range : ranges) {
					if (!replaced && null != replacementRange && replacementRange.start < range.start + range.length) {
						if (range.start < replacementRange.start)
							addFormattedText(plainText.substring(range.start, replacementRange.start), builder, range);
						addFormattedText(replacementValue, builder, replacementRange);
						if (range.length > deletedCharacters) {
							String remainingString = plainText.substring(replacementRange.start + deletedCharacters,
									range.start + range.length);
							addFormattedText(remainingString, builder, range);
							// start += remainingString.length();
						}
						replaced = true;
					} else {
						addFormattedText(plainText.substring(range.start, range.start + range.length), builder, range);
					}
					start = (range.start + range.length);
				}
			}
			if (start < end) {
				if (!replaced && replacementRange.start > start && replacementRange.start < end) {
					builder.append(plainText.substring(start, replacementRange.start));
					addFormattedText(replacementValue, builder, replacementRange);
					if (replacementRange.start + replacementRange.length < end)
						builder.append(plainText.substring(replacementRange.start + replacementRange.length, end));
					replaced = true;
				} else
					builder.append(plainText.substring(start, end));
			} else if (0 == start && 0 == end)
				addFormattedText(replacementValue, builder, replacementRange);
			start = end + styledText.getLineDelimiter().length();
			end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());
			builder.endParagraph();
		}
		return builder.append("</html>").toString();
	}

	/**
	 * Sets the formatted text.
	 * 
	 * @param text
	 *            the new formatted text
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the sAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void setFormattedText(String text) throws ParserConfigurationException, SAXException, IOException {
		ExtraTextParser parser = ExtraTextParser.parse(text);
		int caret = styledText.getCaretOffset();
		styledText.setText(parser.getText());
		styledText.setStyleRanges(parser.getStyleRanges());
		try {
			styledText.setCaretOffset(caret);
		} catch (IllegalArgumentException e) { // Caret could be wrong when cancelling change
		}
	}

	public void setText(String text) {
		try {
			setFormattedText(text);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logError(e);
		}
	}

	/**
	 * Adds the formatted text.
	 * 
	 * @param text
	 *            the text
	 * @param builder
	 *            the builder
	 * @param styleRange
	 *            the style range
	 */
	private void addFormattedText(String text, RichStringBuilder builder, StyleRange styleRange) {
		List<ExtraTextResources.TagStyle> tags = builder.startTagStyles(styleRange);
		builder.append(text);
		builder.endTagStyles(tags.size());
	}

	/**
	 * Apply font style to selection.
	 * 
	 * @param tag
	 *            the style
	 */
	protected void applyTagStyleToSelection(ExtraTextResources.TagStyle tag) {
		Point sel = styledText.getSelectionRange();
		if ((sel == null) || (sel.y == 0)) {
			return;
		}

		StyleRange newStyle;
		for (int i = sel.x; i < (sel.x + sel.y); i++) {
			StyleRange range = styledText.getStyleRangeAtOffset(i);
			if (range != null) {
				newStyle = (StyleRange) range.clone();
				newStyle.start = i;
				newStyle.length = 1;
			} else {
				newStyle = new StyleRange(i, 1, null, null, SWT.NORMAL);
			}

			switch (tag) {
			case BOLD:
				newStyle.fontStyle ^= SWT.BOLD;
				break;
			case ITALIC:
				newStyle.fontStyle ^= SWT.ITALIC;
				break;
			case STRIKE_THROUGH:
				newStyle.strikeout = !newStyle.strikeout;
				break;
			case UNDERLINE:
				newStyle.underline = !newStyle.underline;
				break;
			default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
				break;
			}

			styledText.setStyleRange(newStyle);
			styledText.notifyListeners(SWT.Modify, null);
		}

		styledText.setSelectionRange(sel.x + sel.y, 0);
	}

	/**
	 * Clear all styled data.
	 */
	protected void clearStylesFromSelection() {
		Point sel = styledText.getSelectionRange();
		if ((sel != null) && (sel.y != 0)) {
			StyleRange style = new StyleRange(sel.x, sel.y, null, null, SWT.NORMAL);
			styledText.setStyleRange(style);
		}
		styledText.setSelectionRange(sel.x + sel.y, 0);
	}

	/**
	 * Gets the line breaks.
	 * 
	 * @return the line breaks
	 */
	private Integer[] getLineBreaks() {
		String text = styledText.getText();
		List<Integer> list = new ArrayList<Integer>();
		int lastIdx = 0;
		String lineDelimiter = styledText.getLineDelimiter();
		int lineDelimiterLength = lineDelimiter.length();
		while (true) {
			int br = text.indexOf(lineDelimiter, lastIdx);
			if (-1 != br)
				list.add(br);
			else
				break;
			lastIdx = br + lineDelimiterLength;
		}
		Collections.sort(list);
		return list.toArray(new Integer[list.size()]);
	}

	/**
	 * Handle cut copy.
	 */
	protected void handleCutCopy() {
		// Save the cut/copied style info so that during paste we will maintain the style information. Cut/copied text is put in the
		// clipboard in RTF format, but is not pasted in RTF format. The other way to handle the pasting of styles would be to
		// access the Clipboard directly and parse the RTF text.
		cachedStyles = Collections.synchronizedList(new LinkedList<StyleRange>());
		Point sel = styledText.getSelectionRange();
		int startX = sel.x;
		for (int i = sel.x; i <= sel.x + sel.y - 1; i++) {
			StyleRange style = styledText.getStyleRangeAtOffset(i);
			if (style != null) {
				style.start = style.start - startX;
				if (!cachedStyles.isEmpty()) {
					StyleRange lastStyle = cachedStyles.get(cachedStyles.size() - 1);
					if (lastStyle.similarTo(style) && lastStyle.start + lastStyle.length == style.start) {
						lastStyle.length++;
					} else {
						cachedStyles.add(style);
					}
				} else {
					cachedStyles.add(style);
				}
			}
		}
		pasteItem.setEnabled(true);
	}

	/**
	 * Handle extended modified.
	 * 
	 * @param event
	 *            the event
	 */
	private void handleExtendedModified(ExtendedModifyEvent event) {
		if (event.length == 0)
			return;

		StyleRange style;
		if (event.length == 1 || styledText.getTextRange(event.start, event.length).equals(styledText.getLineDelimiter())) {
			// Have the new text take on the style of the text to its right (during typing) if no style information is active.
			int caretOffset = styledText.getCaretOffset();
			style = null;
			if (caretOffset < styledText.getCharCount())
				style = styledText.getStyleRangeAtOffset(caretOffset);
			if (style != null) {
				style = (StyleRange) style.clone();
				style.start = event.start;
				style.length = event.length;
			} else {
				style = new StyleRange(event.start, 1, null, null, SWT.NORMAL);
			}
			if (boldItem.getSelection())
				style.fontStyle |= SWT.BOLD;
			if (italicItem.getSelection())
				style.fontStyle |= SWT.ITALIC;
			style.strikeout = strikeThroughItem.getSelection();
			if (SWT.UNDERLINE_LINK != style.underlineStyle)
				style.underline = underlineItem.getSelection();
			if (!style.isUnstyled())
				styledText.setStyleRange(style);
		} else {
			// paste occurring, have text take on the styles it had when it was cut/copied
			for (int i = 0; i < cachedStyles.size(); i++) {
				style = cachedStyles.get(i);
				StyleRange newStyle = (StyleRange) style.clone();
				newStyle.start = style.start + event.start;
				styledText.setStyleRange(newStyle);
			}
		}
	}

	/**
	 * Handle text selected.
	 * 
	 * @param event
	 *            the event
	 */
	private void handleTextSelected() {
		Point sel = styledText.getSelectionRange();
		if ((sel != null) && (sel.y != 0)) {
			StyleRange[] styles = styledText.getStyleRanges(sel.x, sel.y);
			boolean enabled = styles.length <= 1;
			if (enabled) {
				String selectedText = styledText.getSelectionText();
				enabled = selectedText.equals(selectedText.trim());
			}
			hyperlinkItem.setEnabled(enabled);
		} else {
			hyperlinkItem.setEnabled(false);
		}
	}

	/**
	 * Handle key released.
	 * 
	 * @param event
	 *            the event
	 */
	private void handleKeyReleased(KeyEvent event) {
		if ((event.keyCode == SWT.ARROW_LEFT) || (event.keyCode == SWT.ARROW_UP) || (event.keyCode == SWT.ARROW_RIGHT)
				|| (event.keyCode == SWT.ARROW_DOWN)) {
			updateStyleButtons();
		}
	}

	/**
	 * Update style buttons.
	 */
	private void updateStyleButtons() {
		Point sel = styledText.getSelectionRange();
		if (sel != null && sel.y > 0) {
			StyleRange[] styles = styledText.getStyleRanges(sel.x, sel.y);
			if (styles != null) {
				if (1 == styles.length)
					enableFontItems(styles[0]);
				else
					disableFontItems();
				return;
			}
		}

		StyleRange style = null;
		int caretOffset = styledText.getCaretOffset();
		if (caretOffset >= 0 && caretOffset < styledText.getCharCount()) {
			style = styledText.getStyleRangeAtOffset(caretOffset);
		}

		if (style != null) {
			enableFontItems(style);
		} else {
			disableFontItems();
		}
	}

	/**
	 * Enable font items.
	 * 
	 * @param style
	 *            the style
	 */
	private void enableFontItems(StyleRange style) {
		boldItem.setSelection((style.fontStyle & SWT.BOLD) != 0);
		italicItem.setSelection((style.fontStyle & SWT.ITALIC) != 0);
		underlineItem.setSelection(style.underline && style.underlineStyle != SWT.UNDERLINE_LINK);
		strikeThroughItem.setSelection(style.strikeout);
		hyperlinkItem.setSelection(true);
	}

	/**
	 * Disable font items.
	 */
	private void disableFontItems() {
		boldItem.setSelection(false);
		italicItem.setSelection(false);
		underlineItem.setSelection(false);
		strikeThroughItem.setSelection(false);
		hyperlinkItem.setSelection(false);
	}

	/**
	 * Gets the hyperlink.
	 * 
	 * @param event
	 *            the event
	 * @return the hyperlink
	 */
	private String getHyperlink(MouseEvent event) {
		try {
			int offset = styledText.getOffsetAtPoint(new Point(event.x, event.y));
			StyleRange styleRange = styledText.getStyleRangeAtOffset(offset);
			if (styleRange != null && styleRange.underline && styleRange.underlineStyle == SWT.UNDERLINE_LINK)
				return (String) styleRange.data;
		} catch (IllegalArgumentException e) { // Invalid offset location - return null
		}
		return null;
	}

	/**
	 * Link activated.
	 * 
	 * @param href
	 *            the href
	 */
	protected void linkActivated(String href) {
		if (null != href) {
			Desktop desktop = Desktop.getDesktop();
			if (href.startsWith("mailto://"))
				try {
					desktop.mail(new URI(href));
				} catch (IOException | URISyntaxException e) {
					displayError(getFromEngineBundle("error.encounteredError"),
							getFromEngineBundle("extraText.invalid.mail", href));
				}
			else {
				desktopBrowse(href, href, desktop);
			}
		}
	}

	private void desktopBrowse(String href, String initialHref, Desktop desktop) {
		try {
			URL url = new URL(href);
			desktop.browse(url.toURI());
		} catch (IOException | URISyntaxException e) {
			if (!href.startsWith("http://") && !href.startsWith("https://"))
				desktopBrowse("http://".concat(href), href, desktop);
			else
				displayError(getFromEngineBundle("error.encounteredError"),
						getFromEngineBundle("extraText.invalid.url", initialHref));
		}
	}

	/**
	 * Creates the tool bar.
	 * 
	 * @param parent
	 *            the parent
	 * @return the tool bar
	 */
	protected void createToolBar(final Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		toolBar = new ToolBar(parent, SWT.FLAT | SWT.BORDER);
		boldItem = new ToolItem(toolBar, SWT.CHECK);
		boldItem.setImage(toolkit.getRegisteredImage("IMG_BOLD"));
		boldItem.setToolTipText(ExtraTextResources.TOOLTIP_BOLD);
		boldItem.addSelectionListener(new TagStyleButtonListener(ExtraTextResources.TagStyle.BOLD));

		italicItem = new ToolItem(toolBar, SWT.CHECK);
		italicItem.setImage(toolkit.getRegisteredImage("IMG_ITALIC"));
		italicItem.setToolTipText(ExtraTextResources.TOOLTIP_ITALIC);
		italicItem.addSelectionListener(new TagStyleButtonListener(ExtraTextResources.TagStyle.ITALIC));

		underlineItem = new ToolItem(toolBar, SWT.CHECK);
		underlineItem.setImage(toolkit.getRegisteredImage("IMG_UNDERLINE"));
		underlineItem.setToolTipText(ExtraTextResources.TOOLTIP_UNDERLINE);
		underlineItem.addSelectionListener(new TagStyleButtonListener(ExtraTextResources.TagStyle.UNDERLINE));

		strikeThroughItem = new ToolItem(toolBar, SWT.CHECK);
		strikeThroughItem.setImage(toolkit.getRegisteredImage("IMG_STRIKE"));
		strikeThroughItem.setToolTipText(ExtraTextResources.TOOLTIP_STRIKE_THROUGH);
		strikeThroughItem.addSelectionListener(new TagStyleButtonListener(ExtraTextResources.TagStyle.STRIKE_THROUGH));

		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem cutItem = new ToolItem(toolBar, SWT.PUSH);
		cutItem.setImage(toolkit.getRegisteredImage("IMG_CUT"));
		cutItem.setToolTipText(ExtraTextResources.TOOLTIP_CUT);
		cutItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleCutCopy();
				styledText.cut();
			}
		});

		ToolItem copyItem = new ToolItem(toolBar, SWT.PUSH);
		copyItem.setImage(toolkit.getRegisteredImage("IMG_COPY"));
		copyItem.setToolTipText(ExtraTextResources.TOOLTIP_COPY);
		copyItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleCutCopy();
				styledText.copy();
			}
		});

		pasteItem = new ToolItem(toolBar, SWT.PUSH);
		pasteItem.setEnabled(false);
		pasteItem.setImage(toolkit.getRegisteredImage("IMG_PASTE"));
		pasteItem.setToolTipText(ExtraTextResources.TOOLTIP_PASTE);
		pasteItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.paste();
			}
		});

		new ToolItem(toolBar, SWT.SEPARATOR);

		hyperlinkItem = new ToolItem(toolBar, SWT.PUSH);
		hyperlinkItem.setImage(toolkit.getRegisteredImage("IMG_HYPERLINK"));
		hyperlinkItem.setToolTipText(ExtraTextResources.TOOLTIP_HYPERLINK);
		hyperlinkItem.setEnabled(false);
		hyperlinkItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StyleRange styleRange = null;
				Point selectionRange;
				if (0 == styledText.getCharCount())
					selectionRange = new Point(0, 0);
				else {
					selectionRange = styledText.getSelectionRange();
					if (selectionRange != null)
						if (selectionRange.y == 0) {
							styleRange = styledText.getStyleRangeAtOffset(selectionRange.x);
						} else {
							StyleRange[] styles = styledText.getStyleRanges(selectionRange.x, selectionRange.y);
							if (styles != null && styles.length == 1)
								styleRange = styles[0];
						}
				}
				String text = null;
				String href = null;
				if (null != styleRange) {
					if (SWT.UNDERLINE_LINK == styleRange.underlineStyle) {
						int[] ranges = styledText.getRanges();
						int index = 0;
						while (index < ranges.length) {
							int start = ranges[index++];
							int length = ranges[index++];
							if (selectionRange.x >= start && selectionRange.x < start + length) {
								styledText.setSelectionRange(start, length);
								styleRange.start = start;
								styleRange.length = length;
								break;
							}
						}
					}
					href = (String) styleRange.data;
				}
				text = styledText.getSelectionText();
				if (null == styleRange) {
					styleRange = new StyleRange();
					styleRange.start = selectionRange.x;
					styleRange.length = selectionRange.y;
				}
				new HyperlinkDialog(parent.getShell(), toolkit, ExtraText.this, text, href, styleRange).open();
			}
		});
	}

	/**
	 * The listener interface for receiving fontStyleButton events. The class that is interested in processing a fontStyleButton
	 * event implements this interface, and the object created with that class is registered with a component using the component's
	 * <code>addFontStyleButtonListener<code> method. When the fontStyleButton event occurs, that object's appropriate method is
	 * invoked.
	 * 
	 * @see FontStyleButtonEvent
	 */
	private class TagStyleButtonListener extends SelectionAdapter {

		/** The style. */
		private ExtraTextResources.TagStyle tag;

		/**
		 * Instantiates a new font style button listener.
		 * 
		 * @param tag
		 *            the style
		 */
		public TagStyleButtonListener(ExtraTextResources.TagStyle tag) {
			this.tag = tag;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			applyTagStyleToSelection(tag);
		}
	}

	/**
	 * Gets the tool bar.
	 * 
	 * @return the tool bar
	 */
	public ToolBar getToolBar() {
		return toolBar;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		// Minimum size is 64 x 64.
		return super.computeSize(Math.max(wHint, 64), Math.max(hHint, 64), changed);
	}
}