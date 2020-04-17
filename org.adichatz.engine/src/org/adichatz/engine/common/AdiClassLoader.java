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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.adichatz.engine.data.GencodePath;

public class AdiClassLoader extends ClassLoader {
	private GencodePath gencodePath;

	// When linked ScenarioResouces is typ BUNDLE, skip error when bin directory does not exist
	private boolean skipErrorOnInvalidPath;

	public AdiClassLoader(GencodePath gencodePath, final ClassLoader parentClassLoader) {
		super(parentClassLoader);
		this.gencodePath = gencodePath;
	}

	/**
	 * Class loading must follow an order:
	 * 
	 * If class belongs to gencode package means class name start by gencode package name.<br>
	 * So Class is a class resulting of a Java class generation from an AWML file.
	 * <ul>
	 * <li>loaded class in the same classloader.</li>
	 * <li>classes from the directories og the class loader</li>
	 * <li>super class loading</li>
	 * </ul>
	 * This loading order provides a way to load new class when changing element of AXML file (specially for Table include).
	 * 
	 * elsewhere.<br>
	 * <ul>
	 * <li>loaded class in the same classloader.</li>
	 * <li>super class loading</li>
	 * <li>classes from the directories og the class loader</li>
	 * </ul>
	 * 
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		Class<?> c = findLoadedClass(className);
		if (c != null)
			return c;
		// if (!InternalPolicy.OSGI_AVAILABLE) {
		// // When using AdiClassLoader in standalone (e.g. for test) super classLoader must be privileged
		// try {
		// return super.loadClass(className);
		// } catch (ClassNotFoundException e) {
		// // Continue trying to load with generated class loader
		// }
		// }
		if (className.startsWith(gencodePath.getGencodePackage())) {
			byte[] b = loadClassData(className);
			if (b != null)
				return defineClass(className, b, 0, b.length);
			return super.loadClass(className);
		} else {
			try {
				return super.loadClass(className);
			} catch (ClassNotFoundException e) {
			}
			byte[] b = loadClassData(className);
			if (b == null)
				throw new ClassNotFoundException(className);
			return defineClass(className, b, 0, b.length);
		}
	}

	private byte[] loadClassData(String name) throws ClassNotFoundException {
		for (File classPath : gencodePath.getClassPaths()) {
			if (classPath.isFile()) {
				try {
					JarFile jarFile = new JarFile(classPath);
					JarEntry entry = jarFile.getJarEntry(name.replace('.', '/') + ".class");
					if (null != entry) {
						byte[] bytes = EngineTools.getByteArray(jarFile.getInputStream(entry));
						jarFile.close();
						return bytes;
					} else
						jarFile.close();
				} catch (IOException e) {
					logError(e);
				}
			} else if (classPath.isDirectory()) {
				File classFile = new File(classPath.getAbsolutePath().concat("/").concat(name.replace('.', '/')).concat(".class"));
				if (classFile.exists()) {
					name = name.replace('.', '/');
					try {
						return EngineTools.getByteArray(classFile.toURI().toURL().openStream());
					} catch (MalformedURLException e) {
						throw new ClassNotFoundException(
								getFromEngineBundle("malformedURLException.message", classFile.getAbsolutePath())); //$NON-NLS-1$
					} catch (IOException e) {
						throw new ClassNotFoundException(getFromEngineBundle("IOException.message", classFile.getAbsolutePath())); //$NON-NLS-1$
					}
				}
			} else if (!skipErrorOnInvalidPath || !"bin".equals(classPath.getName())) {
				logError(getFromEngineBundle("resource.exception.message", classPath.getName()));
			}
		}
		return null;
	}

	public void setSkipErrorOnInvalidPath(boolean raiseErrorOnInvalidPath) {
		this.skipErrorOnInvalidPath = raiseErrorOnInvalidPath;
	}
}
