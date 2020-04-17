package org.adichatz.engine.renderer;

import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.nebula.GridController;

public class GridStripeTableRenderer<T> extends StripeTableRenderer<T> {

	private GridController<T> gridController;

	public GridStripeTableRenderer(ATabularController<T> tabularController, String key) {
		super(tabularController, key);
		this.gridController = (GridController<T>) tabularController;
	}

	public void postRefreshRendering() {
		new GridRendererManager(gridController.getControl()).postRefreshRendering(evenBackground, evenForeground, evenFont,
				oddBackground, oddForeground, oddFont);
	}

}
