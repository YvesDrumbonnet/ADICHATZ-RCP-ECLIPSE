/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe �
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement,
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.data;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.model.AEntityMetaModel;

// TODO: Auto-generated Javadoc
/**
 * The Class PlainDataCache.
 */
public class XjcDataCache extends ADataCache {

	/**
	 * Instantiates a new plain data cache.
	 * 
	 * @param controllerEngine
	 *            the controller engine
	 */
	public XjcDataCache(AdiPluginResources pluginResources, ADataAccess dataAccess) {
		super(dataAccess);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IDataCache#putNewEntity(java.lang.Class)
	 */
	@Override
	public <T> IEntity<T> putNewEntity(Class<T> beanClass) {
		try {
			T bean = beanClass.getDeclaredConstructor().newInstance();

			MultiKey cacheKey = dataAccess.getCacheKey(bean);
			XjcEntity<T> entity = new XjcEntity<T>(dataAccess, bean, IEntityConstants.PERSIST, cacheKey);
			cachedEntities.put(cacheKey, entity);
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
	 * @see org.adichatz.engine.data.ADataCache#fetchEntity(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <T> XjcEntity<T> fetchEntity(T bean) {
		if (null == bean)
			return null;
		MultiKey cacheKey = dataAccess.getCacheKey(bean);
		XjcEntity<T> entity = (XjcEntity<T>) cachedEntities.get(cacheKey);
		if (null == entity) {
			entity = new XjcEntity<T>(dataAccess, bean, IEntityConstants.RETRIEVE, cacheKey);
			cachedEntities.put(cacheKey, entity);
		}
		return entity;
	}

	public <T> XjcEntity<T> fetchEntity(T bean, XjcTreeElement treeElement) {
		XjcEntity<T> entity = fetchEntity(bean);
		entity.setTreeElement(treeElement);
		return entity;
	}

	public <T> XjcEntity<T> fetchEntity(T bean, MultiKey cacheKey) {
		@SuppressWarnings("unchecked")
		XjcEntity<T> entity = (XjcEntity<T>) cachedEntities.get(cacheKey);
		if (null == entity) {
			entity = new XjcEntity<T>(dataAccess, bean, IEntityConstants.RETRIEVE, cacheKey);
			cachedEntities.put(cacheKey, entity);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IDataCache#getTableControllerByCKey(org.adichatz.engine.cache.IEntity)
	 */
	public Set<ATabularController<?>> getTabularControllerByCKey(IEntity<?> entity) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IEntity<T> fetchEntity(AEntityMetaModel<T> entityMM, Object beanId, String... lazyFetchMembers) {
		// bean = beanId
		MultiKey cacheKey = dataAccess.getCacheKey(beanId);
		return (IEntity<T>) cachedEntities.get(cacheKey);
	}
}
