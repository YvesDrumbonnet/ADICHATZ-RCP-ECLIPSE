/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:35:09 CEST 2020
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

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/RadioGroupPart")
public class RadioGroupPart extends AContainerCore {
	// the PShelfController detailPShelfPS.
	protected PShelfController detailPShelfPS;
	protected ControllerCore radioGroupPartBasicItem;
	protected ControllerCore radioGroupPartLifeCycleItem;
	protected ControllerCore radioGroupPartControlItem;
	/**
	 * Instantiates a new RadioGroupPart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public RadioGroupPart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part RadioGroupPart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public RadioGroupPart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
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
		
		// loads and instantiates the class RadioGroupPartBasicItem.
		radioGroupPartBasicItem  = new RadioGroupPartBasicItem(context, detailPShelfPS);
		
		// loads and instantiates the class RadioGroupPartLifeCycleItem.
		radioGroupPartLifeCycleItem  = new RadioGroupPartLifeCycleItem(context, detailPShelfPS);
		
		// loads and instantiates the class RadioGroupPartControlItem.
		radioGroupPartControlItem  = new RadioGroupPartControlItem(context, detailPShelfPS);
	}
}