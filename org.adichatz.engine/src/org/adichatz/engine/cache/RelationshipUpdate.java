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

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.ADataCache;

/**
 * The Class RelationshipUpdate. Element of the buffer of updates on Many-to-Many or One-To-Many or Many-to-One relationship.
 * 
 * @author Yves Drumbonnet
 *
 */
public class RelationshipUpdate<T> {

	/** The entity. */
	private IEntity<T> entity;

	/** The old parent. */
	private Object oldParentBean;

	/** The old parent m key. */
	private MultiKey oldParentMKey;

	/** The new parent. */
	private Object newParentBean;

	/** The new parent m key. */
	private MultiKey newParentMKey;

	/** The parent class. */
	private Class<?> parentClass;

	/** The type. */
	private IEntityConstants.relationshipUpdateType type;

	/** The field name. */
	private String fieldName;

	private RelationshipUpdate<?> linkedRelationship;

	private ADataAccess dataAccess;

	private String mappedByField;

	/**
	 * Instantiates a new relationship update.
	 * 
	 * @param entity
	 *            the entity
	 * @param mappedByField
	 *            the mapped by field
	 * @param oldParentBean
	 *            the old parent bean
	 * @param newParentBean
	 *            the new parent bean
	 * @param type
	 *            the type
	 * @param fieldName
	 *            the field name
	 */
	public RelationshipUpdate(IEntity<T> entity, String mappedByField, Object oldParentBean, Object newParentBean,
			IEntityConstants.relationshipUpdateType type, String fieldName) {
		this.entity = entity;
		this.type = type;
		this.fieldName = fieldName;
		this.dataAccess = entity.getEntityMM().getDataAccess();
		this.mappedByField = mappedByField;
		if (null != oldParentBean) {
			oldParentMKey = new MultiKey(dataAccess.getCacheKey(oldParentBean), mappedByField, fieldName);
			this.oldParentBean = oldParentBean;
			parentClass = oldParentBean.getClass();
			dataAccess.getDataCache().removeFromTabularControllersRU(this, oldParentMKey);
			dataAccess.getDataCache().addRelationshipUpdate(oldParentMKey, this);
		}
		if (null != newParentBean) {
			parentClass = newParentBean.getClass();
			newParentMKey = new MultiKey(dataAccess.getCacheKey(newParentBean), mappedByField, fieldName);
			this.newParentBean = newParentBean;
			dataAccess.getDataCache().addToTabularControllersRU(entity, newParentMKey, false);
			dataAccess.getDataCache().addRelationshipUpdate(newParentMKey, this);
		}
		entity.getRelationshipUpdateMap().put(
				ADataCache.getEntityMultiKey(mappedByField, fieldName, type, oldParentBean, newParentBean), this);
	}

	public String getMappedByField() {
		return mappedByField;
	}

	/**
	 * Gets the old parent multi key.
	 * 
	 * @return the oldParentEntityMKey
	 */
	public MultiKey getOldParentMKey() {
		return oldParentMKey;
	}

	/**
	 * Gets the new parent multi key.
	 * 
	 * @return the newParentEntityMKey
	 */
	public MultiKey getNewParentMKey() {
		return newParentMKey;
	}

	/**
	 * Sets the new parent multi key.
	 * 
	 * @param newParentMKey
	 *            the newParentMKey to set
	 */
	public void setNewParentMKey(MultiKey newParentMKey) {
		this.newParentMKey = newParentMKey;
	}

	/**
	 * Gets the old parent bean.
	 * 
	 * @return the oldParentBean
	 */
	public Object getOldParentBean() {
		return oldParentBean;
	}

	/**
	 * Gets the new parent bean.
	 * 
	 * @return the newParentBean
	 */
	public Object getNewParentBean() {
		return newParentBean;
	}

	/**
	 * Gets the parent class.
	 * 
	 * @return the parentClass
	 */
	public Class<?> getParentClass() {
		return parentClass;
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public IEntity<T> getEntity() {
		return entity;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public IEntityConstants.relationshipUpdateType getType() {
		return type;
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Gets the linked relationship.
	 * 
	 * @return the linkedRelationship
	 */
	public RelationshipUpdate<?> getLinkedRelationship() {
		return linkedRelationship;
	}

	/**
	 * Sets the linked relationship.
	 * 
	 * @param linkedRelationship
	 *            the linkedRelationship to set
	 */
	public void setLinkedRelationship(RelationshipUpdate<?> linkedRelationship) {
		this.linkedRelationship = linkedRelationship;
	}

}
