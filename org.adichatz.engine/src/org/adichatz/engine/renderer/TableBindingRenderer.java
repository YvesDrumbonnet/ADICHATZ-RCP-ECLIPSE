package org.adichatz.engine.renderer;

import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.collection.ATabularController;

public class TableBindingRenderer<T> extends BindingTableRenderer<T> {

	private TableController<T> tableController;

	public TableBindingRenderer(ATabularController<T> tabularController, String key) {
		super(tabularController, key);
		this.tableController = (TableController<T>) tabularController;
	}

	public void postRefreshRendering() {
		new TableRendererManager(tableController.getControl()).postRefreshRendering(evenBackground, evenForeground, evenFont,
				oddBackground, oddForeground, oddFont);
	}
}
