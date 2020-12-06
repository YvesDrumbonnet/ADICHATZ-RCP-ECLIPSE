/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 12:30:08 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.jpa;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;

@AdiResourceURI(URI="adi://org.adichatz.jpa/jpa/EditorToolContainer")
public class EditorToolContainer extends AContainerCore {
	// the PShelfController navigationFolderPS.
	protected PShelfController navigationFolderPS;
	protected ControllerCore editorToolContainerNavigationItem;
	protected ControllerCore editorToolContainerEntityItem;
	protected ControllerCore editorToolContainerErrorItem;
	protected ControllerCore editorToolContainerFilterItem;
	/**
	 * Instantiates a new EditorToolContainer.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public EditorToolContainer(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part EditorToolContainer.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public EditorToolContainer(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
		super(pluginResources, coreController, paramMap);
		ContainerController containerController = (ContainerController) coreController;
		getRegister().put("editorOutline", new RegisterEntry<ContainerController>(containerController, ContainerController.class));
		createContents();
	}

	/**
	 * creates contents
	 */
	public void createContents(){
		// Creates control for PShelfController navigationFolderPS
		navigationFolderPS = new PShelfController("navigationFolder", coreController, this) {
			@Override
			public void synchronize() {
				super.synchronize();
				if (isValid()) {
					setSelection(0);
				}
			}
		};
		
		// loads and instantiates the class EditorToolContainerNavigationItem.
		editorToolContainerNavigationItem  = new EditorToolContainerNavigationItem(context, navigationFolderPS);
		
		// loads and instantiates the class EditorToolContainerEntityItem.
		editorToolContainerEntityItem  = new EditorToolContainerEntityItem(context, navigationFolderPS);
		
		// loads and instantiates the class EditorToolContainerErrorItem.
		editorToolContainerErrorItem  = new EditorToolContainerErrorItem(context, navigationFolderPS);
		
		// loads and instantiates the class EditorToolContainerFilterItem.
		editorToolContainerFilterItem  = new EditorToolContainerFilterItem(context, navigationFolderPS);
	}
}