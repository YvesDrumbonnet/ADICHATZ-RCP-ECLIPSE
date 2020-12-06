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
package org.adichatz.engine.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.data.IBeanInterceptor;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Interface IEntity.
 */
public interface IEntity<T> {

	/**
	 * Gets the bean.
	 * 
	 * @return the bean
	 */
	T getBean();

	/**
	 * Sets the bean.
	 *
	 * @param bean
	 *            the new bean
	 */
	void setBean(Object bean);

	/**
	 * Gets the bean class.
	 * 
	 * @return the beanClass
	 */
	public abstract Class<? extends T> getBeanClass();

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public abstract IBeanInterceptor getBeanInterceptor();

	/**
	 * Checks if is locked.
	 * 
	 * @return true, if is locked
	 */
	boolean isLocked();

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public abstract int getStatus();

	/**
	 * Gets the binding services.
	 * 
	 * @return the binding services
	 */
	public abstract Set<ABindingService> getBindingServices();

	/**
	 * Put status.
	 * 
	 * @param status
	 *            the status
	 * @param forced
	 *            the forced
	 */
	public abstract void putStatus(int status, boolean forced);

	/**
	 * Dispose listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public abstract void disposeListener(AListener listener);

	/**
	 * Gets the bean Id.
	 * 
	 * @return the beanId
	 */
	public abstract Object getBeanId();

	/**
	 * Sets the id bean.
	 * 
	 * @param beanId
	 *            the new bean Id
	 */
	public abstract void setBeanId(Object beanId);

	/**
	 * Gets the cache key.
	 * 
	 * @return the cacheKey
	 */
	public abstract MultiKey getCacheKey();

	/**
	 * Sets the cache key.
	 * 
	 * @param cacheKey
	 *            the new cache key
	 */
	public abstract void setCacheKey(MultiKey cacheKey);

	/**
	 * Gets the lazy fetch manager.
	 * 
	 * @return the lazy fetch manager
	 */
	public abstract LazyFetchManager<T> getLazyFetchManager();

	/**
	 * Sets the lazy fetch manager.
	 * 
	 * @param lazyFetchManager
	 *            the new lazy fetch manager
	 */
	public void setLazyFetchManager(LazyFetchManager<T> lazyFetchManager);

	/**
	 * Gets the listeners.
	 * 
	 * @return the listeners
	 */
	public abstract List<AEntityListener> getListeners();

	/**
	 * Removes the entity from cache.
	 * 
	 * Called when entity is removed:
	 * <ul>
	 * <li>saveEntities for an entity marked as DELETED.</li>
	 * <li>refreshEntities for an entity marked as CREATED.</li>
	 * </ul>
	 */
	public abstract void removeFromCache();

	/**
	 * Fire property changes.
	 */
	public abstract void firePropertyChanges();

	/**
	 * Gets the binding service.
	 * 
	 * @return the the binding service
	 */
	public abstract ABindingService getLockingBindingService();

	/**
	 * Refresh entity if needed.
	 * 
	 * @param bindingService
	 *            the binding service.
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 * 
	 * @throws AdiPMException
	 *             the adi pm exception
	 */
	public abstract void refreshEntityIfNeeded(ABindingService bindingService, String... lazyFetchMembers) throws AdiPMException;

	/**
	 * Checks if is binding service is the locking one.
	 * 
	 * @param bindingService
	 *            the binding service.
	 * 
	 * @return true, if binding service is the locking one.
	 */
	public abstract boolean isLockingBindingService(ABindingService bindingService);

	/**
	 * Dispose from cache.
	 */
	public abstract void disposeFromCache();

	/**
	 * Gets the entity meta model.
	 * 
	 * @return the entity meta model
	 */
	public abstract AEntityMetaModel<T> getEntityMM();

	/**
	 * Fire many to ones.
	 * 
	 * @param toBeDelete
	 *            the to be delete
	 */
	public void fireManyToOnes(boolean toBeDelete);

	/**
	 * Lock.
	 * 
	 * @param locked
	 *            the binding service
	 * @param editor
	 *            the binding service
	 */
	public void lock(boolean locked, ABindingService bindingService);

	/**
	 * Fire listener.
	 * 
	 * @param eventType
	 *            the event type
	 */
	public void fireListener(int eventType);

	/**
	 * Gets the relationship update map.
	 * 
	 * @return the relationshipUpdateMap
	 */
	public Map<MultiKey, RelationshipUpdate<T>> getRelationshipUpdateMap();

	/**
	 * Removes the entity listener.
	 * 
	 * @param entityListener
	 *            the entity listener
	 */
	public void removeEntityListener(AEntityListener entityListener);

	/**
	 * Need refresh.
	 *
	 * @param bindingService
	 *            the binding service
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 * @return true, if successful
	 */
	public boolean needRefresh(ABindingService bindingService, String... lazyFetchMembers);

	/**
	 * Adds the lazy fetch.
	 *
	 * @param bindingService
	 *            the binding service
	 * @param lazyFetchMember
	 *            the lazy fetch member
	 */
	public void addLazyFetch(ABindingService bindingService, String lazyFetchMember);

	/**
	 * Gets the previous status.
	 *
	 * @return the previous status
	 */
	public int getPreviousStatus();

	/**
	 * Sets the initialized.
	 *
	 * @param initialized the new initialized
	 */
	public void setInitialized(boolean initialized);

	/**
	 * Initialize.
	 */
	public void initialize();
}