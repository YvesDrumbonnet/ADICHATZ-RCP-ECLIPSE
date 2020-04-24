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
package org.adichatz.scenario.util;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.xml.bind.JAXBException;

import org.adichatz.engine.cache.BeanUtils;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.xjc.AdichatzConnectorConfigTree;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IParamsContainer;
import org.adichatz.generator.xjc.AddWhenEnum;
import org.adichatz.generator.xjc.ConnectorTree;
import org.adichatz.generator.xjc.ContainerTree;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.IncludeTree;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.NavigatorTree;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PartTree;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.QueryTree;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.generator.xjc.TreeManagerTree;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioAdichatzApplication;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ManifestChanger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioUtil.
 * 
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class ScenarioUtil {
	/**
	 * Checks if is nullable join column.
	 * 
	 * @param method
	 *            the method
	 * @return the boolean
	 * @return<br>
	 *             <ul>
	 *             <li>true if one JoinColumn is nullable</li>
	 *             <li>false if any JoinColumn is not nullable</li>
	 *             <li>null if no JoinColumn is found</li>
	 *             </ul>
	 */
	public static Boolean isNullableJoinColumn(Method method) {
		JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
		JoinColumns joinColumns = null;
		if (null != joinColumn)
			return joinColumn.nullable();
		else {
			joinColumns = method.getAnnotation(JoinColumns.class);
			if (null == joinColumns)
				return null;
			for (JoinColumn childJoinColumn : joinColumns.value())
				if (childJoinColumn.nullable()) {
					return true;
				}
		}
		return false;
	}

	/**
	 * Gets the generic.
	 * 
	 * @param field
	 *            the field
	 * @return the generic
	 */
	public static Class<?> getGeneric(Field field) {
		Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
		if (1 != types.length)
			throw new RuntimeException("Field " + field.getName() + " has not one and only one type argument!");
		if (!(types[0] instanceof Class))
			throw new RuntimeException("Field " + field.getName() + " has not one and only one type argument!");
		return (Class<?>) types[0];
	}

	/**
	 * Creates the xml file.
	 * 
	 * @param file
	 *            the file
	 * @param treeWrapper
	 *            the tree wrapper
	 * @param generationScenario
	 *            the generation scenario
	 */
	public static void createXmlFile(IFile file, Object treeWrapper, GenerationScenarioType generationScenario) {
		try {
			FileOutputStream fOut = new FileOutputStream(file.getLocation().toFile());
			EngineTools.createXmlFile(fOut, treeWrapper, getSchemaLocation(treeWrapper));
			file.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException | FileNotFoundException e) {
			logError(e);
		}
	}

	/**
	 * Creates the xml file.
	 * 
	 * @param file
	 *            the file
	 * @param treeWrapper
	 *            the tree wrapper
	 */
	public static void createXmlFile(File file, Object treeWrapper) {
		EngineTools.createXmlFile(file, treeWrapper, getSchemaLocation(treeWrapper));
	}

	/**
	 * Gets the schema location.
	 * 
	 * @param treeWrapper
	 *            the tree wrapper
	 * @return the schema location
	 */
	private static String getSchemaLocation(Object treeWrapper) {
		String schemaLocation;
		if (treeWrapper instanceof AdichatzConnectorConfigTree)
			schemaLocation = "engine/adichatzConnectorConfigTree.xsd";
		else if (treeWrapper instanceof AdichatzRcpConfigTree)
			schemaLocation = "engine/adichatzRcpConfigTree.xsd";
		else if (treeWrapper instanceof NavigatorTree)
			schemaLocation = "generator/navigatorTree.xsd";
		else if (treeWrapper instanceof ConnectorTree)
			schemaLocation = "generator/connectorTree.xsd";
		else if (treeWrapper instanceof EntityTree)
			schemaLocation = "generator/entityTree.xsd";
		else if (treeWrapper instanceof PartTree)
			schemaLocation = "generator/partTree.xsd";
		else if (treeWrapper instanceof IncludeTree)
			schemaLocation = "generator/includeTree.xsd";
		else if (treeWrapper instanceof ConnectorTree)
			schemaLocation = "generator/connectorTree.xsd";
		else if (treeWrapper instanceof ContainerTree)
			schemaLocation = "generator/containerTree.xsd";
		else if (treeWrapper instanceof QueryTree)
			schemaLocation = "generator/queryTree.xsd";
		else if (treeWrapper instanceof ScenarioTree)
			schemaLocation = "generator/scenarioTree.xsd";
		else if (treeWrapper instanceof TreeManagerTree)
			schemaLocation = "generator/treeManagerTree.xsd";
		else
			return null;
		return EngineConstants.SCHEMA_LOCATION.concat(schemaLocation);
	}

	/**
	 * Gets the tree id.
	 * 
	 * @param resource
	 *            the resource
	 * @return the tree id
	 */
	public static String getTreeId(IResource resource) {
		int extensionLength = 0;
		if (null != resource.getFileExtension())
			extensionLength = resource.getFileExtension().length() + 1;
		String treeId = resource.getName().substring(0, resource.getName().length() - extensionLength);
		if (treeId.endsWith("GENERATED"))
			treeId = treeId.substring(0, treeId.length() - 9);
		return treeId;
	}

	/**
	 * Gets the sub package.
	 * 
	 * @param resource
	 *            the resource
	 * @param skip
	 *            the skip
	 * @return the sub package
	 */
	public static String getSubPackage(IResource resource, int skip) {
		String[] segments = resource.getParent().getFullPath().segments();
		if (segments.length <= skip)
			return "";
		StringBuffer subPackageSB = new StringBuffer(segments[skip]);
		for (int i = skip + 1; i < segments.length; i++)
			subPackageSB.append('.').append(segments[i]);
		return subPackageSB.toString();
	}

	/**
	 * Gets the sub package.
	 * 
	 * The file is assumed to be in resource/xml folder
	 * 
	 * @param resource
	 *            the file
	 * 
	 * @return the sub package
	 */
	public static String getSubPackage(IResource resource) {
		return getSubPackage(resource, 3);
	}

	public static List<IResource> getJavaFiles(IFile xmlFile) {
		ScenarioResources scenarioResources = ScenarioResources.getInstance(xmlFile.getProject());
		String treeId = ScenarioUtil.getTreeId(xmlFile);
		String subPackage = ScenarioUtil.getSubPackage(xmlFile);
		List<IResource> childrenFiles = new ArrayList<>();
		IFolder srcGencodeFolder = scenarioResources.getProject()
				.getFolder(scenarioResources.getGencodePath().getGencodeRelativePath().concat("/src"));
		IFolder srcParentFolder = scenarioResources.getSrcGencodeFolder().getFolder(subPackage.replace('.', '/'));
		if (srcParentFolder.exists()) {
			try {
				for (IResource resource : srcParentFolder.members()) {
					if (resource.getFullPath().lastSegment().startsWith(treeId))
						if (treeId.concat(".java").equals(resource.getName())) // <treeId>.jva file could be empty due to error
							childrenFiles.add(resource);
						else {
							IPath path = resource.getFullPath().makeRelativeTo(srcGencodeFolder.getFullPath());
							Object element = scenarioResources.getJavaProject().findElement(path);
							if (element instanceof ICompilationUnit) {
								ICompilationUnit compilationUnit = (ICompilationUnit) element;
								List<Annotation> annotations = GeneratorUtil.getAnnotations(compilationUnit, "AdiResourceURI");
								if (!annotations.isEmpty()) { // Could be a folder
									NormalAnnotation annotation = (NormalAnnotation) annotations.get(0);
									List values = annotation.values();
									if (!values.isEmpty()) {
										Object value = values.get(0);
										if (value instanceof MemberValuePair) {
											MemberValuePair memberValuePair = (MemberValuePair) value;
											if ("URI".equals(memberValuePair.getName().getIdentifier())) {
												String expression = ((StringLiteral) memberValuePair.getValue()).getLiteralValue();
												if (EngineTools
														.getAdiResourceURI(scenarioResources.getPluginName(), subPackage, treeId)
														.equals(expression))
													childrenFiles.add(resource);
											}
										}
									}
								}
							}
						}
				}
			} catch (CoreException e) {
				logError(e);
			}
		}
		return childrenFiles;

	}

	/**
	 * Gets the datasource.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param datasourceId
	 *            the datasource id
	 * @return the datasource
	 */
	public static DatasourceType getDatasource(ScenarioResources scenarioResources, String datasourceId) {
		ConnectorTreeWrapper connectorTree = getConnectorTree(scenarioResources);
		return connectorTree.getDatasource(datasourceId);
	}

	/**
	 * Gets the connector tree.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @return the connector tree
	 */
	public static ConnectorTreeWrapper getConnectorTree(ScenarioResources scenarioResources) {
		String connectorsTreeURI = scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI);
		if (null != connectorsTreeURI)
			connectorsTreeURI = evalLocation(scenarioResources.getBuffer(), connectorsTreeURI);
		return getConnectorTree(connectorsTreeURI);
	}

	/**
	 * Gets the connector tree.
	 *
	 * @param uri
	 *            the uri
	 * @return the connector tree
	 */
	public static ConnectorTreeWrapper getConnectorTree(String uri) {
		if (null == uri)
			uri = GeneratorConstants.DEFAULT_CONNECTORS_URI;
		String[] keys = EngineTools.getContributionURIToStrings(uri);
		try {
			InputStream inputStream;
			if (InternalPolicy.OSGI_AVAILABLE) {
				Bundle templateBundle = Platform.getBundle(keys[0]);
				if (null == templateBundle)
					throw new RuntimeException(
							getFromGeneratorBundle("scenario.bundle.not.exists", GeneratorConstants.TEMPLATE_BUNDLE));
				inputStream = templateBundle.getEntry(keys[1]).openStream();
			} else {
				inputStream = new File(FileUtil.getPluginHome(keys[0]).concat("/").concat(keys[1])).toURI().toURL().openStream();
			}
			return (ConnectorTreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(), inputStream);
		} catch (JAXBException e) {
			EngineTools.openDialog(MessageDialog.ERROR, "Error unmarshaling", "Error unmarchalling Connectors.xml file!?");
			logError(e);
		} catch (IOException e) {
			EngineTools.openDialog(MessageDialog.ERROR, "Error", "Error opening Connectors.xml file!?");
			logError(e);
		}
		return null;
	}

	/**
	 * Gets the j boss standalone reader.
	 *
	 * @param modelPart
	 *            the model part
	 * @param scenarioResources
	 *            the scenario resources
	 * @return the j boss standalone reader
	 */
	public static JBossStandaloneReader getJBossStandaloneReader(ModelPartType modelPart, ScenarioResources scenarioResources) {
		ConnectorTreeWrapper connectorTree = getConnectorTree(scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI));
		String jbossInstallation = connectorTree.getApplicationServerHome(modelPart.getConnectorASVersion());
		DatasourceWrapper datasource = connectorTree.getDatasource(modelPart.getConnectorDataSource());
		return datasource.getJBossStandaloneReader(jbossInstallation, modelPart.getJndi(), scenarioResources);
	}

	/**
	 * Gets the required bundle names.
	 * 
	 * @param manifestChanger
	 *            the manifest changer
	 * @return the required bundle names
	 */
	public static Set<String> getRequiredBundleNames(ManifestChanger manifestChanger) {
		Set<String> requiredBundleNames = new HashSet<String>();
		try {
			StringTokenizer tokenizer = new StringTokenizer(manifestChanger.getValue(Constants.REQUIRE_BUNDLE), ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				int delim = token.indexOf(';');
				if (-1 != delim)
					token = token.substring(0, delim);
				requiredBundleNames.add(token);
			}
			return requiredBundleNames;
		} catch (NullPointerException e) {
			return requiredBundleNames;
		} catch (IOException | CoreException e) {
			logError(e);
			return null;
		}
	}

	/**
	 * Wait for end auto building.
	 * 
	 * @param project
	 *            the project
	 */
	public static void waitForEndAutoBuilding(IProject project) {
		if (null != project) {
			logInfo(getFromGeneratorBundle("generation.waitFor"));
			// subTask(getFromGeneratorBundle("generation.waitFor"));
			double start = new Long(new Date().getTime()).doubleValue();
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
			} catch (OperationCanceledException e) {
			} catch (InterruptedException e) {
			}
			double end = new Long(new Date().getTime()).doubleValue();
			logInfo(getFromGeneratorBundle("generation.waitFor.time", (end - start) / 1000));
		}
	}

	/**
	 * Gets the resource file from uri.
	 * 
	 * @param currentScenarioResources
	 *            the current scenario resources
	 * @param adiResourceURI
	 *            the adi resource uri
	 * @param rootFolder
	 *            the root folder
	 * @param extension
	 *            the extension
	 * @return the resource file from uri
	 */
	public static IFile getResourceFileFromURI(ScenarioResources currentScenarioResources, String adiResourceURI, String rootFolder,
			String extension) {
		String[] resourceKeys = EngineTools.getInstanceKeys(adiResourceURI);
		ScenarioResources scenarioResources;

		if (null != resourceKeys[0])
			scenarioResources = ScenarioResources.getInstance(resourceKeys[0], currentScenarioResources.getPluginName());
		else
			scenarioResources = currentScenarioResources;
		if (null != scenarioResources) {
			IFolder resourceFolder = scenarioResources.getProject().getFolder(rootFolder);
			if (!".".equals(resourceKeys[1]))
				resourceFolder = resourceFolder.getFolder(resourceKeys[1].replace('.', '/'));
			IFile resourceFile = resourceFolder.getFile(resourceKeys[2].concat(".").concat(extension));
			if (!resourceFile.exists())
				resourceFile = resourceFolder.getFile(resourceKeys[2].concat("GENERATED.").concat(extension));
			if (resourceFile.exists())
				return resourceFile;

		}
		return null;
	}

	/**
	 * Gets the xml file from uri.
	 * 
	 * @param currentScenarioResources
	 *            the current scenario resources
	 * @param adiResourceURI
	 *            the adi resource uri
	 * @return the xml file from uri
	 */
	public static IFile getXmlFileFromURI(ScenarioResources currentScenarioResources, String adiResourceURI) {
		return getResourceFileFromURI(currentScenarioResources, adiResourceURI, EngineConstants.XML_FILES_PATH, "axml");
	}

	/**
	 * Checks if is legacy version.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @return true, if is legacy version
	 */
	public static boolean isLegacyVersion(ScenarioInput scenarioInput) {
		return GeneratorConstants.STUDIO_BUNDLE.equals(scenarioInput.getScenarioResources().getPluginName());
	}

	/**
	 * Copy extended fields whith specific cases for XjcEntity.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 */
	public static <T> void copyExtendedFields(T source, T target) {
		BeanUtils.copyExtendedFields(source, target);
		if (source instanceof PluginEntityWrapper)
			((PluginEntityWrapper) target).setScenarioResources(((PluginEntityWrapper) source).getScenarioResources());
	}

	/**
	 * Initialize scenario resources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @return the scenario resources
	 */
	public static ScenarioResources initializeScenarioResources(String pluginName) {
		return initializeScenarioResources(pluginName, EngineConstants.GENCODE_DIR);
	}

	/**
	 * Initialize scenario resources.
	 *
	 * @param pluginName
	 *            the plugin name
	 * @param gencodePath2
	 *            the gencode path 2
	 * @return the scenario resources
	 */
	public static ScenarioResources initializeScenarioResources(String pluginName, String gencodePath2) {
		Properties properties = new Properties();
		try {
			properties.load(new File("generate.properties").toURI().toURL().openStream());
			if (null == GeneratorConstants.WORKSPACE_DIRECTORY)
				GeneratorConstants.WORKSPACE_DIRECTORY = properties.getProperty("WORKSPACE_DIRECTORY");
			if (null == GeneratorConstants.PLUGINS_DIRECTORY)
				GeneratorConstants.PLUGINS_DIRECTORY = properties.getProperty("PLUGINS_DIRECTORY");
			if (null == GeneratorConstants.SECONDARY_WORKSPACE_DIRECTORY)
				GeneratorConstants.SECONDARY_WORKSPACE_DIRECTORY = properties.getProperty("SECONDARY_WORKSPACE_DIRECTORY");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (InternalPolicy.OSGI_AVAILABLE) {
			ScenarioAdichatzApplication.addPluginResources(EngineConstants.ENGINE_BUNDLE, EngineConstants.ENGINE_BUNDLE, null, "/");
			ScenarioAdichatzApplication.addPluginResources(GeneratorConstants.GENERATOR_BUNDLE, GeneratorConstants.GENERATOR_BUNDLE,
					null, "/");
			ScenarioAdichatzApplication.addPluginResources(EngineConstants.JPA_BUNDLE, EngineConstants.JPA_BUNDLE, null,
					"/resources/gencode/");
		} else {
			ScenarioAdichatzApplication.addJavaPluginResources(EngineConstants.ENGINE_BUNDLE, EngineConstants.ENGINE_BUNDLE, null,
					"/");
			ScenarioAdichatzApplication.addJavaPluginResources(GeneratorConstants.GENERATOR_BUNDLE,
					GeneratorConstants.GENERATOR_BUNDLE, null, "/");
			ScenarioAdichatzApplication.addJavaPluginResources(EngineConstants.JPA_BUNDLE, EngineConstants.JPA_BUNDLE, null,
					"/resources/gencode/");
		}
		ScenarioAdichatzApplication.initialize();
		ScenarioResources scenarioResources = null;
		if (!GeneratorConstants.STUDIO_BUNDLE.equals(pluginName)) {
			scenarioResources = ScenarioResources.getInstance(pluginName, null);
			if (!scenarioResources.isParametersLoaded())
				scenarioResources.loadScenarioParameters();
		}
		return scenarioResources;
	}

	/**
	 * Gets the adi resource URI.
	 *
	 * @param javaFile
	 *            the java file
	 * @return the adi resource URI
	 */
	public static String getAdiResourceURI(IFile javaFile) {
		return getAdiResourceURI(JavaCore.createCompilationUnitFrom(javaFile));
	}

	/**
	 * Gets the adi resource URI.
	 *
	 * @param compilationUnit
	 *            the compilation unit
	 * @return the adi resource URI
	 */
	public static String getAdiResourceURI(ICompilationUnit compilationUnit) {
		List<Annotation> annotations = GeneratorUtil.getAnnotations(compilationUnit, AdiResourceURI.class.getSimpleName());
		if (!annotations.isEmpty()) {
			MemberValuePair mvp = (MemberValuePair) ((NormalAnnotation) annotations.get(0)).values().get(0);
			return ((StringLiteral) mvp.getValue()).getLiteralValue();
		}
		return null;

	}

	/**
	 * Gets the element.
	 *
	 * @param collectionWrapper
	 *            the collection wrapper
	 * @param id
	 *            the id
	 * @return the element
	 */
	public static IElementWrapper getElement(ICollectionWrapper<?> collectionWrapper, String id) {
		for (Object element : collectionWrapper.getElements())
			if (element instanceof IElementWrapper) {
				if (id.equals(((IElementWrapper) element).getId()))
					return (IElementWrapper) element;
				if (element instanceof ICollectionWrapper) {
					IElementWrapper result = getElement((ICollectionWrapper<?>) element, id);
					if (null != result)
						return result;
				}
			}
		return null;
	}

	/**
	 * Copy file and compile.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param className
	 *            the class name
	 * @param sourceURI
	 *            the source URI
	 * @param tokens
	 *            the tokens
	 * @param values
	 *            the values
	 * @param addAffectedFile
	 *            the add affected file
	 * @return the i compilation unit
	 */
	public static ICompilationUnit copyFileAndCompile(ScenarioResources scenarioResources, String className, String sourceURI,
			String[] tokens, String[] values, boolean addAffectedFile) {
		int index = className.lastIndexOf('.');
		String packageName = className.substring(0, index);
		String classSimpleName = className.substring(index + 1);
		IFolder srcFolder = scenarioResources.getProject().getFolder("src");
		IPackageFragmentRoot srcFragment = scenarioResources.getJavaProject().getPackageFragmentRoot(srcFolder);
		IPackageFragment packageFragment = srcFragment.getPackageFragment(packageName);

		ICompilationUnit compilationUnit = packageFragment.getCompilationUnit(classSimpleName + ".java");
		if (!compilationUnit.exists()) {
			IFile classFile = srcFolder.getFile(packageName.replace('.', '/').concat("/").concat(classSimpleName).concat(".java"));
			try {
				URL url = FileLocator.find(new URL(sourceURI));
				if (null != url) {
					StringBuffer fileData = FileUtil.readFileAndReplace(new InputStreamReader(url.openStream()), tokens, values);
					ByteArrayInputStream is = new ByteArrayInputStream(fileData.toString().getBytes());
					classFile.create(is, false, null);
					is.close();
					JavaCore.createCompilationUnitFrom(classFile);
					if (addAffectedFile)
						scenarioResources.getAffectedFiles().add((IFile) compilationUnit.getResource());
				} else
					logError(getFromGeneratorBundle("file.not.found", sourceURI));
			} catch (IOException | CoreException e) {
				logError(e);
			}
		} else
			logWarning(getFromGeneratorBundle("generation.type.exists", sourceURI));
		return compilationUnit;
	}

	/**
	 * Gets the custom scenario tree.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param customizationScenarioFileName
	 *            the customization scenario file name
	 * @return the custom scenario tree
	 */
	public static ScenarioTreeWrapper getCustomScenarioTree(ScenarioResources scenarioResources,
			String customizationScenarioFileName) {
		if (null == customizationScenarioFileName)
			return null;
		try {
			InputStream inputStream = new File(customizationScenarioFileName).toURI().toURL().openStream();
			ScenarioTreeWrapper customizationScenarioTree = (ScenarioTreeWrapper) Generator.getUnmarshaller()
					.unmarshal(inputStream);
			ScenarioTreeWrapper scenarioTree = scenarioResources.getScenarioTree();
			scenarioTree.mergeCustomization(scenarioResources, customizationScenarioTree, IPluginEntityScenario.INIT);
			scenarioTree.addCustomizedScenario(customizationScenarioFileName);
			return customizationScenarioTree;
		} catch (IOException | JAXBException e) {
			logError(e);
			return null;
		}

	}

	/**
	 * Adds the param.
	 *
	 * @param container
	 *            the container
	 * @param id
	 *            the id
	 * @param value
	 *            the value
	 * @param type
	 *            the type
	 * @return true, if successful
	 */
	public static boolean addParam(IParamsContainer container, String id, String value, String type) {
		ParamsType params = container.getParams();
		if (null == params) {
			params = new ParamsType();
			container.setParams(params);
		}
		boolean add = true;
		for (ParamType paramType : params.getParam())
			if (id.equals(paramType.getId())) {
				add = false;
				break;
			}
		if (add) {
			ParamType param = new ParamType();
			param.setValue(value);
			param.setId(id);
			param.setType(type);
			params.getParam().add(param);
			return true;
		} else
			return false;
	}

	/**
	 * Check properties filter.
	 *
	 * @param beanClass
	 *            the bean class
	 * @param regex
	 *            the regex
	 * @return true, if successful
	 */
	public static boolean checkPropertiesFilter(Class<?> beanClass, String regex) {
		for (Field field : beanClass.getDeclaredFields()) {
			if (checkStringFilter(field.getName(), regex))
				return true;
		}
		return false;
	}

	/**
	 * Check string filter.
	 *
	 * @param matchedString
	 *            the matched string
	 * @param regex
	 *            the regex
	 * @return true, if successful
	 */
	public static boolean checkStringFilter(String matchedString, String regex) {
		if (EngineTools.isEmpty(regex))
			return true;
		return matchedString.matches(regex);
	}

	/**
	 * Gets the path elements.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param pathElts
	 *            the path elts
	 * @return the path elements
	 */
	public static List<PathElementType> getPathElements(ScenarioResources scenarioResources, List<PathElementType> pathElts) {
		boolean hasModel = scenarioResources.hasApplicationServer();
		boolean hasRCP = scenarioResources.hasRcpPart();
		List<PathElementType> pathElements = new ArrayList<>();
		// instantiate new ArrayList to avoid ConcurrentModificationException
		if (hasModel && hasRCP) {
			for (PathElementType pathElement : pathElts)
				if (scenarioResources.getCondition(pathElement.getCondition()))
					pathElements.add(pathElement);
		} else {
			for (PathElementType pathElement : pathElts) {
				if (scenarioResources.getCondition(pathElement.getCondition())) {
					boolean process = true;
					AddWhenEnum addWhen = pathElement.getAddWhen();
					if (AddWhenEnum.ALL != addWhen)
						process = (AddWhenEnum.MODEL == addWhen && hasModel) || (AddWhenEnum.UI == addWhen && hasRCP);
					if (process)
						pathElements.add(pathElement);
				}
			}
		}
		return pathElements;
	}

	/**
	 * Eval location.
	 *
	 * @param buffer
	 *            the buffer
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String evalLocation(BufferCode buffer, String value) {
		if (null == value)
			return null;
		return new KeyWordGenerator().evalExpression(buffer, value, false, false);
	}

	/**
	 * Adds the module.
	 *
	 * @param doc
	 *            the doc
	 * @param dependencies
	 *            the dependencies
	 * @param value
	 *            the value
	 */
	public static void addModule(Document doc, Element dependencies, String value) {
		Element module = doc.createElement("module");
		addAttrribute(doc, module, "name", value);
		dependencies.appendChild(module);
	}

	/**
	 * Adds the attrribute.
	 *
	 * @param doc
	 *            the doc
	 * @param element
	 *            the element
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public static void addAttrribute(Document doc, Element element, String name, String value) {
		Attr attr = doc.createAttribute(name);
		attr.setValue(value);
		element.setAttributeNode(attr);
	}

	/**
	 * Checks if is wildfly new version.
	 *
	 * @param connectorVersion
	 *            the connector version
	 * @return true, if is wildfly new version
	 */
	public static boolean isWildflyNewVersion(String connectorVersion) {
		if (EngineTools.isEmpty(connectorVersion))
			return false;
		if (!connectorVersion.startsWith("wildfly-"))
			return false;
		try {
			int version = Integer.parseInt(connectorVersion.substring(8, 10));
			return version > 10;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isWildfly14Version(String connectorVersion) {
		if (EngineTools.isEmpty(connectorVersion))
			return false;
		if (!connectorVersion.startsWith("wildfly-"))
			return false;
		try {
			int version = Integer.parseInt(connectorVersion.substring(8, 10));
			return version >= 14;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Gets the IFile from File.
	 *
	 * @param file
	 *            the file
	 * @return the ifile from file
	 */
	public static IFile getIFileFromFile(File file) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath location = Path.fromOSString(file.getAbsolutePath());
		return workspace.getRoot().getFileForLocation(location);
	}

	public static boolean deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		return file.delete();
	}
}