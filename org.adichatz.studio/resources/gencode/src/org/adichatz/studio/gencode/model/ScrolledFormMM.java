/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:46 CEST 2020
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
import org.adichatz.generator.xjc.ScrolledFormType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ScrolledFormMM")
public class ScrolledFormMM extends AXjcEntityMetaModel<ScrolledFormType> {
	/**
	 * Creates the MetaModel class for ScrolledFormMM.
	 *
	 */
	public ScrolledFormMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ValidableContainerMM");
		create();
	}

	/**
	 * Create contents for MetaModel ScrolledFormMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field managedToolBar
		 */
		RefField<?> refField;
		refField = new RefField(this, "managedToolBar", null, "adi://org.adichatz.studio/model/ManagedToolBarMM");
		
		/**
		 *  add a property field for field bindingServiceClassName
		 */
		new PropertyField(this, "bindingServiceClassName");
		
		/**
		 *  add a property field for field formMessageManager
		 */
		new PropertyField(this, "formMessageManager");
		
		/**
		 *  add a property field for field text
		 */
		new PropertyField(this, "text");
		
		// add one to many for field 'action' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "action", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/ActionMM");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ScrolledFormType> getBeanClass() {
		return ScrolledFormType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ValidableContainerMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}