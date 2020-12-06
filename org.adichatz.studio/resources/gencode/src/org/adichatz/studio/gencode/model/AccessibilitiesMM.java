/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Wed Jul 08 10:05:58 CEST 2020
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
import org.adichatz.generator.xjc.AccessibilitiesType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/AccessibilitiesMM")
public class AccessibilitiesMM extends AXjcEntityMetaModel<AccessibilitiesType> {
	/**
	 * Creates the MetaModel class for AccessibilitiesMM.
	 *
	 */
	public AccessibilitiesMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel AccessibilitiesMM.
	 */
	public void create(){
		
		// add one to many for field 'accessibility' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "accessibility", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/AccessibilityMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<AccessibilitiesType> getBeanClass() {
		return AccessibilitiesType.class;
	}
}