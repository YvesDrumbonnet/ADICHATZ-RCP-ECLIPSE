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
package org.adichatz.engine.validation;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.MessageUtility;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.viewer.IBoundContentProvider;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.IMessageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ABindingService.
 * 
 * @author Yves Drumbonnet
 */
public abstract class ABindingService {

	// +-------------+
	// | S T A T I C |
	// +-------------+

	/** The synchronizing. */
	private static boolean synchronizing;

	/** The synchronizing. */
	private static ABindingService currentBinding;

	/**
	 * Checks if is synchronizing.
	 * 
	 * @return true, if is synchronizing
	 */
	public static boolean isSynchronizing() {
		return synchronizing;
	}

	/**
	 * Sets the synchronizing.
	 * 
	 * @param synchronizingParam
	 *            the new synchronizing
	 */
	public static void setSynchronizing(boolean synchronizingParam) {
		synchronizing = synchronizingParam;
	}

	/** The synchronizing. */
	private static boolean forceRefreshing;

	/**
	 * Checks if is force refreshing.
	 * 
	 * @return true, if is force refreshing
	 */
	public static boolean isForceRefreshing() {
		return forceRefreshing;
	}

	/**
	 * Sets the force refreshing.
	 * 
	 * @param forceRefreshing
	 *            the new force refreshing
	 */
	public static void setForceRefreshing(boolean forceRefreshing) {
		ABindingService.forceRefreshing = forceRefreshing;
	}

	/**
	 * Gets the current binding.
	 * 
	 * @return the current binding
	 */
	public static ABindingService getCurrentBinding() {
		return currentBinding;
	}

	/**
	 * Sets the current binding.
	 * 
	 * @param currentBinding
	 *            the new current binding
	 */
	public static void setCurrentBinding(ABindingService currentBinding) {
		ABindingService.currentBinding = currentBinding;
	}

	// +---------------------+
	// | E N D - S T A T I C |
	// +---------------------+

	/** The bounded controller. */
	protected IBoundedController boundedController;

	/** The listeners. */
	protected List<ABindingListener> listeners = new ArrayList<ABindingListener>();

	/** The error messages. */
	protected ErrorMessageMap errorMessageMap = new ErrorMessageMap(this);

	/** The dirty. */
	private boolean dirty;

	/** The closing: JPAEntity#postRefresh must know when editor is closing. */
	private boolean closing = false;

	/** The binding dispatcher map. */
	protected Map<IEntity<?>, EntityBindingDispatcher> entityServiceMap = new HashMap<IEntity<?>, EntityBindingDispatcher>();

	private Map<String, Object> properties = new HashMap<>();

	private Set<AEntityManagerController> entityContainers = new HashSet<>();

	/**
	 * Instantiates a new a binding service.
	 * 
	 * @param boundedController
	 *            the bounded controller
	 */
	public ABindingService(IBoundedController boundedController) {
		this.boundedController = boundedController;
	}

	/**
	 * Checks if is closing.
	 * 
	 * @return true, if is closing
	 */
	public boolean isClosing() {
		return closing;
	}

	/**
	 * Sets the closing.
	 * 
	 * @param closing
	 *            the new closing
	 */
	public void setClosing(boolean closing) {
		this.closing = closing;
	}

	/**
	 * Gets the entities.
	 * 
	 * @return the entities managed by the editor
	 */
	public Set<IEntity<?>> getEntities() {
		return entityServiceMap.keySet();
	}

	/**
	 * Gets the updated entities.
	 * 
	 * @return the updated entities
	 */
	public Map<IEntity<?>, EntityBindingDispatcher> getUpdatedEntities() {
		Map<IEntity<?>, EntityBindingDispatcher> updatedEntities = new HashMap<IEntity<?>, EntityBindingDispatcher>();
		for (Map.Entry<IEntity<?>, EntityBindingDispatcher> entry : entityServiceMap.entrySet()) {
			if (entry.getKey().isLocked() && entry.getKey().getLockingBindingService().equals(this)) {
				updatedEntities.put(entry.getKey(), entry.getValue());
			}
		}
		return updatedEntities;
	}

