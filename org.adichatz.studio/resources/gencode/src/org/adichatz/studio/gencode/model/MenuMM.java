/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:33:03 CEST 2020
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
import org.adichatz.generator.xjc.MenuType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/MenuMM")
public class MenuMM extends AXjcEntityMetaModel<MenuType> {
	/**
	 * Creates the MetaModel class for MenuMM.
	 *
	 */
	public MenuMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/NodeMM");
		create();
	}

	/**
	 * Create contents for MetaModel MenuMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field expanded
		 */
		new PropertyField(this, "expanded");
		
		// add one to many for field 'menuOrItem' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "menuOrItem", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/MenuMM");
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/ItemMM");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<MenuType> getBeanClass() {
		return MenuType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/NodeMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}