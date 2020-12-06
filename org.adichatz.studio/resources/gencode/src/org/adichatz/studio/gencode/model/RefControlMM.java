/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:31 CEST 2020
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
import org.adichatz.generator.xjc.RefControlType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/RefControlMM")
public class RefControlMM extends AXjcEntityMetaModel<RefControlType> {
	/**
	 * Creates the MetaModel class for RefControlMM.
	 *
	 */
	public RefControlMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ControlFieldMM");
		create();
	}

	/**
	 * Create contents for MetaModel RefControlMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field labelProvider
		 */
		RefField<?> refField;
		refField = new RefField(this, "labelProvider", null, "adi://org.adichatz.studio/model/LabelProviderMM");
		
		/**
		 *  add a property field for field initValues
		 */
		new PropertyField(this, "initValues");
		
		/**
		 *  add a join column (ManyToOne) for field queryReference
		 */
		refField = new RefField(this, "queryReference", null, "adi://org.adichatz.studio/model/QueryReferenceMM");
		
		/**
		 *  add a property field for field poolQueryResult
		 */
		new PropertyField(this, "poolQueryResult");
		
		/**
		 *  add a property field for field values
		 */
		new PropertyField(this, "values");
		
		/**
		 *  add a property field for field displayedValues
		 */
		new PropertyField(this, "displayedValues");
		
		/**
		 *  add a property field for field labelProviderClassName
		 */
		new PropertyField(this, "labelProviderClassName");
		
		/**
		 *  add a property field for field preferenceURI
		 */
		new PropertyField(this, "preferenceURI");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<RefControlType> getBeanClass() {
		return RefControlType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ControlFieldMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}