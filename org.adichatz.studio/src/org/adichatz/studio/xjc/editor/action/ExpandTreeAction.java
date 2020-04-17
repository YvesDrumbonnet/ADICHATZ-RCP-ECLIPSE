package org.adichatz.studio.xjc.editor.action;

import org.adichatz.engine.action.AAction;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.eclipse.jface.viewers.TreeViewer;

public class ExpandTreeAction extends AAction {

	@Override
	public void runAction() {
		XjcTreeController treeController = (XjcTreeController) actionController.getGenCode().getFromRegister("xjcTree");
		((TreeViewer) treeController.getViewer()).expandAll();
	}
}
