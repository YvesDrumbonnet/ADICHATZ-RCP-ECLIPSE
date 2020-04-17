package org.adichatz.studio.xjc.controller;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.studio.db4o.Db4oTools;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.editor.InternalTreeFormEditor;

public class XjcTextController extends ProposalTextController {
	private List<String> initialProposals;;

	public XjcTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	protected List<?> getProposals() {
		if (null == initialProposals) {
			initialProposals = new ArrayList<String>();
			if (!(((XjcBindingService) getBindingService()).getEditor() instanceof InternalTreeFormEditor)) {
				String elementId = getEntity().getEntityMM().getEntityId();
				String propertyId = getRegisterId();
				initialProposals = Db4oTools.getInstance().getProposals(null, elementId, propertyId);
			}
		}
		return initialProposals;
	}
}
