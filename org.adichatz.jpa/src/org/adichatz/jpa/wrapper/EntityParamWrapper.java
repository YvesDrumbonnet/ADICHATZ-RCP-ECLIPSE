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
package org.adichatz.jpa.wrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.xjc.EntityParamType;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityParamWrapper.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("serial")
public class EntityParamWrapper extends EntityParamType implements IMarshalledWrapper, IMarshalledElement {

	/** The entity. */
	private Object entity;

	private Set<String> lazyFetchSet = new HashSet<>();

	/**
	 * Instantiates a new query entity wrapper.
	 * 
	 * @param entity
	 *            the entity
	 */
	public EntityParamWrapper(Object entity) {
		this.entity = entity;
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public Object getEntity() {
		return entity;
	}

	/**
	 * Sets the entity.
	 * 
	 * @param entity
	 *            the new entity
	 */
	public void setEntity(Object entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object someObject) {
		if (!(someObject instanceof EntityParamType))
			return false;
		EntityParamType other = (EntityParamType) someObject;
		if (!Utilities.equals(entityURI, other.getEntityURI()))
			return false;
		if (!Utilities.equals(binaryIdvalue, other.getBinaryIdvalue()))
			return false;
		if (!Utilities.equals(idvalue, other.getIdvalue()))
			return false;
		return true;
	}

	@Override
	public Object preMarshal(boolean closingPart) {
		setEntity(null);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.IMarshalledWrapper#postUnmarshal()
	 */
	@Override
	public Object postUnmarshal() {
		String[] instanceKeys = EngineTools.getInstanceKeys(entityURI);
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(instanceKeys[0]);
		AEntityMetaModel<?> entityMM = pluginResources.getPluginEntityTree().getEntityMM(entityURI);

		Class<?> idType = entityMM.getFieldMap().get(entityMM.getIdFieldName()).getGetMethod().getReturnType();
		Object id = EngineTools.getValueFromXml(idType, idvalue, binaryIdvalue);
		if (EngineTools.isEmpty(lazyFetches))
			return entityMM.getDataAccess().getDataCache().fetchEntity(entityMM, id);
		StringTokenizer tokenizer = new StringTokenizer(lazyFetches, ",");
		String[] lazyFetchMembers = new String[tokenizer.countTokens()];
		int i = 0;
		while (tokenizer.hasMoreElements()) {
			String lazyFetch = tokenizer.nextToken().trim();
			lazyFetchMembers[i++] = lazyFetch;
		}
		return (IEntity<?>) entityMM.getDataAccess().getDataCache().fetchEntity(entityMM, id, lazyFetchMembers);
	}

	public void addLazyFetchMember(String lazyFetchMember) {
		lazyFetchSet.add(lazyFetchMember);
	}

	public void marshallLazyFetches() {
		if (!lazyFetchSet.isEmpty()) {
			StringBuffer lazyFetchSB = new StringBuffer();
			for (String lazyFetch : lazyFetchSet)
				lazyFetchSB.append(", ").append(lazyFetch);
			lazyFetches = lazyFetchSB.substring(2);
		}
	}
}
