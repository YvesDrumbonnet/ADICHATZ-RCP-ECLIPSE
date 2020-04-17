/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:20 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.IconType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/IconMM")
public class IconMM extends AXjcEntityMetaModel<IconType> {
	/**
	 * Creates the MetaModel class for IconMM.
	 *
	 */
	public IconMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel IconMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field type
		 */
		new PropertyField(this, "type");
		
		/**
		 *  add a property field for field image
		 */
		new PropertyField(this, "image");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<IconType> getBeanClass() {
		return IconType.class;
	}
}