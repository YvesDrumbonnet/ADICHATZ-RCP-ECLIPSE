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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import jakarta.persistence.GeneratedValue;

import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.scenario.IPojoRewriter;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;

// TODO: Auto-generated Javadoc
/**
 * The Class PojoTypeRewriter.
 */
public class PgPojoIdRewriter extends ARewriter implements IPojoRewriter {
	private Connection sqlConnection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPojoRewriter#generate(org.adichatz.scenario.ScenarioResources,
	 * org.adichatz.generator.wrapper.GenerationScenarioWrapper, org.adichatz.generator.wrapper.PluginEntityWrapper,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void generate(ScenarioResources scenarioResources, GenerationScenarioWrapper generationScenario,
			Map<String, Object> params) throws IOException, CoreException {
		String beanClassName = (String) params.get(BEAN_CLASS_NAME);
		String tableName = null;
		String catalogName = null;
		String schemaName = null;
		ICompilationUnit compilationUnit = scenarioResources.getJavaProject().findType(beanClassName).getCompilationUnit();
		Document document = new Document(compilationUnit.getSource());
		compilationUnit.close();
		astRoot = getAstRoot(compilationUnit);
		ast = astRoot.getAST();
		Object root = astRoot.types().get(0);
		if (root instanceof TypeDeclaration) {
			TypeDeclaration classType = (TypeDeclaration) root;
			for (MethodDeclaration method : classType.getMethods()) {
				String columnName = null;
				boolean hasIdAnnotation = false;
				boolean hasGeneratedValueAnnotation = false;
				for (Object modifier : method.modifiers()) {
					if (modifier instanceof Annotation) {
						Annotation annotation = (Annotation) modifier;
						if ("Id".equals(annotation.getTypeName().getFullyQualifiedName()))
							hasIdAnnotation = true;
						if ("GeneratedValue".equals(annotation.getTypeName().getFullyQualifiedName())
								|| "GenericGenerator".equals(annotation.getTypeName().getFullyQualifiedName()))
							hasGeneratedValueAnnotation = true;
						if ("Column".equals(annotation.getTypeName().getFullyQualifiedName())) {
							for (Object member : ((NormalAnnotation) annotation).values())
								if (member instanceof MemberValuePair
										&& "name".equals(((MemberValuePair) member).getName().getIdentifier()))
									columnName = getLiteral(member);
						}
					}
				}
				if (hasIdAnnotation) {
					if (!hasGeneratedValueAnnotation && null != columnName) {
						for (Object modifier : classType.modifiers()) {
							if (modifier instanceof NormalAnnotation
									&& "Table".equals(((NormalAnnotation) modifier).getTypeName().getFullyQualifiedName())) {
								for (Object member : ((NormalAnnotation) modifier).values())
									if (member instanceof MemberValuePair) {
										if ("name".equals(((MemberValuePair) member).getName().getIdentifier()))
											tableName = getLiteral(member);
										else if ("catalog".equals(((MemberValuePair) member).getName().getIdentifier()))
											catalogName = getLiteral(member);
										else if ("schema".equals(((MemberValuePair) member).getName().getIdentifier()))
											schemaName = getLiteral(member);
									}
								break;
							}

						}
						if (null != tableName) {
							try {
								ResultSet columnRs = getConnection(scenarioResources, generationScenario).getMetaData()
										.getColumns(catalogName, schemaName, tableName, columnName);
								if (columnRs.first()) {
									int columnsNumber = columnRs.getMetaData().getColumnCount();
									boolean hasNextval = false;
									for (int i = 1; i < columnsNumber; i++) {
										Object obj = columnRs.getObject(i);
										if (obj instanceof String && ((String) obj).toLowerCase().trim().startsWith("nextval")) {
											hasNextval = true;
											break;
										}
									}
									if (hasNextval) {
										NormalAnnotation genneratedValueAnnotation = ast.newNormalAnnotation();
										addImport(GeneratedValue.class.getName());
										ImportDeclaration importDeclaration = addImport(
												"javax.persistence.GenerationType.IDENTITY");
										importDeclaration.setStatic(true);
										genneratedValueAnnotation.setTypeName(ast.newSimpleName("GeneratedValue"));
										MemberValuePair member = ast.newMemberValuePair();
										member.setName(ast.newSimpleName("strategy"));
										member.setValue(ast.newSimpleName("IDENTITY"));
										genneratedValueAnnotation.values().add(member);
										method.modifiers().add(1, genneratedValueAnnotation);
										rewrite(compilationUnit, document, astRoot);
									}
								}
							} catch (SQLException | MalformedTreeException | BadLocationException e) {
								logError(e);
							}
						}
					}
					break;
				}
			}
		}
	}

	private Connection getConnection(ScenarioResources scenarioResources, GenerationScenarioWrapper generationScenario) {
		if (null == sqlConnection) {
			String datasourceId = generationScenario.getModelPart().getConnectorDataSource();
			ConnectorTreeWrapper connectorTree = scenarioResources.getConnectorTree();
			Properties properties = ConnectionUtil.getProperties(connectorTree.getDatasource(datasourceId), true);
			try {
				sqlConnection = ConnectionUtil.getConnection(properties.getProperty(ISqlConnection.JDBC_DRIVER_JAR))
						.getConnection(properties);
			} catch (Exception e) {
				logError(e);
			}
		}
		return sqlConnection;
	}

	private String getLiteral(Object member) {
		return ((StringLiteral) ((MemberValuePair) member).getValue()).getLiteralValue();
	}
}
