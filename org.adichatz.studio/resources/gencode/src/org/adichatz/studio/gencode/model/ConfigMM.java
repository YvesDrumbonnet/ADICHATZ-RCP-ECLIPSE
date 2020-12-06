/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:48 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.ConfigType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ConfigMM")
public class ConfigMM extends AXjcEntityMetaModel<ConfigType> {
	/**
	 * Creates the MetaModel class for ConfigMM.
	 *
	 */
	public ConfigMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel ConfigMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field resourceBundles
		 */
		RefField<?> refField;
		refField = new RefField(this, "resourceBundles", null, "adi://org.adichatz.studio/model/ResourceBundlesMM");
		
		/**
		 *  add a join column (ManyToOne) for field customizations
		 */
		refField = new RefField(this, "customizations", null, "adi://org.adichatz.studio/model/CustomizationsMM");
		
		/**
		 *  add a join column (ManyToOne) for field navigationPaths
		 */
		refField = new RefField(this, "navigationPaths", null, "adi://org.adichatz.studio/model/NavigationPathsMM");
		
		/**
		 *  add a join column (ManyToOne) for field params
		 */
		refField = new RefField(this, "params", null, "adi://org.adichatz.studio/model/ParamsMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ConfigType> getBeanClass() {
		return ConfigType.class;
	}
}