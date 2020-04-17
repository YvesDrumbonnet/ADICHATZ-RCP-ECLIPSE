/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:08 CET 2019
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
import org.adichatz.generator.xjc.ResourceBundlesType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ResourceBundlesMM")
public class ResourceBundlesMM extends AXjcEntityMetaModel<ResourceBundlesType> {
	/**
	 * Creates the MetaModel class for ResourceBundlesMM.
	 *
	 */
	public ResourceBundlesMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel ResourceBundlesMM.
	 */
	public void create(){
		
		// add one to many for field 'resourceBundle' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "resourceBundle", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/ResourceBundleMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ResourceBundlesType> getBeanClass() {
		return ResourceBundlesType.class;
	}
}