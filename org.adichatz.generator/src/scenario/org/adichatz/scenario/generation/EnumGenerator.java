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
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.util.StringTokenizer;

import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

// TODO: Auto-generated Javadoc
/**
 * The Class AdichatzPluginGenerator.
 */

@SuppressWarnings({ "unchecked" })
public class EnumGenerator extends ACompilationUnitGenerator {

	/** The Enum name. */
	private String enumName;

	@Override
	public void generateCU(ScenarioResources scenarioResources) {
	}

	/**
	 * Generate enum type.
	 * 
	 * Valid format examples:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public String generate(ScenarioResources scenarioResources, String pojoType) {
		if (!pojoType.startsWith("#ENUM(") || !pojoType.endsWith(")"))
			throw new RuntimeException(getFromGeneratorBundle("generation.invalid.enum.format", pojoType));
		StringBuffer pojoTypeSB = new StringBuffer(pojoType);
		pojoTypeSB.deleteCharAt(pojoType.length() - 1);
		pojoTypeSB.delete(0, 6);
		int startValue = pojoTypeSB.indexOf("{");
		int endValue = pojoTypeSB.indexOf("}");
		if (-1 == startValue || -1 == endValue)
			throw new RuntimeException(getFromGeneratorBundle("generation.invalid.enum.format", pojoType));
		String valuesStr = pojoTypeSB.substring(startValue + 1, endValue);
		String enumStr = pojoTypeSB.substring(0, startValue).toString().trim();
		if (!enumStr.endsWith(","))
			throw new RuntimeException(getFromGeneratorBundle("generation.invalid.enum.format", pojoType));

		String[] values = valuesStr.split(",");
		String[] params = enumStr.split(",");

		enumName = params[0].trim();
		String modelPackageName = scenarioResources.getGenerationScenario().getModelPart().getModelPackageName();
		try {

			IFolder srcFolder = scenarioResources.getProject().getFolder("src");
			IPackageFragmentRoot srcFragment = scenarioResources.getJavaProject().getPackageFragmentRoot(srcFolder);
			IPackageFragment packageFragment = srcFragment.getPackageFragment(modelPackageName);
			ICompilationUnit compilationUnit = packageFragment.getCompilationUnit(enumName.concat(".java"));
			if (compilationUnit.exists()) {
				logWarning(getFromGeneratorBundle("generation.enum.exists", enumName));
				return modelPackageName.concat(".").concat(enumName);
			}

			ASTParser parser = ASTParser.newParser(AST.JLS9);
			parser.setSource("".toCharArray());

			CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);
			astRoot.recordModifications();
			AST ast = astRoot.getAST();

			// Package: name and javadoc
			PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
			astRoot.setPackage(packageDeclaration);
			StringTokenizer tokenizer = new StringTokenizer(modelPackageName, ".");
			int countTokens = tokenizer.countTokens();
			String[] names = new String[countTokens];
			int j = 0;
			while (tokenizer.hasMoreTokens())
				names[j++] = tokenizer.nextToken();

			Name name = ast.newName(names);
			packageDeclaration.setName(name);

			// public enum {enumName} {
			EnumDeclaration enumDeclaration = ast.newEnumDeclaration();
			enumDeclaration.setJavadoc(addConstructorComment(ast, enumName, null, false));
			enumDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
			enumDeclaration.setName(ast.newSimpleName(enumName));
			astRoot.types().add(enumDeclaration);

			boolean addArgument = false;
			WorkEnumeration[] workEnumerations = new WorkEnumeration[values.length];
			for (int i = 0; i < values.length; i++) {
				String trimmedValue = values[i].trim();
				startValue = trimmedValue.indexOf('(');
				endValue = trimmedValue.indexOf(')');
				String propertyName;
				if (-1 != startValue && -1 != endValue) {
					propertyName = trimmedValue.substring(0, startValue).trim();
					trimmedValue = trimmedValue.substring(startValue + 1, endValue).trim();
					addArgument = true;
				} else {
					propertyName = GeneratorUtil.getJavaName(trimmedValue, FieldCaseEnum.UPPER_CASE, false);
					if (!propertyName.equals(trimmedValue))
						addArgument = true;
				}
				workEnumerations[i] = new WorkEnumeration(propertyName, trimmedValue);
			}
			for (WorkEnumeration workEnumeration : workEnumerations) {
				EnumConstantDeclaration enumConstantDeclaration = ast.newEnumConstantDeclaration();
				ast.newEnumConstantDeclaration();
				if (addArgument) {
					StringLiteral stringLiteral = ast.newStringLiteral();
					stringLiteral.setLiteralValue(workEnumeration.propertyValue.toString());
					enumConstantDeclaration.arguments().add(stringLiteral);
				}
				enumConstantDeclaration.setName(ast.newSimpleName(workEnumeration.propertyName));
				enumDeclaration.enumConstants().add(enumConstantDeclaration);

			}

			// private String value;
			VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
			vdf.setName(ast.newSimpleName("value"));
			FieldDeclaration fieldDeclaration = ast.newFieldDeclaration(vdf);
			fieldDeclaration.setType(ast.newSimpleType(ast.newSimpleName("String")));
			fieldDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PRIVATE_KEYWORD));
			enumDeclaration.bodyDeclarations().add(fieldDeclaration);

			// private MpaaRatingEnum(String value) {
			MethodDeclaration methodDeclaration = ast.newMethodDeclaration();
			methodDeclaration.setConstructor(true);
			methodDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PRIVATE_KEYWORD));
			methodDeclaration.setName(ast.newSimpleName(enumName));
			enumDeclaration.bodyDeclarations().add(methodDeclaration);
			vdf = ast.newVariableDeclarationFragment();
			vdf.setName(ast.newSimpleName("value"));

			SingleVariableDeclaration variableDeclaration = ast.newSingleVariableDeclaration();
			variableDeclaration = ast.newSingleVariableDeclaration();
			variableDeclaration.setType(ast.newSimpleType(ast.newSimpleName("String")));
			variableDeclaration.setName(ast.newSimpleName("value"));
			methodDeclaration.parameters().add(variableDeclaration);

			Block methodBlock = ast.newBlock();
			methodDeclaration.setBody(methodBlock);

			// this.value = value;
			Assignment assignment = ast.newAssignment();
			assignment.setOperator(Assignment.Operator.ASSIGN);
			methodBlock.statements().add(ast.newExpressionStatement(assignment));

			FieldAccess fieldAccess = ast.newFieldAccess();
			fieldAccess.setExpression(ast.newThisExpression());
			fieldAccess.setName(ast.newSimpleName("value"));
			assignment.setLeftHandSide(fieldAccess);
			assignment.setRightHandSide(ast.newSimpleName("value"));

			// public String getValue() { {
			methodDeclaration = ast.newMethodDeclaration();
			methodDeclaration.setConstructor(false);
			methodDeclaration.setReturnType2(ast.newSimpleType(ast.newSimpleName("String")));
			methodDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
			methodDeclaration.setName(ast.newSimpleName("getValue"));
			enumDeclaration.bodyDeclarations().add(methodDeclaration);
			methodBlock = ast.newBlock();
			methodDeclaration.setBody(methodBlock);

			ReturnStatement returnStatement = ast.newReturnStatement();
			returnStatement.setExpression(ast.newSimpleName("value"));
			methodBlock.statements().add(returnStatement);

			Document doc = new Document();
			TextEdit edits = astRoot.rewrite(doc, null);
			edits.apply(doc);
			String sourceCode = doc.get();
			compilationUnit = packageFragment.createCompilationUnit(enumName.concat(".java"), sourceCode, true, null);
			compilationUnit.save(new NullProgressMonitor(), true);
		} catch (BadLocationException | JavaModelException e) {
			logError(e);
		}
		return modelPackageName.concat(".").concat(enumName);
	}

	private class WorkEnumeration {
		String propertyName;

		String propertyValue;

		public WorkEnumeration(String propertyName, String propertyValue) {
			this.propertyName = propertyName;
			this.propertyValue = propertyValue;
		}
	}
}
