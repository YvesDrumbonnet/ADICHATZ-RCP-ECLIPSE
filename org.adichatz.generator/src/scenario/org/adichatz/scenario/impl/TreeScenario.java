/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.scenario.impl;

//*********************
//*** C A U T I O N ***
//*********************
//
//this class is dynamically loaded in the application No reference will be found in the application
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.TreeManagerTreeWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.NodeTypeEnum;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.TreeElementType;
import org.adichatz.generator.xjc.TreeNodeType;
import org.adichatz.scenario.ITreeScenario;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class TableScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TreeScenario extends AScenario implements ITreeScenario {

	/**
	 * Instantiates a new tree scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public TreeScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.ITableScenario#createEntityTable(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createEntityTree(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		TreeManagerTreeWrapper treeManagerTree;
		if (scenarioInput.isGenerateXml()) {
			treeManagerTree = addTreeElements(scenarioInput);
		} else
			treeManagerTree = (TreeManagerTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		createXmlAndPrepare(scenarioInput.setXmlElement(treeManagerTree));
	}

	/**
	 * Adds the tree elements.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @return the tree manager tree wrapper
	 */
	protected TreeManagerTreeWrapper addTreeElements(ScenarioInput scenarioInput) {
		TreeManagerTreeWrapper treeManagerTree;
		treeManagerTree = new TreeManagerTreeWrapper();
		GenerationScenarioWrapper generationScenario = scenarioInput.getScenarioResources().getGenerationScenario();
		for (PluginEntityType pluginEntity : generationScenario.getPluginEntity()) {
			TreeElementType treeElement = new TreeElementType();
			treeElement.setEntityURI(pluginEntity.getEntityURI());
			treeManagerTree.getTreeElement().add(treeElement);

			Map<String, TreeNodeType> treeNodeMap = new HashMap<String, TreeNodeType>();
			PluginEntityType parentPluginEntity = pluginEntity;

			scenarioInput.setPluginEntity(pluginEntity.getEntityURI());
			Class<?> beanClass = scenarioInput.getPluginEntityWrapper().getBeanClass();
			while (null != parentPluginEntity) {
				AEntityMetaModel<?> entityMM = scenarioInput.getPluginEntityTree().getEntityMM(parentPluginEntity.getEntityURI());
				for (Map.Entry<String, PropertyField> entry : entityMM.getFieldMap().entrySet()) {
					if (entry.getValue() instanceof RefField) {
						TreeNodeType treeNode = new TreeNodeType();
						treeNode.setPropertyId(entry.getKey());
						treeNode.setNodeType(NodeTypeEnum.REFERENCE);
						treeNodeMap.put(entry.getKey(), treeNode);
					} else if (entry.getValue() instanceof EntitySet) {
						TreeNodeType treeNode = new TreeNodeType();
						treeNode.setPropertyId(entry.getKey());
						treeNode.setNodeType(NodeTypeEnum.LIST);
						treeNodeMap.put(entry.getKey(), treeNode);
					}
				}

				beanClass = beanClass.getSuperclass();
				PluginEntity pe = scenarioInput.getPluginEntityTree().getPluginEntity(beanClass);
				if (null == pe)
					break;
				parentPluginEntity = generationScenario.getPluginEntity(pe.getEntityURI());
			}

			List<TreeNodeType> treeNodes = new ArrayList<TreeNodeType>(treeNodeMap.values());
			Collections.sort(treeNodes, new Comparator<TreeNodeType>() {
				@Override
				public int compare(TreeNodeType o1, TreeNodeType o2) {
					if (o1.getNodeType().equals(o2.getNodeType()))
						return o1.getPropertyId().compareTo(o2.getPropertyId());
					return NodeTypeEnum.REFERENCE == o1.getNodeType() ? -1 : 1;
				}

			});
			treeElement.getTreeNode().addAll(treeNodes);
		}
		for (TreeElementType treeElement : treeManagerTree.getTreeElement()) {
			addBackground(treeElement);
			addForeground(treeElement);
			addFont(treeElement);
			addImage(treeElement);
			addLabel(treeElement);
		}
		return treeManagerTree;
	}

	protected void addLabel(TreeElementType treeElement) {
	}

	protected void addImage(TreeElementType treeElement) {
	}

	protected void addFont(TreeElementType treeElement) {
	}

	protected void addForeground(TreeElementType treeElement) {
	}

	protected void addBackground(TreeElementType treeElement) {
	}
}
