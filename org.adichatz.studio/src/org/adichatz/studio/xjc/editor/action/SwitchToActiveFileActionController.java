package org.adichatz.studio.xjc.editor.action;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.collection.ManagedToolBarController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.studio.command.SwitchToActiveFileHandler;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;
import org.eclipse.jface.viewers.StructuredSelection;

public class SwitchToActiveFileActionController extends ActionController {

	public SwitchToActiveFileActionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		action = new AAction() {
			@Override
			public void runAction() {
				XjcTreeFormEditor editor = (XjcTreeFormEditor) getRootCore().getController();
				new SwitchToActiveFileHandler().switchFile(new StructuredSelection(editor.getEditorInput().getFile()));
			}
		};
		((ManagedToolBarController) parentController).getToolBarManager().add(action);
		action.setActionController(this);
	}
}
