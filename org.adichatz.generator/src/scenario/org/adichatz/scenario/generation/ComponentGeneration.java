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

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.PluginEntityTreeGenerator;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.tools.TransformTreeTools;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.GenerationUnitWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.AddWhenEnum;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PathElementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.PathElementsType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.scenario.IDetailScenario;
import org.adichatz.scenario.IEditorScenario;
import org.adichatz.scenario.IEntityScenario;
import org.adichatz.scenario.IMessageBundleScenario;
import org.adichatz.scenario.IQueryScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ITableScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentGeneration.
 */
@SuppressWarnings("restriction")
public class ComponentGeneration {
	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The template directory name. */
	private String templateDirName;

	/** The project. */
	private IProject project;

	/** The java project. */
	private IJavaProject javaProject;

	/** The sub monitor. */
	private SubMonitor subMonitor;

	/** The generate xml. */
	private boolean generateXml;

	/** The generate java. */
	private boolean generateJava;

	/** The compile. */
	private boolean compile;

	/** The generation scenario. */
	private GenerationScenarioWrapper generationScenario;

	/**
	 * Instantiates a new component generation.
	 * 
	 * @param scenarioResources the scenario resources
	 */
	public ComponentGeneration(ScenarioResources scenarioResources) {
		this.scenarioResources = scenarioResources;
		this.project = scenarioResources.getProject();
	}

	/**
	 * Gets the scenario resources.
	 * 
	 * @return the scenario resources
	 */
	public ScenarioResources getScenarioResources() {
		return scenarioResources;
	}

	/**
	 * Gets the template directory name.
	 * 
	 * @return the template directory name
	 */
	public String getTemplateDirName() {
		if (null == templateDirName)
			templateDirName = FileUtil.getPluginHome(GeneratorConstants.TEMPLATE_BUNDLE) + "/template";
		return templateDirName;
	}

	/**
	 * Gets the project.
	 * 
	 * @return the project
	 */
	public IProject getProject() {
		if (null == project && InternalPolicy.OSGI_AVAILABLE)
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(scenarioResources.getPluginName());
		return project;
	}

	/**
	 * Gets the java project.
	 * 
	 * @return the java project
	 */
	public IJavaProject getJavaProject() {
		if (null == javaProject) {
			try {
				if (project.hasNature(GeneratorConstants.ADICHATZ_NATURE))
					// Java project is already built in previous generation
					// process
					javaProject = scenarioResources.getJavaProject();
				else {
					// Java project has never been built
					IProjectDescription projectDesc = ResourcesPlugin.getWorkspace().newProjectDescription(getProject().getName());
					javaProject = buildJavaProject(projectDesc);
				}
			} catch (CoreException e) {
				logError(e);
			}
		}
		return javaProject;
	}

	/**
	 * Gets the generation scenario.
	 * 
	 * @return the generation scenario
	 */
	public GenerationScenarioWrapper getGenerationScenario() {
		return generationScenario;
	}

	/**
	 * Sets the generation scenario.
	 *
	 * @param generationScenario the new generation scenario
	 */
	public void setGenerationScenario(GenerationScenarioWrapper generationScenario) {
		this.generationScenario = generationScenario;
	}

	/**
	 * Gets the monitor.
	 * 
	 * @return the monitor
	 */
	public IProgressMonitor getSubMonitor() {
		return subMonitor;
	}

	/**
	 * Sets the monitor.
	 *
	 * @param subMonitor the new sub monitor
	 */
	public void setSubMonitor(SubMonitor subMonitor) {
		this.subMonitor = subMonitor;
	}

	/**
	 * Gets the default scenario.
	 * 
	 * @param generationUnit the scenario
	 * @return the default scenario
	 */
	public Object getDefaultScenario(GenerationUnitType generationUnit) {
		Object defaultScenario = null;
		if (null != generationUnit && !EngineTools.isEmpty(generationUnit.getScenarioClassName()))
			defaultScenario = scenarioResources.getGencodePath().instantiateClass(
					scenarioResources.getComponentClass(generationUnit.getScenarioClassName()),
					new Class[] { GenerationUnitType.class }, new Object[] { generationUnit });
		return defaultScenario;
	}

