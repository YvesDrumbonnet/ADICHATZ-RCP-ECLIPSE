package org.adichatz.studio.util;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.studio.xjc.data.XjcTreeElement;

public class ScenarioTreeElement {
	private XjcTreeElement treeElement;

	private ScenarioTreeElement parentElement;

	private List<ScenarioTreeElement> children = new ArrayList<ScenarioTreeElement>();

	public ScenarioTreeElement(ScenarioTreeElement currentParentElement, XjcTreeElement treeElement) {
		this.treeElement = treeElement;
		if (null != currentParentElement)
			currentParentElement.children.add(this);
		else
			while (null != currentParentElement) {
				if (currentParentElement.treeElement.equals(treeElement.getParentElement())) {
					parentElement = currentParentElement;
					parentElement.children.add(this);
					break;
				}
				currentParentElement = currentParentElement.parentElement;
			}
	}

	public XjcTreeElement getTreeElement() {
		return treeElement;
	}

	public List<ScenarioTreeElement> getChildren() {
		return children;
	}

	public Object getBean() {
		return treeElement.getEntity().getBean();
	}

}
