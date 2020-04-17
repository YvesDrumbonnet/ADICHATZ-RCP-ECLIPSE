/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:55 CET 2019
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
import org.adichatz.generator.xjc.FontTextType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/FontTextMM")
public class FontTextMM extends AXjcEntityMetaModel<FontTextType> {
	/**
	 * Creates the MetaModel class for FontTextMM.
	 *
	 */
	public FontTextMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ControlFieldMM");
		create();
	}

	/**
	 * Create contents for MetaModel FontTextMM.
	 */
	public void create(){
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<FontTextType> getBeanClass() {
		return FontTextType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ControlFieldMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}