	/**
	 * Generate all.
	 * 
	 * @param generationScenario the generation scenario
	 * @param refresh            the refresh (true ==> ResourceChangeEvent is fired
	 *                           for generated axml file)
	 * @return the sets the
	 */
	public void generateAll(GenerationScenarioWrapper generationScenario, boolean refresh) {
		TransformTreeTools.INCLUDE_TREES.clear();
		generationScenario.reinit();
		if (!scenarioResources.isParametersLoaded())
			scenarioResources.loadScenarioParameters();
		subTask(getFromGeneratorBundle("generation.entity.details"));

		List<GenerationEnum> generationEnums = new ArrayList<GenerationEnum>();
		generationEnums.add(GenerationEnum.NAVIGATOR);
		generationEnums.add(GenerationEnum.QUERY);
		generationEnums.add(GenerationEnum.ENTITY);
		generationEnums.add(GenerationEnum.MESSAGE_BUNDLE);
		generationEnums.add(GenerationEnum.DETAIL);
		generationEnums.add(GenerationEnum.TABLE);
		generationEnums.add(GenerationEnum.EDITOR);
		ScenarioTreeWrapper scenarioTree = scenarioResources.getScenarioTree();
		GenerationScenarioWrapper treeGS = (GenerationScenarioWrapper) scenarioTree.getGenerationScenario();

		scenarioResources.refresh();

		for (GenerationEnum generationEnum : generationEnums) {
			if (GenerationEnum.NAVIGATOR == generationEnum) {
				if (null != generationScenario.getGenerationUnit(GenerationEnum.NAVIGATOR)) {
					for (GenerationUnitType generationUnit : generationScenario.getNavigatorGenerationUnit())
						getUIComponentGeneration().buildNavigator(generationUnit);
					scenarioResources.incrementalBuildProject();
				}
			} else {
				Object defaultScenario = getDefaultScenario(treeGS.getGenerationUnit(generationEnum));
				for (PluginEntityType pluginEntityType : getPluginEntities(scenarioTree, generationScenario, generationEnum)) {
					PluginEntityWrapper pluginEntity = (PluginEntityWrapper) pluginEntityType;
					Object scenario;
					GenerationUnitWrapper gu = (GenerationUnitWrapper) pluginEntity.getGenerationUnit(generationEnum);
					if (null != gu) {
						if (!EngineTools.isEmpty(gu.getScenarioClassName()))
							scenario = scenarioResources.getGencodePath().instantiateClass(
									scenarioResources.getComponentClass(gu.getScenarioClassName()),
									new Class[] { GenerationUnitType.class }, new Object[] { gu });
						else
							scenario = defaultScenario;
						if (null == scenario)
							logError(getFromGeneratorBundle("generation.no.default.scenario", pluginEntity.getEntityURI(),
									generationEnum.name()));
						else {
							try {
								PluginEntity<?> pe = gu.getPluginEntity(pluginEntity, scenarioResources);
								generateXml = generateJava = compile = true;
								ScenarioInput scenarioInput = new ScenarioInput(scenarioResources, pe, generateXml, generateJava,
										compile, refresh);
								String[] resourceKeys = EngineTools.getInstanceKeys(gu.getAdiResourceURI());
								switch (generationEnum) {
								case QUERY:
									((IQueryScenario) scenario)
											.createQueryManager(scenarioInput.setInput(resourceKeys[2], resourceKeys[1]));
									break;
								case ENTITY:
									((IEntityScenario) scenario)
											.createEntityTree(scenarioInput.setInput(resourceKeys[2], resourceKeys[1]));
									break;
								case MESSAGE_BUNDLE:
									scenarioResources.createFolderIfNotExist(EngineConstants.RESOURCE_MESSAGE_DIR);
									((IMessageBundleScenario) scenario).createEntityBundle(scenarioInput.setInput(pe));
									break;
								case DETAIL:
									((IDetailScenario) scenario)
											.createEntityDetailFields(scenarioInput.setInput(resourceKeys[2], resourceKeys[1]));
									break;
								case TABLE:
									((ITableScenario) scenario)
											.createEntityTable(scenarioInput.setInput(resourceKeys[2], resourceKeys[1]));
									break;
								case EDITOR:
									((IEditorScenario) scenario)
											.createEditorScenario(scenarioInput.setInput(resourceKeys[2], resourceKeys[1]));
									break;
								default: // to prevent WARNING
											// (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
									break;
								} // END switch
							} catch (Exception e) {
								logError(e);
							}
						} // END if (null != scenario)
					} // END if (null != gu)
				} // END for (PluginEntityType pluginEntity...
			}
		} // END for (GenerationEnum generationEnum...
	}

