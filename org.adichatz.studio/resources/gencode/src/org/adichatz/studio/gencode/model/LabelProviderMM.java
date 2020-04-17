/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:44 CET 2019
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
import org.adichatz.generator.xjc.LabelProviderType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/LabelProviderMM")
public class LabelProviderMM extends AXjcEntityMetaModel<LabelProviderType> {
	/**
	 * Creates the MetaModel class for LabelProviderMM.
	 *
	 */
	public LabelProviderMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel LabelProviderMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field textCode
		 */
		new PropertyField(this, "textCode");
		
		/**
		 *  add a property field for field imageCode
		 */
		new PropertyField(this, "imageCode");
		
		/**
		 *  add a property field for field backgroundCode
		 */
		new PropertyField(this, "backgroundCode");
		
		/**
		 *  add a property field for field foregroundCode
		 */
		new PropertyField(this, "foregroundCode");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<LabelProviderType> getBeanClass() {
		return LabelProviderType.class;
	}
}