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
import org.adichatz.generator.xjc.QueryContentProviderType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/QueryContentProviderMM")
public class QueryContentProviderMM extends AXjcEntityMetaModel<QueryContentProviderType> {
	/**
	 * Creates the MetaModel class for QueryContentProviderMM.
	 *
	 */
	public QueryContentProviderMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ContentProviderMM");
		create();
	}

	/**
	 * Create contents for MetaModel QueryContentProviderMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field adiResourceURI
		 */
		new PropertyField(this, "adiResourceURI");
		
		/**
		 *  add a property field for field pluginKey
		 */
		new PropertyField(this, "pluginKey");
		
		/**
		 *  add a property field for field preferenceURI
		 */
		new PropertyField(this, "preferenceURI");
		
		/**
		 *  add a property field for field comparatorClassName
		 */
		new PropertyField(this, "comparatorClassName");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<QueryContentProviderType> getBeanClass() {
		return QueryContentProviderType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ContentProviderMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}