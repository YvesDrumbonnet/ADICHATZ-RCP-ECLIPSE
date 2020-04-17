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
package org.adichatz.engine.plugin;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.data.PooledQueryResult;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.query.AQueryManager;

// TODO: Auto-generated Javadoc
/**
 * The Class APluginEntityTree.
 *
 * @author Yves Drumbonnet
 */
public class APluginEntityTree {

	/** The pooled query result. */
	private Map<MultiKey, PooledQueryResult> pooledQueryResultMap = new HashMap<>();

	/** The plugin map. */
	protected Map<String, PluginEntity<?>> pluginEntityURIMap = new HashMap<>();

	/** The plugin entity class map. */
	protected Map<String, PluginEntity<?>> pluginEntityClassMap = new HashMap<>();

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/**
	 * Gets the plugin resources.
	 *
	 * @return the plugin resources
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/**
	 * Gets the plugin entity URI map.
	 *
	 * @return the plugin entity URI map
	 */
	public Map<String, PluginEntity<?>> getPluginEntityURIMap() {
		return pluginEntityURIMap;
	}

	/**
	 * Gets the entity MetaModel.
	 * 
	 * @param entityURI
	 *            the entity URI
	 * @return the entity MetaModel
	 */
	public AEntityMetaModel<?> getEntityMM(String entityURI) {
		PluginEntity<?> pluginEntity = pluginEntityURIMap.get(entityURI);
		if (null != pluginEntity)
			return pluginEntity.getEntityMetaModel();
		return null;
	}

	/**
	 * Gets the entity MM.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the entity MM
	 */
	public AEntityMetaModel<?> getEntityMM(Class<?> clazz) {
		return getPluginEntity(clazz).getEntityMetaModel();
	}

	/**
	 * Gets the plugin entity.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the plugin entity
	 */
	public PluginEntity<?> getPluginEntity(Class<?> clazz) {
		if (clazz.isInterface())
			return pluginEntityClassMap.get(clazz.getName());

		PluginEntity<?> pluginEntity = null;
		Class<?> beanClass = clazz;
		while (null == pluginEntity && null != beanClass && !beanClass.equals(Object.class)) {
			pluginEntity = pluginEntityClassMap.get(beanClass.getName());
			beanClass = beanClass.getSuperclass();
		}
		return pluginEntity;
	}

	/**
	 * Gets the pooled query result.
	 * 
	 * @return the pooled query result
	 */
	public Map<MultiKey, PooledQueryResult> getPooledQueryResultMap() {
		return pooledQueryResultMap;
	}

	/**
	 * Gets the pooled query result.
	 *
	 * @param multiKey
	 *            the multi key
	 * @return the pooled query result
	 */
	public PooledQueryResult getPooledQueryResult(MultiKey multiKey) {
		PooledQueryResult pooledQueryResult = pooledQueryResultMap.get(multiKey);
		if (null == pooledQueryResult) {
			String[] instanceKeys = EngineTools.getInstanceKeys(multiKey.getString(0));
			AdiPluginResources queryPR = null == instanceKeys[0] ? pluginResources
					: AdichatzApplication.getPluginResources(instanceKeys[0]);
			AQueryManager<?> queryManager = (AQueryManager<?>) queryPR.getNewInstance(instanceKeys[2], instanceKeys[1]);
			pooledQueryResult = new PooledQueryResult(queryManager);
			pooledQueryResultMap.put(multiKey, pooledQueryResult);
		}
		return pooledQueryResult;
	}

	/**
	 * reload.
	 */
	public void reload() {
		pooledQueryResultMap.clear();
		pluginEntityURIMap.clear();
		pluginResources.getGencodePath().defineClassLoader(pluginResources.getParentClassLoader());
		pluginResources.loadPluginEntities();
	}

	/**
	 * Adds the plugin entity.
	 * 
	 * @param uiPluginResources
	 *            the ui plugin resources
	 * @param entityURI
	 *            the entity uri
	 * @return the plugin entity
	 */
	@SuppressWarnings("rawtypes")
	public PluginEntity<?> addPluginEntity(AdiPluginResources modelPluginResources, String entityURI) {
		PluginEntity<?> pluginEntity = pluginEntityURIMap.get(entityURI);
		if (null == pluginEntity) {
			pluginEntity = new PluginEntity(modelPluginResources, this, entityURI);
			pluginEntityURIMap.put(entityURI, pluginEntity);
		}
		if (!modelPluginResources.equals(pluginResources))
			modelPluginResources.getPluginEntityTree().getPluginEntityURIMap().put(pluginEntity.getEntityURI(), pluginEntity);
		return pluginEntity;
	}
}