	/**
	 * Gets the plugin entities.
	 *
	 * @param scenarioTree       the scenario tree
	 * @param generationScenario the generation scenario
	 * @param generationEnum     the generation enum
	 * @return the plugin entities
	 */
	private List<PluginEntityType> getPluginEntities(ScenarioTree scenarioTree, GenerationScenarioWrapper generationScenario,
			GenerationEnum generationEnum) {
		if (null != generationScenario.getGenerationUnit(generationEnum))
			return scenarioTree.getGenerationScenario().getPluginEntity();
		List<PluginEntityType> pluginEntities = new ArrayList<PluginEntityType>();
		for (PluginEntityType pluginEntity : generationScenario.getPluginEntity()) {
			if (null != ((PluginEntityWrapper) pluginEntity).getGenerationUnit(generationEnum))
				pluginEntities.add(pluginEntity);
		}
		return pluginEntities;
	}

	/**
	 * Builds the plugin files.
	 */
	public void buildActivatorFile() {
		String dirPathName = "src/".concat(scenarioResources.getPluginPackage().replace('.', '/'));
		scenarioResources.createFolderIfNotExist(dirPathName);
		new ActivatorGenerator().generateCU(scenarioResources);
	}

	/**
	 * Builds the build properties file.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void buildBuildPropertiesFile() {
		try {
			FileWriter buildFW = new FileWriter(getScenarioResources().getPluginHome() + "/build.properties");
			StringBuffer buildSB = new StringBuffer();
			buildSB.append("source.. = src/,\\\n");
			buildSB.append("           resources/gencode/src/" + "\n");
			buildSB.append("output.. = bin/,\\" + "\n");
			buildSB.append("           resources/gencode/bin/\n");
			buildSB.append("bin.includes = META-INF/,\\\n");
			if (scenarioResources.hasRcpPart()) {
				buildSB.append("               Application.e4xmi,\\\n");
				buildSB.append("               plugin.xml,\\\n");
				buildSB.append("               " + EngineConstants.XML_FILES_PATH + "/AdichatzRcpConfig.xml,\\\n");
				buildSB.append("               plugin.properties,\\\n");
			}
			IFolder resourcesFolder = project.getFolder("resources");
			try {
				for (IResource resource : resourcesFolder.members()) {
					String name = resource.getName();
					if (!"gencode".equals(name) && !"build".equals(name))
						buildSB.append("               resources/").append(name).append("/,\\\n");
				}
			} catch (CoreException e) {
				logError(e);
			}
			String connectorVersion = null;
			if (scenarioResources.hasModelPart()) {
				connectorVersion = scenarioResources.getConnectorVersion();
				if (connectorVersion.equals(IScenarioConstants.JBOSS_7_1))
					buildSB.append("               log4j.properties,\\\n");
				buildSB.append("               " + EngineConstants.XML_FILES_PATH + "/AdichatzConnectorConfig.xml,\\\n");
			}
			buildSB.append("               .\n");
			if (scenarioResources.hasModelPart() && !connectorVersion.equals(IScenarioConstants.JSE))
				buildSB.append("bin.excludes = bin/" + scenarioResources.getPluginPackage().replace('.', '/') + "/ejb/");
			buildFW.write(buildSB.toString());
			buildFW.close();
			scenarioResources.getProject().getFile("build.properties").refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (IOException | CoreException e) {
			logError(e);
		}
	}

	/**
	 * Creates the manifest file.
	 */
	@SuppressWarnings("deprecation")
	public void buildManifestFile() {
		try {
			subTask(getFromGeneratorBundle("generation.manifest.file"));
			File manifestFile = getProject().getFile("META-INF/MANIFEST.MF").getLocation().toFile();
			Set<String> requiredBundles = new HashSet<>();
			if (!manifestFile.exists()) {
				scenarioResources.createFolderIfNotExist("META-INF");
				FileWriter manifestWriter = new FileWriter(manifestFile);
				StringBuffer manifestSB = new StringBuffer();

				manifestSB.append("Manifest-Version: 1.0\n");
				manifestSB.append(Constants.BUNDLE_MANIFESTVERSION + ": 2\n");
				manifestSB.append(Constants.BUNDLE_VERSION + ": 1.0.0\n");
				manifestSB.append(Constants.BUNDLE_NAME + ": " + scenarioResources.getPluginName() + "\n");
				manifestSB.append(Constants.BUNDLE_SYMBOLICNAME + ": " + scenarioResources.getPluginName() + "\n");
				manifestSB.append(Constants.BUNDLE_ACTIVATOR + ": " + scenarioResources.getPluginPackage() + ".Activator\n");
				manifestSB.append(Constants.BUNDLE_REQUIREDEXECUTIONENVIRONMENT + ": JavaSE-9\n");
				manifestSB.append(Constants.REQUIRE_BUNDLE + ": org.eclipse.core.runtime,\n");
				manifestSB.append(" " + EngineConstants.ENGINE_BUNDLE + ",\n");
				manifestSB.append(" " + EngineConstants.JPA_BUNDLE + ",\n");
				manifestSB.append(" " + GeneratorConstants.GENERATOR_BUNDLE + ",\n");
				manifestSB.append(" " + EngineConstants.COMMON_BUNDLE + ",\n");
				manifestSB.append(" org.adichatz.resources\n");
				manifestWriter.write(manifestSB.toString());
				manifestWriter.close();
				refresh("META-INF");
			}

			ManifestChanger manifestChanger = scenarioResources.getManifestChanger();
			ModelComponentGeneration modelComponent = null;
			boolean hasModelPart = scenarioResources.hasModelPart();
			if (hasModelPart) {
				modelComponent = getModelComponentGeneration();

				manifestChanger.addAttributeElement(Constants.EXPORT_PACKAGE, modelComponent.getModelPackageName());
				manifestChanger.addAttributeElement(Constants.EXPORT_PACKAGE, scenarioResources.getPluginPackage() + ".ejb");

				// Add Bundle-ClassPath and Eclipse-RegisterBuddy attribute for
				// JSE context
				manifestChanger.removeAttribute(Constants.BUNDLE_CLASSPATH);
				IFolder libFolder = getProject().getFolder(IScenarioConstants.LIB_DIRECTORY);
				if (libFolder.exists()) {
					libFolder.refreshLocal(IResource.DEPTH_ONE, null);
					for (IResource resources : libFolder.members())
						if (resources instanceof IFile)
							manifestChanger.addAttributeElement(Constants.BUNDLE_CLASSPATH,
									IScenarioConstants.LIB_DIRECTORY.concat("/").concat(resources.getName()));
				}
				if (IScenarioConstants.JSE.equals(modelComponent.getConnectorVersion())) {
					// for avoiding javax.persistence.PersistenceException: No
					// Persistence provider for EntityManager named XXX
					manifestChanger.addAttributeElement("Eclipse-RegisterBuddy", "org.adichatz.resources");
					// Add jdbc driver
					manifestChanger.addAttributeElement(Constants.BUNDLE_CLASSPATH,
							IScenarioConstants.LIB_DIRECTORY + "/" + modelComponent.getJdbcDriverFile().getName());
				}
			}
			boolean hasRcpPart = scenarioResources.hasRcpPart();
			if (hasRcpPart) {
				// Bundle-SymbolicName
				manifestChanger.replaceAttributeElement(Constants.BUNDLE_SYMBOLICNAME,
						scenarioResources.getPluginName() + ";singleton:=true", 3);

				manifestChanger.addAttributeElement(Constants.BUNDLE_LOCALIZATION, "plugin");

				requiredBundles.addAll(Arrays.asList(new String[] { EngineConstants.ENGINE_E4_BUNDLE,
						GeneratorConstants.TOOL_BUNDLE, "javax.inject", "org.adichatz.css.theme", "org.eclipse.jface",
						"org.eclipse.e4.core.commands", "org.eclipse.e4.core.contexts", "org.eclipse.e4.core.di",
						"org.eclipse.e4.core.services", "org.eclipse.e4.ui.css.core", "org.eclipse.e4.ui.css.swt",
						"org.eclipse.e4.ui.model.workbench", "org.eclipse.e4.ui.services", "org.eclipse.e4.ui.workbench",
						"org.eclipse.e4.ui.workbench.addons.swt", "org.eclipse.e4.ui.workbench.renderers.swt",
						"org.eclipse.e4.ui.workbench.swt", "org.eclipse.osgi.services", "org.eclipse.ui.forms",
						"org.w3c.css.sac" }));
				for (PluginEntityType pluginEntity : scenarioResources.getGenerationScenario().getPluginEntity()) {
					String pluginKey = ((PluginEntityWrapper) pluginEntity).getEntityKeys()[0];
					if (!scenarioResources.getPluginName().equals(pluginKey))
						requiredBundles.add(pluginKey);
				}
				if (scenarioResources.isApplication())
					manifestChanger.addAttributeElement(Constants.BUNDLE_LOCALIZATION, "plugin");
			}
			PathElementsType pathElements = scenarioResources.getScenarioTree().getPathElements();
			for (PathElementType pathElement : ScenarioUtil.getPathElements(scenarioResources, pathElements.getPathElement()))
				if (pathElement.isAddToManifestFile()
						&& (PathElementEnum.PLUGIN == pathElement.getType() || PathElementEnum.PROJECT == pathElement.getType()))
					if (AddWhenEnum.ALL == pathElement.getAddWhen()
							|| (hasModelPart && AddWhenEnum.MODEL == pathElement.getAddWhen())
							|| (hasRcpPart && AddWhenEnum.UI == pathElement.getAddWhen()))
						requiredBundles.add(ScenarioUtil.evalLocation(scenarioResources.getBuffer(), pathElement.getLocation()));
			for (String requiredBundle : requiredBundles)
				manifestChanger.addAttributeElement(Constants.REQUIRE_BUNDLE, requiredBundle);
			String requireBundles = manifestChanger.getMainAttributes().getValue(Constants.BUNDLE_CONTACTADDRESS);
			if (null != requireBundles && -1 == requireBundles.indexOf(","))
				manifestChanger.addAttributeElement(Constants.REQUIRE_BUNDLE, ".");

			manifestChanger.replaceAttributeElement(IScenarioConstants.ADICHATZ_GENCODE_PACKAGE,
					scenarioResources.getPluginPackage(), -1);
			if (hasModelPart)
				manifestChanger.replaceAttributeElement(IScenarioConstants.ADICHATZ_CONNECTOR_VERSION,
						modelComponent.getConnectorVersion(), -1);
			if (hasModelPart || hasRcpPart) {
				manifestChanger.write();
			}
		} catch (IOException |

				CoreException e) {
			logError(e);
		}
		refresh("META-INF");
	}

