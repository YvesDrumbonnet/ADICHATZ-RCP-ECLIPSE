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
package org.adichatz.generator.common;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.scenario.generation.ManifestChanger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtil.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("restriction")
public class FileUtil {

	/** The Constant EOF. */
	public static final int EOF = -1;

	/** The Constant EOL. */
	public static final int EOL = 10;

	/** The BYTE. */
	public static int BYTE = 1024;

	/**
	 * Replace in file.
	 *
	 * @param file
	 *            the file
	 * @param tokens
	 *            the tokens
	 * @param values
	 *            the values
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void replaceInFile(File file, String[] tokens, String[] values) throws IOException {
		StringBuffer fileData = readFileAndReplace(new FileReader(file), tokens, values);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.append(fileData.toString());
		fileWriter.close();
	}

	/**
	 * Copy file.
	 * 
	 * @param srcFileName
	 *            the src file name
	 * @param destFileName
	 *            the dest file name
	 * @param tokens
	 *            the tokens
	 * @param values
	 *            the values
	 * @param force
	 *            the force
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFile(String srcFileName, String destFileName, String[] tokens, String[] values, boolean force)
			throws IOException {
		copyFile(new File(srcFileName), new File(destFileName), tokens, values, force);
	}

	/**
	 * Copy file.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @param tokens
	 *            the tokens
	 * @param values
	 *            the values
	 * @param force
	 *            the force
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFile(File srcFile, File destFile, String[] tokens, String[] values, boolean force) throws IOException {
		if (force || !destFile.exists()) {
			FileWriter out = new FileWriter(destFile);
			StringBuffer fileData = readFileAndReplace(new FileReader(srcFile), tokens, values);
			out.write(fileData.toString());
			out.close();
		}

	}

	/**
	 * Copy file.
	 *
	 * @param srcFileName
	 *            the src file name
	 * @param destFileName
	 *            the dest file name
	 * @param force
	 *            the force
	 */
	public static void copyFile(String srcFileName, String destFileName, boolean force) {
		copyFile(new File(srcFileName), new File(destFileName), force);
	}

	/**
	 * Copy file.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @param force
	 *            the force
	 */
	public static void copyFile(File srcFile, File destFile, boolean force) {
		if (force || !destFile.exists()) {
			try {
				InputStream in = new FileInputStream(srcFile);
				OutputStream out = new FileOutputStream(destFile);
				EngineTools.copyStream(in, out);
			} catch (IOException e) {
				logError(e);
			}
		}
	}

