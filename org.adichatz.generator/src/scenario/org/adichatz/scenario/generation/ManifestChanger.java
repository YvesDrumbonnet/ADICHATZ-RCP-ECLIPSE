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
package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ManifestModifier.
 *
 * @author Yves Drumbonnet
 */
public class ManifestChanger {

	/** The manifest i file. */
	private IFile manifestIFile;

	/** The manifest file. */
	private URL manifestUrl;

	/** The elements. */
	private List<String> elements;

	/** The main attributes. */
	private Attributes mainAttributes;

	/**
	 * Instantiates a new manifest modifier.
	 *
	 * @param project
	 *            the project
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public ManifestChanger(IProject project, ScenarioResources scenarioResources) {
		if (null != project)
			this.manifestIFile = project.getFile("META-INF/MANIFEST.MF");
		else {
			String manifestFileName = scenarioResources.getPluginHome() + "/META-INF/MANIFEST.MF";
			try {
				File manifestFile = new File(manifestFileName);
				if (manifestFile.exists())
					this.manifestUrl = manifestFile.toURI().toURL();
			} catch (MalformedURLException e) {
				logError(getFromEngineBundle("malformedURLException.message", manifestFileName), e);
				logError(e);
			}
		}
	}

	/**
	 * Instantiates a new manifest modifier.
	 *
	 * @param url
	 *            the url
	 */
	public ManifestChanger(URL url) {
		this.manifestUrl = url;
	}

