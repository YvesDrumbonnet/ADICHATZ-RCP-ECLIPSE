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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.NavigatorTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.MenuType;
import org.adichatz.generator.xjc.PartImageTypeEnum;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeNavigatorScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TreeNavigatorScenario extends ANavigatorScenario {

	/**
	 * Instantiates a new a tree scenario.
	 * 
	 * @param generationUnit the generation unit
	 */
	public TreeNavigatorScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.scenario.IMenuScenario#createMenuTree()
	 */
	@Override
	public void createNavigatorTree(ScenarioInput scenarioInput, List<PluginEntityType> pluginEntities) {
		this.scenarioInput = scenarioInput;
		NavigatorTreeWrapper menuTree;

		if (scenarioInput.isGenerateXml()) {
			menuTree = new NavigatorTreeWrapper();
			/*
			 * Creates root element
			 */
			menuTree.setId(scenarioInput.getTreeId());
			menuTree.setLabel("#MSG(" + getNavigatorName() + ",root.menu)");

			Collections.sort(pluginEntities, new Comparator<PluginEntityType>() {
				@Override
				public int compare(PluginEntityType o1, PluginEntityType o2) {
					return o1.getEntityURI().compareToIgnoreCase(o2.getEntityURI());
				}
			});
			for (PluginEntityType pluginEntity : pluginEntities)
				if (!((PluginEntityWrapper) pluginEntity).isOneToOne())
					addPluginEntityEntry(menuTree, (PluginEntityWrapper) pluginEntity);
		} else
			menuTree = (NavigatorTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);

		createXmlAndPrepare(scenarioInput.setXmlElement(menuTree));
		addNavigatorInRcpConfig(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.scenario.INavigatorScenario#addPluginEntityEntry(org.adichatz.
	 * generator.xjc.NavigatorTree,
	 * org.adichatz.generator.wrapper.PluginEntityWrapper)
	 */
	@Override
	public void addPluginEntityEntry(MenuType parentMenu, PluginEntityWrapper pluginEntity) {
		String lowerEntityId = EngineTools.lowerCaseFirstLetter(pluginEntity.getEntityId());
		/*
		 * Creates a sub menu
		 */
		MenuType menu = new MenuType();
		menu.setId(lowerEntityId);
		menu.setLabel("#MSG(" + getNavigatorName() + "," + menu.getId() + "Menu)");
		String image = pluginEntity.getIcon(PartImageTypeEnum.MENU);
		menu.setImage("#IMG(" + (null == image ? "IMG_MENU" : image) + ")");
		parentMenu.getMenuOrItem().add(menu);
		if (addAuthorization(pluginEntity, RETRIEVE_ROLE))
			menu.setValid("#AUTHORIZATION(" + pluginEntity.getEntityURI() + ",IEntityConstants.RETRIEVE)");

		/*
		 * Creates item create
		 */
		addItemMenu(menu, lowerEntityId, pluginEntity, PartImageTypeEnum.CREATION_ITEM);

		/*
		 * Creates item query
		 */
		addItemMenu(menu, lowerEntityId, pluginEntity, PartImageTypeEnum.QUERY_ITEM);
	}

	@Override
	protected String getId() {
		return "adichatz.tree.navigator";
	}

	@Override
	protected String getLabel() {
		return "Tree Navigator";
	}

	@Override
	protected NavigatorType getNewNavigator(String adiResourceURI) {
		if (null == adiResourceURI)
			adiResourceURI = "adi://" + scenarioInput.getScenarioResources().getPluginName()
					+ "/treeNavigator/TreeNavigatorContent";
		return getNewNavigator("treeNavigator", "adichatz.tree.TreeNavigator",
				"bundleclass://org.adichatz.engine.e4/org.adichatz.engine.e4.part.GroupNavigator", adiResourceURI);
	}

	protected NavigatorType getNewNavigator() {
		return getNewNavigator("treeNavigator", "adichatz.tree.navigator",
				"bundleclass://org.adichatz.engine.e4/org.adichatz.engine.e4.part.TreeNavigator", "adi://"
						+ scenarioInput.getScenarioResources().getPluginName() + "/treeNavigator/TreeNavigatorContent");
	}

}
