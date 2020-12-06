/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:56 CEST 2020
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
import org.adichatz.generator.xjc.ListDetailContentProviderType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ListDetailContentProviderMM")
public class ListDetailContentProviderMM extends AXjcEntityMetaModel<ListDetailContentProviderType> {
	/**
	 * Creates the MetaModel class for ListDetailContentProviderMM.
	 *
	 */
	public ListDetailContentProviderMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/QueryContentProviderMM");
		create();
	}

	/**
	 * Create contents for MetaModel ListDetailContentProviderMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field fieldName
		 */
		new PropertyField(this, "fieldName");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ListDetailContentProviderType> getBeanClass() {
		return ListDetailContentProviderType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/QueryContentProviderMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}