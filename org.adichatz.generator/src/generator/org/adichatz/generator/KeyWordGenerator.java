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
import static org.adichatz.generator.Statement.newStatement;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.TreeController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.data.AEntity;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.widgets.GMap;
import org.adichatz.engine.widgets.imageviewer.ImageViewer;
import org.adichatz.generator.common.AEvalVariable;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.PropertyField;
import org.adichatz.generator.tools.AdditionalBufferCode;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tree.AXjcTreeManager;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.MenuManagerWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.internal.IArgCollectionWrapper;
import org.adichatz.generator.wrapper.internal.IColumnWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IEntityContainerWrapper;
import org.adichatz.generator.wrapper.internal.IItemCompositeWrapper;
import org.adichatz.generator.wrapper.internal.IRootWrapper;
import org.adichatz.generator.wrapper.internal.ITableColumnWrapper;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.EditableFormTextType;
import org.adichatz.generator.xjc.ExtraTextType;
import org.adichatz.generator.xjc.FileTextType;
import org.adichatz.generator.xjc.ImageTypeEnum;
import org.adichatz.generator.xjc.LayoutType;
import org.adichatz.generator.xjc.MapDataTypeEnum;
import org.adichatz.generator.xjc.MapTypeIdEnum;
import org.adichatz.generator.xjc.MenuManagerType;
import org.adichatz.generator.xjc.MultiChoiceTypeEnum;
import org.adichatz.generator.xjc.PGroupMenuType;
import org.adichatz.generator.xjc.PGroupToolItemType;
import org.adichatz.generator.xjc.RefTextType;
import org.adichatz.generator.xjc.RichTextType;
import org.adichatz.generator.xjc.SetType;
import org.adichatz.generator.xjc.ShelfRendererEnum;
import org.adichatz.generator.xjc.TableColumnType;
import org.adichatz.generator.xjc.ToggleRendererEnum;
import org.adichatz.generator.xjc.ToolItemRendererEnum;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.pgroup.MinMaxToggleRenderer;
import org.eclipse.nebula.widgets.pgroup.TreeNodeToggleRenderer;
import org.eclipse.nebula.widgets.pgroup.TwisteToggleRenderer;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyWordGenerator.
 *
 * @author Yves Drumbonnet
 */
public class KeyWordGenerator extends AGenerator {
	/** The type map. */
	protected Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();

	/** The attribute map. */
	protected Map<String, PropertyField> attributeMap = new HashMap<String, PropertyField>();

	/** The param functions. */
	protected Map<String, AEvalVariable> functionMap = new HashMap<String, AEvalVariable>();

	/**
	 * Instantiates a new key word generator.
	 */
	public KeyWordGenerator() {
		initTypeMap();
		initParamFunctions();
		initAttributeMap();
	}

	/**
	 * Inits the type map.
	 */
	protected void initTypeMap() {
		typeMap.put(ParamMap.TABULAR_CONTROLLER, ATabularController.class);
		typeMap.put(ParamMap.TREE_CONTROLLER, TreeController.class);
		typeMap.put(ParamMap.CONTENT_PROVIDER, ATabularContentProvider.class);
		typeMap.put(ParamMap.ENTITY, AEntity.class);
		typeMap.put(IScenarioConstants.PROJECT, IProject.class);
	}

	/**
	 * Inits the param functions.
	 */
	protected void initParamFunctions() {
		/*
		 * Authorization
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				StringBuffer authorizationBuffer = new StringBuffer();
				int nextCommaPos = paramVariable.indexOf(',');
				if (-1 == nextCommaPos) {
					authorizationBuffer.append("genCode");
				} else {
					String entityURI = paramVariable.substring(0, nextCommaPos);
					if (-1 < entityURI.indexOf('#'))
						authorizationBuffer.append(evalExpr2Str(statement.getBufferCode(), entityURI, typed));
					else
						authorizationBuffer.append("getPluginResources().getPluginEntity(\"" + entityURI
								+ "\").getEntityMetaModel().getPluginEntity()");
				}
				authorizationBuffer.append(".checkPrivilege(");
				String privilege = paramVariable.substring(nextCommaPos + 1).trim();
				if (-1 != privilege.indexOf("IEntityConstants."))
					getObjectName(statement.getBufferCode(), IEntityConstants.class);
				authorizationBuffer.append(evalExpression(newStatement(statement, privilege), true));
				return authorizationBuffer.append(")").toString();
			}
		}, "A", "AUTHORIZATION");

		/*
		 * BEAN
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return evalBean(statement.getBufferCode(), paramVariable, typed, "getBean");
			}

		}, "B", "BEAN");

		/*
		 * BEAN_INTERCEPTOR
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return evalBean(statement.getBufferCode(), paramVariable, typed, "getBeanInterceptor");
			}

		}, "BI", "BEAN_INTERCEPTOR");

		/*
		 * PARAM
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				int nextCommaPos = variable.indexOf(',');
				Class<?> paramType;
				String paramKey;
				if (-1 == nextCommaPos) {
					if (typeMap.containsKey(variable))
						paramType = typeMap.get(variable);
					else
						paramType = String.class;
					paramKey = variable;
				} else {
					paramType = GeneratorUtil.getClazzOrPrimitive(variable.substring(nextCommaPos + 1).trim());
					paramKey = variable.substring(0, nextCommaPos).trim();
					typed = true; // types because paramType is specified
				}
				if (typed)
					return "((" + getObjectName(statement.getBufferCode(), paramType) + ") context.getParam(\"" + paramKey + "\"))";
				else
					return "context.getParam(\"" + paramKey + "\")";
			}
		}, "P", "PARAM");

		/*
		 * QUERY_MANAGER
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				String[] variables = variable.split(",");
				String treeId;
				String subPackage;
				StringBuffer pluginResourcesSB = new StringBuffer();
				String classNamePR = statement.getBufferCode().getGenerator().getObjectName(AdiPluginResources.class);
				pluginResourcesSB.append(classNamePR).append(" queryManagerPR =  ");
				if (2 == variable.length()) {
					pluginResourcesSB.append("coreController.getPluginResources();");
					treeId = variables[0];
					subPackage = variables[1];
				} else {
					pluginResourcesSB.append(getPluginResources(statement.getBufferCode(), variables[0]));
					treeId = variables[1];
					subPackage = variables[2];
				}
				pluginResourcesSB.append(".getNewInstance()");
				pluginResourcesSB.append(CodeGenerationUtil.betweenQuotes(treeId));
				pluginResourcesSB.append(", ");
				pluginResourcesSB.append(CodeGenerationUtil.betweenQuotes(subPackage));
				pluginResourcesSB.append(");");
				return pluginResourcesSB.toString();
			}
		}, "QM", "QUERY_MANAGER");

		/*
		 * PLUGIN_RESOURCE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return getPluginResources(statement.getBufferCode(), evalExpression(newStatement(statement, paramVariable), typed));
			}
		}, "PR", "PLUGIN_RESOURCE");

		/*
		 * CONTROLLER
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (EngineTools.isEmpty(paramVariable))
					return statement.getBufferCode().getGenerator().getThis();
				else
					return getController(statement.getBufferCode(), evalExpression(newStatement(statement, paramVariable), typed),
							typed);
			}
		}, "C", "CONTROLLER");

		/*
		 * REFERENCE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (EngineTools.isEmpty(paramVariable))
					return statement.getBufferCode().getGenerator().getThis();
				else
					return getRef(statement.getBufferCode(), evalExpression(newStatement(statement, paramVariable), typed), typed);
			}
		}, "REF", "REFERENCE");

		/*
		 * CONTROL_CONTROLLER
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (EngineTools.isEmpty(paramVariable))
					return "getControl()";
				else {
					String variable = evalExpression(newStatement(statement, paramVariable), typed);
					int commaPos = variable.indexOf(',');
					if (0 < commaPos)
						variable = variable.substring(0, commaPos);
					if (typed) {
						try {
							Class<?> controllerClass = statement.getBufferCode().getGenerator().getScenarioInput()
									.getRegistryClass(variable);
							return "((" + getObjectName(statement.getBufferCode(), controllerClass) + ") getFromRegister(\""
									+ variable + "\")).getControl()";
						} catch (NullPointerException e) {
							throw e;
						}
					} else
						return "getFromRegister(\"" + variable + "\").getControl()";
				}
			}
		}, "CC", "CONTROL_CONTROLLER");

		/*
		 * CONTROL
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (EngineTools.isEmpty(paramVariable))
					return "getControl()";
				else {
					String variable = evalExpression(newStatement(statement, paramVariable), typed);
					int commaPos = variable.indexOf(',');
					if (0 < commaPos)
						variable = variable.substring(0, commaPos);
					if (typed) {
						try {
							Class<?> controllerClass = statement.getBufferCode().getGenerator().getScenarioInput()
									.getRegistryClass(variable);
							return "((" + getObjectName(statement.getBufferCode(), controllerClass) + ") getFromRegister(\""
									+ variable + "\")).getControl()";
						} catch (NullPointerException e) {
							throw e;
						}
					} else
						return "getFromRegister(\"" + variable + "\").getControl()";
				}
			}
		}, "CONTROL");

		/*
		 * ELEMENT
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String beanClassName;
				if (statement.getBufferCode().getGenerator() instanceof ControlGenerator)
					beanClassName = getObjectName(statement.getBufferCode(),
							((ControlGenerator) statement.getBufferCode().getGenerator()).getControlFieldType());
				else
					beanClassName = getObjectName(statement.getBufferCode(),
							CodeGenerationUtil.getUIBeanClass(statement.getBufferCode().getGenerator().getScenarioInput()));
				return "((" + beanClassName + ") element)";
			}
		}, "ELEMENT");

		/*
		 * FIELDVALUE
		 */
		addEvalVariable(new AEvalVariable() {
			// Usage is: #FV() | #FV(fieldName)
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				PluginEntityWrapper pluginEntity = statement.getBufferCode().getPluginEntityWrapper();
				if (null == pluginEntity) {
					logError(getFromGeneratorBundle("generation.no.plugin.entity", statement.getExpression()));
					return null;
				}
				Class<?> uiBeanClass = CodeGenerationUtil
						.getUIBeanClass(statement.getBufferCode().getGenerator().getScenarioInput());
				IColumnWrapper columnWrapper = statement.getBufferCode().getGenerator().getScenarioInput().getColumnWrapper();
				if (null != columnWrapper && columnWrapper instanceof TableColumnType) {
					Method getMethod = FieldTools.getGetMethod(
							CodeGenerationUtil.getUIBeanClass(statement.getBufferCode().getGenerator().getScenarioInput()),
							columnWrapper.getProperty(), true);
					return "row." + getMethod.getName() + "()";
				} else {
					if (!EngineTools.isEmpty(paramVariable)) {
						String variable = evalExpression(newStatement(statement, paramVariable), typed);
						int pos = variable.indexOf(',');
						String defaultValue = null;
						if (-1 != pos) {
							defaultValue = variable.substring(pos + 1).trim();
							variable = variable.substring(0, pos);
						}
						Method getMethod = FieldTools.getGetMethod(uiBeanClass, variable, true);
						String result = "((" + getObjectName(statement.getBufferCode(), uiBeanClass) + ") getEntity().getBean())."
								+ getMethod.getName() + "()";
						if (null != defaultValue) {
							if (getMethod.getReturnType().equals(String.class) && !defaultValue.startsWith("\""))
								defaultValue = "\"".concat(defaultValue).concat("\"");
							result = statement.getBufferCode().getGenerator().getObjectName(EngineTools.class) + ".nvl(" + result
									+ ", " + defaultValue + ")";
						}
						return result;
					}
					if (null != columnWrapper && null != columnWrapper.getProperty()) {
						try {
							Method getMethod = FieldTools.getGetMethod(uiBeanClass, columnWrapper.getProperty(), true);
							if (statement.getBufferCode().getKeyBuffer().equals("convertModelToTarget")) {
								return "((" + getObjectName(statement.getBufferCode(), getMethod.getReturnType()) + ") value)";
							}
							return "((" + getObjectName(statement.getBufferCode(), uiBeanClass) + ") getEntity().getBean())."
									+ getMethod.getName() + "()";
						} catch (Exception e) {
							logError(e);
						}
					} else {
						if (null == statement.getBufferCode().getPluginEntity())
							logError(getFromGeneratorBundle("generation.FIELDVALUE.cannotBeBuild", paramVariable,
									(null == columnWrapper) ? "!?" : columnWrapper.getId()));
						else {
							Class<?> beanClass = uiBeanClass;
							Method getMethod = FieldTools.getGetMethod(beanClass, "country", true);
							return "((" + getObjectName(statement.getBufferCode(), beanClass) + ") value)." + getMethod.getName()
									+ "()";
						}
					}
					return "";
				}
			}
		}, "FV", "FIELDVALUE");

