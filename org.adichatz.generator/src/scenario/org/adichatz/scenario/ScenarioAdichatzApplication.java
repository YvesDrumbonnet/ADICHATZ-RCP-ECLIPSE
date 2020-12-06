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
package org.adichatz.scenario;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.HashSet;

import org.adichatz.common.ejb.Session;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.util.PlainPluginResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioAdichatzApplication.
 */
@SuppressWarnings("restriction")
public class ScenarioAdichatzApplication extends AdichatzApplication {
	private static AdichatzApplication previousInstance;

	private static AdichatzApplication BEFORE;

	/**
	 * Initialize.
	 */
	public static void initialize() {
		previousInstance = THIS;
		THIS = new ScenarioAdichatzApplication();
		if (null == LogBroker.getLogger())
			new ScenarioLog();
		if (null != previousInstance)
			THIS.getPluginMap().putAll(previousInstance.getPluginMap());
	}

	/**
	 * Instantiates a new scenario adichatz application.
	 */
	public ScenarioAdichatzApplication() {
		super();
		BEFORE = this;
		applContext.put(Session.class.getName(),
				new Session(GeneratorConstants.STUDIO_BUNDLE, System.getProperty("user.name"), new HashSet<String>()));
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @param pluginResourcesKey
	 *            the plugin resources key
	 * @return the plugin resources
	 */
	protected AdiPluginResources _getPluginResources(String pluginResourcesKey) {
		AdiPluginResources pluginResources = pluginMap.get(pluginResourcesKey);
		if (null == pluginResources) {
			if (InternalPolicy.OSGI_AVAILABLE) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(pluginResourcesKey);
				if (null != project && project.exists())
					return ScenarioResources.getInstance(project).getPluginResources();

				Bundle bundle = Platform.getBundle(pluginResourcesKey);
				if (null != bundle)
					try {
						bundle.start();
						// PluginResources is added by pluginMap in bundle start process
						return pluginMap.get(pluginResourcesKey);
					} catch (BundleException e) {
						logError(e);
						return null;
					}
				else
					throw new RuntimeException("Plugin '" + pluginResourcesKey + "' is unknown!");
			} else {
				return addPluginResources(pluginResourcesKey);
			}
		}
		return pluginResources;
	}

	/**
	 * Adds the plugin resources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @return the scenario plugin resources
	 */
	public static ScenarioPluginResources addPluginResources(String pluginName) {
		return new ScenarioPluginResources(ScenarioResources.getInstance(pluginName, null));
	}

	/**
	 * Adds the plugin resources.
	 *
	 * @param pluginName
	 *            the plugin name
	 * @param pluginPackage
	 *            the plugin package
	 * @param gencodeDir
	 *            the gencode dir
	 * @return the plain plugin resources
	 */
	public static PlainPluginResources addPluginResources(String pluginName, String pluginPackage, String gencodePackage,
			String gencodeDir) {
		EngineConstants.GENCODE_DIR = gencodeDir;
		String pluginHome = FileUtil.getPluginHome(pluginPackage);
		GencodePath gencodePath = new GencodePath(pluginHome, pluginName, gencodePackage, gencodeDir,
				ScenarioUtil.class.getClassLoader());
		return new PlainPluginResources(pluginHome, pluginName, pluginPackage, gencodePath);
	}

	public static PlainPluginResources addJavaPluginResources(String pluginName, String pluginPackage, String gencodePackage,
			String gencodeDir) {
		EngineConstants.GENCODE_DIR = gencodeDir;
		String pluginHome = FileUtil.getPluginHome(pluginPackage);
		GencodePath gencodePath = new GencodePath(pluginHome, pluginName, gencodePackage, gencodeDir,
				ScenarioUtil.class.getClassLoader());
		return new JavaPluginResources(pluginHome, pluginName, pluginPackage, gencodePath);
	}

	public static void reinitPreviousInstance() {
		if (null != previousInstance) {
			BEFORE = THIS;
			THIS = previousInstance;
		}
	}

	public static void reinitCurrentInstance() {
		if (null != BEFORE)
			THIS = BEFORE;
	}

}
