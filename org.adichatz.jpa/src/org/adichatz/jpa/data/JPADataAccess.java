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
package org.adichatz.jpa.data;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.EntityNotFoundException;

import org.adichatz.common.ejb.AEntityCallfore;
import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.HomeLazyNode;
import org.adichatz.common.ejb.ManyToManyUpdate;
import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.ProxyEntity;
import org.adichatz.common.ejb.ProxyTransaction;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.common.ejb.Session;
import org.adichatz.common.ejb.remote.IAdiLockManager;
import org.adichatz.common.ejb.remote.IAdiPersistenceManager;
import org.adichatz.common.ejb.util.AdiLock;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.MessageUtility;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.IBeanInterceptorFactory;
import org.adichatz.engine.data.IGatewayConnector;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.BoundedPartFatalError;
import org.adichatz.engine.e4.part.BoundedPartFatalError.ERROR_TYPE;
import org.adichatz.engine.e4.resource.PartBindingService;
import org.adichatz.engine.extra.AChangesActionDialog;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.jpa.editor.EntityEditorPart;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class ADataBaseTools.
 * 
 * Only link between Adichatz engine and Adichatz JPA connector
 * 
 * Four main methods
 * <ul>
 * <li><a>find</a> for fetching one bean knowing its identifier.</li>
 * <li><a>getResultList</a> for retrieving a collection of beans.</li>
 * <li><a>saveEntities</a> to save a set of entities.</li>
 * <li><a>refreshEntities</a> to refresh (rolling back if needed) a set of entities</li>
 * </ul>
 * 
 * @author Yves Drumbonnet
 */
public class JPADataAccess extends ADataAccess {

	/** The adichatz persistence manager. */
	private IAdiPersistenceManager adichatzPersistenceManager;

	/** The proxy key. */
	private int proxyKey;

	protected Map<Object, JPAEntity<?>> entityMap = new HashMap<Object, JPAEntity<?>>();

	/** The adichatz lock manager. */
	private IAdiLockManager adichatzLockManager;

	/** Store cache key od deleted entities to avoid creation of bean with same id. */
	protected Set<MultiKey> deletedEntityCacheKeys = new HashSet<MultiKey>();

	/** Store dirty entities (MERGE or REMOVE) */
	protected Set<JPAEntity<?>> dirtyEntities = new HashSet<>();

	protected Session session;

	/**
	 * Instantiates a new jPA data access.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param beanInterceptorFactory
	 *            the bean interceptor factory
	 * @param gatewayConnector
	 *            the gateway connector
	 */
	public JPADataAccess(AdiPluginResources pluginResources, IBeanInterceptorFactory beanInterceptorFactory,
			IGatewayConnector gatewayConnector) {
		super(pluginResources);
		dataCache = new JPADataCache(this);
		this.gatewayConnector = gatewayConnector;
		this.beanInterceptorFactory = beanInterceptorFactory;
		session = AdichatzApplication.getInstance().getContextValue(Session.class);
	}

	/**
	 * Gets the persistence manager.
	 * 
	 * @return the persistence manager
	 */
	public IAdiPersistenceManager getPersistenceManager() {
		if (null == adichatzPersistenceManager)
			try {
				if (null == session) {
					throw new AdiApplicationServerException(getFromJpaBundle("AS.no.session"));
				}
				adichatzPersistenceManager = gatewayConnector.getPersistenceManager();
			} catch (Exception e) {
				String message = getFromJpaBundle("AS.access.error.message");
				displayError(getFromJpaBundle("AS.access.error.title"), e, message);
				throw new AdiApplicationServerException(e);
			}
		return adichatzPersistenceManager;
	}

	@SuppressWarnings("unchecked")
	public <T> IEntity<T> findEntity(String entityURI, Object beanId, String... lazyFetchMembers) {
		AEntityMetaModel<T> entityMM = (AEntityMetaModel<T>) pluginResources.getPluginEntityTree().getEntityMM(entityURI);
		T bean = (T) findProxyEntity(entityMM.getBeanClass(), beanId, lazyFetchMembers).getBean();
		IEntity<T> entity = dataCache.fetchEntity(bean);
		return entity;
	}

