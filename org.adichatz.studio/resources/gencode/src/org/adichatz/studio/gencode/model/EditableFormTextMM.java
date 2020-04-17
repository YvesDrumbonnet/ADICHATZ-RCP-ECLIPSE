/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:15:54 CET 2019
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
import org.adichatz.generator.xjc.EditableFormTextType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/EditableFormTextMM")
public class EditableFormTextMM extends AXjcEntityMetaModel<EditableFormTextType> {
	/**
	 * Creates the MetaModel class for EditableFormTextMM.
	 *
	 */
	public EditableFormTextMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		pluginEntity.setSuperEntityURI("adi://org.adichatz.studio/model/ControlFieldMM");
		create();
	}

	/**
	 * Create contents for MetaModel EditableFormTextMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field parseTags
		 */
		new PropertyField(this, "parseTags");
		
		/**
		 *  add a property field for field expandURLs
		 */
		new PropertyField(this, "expandURLs");
		
		/**
		 *  add a property field for field paragraphsSeparated
		 */
		new PropertyField(this, "paragraphsSeparated");
		
		/**
		 *  add a property field for field whitespaceNormalized
		 */
		new PropertyField(this, "whitespaceNormalized");
		
		/**
		 *  add a property field for field text
		 */
		new PropertyField(this, "text");
		
		// add one to many for field 'textFont' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "textFont", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/TextFontMM");
		
		// add one to many for field 'textColor' (parent clause='null')
		oneToMany = new XjcEntitySet(this, "textColor", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/TextColorMM");
		
		// add one to many for field 'textImage' (parent clause='null')
		oneToMany = new XjcEntitySet(this, "textImage", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/TextImageMM");
		addSuperFields();
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<EditableFormTextType> getBeanClass() {
		return EditableFormTextType.class;
	}
	@Override
	protected void addSuperFields(){
		AEntityMetaModel<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM("adi://org.adichatz.studio/model/ControlFieldMM");
		fieldMap.putAll(superEntityMM.getFieldMap());
		super.addSuperFields();
	}
}