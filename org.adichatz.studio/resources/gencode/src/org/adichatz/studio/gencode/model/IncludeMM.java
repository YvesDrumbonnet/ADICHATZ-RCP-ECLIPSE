/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:42 CEST 2020
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
import org.adichatz.generator.xjc.IncludeType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/IncludeMM")
public class IncludeMM extends AXjcEntityMetaModel<IncludeType> {
	/**
	 * Creates the MetaModel class for IncludeMM.
	 *
	 */
	public IncludeMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/WidgetMM");
		create();
	}

	/**
	 * Create contents for MetaModel IncludeMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field params
		 */
		RefField<?> refField;
		refField = new RefField(this, "params", null, "adi://org.adichatz.studio/model/ParamsMM");
		
		/**
		 *  add a join column (ManyToOne) for field customizations
		 */
		refField = new RefField(this, "customizations", null, "adi://org.adichatz.studio/model/CustomizationsMM");
		
		/**
		 *  add a property field for field adiResourceURI
		 */
		new PropertyField(this, "adiResourceURI");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<IncludeType> getBeanClass() {
		return IncludeType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/WidgetMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}