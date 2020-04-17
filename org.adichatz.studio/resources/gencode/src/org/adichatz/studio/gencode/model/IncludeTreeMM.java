/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:20 CET 2019
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
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.IncludeTree;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/IncludeTreeMM")
public class IncludeTreeMM extends AXjcEntityMetaModel<IncludeTree> {
	/**
	 * Creates the MetaModel class for IncludeTreeMM.
	 *
	 */
	public IncludeTreeMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ContainerMM");
		create();
	}

	/**
	 * Create contents for MetaModel IncludeTreeMM.
	 */
	public void create(){
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<IncludeTree> getBeanClass() {
		return IncludeTree.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ContainerMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}