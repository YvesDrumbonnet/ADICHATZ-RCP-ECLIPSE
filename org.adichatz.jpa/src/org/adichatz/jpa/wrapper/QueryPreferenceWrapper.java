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
package org.adichatz.jpa.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.XmlEqualsBuilder;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.xjc.QueryOpenClauseType;
import org.adichatz.jpa.xjc.QueryPaginationType;
import org.adichatz.jpa.xjc.QueryParameterType;
import org.adichatz.jpa.xjc.QueryPreferenceType;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryPreferenceWrapper.
 *
 * @param <T>
 *            the generic type
 */
public class QueryPreferenceWrapper<T> extends QueryPreferenceType implements IMarshalledWrapper {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4603963468415883401L;

	/** The visible parameters. */
	private List<QueryParameterType> visibleParameters;

	/**
	 * Instantiates a new query preference wrapper.
	 */
	public QueryPreferenceWrapper() {
		pagination = new QueryPaginationWrapper();
	}

	/**
	 * Clone.
	 *
	 * @param userQueryPreference
	 *            the preference passed User preferences
	 * @return the query preference wrapper
	 */
	public QueryPreferenceWrapper<T> clone(QueryPreferenceWrapper<T> userQueryPreference) {
		QueryPreferenceWrapper<T> clone = new QueryPreferenceWrapper<T>();
		for (QueryParameterType queryParameter : getParameter()) {
			QueryParameterWrapper cloneableParameter = ((QueryParameterWrapper) queryParameter).getCloneable(userQueryPreference);
			if (null != cloneableParameter)
				clone.getParameter().add(cloneableParameter.clone());
		}
		clone.id = id;
		for (QueryOpenClauseType queryOpenClause : getOpenClause()) {
			QueryOpenClauseWrapper cloneOpenClause = new QueryOpenClauseWrapper();
			cloneOpenClause.setClause(queryOpenClause.getClause());
			cloneOpenClause.setValid(queryOpenClause.isValid());
			cloneOpenClause.setTitle(queryOpenClause.getTitle());
			for (QueryParameterType parameter : queryOpenClause.getParameter())
				cloneOpenClause.getParameter().add(((QueryParameterWrapper) parameter).clone());
			clone.getOpenClause().add(cloneOpenClause);
		}
		clone.fullTextClause = fullTextClause;
		clone.orderByClause = orderByClause;
		clone.pagination = (QueryPaginationType) EngineTools.cloneSerializable(pagination);
		return clone;
	}

	/**
	 * Before marshalling.
	 */
	public void beforeMarshalling() {
		for (QueryParameterType parameter : getParameter())
			((QueryParameterWrapper) parameter).setExpressions();
		for (QueryOpenClauseType openClause : getOpenClause()) {
			for (QueryParameterType parameter : openClause.getParameter())
				((QueryParameterWrapper) parameter).setExpressions();
		}
	}

	public QueryParameterWrapper getParameter(String parameterId) {
		for (QueryParameterType parameter : getParameter())
			if (parameter.getId().equals(parameterId))
				return (QueryParameterWrapper) parameter;
		return null;
	}

	/**
	 * Initialize parameters.
	 */
	public void initializeParameters(AQueryManager<T> queryManager) {
		for (QueryParameterType parameterType : getParameter()) {
			QueryParameterWrapper parameter = (QueryParameterWrapper) parameterType;
			if (null != parameter.getExpression() || null != parameter.getBinaryExpression() && null == parameter.getValue())
				parameter.initializeValues(
						((JPAQueryManager<T>) queryManager).getJointureMap().get(parameter.getSuffix()).getEntityMM());
		}
		for (QueryOpenClauseType openClause : getOpenClause()) {
			for (QueryParameterType parameterType : openClause.getParameter()) {
				QueryParameterWrapper parameter = (QueryParameterWrapper) parameterType;
				JointureWrapper jointure = ((JPAQueryManager<T>) queryManager).getJointureMap().get(parameter.getSuffix());
				if (null != parameter.getExpression() || null != parameter.getBinaryExpression() && null == parameter.getValue())
					parameter.initializeValues(null == jointure ? null : jointure.getEntityMM());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return XmlEqualsBuilder.reflectionEquals(this, obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.IMarshalledWrapper#postUnmarshal()
	 */
	@Override
	public Object postUnmarshal() {
		QueryPreferenceWrapper<T> clone = clone(null);
		if (null != pagination)
			((QueryPaginationWrapper) clone.getPagination()).setCompleted(false);
		return clone;
	}

	/**
	 * Gets the visible parameters.
	 *
	 * @return the visible parameters
	 */
	public List<QueryParameterType> getVisibleParameters() {

		if (visibleParameters == null) {
			visibleParameters = new ArrayList<QueryParameterType>();
			for (QueryParameterType parameter : getParameter())
				if (parameter.isVisible())
					visibleParameters.add(parameter);
		}
		return this.visibleParameters;
	}

	/**
	 * Checks for parameters.
	 *
	 * @return true, if successful
	 */
	public boolean hasParameters() {
		for (QueryParameterType columnParameter : getParameter()) {
			Boolean valid = ((QueryParameterWrapper) columnParameter).isMergeValid();
			if (columnParameter.isVisible() && null != valid && valid) {
				return true;
			}
		}
		for (QueryOpenClauseType openClause : getOpenClause()) {
			if (openClause.isValid()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the visible open clause.
	 *
	 * @return the visible open clause
	 */
	public List<QueryOpenClauseType> getVisibleOpenClause() {
		if (openClause == null)
			openClause = new ArrayList<QueryOpenClauseType>();
		return openClause;
	}

	/**
	 * Checks if is open clause visible.
	 *
	 * @return true, if is open clause visible
	 */
	public boolean isOpenClauseVisible() {
		return !getVisibleOpenClause().isEmpty();
	}
}
