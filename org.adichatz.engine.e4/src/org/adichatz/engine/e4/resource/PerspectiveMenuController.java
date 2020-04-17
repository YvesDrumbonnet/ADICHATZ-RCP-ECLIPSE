package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.menu.ANodeController;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.core.AMenuCore;
import org.adichatz.engine.e4.handler.PerspectiveResetHandler;
import org.adichatz.engine.e4.handler.PerspectiveSwitchHandler;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.graphics.Image;

public class PerspectiveMenuController extends MenuController {
	private List<ANodeController> children;

	public PerspectiveMenuController(AdiPluginResources pluginResources, final AMenuCore menuCore, String nodeId, String label,
			String toolTipText) {
		super(pluginResources, menuCore, nodeId, label, toolTipText);
	}

	@Override
	public List<ANodeController> getChildren() {
		ItemController itemController;
		if (null == children) {
			IEclipseContext context = E4AdichatzApplication.getInstance().getContext();
			final MApplication application = context.get(MApplication.class);
			children = new ArrayList<>(4);
			itemController = new ItemController(getPluginResources(), getMenuCore(), "#perspective.reset#", getFromEngineE4Bundle(
					"menu.perspective.reset", E4AdichatzApplication.getInstance().getCurrentPerspective().getLabel()), null) {
				@Override
				public void handleActivate() {
					new PerspectiveResetHandler().execute(application);
				}

				@Override
				public Image getImage() {
					return AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_E4_BUNDLE,
							"IMG_PERSPECTIVE_RESET.png");
				}
			};
			children.add(itemController);
			if (1 < E4AdichatzApplication.getInstance().getPerspectiveStack().getChildren().size())
				for (final MPerspective perspective : E4AdichatzApplication.getInstance().getPerspectiveStack().getChildren()) {
					if (!perspective.equals(E4AdichatzApplication.getInstance().getCurrentPerspective())) {
						itemController = new ItemController(getPluginResources(), getMenuCore(),
								getFromEngineE4Bundle("menu.perspective.Switch", perspective.getLocalizedLabel()),
								perspective.getLabel(), null) {
							@Override
							public void handleActivate() {
								new PerspectiveSwitchHandler().switchPerspective(application.getContext().get(EPartService.class),
										perspective);
							}

							@Override
							public Image getImage() {
								return EngineTools.getImage(perspective.getIconURI());
							}
						};
						children.add(itemController);
					}
				}
		}
		return children;
	}
}
