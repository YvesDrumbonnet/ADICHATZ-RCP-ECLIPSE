package org.adichatz.engine.renderer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.eclipse.nebula.widgets.pshelf.AbstractRenderer;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IFormColors;

public class AdiPShelfRenderer extends AbstractRenderer {
	private int textMargin = 2;

	private int margin = 4;

	private PShelf parent;

	private int spacing = 8;

	private Font font;

	private Font selectedFont;

	private Color gradient1;

	private Color gradient2;

	private Color selectedGradient1;

	private Color selectedGradient2;

	private Color hoverGradient1;

	private Color hoverGradient2;

	private Color lineColor;

	private Color selectedForeground;

	private Color hoverForeground;

	private Color foreground;

	public AdiPShelfRenderer() {
	}

	/**
	 * {@inheritDoc}
	 */
	public Point computeSize(GC gc, int wHint, int hHint, Object value) {
		PShelfItem item = (PShelfItem) value;

		int h = 0;

		gc.setFont(font);

		if (item.getImage() == null) {
			h = gc.getFontMetrics().getHeight() + (2 * (textMargin));
		} else {
			h = Math.max(item.getImage().getBounds().height, gc.getFontMetrics().getHeight() + (2 * textMargin));
		}

		gc.setFont(selectedFont);

		h = Math.max(h, gc.getFontMetrics().getHeight() + (2 * textMargin));

		h += 2 * margin;

		if (h % 2 != 0)
			h++;

		return new Point(wHint, h);
	}

	/**
	 * {@inheritDoc}
	 */
	public void paint(GC gc, Object value) {
		PShelfItem item = (PShelfItem) value;

		// Color back = parent.getBackground();
		Color fore = parent.getForeground();

		if (isSelected()) {
			gc.setForeground(selectedGradient1);
			gc.setBackground(selectedGradient2);
		} else {
			if (isHover()) {
				gc.setForeground(hoverGradient1);
				gc.setBackground(hoverGradient2);
			} else {
				gc.setForeground(gradient1);
				gc.setBackground(gradient2);
			}
		}

		gc.fillGradientRectangle(getBounds().x, getBounds().y, getBounds().width, getBounds().height, true);

		if ((parent.getStyle() & SWT.SIMPLE) != 0) {
			if (!isSelected()) {
				gc.setForeground(lineColor);
				gc.drawLine(0, getBounds().y, getBounds().width - 1, getBounds().y);
			}
		} else {
			if (parent.getItems()[0] != item) {
				gc.setForeground(lineColor);
				gc.drawLine(0, getBounds().y, getBounds().width - 1, getBounds().y);
			}

			if (isSelected()) {
				gc.setForeground(lineColor);
				gc.drawLine(0, getBounds().y + getBounds().height - 1, getBounds().width - 1,
						getBounds().y + getBounds().height - 1);
			}
		}

		boolean imageLeft = true;

		if ((parent.getStyle() & SWT.SIMPLE) != 0) {
			imageLeft = !isSelected();
		}

		int x = 6;
		if (item.getImage() != null && imageLeft) {
			int y2 = (getBounds().height - item.getImage().getBounds().height) / 2;
			if ((getBounds().height - item.getImage().getBounds().height) % 2 != 0)
				y2++;

			gc.drawImage(item.getImage(), x, getBounds().y + y2);

			x += item.getImage().getBounds().width + spacing;
		}

		if (isSelected()) {
			gc.setFont(selectedFont);
			gc.setForeground(selectedForeground != null ? selectedForeground : fore);
		} else if (isHover()) {
			gc.setFont(font);
			gc.setForeground(hoverForeground != null ? hoverForeground : fore);
		} else {
			gc.setFont(font);
			gc.setForeground(foreground != null ? foreground : fore);
		}

		int y2 = (getBounds().height - gc.getFontMetrics().getHeight()) / 2;
		if ((getBounds().height - gc.getFontMetrics().getHeight()) % 2 != 0)
			y2++;

		int textWidth = getBounds().width - 12;
		if (item.getImage() != null) {
			textWidth -= item.getImage().getBounds().width;
			textWidth -= 6;
		}

		gc.drawString(getShortString(gc, item.getText(), textWidth), x, getBounds().y + y2, true);

		if (item.getImage() != null && !imageLeft) {
			int y3 = (getBounds().height - item.getImage().getBounds().height) / 2;
			if ((getBounds().height - item.getImage().getBounds().height) % 2 != 0)
				y3++;

			gc.drawImage(item.getImage(), getBounds().width - 6 - item.getImage().getBounds().width, getBounds().y + y3);
		}

		if (isFocus()) {
			gc.drawFocus(1, 1, getBounds().width - 2, getBounds().height - 1);
		}
	}

	public void initialize(Control control) {
		this.parent = (PShelf) control;

		font = selectedFont = EngineTools.getModifiedFont(parent.getFont(), SWT.BOLD);
		initializeColors();
	}

