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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.OneToOneTypeEnum;
import org.adichatz.jpa.data.AdiOneToOne;
import org.adichatz.jpa.data.AdiOneToOnes;
import org.adichatz.scenario.IPojoRewriter;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterOneToOneRewriter.
 * 
 * delete fields and methods of parent OTO relationship and remove unnecessary imports.<BR>
 * This is needed because LazyFetch is not supported by hibernate so each OTO relationship is loaded when query Parent bean.
 *
 * @author Yves Drumbonnet
 */
public class MasterOneToOneRewriter extends ARewriter implements IPojoRewriter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPojoRewriter#generate(org.adichatz.scenario.ScenarioResources,
	 * org.adichatz.generator.wrapper.GenerationScenarioWrapper, org.adichatz.generator.wrapper.PluginEntityWrapper,
	 * java.lang.String)
	 */
	@Override
	public void generate(ScenarioResources scenarioResources, GenerationScenarioWrapper generationScenario,
			Map<String, Object> params) throws IOException, CoreException {
		String beanClassName = (String) params.get(BEAN_CLASS_NAME);
		PluginEntityWrapper pluginEntity = (PluginEntityWrapper) params.get(PLUGIN_ENTITY);
		ICompilationUnit compilationUnit = scenarioResources.getJavaProject().findType(beanClassName).getCompilationUnit();
		Document document = new Document(compilationUnit.getSource());
		compilationUnit.close();
		astRoot = getAstRoot(compilationUnit);
		Object root = astRoot.types().get(0);
		if (root instanceof TypeDeclaration) {
			boolean removeCascadeType = true;
			TypeDeclaration typeDeclaration = (TypeDeclaration) root;
			ast = astRoot.getAST();
			EntityTreeWrapper entityTree = pluginEntity.getEntityTree();
			Set<String> currentFieldOTOs = new HashSet<>();
			boolean change = false;

			List<OneToOneType> oneToOnes = new ArrayList<>();
			for (OneToOneType oneToOne : entityTree.getOneToOne())
				if (OneToOneTypeEnum.MASTER == oneToOne.getOneToOneType())
					oneToOnes.add(oneToOne);
			List<Annotation> otoAnnotationList = GeneratorUtil.getAnnotations(astRoot, "AdiOneToOnes");
			try {
				if (oneToOnes.isEmpty()) {
					if (!otoAnnotationList.isEmpty()) {
						typeDeclaration.modifiers().remove(otoAnnotationList);
						change = true;
					}
				} else {
					NormalAnnotation otoArrayAnnotation;
					MemberValuePair fieldMVP;
					ArrayInitializer otoAI;
					Set<String> otoNames = new HashSet<>();
					if (otoAnnotationList.isEmpty()) {
						otoArrayAnnotation = ast.newNormalAnnotation();
						otoArrayAnnotation.setTypeName(ast.newSimpleName("AdiOneToOnes"));
						typeDeclaration.modifiers().add(0, otoArrayAnnotation);
						addImport(AdiOneToOnes.class.getName());
						fieldMVP = ast.newMemberValuePair();
						fieldMVP.setName(ast.newSimpleName("oneToOnes"));
						otoAI = ast.newArrayInitializer();
						fieldMVP.setValue(otoAI);
						otoArrayAnnotation.values().add(fieldMVP);
						change = true;
					} else {
						otoArrayAnnotation = (NormalAnnotation) otoAnnotationList.get(0);
						fieldMVP = (MemberValuePair) otoArrayAnnotation.values().get(0);
						otoAI = (ArrayInitializer) fieldMVP.getValue();
						for (Object element : otoAI.expressions()) {
							NormalAnnotation otoAnn = (NormalAnnotation) element;
							for (Object value : otoAnn.values()) {
								if (value instanceof MemberValuePair
										&& "fieldName".equals(((MemberValuePair) value).getName().getFullyQualifiedName()))
									otoNames.add(((StringLiteral) ((MemberValuePair) value).getValue()).getLiteralValue());
							}
						}
					}
					for (OneToOneType oneToOne : oneToOnes) {
						String fieldName = oneToOne.getId();
						MPBVisitor visitor = new MPBVisitor(fieldName);
						astRoot.accept(visitor);
						FieldDeclaration fd = visitor.getSearchedFieldDeclaration();
						typeDeclaration.bodyDeclarations().remove(fd);
						if (!currentFieldOTOs.contains(fieldName)) {
							change = true;
							String suffix = EngineTools.upperCaseFirstLetter(fieldName);
							removeMethod(typeDeclaration, "get".concat(suffix), 0);
							removeMethod(typeDeclaration, "set".concat(suffix), 1);
						}
						// Add Annotation
						if (!otoNames.contains(oneToOne.getId())) {
							change = true;
							addImport(AdiOneToOne.class.getName());
							NormalAnnotation otoAnnotation = ast.newNormalAnnotation();
							otoAnnotation.setTypeName(ast.newSimpleName("AdiOneToOne"));
							MemberValuePair memberValuePair = ast.newMemberValuePair();
							memberValuePair.setName(memberValuePair.getAST().newSimpleName("fieldName"));
							StringLiteral literal = memberValuePair.getAST().newStringLiteral();
							literal.setLiteralValue(oneToOne.getId());
							memberValuePair.setValue(literal);
							otoAnnotation.values().add(memberValuePair);

							memberValuePair = ast.newMemberValuePair();
							memberValuePair.setName(memberValuePair.getAST().newSimpleName("entityURI"));
							literal = memberValuePair.getAST().newStringLiteral();
							literal.setLiteralValue(oneToOne.getEntityURI());
							memberValuePair.setValue(literal);
							otoAnnotation.values().add(memberValuePair);

							memberValuePair = ast.newMemberValuePair();
							memberValuePair.setName(memberValuePair.getAST().newSimpleName("mappedBy"));
							literal = memberValuePair.getAST().newStringLiteral();
							literal.setLiteralValue(oneToOne.getMappedBy());
							memberValuePair.setValue(literal);
							otoAnnotation.values().add(memberValuePair);

							EntityTreeWrapper etw = scenarioResources.getPluginEntityWrapper(oneToOne.getEntityURI())
									.getEntityTree();
							if (2 > etw.getOneToOne().size() && etw.getManyToMany().isEmpty() && etw.getOneToMany().isEmpty()) {
								removeCascadeType = false;
								addImport(CascadeType.class.getName());
								memberValuePair = ast.newMemberValuePair();
								memberValuePair.setName(memberValuePair.getAST().newSimpleName("cascade"));
								ArrayInitializer memberAI = ast.newArrayInitializer();
								QualifiedName removeCascadeQN = ast.newQualifiedName(ast.newSimpleName("CascadeType"),
										ast.newSimpleName("REMOVE"));
								memberAI.expressions().add(removeCascadeQN);
								memberValuePair.setValue(memberAI);
								otoAnnotation.values().add(memberValuePair);
							}

							otoAI.expressions().add(otoAnnotation);
						}
					}
				}
				if (change) {
					Visitor visitor = new Visitor();
					astRoot.accept(visitor);
					if (visitor.removeOneToOne || (visitor.removeCascadeType && removeCascadeType) || visitor.removeFetchType) {
						Map<String, ImportDeclaration> importDeclarationMap = new HashMap<>();
						List imports = astRoot.imports();
						for (Object o : imports) {
							ImportDeclaration importDeclaration = (ImportDeclaration) o;
							importDeclarationMap.put(importDeclaration.getName().getFullyQualifiedName(), importDeclaration);
						}
						removeImport(visitor, visitor.removeOneToOne, importDeclarationMap, OneToOne.class);
						removeImport(visitor, visitor.removeFetchType, importDeclarationMap, FetchType.class);
						if (removeCascadeType)
							removeImport(visitor, visitor.removeCascadeType, importDeclarationMap, CascadeType.class);
						if (oneToOnes.isEmpty()) {
							removeImport(visitor, true, importDeclarationMap, AdiOneToOne.class);
							removeImport(visitor, true, importDeclarationMap, AdiOneToOnes.class);
						}
					}
					rewrite(compilationUnit, document, astRoot);
					logInfo(getFromGeneratorBundle("generation.rewriter.launch", this.getClass().getSimpleName(), beanClassName));
				} else {
					logInfo(getFromGeneratorBundle("generation.rewriter.no.change", this.getClass().getSimpleName(),
							beanClassName));
				}
			} catch (MalformedTreeException | BadLocationException e) {
				logError(e);
			}
		}

	}

	/**
	 * Removes the import.
	 *
	 * @param visitor
	 *            the visitor
	 * @param remove
	 *            the remove
	 * @param importDeclarationMap
	 *            the import declaration map
	 * @param importClass
	 *            the import class
	 */
	private void removeImport(Visitor visitor, boolean remove, Map<String, ImportDeclaration> importDeclarationMap,
			Class<?> importClass) {
		if (remove) {
			ImportDeclaration importDeclaration = importDeclarationMap.get(importClass.getName());
			if (null != importDeclaration)
				astRoot.imports().remove(importDeclaration);
		}
	}

	/**
	 * Removes the method.
	 *
	 * @param typeDeclaration
	 *            the type declaration
	 * @param methoName
	 *            the metho name
	 * @param paramNb
	 *            the param nb
	 */
	private void removeMethod(TypeDeclaration typeDeclaration, String methoName, int paramNb) {
		MPBVisitor visitor = new MPBVisitor(methoName);
		astRoot.accept(visitor);
		MethodDeclaration md = visitor.getSearchedMethod();
		if (null != md && paramNb == md.parameters().size())
			typeDeclaration.bodyDeclarations().remove(md);

	}

	/**
	 * The Class Visitor.
	 *
	 * @author Yves Drumbonnet
	 */
	class Visitor extends ASTVisitor {

		/** The remove one to one. */
		private boolean removeOneToOne = true;

		/** The remove fetch type. */
		private boolean removeFetchType = true;

		/** The remove cascade type. */
		private boolean removeCascadeType = true;

		/**
		 * Instantiates a new visitor.
		 */
		private Visitor() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MarkerAnnotation)
		 */
		@Override
		public boolean visit(MarkerAnnotation node) {
			if (node.getTypeName().getFullyQualifiedName().equals(OneToOne.class.getName()))
				removeOneToOne = false;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MemberValuePair)
		 */
		@Override
		public boolean visit(MemberValuePair node) {
			Expression expression = node.getValue();
			if (expression instanceof QualifiedName) {
				if (expression.toString().startsWith("FetchType."))
					removeFetchType = false;
				if (expression.toString().startsWith("CascadeType."))
					removeCascadeType = false;
			}
			return true;
		}

		@Override
		public boolean visit(QualifiedName node) {
			if (node.toString().startsWith("CascadeType."))
				removeCascadeType = false;
			return true;
		}
	}

}
