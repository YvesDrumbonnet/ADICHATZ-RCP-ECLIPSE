package org.adichatz.engine.controller.nebula;

import org.adichatz.engine.controller.IActionController;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.nebula.widgets.pgroup.PGroupToolItem;

public abstract class APGroupToolItem extends PGroupToolItem {

	protected IActionController pgroupToolItemController;

	public APGroupToolItem(PGroup parent, int style) {
		super(parent, style);
	}

	/**
	 * Gets the from param map.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the from param map
	 */
	protected Object getParam(String key) {
		return pgroupToolItemController.getParamMap().get(key);
	}

	public IActionController getActionController() {
		return pgroupToolItemController;
	}

	public void setActionController(IActionController pgroupToolItemController) {
		this.pgroupToolItemController = pgroupToolItemController;
	}

	/**
	 * Post create.
	 */
	public void postCreate() {
	}

}
