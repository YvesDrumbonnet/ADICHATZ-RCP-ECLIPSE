package org.adichatz.engine.renderer;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class GridRendererManager {
	/** The odd. */
	protected boolean odd = true;

	private Grid grid;

	public GridRendererManager(Grid grid) {
		this.grid = grid;
	}

	public void postRefreshRendering(Color evenBackground, Color evenForeground, Font evenFont, Color oddBackground,
			Color oddForeground, Font oddFont) {
		Color defaultBackground = grid.getBackground();
		Color defaultForeground = grid.getForeground();
		Font defaultFont = grid.getFont();
		int columnCount = grid.getColumnCount();
		for (GridItem gridItem : grid.getItems()) {
			for (int i = 0; i < columnCount; i++) {
				if (odd) {
					setBackground(defaultBackground, oddBackground, gridItem, i);
					setForeground(defaultForeground, oddForeground, gridItem, i);
					setFont(defaultFont, oddFont, gridItem, i);
				} else {
					setBackground(defaultBackground, evenBackground, gridItem, i);
					setForeground(defaultForeground, evenForeground, gridItem, i);
					setFont(defaultFont, evenFont, gridItem, i);
				}
			}
			odd = !odd;
		}
	}

	private void setBackground(Color defaultColor, Color newColor, GridItem gridItem, int i) {
		Color currentColor = gridItem.getBackground(i);
		if (null == currentColor || currentColor.equals(defaultColor))
			gridItem.setBackground(i, newColor);
	}

	private void setForeground(Color defaultColor, Color newColor, GridItem gridItem, int i) {
		Color currentColor = gridItem.getForeground(i);
		if (null == currentColor || currentColor.equals(defaultColor))
			gridItem.setForeground(i, newColor);
	}

	private void setFont(Font defaultFont, Font newFont, GridItem gridItem, int i) {
		Font currentFont = gridItem.getFont(i);
		if (null == currentFont || currentFont.equals(defaultFont))
			gridItem.setFont(i, newFont);
	}
}
