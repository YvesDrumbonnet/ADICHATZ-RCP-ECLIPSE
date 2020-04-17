package org.adichatz.engine.renderer;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableRendererManager {
	/** The odd. */
	protected boolean odd = true;

	private Table table;

	public TableRendererManager(Table table) {
		this.table = table;
	}

	public void postRefreshRendering(Color evenBackground, Color evenForeground, Font evenFont, Color oddBackground,
			Color oddForeground, Font oddFont) {
		Color defaultBackground = table.getBackground();
		Color defaultForeground = table.getForeground();
		Font defaultFont = table.getFont();
		int columnCount = table.getColumnCount();
		for (TableItem tableItem : table.getItems()) {
			for (int i = 0; i < columnCount; i++) {
				if (odd) {
					setBackground(defaultBackground, oddBackground, tableItem, i);
					setForeground(defaultForeground, oddForeground, tableItem, i);
					setFont(defaultFont, oddFont, tableItem, i);
				} else {
					setBackground(defaultBackground, evenBackground, tableItem, i);
					setForeground(defaultForeground, evenForeground, tableItem, i);
					setFont(defaultFont, evenFont, tableItem, i);
				}
			}
			odd = !odd;
		}
	}

	private void setBackground(Color defaultColor, Color newColor, TableItem tableItem, int i) {
		Color currentColor = tableItem.getBackground(i);
		if (null == currentColor || currentColor.equals(defaultColor))
			tableItem.setBackground(i, newColor);
	}

	private void setForeground(Color defaultColor, Color newColor, TableItem tableItem, int i) {
		Color currentColor = tableItem.getForeground(i);
		if (null == currentColor || currentColor.equals(defaultColor))
			tableItem.setForeground(i, newColor);
	}

	private void setFont(Font defaultFont, Font newFont, TableItem tableItem, int i) {
		Font currentFont = tableItem.getFont(i);
		if (null == currentFont || currentFont.equals(defaultFont))
			tableItem.setFont(i, newFont);
	}
}
