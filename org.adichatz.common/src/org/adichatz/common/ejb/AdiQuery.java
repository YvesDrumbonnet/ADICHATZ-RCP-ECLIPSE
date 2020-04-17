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
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiQuery.
 *
 * @author Yves Arpheuil
 */
public class AdiQuery implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5898428445702195651L;

	/**
	 * The Enum QUERY_TYPE.
	 *
	 * @author Yves Arpheuil
	 */
	public static enum QUERY_TYPE {
		JQL, SQL, SingleJQL, SingleSQL
	}

	/** The query string. */
	private String queryString;

	/** The parameters. */
	private Serializable[] parameters;

	/** The first result. */
	private int firstResult = 0;

	/** The max results. */
	private Integer maxResults;

	/** The bean class. */
	private Class<?> beanClass;

	/** The bean alias. */
	private String beanAlias;

	/** The jointure aliases. */
	private String[] jointureAliases = new String[0];

	/** The jointure paths. */
	private String[] jointurePaths = new String[0];

	/** The query type. */
	private QUERY_TYPE queryType;

	/** The query result. */
	private QueryResult queryResult;

	/** The paginated. */
	private boolean paginated;

	/**
	 * Instantiates a new adi query.
	 *
	 * @param queryType the query type
	 */
	public AdiQuery(QUERY_TYPE queryType) {
		this.queryType = queryType;
	}

	/**
	 * Instantiates a new adi query.
	 *
	 * @param queryType the query type
	 * @param queryString the query string
	 * @param parameters the parameters
	 */
	public AdiQuery(QUERY_TYPE queryType, String queryString, Serializable... parameters) {
		this.queryString = queryString;
		this.queryType = queryType;
		this.parameters = parameters;
	}

	/**
	 * Gets the query string.
	 *
	 * @return the query string
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Sets the query string.
	 *
	 * @param queryString the new query string
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Serializable[] getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(List<Serializable> parameters) {
		if (null == parameters || parameters.isEmpty())
			this.parameters = null;
		else
			this.parameters = parameters.toArray(new Serializable[parameters.size()]);
	}

	/**
	 * Gets the first result.
	 *
	 * @return the first result
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * Sets the first result.
	 *
	 * @param firstResult the new first result
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * Gets the max results.
	 *
	 * @return the max results
	 */
	public Integer getMaxResults() {
		return maxResults;
	}

	/**
	 * Sets the max results.
	 *
	 * @param maxResults the new max results
	 */
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * Gets the bean class.
	 *
	 * @return the bean class
	 */
	public Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * Adds the entity.
	 *
	 * @param beanAlias the bean alias
	 * @param beanClass the bean class
	 */
	public void addEntity(String beanAlias, Class<?> beanClass) {
		this.beanAlias = beanAlias;
		this.beanClass = beanClass;
	}

	/**
	 * Gets the bean alias.
	 *
	 * @return the bean alias
	 */
	public String getBeanAlias() {
		return beanAlias;
	}

	/**
	 * Adds the jointure (alias and path are added in respective arrays jointureAliases and jointurePaths).
	 * 
	 * @param alias
	 *            the alias
	 * @param path
	 *            the path
	 */
	public void addJointure(String alias, String path) {
		int length = this.jointureAliases.length;
		int newLength = length + 1;
		String[] temp = new String[newLength];
		if (0 != length)
			System.arraycopy(jointureAliases, 0, temp, 0, length);
		temp[length] = alias;
		jointureAliases = temp;
		temp = new String[newLength];
		if (0 != length)
			System.arraycopy(jointurePaths, 0, temp, 0, length);
		temp[length] = path;
		jointurePaths = temp;
	}

	/**
	 * Gets the jointure aliases.
	 *
	 * @return the jointure aliases
	 */
	public String[] getJointureAliases() {
		return jointureAliases;
	}

	/**
	 * Gets the jointure paths.
	 *
	 * @return the jointure paths
	 */
	public String[] getJointurePaths() {
		return jointurePaths;
	}

	/**
	 * Gets the query type.
	 *
	 * @return the query type
	 */
	public QUERY_TYPE getQueryType() {
		return queryType;
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
	 * @param queryResult the new query result
	 */
	public void setQueryResult(QueryResult queryResult) {
		this.queryResult = queryResult;
	}

	/**
	 * Checks if is paginated.
	 *
	 * @return true, if is paginated
	 */
	public boolean isPaginated() {
		return paginated;
	}

	/**
	 * Sets the paginated.
	 *
	 * @param paginated the new paginated
	 */
	public void setPaginated(boolean paginated) {
		this.paginated = paginated;
	}
}
