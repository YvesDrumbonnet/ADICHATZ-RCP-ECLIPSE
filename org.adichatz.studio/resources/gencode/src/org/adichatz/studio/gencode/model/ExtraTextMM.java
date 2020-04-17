/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:54 CET 2019
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
import org.adichatz.generator.xjc.ExtraTextType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ExtraTextMM")
public class ExtraTextMM extends AXjcEntityMetaModel<ExtraTextType> {
	/**
	 * Creates the MetaModel class for ExtraTextMM.
	 *
	 */
	public ExtraTextMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ControlFieldMM");
		create();
	}

	/**
	 * Create contents for MetaModel ExtraTextMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field addRefreshItem
		 */
		new PropertyField(this, "addRefreshItem");
		
		/**
		 *  add a property field for field editable
		 */
		new PropertyField(this, "editable");
		
		/**
		 *  add a property field for field orientation
		 */
		new PropertyField(this, "orientation");
		
		/**
		 *  add a property field for field tabs
		 */
		new PropertyField(this, "tabs");
		
		/**
		 *  add a property field for field text
		 */
		new PropertyField(this, "text");
		
		/**
		 *  add a property field for field textLimit
		 */
		new PropertyField(this, "textLimit");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ExtraTextType> getBeanClass() {
		return ExtraTextType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ControlFieldMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}