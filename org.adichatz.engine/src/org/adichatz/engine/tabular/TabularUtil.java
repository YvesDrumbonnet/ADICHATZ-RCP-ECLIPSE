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
package org.adichatz.engine.tabular;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.engine.xjc.TableComponentType;

// TODO: Auto-generated Javadoc
/**
 * The Class TabularUtil.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TabularUtil {

	/** The THIS. */
	private static TabularUtil THIS;

	/**
	 * Gets the single instance of TabularUtil.
	 * 
	 * @return single instance of TabularUtil
	 */
	public static TabularUtil getInstance() {
		if (null == THIS) {
			THIS = new TabularUtil();
			/*
			 * Add pagination from all Adichatz Plugin Resources.
			 * 
			 * Process Application Plugin Resources to the end to override other configuration. Put a null class name for remove a
			 * pagination defined in other plugin
			 */
			THIS.tableStatusBarMap = new HashMap<String, TableComponentType>();
			THIS.tableRendererMap = new HashMap<String, List<TableComponentType>>();
			AdiPluginResources applicationPR = AdichatzApplication.getInstance().getApplicationPluginResources();

			for (AdiPluginResources pluginResources : AdichatzApplication.getInstance().getPluginMap().values()) {
				if (!pluginResources.equals(applicationPR))
					THIS.addTableComponent(pluginResources);
			}
			if (null != applicationPR) {
				THIS.addTableComponent(applicationPR);
			}

			THIS.tableComponents = new ArrayList<TableComponentType>();
			Collection<TableComponentType> tableStatusBars = THIS.getTableComponentMap().values();
			for (TableComponentType statusBar : tableStatusBars)
				THIS.tableComponents.add(statusBar);
			Collections.sort(THIS.tableComponents, new Comparator<TableComponentType>() {

				@Override
				public int compare(TableComponentType arg0, TableComponentType arg1) {
					return arg0.getRank() - arg1.getRank();
				}
			});

			THIS.tableRenderers = new ArrayList<TableComponentType>();
			Collection<List<TableComponentType>> tableComponentLists = THIS.getTableRendererMap().values();
			for (List<TableComponentType> tableRendererList : tableComponentLists)
				THIS.tableRenderers.addAll(tableRendererList);
		}
		return THIS;
	}

	/**
	 * Adds the status bar.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 */
	private void addTableComponent(AdiPluginResources pluginResources) {
		AdichatzRcpConfigTree configTree = (AdichatzRcpConfigTree) pluginResources.getConfigTree("AdichatzRcpConfig.xml", true);
		if (null != configTree && null != configTree.getRcpConfiguration()) {
			if (null != configTree.getRcpConfiguration().getTableStatusBars()) {
				boolean first = true;
				for (TableComponentType tableComponent : configTree.getRcpConfiguration().getTableStatusBars()
						.getTableStatusBar()) {
					if (first) {
						defaultTableComponent = tableComponent;
						first = false;
					}
					if (EngineTools.isEmpty(tableComponent.getClassName()))
						tableStatusBarMap.remove(tableComponent.getId());
					else {
						tableComponent.setText(
								pluginResources.getMessage(tableComponent.getResourceBundleKey(), tableComponent.getText()));
						tableStatusBarMap.put(tableComponent.getId(), tableComponent);
						if (tableComponent.isDefault())
							defaultTableComponent = tableComponent;
					}
				}
			}
			if (null != configTree.getRcpConfiguration().getTableRenderers()) {
				for (TableComponentType tableRenderer : configTree.getRcpConfiguration().getTableRenderers().getTableRenderer()) {
					if (EngineTools.isEmpty(tableRenderer.getClassName()))
						tableRendererMap.remove(tableRenderer.getId());
					else {
						tableRenderer
								.setText(pluginResources.getMessage(tableRenderer.getResourceBundleKey(), tableRenderer.getText()));
						List<TableComponentType> tableRendererList = tableRendererMap.get(tableRenderer.getId());
						if (null == tableRendererList) {
							tableRendererList = new ArrayList<TableComponentType>();
							tableRendererMap.put(tableRenderer.getId(), tableRendererList);
						}
						tableRendererList.add(tableRenderer);
					}
				}
			}
		}
	}

	/**
	 * *************************************************************************************************************** status bar
	 * ***************************************************************************************************************.
	 */
	private Map<String, TableComponentType> tableStatusBarMap;

	/** The status bars. */
	private List<TableComponentType> tableComponents;

	/** The default status bar. */
	private TableComponentType defaultTableComponent;

	/**
	 * Gets the status bar map.
	 * 
	 * @return the status bar map
	 */
	public Map<String, TableComponentType> getTableComponentMap() {
		return tableStatusBarMap;
	}

	/**
	 * Gets the status bars.
	 * 
	 * @return the status bars
	 */
	public List<TableComponentType> getStatusBars() {
		return tableComponents;
	}

	/**
	 * Gets the default status bar.
	 * 
	 * @return the default status bar
	 */
	public TableComponentType getDefaultStatusBar() {
		return defaultTableComponent;
	}

	/**
	 * Gets the status bar.
	 * 
	 * @param statusBarKey
	 *            the status bar key
	 * @return the status bar
	 */
	public TableComponentType getStatusBar(String statusBarKey) {
		return tableStatusBarMap.get(statusBarKey);
	}

	/**
	 * ***************************************************************************************************************
	 * TABLE_RENDERER
	 * ***************************************************************************************************************.
	 */

	private Map<String, List<TableComponentType>> tableRendererMap;

	/** The table renders. */
	private List<TableComponentType> tableRenderers;

	/**
	 * Gets the table renderer map.
	 * 
	 * @return the table renderer map
	 */
	private Map<String, List<TableComponentType>> getTableRendererMap() {
		return tableRendererMap;
	}

	/**
	 * Gets the table renderer.
	 * 
	 * @param tabularController
	 *            the tabular controller
	 * @param rendererKey
	 *            the renderer key
	 * @return the table renderer
	 */
	public TableComponentType getTableRenderer(ATabularController<?> tabularController, String rendererKey) {
		List<TableComponentType> tableRendererList = getTableRendererMap().get(rendererKey);
		if (null != tableRendererList)
			for (TableComponentType tableRenderer : tableRendererList) {
				if (isTabularCompatible(tableRenderer, tabularController))
					return tableRenderer;
			}
		return null;
	}

	/**
	 * Gets the tabular renderers.
	 * 
	 * @param tabularController
	 *            the tabular controller
	 * @param onlyDefault
	 *            the only default
	 * @return the tabular renderers
	 */
	public List<TableComponentType> getTabularRenderers(ATabularController<?> tabularController, boolean onlyDefault) {
		List<TableComponentType> tableRendererList = new ArrayList<TableComponentType>();
		for (TableComponentType tableComponent : tableRenderers) {
			if ((!onlyDefault || tableComponent.isDefault()) && isTabularCompatible(tableComponent, tabularController))
				tableRendererList.add(tableComponent);
		}
		return tableRendererList;
	}

	/**
	 * Checks if is tabular compatible.
	 * 
	 * @param tableRenderer
	 *            the table renderer
	 * @param tabularController
	 *            the tabular controller
	 * @return true, if is tabular compatible
	 */
	private boolean isTabularCompatible(TableComponentType tableRenderer, ATabularController<?> tabularController) {
		if (tableRenderer.isRequireBindingService() && null == tabularController.getBindingService())
			return false;
		return (EngineTools.isEmpty(tableRenderer.getTabularClassName()) || ReflectionTools
				.hasSuperClass(tabularController.getClass(), ReflectionTools.getClazz(tableRenderer.getTabularClassName())));
	}
}
