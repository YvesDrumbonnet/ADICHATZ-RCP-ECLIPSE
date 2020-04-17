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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.internal.InternalPolicy;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceBundleManager.
 */
@SuppressWarnings("restriction")
public class ResourceBundleManager {

	/*
	 * S T A T I C
	 */
	/** *************************************************************************. */

	private Map<String, AdiResourceBundle> resourceBundleMap = new HashMap<String, AdiResourceBundle>();

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/**
	 * Instantiates a new resource bundle manager.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 */
	public ResourceBundleManager(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	/**
	 * Gets the file name.
	 * 
	 * @param dirName
	 *            the dir name
	 * @param resourceName
	 *            the resource name
	 * @param language
	 *            the language
	 * @param generated
	 *            the generated
	 * @return the file name
	 */
	public static String getFileName(String dirName, String resourceName, String language, boolean generated) {
		StringBuffer fileNameSB = new StringBuffer(dirName).append("/").append(resourceName);
		if (generated)
			fileNameSB.append("GENERATED");
		if (null != language)
			fileNameSB.append("_").append(language);
		fileNameSB.append(".properties");
		return fileNameSB.toString();
	}

	/**
	 * Gets the resource bundle.
	 * 
	 * @param resourceKey
	 *            the resource key
	 * @return the resource bundle
	 */
	public AdiResourceBundle getResourceBundle(String resourceKey) {
		AdiResourceBundle resourceBundle = resourceBundleMap.get(resourceKey);
		if (null == resourceBundle) {
			resourceBundle = createResourceBundle(resourceKey, EngineConstants.LANGUAGE);
			resourceBundleMap.put(resourceKey, resourceBundle);
		}
		return resourceBundle;
	}

	/**
	 * Creates the resource bundle.
	 * 
	 * @param resourceKey
	 *            the resource key
	 * @param language
	 *            the language
	 * @return the adi resource bundle
	 */
	private AdiResourceBundle createResourceBundle(String resourceKey, String language) {
		AdiResourceBundle resourceBundle = null;
		AdiResourceBundle parent = null;
		URL url = null;
		try {
			String resourceBundleDir;
			String resourceBundleName;
			if (resourceKey.startsWith("%")) {
				resourceBundleName = resourceKey.substring(1);
				int dirIndex = resourceBundleName.lastIndexOf('/');
				resourceBundleDir = -1 == dirIndex ? "" : resourceBundleName.substring(0, dirIndex);
				resourceBundleName = resourceBundleName.substring(dirIndex + 1);
			} else {
				int slashIndex = resourceKey.indexOf('/');
				if (-1 != slashIndex) {
					resourceBundleDir = EngineConstants.RESOURCE_MESSAGE_DIR.concat("/")
							.concat(resourceKey.substring(0, slashIndex).replace('.', '/'));
					resourceBundleName = resourceKey.substring(slashIndex + 1);
				} else {
					resourceBundleDir = EngineConstants.RESOURCE_MESSAGE_DIR;
					resourceBundleName = resourceKey;
				}
			}
			if (InternalPolicy.OSGI_AVAILABLE) {
				url = Platform.getBundle(pluginResources.getPluginName())
						.getEntry(getFileName(resourceBundleDir, resourceBundleName, language, false));
				if (null == url) {
					url = Platform.getBundle(pluginResources.getPluginName())
							.getEntry(getFileName(resourceBundleDir, resourceBundleName, language, true));
				}
			} else {
				if (pluginResources.getPluginHome().endsWith(".jar")) {
					String urlRadix = "jar:file:/".concat(pluginResources.getPluginHome()).concat("!/");
					url = new URL(urlRadix.concat(getFileName(resourceBundleDir, resourceBundleName, language, false)));
					try {
						InputStream inputStream = url.openStream();
						inputStream.close();
					} catch (IOException e) {
						try {
							url = new URL(urlRadix.concat(getFileName(resourceBundleDir, resourceBundleName, language, true)));
							InputStream inputStream = url.openStream();
							inputStream.close();
						} catch (IOException e1) {
							url = null;
						}
					}
				} else {
					File bundleFile = new File(pluginResources.getPluginHome().concat("/")
							.concat(getFileName(resourceBundleDir, resourceBundleName, language, false)));
					if (!bundleFile.exists()) {
						bundleFile = new File(pluginResources.getPluginHome().concat("/")
								.concat(getFileName(resourceBundleDir, resourceBundleName, language, true)));
					}
					if (bundleFile.exists())
						url = bundleFile.toURI().toURL();
				}
			}
			if (null != url) {
				InputStream stream = url.openStream();
				resourceBundle = new AdiResourceBundle(stream);
				stream.close();
			} else if (null == language) {
				throw new ResourceBundleException("Cannot add resource bundle '" + resourceKey + "' for '"
						+ pluginResources.getPluginName() + "', directory: '" + resourceBundleDir + "'!");
			}
			if (null != language) {
				parent = createResourceBundle(resourceKey, null);
				if (null != resourceBundle && null != parent)
					resourceBundle.setParent(parent);
			}
		} catch (IOException e) {
			logError(e);
		}
		return null != resourceBundle ? resourceBundle : parent;

	}

	/**
	 * Gets the resource bundle map.
	 *
	 * @return the resource bundle map
	 */
	public Map<String, AdiResourceBundle> getResourceBundleMap() {
		return resourceBundleMap;
	}
}
