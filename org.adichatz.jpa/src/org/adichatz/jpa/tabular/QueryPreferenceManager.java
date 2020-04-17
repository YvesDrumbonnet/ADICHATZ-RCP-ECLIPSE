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
package org.adichatz.jpa.tabular;

import java.io.Serializable;
import java.util.List;

import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.query.IQueryPreferenceManager;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.wrapper.JointureWrapper;
import org.adichatz.jpa.wrapper.QueryOpenClauseWrapper;
import org.adichatz.jpa.wrapper.QueryPaginationWrapper;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.xjc.QueryOpenClauseType;
import org.adichatz.jpa.xjc.QueryParameterType;
import org.adichatz.jpa.xjc.QueryPreferenceType;

public class QueryPreferenceManager<T> implements IQueryPreferenceManager<T> {

	/** The UI plugin resources. */
	private AdiPluginResources uiPluginResources;

	protected boolean completed;

	/** The query count. */
	protected Long paginationQueryCount;

	/** The query preference. */
	private QueryPreferenceWrapper<T> initialQueryPreference;

	private QueryPreferenceWrapper<T> queryPreference;

	private JPAQueryManager<T> queryManager;

	/** The cursor pagination. */
	private int cursorPagination;

	public QueryPreferenceManager(JPAQueryManager<T> queryManager, AdiPluginResources uiPluginResources) {
		this.queryManager = queryManager;
		this.uiPluginResources = uiPluginResources;
	}

	public AdiPluginResources getUiPluginResources() {
		return uiPluginResources;
	}

	public QueryPreferenceWrapper<T> getQueryPreference() {
		return queryPreference;
	}

	public AQueryManager<T> getQueryManager() {
		return queryManager;
	}

	public QueryPreferenceWrapper<T> getInitialQueryPreference() {
		if (null == initialQueryPreference)
			initialQueryPreference = queryPreference.clone(null);
		return initialQueryPreference;
	}

	public void setInitialQueryPreference() {
		initialQueryPreference = queryPreference.clone(null);
	}

	/**
	 * Checks if is completed.
	 * 
	 * @return true, if is completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	public void refreshPagination(QueryResult queryResult) {
		if (null != queryResult.getQueryCount())
			paginationQueryCount = queryResult.getQueryCount();
	}

	/**
	 * Gets the pagination query count.
	 * 
	 * @return the pagination query count
	 */
	public Long getPaginationQueryCount() {
		return paginationQueryCount;
	}

	/**
	 * Gets the cursor pagination.
	 * 
	 * @return the cursor pagination
	 */
	public int getCursorPagination() {
		return cursorPagination;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.IQueryPreferenceManager#setCursorPagination(org.adichatz.common.ejb.AdiQuery,
	 * org.adichatz.common.ejb.QueryResult, boolean)
	 */
	public void setCursorPagination(AdiQuery adiQuery, QueryResult queryResult, boolean paginated) {
		cursorPagination = adiQuery.getFirstResult() + queryResult.getQueryResultList().size();
		if (null != queryResult.getQueryCount())
			paginationQueryCount = queryResult.getQueryCount();
		QueryPaginationWrapper pagination = getPagination();
		if (paginated) {
			if (null == adiQuery.getMaxResults() || queryResult.getQueryResultList().size() < adiQuery.getMaxResults()) {
				completed = true;
				paginationQueryCount = Long.valueOf(pagination.getFirstResult() + queryResult.getQueryResultList().size());
			} else
				adiQuery.setFirstResult(pagination.getFirstResult() + queryResult.getQueryResultList().size());
		} else {
			int queryCount = queryResult.getQueryResultList().size();
			completed = null == pagination.getCurrentMaxResults() || pagination.getCurrentMaxResults() > queryCount
					|| 0 == queryCount;
		}
	}

	public void setQueryPreference(QueryPreferenceWrapper<T> newQueryPreference) {
		this.queryPreference = newQueryPreference;
		getInitialQueryPreference();
	}

	public void refreshPreference() {
		queryPreference = initialQueryPreference.clone(null);
	}

	/**
	 * Adds the parent parameter.
	 *
	 * @param entityset
	 *            the entityset
	 * @param parentBean
	 *            the parent bean
	 * @return the query open clause type
	 */
	public QueryOpenClauseType addParentParameter(String clause, Object parentBean) {
		QueryOpenClauseType openClause = null;
		for (QueryOpenClauseType queryOpenClause : queryPreference.getOpenClause())
			if (EngineConstants.PARENT_BEAN_PARAM.equals(queryOpenClause.getTitle())) {
				openClause = queryOpenClause;
				break;
			}
		if (null == openClause) {
			openClause = new QueryOpenClauseWrapper();
			openClause.setTitle(EngineConstants.PARENT_BEAN_PARAM);
			queryPreference.getOpenClause().add(openClause);
		}
		openClause.setClause(clause);

		QueryParameterWrapper openParameter = new QueryParameterWrapper();
		openParameter.setValue((Serializable) parentBean);
		openClause.getParameter().clear();
		openClause.getParameter().add(openParameter);
		return openClause;
	}