	/**
	 * Gets the main attributes.
	 *
	 * @return the main attributes
	 * @throws CoreException
	 *             the core exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Attributes getMainAttributes() throws CoreException, IOException {
		if (null == mainAttributes) {
			if ((null == manifestUrl && null == manifestIFile) || (null != manifestIFile && !manifestIFile.exists()))
				return new Attributes();
			InputStream manifestIS;
			if (null == manifestUrl) {
				if (!manifestIFile.isSynchronized(IResource.DEPTH_ZERO))
					manifestIFile.refreshLocal(IResource.DEPTH_ZERO, null);
				manifestIS = manifestIFile.getContents();
			} else
				manifestIS = manifestUrl.openStream();
			mainAttributes = new Manifest(manifestIS).getMainAttributes();
			manifestIS.close();
		}
		return mainAttributes;
	}

	/**
	 * Gets the elements.
	 * 
	 * @return the elements
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	private List<String> getElements() throws IOException, CoreException {
		if (null == elements) {
			elements = new ArrayList<String>();
			InputStream manifestIS = null == manifestUrl ? manifestIFile.getContents() : manifestUrl.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(manifestIS));
			String lineStr = null;
			boolean isFragmentLine = false;
			elements = new ArrayList<String>();
			while ((lineStr = br.readLine()) != null) {
				if (!isFragmentLine) {
					int index = lineStr.indexOf(':');
					if (-1 == index) {
						br.close();
						throw new IllegalArgumentException(lineStr + " is not a valid manifest element for Adichatz engine!");
					}
					elements.add(lineStr.substring(0, index).trim());
				}
				isFragmentLine = lineStr.trim().endsWith(",");
			}
			br.close();
			manifestIS.close();
		}
		return elements;
	}

	/**
	 * Removes the attribute.
	 *
	 * @param attributeName
	 *            the attribute name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void removeAttribute(String attributeName) throws IOException, CoreException {
		getElements().remove(attributeName);
		getMainAttributes().remove(new Name(attributeName));
	}

	/**
	 * Removes the attribute element from manifest.
	 * 
	 * If element contains '*' character, matches will be used.<br>
	 * e.g. ".*org\\.adichatz\\..*" means containing string 'org.adichatz.'
	 *
	 * @param attributeName
	 *            the attribute name
	 * @param element
	 *            the element
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void removeAttributeElement(String attributeName, String element) throws IOException, CoreException {
		String attribute = getMainAttributes().getValue(attributeName);
		element = element.trim();
		boolean regexp = -1 != element.indexOf('*');
		if (!EngineTools.isEmpty(attribute)) {
			StringTokenizer tokenizer = new StringTokenizer(attribute, ",");
			StringBuffer attributeSB = new StringBuffer();
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken();
				if (regexp) {
					if (!token.matches(element)) {
						if (0 < attributeSB.length())
							attributeSB.append(",");
						attributeSB.append(token.trim());
					}
				} else {
					if (!token.equals(element)) {
						if (0 < attributeSB.length())
							attributeSB.append(",");
						attributeSB.append(token.trim());
					}

				}
			}
			String name = attributeSB.toString();
			if (0 == attributeSB.length()) {
				mainAttributes.remove(new Name(attributeName));
				getElements().remove(attributeName);
			} else
				mainAttributes.putValue(attributeName, name);
		}
	}

	/**
	 * Adds the attribute element.
	 *
	 * @param attributeName
	 *            the attribute name
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public boolean addAttributeElement(String attributeName, String element) throws IOException, CoreException {
		return addAttributeElement(attributeName, element, -1);
	}

	/**
	 * Replace attribute element.
	 *
	 * @param attributeName
	 *            the attribute name
	 * @param element
	 *            the element
	 * @param pos
	 *            the pos
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void replaceAttributeElement(String attributeName, String element, int pos) throws IOException, CoreException {
		getMainAttributes().remove(new Name(attributeName));
		getElements().remove(attributeName);
		addAttributeElement(attributeName, element, pos);
	}

	/**
	 * Adds the attribute element.
	 *
	 * @param attributeName
	 *            the attribute name
	 * @param element
	 *            the element
	 * @param pos
	 *            the pos
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public boolean addAttributeElement(String attributeName, String element, int pos) throws IOException, CoreException {
		String attribute = getMainAttributes().getValue(attributeName);
		element = element.trim();
		if (EngineTools.isEmpty(attribute)) {
			getMainAttributes().putValue(attributeName, element);
			if ((-1 == pos))
				getElements().add(attributeName);
			else
				getElements().add(pos, attributeName);
			return true;
		} else {
			List<String> elements = new ArrayList<>();
			StringTokenizer tokenizer = new StringTokenizer(attribute, ",");
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken().trim();
				elements.add(token);
			}
			if (!elements.contains(element)) {
				if (elements.contains(".")) { // put "." as last element
					elements.remove(".");
					elements.add(element);
					elements.add(".");
				} else
					elements.add(element);

				StringBuffer attributeSB = new StringBuffer();
				for (String token : elements) {
					attributeSB.append(", ").append(token);
				}
				mainAttributes.putValue(attributeName, attributeSB.substring(2));
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the value.
	 *
	 * @param name
	 *            the name
	 * @return the value
	 * @throws CoreException
	 *             the core exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getValue(String name) throws CoreException, IOException {
		return getMainAttributes().getValue(new Name(name));
	}

	/**
	 * Checks for value.
	 *
	 * @param attributeName
	 *            the attribute name
	 * @param valueParam
	 *            the value param
	 * @return true, if successful
	 * @throws CoreException
	 *             the core exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean hasValue(String attributeName, String valueParam) throws CoreException, IOException {
		String value = getValue(attributeName);
		if (null != value) {
			StringTokenizer tokenizer = new StringTokenizer(value, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				if (valueParam.equals(token))
					return true;
			}
		}
		return false;
	}

	/**
	 * Gets the symbolic name.
	 *
	 * @return the symbolic name
	 * @throws CoreException
	 *             the core exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getSymbolicName() throws CoreException, IOException {
		String symbolicName = getValue(Constants.BUNDLE_SYMBOLICNAME);
		if (null == symbolicName)
			return null;
		StringTokenizer tokenizer = new StringTokenizer(symbolicName, ";");
		return tokenizer.nextToken().trim();
	}

	/**
	 * Write.
	 * 
	 * @param stringBuffer
	 *            the string buffer
	 * @param attributeName
	 *            the attribute name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	private void write(StringBuffer stringBuffer, String attributeName) throws IOException, CoreException {
		String attributeValue = getMainAttributes().getValue(attributeName);
		if (null != attributeValue) {
			StringTokenizer tokenizer = new StringTokenizer(attributeValue, ",");
			stringBuffer.append(attributeName).append(": ");
			if (tokenizer.hasMoreElements())
				stringBuffer.append(tokenizer.nextToken());
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken();
				stringBuffer.append(",\n ").append(token);
			}
			stringBuffer.append("\n");
		}
	}

	/**
	 * Write.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void write() throws IOException, CoreException {
		if (null != getValue(Constants.BUNDLE_CLASSPATH))
			addAttributeElement(Constants.BUNDLE_CLASSPATH, ".");
		StringBuffer exportableSB = new StringBuffer();
		for (String element : getElements()) {
			write(exportableSB, element);
		}

		if (null != manifestIFile)
			manifestIFile.setContents(new ByteArrayInputStream(exportableSB.toString().getBytes("UTF-8")), true, false, null);
		else {
			try {
				FileWriter fileWriter = new FileWriter(new File(manifestUrl.toURI()));
				fileWriter.write(exportableSB.toString());
				fileWriter.close();
			} catch (URISyntaxException e) {
				logError(e);
			}
		}
	}

	/**
	 * Exist.
	 *
	 * @return true, if successful
	 */
	public boolean exist() {
		if (null != manifestIFile)
			return manifestIFile.exists();
		return null != manifestUrl;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		mainAttributes = null;
	}
}
