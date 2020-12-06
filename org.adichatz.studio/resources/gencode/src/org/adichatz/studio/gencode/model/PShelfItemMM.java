/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:33:06 CEST 2020
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
import org.adichatz.generator.xjc.PShelfItemType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/PShelfItemMM")
public class PShelfItemMM extends AXjcEntityMetaModel<PShelfItemType> {
	/**
	 * Creates the MetaModel class for PShelfItemMM.
	 *
	 */
	public PShelfItemMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/FieldContainerMM");
		create();
	}

	/**
	 * Create contents for MetaModel PShelfItemMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field image
		 */
		new PropertyField(this, "image");
		
		/**
		 *  add a property field for field text
		 */
		new PropertyField(this, "text");
		
		/**
		 *  add a property field for field toolTipText
		 */
		new PropertyField(this, "toolTipText");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<PShelfItemType> getBeanClass() {
		return PShelfItemType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/FieldContainerMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}