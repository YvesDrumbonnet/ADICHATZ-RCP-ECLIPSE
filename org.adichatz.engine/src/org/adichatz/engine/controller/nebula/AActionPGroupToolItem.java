package org.adichatz.engine.controller.nebula;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.controller.IActionController;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public abstract class AActionPGroupToolItem extends APGroupToolItem {

	protected AAction action;

	public AActionPGroupToolItem(PGroup parent, int style) {
		super(parent, style);
		addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.runAction();
			}
		});
	}

	public void setActionController(IActionController pgroupToolItemController) {
		super.setActionController(pgroupToolItemController);
		action.setActionController(pgroupToolItemController);
	}

}
