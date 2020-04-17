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
package org.adichatz.scenario.impl;

//*********************
//*** C A U T I O N ***
//*********************
//
//this class is dynamically loaded in the application No reference will be found in the application
//

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.generator.wrapper.CustomizationsWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.TabularWrapper;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ItemType;
import org.adichatz.generator.xjc.ListDetailContentProviderType;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupListNavigatorScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class GroupListNavigatorScenario extends GroupNavigatorScenario {

	/**
	 * Instantiates a new group list navigator scenario.
	 * 
	 * @param generationUnit the generation unit
	 */
	public GroupListNavigatorScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.impl.ANavigatorScenario#addQueryItem(org.adichatz.
	 * generator.xjc.ItemType, java.lang.String,
	 * org.adichatz.generator.wrapper.PluginEntityWrapper)
	 */
	@Override
	protected void addQueryItem(ItemType item, String lowerEntityId, PluginEntityWrapper pluginEntity) {
		super.addQueryItem(item, lowerEntityId, pluginEntity);
		removeParam(item, ParamMap.ADI_RESOURCE_URI);
		removeParam(item, ParamMap.CONTENT_PROVIDER);
		addParam(item, ParamMap.ADI_RESOURCE_URI,
				EngineTools.getAdiResourceURI(EngineConstants.JPA_BUNDLE, "query", "ListDetailQueryForm"));
		String treeId = pluginEntityScenario.getTreeId(pluginEntity, GenerationEnum.DETAIL);
		String subPackage = pluginEntityScenario.getSubPackage(pluginEntity, GenerationEnum.DETAIL);
		addParam(item, ParamMap.DETAIL_RESOURCE_URI,
				EngineTools.getAdiResourceURI(scenarioInput.getScenarioResources().getPluginName(), subPackage, treeId));
		addParam(item, ParamMap.ADD_EDITOR_TOOLBAR, "true");
		CustomizationsWrapper customizations = getCustomizations(item);
		TabularWrapper tabular = new TabularWrapper();
		tabular.setId("tableInclude:table");
		tabular.setRefreshAtStart("true");
		tabular.setStatusBarKey("Navigation");
		customizations.getElements().add(tabular);

		ListDetailContentProviderType queryContentProvider = new ListDetailContentProviderType();
		queryContentProvider.setId(ParamMap.CONTENT_PROVIDER);
		treeId = pluginEntityScenario.getTreeId(pluginEntity, GenerationEnum.QUERY);
		subPackage = pluginEntityScenario.getSubPackage(pluginEntity, GenerationEnum.QUERY);
		queryContentProvider.setAdiResourceURI(EngineTools.getAdiResourceURI(pluginKey, subPackage, treeId));
		item.getParams().getParam().add(queryContentProvider);
		addSupplQueryItem(item, lowerEntityId, pluginEntity);
	}

	@Override
	protected NavigatorType getNewNavigator(String adiResourceURI) {
		if (null == adiResourceURI)
			adiResourceURI = "adi://" + scenarioInput.getScenarioResources().getPluginName()
					+ "/groupListNavigator/GroupListNavigatorContent";
		return getNewNavigator("groupListNavigator", "adichatz.list.navigator",
				"bundleclass://org.adichatz.engine.e4/org.adichatz.engine.e4.part.GroupNavigator", adiResourceURI);
	}

	@Override
	protected void addSupplQueryItem(ItemType item, String lowerEntityId, PluginEntityWrapper pluginEntity) {
		addParam(item, ParamMap.TITLE, "#MSG(" + getNavigatorName() + "," + lowerEntityId + "ListDetailEditorTitle)");
		addParam(item, ParamMap.TOOL_TIP_TEXT, "#MSG(" + getNavigatorName() + "," + lowerEntityId + "ListDetailEditorToolTipText)");
	}

	@Override
	protected String getId() {
		return "adichatz.group.list.navigator";
	}

	@Override
	protected String getLabel() {
		return "List/Detail Navigator";
	}

}
