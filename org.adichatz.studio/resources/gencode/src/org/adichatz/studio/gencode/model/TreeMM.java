/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:47 CET 2019
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
import org.adichatz.generator.xjc.TreeType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/TreeMM")
public class TreeMM extends AXjcEntityMetaModel<TreeType> {
	/**
	 * Creates the MetaModel class for TreeMM.
	 *
	 */
	public TreeMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/SetMM");
		create();
	}

	/**
	 * Create contents for MetaModel TreeMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field showRoot
		 */
		new PropertyField(this, "showRoot");
		
		/**
		 *  add a property field for field treeManager
		 */
		new PropertyField(this, "treeManager");
		
		/**
		 *  add a property field for field expand
		 */
		new PropertyField(this, "expand");
		
		/**
		 *  add a property field for field rootElement
		 */
		new PropertyField(this, "rootElement");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<TreeType> getBeanClass() {
		return TreeType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/SetMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}