/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.engine.query;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.tabular.ATabularContentProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AQueryManager.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            Entity bean type
 */
public abstract class AQueryManager<T> {
	/*
	 * S T A T I C
	 */

	/** The Default max result. */
	public static Integer DEFAULT_MAX_RESULTS;

	/*
	 * end S T A T I C
	 */
	/** The completed. */

	/** The query result. */
	protected QueryResult queryResult;

	/** The entity MetaModel. */
	protected AEntityMetaModel<T> entityMM;

	/** The lazy fetch members. */
	protected Set<String> lazyFetchMembers = new HashSet<>();

	/** The content provider. */
	protected ATabularContentProvider<T> contentProvider;

	/**
	 * Gets the lazy fetch members.
	 * 
	 * @return the lazy fetch members
	 */
	public Set<String> getLazyFetchMembers() {
		return lazyFetchMembers;
	}

	/**
	 * Gets the first result.
	 * 
	 * @return the first result
	 */
	public int getFirstResult() {
		return 0;
	}

	/**
	 * Gets the max results.
	 * 
	 * @return the max results
	 */
	public Integer getMaxResults() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.IQueryManager#getQueryResult()
	 */
	/**
	 * Gets the query result.
	 * 
	 * @return the query result
	 */
	public QueryResult getQueryResult() {
		return queryResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.IQueryManager#setQueryResult(org.adichatz.common.ejb.QueryResult)
	 */
	/**
	 * Sets the query result.
	 * 
	 * @param queryResult
	 *            the new query result
	 */
	public void setQueryResult(QueryResult queryResult) {
		this.queryResult = queryResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.query.IQueryManager#launchQueryResult()
	 */
	/**
	 * Launch query result.
	 * 
	 * @return the query result
	 */
	public abstract QueryResult launchQueryResult();

	/**
	 * Gets the entity meta model.
	 * 
	 * @return the entity meta model
	 */
	public AEntityMetaModel<T> getEntityMM() {
		return entityMM;
	}

	/**
	 * Gets the content provider.
	 *
	 * @return the content provider
	 */
	public ATabularContentProvider<T> getContentProvider() {
		return contentProvider;
	}

	/**
	 * Sets the content provider.
	 * 
	 * @param contentProvider
	 *            the new content provider
	 */
	public void setContentProvider(ATabularContentProvider<T> contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * Checks if is paginated.
	 * 
	 * @return true, if is paginated
	 */
	public boolean isPaginated() {
		return false;
	}

	/**
	 * Adds the parent parameter.
	 * 
	 * @param expression
	 *            the expression
	 */
	public void addParentParameter(Object expression) {
	}

	/**
	 * Inits the pagination.
	 */
	public boolean initPagination() {
		return false;
	}

	/**
	 * Checks if is query completed.
	 *
	 * @return true, if is query completed
	 */
	public boolean isQueryCompleted() {
		return true;
	}

	/**
	 * Fire listener.
	 *
	 * @param eventType
	 *            the event type
	 */
	public void fireListener(int eventType) {
	}

	/**
	 * Gets the query preference manager.
	 *
	 * @return the query preference manager
	 */
	public IQueryPreferenceManager<T> getQueryPreferenceManager() {
		return null;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public AdiQuery getQuery() { // Used by statusbar
		return null;
	}
}
