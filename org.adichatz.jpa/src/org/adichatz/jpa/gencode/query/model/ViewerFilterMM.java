/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Fri Feb 21 12:39:47 CET 2020
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
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.jpa.xjc.FilterType;

@AdiResourceURI(URI="adi://org.adichatz.jpa/query.model/ViewerFilterMM")
public class ViewerFilterMM extends AEntityMetaModel<FilterType> {
	/**
	 * Creates the MetaModel class for ViewerFilterMM.
	 *
	 */
	public ViewerFilterMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel ViewerFilterMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field searchString
		 */
		new PropertyField(this, "searchString");
		
		/**
		 *  add a property field for field text
		 */
		new PropertyField(this, "text");
		
		/**
		 *  add a property field for field column
		 */
		new PropertyField(this, "column");
		
		/**
		 *  add a property field for field columnType
		 */
		new PropertyField(this, "columnType");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<FilterType> getBeanClass() {
		return FilterType.class;
	}
}