	/**
	 * Merge.
	 *
	 * @param mergePreference
	 *            the merge preference
	 */
	public void merge(QueryPreferenceWrapper<T> mergePreference) {
		queryPreference.setPagination(mergePreference.getPagination());
		queryPreference.setFullTextClause(mergePreference.getFullTextClause());
		queryPreference.setOrderByClause(mergePreference.getOrderByClause());
		for (QueryParameterType mergeParameter : mergePreference.getParameter())
			mergeParameter((QueryParameterWrapper) mergeParameter, queryPreference.getParameter());
		for (QueryOpenClauseType mergeOpenClause : mergePreference.getOpenClause()) {
			boolean exist = false;
			for (QueryOpenClauseType localOpenClause : queryPreference.getOpenClause()) {
				if (localOpenClause.getTitle().equals(mergeOpenClause.getTitle())) {
					exist = true;
					if (!localOpenClause.isPermanent()) {
						for (QueryParameterType mergeParameter : mergeOpenClause.getParameter())
							mergeParameter((QueryParameterWrapper) mergeParameter, localOpenClause.getParameter());
					}
				}
			}
			if (!exist)
				queryPreference.getOpenClause().add(mergeOpenClause);
		}
	}

	/**
	 * Merge parameter.
	 *
	 * @param mergeParameter
	 *            the merge parameter
	 * @param parameters
	 *            the parameters
	 */
	private void mergeParameter(QueryParameterWrapper mergeParameter, List<QueryParameterType> parameters) {
		for (QueryParameterType parameter : parameters)
			if (parameter.getId().equals(mergeParameter.getId())) {
				if (!parameter.isPermanent())
					mergeParameter((QueryParameterWrapper) parameter, mergeParameter);
				break;
			}
	}

	/**
	 * Merge parameter.
	 * 
	 * Inject values from mergeParameter
	 *
	 * @param target
	 *            the target
	 * @param source
	 *            the merge parameter
	 */
	private void mergeParameter(QueryParameterWrapper target, QueryParameterWrapper source) {
		target.setPermanent(source.isPermanent());
		target.setVisible(source.isVisible());
		target.setExpression(source.getExpression());
		target.setBinaryExpression(source.getBinaryExpression());
		target.setSecondExpression(source.getSecondExpression());
		target.setBinarySecondExpression(source.getBinarySecondExpression());
		target.setEntityURI(source.getEntityURI());
		JointureWrapper jointure = queryManager.getJointureMap().get(target.getSuffix());
		target.initializeValues(null != jointure ? jointure.getEntityMM() : null);
		target.setColumnText(source.getColumnText());
		target.setProperty(source.getProperty());
		target.setPrompt(source.getPrompt());
		target.setSecondColumnText(source.getSecondColumnText());
		target.setOperator(source.getOperator());
		target.setValid(source.isMergeValid());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void injectQueryPreference(Object value) {
		QueryPreferenceType queryPreference = (QueryPreferenceType) value;
		QueryPreferenceWrapper<T> newQueryPreference = getInitialQueryPreference().clone(null);
		this.queryPreference = newQueryPreference;
		merge((QueryPreferenceWrapper<T>) queryPreference);
	}

	public QueryPaginationWrapper getPagination() {
		return (QueryPaginationWrapper) queryPreference.getPagination();
	}

	@Override
	public void setMaxResults(Integer maxResult) {
		queryPreference.getPagination().setMaxResults(maxResult);
	}

	@Override
	public Integer getCurrentMaxResults() {
		return ((QueryPaginationWrapper) queryPreference.getPagination()).getCurrentMaxResults();
	}

	@Override
	public void setCurrentMaxResults() {
		QueryPaginationWrapper pagination = (QueryPaginationWrapper) queryPreference.getPagination();
		pagination.setCurrentMaxResults(pagination.getMaxResults());
	}

	@Override
	public Integer getFirstResult() {
		return queryPreference.getPagination().getFirstResult();
	}

	@Override
	public void setFirstResult(Integer firstResult) {
		queryPreference.getPagination().setFirstResult(firstResult);
	}

}
