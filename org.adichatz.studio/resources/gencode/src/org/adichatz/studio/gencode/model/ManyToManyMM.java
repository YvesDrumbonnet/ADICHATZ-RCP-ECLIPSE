/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:23 CET 2019
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
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.ManyToManyType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ManyToManyMM")
public class ManyToManyMM extends AXjcEntityMetaModel<ManyToManyType> {
	/**
	 * Creates the MetaModel class for ManyToManyMM.
	 *
	 */
	public ManyToManyMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/EntitySetMM");
		create();
	}

	/**
	 * Create contents for MetaModel ManyToManyMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field params
		 */
		RefField<?> refField;
		refField = new RefField(this, "params", null, "adi://org.adichatz.studio/model/ParamsMM");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ManyToManyType> getBeanClass() {
		return ManyToManyType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/EntitySetMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}