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

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.InvocationTargetException;

import org.adichatz.common.ejb.ProxyEntity;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.model.AEntityMetaModel;

// TODO: Auto-generated Javadoc
/**
 * The Class JPADataCache.
 * 
 * Manage all elements of the Adichatz Data cache that is to say
 * <ul>
 * <li>Relationship update: changes on Many-to-Many, or One-to-Many or Many-to-One relationship.</li>
 * <li>Entity Wrapper: stores meta data on entities</li>
 * <li>Entities</li>
 * <li>Links between entites and tabulars (table, grid...)</li>
 * </ul>
 * 
 * @author Yves Drumbonnet
 * 
 */
public class JPADataCache extends ADataCache {

	/**
	 * Instantiates a new JPA data cache.
	 *
	 * @param dataAccess
	 *            the data access
	 */
	public JPADataCache(JPADataAccess dataAccess) {
		super(dataAccess);
	}

	/**
	 * Fetch entity.
	 *
	 * @param <T>
	 *            the generic type
	 * @param bean
	 *            the bean
	 * @return the entity< t>
	 */
	@SuppressWarnings("unchecked")
	public <T> JPAEntity<T> fetchEntity(T bean) {
		if (null == bean)
			return null;
		JPAEntity<T> entity = (JPAEntity<T>) createdEntities.get(bean);
		if (null == entity)
			entity = (JPAEntity<T>) cachedEntities.get(dataAccess.getCacheKey(bean));
		if (null == entity) {
			entity = new JPAEntity<T>(getDataAccess(), bean, IEntityConstants.RETRIEVE);
		}
		cachedEntities.put(entity.getCacheKey(), entity);
		return entity;
	}

	/**
	 * Put new entity. The bean is created (do not exist on the database). Put a entity wrapping the bean in the cache.
	 *
	 * @param <T>
	 *            the generic type
	 * @param beanClass
	 *            the bean class
	 * @return the entity< t>
	 */
	@SuppressWarnings("unchecked")
	public <T> IEntity<T> putNewEntity(Class<T> beanClass) {
		try {
			if (beanClass.isInterface()) {
				AdiPluginResources pluginResources = dataAccess.getPluginResources();
				beanClass = (Class<T>) pluginResources.getPluginEntityTree().getPluginEntity(beanClass).getEntityMetaModel()
						.getBeanClass();
			}
			T bean = beanClass.getDeclaredConstructor().newInstance();
			JPAEntity<T> entity = new JPAEntity<T>(getDataAccess(), bean, IEntityConstants.PERSIST);
			cachedEntities.put(entity.getCacheKey(), entity);
			return entity;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logError("Error instantiating a new entity (" + beanClass.getName() + ")!", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.ADataCache#fetchEntity(org.adichatz.engine.model.AEntityMetaModel, java.lang.Object,
	 * java.lang.String[])
	 */
	public <T> IEntity<T> fetchEntity(AEntityMetaModel<T> entityMM, Object beanId, String... lazyFetchMembers) {
		ProxyEntity<T> proxyEntity = ((JPADataAccess) dataAccess).findProxyEntity(entityMM.getBeanClass(), beanId,
				lazyFetchMembers);
		T bean = (T) proxyEntity.getBean();
		IEntity<T> entity = fetchEntity(bean);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.ADataCache#getDataAccess()
	 */
	@Override
	public JPADataAccess getDataAccess() {
		return (JPADataAccess) super.getDataAccess();
	}
}
