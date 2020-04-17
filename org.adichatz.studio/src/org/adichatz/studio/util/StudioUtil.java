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
package org.adichatz.studio.util;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.collection.GroupController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.PathElementWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.PathElementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.StudioResources;
import org.adichatz.studio.xjc.controller.ScenarioTreeController;
import org.adichatz.studio.xjc.data.XjcDataAccess;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.ISourceType;
import org.eclipse.jdt.internal.core.ResolvedBinaryType;
import org.eclipse.jdt.internal.core.ResolvedSourceType;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class StudioUtil.
 *
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class StudioUtil {

	/** The studio RBM. */
	private static AdiResourceBundle studioRBM;

	/** The icons files. */
	public static Set<String> ICONS_FILES;

	/** The xjc dir file. */
	private static File xjcDirFile;

	/**
	 * Gets the icon file name.
	 *
	 * @param entityURI
	 *            the entity URI
	 * @return the icon file name
	 */
	public static String getIconFileName(String entityURI) {
		if (null == ICONS_FILES) {
			ICONS_FILES = new HashSet<String>();
			if (InternalPolicy.OSGI_AVAILABLE) {
				Bundle studioBundle = Platform.getBundle(GeneratorConstants.STUDIO_BUNDLE);
				Enumeration<String> iconFiles = studioBundle.getEntryPaths("resources/icons/xjc");
				int index = 20;
				while (iconFiles.hasMoreElements())
					ICONS_FILES.add(iconFiles.nextElement().substring(index));
			} else {
				String studioPluginHome = FileUtil.getPluginHome(GeneratorConstants.STUDIO_BUNDLE);
				for (String fileName : new File(studioPluginHome + "/resources/icons/xjc").list()) {
					ICONS_FILES.add(fileName);
				}
			}
		}

		String fileName = EngineTools.getEntityId(EngineTools.lowerCaseFirstLetter(EngineTools.getInstanceKeys(entityURI)[2]));
		if (fileName.endsWith("Tree"))
			fileName = fileName.substring(0, fileName.length() - 4);
		fileName = fileName.concat(".png");
		return ICONS_FILES.contains(fileName) ? "xjc/".concat(fileName) : null;
	}

	/**
	 * Gets the xjc dir file.
	 *
	 * @return the xjc dir file
	 */
	public static File getXjcDirFile() {
		if (null == xjcDirFile) {
			xjcDirFile = new File(
					FileUtil.getPluginHome(GeneratorConstants.GENERATOR_BUNDLE) + "/src/generator/org/adichatz/generator/xjc");
			if (!xjcDirFile.exists())
				throw new RuntimeException(xjcDirFile.getAbsolutePath().concat(" does not exist!"));
		}
		return xjcDirFile;
	}

	/**
	 * Gets the MessageBundle for Studio bundle.
	 * 
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * 
	 * @return the from tool bundle
	 */
	public static String getFromStudioBundle(String key, Object... variables) {
		if (null == studioRBM)
			studioRBM = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getResourceBundleManager()
					.getResourceBundle("adichatzStudio");
		return studioRBM.getValueFromBundle(key, variables);
	}

	/**
	 * Log.
	 * 
	 * @param status
	 *            the status
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public static void log(int status, int code, String message, Exception exception) {
		if (IStatus.INFO < status)
			logError(message, exception);
		else {
			logInfo(message);
			((StudioLog) LogBroker.getLogger()).log(status, code, message, exception);
		}
	}

	/**
	 * Refresh project.
	 *
	 * @param project
	 *            the project
	 * @param message
	 *            the message
	 * @param monitor
	 *            the monitor
	 */
	public static void refreshProject(IProject project, String message, IProgressMonitor monitor) {
		try {
			if (!EngineTools.isEmpty(message))
				logInfo(message);
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			logError(e);
		}
	}

	/**
	 * Initialize scenario resources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @return the scenario resources
	 */
	public static ScenarioResources initializeScenarioResources(String pluginName) {
		ScenarioResources scenarioResources = ScenarioUtil.initializeScenarioResources(pluginName, EngineConstants.GENCODE_DIR);
		if (GeneratorConstants.STUDIO_BUNDLE.equals(pluginName)) {
			scenarioResources = new StudioResources();
			ScenarioResources.getScenarioResourcesMap().put(GeneratorConstants.STUDIO_BUNDLE, scenarioResources);
			if (!scenarioResources.isParametersLoaded())
				scenarioResources.loadScenarioParameters();
		}
		return scenarioResources;
	}

	/**
	 * Creates the scenario resources.
	 *
	 * @param project
	 *            the project
	 * @return the scenario resources
	 */
	public static ScenarioResources createScenarioResources(IProject project) {
		String pluginName = project.getName();
		ScenarioResources scenarioResources = ScenarioResources.getScenarioResourcesMap().get(pluginName);
		if (null != scenarioResources) {
			logWarning(getFromStudioBundle("scenario.scenario.already.exist", pluginName));
			ScenarioResources.getScenarioResourcesMap().remove(pluginName);
			AdichatzApplication.getInstance().getPluginMap().remove(pluginName);
			ADataCache dataCache = StudioRcpPlugin.getDefault().getPluginResources().getDataAccess().getDataCache();
			for (MultiKey cacheKey : dataCache.getCachedEntities().keySet()
					.toArray(new MultiKey[dataCache.getCachedEntities().size()])) {
				if (pluginName.equals(cacheKey.getKey(0)))
					dataCache.getCachedEntities().remove(cacheKey);
			}
		}
		scenarioResources = new ScenarioResources(project, true);
		ScenarioResources.getScenarioResourcesMap().put(pluginName, scenarioResources);
		return scenarioResources;
	}

	/**
	 * Gets the hierarchy.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param superTypeName
	 *            the super type name
	 * @return the hierarchy
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getHierarchy(ScenarioResources scenarioResources, String superTypeName) {
		if (null == superTypeName)
			return new ArrayList(0);
		Set proposalSet = new HashSet<String>();
		proposalSet.addAll(getInitialProposals(scenarioResources, superTypeName));
		if (null != scenarioResources.getScenarioTree().getPathElements())
			for (PathElementType pathElement : ScenarioUtil.getPathElements(scenarioResources,
					scenarioResources.getScenarioTree().getPathElements().getPathElement())) {
				if (((PathElementWrapper) pathElement).isAdditionalResourcePath()
						&& PathElementEnum.PROJECT == pathElement.getType()) {
					IProject pathProject = ResourcesPlugin.getWorkspace().getRoot().getProject(pathElement.getLocation());
					try {
						IJavaProject javaProject = (IJavaProject) pathProject.getNature(JavaCore.NATURE_ID);
						IType superClassType = javaProject.findType(superTypeName);
						if (null != superClassType) {
							ITypeHierarchy typeHierarchy = superClassType.newTypeHierarchy(null);
							IType[] subtypes = typeHierarchy.getAllSubtypes(superClassType);
							for (IType subType : subtypes)
								proposalSet.add(subType.getFullyQualifiedName());
						}
					} catch (CoreException e) {
						logError(e);
					}
				}
			}
		return new ArrayList(proposalSet);
	}

	/**
	 * Gets the initial proposals.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param superType
	 *            the super type
	 * @return the initial proposals
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List getInitialProposals(ScenarioResources scenarioResources, String superType) {
		IJavaProject javaProject = scenarioResources.getJavaProject();
		ClassLoader classLoader = scenarioResources.getGencodePath().getClassLoader();
		Set initialProposals = new HashSet();
		try {
			IType superClassType = javaProject.findType(superType);
			if (null != superClassType) {
				ITypeHierarchy typeHierarchy = superClassType.newTypeHierarchy(null);
				IType[] subtypes = typeHierarchy.getAllSubtypes(superClassType);
				for (IType subType : subtypes) {
					if (subType instanceof ResolvedSourceType) {
						ResolvedSourceType element = (ResolvedSourceType) subType;
						if (element.isClass()) {
							ISourceType info = (ISourceType) element.getElementInfo();
							if ((info.getModifiers() & ClassFileConstants.AccPublic) != 0
									&& ((info.getModifiers() & ClassFileConstants.AccAbstract) == 0
											|| addAbstract(superType, subType)) //
									&& null != javaProject.findType(element.getFullyQualifiedName())
									&& existClass(classLoader, subType.getFullyQualifiedName()))
								initialProposals.add(subType.getFullyQualifiedName());
						}
					} else if (subType instanceof ResolvedBinaryType) {
						ResolvedBinaryType element = (ResolvedBinaryType) subType;
						if (element.isClass()) {
							IBinaryType info = (IBinaryType) element.getElementInfo();
							if ((info.getModifiers() & ClassFileConstants.AccPublic) != 0
									&& ((info.getModifiers() & ClassFileConstants.AccAbstract) == 0
											|| addAbstract(superType, subType)) //
									&& null != javaProject.findType(element.getFullyQualifiedName())
									&& existClass(classLoader, subType.getFullyQualifiedName()))
								initialProposals.add(subType.getFullyQualifiedName());
						}
					}
				}
			}
		} catch (CoreException e) {
			logError(e);
		}
		return new ArrayList(initialProposals);
	}

	/**
	 * Exist class.
	 *
	 * @param classLoader
	 *            the class loader
	 * @param className
	 *            the class name
	 * @return true, if successful
	 */
	private static boolean existClass(ClassLoader classLoader, String className) {
		Class<?> clazz = null;
		try {
			clazz = classLoader.loadClass(className);
		} catch (Exception e) {
		}
		return null != clazz;
	}

	/**
	 * Adds the abstract.
	 *
	 * @param superType
	 *            the super type
	 * @param subType
	 *            the sub type
	 * @return true, if successful
	 */
	private static boolean addAbstract(String superType, IType subType) {
		if (!AController.class.getName().equals(superType))
			return false;
		else {
			char[] name = subType.getElementName().toCharArray();
			if (name.length > 1 && 'A' == name[0] && Character.isUpperCase(name[1]))
				return false;
		}
		return true;
	}

	/**
	 * Gets the encrypted key.
	 *
	 * @return the encrypted key
	 */
	public static String getEncryptedKey() {
		try {
			InputStream inputStream = Platform.getBundle(GeneratorConstants.TEMPLATE_BUNDLE).getEntry("template/encryptedkey.txt")
					.openStream();
			GeneratorConstants.ENCRYPTED_KEY = new String(EngineTools.getByteArray(inputStream));
		} catch (IOException e) {
			logError(e);
		}
		return GeneratorConstants.ENCRYPTED_KEY;
	}

	/**
	 * Validate customization file.
	 *
	 * @param fileName
	 *            the file name
	 * @param core
	 *            the core
	 * @return the object
	 */
	public static Object validateCustomizationFile(String fileName, ControllerCore core) {
		ScenarioTreeController scenarioTreeController = null;
		GroupController parentGroupController = null;
		if (null != core) {
			scenarioTreeController = (ScenarioTreeController) core.getFromRegister("customGenerationTree");
			parentGroupController = ((GroupController) core.getFromRegister("customScenarioGroup"));
		}
		if (!EngineTools.isEmpty(fileName)) {
			File customizationFile = new File(fileName);
			if (!customizationFile.exists()) {
				refreshScenarioTree(scenarioTreeController, parentGroupController, null);
				return getFromEngineBundle("error.file.does.not.exist", fileName);
			} else {
				InputStream inputStream;
				try {
					inputStream = customizationFile.toURI().toURL().openStream();
					try {
						Object treeWrapper = Generator.getUnmarshaller().unmarshal(inputStream);
						if (!(treeWrapper instanceof ScenarioTreeWrapper)) {
							refreshScenarioTree(scenarioTreeController, parentGroupController, null);
							return getFromStudioBundle("scenario.custom.invalid.type", fileName,
									ScenarioTree.class.getSimpleName());
						}
						if (null != core) {
							XjcDataAccess dataAccess = new XjcDataAccess(parentGroupController.getPluginResources());
							IEntity<?> entity = dataAccess.getDataCache().fetchEntity(treeWrapper);
							refreshScenarioTree(scenarioTreeController, parentGroupController, entity);
						}
						return treeWrapper;
					} catch (JAXBException e) {
						refreshScenarioTree(scenarioTreeController, parentGroupController, null);
						return getFromStudioBundle("scenario.custom.invalid.syntax", fileName);
					}
				} catch (IOException e) {
					refreshScenarioTree(scenarioTreeController, parentGroupController, null);
					logError(e);
					return e.getLocalizedMessage();
				}
			}
		}
		refreshScenarioTree(scenarioTreeController, parentGroupController, null);
		return null;
	}

	/**
	 * Refresh scenario tree.
	 *
	 * @param scenarioTreeController
	 *            the scenario tree controller
	 * @param parentGroupController
	 *            the parent group controller
	 * @param entity
	 *            the entity
	 */
	private static void refreshScenarioTree(ScenarioTreeController scenarioTreeController, GroupController parentGroupController,
			IEntity<?> entity) {
		if (null != parentGroupController) {
			new EntityInjection(parentGroupController, entity);
			scenarioTreeController.refresh();
			((TreeViewer) scenarioTreeController.getViewer()).expandAll();
		}
	}

	/**
	 * Gets the project from execution.
	 *
	 * @param event
	 *            the event
	 * @return the project from execution
	 */
	public static IProject getProjectFromExecution(ExecutionEvent event) {
		IProject project = null;
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection).getFirstElement();
			if (selectedObject instanceof IResource)
				project = ((IResource) selectedObject).getProject();
			else if (selectedObject instanceof IJavaProject)
				project = ((IJavaProject) selectedObject).getProject();
			else if (selectedObject instanceof ICompilationUnit)
				project = ((ICompilationUnit) selectedObject).getJavaProject().getProject();
			else if (selectedObject instanceof PackageFragmentRootContainer)
				project = ((PackageFragmentRootContainer) selectedObject).getJavaProject().getProject();
			else if (selectedObject instanceof IPackageFragmentRoot)
				project = ((IPackageFragmentRoot) selectedObject).getJavaProject().getProject();
			else if (selectedObject instanceof IPackageFragment)
				project = ((IPackageFragment) selectedObject).getJavaProject().getProject();
		}
		return project;
	}

	/**
	 * Gets the scenario tree generation class from URI.
	 *
	 * @param scenarioTreeGenerationURI
	 *            the scenario tree generation URI
	 * @return the scenario tree generation class
	 * @throws Exception
	 *             the exception
	 */
	public static Class<?> getScenarioTreeGenerationClass(String scenarioTreeGenerationURI) throws Exception {
		Class<?> scenarioTreeGenerationClass;
		String[] instanceKeys = EngineTools.getContributionURIToStrings(scenarioTreeGenerationURI);
		Bundle bundle = Platform.getBundle(instanceKeys[0]);
		if (null == bundle) {
			bundle = Platform.getBundle(GeneratorConstants.GENERATOR_BUNDLE);
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(instanceKeys[0]);
			if (!genProject.exists())
				return null;
			IJavaProject javaProject = (IJavaProject) genProject.getNature(JavaCore.NATURE_ID);
			String pluginHome = genProject.getLocation().toOSString();
			String outputLocation = javaProject.getOutputLocation().removeFirstSegments(1).toString();
			StringBuffer classFileSB = new StringBuffer(pluginHome).append("/").append(outputLocation).append("/")
					.append(instanceKeys[1].replace('.', '/')).append(".class");
			File classFile = new File(classFileSB.toString());
			ClassLoader classLoader = new ClassLoader(StudioRcpPlugin.class.getClassLoader()) {
				boolean first = true;

				@Override
				public Class<?> loadClass(String className) throws ClassNotFoundException {
					if (first) {
						first = false;
						InputStream inputStream;
						try {
							inputStream = classFile.toURI().toURL().openStream();
							byte[] bytes = EngineTools.getByteArray(inputStream);
							inputStream.close();
							return defineClass(className, bytes, 0, bytes.length);
						} catch (IOException e) {
							throw new ClassNotFoundException(className);
						}
					}
					return super.loadClass(className);
				}
			};
			scenarioTreeGenerationClass = classLoader.loadClass(instanceKeys[1]);
		} else {
			scenarioTreeGenerationClass = bundle.loadClass(instanceKeys[1]);
		}
		return scenarioTreeGenerationClass;
	}

}
