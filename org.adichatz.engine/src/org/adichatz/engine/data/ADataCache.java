/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 * 
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 * 
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.data;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.common.ejb.util.IEntityConstants.relationshipUpdateType;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LazyFetchManager;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.renderer.BasicTableRenderer;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Class ADataCache.
 * 
 * Manage all elements of the Adichatz Data cache that is to say
 * <ul>
 * <li>Relationship update: changes on Many-to-Many, or One-to-Many or
 * Many-to-One relationship.</li>
 * <li>Entity Wrapper: stores meta data on entities</li>
 * <li>Entities</li>
 * <li>Links between entites and tabulars</li>
 * </ul>
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ADataCache implements IDataCache {

	/** The data access. */
	protected ADataAccess dataAccess;

	/**
	 * Instantiates a new a data cache.
	 * 
	 * @param dataAccess the data access
	 */
	public ADataCache(ADataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	/**
	 * Gets the data access.
	 * 
	 * @return the data access
	 */
	public ADataAccess getDataAccess() {
		return dataAccess;
	}

	/**
	 * **************************************************************************************************************************.
	 */
	/**
	 * Relationship Updates
	 */

	/** The map. */
	private Map<MultiKey, List<RelationshipUpdate<?>>> rUpdateMap = new HashMap<MultiKey, List<RelationshipUpdate<?>>>();

	/**
	 * Gets the r update map.
	 * 
	 * @return the Relationship Update Map
	 */
	public Map<MultiKey, List<RelationshipUpdate<?>>> getRUpdateMap() {
		return rUpdateMap;
	}

	/**
	 * Ask for a new update on relationship in the cache.
	 * <ul>
	 * <li>If an "inverse" relationship update already exist, the "inverse" update
	 * is removes from cache and nothing is added.</li>
	 * <li>If the same relationship owns already an update, this update will be
	 * updated.</li>
	 * </ul>
	 * 
	 * @param <T>           the
	 * @param entity        the entity
	 * @param mappedByField the mapped by field
	 * @param oldParent     the old parent
	 * @param newParent     the new parent
	 * @param type          the type
	 * @param fieldName     the field name
	 * @return the relationship update
	 */
	public <T> RelationshipUpdate<T> doRelationshipUpdate(IEntity<T> entity, String mappedByField, Object oldParent,
			Object newParent, IEntityConstants.relationshipUpdateType type, String fieldName) {
		/**
		 * MultiKey for entity#relationshipUpdateMap is
		 * <ul>
		 * <li>(mappedByField, fieldName) for ManyTo One relationship (type =
		 * UPDATE_MANY_TO_ONE)</li>
		 * <li>(mappedByField, fieldName, newParent) when adding ManyToMany relationship
		 * (type = ADD_MANY_TO_MANY)</li>
		 * <li>(mappedByField, fieldName, oldParent) when removing ManyToMany
		 * relationship (type = REMOVE_MANY_TO_MANY)</li>
		 * </ul>
		 */
		MultiKey entityMultiKey = getEntityMultiKey(mappedByField, fieldName, type, oldParent, newParent);
		RelationshipUpdate<T> relationshipUpdate = entity.getRelationshipUpdateMap().get(entityMultiKey);
		if (null == relationshipUpdate) {
			/*
			 * Instantiates a new relationship update
			 * 
			 * tabularControllerByPKey, relationshipUpdates and entity#relationshipUpdateMap
			 * are touched by invoking RelationshipUpdate constructor
			 */
			relationshipUpdate = new RelationshipUpdate<T>(entity, mappedByField, oldParent, newParent, type,
					fieldName);
		} else {
			if (null != relationshipUpdate.getNewParentMKey()) {
				removeRelationshipUpdate(relationshipUpdate.getNewParentMKey(), relationshipUpdate);
				removeFromTabularControllersRU(relationshipUpdate, relationshipUpdate.getNewParentMKey());
			}
			if (null != newParent) {
				if (null != relationshipUpdate.getOldParentMKey()
						&& ((MultiKey) relationshipUpdate.getOldParentMKey().getKey(0)).getKey(1)
								.equals(dataAccess.getBeanId(newParent))) {
					removeRelationshipUpdate(relationshipUpdate.getOldParentMKey(), relationshipUpdate);
					addToTabularControllersRU(relationshipUpdate.getEntity(), relationshipUpdate.getOldParentMKey(),
							false);
					entity.getRelationshipUpdateMap().remove(entityMultiKey);
				} else {
					relationshipUpdate.setNewParentMKey(
							new MultiKey(dataAccess.getCacheKey(newParent), mappedByField, fieldName));
					addRelationshipUpdate(relationshipUpdate.getNewParentMKey(), relationshipUpdate);
					addToTabularControllersRU(relationshipUpdate.getEntity(), relationshipUpdate.getNewParentMKey(),
							false);
					entity.getRelationshipUpdateMap().put(entityMultiKey, relationshipUpdate);
				}
			}
		}
		return relationshipUpdate;
	}

	public static MultiKey getEntityMultiKey(String mappedByField, String fieldName, relationshipUpdateType type,
			Object oldParent, Object newParent) {
		switch (type) {
		case UPDATE_MANY_TO_ONE:
			return new MultiKey(mappedByField, fieldName);
		case ADD_MANY_TO_MANY:
			return new MultiKey(mappedByField, fieldName, newParent);
		case REMOVE_MANY_TO_MANY:
			return new MultiKey(mappedByField, fieldName, oldParent);
		default:
			break;
		}
		return null;
	}

	/**
	 * Adds the relationship update.
	 * 
	 * @param multiKey           the multi key
	 * @param relationshipUpdate the relationship update
	 */
	public void addRelationshipUpdate(MultiKey multiKey, RelationshipUpdate<?> relationshipUpdate) {
		List<RelationshipUpdate<?>> relationshipUpdates = rUpdateMap.get(multiKey);
		if (null == relationshipUpdates) {
			relationshipUpdates = new ArrayList<RelationshipUpdate<?>>();
			rUpdateMap.put(multiKey, relationshipUpdates);
		}
		relationshipUpdates.add(relationshipUpdate);
	}

	/**
	 * Removes the relationship update.
	 * 
	 * @param multiKey           the multi key
	 * @param relationshipUpdate the relationship update
	 */
	public void removeRelationshipUpdate(MultiKey multiKey, RelationshipUpdate<?> relationshipUpdate) {
		if (null != multiKey) {
			List<RelationshipUpdate<?>> relationshipUpdates = rUpdateMap.get(multiKey);
			if (null != relationshipUpdates) {
				relationshipUpdates.remove(relationshipUpdate);
				if (0 == relationshipUpdates.size())
					rUpdateMap.remove(multiKey);
				relationshipUpdate.getEntity().getRelationshipUpdateMap()
						.remove(ADataCache.getEntityMultiKey(multiKey.getString(1), relationshipUpdate.getFieldName(),
								relationshipUpdate.getType(), relationshipUpdate.getOldParentBean(),
								relationshipUpdate.getNewParentBean()));
			}
		}
	}

	/**
	 * **************************************************************************************************************************.
	 */
	/**
	 * Entity
	 */

	/** The entity by bean. */
	protected Map<MultiKey, IEntity<?>> cachedEntities = new HashMap<MultiKey, IEntity<?>>();

	/** The created entity by bean Id. */
	protected Map<Object, IEntity<?>> createdEntities = new HashMap<Object, IEntity<?>>();

	/**
	 * Gets the cached entities.
	 * 
	 * @return the cached entities
	 */
	public Map<MultiKey, IEntity<?>> getCachedEntities() {
		return cachedEntities;
	}

	@SuppressWarnings("unchecked")
	public <T> IEntity<T> getEntity(AEntityMetaModel<T> entityMM, Object beanId) {
		MultiKey cacheKey = dataAccess.getCacheKey(entityMM.getEntityId(), beanId);
		return (IEntity<T>) cachedEntities.get(cacheKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IDataCache#getCreatedEntities()
	 */
	/**
	 * Gets the created entities.
	 * 
	 * @return the created entities
	 */
	public Map<Object, IEntity<?>> getCreatedEntities() {
		return createdEntities;
	}

	/**
	 * Fetch entity.
	 * 
	 * @param <T>  the
	 * @param bean the bean
	 * @return the i entity< t>
	 */
	public abstract <T> IEntity<T> fetchEntity(T bean);

	public <T> IEntity<T> fetchEntity(T bean, ABindingService bindingService, String... lazyFetchMembers) {
		IEntity<T> entity = fetchEntity(bean);
		if (null != entity) {
			if (null != lazyFetchMembers && 0 < lazyFetchMembers.length)
				try {
					entity.refreshEntityIfNeeded(bindingService, lazyFetchMembers);
				} catch (AdiPMException e) {
					logError(e);
				}
			if (null != bindingService)
				entity.getBindingServices().add(bindingService);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.cache.IDataCache#fetchTransientEntity(java.lang.Object,
	 * org.adichatz.engine.validation.ABindingService, java.lang.String[])
	 */
	/**
	 * Fetch transient entity.
	 * 
	 * @param <T>              the
	 * @param bean             the bean
	 * @param bindingService   the binding service
	 * @param lazyFetchMembers the lazy fetch members
	 * @return the i entity
	 * @throws Exception the exception
	 */
	public <T> IEntity<T> fetchTransientEntity(T bean, ABindingService bindingService, String... lazyFetchMembers)
			throws Exception {
		IEntity<T> entity = fetchEntity(bean);
		if (null != lazyFetchMembers && 0 < lazyFetchMembers.length) {
			LazyFetchManager<T> lazyFetchManager = new LazyFetchManager<T>(entity);
			LazyFetchManager<T> currentLazyFetchManager = entity.getLazyFetchManager();
			entity.refreshEntityIfNeeded(bindingService, lazyFetchMembers);
			lazyFetchManager.getLazyFetchMemberMap().clear();
			lazyFetchManager = null;
			entity.setLazyFetchManager(currentLazyFetchManager);
		}
		return entity;
	}

	/**
	 * **************************************************************************************************************************.
	 */
	/**
	 * Entity Tabular Link
	 */

	/** The tabular controller by parent cache key. */
	private Map<MultiKey, Set<ATabularController<?>>> tabularControllerByPKey = new HashMap<MultiKey, Set<ATabularController<?>>>();

	/** The tabular controller by cache key. */
	private Map<MultiKey, Set<ATabularController<?>>> tabularControllerByCKey = new HashMap<MultiKey, Set<ATabularController<?>>>();

	/**
	 * Gets the tabular controller by parent key.
	 * 
	 * @return the tabular controller by parent key
	 */
	public Map<MultiKey, Set<ATabularController<?>>> getTabularControllerByPKey() {
		return tabularControllerByPKey;
	}

	/**
	 * Gets the tabular controller by c key.
	 * 
	 * @return the tabular controller by c key
	 */
	public Map<MultiKey, Set<ATabularController<?>>> getTabularControllerByCKey() {
		return tabularControllerByCKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.cache.IDataCache#getTabularControllerByCKey(org.adichatz.
	 * engine.cache.IEntity)
	 */
	/**
	 * Gets the tabular controller by c key.
	 * 
	 * @param entity the entity
	 * @return the tabular controller by c key
	 */
	public Set<ATabularController<?>> getTabularControllerByCKey(IEntity<?> entity) {
		return tabularControllerByCKey.get(entity.getCacheKey());
	}

	/**
	 * Gets the tabular controllers by multiKey
	 * (parentEntity#cacheKey(),mappedByField.
	 * 
	 * @param parentKey         the parent key
	 * @param tabularController the tabular controller
	 */
	public void addTabularControllerRU(MultiKey parentKey, ATabularController<?> tabularController) {
		Set<ATabularController<?>> tabularControllers = tabularControllerByPKey.get(parentKey);

		if (null == tabularControllers) {
			tabularControllers = new HashSet<ATabularController<?>>();
			tabularControllerByPKey.put(parentKey, tabularControllers);
		}
		tabularControllers.add(tabularController);
	}

	/**
	 * Adds the to tabular controllers.
	 * 
	 * @param <T>       the
	 * @param entity    the entity
	 * @param parentKey the multi key
	 */
	@SuppressWarnings("unchecked")
	public <T> void addToTabularControllersRU(IEntity<T> entity, MultiKey parentKey, boolean update) {
		if (null != parentKey) {
			Set<ATabularController<?>> tabularControllers = tabularControllerByPKey.get(parentKey);
			if (null != tabularControllers)
				for (ATabularController<?> tabularController : tabularControllers) {
					ATabularContentProvider<T> tabularContentProvider = (ATabularContentProvider<T>) tabularController
							.getViewer().getContentProvider();
					tabularController.setSelectInNewThread(false);
					tabularContentProvider.addEntity(entity, true); // needed when adding entity
					tabularController.setSelectInNewThread(true);
					tabularController.showStatusBar();
					if (update) // When entity is refreshed (from server), update method recomputes tabular
								// renderer
						tabularController.update(entity, entity.getBean());
				}
		}
	}

	/**
	 * Removes the tabular controller.
	 * 
	 * @param parentKey         the parent key
	 * @param tabularController the tabular controller
	 */
	public void removeTabularControllerRU(MultiKey parentKey, ATabularController<?> tabularController) {
		Set<ATabularController<?>> tabularControllers = tabularControllerByPKey.get(parentKey);
		if (null != tabularControllers) {
			tabularControllers.remove(tabularController);
			if (0 == tabularControllers.size())
				tabularControllerByPKey.remove(parentKey);
		}
	}

	/**
	 * Removes the from tabular controllers.
	 * 
	 * @param <T>                the
	 * @param relationshipUpdate the relationship update
	 * @param parentKey          the multi key
	 */
	@SuppressWarnings("unchecked")
	public <T> void removeFromTabularControllersRU(RelationshipUpdate<T> relationshipUpdate, MultiKey parentKey) {
		if (null != parentKey) {
			Set<ATabularController<?>> tabularControllers = tabularControllerByPKey.get(parentKey);
			if (null != tabularControllers)
				for (ATabularController<?> tabularController : tabularControllers) {
					tabularController.setSelectInNewThread(false);
					IEntity<T> entity = relationshipUpdate.getEntity();
					((ATabularContentProvider<T>) tabularController.getViewer().getContentProvider())
							.removeEntity(entity, true);
					tabularController.setSelectInNewThread(true);
					BasicTableRenderer<?> tableRenderer = tabularController.getTableRenderer();
					tableRenderer.removeErrorBean(entity.getBean());
					tableRenderer.removeUpdatedBean(entity.getBean());
					tabularController.showStatusBar();
				}
		}
	}

	/**
	 * Adds the tabular controller for cache key.
	 * 
	 * @param cacheKey          the cache key
	 * @param tabularController the tabular controller
	 */
	public void addTabularCtlerForCKey(MultiKey cacheKey, ATabularController<?> tabularController) {
		Set<ATabularController<?>> tabularControllers = tabularControllerByCKey.get(cacheKey);

		if (null == tabularControllers) {
			tabularControllers = new HashSet<ATabularController<?>>();
			tabularControllerByCKey.put(cacheKey, tabularControllers);
		}
		tabularControllers.add(tabularController);
	}

	/**
	 * Removes the tabular ctler for c key.
	 * 
	 * @param cacheKey          the cache key
	 * @param tabularController the tabular controller
	 */
	public void removeTabularCtlerForCKey(MultiKey cacheKey, ATabularController<?> tabularController) {
		Set<ATabularController<?>> tabularControllers = tabularControllerByCKey.get(cacheKey);
		if (null != tabularControllers) {
			tabularControllers.remove(tabularController);
			if (0 == tabularControllers.size())
				tabularControllerByCKey.remove(cacheKey);
		}
	}

	/**
	 * Put new entity.
	 * 
	 * @param <T>       the
	 * @param beanClass the bean class
	 * @return the i entity< t>
	 */
	public abstract <T> IEntity<T> putNewEntity(Class<T> beanClass);

	public abstract <T> IEntity<T> fetchEntity(AEntityMetaModel<T> entityMM, Object beanId, String... lazyFetchMembers);

	public void fireChangeOnEntitySet() {
	}
}
