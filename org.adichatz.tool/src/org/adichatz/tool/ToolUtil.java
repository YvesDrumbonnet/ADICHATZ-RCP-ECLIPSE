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
package org.adichatz.tool;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.generator.broadcast.BroadcastUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.tool.cacheManager.CacheManagerPart;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.osgi.framework.Bundle;

public class ToolUtil {
	public static String NAVIGATOR_FILTERS = "prefs.date.navigator_filters";

	public static String ADD_TOOL_NAVIGATOR = "prefs.add.tool.navigator";

	public static String HIDE_EXTERNAL_RESOURCES = "prefs.hide.external.resources";

	public static String CACHE_MANAGER_CONTRIBUTION_URI = "bundleclass://org.adichatz.tool/"
			.concat(CacheManagerPart.class.getName());

	public static String APPL_PARAM_MANAGER_CONTRIBUTION_URI = "bundleclass://org.adichatz.tool/"
			.concat(ApplicationParamManagerPart.class.getName());

	public static String PLUGIN_RESOURCES = "PLUGIN_RESOURCES";

	private static int IDE_COMM_PORT = -1;

	/**
	 * Gets the from tool bundle.
	 * 
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * 
	 * @return the from tool bundle
	 */
	public static String getFromToolBundle(String key, Object... variables) {
		return AdichatzApplication.getInstance().getMessage(GeneratorConstants.TOOL_BUNDLE, "adichatzTool", key, variables);
	}

	public static void openApplicationParamManagerEditor(AdiPluginResources pluginResources, final MApplication application,
			final EModelService modelService, final EPartService partService) {
		openEditor(pluginResources, application, modelService, partService, ToolUtil.APPL_PARAM_MANAGER_CONTRIBUTION_URI,
				"org.adichatz.ui.part.applicationParamManager", "tool.applicationParamManager");
	}

	public static void openCacheManagerEditor(AdiPluginResources pluginResources, final MApplication application,
			final EModelService modelService, final EPartService partService) {
		openEditor(pluginResources, application, modelService, partService, ToolUtil.CACHE_MANAGER_CONTRIBUTION_URI,
				"org.adichatz.ui.part.cacheManager", "tool.cacheManager");
	}

	private static void openEditor(AdiPluginResources pluginResources, final MApplication application,
			final EModelService modelService, final EPartService partService, String contribURI, String elementId, String label) {
		MPart part = MBasicFactory.INSTANCE.createPart();
		MPartStack editorPartStack = (MPartStack) AdichatzApplication.getInstance().getContextValue(EngineE4Util.EDITOR_PARTSTACK);
		for (MStackElement element : editorPartStack.getChildren())
			if (element instanceof MPart && contribURI.equals(((MPart) element).getContributionURI())) {
				part = (MPart) element;
				break;
			}
		if (null == part.getContributionURI()) {
			part.setContributionURI(contribURI);
			part.setElementId(elementId);
			part.setLabel(getFromToolBundle(label));
			part.setTooltip(getFromToolBundle(label));
			part.setCloseable(true);
			part.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
			part.getTags().add(IPresentationEngine.NO_MOVE);
			part.getTransientData().put(EngineE4Util.NO_SAVE, true);
			part.getTransientData().put(PLUGIN_RESOURCES, pluginResources);
			part.getPersistedState().put(IWorkbench.PERSIST_STATE, "false");
			editorPartStack.getChildren().add(part);
		}
		MPart mpart = partService.showPart(part, PartState.ACTIVATE);
		mpart.getTransientData().put(EngineE4Util.NO_SAVE, true);
	}

	public static void openNavigatorIDE(String adiResourceURI) {
		try {
			if (-1 == IDE_COMM_PORT) {
				Properties properties = new Properties();
				FileReader fileReader = new FileReader(BroadcastUtil.ADI_PROPERTY_FILE_NAME);
				properties.load(fileReader);
				fileReader.close();
				String portProperty = properties.getProperty(BroadcastUtil.PREFS_IDE_COMM_PORT);
				if (null != portProperty)
					IDE_COMM_PORT = Integer.parseInt(portProperty);
				else
					logError(getFromToolBundle("resourceBroadcast.invalid.port"));
			}
			if (-1 != IDE_COMM_PORT)
				BroadcastUtil.sendSocket(IDE_COMM_PORT, adiResourceURI);
		} catch (IOException e) {
			logError(e);
		}
	}

	/**
	 * Checks if is project.
	 *
	 * @param projectName
	 *            the project name
	 * @return true, if is project
	 */
	public static boolean isProject(String projectName) {
		// cannot use 'ResourcesPlugin.getWorkspace().getRoot().getProject(projectName)' instruction because this method is called
		// from application runtime and not from IDE.
		if (null == projectName)
			projectName = AdichatzApplication.getInstance().getApplicationPluginResources().getPluginName();
		try {
			Bundle bundle = Platform.getBundle(projectName);
			File file = FileLocator.getBundleFile(bundle);
			return file.exists() && file.isDirectory();
		} catch (IOException e) {
			logError(e);
		}
		return false;
	}
}
