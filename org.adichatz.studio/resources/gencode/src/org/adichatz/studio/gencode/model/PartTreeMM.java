/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:33:06 CEST 2020
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
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.PartTree;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/PartTreeMM")
public class PartTreeMM extends AXjcEntityMetaModel<PartTree> {
	/**
	 * Creates the MetaModel class for PartTreeMM.
	 *
	 */
	public PartTreeMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel PartTreeMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field config
		 */
		RefField<?> refField;
		refField = new RefField(this, "config", null, "adi://org.adichatz.studio/model/ConfigMM");
		
		/**
		 *  add a property field for field additionalCode
		 */
		new PropertyField(this, "additionalCode");
		
		/**
		 *  add a property field for field bindingServiceClassName
		 */
		new PropertyField(this, "bindingServiceClassName");
		
		/**
		 *  add a property field for field coreClassName
		 */
		new PropertyField(this, "coreClassName");
		
		/**
		 *  add a property field for field entityURI
		 */
		new PropertyField(this, "entityURI");
		
		/**
		 *  add a property field for field image
		 */
		new PropertyField(this, "image");
		
		/**
		 *  add a property field for field outlinePageClassName
		 */
		new PropertyField(this, "outlinePageClassName");
		
		/**
		 *  add a property field for field title
		 */
		new PropertyField(this, "title");
		
		/**
		 *  add a property field for field toolTipText
		 */
		new PropertyField(this, "toolTipText");
		
		// add one to many for field 'formPageOrInclude' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "formPageOrInclude", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/FormPageMM");
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/IncludeMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<PartTree> getBeanClass() {
		return PartTree.class;
	}
}