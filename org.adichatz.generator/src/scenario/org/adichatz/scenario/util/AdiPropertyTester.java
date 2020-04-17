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
package org.adichatz.scenario.util;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.wrapper.AdichatzRcpConfigTreeWrapper;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.generation.ManifestChanger;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class StudioPropertyTester.
 */
public class AdiPropertyTester extends PropertyTester {

	/** The Is Active File. */
	private static String IS_ACTIVE_FILE = "isActiveFile";

	/** The Is Bundle File. */
	private static String IS_XML_FILE = "isXmlFile";

	/** The Is Bundle File. */
	private static String IS_BUNDLE_FILE = "isBundleFile";

	/** The Is scenario file. */
	private static String IS_SCENARIO_FILE = "isScenarioFile";

	/** The Is adichatz config file. */
	private static String IS_ADICHATZ_CONFIG_FILE = "isAdichatzConfigFile";

	private static String IS_GENERATED_JAVA_FILE = "isGeneratedJavaFile";

	/** The Is comparable. */
	private static String IS_COMPARABLE = "isComparable";

	private static String IS_SWITCHABLE = "isSwitchable";

	private static String IS_ADICHATZ_RESOURCE = "isAdichatzResource";

	private static String IS_OPEN_ADICHATZ_PROJECT = "isOpenAdichatzProject";

	private static String HAS_SCENARIO_FILE = "hasScenarioFile";

