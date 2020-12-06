/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:33:11 CEST 2020
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
import org.adichatz.generator.xjc.ScenariosType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ScenariosMM")
public class ScenariosMM extends AXjcEntityMetaModel<ScenariosType> {
	/**
	 * Creates the MetaModel class for ScenariosMM.
	 *
	 */
	public ScenariosMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel ScenariosMM.
	 */
	public void create(){
		
		// add one to many for field 'scenario' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "scenario", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/ScenarioMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ScenariosType> getBeanClass() {
		return ScenariosType.class;
	}
}