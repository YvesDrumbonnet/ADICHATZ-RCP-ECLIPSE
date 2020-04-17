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
package org.adichatz.studio.xjc.scenario;

import java.util.List;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.wrapper.TreeManagerTreeWrapper;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.JointureType;
import org.adichatz.generator.xjc.JointureTypeEnum;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.NodeTypeEnum;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.QueryOpenClauseType;
import org.adichatz.generator.xjc.QueryParameterType;
import org.adichatz.generator.xjc.TreeElementType;
import org.adichatz.generator.xjc.TreeNodeType;
import org.adichatz.scenario.ITreeScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.impl.TreeScenario;
import org.adichatz.studio.db4o.Db4oTools;
import org.adichatz.studio.db4o.model.Element;
import org.adichatz.studio.util.StudioUtil;
import org.adichatz.studio.xjc.data.AXjcTreeChild;
import org.adichatz.studio.xjc.editor.AStudioTreeManager;

// TODO: Auto-generated Javadoc
/**
 * The Class TableScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class XjcTreeScenario extends TreeScenario implements ITreeScenario {
	private APluginEntityTree pluginEntityTree;

	/**
	 * Instantiates a new a XJC tree scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public XjcTreeScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	@Override
	public void createEntityTree(ScenarioInput scenarioInput) {
		pluginEntityTree = scenarioInput.getScenarioResources().getPluginResources().getPluginEntityTree();
		super.createEntityTree(scenarioInput);
	}

	@Override
	protected TreeManagerTreeWrapper addTreeElements(ScenarioInput scenarioInput) {
		TreeManagerTreeWrapper treeManagerTree = super.addTreeElements(scenarioInput);
		treeManagerTree.setSuperTreeManagerClassName(AStudioTreeManager.class.getName());
		for (TreeElementType treeElement : treeManagerTree.getTreeElement()) {
			if ("adi://model/GenerationScenario".equals(treeElement.getEntityURI())) {
				List<TreeNodeType> treeNodes = treeElement.getTreeNode();
				for (TreeNodeType node : treeNodes.toArray(new TreeNodeType[treeNodes.size()])) {
					if (NodeTypeEnum.LIST != node.getNodeType())
						treeNodes.remove(node);
				}
			}
			treeElement.setTreeChildClassName(AXjcTreeChild.class.getName());
		}
		return treeManagerTree;
	}

	@Override
	protected void addLabel(TreeElementType treeElement) {
		PluginEntity pluginEntity = pluginEntityTree.getPluginEntityURIMap().get(treeElement.getEntityURI());
		if ("adi://org.adichatz.studio/model/GenerationUnitMM".equals(treeElement.getEntityURI())) {
			StringBuffer codeSB = new StringBuffer();
			codeSB.append("import ").append(GenerationUnitType.class.getName()).append(END_LINE);
			codeSB.append("import ").append(EngineTools.class.getName()).append(END_LINE);
			codeSB.append("GenerationUnitType  generationUnit = (GenerationUnitType) treeElement.getElement();").append(END_LINE);
			codeSB.append("if (null == generationUnit.getAdiResourceURI())").append(END_LINE);
			codeSB.append("    return concat(\"").append(pluginEntity.getEntityId()).append("\", generationUnit.getType().name());")
					.append(END_LINE);
			codeSB.append("else").append(END_LINE);
			codeSB.append("    try {").append(END_LINE);
			codeSB.append("        return concat(\"").append(pluginEntity.getEntityId()).append(
					"\", EngineTools.getInstanceKeys(generationUnit.getAdiResourceURI())[2].concat(\" - \").concat(generationUnit.getType().name()));")
					.append(END_LINE);
			codeSB.append("    } catch(Exception e) {").append(END_LINE);
			codeSB.append("        return generationUnit.getAdiResourceURI();").append(END_LINE);
			codeSB.append("    }").append(END_LINE);
			treeElement.setLabelCode(codeSB.toString());
		} else if ("adi://org.adichatz.studio/model/ListenerMM".equals(treeElement.getEntityURI())) {
			StringBuffer codeSB = new StringBuffer();
			codeSB.append("import ").append(ListenerType.class.getName()).append(END_LINE);
			codeSB.append("ListenerType  listener = (ListenerType) treeElement.getElement();").append(END_LINE);
			codeSB.append("return \"Listener \".concat(null == listener.getListenerTypes() ? \"\": listener.getListenerTypes());");
			treeElement.setLabelCode(codeSB.toString());
		} else if ("adi://org.adichatz.studio/model/QueryParameterMM".equals(treeElement.getEntityURI())) {
			StringBuffer codeSB = new StringBuffer();
			codeSB.append("import ").append(QueryParameterType.class.getName()).append(END_LINE);
			codeSB.append("QueryParameterType  parameter = (QueryParameterType) treeElement.getElement();").append(END_LINE);
			codeSB.append("return \"").append(pluginEntity.getEntityId())
					.append(": \".concat(null == parameter.getId() ? parameter.getProperty() : parameter.getId());");
			treeElement.setLabelCode(codeSB.toString());
		} else if ("adi://org.adichatz.studio/model/QueryOpenClauseMM".equals(treeElement.getEntityURI())) {
			StringBuffer codeSB = new StringBuffer();
			codeSB.append("import ").append(QueryOpenClauseType.class.getName()).append(END_LINE);
			codeSB.append("QueryOpenClauseType  openClause = (QueryOpenClauseType) treeElement.getElement();").append(END_LINE);
			codeSB.append("return null == openClause.getTitle() ? \"QueryOpenClause\" : \"").append(pluginEntity.getEntityId())
					.append(": \".concat(openClause.getTitle());");
			treeElement.setLabelCode(codeSB.toString());
		} else if ("adi://org.adichatz.studio/model/JointureMM".equals(treeElement.getEntityURI())) {
			StringBuffer codeSB = new StringBuffer();
			codeSB.append("import ").append(JointureType.class.getName()).append(END_LINE);
			codeSB.append("import ").append(JointureTypeEnum.class.getName()).append(END_LINE);
			codeSB.append("JointureType  jointure = (JointureType) treeElement.getElement();").append(END_LINE);
			codeSB.append("JointureTypeEnum  jointureType = jointure.getJointureType();").append(END_LINE);
			codeSB.append("return concatNvl(\"").append(pluginEntity.getEntityId()).append(
					":\", null == jointureType ? \"?\" : jointureType.value(), jointure.getFieldName(), jointure.getSuffix());");
			treeElement.setLabelCode(codeSB.toString());
		} else {
			String suffixMethodName = null;
			if (ReflectionTools.hasSuperClass(pluginEntity.getEntityMetaModel().getBeanClass(), ElementType.class) //
					|| ReflectionTools.hasSuperClass(pluginEntity.getEntityMetaModel().getBeanClass(), PropertyFieldType.class)
					|| "Param".equals(pluginEntity.getEntityId()) //
					|| "Menu".equals(pluginEntity.getEntityId()) //
					|| "Navigator".equals(pluginEntity.getEntityId()) //
					|| "Item".equals(pluginEntity.getEntityId()) //
					|| "PartInclude".equals(pluginEntity.getEntityId()) //
					|| "OneToOne".equals(pluginEntity.getEntityId()) //
					|| "QueryColumnParameter".equals(pluginEntity.getEntityId()) //
			)
				suffixMethodName = "getId";
			else if ("PluginEntity".equals(pluginEntity.getEntityId()))
				suffixMethodName = "getEntityURI";
			else if ("QueryJPAPart".equals(pluginEntity.getEntityId()))
				suffixMethodName = "getEntityId";
			else if ("Validator".equals(pluginEntity.getEntityId()))
				suffixMethodName = "getKey";
			else if ("Join".equals(pluginEntity.getEntityId()))
				suffixMethodName = "getFieldName";
			if (null != suffixMethodName) {
				StringBuffer codeSB = new StringBuffer();
				codeSB.append("import ").append(pluginEntity.getEntityMetaModel().getBeanClass().getName()).append(END_LINE);
				codeSB.append("return concat(\"").append(pluginEntity.getEntityId()).append("\", ((")
						.append(pluginEntity.getEntityMetaModel().getBeanClass().getSimpleName())
						.append(") treeElement.getElement()).").append(suffixMethodName).append("());").append(END_LINE);
				treeElement.setLabelCode(codeSB.toString());
			} else
				treeElement.setLabel("\"" + pluginEntity.getEntityId() + "\";");
		}
	}

	@Override
	protected void addImage(TreeElementType treeElement) {
		String entityId = EngineTools.getEntityId(EngineTools.getInstanceKeys(treeElement.getEntityURI())[2]);
		Element element = Db4oTools.getInstance().getElement(null, entityId, false);
		if ("adi://org.adichatz.studio/model/CopyResourceMM".equals(treeElement.getEntityURI()))
			treeElement.setImage("#IMG(adi://org.adichatz.studio/./IMG_COPY.png);");
		else if ("adi://org.adichatz.studio/model/RemoveResourceMM".equals(treeElement.getEntityURI()))
			treeElement.setImage("#IMG(adi://org.adichatz.studio/./IMG_DELETE.gif);");
		else if (null != element && null != element.getImage())
			treeElement.setImage("#IMG(" + element.getImage() + ");");
		else {
			String fileName = StudioUtil.getIconFileName(treeElement.getEntityURI());
			if (null != fileName)
				treeElement.setImage("#IMG(" + (-1 == fileName.indexOf('/') ? "./" : "") + fileName + ");");
		}
	}
}
