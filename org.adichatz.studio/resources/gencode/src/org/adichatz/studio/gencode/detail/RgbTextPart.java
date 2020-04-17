/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:18:40 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.detail;

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

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/RgbTextPart")
public class RgbTextPart extends AContainerCore {
	// the PShelfController detailPShelfPS.
	protected PShelfController detailPShelfPS;
	protected ControllerCore rgbTextPartBasicItem;
	protected ControllerCore rgbTextPartLifeCycleItem;
	protected ControllerCore rgbTextPartControlItem;
	/**
	 * Instantiates a new RgbTextPart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public RgbTextPart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part RgbTextPart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public RgbTextPart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
		super(pluginResources, coreController, paramMap);
		ContainerController containerController = (ContainerController) coreController;
		getRegister().put("detailPart", new RegisterEntry<ContainerController>(containerController, ContainerController.class));
		createContents();
	}

	/**
	 * creates contents
	 */
	public void createContents(){
		// Creates control for PShelfController detailPShelfPS
		detailPShelfPS = new PShelfController("detailPShelf", coreController, this) {
			@Override
			public void synchronize() {
				super.synchronize();
				if (isValid()) {
					setSelection(0);
				}
			}
		};
		detailPShelfPS.setDelayed(true);
		
		// loads and instantiates the class RgbTextPartBasicItem.
		rgbTextPartBasicItem  = new RgbTextPartBasicItem(context, detailPShelfPS);
		
		// loads and instantiates the class RgbTextPartLifeCycleItem.
		rgbTextPartLifeCycleItem  = new RgbTextPartLifeCycleItem(context, detailPShelfPS);
		
		// loads and instantiates the class RgbTextPartControlItem.
		rgbTextPartControlItem  = new RgbTextPartControlItem(context, detailPShelfPS);
	}
}