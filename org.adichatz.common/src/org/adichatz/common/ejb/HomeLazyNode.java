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
package org.adichatz.common.ejb;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.adichatz.common.ejb.remote.IAdiPersistenceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeLazyNode.
 * 
 * A node of the lazy node (Node of the decision tree of lazy fetches) server side.
 * 
 * @see org.adichatz.engine.cache.LazyFetchManager
 * @author Yves
 *
 */
public class HomeLazyNode implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1622507286139075730L;

	/** The parent bean. */
	private Object bean;

	/** The children. */
	private Set<HomeLazyNode> children = new HashSet<HomeLazyNode>();

	/** The field name. */
	private String fieldName;

	/** The initialize OneToOne. */
	private boolean initializeOTO;

	/** The query. */
	private AdiQuery query;

	/** The query result. */
	private QueryResult queryResult;

	/** The lazy fetch member. */
	private String lazyFetchMember;

	/**
	 * Instantiates a new home lazy node.
	 * 
	 * @param bean
	 *            the bean
	 */
	public HomeLazyNode(Object bean) {
		this.bean = bean;
	}

	public HomeLazyNode(String lazyFetchMember, String fieldName, boolean initializeOTO) {
		this(lazyFetchMember, fieldName);
		this.initializeOTO = initializeOTO;
	}

	/**
	 * Instantiates a new home lazy node.
	 * 
	 * @param lazyFetchMember
	 *            the lazy fetch member
	 * @param fieldName
	 *            the field name
	 */
	public HomeLazyNode(String lazyFetchMember, String fieldName) {
		this.fieldName = fieldName;
		this.lazyFetchMember = lazyFetchMember;
	}

	/**
	 * Instantiates a new home lazy node.
	 * 
	 * @param lazyFetchMember
	 *            the lazy fetch member
	 * @param query
	 *            the query
	 */
	public HomeLazyNode(String lazyFetchMember, AdiQuery query) {
		this.query = query;
		this.lazyFetchMember = lazyFetchMember;
	}

	/**
	 * Gets the bean.
	 * 
	 * @return the bean
	 */
	public Object getBean() {
		return bean;
	}

	/**
	 * Sets the bean.
	 * 
	 * @param bean
	 *            the new bean
	 */
	public void setBean(Object bean) {
		this.bean = bean;
	}

	/**
	 * Gets the children nodes.
	 * 
	 * @return the children
	 */
	public Set<HomeLazyNode> getChildren() {
		return children;
	}

	/**
	 * Gets the query result.
	 * 
	 * @return the query result
	 */
	public QueryResult getQueryResult() {
		return queryResult;
	}

	/**
	 * Sets the query result.
	 * 
	 * @param queryResult
	 *            the new query result
	 */
	public void setQueryResult(QueryResult queryResult) {
		this.queryResult = queryResult;
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public AdiQuery getQuery() {
		return query;
	}

	/**
	 * Gets the lazy fetch member.
	 * 
	 * @return the lazyFetchMember
	 */
	public String getLazyFetchMember() {
		return lazyFetchMember;
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	public boolean isInitializeOTO() {
		return initializeOTO;
	}

	/**
	 * Execute lazy.
	 * 
	 * @param adiPM
	 *            the adi pm
	 * @throws AdiPMException
	 *             the adi pm exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchFieldException
	 *             the no such field exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	public void executeLazy(IAdiPersistenceManager adiPM)
			throws AdiPMException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		for (HomeLazyNode lazyNode : children) {
			if (null != bean) {
				Field field = bean.getClass().getDeclaredField(lazyNode.fieldName);
				boolean accessible = field.canAccess(bean);
				field.setAccessible(true);
				Object childBean = field.get(bean);
				if (null != childBean) {
					childBean = adiPM.getFromProxy(childBean);
					lazyNode.setBean(childBean);
					field.set(bean, childBean);
				}
				field.setAccessible(accessible);
				lazyNode.executeLazy(adiPM);
			}
		}
	}
}
