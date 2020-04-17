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
package org.adichatz.jpa.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.adichatz.jpa.tabular.QueryPreferenceManager;
import org.adichatz.jpa.wrapper.JointureWrapper;
import org.adichatz.jpa.wrapper.QueryPaginationWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.xjc.PreferenceTree;
import org.adichatz.jpa.xjc.QueryPaginationType;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAQueryManager.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            the bean type
 */
public class JPAQueryManager<T> extends AQueryManager<T> {
	protected boolean valid = true;

	protected boolean queryValid;

	/** The query. */
	protected AdiQuery adiQuery;

	/** The suffix. */
	protected String suffix;

	/** The jointures. */
	// Ordered list of jointures (used for building query)
	protected List<JointureWrapper> jointures = new ArrayList<JointureWrapper>();

	/** The jointure map. */
	// Map of jointures linked to query manager reference by suffix
	protected Map<String, JointureWrapper> jointureMap = new HashMap<String, JointureWrapper>();

	/** The sql clause. */
	protected String sqlClause;

	/** The where clause. */
	protected String whereClause;

	/** The query builder. */
	protected IJPAQueryBuilder<T> queryBuilder;

	/** The listeners. */
	protected List<AListener> queryListeners = new ArrayList<AListener>();

	/** The customized query preference map. */
	protected Map<String, PreferenceTree> customizedPreferenceTreeMap = new HashMap<>();

	protected QueryPreferenceManager<T> queryPreferenceManager;

	/**
	 * Instantiates a new JPA query manager.
	 * 
	 * Needed for PlainBeanInterceptor (see QueryToolContainer).
	 */
	public JPAQueryManager() {
		super();
	}

	/**
	 * Instantiates a new JPA query manager.
	 *
	 * @param queryType
	 *            the query type
	 * @param suffix
	 *            the suffix
	 * @param uiPluginResources
	 *            the ui plugin resources
	 * @param lazyFetchMembers
	 *            the lazy fetch members
	 */
	public JPAQueryManager(AdiQuery.QUERY_TYPE queryType, String suffix, AdiPluginResources uiPluginResources,
			String... lazyFetchMembers) {
		queryPreferenceManager = new QueryPreferenceManager<T>(this, uiPluginResources);
		adiQuery = new AdiQuery(queryType);
		this.suffix = suffix;
		for (String lazyFetchMember : lazyFetchMembers)
			this.lazyFetchMembers.add(lazyFetchMember);
	}

	public List<JointureWrapper> getJointures() {
		return jointures;
	}

	public Map<String, JointureWrapper> getJointureMap() {
		return jointureMap;
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public AdiQuery getQuery() {
		return adiQuery;
	}

	/**
	 * Sets the query.
	 * 
	 * @param adiQuery
	 *            the new query
	 */
	public void setQuery(AdiQuery adiQuery) {
		this.adiQuery = adiQuery;
	}

	/**
	 * Gets the query builder.
	 *
	 * @return the query builder
	 */
	public IJPAQueryBuilder<T> getQueryBuilder() {
		return queryBuilder;
	}

	/**
	 * Gets the sql clause.
	 * 
	 * @return the sql clause
	 */
	public String getSqlClause() {
		return sqlClause;
	}

	/**
	 * Gets the where clause.
	 *
	 * @return the where clause
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.AQueryManager#launchQueryResult()
	 */
	public QueryResult launchQueryResult() {
		queryValid = true;
		fireListener(IEventType.PRE_QUERY);
		if (queryValid && valid) {
			QueryPaginationWrapper pagination = queryPreferenceManager.getPagination();
			adiQuery.setFirstResult(null == pagination.getFirstResult() ? 0 : pagination.getFirstResult());
			adiQuery.setMaxResults(pagination.getCurrentMaxResults());

			IJPAQueryBuilder<T> queryBuilder = null == this.queryBuilder ? new JPAQueryBuilder<T>(this) : this.queryBuilder;
			adiQuery.setQueryString(queryBuilder.buildSQLQuery());
			adiQuery.setPaginated(isPaginated());
			queryResult = ((JPADataAccess) getEntityMM().getDataAccess()).getQueryResult(adiQuery);
			if (null == queryResult) // error occurred
				return null;
			queryPreferenceManager.setCursorPagination(adiQuery, queryResult, isPaginated());

			fireListener(IEventType.POST_QUERY);
		}
		return queryResult;
	}

	public boolean isQueryValid() {
		return queryValid;
	}

	public void setQueryValid(boolean queryValid) {
		this.queryValid = queryValid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * Fire listener.
	 *
	 * @param eventType
	 *            the event type
	 */
	public void fireListener(int eventType) {
		if (null != queryListeners)
			AListener.fireListener(queryListeners, new AdiEvent(eventType));
	}

	/**
	 * Gets the query listeners.
	 *
	 * @return the query listeners
	 */
	public List<AListener> getQueryListeners() {
		if (null == queryListeners)
			queryListeners = new ArrayList<>();
		return queryListeners;
	}

	/**
	 * Gets the customized preference tree map.
	 *
	 * @return the customized preference tree map
	 */
	public Map<String, PreferenceTree> getCustomizedPreferenceTreeMap() {
		return customizedPreferenceTreeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.AQueryManager#getQueryPreferenceManager()
	 */
	@Override
	public QueryPreferenceManager<T> getQueryPreferenceManager() {
		return queryPreferenceManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.AQueryManager#isPaginated()
	 */
	@Override
	public boolean isPaginated() {
		QueryPreferenceWrapper<T> queryPreference = queryPreferenceManager.getQueryPreference();
		return null != queryPreference && null != queryPreference.getPagination()
				&& null != queryPreference.getPagination().getMaxResults();
	}

	/**
	 * Inits the pagination.
	 */
	public boolean initPagination() {
		if (contentProvider instanceof EntitySetContentProvider)
			return false;
		QueryPaginationType pagination = queryPreferenceManager.getQueryPreference().getPagination();
		if (null == pagination.getMaxResults() && null != DEFAULT_MAX_RESULTS)
			pagination.setMaxResults(DEFAULT_MAX_RESULTS);
		return true;
	}

}
