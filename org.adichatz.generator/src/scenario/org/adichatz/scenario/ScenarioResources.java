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
package org.adichatz.scenario;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logTrace;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.function.BooleanSupplier;
import java.util.jar.Attributes;

import jakarta.persistence.Entity;
import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.data.IBeanInterceptorFactory;
import org.adichatz.engine.data.IGatewayConnector;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PathElementWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ApplicationServerType;
import org.adichatz.generator.xjc.ControllerType;
import org.adichatz.generator.xjc.CopyResourceType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.GeneratorType;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PathElementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.PathElementsType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.PojoRewriterType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RcpPartType;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.scenario.generation.ARewriter;
import org.adichatz.scenario.generation.ActivatorGenerator;
import org.adichatz.scenario.generation.ComponentGeneration;
import org.adichatz.scenario.generation.JakartaPersistenceRewriter;
import org.adichatz.scenario.generation.ManifestChanger;
import org.adichatz.scenario.generation.PojoTypeRewriter;
import org.adichatz.scenario.impl.PluginEntityScenario;
import org.adichatz.scenario.util.InFlyClassBuilder;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.osgi.container.ModuleLoader;
import org.eclipse.osgi.internal.framework.EquinoxBundle;
import org.eclipse.osgi.internal.loader.BundleLoader;
import org.eclipse.osgi.internal.loader.ModuleClassLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioResources.
 * 
 * Manage scenario resource for a project
 *
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class ScenarioResources {

	/*
	 * S T A T I C
	 */

	/** The INVALID SCENARIO FILE label. */
	private static String INVALID_SCENARIO_FILE = "Invalid scenario file for bundle ";

	/** The scenario resources map. */
	protected static Map<String, ScenarioResources> scenarioResourcesMap = new HashMap<String, ScenarioResources>();

	/** The buffer. */
	private BufferCode buffer;

	/** The dirty scenario file. */
	private boolean dirtyScenarioFile;

	/** 
	 * determines if the lack of model connector is an error or if the generation process is in progress. .
	 */
	public boolean ACTIVATOR_FIRST_PASSAGE = false;

	// Flag pointing if process is in Infly to avoid infinite loop
	private boolean inInfly;

	/**
	 * Gets the scenario resources map.
	 *
	 * @return the scenario resources map
	 */
	public static Map<String, ScenarioResources> getScenarioResourcesMap() {
		return scenarioResourcesMap;
	}

	/**
	 * Gets the single instance of ScenarioResources.
	 *
	 * @param pluginName
	 *            the plugin name
	 * @param currentSR
	 *            the current SR
	 * @return single instance of ScenarioResources
	 */
	public static ScenarioResources getInstance(String pluginName, String currentSR) {
		ScenarioResources scenarioResources = scenarioResourcesMap.get(pluginName);
		if (null != scenarioResources && (pluginName.equals(currentSR) || scenarioResources.isSynchronized()))
			return scenarioResources;
		if (InternalPolicy.OSGI_AVAILABLE)
			scenarioResources = new ScenarioResources(pluginName);
		else
			scenarioResources = new ScenarioResources(pluginName) {
				@Override
				public void refresh() {
				}
			};
		scenarioResourcesMap.put(pluginName, scenarioResources);
		return scenarioResources;
	}

	/**
	 * Gets the single instance of ScenarioResources.
	 * 
	 * @param project
	 *            the project
	 * @return single instance of ScenarioResources
	 */
	public static ScenarioResources getInstance(IProject project) {
		String pluginName = project.getName();
		ScenarioResources scenarioResources = scenarioResourcesMap.get(pluginName);
		if (null != scenarioResources && scenarioResources.isSynchronized())
			return scenarioResources;
		scenarioResources = new ScenarioResources(project, false);
		scenarioResourcesMap.put(pluginName, scenarioResources);
		return scenarioResources;
	}

	/*
	 * end S T A T I C
	 */

	/** The xml folder. */
	private IFolder xmlFolder;

	/** The xml folder. */
	private IFile scenarioFile;

	/** The gencode folder. */
	private IFolder gencodeFolder;

	/** The affected files. */
	private Set<IFile> affectedFiles = new HashSet<IFile>();

	/** The erroneous files. */
	private Set<IFile> erroneousFiles = new HashSet<IFile>();

	/** The key word generator. */
	private KeyWordGenerator keyWordGenerator;

	/** The param map. */
	private Map<String, String> paramMap;

	/** The scenario tree. */
	private ScenarioTreeWrapper scenarioTree;

	/** The controller map. */
	private Map<String, String> controllerMap;

	/** The wrapper map. */
	private Map<String, String> wrapperMap;

	/** The controller map. */
	private Map<String, Class<?>> generatorMap;

	/** The plugin home. */
	private String pluginHome = "";

	/** The plugin package. */
	private String pluginPackage;

	/** The plugin name. */
	private String pluginName;

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The gencode path. */
	private GencodePath gencodePath;

	/** The class path. */
	private String classPath;

	/** The parameters loaded. */
	private boolean parametersLoaded;

	/** The project. */
	private IProject project;

	/** The project. */
	private IJavaProject javaProject;

	/** The project. */
	private Bundle bundle;

	/** The current stamp. */
	private long currentStamp;

	/** The connector version. */
	private String connectorVersion;

	/** The plugin entity URI map. */
	private Map<String, PluginEntityWrapper> pluginEntityURIMap;

	/** The plugin entity scenario. */
	private IPluginEntityScenario pluginEntityScenario;

	/** The manifest changer. */
	private ManifestChanger manifestChanger;

	/** The build pojo dir. */
	private File buildPojoDir;

	/** The accessible adi plugins. */
	private Map<String, AdiResourceContainer> accessibleAdiPlugins;

	/** The impact model. */
	private Boolean impactModel;

	/** The impact rcp. */
	private Boolean impactRcp;

	/** The connector tree. */
	private ConnectorTreeWrapper connectorTree;

	/** The condition map. predicate is key, BooleanSupplier instance is value */
	private Map<String, BooleanSupplier> conditionMap = new HashMap<>();

	/**
	 * Instantiates a new scenario resources.
	 *
	 * @param project
	 *            the project
	 * @param buildingProject
	 *            the building project
	 */
	public ScenarioResources(IProject project, boolean buildingProject) {
		this.project = project;
		initFromProject(getScenarioFile(), buildingProject);
		currentStamp = computeCurrentStamp();
	}

	/**
	 * Instantiates a new scenario resources.
	 * 
	 * @param bundleName
	 *            the bundle name
	 */
	protected ScenarioResources(String bundleName) {
		initScenarioResources(bundleName);
		if (InternalPolicy.OSGI_AVAILABLE)
			currentStamp = computeCurrentStamp();
	}

	/**
	 * Inits the scenario resources.
	 * 
	 * @param bundleName
	 *            the bundle name
	 */
	private void initScenarioResources(String bundleName) {
		if (null != bundleName) {
			try {
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
				if (project.exists()) {
					initFromProject(getScenarioFile(), false);
				} else {
					bundle = Platform.getBundle(bundleName);
					if (null != bundle)
						initFromBundle(bundle);
					else
						LogBroker.displayError("Invalid bundle name", "Bundle or project " + bundleName + " does not exist!");
				}
			} catch (java.lang.IllegalStateException e) {
				initFromDir(bundleName);
			}
		}
		pluginEntityScenario = null;
	}

	/**
	 * Inits the from project.
	 *
	 * @param scenarioFile
	 *            the scenario file
	 * @param buildingProject
	 *            the building project
	 */
	public void initFromProject(IFile scenarioFile, boolean buildingProject) {
		if (!project.exists()) // When deleting project with open resources
			throw new RuntimeException(getFromGeneratorBundle("generation.project.not.exists", project.getName()));
		pluginHome = project.getLocation().toOSString();
		try {
			manifestChanger = new ManifestChanger(project, null);
			pluginName = getPluginName(manifestChanger.getMainAttributes());
			if (null == pluginName)
				pluginName = project.getName();
			if (scenarioFile.exists()) {
				scenarioFile.refreshLocal(IResource.DEPTH_ZERO, null);
				loadScenarioTree(scenarioFile.getContents());
				if (null == pluginPackage)
					throw new RuntimeException(getFromGeneratorBundle("generation.null.plugin.package", pluginName));
				/*
				 * Defines entries
				 */
				createFolderIfNotExist("src");
				createFolderIfNotExist("bin");
				createFolderIfNotExist("resources/gencode/bin");
				createFolderIfNotExist("resources/gencode/src");
				createProjectGencodePath();
				if (!buildingProject && parametersLoaded) // When creating project, folder are not initialized
					loadScenarioParameters();
			} else {
				throw new RuntimeException("Invalid Adichatz project '" + project.getName() + "'!? '" + scenarioFile.getName()
						+ "' scenarioFile does not exist");
			}
		} catch (CoreException | JAXBException | IOException e) {
			throw new RuntimeException(INVALID_SCENARIO_FILE + project.getName() + "?!", e);
		}
	}

	/**
	 * Creates the project gencode path.
	 */
	private void createProjectGencodePath() {
		if (InternalPolicy.OSGI_AVAILABLE) {
			Bundle templateBundle = Platform.getBundle(GeneratorConstants.TEMPLATE_BUNDLE);
			// AVISATZ landmark A revoir
			// add Hibernate core jar because it is needed when manipulating Bean implementing FieldHandler (OTO).
			URL[] urls = new URL[] { templateBundle.getEntry("template/lib/lib-jse/" + GeneratorConstants.HIBERNATE_CORE_JAR) };
			ClassLoader parentClassLoader = new URLClassLoader(urls, getClass().getClassLoader());
			gencodePath = new GencodePath(pluginHome, pluginPackage, getParam(IScenarioConstants.GENCODE_PACKAGE),
					EngineConstants.GENCODE_DIR, parentClassLoader);
		}
		gencodePath = new GencodePath(pluginHome, pluginPackage, getParam(IScenarioConstants.GENCODE_PACKAGE),
				EngineConstants.GENCODE_DIR, getClass().getClassLoader());
		gencodePath.getClassPaths().add(new File(pluginHome.concat("/bin")));
		if (null != scenarioTree.getPathElements()) {
			addPathElements(ScenarioUtil.getPathElements(this, scenarioTree.getPathElements().getPathElement()));
			getPluginResources().setGencodePath(gencodePath);
		}
	}

	/**
	 * Inits the from project.
	 * 
	 * @param pluginName
	 *            the plugin name
	 */
	private void initFromDir(String pluginName) {
		this.pluginName = pluginName;
		pluginHome = FileUtil.getPluginHome(pluginName);
		String manifestFileName = pluginHome + "/META-INF/MANIFEST.MF";
		try {
			manifestChanger = new ManifestChanger(new File(manifestFileName).toURI().toURL());
		} catch (MalformedURLException e) {
			logError(getFromEngineBundle("malformedURLException.message", manifestFileName), e);
		}

		try {
			File scenarioFile = new File(
					pluginHome + "/" + EngineConstants.XML_FILES_PATH + "/" + GeneratorConstants.SCENARIO_FILE);
			if (scenarioFile.exists()) {
				loadScenarioTree(scenarioFile.toURI().toURL().openStream());
				String gencodeDir = getParam(IScenarioConstants.GENCODE_DIRECTORY);
				if (null == gencodeDir)
					gencodeDir = EngineConstants.GENCODE_DIR;
				gencodePath = new GencodePath(pluginHome, pluginPackage, getParam(IScenarioConstants.GENCODE_PACKAGE), gencodeDir,
						getClass().getClassLoader());
				gencodePath.getClassPaths().add(new File(pluginHome.concat("/bin")));
			} else {
				throw new RuntimeException("Invalid Adichatz project '" + pluginName + "'!?");
			}
		} catch (IOException | JAXBException e) {
			throw new RuntimeException(INVALID_SCENARIO_FILE + pluginName + "?!", e);
		}
	}

	/**
	 * Inits the from bundle.
	 * 
	 * @param bundle
	 *            the bundle
	 */
	private void initFromBundle(Bundle bundle) {
		try {
			pluginHome = FileLocator.getBundleFile(bundle).getPath();
			manifestChanger = new ManifestChanger(bundle.getEntry("META-INF/MANIFEST.MF"));
			Attributes attributes = manifestChanger.getMainAttributes();
			pluginName = getPluginName(attributes);
			URL scenarioURL = bundle.getEntry(EngineConstants.XML_FILES_PATH + "/" + GeneratorConstants.SCENARIO_FILE);

			if (null != scenarioURL) {
				if (Bundle.UNINSTALLED == bundle.getState())
					try {
						bundle.start();
					} catch (BundleException e) {
						logError("cannot start bundle " + bundle.getSymbolicName() + "!", e);
					}
				loadScenarioTree(scenarioURL.openStream());
				AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(pluginName);

				ModuleLoader moduleLoader = ((EquinoxBundle) bundle).getModule().getCurrentRevision().getWiring().getModuleLoader();
				String genCodeDir = EngineConstants.GENCODE_DIR;
				File genCodeBinDir = new File(pluginHome.concat("/").concat(EngineConstants.GENCODE_DIR));
				if (!genCodeBinDir.exists())
					genCodeDir = "/";
				if (moduleLoader instanceof BundleLoader) {
					ModuleClassLoader parentLoader = ((BundleLoader) moduleLoader).getModuleClassLoader();
					gencodePath = new GencodePath(pluginHome, pluginPackage, getParam(IScenarioConstants.GENCODE_PACKAGE),
							genCodeDir, parentLoader);
					pluginResources.setGencodePath(gencodePath);
				} else {
					if (null == pluginResources) {
						gencodePath = new GencodePath(pluginHome, pluginPackage, getParam(IScenarioConstants.GENCODE_PACKAGE),
								genCodeDir, bundle.getClass().getClassLoader());
					} else {
						gencodePath = pluginResources.getGencodePath();
					}
				}
				if (addBinDirectory())
					gencodePath.getClassPaths().add(new File(pluginHome.concat("/bin")));
				else
					gencodePath.getClassPaths().add(new File(pluginHome));
			} else {
				throw new RuntimeException("Invalid Adichatz bundle '" + pluginName + "'!? No Scenario.xml file has been found.");
			}
		} catch (IOException | CoreException | JAXBException e) {
			throw new RuntimeException(INVALID_SCENARIO_FILE + bundle.getSymbolicName() + "?!", e);
		}
	}

	/**
	 * Gets the plugin name.
	 * 
	 * @param attributes
	 *            the attributes
	 * @return the plugin name
	 */
	private String getPluginName(Attributes attributes) {
		String symbolicName = attributes.getValue("Bundle-SymbolicName");
		if (null == symbolicName)
			return null;
		StringTokenizer tokenizer = new StringTokenizer(symbolicName, ";");
		return tokenizer.nextToken().trim();
	}

	/**
	 * Checks if is synchronized.
	 * 
	 * @return true, if is synchronized
	 */
	protected boolean isSynchronized() {
		if (null == project)
			return true;
		long currentStamp = getXmlFolder().getFile(GeneratorConstants.SCENARIO_FILE).getModificationStamp();
		if (this.currentStamp == currentStamp)
			return true;
		this.currentStamp = currentStamp;
		return false;
	}

	/**
	 * Marshal scenario file.
	 */
	public void marshalScenarioFile() {
		ScenarioUtil.createXmlFile(getScenarioFile().getLocation().toFile(), getScenarioTree());
		try {
			scenarioFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			logError(e);
		}
		currentStamp = scenarioFile.getModificationStamp();
		dirtyScenarioFile = false;
	}

	/**
	 * Compute current stamp.
	 * 
	 * @return the long
	 */
	private long computeCurrentStamp() {
		return getScenarioFile().getModificationStamp();
	}

	/**
	 * Checks if is bundle.
	 * 
	 * @return true, if is bundle
	 */
	public boolean isBundle() {
		return InternalPolicy.OSGI_AVAILABLE && null == project;
	}

	/**
	 * Adds the bin directory.
	 * 
	 * @return true, if successful
	 */
	public boolean addBinDirectory() {
		return !isBundle() || (new File(pluginHome).isDirectory()
				&& new File(pluginHome.concat("/bin/").concat(pluginPackage.replace('.', '/'))).exists());
	}

	/**
	 * Gets the param.
	 * 
	 * @param key
	 *            the key
	 * @return the param
	 */
	public String getParam(String key) {
		return paramMap.get(key);
	}

	/**
	 * Put param.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void putParam(String key, String value) {
		value = ScenarioUtil.evalLocation(getBuffer(), value);
		ParamsType params = getScenarioTree().getParams();
		if (null == params) {
			params = new ParamsType();
			scenarioTree.setParams(params);
		}
		if (paramMap.containsKey(key)) {
			if (!value.equals(paramMap.get(key))) { // No change
				ParamType param = GeneratorUtil.getParam(scenarioTree.getParams(), key);
				if (null != param) {
					param.setValue(value);
					paramMap.put(key, value);
				}
			}
		} else {
			ParamType param = new ParamType();
			param.setId(key);
			param.setValue(value);
			params.getParam().add(param);
			paramMap.put(key, value);
		}
	}

	/**
	 * Gets the controller.
	 *
	 * @param controlWrapper
	 *            the control wrapper
	 * @return the controller
	 */
	public String getController(Object controlWrapper) {
		String controllerClassName = null;
		Class<?> parentWrapperClass = controlWrapper.getClass();
		while (null == controllerClassName && !parentWrapperClass.equals(Object.class)) {
			controllerClassName = controllerMap.get(parentWrapperClass.getName());
			parentWrapperClass = parentWrapperClass.getSuperclass();
		}
		return controllerClassName;
	}

	/**
	 * Gets the wrapper.
	 *
	 * @param gencodePath
	 *            the gencode path
	 * @param controllerClassName
	 *            the controller class name
	 * @return the wrapper
	 */
	public String getWrapper(GencodePath gencodePath, String controllerClassName) {
		if (null == wrapperMap) {
			wrapperMap = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : controllerMap.entrySet())
				wrapperMap.put(entry.getValue(), entry.getKey());
		}
		String wrapperClassName = null;
		Class<?> parentControllerClass = gencodePath.getClazz(controllerClassName);
		while (null == wrapperClassName && !parentControllerClass.equals(Object.class)) {
			wrapperClassName = wrapperMap.get(parentControllerClass.getName());
			parentControllerClass = parentControllerClass.getSuperclass();
		}
		return wrapperClassName;
	}

	/**
	 * Gets the simple controller name.
	 *
	 * @param controlWrapper
	 *            the control wrapper
	 * @param imports
	 *            the imports
	 * @return the simple controller name
	 */
	public String getSimpleControllerName(Object controlWrapper, SortedSet<String> imports) {
		String controllerClassName = getController(controlWrapper);
		imports.add(controllerClassName);
		String simpleControllerName = controllerClassName.substring(controllerClassName.lastIndexOf('.') + 1);
		return simpleControllerName;
	}

	/**
	 * Checks if is dirty scenario file.
	 *
	 * @return true, if is dirty scenario file
	 */
	public boolean isDirtyScenarioFile() {
		return dirtyScenarioFile;
	}

	/**
	 * Sets the dirty scenario file.
	 *
	 * @param dirtyScenarioFile
	 *            the new dirty scenario file
	 */
	public void setDirtyScenarioFile(boolean dirtyScenarioFile) {
		this.dirtyScenarioFile = dirtyScenarioFile;
	}

	/**
	 * Gets the generator map.
	 * 
	 * @param element
	 *            the element
	 * @return the generator map
	 */
	public Class<?> getGenerator(Object element) {
		if (!parametersLoaded)
			loadScenarioParameters();
		return generatorMap.get(element instanceof String ? element : element.getClass().getName());
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @return the plugin resources
	 */
	public AdiPluginResources getPluginResources() {
		if (null == pluginResources) {
			pluginResources = new ScenarioPluginResources(this);
		}

		return pluginResources;
	}

	/**
	 * Gets the generation scenario.
	 *
	 * @return the generation scenario
	 */
	public GenerationScenarioWrapper getGenerationScenario() {
		return (GenerationScenarioWrapper) getScenarioTree().getGenerationScenario();
	}

	/**
	 * Sets the plugin resources.
	 * 
	 * @param pluginResources
	 *            the new plugin resources
	 */
	public void setPluginResources(AdiPluginResources pluginResources) {
		if (!pluginResources.equals(this.pluginResources)) {
			this.pluginResources = pluginResources;
			pluginResources.getGencodePath().getClassPaths().addAll(gencodePath.getClassPaths());
		}
	}

	/**
	 * Gets the plugin home.
	 * 
	 * @return the plugin home
	 */
	public String getPluginHome() {
		return pluginHome;
	}

	/**
	 * Sets the plugin home.
	 * 
	 * @param pluginHome
	 *            the new plugin home
	 */
	public void setPluginHome(String pluginHome) {
		this.pluginHome = pluginHome;
	}

	/**
	 * Gets the plugin name.
	 * 
	 * @return the plugin name
	 */
	public String getPluginName() {
		return pluginName;
	}

	/**
	 * Gets the plugin package.
	 * 
	 * @return the plugin package
	 */
	public String getPluginPackage() {
		return pluginPackage;
	}

	/**
	 * Gets the gencode path.
	 * 
	 * @return the gencode path
	 */
	public GencodePath getGencodePath() {
		return gencodePath;
	}

	/**
	 * Gets the scenario tree.
	 * 
	 * @return the scenario tree
	 */
	public ScenarioTreeWrapper getScenarioTree() {
		if (null == scenarioTree)
			initScenarioResources(pluginName);
		return scenarioTree;
	}

	/**
	 * Gets the connector tree.
	 *
	 * @return the connector tree
	 */
	public ConnectorTreeWrapper getConnectorTree() {
		if (null == connectorTree)
			connectorTree = ScenarioUtil.getConnectorTree(getParam(IScenarioConstants.CONNECTORS_URI));
		return connectorTree;
	}

	/**
	 * Gets the bundle.
	 * 
	 * @return the bundle
	 */
	public Bundle getBundle() {
		return bundle;
	}

	/**
	 * Gets the project.
	 * 
	 * @return the project
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Gets the java project.
	 * 
	 * @return the java project
	 */
	public IJavaProject getJavaProject() {
		if (null == javaProject && null != project)
			try {
				javaProject = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
			} catch (CoreException e) {
				logError(e);
			}
		return javaProject;
	}

	/**
	 * Gets the class path.
	 * 
	 * @return the class path
	 */
	public String getClassPath() {
		return classPath;
	}

	/**
	 * Gets the connector version.
	 * 
	 * @return the connector version
	 */
	public String getConnectorVersion() {
		if (null == connectorVersion) {
			try {
				connectorVersion = manifestChanger.getMainAttributes().getValue(IScenarioConstants.ADICHATZ_CONNECTOR_VERSION);
				if (null == connectorVersion && null != getScenarioTree() && null != getGenerationScenario()
						&& null != getGenerationScenario().getModelPart())
					connectorVersion = getGenerationScenario().getModelPart().getConnectorASVersion();
				if (null == connectorVersion && hasModelPart() && !ACTIVATOR_FIRST_PASSAGE)
					logWarning("Manifest file of project '" + pluginName + "' has no '"
							+ IScenarioConstants.ADICHATZ_CONNECTOR_VERSION + "' attribute!");
			} catch (CoreException | IOException e) {
				logError(e);
			}
		}
		return connectorVersion;
	}

	/**
	 * Gets the manifest changer.
	 *
	 * @return the manifest changer
	 */
	public ManifestChanger getManifestChanger() {
		return manifestChanger;
	}

	/**
	 * Reinit manifest changer.
	 */
	public void reinitManifestChanger() {
		manifestChanger = new ManifestChanger(project, null);
	}

	/**
	 * Load scenario tree.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	public void loadScenarioTree(InputStream inputStream) throws JAXBException {
		scenarioTree = (ScenarioTreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(), inputStream);
		for (PluginEntityType pluginEntity : getGenerationScenario().getPluginEntity()) {
			String[] instanceKeys = EngineTools.getInstanceKeys(pluginEntity.getEntityURI());
			if (null == instanceKeys[0] || instanceKeys[0].equals(pluginName))
				((PluginEntityWrapper) pluginEntity).setScenarioResources(this);
			else {
				((PluginEntityWrapper) pluginEntity)
						.setScenarioResources(ScenarioResources.getInstance(instanceKeys[0], pluginName));
			}
		}
		loadParams(false);
	}

	/**
	 * Load params.
	 *
	 * @param recreateGencodePath
	 *            the recreate gencode path
	 */
	public void loadParams(boolean recreateGencodePath) {
		paramMap = new HashMap<String, String>();
		if (null != scenarioTree.getParams()) {
			for (ParamType param : scenarioTree.getParams().getParam()) {
				if (IScenarioConstants.PLUGIN_PACKAGE.equals(param.getId()) && null != param.getValue()) {
					String currentPluginPackage = pluginPackage;
					pluginPackage = param.getValue();
					if (null != project && null != currentPluginPackage && !currentPluginPackage.equals(pluginPackage)) {
						// Change modelPackageName if modelPackageName is not specified in modelPart.
						if (null != getGenerationScenario().getModelPart())
							getGenerationScenario().getModelPart().setModelPackageName(pluginPackage + ".model");
						try {
							IFolder srcFolder = project.getFolder("src");
							if (srcFolder.exists()) {
								IFile activatorFile = srcFolder.getFile(currentPluginPackage.replace('.', '/') + "/Activator.java");
								if (activatorFile.exists()) {
									activatorFile.delete(true, null);
									createFolderIfNotExist("src/" + pluginPackage.replace('.', '/'));
									new ActivatorGenerator().generateCU(this);
								}
								IContainer container = activatorFile.getParent();
								if (container.exists())
									try {
										while (!srcFolder.equals(container) && 0 == container.members().length) {
											container.delete(true, null);
											container = container.getParent();
										}
									} catch (ResourceException e) { // container.members() could return ResourceException
										logError(e.getLocalizedMessage());
									}

							}
							ManifestChanger manifestChanger = getManifestChanger();
							if (manifestChanger.exist())
								manifestChanger.write();
						} catch (CoreException | IOException e) {
							logError(e);
						}
					}
				} else if (IScenarioConstants.WORKSPACE_DIRECTORY.equals(param.getId()))
					GeneratorConstants.WORKSPACE_DIRECTORY = param.getValue();
				paramMap.put(param.getId(), ScenarioUtil.evalLocation(getBuffer(), param.getValue()));
			}
			// Instantiates a new Plugin resources with parameters values
			if (null == pluginHome)
				pluginHome = FileUtil.getPluginHome(pluginName);
		}
		if (recreateGencodePath)
			createProjectGencodePath();
	}

	/**
	 * Load scenario parameters.
	 */
	public void loadScenarioParameters() {
		parametersLoaded = true;
		/*
		 * set class path for generator
		 */
		if (null == scenarioTree.getPathElements())
			scenarioTree.setPathElements(new PathElementsType());

		List<PathElementType> pathElements = new ArrayList<PathElementType>();
		Set<String> requiredBundleNames = ScenarioUtil.getRequiredBundleNames(getManifestChanger());

		for (String bundleName : requiredBundleNames) {
			PathElementWrapper pathElement = new PathElementWrapper();
			pathElement.setLocation(bundleName);
			pathElement.setAdditionalResourcePath(null != bundle);
			pathElement.setType(PathElementEnum.PLUGIN);
			pathElements.add(pathElement);
		}
		for (PathElementType pathElement : ScenarioUtil.getPathElements(this, scenarioTree.getPathElements().getPathElement()))
			if ((PathElementEnum.PLUGIN != pathElement.getType() || !requiredBundleNames.contains(pathElement.getLocation()))) {
				pathElements.add(pathElement);
				((PathElementWrapper) pathElement).setAdditionalResourcePath(true);
			}

		classPath = addPathElements(pathElements);
		if (null != pluginResources)
			pluginResources.getGencodePath().getClassPaths().addAll(gencodePath.getClassPaths());

		// Sets controllerMap and generatorMap if scenarioResources does not point on a plugin (bundle) but points on a project
		controllerMap = new HashMap<String, String>();
		wrapperMap = null;
		generatorMap = new HashMap<String, Class<?>>();
		if (null == bundle) {
			// Sets controllerMap
			if (null != getScenarioTree().getControllers() && null == bundle)
				for (ControllerType controller : scenarioTree.getControllers().getController()) {
					controllerMap.put(controller.getWrapperClassName(), controller.getControllerClassName());
				}

			// Sets generatorMap
			if (null != scenarioTree.getGenerators() && null == bundle)
				for (GeneratorType generator : scenarioTree.getGenerators().getGenerator()) {
					if ("#KEYWORDGENERATOR()".equals(generator.getTreeClassName()))
						keyWordGenerator = (KeyWordGenerator) gencodePath.instantiateClass(generator.getGeneratorClassName(),
								new Class[] {}, new Object[] {});
					else
						generatorMap.put(generator.getTreeClassName(), gencodePath.getClazz(generator.getGeneratorClassName()));
				}
		}
	}

	/**
	 * Checks for model part.
	 * 
	 * @return true, if successful
	 */
	public boolean hasModelPart() {
		return null != getGenerationScenario().getModelPart();
	}

	/**
	 * Checks for rcp part.
	 * 
	 * @return true, if successful
	 */
	public boolean hasRcpPart() {
		return null != getGenerationScenario().getRcpPart();
	}

	/**
	 * Checks for navigator.
	 *
	 * @return true, if successful
	 */
	public boolean hasNavigator() {
		for (GenerationUnitType generationUnit : getGenerationScenario().getGenerationUnit()) {
			if (GenerationEnum.NAVIGATOR == generationUnit.getType()) {
				IFile axmlFile = ScenarioUtil.getXmlFileFromURI(this, generationUnit.getAdiResourceURI());
				return null != axmlFile;
			}
		}
		return false;
	}

	/**
	 * Checks for plugin enitty tree.
	 *
	 * @return true, if successful
	 */
	public boolean hasPluginEnittyTree() {
		return getSrcGencodeFolder().getFile(EngineConstants.PLUGIN_ENTITY_TREE.concat(".java")).exists();
	}

	/**
	 * Checks if is application.
	 * 
	 * @return true, if is application
	 */
	public boolean isApplication() {
		RcpPartType rcpPart = getGenerationScenario().getRcpPart();
		return null == rcpPart ? false : (null == rcpPart.isApplication() ? false : rcpPart.isApplication());
	}

	/**
	 * Checks for application server.
	 * 
	 * @return true, if successful
	 */
	public boolean hasApplicationServer() {
		String connectionVersion = getConnectorVersion();
		return null != connectionVersion && !IScenarioConstants.JSE.equals(connectionVersion);
	}

	/**
	 * Inits the jpa data access.
	 */
	public void initJPADataAccess() {
		try {
			gencodePath.getClassPaths().add(new File(pluginHome.concat("/bin")));
			List<File> files = new ArrayList<File>();
			String libPath = "resources/lib";
			if (null != project) {
				IFolder libFolder = project.getFolder(libPath);
				for (IResource resources : libFolder.members())
					files.add(new File(resources.getLocation().toOSString()));
				files.add(new File(project.getFolder("bin").getLocation().toOSString()));
			} else if (isBundle()) {
				Enumeration<String> libFiles = bundle.getEntryPaths(libPath);
				while (libFiles.hasMoreElements()) {
					files.add(new File(pluginHome + bundle.getEntry(libFiles.nextElement()).getFile()));
				}
				if (addBinDirectory())
					files.add(new File(pluginHome + bundle.getEntry("bin").getFile()));
				else
					files.add(FileLocator.getBundleFile(bundle));
			} else {
				File libDir = new File(pluginHome + "/" + libPath);
				for (File file : libDir.listFiles())
					files.add(file);
				files.add(new File(pluginHome + "/bin"));
			}
			gencodePath.getClassPaths().addAll(files);

			Class<?> beanInterceptorClass = gencodePath.getClazz(pluginPackage + ".BeanInterceptor");
			IBeanInterceptorFactory beanInterceptorFactory = (IBeanInterceptorFactory) ReflectionTools
					.invokeMethod(ReflectionTools.getMethod(beanInterceptorClass, "createBeanInterceptorFactory"), null);

			@SuppressWarnings("rawtypes")
			Class[] classes = IScenarioConstants.JSE.equals(getConnectorVersion()) ? new Class[] { AdiPluginResources.class }
					: new Class[] {};
			Object[] params = IScenarioConstants.JSE.equals(getConnectorVersion()) ? new Object[] { getPluginResources() }
					: new Object[] {};
			IGatewayConnector gatewayConnector = (IGatewayConnector) gencodePath.instantiateClass(pluginPackage, "JPAConnector",
					classes, params);

			getPluginResources().setDataAccess(new JPADataAccess(pluginResources, beanInterceptorFactory, gatewayConnector));
		} catch (CoreException | IOException e) {
			logError(e);
		}

	}

	/**
	 * Gets the key word generator.
	 * 
	 * @return the key word generator
	 */
	public KeyWordGenerator getKeyWordGenerator() {
		if (null == keyWordGenerator)
			keyWordGenerator = new KeyWordGenerator();
		return keyWordGenerator;
	}

	/**
	 * Gets the model file names.
	 *
	 * @return the model file names
	 */
	public List<String> getModelFileNames() {
		List<String> modelFileNames = new ArrayList<String>();
		final String modelDirName = scenarioTree.getGenerationScenario().getModelPart().getModelPackageName();
		if (null != bundle) {
			int length = modelDirName.length();
			Enumeration<String> libFiles = bundle.getEntryPaths(modelDirName.replace(".", "/"));
			if (null == libFiles) {
				libFiles = bundle.getEntryPaths("src/".concat(modelDirName.replace(".", "/")));
				length = length + 4;
			}
			while (libFiles.hasMoreElements()) {
				String fileName = libFiles.nextElement().substring(length + 1);
				if (fileName.endsWith(".class") || fileName.endsWith(".java")) {
					String entityId = fileName.substring(0, fileName.length() - (fileName.endsWith(".class") ? 6 : 5));
					ModelPartType modelPart = getGenerationScenario().getModelPart();
					if (null == modelPart)
						throw new RuntimeException(getFromGeneratorBundle("scenario.missing.modelPart"));
					String entityClassName = modelPart.getModelPackageName() + "." + entityId;
					Class<?> entityClass = gencodePath.getClazz(entityClassName);
					if (null != entityClass && null != entityClass.getAnnotation(Entity.class))
						modelFileNames.add(fileName);
				}
			}
		} else {
			File modelDir = new File(pluginHome + "/src/" + modelDirName.replace(".", "/"));
			if (!modelDir.exists())
				return modelFileNames;
			modelFileNames = Arrays.asList(modelDir.list(new FilenameFilter() {
				public boolean accept(File dir, String fileName) {
					if (fileName.endsWith(".java")) {
						String entityId = fileName.substring(0, fileName.length() - 5);
						String entityClassName = modelDirName + "." + entityId;
						try {
							IType type = getJavaProject().findType(entityClassName);
							return GeneratorUtil.hasAnnotation(type.getCompilationUnit(), "Entity");
						} catch (JavaModelException e) {
							logError(e);
						}
					}
					return false;
				}
			}));
		}
		Collections.sort(modelFileNames);
		return modelFileNames;
	}

	/**
	 * Gets the ejb jar file name.
	 *
	 * @return the ejb jar file name
	 */
	public String getEjbJarFileName() {
		ModelPartType modelPart = getGenerationScenario().getModelPart();
		return null == modelPart ? null : modelPart.getEjbFileName();
	}

	/**
	 * Gets the project EJB jar file.
	 *
	 * @return the project jar file
	 */
	public String getProjectEJBJarFileName() {
		String ejbJarFileName = getEjbJarFileName();
		return null == ejbJarFileName ? null : pluginHome + "/resources/build/" + ejbJarFileName;
	}

	/**
	 * Gets the plugin entity scenario.
	 * 
	 * @return the plugin entity scenario
	 */
	public IPluginEntityScenario getPluginEntityScenario() {
		if (null == pluginEntityScenario) {
			String pluginScenarioClassName = scenarioTree.getScenarioClassName(GenerationEnum.PLUGIN_ENTITY);
			if (EngineTools.isEmpty(pluginScenarioClassName))
				pluginScenarioClassName = PluginEntityScenario.class.getName();
			pluginEntityScenario = (IPluginEntityScenario) gencodePath.instantiateClass(getComponentClass(pluginScenarioClassName),
					new Class[] { GenerationUnitType.class, ScenarioResources.class }, new Object[] { null, this });
		}
		return pluginEntityScenario;
	}

	/**
	 * Gets the plugin entity.
	 *
	 * @param entityURI
	 *            the entity URI
	 * @return the plugin entity
	 */
	public PluginEntityWrapper getPluginEntityWrapper(String entityURI) {
		if (null == pluginEntityURIMap) {
			pluginEntityURIMap = new HashMap<String, PluginEntityWrapper>();
			for (PluginEntityType pluginEntity : scenarioTree.getGenerationScenario().getPluginEntity())
				pluginEntityURIMap.put(pluginEntity.getEntityURI(), (PluginEntityWrapper) pluginEntity);
		}
		return pluginEntityURIMap.get(entityURI);
	}

	/**
	 * Post save.
	 */
	public void refresh() {
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(pluginName);
		if (null != pluginResources)
			this.pluginResources = pluginResources;
		// Model classes could have been manually changed
		for (PluginEntityType pluginEntity : scenarioTree.getGenerationScenario().getPluginEntity())
			((PluginEntityWrapper) pluginEntity).setBeanClass(null);
		pluginEntityURIMap = null;
		pluginEntityScenario = null;
		loadParams(true);
		if (!scenarioTree.getGenerationScenario().getPluginEntity().isEmpty())
			refreshPluginEntityTree();
		connectorTree = null;
		conditionMap.clear();
	}

	/**
	 * Gets the src gencode folder.
	 *
	 * @return the src gencode folder
	 */
	public IFolder getSrcGencodeFolder() {
		return project.getFolder("resources/gencode/src/".concat(gencodePath.getGencodePackage().replace('.', '/')));
	}

	/**
	 * Gets the bin gencode folder.
	 *
	 * @return the bin gencode folder
	 */
	public IFolder getBinGencodeFolder() {
		return project.getFolder("resources/gencode/bin/".concat(gencodePath.getGencodePackage().replace('.', '/')));
	}

	/**
	 * Gets the condition.
	 *
	 * @param predicate
	 *            the predicate
	 * @return the condition
	 */
	public boolean getCondition(String predicate) {
		if (EngineTools.isEmpty(predicate))
			return true;
		if (inInfly)
			return false;
		inInfly = true;
		BooleanSupplier condition = conditionMap.get(predicate);
		if (null == condition)
			try {
				condition = new InFlyClassBuilder(this, predicate).evaluateSupplier();
				conditionMap.put(predicate, condition);
			} catch (Exception e) {
				logError(e);
				inInfly = false;
				return true;
			}
		inInfly = false;
		return condition.getAsBoolean();
	}

	/**
	 * Creates the folder if not exist.
	 * 
	 * @param pathName
	 *            the path name
	 * @return the i folder
	 */
	public IFolder createFolderIfNotExist(String pathName) {
		if (InternalPolicy.OSGI_AVAILABLE) {
			StringTokenizer tokenizer = new StringTokenizer(pathName, "/");
			String path = null;
			IFolder folder = null;
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken();
				path = null == path ? token : path.concat("/").concat(token);
				folder = project.getFolder(path);
				if (!folder.exists())
					try {
						folder.create(true, true, null);
					} catch (CoreException e) {
						logError(getFromGeneratorBundle("generation.cannot.create.folder", folder.getName()));
						logError(e);
					}
			}
			return folder;
		}
		return null;
	}

	/**
	 * Gets the builds the pojo dir.
	 *
	 * @return the builds the pojo dir
	 */
	public File getBuildPojoDir() {
		if (null == buildPojoDir)
			buildPojoDir = new File(pluginHome.concat("/resources/build/pojo"));
		return buildPojoDir;
	}

	/**
	 * Delete resources.
	 *
	 * @param scenarioInput
	 *            the scenario input
	 * @param isXmlMenuFile
	 *            the is xml menu file
	 */
	public void deleteResources(ScenarioInput scenarioInput, boolean isXmlMenuFile) {
		GenerationScenarioWrapper generationScenario = getGenerationScenario();
		IFile xmlFile = getXmlFile(scenarioInput.getSubPackage(), scenarioInput.getTreeId(), true);
		if (xmlFile.exists()) {
			try {
				generationScenario.deleteJavaFiles(xmlFile);
				xmlFile.delete(true, null);
			} catch (CoreException e) {
				logError(e);
			}
		}
	}

	/**
	 * Gets the jpa connector file.
	 *
	 * @return the jpa connector file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public File getJpaConnectorFile() throws IOException {
		// sets jpaConnectorJarFile
		ApplicationServerType applicationServer = getConnectorTree()
				.getApplicationServer(scenarioTree.getGenerationScenario().getModelPart().getConnectorASVersion());
		String jpaConnectorJarName = GeneratorUtil.getParamValue(applicationServer.getParams(),
				IScenarioConstants.ADICHATZ_CONNECTOR);
		return project.getFile("/" + IScenarioConstants.LIB_DIRECTORY + "/" + jpaConnectorJarName).getLocation().toFile();
	}

	/**
	 * Gets the xml file.
	 *
	 * @param subPackage
	 *            the sub package
	 * @param treeId
	 *            the tree id
	 * @param onlyGenerated
	 *            the only generated
	 * @return the xml file
	 */
	public IFile getXmlFile(String subPackage, String treeId, boolean onlyGenerated) {
		IFolder folder = getXmlFolder().getFolder(null == subPackage ? "" : subPackage.replace('.', '/'));
		IFile xmlFile = null;
		if (!onlyGenerated)
			xmlFile = folder.getFile(treeId.concat(".axml"));
		if (null == xmlFile || !xmlFile.exists())
			xmlFile = folder.getFile(treeId.concat("GENERATED.axml"));
		return xmlFile;
	}

	/**
	 * Gets the xml folder.
	 *
	 * @return the xml folder
	 */
	public IFolder getXmlFolder() {
		if (null == xmlFolder)
			xmlFolder = getProject().getFolder(EngineConstants.XML_FILES_PATH);
		return xmlFolder;
	}

	public IFile getScenarioFile() {
		if (null == scenarioFile)
			scenarioFile = getXmlFolder().getFile(GeneratorConstants.SCENARIO_FILE);
		return scenarioFile;
	}

	/**
	 * Gets the gencode folder.
	 *
	 * @return the gencode folder
	 */
	public IFolder getGencodeFolder() {
		if (null == gencodeFolder)
			gencodeFolder = getProject().getFolder(getPluginResources().getGencodePath().getGencodeRelativePath().concat("/src"));
		return gencodeFolder;
	}

	/**
	 * Gets the java folder.
	 *
	 * @param subPackage
	 *            the sub package
	 * @return the java folder
	 */
	public IFolder getJavaFolder(String subPackage) {
		IFolder rootFolder = getGencodeFolder()
				.getFolder(getPluginResources().getGencodePath().getGencodePackage().replace(".", "/"));
		if (EngineTools.isEmpty(subPackage))
			return rootFolder;
		else
			return rootFolder.getFolder(subPackage.replace(".", "/"));
	}

	/**
	 * Checks for customized value.
	 *
	 * @param pluginEntity
	 *            the plugin entity
	 * @return true, if successful
	 */
	public boolean hasCustomizedValue(PluginEntityWrapper pluginEntity) {
		return false;
	}

	/**
	 * Gets the affected files.
	 *
	 * @return the affected files
	 */
	public Set<IFile> getAffectedFiles() {
		return affectedFiles;
	}

	/**
	 * Gets the erroneous files.
	 *
	 * @return the erroneous files
	 */
	public Set<IFile> getErroneousFiles() {
		return erroneousFiles;
	}

	/**
	 * Incremental build project.
	 */
	public void incrementalBuildProject() {
		if (null != project) {
			try {
				for (IFile file : affectedFiles.toArray(new IFile[affectedFiles.size()])) {
					logTrace(getFromGeneratorBundle("generation.refreshing.file", file.getName()));
					file.refreshLocal(IResource.DEPTH_ZERO, null);
				}
				project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
				logTrace(getFromGeneratorBundle("generation.incremental.build", project.getName()));
				for (IFile affectedFile : affectedFiles) {
					if (affectedFile.exists()) {
						if (IMarker.SEVERITY_ERROR == affectedFile.findMaxProblemSeverity(IMarker.PROBLEM, true,
								IResource.DEPTH_ZERO))
							erroneousFiles.add(affectedFile);
					} else
						logError(getFromEngineBundle("error.file.does.not.exist", affectedFile.getFullPath()));
				}
				affectedFiles.clear();
			} catch (CoreException e) {
				logError(e);
			}
		}
	}

	/**
	 * Gets the accessible adi plugins.
	 *
	 * @return the accessible adi plugins
	 */
	public Map<String, AdiResourceContainer> getAccessibleAdiPlugins() {
		if (null == accessibleAdiPlugins) {
			accessibleAdiPlugins = new HashMap<String, AdiResourceContainer>();
			accessibleAdiPlugins.put(pluginName, new AdiResourceContainer(null, project, pluginPackage, false));
			Set<String> requiredBundleNames = ScenarioUtil.getRequiredBundleNames(new ManifestChanger(project, null));
			for (String bundleName : requiredBundleNames) {
				String gencodePackage = null;
				Bundle requiredBundle = Platform.getBundle(bundleName);
				IProject requiredProject = null;
				if (null != requiredBundle) {
					gencodePackage = requiredBundle.getHeaders().get(IScenarioConstants.ADICHATZ_GENCODE_PACKAGE);
				} else {
					requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
					try {
						gencodePackage = new ManifestChanger(requiredProject, null)
								.getValue(IScenarioConstants.ADICHATZ_GENCODE_PACKAGE);
					} catch (CoreException | IOException e) {
						logError(e);
					}
				}
				if (null != gencodePackage)
					accessibleAdiPlugins.put(bundleName,
							new AdiResourceContainer(requiredBundle, requiredProject, gencodePackage, false));
			}
		}
		return accessibleAdiPlugins;
	}

	/**
	 * Reinit accessible adi plugins.
	 */
	public void reinitAccessibleAdiPlugins() {
		this.accessibleAdiPlugins = null;
	}

	/**
	 * Refresh plugin entity tree.
	 */
	public void refreshPluginEntityTree() {
		if (null != project) {
			String treeClassName = gencodePath.getGencodePackage().concat(".").concat(EngineConstants.PLUGIN_ENTITY_TREE);
			Class<?> treeClass = gencodePath.getClazz(treeClassName, true);
			if (null == treeClass) {
				IFolder gencodeFolder = project.getFolder(EngineConstants.GENCODE_DIR + "src");
				try {
					// No warning, if gencodeFle is empty because it is first step.
					if (gencodeFolder.exists() && 0 < gencodeFolder.members().length)
						logWarning(
								getFromGeneratorBundle("classNotFound.try.incrementalBuild", EngineConstants.PLUGIN_ENTITY_TREE));
				} catch (CoreException e) {
					logError(e);
				}
				IFile treeFile = getGencodeFolder().getFile(treeClassName.replace('.', '/').concat(".java"));
				if (treeFile.exists()) {
					logTrace(getFromGeneratorBundle("generation.refreshing.file", treeFile.getName()));
					try {
						treeFile.touch(null);
						treeFile.refreshLocal(IResource.DEPTH_ZERO, null);
					} catch (CoreException e) {
						logError(e);
					}
				}
			}

		}
		getPluginResources().loadPluginEntities();
	}

	/**
	 * Checks if is parameters loaded.
	 *
	 * @return true, if is parameters loaded
	 */
	public boolean isParametersLoaded() {
		return parametersLoaded;
	}

	/**
	 * Rewrite pojos.
	 *
	 * @param workPluginEntities
	 *            the work plugin entities
	 * @param reinit
	 *            the reinit
	 * @param rewrite
	 *            the rewrite
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void rewritePojos(List<PluginEntityWrapper> workPluginEntities, boolean reinit, boolean rewrite)
			throws IOException, CoreException {
		GenerationScenarioType generationScenario = getGenerationScenario();
		ModelPartType modelPart = generationScenario.getModelPart();
		if (null == modelPart)
			throw new RuntimeException("scenario.missing.modelPart");
		IFolder modelFolder = project.getFolder("src/".concat(modelPart.getModelPackageName().replace('.', '/')));
		modelFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		boolean refresh = false;
		for (PluginEntityWrapper pluginEntity : workPluginEntities) {
			if (!pluginEntity.isProtectPojo()) {
				List<PojoRewriterType> pojoRewriters = new ArrayList<>();
				String beanClassName = modelPart.getModelPackageName() + "." + pluginEntity.getPluginEntityId();
				if (null != modelPart.getPojoRewriters()) {
					for (PojoRewriterType pojoRewriter : modelPart.getPojoRewriters().getPojoRewriter()) {
						if (!EngineTools.isEmpty(pojoRewriter.getRewriterClassName())) {
							if (EngineTools.isEmpty(pojoRewriter.getEntityRegex())
									|| ScenarioUtil.checkStringFilter(pluginEntity.getEntityId(), pojoRewriter.getEntityRegex())) {
								Class<?> beanClass = getGencodePath().getClazz(beanClassName);
								if (EngineTools.isEmpty(pojoRewriter.getPropertyRegex())
										|| ScenarioUtil.checkPropertiesFilter(beanClass, pojoRewriter.getPropertyRegex())) {
									pojoRewriters.add(pojoRewriter);
								}
							}
						} else
							logError(getFromEngineBundle("generation.null.rewiter.class.name", pluginEntity.getEntityURI()));
					}
				}
				if (null != pluginEntity.getPojoRewriters())
					for (PojoRewriterType pojoRewriter : pluginEntity.getPojoRewriters().getPojoRewriter())
						if (null != pojoRewriter.getRewriterClassName())
							pojoRewriters.add(pojoRewriter);
						else
							logError(getFromEngineBundle("generation.null.rewiter.class.name", pluginEntity.getEntityURI()));
				if (reinit) {
					IType beanType = getJavaProject().findType(beanClassName);
					String entityId = pluginEntity.getEntityId();
					if (null != Platform.getBundle("jakarta.xml.bind")) // Java version >= 9 and use jakarta plugin for JEE JAXB.
						addPojoRewriter(entityId, pojoRewriters, JakartaPersistenceRewriter.class.getName());

					if (null == beanType)
						logError("No type find for following bean class: " + beanClassName + "!?");
					else {
						ICompilationUnit compilationUnit = beanType.getCompilationUnit();
						CompilationUnit astRoot = ARewriter.getAstRoot(compilationUnit);
						TypeDeclaration classType = (TypeDeclaration) astRoot.types().get(0);
						for (FieldDeclaration field : classType.getFields()) {
							String fieldName = ((VariableDeclarationFragment) field.fragments().get(0)).getName().getIdentifier();
							PropertyFieldType propertyField = pluginEntity.getPropertyField(fieldName);
							if (null != propertyField) {
								if (!EngineTools.isEmpty(propertyField.getPojoType())) {
									addPojoRewriter(entityId, pojoRewriters, PojoTypeRewriter.class.getName());
									break;
								}
							} else if (null != generationScenario) {
								PropertyFieldType pField = ((GenerationScenarioWrapper) generationScenario)
										.getPropertyField(fieldName);
								if (null != pField && !EngineTools.isEmpty(pField.getPojoType())) {
									addPojoRewriter(entityId, pojoRewriters, PojoTypeRewriter.class.getName());
									break;
								}
							}
						}
					}
				}
				if (rewrite && !pojoRewriters.isEmpty()) {
					rewritePojo(pojoRewriters, (GenerationScenarioWrapper) generationScenario, pluginEntity, beanClassName);
					refresh = true;
				}
			}
		}
		if (refresh) {
			pluginResources.getGencodePath().defineClassLoader(pluginResources.getParentClassLoader());
			modelFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
			ScenarioUtil.waitForEndAutoBuilding(project);
		}
	}

	/**
	 * Adds the pojo rewriter.
	 *
	 * @param entityId
	 *            the entity id
	 * @param pojoRewriters
	 *            the pojo rewriters
	 * @param rewriterClassName
	 *            the rewriter class name
	 */
	private void addPojoRewriter(String entityId, List<PojoRewriterType> pojoRewriters, String rewriterClassName) {
		for (PojoRewriterType pojoRewriter : pojoRewriters)
			if (rewriterClassName.equals(pojoRewriter.getRewriterClassName())) {
				logError(getFromGeneratorBundle("generation.duplicate.rewriter", rewriterClassName, entityId));
				return;
			}
		PojoRewriterType pojoRewriter = new PojoRewriterType();
		pojoRewriter.setRewriterClassName(rewriterClassName);
		pojoRewriters.add(pojoRewriter);
	}

	/**
	 * Rewrite pojo.
	 *
	 * @param pojoRewriters
	 *            the pojo rewriters
	 * @param generationScenario
	 *            the generation scenario
	 * @param pluginEntity
	 *            the plugin entity
	 * @param beanClassName
	 *            the bean class name
	 */
	public void rewritePojo(List<PojoRewriterType> pojoRewriters, GenerationScenarioWrapper generationScenario,
			PluginEntityWrapper pluginEntity, String beanClassName) {
		Collections.sort(pojoRewriters, new Comparator<PojoRewriterType>() {
			@Override
			public int compare(PojoRewriterType o1, PojoRewriterType o2) {
				int priority1 = null == o1.getPriority() ? 0 : o1.getPriority();
				int priority2 = null == o2.getPriority() ? 0 : o2.getPriority();
				return priority1 - priority2;
			}
		});
		for (PojoRewriterType pojoRewriterType : pojoRewriters) {
			try {
				IPojoRewriter pojoRewriter = (IPojoRewriter) gencodePath.instantiateClass(pojoRewriterType.getRewriterClassName(),
						new Class[] {}, new Object[] {});
				try {
					Map<String, Object> params = new HashMap<>();
					params.put(IPojoRewriter.PLUGIN_ENTITY, pluginEntity);
					params.put(IPojoRewriter.BEAN_CLASS_NAME, beanClassName);
					if (null != pojoRewriterType.getParams())
						for (ParamType param : pojoRewriterType.getParams().getParam())
							params.put(param.getId(), param.getValue());
					pojoRewriter.generate(this, generationScenario, params);
				} catch (IOException | CoreException e) {
					logError(e);
				}
				if (null != pluginEntity) {
					PluginEntityWrapper pew = getPluginEntityWrapper(pluginEntity.getEntityURI());
					// pew and pluginEntity could be different
					if (null != pew) {
						pew.setBeanClass(null); // force beanClass reinitialization (PluginEntity and PlugineEntityWrapper)
					}
				}
			} catch (Exception e) {
				logError(e);
			}
		}
	}

	/**
	 * Gets the value of the impactModel property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public boolean isImpactModel() {
		if (impactModel == null) {
			return false;
		} else {
			return impactModel;
		}
	}

	/**
	 * Sets the value of the impactModel property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setImpactModel(Boolean value) {
		this.impactModel = value;
	}

	/**
	 * Gets the value of the impactRcp property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public boolean isImpactRcp() {
		if (impactRcp == null) {
			return false;
		} else {
			return impactRcp;
		}
	}

	/**
	 * Sets the value of the impactRcp property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setImpactRcp(Boolean value) {
		this.impactRcp = value;
	}

	/**
	 * Gets the buffer.
	 *
	 * @return the buffer
	 */
	public BufferCode getBuffer() {
		if (null == buffer)
			buffer = new BufferCode(this);
		return buffer;
	}

	/** The component generation. */
	private ComponentGeneration componentGeneration;

	/**
	 * Gets the component generation.
	 *
	 * @return the component generation
	 */
	public ComponentGeneration getComponentGeneration() {
		if (null == componentGeneration) {
			String componentGenerationURI = getParam(IScenarioConstants.COMPONENT_GENERATION_URI);
			if (EngineTools.isEmpty(componentGenerationURI))
				componentGeneration = new ComponentGeneration(this);
			else {
				Class<?> componentGenerationClass = getComponentClass(componentGenerationURI);
				componentGeneration = (ComponentGeneration) gencodePath.instantiateClass(componentGenerationClass,
						new Class[] { ScenarioResources.class }, new Object[] { this });
			}
		}
		return componentGeneration;
	}

	/**
	 * Gets the component class.
	 *
	 * @param componentClassURI
	 *            the component class URI
	 * @return the component class
	 */
	public Class<?> getComponentClass(String componentClassURI) {
		if (EngineTools.isEmpty(componentClassURI))
			return null;
		String componentClassName;
		if (componentClassURI.startsWith("bundleclass://"))
			try {
				String pathName = null;
				String[] instanceKeys = EngineTools.getContributionURIToStrings(componentClassURI);
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(instanceKeys[0]);
				if (project.exists())
					pathName = project.getLocation().append("bin").toOSString();
				else {
					Bundle bundle = Platform.getBundle(instanceKeys[0]);
					if (null == bundle)
						throw new RuntimeException(getFromEngineBundle("component.generation.uri.not.valid", componentClassURI));
					pathName = FileLocator.getBundleFile(bundle).getPath();
					if (new File(pathName).isDirectory() && new File(pathName.concat("/bin")).exists())
						pathName = pathName.concat("/bin");
				}
				gencodePath.getClassPaths().add(new File(pathName));
				componentClassName = instanceKeys[1];
			} catch (AdichatzErrorException | IOException e) {
				throw new RuntimeException(getFromGeneratorBundle("component.generation.uri.not.valid", componentClassURI), e);
			}
		else
			componentClassName = componentClassURI;
		return gencodePath.getClazz(componentClassName);
	}

	/**
	 * Adds the path elements.
	 *
	 * @param pathElements
	 *            the path elements
	 * @return the string
	 */
	public String addPathElements(List<PathElementType> pathElements) {
		String classPath = "";
		String separator = System.getProperties().getProperty("path.separator");
		for (PathElementType element : pathElements.toArray(new PathElementType[pathElements.size()]))
			if (getCondition(element.getCondition())) {
				try {
					PathElementWrapper pathElement = (PathElementWrapper) element;
					switch (pathElement.getType()) {
					case PLUGIN:
						if (InternalPolicy.OSGI_AVAILABLE) {
							try {
								Bundle bundle = Platform.getBundle(pathElement.getLocation());
								addBundlePathElement(pathElements, pathElement, bundle);
							} catch (IOException e) {
								logError(e);
							}
						} else {
							addFilePath(pathElement);
						}
						break;
					case PROJECT:
						if (InternalPolicy.OSGI_AVAILABLE) {
							IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(pathElement.getLocation());
							if (null == proj || null == proj.getLocation()) {
								boolean projectError = true;
								Bundle bundle = Platform.getBundle(pathElement.getLocation());
								if (null != bundle) {
									projectError = false;
									logWarning(getFromGeneratorBundle("generation.project.not.exists.plugin.assumed",
											pathElement.getLocation()));
									addBundlePathElement(pathElements, pathElement, bundle);
								}
								if (projectError)
									logError(getFromGeneratorBundle("generation.project.not.exists", pathElement.getLocation()));
							} else {
								addBinLibrary(proj, pathElements, pathElement);
							}
						} else {
							if (!addFilePath(pathElement))
								throw new RuntimeException(
										getFromGeneratorBundle("scenario.osgi.project.incompatible", pathElement.getLocation()));
						}
						break;
					case LIBRARY:
					case DIRECTORY:
						try {
							String dirName = ScenarioUtil.evalLocation(getBuffer(), pathElement.getLocation());
							if (new File(dirName).exists())
								pathElement.setPathName(dirName);
							else
							// If scenario is inside a bundle, these directories disappear
							if ((!pathElement.getLocation().equals("#PLUGINHOME()/bin")
									&& !pathElement.getLocation().equals("#PLUGINHOME()/resources/gencode/bin"))
									|| null == getBundle()) {
								boolean displayError = true;
								if (null != getScenarioTree().getActionResources())
									for (CopyResourceType copyResource : scenarioTree.getActionResources().getCopyResource()) {
										if (getCondition(copyResource.getCondition())) {
											String targetURI = copyResource.getTargetURI();
											if (targetURI.equals(pathElement.getLocation())) {
												displayError = false;
												break;
											}
											int index = copyResource.getSourceURI().lastIndexOf('/');
											if (-1 != index) {
												targetURI = targetURI.concat(copyResource.getSourceURI().substring(index));
												if (pathElement.getLocation().equals(targetURI)) {
													displayError = false;
													break;
												}
											}
										}
									}
								if (displayError)
									logError("Directory " + dirName
											+ " does not exist and cannot be added to generator class path!!!");
							}
						} catch (NullPointerException e) {
						}
						break;
					default:
						break;
					}
					if (!EngineTools.isEmpty(pathElement.getPathName())) {
						classPath = classPath + separator + pathElement.getPathName();
						if (pathElement.isAdditionalResourcePath() || pathElement.isAddToManifestFile()
								|| pathElement.isAddToClassPath())
							gencodePath.getClassPaths().add(new File(pathElement.getPathName()));
					}
				} catch (Exception e) {
					logError(e);
				}
			}
		if (!classPath.isEmpty())
			classPath = classPath.substring(separator.length());
		return classPath;
	}

	/**
	 * Adds the file path.
	 *
	 * @param pathElement
	 *            the path element
	 * @return true, if successful
	 */
	public boolean addFilePath(PathElementWrapper pathElement) {
		String fileName = getLibFromDir(GeneratorConstants.PLUGINS_DIRECTORY, pathElement.getLocation(), false);
		if (null != fileName)
			pathElement.setPathName(fileName);
		else {
			fileName = getLibFromDir(GeneratorConstants.WORKSPACE_DIRECTORY, pathElement.getLocation(), true);
			if (null != fileName)
				pathElement.setPathName(fileName);
			else if (null != GeneratorConstants.SECONDARY_WORKSPACE_DIRECTORY) {
				fileName = getLibFromDir(GeneratorConstants.SECONDARY_WORKSPACE_DIRECTORY, pathElement.getLocation(), true);
				if (null != fileName)
					pathElement.setPathName(fileName);
				else
					return false;
			}
		}
		return true;
	}

	/**
	 * Adds the bundle path element.
	 *
	 * @param pathElements
	 *            the path elements
	 * @param pathElement
	 *            the path element
	 * @param bundle
	 *            the bundle
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 * @throws JavaModelException
	 *             the java model exception
	 */
	public void addBundlePathElement(List<PathElementType> pathElements, PathElementWrapper pathElement, Bundle bundle)
			throws IOException, CoreException, JavaModelException {
		String path = "";
		if (null != bundle) {
			path = FileLocator.getBundleFile(bundle).getPath();
			if (new File(path).isDirectory() && new File(path.concat("/bin")).exists())
				path = path.concat("/bin");
		} else {
			IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(pathElement.getLocation());
			if (null == proj || null == proj.getLocation())
				logError("Project '" + pathElement.getLocation() + "' does not exist!? ");
			else
				addBinLibrary(proj, pathElements, pathElement);
		}
		pathElement.setPathName(path);
	}

	/**
	 * Gets the lib from dir.
	 * 
	 * @param dirName
	 *            the dir name
	 * @param bundleName
	 *            the bundle name
	 * @param addBin
	 *            the add bin
	 * 
	 * @return the lib from dir
	 */
	private String getLibFromDir(String dirName, final String bundleName, boolean addBin) {
		for (String fileName : new File(dirName).list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(bundleName) && !name.startsWith(bundleName + ".");
			}
		})) {
			return dirName + "/" + fileName + (addBin && new File(dirName + "/" + fileName + "/bin").exists() ? "/bin" : "");
		}
		return null;

	}

	/**
	 * Adds the bin library.
	 *
	 * @param proj
	 *            the proj
	 * @param pathElements
	 *            the path elements
	 * @param pathElementWrapper
	 *            the path element wrapper
	 * @throws CoreException
	 *             the core exception
	 * @throws JavaModelException
	 *             the java model exception
	 */
	private void addBinLibrary(IProject proj, List<PathElementType> pathElements, PathElementWrapper pathElementWrapper)
			throws CoreException, JavaModelException {
		boolean first = true;
		IJavaProject javaProj = (IJavaProject) proj.getNature(JavaCore.NATURE_ID);
		for (IClasspathEntry entry : javaProj.getRawClasspath()) {
			if (IClasspathEntry.CPE_SOURCE == entry.getEntryKind()) {
				PathElementWrapper pathElement;
				if (first) {
					first = false;
					pathElement = pathElementWrapper;
				} else {
					pathElement = new PathElementWrapper();
					pathElement.setLocation(pathElementWrapper.getLocation());
					pathElement.setType(pathElementWrapper.getType());
					pathElements.add(pathElement);
				}
				if (null == entry.getOutputLocation())
					pathElement.setPathName(proj.getLocation().append("bin").toOSString());
				else
					pathElement
							.setPathName(proj.getLocation().append(entry.getOutputLocation().removeFirstSegments(1)).toOSString());
			}
		}
	}

}