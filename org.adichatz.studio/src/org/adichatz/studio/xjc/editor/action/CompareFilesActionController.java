package org.adichatz.studio.xjc.editor.action;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.studio.command.CompareCustomGeneratedVersionHandler;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;

public class CompareFilesActionController extends ActionController {

	public CompareFilesActionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		action = new AAction() {
			@Override
			public void runAction() {
				XjcTreeFormEditor editor = (XjcTreeFormEditor) getRootCore().getController();
				new CompareCustomGeneratedVersionHandler().compare(editor.getEditorInput().getFile());
			}
		};
		super.createControl();
		//		((ManagedToolBarController) parentController).getToolBarManager().add(action);
		action.setActionController(this);
	}
}
