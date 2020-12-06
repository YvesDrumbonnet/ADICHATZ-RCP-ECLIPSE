/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:57 CEST 2020
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
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/EntityTreeMM")
public class EntityTreeMM extends AXjcEntityMetaModel<EntityTree> {
	/**
	 * Creates the MetaModel class for EntityTreeMM.
	 *
	 */
	public EntityTreeMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel EntityTreeMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field entityURI
		 */
		new PropertyField(this, "entityURI");
		
		/**
		 *  add a property field for field superEntityURI
		 */
		new PropertyField(this, "superEntityURI");
		
		/**
		 *  add a property field for field idFieldName
		 */
		new PropertyField(this, "idFieldName");
		
		/**
		 *  add a property field for field callbackClassNames
		 */
		new PropertyField(this, "callbackClassNames");
		
		/**
		 *  add a property field for field callforeClassNames
		 */
		new PropertyField(this, "callforeClassNames");
		
		/**
		 *  add a property field for field compositeKeyStrategyFactoryClassName
		 */
		new PropertyField(this, "compositeKeyStrategyFactoryClassName");
		
		// add one to many for field 'propertyField' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "propertyField", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/PropertyFieldMM");
		
		// add one to many for field 'oneToMany' (parent clause='null')
		oneToMany = new XjcEntitySet(this, "oneToMany", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/OneToManyMM");
		
		// add one to many for field 'manyToMany' (parent clause='null')
		oneToMany = new XjcEntitySet(this, "manyToMany", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/ManyToManyMM");
		
		// add one to many for field 'oneToOne' (parent clause='null')
		oneToMany = new XjcEntitySet(this, "oneToOne", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/OneToOneMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<EntityTree> getBeanClass() {
		return EntityTree.class;
	}
}