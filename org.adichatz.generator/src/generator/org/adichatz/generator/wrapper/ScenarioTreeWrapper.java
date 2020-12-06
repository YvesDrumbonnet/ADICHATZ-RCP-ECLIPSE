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
package org.adichatz.generator.wrapper;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.xjc.ActionResourcesType;
import org.adichatz.generator.xjc.ActionWhenTypeEnum;
import org.adichatz.generator.xjc.AddWhenEnum;
import org.adichatz.generator.xjc.ControllerType;
import org.adichatz.generator.xjc.ControllersType;
import org.adichatz.generator.xjc.CopyResourceType;
import org.adichatz.generator.xjc.CustomizedScenarioType;
import org.adichatz.generator.xjc.CustomizedScenariosType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GeneratorType;
import org.adichatz.generator.xjc.GeneratorsType;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PathElementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.PathElementsType;
import org.adichatz.generator.xjc.RemoveResourceType;
import org.adichatz.generator.xjc.ReplacementType;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.generator.xjc.ScenarioType;
import org.adichatz.generator.xjc.ScenariosType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ActionResourceManager;
import org.adichatz.scenario.generation.ManifestChanger;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioTreeWrapper.
 */
public class ScenarioTreeWrapper extends ScenarioTree {
	/** The config file names. */
	public static String[] CONFIG_FILE_NAMES = new String[] { "Application.e4xmi", "plugin.properties", "plugin.xml" };

	/** The application file names. */
	public static String[] APPLICATION_FILE_NAMES = new String[] { "StartupLifeCycleHandler.java" };

	private Set<String> libClassPaths;

	@Override
	public GeneratorsType getGenerators() {
		if (null == generators)
			generators = new GeneratorsType();
		return generators;
	}

	@Override
	public ScenariosType getScenarios() {
		if (null == scenarios)
			scenarios = new ScenariosType();
		return scenarios;
	}

	@Override
	public ControllersType getControllers() {
		if (null == controllers)
			controllers = new ControllersType();
		return controllers;
	}

	@Override
	public PathElementsType getPathElements() {
		if (null == pathElements)
			pathElements = new PathElementsType();
		return pathElements;
	}