	private static String IS_APPLICATION = "isApplication";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 * java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (isOpenAdichatzProject(receiver)) {
			if (IS_ACTIVE_FILE.equals(property))
				return isActiveFile(receiver);
			if (IS_SCENARIO_FILE.equals(property))
				return isScenarioFile(receiver);
			if (IS_COMPARABLE.equals(property))
				return isComparable(receiver);
			if (IS_SWITCHABLE.equals(property))
				return isSwitchable(receiver);
			if (IS_XML_FILE.equals(property))
				return isAdiXmlFile(receiver);
			if (IS_GENERATED_JAVA_FILE.equals(property))
				return isGeneratedJavaFile(receiver);
			if (IS_BUNDLE_FILE.equals(property))
				return isAdiBundleFile(receiver);
			if (IS_ADICHATZ_RESOURCE.equals(property))
				return isAdichatzResource(receiver);
			if (HAS_SCENARIO_FILE.equals(property))
				return hasScenarioFile(receiver);
			if (IS_APPLICATION.equals(property))
				return isApplication(receiver);
			if (IS_OPEN_ADICHATZ_PROJECT.equals(property))
				return true;
		}
		return false;
	}

	public static boolean isApplication(Object receiver) {
		if (receiver instanceof IResource) {
			IProject project = ((IResource) receiver).getProject();
			IFile configFile = project.getFile(EngineConstants.XML_FILES_PATH + "/" + GeneratorConstants.SCENARIO_FILE);
			if (configFile.exists()) {
				ScenarioTreeWrapper scenarioTree = (ScenarioTreeWrapper) getConfigTreeWrapper(configFile, IS_SCENARIO_FILE, false);
				if (null != scenarioTree)
					return null != scenarioTree.getGenerationScenario() && null != scenarioTree.getGenerationScenario().getRcpPart()
							&& scenarioTree.getGenerationScenario().getRcpPart().isApplication();
			}
		}
		return false;
	}

	public static boolean hasModelAspect(Object receiver) {
		try {
			if (receiver instanceof IProject) {
				IProject project = (IProject) receiver;
				ManifestChanger manifestChanger = new ManifestChanger(project, null);
				if (null != manifestChanger.getValue(IScenarioConstants.ADICHATZ_CONNECTOR_VERSION)) {
					IFile configFile = project.getFile(EngineConstants.XML_FILES_PATH + "/" + GeneratorConstants.SCENARIO_FILE);
					ITreeWrapper treeWrapper = getConfigTreeWrapper(configFile, IS_SCENARIO_FILE, false);
					if (null != treeWrapper)
						return null != ((ScenarioTree) treeWrapper).getGenerationScenario().getModelPart();
				}
				return false;
			} else if (receiver instanceof Bundle) {
				Bundle bundle = (Bundle) receiver;
				URL manifestUrl = bundle.getEntry("META-INF/MANIFEST.MF");
				if (null != manifestUrl) {
					ManifestChanger manifestChanger = new ManifestChanger(manifestUrl);
					if (null != manifestChanger.getValue(IScenarioConstants.ADICHATZ_CONNECTOR_VERSION)) {
						InputStream scenarioInputStream;
						try {
							URL scenarioURL = bundle
									.getEntry(EngineConstants.XML_FILES_PATH + "/" + GeneratorConstants.SCENARIO_FILE);
							if (null == scenarioURL)
								return false;
							scenarioInputStream = scenarioURL.openStream();
							if (null != scenarioInputStream) {
								Object treeWrapper = FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(),
										scenarioInputStream);
								if (null != treeWrapper)
									return null != ((ScenarioTree) treeWrapper).getGenerationScenario().getModelPart();

							}
						} catch (IOException | JAXBException e) {
							logError(e);
						}
					}
				}

			}
		} catch (IOException | CoreException e) {
			logError(e);
		}
		return false;
	}

	/**
	 * Checks if is adi file.
	 * 
	 * @param receiver
	 *            the receiver
	 * @return true, if is adi file
	 */
	public static boolean isAdichatzResource(Object receiver) {
		if (receiver instanceof IFile && ((IFile) receiver).exists()) {
			IFile file = (IFile) receiver;
			IPath path = file.getFullPath();
			return isXMLResource(path) || isBundleResource(path);
		}
		return false;
	}

	public static boolean isGeneratedJavaFile(Object receiver) {
		if (receiver instanceof IFile && ((IFile) receiver).exists()) {
			IFile file = (IFile) receiver;
			if ("java".equals(file.getFileExtension())) {
				IPath path = file.getFullPath();
				if (2 < path.segmentCount() && "resources".equals(path.segment(1)) && "gencode".equals(path.segment(2))) {
					String adiResourceURI = ScenarioUtil.getAdiResourceURI(file);
					return ScenarioUtil.getXmlFileFromURI(null, adiResourceURI).exists();
				}
			}
		}
		return false;

	}

	public static boolean isXMLResource(IPath path) {
		return 2 < path.segmentCount() && "resources".equals(path.segment(1)) && "xml".equals(path.segment(2));
	}

	public static boolean isBundleResource(IPath path) {
		return 2 < path.segmentCount() && "resources".equals(path.segment(1)) && "messages".equals(path.segment(2));
	}

	public static boolean isComparable(Object receiver) {
		if (!isAdichatzResource(receiver) || !(receiver instanceof IFile))
			return false;
		return null != getBothVersionFile((IFile) receiver);
	}

	public static boolean isSwitchable(Object receiver) {
		IFile file = (IFile) receiver;
		if (null == file.getFileExtension())
			return false;
		return file.getName().endsWith("GENERATED.".concat(file.getFileExtension())) && isActiveFile(receiver);
	}

	public static IFile[] getBothVersionFile(IFile file) {
		String treeId = ScenarioUtil.getTreeId(file);
		IFile leftFile = file.getParent().getFile(new Path(treeId.concat(".").concat(file.getFileExtension())));
		IFile rightFile = file.getParent().getFile(new Path(treeId.concat("GENERATED.").concat(file.getFileExtension())));
		if (leftFile.exists() && rightFile.exists())
			return new IFile[] { leftFile, rightFile };
		return null;
	}

	/**
	 * Checks if is adi xml file.
	 * 
	 * @param receiver
	 *            the receiver
	 * @return true, if is adi xml file
	 */
	public static boolean isAdiXmlFile(Object receiver) {
		return isAdichatzResource(receiver) && "axml".equals(((IFile) receiver).getFileExtension());
	}

	/**
	 * Checks if is adi bundle file.
	 * 
	 * @param receiver
	 *            the receiver
	 * @return true, if is adi bundle file
	 */
	public static boolean isAdiBundleFile(Object receiver) {
		if (receiver instanceof IFile) {
			IFile file = (IFile) receiver;
			IPath path = file.getFullPath();
			return 2 < path.segmentCount() && "resources".equals(path.segment(1)) && "messages".equals(path.segment(2))
					&& "properties".equals(file.getFileExtension());
		}
		return false;
	}

	/**
	 * Checks if is active file.
	 * 
	 * @param receiver
	 *            the receiver
	 * @return true, if is active file
	 */
	public static boolean isActiveFile(Object receiver) {
		if (isAdiXmlFile(receiver) || isAdiBundleFile(receiver)) {
			IFile file = (IFile) receiver;
			if (!file.getName().endsWith("GENERATED.axml") && !file.getName().endsWith("GENERATED.properties"))
				return true;
			String activeFileName = file.getName();
			if ("axml".equals(file.getFileExtension())) {
				activeFileName = activeFileName.substring(0, activeFileName.length() - 14).concat(".axml");
				return !((IFolder) file.getParent()).getFile(activeFileName).exists();
			} else {
				activeFileName = activeFileName.substring(0, activeFileName.length() - 20).concat(".properties");
				return !((IFolder) file.getParent()).getFile(activeFileName).exists();
			}
		}
		return false;
	}

	private static ITreeWrapper getConfigTreeWrapper(Object receiver, String property, boolean refreshed) {
		IFile configFile = (IFile) receiver;
		if (configFile.exists() && isAdichatzResource(receiver) && "xml".equals(configFile.getFileExtension())) {
			try {
				return (ITreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(), ((IFile) receiver).getContents());
			} catch (JAXBException e) {
				logError(e);
			} catch (CoreException e) {
				if (!refreshed) {
					try {
						((IFile) receiver).refreshLocal(IResource.DEPTH_ZERO, null);
						getConfigTreeWrapper(receiver, property, true);
					} catch (CoreException e1) {
						logError(e1);
					}
				} else
					logError(e);
			}
		}
		return null;
	}

	/**
	 * Checks if is config file.
	 * 
	 * @param receiver
	 *            the receiver
	 * @param property
	 *            the property
	 * @return true, if is config file
	 */
	public static boolean isConfigFile(Object receiver, String property) {
		if (isAdichatzResource(receiver) && "xml".equals(((IFile) receiver).getFileExtension())) {
			ITreeWrapper treeWrapper = getConfigTreeWrapper(receiver, property, false);
			if (null == property)
				return treeWrapper instanceof ScenarioTreeWrapper || treeWrapper instanceof AdichatzRcpConfigTreeWrapper;
			else if (IS_SCENARIO_FILE.equals(property))
				return treeWrapper instanceof ScenarioTreeWrapper;
			else if (IS_ADICHATZ_CONFIG_FILE.equals(property))
				return treeWrapper instanceof AdichatzRcpConfigTreeWrapper;
		}
		return false;
	}

	/**
	 * Checks if is scenario file.
	 * 
	 * @param receiver
	 *            the receiver
	 * @return true, if is scenario file
	 */
	public static boolean isScenarioFile(Object receiver) {
		return isConfigFile(receiver, IS_SCENARIO_FILE);
	}

	public static boolean isOpenAdichatzProject(Object receiver) {
		IProject project = ((IResource) receiver).getProject();
		try {
			return project.isOpen() && project.isNatureEnabled(GeneratorConstants.ADICHATZ_NATURE) && hasScenarioFile(receiver);
		} catch (CoreException e) {
			logError(e);
		}
		return false;
	}

	private static boolean hasScenarioFile(Object receiver) {
		IProject project = ((IResource) receiver).getProject();
		return isConfigFile(project.getFile(EngineConstants.XML_FILES_PATH.concat("/" + GeneratorConstants.SCENARIO_FILE)),
				IS_SCENARIO_FILE);
	}
}