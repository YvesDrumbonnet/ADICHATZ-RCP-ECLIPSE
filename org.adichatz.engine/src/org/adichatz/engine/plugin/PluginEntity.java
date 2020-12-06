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
package org.adichatz.engine.plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.Session;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.model.AEntityMetaModel;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityMetaModel.
 * 
 * @author Adichatz
 */
public class PluginEntity<T> {

	/** The plugin resources. */
	AdiPluginResources pluginResources;

	/** The plugin entity tree. */
	APluginEntityTree pluginEntityTree;

	/** The privileges. */
	protected Map<Integer, Set<String>> privileges;

	/** The entity model. */
	private AEntityMetaModel<T> entityMetaModel;

	/** The image key map. */
	private Map<String, String> imageKeyMap = new HashMap<>();

	private Map<String, String> adiResourceUriMap = new HashMap<>();

	/** The entity keys. */
	private String[] entityKeys;

	/** The entity uri. */
	private String entityURI;

	/** The super entity uri. */
	private String superEntityURI;

	private Class<? extends T> uiBeanClass;

	/**
	 * Instantiates a new entity meta model.
	 * 
	 * @param entityURI
	 *            the entity uri
	 * @param metaModel
	 *            the meta model
	 */
	public PluginEntity(AdiPluginResources pluginResources, APluginEntityTree pluginEntityTree, String entityURI) {
		this.pluginResources = pluginResources;
		this.pluginEntityTree = pluginEntityTree;
		this.entityURI = entityURI;
	}

	/**
	 * Gets the entity uri.
	 * 
	 * @return the entity uri
	 */
	public String getEntityURI() {
		return entityURI;
	}

	/**
	 * Gets the super entity uri.
	 * 
	 * @return the super entity uri
	 */
	public String getSuperEntityURI() {
		return superEntityURI;
	}

	/**
	 * Sets the super entity uri.
	 * 
	 * @param superEntityURI
	 *            the new super entity uri
	 */
	public void setSuperEntityURI(String superEntityURI) {
		this.superEntityURI = superEntityURI;
	}

	/**
	 * Gets the entity keys.
	 * 
	 * @return the entity keys
	 */
	public String[] getEntityKeys() {
		if (null == entityKeys) {
			entityKeys = EngineTools.getInstanceKeys(entityURI);
		}
		return entityKeys;
	}

	/**
	 * Gets the image key map.
	 * 
	 * @return the image key map
	 */
	public Map<String, String> getImageKeyMap() {
		return imageKeyMap;
	}

	public Map<String, String> getAdiResourceUriMap() {
		return adiResourceUriMap;
	}

	/**
	 * Gets the entity model.
	 * 
	 * @return the entity model
	 */
	@SuppressWarnings("unchecked")
	public AEntityMetaModel<?> getEntityMetaModel() {
		if (null == entityMetaModel) {
			try {
				String entityMMClassName = pluginResources.getClassName(getEntityKeys()[2], getEntityKeys()[1]);
				GencodePath gencodePath = pluginResources.getGencodePath();
				entityMetaModel = (AEntityMetaModel<T>) gencodePath.instantiateClass(gencodePath.getClazz(entityMMClassName, true),
						new Class[] { PluginEntity.class }, new Object[] { this });
			} catch (Exception e) {
			}
		}
		return entityMetaModel;
	}

	/**
	 * Check privilege.
	 * 
	 * @param privilege
	 *            the privilege
	 * 
	 * @return true, if successful
	 */
	public boolean checkPrivilege(int privilege) {
		if (null == privileges)
			return true;
		Set<String> roles = privileges.get(privilege);
		if (null == roles)
			return true;
		for (String role : roles)
			if (AdichatzApplication.getInstance().getContextValue(Session.class).getRoles().contains(role))
				return true;
		return false;
	}

	/**
	 * Adds the privileges for the entity wrapper.
	 * 
	 * @param privilege
	 *            the privilege
	 * @param roleList
	 *            the role list
	 */
	public void addPrivileges(int privilege, String... roleList) {
		if (null != roleList) {
			if (null == privileges)
				privileges = new HashMap<Integer, Set<String>>();
			Set<String> roles = privileges.get(privilege);
			if (null == roles) {
				roles = new HashSet<String>();
				privileges.put(privilege, roles);
			}
			for (String role : roleList)
				roles.add(role);
		}
	}

	/**
	 * Gets the plugin entity tree.
	 *
	 * @return the plugin entity tree
	 */
	public APluginEntityTree getPluginEntityTree() {
		return pluginEntityTree;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public String getEntityId() {
		return EngineTools.getEntityId(getEntityKeys()[2]);
	}

	/**
	 * Gets the pojo class.
	 * 
	 * Pojo and bean class can be different when using interface (pojo class implements bean class).
	 *
	 * @return the pojo class
	 */
	public Class<? extends T> getUIBeanClass() {
		if (null == uiBeanClass && null == getEntityMetaModel())
			return null;
		return null == uiBeanClass ? entityMetaModel.getUIBeanClass() : uiBeanClass;
	}

	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}
}
