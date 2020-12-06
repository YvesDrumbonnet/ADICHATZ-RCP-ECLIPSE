/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 12:30:00 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.query.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;

@AdiResourceURI(URI="adi://org.adichatz.jpa/query.model/QueryPreferenceMM")
public class QueryPreferenceMM extends AEntityMetaModel<QueryPreferenceWrapper> {
	/**
	 * Creates the MetaModel class for QueryPreferenceMM.
	 *
	 */
	public QueryPreferenceMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel QueryPreferenceMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field orderByClause
		 */
		new PropertyField(this, "orderByClause");
		
		/**
		 *  add a property field for field value
		 */
		new PropertyField(this, "value");
		
		/**
		 *  add a property field for field id
		 */
		new PropertyField(this, "id");
		
		/**
		 *  add a property field for field type
		 */
		new PropertyField(this, "type");
		
		// add one to many for field 'parameter' (parent clause='null')
		new EntitySet(this, "parameter", null);
		
		// add one to many for field 'openClause' (parent clause='null')
		new EntitySet(this, "openClause", null);
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<QueryPreferenceWrapper> getBeanClass() {
		return QueryPreferenceWrapper.class;
	}
}