	@Override
	public ParamsType getParams() {
		if (null == params)
			params = new ParamsType();
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.xjc.ScenarioTree#getGenerationScenario()
	 */
	@Override
	public GenerationScenarioType getGenerationScenario() {
		if (null == generationScenario)
			generationScenario = new GenerationScenarioWrapper();
		return generationScenario;
	}

	@Override
	public CustomizedScenariosType getCustomizedScenarios() {
		return customizedScenarios;
	}

	/**
	 * Merge customization.
	 *
	 * @param customizationScenarioTree
	 *            the customization scenario tree
	 * @param part
	 *            the part
	 */
	public void mergeCustomization(ScenarioResources scenarioResources, ScenarioTreeWrapper customizationScenarioTree, int part) {
		if (null != customizationScenarioTree.getGenerationScenario() && null == generationScenario)
			generationScenario = new GenerationScenarioWrapper();
		if (0 != (part & IPluginEntityScenario.INIT)) {
			/*
			 * Inject customized params.
			 */
			if (!customizationScenarioTree.getParams().getParam().isEmpty()) {
				Map<String, ParamType> customizedParams = new HashMap<>();
				if (null == params)
					params = new ParamsType();
				for (ParamType param : customizationScenarioTree.getParams().getParam()) {
					boolean addParam = false;
					if (EngineTools.isEmpty(param.getType()) || "ALL".equals(param.getType()))
						addParam = true;
					else {
						if ("MODEL".equals(param.getType()))
							addParam = scenarioResources.hasModelPart();
						else if ("RUNTIME".equals(param.getType()))
							addParam = scenarioResources.hasRcpPart();
						else
							logError(getFromGeneratorBundle("scenario.invalid.param.type", scenarioResources.getPluginName(),
									param.getId(), param.getType()));
					}
					if (addParam)
						customizedParams.put(param.getId(), param);
				}
				for (ParamType param : params.getParam()) {
					ParamType customizedParam = customizedParams.get(param.getId());
					if (null != customizedParam) {
						customizedParams.remove(param.getId());
						param.setType(customizedParam.getType());
						param.setValue(new KeyWordGenerator().evalExpressionSlash(customizedParam.getValue()));
					}
				}
				for (ParamType param : customizedParams.values()) {
					param.setValue(new KeyWordGenerator().evalExpressionSlash(param.getValue()));
					params.getParam().add(param);
				}
				customizedParams.clear();
			}

			/*
			 * Inject customized scenarios.
			 */
			if (!customizationScenarioTree.getScenarios().getScenario().isEmpty()) {
				Map<GenerationEnum, ScenarioType> customizedScenarios = new HashMap<>();
				if (null == scenarios)
					scenarios = new ScenariosType();
				for (ScenarioType scenario : customizationScenarioTree.getScenarios().getScenario()) {
					customizedScenarios.put(scenario.getType(), scenario);
				}
				for (ScenarioType scenario : scenarios.getScenario()) {
					ScenarioType customizedScenario = customizedScenarios.get(scenario.getType());
					if (null != customizedScenario) {
						customizedScenarios.remove(scenario.getType());
						scenario.setScenarioClassName(customizedScenario.getScenarioClassName());
					}
				}
				for (ScenarioType generator : customizedScenarios.values()) {
					scenarios.getScenario().add(generator);
				}
				customizedScenarios.clear();
			}

			/*
			 * Inject customized generators.
			 */
			if (!customizationScenarioTree.getGenerators().getGenerator().isEmpty()) {
				Map<String, GeneratorType> customizedGenerators = new HashMap<>();
				if (null == generators)
					generators = new GeneratorsType();
				for (GeneratorType generator : customizationScenarioTree.getGenerators().getGenerator()) {
					customizedGenerators.put(generator.getTreeClassName(), generator);
				}
				for (GeneratorType generator : generators.getGenerator()) {
					GeneratorType customizedGenerator = customizedGenerators.get(generator.getTreeClassName());
					if (null != customizedGenerator) {
						customizedGenerators.remove(generator.getTreeClassName());
						generator.setGeneratorClassName(customizedGenerator.getGeneratorClassName());
					}
				}
				for (GeneratorType generator : customizedGenerators.values()) {
					generators.getGenerator().add(generator);
				}
				customizedGenerators.clear();
			}

			/*
			 * Inject customized pathElements.
			 */
			if (!customizationScenarioTree.getPathElements().getPathElement().isEmpty()) {
				Map<String, PathElementType> customizedPathElements = new HashMap<>();
				if (null == pathElements)
					pathElements = new PathElementsType();
				for (PathElementType pathElement : customizationScenarioTree.getPathElements().getPathElement()) {
					translatePathElement(scenarioResources, pathElement);
					customizedPathElements.put(pathElement.getLocation(), pathElement);
				}
				for (PathElementType pathElement : pathElements.getPathElement()) {
					PathElementType customizedPathElement = customizedPathElements.get(pathElement.getLocation());
					if (null != customizedPathElement) {
						customizedPathElements.remove(pathElement.getLocation());
						pathElement.setLocation(customizedPathElement.getLocation());
					}
				}
				for (PathElementType pathElement : customizedPathElements.values()) {
					pathElements.getPathElement().add(pathElement);
				}
				customizedPathElements.clear();
				processAdditionalLibraries(scenarioResources, null);
			}

			/*
			 * Inject customized controllers.
			 */
			if (!customizationScenarioTree.getControllers().getController().isEmpty()) {
				Map<String, ControllerType> customizedControllers = new HashMap<>();
				if (null == controllers)
					controllers = new ControllersType();
				for (ControllerType controller : customizationScenarioTree.getControllers().getController()) {
					customizedControllers.put(controller.getWrapperClassName(), controller);
				}
				for (ControllerType controller : controllers.getController()) {
					ControllerType customizedController = customizedControllers.get(controller.getWrapperClassName());
					if (null != customizedController) {
						customizedControllers.remove(controller.getWrapperClassName());
						controller.setWrapperClassName(customizedController.getWrapperClassName());
					}
				}
				for (ControllerType controller : customizedControllers.values()) {
					controllers.getController().add(controller);
				}
				customizedControllers.clear();
			}
			/*
			 * Inject customized Action resources.
			 */
			if (null != customizationScenarioTree.getActionResources()) {
				actionResources = getForcedActionResources();
				if (!customizationScenarioTree.getActionResources().getCopyResource().isEmpty()) {
					Map<String, CopyResourceType> copyResources = new HashMap<>();
					for (CopyResourceType copyResource : customizationScenarioTree.getActionResources().getCopyResource()) {
						copyResources.put(copyResource.getSourceURI(), copyResource);
					}
					for (CopyResourceType copyResource : actionResources.getCopyResource()) {
						CopyResourceType customizedCopyResource = copyResources.get(copyResource.getSourceURI());
						if (null != customizedCopyResource) {
							copyResources.remove(copyResource.getSourceURI());
							copyResource.setTargetURI(customizedCopyResource.getTargetURI());
						}
					}
					for (CopyResourceType copyResource : copyResources.values()) {
						actionResources.getCopyResource().add(copyResource);
					}
					copyResources.clear();
				}
				if (!customizationScenarioTree.getActionResources().getRemoveResource().isEmpty()) {
					Map<String, RemoveResourceType> removeResources = new HashMap<>();
					for (RemoveResourceType removeResource : customizationScenarioTree.getActionResources().getRemoveResource()) {
						removeResources.put(removeResource.getTargetURI(), removeResource);
						CopyResourceType deletedCopyResource = null;
						for (CopyResourceType copyResource : actionResources.getCopyResource())
							if (removeResource.getTargetURI().equals(copyResource.getTargetURI())) {
								deletedCopyResource = copyResource;
								break;
							}
						if (null == deletedCopyResource)
							actionResources.getCopyResource().remove(deletedCopyResource);
					}
					for (RemoveResourceType removeResource : actionResources.getRemoveResource()) {
						RemoveResourceType customizedRemoveResource = removeResources.get(removeResource.getTargetURI());
						if (null != customizedRemoveResource) {
							removeResources.remove(removeResource.getTargetURI());
							removeResource.setTargetURI(customizedRemoveResource.getTargetURI());
						}
					}
					for (RemoveResourceType removeResource : removeResources.values()) {
						actionResources.getRemoveResource().add(removeResource);
					}
					removeResources.clear();
				}
			}
		} // END if (0 != (part & IPluginEntityScenario.INIT))
		if ((0 != (part & IPluginEntityScenario.RCP_PART) || 0 != (part & IPluginEntityScenario.ENTITY))
				&& null != customizationScenarioTree.getGenerationScenario()) {
			if (0 != (part & IPluginEntityScenario.RCP_PART)
					&& null != customizationScenarioTree.getGenerationScenario().getRcpPart())
				generationScenario.setRcpPart(customizationScenarioTree.getGenerationScenario().getRcpPart());
			((GenerationScenarioWrapper) generationScenario).mergeCustomization(customizationScenarioTree.getGenerationScenario(),
					scenarioResources.getPluginName(), part);
		}
	}

	private void translatePathElement(ScenarioResources scenarioResources, PathElementType pathElement) {
		if (pathElement.isAddToManifestFile() && PathElementEnum.LIBRARY == pathElement.getType()) {
			String location = pathElement.getLocation();
			if (!location.startsWith("#PLUGINHOME()") && !location.startsWith("resources/lib")
					&& !location.startsWith("/resources/lib")) {
				location = ScenarioUtil.evalLocation(scenarioResources.getBuffer(), location);
				File srcFile = new File(location);
				if (srcFile.exists()) {
					pathElement.setLocation("#PLUGINHOME()/resources/lib/" + srcFile.getName());
					scenarioResources.createFolderIfNotExist("resources/lib");
					FileUtil.copyFile(srcFile,
							new File(ScenarioUtil.evalLocation(scenarioResources.getBuffer(), pathElement.getLocation())), true);
				} else
					logError(getFromGeneratorBundle("path.element.invalid.location", location));
			}
		}
	}

	public void processAdditionalLibraries(ScenarioResources scenarioResources, AddWhenEnum addWhen) {
		libClassPaths = null;
		if (null != pathElements && scenarioResources.getProject().getFile("META-INF/MANIFEST.MF").exists()) {
			ManifestChanger manifestChanger = null;
			Set<String> classpathEntries = new HashSet<>();
			for (PathElementType pathElement : ScenarioUtil.getPathElements(scenarioResources, pathElements.getPathElement()))
				if (Utilities.equals(pathElement.getAddWhen(), addWhen)
						&& scenarioResources.getCondition(pathElement.getCondition())) {
					if (pathElement.isAddToClassPath() || pathElement.isAddToManifestFile()) {
						if (pathElement.isAddToManifestFile()) {
							boolean writeManifest = false;
							if (null == manifestChanger) {
								manifestChanger = scenarioResources.getManifestChanger();
								manifestChanger.clear();
							}
							switch (pathElement.getType()) {
							case DIRECTORY:
								logError(getFromGeneratorBundle("path.element.add.directory"));
								break;
							case PLUGIN:
							case PROJECT:
								try {
									if (manifestChanger.addAttributeElement(Constants.REQUIRE_BUNDLE,
											ScenarioUtil.evalLocation(scenarioResources.getBuffer(), pathElement.getLocation())))
										writeManifest = true;
								} catch (IOException | CoreException e) {
									logError(e);
								}
								break;
							case LIBRARY:
								try {
									manifestChanger.addAttributeElement(Constants.BUNDLE_CLASSPATH,
											ScenarioUtil.evalLocation(scenarioResources.getBuffer(), pathElement.getLocation()));
									writeManifest = true;
								} catch (IOException | CoreException e) {
									logError(e);
								}
								break;
							default:
								break;
							}
							if (writeManifest)
								try {
									manifestChanger.write();
								} catch (IOException | CoreException e) {
									logError(e);
								}
						}
						if (pathElement.isAddToClassPath()) {
							String path = ScenarioUtil.evalLocation(scenarioResources.getBuffer(), pathElement.getLocation());
							if (!getLibClasspaths(scenarioResources).contains(path))
								classpathEntries.add(path);
						}
					}
				}
			if (!classpathEntries.isEmpty()) {
				try {
					List<IClasspathEntry> currentEntries = new ArrayList<>();
					currentEntries.addAll(Arrays.asList(scenarioResources.getJavaProject().getRawClasspath()));
					for (String entry : classpathEntries) {
						IFile file = scenarioResources.getProject().getFile(entry);
						IPath path = file.getFullPath();
						IClasspathEntry classpathEntry = JavaCore.newLibraryEntry(path, null, null);
						currentEntries.add(classpathEntry);
					}
					scenarioResources.getJavaProject()
							.setRawClasspath(currentEntries.toArray(new IClasspathEntry[classpathEntries.size()]), null);
				} catch (JavaModelException e) {
					logError(e);
				}
			}
		}
	}

	private Set<String> getLibClasspaths(ScenarioResources scenarioResources) {
		if (null == libClassPaths) {
			libClassPaths = new HashSet<>();
			try {
				for (IClasspathEntry entry : scenarioResources.getJavaProject().getRawClasspath())
					if (IClasspathEntry.CPE_LIBRARY == entry.getEntryKind())
						libClassPaths.add(entry.getPath().makeRelativeTo(scenarioResources.getProject().getFullPath()).toString());
			} catch (JavaModelException e) {
				logError(e);
			}
		}
		return libClassPaths;
	}

	public CustomizedScenarioType addCustomizedScenario(String scenarioFile) {
		if (null == customizedScenarios)
			customizedScenarios = new CustomizedScenariosType();
		CustomizedScenarioType customizedScenario = new CustomizedScenarioType();
		customizedScenario.setScenarioFile(scenarioFile);
		customizedScenario.setMergeDate(EngineTools.getXMLGregorianCalendar(new Date()));
		customizedScenarios.getCustomizedScenario().add(customizedScenario);
		return customizedScenario;
	}

	@SuppressWarnings("restriction")
	public void addModelResources(ScenarioResources scenarioResources) {
		try {
			File commonPlugin;
			if (InternalPolicy.OSGI_AVAILABLE)
				commonPlugin = FileLocator.getBundleFile(Platform.getBundle(EngineConstants.COMMON_BUNDLE));
			else
				commonPlugin = new File(FileUtil.getPluginHome(EngineConstants.COMMON_BUNDLE));
			boolean addBin = !commonPlugin.getPath().endsWith(".jar");

			ModelPartType modelPart = generationScenario.getModelPart();
			String packageName = scenarioResources.getParam(IScenarioConstants.PLUGIN_PACKAGE);
			String targetSourceDir = "#PLUGINHOME()/src/" + packageName.replace('.', '/');
			String ejbDir = targetSourceDir + "/ejb/";
			String connectorVersion = modelPart.getConnectorASVersion();
			Set<String> sources = new HashSet<>();
			for (CopyResourceType copyResource : getForcedActionResources().getCopyResource())
				sources.add(copyResource.getSourceURI() + "#" + copyResource.getActionWhen().name());
			if (IScenarioConstants.JSE.equals(connectorVersion)) {
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JPAConnector-jse.java",
						targetSourceDir + "/JPAConnector.java", new String[] { "#{adichatz.package.name}" },
						new String[] { packageName }, true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JSEPersistenceManager.java",
						ejbDir + "/JSEPersistenceManager.java",
						new String[] { "#{adichatz.persistence.unit}", "#{adichatz.package.name}" },
						new String[] { modelPart.getDataSourceUnit(), packageName }, false, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JSELockManager.java",
						ejbDir + "/JSELockManager.java", new String[] { "#{adichatz.package.name}" }, new String[] { packageName },
						false, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
			} else {
				String moduleName = modelPart.getEjbFileName();
				moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
				if (IScenarioConstants.JBOSS_7_1.equals(connectorVersion))
					addCopyResources(sources, "#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/config/log4j.properties",
							"#PLUGINHOME()/log4j.properties", null, null, true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				// Copy JPA Connector class from template
				if (ScenarioUtil.isWildfly14Version(connectorVersion))
					addCopyResources(sources,
							"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JPAConnector-wildfly14.java",
							targetSourceDir + "/JPAConnector.java",
							new String[] { "#{adichatz.persistence.manager}", "#{adichatz.lock.manager}",
									"#{adichatz.package.name}", "#{adichatz.module.name}", "#{adichatz.project.name}" },
							new String[] { modelPart.getPersistenceManagerClassName(), modelPart.getLockManagerClassName(),
									packageName, moduleName, scenarioResources.getPluginName() },
							true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				else if (ScenarioUtil.isWildflyNewVersion(connectorVersion))
					addCopyResources(sources,
							"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JPAConnector-wildfly11.java",
							targetSourceDir + "/JPAConnector.java",
							new String[] { "#{adichatz.persistence.manager}", "#{adichatz.lock.manager}",
									"#{adichatz.package.name}", "#{adichatz.module.name}", "#{adichatz.project.name}" },
							new String[] { modelPart.getPersistenceManagerClassName(), modelPart.getLockManagerClassName(),
									packageName, moduleName, scenarioResources.getPluginName() },
							true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				else
					addCopyResources(sources,
							"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JPAConnector-JBOSS71.java",
							targetSourceDir + "/JPAConnector.java",
							new String[] { "#{adichatz.persistence.manager}", "#{adichatz.lock.manager}",
									"#{adichatz.package.name}", "#{adichatz.module.name}", "#{adichatz.project.name}" },
							new String[] { modelPart.getPersistenceManagerClassName(), modelPart.getLockManagerClassName(),
									packageName, moduleName, scenarioResources.getPluginName() },
							true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/InitEJBConnection.java",
						ejbDir + "/InitEJBConnection.java", new String[] { "#{adichatz.package.name}" },
						new String[] { packageName }, true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/InitEJBConnectionBean.java",
						ejbDir + "/InitEJBConnectionBean.java", new String[] { "#{adichatz.package.name}" },
						new String[] { packageName }, true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JEEPersistenceManager.java",
						ejbDir + "/" + modelPart.getPersistenceManagerClassName() + ".java",
						new String[] { "#{adichatz.persistence.manager}", "#{adichatz.persistence.unit}",
								"#{adichatz.lock.manager}", "#{adichatz.package.name}", "#{adichatz.module.name}" },
						new String[] { modelPart.getPersistenceManagerClassName(), modelPart.getDataSourceUnit(),
								modelPart.getLockManagerClassName(), packageName, moduleName },
						true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				addCopyResources(sources,
						"#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/JEELockManagerBean.java",
						ejbDir + "/" + modelPart.getLockManagerClassName() + ".java",
						new String[] { "#{adichatz.lock.manager}", "#{adichatz.package.name}" },
						new String[] { modelPart.getLockManagerClassName(), packageName }, true,
						ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
				String sourceURI = addBin ? "#PLUGINBIN(" + EngineConstants.COMMON_BUNDLE + ")/org/adichatz/common/ejb"
						: "#PLUGINBIN(" + EngineConstants.COMMON_BUNDLE + ")/org/adichatz/common/ejb";
				addCopyResources(sources, sourceURI, "#EJBJARFILE()!/org/adichatz/common/ejb", new String[] {}, new String[] {},
						true, ActionWhenTypeEnum.WHEN_BUILDING_EJB_JAR);
				sourceURI = addBin ? "#PLUGINBIN(" + EngineConstants.COMMON_BUNDLE + ")/org/adichatz/common/ejb"
						: "#PLUGINHOME(" + EngineConstants.COMMON_BUNDLE + ")!/org/adichatz/common/ejb";
				String connectorFile = scenarioResources.getJpaConnectorFile().getAbsolutePath();
				addCopyResources(sources, connectorFile + "!/org/adichatz/hibernate/ejb",
						"#EJBJARFILE()!/org/adichatz/hibernate/ejb", new String[] {}, new String[] {}, true,
						ActionWhenTypeEnum.WHEN_BUILDING_EJB_JAR);
				addCopyResources(sources, connectorFile + "!/org/adichatz/hibernate/common",
						"#EJBJARFILE()!/org/adichatz/hibernate/common", new String[] {}, new String[] {}, true,
						ActionWhenTypeEnum.WHEN_BUILDING_EJB_JAR);
			}
			addCopyResources(sources, "#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/src/BeanInterceptor.java",
					targetSourceDir + "/BeanInterceptor.java", new String[] { "#{adichatz.package.name}" },
					new String[] { packageName }, true, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
		} catch (IOException e) {
			logError(e);
		}
	}

	private void addCopyResources(Set<String> sources, String sourceURI, String targetURI, String[] tokens, String[] values,
			boolean force, ActionWhenTypeEnum actionWhenType) {
		if (!sources.contains(sourceURI + "#" + actionWhenType.name())) {
			CopyResourceType copyResources = new CopyResourceType();
			copyResources.setSourceURI(sourceURI);
			copyResources.setTargetURI(targetURI);
			if (tokens.length == values.length) {
				copyResources.setForce(force);
				copyResources.setActionWhen(actionWhenType);
				getForcedActionResources().getCopyResource().add(copyResources);
				for (int i = 0; i < tokens.length; i++) {
					ReplacementType replacement = new ReplacementType();
					replacement.setToken(tokens[i]);
					replacement.setValue(values[i]);
					copyResources.getReplacement().add(replacement);
				}
			} else
				logError(getFromGeneratorBundle("invalid.tokens.copy.resources", targetURI));
		}
	}

	public ActionResourcesType getForcedActionResources() {
		if (null == actionResources) {
			actionResources = new ActionResourcesType();
			setActionResources(actionResources);
		}
		return actionResources;
	}

	public void actionResource(ScenarioResources scenarioResources, ActionWhenTypeEnum actionWhen) {
		if (null != actionResources) {
			ActionResourceManager actionResourceManager = new ActionResourceManager(scenarioResources);
			Set<File> changedFiles = new HashSet<>();
			for (CopyResourceType copyResource : actionResources.getCopyResource()) {
				if (actionWhen == copyResource.getActionWhen() && scenarioResources.getCondition(copyResource.getCondition())) {
					changedFiles.addAll(actionResourceManager.copyResource(copyResource));
				}
			}
			for (RemoveResourceType removeResource : actionResources.getRemoveResource()) {
				if (actionWhen == removeResource.getActionWhen() && scenarioResources.getCondition(removeResource.getCondition()))
					actionResourceManager.removeResource(removeResource);
			}
			Set<IContainer> changedFolders = new HashSet<>();
			for (File file : changedFiles) {
				if (file.exists()) {
					IFile changedFile = ScenarioUtil.getIFileFromFile(file);
					if (!changedFile.exists())
						changedFolders.add(changedFile.getParent());
				}
			}
			for (IContainer container : changedFolders)
				try {
					container.refreshLocal(IResource.DEPTH_ONE, null);
				} catch (CoreException e) {
					logError(e);
				}
		}
	}

	public List<RemoveResourceType> getAllActionResources() {
		List<RemoveResourceType> allActionResources = new ArrayList<>();
		if (null != actionResources) {
			allActionResources.addAll(actionResources.getCopyResource());
			allActionResources.addAll(actionResources.getRemoveResource());
		}
		return allActionResources;
	}

	public String getScenarioClassName(GenerationEnum generationEnum) {
		for (ScenarioType scenario : getScenarios().getScenario())
			if (generationEnum.equals(scenario.getType())) {
				return scenario.getScenarioClassName();
			}
		return null;

	}
}
