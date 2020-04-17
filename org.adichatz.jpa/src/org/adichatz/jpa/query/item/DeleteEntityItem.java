package org.adichatz.jpa.query.item;

import org.adichatz.engine.controller.nebula.AActionPGroupToolItem;
import org.adichatz.jpa.action.DeleteEntityAction;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DeleteEntityItem extends AActionPGroupToolItem {

	public DeleteEntityItem(PGroup parent, int style) {
		super(parent, style);

		action = new DeleteEntityAction() {
			@Override
			public void init() {
			}
		};

		addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				action.runAction();
			}
		});
	}

}
