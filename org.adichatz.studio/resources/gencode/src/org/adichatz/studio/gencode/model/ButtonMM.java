/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:48 CET 2019
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
import org.adichatz.generator.xjc.ButtonType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ButtonMM")
public class ButtonMM extends AXjcEntityMetaModel<ButtonType> {
	/**
	 * Creates the MetaModel class for ButtonMM.
	 *
	 */
	public ButtonMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ControlMM");
		create();
	}

	/**
	 * Create contents for MetaModel ButtonMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field image
		 */
		new PropertyField(this, "image");
		
		/**
		 *  add a property field for field reflow
		 */
		new PropertyField(this, "reflow");
		
		/**
		 *  add a property field for field text
		 */
		new PropertyField(this, "text");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ButtonType> getBeanClass() {
		return ButtonType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ControlMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}