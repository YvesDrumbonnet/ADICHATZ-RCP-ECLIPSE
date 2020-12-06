/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:20 CEST 2020
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
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.ActionType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ActionMM")
public class ActionMM extends AXjcEntityMetaModel<ActionType> {
	/**
	 * Creates the MetaModel class for ActionMM.
	 *
	 */
	public ActionMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/WidgetMM");
		create();
	}

	/**
	 * Create contents for MetaModel ActionMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field params
		 */
		RefField<?> refField;
		refField = new RefField(this, "params", null, "adi://org.adichatz.studio/model/ParamsMM");
		
		/**
		 *  add a property field for field actionCode
		 */
		new PropertyField(this, "actionCode");
		
		/**
		 *  add a property field for field actionClassName
		 */
		new PropertyField(this, "actionClassName");
		
		/**
		 *  add a property field for field imageDescriptor
		 */
		new PropertyField(this, "imageDescriptor");
		
		/**
		 *  add a property field for field rank
		 */
		new PropertyField(this, "rank");
		
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
	public Class<ActionType> getBeanClass() {
		return ActionType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/WidgetMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}