		/*
		 * ROLES
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return statement.getBufferCode().getGenerator().getObjectName(AdichatzApplication.class)
						+ ".getInstance().getSession().isAuthorized("
						+ CodeGenerationUtil.getValuesFromList(evalExpression(newStatement(statement, paramVariable), typed)) + ")";
			}
		}, "ROLES");

		/*
		 * ENTITY_META_MODEL
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (EngineTools.isEmpty(paramVariable))
					return "getEntity().getEntityMM()";
				else {
					String controller;
					if (statement.getBufferCode().getGenerator() instanceof ControlGenerator)
						controller = ((ControlGenerator) statement.getBufferCode().getGenerator()).getControllerName();
					else
						controller = "coreController";
					return controller + ".getPluginResources().getPluginEntity(\""
							+ evalExpression(newStatement(statement, evalExpression(newStatement(statement, paramVariable), typed)),
									typed)
							+ "\").getEntityMetaModel()";
				}
			}
		}, "EMM", "ENTITY_MM", "ENTITY_META_MODEL");

		/*
		 * ENTITY
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				if (statement.getBufferCode().getGenerator() instanceof ControlGenerator) {
					if (EngineTools.isEmpty(paramVariable)) {
						if (((ControlGenerator) statement.getBufferCode().getGenerator())
								.getElementWrapper() instanceof MenuManagerWrapper)
							return "((" + getObjectName(statement.getBufferCode(), ACollectionController.class)
									+ ") getMenuContainer()).getEntity()";
						else
							return "getEntity()";
					} else {
						return "getDataAccess().getDataCache().fetchEntity("
								+ evalExpression(newStatement(statement, variable), typed) + ")";
					}
				}
				if (EngineTools.isEmpty(paramVariable))
					return "getEntity()";
				else
					return "getDataAccess().getDataCache().fetchEntity(" + evalExpression(newStatement(statement, variable), typed)
							+ ")";
			}
		}, "E", "ENTITY");

		/*
		 * ROW
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (statement.getBufferCode().getGenerator().getParentGenerator() instanceof ATabularGenerator)
					return "row";
				else {
					ACodeGenerator parentGenerator = statement.getBufferCode().getGenerator().getParentGenerator();
					while (null != parentGenerator) {
						if (parentGenerator instanceof ATabularGenerator) {
							return "tabularController.getSelectedObject()";
						}
						parentGenerator = parentGenerator.getParentGenerator();
					}
					return "((" + getObjectName(statement.getBufferCode(), ASetController.class)
							+ ") getMenuContainer()).getSelectedObject()";
				}
			}
		}, "R", "ROW");

		/*
		 * IMG and IMGDESC
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String adiImageURI = evalExpression(newStatement(statement, paramVariable), typed);
				String[] bundleKeys = null;
				StringBuffer imageBuffer = new StringBuffer();
				if (-1 == adiImageURI.indexOf('#')) {
					try {
						bundleKeys = EngineTools.getInstanceKeys(adiImageURI);
					} catch (AdichatzErrorException e) {
						if (-1 == adiImageURI.indexOf('.')) {
							imageBuffer.append(getObjectName(statement.getBufferCode(), AdichatzApplication.class))
									.append(".getInstance().getFormToolkit().getRegisteredImage")
									.append(key.equals("IMGDESC") ? "Descriptor" : "").append("(")
									.append("\"" + adiImageURI + "\")");
							return imageBuffer.toString();
						}
						bundleKeys = new String[] { statement.getBufferCode().getScenarioResources().getPluginName(), ".",
								adiImageURI };
					}
				}

				if (null == bundleKeys[0])
					bundleKeys[0] = statement.getBufferCode().getScenarioResources().getPluginName();
				imageBuffer.append(getObjectName(statement.getBufferCode(), AdichatzApplication.class))
						.append(".getInstance().getImage").append(key.equals("IMGDESC") ? "Descriptor" : "").append("(")
						.append("\"" + bundleKeys[0] + "\"").append(", ");
				String imageKey = bundleKeys[2];
				if (!".".equals(bundleKeys[1]))
					imageKey = bundleKeys[1].concat("/").concat(imageKey);
				imageBuffer.append(evalExpr2Str(statement.getBufferCode(), imageKey, true)).append(")");
				return imageBuffer.toString();
			}
		}, "IMG", "IMGDESC");

		/*
		 * MESSAGE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				/**
				 * Message could have different two formats<br>
				 * . . - adi://pluginName/resourceBundleName, key [, paramm...]<br>
				 * . . - resourceBundleName, key [, paramm...]<br>
				 * In second case, pluginName is assumed by current plugin.
				 */
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				StringBuffer messageBuffer = new StringBuffer();
				int nextCommaPos = -1;
				int commaPos = -1;
				commaPos = variable.indexOf(',');
				nextCommaPos = variable.indexOf(',', commaPos + 1);
				String adiResourceURI = null;
				try {
					adiResourceURI = variable.substring(0, commaPos).trim();
				} catch (Exception e) {
					throw new RuntimeException("Invalid format for message'" + paramVariable
							+ "'? usage is MESSAGE(<message URI>, [variable1, variable2, ...])");
				}
				boolean addMessageBundle = false;
				String[] bundleKeys = null;
				messageBuffer.append(getObjectName(statement.getBufferCode(), AdichatzApplication.class) + ".getInstance()");
				if (-1 == adiResourceURI.indexOf('#')) {
					try {
						bundleKeys = EngineTools.getInstanceKeys(adiResourceURI);
					} catch (AdichatzErrorException e) {
						String resourceBundleName = statement.getBufferCode().getGenerator().getScenarioInput()
								.getBundleTranslation().get(adiResourceURI);
						if (null == resourceBundleName) {
							adiResourceURI = "adi://".concat(statement.getBufferCode().getScenarioResources().getPluginName())
									.concat("/./").concat(adiResourceURI);
							bundleKeys = EngineTools.getInstanceKeys(adiResourceURI);
						} else {
							if (-1 != resourceBundleName.indexOf('#')) {
								addMessageBundle = true;
								adiResourceURI = resourceBundleName;
							} else {
								adiResourceURI = evalExpr2Str(statement.getBufferCode(), resourceBundleName, true);
								bundleKeys = EngineTools.getInstanceKeys(adiResourceURI);
							}
						}
					}
				} else {
					addMessageBundle = true;
				}
				if (addMessageBundle)
					messageBuffer.append(".getMessageBundle(").append(evalExpr2Str(statement.getBufferCode(), adiResourceURI, true))
							.append(", ");
				else {
					messageBuffer.append(".getMessage(\"").append(bundleKeys[0]).append("\", \"");
					if (!".".equals(bundleKeys[1]))
						messageBuffer.append(bundleKeys[1]).append('/');
					messageBuffer.append(bundleKeys[2]).append("\", ");
				}
				nextCommaPos = variable.indexOf(',', commaPos + 1);
				if (-1 == nextCommaPos)
					messageBuffer.append(evalExpr2Str(statement.getBufferCode(), variable.substring(commaPos + 1).trim(), typed));
				else {
					messageBuffer.append(
							evalExpr2Str(statement.getBufferCode(), variable.substring(commaPos + 1, nextCommaPos).trim(), typed));
					while (true) {
						int start = nextCommaPos + 1;
						nextCommaPos = variable.indexOf(',', start);
						messageBuffer.append(", ");
						if (-1 == nextCommaPos) {
							nextCommaPos = variable.indexOf(')', start);
							messageBuffer.append(evalExpression(newStatement(statement, variable.substring(start).trim()), true));
							break;
						} else {
							messageBuffer.append(
									evalExpression(newStatement(statement, variable.substring(start, nextCommaPos).trim()), true));
						}
					}
				}
				return messageBuffer.append(")").toString();
			}
		}, "M", "MSG", "MESSAGE");

		/*
		 * TREE_MANAGER
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				String[] variables = variable.split(",");
				return statement.getBufferCode().getGenerator().getObjectName(AXjcTreeManager.class)
						+ ".newTreeManager(coreController.getPluginResources(), "
						+ evalExpr2Str(statement.getBufferCode(), variables[0].trim(), false) + ", "
						+ evalExpr2Str(statement.getBufferCode(), variables[1].trim(), false) + ")";
			}
		}, "TM", "TREE_MANAGER");

		/*
		 * FUNCTION
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return paramVariable;
			}
		}, "L", "LITERAL");

		/*
		 * COLOR
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return getColor(newStatement(statement, paramVariable));
			}
		}, "COLOR");

		/*
		 * CSSCOLOR
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return KeyWordGenerator.this.getCSSColor(statement, paramVariable, false);
			}
		}, "CSSCOLOR");

		/*
		 * FONT
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return getFont(newStatement(statement, paramVariable));
			}
		}, "FONT");

		/*
		 * CSSFONT
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return getCSSFont(statement, paramVariable);
			}
		}, "CSSFONT");

		/*
		 * FIND
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {

				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				StringTokenizer tokenizer = new StringTokenizer(variable, ",");
				int count = tokenizer.countTokens();
				if (count < 1) {
					logError(
							"Invalid format for #FIND(...) function. Usage is #FIND(<identifier>[, lazyFetchMember1, lazyFetchMember2...])");
					return null;
				}
				PluginEntity<?> pluginEntity = statement.getBufferCode().getGenerator().getScenarioInput().getPluginEntity();
				if (null == pluginEntity) {
					logError(
							"Cannot use function #FIND(<identifier>[, lazyFetchMember1, lazyFetchMember2...]) becaus plugin entity is undefined");
					return null;
				}
				try {
					BufferCode extraBuffer = statement.getBufferCode().getGenerator().addExtraBuffer("findSpecificEntity");
					extraBuffer.addIdent(3);
					extraBuffer.appendPlus("private " + statement.getBufferCode().getGenerator().getObjectName(IEntity.class)
							+ "<?> findSpecificEntity() {");
					extraBuffer.append("String entityURI = \"" + pluginEntity.getEntityURI() + "\";");
					extraBuffer.append(statement.getBufferCode().getGenerator().getObjectName(AdiPluginResources.class)
							+ " entityPR = " + statement.getBufferCode().getGenerator().getObjectName(AdichatzApplication.class)
							+ ".getPluginResources(" + statement.getBufferCode().getGenerator().getObjectName(EngineTools.class)
							+ ".getInstanceKeys(entityURI)[0]);");
					extraBuffer.append(statement.getBufferCode().getGenerator().getObjectName(JPADataAccess.class)
							+ " dataAccess = (" + statement.getBufferCode().getGenerator().getObjectName(JPADataAccess.class)
							+ ") entityPR.getDataAccess();");

					String beanId = evalExpression(newStatement(statement, tokenizer.nextToken().trim()), true);
					StringBuffer findBuffer = new StringBuffer();
					while (tokenizer.hasMoreElements()) {
						findBuffer.append(", ");
						findBuffer.append(evalExpr2Str(statement.getBufferCode(), tokenizer.nextToken().trim(), false));
					}
					extraBuffer.append("return dataAccess.findEntity(entityURI, " + beanId + findBuffer.toString() + ");");
					extraBuffer.appendMinus("}");
				} catch (IOException e) {
					logError(e);
				}
				return "findSpecificEntity()";
			}
		}, "FIND");

		/*
		 * FIND_URI
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				StringTokenizer tokenizer = new StringTokenizer(variable, ",");
				int count = tokenizer.countTokens();
				if (count < 2) {
					logError(
							"Invalid format for #FIND_URI(...) function. Usage is #FIND_URI(<Entity URI>,<identifier>[, lazyFetchMember1, lazyFetchMember2...])");
					return null;
				}

				try {
					BufferCode extraBuffer = statement.getBufferCode().getGenerator().addExtraBuffer("findSpecificEntity");
					extraBuffer.addIdent(3);
					extraBuffer.appendPlus("private " + statement.getBufferCode().getGenerator().getObjectName(IEntity.class)
							+ "<?> findSpecificEntity() {");
					extraBuffer.append("String entityURI = "
							+ evalExpr2Str(statement.getBufferCode(), tokenizer.nextToken().trim(), false) + ";");
					extraBuffer.append(statement.getBufferCode().getGenerator().getObjectName(AdiPluginResources.class)
							+ " entityPR = " + statement.getBufferCode().getGenerator().getObjectName(AdichatzApplication.class)
							+ ".getPluginResources(" + statement.getBufferCode().getGenerator().getObjectName(EngineTools.class)
							+ ".getInstanceKeys(entityURI)[0]);");
					extraBuffer.append(statement.getBufferCode().getGenerator().getObjectName(JPADataAccess.class)
							+ " dataAccess = (" + statement.getBufferCode().getGenerator().getObjectName(JPADataAccess.class)
							+ ") entityPR.getDataAccess();");

					String beanId = evalExpression(newStatement(statement, tokenizer.nextToken().trim()), true);
					StringBuffer findBuffer = new StringBuffer();
					while (tokenizer.hasMoreElements()) {
						findBuffer.append(", ");
						findBuffer.append(evalExpr2Str(statement.getBufferCode(), tokenizer.nextToken().trim(), false));
					}
					extraBuffer.append("return dataAccess.findEntity(entityURI, " + beanId + findBuffer.toString() + ");");
					extraBuffer.appendMinus("}");
				} catch (IOException e) {
					logError(e);
				}
				return "findSpecificEntity()";
			}
		}, "FIND_URI");

		/*
		 * FIND_QUERY
		 */
		addEvalVariable(new AEvalVariable() { // AVISATZ a revoir Ne fonctionne pas
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				StringTokenizer tokenizer = new StringTokenizer(variable, ",");
				int count = tokenizer.countTokens();
				if (count < 2) {
					logError(
							"Invalid format for #FIND_QUERY(...) function. Usage is #FIND_QUERY(<Entity URI>,<identifier>[, lazyFetchMember1, lazyFetchMember2...])");
					return null;
				}

				try {
					BufferCode extraBuffer = statement.getBufferCode().getGenerator().addExtraBuffer("findSpecificEntity");
					extraBuffer.addIdent(3);
					extraBuffer.appendPlus("private " + statement.getBufferCode().getGenerator().getObjectName(IEntity.class)
							+ "<?> findSpecificEntity() {");
					extraBuffer.append("String entityURI = "
							+ evalExpr2Str(statement.getBufferCode(), tokenizer.nextToken().trim(), false) + ";");
					extraBuffer.append(statement.getBufferCode().getGenerator().getObjectName(AdiPluginResources.class)
							+ " entityPR = " + statement.getBufferCode().getGenerator().getObjectName(AdichatzApplication.class)
							+ ".getPluginResources(" + statement.getBufferCode().getGenerator().getObjectName(EngineTools.class)
							+ ".getInstanceKeys(entityURI)[0]);");
					extraBuffer.append(statement.getBufferCode().getGenerator().getObjectName(JPADataAccess.class)
							+ " dataAccess = (" + statement.getBufferCode().getGenerator().getObjectName(JPADataAccess.class)
							+ ") entityPR.getDataAccess();");

					String beanId = evalExpression(newStatement(statement, tokenizer.nextToken().trim()), true);
					StringBuffer findBuffer = new StringBuffer();
					while (tokenizer.hasMoreElements()) {
						findBuffer.append(", ");
						findBuffer.append(evalExpr2Str(statement.getBufferCode(), tokenizer.nextToken().trim(), false));
					}
					extraBuffer.append("return dataAccess.findEntity(entityURI, " + beanId + findBuffer.toString() + ");");
					extraBuffer.appendMinus("}");
				} catch (IOException e) {
					logError(e);
				}
				return "findSpecificEntity()";
			}
		}, "FIND_QUERY");

		/*
		 * PREFERENCE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				StringTokenizer tokenizer = new StringTokenizer(variable, ",");
				int count = tokenizer.countTokens();
				if (count != 2 && count != 1) {
					invalidFormat();
					return null;
				}
				String parameter = tokenizer.nextToken();
				if (EngineTools.isEmpty(variable)) {
					invalidFormat();
					return null;
				}
				StringBuffer preferenceSB = new StringBuffer(
						statement.getBufferCode().getGenerator().getObjectName(EngineConstants.class));
				preferenceSB.append(".ADICHATZ_APPLICATION_PLUGIN.getPreferenceStore().get");
				if (2 == count) {
					String suffix = EngineTools.upperCaseFirstLetter(tokenizer.nextToken().trim());
					if (!"String".equals(suffix) && !"Boolean".equals(suffix) && !"Int".equals(suffix) && !"Long".equals(suffix)
							&& !"Float".equals(suffix) && !"Double".equals(suffix)) {
						invalidFormat();
						return null;
					}
					preferenceSB.append(suffix);
				} else
					preferenceSB.append("String");
				preferenceSB.append("(").append(CodeGenerationUtil.betweenQuotes(parameter)).append(")");
				return preferenceSB.toString();
			}

			private void invalidFormat() {
				logError(
						"Invalid format for #PREFERENCE(...) function. Usage is #PREFERENCE(parameter[, String | Boolean | Int | Long | Float | Double])");
			}
		}, "PREFERENCE", "PREF");

		/*
		 * OUTLINE_PAGE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return statement.getBufferCode().getGenerator().getObjectName(OutlinePart.class)
						+ ".getInstance().getCurrentPage()";
			}
		}, "OUTLINE_PAGE");

		/*
		 * PLUGINHOME
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				if (EngineTools.isEmpty(variable))
					return statement.getBufferCode().getScenarioResources().getPluginHome();
				return FileUtil.getPluginHome(variable);
			}
		}, "PLUGINHOME");

		/*
		 * PLUGINBIN
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				String variable = evalExpression(newStatement(statement, paramVariable), typed);
				String pluginBin;
				if (EngineTools.isEmpty(variable))
					pluginBin = statement.getBufferCode().getScenarioResources().getPluginHome();
				else
					pluginBin = FileUtil.getPluginHome(variable);
				pluginBin = pluginBin.concat(pluginBin.endsWith(".jar") ? "!" : "/bin");
				return pluginBin;
			}
		}, "PLUGINBIN");

		/*
		 * WORKSPACE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return null == GeneratorConstants.WORKSPACE_DIRECTORY ? Platform.getInstanceLocation().getURL().getPath()
						: GeneratorConstants.WORKSPACE_DIRECTORY;
			}
		}, "WORKSPACE");

		/*
		 * MODELPACKAGE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				if (null != statement.getBufferCode().getGenerator()) {
					return statement.getBufferCode().getGenerator().getScenarioInput().getModelSR().getGenerationScenario()
							.getModelPart().getModelPackageName();
				} else if (null != statement.getBufferCode().getScenarioResources().getGenerationScenario().getModelPart()
						&& null != statement.getBufferCode().getScenarioResources().getGenerationScenario())
					return statement.getBufferCode().getScenarioResources().getGenerationScenario().getModelPart()
							.getModelPackageName();
				logError(getFromGeneratorBundle("generation.bad.Expression", statement.getExpression()));
				return null;
			}
		}, "MODELPACKAGE");

		/*
		 * MODELPACKAGEDIR
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return functionMap.get("MODELPACKAGE").eval(statement, key, paramVariable, typed).replace('.', '/');
			}
		}, "MODELPACKAGEDIR");

		/*
		 * MODELPACKAGEDIR
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return statement.getBufferCode().getScenarioResources().getPluginPackage();
			}
		}, "PLUGINPACKAGE");

		/*
		 * PLUGINPACKAGEDIR
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return statement.getBufferCode().getScenarioResources().getPluginPackage().replace('.', '/');
			}
		}, "PLUGINPACKAGEDIR");

		/*
		 * PLUGINNAME
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return statement.getBufferCode().getScenarioResources().getPluginName();
			}
		}, "PLUGINNAME");

		/*
		 * EJBJARFILE
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return statement.getBufferCode().getScenarioResources().getProjectEJBJarFileName();
			}
		}, "EJBJARFILE");

		/*
		 * NULL
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				return "null";
			}
		}, "NULL");

		/*
		 * BOLD, ITALIC
		 */
		addEvalVariable(new AEvalVariable() {
			@Override
			public String eval(Statement statement, String key, String paramVariable, boolean typed) {
				ACodeGenerator generator = statement.getBufferCode().getGenerator();
				if (EngineTools.isEmpty(paramVariable)) {
					String fontFunction = key.equals("BOLD") ? "getBoldFont()" : "getItalicFont()";
					if (generator instanceof ControlGenerator) {
						ControlGenerator controlGenerator = (ControlGenerator) statement.getBufferCode().getGenerator();
						if (controlGenerator.getElementWrapper() instanceof SetType)
							return fontFunction;
						if (controlGenerator.getElementWrapper() instanceof ITableColumnWrapper) {
							return "tabularController." + fontFunction;
						}
					}
					return generator.getObjectName(EngineTools.class) + ".getModifiedFont(getControl().getFont(), "
							+ generator.getObjectName(SWT.class) + "." + key + ")";
				} else {
					String param = null;
					if (paramVariable.startsWith("JFaceResources."))
						param = generator.getObjectName(JFaceResources.class) + ".getFontRegistry().get(" + paramVariable + ")";
					else
						param = paramVariable;
					return generator.getObjectName(EngineTools.class) + ".getModifiedFont(" + param + ", "
							+ generator.getObjectName(SWT.class) + "." + key + ")";
				}
			}
		}, "BOLD", "ITALIC");

	}

	/**
	 * Eval bean.
	 *
	 * @param buffer
	 *            the buffer
	 * @param paramVariable
	 *            the param variable
	 * @param typed
	 *            the typed
	 * @param functionName
	 *            the function name
	 * @return the string
	 */
	protected String evalBean(BufferCode buffer, String paramVariable, boolean typed, String functionName) {
		String parentStr = "";
		if (buffer.getGenerator() instanceof ControlGenerator
				&& !(((ControlGenerator) buffer.getGenerator()).getControlWrapper() instanceof IEntityContainerWrapper))
			parentStr = "parentController.";
		else if (buffer instanceof AdditionalBufferCode)
			parentStr = ((AdditionalBufferCode) buffer).getCollectionName() + ".";
		if (EngineTools.isEmpty(paramVariable))
			if (null == buffer.getPluginEntity())
				return parentStr + "getEntity()." + functionName + "()";
			else if ("getEntityInjection".equals(buffer.getKeyBuffer())) { // current entityURI could have been just changed. So, BEAN focuses on parent element
				ACodeGenerator parentGenerator = ((ControlGenerator) buffer.getGenerator()).getParentGenerator()
						.getParentGenerator();
				Class<?> beanClass = CodeGenerationUtil.getUIBeanClass(parentGenerator.getScenarioInput());
				return "((" + getObjectName(buffer, beanClass) + ") parentController.getEntity()." + functionName + "())";
			} else
				return "((" + getObjectName(buffer, CodeGenerationUtil.getUIBeanClass(buffer.getGenerator().getScenarioInput()))
						+ ") " + parentStr + "getEntity()." + functionName + "())";
		String variable = evalExpression(buffer, paramVariable, typed, false);
		IElementWrapper element = getElementWrapper(buffer.getGenerator().getScenarioInput().getRootWrapper(), variable);
		if (null == element)
			throw new RuntimeException("No element having identifier ='" + variable + "' found!");
		if (null == element.getPluginEntity())
			throw new RuntimeException("No plugin entity was fixed for element '" + element.getId() + "'!");
		return "((" + getObjectName(buffer, getUIBeanClass(buffer, element)) + ") (("
				+ getObjectName(buffer, AWidgetController.class) + ") getFromRegister(\"" + variable + "\")).getEntity()."
				+ functionName + "())";
	}

	protected Class<?> getUIBeanClass(BufferCode buffer, IElementWrapper element) {
		ScenarioInput scenarioInput = new ScenarioInput(buffer.getScenarioResources(), element.getPluginEntity());
		return CodeGenerationUtil.getUIBeanClass(scenarioInput);
		// Class<?> beanClass = element.getPluginEntity().getUIBeanClass();
		// if (null != beanClass)
		// return beanClass;
		//
		// return buffer.getGenerator().getScenarioInput().getPluginEntityWrapper(element.getPluginEntity().getEntityURI())
		// .getUIBeanClass();
	}

	/**
	 * Inits the attribute map.
	 */
	protected void initAttributeMap() {
		attributeMap.put("alignment", new PropertyField("Style", "", "setAlignment")); // column
		attributeMap.put("addMarker", new PropertyField("Boolean", "getControl()")); // GMap
		attributeMap.put("background", new PropertyField("Color", ""));

		attributeMap.put("backgroundImage", new PropertyField("Image", "getControl()"));
		attributeMap.put("bounds", new PropertyField("Bounds", "getControl()"));
		attributeMap.put("capture", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("changeCoordinates", new PropertyField("Boolean", "getControl()")); // GMap
		attributeMap.put("clientBackground", new PropertyField("Color", "getComposite()", "setBackground")); // ClientCanvasType
		attributeMap.put("clientBackgroundImage", new PropertyField("Image", "getComposite()", "setBackgroundImage")); // ClientCanvasType
		attributeMap.put("clientBounds", new PropertyField("Bounds", "getComposite()", "setBounds")); // ClientCanvasType
		attributeMap.put("clientCapture", new PropertyField("Boolean", "getComposite()", "setCapture")); // ClientCanvasType
		attributeMap.put("clientFont", new PropertyField("Font", "getComposite()", "setFont")); // ClientCanvasType
		attributeMap.put("clientForeground", new PropertyField("Color", "getComposite()", "setForeground")); // ClientCanvasType
		attributeMap.put("clientLayoutData", new PropertyField("String", "getComposite()", "setLayoutData")); // ClientCanvasType
		attributeMap.put("clientLocation", new PropertyField("Location", "getComposite()", "setLocation")); // ClientCanvasType
		attributeMap.put("clientRedraw", new PropertyField("Boolean", "getComposite()", "setRedraw")); // ClientCanvasType
		attributeMap.put("clientSize", new PropertyField("Size", "getComposite()", "setSize")); // ClientCanvasType
		attributeMap.put("clientStyle", new PropertyField("Style", "", "setClientStyle", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL)); // ClientCanvasType
		attributeMap.put("columnOrder", new PropertyField("String", "", "getControllerPreferenceManager().setColumnOrder",
				LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE)); // TabularWrapper
		attributeMap.put("containerBackground", new PropertyField("Color", "getComposite()", "setBackground")); // IEnclosedControlWrapper
		attributeMap.put("containerBackgroundImage", new PropertyField("Image", "getComposite()", "setBackgroundImage")); // IEnclosedControlWrapper
		attributeMap.put("containerBounds", new PropertyField("Bounds", "getComposite()", "setBounds")); // IEnclosedControlWrapper
		attributeMap.put("containerCapture", new PropertyField("Boolean", "getComposite()", "setCapture")); // IEnclosedControlWrapper
		attributeMap.put("containerFont", new PropertyField("Font", "getComposite()", "setFont")); // IEnclosedControlWrapper
		attributeMap.put("containerForeground", new PropertyField("Color", "getComposite()", "setForeground")); // IEnclosedControlWrapper
		attributeMap.put("containerLayoutData", new PropertyField("String", "getComposite()", "setLayoutData")); // IEnclosedControlWrapper
		attributeMap.put("containerLocation", new PropertyField("Location", "getComposite()", "setLocation")); // IEnclosedControlWrapper
		attributeMap.put("containerRedraw", new PropertyField("Boolean", "getComposite()", "setRedraw")); // IEnclosedControlWrapper
		attributeMap.put("containerSize", new PropertyField("Size", "getComposite()", "setSize")); // IEnclosedControlWrapper
		attributeMap.put("containerStyle",
				new PropertyField("Style", "", "setContainerStyle", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL)); // IContainerWrapper

		attributeMap.put("coordPattern", new PropertyField("String", "getControl()", "setPattern")); // gmap
		attributeMap.put("defaultSelection",
				new PropertyField("Integer", "getControl()", "setDefaultSelection", LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE)); // RadioGroup
		attributeMap.put("delayMillisLoading", new PropertyField("Integer", "getControl()")); // GMap
		attributeMap.put("dialogFormClassName", new PropertyField("String", "getControl()"));
		attributeMap.put("dirtyManagement", new PropertyField("Boolean", "")); // DirtyContainerType
		attributeMap.put("editable", new PropertyField("Boolean", "getControl()") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				if (element instanceof ExtraTextType)
					return "getStyledText().";
				else
					return controlGetter + ".";
			}
		});
		attributeMap.put("expanded", new PropertyField("Boolean", "getControl()")); // Section
		attributeMap.put("filterExtension", new PropertyField("String", "getControl()")); // FileText
		attributeMap.put("filterPath", new PropertyField("String", "getControl()")); // FileText
		attributeMap.put("fitCanvas",
				new PropertyField("Boolean", "getControl()", "setFitCanvas", LIFECYCLE_STAGE.AFTER_SYNCHRONIZE)); // ImageViewer
		attributeMap.put("font", new PropertyField("Font", ""));
		attributeMap.put("forceBinding", new PropertyField("Boolean", "", "setForceBinding", LIFECYCLE_STAGE.BEFORE_INITIALIZE));
		attributeMap.put("foreground", new PropertyField("Color", ""));
		attributeMap.put("formText", new PropertyField("String", "getControl().getForm()", "setText"));
		attributeMap.put("formattedText", new PropertyField("String", "")); // ExtraText
		attributeMap.put("headerVisible", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("helpLabel", new PropertyField("String", ""));
		attributeMap.put("helpSpecify", new PropertyField("String", ""));
		attributeMap.put("helpMessage", new PropertyField("String", ""));
		attributeMap.put("image", new PropertyField("Image", "getControl()", "setImage") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				if (element instanceof IItemCompositeWrapper)
					return "getItem().";
				else if (element instanceof ColumnFieldType)
					return "getControl().getColumn().";
				else if (element instanceof PGroupMenuType)
					return "getPGroupToolItem().";
				else if (element instanceof PGroupToolItemType)
					return "getControl().";
				else
					return controlGetter + ".";
			}
		});
		attributeMap.put("imageDescriptor", new PropertyField("ImageDescriptor", "getControl()", "setImageDescriptor") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				return (element instanceof EditableFormTextType || element instanceof MenuManagerType) ? "" : controlGetter + ".";
			}

			@Override
			public LIFECYCLE_STAGE getLifeCycleStage(IElementWrapper element) {
				if (element instanceof MenuManagerType)
					return LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL;
				return super.getLifeCycleStage(element);
			}
		});
		attributeMap.put("imageType", new PropertyField("ImageType", "", "setImageType", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL));
		attributeMap.put("integer", new PropertyField("Boolean", "getNumericText()"));
		attributeMap.put("layout", new PropertyField("Layout", "getComposite()") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				if (element instanceof IArgCollectionWrapper)
					return "";
				return "getComposite().";
			}
		});
		attributeMap.put("layoutData", new PropertyField("String", "getControl()"));
		attributeMap.put("lazyFetches", new PropertyField("LazyFetches", null, "addLazyFetchMembers") {
			@Override
			public LIFECYCLE_STAGE getLifeCycleStage(IElementWrapper element) {
				if (element instanceof SetType)
					return LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL;
				return LIFECYCLE_STAGE.AFTER_INSTANTIATE_CONTROLLER;
			}
		});
		attributeMap.put("linesVisible", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("locale", new PropertyField("Locale", "getControl()"));
		attributeMap.put("location", new PropertyField("Location", "getControl()"));
		attributeMap.put("locked", new PropertyField("Boolean", ""));
		attributeMap.put("mapControl", new PropertyField("Boolean", "getControl()")); // gmap
		attributeMap.put("mapDataType", new PropertyField("MapDataType", "getControl()")); // gmap
		attributeMap.put("mapTypeId", new PropertyField("MapTypeId", "getControl()")); // gmap
		attributeMap.put("masterDetail", new PropertyField("Boolean", "")); // SashForm
		attributeMap.put("maximizedChild", new PropertyField("Integer", "", null, LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE)); // sashForm
		attributeMap.put("maximizedChild", new PropertyField("Integer", "", null, LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE)); // sashForm
		attributeMap.put("maxNumberOfStars", new PropertyField("Integer", "getControl()")); // starRating
		attributeMap.put("maxResults", new PropertyField("Integer", "", "setMaxResults", LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE));
		attributeMap.put("moveable", new PropertyField("Boolean", "getControl()")); // TableColumn
		attributeMap.put("multiChoiceType", new PropertyField("MultiChoiceType", ""));
		attributeMap.put("numberOfColumns", new PropertyField("Integer", "getControl()")); // multiChoice
		attributeMap.put("numericPattern",
				new PropertyField("String", "", "setNumericPattern", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL)); // starRating
		attributeMap.put("orientation", new PropertyField("Orientation", "getControl()"));
		attributeMap.put("pack", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("poolQueryResult",
				new PropertyField("Boolean", "", "setPoolQueryResult", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL)); // RefControl
		attributeMap.put("popupToolbar", new PropertyField("Boolean", "getControl()")); // multiChoice
		attributeMap.put("popupTitle", new PropertyField("Boolean", "getControl()")); // multiChoice
		attributeMap.put("popupMaxWidth", new PropertyField("Integer", "getControl()")); // multiChoice
		attributeMap.put("popupMaxHeight", new PropertyField("Integer", "getControl()")); // multiChoice
		attributeMap.put("preferenceURI", new PropertyField("String", "")); // RefControl
		attributeMap.put("rectangle", new PropertyField("Rectangle", "getControl()"));
		attributeMap.put("rank", new PropertyField("Integer", "", "setRank", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL));
		attributeMap.put("redraw", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("refreshAtStart",
				new PropertyField("Boolean", "", "refreshAtStart", LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE)); // SetType
		attributeMap.put("resizeable", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("rootElement", new PropertyField("String", ""));
		attributeMap.put("sashFormToolBar", new PropertyField("Boolean", "", "addSashFormToolBar"));
		attributeMap.put("separator", new PropertyField("String", "getControl()")); // multiChoice
		attributeMap.put("showRoot", new PropertyField("Boolean", "")); // Show root element on tree
		attributeMap.put("size", new PropertyField("Size", ""));
		attributeMap.put("sortedColumn", new PropertyField("String", "", "setSortedColumn", LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE)); // TabularType
		attributeMap.put("statusBarKey", new PropertyField("String", "", "getControllerPreferenceManager().setStatusBarKey",
				LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE));
		attributeMap.put("tableRendererKey", new PropertyField("String", "", "getControllerPreferenceManager().setTableRendererKey",
				LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE));
		attributeMap.put("style", new PropertyField("Style", "", "setStyle", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL));
		attributeMap.put("tabStops", new PropertyField("Integer", "getControl()"));
		attributeMap.put("tabs", new PropertyField("Integer", "getControl()"));
		attributeMap.put("text", new PropertyField("String", "getControl()", "setText") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				if (element instanceof EditableFormTextType)
					return "formText.";
				else if (element instanceof ExtraTextType)
					return "extraText.";
				else if (element instanceof RichTextType)
					return "richTextViewer.";
				else if (element instanceof MenuManagerType)
					return "";
				else if (element instanceof IItemCompositeWrapper)
					return "getItem().";
				else if (element instanceof ColumnFieldType)
					return "getControl().getColumn().";
				else if (element instanceof PGroupMenuType)
					return "getPGroupToolItem().";
				return controlGetter + ".";
			}

			@Override
			public LIFECYCLE_STAGE getLifeCycleStage(IElementWrapper element) {
				if (element instanceof MenuManagerType)
					return LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL;
				return super.getLifeCycleStage(element);
			}
		});
		attributeMap.put("textLayoutData", new PropertyField("String", "getControl().getText()", ".setLayoutData"));
		attributeMap.put("textLimit", new PropertyField("Integer", "getControl()") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				if (element instanceof RefTextType)
					return "getControl().getText().";
				return controlGetter + ".";
			}
		});
		attributeMap.put("toolTipText", new PropertyField("String", "getControl()") {
			@Override
			public String getControlGetter(IElementWrapper element) {
				if (element instanceof IItemCompositeWrapper)
					return "getItem().";
				else if (element instanceof PGroupMenuType)
					return "getPGroupToolItem().";
				else if (element instanceof FileTextType)
					return "getControl().getText().";
				return controlGetter + ".";
			}
		});
		attributeMap.put("treeManager", new PropertyField("String", ""));
		attributeMap.put("value", new PropertyField("Object", "", null, LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE));
		attributeMap.put("width", new PropertyField("Integer", "getControl().getColumn()")); // Column
		attributeMap.put("weights", new PropertyField("Weights", "getControl()", null, LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE));
		attributeMap.put("zoom", new PropertyField("Integer", "getControl()"));

		/* EditableFormText */
		attributeMap.put("parseTags", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("expandURLs", new PropertyField("Boolean", "getControl()"));
		attributeMap.put("paragraphsSeparated", new PropertyField("Boolean", "getControl().getFormText()"));
		attributeMap.put("whitespaceNormalized", new PropertyField("Boolean", "getControl().getFormText()"));

		/* NEBULA */
		attributeMap.put("groupStrategy", new PropertyField("groupStrategy", "getControl()", "setStrategy")); // PGroup
		attributeMap.put("toggleRenderer", new PropertyField("toggleRenderer", "getControl()", "setToggleRenderer")); // PGroup
		attributeMap.put("togglePosition", new PropertyField("Style", "getControl()", "setTogglePosition")); // PGroup
		attributeMap.put("imagePosition", new PropertyField("Style", "getControl()", "setImagePosition")); // PGroup
		attributeMap.put("linePosition", new PropertyField("Style", "getControl()", "setLinePosition")); // PGroup
		attributeMap.put("shelfRenderer", new PropertyField("shelfRenderer", "getControl()", "setRenderer")); // PShelf
		attributeMap.put("rowHeaderVisible", new PropertyField("Boolean", "getControl()")); // Grid
		attributeMap.put("columnScrolling", new PropertyField("Boolean", "getControl()")); // Grid
		attributeMap.put("selectionEnabled", new PropertyField("Boolean", "getControl()")); // Grid
		attributeMap.put("cellSelectionEnabled", new PropertyField("Boolean", "getControl()", null) {
			@Override
			public String getControlGetter(IElementWrapper element) {
				return element instanceof ColumnFieldType ? "getControl().getColumn()." : controlGetter + ".";
			}
		}); // Grid, GridColumn
		attributeMap.put("minimumWidth", new PropertyField("Integer", "getControl().getColumn()")); // Column
		attributeMap.put("summary", new PropertyField("Boolean", "getControl().getColumn()")); // GridColumn
		attributeMap.put("wordWrap", new PropertyField("Boolean", "getControl().getColumn()")); // GridColumn
		attributeMap.put("headerFont", new PropertyField("Font", "getControl()", null) {
			@Override
			public String getControlGetter(IElementWrapper element) {
				return element instanceof ColumnFieldType ? "getControl().getColumn()." : controlGetter + ".";
			}
		}); // GridColumn, GridColumnGroup
		attributeMap.put("headerWordWrap", new PropertyField("Boolean", "getControl()", null) {
			@Override
			public String getControlGetter(IElementWrapper element) {
				return element instanceof ColumnFieldType ? "getControl().getColumn()." : controlGetter + ".";
			}
		}); // GridColumn, GridColumnGroup
	}

	/**
	 * Process for properties of an element (e.g. RefTextWrapper, TableWrapper...) are centralized here whatever the element.
	 * However, process for property are customized.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * @param element
	 *            the element
	 * @param controllerClass
	 *            the controller class
	 * @param lifeCycleStage
	 *            the life cycle stage
	 * @param prefix
	 *            the prefix
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings({ "rawtypes" })
	public void processElement(BufferCode bufferCode, IElementWrapper element, Class controllerClass,
			LIFECYCLE_STAGE lifeCycleStage, String prefix) throws IOException {
		Class<?> elementClass = EngineTools.getChildXjcClass(element);
		while (EngineTools.isXmlType(elementClass)) {
			processField(elementClass, bufferCode, element, lifeCycleStage, prefix);
			elementClass = elementClass.getSuperclass();
		}
	}

	/**
	 * Process field.
	 * 
	 * @param elementClass
	 *            the element class
	 * @param bufferCode
	 *            the buffer code
	 * @param element
	 *            the element
	 * @param lifeCycleStage
	 *            the life cycle stage
	 * @param prefix
	 *            the prefix
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void processField(Class<?> elementClass, BufferCode bufferCode, IElementWrapper element, LIFECYCLE_STAGE lifeCycleStage,
			String prefix) throws IOException {
		for (Field field : elementClass.getDeclaredFields()) {
			PropertyField propertyField = attributeMap.get(field.getName());
			if (null != propertyField && lifeCycleStage == propertyField.getLifeCycleStage(element)) {
				Method getMethod = FieldTools.getGetMethod(elementClass, field.getName(), true);
				Object fieldValue;
				if (null != getMethod) {
					fieldValue = ReflectionTools.invokeMethod(getMethod, element);
					if (!propertyField.hasSpecialCode(bufferCode, fieldValue)) {

						String setter = propertyField.getSetter(element);
						if (null == setter)
							setter = "set" + EngineTools.upperCaseFirstLetter(field.getName());
						if (fieldValue instanceof LayoutType) {
							String migLayoutString = "";
							migLayoutString = "new " + bufferCode.getGenerator().getObjectName(MigLayout.class) + "(";
							migLayoutString += addMigLayoutAttibute(bufferCode, ((LayoutType) fieldValue).getLayoutConstraints());
							migLayoutString += ","
									+ addMigLayoutAttibute(bufferCode, ((LayoutType) fieldValue).getColumnConstraints());
							migLayoutString += ","
									+ addMigLayoutAttibute(bufferCode, ((LayoutType) fieldValue).getRowConstraints());
							migLayoutString += ")";
							bufferCode.append(
									prefix + propertyField.getControlGetter(element) + setter + "(" + migLayoutString + ");");
						} else if (fieldValue instanceof ImageTypeEnum) {
							bufferCode.getGenerator().getObjectName(ImageViewer.class);
							StringBuffer imageTypeSB = new StringBuffer("setImageType(ImageViewer.IMAGE_TYPE.");
							switch ((ImageTypeEnum) fieldValue) {
							case DATA:
								imageTypeSB.append("DATA");
								break;
							case FILE:
								imageTypeSB.append("FILE");
								break;
							case URL:
								imageTypeSB.append("URL");
								break;
							default:
								throw new RuntimeException("Image has no valid type! Usage is <imageType=\"Data | File | Url\">.");
							}
							bufferCode.append(imageTypeSB.append(");").toString());
						} else if (fieldValue instanceof MapDataTypeEnum) {
							bufferCode.getGenerator().getObjectName(GMap.class);
							StringBuffer gmapDataTypeSB = new StringBuffer("getControl().setMapDataType(GMap.MAP_DATA_TYPE.");
							gmapDataTypeSB.append(((MapDataTypeEnum) fieldValue).value());
							bufferCode.append(gmapDataTypeSB.append(");").toString());
						} else if (fieldValue instanceof MapTypeIdEnum) {
							bufferCode.getGenerator().getObjectName(GMap.class);
							StringBuffer gmapTypeIdSB = new StringBuffer("getControl().setMapTypeId(GMap.MAP_TYPE_ID.");
							gmapTypeIdSB.append(((MapTypeIdEnum) fieldValue).value());
							bufferCode.append(gmapTypeIdSB.append(");").toString());
						} else if (fieldValue instanceof MultiChoiceTypeEnum) {
							StringBuffer multiChoiceTypeSB = new StringBuffer("setMultiChoiceType(MULTI_CHOICE_TYPE.");
							switch ((MultiChoiceTypeEnum) fieldValue) {
							case ARRAY:
								multiChoiceTypeSB.append("ARRAY");
								break;
							case STRING:
								multiChoiceTypeSB.append("STRING");
								break;
							default:
								throw new RuntimeException(
										"MultiChoice has no valid type! Usage is <multiChoiceType=\"Array | String\">.");
							}
							bufferCode.append(multiChoiceTypeSB.append(");").toString());
						} else if (fieldValue instanceof ToolItemRendererEnum) {
							switch ((ToolItemRendererEnum) fieldValue) {
							case SIMPLE:
								// Default value
								break;
							case NONE:
								bufferCode.append("pgroup.setToolItemRenderer(null);");
								break;
							default:
								throw new RuntimeException(
										"PGroup has no valid Tool Item Renderer! Usage is <groupStrategy=\"Form | Rectangle | Simple\">.");
							}
						} else if (fieldValue instanceof ToggleRendererEnum) {
							StringBuffer toggleRendererSB = new StringBuffer(prefix + "getControl().setToggleRenderer(new ");
							switch ((ToggleRendererEnum) fieldValue) {
							case CHEVRONS:
								bufferCode.append(prefix + "getControl().setToggleRenderer(null);");
								// Default value
								break;
							case MIN_MAX:
								toggleRendererSB.append(getObjectName(bufferCode, MinMaxToggleRenderer.class));
								bufferCode.append(toggleRendererSB.append("());").toString());
								break;
							case NONE:
								bufferCode.append(prefix + "getControl().setToggleRenderer(null);");
								break;
							case TREE:
								toggleRendererSB.append(getObjectName(bufferCode, TreeNodeToggleRenderer.class));
								bufferCode.append(toggleRendererSB.append("());").toString());
								break;
							case TWISTE:
								toggleRendererSB.append(getObjectName(bufferCode, TwisteToggleRenderer.class));
								bufferCode.append(toggleRendererSB.append("());").toString());
								break;
							default:
								throw new RuntimeException(
										"PGroup has no valid strategy! Usage is <groupStrategy=\"Form | Rectangle | Simple\">.");
							}
						} else if (fieldValue instanceof ShelfRendererEnum) {
							StringBuffer shelfRendererSB = new StringBuffer("pshelf.setRenderer(new ");
							switch ((ShelfRendererEnum) fieldValue) {
							case PALETTE:
								// Default value
								break;
							case REDMOND:
								shelfRendererSB.append(getObjectName(bufferCode, RedmondShelfRenderer.class));
								bufferCode.append(shelfRendererSB.append("());").toString());
								break;
							default:
								throw new RuntimeException(
										"PShelf has no valid renderer! Usage is <shelfRenderer =\"Palette | Redmond\">.");
							}
						} else if (!(getMethod.getReturnType().equals(String.class))) {
							if (addProperty(fieldValue, elementClass, bufferCode, getMethod)) {
								if (!propertyField.getPropertyType().equals("Boolean") || null == propertyField.getNvlValue()
										|| !propertyField.getNvlValue().equals(fieldValue)) {
									bufferCode.append(
											prefix + propertyField.getControlGetter(element) + setter + "(" + fieldValue + ");");
								} else if (null != propertyField.getNvlValue())
									bufferCode.append(prefix + propertyField.getControlGetter(element) + setter + "("
											+ propertyField.getNvlValue() + ");");
							}
						} else if (EngineTools.isEmpty((String) fieldValue) && null != propertyField.getNvlValue()) {
							bufferCode.append(prefix + propertyField.getControlGetter(element) + setter + "("
									+ propertyField.getNvlValue() + ");");

						} else {
							if (addProperty(fieldValue, elementClass, bufferCode, getMethod)) {
								if ("refreshAtStart".equals(field.getName())) {
									boolean addCondition = !"true".equals(((String) fieldValue).toLowerCase());
									if (!"false".equals(((String) fieldValue).toLowerCase())) {
										if (addCondition)
											bufferCode
													.appendPlus("if("
															+ evalExpression(new Statement(prefix, getMethod.getName(), null,
																	bufferCode, field.getName(), (String) fieldValue), false)
															+ ")");
										bufferCode.append(prefix + propertyField.getControlGetter(element) + "refresh();");
										if (addCondition)
											bufferCode.addIdent(-1);
									}
								} else
									bufferCode.append(new Statement(prefix, propertyField.getControlGetter(element), setter,
											bufferCode, field.getName(), (String) fieldValue).getStatement(this));
							}
						}
					}
				} else { // if (null == getMethod)
					logError("Element:" + element.getId() + ",\t\tField:" + field.getName() + " has no getter!");
				}
			}
		}

		// }
	}

	/**
	 * Adds the property.
	 * 
	 * Return true when field value is not null and not in customization process or field value different from default value
	 * 
	 * @param fieldValue
	 *            the field value
	 * @param elementClass
	 *            the element class
	 * @param bufferCode
	 *            the buffer code
	 * @param getMethod
	 *            the get method
	 * @return true, if successful
	 */
	boolean addProperty(Object fieldValue, Class<?> elementClass, BufferCode bufferCode, Method getMethod) {
		if (null == fieldValue)
			return false;
		if (!bufferCode.isCustomization())
			return true;
		// Retur field value != default value
		return !fieldValue.equals(ReflectionTools.invokeMethod(getMethod, ReflectionTools.instantiateClass(elementClass)));
	}

	/**
	 * Return the string value from an expression interpreting functions as param, message or element...
	 * 
	 * @param buffer
	 *            the buffer
	 * @param expression
	 *            the expression
	 * @param typed
	 *            true if variable evaluation must be typed
	 * 
	 * @return the string
	 */
	public String evalExpr2Str(BufferCode buffer, String expression, boolean typed) {
		return evalExpr2Str(buffer, expression, typed, false);
	}

	public String evalExpression(BufferCode buffer, String expression, boolean typed, boolean insideString) {
		return evalExpression(new Statement(null, null, null, buffer, null, expression), typed, insideString);
	}

	/**
	 * Eval expr2 str.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param expression
	 *            the expression
	 * @param typed
	 *            the typed
	 * @param insideString
	 *            the inside string (if true do not add quotes)
	 * @return the string
	 */
	public String evalExpr2Str(BufferCode buffer, String expression, boolean typed, boolean insideString) {
		if (null == expression)
			return "null";
		String result = evalExpression(buffer, expression, typed, insideString);
		if (!insideString && result.equals(expression) && (!result.startsWith("\"") || !result.endsWith("\"")))
			return CodeGenerationUtil.betweenQuotes(expression);
		return result;
	}

	/**
	 * Eval expression.
	 *
	 * @param expression
	 *            the expression
	 * @return the string
	 */
	public String evalExpression(String expression) {
		// Use outside generator (e.g. #PLUGINHOME(pluginName))
		if ("#PLUGINHOME()".equals(expression)) {
			// Do not use for #PLUGINHOME() because scenario resource is needed and unknown
			logError(getFromGeneratorBundle("generation.bad.Expression", expression));
			return expression;
		}
		if (null == expression)
			return null;
		return evalExpression(new Statement(null, null, null, null, null, expression), false);
	}

	/**
	 * Eval expression slash.
	 *
	 * @param expression
	 *            the expression
	 * @return the string
	 */
	public String evalExpressionSlash(String expression) {
		String value = evalExpression(expression).replace('\\', '/');
		return value;
	}

	/**
	 * Eval expression.
	 *
	 * @param buffer
	 *            the buffer
	 * @param expression
	 *            the expression
	 * @param typed
	 *            the typed
	 * @return the string
	 */
	public String evalExpression(Statement statement, boolean typed) {
		return evalExpression(statement, typed, false);
	}

	/**
	 * Evaluate an expression interpreting functions as param, message or element...
	 *
	 * @param buffer
	 *            the buffer
	 * @param expression
	 *            the expression
	 * @param typed
	 *            true if variable evaluation must be typed
	 * @param insideString
	 *            the inside string
	 * @return the string
	 */
	private String evalExpression(Statement statement, boolean typed, boolean insideString) {
		String expression = statement.getExpression();
		BufferCode buffer = statement.getBufferCode();
		if (null == expression)
			return "null";
		StringBuffer expressionSB = new StringBuffer(expression);
		int index = 0;
		boolean addBeginQuote = true;
		boolean removeEndQuote = false;
		// int endFunction = 0;
		try {
			while (index < expressionSB.length() - 1) {
				if ('#' == expressionSB.charAt(index)) {
					AdiFunction function = new AdiFunction(expressionSB, index);
					if (!function.isFunction)
						break;
					String variable = expressionSB.substring(function.endFunction, function.left);
					if (variable.contains("#")) {
						String newExpression = variable;
						AdiFunction internalFunction = new AdiFunction(new StringBuffer(variable), 0);
						if (internalFunction.isFunction) {
							String varExpression = evalExpression(
									new Statement(null, null, null, buffer, statement.getProperty(), newExpression), typed,
									insideString);
							expression = expression.replace(variable, varExpression);
							variable = varExpression;
						}
					}
					expressionSB.delete(index, function.left + 1);
					String member = evalVariable(statement, function.functionKey, variable, typed);
					if (null == member) {
						logError(getFromGeneratorBundle("generation.bad.Expression", expression));
						return expression;
					}
					if (("FONT".equals(function.functionKey) || "CSSFONT".equals(function.functionKey))
							&& index < expressionSB.length() - 1) {
						member = enrichMemberFont(buffer.getGenerator(), member, index, expressionSB);
					}
					expressionSB.insert(index, member);
					index = index + member.length();
					if (insideString) {
						if (function.beginFunction == 0)
							addBeginQuote = false;
						else if (!removeEndQuote) {
							expressionSB.insert(function.beginFunction, "\" + ");
							index = index + 4;
						}
						expressionSB.insert(index, " + \"");
						index = index + 4;
						removeEndQuote = true;
					}

				} else {
					removeEndQuote = false;
					index++;
				}
			}
			if (insideString) {
				if (removeEndQuote)
					expressionSB.delete(index - 3, index);
				else
					expressionSB.append('"');
				if (addBeginQuote)
					expressionSB.insert(0, '"');
			}
			return expressionSB.toString();
		} catch (ParseException e) {
			logError(e);
		}
		return expression;
	}

	private String enrichMemberFont(ACodeGenerator generator, String member, int index, StringBuffer expressionSB) {
		int len = member.length();
		while (true) {
			member = enrich(generator, member, expressionSB, index, "bold");
			member = enrich(generator, member, expressionSB, index, "italic");
			member = enrich(generator, member, expressionSB, index, "normal");
			int newLen = member.length();
			if (newLen == len)
				break;
			else
				len = newLen;
		}
		return member;
	}

	private String enrich(ACodeGenerator generator, String member, StringBuffer expressionSB, int index, String key) {
		int len = expressionSB.length() - 1;
		if (-1 == len)
			return member;
		char c = expressionSB.charAt(index);
		while ((' ' == c || ')' == c) && index < len) {
			index++;
			c = expressionSB.charAt(index);
		}
		if ('.' == expressionSB.charAt(index)) {
			index++;
			int length = key.length() + 2;
			if (key.concat("()").equals(expressionSB.substring(index, index + length))) {
				expressionSB.delete(index - 1, index + length);
				member = generator.getObjectName(EngineTools.class) + ".getModifiedFont(" + member + ","
						+ generator.getObjectName(SWT.class) + "." + key.toUpperCase() + ")";
			}
		}
		return member;
	}

	/**
	 * determines the position of the left parenthesis corresponding to the right parenthesis.
	 * 
	 * @param sb
	 *            the sb
	 * @param pos
	 *            the pos
	 * @param rank
	 *            the rank
	 * 
	 * @return the int
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public static int findLeft(StringBuffer sb, int pos, int rank) throws ParseException {
		int index = pos;
		while (index < sb.length()) {
			if (')' == sb.charAt(index))
				if (0 == rank)
					return index;
				else
					return findLeft(sb, index + 1, rank - 1);
			if ('(' == sb.charAt(index))
				return findLeft(sb, index + 1, rank + 1);
			index++;
		}
		throw new ParseException("Right parenthesis is missing!", 0);
	}

	/**
	 * Gets the variable.
	 *
	 * @param function
	 *            the function
	 * @param expression
	 *            the expression
	 * @return the variable
	 */
	public static String getVariable(String function, String expression) {
		int start = function.length() + 1;
		try {
			return expression.substring(start, findLeft(new StringBuffer(expression), start, 0));
		} catch (ParseException e) {
			logError(getFromGeneratorBundle("keyword.invalid.expression", function, expression), e);
			return null;
		}
	}

	/**
	 * Eval variable.
	 *
	 * @param buffer
	 *            the buffer
	 * @param expression
	 *            the expression
	 * @param key
	 *            the key
	 * @param paramVariable
	 *            the param variable
	 * @param typed
	 *            true if variable evaluation must be typed
	 * @return the string
	 */
	private String evalVariable(Statement statement, String key, String paramVariable, boolean typed) {
		AEvalVariable evalVariable = functionMap.get(key);
		if (null != evalVariable)
			return evalVariable.eval(statement, key, paramVariable, typed);
		logError("Nothing to evaluate in <<" + paramVariable + ">> for <<" + key + ">>!");
		return "";
	}

	/**
	 * Eval property.
	 *
	 * @param statement
	 *            the statement
	 * @return the object
	 */
	public Object evalProperty(Statement statement) {
		BufferCode bufferCode = statement.getBufferCode();
		String property = statement.getProperty();
		bufferCode.setProperty(property);
		String value = evalExpression(statement, true);
		PropertyField propertyField = attributeMap.get(property);
		if (null == propertyField) {
			logError(getFromGeneratorBundle("generation.invalid.property", property));
			return statement.getExpression();
		}
		String propertyType = propertyField.getPropertyType();
		if (propertyType.equals("Integer")) {
			return Integer.valueOf(value);
		} else if (propertyType.equals("Boolean")) {
			if (value.toLowerCase().equals("true"))
				return true;
			else if (value.toLowerCase().equals("false"))
				return false;
			else
				return value;
		} else if (propertyType.equals("Color")) {
			return value;
		} else if (propertyType.equals("StringExpr")) {
			return getStringExpr(bufferCode, value);
		} else if (propertyType.equals("Font")) {
			return value;
		} else if (propertyType.equals("Image")) {
			return getImage(bufferCode, value);
		} else if (propertyType.equals("ImageDescriptor")) {
			return getImage(bufferCode, value);
		} else if (propertyType.equals("Locale")) {
			return getLocale(bufferCode, value);
		} else if (propertyType.equals("Orientation")) {
			if (null != value && -1 != value.indexOf("SWT."))
				bufferCode.getGenerator().getObjectName(SWT.class);
			return value;
		} else if (propertyType.equals("Weights")) {
			return "new int[]{" + value + "}";
		} else if (propertyType.equals("Entity")) {
			return "".equals(value) ? "null" : value;
		} else if (propertyType.equals("Style")) {
			return getStyle(bufferCode.getGenerator(), value);
		} else if (propertyType.equals("LazyFetches")) {
			return getLazyFetches(bufferCode, value);
		} else if (propertyType.equals("String")) {
			if (null == value)
				return "null";
			return (value.equals(statement.getExpression())) ? CodeGenerationUtil.betweenQuotes(value) : value;
		}
		bufferCode.setProperty(null);
		return value;
	}

	/**
	 * Gets the object name.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param clazz
	 *            the clazz
	 * @return the object name
	 */
	protected String getObjectName(BufferCode buffer, Class<?> clazz) {
		return buffer.getGenerator().getObjectName(clazz);
	}

	/**
	 * Adds the eval variable.
	 * 
	 * @param evalVariable
	 *            the eval variable
	 * @param keys
	 *            the keys
	 */
	protected void addEvalVariable(AEvalVariable evalVariable, String... keys) {
		for (String key : keys) {
			if (functionMap.containsKey(key))
				logWarning(getFromGeneratorBundle("keyword.duplicate.function", key));
			functionMap.put(key, evalVariable);
		}
	}

	/**
	 * Gets the element wrapper.
	 * 
	 * @param rootWrapper
	 *            the root wrapper
	 * @param variable
	 *            the variable
	 * @return the element wrapper
	 */
	private IElementWrapper getElementWrapper(IRootWrapper rootWrapper, String variable) {
		if (null == rootWrapper)
			return null;
		int index = variable.indexOf(':');
		if (-1 == index)
			return rootWrapper.getElementMap().get(variable);
		else {
			IElementWrapper element = rootWrapper.getElementMap().get(variable.substring(0, index));
			if (null != element && element instanceof IncludeWrapper)
				return getElementWrapper(((IncludeWrapper) element).getIncludeTree(), variable.substring(index + 1));
			return null;
		}
	}

	public String getRef(BufferCode buffer, String variable, boolean typed) {
		ScenarioInput scenarioInput = buffer.getGenerator().getScenarioInput();
		int commaPos = variable.indexOf(',');
		ACodeGenerator generator = buffer.getGenerator();
		String controllerClassName = null;
		if (0 < commaPos) {
			controllerClassName = variable.substring(commaPos + 1).trim();
			variable = variable.substring(0, commaPos);
		}
		if (typed) {
			try {
				Class<?> controllerClass;
				if (null == controllerClassName) {
					controllerClass = scenarioInput.getRegistryClass(variable);
				} else
					controllerClass = scenarioInput.getScenarioResources().getGencodePath().getClazz(controllerClassName);
				// clazz.getPackage() could return null due to classloader issue.
				int index = controllerClass.getName().lastIndexOf('.');
				String packageName = controllerClass.getName().substring(0, index);
				/*
				 * If class is internal class (e.g. new Controller(..){...}), take super class
				 */
				if (packageName.startsWith(scenarioInput.getScenarioResources().getGencodePath().getGencodePackage()))
					controllerClass = controllerClass.getSuperclass();
				return "(" + generator.getObjectName(controllerClass) + ") " + variable + ")";
			} catch (NullPointerException e) {
				logError(getFromGeneratorBundle("cannot.find.controller", variable));
				// throw e;
			}
		} // else
		return "getFromRegister(\"" + variable + "\")";
	}

	/**
	 * Gets the controller.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param variable
	 *            the variable
	 * @param typed
	 *            the typed
	 * @return the controller
	 */
	private String getController(BufferCode buffer, String variable, boolean typed) {
		ScenarioInput scenarioInput = buffer.getGenerator().getScenarioInput();
		int commaPos = variable.indexOf(',');
		ACodeGenerator generator = buffer.getGenerator();
		String controllerClassName = null;
		if (0 < commaPos) {
			controllerClassName = variable.substring(commaPos + 1).trim();
			variable = variable.substring(0, commaPos);
		}
		if (typed) {
			try {
				Class<?> controllerClass;
				if (null == controllerClassName) {
					controllerClass = scenarioInput.getRegistryClass(variable);
				} else
					controllerClass = scenarioInput.getScenarioResources().getGencodePath().getClazz(controllerClassName);
				// clazz.getPackage() could return null due to classloader issue.
				int index = controllerClass.getName().lastIndexOf('.');
				String packageName = controllerClass.getName().substring(0, index);
				/*
				 * If class is internal class (e.g. new Controller(..){...}), take super class
				 */
				if (packageName.startsWith(scenarioInput.getScenarioResources().getGencodePath().getGencodePackage()))
					controllerClass = controllerClass.getSuperclass();
				return "((" + generator.getObjectName(controllerClass) + ") getFromRegister(\"" + variable + "\"))";
			} catch (NullPointerException e) {
				logError(getFromGeneratorBundle("cannot.find.controller", variable));
				// throw e;
			}
		} // else
		return "getFromRegister(\"" + variable + "\")";
	}

	/**
	 * Adds the Miglayout attribute.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param attribute
	 *            the attribute
	 * @return the string
	 */
	private String addMigLayoutAttibute(BufferCode buffer, String attribute) {
		if (!EngineTools.isEmpty(attribute)) {
			Class<?> clazz = null;
			int index = attribute.indexOf('(');
			if (attribute.replace(" ", "").startsWith("newLC("))
				clazz = LC.class;
			if (attribute.replace(" ", "").startsWith("newCC("))
				clazz = CC.class;
			if (attribute.replace(" ", "").startsWith("newAC("))
				clazz = AC.class;
			if (null == clazz)
				return CodeGenerationUtil.betweenQuotes(attribute);
			return "new " + buffer.getGenerator().getObjectName(clazz) + attribute.substring(null == clazz ? 0 : index);
		}
		return null;
	}

	/**
	 * Gets the function map.
	 * 
	 * @return the function map
	 */
	public Map<String, AEvalVariable> getFunctionMap() {
		return functionMap;
	}

	/**
	 * Gets the style.
	 * 
	 * @param generator
	 *            the generator
	 * @param paramStr
	 *            the param str
	 * @return the style
	 */
	public String getStyle(ACodeGenerator generator, String paramStr) {
		if (null != paramStr) {
			if (-1 != paramStr.indexOf("SWT."))
				generator.getObjectName(SWT.class);
			if (-1 != paramStr.indexOf("AdiSWT."))
				generator.getObjectName(AdiSWT.class);
			if (-1 != paramStr.indexOf("Section."))
				generator.getObjectName(Section.class);
		}
		return paramStr;
	}

	public String getLazyFetches(BufferCode bufferCode, String lazyFetches) {
		String result = evalExpr2Str(bufferCode, lazyFetches, false);
		if (result.equals(lazyFetches)) {
			StringTokenizer tokenizer = new StringTokenizer(lazyFetches, ",");
			StringBuffer lazyBufferBuffer = new StringBuffer();
			while (tokenizer.hasMoreElements()) {
				lazyBufferBuffer.append(", ");
				lazyBufferBuffer.append(evalExpr2Str(bufferCode, tokenizer.nextToken().trim(), false));
			}
		}
		return result;
	}

	/**
	 * Gets the locale.
	 *
	 * @param bufferCode
	 *            the buffer code
	 * @param languageTag
	 *            the language tag
	 * @return the locale
	 */
	public String getLocale(BufferCode bufferCode, String languageTag) {
		Locale locale = Locale.forLanguageTag(languageTag);
		if (null != locale) {
			try {
				locale.getISO3Country();
				return bufferCode.getGenerator().getObjectName(Locale.class) + ".forLanguageTag("
						+ evalExpr2Str(bufferCode, languageTag, false) + ")";
			} catch (MissingResourceException e) {
			}
		}
		logError(getFromGeneratorBundle("generation.invalid.locale.value", languageTag));
		return "null";
	}

	/**
	 * Gets the color.
	 *
	 * @param generator
	 *            the generator
	 * @param expression
	 *            the expression
	 * @return the color
	 */
	public String getColor(Statement statement) {
		ACodeGenerator generator = statement.getBufferCode().getGenerator();
		String expression = statement.getExpression();
		String startString = "";
		String variableString = "";
		if (EngineTools.isHexaColor(expression)) {
			startString = generator.getObjectName(EngineTools.class) + ".getColorFromHexa(\"" + expression + "\")";
		} else if (expression.startsWith("IFormColors.")) {
			startString = generator.getObjectName(AdichatzApplication.class)
					+ ".getInstance().getFormToolkit().getColors().getColor(";
			variableString = expression.substring(11);
			variableString = generator.getObjectName(IFormColors.class) + variableString + ")";
		} else if (expression.startsWith("SWT.COLOR_")) {
			startString = generator.getObjectName(Display.class) + ".getCurrent().getSystemColor(";
			variableString = expression.substring(3);
			variableString = generator.getObjectName(SWT.class) + variableString + ")";
		} else {
			return evalExpression(statement, false);
		}
		return startString + variableString;
	}

	/**
	 * Gets the CSS color.
	 *
	 * @param statement
	 *            the statement
	 * @param expression
	 *            the expression
	 * @return the CSS color
	 */
	public String getCSSColor(Statement statement, String variables, boolean formText) {
		// bufferCode could be another buffer declared in ControlGenerator class
		int index = variables.indexOf(',');
		if (-1 != index) {
			String selector = variables.substring(0, index).trim();
			String colorProperty = variables.substring(index + 1).trim();
			ACodeGenerator generator = statement.getBufferCode().getGenerator();
			return generator.getObjectName(AReskinManager.class) + ".getInstance().getColor(\"" + selector + "\", \""
					+ colorProperty + "\", null, getControl())";
		} else
			logError(getFromGeneratorBundle("generation.invalid.CSS.color", variables));
		return null;
	}

	/**
	 * Gets the font.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * @param value
	 *            the value
	 * @return the font
	 */
	public String getFont(Statement statement) {
		String value = statement.getExpression().trim();
		if (value.startsWith("JFaceResources."))
			return statement.getBufferCode().getGenerator().getObjectName(JFaceResources.class) + ".getFontRegistry().get(" + value
					+ ")";
		return evalExpression(newStatement(statement, value), false);
	}

	/**
	 * Gets the CSS font.
	 *
	 * @param statement
	 *            the statement
	 * @param expression
	 *            the expression
	 * @return the CSS font
	 */
	public String getCSSFont(Statement statement, String selector) {
		ACodeGenerator generator = statement.getBufferCode().getGenerator();
		//		if ("font".equals(statement.getProperty())) {
		//			statement.setGetter("");
		//			if ("font".equals(statement.getProperty())) {
		//				control = "getControl()";
		//				statement.setSetter("setCssFont");
		//			} else {
		//				control = "getControl()";
		//				statement.setSetter("setCssForeground");
		//			}
		//			return "\"" + selector + "\", " + control;
		//		}
		//		else {
		return generator.getObjectName(AReskinManager.class) + ".getInstance().getFont(\"" + selector + "\", getControl())";
		//	}
	}

	public String getTextFont(BufferCode bufferCode, String value) {
		Statement statement = new Statement("", "", "", bufferCode, "font", value);
		Object result = evalProperty(statement);
		return statement.getSetter() + (statement.getSetter().isEmpty() ? "" : "(") + result + statement.getPostSetter();
	}

	/**
	 * Gets the image.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * @param value
	 *            the value
	 * @return the image
	 */
	public String getImage(BufferCode bufferCode, String value) {
		if (value.trim().startsWith("Dialog.")) {
			bufferCode.getGenerator().getObjectName(Dialog.class);
			return value;
		} else
			return evalExpression(bufferCode, value, false, false);
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param pluginResourcesKey
	 *            the plugin resources key
	 * @return the plugin resources
	 */
	public String getPluginResources(BufferCode buffer, String pluginResourcesKey) {
		return buffer.getGenerator().getObjectName(AdichatzApplication.class) + ".getPluginResources("
				+ evalExpr2Str(buffer, pluginResourcesKey, true) + ")";
	}

	/**
	 * Gets the string expr.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param value
	 *            the value
	 * @return the string expr
	 */
	private String getStringExpr(BufferCode buffer, String value) {
		return evalExpr2Str(buffer, value, false);
	}

	/**
	 * The Class AdiFunction.
	 *
	 * @author Yves Drumbonnet
	 */
	private class AdiFunction {

		/** The is function. */
		boolean isFunction;

		/** The function key. */
		String functionKey;

		/** The begin function. */
		int beginFunction;

		/** The end function. */
		int endFunction;

		/** The left. */
		int left;

		/** The expression SB. */
		StringBuffer expressionSB;

		/**
		 * Instantiates a new adi function.
		 *
		 * @param expressionSB
		 *            the expression SB
		 * @param index
		 *            the index
		 * @throws ParseException
		 *             the parse exception
		 */
		AdiFunction(StringBuffer expressionSB, int index) throws ParseException {
			this.expressionSB = expressionSB;
			endFunction = expressionSB.indexOf("(", index + 1) + 1;
			if (0 != endFunction) {
				beginFunction = index;
				functionKey = expressionSB.substring(index + 1, endFunction - 1);
				if (functionMap.containsKey(functionKey)) {
					isFunction = true;
					// searching for left parenthesis corresponding to the function
					left = findLeft(expressionSB, endFunction, 0);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			if (!isFunction)
				return expressionSB.toString() + " is not a fuction";
			// TODO Auto-generated method stub
			return expressionSB.toString() + " - Function:" + functionKey + " -  Begin: " + beginFunction + ", left: " + left
					+ ", end: " + endFunction;
		}
	}

	/**
	 * Gets the statement. (used by test).
	 *
	 * @param prefix
	 *            the prefix
	 * @param getter
	 *            the getter
	 * @param setter
	 *            the setter
	 * @param bufferCode
	 *            the buffer code
	 * @param property
	 *            the property
	 * @param expression
	 *            the expression
	 * @return the statement
	 */
	public String getStatement(String prefix, String getter, String setter, BufferCode bufferCode, String property,
			String expression) {
		return new Statement(prefix, getter, setter, bufferCode, property, expression).getStatement(this);
	}
}
