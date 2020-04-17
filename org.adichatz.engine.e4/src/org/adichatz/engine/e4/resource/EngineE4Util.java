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
package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IController;
import org.adichatz.engine.controller.collection.CompositeBagController;
import org.adichatz.engine.controller.collection.IncludeController;
import org.adichatz.engine.controller.menu.ANodeController;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.controller.menu.NavigatorPath;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.MultiBoundedPart;
import org.adichatz.engine.extra.AdiResourceURI;

// TODO: Auto-generated Javadoc
/**
 * The Class EngineE4Util.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class EngineE4Util {

	/** The Root contribution URI. */
	public static String ROOT_CONTRIBUTION_URI = "bundleclass://".concat(EngineConstants.ENGINE_E4_BUNDLE).concat("/");

	/** The Default contribution URI. */
	public static String DEFAULT_CONTRIBUTION_URI = ROOT_CONTRIBUTION_URI.concat(BoundedPart.class.getName());

	public static String ICON_URI_PREFIX = "platform:/plugin/".concat(EngineConstants.ENGINE_E4_BUNDLE)
			.concat(EngineConstants.ICON_FILES_PATH);

	public static String THEME_ID = "themeid";

	public static String NO_SAVE = "#NO_SAVE#"; // transient data flag for not saving part

	/**
	 * Gets the value from engine bundle.
	 * 
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * @return the from bundle
	 */
	public static String getFromEngineE4Bundle(String key, Object... variables) {
		return AdichatzApplication.getInstance().getMessage(EngineConstants.ENGINE_E4_BUNDLE, "adichatzEngineE4", key, variables);
	}

	/**
	 * Gets the icon uri.
	 * 
	 * @param pluginKey
	 *            the plugin key
	 * @param adiImageURI
	 *            the adi image uri
	 * @return the icon uri
	 */
	public static String getIconURI(String pluginKey, String adiImageURI) {
		String[] instanceKeys = EngineTools.getInstanceKeys(adiImageURI);
		StringBuffer iconURI = new StringBuffer("platform:/plugin/");
		iconURI.append(null == instanceKeys[0] ? pluginKey : instanceKeys[0]);
		iconURI.append(EngineConstants.ICON_FILES_PATH);
		if (null != instanceKeys[1] && !instanceKeys[1].equals("."))
			iconURI.append(instanceKeys[1].replace('.', '/')).append("/");
		iconURI.append(instanceKeys[2]);
		return iconURI.toString();
	}

	/**
	 * Gets the contribution uri.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @param className
	 *            the class name
	 * @return the contribution uri
	 */
	public static String getContributionURI(String pluginName, String className) {
		return "bundleclass://".concat(pluginName).concat("/").concat(className);
	}

	public static void displayRegisterTree(ICollectionController collectionController) {
		currentAdiResourceURI = null;
		displayRegisterTree(collectionController, 0);
		if (collectionController instanceof MultiBoundedPart) {
			System.out.println("\n=== Pages ===");
			for (String pageId : ((MultiBoundedPart) collectionController).getPageManagerMap().keySet())
				System.out.println("\t\t - " + pageId);
		}
		if (collectionController instanceof BoundedPart
				&& null != ((BoundedPart) collectionController).getGenCode().getNavigations()) {
			System.out.println("\n=== Navigations ===");
			for (APartNavigation navigation : ((BoundedPart) collectionController).getGenCode().getNavigations())
				System.out.println("\t\t - " + navigation.getName() + ":\t\t<" + navigation.getPath() + ">");
		}
	}

	private static void displayRegisterTree(ICollectionController collectionController, int level) {
		String label;
		if (collectionController instanceof ACollectionController) {
			label = ((ACollectionController) collectionController).getRegisterId();
		} else if (collectionController instanceof BoundedPart) {
			label = ((BoundedPart) collectionController).getTitle();
		} else {
			logError(getFromEngineE4Bundle("display.tree.invalid.class", collectionController.getClass().getName()));
			return;
		}
		printLabelDisplayTree(collectionController, label, "+ ", level++);
		List<AWidgetController> childControllers = collectionController.getChildControllers();
		if (collectionController instanceof CompositeBagController) {
			childControllers = new ArrayList<AWidgetController>(
					((CompositeBagController) collectionController).getBagSelectionMap().values());
		}

		for (AWidgetController controller : childControllers) {
			if (controller instanceof ICollectionController) {
				String oldCurrentAdiResourceURI = currentAdiResourceURI;
				displayRegisterTree((ICollectionController) controller, level);
				if (null != oldCurrentAdiResourceURI && !oldCurrentAdiResourceURI.equals(currentAdiResourceURI)) {
					currentAdiResourceURI = null;
				}
			} else
				printLabelDisplayTree(controller, controller.getRegisterId(), " - ", level);
		}
	}

	private static String currentAdiResourceURI;

	private static void printLabelDisplayTree(IController controller, String label, String sign, int level) {
		if (null == currentAdiResourceURI && (controller instanceof AWidgetController || controller instanceof BoundedPart)) {
			if (controller instanceof AWidgetController) {
				Class<?> partClass = ((AWidgetController) controller).getGenCode().getContext().getXmlTreeGenCode().getClass();
				AdiResourceURI uriAnnotation = partClass.getAnnotation(AdiResourceURI.class);
				if (null != uriAnnotation) {
					currentAdiResourceURI = uriAnnotation.URI();
				}
			} else {
				currentAdiResourceURI = ((BoundedPart) controller).getGenCode().getAdiResourceURI();
			}
			if (null != currentAdiResourceURI)
				System.out.println("\t=== Current corresponding axml file:\t" + currentAdiResourceURI + " ===");
		}
		StringBuffer lineSB = new StringBuffer();
		for (int i = 0; i < level; i++)
			lineSB.append("\t");
		lineSB.append(sign).append(label).append("\t\t[");
		String className = controller.getClass().getName();
		if (-1 != className.indexOf('$'))
			className = controller.getClass().getSuperclass().getName();
		lineSB.append(className).append("]");
		if (controller instanceof IncludeController)
			currentAdiResourceURI = null;
		System.out.println(lineSB.toString());
	}

	public static void displayNavigatorTree(String navigatorId) {
		NavigatorPath navigatorPath = AdichatzApplication.getInstance().getNavigatorMap().get(navigatorId);
		if (null != navigatorPath) {
			System.out.println(navigatorId);
			for (ANodeController node : navigatorPath.getRootController().getChildren()) {
				printMenuPath(node, 1);
			}
		}
	}

	private static void printMenuPath(ANodeController node, int level) {
		StringBuffer lineSB = new StringBuffer();
		for (int i = 0; i < level; i++)
			lineSB.append("\t");
		lineSB.append(node instanceof ItemController ? "- " : "+ ");
		lineSB.append(node.getNodeId());
		System.out.println(lineSB.toString());
		if (node instanceof MenuController) {
			level++;
			for (ANodeController child : ((MenuController) node).getChildren()) {
				printMenuPath(child, level);
			}
		}
	}
}
