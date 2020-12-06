/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:33:13 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.TreeElementType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/TreeElementMM")
public class TreeElementMM extends AXjcEntityMetaModel<TreeElementType> {
	/**
	 * Creates the MetaModel class for TreeElementMM.
	 *
	 */
	public TreeElementMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel TreeElementMM.
	 */
	public void create(){
		
		/**
		 *  add a property field for field backgroundCode
		 */
		new PropertyField(this, "backgroundCode");
		
		/**
		 *  add a property field for field foregroundlCode
		 */
		new PropertyField(this, "foregroundlCode");
		
		/**
		 *  add a property field for field fontCode
		 */
		new PropertyField(this, "fontCode");
		
		/**
		 *  add a property field for field labelCode
		 */
		new PropertyField(this, "labelCode");
		
		/**
		 *  add a property field for field imageCode
		 */
		new PropertyField(this, "imageCode");
		
		/**
		 *  add a property field for field entityURI
		 */
		new PropertyField(this, "entityURI");
		
		/**
		 *  add a property field for field background
		 */
		new PropertyField(this, "background");
		
		/**
		 *  add a property field for field foreground
		 */
		new PropertyField(this, "foreground");
		
		/**
		 *  add a property field for field font
		 */
		new PropertyField(this, "font");
		
		/**
		 *  add a property field for field image
		 */
		new PropertyField(this, "image");
		
		/**
		 *  add a property field for field label
		 */
		new PropertyField(this, "label");
		
		/**
		 *  add a property field for field treeChildClassName
		 */
		new PropertyField(this, "treeChildClassName");
		
		// add one to many for field 'treeNode' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "treeNode", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/TreeNodeMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<TreeElementType> getBeanClass() {
		return TreeElementType.class;
	}
}