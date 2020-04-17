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

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDataCache.
 */
public interface IDataCache {

	/**
	 * Gets the cached entities.
	 * 
	 * @return the cached entities
	 */
	public Map<MultiKey, IEntity<?>> getCachedEntities();

	/**
	 * Put new entity.
	 * 
	 * @param <T>
	 *            the
	 * @param beanClass
	 *            the bean class
	 * @return the i entity< t>
	 */
	public <T> IEntity<T> putNewEntity(Class<T> beanClass);

	/**
	 * Fetch entity.
	 * 
	 * @param <T>
	 *            the
	 * @param bean
	 *            the bean
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 * @return the i entity< t>
	 */
	public <T> IEntity<T> fetchEntity(T bean, String... lazyFetchMembers);

	/**
	 * Fetch transient entity.
	 * 
	 * @param <T>
	 *            the
	 * @param bean
	 *            the bean
	 * @param bindingService
	 *            the binding service
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 * @return the i entity< t>
	 * @throws Exception
	 *             the exception
	 */
	public <T> IEntity<T> fetchTransientEntity(T bean, ABindingService bindingService, String... lazyFetchMembers) throws Exception;

	/**
	 * Adds the table ctler for c key.
	 * 
	 * @param cacheKey
	 *            the cache key
	 * @param tableController
	 *            the table controller
	 */
	public void addTableCtlerForCKey(MultiKey cacheKey, ATabularController<?> tableController);

	/**
	 * Removes the table ctler for c key.
	 * 
	 * @param cacheKey
	 *            the cache key
	 * @param tableController
	 *            the table controller
	 */
	public void removeTableCtlerForCKey(MultiKey cacheKey, ATabularController<?> tableController);

	/**
	 * Removes the from table controllers.
	 * 
	 * @param <T>
	 *            the
	 * @param relationshipUpdate
	 *            the relationship update
	 * @param parentKey
	 *            the multi key
	 */
	public <T> void removeFromTableControllersRU(RelationshipUpdate<T> relationshipUpdate, MultiKey parentKey);

	/**
	 * Adds the relationship update.
	 * 
	 * @param multiKey
	 *            the multi key
	 * @param relationshipUpdate
	 *            the relationship update
	 */
	public void addRelationshipUpdate(MultiKey multiKey, RelationshipUpdate<?> relationshipUpdate);

	/**
	 * Adds the to table controllers.
	 * 
	 * @param <T>
	 *            the
	 * @param entity
	 *            the entity
	 * @param parentKey
	 *            the multi key
	 */
	public <T> void addToTableControllersRU(IEntity<T> entity, MultiKey parentKey);

	/**
	 * Gets the table controllers by multiKey (parentEntity#cacheKey(),mappedByField.
	 * 
	 * @param parentKey
	 *            the parent key
	 * @param tableController
	 *            the table controller
	 */
	public void addTableControllerRU(MultiKey parentKey, ATabularController<?> tableController);

	/**
	 * Gets the relationship update map.
	 * 
	 * @return the Relationship Update Map
	 */
	public Map<MultiKey, List<RelationshipUpdate<?>>> getRUpdateMap();

	/**
	 * Removes the table controller.
	 * 
	 * @param parentKey
	 *            the parent key
	 * @param tableController
	 *            the table controller
	 */
	public void removeTableControllerRU(MultiKey parentKey, ATabularController<?> tableController);

	/**
	 * Ask for a new update on relationship in the cache.
	 * <ul>
	 * <li>If an "inverse" relationship update already exist, the "inverse" update is removes from cache and nothing is added.</li>
	 * <li>If the same relationship owns already an update, this update will be updated.</li>
	 * </ul>
	 * 
	 * @param <T>
	 *            the
	 * @param entity
	 *            the entity
	 * @param mappedByField
	 *            the mapped by field
	 * @param oldParent
	 *            the old parent
	 * @param newParent
	 *            the new parent
	 * @param type
	 *            the type
	 * @param fieldName
	 *            the field name
	 * @return the relationship update
	 */
	public <T> RelationshipUpdate<T> doRelationshipUpdate(IEntity<T> entity, String mappedByField, Object oldParent,
			Object newParent, int type, String fieldName);

	/**
	 * Gets the created entities.
	 * 
	 * @return the created entities
	 */
	public Map<Object, IEntity<?>> getCreatedEntities();

	/**
	 * Gets the previous deleted entities cached keys.
	 * 
	 * @return the deleted entities cached keys
	 */
	public Set<MultiKey> getDeletedEntitiesCachedKeys();

	/**
	 * Removes the relationship update.
	 * 
	 * @param multiKey
	 *            the multi key
	 * @param relationshipUpdate
	 *            the relationship update
	 */
	public void removeRelationshipUpdate(MultiKey multiKey, RelationshipUpdate<?> relationshipUpdate);

	/**
	 * Gets the table controller by cache key.
	 * 
	 * @return the table controller by cache key
	 */
	public Map<MultiKey, Set<ATabularController<?>>> getTableControllerByCKey();

	/**
	 * Gets the table controller by c key.
	 * 
	 * @param entity
	 *            the entity
	 * @return the table controller by c key
	 */
	public Set<ATabularController<?>> getTableControllerByCKey(IEntity<?> entity);

	/**
	 * Gets the table controller by parent cache key.
	 * 
	 * @return the table controller by parent cache key
	 */
	public Map<MultiKey, Set<ATabularController<?>>> getTableControllerByPKey();
}
