package org.adichatz.studio.util;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.graphics.Image;

public class GenerationLabelProvider extends ColumnLabelProvider {
	private ColumnLabelProvider columnLabelProvider;

	public GenerationLabelProvider(IBaseLabelProvider columnLabelProvider) {
		this.columnLabelProvider = (ColumnLabelProvider) columnLabelProvider;
	}

	@Override
	public String getText(Object element) {
		return columnLabelProvider.getText(((ScenarioTreeElement) element).getTreeElement());
	}

	@Override
	public Image getImage(Object element) {
		return columnLabelProvider.getImage(((ScenarioTreeElement) element).getTreeElement());
	}
}
