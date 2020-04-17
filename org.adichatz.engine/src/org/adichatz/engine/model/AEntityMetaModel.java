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
package org.adichatz.engine.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.AEntityCallfore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.ICompositeKeyStrategyFactory;
import org.adichatz.engine.plugin.PluginEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class AEntityMetaModel.
 * 
 * @param <T>
 *            the
 * @author Yves Drumbonnet
 * 
 */
public abstract class AEntityMetaModel<T> {

	/** The application entity. */
	protected PluginEntity<?> pluginEntity;

	/** The lazy field Map. */
	protected Map<String, PropertyField> fieldMap = new HashMap<String, PropertyField>();

	/** The identitier field name. */
	protected String idFieldName;

	protected List<Class<? extends AEntityCallfore<T>>> callforeClasses;

	protected ICompositeKeyStrategyFactory cksFactory;

	protected boolean oneToOne;

	/**
	 * Instantiates a new a entity meta model.
	 * 
	 * @param pluginEntity
	 *            the plugin entity
	 */
	public AEntityMetaModel(PluginEntity<?> pluginEntity) {
		this.pluginEntity = pluginEntity;
	}

	/**
	 * Gets the fields.
	 * 
	 * @return the fields
	 */
	public Map<String, PropertyField> getFieldMap() {
		return fieldMap;
	}

	public boolean isOneToOne() {
		return oneToOne;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public String getEntityId() {
		return pluginEntity.getEntityId();
	}

	/**
	 * Gets the plugin entity.
	 * 
	 * @return the plugin entity
	 */
	public PluginEntity<?> getPluginEntity() {
		return pluginEntity;
	}

	/**
	 * Gets the id.
	 * 
	 * @param bean
	 *            the bean
	 * 
	 * @return the id
	 */
	public Object getId(Object bean) {
		return bean.hashCode();
	}

	/**
	 * Sets the id.
	 * 
	 * @param bean
	 *            the bean
	 * @param beanId
	 *            the bean id
	 */
	public void setId(Object bean, Object beanId) {
	}

	/**
	 * Gets the bean class.
	 * 
	 * @return the bean class
	 */
	public abstract Class<? extends T> getBeanClass();

	/**
	 * Gets the entity set.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the entity set
	 */
	public EntitySet<?> getEntitySet(String id) {
		if (null == fieldMap)
			return null;
		return (EntitySet<?>) fieldMap.get(id);
	}

	/**
	 * Gets the id field name.
	 * 
	 * @return the id field name
	 */
	public String getIdFieldName() {
		return idFieldName;
	}

	/**
	 * Gets the data access.
	 * 
	 * @return the data access
	 */
	public ADataAccess getDataAccess() {
		return pluginEntity.getPluginResources().getDataAccess();
	}

	/**
	 * Gets the id string.
	 * 
	 * @param bean
	 *            the bean
	 * 
	 * @return the id string
	 */
	public String getIdString(Object bean) {
		Object id = getId(bean);
		return String.valueOf(null == id ? "" : id);
	}

	public List<Class<? extends AEntityCallfore<T>>> getCallforeClasses() {
		return callforeClasses;
	}

	/**
	 * Gets the cks factory.
	 * 
	 * @return the cks factory
	 */
	public ICompositeKeyStrategyFactory getCksFactory() {
		return cksFactory;
	}

	public Class<? extends T> getUIBeanClass() {
		return getBeanClass();
	}

}
