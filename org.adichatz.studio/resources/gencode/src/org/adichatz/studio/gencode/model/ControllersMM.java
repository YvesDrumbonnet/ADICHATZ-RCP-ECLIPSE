/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:11 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.ControllersType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ControllersMM")
public class ControllersMM extends AXjcEntityMetaModel<ControllersType> {
	/**
	 * Creates the MetaModel class for ControllersMM.
	 *
	 */
	public ControllersMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel ControllersMM.
	 */
	public void create(){
		
		// add one to many for field 'controller' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "controller", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/ControllerMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ControllersType> getBeanClass() {
		return ControllersType.class;
	}
}