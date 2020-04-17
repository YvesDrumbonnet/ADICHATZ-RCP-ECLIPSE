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
package org.adichatz.common.ejb.extra;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.ICompositeKeyStrategy;
import org.adichatz.common.ejb.ProxyEntity;

/**
 * The Class CompositeKeyStrategy.
 * 
 * Allows to pass on data to automatically createt new identifier where identifier is like:<br>
 * <ul>
 * <li>parent identifier</li>
 * <li>increment</li>
 * </ul>
 * 
 * @see org.adichatz.common.ejb.NextValueGenerator
 * @see org.adichatz.common.ejb.AAdiPersistenceManager
 * 
 * @author Yves Drumbonnet
 *
 */
public class CompositeKeyStrategy implements ICompositeKeyStrategy, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2738775685797042578L;

	private Object id;

	private String maxQueryStr;

	private Object parentBean;

	private String incrementFieldName;

	private String idFieldName;

	private ICompositeKeyStrategy.INCREMENT_TYPE incrementType;

	public CompositeKeyStrategy(Object parentBean, Object id, String idFieldName, String incrementFieldName, String maxQueryStr,
			ICompositeKeyStrategy.INCREMENT_TYPE incrementType) {
		this.id = id;
		this.idFieldName = idFieldName;
		this.incrementFieldName = incrementFieldName;
		this.maxQueryStr = maxQueryStr;
		this.parentBean = parentBean;
		this.incrementType = incrementType;
	}

	public String getMaxQueryStr() {
		return maxQueryStr;
	}

	public Object getParentBean() {
		return parentBean;
	}

	public void computeCompositeKey(ProxyEntity<?> proxyEntity, Object nextValue) throws AdiPMException {
		try {
			Field field = id.getClass().getDeclaredField(incrementFieldName);
			boolean accessible = field.canAccess(proxyEntity.getBean());
			field.setAccessible(true);
			field.set(id, nextValue);
			field.setAccessible(accessible);

			field = proxyEntity.getBeanClass().getDeclaredField(idFieldName);
			field.setAccessible(true);
			field.set(proxyEntity.getBean(), id);
			field.setAccessible(accessible);

		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new AdiPMException(e, proxyEntity.getBean());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.common.ejb.ICompositeKeyStrategy#getIncrementType()
	 */
	@Override
	public INCREMENT_TYPE getIncrementType() {
		return incrementType;
	}
}
