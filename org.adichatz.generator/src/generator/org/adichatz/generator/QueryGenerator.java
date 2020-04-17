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
package org.adichatz.generator;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.wrapper.JointureWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.wrapper.internal.IJointure;
import org.adichatz.generator.xjc.ColumnPreferenceType;
import org.adichatz.generator.xjc.ControllerPreferenceType;
import org.adichatz.generator.xjc.JointureAliasType;
import org.adichatz.generator.xjc.JointureType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.PreferenceType;
import org.adichatz.generator.xjc.QueryTree;
import org.adichatz.jpa.query.IJPAQueryBuilder;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.swt.SWT;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryGenerator.
 */
public class QueryGenerator extends AClassGenerator {

	private List<BufferCodeLevel> bufferCodeLevels = new ArrayList<BufferCodeLevel>();

	/** The query tree. */
	private QueryTreeWrapper queryTree;

	/** The lazy fetch members. */
	private List<String> lazyFetchMembers;

	private String generic;

	private String genericClassName;

	/**
	 * Instantiates a new query manager generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public QueryGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		this.queryTree = (QueryTreeWrapper) scenarioInput.getXmlElement();

		if (null == scenarioInput.getPluginEntity())
			scenarioInput.setPluginEntity(
					scenarioInput.getScenarioResources().getPluginResources().getPluginEntity(queryTree.getEntityURI()));

		genericClassName = getObjectName(CodeGenerationUtil.getUIBeanClass(scenarioInput));
		Class<?> queryManagerClass;
		if (EngineTools.isEmpty(queryTree.getParentQueryManagerURI()))
			queryManagerClass = JPAQueryManager.class;
		else
			queryManagerClass = ReflectionTools.getClassFromURI(queryTree.getParentQueryManagerURI());
		generic = "<" + genericClassName + ">";
		createClassFile(scenarioInput, " extends " + getObjectName(queryManagerClass) + generic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {
		/*
		 * Generate constructors
		 */
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates the Query Manager class for " + className + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(AdiPluginResources.class) + " uiPluginResources) {");

		StringBuffer superSB = new StringBuffer("super(" + getObjectName(AdiQuery.class) + ".QUERY_TYPE.");

		lazyFetchMembers = new ArrayList<String>();
		switch (queryTree.getQueryType()) {
		case JQL:
			superSB.append("JQL, ");
			addLazyFetch(queryTree, "");
			break;
		case SQL:
			superSB.append("SQL, ");
			if (null != queryTree.getJointureAliases()) {
				String prefix = queryTree.getSuffix().concat(".");
				String suffix = "";
				addAliasFetchMembers(queryTree, prefix, suffix);
			}
			break;
		}
		superSB.append(CodeGenerationUtil.betweenQuotes(queryTree.getSuffix()));
		superSB.append(", uiPluginResources");

		for (String lazyFetch : lazyFetchMembers) {
			boolean toBeAdded = true;
			String prefix = lazyFetch + ".";
			for (String presentlazyFetch : lazyFetchMembers) {
				if (presentlazyFetch.startsWith(prefix)) {
					toBeAdded = false;
					break;
				}
			}
			if (toBeAdded)
				superSB.append(", \"" + lazyFetch + "\"");
		}
		superSB.append(");");
		classBodyBuffer.append(superSB.toString());
		classBodyBuffer.append("");
		classBodyBuffer.append("entityMM = (" + getObjectName(AEntityMetaModel.class) + "<" + genericClassName
				+ ">) uiPluginResources.getPluginEntity(\"" + scenarioInput.getPluginEntity().getEntityURI()
				+ "\").getEntityMetaModel();");

		switch (queryTree.getQueryType()) {
		case JQL:
			addNewJointure(classBodyBuffer, queryTree, "null");
			for (JointureType jointure : queryTree.getJointure()) {
				addJointureAndCriteria(classBodyBuffer, "query",
						scenarioInput.getPluginEntityTree().getPluginEntityURIMap().get(queryTree.getEntityURI()),
						(JointureWrapper) jointure);
			}
			Collections.sort(bufferCodeLevels, new Comparator<BufferCodeLevel>() {
				@Override
				public int compare(BufferCodeLevel o1, BufferCodeLevel o2) {
					return o1.level - o2.level;
				}
			});
			for (BufferCodeLevel bufferCodeLevel : bufferCodeLevels)
				classBodyBuffer.append(bufferCodeLevel.getStringBuffer());
			if (!EngineTools.isEmpty(queryTree.getWhereClause()))
				classBodyBuffer.append("whereClause = " + CodeGenerationUtil.betweenQuotes(queryTree.getWhereClause()) + ";");
			break;
		case SQL:
			classBodyBuffer.append("sqlClause = " + CodeGenerationUtil.betweenQuotes(queryTree.getSqlClause()) + ";");
			classBodyBuffer.append("adiQuery.addEntity(" + CodeGenerationUtil.betweenQuotes(queryTree.getSuffix())
					+ ", entityMM.getBeanClass());");
			if (null != queryTree.getJointureAliases())
				for (JointureAliasType jointureAlias : queryTree.getJointureAliases().getJointureAlias()) {
					classBodyBuffer.append("adiQuery.addJointure(" + CodeGenerationUtil.betweenQuotes(jointureAlias.getAlias())
							+ ", " + CodeGenerationUtil.betweenQuotes(jointureAlias.getPath()) + ");");
				}
			break;
		}
		if (null != queryTree.getQueryBuilder()) {
			classBodyBuffer.append(getObjectName(Class.class) + generic + " queryBuilderClass = (" + getObjectName(Class.class)
					+ generic + ") " + getObjectName(ReflectionTools.class) + ".getClassFromURI("
					+ CodeGenerationUtil.betweenQuotes(queryTree.getQueryBuilder().getQueryBuilderURI()) + ");");
			String params;
			if (null != queryTree.getQueryBuilder().getParams()) {
				params = "params";
				classBodyBuffer.append(
						getObjectName(Map.class) + "<String, String> params = new " + getObjectName(HashMap.class) + "<>();");
				for (ParamType param : queryTree.getQueryBuilder().getParams().getParam())
					classBodyBuffer.append("params.put(" + keyWordGenerator.evalExpr2Str(classBodyBuffer, param.getId(), false)
							+ "," + keyWordGenerator.evalExpr2Str(classBodyBuffer, param.getValue(), false) + ");");
			} else
				params = "null";
			classBodyBuffer.append("queryBuilder = (" + getObjectName(IJPAQueryBuilder.class) + generic + ") "
					+ getObjectName(ReflectionTools.class) + ".instantiateClass(queryBuilderClass, new "
					+ getObjectName(Class.class) + "[] {" + getObjectName(JPAQueryManager.class) + ".class, "
					+ getObjectName(Map.class) + ".class},new " + getObjectName(Object.class) + "[] { this, " + params + " });");
		}

		if (!EngineTools.isEmpty(queryTree.getValid())) {
			classBodyBuffer.append("valid = " + keyWordGenerator.evalExpression(queryTree.getValid()) + ";");
		}

		if (null != queryTree.getQueryPreference()) {
			String queryPreferenceCN = getObjectName("org.adichatz.jpa.wrapper.QueryPreferenceWrapper");
			classBodyBuffer.appendPlus("{");
			classBodyBuffer.append(queryPreferenceCN + "<" + genericClassName + "> queryPreference = new " + queryPreferenceCN + "<"
					+ genericClassName + ">();");
			CodeGenerationUtil.addQueryPreference(classBodyBuffer, queryTree.getQueryPreference(), "queryPreference");
			classBodyBuffer.append("queryPreferenceManager.setQueryPreference(queryPreference);");
			classBodyBuffer.appendMinus("}");
		}
		if (null != queryTree.getCustomizedPreferences()) {
			classBodyBuffer.append("");
			for (PreferenceType preference : queryTree.getCustomizedPreferences().getPreference()) {
				if (EngineTools.isEmpty(preference.getId())) {
					logError(getFromGeneratorBundle("generation.error.field.mandatory", "Id", PreferenceType.class.getName()));
					continue;
				}
				classBodyBuffer.appendPlus("{");
				String preferenceTreeCN = getObjectName("org.adichatz.jpa.xjc.PreferenceTree");
				classBodyBuffer.append(preferenceTreeCN + " preferenceTree = new " + preferenceTreeCN + "();");
				if (null != preference.getQueryPreference()) {
					String queryPreferenceCN = getObjectName("org.adichatz.jpa.wrapper.QueryPreferenceWrapper");
					classBodyBuffer.append(queryPreferenceCN + "<" + genericClassName + "> customizedQueryPreference = new "
							+ queryPreferenceCN + "<" + genericClassName + ">();");
					CodeGenerationUtil.addQueryPreference(classBodyBuffer, preference.getQueryPreference(),
							"customizedQueryPreference");
					classBodyBuffer.append("preferenceTree.setQueryPreference(customizedQueryPreference);");
				}
				if (null != preference.getControllerPreference()) {
					ControllerPreferenceType controllerPreference = preference.getControllerPreference();
					String controllerPreferenceCN = getObjectName("org.adichatz.jpa.wrapper.ControllerPreferenceWrapper");
					classBodyBuffer.append(controllerPreferenceCN + "<" + genericClassName
							+ "> customizedControllerPreference = new " + controllerPreferenceCN + "<" + genericClassName + ">();");
					CodeGenerationUtil.addStringProperty(classBodyBuffer, "customizedControllerPreference.setId",
							controllerPreference.getId());
					CodeGenerationUtil.addStringProperty(classBodyBuffer, "customizedControllerPreference.setColumnOrder",
							controllerPreference.getColumnOrder());
					CodeGenerationUtil.addStringProperty(classBodyBuffer, "customizedControllerPreference.setStatusBarKey",
							controllerPreference.getStatusBarKey());
					CodeGenerationUtil.addStringProperty(classBodyBuffer, "customizedControllerPreference.setTableRendererKey",
							controllerPreference.getTableRendererKey());
					if (null != controllerPreference.getColumnPreferences()) {
						String columnPreferencesCN = getObjectName("org.adichatz.jpa.xjc.ColumnPreferencesType");
						String columnPreferenceCN = getObjectName("org.adichatz.jpa.xjc.ColumnPreferenceType");
						classBodyBuffer.append(columnPreferencesCN + " columnPreferences = new " + columnPreferencesCN + "();");
						classBodyBuffer.append("customizedControllerPreference.setColumnPreferences(columnPreferences);");
						classBodyBuffer.append(columnPreferenceCN + " columnPreference;");
						for (ColumnPreferenceType columnPreference : controllerPreference.getColumnPreferences()
								.getColumnPreference()) {
							classBodyBuffer.append("columnPreference = new " + columnPreferenceCN + "();");
							if (!columnPreference.isVisible())
								classBodyBuffer.append("columnPreference.setVisible(false);");
							CodeGenerationUtil.addStringProperty(classBodyBuffer, "columnPreference.setId",
									columnPreference.getId());
							if (SWT.DEFAULT != columnPreference.getWidth())
								classBodyBuffer.append("columnPreference.setWidth(" + columnPreference.getWidth() + ");");
							classBodyBuffer.append("columnPreferences.getColumnPreference().add(columnPreference);");
						}
					}
					if (null != controllerPreference.getFilters()) {
						String filtersCN = getObjectName("org.adichatz.jpa.xjc.FiltersType");
						String filterCN = getObjectName("org.adichatz.jpa.xjc.FilterType");
						classBodyBuffer.append("customizedControllerPreference.setFilters(new " + filtersCN + "());");
						classBodyBuffer.append(filterCN + " filter;");
						for (org.adichatz.generator.xjc.FilterType filter : controllerPreference.getFilters().getFilter()) {
							classBodyBuffer.append("filter = new " + filterCN + "();");
							CodeGenerationUtil.addStringProperty(classBodyBuffer, "filter.setColumn", filter.getColumn());
							CodeGenerationUtil.addStringProperty(classBodyBuffer, "filter.setSearchString",
									filter.getSearchString());
							CodeGenerationUtil.addStringProperty(classBodyBuffer, "filter.setText", filter.getText());
							classBodyBuffer.append("filter.setCaseInsensitive(" + filter.isCaseInsensitive() + ");");
							classBodyBuffer.append("filter.setEnabled(" + filter.isEnabled() + ");");
							classBodyBuffer.append("filter.setExactString(" + filter.isExactString() + ");");
							classBodyBuffer.append("customizedControllerPreference.getFilters().getFilter().add(filter);");
						}
					}
					classBodyBuffer.append("preferenceTree.setControllerPreference(customizedControllerPreference);");
				}
				classBodyBuffer.append("customizedPreferenceTreeMap.put("
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, preference.getId(), false) + ",preferenceTree);");
				classBodyBuffer.appendMinus("}");
			}
		}
		classBodyBuffer.appendMinus("}");

	}

	protected void addAliasFetchMembers(QueryTree sqlQuery, String prefix, String suffix) {
		for (JointureAliasType jointureAlias : sqlQuery.getJointureAliases().getJointureAlias()) {
			if (jointureAlias.getPath().startsWith(prefix)) {
				String newSuffix = suffix + jointureAlias.getPath().substring(prefix.length());
				lazyFetchMembers.add(newSuffix);
				addAliasFetchMembers(sqlQuery, jointureAlias.getAlias() + ".", suffix + ".");
			}
		}
	}

	/**
	 * Adds the lazy fetch.
	 * 
	 * @param queryPart
	 *            the query part
	 * @param prefix
	 *            the prefix
	 */
	private void addLazyFetch(IJointure joinPart, String prefix) {
		for (JointureType join : joinPart.getJointure()) {
			String childPrefix = prefix + join.getFieldName();
			lazyFetchMembers.add(childPrefix);
			addLazyFetch((JointureWrapper) join, childPrefix + ".");
		}
	}

	/**
	 * Adds the jointure and criteria.
	 * 
	 * @param parentSuffix
	 *            the parent suffix
	 * @param parentPluginEntity
	 *            the parent plugin entity
	 * @param join
	 *            the join
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void addJointureAndCriteria(BufferCode buffer, String parentJointureName, PluginEntity<?> parentPluginEntity,
			JointureWrapper join) throws IOException {
		PluginEntityWrapper pew = scenarioInput.getPluginEntityWrapper(parentPluginEntity.getEntityURI());
		Class<?> entityClass = FieldTools.getGetMethod(pew.getBeanClass(), join.getFieldName(), true).getReturnType();
		PluginEntity<?> pluginEntity;
		if (null == join.getEntityURI()) {
			pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntity(entityClass);
			join.setEntityURI(pluginEntity.getEntityURI());
		} else
			pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntityURIMap().get(join.getEntityURI());
		if (null == pluginEntity)
			logWarning(getFromGeneratorBundle("noEntityFoundForQuery", parentPluginEntity.getEntityURI(), join.getFieldName(),
					entityClass.getName()));
		else {
			String jointureName = addNewJointure(buffer, join, parentJointureName);
			for (JointureType jointure : join.getJointure())
				addJointureAndCriteria(
						new BufferCodeLevel(classBodyBuffer.equals(buffer) ? 1 : ((BufferCodeLevel) buffer).level + 1),
						jointureName, pluginEntity, (JointureWrapper) jointure);
		}
	}

	/**
	 * Adds the new jointure.
	 * 
	 * @param parentSuffix
	 *            the parent suffix
	 * @param entityId
	 *            the entity id
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String addNewJointure(BufferCode buffer, IJointure jointure, String parentJointureName) throws IOException {
		String jointureCN = getObjectName("org.adichatz.jpa.wrapper.JointureWrapper");
		buffer.append(jointureCN + " " + jointure.getJointureName() + " = new " + jointureCN + "("
				+ CodeGenerationUtil.betweenQuotes(jointure.getSuffix()) + ", " + parentJointureName + ", "
				+ CodeGenerationUtil.betweenQuotes(jointure.getEntityURI()) + ", this);");
		if (jointure instanceof JointureWrapper) {
			JointureWrapper jointureWrapper = (JointureWrapper) jointure;
			buffer.append(jointure.getJointureName() + ".setFieldName("
					+ CodeGenerationUtil.betweenQuotes(jointureWrapper.getFieldName()) + ");");
			buffer.append(jointure.getJointureName() + ".setJointureType(" + getObjectName("org.adichatz.jpa.xjc.JointureTypeEnum")
					+ "." + jointureWrapper.getJointureType().name() + ");");
		}
		return jointure.getJointureName();
	}

	public class BufferCodeLevel extends BufferCode {

		private int level;

		public BufferCodeLevel(int level) {
			super(QueryGenerator.this, 2, null);
			this.level = level;
			bufferCodeLevels.add(this);
		}

	}
}
