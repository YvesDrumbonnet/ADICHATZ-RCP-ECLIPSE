package org.adichatz.jpa.extra;

import org.adichatz.common.ejb.ProxyEntity;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.jpa.data.JPADataAccess;

public class JPASimulationTools {
	public static <T> IEntity<T> findEntity(String entityURI, Object beanId, String... lazyFetchMembers) {
		String[] instanceKeys = EngineTools.getInstanceKeys(entityURI);
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(instanceKeys[0]);
		return ((JPADataAccess) pluginResources.getDataAccess()).findEntity(entityURI, beanId, lazyFetchMembers);
	}

	public static <T> ProxyEntity<T> findProxyEntity(String entityURI, Object beanId, String... lazyFetchMembers) {
		String[] instanceKeys = EngineTools.getInstanceKeys(entityURI);
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(instanceKeys[0]);
		return ((JPADataAccess) pluginResources.getDataAccess()).findProxyEntity(entityURI, beanId, lazyFetchMembers);
	}
}