	public void initializeColors() {
		AReskinManager reskinManager = AReskinManager.getInstance();
		if (null == reskinManager) {
			AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
			gradient1 = toolkit.getColors().getColor(IFormColors.H_GRADIENT_START);
			gradient2 = toolkit.getColors().getColor(IFormColors.H_GRADIENT_END);
			selectedGradient1 = toolkit.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE2);
			selectedGradient2 = toolkit.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE1);
			hoverGradient1 = toolkit.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE1); // Dark Blue grey
			hoverGradient2 = toolkit.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE2);
			lineColor = toolkit.getColors().getColor(IFormColors.TB_BG);
			foreground = toolkit.getColors().getColor(IFormColors.TITLE);
			hoverForeground = foreground;
		} else {
			gradient1 = reskinManager.getColor("PShelf", "pshelf-unselectedcolor-start", null, null);
			gradient2 = reskinManager.getColor("PShelf", "pshelf-unselectedcolor-end", null, null);
			selectedGradient1 = reskinManager.getColor("PShelf", "pshelf-selectedcolor-start", null, null);
			selectedGradient2 = reskinManager.getColor("PShelf", "pshelf-selectedcolor-end", null, null);
			hoverGradient1 = reskinManager.getColor("PShelf", "pshelf-hovercolor-start", null, null);
			hoverGradient2 = reskinManager.getColor("PShelf", "pshelf-hovercolor-end", null, null);
			lineColor = reskinManager.getColor("PShelf", "pshelf-line-color", null, null);
			foreground = reskinManager.getColor("PShelf", "color", null, null);
			hoverForeground = reskinManager.getColor("PShelf", "hover-color", null, null);
		}
		selectedForeground = hoverForeground;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getGradient1() {
		return gradient1;
	}

	public void setGradient1(Color gradient1) {
		this.gradient1 = gradient1;
	}

	public Color getGradient2() {
		return gradient2;
	}

	public void setGradient2(Color gradient2) {
		this.gradient2 = gradient2;
	}

	public Color getHoverGradient1() {
		return hoverGradient1;
	}

	public void setHoverGradient1(Color hoverGradient1) {
		this.hoverGradient1 = hoverGradient1;
	}

	public Color getHoverGradient2() {
		return hoverGradient2;
	}

	public void setHoverGradient2(Color hoverGradient2) {
		this.hoverGradient2 = hoverGradient2;
	}

	public Font getSelectedFont() {
		return selectedFont;
	}

	public void setSelectedFont(Font selectedFont) {
		this.selectedFont = selectedFont;
	}

	public Color getSelectedForeground() {
		return selectedForeground;
	}

	/**
	 * Sets text color for the selected item.
	 * 
	 * @param selectedForeground
	 *            Can be <code>null</code>, foreground color of the parent is used in that case.
	 */
	public void setSelectedForeground(Color selectedForeground) {
		this.selectedForeground = selectedForeground;
	}

	public Color getHoverForeground() {
		return hoverForeground;
	}

	/**
	 * Sets text color for the hovered item.
	 * 
	 * @param hoverForeground
	 *            Can be <code>null</code>, foreground color of the parent is used in that case.
	 */
	public void setHoverForeground(Color hoverForeground) {
		this.hoverForeground = hoverForeground;
	}

	public Color getForeground() {
		return foreground;
	}

	/**
	 * Sets text color for non-selected items.
	 * 
	 * @param foreground
	 *            Can be <code>null</code>, foreground color of the parent is used in that case.
	 */
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Color getSelectedGradient1() {
		return selectedGradient1;
	}

	public void setSelectedGradient1(Color selectedGradient1) {
		this.selectedGradient1 = selectedGradient1;
	}

	public Color getSelectedGradient2() {
		return selectedGradient2;
	}

	public void setSelectedGradient2(Color selectedGradient2) {
		this.selectedGradient2 = selectedGradient2;
	}

	private static String getShortString(GC gc, String t, int width) {

		if (t == null) {
			return null;
		}

		if (t.equals("")) {
			return "";
		}

		if (width >= gc.stringExtent(t).x) {
			return t;
		}

		int w = gc.stringExtent("...").x;
		String text = t;
		int l = text.length();
		int pivot = l / 2;
		int s = pivot;
		int e = pivot + 1;
		while (s >= 0 && e < l) {
			String s1 = text.substring(0, s);
			String s2 = text.substring(e, l);
			int l1 = gc.stringExtent(s1).x;
			int l2 = gc.stringExtent(s2).x;
			if (l1 + w + l2 < width) {
				text = s1 + "..." + s2;
				break;
			}
			s--;
			e++;
		}

		if (s == 0 || e == l) {
			text = text.substring(0, 1) + "..." + text.substring(l - 1, l);
		}

		return text;
	}

}
