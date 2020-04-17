/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:12 CET 2019
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
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/GenerationUnitMM")
public class GenerationUnitMM extends AXjcEntityMetaModel<GenerationUnitType> {
	/**
	 * Creates the MetaModel class for GenerationUnitMM.
	 *
	 */
	public GenerationUnitMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel GenerationUnitMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field generators
		 */
		RefField<?> refField;
		refField = new RefField(this, "generators", null, "adi://org.adichatz.studio/model/GeneratorsMM");
		
		/**
		 *  add a property field for field scenarioClassName
		 */
		new PropertyField(this, "scenarioClassName");
		
		/**
		 *  add a property field for field adiResourceURI
		 */
		new PropertyField(this, "adiResourceURI");
		
		/**
		 *  add a property field for field type
		 */
		new PropertyField(this, "type");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<GenerationUnitType> getBeanClass() {
		return GenerationUnitType.class;
	}
}