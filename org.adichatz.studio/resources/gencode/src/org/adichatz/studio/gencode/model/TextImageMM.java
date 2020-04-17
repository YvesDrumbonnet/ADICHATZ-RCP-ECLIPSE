/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:33 CET 2019
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
import org.adichatz.generator.xjc.TextImageType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/TextImageMM")
public class TextImageMM extends AXjcEntityMetaModel<TextImageType> {
	/**
	 * Creates the MetaModel class for TextImageMM.
	 *
	 */
	public TextImageMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel TextImageMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field key
		 */
		new PropertyField(this, "key");
		
		/**
		 *  add a property field for field image
		 */
		new PropertyField(this, "image");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<TextImageType> getBeanClass() {
		return TextImageType.class;
	}
}