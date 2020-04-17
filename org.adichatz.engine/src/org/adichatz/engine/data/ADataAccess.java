/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.engine.data;

import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IRootController;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Class ADataAccess.
 * 
 * @author Yves Drumbonnet
 */
public abstract class ADataAccess {

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The plugin entity tree. */
	protected APluginEntityTree pluginEntityTree;

	/** The data cache. */
	protected ADataCache dataCache;

	/** The bean interceptor factory. */
	protected IBeanInterceptorFactory beanInterceptorFactory;

	/** The gateway connector. */
	protected IGatewayConnector gatewayConnector;

	/**
	 * Instantiates a new a data access.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 */
	public ADataAccess(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
		pluginEntityTree = pluginResources.getPluginEntityTree();
	}

	/**
	 * Gets the data cache.
	 * 
	 * @return the data cache
	 */
	public ADataCache getDataCache() {
		return dataCache;
	}

	/**
	 * Sets the data cache.
	 * 
	 * @param dataCache
	 *            the new data cache
	 */
	public void setDataCache(ADataCache dataCache) {
		this.dataCache = dataCache;
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @return the plugin resources
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/**
	 * Gets the bean interceptor factory.
	 * 
	 * @return the bean interceptor factory
	 */
	public IBeanInterceptorFactory getBeanInterceptorFactory() {
		return beanInterceptorFactory;
	}

	/**
	 * Gets the gateway connector.
	 * 
	 * @return the gateway connector
	 */
	public IGatewayConnector getGatewayConnector() {
		return gatewayConnector;
	}

	/**
	 * Gets the bean id.
	 * 
	 * @param <T>
	 *            the
	 * @param bean
	 *            the bean
	 * @return the bean id
	 */
	@SuppressWarnings("unchecked")
	public <T> Object getBeanId(T bean) {
		PluginEntity<?> pluginEntity = pluginResources.getPluginEntityTree().getPluginEntity(bean.getClass());
		if (null != pluginEntity)
			return ((AEntityMetaModel<T>) pluginEntity.getEntityMetaModel()).getId(bean);
		return null;
	}

	/**
	 * Gets the cache key.
	 * 
	 * @param object
	 *            the object
	 * @return the cache key
	 */
	public MultiKey getCacheKey(Object object) {
		Object bean;
		if (object instanceof IBeanInterceptor)
			bean = ((IBeanInterceptor) object).adi$getEntity().getBean();
		else
			bean = object;
		@SuppressWarnings("rawtypes")
		AEntityMetaModel entityMM = pluginResources.getPluginEntityTree().getEntityMM(bean.getClass());
		Object id = entityMM.getId(bean);
		if (null == id)
			id = dataCache.fetchEntity(bean).getBeanId();
		return getCacheKey(entityMM.getEntityId(), id);
	}

	/**
	 * Gets the cache key.
	 * 
	 * @param entityId
	 *            the entity id
	 * @param identifier
	 *            the identifier
	 * @return the cache key
	 */
	public MultiKey getCacheKey(String entityId, Object identifier) {
		return new MultiKey(entityId, identifier);
	}

	/**
	 * Lock.
	 * 
	 * @param bindingService
	 *            the binding service
	 * @param status
	 *            the status
	 * @param entities
	 *            the entities
	 * @return true, if successful
	 */
	public boolean lock(ABindingService bindingService, int status, IEntity<?>... entities) {
		for (IEntity<?> entity : entities) {
			entity.lock(true, bindingService);
			entity.putStatus(status, false);
		}
		return true;
	}

	/**
	 * Save entities.
	 * 
	 * @param bindingService
	 *            the binding service
	 * @param entitySet
	 *            the entity set
	 * @return true, if successful
	 */
	public boolean saveEntities(ABindingService bindingService, Set<IEntity<?>> entitySet) {
		IEntity<?>[] entities = new IEntity<?>[entitySet.size()];
		int index = 0;
		for (IEntity<?> entity : entitySet)
			entities[index++] = entity;
		return saveEntities(bindingService, entities);
	}

	/**
	 * Save entities.
	 * 
	 * @param bindingService
	 *            the binding service
	 * @param entities
	 *            the entities
	 * @return true, if successful
	 */
	public abstract boolean saveEntities(ABindingService bindingService, IEntity<?>... entities);

	/**
	 * Refresh entities.
	 * 
	 * @param bindingService
	 *            the binding service
	 * @param unlock
	 *            the unlock
	 * @param entities
	 *            the entities
	 * @return true, if successful
	 */
	public abstract boolean refreshEntities(ABindingService bindingService, boolean unlock, IEntity<?>... entities);

	public void checkDirty(ABindingService bindingService) {
		Object boundedController = bindingService.getBoundedController();
		if (null != boundedController) {
			IRootController rootController = null;
			if (boundedController instanceof IRootController)
				rootController = (IRootController) boundedController;
			else if (boundedController instanceof AWidgetController) {
				IContainerController containerController = ((AWidgetController) boundedController).getGenCode().getContext()
						.getRootCore().getController();
				if (containerController instanceof IRootController) {
					rootController = (IRootController) containerController;
					((IRootController) containerController).setDirty(!bindingService.getUpdatedEntities().isEmpty());
				}
			}
			if (null != rootController)
				rootController.setDirty(!bindingService.getUpdatedEntities().isEmpty());
		}
	}

}