	/**
	 * Builds the java project.
	 * 
	 * @param projectDesc the project desc
	 * @return the i java project
	 */
	private IJavaProject buildJavaProject(IProjectDescription projectDesc) {
		/*
		 * Defines Natures
		 */
		String[] natures = new String[3];
		natures[0] = "org.eclipse.pde.PluginNature";
		natures[1] = "org.eclipse.jdt.core.javanature";
		natures[2] = GeneratorConstants.ADICHATZ_NATURE;
		projectDesc.setNatureIds(natures);

		/*
		 * Defines builders
		 */
		ICommand[] commands = new ICommand[3];
		commands[0] = projectDesc.newCommand();
		commands[0].setBuilderName("org.eclipse.jdt.core.javabuilder");
		commands[1] = projectDesc.newCommand();
		commands[1].setBuilderName("org.eclipse.pde.ManifestBuilder");
		commands[2] = projectDesc.newCommand();
		commands[2].setBuilderName("org.eclipse.pde.SchemaBuilder");

		projectDesc.setBuildSpec(commands);
		try {
			project.setDescription(projectDesc, subMonitor);

			/*
			 * Defines entries
			 */
			scenarioResources.createFolderIfNotExist("src");
			scenarioResources.createFolderIfNotExist("bin");
			scenarioResources.createFolderIfNotExist("resources/gencode/bin");
			scenarioResources.createFolderIfNotExist("resources/gencode/src");

			javaProject = getScenarioResources().getJavaProject();
			javaProject.open(subMonitor);
			javaProject.setOutputLocation(project.getFolder("bin").getFullPath(), subMonitor);

			List<IClasspathEntry> newEntries = new ArrayList<IClasspathEntry>();
			newEntries.add(JavaCore.newSourceEntry(project.getFullPath().append("src")));
			newEntries.add(JavaCore.newSourceEntry(project.getFullPath().append("resources/gencode/src"), null, null,
					project.getFullPath().append("resources/gencode/bin")));
			newEntries.add(JavaCore.newContainerEntry(new Path(
					"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-12")));
			newEntries.add(JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins")));
			javaProject.setRawClasspath(newEntries.toArray(new IClasspathEntry[newEntries.size()]), subMonitor);

			project.setDescription(projectDesc, subMonitor);
			return javaProject;
		} catch (CoreException e) {
			logError("Java project could not be built!", e);
		}
		return null;
	}

	/**
	 * Generate plugin entity tree class.
	 */
	public void generatePluginEntityTreeClass() {
		ScenarioInput scenarioInput = new ScenarioInput(scenarioResources, EngineConstants.PLUGIN_ENTITY_TREE, "", false, true,
				true, false);
		logInfo(getFromGeneratorBundle("generation.build.class", EngineConstants.PLUGIN_ENTITY_TREE));
		new PluginEntityTreeGenerator(scenarioInput);
	}

	/**
	 * Refresh project.
	 */
	public void refreshProject() {
		if (null != getProject()) {
			logInfo(getFromGeneratorBundle("generation.refreshing.project", getProject().getName()));
			refresh("");
		}
	}

	/**
	 * Refresh.
	 * 
	 * @param resourcePath the resource path
	 */
	public void refresh(String resourcePath) {
		if (null != getProject()) {
			try {
				if (EngineTools.isEmpty(resourcePath) || "/".equals(resourcePath))
					getProject().refreshLocal(IResource.DEPTH_INFINITE, subMonitor);
				else {
					IResource folder = getProject().getFolder(resourcePath);
					if (folder.exists()) {
						logInfo(getFromGeneratorBundle("generation.refreshing.folder", resourcePath));
						folder.refreshLocal(IResource.DEPTH_INFINITE, subMonitor);
					}
				}
			} catch (CoreException e) {
				logError(e);
			}
			ScenarioUtil.waitForEndAutoBuilding(project);
		}
	}

	/**
	 * Sub task.
	 * 
	 * @param taskName the task name
	 */
	public void subTask(String taskName) {
		if (null != subMonitor)
			subMonitor.subTask(taskName);
	}

	/**
	 * Generate.
	 *
	 * @param generation   the generation
	 * @param entityURI    the entity uri
	 * @param generateXml  the generate xml
	 * @param generateJava the generate java
	 * @param compile      the compile
	 */
	public void generate(GenerationEnum generation, String entityURI, boolean generateXml, boolean generateJava, boolean compile) {
		List<PluginEntityType> pluginEntities = new ArrayList<PluginEntityType>();
		for (PluginEntityType pluginEntity : scenarioResources.getGenerationScenario().getPluginEntity())
			if (entityURI.equals(pluginEntity.getEntityURI()))
				pluginEntities.add(pluginEntity);
		generate(generation, pluginEntities, generateXml, generateJava, compile);
	}

	/**
	 * Generate.
	 *
	 * @param generation   the generation
	 * @param generateXml  the generate xml
	 * @param generateJava the generate java
	 * @param compile      the compile
	 */
	public void generate(GenerationEnum generation, boolean generateXml, boolean generateJava, boolean compile) {
		List<PluginEntityType> pluginEntities = scenarioResources.getGenerationScenario().getPluginEntity();
		generate(generation, pluginEntities, generateXml, generateJava, compile);
	}

	/**
	 * Generate.
	 *
	 * @param generationEnum the generation enum
	 * @param pluginEntities the plugin entities
	 * @param generateXml    the generate xml
	 * @param generateJava   the generate java
	 * @param compile        the compile
	 */
	public void generate(GenerationEnum generationEnum, List<PluginEntityType> pluginEntities, boolean generateXml,
			boolean generateJava, boolean compile) {
		this.generateXml = generateXml;
		this.generateJava = generateJava;
		this.compile = compile;
		GenerationScenarioWrapper generationScenario = new GenerationScenarioWrapper();
		GenerationUnitType generationUnit;
		switch (generationEnum) {
		case NAVIGATOR:
			generationScenario.addGenerationUnit(scenarioResources, generationEnum);
			break;
		case ENTITY:
		case MESSAGE_BUNDLE:
		case EDITOR:
		case DETAIL:
		case TABLE:
		case QUERY:
			for (PluginEntityType pe : pluginEntities) {
				PluginEntityWrapper pluginEntity = new PluginEntityWrapper(pe);

				generationUnit = ((PluginEntityWrapper) pe).getGenerationUnit(generationEnum);
				if (null != generationUnit) {
					pluginEntity.getGenerationUnit().add(generationUnit);
				}
				generationScenario.addPluginEntity(pluginEntity);
			}
			break;
		default: // to prevent WARNING
					// (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
			break;
		}

		scenarioResources.createFolderIfNotExist("bin");
		scenarioResources.createFolderIfNotExist("resources/gencode/bin");
		if (!scenarioResources.isParametersLoaded())
			scenarioResources.loadScenarioParameters();
		refresh("src/".concat(scenarioResources.getPluginPackage().replace('.', '/')));
		generateAll(generationScenario, false);

		refreshProject();
	}

	private UIComponentGeneration uiComponentGeneration;

	private ModelComponentGeneration modelComponentGeneration;

	public ModelComponentGeneration getModelComponentGeneration() {
		if (null == modelComponentGeneration) {
			modelComponentGeneration = new ModelComponentGeneration(this, null);
		}
		return modelComponentGeneration;
	}

	public UIComponentGeneration getUIComponentGeneration() {
		if (null == uiComponentGeneration) {
			uiComponentGeneration = new UIComponentGeneration(this, null);
		}
		return uiComponentGeneration;
	}
}
