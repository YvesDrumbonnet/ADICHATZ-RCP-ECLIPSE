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

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.scenario.ScenarioResources;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ACompilationUnitGenerator.
 */
public abstract class ACompilationUnitGenerator {

	/**
	 * Adds the import.
	 *
	 * @param astRoot
	 *            the ast root
	 * @param ast
	 *            the ast
	 * @param imports
	 *            the imports
	 * @param className
	 *            the class name
	 * @return the import declaration
	 */
	@SuppressWarnings("unchecked")
	public static ImportDeclaration addImport(CompilationUnit astRoot, AST ast, Set<String> imports, String className) {
		if (!imports.contains(className)) {
			ImportDeclaration importDeclaration = ast.newImportDeclaration();
			importDeclaration.setName(ast.newName(ACompilationUnitGenerator.getSimpleNames(className)));
			importDeclaration.setOnDemand(false);
			astRoot.imports().add(importDeclaration);
			imports.add(className);
			return importDeclaration;
		}
		return null;
	}

	/**
	 * Gets the simple names.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the simple names
	 */
	static public String[] getSimpleNames(String qualifiedName) {
		StringTokenizer st = new StringTokenizer(qualifiedName, ".");
		ArrayList<String> list = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String name = st.nextToken().trim();
			if (!name.equals("*"))
				list.add(name);
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Generate cu.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public abstract void generateCU(ScenarioResources scenarioResources);

	/**
	 * Adds the constructor comment.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param ast
	 *            the ast
	 * @param className
	 *            the class name
	 * @return the javadoc
	 */
	protected Javadoc addConstructorComment(AST ast, String className, String supplementedComment, boolean addCaution) {
		Javadoc javadoc = ast.newJavadoc();
		addLine2Comment(ast, javadoc, "The Class ".concat(className).concat(". (generated by Adichatz Tools on ")
				.concat(new Date().toString().concat(").")));
		if (null != supplementedComment) {
			addLine2Comment(ast, javadoc, "");
			addLine2Comment(ast, javadoc, supplementedComment);
		}
		if (addCaution) {
			addLine2Comment(ast, javadoc, "");
			addLine2Comment(ast, javadoc,
					"CAUTION: Generation process could be relaunched by developper so be careful if you manually change code.");
		}
		return javadoc;
	}

	/**
	 * Adds the class comment.
	 * 
	 * @param ast
	 *            the ast
	 * @return the javadoc
	 */
	public static Javadoc addClassComment(AST ast) {
		Javadoc javadoc = ast.newJavadoc();
		addLine2Comment(ast, javadoc, "Copyright © Adichatz (2007-2020) - www.adichatz.org");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "yves@adichatz.org");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "This software is a computer program whose purpose is to build easily");
		addLine2Comment(ast, javadoc, "Eclipse RCP applications using JPA in a JEE or JSE context.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "This software is governed by the CeCILL license under French law and");
		addLine2Comment(ast, javadoc, "abiding by the rules of distribution of free software.  You can  use,");
		addLine2Comment(ast, javadoc, "modify and/ or redistribute the software under the terms of the CeCILL");
		addLine2Comment(ast, javadoc, "license as circulated by CEA, CNRS and INRIA at the following URL");
		addLine2Comment(ast, javadoc, "\"http://www.cecill.info\".");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "As a counterpart to the access to the source code and  rights to copy,");
		addLine2Comment(ast, javadoc, "modify and redistribute granted by the license, users are provided only");
		addLine2Comment(ast, javadoc, "with a limited warranty  and the software's author,  the holder of the");
		addLine2Comment(ast, javadoc, "economic rights,  and the successive licensors  have only  limited");
		addLine2Comment(ast, javadoc, "liability.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "In this respect, the user's attention is drawn to the risks associated");
		addLine2Comment(ast, javadoc, "with loading,  using,  modifying and/or developing or reproducing the");
		addLine2Comment(ast, javadoc, "software by the user in light of its specific status of free software,");
		addLine2Comment(ast, javadoc, "that may mean  that it is complicated to manipulate,  and  that  also");
		addLine2Comment(ast, javadoc, "therefore means  that it is reserved for developers  and  experienced");
		addLine2Comment(ast, javadoc, "professionals having in-depth computer knowledge. Users are therefore");
		addLine2Comment(ast, javadoc, "encouraged to load and test the software's suitability as regards their");
		addLine2Comment(ast, javadoc, "requirements in conditions enabling the security of their systems and/or");
		addLine2Comment(ast, javadoc, "data to be ensured and,  more generally, to use and operate it in the");
		addLine2Comment(ast, javadoc, "same conditions as regards security.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "The fact that you are presently reading this means that you have had");
		addLine2Comment(ast, javadoc, "knowledge of the CeCILL license and that you accept its terms.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "*******************************************************************************");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "Copyright © Adichatz (2007-2020) - www.adichatz.org");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "yves@adichatz.org");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "Ce logiciel est un programme informatique servant à construire rapidement des");
		addLine2Comment(ast, javadoc, "applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "Ce logiciel est régi par la licence CeCILL soumise au droit français et");
		addLine2Comment(ast, javadoc, "respectant les principes de diffusion des logiciels libres. Vous pouvez");
		addLine2Comment(ast, javadoc, "utiliser, modifier et/ou redistribuer ce programme sous les conditions");
		addLine2Comment(ast, javadoc, "de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA");
		addLine2Comment(ast, javadoc, "sur le site \"http://www.cecill.info\".");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "En contrepartie de l'accessibilité au code source et des droits de copie,");
		addLine2Comment(ast, javadoc, "de modification et de redistribution accordés par cette licence, il n'est");
		addLine2Comment(ast, javadoc, "offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,");
		addLine2Comment(ast, javadoc, "seule une responsabilité restreinte pèse sur l'auteur du programme,  le");
		addLine2Comment(ast, javadoc, "titulaire des droits patrimoniaux et les concédants successifs.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "A cet égard  l'attention de l'utilisateur est attirée sur les risques");
		addLine2Comment(ast, javadoc, "associés au chargement,  à l'utilisation,  à la modification et/ou au");
		addLine2Comment(ast, javadoc, "développement et à la reproduction du logiciel par l'utilisateur étant");
		addLine2Comment(ast, javadoc, "donné sa spécificité de logiciel libre, qui peut le rendre complexe à");
		addLine2Comment(ast, javadoc, "manipuler et qui le réserve donc à des développeurs et des professionnels");
		addLine2Comment(ast, javadoc, "avertis possédant  des  connaissances  informatiques approfondies.  Les");
		addLine2Comment(ast, javadoc, "utilisateurs sont donc invités à charger  et  tester  l'adéquation  du");
		addLine2Comment(ast, javadoc, "logiciel à leurs besoins dans des conditions permettant d'assurer la");
		addLine2Comment(ast, javadoc, "sécurité de leurs systèmes et ou de leurs données et, plus généralement,");
		addLine2Comment(ast, javadoc, "à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.");
		addLine2Comment(ast, javadoc, "");
		addLine2Comment(ast, javadoc, "Le fait que vous puissiez accéder à cet en-tête signifie que vous avez");
		addLine2Comment(ast, javadoc, "pris connaissance de la licence CeCILL, et que vous en avez accepté les");
		addLine2Comment(ast, javadoc, "termes.");
		return javadoc;
	}

	/**
	 * Adds the line2 comment.
	 * 
	 * @param ast
	 *            the ast
	 * @param javadoc
	 *            the javadoc
	 * @param text
	 *            the text
	 */
	@SuppressWarnings({ "unchecked" })
	public static void addLine2Comment(AST ast, Javadoc javadoc, String text) {
		final TextElement txt = ast.newTextElement();
		txt.setText(text);
		final TagElement tag = ast.newTagElement();
		tag.fragments().add(txt);
		javadoc.tags().add(tag);
	}
}
