/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:37 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.AccessibilityType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/AccessibilityMM")
public class AccessibilityMM extends AXjcEntityMetaModel<AccessibilityType> {
	/**
	 * Creates the MetaModel class for AccessibilityMM.
	 *
	 */
	public AccessibilityMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ElementMM");
		create();
	}

	/**
	 * Create contents for MetaModel AccessibilityMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field accept
		 */
		new PropertyField(this, "accept");
		
		/**
		 *  add a property field for field value
		 */
		new PropertyField(this, "value");
		
		/**
		 *  add a property field for field type
		 */
		new PropertyField(this, "type");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<AccessibilityType> getBeanClass() {
		return AccessibilityType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ElementMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}