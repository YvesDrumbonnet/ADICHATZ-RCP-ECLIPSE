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
package org.adichatz.engine.controller.menu;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.utils.INavigator;
import org.adichatz.engine.core.ARootMenuCore;
import org.adichatz.engine.xjc.MenuPathType;

// TODO: Auto-generated Javadoc
/**
 * The Class NavigatorPath.
 */
public class NavigatorPath {

	/** The menu paths. */
	private List<MenuPathType> menuPaths;

	/** The root controller. */
	private MenuController rootController;

	/**
	 * Instantiates a new navigator path.
	 * 
	 * @param menuPaths
	 *            the menu paths
	 */
	public NavigatorPath(List<MenuPathType> menuPaths) {
		this.menuPaths = menuPaths;
	}

	/**
	 * Creates the menu.
	 *
	 * @param eclipseContext
	 *            the eclipse context
	 * @param navigator
	 *            the navigator
	 * @return the menu controller
	 */
	public MenuController createMenu(Object eclipseContext, INavigator navigator) {
		rootController = new MenuController(AdichatzApplication.getInstance().getApplicationPluginResources(), null, "#rootMenu#",
				null, null);
		boolean first = true;
		for (MenuPathType menuPath : menuPaths) {
			try {
				ARootMenuCore rootMenuCore;
				if (menuPath.getAdiResourceURI().startsWith("bundleclass://")) {
					Class<?> navigatorContentClass = ReflectionTools.getClassFromURI(menuPath.getAdiResourceURI());
					if (menuPath.isUniqueInstance()) {
						try {
							rootMenuCore = (ARootMenuCore) navigatorContentClass.getMethod("getInstance").invoke(null);
						} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
								| SecurityException e) {
							logError("no static 'getInstance( method for " + menuPath.getAdiResourceURI() + " URI!");
							rootMenuCore = null;
						}
						if (null == rootMenuCore)
							rootMenuCore = (ARootMenuCore) navigatorContentClass
									.getConstructor(new Class[] { MenuController.class })
									.newInstance(new Object[] { rootController });
					} else
						rootMenuCore = (ARootMenuCore) navigatorContentClass.getConstructor(new Class[] { MenuController.class })
								.newInstance(new Object[] { rootController });
				} else
					rootMenuCore = (ARootMenuCore) AdichatzApplication.prepareAndInstantiateClass(menuPath.getAdiResourceURI(),
							new Class[] { MenuController.class }, new Object[] { rootController });
				if (first) {
					first = false;
					navigator.setMenuCore(rootMenuCore);
				}
				rootMenuCore.setEclipseContext(eclipseContext);
				rootMenuCore.createChildren();
				addMenu(menuPath.getMenuPathURI(), rootMenuCore.getMenuController());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				logError(e);
			}
		}
		return rootController;
	}

	/**
	 * Adds the menu.
	 * 
	 * @param menuURI
	 *            the menu uri
	 * @param menuController
	 *            the menu controller
	 */
	private void addMenu(String menuURI, MenuController menuController) {
		MenuController parentMenuController = null;
		if (EngineTools.isEmpty(menuURI))
			parentMenuController = rootController;
		else {
			String[] instanceKeys = EngineTools.getInstanceKeys(menuURI);
			if (null == instanceKeys[0]) {
				logWarning(getFromEngineBundle("menuPath.invalid.URI", menuURI));
				return;
			}
			parentMenuController = findChildMenu(instanceKeys[0], instanceKeys[2], rootController);
		}
		if (null != parentMenuController) {
			parentMenuController.getChildren().addAll(menuController.getChildren());
		} else
			logWarning(getFromEngineBundle("menuPath.invalid.URI", menuURI));

	}

	/**
	 * Find child menu.
	 * 
	 * @param pluginId
	 *            the plugin id
	 * @param menuId
	 *            the menu id
	 * @param parentController
	 *            the parent controller
	 * @return the menu controller
	 */
	private MenuController findChildMenu(String pluginId, String menuId, MenuController parentController) {
		List<MenuController> childMenuControllers = new ArrayList<MenuController>();
		for (ANodeController nodeController : parentController.getChildren()) {
			if (nodeController instanceof MenuController) {
				if (pluginId.equals(nodeController.getPluginResources().getPluginName())
						&& menuId.equals(nodeController.getNodeId()))
					return (MenuController) nodeController;
				childMenuControllers.add((MenuController) nodeController);
			}
		}
		for (MenuController menuController : childMenuControllers) {
			MenuController childController = findChildMenu(pluginId, menuId, menuController);
			if (null != childController)
				return childController;
		}
		return null;
	}

	/**
	 * Gets the root controller.
	 *
	 * @return the root controller
	 */
	public MenuController getRootController() {
		return rootController;
	}

	/**
	 * Gets the menu paths.
	 *
	 * @return the menu paths
	 */
	public List<MenuPathType> getMenuPaths() {
		return menuPaths;
	}
}