	/**
	 * Read reader into string buffer.
	 *
	 * @param reader
	 *            the reader
	 * @return the string buffer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static StringBuffer readerAsStringBuffer(Reader reader) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(BYTE);
		BufferedReader bufferedReader = new BufferedReader(reader);
		char[] buf = new char[BYTE];
		int numRead = 0;
		while ((numRead = bufferedReader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[BYTE];
		}
		bufferedReader.close();
		return fileData;
	}

	/**
	 * Read file and replace.
	 *
	 * @param reader
	 *            the file reader
	 * @param tokens
	 *            the tokens
	 * @param values
	 *            the values
	 * @return the string buffer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static StringBuffer readFileAndReplace(Reader reader, String[] tokens, String[] values) throws java.io.IOException {
		if (tokens.length != values.length)
			throw new IOException("Tokens number (" + tokens.length + ") is different from values number(" + values.length
					+ ") Do not know how to make changes.");
		StringBuffer fileData = readerAsStringBuffer(reader);
		for (int i = 0; i < tokens.length; i++) {
			int index = 0;
			while (-1 != (index = fileData.indexOf(tokens[i], index))) {
				fileData = fileData.delete(index, index + tokens[i].length()).insert(index, values[i]);
				index += values[i].length();
			}
		}
		return fileData;
	}

	/**
	 * Copy directory.
	 * 
	 * @param srcPath
	 *            the src path
	 * @param destPath
	 *            the dest path
	 * @param maxDepth
	 *            the max depth
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyDirectory(File srcPath, File destPath, int maxDepth) throws IOException {
		copyDirectory(srcPath, destPath, 0, maxDepth);
	}

	/**
	 * Copy directory.
	 * 
	 * @param srcPath
	 *            the src path
	 * @param destPath
	 *            the dest path
	 * @param depth
	 *            the depth
	 * @param maxDepth
	 *            the max depth
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void copyDirectory(File srcPath, File destPath, int depth, int maxDepth) throws IOException {
		if (srcPath.isDirectory()) {
			if (-1 == maxDepth || depth <= maxDepth) {
				if (!destPath.exists()) {
					destPath.mkdirs();
				}
				String files[] = srcPath.list();
				for (int i = 0; i < files.length; i++) {
					copyDirectory(new File(srcPath, files[i]), new File(destPath, files[i]), depth + 1, maxDepth);
				}
			}
		} else {
			if (!srcPath.exists()) {
				throw new FileNotFoundException("File or directory " + srcPath.getAbsolutePath() + " does not exist.");
			} else {
				copyFile(srcPath, destPath);
			}
		}
	}

	/**
	 * Copy file.
	 *
	 * @param srcPath
	 *            the src path
	 * @param destPath
	 *            the dest path
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFile(File srcPath, File destPath) throws FileNotFoundException, IOException {
		EngineTools.copyStream(new FileInputStream(srcPath), new FileOutputStream(destPath));
	}

	/**
	 * Copy directory.
	 * 
	 * @param srcPath
	 *            the src path
	 * @param destPath
	 *            the dest path
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyDirectory(File srcPath, File destPath) throws IOException {
		copyDirectory(srcPath, destPath, -1, -1);
	}

	/**
	 * Copy jar file from jar file.
	 *
	 * @param jarOutStream
	 *            the jar out stream
	 * @param entryRoot
	 *            the entry root
	 * @param jarFile
	 *            the jar file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyJarFileFromJarFile(JarOutputStream jarOutStream, String entryRoot, JarFile jarFile) throws IOException {
		JarEntry jarEntry;
		Enumeration<JarEntry> entries = jarFile.entries();
		byte[] buf = new byte[BYTE];
		while (entries.hasMoreElements()) {
			jarEntry = entries.nextElement();
			if (jarEntry.getName().startsWith(entryRoot)) {
				jarOutStream.putNextEntry(jarEntry);
				InputStream in = jarFile.getInputStream(jarEntry);
				int len;
				while ((len = in.read(buf)) > 0) {
					jarOutStream.write(buf, 0, len);
				}

				// Complete the entry
				jarOutStream.closeEntry();
				in.close();
			}
		}

	}

	/**
	 * Adds the folder2 zip file.
	 * 
	 * @param zipOutStream
	 *            the zip out stream
	 * @param entryRoot
	 *            the entry root
	 * @param folder
	 *            the folder
	 * @param deleted
	 *            the deleted
	 */
	public static void addFolder2ZipFile(ZipOutputStream zipOutStream, String entryRoot, IFolder folder, boolean deleted) {
		try {
			for (IResource resource : folder.members()) {
				if (resource instanceof IFolder) {
					addFolder2ZipFile(zipOutStream, entryRoot.concat(resource.getName()).concat("/"), (IFolder) resource, deleted);
				} else if (resource instanceof IFile)
					addContent2ZipFile(zipOutStream, entryRoot.concat(resource.getName()), ((IFile) resource).getContents(),
							deleted);
			}
		} catch (CoreException | IOException e) {
			logError(e);
		}
	}

	/**
	 * Adds the content to zip file.
	 * 
	 * @param zipOutStream
	 *            the zip out stream
	 * @param entryName
	 *            the entry name
	 * @param in
	 *            the in
	 * @param deleted
	 *            the deleted
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addContent2ZipFile(ZipOutputStream zipOutStream, String entryName, InputStream in, boolean deleted)
			throws IOException {
		byte[] buf = new byte[BYTE];
		try {
			zipOutStream.putNextEntry(new ZipEntry(deleted ? "DELETED/".concat(entryName) : entryName));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				zipOutStream.write(buf, 0, len);
			}
		} catch (ZipException e) {
			if (!e.getMessage().startsWith("duplicate entry: "))
				throw e;
		} finally {
			// Complete the entry
			zipOutStream.closeEntry();
			in.close();
		}
	}

	/**
	 * Gets the plugin home.
	 * 
	 * @param bundleName
	 *            the bundle name
	 * @return the plugin home
	 */
	public static String getPluginHome(String bundleName) {
		if (InternalPolicy.OSGI_AVAILABLE)
			try {
				Bundle bundle = Platform.getBundle(bundleName);
				if (null != bundle) {
					File file = FileLocator.getBundleFile(bundle);
					if (null == file || !file.exists()) {
						logError(getFromGeneratorBundle("generation.invalid.bundleName", bundleName));
						return null;
					}
					return FileLocator.getBundleFile(bundle).getPath();
				} else {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
					if (null == project || !project.exists()) {
						logError(getFromGeneratorBundle("generation.invalid.bundleName", bundleName));
						return null;
					}
					return project.getLocation().toOSString();
				}

			} catch (IOException e) {
				logError(e);
				return null;
			}
		else {
			String pluginHome = GeneratorConstants.WORKSPACE_DIRECTORY + "/" + bundleName;
			File file = new File(pluginHome);
			if (file.exists() && file.isDirectory())
				return pluginHome;
			pluginHome = GeneratorConstants.SECONDARY_WORKSPACE_DIRECTORY + "/" + bundleName;
			file = new File(pluginHome);
			if (file.exists() && file.isDirectory())
				return pluginHome;
			pluginHome = GeneratorConstants.PLUGINS_DIRECTORY + "/" + bundleName;
			File pluginDir = new File(GeneratorConstants.PLUGINS_DIRECTORY);
			if (pluginDir.exists() && pluginDir.isDirectory()) {
				for (File pluginFile : pluginDir.listFiles()) {
					String fileName = pluginFile.getName();
					if (fileName.startsWith(bundleName) && fileName.endsWith(".jar")) {
						try {
							URL url = new URL("jar:file:/".concat(pluginFile.getAbsolutePath()).concat("!/META-INF/MANIFEST.MF"));
							if (bundleName.equals(new ManifestChanger(url).getSymbolicName()))
								return pluginFile.getAbsolutePath();
						} catch (IOException | CoreException e) {
							logError(e);
						}
					}
				}
			}
			file = new File(pluginHome);
			if (file.exists())
				return pluginHome;
			return GeneratorConstants.WORKSPACE_DIRECTORY + "/" + bundleName;
		}
	}

