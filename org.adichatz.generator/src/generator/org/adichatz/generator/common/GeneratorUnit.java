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
import static org.adichatz.engine.common.LogBroker.logTrace;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.xjc.NavigatorTree;
import org.adichatz.scenario.ScenarioInput;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneratorUnit.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class GeneratorUnit {

	/*
	 * S T A T I C - B E G I N
	 */

	/** The xsd files for AdichatzConfig tree. */
	public static String XSD_ADICHATZ_CONFIG = "/engine/adichatzConfig.xsd";

	/** The xsd files for part tree. */
	public static String XSD_PART = "/generator/partTree.xsd";

	/** The xsd files for entity tree. */
	public static String XSD_PLUGIN_ENTITY = "/generator/pluginEntityTree.xsd";

	/** The xsd files for query manger tree. */
	public static String XSD_QUERY = "/generator/queryManagerTree.xsd";

	/** The xsd files for tree manger tree. */
	public static String XSD_TREE = "/generator/treeManagerTree.xsd";

	/** The xsd files for menu tree. */
	public static String XSD_ENTITY = "/generator/entityTree.xsd";

	/** The xsd files for menu tree. */
	public static String XSD_MENU = "/generator/navigatorTree.xsd";

	/** The xsd files for menu tree. */
	public static String XSD_INCLUDE = "/generator/includeTree.xsd";

	/** The last update. */
	private static long lastUpdate = new Date().getTime();

	/*
	 * S T A T I C - E N D
	 */

	/** The scenario input. */
	private ScenarioInput scenarioInput;

	/** The root java file name. */
	private String rootJavaFileName;

	/** The root class file name. */
	private String rootClassFileName;

	/** The java file. */
	private File javaFile;

	/** The class file. */
	private File classFile;

	/** The xml file. */
	private File xmlFile;

	/** The files with problem. */
	private List<File> filesWithProblem;

	/** The source dir. */
	private File sourceDir;

	/** The bin dir. */
	private String binDir;

	/** The files. */
	private File[] files;

	/**
	 * Instantiates a new generator unit.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public GeneratorUnit(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
	}

	/**
	 * Initialize.
	 */
	public void initialize() {
		initialize(true);
	}

	/**
	 * Initialize.
	 * 
	 * @param getTree
	 *            the get tree
	 */
	public void initialize(boolean getTree) {
		// loads treeWrapper from xml file
		if (getTree)
			getTreeWrapperFromXml(true);
		logTrace(getFromGeneratorBundle("loadedTreeWrapper", scenarioInput.getTreeId()));

		initializeFiles();
	}

	private void initializeFiles() {
		GencodePath gencodePath = scenarioInput.getScenarioResources().getGencodePath();
		xmlFile = getXmlFileFromTreeId(gencodePath);

		rootJavaFileName = gencodePath.getGenCodeSrcLocation() + "/"
				+ gencodePath.getCodeGenDirectory(scenarioInput.getSubPackage()) + "/" + scenarioInput.getTreeId();
		rootClassFileName = gencodePath.getGenCodeBinLocation() + "/"
				+ gencodePath.getCodeGenDirectory(scenarioInput.getSubPackage()) + "/" + scenarioInput.getTreeId();

		javaFile = new File(rootJavaFileName + ".java");
		classFile = new File(rootClassFileName + ".class");

		sourceDir = new File(
				gencodePath.getGenCodeSrcLocation() + "/" + gencodePath.getCodeGenDirectory(scenarioInput.getSubPackage()));
		binDir = gencodePath.getGenCodeBinLocation() + "/" + gencodePath.getCodeGenDirectory(scenarioInput.getSubPackage()) + "/";
	}

	/**
	 * Gets the files with problem.
	 * 
	 * @return the files with problem
	 */
	public List<File> getFilesWithProblem() {
		if (null == filesWithProblem) {
			initializeFiles();
			filesWithProblem = new ArrayList<File>();
			if (javaFile.lastModified() > classFile.lastModified()) {
				logTrace(getFromGeneratorBundle("javaFileMoreRecent", scenarioInput.getTreeId()));
				filesWithProblem.add(javaFile);
			} else
				for (File childJavaFile : getFiles()) {
					String className = childJavaFile.getName().substring(0, childJavaFile.getName().length() - 5);
					classFile = new File(binDir + className + ".class");
					if (0 == classFile.lastModified() || childJavaFile.lastModified() > classFile.lastModified()) {
						logTrace(getFromGeneratorBundle("outDatedClassFile", childJavaFile.getName()));
						filesWithProblem.add(childJavaFile);
					}
				}
		}
		return filesWithProblem;
	}

	/**
	 * Gets the files.
	 * 
	 * @return the files
	 */
	private File[] getFiles() {
		if (null == files) {
			files = sourceDir.listFiles(new FileFilter() {
				String prefix = scenarioInput.getTreeId();

				public boolean accept(File file) {
					if (file.getName().equals(scenarioInput.getTreeId().concat(".java")))
						return true;
					return file.getName().endsWith(".java") && file.getName().startsWith(prefix);
				}
			});
		}
		return files;
	}

	/**
	 * Check generation.
	 * 
	 * @return true, if successful
	 */
	public boolean checkGeneration() {
		GencodePath gencodePath = scenarioInput.getScenarioResources().getGencodePath();
		if (xmlFile.lastModified() > javaFile.lastModified()) {
			logTrace(getFromGeneratorBundle("xmlFileMoreRecent", scenarioInput.getTreeId(),
					new Date(xmlFile.lastModified()).toString(), new Date(javaFile.lastModified()).toString()));
			return false;
		} else if (0 == classFile.lastModified()) {
			logTrace(getFromGeneratorBundle("classFileNotFound", scenarioInput.getTreeId(), javaFile.getName()));
			return false;
		} else if (null == gencodePath
				.getClazz(gencodePath.getGenCodePackage(scenarioInput.getSubPackage()) + "." + scenarioInput.getTreeId())) {
			logTrace(getFromGeneratorBundle("classCannotBeLoaded", scenarioInput.getTreeId()));
			return false;
		}
		return true;
	}

	/**
	 * Check last update.
	 * 
	 * @return true, if successful
	 */
	public boolean isJavaFilesExpired() {
		return javaFile.lastModified() < xmlFile.lastModified();
	}

	/**
	 * Check last update.
	 * 
	 * @return true, if successful
	 */
	public boolean checkLastUpdate() {
		if (lastUpdate < xmlFile.lastModified()) {
			lastUpdate = xmlFile.lastModified();
			return false;
		}
		if (lastUpdate < javaFile.lastModified()) {
			lastUpdate = javaFile.lastModified();
			return false;
		}
		return true;
	}

	/**
	 * Gets the xml file dir.
	 * 
	 * @param gencodePath
	 *            the gencode path
	 * @param pathName
	 *            the path name
	 * @return the xml file dir
	 */
	public String getXmlFileDir(GencodePath gencodePath, String pathName) {
		if (EngineTools.isEmpty(pathName))
			return gencodePath.getXmlFilesLocation();
		return FileUtility.mkdirs(gencodePath.getXmlFilesLocation() + "/" + pathName.replace('.', '/'));
	}

	/**
	 * Gets the tree wrapper from xml.
	 * 
	 * @param errorWhenNotExist
	 *            the error when not exist
	 * @return the tree wrapper from xml
	 */
	public ITreeWrapper getTreeWrapperFromXml(boolean errorWhenNotExist) {
		Exception exception = null;
		logTrace(getFromGeneratorBundle("creatingDefaultTreeWrapper", scenarioInput.getTreeId()));
		Bundle bundle = scenarioInput.getScenarioResources().getBundle();
		try {
			ITreeWrapper treeWrapper = null;
			if (null != bundle) {
				String radix = EngineConstants.XML_FILES_PATH.concat("/").concat(scenarioInput.getSubPackage().replace(".", "/"))
						.concat("/").concat(scenarioInput.getTreeId());
				URL xmlUrl = bundle.getEntry(radix.concat(".axml"));
				if (null == xmlUrl)
					xmlUrl = bundle.getEntry(radix.concat("GENERATED.axml"));
				if (null == xmlUrl)
					throw new IOException("Entry has not been found for treeId '" + scenarioInput.getTreeId()
							+ "' and sub package '" + scenarioInput.getSubPackage() + "'!?");
				treeWrapper = (ITreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(), xmlUrl.openStream());
			} else {
				GencodePath gencodePath = scenarioInput.getScenarioResources().getGencodePath();
				File xmlFile = getXmlFileFromTreeId(gencodePath);
				if (xmlFile.exists())
					treeWrapper = (ITreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(), xmlFile);
			}
			if (null != treeWrapper) {
				treeWrapper.setPluginName(scenarioInput.getScenarioResources().getPluginName());
				treeWrapper.setTreeId(scenarioInput.getTreeId());
				treeWrapper.setSubPackage(scenarioInput.getSubPackage());
				treeWrapper.setXmlFile(xmlFile);
				scenarioInput.setXmlElement(treeWrapper);
			}
			return treeWrapper;
		} catch (JAXBException | IOException e) {
			exception = e;
		}
		if (errorWhenNotExist)
			throw new RuntimeException("Don't know how to unmarshall xml file for tree Id <" + scenarioInput.getTreeId()
					+ "> and sub package <" + scenarioInput.getSubPackage() + ">!", exception);
		return null;
	}

	/**
	 * Generate code from xml tree.
	 * 
	 * @param compile
	 *            the compile
	 */
	public void generateCodeFromXMLTree(boolean compile) {
		scenarioInput.setCompile(compile);
		/*
		 * Checks NavigatorTree.id is tree Wrapper is a Navigator Tree Wrapper
		 */
		if (scenarioInput.getXmlElement() instanceof NavigatorTree) {
			String menuId = ((NavigatorTree) scenarioInput.getXmlElement()).getId();
			if (null == menuId || !menuId.equals(scenarioInput.getTreeId())) {
				((NavigatorTree) scenarioInput.getXmlElement()).setId(scenarioInput.getTreeId());
			}
		}

		// determines the accurate generator
		LogBroker.logInfo(getFromGeneratorBundle("generation.generating.java.file", scenarioInput.getTreeId(),
				scenarioInput.getSubPackage()));
		GeneratorUtil.instantiateGenerator(scenarioInput, scenarioInput.getXmlElement(), new Class[] { ScenarioInput.class },
				new Object[] { scenarioInput });
	}

	/**
	 * Generate class.
	 */
	public void generateClass() {
		initialize();
		if (null == scenarioInput.getXmlElement())
			logError(getFromGeneratorBundle("invalid.treeId.subPackage", scenarioInput.getTreeId(), scenarioInput.getSubPackage()));
		else
			generateCodeFromXMLTree(true);
	}

	/**
	 * Gets the file from tree id.
	 * 
	 * @param genCodePath
	 *            the gen code path
	 * @return the file from tree id
	 */
	public File getXmlFileFromTreeId(GencodePath genCodePath) {
		File xmlFile = new File(
				getXmlFileDir(genCodePath, scenarioInput.getSubPackage()) + "/" + scenarioInput.getTreeId() + ".axml");
		if (!xmlFile.exists()) {
			xmlFile = new File(
					getXmlFileDir(genCodePath, scenarioInput.getSubPackage()) + "/" + scenarioInput.getTreeId() + "GENERATED.axml");
		}
		return xmlFile;
	}
}
