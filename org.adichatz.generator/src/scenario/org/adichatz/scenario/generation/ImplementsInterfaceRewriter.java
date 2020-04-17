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
import java.util.HashMap;
import java.util.Map;

import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.osgi.framework.Constants;

// TODO: Auto-generated Javadoc

/**
 * The Class ImplementsInterfaceRewriter.
 * 
 * Rewriter for regenerate usage of interfaces in Pojo. Each Pojo implements an interface (e.g. Contact implements IOrgContact)
 * 
 * Parameters:
 * <ul>
 * <li>beanClassName: Bean class name</li>
 * <li>interfacePrefix: Prefix used for interface name</li>
 * <li>interfaceSuffix: Suffix used for interface name</li>
 * <li>interfacePackageName: package where to find interfaces</li>
 * <li>generateInterfaces: 'true' if interfaces must be generated</li>
 * </ul>
 * 
 * @author Yves Drumbonnet
 */
public class ImplementsInterfaceRewriter extends AInterfaceRewriter {

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
		interfacePackageName = ScenarioUtil.evalLocation(scenarioResources.getBuffer(),
				(String) params.get("interfacePackageName"));
		interfacePrefix = (String) params.get("interfacePrefix");
		interfaceSuffix = (String) params.get("interfaceSuffix");
		String generateInterfaces = (String) params.get("generateInterfaces");
		if (null == interfacePackageName) {
			logError(getFromGeneratorBundle("generation.rewriter.interfacePackageName.missing", getClass().getName()));
			return;
		}
		javaProject = scenarioResources.getJavaProject();
		try {
			ICompilationUnit compilationUnit = javaProject.findType(beanClassName).getCompilationUnit();
			Document document = new Document(compilationUnit.getSource());
			compilationUnit.close();
			astRoot = getAstRoot(compilationUnit);
			ast = astRoot.getAST();
			classType = (TypeDeclaration) astRoot.types().get(0);
			params.put("classType", classType);

			// Create interface if 'interfacePackageName' param exists
			if (null != generateInterfaces && "true".equals(generateInterfaces.toLowerCase())) {
				new InterfacePojoGenerator().generate(scenarioResources, generationScenario, params);
				//
				ManifestChanger manifestChanger = scenarioResources.getManifestChanger();
				manifestChanger.addAttributeElement(Constants.EXPORT_PACKAGE, interfacePackageName);
				manifestChanger.write();
			}

			SimpleType interfaceType = getNewType(ast, beanClassName.substring(beanClassName.lastIndexOf('.') + 1));
			String interfaceName = interfaceType.getName().getFullyQualifiedName();
			classType.superInterfaceTypes().add(interfaceType);
			for (MethodDeclaration md : classType.getMethods()) {
				boolean embeddedId = null != GeneratorUtil.getAnnotation(md, "EmbeddedId");
				if (null != GeneratorUtil.getAnnotation(md, "ManyToOne") || embeddedId) {
					MethodDeclaration setter = GeneratorUtil.getMethodDeclaration(astRoot,
							"s".concat(md.getName().getIdentifier().substring(1)));
					SingleVariableDeclaration vd = (SingleVariableDeclaration) setter.parameters().get(0);
					SimpleType vdType = (SimpleType) vd.getType();
					vd.setType(getNewType(ast, vdType.getName().getFullyQualifiedName()));
					Assignment assignment = (Assignment) ((ExpressionStatement) setter.getBody().statements().get(0))
							.getExpression();
					SimpleName simpleName = (SimpleName) assignment.getRightHandSide();
					CastExpression castExpression = ast.newCastExpression();
					castExpression.setExpression(ast.newSimpleName(simpleName.getIdentifier()));
					castExpression.setType(vdType);
					assignment.setRightHandSide(castExpression);
					if (embeddedId) {
						Map<String, Object> embeddedParams = new HashMap<>();
						String entityId = ((SimpleType) md.getReturnType2()).getName().getFullyQualifiedName();
						String embeddedClassName = beanClassName.substring(0, beanClassName.lastIndexOf('.') + 1).concat(entityId);
						embeddedParams.put(BEAN_CLASS_NAME, embeddedClassName);
						embeddedParams.put("interfacePackageName", interfacePackageName);
						embeddedParams.put("interfacePrefix", interfacePrefix);
						embeddedParams.put("interfaceSuffix", interfaceSuffix);
						embeddedParams.put("generateInterfaces", "true");
						new ImplementsInterfaceRewriter().generate(scenarioResources, generationScenario, embeddedParams);
					}
				}
			}
			rewrite(compilationUnit, document, astRoot);
			compilationUnit.close();
			compilationUnit.getResource().refreshLocal(IResource.DEPTH_ZERO, null);

			PluginEntityWrapper pluginEntity = (PluginEntityWrapper) params.get(PLUGIN_ENTITY);
			if (null != pluginEntity && null == pluginEntity.getUiBeanClassName()) { // PluginEntity is null for embedded class
				pluginEntity.setUiBeanClassName(interfacePackageName + "." + interfaceName);
				scenarioResources.setDirtyScenarioFile(true);
			}
			logInfo(getFromGeneratorBundle("generation.rewriter.launch", this.getClass().getSimpleName(), beanClassName));
		} catch (JavaModelException | BadLocationException | RuntimeException e) {
			logError(e);
		}
	}
}
