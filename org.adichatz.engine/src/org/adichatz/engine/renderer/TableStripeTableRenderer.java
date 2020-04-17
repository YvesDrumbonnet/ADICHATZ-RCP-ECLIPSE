package org.adichatz.engine.renderer;

import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.TableController;

public class TableStripeTableRenderer<T> extends StripeTableRenderer<T> {

	private TableController<T> tableController;

	public TableStripeTableRenderer(ATabularController<T> tabularController, String key) {
		super(tabularController, key);
		this.tableController = (TableController<T>) tabularController;
	}

	public void postRefreshRendering() {
		new TableRendererManager(tableController.getControl()).postRefreshRendering(evenBackground, evenForeground, evenFont,
				oddBackground, oddForeground, oddFont);
	}
}
