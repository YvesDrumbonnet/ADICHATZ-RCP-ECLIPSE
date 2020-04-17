/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.generator;

import java.io.IOException;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tree.ATreeChild;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.generator.tree.AXjcTreeManager;
import org.adichatz.generator.wrapper.TreeManagerTreeWrapper;
import org.adichatz.generator.xjc.NodeTypeEnum;
import org.adichatz.generator.xjc.TreeElementType;
import org.adichatz.generator.xjc.TreeNodeType;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeManagerGenerator.
 */
public class TreeManagerGenerator extends AClassGenerator {

	/** The table wrapper. */
	private final TreeManagerTreeWrapper treeManagerTree;

	/**
	 * Instantiates a new tree manager generator.
	 * 
	 * @param treeManagerTree
	 *            the query tree
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 */
	public TreeManagerGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput, null);

		this.treeManagerTree = (TreeManagerTreeWrapper) scenarioInput.getXmlElement();

		StringBuffer extendsSB = new StringBuffer(" extends ");
		if (EngineTools.isEmpty(treeManagerTree.getSuperTreeManagerClassName()))
			extendsSB.append(getObjectName(AXjcTreeManager.class));
		else
			extendsSB.append(getObjectName(scenarioInput.getScenarioResources().getGencodePath()
					.getClazz(treeManagerTree.getSuperTreeManagerClassName())));

		createClassFile(scenarioInput, extendsSB.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {

		/*
		 * Generate the constructor
		 */
		classBodyBuffer.append("");
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates the Tree Manager class for XjcTreeManager.");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(AdiPluginResources.class) + " pluginResources) {");
		classBodyBuffer.append("super(pluginResources);");

		APluginEntityTree pluginEntityTree = scenarioInput.getPluginEntityTree();
		for (TreeElementType treeElement : treeManagerTree.getTreeElement()) {
			Class<?> parentBeanclass = pluginEntityTree.getEntityMM(treeElement.getEntityURI()).getBeanClass();
			String treeChildClassName;
			if (EngineTools.isEmpty(treeElement.getTreeChildClassName()))
				treeChildClassName = getObjectName(ATreeChild.class);
			else
				treeChildClassName = getObjectName(
						scenarioInput.getScenarioResources().getGencodePath().getClazz(treeElement.getTreeChildClassName()));
			classBodyBuffer.appendPlus("treeChildMap.put(\"" + treeElement.getEntityURI() + "\", new " + treeChildClassName + "("
					+ getObjectName(parentBeanclass) + ".class) {");

			// getText
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public String getText(" + getObjectName(AXjcTreeElement.class) + " treeElement) {");
			if (null != treeElement.getLabelCode()) {
				CodeGenerationUtil.addCode(classBodyBuffer, treeElement.getLabelCode());
			} else {
				classBodyBuffer
						.append(keyWordGenerator.evalExpression(classBodyBuffer, "return " + treeElement.getLabel(), false, false));
			}
			classBodyBuffer.appendMinus("}");

			// getImage
			if (null != treeElement.getImageCode() || !EngineTools.isEmpty(treeElement.getImage())) {
				classBodyBuffer.append("@Override");
				classBodyBuffer.appendPlus("public " + getObjectName(Image.class) + " getImage("
						+ getObjectName(AXjcTreeElement.class) + " treeElement) {");
				if (null != treeElement.getImageCode()) {
					CodeGenerationUtil.addCode(classBodyBuffer, treeElement.getImageCode());
				} else {
					classBodyBuffer.append(
							keyWordGenerator.evalExpression(classBodyBuffer, "return " + treeElement.getImage(), false, false));
				}
				classBodyBuffer.appendMinus("}");
			}

			// init
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void init() {");
			StringBuffer references = new StringBuffer();
			StringBuffer collections = new StringBuffer();
			for (TreeNodeType treeNode : treeElement.getTreeNode()) {
				if (NodeTypeEnum.REFERENCE == treeNode.getNodeType()) {
					if (0 != references.length())
						references.append(", ");
					references.append(keyWordGenerator.evalExpr2Str(classBodyBuffer, treeNode.getPropertyId(), false));
				} else {
					if (0 != collections.length())
						collections.append(", ");
					collections.append(keyWordGenerator.evalExpr2Str(classBodyBuffer, treeNode.getPropertyId(), false));
				}
			}
			classBodyBuffer.append("refFieldNames =new String[] {" + references + "};");
			classBodyBuffer.append("entitySetNames =new String[] {" + collections + "};");
			classBodyBuffer.appendMinus("}");

			classBodyBuffer.appendMinus("});");
		}
		classBodyBuffer.appendMinus("}");
		CodeGenerationUtil.addAdditionalCode(classBodyBuffer, treeManagerTree.getAdditionalCode());
	}
}
