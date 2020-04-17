package org.adichatz.engine.viewer;

import org.eclipse.jface.viewers.LabelProvider;

public class EnumLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (null == element)
			return null;
		StringBuffer textSB = new StringBuffer(String.valueOf(element).toLowerCase());
		int index = textSB.lastIndexOf("_");
		while (-1 != index) {
			textSB.deleteCharAt(index);
			String upper = String.valueOf(textSB.charAt(index)).toUpperCase();
			textSB.replace(index, index + 1, upper);
			index = textSB.lastIndexOf("_");
		}
		return textSB.toString();
	}
}
