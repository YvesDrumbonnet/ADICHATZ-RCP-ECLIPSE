/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:16:09 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.model;

import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.xjc.ConnectorTree;
import org.adichatz.studio.xjc.scenario.AXjcEntityMetaModel;

@AdiResourceURI(URI="adi://org.adichatz.studio/model/ConnectorTreeMM")
public class ConnectorTreeMM extends AXjcEntityMetaModel<ConnectorTree> {
	/**
	 * Creates the MetaModel class for ConnectorTreeMM.
	 *
	 */
	public ConnectorTreeMM(PluginEntity<?> pluginEntity) {
		super(pluginEntity);
		create();
	}

	/**
	 * Create contents for MetaModel ConnectorTreeMM.
	 */
	public void create(){
		
		/**
		 *  add a join column (ManyToOne) for field applicationServers
		 */
		RefField<?> refField;
		refField = new RefField(this, "applicationServers", null, "adi://org.adichatz.studio/model/ApplicationServersMM");
		
		/**
		 *  add a join column (ManyToOne) for field datasources
		 */
		refField = new RefField(this, "datasources", null, "adi://org.adichatz.studio/model/DatasourcesMM");
	}
	
	/* (non-Javadoc)
	 * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()
	 */
	@Override
	public Class<ConnectorTree> getBeanClass() {
		return ConnectorTree.class;
	}
}