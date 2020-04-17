package org.adichatz.tool;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.core.AMenuCore;
import org.eclipse.swt.graphics.Font;

public abstract class ToolItemController extends ItemController {

	private boolean updateable;

	public ToolItemController(AdiPluginResources pluginResources, AMenuCore menuCore, String nodeId, String label,
			String toolTipText, boolean updateable) {
		super(pluginResources, menuCore, nodeId, label, toolTipText);
		this.updateable = updateable;
	}

	@Override
	public Font getFont() {
		return updateable ? null : ToolNavigatorContent.ITALIC_FONT;
	}
}
