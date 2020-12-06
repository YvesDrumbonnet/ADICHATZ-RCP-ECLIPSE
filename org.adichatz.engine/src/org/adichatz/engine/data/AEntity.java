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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LazyFetchManager;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Class AEntity.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            the
 */
public abstract class AEntity<T> implements IEntity<T> {

	/** The entity meta model. */
	protected AEntityMetaModel<T> entityMM;

	/** The bean. */
	protected T bean;

	/** The status. */
	protected int status = IEntityConstants.RETRIEVE;

	/** The previous status. */
	protected int previousStatus = IEntityConstants.RETRIEVE;

	/** The entity. */
	protected IBeanInterceptor beanInterceptor;

	/** The bean Id. */
	protected Object beanId;

	/** The cache key. */
	protected MultiKey cacheKey;

	/** The relationship update map. */
	protected Map<MultiKey, RelationshipUpdate<T>> relationshipUpdateMap = new HashMap<MultiKey, RelationshipUpdate<T>>();

	/** The locking binding service. */
	protected ABindingService lockingBindingService;

	/** The locked. */
	protected boolean locked = false;

	/** The lazy fetch manager. */
	protected LazyFetchManager<T> lazyFetchManager;

	/** The bean class. */
	protected Class<? extends T> beanClass;

	/** The listeners. */
	protected List<AEntityListener> listeners = new ArrayList<AEntityListener>();

	/** the binding services. */
	protected Set<ABindingService> bindingServices = new HashSet<ABindingService>();

	/** the binding services. */
	protected Set<AEntityManagerController> entityManagerControllers = new HashSet<AEntityManagerController>();

	/**
	 * Instantiates a new a entity.
	 * 
	 * @param dataAccess
	 *            the data access
	 * @param bean
	 *            the bean
	 * @param status
	 *            the status
	 */
	@SuppressWarnings("unchecked")
	public AEntity(ADataAccess dataAccess, T bean, int status) {
		Class<?> beanClass = bean.getClass();
		while (null == entityMM && !beanClass.equals(Object.class)) {
			entityMM = (AEntityMetaModel<T>) dataAccess.getPluginResources().getPluginEntityTree().getEntityMM(beanClass);
			beanClass = beanClass.getSuperclass();
		}
		if (null == entityMM)
			throw new RuntimeException("No Entity Meta Model found for class: " + bean.getClass());
		this.beanClass = entityMM.getBeanClass();
		this.bean = bean;

		putStatus(status, false);
		previousStatus = status;
		// Do not initialize lazyFetchManager before having initialized entityMM
		lazyFetchManager = new LazyFetchManager<T>(this);
	}

	/**
	 * Gets the bean.
	 *
	 * @return the bean
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getBean()
	 */
	public T getBean() {
		return bean;
	}

	/**
	 * Sets the bean.
	 *
	 * @param bean the new bean
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#setBean(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setBean(Object bean) {
		this.bean = (T) bean;
	}

	/**
	 * Gets the bean class.
	 *
	 * @return the bean class
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getBeanClass()
	 */
	public Class<? extends T> getBeanClass() {
		return beanClass;
	}

