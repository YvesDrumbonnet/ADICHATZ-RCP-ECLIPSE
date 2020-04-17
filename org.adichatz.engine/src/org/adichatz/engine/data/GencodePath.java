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
package org.adichatz.engine.data;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

import org.adichatz.engine.common.AdiClassLoader;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.JarClassLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class GenCodePath.
 * 
 * Gives plugin home and locations for generated code and xml files
 */
public class GencodePath {

	/** The classPaths. */
	private Set<File> classPaths = new HashSet<File>();

	/** The plugin home. */
	private String pluginHome;

	/** The generated code bin location. */
	private String genCodeBinLocation;

	/** The generated code src location. */
	private String genCodeSrcLocation;

	/** The xml files location. */
	private String xmlFilesLocation;

	/** The generated code package. */
	private String gencodePackage;

	/** The class loader. */
	private ClassLoader classLoader;

	/** The plugin file. */
	private File pluginFile;

	private String gencodeRelativePath;

	/**
	 * Instantiates a new gencode path.
	 *
	 * @param pluginHome
	 *            the plugin home
	 * @param pluginPackage
	 *            the plugin package
	 * @param gencodePackage
	 *            the gencode dir (e.g "gencode": generated class name start with{plugin.name}.gencode).
	 * @param gencodeRelativePath
	 *            the gencode relative path (e.g. "resources/gencode": files are stored under this directory)
	 * @param parentClassLoader
	 *            the parent class loader
	 */
	public GencodePath(String pluginHome, String pluginPackage, String gencodePackage, String gencodeRelativePath,
			ClassLoader parentClassLoader) {
		this.pluginHome = pluginHome;
		this.gencodeRelativePath = gencodeRelativePath;
		genCodeSrcLocation = pluginHome.concat(gencodeRelativePath).concat("src");
		xmlFilesLocation = pluginHome.concat("/").concat(EngineConstants.XML_FILES_PATH);
		if (EngineTools.isEmpty(gencodePackage))
			gencodePackage = pluginPackage.concat(".gencode");
		this.gencodePackage = gencodePackage;
		if (!gencodeRelativePath.endsWith("/"))
			this.gencodeRelativePath = gencodeRelativePath.concat("/");
		pluginFile = new File(pluginHome);
		defineClassLoader(parentClassLoader);
	}