	/**
	 * Delete generated files.
	 * 
	 * @param directory
	 *            the directory
	 */
	public static void deleteGeneratedFiles(File directory) {
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.isDirectory())
					deleteGeneratedFiles(file);
				else if (file.getName().endsWith("GENERATED.xml") || file.getName().endsWith(".java")
						|| file.getName().endsWith("GENERATED.properties"))
					file.delete();
			}
			if (0 == directory.listFiles().length)
				directory.delete();
		}
	}

	/**
	 * Adds the path2 jar file.
	 * 
	 * @param jarOutStream
	 *            the jar out stream
	 * @param bundleName
	 *            the bundle name
	 * @param ejbPath
	 *            the ejb path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addPath2JarFile(JarOutputStream jarOutStream, String bundleName, String ejbPath) throws IOException {
		Bundle commonBundle = Platform.getBundle(bundleName);
		Enumeration<String> ejbPaths = commonBundle.getEntryPaths(ejbPath);
		int skipCharacters = 0;
		if (null == ejbPaths) { // Application is launched by Adichatz development workspace
			ejbPath = "bin/".concat(ejbPath);
			ejbPaths = commonBundle.getEntryPaths(ejbPath);
			skipCharacters = 4;
		}
		addEntry2JarFile(jarOutStream, commonBundle, ejbPath, ejbPaths, "", skipCharacters);

	}

	/**
	 * Adds the entry2 jar file.
	 * 
	 * @param jarOutStream
	 *            the jar out stream
	 * @param bundle
	 *            the bundle
	 * @param ejbPath
	 *            the ejb path
	 * @param ejbPaths
	 *            the ejb paths
	 * @param entryRoot
	 *            the entry root
	 * @param skipCharacters
	 *            the skip characters
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void addEntry2JarFile(JarOutputStream jarOutStream, Bundle bundle, String ejbPath, Enumeration<String> ejbPaths,
			String entryRoot, int skipCharacters) throws IOException {
		int rootIndex = ejbPath.length();
		while (ejbPaths.hasMoreElements()) {
			String path = ejbPaths.nextElement();
			if (path.endsWith("/")) {
				String dir = path.substring(rootIndex, path.length() - 1);
				String childRoot = entryRoot.isEmpty() ? dir : entryRoot.concat("/").concat(dir);
				addEntry2JarFile(jarOutStream, bundle, path, bundle.getEntryPaths(path), childRoot, skipCharacters);
			} else {
				FileUtil.addContent2ZipFile(jarOutStream, path.substring(skipCharacters), bundle.getEntry(path).openStream(),
						false);
			}
		}
	}

	/**
	 * Convert stream to string.
	 *
	 * @param is
	 *            the is
	 * @return the string
	 */
	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[] buffer) method. We iterate until the Reader return -1
		 * which means there's no more data to read. We use the StringWriter class to produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader;
				try {
					reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
				} finally {
					is.close();
				}
				return writer.toString();
			} catch (IOException e) {
				logError(e);
			}
		}
		return null;
	}

	/**
	 * Convert file to string.
	 *
	 * @param fileName
	 *            the file name
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String convertFileToString(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		reader.close();
		return stringBuilder.toString();
	}
}