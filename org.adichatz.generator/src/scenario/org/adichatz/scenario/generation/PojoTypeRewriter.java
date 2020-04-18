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
package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.QueryBuilderType;
import org.adichatz.scenario.IPojoRewriter;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class PojoTypeRewriter.
 */
public class PojoTypeRewriter extends ARewriter implements IPojoRewriter {

	/** The tsvector. */
	private String TSVECTOR = "tsvector";

	private String HIBERNATE_DIALECT = "hibernate.dialect";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPojoRewriter#generate(org.adichatz.scenario. ScenarioResources,
	 * org.adichatz.generator.wrapper.GenerationScenarioWrapper, org.adichatz.generator.wrapper.PluginEntityWrapper,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void generate(ScenarioResources scenarioResources, GenerationScenarioWrapper generationScenario,
			Map<String, Object> params) throws IOException, CoreException {
		PluginEntityWrapper pluginEntity = (PluginEntityWrapper) params.get(PLUGIN_ENTITY);
		String beanClassName = (String) params.get(BEAN_CLASS_NAME);
		IJavaProject javaProject = scenarioResources.getJavaProject();
		try {
			ICompilationUnit compilationUnit = javaProject.findType(beanClassName).getCompilationUnit();
			Document document = new Document(compilationUnit.getSource());
			compilationUnit.close();

			astRoot = getAstRoot(compilationUnit);
			ast = astRoot.getAST();

			for (Field field : scenarioResources.getGencodePath().getClazz(beanClassName).getDeclaredFields())
				if (!Modifier.isStatic(field.getModifiers())) {
					boolean addEnum = false;
					String fieldName = field.getName();
					PropertyFieldType propertyField = pluginEntity.getPropertyField(fieldName);
					String pojoType = null;
					if (null != propertyField)
						pojoType = propertyField.getPojoType();
					else {
						PropertyFieldType pField = generationScenario.getPropertyField(fieldName);
						if (null != pField && !EngineTools.isEmpty(pField.getPojoType()))
							pojoType = pField.getPojoType();
					}
					if (!EngineTools.isEmpty(pojoType)) {
						String upperFieldName = EngineTools.upperCaseFirstLetter(fieldName);

						String fieldType;
						boolean isArray = false;
						if (-1 != pojoType.indexOf("#ENUM")) {
							pojoType = new EnumGenerator().generate(scenarioResources, pojoType.trim());
							addEnum = true;
						} else {
							Class<?> pojoTypeClass = scenarioResources.getGencodePath().getClazz(pojoType, true);
							if (null != pojoTypeClass && pojoTypeClass.isEnum())
								addEnum = true;
						}
						int pojoIndex = pojoType.lastIndexOf('.');
						if (-1 != pojoIndex) {
							int beanIndex = beanClassName.lastIndexOf('.');
							fieldType = pojoType.substring(pojoIndex + 1);
							String pojoPakageName = pojoType.substring(0, pojoIndex);
							if (-1 == beanIndex)
								addImport(pojoType);
							else {
								String beanPakageName = beanClassName.substring(0, pojoIndex);
								if (!pojoPakageName.equals(beanPakageName))
									addImport(pojoType);
							}
						} else {
							fieldType = pojoType;
						}

						if (fieldType.endsWith("[]")) {// array
							isArray = true;
							fieldType = fieldType.substring(0, fieldType.length() - 2);
						}

						// Field
						MPBVisitor visitor = new MPBVisitor(fieldName);
						astRoot.accept(visitor);
						FieldDeclaration fieldDeclaration = visitor.getSearchedFieldDeclaration();
						if (null == fieldDeclaration)
							throw new RuntimeException("No '" + fieldName + "' field for '" + beanClassName + "' class!?");

						if (!TSVECTOR.equals(pojoType))
							fieldDeclaration.setType(getType(ast, fieldType, isArray));

						// getMethod
						MethodDeclaration getMethodDeclaration = GeneratorUtil.getMethodDeclaration(astRoot,
								"get".concat(upperFieldName));
						if (null == getMethodDeclaration)
							throw new RuntimeException(
									"No '" + "get".concat(upperFieldName) + "' field for '" + beanClassName + "' class!?");
						if (!TSVECTOR.equals(pojoType))
							getMethodDeclaration.setReturnType2(getType(ast, fieldType, isArray));
						if (!fieldType.endsWith(".Date") && !pojoType.endsWith(".Date")) {
							Annotation temporalAnnotation = GeneratorUtil.getAnnotation(getMethodDeclaration, "Temporal");
							if (null != temporalAnnotation)
								getMethodDeclaration.modifiers().remove(temporalAnnotation);
						}
						if (addEnum || (isArray && !"byte".equals(fieldType))) {
							Annotation typeAnnotation = GeneratorUtil.getAnnotation(getMethodDeclaration, "Type");
							if (null != typeAnnotation)
								getMethodDeclaration.modifiers().remove(typeAnnotation);
							typeAnnotation = ast.newNormalAnnotation();
							addImport("org.hibernate.annotations.Type");
							typeAnnotation.setTypeName(ast.newSimpleName("Type"));
							MemberValuePair member = ast.newMemberValuePair();
							member.setName(ast.newSimpleName("type"));
							if (addEnum) {
								StringLiteral stringLiteral = ast.newStringLiteral();
								stringLiteral.setLiteralValue(scenarioResources.getPluginPackage() + ".ejb.GlobalEnumType");
								member.setValue(stringLiteral);
								((NormalAnnotation) typeAnnotation).values().add(member);

								// parameters={@Parameter(name="enumClassName",
								// value="${ClassName}}")
								member = ast.newMemberValuePair();
								member.setName(ast.newSimpleName("parameters"));
								NormalAnnotation parameterAnnotation = ast.newNormalAnnotation();
								parameterAnnotation.setTypeName(ast.newSimpleName("Parameter"));
								addImport("org.hibernate.annotations.Parameter");
								MemberValuePair paramMember = ast.newMemberValuePair();
								paramMember.setName(ast.newSimpleName("name"));
								stringLiteral = ast.newStringLiteral();
								stringLiteral.setLiteralValue("enumClassName");
								paramMember.setValue(stringLiteral);
								parameterAnnotation.values().add(paramMember);

								paramMember = ast.newMemberValuePair();
								paramMember.setName(ast.newSimpleName("value"));
								stringLiteral = ast.newStringLiteral();
								stringLiteral.setLiteralValue(pojoType);
								paramMember.setValue(stringLiteral);
								parameterAnnotation.values().add(paramMember);
								member.setValue(parameterAnnotation);
								((NormalAnnotation) typeAnnotation).values().add(member);
							} else {
								StringLiteral stringLiteral = ast.newStringLiteral();
								// Only array of String is available.
								stringLiteral.setLiteralValue(scenarioResources.getPluginPackage() + ".ejb.StringArrayType");
								member.setValue(stringLiteral);
								((NormalAnnotation) typeAnnotation).values().add(member);
							}
							getMethodDeclaration.modifiers().add(1, typeAnnotation);
						}
						// setMethod
						MethodDeclaration setMethodDeclaration = GeneratorUtil.getMethodDeclaration(astRoot,
								"set".concat(upperFieldName));
						if (null == setMethodDeclaration)
							throw new RuntimeException(
									"No '" + "set".concat(upperFieldName) + "' field for '" + beanClassName + "' class!?");
						if (TSVECTOR.equals(fieldType) || "skip".equals(fieldType)) {
							// Delete field, get and set method for this field
							// because tsvector field cannot be managed by JPA.
							TypeDeclaration classType = (TypeDeclaration) astRoot.types().get(0);
							classType.bodyDeclarations().remove(setMethodDeclaration);
							classType.bodyDeclarations().remove(getMethodDeclaration);
							classType.bodyDeclarations().remove(fieldDeclaration);
							if (TSVECTOR.equals(fieldType)) {
								ModelPartType modelPart = scenarioResources.getGenerationScenario().getModelPart();
								DatasourceWrapper dataSource = scenarioResources.getConnectorTree()
										.getDatasource(modelPart.getConnectorDataSource());
								if (null == dataSource)
									logError(getFromGeneratorBundle("generation.invalid.database",
											modelPart.getConnectorDataSource()));
								else if ("pgsql".equals(dataSource.getDriver())) {
									// add Dialect in model part params if not exist
									if (null == GeneratorUtil.getParam(modelPart.getParams(), HIBERNATE_DIALECT)) {
										ParamsType paramsType = modelPart.getParams();
										if (null == paramsType) {
											paramsType = new ParamsType();
											modelPart.setParams(paramsType);
										}
										ParamType param = new ParamType();
										param.setId(HIBERNATE_DIALECT);
										param.setValue(scenarioResources.getPluginPackage() + ".ejb.PgFullTextDialect");
										paramsType.getParam().add(param);
									}

									/*
									 * add param FullTextIncludeURI with type QUERY on current plugin entity. FullTextIncludeURI
									 * could be passed as variable (ParamMap), to QueryForm.xml to indicate to use a FullText clause
									 */
									if (null == pluginEntity.getQueryBuilder()) {
										QueryBuilderType queryBuilder = new QueryBuilderType();
										queryBuilder.setQueryBuilderURI("bundleclass://" + scenarioResources.getPluginName() + "/"
												+ scenarioResources.getPluginPackage() + ".PgQueryBuilder");
										ParamsType builderParams = new ParamsType();
										queryBuilder.setParams(builderParams);
										ParamType param = new ParamType();
										param.setId("#LANGUAGE#");
										param.setValue("english");
										builderParams.getParam().add(param);
										param = new ParamType();
										param.setId("#FIELD_NAME#");
										param.setValue(field.getName());
										builderParams.getParam().add(param);
										pluginEntity.setQueryBuilder(queryBuilder);
										// }
									}
								} else
									logError(getFromGeneratorBundle("generation.no.specific.dialect",
											modelPart.getConnectorDataSource()));
							}
						} else {
							SingleVariableDeclaration vd = (SingleVariableDeclaration) setMethodDeclaration.parameters().get(0);
							vd.setType(getType(ast, fieldType, isArray));
						}
					}
				}
			rewrite(compilationUnit, document, astRoot);
			logInfo(getFromGeneratorBundle("generation.rewriter.launch", this.getClass().getSimpleName(), beanClassName));
		} catch (JavaModelException | BadLocationException | RuntimeException e) {
			logError(e);
		}
	}

	private Type getType(AST ast, String fieldType, boolean isArray) {
		Type type;
		if (Character.isLowerCase(fieldType.charAt(0)))
			type = ast.newPrimitiveType(PrimitiveType.toCode(fieldType));
		else
			type = ast.newSimpleType(ast.newSimpleName(fieldType));
		if (isArray)
			type = ast.newArrayType(type);
		return type;
	}
}
