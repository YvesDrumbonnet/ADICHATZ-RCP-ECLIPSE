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
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WildcardType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

// TODO: Auto-generated Javadoc

/**
 * The Class InterfacePojoRewriter.
 * 
 * Rewriter for regenerate using of interfaces in Pojo. Each Pojo implements an interface (e.g. Contact implements IOrgContact)
 * 
 * Parameters:
 * <ul>
 * <li>beanClassName: Bean class name</li>
 * <li>interfaceClassName: Bean class name</li>
 * <li>interfacePrefix: Prefix used for interface name</li>
 * <li>interfaceSuffix: Suffix used for interface name</li>
 * <li>interfacePackageName: package where to find interfaces</li>
 * </ul>
 * 
 * @author Yves Drumbonnet
 */
public class InterfacePojoGenerator extends AInterfaceRewriter {

	/** The import set. */
	private Set<String> importSet;

	/** The import map. */
	private Map<String, String> importMap;

	/**
	 * Generate implements interface clause and Change Pojo and generate interfaces.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param generationScenario
	 *            the generation scenario
	 * @param params
	 *            the params
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	@Override
	public void generate(ScenarioResources scenarioResources, GenerationScenarioWrapper generationScenario,
			Map<String, Object> params) throws IOException, CoreException {
		beanClassName = (String) params.get(BEAN_CLASS_NAME);
		classType = (TypeDeclaration) params.get("classType");
		interfacePackageName = ScenarioUtil.evalLocation(scenarioResources.getBuffer(),
				(String) params.get("interfacePackageName"));
		interfacePrefix = (String) params.get("interfacePrefix");
		interfaceSuffix = (String) params.get("interfaceSuffix");
		if (null == interfacePackageName) {
			logError(getFromGeneratorBundle("generation.rewriter.interfacePackageName.missing", getClass().getName()));
			return;
		}

		if (null == classType) {
			ICompilationUnit beanCU = javaProject.findType(beanClassName).getCompilationUnit();
			beanCU.close();
			CompilationUnit beanAR = getAstRoot(beanCU);
			classType = (TypeDeclaration) beanAR.types().get(0);
		}

		javaProject = scenarioResources.getJavaProject();
		try {
			// scenarioResources.createFolderIfNotExist("src/".concat(interfacePackageName.replace('.', '/')));
			IPackageFragmentRoot srcFragment = javaProject.getPackageFragmentRoot(javaProject.getProject().getFolder("src"));
			IPackageFragment packageFragment = null;
			try {
				packageFragment = srcFragment.createPackageFragment(interfacePackageName, false, null);
			} catch (JavaModelException e) {
				logError(
						getFromGeneratorBundle("generation.cannot.create.folder", "/src/" + interfacePackageName.replace('.', '/')),
						e);
				return;
			}

			ASTParser parser = ASTParser.newParser(AST.JLS9);
			parser.setSource("".toCharArray());

			astRoot = (CompilationUnit) parser.createAST(null);
			astRoot.recordModifications();
			ast = astRoot.getAST();

			importSet = new HashSet<>();
			importMap = new HashMap<>();
			for (Object import_ : ((CompilationUnit) classType.getParent()).imports()) {
				String importName = ((ImportDeclaration) import_).getName().getFullyQualifiedName();
				importMap.put(importName.substring(importName.lastIndexOf('.') + 1), importName);
			}

			PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
			packageDeclaration.setName(ast.newName(ACompilationUnitGenerator.getSimpleNames(interfacePackageName)));
			packageDeclaration.setJavadoc(ACompilationUnitGenerator.addClassComment(ast));
			astRoot.setPackage(packageDeclaration);

			String interfaceName = getNewName(ast, beanClassName.substring(beanClassName.lastIndexOf('.') + 1));
			TypeDeclaration interfaceType = ast.newTypeDeclaration();
			interfaceType.setInterface(true);
			interfaceType.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
			interfaceType.setName(ast.newSimpleName(interfaceName.substring(interfaceName.lastIndexOf('.') + 1)));
			astRoot.types().add(interfaceType);
			for (MethodDeclaration md : classType.getMethods()) {
				boolean oneToMany = null != GeneratorUtil.getAnnotation(md, "OneToMany")
						|| null != GeneratorUtil.getAnnotation(md, "ManyToMany");
				boolean manyToOne = null != GeneratorUtil.getAnnotation(md, "ManyToOne");
				boolean column = null != GeneratorUtil.getAnnotation(md, "Column");
				boolean embeddedId = null != GeneratorUtil.getAnnotation(md, "EmbeddedId");
				if (oneToMany || manyToOne || column || embeddedId) {
					MethodDeclaration getterMD = ast.newMethodDeclaration();
					MethodDeclaration setterMD = ast.newMethodDeclaration();
					String getterName = md.getName().getIdentifier();
					String fieldName;
					if (getterName.startsWith("is"))
						fieldName = getterName.substring(2);
					else
						fieldName = getterName.substring(3);
					setterMD.setName(ast.newSimpleName("set".concat(fieldName)));
					getterMD.setName(ast.newSimpleName(getterName));
					fieldName = EngineTools.lowerCaseFirstLetter(fieldName);
					Type mdType = md.getReturnType2();
					Type getterType;
					Type setterType = null;
					if (oneToMany) {
						getterType = rewriteInterfaceType(astRoot, ast, (ParameterizedType) mdType);
						// setterType = rewriteType(astRoot, ast, (ParameterizedType) mdType);
					} else if (manyToOne || embeddedId) {
						SimpleType simpleType = (SimpleType) mdType;
						getterType = getNewType(ast, simpleType.getName().getFullyQualifiedName());
						setterType = getNewType(ast, simpleType.getName().getFullyQualifiedName());
					} else {
						if (mdType instanceof SimpleType) {
							String typeName = ((SimpleType) mdType).getName().getFullyQualifiedName();
							addInterfImport(astRoot, ast, typeName);
							getterType = ast.newSimpleType(ast.newName(typeName));
							setterType = ast.newSimpleType(ast.newName(typeName));
						} else if (mdType instanceof ArrayType) {
							Type type = ((ArrayType) mdType).getElementType();
							if (type instanceof PrimitiveType) {
								getterType = ast.newArrayType(ast.newPrimitiveType(PrimitiveType.toCode(type.toString())));
								setterType = ast.newArrayType(ast.newPrimitiveType(PrimitiveType.toCode(type.toString())));
							} else {
								throw new RuntimeException(getFromGeneratorBundle("generation.rewriter.array.type.missing",
										getClass().getName(), interfaceName, mdType));
							}
						} else {
							getterType = ast.newPrimitiveType(PrimitiveType.toCode(mdType.toString()));
							setterType = ast.newPrimitiveType(PrimitiveType.toCode(mdType.toString()));
						}
					}
					getterMD.setReturnType2(getterType);
					getterMD.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
					interfaceType.bodyDeclarations().add(getterMD);
					if (null != setterType) {
						SingleVariableDeclaration valueVD = ast.newSingleVariableDeclaration();
						valueVD.setName(ast.newSimpleName(fieldName));
						setterMD.parameters().add(valueVD);
						valueVD.setType(setterType);
						setterMD.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
						interfaceType.bodyDeclarations().add(setterMD);
					}
				}
			}

			Document doc = new Document();
			TextEdit edits = astRoot.rewrite(doc, null);
			edits.apply(doc);
			String sourceCode = doc.get();
			ICompilationUnit compilationUnit = packageFragment.createCompilationUnit(interfaceName.concat(".java"), sourceCode,
					true, null);
			compilationUnit.save(new NullProgressMonitor(), true);
		} catch (JavaModelException | BadLocationException | RuntimeException e) {
			logError(e);
		}
	}

	/**
	 * Rewrite interface type.
	 *
	 * @param astRoot
	 *            the ast root
	 * @param ast
	 *            the ast
	 * @param parameterizedType
	 *            the parameterized type
	 * @return the parameterized type
	 */
	private ParameterizedType rewriteInterfaceType(CompilationUnit astRoot, AST ast, ParameterizedType parameterizedType) {
		String typeName = ((SimpleType) parameterizedType.getType()).getName().getFullyQualifiedName();
		addInterfImport(astRoot, ast, typeName);
		SimpleType newSimpleType = ast.newSimpleType(ast.newSimpleName(typeName));
		ParameterizedType newParamType = ast.newParameterizedType(newSimpleType);
		SimpleType subType = (SimpleType) parameterizedType.typeArguments().get(0);
		WildcardType wildcard = ast.newWildcardType();
		wildcard.setBound(getNewType(ast, subType.getName().getFullyQualifiedName()));
		newParamType.typeArguments().add(wildcard);
		return newParamType;
	}

	/**
	 * Adds the interf import.
	 *
	 * @param astRoot
	 *            the ast root
	 * @param ast
	 *            the ast
	 * @param typeName
	 *            the type name
	 */
	private void addInterfImport(CompilationUnit astRoot, AST ast, String typeName) {
		String fullName = importMap.get(typeName);
		if (null != fullName) {
			importMap.remove(typeName);
			ACompilationUnitGenerator.addImport(astRoot, ast, importSet, fullName);
		}
	}
}
