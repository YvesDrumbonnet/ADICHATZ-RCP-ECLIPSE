package org.adichatz.studio.xjc.scenario;

import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.PluginEntity;

public abstract class AXjcEntityMetaModel<T> extends AEntityMetaModel<T> {

	public AXjcEntityMetaModel(PluginEntity pluginEntity) {
		super(pluginEntity);
	}

	protected void addSuperFields() {
	}
}
