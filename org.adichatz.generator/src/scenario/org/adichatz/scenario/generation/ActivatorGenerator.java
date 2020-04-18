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

import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.IBeanInterceptorFactory;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

// TODO: Auto-generated Javadoc
/**
 * The Class AdichatzPluginGenerator.
 */
@SuppressWarnings({ "unchecked" })
public class ActivatorGenerator extends ACompilationUnitGenerator {

	/** The Activator class name. */
	private String ACTIVATOR_CLASS_NAME = "Activator";

	/**
	 * Generate activator.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public void generateCU(ScenarioResources scenarioResources) {
		try {
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
			packageDeclaration.setName(ast.newName(ACompilationUnitGenerator.getSimpleNames(scenarioResources.getPluginPackage())));

			Set<String> imports = new HashSet<String>();
			imports.add(BundleActivator.class.getName());
			imports.add(AdiPluginResources.class.getName());
			imports.add(BundleContext.class.getName());
			if (scenarioResources.hasModelPart()) {
				imports.add(ADataAccess.class.getName());
				imports.add(JPADataAccess.class.getName());
				imports.add(IBeanInterceptorFactory.class.getName());
			}

			TypeDeclaration classType = ast.newTypeDeclaration();
			String supplementedComment = null;
			if (scenarioResources.hasModelPart() || scenarioResources.hasRcpPart()) {
				if (scenarioResources.hasModelPart() && scenarioResources.hasRcpPart())
					supplementedComment = "Activator assumes model and RCP aspect.";
				else if (scenarioResources.hasModelPart())
					supplementedComment = "Activator assumes model aspect.";
				else
					supplementedComment = "Activator assumes RCP aspect.";
			}

			classType.setJavadoc(addConstructorComment(ast, ACTIVATOR_CLASS_NAME, supplementedComment, true));
			classType.setInterface(false);
			classType.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
			classType.setName(ast.newSimpleName(ACTIVATOR_CLASS_NAME));
			classType.superInterfaceTypes().add(ast.newSimpleType(ast.newSimpleName(BundleActivator.class.getSimpleName())));
			astRoot.types().add(classType);

			SingleVariableDeclaration contextVD = ast.newSingleVariableDeclaration();
			contextVD = ast.newSingleVariableDeclaration();
			contextVD.setType(ast.newSimpleType(ast.newSimpleName("BundleContext")));
			contextVD.setName(ast.newSimpleName("context"));

			Block methodBlock = addActivatorMethod(ast, imports, classType, "start");

			ClassInstanceCreation cc;
			StringLiteral sl;
			VariableDeclarationStatement vds;
			VariableDeclarationFragment vdf;
			MethodInvocation mi;

			cc = ast.newClassInstanceCreation();
			cc.setType(ast.newSimpleType(ast.newSimpleName("AdiPluginResources")));

			sl = ast.newStringLiteral();
			sl.setLiteralValue(scenarioResources.getPluginName());
			cc.arguments().add(sl);

			sl = ast.newStringLiteral();
			sl.setLiteralValue(scenarioResources.getPluginPackage());
			cc.arguments().add(sl);

			String gencodePackage = scenarioResources.getParam(IScenarioConstants.GENCODE_PACKAGE);
			if (null == gencodePackage)
				cc.arguments().add(ast.newNullLiteral());
			else {
				sl = ast.newStringLiteral();
				sl.setLiteralValue(null == gencodePackage ? "gencode" : gencodePackage);
				cc.arguments().add(sl);
			}

			MethodInvocation mi2;
			mi = ast.newMethodInvocation();
			mi.setName(ast.newSimpleName("getClass"));
			mi2 = ast.newMethodInvocation();
			mi2.setExpression(mi);
			mi2.setName(ast.newSimpleName("getClassLoader"));
			cc.arguments().add(mi2);

			if (scenarioResources.hasRcpPart() || scenarioResources.hasModelPart()) {
				// AdiPluginResources pluginResources = new AdiPluginResources("${project}", "${package}",
				// getClass().getClassLoader());
				vdf = ast.newVariableDeclarationFragment();
				vdf.setName(ast.newSimpleName("pluginResources"));
				vds = ast.newVariableDeclarationStatement(vdf);
				methodBlock.statements().add(vds);
				vds.setType(ast.newSimpleType(ast.newSimpleName("AdiPluginResources")));
				vdf.setInitializer(cc);
			} else {
				// new AdiPluginResources("${project}", "${package}", getClass().getClassLoader());
				methodBlock.statements().add(ast.newExpressionStatement(cc));
			}

			if (scenarioResources.isApplication()) {
				// new E4AdichatzApplication(AdichatzApplication.getInstance(), pluginResources);
				imports.add(E4AdichatzApplication.class.getName());
				imports.add(AdichatzApplication.class.getName());
				cc = ast.newClassInstanceCreation();
				cc.setType(ast.newSimpleType(ast.newSimpleName("E4AdichatzApplication")));
				mi = ast.newMethodInvocation();
				mi.setExpression(ast.newSimpleName("AdichatzApplication"));
				mi.setName(ast.newSimpleName("getInstance"));
				cc.arguments().add(mi);
				cc.arguments().add(ast.newName(getSimpleNames("pluginResources")));
				methodBlock.statements().add(ast.newExpressionStatement(cc));
			}

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

				String modelPackageName = scenarioResources.getGenerationScenario().getModelPart().getModelPackageName();
				if (null != modelPackageName) {
					// pluginResources.setModelPackageName("xxxx");
					mi = ast.newMethodInvocation();
					mi.setExpression(ast.newSimpleName("pluginResources"));
					mi.setName(ast.newSimpleName("setModelPackageName"));
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
			} else if (scenarioResources.hasRcpPart()) {
				// pluginResources.loadPluginEntities();
				mi = ast.newMethodInvocation();
				mi.setExpression(ast.newSimpleName("pluginResources"));
				mi.setName(ast.newSimpleName("loadPluginEntities"));
				methodBlock.statements().add(ast.newExpressionStatement(mi));
			}

			addActivatorMethod(ast, imports, classType, "stop");

			for (String importClause : imports) {
				ImportDeclaration importDeclaration = ast.newImportDeclaration();
				importDeclaration.setName(ast.newName(getSimpleNames(importClause)));
				importDeclaration.setOnDemand(false);
				astRoot.imports().add(importDeclaration);
			}

			Document doc = new Document();
			TextEdit edits = astRoot.rewrite(doc, null);
			edits.apply(doc);
			String sourceCode = doc.get();
			ICompilationUnit compilationUnit = packageFragment.createCompilationUnit(ACTIVATOR_CLASS_NAME.concat(".java"),
					sourceCode, true, null);
			compilationUnit.save(new NullProgressMonitor(), true);
			logInfo(getFromGeneratorBundle("generation.activator.generated"));

		} catch (Exception e) {
			logError(e);
		}
	}

	/**
	 * Adds the activator method.
	 *
	 * @param ast
	 *            the ast
	 * @param imports
	 *            the imports
	 * @param classType
	 *            the class type
	 * @param methodName
	 *            the method name
	 * @return the block
	 */
	private Block addActivatorMethod(AST ast, Set<String> imports, TypeDeclaration classType, String methodName) {
		SingleVariableDeclaration contextVD;
		MethodDeclaration methodDeclaration = ast.newMethodDeclaration();

		NormalAnnotation override = ast.newNormalAnnotation();
		override.setTypeName(ast.newSimpleName("Override"));
		imports.add(Override.class.getName());

		methodDeclaration.setConstructor(false);
		methodDeclaration.modifiers().add(override);
		methodDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
		methodDeclaration.setName(ast.newSimpleName(methodName));
		classType.bodyDeclarations().add(methodDeclaration);
		methodDeclaration.thrownExceptionTypes().add(ast.newSimpleType(ast.newSimpleName("Exception")));
		contextVD = ast.newSingleVariableDeclaration();
		contextVD.setType(ast.newSimpleType(ast.newSimpleName("BundleContext")));
		contextVD.setName(ast.newSimpleName("context"));
		methodDeclaration.parameters().add(contextVD);

		Block methodBlock = ast.newBlock();
		methodDeclaration.setBody(methodBlock);
		return methodBlock;
	}
}