	public void defineClassLoader(ClassLoader parentClassLoader) {
		if (pluginFile.isDirectory()) {
			genCodeBinLocation = pluginHome.concat(this.gencodeRelativePath.concat("bin/"));
			defineAdiClassLoader(parentClassLoader);
		} else {
			defineJarClassLoader(parentClassLoader);
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
	 * Gets the gen code src location.
	 * 
	 * @return the gen code src location
	 */
	public String getGenCodeSrcLocation() {
		if (null == genCodeSrcLocation)
			genCodeSrcLocation = new StringBuffer(pluginHome).append("/" + EngineConstants.GENCODE_DIR + "/src").toString();
		return genCodeSrcLocation;
	}

	/**
	 * Gets the gen code bin location.
	 * 
	 * @return the gen code bin location
	 */
	public String getGenCodeBinLocation() {
		return genCodeBinLocation;
	}

	/**
	 * Gets the xml files location.
	 * 
	 * @return the xml files location
	 */
	public String getXmlFilesLocation() {
		return xmlFilesLocation;
	}

	/**
	 * Gets the generated code package.
	 * 
	 * @return the generated code package
	 */
	public String getGencodePackage() {
		return gencodePackage;
	}

	/**
	 * Gets the package name for generated classes.
	 * 
	 * @param subPackage
	 *            the sub package
	 * 
	 * @return the package name for generated classes
	 */
	public String getGenCodePackage(String subPackage) {
		if (EngineTools.isEmpty(subPackage) || ".".equals(subPackage))
			return gencodePackage;
		return gencodePackage + "." + subPackage;
	}

	/**
	 * Define new class loader.
	 * 
	 * @param parentClassLoader
	 *            the parent class loader
	 * @return the class loader
	 */
	public void defineAdiClassLoader(final ClassLoader parentClassLoader) {
		classLoader = AccessController.doPrivileged(new PrivilegedAction<AdiClassLoader>() {
			public AdiClassLoader run() {
				AdiClassLoader pluginClassLoader = new AdiClassLoader(GencodePath.this, parentClassLoader);
				if (null != genCodeBinLocation)
					classPaths.add(new File(genCodeBinLocation));
				return pluginClassLoader;
			}
		});
	}

	/**
	 * Define jar class loader.
	 * 
	 * @param parentClassLoader
	 *            the parent class loader
	 */
	public void defineJarClassLoader(final ClassLoader parentClassLoader) {
		classLoader = AccessController.doPrivileged(new PrivilegedAction<JarClassLoader>() {
			public JarClassLoader run() {
				try {
					return new JarClassLoader(new JarFile(pluginFile), parentClassLoader);
				} catch (IOException e) {
					throw new RuntimeException("Could not instantiate a JarClassLoader for " + pluginFile.getAbsolutePath());
				}
			}
		});
	}

	/**
	 * Gets the class loader.
	 * 
	 * @return the class loader
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * Sets the class loader.
	 * 
	 * @param classLoader
	 *            the new class loader
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * Instantiate class.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param paramClasses
	 *            the param classes
	 * @param params
	 *            the params
	 * 
	 * @return the object
	 */
	public Object instantiateClass(Class<?> clazz, @SuppressWarnings("rawtypes") Class[] paramClasses, Object[] params) {
		Object result = null;
		try {
			result = clazz.getConstructor(paramClasses).newInstance(params);
		} catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException
				| InvocationTargetException | NoSuchMethodException e) {
			logError("Error instantiating class " + clazz.getCanonicalName(), e);
		}
		return result;
	}

	/**
	 * Instantiate class.
	 * 
	 * @param className
	 *            the class name
	 * @param paramClasses
	 *            the param classes
	 * @param params
	 *            the params
	 * 
	 * @return the object
	 */
	public Object instantiateClass(String className, @SuppressWarnings("rawtypes") Class[] paramClasses, Object[] params) {
		return instantiateClass(getClazz(className), paramClasses, params);
	}

	/**
	 * Instantiate class.
	 * 
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * @param paramClasses
	 *            the param classes
	 * @param params
	 *            the params
	 * 
	 * @return the object
	 */
	@SuppressWarnings("rawtypes")
	public Object instantiateClass(String packageName, String className, Class[] paramClasses, Object[] params) {
		return instantiateClass(getClazz(packageName, className), paramClasses, params);
	}

	/**
	 * Gets the clazz.
	 * 
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * 
	 * @return the clazz
	 */
	public Class<?> getClazz(String packageName, String className) {
		return getClazz(new StringBuffer(packageName).append('.').append(className).toString());
	}

	/**
	 * Gets the clazz.
	 * 
	 * @param className
	 *            the class name
	 * 
	 * @return the clazz
	 */
	public Class<?> getClazz(String className) {
		return getClazz(className, false);
	}

	/**
	 * Gets the clazz.
	 * 
	 * @param className
	 *            the class name
	 * @param skipClassNotFound
	 *            the skip class not found
	 * 
	 * @return the clazz
	 */
	public Class<?> getClazz(String className, boolean skipClassNotFound) {
		try {
			return classLoader.loadClass(className);
		} catch (NoClassDefFoundError e) {
			logError("Error loading class " + className, e);
		} catch (ClassNotFoundException e) {
			if (!skipClassNotFound) {
				logError("Error loading class " + className, e);
			}
		}
		return null;
	}

	/**
	 * Gets the class name.
	 * 
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * 
	 * @return the class name
	 */
	public String getClassName(String treeId, String subPackage) {
		StringBuffer className = new StringBuffer(gencodePackage);
		if (!EngineTools.isEmpty(subPackage) && !".".equals(subPackage))
			className.append('.').append(subPackage);
		return className.append('.').append(treeId).toString();
	}

	/**
	 * Gets the code gen directory.
	 * 
	 * @param subPackage
	 *            the sub package
	 * 
	 * @return the code gen directory
	 */
	public String getCodeGenDirectory(String subPackage) {
		return getGenCodePackage(subPackage).replace('.', '/');
	}

	/**
	 * Gets the classPaths.
	 * 
	 * @return the classPaths
	 */
	public Set<File> getClassPaths() {
		return classPaths;
	}

	/**
	 * Gets the gencode relative path.
	 * 
	 * @return the gencode relative path
	 */
	public String getGencodeRelativePath() {
		return gencodeRelativePath;
	}
}
