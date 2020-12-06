/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:53 CEST 2020
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
import org.adichatz.generator.xjc.CustomizedPreferenceType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/CustomizedPreferenceMM")
public class CustomizedPreferenceMM extends AXjcEntityMetaModel<CustomizedPreferenceType> {
	/**
	 * Creates the MetaModel class for CustomizedPreferenceMM.
	 *
	 */
	public CustomizedPreferenceMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel CustomizedPreferenceMM.
	 */
	public void create(){
		
		// add one to many for field 'preference' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "preference", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/PreferenceMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<CustomizedPreferenceType> getBeanClass() {
		return CustomizedPreferenceType.class;
	}
}