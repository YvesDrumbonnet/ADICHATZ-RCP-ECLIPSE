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
package org.adichatz.jpa.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.jpa.wrapper.JointureWrapper;
import org.adichatz.jpa.wrapper.QueryOpenClauseWrapper;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.xjc.QueryOpenClauseType;
import org.adichatz.jpa.xjc.QueryParameterType;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAQueryBuilder.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            the generic type
 */
public class JPAQueryBuilder<T> implements IJPAQueryBuilder<T> {

	/** The start JPQL. */
	protected String startJPQL = "<span>";

	/** The start name. */
	protected String startName = "<span>";

	/** The start suffix. */
	protected String startSuffix = "<span>";

	/** The query manager. */
	protected JPAQueryManager<T> queryManager;

	/** The add form text tag. */
	protected boolean addFormTextTag;

	/** The has where clause. */
	protected boolean hasWhereClause;

	/** The query SB. */
	protected StringBuffer querySB;

	/** The meta model. */
	protected APluginEntityTree pluginEnitityTree;

	/** The param index. */
	protected int paramIndex = 1;

	/** The is JPQL. */
	protected boolean isJPQL;

	/** The query preference. */
	private QueryPreferenceWrapper<T> queryPreference;

	/**
	 * Instantiates a new JPA query builder.
	 *
	 * @param queryManager
	 *            the query manager
	 */
	public JPAQueryBuilder(JPAQueryManager<T> queryManager) {
		this.queryManager = queryManager;
		this.pluginEnitityTree = queryManager.getEntityMM().getPluginEntity().getPluginEntityTree();
		this.startJPQL = "<span  color=\"jpql\" font=\"normal\">";
		this.startName = "<span color=\"name\" font=\"normal\">";
		this.startSuffix = "<span color=\"suffix\" font=\"suffix\">";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.query.IJPAQueryBuilder#buildSQLQuery()
	 */
	@Override
	public String buildSQLQuery() {
		this.addFormTextTag = false;
		return buildQuery().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.query.IJPAQueryBuilder#buildQueryText()
	 */
	@Override
	public String buildQueryText() {
		this.addFormTextTag = true;
		return buildQuery().toString();
	}

	/**
	 * Builds the query.
	 *
	 * @return the string buffer
	 */
	public StringBuffer buildQuery() {
		queryPreference = queryManager.getQueryPreferenceManager().getQueryPreference();
		queryPreference.initializeParameters(queryManager);
		hasWhereClause = false;
		querySB = new StringBuffer();
		paramIndex = 1;
		if (addFormTextTag)
			querySB.append("<form><p>");
		List<Serializable> parameters = new ArrayList<Serializable>();
		isJPQL = AdiQuery.QUERY_TYPE.JQL == queryManager.getQuery().getQueryType();
		if (isJPQL) {
			addJPQLJointure();
		} else {
			addSQLClause();
		}
		if (addFormTextTag)
			querySB.append("<br/>");

		if (null != queryManager.getWhereClause()) {
			addWhereClause();
		}

		if (null != queryPreference) {
			addParameters(parameters);
			addOpenCLause(parameters);
			addFullTextClause();
			addOrderByCLause();
			queryManager.getQuery().setParameters(parameters);
		}
		if (addFormTextTag)
			querySB.append("</p></form>");

		return querySB;
	}

	/**
	 * Adds the full text clause.
	 */
	protected void addFullTextClause() {
	}

	/**
	 * Adds the open C lause.
	 *
	 * @param parameters
	 *            the parameters
	 */
	protected void addOpenCLause(List<Serializable> parameters) {
		for (QueryOpenClauseType openClause : queryPreference.getOpenClause()) {
			((QueryOpenClauseWrapper) openClause).computeValid();
			if (openClause.isValid()) {
				append("    ", " ");
				appendAndOrWhere();
				String whereClause = openClause.getClause();
				if (addFormTextTag) {
					whereClause = whereClause.replace("<", "&lt;");
					whereClause = whereClause.replace("&", "&amp;");
				}

				for (QueryParameterType openParameter : openClause.getParameter()) {
					QueryParameterWrapper parameter = (QueryParameterWrapper) openParameter;
					parameters.add(getExpression(parameter));
					whereClause = whereClause.replaceFirst("\\?#", getParamSB());
				}
				if (addFormTextTag)
					querySB.append(startName);
				querySB.append(whereClause);
				if (addFormTextTag)
					querySB.append("</span><br/>");
			}
		}
	}

	/**
	 * Adds the parameters.
	 *
	 * @param parameters
	 *            the parameters
	 */
	protected void addParameters(List<Serializable> parameters) {
		for (QueryParameterType parameterType : queryPreference.getParameter()) {
			if (parameterType.isValid()) {
				QueryParameterWrapper parameter = (QueryParameterWrapper) parameterType;
				append("    ", " ");
				appendAndOrWhere();
				/*
				 * If expression is a bean managed
				 */
				appendCode(parameter.getSuffix(), startSuffix);
				querySB.append('.');
				appendCode(parameter.getProperty(), startName);

				if (null != parameter.getIdValue()) {
					querySB.append('.');
					appendCode(parameter.getIdFieldName(pluginEnitityTree), startName);
				}
				String operator = parameter.getOperator();
				if (addFormTextTag)
					operator = operator.replace("<", "&lt;");
				querySB.append(' ').append(operator);

				if (!parameter.noExpression()) {
					querySB.append(' ');
					appendCode(getParamSB(), startSuffix);
					parameters.add((Serializable) (null != parameter.getIdValue() ? parameter.getIdValue() : parameter.getValue()));
				}
				if (!parameter.noExpression() && !parameter.oneExpression()) {
					querySB.append(' ');
					appendCode("and", startJPQL);
					querySB.append(' ');
					appendCode(getParamSB(), startSuffix);

					parameters.add(getExpression(parameter));
				}
				if (addFormTextTag)
					querySB.append("<br/>");
			}
		}
	}

	/**
	 * Adds the where clause.
	 */
	protected void addWhereClause() {
		append("    ", " ");
		appendAndOrWhere();
		querySB.append(queryManager.getWhereClause());
	}

	/**
	 * Adds the SQL clause.
	 */
	protected void addSQLClause() {
		appendCode(queryManager.getSqlClause(), startJPQL);
		int index = queryManager.getSqlClause().toLowerCase().indexOf("where");
		if (0 < index) {
			char before = queryManager.getSqlClause().charAt(index - 1);
			char after = queryManager.getSqlClause().charAt(index + 5);
			hasWhereClause = (' ' == before || '\n' == before) && (' ' == after || '\n' == after);
		}
	}

	/**
	 * Adds the JPQL jointure.
	 */
	protected void addJPQLJointure() {
		appendCode("from", startJPQL);
		for (JointureWrapper jointure : queryManager.getJointures()) {
			if (null == jointure.getJointureType()) { // skip main jointure (from Pojo)
				querySB.append(" ");
				appendCode(queryManager.getEntityMM().getBeanClass().getSimpleName(), startName);
			} else {
				append("    ", " ");
				appendCode(jointure.getJointureType().value(), startJPQL);
				querySB.append(" ");
				appendCode(jointure.getParentJointure().getSuffix(), startSuffix);
				querySB.append(".");
				appendCode(jointure.getFieldName(), startName);
			}
			if (!EngineTools.isEmpty(jointure.getSuffix())) {
				querySB.append(" ");
				appendCode(jointure.getSuffix(), startSuffix);
			}
		}
	}

	/**
	 * Adds the order by C lause.
	 */
	protected void addOrderByCLause() {
		if (null != queryPreference.getOrderByClause()) {
			append("", " ");
			appendCode("order by", startJPQL);
			querySB.append(' ');
			appendCode(queryPreference.getOrderByClause(), startName);
		}
	}

	/**
	 * Gets the param SB.
	 *
	 * @return the param SB
	 */
	protected String getParamSB() {
		StringBuffer paramSB;
		if (addFormTextTag) {
			paramSB = new StringBuffer("</span>").append(startSuffix).append("?");
			if (isJPQL)
				paramSB.append(String.valueOf(paramIndex++));
			paramSB.append("</span>").append(startName);
		} else {
			paramSB = new StringBuffer("?");
			if (isJPQL)
				paramSB.append(paramIndex++);
		}
		return paramSB.toString();
	}

	/**
	 * Append and or where.
	 */
	protected void appendAndOrWhere() {
		if (hasWhereClause) {
			appendCode("and", startJPQL);
		} else {
			hasWhereClause = true;
			appendCode("where", startJPQL);
		}
		querySB.append(' ');
	}

	/**
	 * Gets the expression.
	 *
	 * @param parameter
	 *            the parameter
	 * @return the expression
	 */
	protected Serializable getExpression(QueryParameterWrapper parameter) {
		return (Serializable) (null != parameter.getIdValue() ? parameter.getIdValue() : parameter.getValue());
	}

	/**
	 * Append.
	 *
	 * @param withFlag
	 *            the with flag
	 * @param withoutFlag
	 *            the without flag
	 */
	protected void append(String withFlag, String withoutFlag) {
		if (addFormTextTag)
			querySB.append(withFlag);
		else
			querySB.append(withoutFlag);

	}

	/**
	 * Append code.
	 *
	 * @param code
	 *            the code
	 * @param startTag
	 *            the start tag
	 */
	protected void appendCode(String code, String startTag) {
		if (addFormTextTag)
			querySB.append(startTag);
		querySB.append(code);
		if (addFormTextTag)
			querySB.append("</span>");
	}

}
