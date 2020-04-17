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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logTrace;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtility.
 * 
 * Contains utilities for files management
 * 
 * @author Yves Drumbonnet
 *
 */
public class FileUtility {

	/**
	 * Gets the tree from xml file.
	 * 
	 * @param unmarshaller
	 *            the unmarshaller
	 * @param file
	 *            the file
	 * 
	 * @return the tree from xml file
	 * @throws JAXBException
	 */
	public static Object getTreeFromXmlFile(Unmarshaller unmarshaller, File file) throws JAXBException {
		try {
			if (0l == file.length()) {
				logTrace("Empty file: ".concat(file.getAbsolutePath()));
				return null;
			}
			// Could not use resource because resource are not initialized
			logTrace("Unmarshalling file: ".concat(file.getAbsolutePath()));
			InputStream inputStream = file.toURI().toURL().openStream();
			Object treeWrapper = unmarshaller.unmarshal(inputStream);
			inputStream.close();
			return treeWrapper;
		} catch (IOException e) {
			logError("Error loading xml file for " + file.getAbsolutePath(), e);
		}
		return null;
	}

	public static Object getTreeFromXmlFile(Unmarshaller unmarshaller, String pluginHome, String pathName) {
		InputStream inputStream = getInputStream(pluginHome, pathName);
		if (null == inputStream)
			return null;
		Object treeWrapper = null;
		try {
			treeWrapper = unmarshaller.unmarshal(inputStream);
			inputStream.close();
		} catch (IOException | JAXBException e) {
			logError(e);
		}
		return treeWrapper;

	}

	/**
	 * Gets the tree from xml file.
	 * 
	 * @param unmarshaller
	 *            the unmarshaller
	 * @param file
	 *            the file
	 * 
	 * @return the tree from xml file
	 * @throws JAXBException
	 */
	public static Object getTreeFromXmlFile(Unmarshaller unmarshaller, InputStream inputStream) throws JAXBException {
		Object treeWrapper = unmarshaller.unmarshal(inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
			logError(e);
		}
		return treeWrapper;
	}

	/**
	 * Returns a directory file name after having created it if needed.
	 * 
	 * @param dirName
	 *            the dir name
	 * 
	 * @return the directory file name
	 */
	public static String mkdirs(String dirName) {
		File file = new File(dirName);
		if (!file.exists()) {
			logTrace(getFromEngineBundle("createDirectories", file.getAbsolutePath()));
			if (!file.mkdirs())
				logError("Could not create directories for " + file.getAbsolutePath() + "!");
		}
		return dirName;
	}

	public static String getPluginHome(String bundleName) {
		try {
			Bundle bundle = Platform.getBundle(bundleName);
			if (null != bundle)
				return FileLocator.getBundleFile(bundle).getPath();
		} catch (IOException e) {
			logError(e);
		}
		return null;
	}

	public static InputStream getInputStream(String pluginHome, String pathName) {
		File file = new File(pluginHome);
		try {
			if (file.isDirectory())
				return new File(pluginHome.concat("/").concat(pathName)).toURI().toURL().openStream();
			JarFile jarFile = new JarFile(pluginHome);
			JarEntry entry = jarFile.getJarEntry(pathName);
			if (null != entry) {
				InputStream inputStream = jarFile.getInputStream(entry);
				jarFile.close();
				return inputStream;
			}
			jarFile.close();
		} catch (IOException e) {
			logError(e);
		}
		return null;
	}
}
