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

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.IBeanInterceptorFactory;
import org.adichatz.engine.e4.resource.AStartupLifeCycleHandler;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

// TODO: Auto-generated Javadoc
/**
 * The Class AdichatzPluginGenerator.
 */
@SuppressWarnings({ "unchecked" })
public class StartLifeCycleGenerator extends ACompilationUnitGenerator {

	/** The Generated class name. */
	private String GENERATED_CLASS_NAME = "StartupLifeCycleHandler";

	/**
	 * Generate activator.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public void generateCU(ScenarioResources scenarioResources) {
		IFolder srcFolder = scenarioResources.getProject().getFolder("src");
		IPackageFragmentRoot srcFragment = scenarioResources.getJavaProject().getPackageFragmentRoot(srcFolder);
		IPackageFragment packageFragment = srcFragment.getPackageFragment(scenarioResources.getPluginPackage());

		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setSource("".toCharArray());

		CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);
		astRoot.recordModifications();
		AST ast = astRoot.getAST();

		PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
		packageDeclaration.setJavadoc(addClassComment(ast));
		astRoot.setPackage(packageDeclaration);
		StringTokenizer tokenizer = new StringTokenizer(scenarioResources.getPluginPackage(), ".");
		int countTokens = tokenizer.countTokens();
		String[] names = new String[countTokens];
		int i = 0;
		while (tokenizer.hasMoreTokens())
			names[i++] = tokenizer.nextToken();

		Name name = ast.newName(names);

		packageDeclaration.setName(name);

		Set<String> imports = new HashSet<String>();
		imports.add(AStartupLifeCycleHandler.class.getName());
		imports.add(PostContextCreate.class.getName());
		imports.add(AdichatzApplication.class.getName());
		if (scenarioResources.hasModelPart()) {
			imports.add(ADataAccess.class.getName());
			imports.add(JPADataAccess.class.getName());
			imports.add(IBeanInterceptorFactory.class.getName());
		}

		// public class StartupLifeCycleHandler extends AStartupLifeCycleHandler {
		TypeDeclaration classType = ast.newTypeDeclaration();
		classType.setJavadoc(addConstructorComment(ast, GENERATED_CLASS_NAME, null, true));
		classType.setInterface(false);
		classType.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
		classType.setName(ast.newSimpleName(GENERATED_CLASS_NAME));
		classType.setSuperclassType(ast.newSimpleType(ast.newSimpleName(AStartupLifeCycleHandler.class.getSimpleName())));

		astRoot.types().add(classType);

		// public void startup(final IEventBroker eventBroker) {
		MethodDeclaration methodDeclaration = ast.newMethodDeclaration();
		methodDeclaration.setConstructor(false);
		methodDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
		methodDeclaration.setName(ast.newSimpleName("startup"));
		classType.bodyDeclarations().add(methodDeclaration);

		Block methodBlock = ast.newBlock();
		// @PostContextCreate
		MarkerAnnotation postContextCreate = ast.newMarkerAnnotation();
		postContextCreate.setTypeName(ast.newSimpleName("PostContextCreate"));
		methodDeclaration.modifiers().add(0, postContextCreate);

		methodDeclaration.setBody(methodBlock);

		ClassInstanceCreation cc;

		// pluginResources = AdichatzApplication.getPluginResources("projectName");
		MethodInvocation mi = ast.newMethodInvocation();
		mi.setExpression(ast.newSimpleName("AdichatzApplication"));
		mi.setName(ast.newSimpleName("getPluginResources"));
		StringLiteral sl;
		sl = ast.newStringLiteral();
		sl.setLiteralValue(scenarioResources.getPluginName());
		mi.arguments().add(sl);

		Assignment assignment = ast.newAssignment();
		assignment.setLeftHandSide(ast.newSimpleName("pluginResources"));
		assignment.setRightHandSide(mi);
		methodBlock.statements().add(ast.newExpressionStatement(assignment));

		// init(context);
		mi = ast.newMethodInvocation();
		mi.setName(ast.newSimpleName("init"));
		mi.arguments().add(ast.newSimpleName("context"));
		methodBlock.statements().add(ast.newExpressionStatement(mi));

		VariableDeclarationStatement vds;
		VariableDeclarationFragment vdf;
		if (scenarioResources.hasModelPart()) {

			// IBeanInterceptorFactory beanInterceptorFactory = BeanInterceptor.createBeanInterceptorFactory();
			vdf = ast.newVariableDeclarationFragment();
			vdf.setName(ast.newSimpleName("beanInterceptorFactory"));
			vds = ast.newVariableDeclarationStatement(vdf);
			vds.setType(ast.newSimpleType(ast.newSimpleName("IBeanInterceptorFactory")));

			mi = ast.newMethodInvocation();
			mi.setExpression(ast.newSimpleName("BeanInterceptor"));
			mi.setName(ast.newSimpleName("createBeanInterceptorFactory"));
			vdf.setInitializer(mi);
			methodBlock.statements().add(vds);

			// ADataAccess dataAccess = new JPADataAccess(pluginResources, beanInterceptorFactory, new JPAConnector());
			cc = ast.newClassInstanceCreation();
			cc.setType(ast.newSimpleType(ast.newSimpleName("JPADataAccess")));
			cc.arguments().add(ast.newName(getSimpleNames("pluginResources")));
			cc.arguments().add(ast.newName(getSimpleNames("beanInterceptorFactory")));
			ClassInstanceCreation ccJPA = ast.newClassInstanceCreation();
			ccJPA.setType(ast.newSimpleType(ast.newSimpleName("JPAConnector")));
			if (IScenarioConstants.JSE.equals(scenarioResources.getConnectorVersion())) {
				ccJPA.arguments().add(ast.newName(getSimpleNames("pluginResources")));
			}
			cc.arguments().add(ccJPA);

			vdf = ast.newVariableDeclarationFragment();
			vdf.setName(ast.newSimpleName("dataAccess"));
			vdf.setInitializer(cc);
			vds = ast.newVariableDeclarationStatement(vdf);
			vds.setType(ast.newSimpleType(ast.newSimpleName("ADataAccess")));
			methodBlock.statements().add(vds);

			// pluginResources.setModelPackageName("xxxx");
			mi = ast.newMethodInvocation();
			mi.setExpression(ast.newSimpleName("pluginResources"));
			mi.setName(ast.newSimpleName("setModelPackageName"));
			String modelPackageName = scenarioResources.getGenerationScenario().getModelPart().getModelPackageName();
			sl = ast.newStringLiteral();
			sl.setLiteralValue(modelPackageName);
			mi.arguments().add(sl);
			methodBlock.statements().add(ast.newExpressionStatement(mi));

			// pluginResources.setDataAccess(dataAccess);
			mi = ast.newMethodInvocation();
			mi.setExpression(ast.newSimpleName("pluginResources"));
			mi.setName(ast.newSimpleName("setDataAccess"));
			mi.arguments().add(ast.newSimpleName("dataAccess"));
			methodBlock.statements().add(ast.newExpressionStatement(mi));
		}

		for (String importClause : imports) {
			ImportDeclaration importDeclaration = ast.newImportDeclaration();
			importDeclaration.setName(ast.newName(getSimpleNames(importClause)));
			importDeclaration.setOnDemand(false);
			astRoot.imports().add(importDeclaration);
		}

		try {
			Document doc = new Document();
			TextEdit edits = astRoot.rewrite(doc, null);
			edits.apply(doc);
			String sourceCode = doc.get();
			ICompilationUnit compilationUnit = packageFragment.createCompilationUnit(GENERATED_CLASS_NAME.concat(".java"),
					sourceCode, true, null);
			compilationUnit.save(new NullProgressMonitor(), true);
		} catch (BadLocationException | JavaModelException e) {
			logError(e);
		}
	}
}
