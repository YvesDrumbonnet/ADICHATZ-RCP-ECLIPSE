/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.jpa.tabular;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.viewer.IBoundContentProvider;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.wrapper.ListDetailContentProviderWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class EntitySetContentProvider.
 */
public class ListDetailContentProvider<T> extends JPAQueryContentProvider<T> implements IBoundContentProvider<T> {

	/** The parent entity cache key. */
	protected MultiKey parentEntityCacheKey = null;

	/** The bean map. */
	protected Map<MultiKey, T> beanMap = new HashMap<MultiKey, T>();

	/** The data cache. */
	protected ADataCache dataCache;

	protected AdiPluginResources modelPluginResources;

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
	@SuppressWarnings("unchecked")
	public ListDetailContentProvider(AdiPluginResources uiPluginResources, String adiQueryURI, String preferenceURI) {
		super((AQueryManager<T>) AdichatzApplication.prepareAndInstantiateClass(adiQueryURI,
				new Class[] { AdiPluginResources.class }, new Object[] { uiPluginResources }), adiQueryURI, preferenceURI);
		dataCache = queryManager.getEntityMM().getDataAccess().getDataCache();
	}

	/**
	 * Gets the bean map.
	 * 
	 * @return the beanMap
	 */
	public Map<MultiKey, T> getBeanMap() {
		return beanMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.QueryContentProvider#launchQuery()
	 */
	@Override
	public void launchQuery() {
		super.launchQuery();
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
		if (null != queryResult) { // e.g. is null when application server could not be reached
			AEntityMetaModel entityMM = queryManager.getEntityMM();
			for (Object element : queryResult.getQueryResultList()) {
				MultiKey cacheKey = entityMM.getDataAccess().getCacheKey(entityMM.getEntityId(), entityMM.getId(element));
				beanMap.put(cacheKey, (T) element);
				dataCache.addTabularCtlerForCKey(cacheKey, tabularController);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.QueryContentProvider#dispose()
	 */
	public void dispose() {
		if (null != parentEntityCacheKey) // parentEntityCacheKey is null when items were not be initialized.
			dataCache.removeTabularControllerRU(parentEntityCacheKey, tabularController);
		for (MultiKey cacheKey : beanMap.keySet())
			dataCache.removeTabularCtlerForCKey(cacheKey, tabularController);
		beanMap.clear();
	}

	@Override
	public IMarshalledWrapper preMarshal(boolean closingPart) {
		ListDetailContentProviderWrapper ldcpWrapper = new ListDetailContentProviderWrapper();
		ldcpWrapper.setId(ParamMap.CONTENT_PROVIDER);
		ldcpWrapper.setAdiResourceURI(adiQueryURI);
		ldcpWrapper.setPluginKey(
				((JPAQueryManager<T>) queryManager).getQueryPreferenceManager().getUiPluginResources().getPluginName());
		ldcpWrapper.setPreferenceURI(preferenceURI);
		return ldcpWrapper;
	}
}
