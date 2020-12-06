/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 12:29:58 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode;

import javax.inject.Inject;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.core.ARootMenuCore;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.PerspectiveMenuController;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.graphics.Image;

@AdiResourceURI(URI="adi://org.adichatz.jpa/./JpaMenu")
public class JpaMenu extends ARootMenuCore {
	@Inject
	protected AdiFormToolkit toolkit;
	
	public JpaMenu(MenuController rootMenuController) {
		super("org.adichatz.jpa", rootMenuController);
	}
	
	/**
	 * Creates children (menu or item) for menu JpaMenu
	 */
	public void createChildren() {
		coreController = new MenuController(pluginResources, this, "JpaMenu", null, null);
		
		// Create menu JpaToolsMENU
		MenuController jpaTools = new MenuController(pluginResources, this, "jpaTools", AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "adichatzJpa", "tools"), null) {
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage("org.adichatz.engine.e4", "IMG_TOOL.png");
			}
		};
		ItemController item;
		
		// Create item openPreference
		item = new ItemController(pluginResources, this, "openPreference", AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "adichatzJpa", "preferences"), null) {
			@Override
			public void handleActivate() {
				IEclipseContext context = (IEclipseContext) getTransientData().get(IEclipseContext.class.getName());
				E4AdichatzApplication.openPart(context, getParamMap());
			}
			@Override
			public ParamMap getParamMap() {
				paramMap = new ParamMap();
				paramMap.put("COMMAND", "adichatz.preference.command");
				paramMap.put(ParamMap.PLUGIN_RESOURCES, pluginResources);
				return paramMap;
			}
			public Image getImage() {
				return toolkit.getRegisteredImage("IMG_PREFERENCE");
			}
		};
		jpaTools.getChildren().add(item);
		
		// Create item IMG_SWITCH_EDITORS.png
		item = new ItemController(pluginResources, this, "IMG_SWITCH_EDITORS.png", AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "adichatzJpa", "switchEditors"), null) {
			@Override
			public void handleActivate() {
				IEclipseContext context = (IEclipseContext) getTransientData().get(IEclipseContext.class.getName());
				E4AdichatzApplication.openPart(context, getParamMap());
			}
			@Override
			public ParamMap getParamMap() {
				paramMap = new ParamMap();
				paramMap.put("COMMAND", "adichatz.switch.editor.command");
				paramMap.put(ParamMap.PLUGIN_RESOURCES, pluginResources);
				return paramMap;
			}
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage("org.adichatz.engine.e4", "IMG_SWITCH_EDITORS.png");
			}
		};
		jpaTools.getChildren().add(item);
		
		// Create item openPooledQueries
		item = new ItemController(pluginResources, this, "openPooledQueries", AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "adichatzJpa", "pooledQueries"), null) {
			@Override
			public void handleActivate() {
				IEclipseContext context = (IEclipseContext) getTransientData().get(IEclipseContext.class.getName());
				E4AdichatzApplication.openPart(context, getParamMap());
			}
			@Override
			public ParamMap getParamMap() {
				paramMap = new ParamMap();
				paramMap.put("COMMAND", "adichatz.application.pooledQuery.command");
				paramMap.put(ParamMap.PLUGIN_RESOURCES, pluginResources);
				return paramMap;
			}
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage("org.adichatz.engine.e4", "IMG_QUERY_MANAGER.png");
			}
		};
		jpaTools.getChildren().add(item);
		
		// Create menu PerspectiveMenuMENU
		PerspectiveMenuController perspectiveMenu = new PerspectiveMenuController(pluginResources, this, "perspectiveMenu", AdichatzApplication.getInstance().getMessage("org.adichatz.engine.e4", "adichatzEngineE4", "menu.perspective"), null) {
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage("org.adichatz.engine.e4", "IMG_PERSPECTIVE.png");
			}
		};
		jpaTools.getChildren().add(perspectiveMenu);
		coreController.getChildren().add(jpaTools);
	}
}