	/**
	 * Gets the bean interceptor.
	 *
	 * @return the bean interceptor
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getBeanInterceptor()
	 */
	public IBeanInterceptor getBeanInterceptor() {
		return beanInterceptor;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getStatus()
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Gets the previous status.
	 *
	 * @return the previous status
	 */
	public int getPreviousStatus() {
		return previousStatus;
	}

	/**
	 * Gets the binding services.
	 *
	 * @return the binding services
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getBindingServices()
	 */
	@Override
	public Set<ABindingService> getBindingServices() {
		return bindingServices;
	}

	/**
	 * Dispose listener.
	 *
	 * @param listener the listener
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#disposeListener(org.adichatz.engine.listener.AListener)
	 */
	public void disposeListener(AListener listener) {
		if (null != listeners)
			listeners.remove(listener);
	}

	/**
	 * Gets the entity meta model.
	 * 
	 * @return the entity meta model
	 */
	public AEntityMetaModel<T> getEntityMM() {
		return entityMM;
	}

	/**
	 * Gets the bean id.
	 *
	 * @return the bean id
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getBeanId()
	 */
	public Object getBeanId() {
		return beanId;
	}

	/**
	 * Sets the bean id.
	 *
	 * @param beanId the new bean id
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#setBeanId(java.lang.Object)
	 */
	public void setBeanId(Object beanId) {
		this.beanId = beanId;
	}

	/**
	 * Gets the cache key.
	 *
	 * @return the cache key
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getCacheKey()
	 */
	public MultiKey getCacheKey() {
		return cacheKey;
	}

	/**
	 * Sets the cache key.
	 *
	 * @param cacheKey the new cache key
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#setCacheKey(org.adichatz.common.ejb.MultiKey)
	 */
	public void setCacheKey(MultiKey cacheKey) {
		this.cacheKey = cacheKey;
	}

	/**
	 * Gets the locking validation container.
	 * 
	 * @return the validation container
	 */
	public ABindingService getLockingBindingService() {
		return lockingBindingService;
	}

	/**
	 * Lock.
	 * 
	 * @param locked
	 *            the locked
	 * @param bindingService
	 *            the binding service
	 */
	@Override
	public void lock(boolean locked, ABindingService bindingService) {
		ABindingService currentLockingBS = locked ? bindingService : lockingBindingService;
		if (locked) {
			lockingBindingService = bindingService;
		} else
			lockingBindingService = null;
		this.locked = locked;
		if (null != currentLockingBS)
			currentLockingBS.setDirty();
		for (ABindingService bs : bindingServices)
			bs.getBindingDispatcher(this).handleLockingEvent(null);
		fireListener(IEventType.LOCK_ENTITY);
		if (null != bindingService)
			entityMM.getDataAccess().checkDirty(bindingService);
	}

	/**
	 * Fire listener.
	 *
	 * @param eventType the event type
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#fireListener(int)
	 */
	public void fireListener(int eventType) {
		AListener.fireListener(listeners, new AdiEntityEvent(eventType, this));
	}

	/**
	 * Checks if is locked.
	 * 
	 * @return true, if is locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Gets the lazy fetch manager.
	 *
	 * @return the lazy fetch manager
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getLazyFetchManager()
	 */
	public LazyFetchManager<T> getLazyFetchManager() {
		return lazyFetchManager;
	}

	/**
	 * Sets the lazy fetch manager.
	 *
	 * @param lazyFetchManager the new lazy fetch manager
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#setLazyFetchManager(org.adichatz.engine.cache.LazyFetchManager)
	 */
	public void setLazyFetchManager(LazyFetchManager<T> lazyFetchManager) {
		this.lazyFetchManager = lazyFetchManager;
	}

	/**
	 * Gets the listeners.
	 *
	 * @return the listeners
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#getListeners()
	 */
	@Override
	public List<AEntityListener> getListeners() {
		return listeners;
	}

	/**
	 * Gets the relationship update map.
	 * 
	 * @return the relationshipUpdateMap
	 */
	public Map<MultiKey, RelationshipUpdate<T>> getRelationshipUpdateMap() {
		return relationshipUpdateMap;
	}

	/**
	 * Dispose.
	 * 
	 * The entity is no longer used in cache.
	 * 
	 * @return true, if dispose from cache
	 */
	public void disposeFromCache() {
		if (null == bindingServices || bindingServices.isEmpty()) {
			relationshipUpdateMap.clear();
			ADataCache dataCache = entityMM.getDataAccess().getDataCache();
			dataCache.getCachedEntities().remove(cacheKey);
			dataCache.getCreatedEntities().remove(bean);
			listeners.clear();
		}
	}

	/**
	 * Removes the from cache.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#removeFromCache()
	 */
	@SuppressWarnings("unchecked")
	public void removeFromCache() {
		Set<ATabularController<?>> tableControllers = entityMM.getDataAccess().getDataCache().getTabularControllerByCKey(this);
		if (null != tableControllers) {
			/*
			 * An Array is used to avoid ConcurrentModificationException
			 */
			ATabularController<?>[] tableControllerA = tableControllers.toArray(new ATabularController[tableControllers.size()]);
			for (ATabularController<?> tableController : tableControllerA)
				((ATabularContentProvider<T>) tableController.getViewer().getContentProvider()).removeEntity(this, true);

			tableControllers.clear();
			entityMM.getDataAccess().getDataCache().getTabularControllerByCKey().remove(cacheKey);
		}
		/*
		 * An Array is used to avoid ConcurrentModificationException
		 */
		RelationshipUpdate<T>[] relationshipUpdates = relationshipUpdateMap.values()
				.toArray(new RelationshipUpdate[relationshipUpdateMap.size()]);
		for (RelationshipUpdate<T> relationshipUpdate : relationshipUpdates) {
			entityMM.getDataAccess().getDataCache().removeRelationshipUpdate(relationshipUpdate.getNewParentMKey(),
					relationshipUpdate);
			entityMM.getDataAccess().getDataCache().removeRelationshipUpdate(relationshipUpdate.getOldParentMKey(),
					relationshipUpdate);
			entityMM.getDataAccess().getDataCache().removeFromTabularControllersRU(relationshipUpdate,
					relationshipUpdate.getNewParentMKey());
			entityMM.getDataAccess().getDataCache().removeRelationshipUpdate(relationshipUpdate.getOldParentMKey(),
					relationshipUpdate);
			entityMM.getDataAccess().getDataCache().removeFromTabularControllersRU(relationshipUpdate,
					relationshipUpdate.getOldParentMKey());
		}
		relationshipUpdateMap.clear();
		if (IEntityConstants.REMOVE != status)
			finalizeRemove();
	}

	/**
	 * Finalize remove.
	 */
	public void finalizeRemove() {
		if (null != bindingServices) {
			// bindingServices.toArray needed to avoid ConcurrentModificationException
			for (ABindingService bindingService : bindingServices.toArray(new ABindingService[0])) {
				if (bindingService.equals(lockingBindingService))
					lockingBindingService = null;
				bindingService.disposeEntity(this);
			}
			bindingServices = null;
		}

		if (null != lazyFetchManager)
			lazyFetchManager.getBindingServiceMap().clear();

		disposeFromCache();
	}

	/**
	 * Checks if is locking binding service.
	 * 
	 * @param bindingService
	 *            the binding service
	 * @return true, if is locking binding service
	 */
	@Override
	public boolean isLockingBindingService(ABindingService bindingService) {
		if (status > IEntityConstants.RETRIEVE && bindingService.equals(lockingBindingService))
			return true;
		return false;
	}

	/**
	 * Fire many to ones.
	 * 
	 * @param toBeDelete
	 *            the to be delete
	 */
	public void fireManyToOnes(boolean toBeDelete) {
		/*
		 * Puts ManyToOne relationships to be deleted for the entity
		 */
		for (PropertyField joinColumn : entityMM.getFieldMap().values()) {
			if (joinColumn instanceof RefField) {
				Object parentBean = ReflectionTools
						.invokeMethod(entityMM.getFieldMap().get(joinColumn.getFieldName()).getGetMethod(), bean);
				if (null != parentBean) {
					entityMM.getDataAccess().getDataCache().doRelationshipUpdate(this,
							((RefField<?>) joinColumn).getParentMappedBy(), toBeDelete ? parentBean : null,
							toBeDelete ? null : parentBean, IEntityConstants.relationshipUpdateType.UPDATE_MANY_TO_ONE,
							joinColumn.getFieldName());
				}
			}
		}
	}

	/**
	 * Fire property changes.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#firePropertyChanges()
	 */
	public void firePropertyChanges() {
		firePropertyChanges(beanClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#firePropertyChanges()
	 */
	/**
	 * Fire property changes.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	public void firePropertyChanges(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !ReflectionTools.hasInterface(field.getType(), Collection.class)) {
				boolean accessible = field.canAccess(bean);
				field.setAccessible(true);
				try {
					beanInterceptor.getPropertyChangeSupport().firePropertyChange(field.getName(), null, field.get(bean));
				} catch (Exception e) {
					LogBroker.logError("Field:" + field.getName(), e);
				}
				field.setAccessible(accessible);
			}
		}
	}

	/**
	 * Put status.
	 *
	 * @param status the status
	 * @param forced the forced
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#putStatus(int, boolean)
	 */
	public void putStatus(int status, boolean forced) {
		if (IEntityConstants.NULL == status) {
			if (IEntityConstants.REMOVE == this.status) {
				fireManyToOnes(false);
			}
		} else if (forced || this.status < status) {
			AdiEntityEvent entityEvent = new AdiEntityEvent(IEventType.CHANGE_STATUS, this, this.status);
			previousStatus = this.status;
			this.status = status;
			AListener.fireListener(listeners, entityEvent);
			if (null != bindingServices)
				for (ABindingService bindingService : bindingServices)
					bindingService.fireListener(IEventType.CHANGE_STATUS);
		}
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 41; // any arbitrary constant will do
	}

	/**
	 * Removes the entity listener.
	 *
	 * @param entityListener the entity listener
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#removeEntityListener(org.adichatz.engine.listener.AEntityListener)
	 */
	@Override
	public void removeEntityListener(AEntityListener entityListener) {
		listeners.remove(entityListener);
	}

	/**
	 * Need refresh.
	 *
	 * @param bindingService the binding service
	 * @param lazyFetchMembers the lazy fetch members
	 * @return true, if successful
	 */
	public boolean needRefresh(ABindingService bindingService, String... lazyFetchMembers) {
		return false;
	}

	/**
	 * Adds the lazy fetch.
	 *
	 * @param bindingService the binding service
	 * @param lazyFetchMember the lazy fetch member
	 */
	public void addLazyFetch(ABindingService bindingService, String lazyFetchMember) {
		lazyFetchManager.addLazyFetch(bindingService, lazyFetchMember);
	}

	/**
	 * Sets the initialized.
	 *
	 * @param initialized the new initialized
	 */
	@Override
	public void setInitialized(boolean initialized) {
	}

	/**
	 * Initialize.
	 */
	@Override
	public void initialize() {
	}
}