	/**
	 * Gets the error messages.
	 * 
	 * @return the error messages
	 */
	public ErrorMessageMap getErrorMessageMap() {
		return errorMessageMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#removeMessage(org.adichatz.engine.cache.IEntity)
	 */
	/**
	 * Removes the message.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void removeMessage(IEntity<?> entity) {
		Set<ErrorMessage> errorMessages = errorMessageMap.get(entity);
		if (null != errorMessages) {
			// Use array to avoid ConcurrentModificationException
			for (ErrorMessage errorMessage : errorMessages.toArray(new ErrorMessage[errorMessages.size()])) {
				IMessageManager messageManager = errorMessage.getValidator().getFormMessageManager().getForm().getMessageManager();
				for (IValidableController controller : errorMessage.getValidator().getHostingControllers())
					// Control could be disposed at editor losing when error occured at editor opening.
					// There is no change to cancel but a message to remove.
					if (!controller.getControl().isDisposed())
						messageManager.removeMessage(errorMessage.getValidator().getKey(), controller.getControl());
				removeMessage(errorMessage);
			}
		}
	}

	/**
	 * Dispose entity from binding service.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void disposeEntity(IEntity<?> entity) {
		removeMessage(entity);
		EntityBindingDispatcher entityDispatcher = entityServiceMap.get(entity);
		if (null != entityDispatcher) {
			entityDispatcher.dispose();
			entityServiceMap.remove(entity);
			for (EntityInjection entityInjection : entityDispatcher.getEntityInjections())
				entityInjection.clearTransientListeners();
		}
		entity.getBindingServices().remove(this);
		setDirty();
		// Fire a change status event so that dirty could be computed
		if (IEntityConstants.RETRIEVE < entity.getStatus())
			AListener.fireListener(listeners, new AdiEntityEvent(IEventType.CHANGE_STATUS, entity, IEntityConstants.NULL));

		if (null != entity.getListeners()) {
			List<AListener> toBeDisposedListeners = new ArrayList<AListener>();
			for (AListener listener : entity.getListeners()) {
				if (this.equals(((AEntityListener) listener).getEntityBindingService()))
					toBeDisposedListeners.add(listener);
			}
			for (AListener listener : toBeDisposedListeners)
				listener.dispose();
		}
	}

	/**
	 * Close binding service.
	 */
	public void close() {
		// Use a table to avoid ConcurrentModificationException
		IEntity<?>[] entityTable = entityServiceMap.keySet().toArray(new IEntity<?>[entityServiceMap.size()]);
		for (IEntity<?> entity : entityTable) {
			disposeEntity(entity);
			if (null != entity.getLazyFetchManager())
				entity.getLazyFetchManager().removeBindingService(this);
			entity.disposeFromCache();
		}
		entityServiceMap.clear();
		listeners.clear();
		clearMessages();
	}

	/**
	 * Gets the listeners.
	 * 
	 * @return the listeners
	 */
	public List<ABindingListener> getListeners() {
		return listeners;
	}

	/**
	 * Fire listener.
	 * 
	 * @param eventType
	 *            the event type
	 */
	public void fireListener(int eventType) {
		AListener.fireListener(listeners, new AdiEvent(eventType));
	}

	/**
	 * Dispose listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void disposeListener(AListener listener) {
		if (null != listeners)
			listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#saveEntities()
	 */
	/**
	 * Save entities.
	 * 
	 * @return true, if successful
	 */
	public boolean saveEntities() {
		if (!errorMessageMap.isEmpty()) {
			throw new AdichatzErrorException(getFromEngineBundle("error.validationErrorEncountered", getTitle()));
		} else {
			fireListener(IEventType.PRE_SAVE_ENTITIES);
			Set<IEntity<?>> updatedEntities = getUpdatedEntities().keySet();
			if (updatedEntities.isEmpty())
				return true; // @Persist could be launched after a first doSave()
			ADataAccess dataAccess = updatedEntities.iterator().next().getEntityMM().getDataAccess();
			boolean result = dataAccess.saveEntities(this, updatedEntities);
			fireListener(IEventType.POST_SAVE_ENTITIES);
			for (Map.Entry<IEntity<?>, EntityBindingDispatcher> entry : entityServiceMap.entrySet()) {
				entry.getValue().handleLockingEvent(null);
			}
			return result;
		}
	}

	/**
	 * Refresh entities.
	 * 
	 * @param entities
	 *            the entities
	 * @return true, if successful
	 */
	public boolean refreshEntities(Set<IEntity<?>> entities) {
		return refreshEntities(entities.toArray(new IEntity<?>[entities.size()]));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#refreshEntities(org.adichatz.engine.cache.IEntity<?>[])
	 */
	/**
	 * Refresh entities.
	 * 
	 * @param entities
	 *            the entities
	 * @return true, if successful
	 */
	public boolean refreshEntities(IEntity<?>... entities) {
		ADataAccess dataAccess = null;
		fireListener(IEventType.PRE_REFRESH_ENTITIES);
		ABindingService.setForceRefreshing(true);
		try {
			if (0 != entities.length) {
				List<IEntity<?>> entitiesToBeRefreshed = new ArrayList<IEntity<?>>();
				List<IEntity<?>> affectedEntities = new ArrayList<IEntity<?>>(); // Entities affected in other editors that cannot
																					// be refreshed
				for (IEntity<?> entity : entities) {
					if (null != entity.getLockingBindingService() && !entity.getLockingBindingService().equals(this))
						affectedEntities.add(entity);
					else {
						dataAccess = entity.getEntityMM().getDataAccess();
						entitiesToBeRefreshed.add(entity);
						Set<LocalLazyNode> rootLazyNodes = null == entity.getLazyFetchManager() ? null
								: entity.getLazyFetchManager().getBindingServiceMap().get(this);
						if (null != rootLazyNodes)
							for (LocalLazyNode lazyNode : rootLazyNodes) {
								lazyNode.setToFetchParent(true);
							}
					}
				}
				if (!affectedEntities.isEmpty())
					// Send message <<Entity was locked in another editor>>
					// User can continue skipping the current entity or cancel the refresh process.
					MessageUtility.displayCannotRefreshEntity(affectedEntities);

				if (null != dataAccess) {
					if (dataAccess.refreshEntities(this, true,
							entitiesToBeRefreshed.toArray(new IEntity[entitiesToBeRefreshed.size()])))
						for (IEntity<?> entity : entitiesToBeRefreshed) {
							Set<ErrorMessage> entityErroMessages = errorMessageMap.get(entity);
							if (null != entityErroMessages)
								for (ErrorMessage errorMessage : entityErroMessages
										.toArray(new ErrorMessage[entityErroMessages.size()]))
									errorMessageMap.remove(errorMessage);
							if (IEntityConstants.TODELETE == entity.getStatus()) {
								// Entity no longer exists on server and must be deleted from the application.
								MessageUtility.displayEntityToDelete(entity, this);
								entity.removeFromCache();
							}
						} // for (IEntity<?> entity : entitiesToBeRefreshed)
					else
						LogBroker.displayError(getFromEngineBundle("error.doNotKnowHowToRefreshEntities"),
								getFromEngineBundle("error.doNotKnowHowToRefreshEntities"));
				}
			}
		} catch (InterruptedException e) {
			return false;
		} catch (Exception e) {
			LogBroker.logError(e);
		} finally {
			ABindingService.setForceRefreshing(false);
		}
		fireListener(IEventType.POST_REFRESH_ENTITIES);
		return true;
	}

	/**
	 * Refreshe included tables.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void refreshIncludedTables(IEntity<?> entity) {
		Set<ATabularController<?>> tableControllers = entity.getEntityMM().getDataAccess().getDataCache()
				.getTabularControllerByCKey(entity);
		if (null != tableControllers) { // needed to recompute TableRenderer.errorColor.
			for (ATabularController<?> tableController : tableControllers) {
				if (tableController.getViewer().getContentProvider() instanceof IBoundContentProvider) {
					@SuppressWarnings("rawtypes")
					Object bean = ((IBoundContentProvider) tableController.getViewer().getContentProvider()).getBeanMap()
							.get(entity.getCacheKey());
					tableController.update(entity, bean);
				}
			}
		} // END if (null != tableControllers)
	}

	/**
	 * Checks if is dirty.
	 * 
	 * @return true, if is dirty
	 */
	public boolean isDirty() {
		return dirty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#setDirty()
	 */
	/**
	 * Sets the dirty.
	 */
	public void setDirty() {
		this.dirty = !getUpdatedEntities().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#removeMessages(org.adichatz.engine.controller.IValidableController)
	 */
	/**
	 * Removes the messages.
	 * 
	 * @param validableController
	 *            the validable controller
	 */
	public void removeMessages(IValidableController validableController) {
		Set<ErrorMessage> errorMessages = errorMessageMap.get(validableController);
		if (null != errorMessages)
			for (ErrorMessage errorMessage : errorMessages.toArray(new ErrorMessage[errorMessages.size()]))
				errorMessage.getValidator().setLevel(IMessageProvider.NONE, null);
	}

	/**
	 * Removes the message (could be overriden by specific Binding Service).
	 * 
	 * @param errorMessage
	 *            the error message
	 * @return true, if successful
	 */
	public boolean removeMessage(ErrorMessage errorMessage) {
		return errorMessageMap.remove(errorMessage);
	}

	/**
	 * Adds the message (could be overriden by specific Binding Service).
	 * 
	 * @param errorMessage
	 *            the error message
	 * @return true, if successful
	 */
	public boolean addMessage(ErrorMessage errorMessage) {
		return errorMessageMap.add(errorMessage);
	}

	/**
	 * Clear messages.
	 */
	public void clearMessages() {
		for (ErrorMessage errorMessage : errorMessageMap.getErrorMessages()
				.toArray(new ErrorMessage[errorMessageMap.getErrorMessages().size()])) {
			removeMessage(errorMessage);
		}
	}

	/**
	 * Adds the binding listener.
	 * 
	 * @param bindingListener
	 *            the binding listener
	 */
	public void addBindingListener(ABindingListener bindingListener) {
		listeners.add(bindingListener);
		bindingListener.setBindingService(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getBindingDispatcher(org.adichatz.engine.cache.IEntity)
	 */
	/**
	 * Gets the binding dispatcher.
	 * 
	 * @param entity
	 *            the entity
	 * @return the binding dispatcher
	 */
	public EntityBindingDispatcher getBindingDispatcher(IEntity<?> entity) {
		EntityBindingDispatcher bindingDispatcher = entityServiceMap.get(entity);
		if (null == bindingDispatcher) {
			bindingDispatcher = new EntityBindingDispatcher(entity);
			if (null != entity) {
				entityServiceMap.put(entity, bindingDispatcher);
				entity.getBindingServices().add(this);
				AListener.fireListener(listeners, new AdiEntityEvent(IEventType.ADD_ENTITY, entity));
			}

		}
		return bindingDispatcher;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return null;
	}

	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	public Image getImage() {
		return null;
	}

	/**
	 * Gets the error paths.
	 * 
	 * @return the error paths
	 */
	public List<ErrorPath> getErrorPaths() {
		return null;
	}

	/**
	 * Activate controllers.
	 * 
	 * @param errorPath
	 *            the error path
	 */
	public void activateControllers(ErrorPath errorPath) {
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return null;
	}

	public Set<AEntityManagerController> getEntityContainers() {
		return entityContainers;
	}

	public void synchronize() {
		List<IEntity<?>> entitiesToBeRefreshed = new ArrayList<>();
		for (Map.Entry<IEntity<?>, EntityBindingDispatcher> entry : entityServiceMap.entrySet()) {
			Set<String> lazyFetchMembers = entry.getValue().getLazyFetchMembers();
			boolean refresh;
			if (null == lazyFetchMembers)
				refresh = entry.getKey().needRefresh(this);
			else
				refresh = entry.getKey().needRefresh(this, lazyFetchMembers.toArray(new String[lazyFetchMembers.size()]));
			if (refresh)
				entitiesToBeRefreshed.add(entry.getKey());
		}
		if (!entitiesToBeRefreshed.isEmpty())
			refreshEntities(entitiesToBeRefreshed.toArray(new IEntity<?>[entitiesToBeRefreshed.size()]));
		for (IEntity<?> entity : getEntities())
			entity.firePropertyChanges();
		for (AEntityManagerController entityContainer : entityContainers) {
			entityContainer.postSynchronize();
		}
		entityContainers.clear();
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Gets the bounded controller.
	 *
	 * @return the bounded controller
	 */
	public IBoundedController getBoundedController() {
		return boundedController;
	}

	public Map<IEntity<?>, EntityBindingDispatcher> getEntityServiceMap() {
		return entityServiceMap;
	}
}
