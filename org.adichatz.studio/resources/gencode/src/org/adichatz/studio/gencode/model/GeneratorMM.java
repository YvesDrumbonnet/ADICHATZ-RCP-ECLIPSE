/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:19 CET 2019
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
import org.adichatz.generator.xjc.GeneratorType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/GeneratorMM")
public class GeneratorMM extends AXjcEntityMetaModel<GeneratorType> {
	/**
	 * Creates the MetaModel class for GeneratorMM.
	 *
	 */
	public GeneratorMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel GeneratorMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field treeClassName
		 */
		new PropertyField(this, "treeClassName");
		
		/**
		 *  add a property field for field generatorClassName
		 */
		new PropertyField(this, "generatorClassName");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<GeneratorType> getBeanClass() {
		return GeneratorType.class;
	}
}