	public <T> IEntity<T> findEntity(ProxyEntity<T> proxyEntity) {
		ProxyEntity<T> proxyResult = findImpl(proxyEntity);
		if (null == proxyResult)
			return null;
		IEntity<T> entity = dataCache.fetchEntity(proxyEntity.getBean());
		return entity;
	}

	/**
	 * Find proxy entity.
	 * 
	 * @param reference
	 *            the reference
	 * @param beanId
	 *            the bean id
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 * @return the proxy entity
	 */
	public <T> ProxyEntity<T> findProxyEntity(Object reference, Object beanId, String... lazyFetchMembers) {
		return findProxyEntity(reference, beanId, false, lazyFetchMembers);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> ProxyEntity<T> findProxyEntity(Object reference, Object beanId, boolean nullwhenNotFound,
			String... lazyFetchMembers) {
		Class<?> beanClass = null;
		if (reference instanceof Class<?>)
			beanClass = (Class<?>) reference;
		else if (reference instanceof String)
			beanClass = pluginEntityTree.getEntityMM((String) reference).getBeanClass();
		if (null == beanClass) {
			displayError("Invalid invocation of " + this.getClass().getName() + ".find(Object, Object)",
					"Syntax is find ([Class | String],beanId)");
			return null;
		} else
			return findImpl(new ProxyEntity(beanClass, beanId, nullwhenNotFound, getHomeLazyNode(lazyFetchMembers)));
	}

	private <T> ProxyEntity<T> findImpl(ProxyEntity<T> proxyentity) {
		try {
			return getPersistenceManager().find(session, proxyentity);
		} catch (AdiApplicationServerException e) {
			// Display error already done
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (AdiPMException e) {
			displayError(getFromJpaBundle("jpa.errorFindingEntity"), e, errorMessage(e, e.getBean()));
		} catch (Exception e) {
			Throwable throwable = gatewayConnector.extractException(e);
			displayError(getFromJpaBundle("jpa.errorFetchingEntity"), e, errorMessage(throwable, null),
					throwable.getCause().toString());
		}
		return null;
	}

	/**
	 * Gets the home lazy node.
	 * 
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 * @return the home lazy node
	 */
	private HomeLazyNode getHomeLazyNode(String... lazyFetchMembers) {
		if (0 == lazyFetchMembers.length)
			return null;
		HomeLazyNode rootLazyNode = new HomeLazyNode(null);
		/*
		 * Creates tree of lazy nodes following to given lazyFetch members.
		 */
		for (String lazyFetchMember : lazyFetchMembers) {
			StringTokenizer tokenizer = new StringTokenizer(lazyFetchMember, ".");
			HomeLazyNode parentNode = rootLazyNode;
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken().trim();
				HomeLazyNode childNode = getChild(parentNode, token);
				if (null == childNode) {
					childNode = new HomeLazyNode(lazyFetchMember, token);
					parentNode.getChildren().add(childNode);
				}
				parentNode = childNode;
			}
		}
		return rootLazyNode;
	}

	/**
	 * Gets the child.
	 * 
	 * @param parentNode
	 *            the parent node
	 * @param fieldName
	 *            the field name
	 * @return the child
	 */
	private HomeLazyNode getChild(HomeLazyNode parentNode, String fieldName) {
		for (HomeLazyNode lazyNode : parentNode.getChildren())
			if (lazyNode.getFieldName().equals(fieldName))
				return lazyNode;
		return null;
	}

	/**
	 * Exec update query.
	 * 
	 * @param queryStr
	 *            the query str
	 * @return the int
	 */
	public int execUpdateQuery(AdiQuery adiQuery) {
		try {
			return getPersistenceManager().execUpdateQuery(adiQuery);
		} catch (AdiPMException e) {
			displayError(getFromJpaBundle("jpa.errorFindingEntity"), e, errorMessage(e, e.getBean()));
			return -1;
		}
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
	@SuppressWarnings({ "unchecked", "incomplete-switch" })
	@Override
	public boolean saveEntities(ABindingService bindingService, IEntity<?>... entities) {
		/** The entity map. */
		entityMap.clear();

		ABindingService.setSynchronizing(true);

		boolean processed = true;
		proxyKey = 0;
		try {
			List<ProxyEntity<?>> proxyEntities = new ArrayList<ProxyEntity<?>>();
			for (IEntity<?> entity : entities) {
				entity.fireListener(IEventType.PRE_SAVE);
				proxyEntities.add(newProxyEntity((JPAEntity<?>) entity, IEntityConstants.PERSIST != entity.getStatus()));
				if (null != entity.getLazyFetchManager())
					entity.getLazyFetchManager().getRootLazyNode().setToFetchParent(false);
			}

			/*
			 * Prepares process form ManyToMany updates if needed.
			 */
			for (ProxyEntity<?> proxyEntity : proxyEntities)
				if (IEntityConstants.MERGE == proxyEntity.getStatus()) {
					JPAEntity<?> entity = entityMap.get(proxyEntity.getProxyKey());
					for (RelationshipUpdate<?> relationshipUpdate : entity.getRelationshipUpdateMap().values()) {
						switch (relationshipUpdate.getType()) {
						case ADD_MANY_TO_MANY:
							for (int i = 0; i < entityMap.size(); i++) {
								if (entityMap.get(i).getCacheKey().equals(relationshipUpdate.getNewParentMKey().getKey(0))) {
									proxyEntity.getManyToManyUpdates().add(new ManyToManyUpdate(relationshipUpdate.getFieldName(),
											relationshipUpdate.getMappedByField(), relationshipUpdate.getType(), i));
									break;
								}
							}
							break;
						case REMOVE_MANY_TO_MANY:
							for (int i = 0; i < entityMap.size(); i++) {
								if (entityMap.get(i).getCacheKey().equals(relationshipUpdate.getOldParentMKey().getKey(0))) {
									proxyEntity.getManyToManyUpdates().add(new ManyToManyUpdate(relationshipUpdate.getFieldName(),
											relationshipUpdate.getMappedByField(), relationshipUpdate.getType(), i));
									break;
								}
							}
							break;
						}
					}
				}

			/**
			 ************************************************ 
			 * Persistence Manager call for saving entities *
			 ************************************************ 
			 */
			preSaveEntities();
			ProxyTransaction proxyTransaction = new ProxyTransaction(session, pluginResources.getPluginName());
			proxyTransaction.setProxyEntities(proxyEntities);
			proxyTransaction = getPersistenceManager().saveEntities(proxyTransaction);
			for (JPAEntity<?> jpaEntity : entityMap.values())
				postForecall(jpaEntity);

			for (ProxyEntity<?> proxyEntity : proxyTransaction.getProxyEntities()) {
				JPAEntity<?> entity = entityMap.get(proxyEntity.getProxyKey());
				if (proxyEntity.getStatus() == IEntityConstants.REMOVE) {
					entity.removeFromCache();
					if (!entity.getEntityMM().isOneToOne())
						// Store cache key to avoid creation of bean with same id except if it is a One To One bean.
						deletedEntityCacheKeys.add(entity.getCacheKey());
				} else {
					if (IEntityConstants.PERSIST == entity.getStatus()) {
						// Remove reference to temp cacheKey and add new cacheKey whith new identifier
						dataCache.getCachedEntities().remove(entity.getCacheKey());
						dataCache.getCreatedEntities().remove(entity.getBean());
						MultiKey createdCacheKey = getCacheKey(proxyEntity.getBean());
						Object beanId = pluginResources.getPluginEntityTree().getEntityMM(proxyEntity.getBean().getClass())
								.getId(proxyEntity.getBean());
						entity.setBeanId(beanId);
						entity.getEntityMM().setId(entity.getBeanInterceptor(), beanId);

						Set<ATabularController<?>> tablecontrollers = dataCache.getTabularControllerByCKey(entity);
						if (null != tablecontrollers) {
							for (ATabularController<?> tableController : tablecontrollers) {
								EntitySetContentProvider<?> contentProvider = (EntitySetContentProvider<?>) tableController
										.getViewer().getContentProvider();
								@SuppressWarnings("rawtypes")
								Map beanMap = contentProvider.getBeanMap();
								Object bean = beanMap.get(entity.getCacheKey());
								entity.getEntityMM().setId(bean, beanId);
								beanMap.remove(entity.getCacheKey());
								beanMap.put(createdCacheKey, bean);
								dataCache.addTabularCtlerForCKey(createdCacheKey, tableController);
								tableController.getViewer().update(bean, null);
							}
							tablecontrollers.clear();
							dataCache.getTabularControllerByCKey().remove(entity.getCacheKey());
						}

						entity.setCacheKey(createdCacheKey);
						dataCache.getCachedEntities().put(entity.getCacheKey(), entity);
					}
					proxyEntity.setStatus(IEntityConstants.RETRIEVE);
					ABindingService.setSynchronizing(false);
					entity.postRefresh(bindingService, proxyEntity);
					ABindingService.setSynchronizing(true);
					if (IEntityConstants.PERSIST == entity.getStatus())
						// removes application lock (no lock on the server because it was a created bean).
						entity.lock(false, null);
					else if (proxyEntity.isFireUnlock())
						/**
						 * Entity is unlocked in the application<br>
						 * unlock must be fired before changing status for test in hanleEvent on action listener
						 */
						if (entity.isLocked())
							entity.lock(false, null);
					entity.putStatus(IEntityConstants.RETRIEVE, true);
					entity.getBeanInterceptor().fireImpactedTabularsChange();

					/*
					 * Deletes cached relationship updates so that update are definitive.
					 * 
					 * A table is used to avoid ConcurrentModificationException
					 */
					RelationshipUpdate<?>[] relationshipUpdates = entity.getRelationshipUpdateMap().values()
							.toArray(new RelationshipUpdate[entity.getRelationshipUpdateMap().size()]);
					for (RelationshipUpdate<?> relationshipUpdate : relationshipUpdates) {
						dataCache.removeRelationshipUpdate(relationshipUpdate.getNewParentMKey(), relationshipUpdate);
						dataCache.removeRelationshipUpdate(relationshipUpdate.getOldParentMKey(), relationshipUpdate);
					}
					entity.getRelationshipUpdateMap().clear();
				}
				entity.fireListener(IEventType.POST_SAVE);
				if (proxyEntity.getStatus() == IEntityConstants.REMOVE)
					entity.finalizeRemove();
			}
		} catch (AdiPMException e) {
			displayError("Error saving object", e, errorMessage(e, e.getBean()));
			processed = false;
		} catch (Exception e) {
			Throwable throwable = gatewayConnector.extractException(e);
			if (null == throwable)
				throwable = e;
			Throwable cause = throwable.getCause();
			if (null == cause)
				displayError("Error saving object", e, errorMessage(throwable, null));
			else
				displayError("Error saving object", e, errorMessage(throwable, null), cause.toString());
			processed = false;
		} finally {
			if (null != bindingService)
				checkDirty(bindingService);
			ABindingService.setSynchronizing(false);
		}
		return processed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.ADataAccess#getDataCache()
	 */
	@Override
	public JPADataCache getDataCache() {
		return (JPADataCache) super.getDataCache();
	}

	/**
	 * pre (before) save entities.
	 * 
	 * Do nothing. Extend JPADataAccess to add specific process before sending proxy entities to server
	 */
	protected void preSaveEntities() {
		for (JPAEntity<?> jpaEntity : entityMap.values())
			preForecall(jpaEntity);
	}

	private <U extends Object> void preForecall(JPAEntity<U> jpaEntity) {
		List<AEntityCallfore<U>> callfores = getCallforeInstances(jpaEntity);
		if (null != callfores)
			for (AEntityCallfore<U> callfore : callfores)
				switch (jpaEntity.getStatus()) {
				case IEntityConstants.MERGE:
					callfore.preMerge(jpaEntity.getBean());
					break;
				case IEntityConstants.PERSIST:
					callfore.prePersist(jpaEntity.getBean());
					break;
				case IEntityConstants.REMOVE:
					callfore.preRemove(jpaEntity.getBean());
					break;
				}
	}

	@SuppressWarnings("unchecked")
	private <U extends Object> List<AEntityCallfore<U>> getCallforeInstances(JPAEntity<U> jpaEntity) {
		List<Class<? extends AEntityCallfore<U>>> callforeClasses = jpaEntity.getEntityMM().getCallforeClasses();
		if (null != callforeClasses) {
			List<AEntityCallfore<U>> callforeInstances = new ArrayList<>(callforeClasses.size());
			for (Class<? extends AEntityCallfore<U>> callforeClass : callforeClasses) {
				callforeInstances
						.add((AEntityCallfore<U>) ReflectionTools.instantiateClass(callforeClass, new Class[] {}, new Object[] {}));
			}
			return callforeInstances;
		}
		return null;
	}

	private <U extends Object> void postForecall(JPAEntity<U> jpaEntity) {
		List<AEntityCallfore<U>> callfores = getCallforeInstances(jpaEntity);
		if (null != callfores)
			for (AEntityCallfore<U> callfore : callfores)
				switch (jpaEntity.getStatus()) {
				case IEntityConstants.MERGE:
					callfore.postMerge(jpaEntity.getBean());
					break;
				case IEntityConstants.PERSIST:
					callfore.postPersist(jpaEntity.getBean());
					break;
				case IEntityConstants.REMOVE:
					callfore.postRemove(jpaEntity.getBean());
					break;
				}
	}

	public ProxyTransaction batchRequests(ABindingService bindingService, List<AdiQuery> adiQueries, List<IEntity<?>> entities,
			boolean unlock) {
		ProxyTransaction proxyTransaction;
		boolean hasEntities = null != entities && !entities.isEmpty();
		proxyTransaction = new ProxyTransaction(session, pluginResources.getPluginName(), unlock);
		if (hasEntities) {
			List<ProxyEntity<?>> proxyEntities = preRefreshEntities(bindingService, unlock,
					entities.toArray(new IEntity<?>[entities.size()]));
			proxyTransaction.setProxyEntities(proxyEntities);
		}
		if (null != adiQueries && !adiQueries.isEmpty())
			proxyTransaction.setProxyQueries(adiQueries);
		try {
			proxyTransaction = getPersistenceManager().batchRequests(proxyTransaction);
			if (hasEntities)
				postRefreshEntitties(bindingService, proxyTransaction, unlock);
		} catch (AdiPMException e) {
			displayError(getFromJpaBundle("jpa.errorRefreshingEntity"), e, errorMessage(e, e.getBean()));
			ABindingService.setSynchronizing(false);
		} catch (Exception e) {
			e.printStackTrace();
			Throwable throwable = gatewayConnector.extractException(e);
			ABindingService.setSynchronizing(false);
			displayError(getFromJpaBundle("jpa.errorRefreshingEntity"), e, errorMessage(throwable, null),
					throwable.getCause().toString());
		}
		return proxyTransaction;
	}

	public List<ProxyEntity<?>> preRefreshEntities(ABindingService bindingService, boolean unlock, IEntity<?>... entities) {
		entityMap.clear();
		proxyKey = 0;
		List<ProxyEntity<?>> proxyEntities = new ArrayList<ProxyEntity<?>>();
		for (final IEntity<?> entity : entities) {
			if (IEntityConstants.PERSIST == entity.getStatus()) {
				if (unlock) {
					entity.fireListener(IEventType.PRE_REFRESH);
					entity.fireListener(IEventType.POST_REFRESH);
					entity.lock(false, bindingService);
					entity.removeFromCache();
				}
			} else {
				proxyEntities.add(newProxyEntity((JPAEntity<?>) entity,
						entity.isLocked() && entity.getLockingBindingService().equals(bindingService) && unlock));
				entity.fireListener(IEventType.PRE_REFRESH);
			}
		}
		return proxyEntities;
	}

	private void postRefreshEntitties(ABindingService bindingService, ProxyTransaction proxyTransaction, boolean unlock) {
		for (ProxyEntity<?> proxyEntity : proxyTransaction.getProxyEntities()) {
			final JPAEntity<?> entity = entityMap.get(proxyEntity.getProxyKey());

			if (IEntityConstants.TODELETE == proxyEntity.getStatus()) {
				entity.putStatus(IEntityConstants.TODELETE, true);
				if (bindingService instanceof PartBindingService) {
					BoundedPart part = (BoundedPart) ((PartBindingService) bindingService).getEditor();
					Set<IEntity<?>> updatedEntities = part.getBoundedPartChangeManager().getUpdatedEntities();
					updatedEntities.add(entity);
					if (part instanceof EntityEditorPart && ((EntityEditorPart) part).getEntity().equals(entity)) {
						new BoundedPartFatalError(part, ERROR_TYPE.mainEntityNoLongerExists, () -> {
							new AChangesActionDialog(updatedEntities, part.getPluginResources(),
									getFromJpaBundle("jpa.error.deleted.bean"),
									getFromJpaBundle("jpa.error.bean.deleted.on.database", entity.getEntityMM().getEntityId(),
											entity.getBeanId()),
									getFromJpaBundle("jpa.error.lost.modifications")).open();
						});
						return;
					} else
						displayError(getFromJpaBundle("jpa.error.deleted.bean"), getFromJpaBundle(
								"jpa.error.bean.deleted.on.database", entity.getEntityMM().getEntityId(), entity.getBeanId()));
				}
			} else {
				entity.postRefresh(bindingService, proxyEntity);
				if (proxyEntity.isFireUnlock())
					/**
					 * Entity is unlocked in the application<br>
					 * unlock must be fired before changing status for test in handleEvent on action listener
					 */
					if (entity.isLocked())
						((JPAEntity<?>) entity).lock(false, null);
				entity.putStatus(!unlock ? IEntityConstants.NULL : IEntityConstants.RETRIEVE, true);
				entity.getBeanInterceptor().fireImpactedTabularsChange();
			}
			if (!entity.getRelationshipUpdateMap().isEmpty()) {
				RelationshipUpdate<?>[] relationshipUpdates = entity.getRelationshipUpdateMap().values()
						.toArray(new RelationshipUpdate[entity.getRelationshipUpdateMap().size()]);
				for (RelationshipUpdate<?> relationshipUpdate : relationshipUpdates) {
					/*
					 * Reinitialization of ManyToMany relationship update is needed. Add an inverse relationship update for firing
					 * cancellation and removing old relationship update
					 */
					switch (relationshipUpdate.getType()) {
					case ADD_MANY_TO_MANY:
						dataCache.removeRelationshipUpdate(relationshipUpdate.getNewParentMKey(), relationshipUpdate);
						dataCache.removeFromTabularControllersRU(relationshipUpdate, relationshipUpdate.getNewParentMKey());
						break;
					case REMOVE_MANY_TO_MANY:
						dataCache.removeRelationshipUpdate(relationshipUpdate.getOldParentMKey(), relationshipUpdate);
						dataCache.addToTabularControllersRU(relationshipUpdate.getEntity(), relationshipUpdate.getOldParentMKey(),
								true);
						break;
					case UPDATE_MANY_TO_ONE:
						dataCache.doRelationshipUpdate(entity,
								null == relationshipUpdate.getOldParentMKey() ? null
										: (String) relationshipUpdate.getMappedByField(),
								null, relationshipUpdate.getOldParentBean(), relationshipUpdate.getType(),
								relationshipUpdate.getFieldName());
						break;
					}
				}
			}
			entity.fireListener(IEventType.POST_REFRESH);
		}

	}

	@Override
	public boolean refreshEntities(ABindingService bindingService, boolean unlock, IEntity<?>... entities) {
		boolean checkDirty = false;
		if (null != bindingService)
			for (IEntity<?> entity : entities)
				if (IEntityConstants.RETRIEVE != entity.getStatus()) {
					checkDirty = true;
					break;
				}
		try {
			List<ProxyEntity<?>> proxyEntities = preRefreshEntities(bindingService, unlock, entities);
			ProxyTransaction proxyTransaction = new ProxyTransaction(session, pluginResources.getPluginName(), unlock);
			proxyTransaction.setProxyEntities(proxyEntities);
			proxyTransaction = getPersistenceManager().refreshEntities(proxyTransaction);
			postRefreshEntitties(bindingService, proxyTransaction, unlock);
			for (IEntity<?> entity : entities)
				entity.setInitialized(true);
			return true;
		} catch (AdiPMException e) {
			displayError(getFromJpaBundle("jpa.errorRefreshingEntity"), e, errorMessage(e, e.getBean()));
			ABindingService.setSynchronizing(false);
		} catch (Exception e) {
			e.printStackTrace();
			Throwable throwable = gatewayConnector.extractException(e);
			ABindingService.setSynchronizing(false);
			displayError(getFromJpaBundle("jpa.errorRefreshingEntity"), e, errorMessage(throwable, null),
					throwable.getCause().toString());
		} finally {
			if (checkDirty)
				checkDirty(bindingService);
		}
		return false;
	}

	/**
	 * Copy home lazy fetch.
	 * 
	 * @param parentHomeNode
	 *            the parent home node
	 * @param parentLocalNode
	 *            the parent local node
	 * @param entityMM
	 *            the entity MetaModel
	 */
	private void copyHomeLazyFetch(HomeLazyNode parentHomeNode, LocalLazyNode parentLocalNode, AEntityMetaModel<?> entityMM) {
		for (Map.Entry<String, LocalLazyNode> entry : parentLocalNode.getChildrenMap().entrySet()) {
			HomeLazyNode homeNode;
			LocalLazyNode lazyNode = entry.getValue();
			homeNode = new HomeLazyNode(entry.getKey(), lazyNode.getLazyField().getFieldName(), lazyNode.isInitializeOTO());
			AEntityMetaModel<?> childEntityMM;
			if (lazyNode.getLazyField() instanceof RefField)
				childEntityMM = ((RefField<?>) lazyNode.getLazyField()).getParentEntityMM();
			else
				childEntityMM = lazyNode.getLazyField().getEntityMM();
			copyHomeLazyFetch(homeNode, entry.getValue(), childEntityMM);
			parentHomeNode.getChildren().add(homeNode);
		}
		//	}
	}

	/**
	 * Instances new proxy entity to be sent to the server.
	 * 
	 * @param <T>
	 *            the
	 * @param entity
	 *            the entity
	 * @param fireUnlock
	 *            the fire unlock
	 * @return the proxy entity< t>
	 */
	private <T> ProxyEntity<T> newProxyEntity(JPAEntity<T> entity, boolean fireUnlock) {
		ProxyEntity<T> proxyEntity = new ProxyEntity<T>(entity.getBean(), entity.getBeanClass(), entity.getBeanId(), proxyKey,
				entity.getStatus(), entity.getCompositeKeyStrategy(), fireUnlock);
		if (IEntityConstants.PERSIST >= entity.getStatus())
			entity.cloneBeforeBean();
		entityMap.put(proxyKey++, entity);
		HomeLazyNode rootLazyNode = new HomeLazyNode(entity.getBean());
		copyHomeLazyFetch(rootLazyNode, entity.getLazyFetchManager().getRootLazyNode(), entity.getEntityMM());
		proxyEntity.setRootLazyNode(rootLazyNode);
		return proxyEntity;
	}

	/**
	 * Error message.
	 * 
	 * @param throwrable
	 *            the throwrable
	 * @param bean
	 *            the bean
	 * 
	 * @return the string
	 */
	private String errorMessage(Throwable throwrable, Object bean) {
		String localizedMessage = throwrable.getLocalizedMessage();
		if (null == localizedMessage)
			localizedMessage = EngineTools.getErrorString(throwrable);
		if (null != bean)
			return localizedMessage + " - " + getFromEngineBundle("entity") + ": " + bean.getClass().getSimpleName() + ", "
					+ getFromEngineBundle("identifier") + ": " + getBeanId(bean);
		else
			return localizedMessage;
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
	@Override
	public boolean lock(ABindingService bindingService, int status, IEntity<?>... entities) {
		List<AdiLock> adiLocks = new ArrayList<AdiLock>();
		for (int i = 0; i < entities.length; i++) {
			if (entities[i].isLocked()) {
				if (!bindingService.equals(entities[i].getLockingBindingService())) {
					MessageUtility.displayEntityMessage(entities[i], getFromEngineBundle("detail.entityLockedInOtherEnvironment"),
							getFromJpaBundle("detail.entityCannotBeLockMessage"), true);
					return false;
				}
			} else {
				if (!entities[i].getEntityMM().getPluginEntity().checkPrivilege(status)) {
					MessageUtility.displayEntityMessage(entities[i], getFromEngineBundle("notAllowedOperation"),
							getFromJpaBundle(getMessage(status)), false);
					return false;
				}
				adiLocks.add(new AdiLock(entities[i].getBeanClass(), entities[i].getBeanId(), session));
			}
		}
		if (0 == adiLocks.size() || getAdichatzLockManager().lock(adiLocks.toArray(new AdiLock[adiLocks.size()]))) {
			super.lock(bindingService, status, entities);
			return true;
		} else {
			EngineTools.openDialog(MessageDialog.ERROR, getFromJpaBundle("detail.entityCannotBeLockTitle"),
					getFromJpaBundle("detail.entityCannotBeLockMessage"));
		}
		return false;

	}

	/**
	 * Gets the message.
	 * 
	 * @param status
	 *            the status
	 * 
	 * @return the message
	 */
	private String getMessage(int status) {
		switch (status) {
		case IEntityConstants.MERGE:
			return "jpa.entityCannotBeUpdateMessage";
		case IEntityConstants.REMOVE:
			return "jpa.entityCannotBeRemoveMessage";
		case IEntityConstants.PERSIST:
			return "jpa.entityCannotBePersistMessage";

		default:
			return null;
		}
	}

	/**
	 * Gets the adichatz lock manager.
	 * 
	 * @return the adichatz lock manager
	 */
	private IAdiLockManager getAdichatzLockManager() {
		if (null == adichatzLockManager)
			try {
				adichatzLockManager = gatewayConnector.getLockManager();
			} catch (Exception e) {
				LogBroker.logError(e);
			}
		return adichatzLockManager;
	}

	public QueryResult getQueryResult(AdiQuery adiQuery) {
		try {
			return getPersistenceManager().getQueryResult(adiQuery);
		} catch (AdiApplicationServerException e) {
		} catch (Exception e) {
			Throwable throwable = gatewayConnector.extractException(e);
			Throwable cause = throwable.getCause();
			String causeStr = null == cause ? throwable.toString() : cause.toString();
			displayError(getFromJpaBundle("query.errorLaunchingQuery"), e, errorMessage(throwable, null), causeStr);
		}
		return null;
	}

	/**
	 * Gets the deleted entity cached keys.
	 * 
	 * @return the deleted entity cached keys
	 */
	public Set<MultiKey> getDeletedEntityCachedKeys() {
		return deletedEntityCacheKeys;
	}

	/**
	 * Gets the dirty entities.
	 * 
	 * @return set of entities whith status REMOVE or MERGE<br>
	 *         Status are transient inside application. Save "dirty" entities set the status to RETRIEVE and remove entities from
	 *         the dirty set.
	 */
	public Set<JPAEntity<?>> getDirtyEntities() {
		return dirtyEntities;
	}
}
