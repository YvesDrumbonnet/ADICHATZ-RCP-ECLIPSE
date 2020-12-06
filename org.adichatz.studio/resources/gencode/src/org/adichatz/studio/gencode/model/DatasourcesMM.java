/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:32:48 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.DatasourcesType;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/DatasourcesMM")
public class DatasourcesMM extends AXjcEntityMetaModel<DatasourcesType> {
	/**
	 * Creates the MetaModel class for DatasourcesMM.
	 *
	 */
	public DatasourcesMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel DatasourcesMM.
	 */
	public void create(){
		
		// add one to many for field 'datasource' (parent clause='null')
		XjcEntitySet oneToMany;
		oneToMany = new XjcEntitySet(this, "datasource", null);
		oneToMany.addElementEntity("adi://org.adichatz.studio/model/DatasourceMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<DatasourcesType> getBeanClass() {
		return DatasourcesType.class;
	}
}