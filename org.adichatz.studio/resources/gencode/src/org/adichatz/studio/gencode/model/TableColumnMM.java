/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:40 CEST 2020
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
import org.adichatz.generator.xjc.TableColumnType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/TableColumnMM")
public class TableColumnMM extends AXjcEntityMetaModel<TableColumnType> {
	/**
	 * Creates the MetaModel class for TableColumnMM.
	 *
	 */
	public TableColumnMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ColumnFieldMM");
		create();
	}

	/**
	 * Create contents for MetaModel TableColumnMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field controllerClassName
		 */
		new PropertyField(this, "controllerClassName");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<TableColumnType> getBeanClass() {
		return TableColumnType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ColumnFieldMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}