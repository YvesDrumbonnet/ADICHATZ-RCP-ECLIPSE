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
package org.adichatz.jpa.tabular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.viewer.IBoundContentProvider;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.wrapper.EntitySetContentProviderWrapper;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;

// TODO: Auto-generated Javadoc
/**
 * The Class EntitySetContentProvider.
 */
public class EntitySetContentProvider<T> extends JPAQueryContentProvider<T>
		implements IBoundContentProvider<T>, IMarshalledElement {

	/** The parent entity cache key. */
	protected MultiKey parentEntityCacheKey = null;

	/** The bean map. */
	protected Map<MultiKey, T> beanMap = new HashMap<MultiKey, T>();

	/** The entity set. */
	protected EntitySet<T> entitySet;

	/** The data cache. */
	protected ADataCache dataCache;

	/**
	 * Instantiates a new entity set content provider.
	 * 
	 * @param entitySet
	 *            the entity set
	 * @param queryTreeId
	 *            the query tree id
	 * @param querySubPackage
	 *            the query sub package
	 */
	public EntitySetContentProvider(EntitySet<T> entitySet, String adiQueryURI, String preferenceURI) {
		super(entitySet.getQueryManager(adiQueryURI, preferenceURI), adiQueryURI, preferenceURI);
		this.entitySet = entitySet;
		this.dataCache = entitySet.getEntityMM().getDataAccess().getDataCache();
	}

	/**
	 * Gets the bean map.
	 * 
	 * @return the beanMap
	 */
	public Map<MultiKey, T> getBeanMap() {
		return beanMap;
	}

	/**
	 * Gets the entity set.
	 * 
	 * @return the entity set
	 */
	public EntitySet<T> getEntitySet() {
		return entitySet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.QueryContentProvider#launchQuery()
	 */
	@Override
	public void launchQuery() {
		if (null != tabularController.getEntity() && IEntityConstants.MERGE >= tabularController.getEntity().getStatus()) {
			((JPAQueryManager<T>) queryManager).getQueryPreferenceManager().addParentParameter(entitySet.getParentClause(),
					tabularController.getEntity().getBean());
			super.launchQuery();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.QueryContentProvider#postRefresh()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void postRefresh() {
		beanMap.clear();
		if (null == queryResult) // New record
			return;
		AEntityMetaModel entityMM = queryManager.getEntityMM();
		for (Object element : queryResult.getQueryResultList()) {
			MultiKey cacheKey = entityMM.getDataAccess().getCacheKey(entityMM.getEntityId(), entityMM.getId(element));
			beanMap.put(cacheKey, (T) element);
			dataCache.addTabularCtlerForCKey(cacheKey, tabularController);
		}

		/* add tableController to cache in order to databind contents and update on relationship */
		parentEntityCacheKey = new MultiKey(tabularController.getEntity().getCacheKey(), entitySet.getFieldName(),
				entitySet.getMappedBy());
		dataCache.addTabularControllerRU(parentEntityCacheKey, tabularController);

		/*
		 * Adds or removes element according to not committed action on elements in the application
		 */
		List<RelationshipUpdate<?>> relationshipUpdates = dataCache.getRUpdateMap().get(parentEntityCacheKey);
		if (null != relationshipUpdates) {
			for (RelationshipUpdate<?> relationshipUpdate : relationshipUpdates) {
				if (parentEntityCacheKey.equals(relationshipUpdate.getOldParentMKey())) {
					removeEntity((IEntity<T>) relationshipUpdate.getEntity(), false);
				}
				if (parentEntityCacheKey.equals(relationshipUpdate.getNewParentMKey()))
					addEntity((IEntity<T>) relationshipUpdate.getEntity(), false);
			}
			tabularController.getViewer().refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATabularContentProvider#addEntity(org.adichatz.engine.cache.IEntity, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addEntity(IEntity<T> entity, boolean refresh) {
		try {
			T bean;
			if (null != queryManager.getLazyFetchMembers()) {
				bean = beanMap.get(entity.getCacheKey());
				/**
				 * The bean could be previously loaded and removed due to relationship update.<BR>
				 * This could be the case when refreshing after a remove link.<BR>
				 * In this case, do not fetch a new entity, especially since call of a refresh process inside a refresh process is
				 * not correct.
				 */
				if (null == bean) {
					List<String> lazyFetchMembers = new ArrayList<String>();
					for (String lazyFetch : queryManager.getLazyFetchMembers())
						lazyFetchMembers.add(lazyFetch);
					dataCache.fetchTransientEntity(entity.getBean(), null,
							lazyFetchMembers.toArray(new String[lazyFetchMembers.size()]));
					bean = entity.getBean();
				}
			} else
				bean = entity.getBean();
			// queryManager.getQueryResult() is different from tabularController.getViewer()).getInput()
			((QueryResult) tabularController.getViewer().getInput()).getQueryResultList().add(bean);
			((AbstractTableViewer) tabularController.getViewer()).add(bean);
			if (refresh) {
				tabularController.getViewer().refresh();
				tabularController.getViewer().setSelection(new StructuredSelection(new Object[] { entity.getBean() }));
			}

			beanMap.put(entity.getCacheKey(), bean);
			dataCache.addTabularCtlerForCKey(entity.getCacheKey(), tabularController);
		} catch (Exception e) {
			LogBroker.logError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATabularContentProvider#removeEntity(org.adichatz.engine.cache.IEntity, boolean)
	 */
	@Override
	public void removeEntity(IEntity<T> entity, boolean refresh) {
		if (!tabularController.getViewer().getSelection().isEmpty()) {
			MultiKey cacheKey = entitySet.getEntityMM().getDataAccess()
					.getCacheKey(((StructuredSelection) tabularController.getViewer().getSelection()).getFirstElement());
			if (entity.getCacheKey().equals(cacheKey))
				tabularController.getViewer().setSelection(StructuredSelection.EMPTY);
		}
		Object bean = beanMap.get(entity.getCacheKey());
		if (null != bean) {
			// queryManager.getQueryResult() is different from tabularController.getViewer()).getInput()
			((QueryResult) tabularController.getViewer().getInput()).getQueryResultList().remove(bean);
			beanMap.remove(entity.getCacheKey());
			((AbstractTableViewer) tabularController.getViewer()).remove(bean);
			if (refresh)
				tabularController.getViewer().refresh();
		}

		dataCache.removeTabularCtlerForCKey(entity.getCacheKey(), tabularController);
	}

	public boolean hasChange() {
		for (MultiKey multiKey : tabularController.getEntity().getRelationshipUpdateMap().keySet()) {
			if (entitySet.getFieldName().equals(multiKey.getKey(1)))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.QueryContentProvider#dispose()
	 */
	public void dispose() {
		if (null != parentEntityCacheKey) { // parentEntityCacheKey is null when items were not be initialized.
			dataCache.removeTabularControllerRU(parentEntityCacheKey, tabularController);
			for (MultiKey cacheKey : beanMap.keySet())
				dataCache.removeTabularCtlerForCKey(cacheKey, tabularController);
			beanMap.clear();
		}
	}

	@Override
	public IMarshalledWrapper preMarshal(boolean closingPart) {
		EntitySetContentProviderWrapper ascWrapper = new EntitySetContentProviderWrapper();
		ascWrapper.setId(ParamMap.CONTENT_PROVIDER);
		ascWrapper.setAdiResourceURI(adiQueryURI);
		ascWrapper.setFieldName(entitySet.getFieldName());
		ascWrapper.setParentEntityURI(entitySet.getEntityMM().getPluginEntity().getEntityURI());
		return ascWrapper;
	}
}
