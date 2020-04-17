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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.adichatz.common.ejb.AEntityCallback;
import org.adichatz.common.ejb.EntityModel;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;

// TODO: Auto-generated Javadoc
/**
 * The Class PersistenceManagerGenerator.
 */
public class PersistenceManagerGenerator extends ARewriter {

	/**
	 * Generate.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 */
	@SuppressWarnings("unchecked")
	public void generate(ScenarioResources scenarioResources) {

		ModelPartType modelPart = scenarioResources.getGenerationScenario().getModelPart();

		logInfo(getFromGeneratorBundle("jpa.buildingRolePrivilegeClass", modelPart.getPersistenceManagerClassName()));
		IPath path = new Path(scenarioResources.getParam(IScenarioConstants.PLUGIN_PACKAGE).replace('.', '/') + "/ejb/"
				+ modelPart.getPersistenceManagerClassName() + ".java");
		try {
			ICompilationUnit compilationUnit = (ICompilationUnit) scenarioResources.getJavaProject().findElement(path);
			/* Instanciates a document for the PersistenceManager bean. */
			Document doc = new Document(compilationUnit.getSource());
			compilationUnit.close();
			astRoot = getAstRoot(compilationUnit);
			ast = astRoot.getAST();
			ASTRewrite rewriter = ASTRewrite.create(ast);

			MPBVisitor visitor = new MPBVisitor("initialize");
			astRoot.accept(visitor);

			MethodDeclaration methodDeclaration = visitor.getSearchedMethod();

			/*
			 * Adds a initialize method with @Inject annotation
			 */
			Block block = ast.newBlock();
			for (PluginEntityType pluginEntity : scenarioResources.getGenerationScenario().getPluginEntity()) {
				EntityTreeWrapper entityTree = ((PluginEntityWrapper) pluginEntity).getEntityTree();
				if (null != entityTree && !EngineTools.isEmpty(entityTree.getCallbackClassNames())
						|| null != pluginEntity.getRetrieveRoles() || null != pluginEntity.getMergeRoles()
						|| null != pluginEntity.getPersistRoles() || null != pluginEntity.getRemoveRoles()) {
					if (null == methodDeclaration) {
						methodDeclaration = ast.newMethodDeclaration();
						methodDeclaration.setConstructor(false);
						NormalAnnotation inject = ast.newNormalAnnotation();
						inject.setTypeName(ast.newSimpleName("Inject"));
						addImport(Inject.class.getName());
						methodDeclaration.modifiers().add(inject);
						methodDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
						methodDeclaration.setName(ast.newSimpleName("initialize"));
						TypeDeclaration classType = (TypeDeclaration) astRoot.types().get(0);
						classType.bodyDeclarations().add(methodDeclaration);
						Javadoc javadoc = ast.newJavadoc();
						ACompilationUnitGenerator.addLine2Comment(ast, javadoc,
								"This method would be automatically generated by Adichatz tools when security or callbacks are injected");
						ACompilationUnitGenerator.addLine2Comment(ast, javadoc, "");
						ACompilationUnitGenerator.addLine2Comment(ast, javadoc, "BE CAREFUL BEFORE UPDATING THIS METHOD!");
						ACompilationUnitGenerator.addLine2Comment(ast, javadoc, "");
						ACompilationUnitGenerator.addLine2Comment(ast, javadoc,
								"Changes will be transient up to next security injection process.");
						ACompilationUnitGenerator.addLine2Comment(ast, javadoc, "");
						methodDeclaration.setJavadoc(javadoc);
					}
					Block methodBlock = ast.newBlock();
					methodDeclaration.setBody(methodBlock);
					VariableDeclarationStatement vds;
					VariableDeclarationFragment vdf;
					// EntityModel<BEAN> entityModel = new EntityModel<BEAN>();
					vdf = ast.newVariableDeclarationFragment();
					vds = ast.newVariableDeclarationStatement(vdf);

					Class<?> beanClass = ((PluginEntityWrapper) pluginEntity).getBeanClass();
					if (null == beanClass)
						throw new RuntimeException("No class found for pluginEntity '" + pluginEntity.getEntityURI()
								+ "'! May be scenario resources must be refreshed!?");
					addImport(beanClass.getName());
					addImport(EntityModel.class.getName());
					vdf.setName(ast.newSimpleName("entityModel"));
					vds.setType(getParameterizedType(EntityModel.class, beanClass));
					ClassInstanceCreation cc = ast.newClassInstanceCreation();
					cc.setType(getParameterizedType(EntityModel.class, beanClass));
					vdf.setInitializer(cc);
					methodBlock.statements().add(vds);

					// entityModelMap.put("#BEANCLASSNAME", entityModel);
					MethodInvocation mi = ast.newMethodInvocation();
					mi.setExpression(ast.newSimpleName("entityModelMap"));
					mi.setName(ast.newSimpleName("put"));
					StringLiteral sl = ast.newStringLiteral();
					sl.setLiteralValue(beanClass.getName());
					mi.arguments().add(sl);
					mi.arguments().add(ast.newSimpleName("entityModel"));
					methodBlock.statements().add(ast.newExpressionStatement(mi));

					if (!EngineTools.isEmpty(entityTree.getCallbackClassNames())) {
						StringTokenizer tokenizer = new StringTokenizer(entityTree.getCallbackClassNames(), ",");
						int length = tokenizer.countTokens();
						addImport(ArrayList.class.getName());
						addImport(List.class.getName());
						addImport(AEntityCallback.class.getName());

						// List<AEntityCallback<#BEANCLASSNAME>> entityCallbacks = new ArrayList<>(#CALLBACK_NUMBER);
						vdf = ast.newVariableDeclarationFragment();
						vds = ast.newVariableDeclarationStatement(vdf);
						vdf.setName(ast.newSimpleName("entityCallbacks"));
						vds.setType(getListCallbackPT(List.class, beanClass));
						cc = ast.newClassInstanceCreation();
						cc.setType(getListCallbackPT(ArrayList.class, beanClass));
						cc.arguments().add(ast.newNumberLiteral(String.valueOf(length)));

						vdf.setInitializer(cc);
						methodBlock.statements().add(vds);

						while (tokenizer.hasMoreTokens()) {
							String callbackClassName = tokenizer.nextToken().trim();
							String simpleCallbackClassName = callbackClassName.substring(callbackClassName.lastIndexOf('.') + 1);
							addImport(callbackClassName);

							// entityCallbacks.add(new #CALLBACK(entityManager));
							mi = ast.newMethodInvocation();
							mi.setExpression(ast.newSimpleName("entityCallbacks"));
							mi.setName(ast.newSimpleName("add"));
							cc = ast.newClassInstanceCreation();
							cc.setType(ast.newSimpleType(ast.newSimpleName(simpleCallbackClassName)));
							cc.arguments().add(ast.newSimpleName("entityManager"));
							mi.arguments().add(cc);
							methodBlock.statements().add(ast.newExpressionStatement(mi));
						}
						// entityModel.setEntityCallbacks(entityCallbacks);
						mi = ast.newMethodInvocation();
						mi.setExpression(ast.newSimpleName("entityModel"));
						mi.setName(ast.newSimpleName("setEntityCallbacks"));
						mi.arguments().add(ast.newSimpleName("entityCallbacks"));
						methodBlock.statements().add(ast.newExpressionStatement(mi));

						if (!EngineTools.isEmpty(pluginEntity.getRetrieveRoles())) {
							addImport(IEntityConstants.class.getName());
							addRoleTableArgument(methodBlock, "IEntityConstants.RETRIEVE", pluginEntity.getRetrieveRoles());
						}
						if (!EngineTools.isEmpty(pluginEntity.getPersistRoles())) {
							addImport(IEntityConstants.class.getName());
							addRoleTableArgument(methodBlock, "IEntityConstants.PERSIST", pluginEntity.getPersistRoles());
						}
						if (!EngineTools.isEmpty(pluginEntity.getRemoveRoles())) {
							addImport(IEntityConstants.class.getName());
							addRoleTableArgument(methodBlock, "IEntityConstants.REMOVE", pluginEntity.getRemoveRoles());
						}
						if (!EngineTools.isEmpty(pluginEntity.getMergeRoles())) {
							addImport(IEntityConstants.class.getName());
							addRoleTableArgument(methodBlock, "IEntityConstants.MERGE", pluginEntity.getMergeRoles());
						}
					}
					MethodInvocation addEntityInvocation = ast.newMethodInvocation();
					addEntityInvocation.setName(ast.newSimpleName("addEntityModel"));

					TypeLiteral modelClass = ast.newTypeLiteral();
					String entityClassName = ((PluginEntityWrapper) pluginEntity).getBeanClass().getCanonicalName();
					modelClass.setType(ast.newSimpleType(GeneratorUtil.getQualifiedName(ast, entityClassName)));
					addEntityInvocation.arguments().add(modelClass);
					block.statements().add(ast.newExpressionStatement(addEntityInvocation));
				}
			}

			if (IScenarioConstants.JSE.equals(modelPart.getConnectorASVersion())) {
				visitor = new MPBVisitor("JSEPersistenceManager");
				astRoot.accept(visitor);
				block = ast.newBlock();
				SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();
				block.statements().add(superConstructorInvocation);
				StringLiteral sl = ast.newStringLiteral();
				sl.setLiteralValue(modelPart.getDataSourceUnit());
				superConstructorInvocation.arguments().add(sl);
				superConstructorInvocation.arguments().add(ast.newName("properties"));

				/* Rewrites method */
				rewriter.replace(visitor.getSearchedMethod().getBody(), block, null);
			} else {
				visitor = new MPBVisitor("entityManager");
				astRoot.accept(visitor);

				FieldDeclaration fieldDeclaration = visitor.getSearchedFieldDeclaration();
				if (null == fieldDeclaration)
					throw new RuntimeException(
							"Field <private EntitManager entityManager;> must exist in Persistence Manager class!!! "
									+ "Roles, Privileges and Callbacks will no be managed in this project.");
				Object modifier = fieldDeclaration.modifiers().get(0);
				if (!(modifier instanceof NormalAnnotation))
					throw new RuntimeException("Field 'entityManager' of class '" + modelPart.getPersistenceManagerClassName()
							+ "' must have PersistenceContext as first modifier.");

				VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
				vdf.setName(ast.newSimpleName("entityManager"));
				fieldDeclaration = ast.newFieldDeclaration(vdf);
				fieldDeclaration.setType(ast.newSimpleType(ast.newSimpleName("EntityManager")));

				NormalAnnotation annotation = ast.newNormalAnnotation();
				annotation.setTypeName(ast.newName("PersistenceContext"));

				MemberValuePair memberValuePair = ast.newMemberValuePair();
				memberValuePair.setName(memberValuePair.getAST().newSimpleName("unitName"));
				StringLiteral literal = memberValuePair.getAST().newStringLiteral();
				literal.setLiteralValue(modelPart.getDataSourceUnit());
				memberValuePair.setValue(literal);
				annotation.values().add(memberValuePair);

				fieldDeclaration.modifiers().add(annotation);
				fieldDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PRIVATE_KEYWORD));

			}
			rewrite(compilationUnit, doc, astRoot);
		} catch (JavaModelException | MalformedTreeException | BadLocationException e) {
			logError(e);
		}
	}

	@SuppressWarnings("unchecked")
	private ParameterizedType getListCallbackPT(Class<?> listClass, Class<?> beanClass) {
		ParameterizedType parameterizedType = ast
				.newParameterizedType(ast.newSimpleType(ast.newSimpleName(listClass.getSimpleName())));
		ParameterizedType argPM = getParameterizedType(AEntityCallback.class, beanClass);
		parameterizedType.typeArguments().add(argPM);
		return parameterizedType;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ParameterizedType getParameterizedType(Class type, Class<?> genericTypeClass) {
		ParameterizedType argPM = ast.newParameterizedType(ast.newSimpleType(ast.newSimpleName(type.getSimpleName())));
		argPM.typeArguments().add(ast.newSimpleType(ast.newSimpleName(genericTypeClass.getSimpleName())));
		return argPM;
	}

	/**
	 * Adds the role table argument.
	 * 
	 * @param ast
	 *            the ast
	 * @param addEntityInvocation
	 *            the add entity invocation
	 * @param roles
	 *            the roles
	 */
	@SuppressWarnings("unchecked")
	private void addRoleTableArgument(Block block, String privilege, String roles) {
		MethodInvocation mi = ast.newMethodInvocation();
		mi.setExpression(ast.newSimpleName("entityModel"));
		mi.setName(ast.newSimpleName("addPrivilege"));
		block.statements().add(ast.newExpressionStatement(mi));
		mi.arguments().add(ast.newName(ACompilationUnitGenerator.getSimpleNames(privilege)));

		ArrayCreation rolesCreation = ast.newArrayCreation();
		rolesCreation.setType(ast.newArrayType(ast.newSimpleType(ast.newSimpleName("String"))));
		ArrayInitializer rolesInitializer = ast.newArrayInitializer();
		StringTokenizer tokenizer = new StringTokenizer(roles, ",");
		while (tokenizer.hasMoreElements()) {
			StringLiteral literal = ast.newStringLiteral();
			literal.setLiteralValue(tokenizer.nextToken().trim());
			rolesInitializer.expressions().add(literal);
		}
		rolesCreation.setInitializer(rolesInitializer);
		mi.arguments().add(rolesCreation);
	}

}