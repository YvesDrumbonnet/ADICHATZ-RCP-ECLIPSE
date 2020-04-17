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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// TODO: Auto-generated Javadoc
/**
 * The Class ReflectionTools provides static method uses in reflection processing.
 */
public class ReflectionTools {

	/**
	 * Gets the clazz.
	 * 
	 * @param className
	 *            the class name
	 * 
	 * @return the clazz
	 */
	public static Class<?> getClazz(String className) {
		if (null == className)
			return null;
		try {
			return AdichatzApplication.getInstance().getApplicationPluginResources().getParentClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			logError("Error loading class " + className, e);
			return null;
		}
	}

	public static Class<?> getClassFromURI(String contributionURI) {
		String[] keys = EngineTools.getContributionURIToStrings(contributionURI);
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(keys[0]);
		if (null == pluginResources) {
			throw new RuntimeException("Invalid plugin key '" + keys[0] + "' from URI '" + contributionURI + "'!");
		} else
			return pluginResources.getGencodePath().getClazz(keys[1]);
	}

	/**
	 * Instantiate class.
	 * 
	 * @param clazz
	 *            the clazz
	 * 
	 * @return the object
	 */
	public static Object instantiateClass(Class<?> clazz) {
		Object result = null;
		try {
			result = clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logError("Error instantiating class " + clazz.getCanonicalName(), e);
		}
		return result;
	}

	public static Object instantiateURI(String contributionURI, @SuppressWarnings("rawtypes") Class[] paramClasses,
			Object[] params) {
		return instantiateClass(getClassFromURI(contributionURI), paramClasses, params);
	}

	/**
	 * Instantiate class.
	 * 
	 * @param className
	 *            the class name
	 * 
	 * @return the object
	 */
	public static Object instantiateClass(String className) {
		return instantiateClass(getClazz(className));
	}

	public static Object instantiateClass(String className, @SuppressWarnings("rawtypes") Class[] paramClasses, Object[] params) {
		return instantiateClass(getClazz(className), paramClasses, params);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object instantiateClass(Class clazz, Class[] paramClasses, Object[] params) {
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
	 * Invoke a method of an object which has no parameters.
	 * 
	 * @param methodName
	 *            the method name
	 * @param object
	 *            the object
	 * 
	 * @return the object
	 */
	public static Object invokeMethod(String methodName, Object object) {
		return invokeMethod(methodName, object, new Class[] {}, new Object[] {});
	}

	/**
	 * Invoke a method of an object which has parameters.
	 * 
	 * @param methodName
	 *            the method name
	 * @param object
	 *            the object
	 * @param paramType
	 *            the param type
	 * @param params
	 *            the params
	 * 
	 * @return the object
	 */
	public static Object invokeMethod(String methodName, Object object, Class<?>[] paramType, Object[] params) {
		try {
			return invokeMethod(object.getClass().getMethod(methodName, paramType), object, params);
		} catch (SecurityException | NoSuchMethodException e) {
			logError("Error invoking method " + methodName + " for class" + object.getClass(), e);
		}
		return null;
	}

	public static Object invokeStaticMethod(String methodName, String className) {
		try {
			return invokeMethod(ReflectionTools.getClazz(className).getMethod(methodName), null);
		} catch (SecurityException | NoSuchMethodException e) {
			logError("Error invoking method " + methodName + " for class" + className, e);
		}
		return null;

	}

	public static Object invokeStaticMethod(String methodName, Object object, Class<?>[] paramType, Object[] params) {
		try {
			return invokeMethod(object.getClass().getMethod(methodName, paramType), null, params);
		} catch (SecurityException | NoSuchMethodException e) {
			logError("Error invoking method " + methodName + " for class" + object.getClass(), e);
		}
		return null;
	}

	/**
	 * Invoke a method of an object which has no parameters.
	 * 
	 * @param method
	 *            the method
	 * @param object
	 *            the object of the method
	 * 
	 * @return the object
	 */
	public static Object invokeMethod(Method method, Object object) {
		return invokeMethod(method, object, new Object[] {});
	}

	/**
	 * Invoke a method of an object.
	 * 
	 * @param method
	 *            the method
	 * @param object
	 *            the object of the method
	 * @param params
	 *            tables of parameters for the invoked method.
	 * 
	 * @return the object result of the invocation of the method²
	 */
	public static Object invokeMethod(Method method, Object object, Object[] params) {
		try {
			return method.invoke(object, params);
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			logError("Error method " + method.getName() + " for class " + method.getDeclaringClass(), e);
		}
		return null;
	}

	/**
	 * Gets the method.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param methodName
	 *            the method name
	 * @param parameterTypes
	 *            the parameter types
	 * 
	 * @return the method
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		try {
			return clazz.getMethod(methodName, parameterTypes);
		} catch (SecurityException | NoSuchMethodException e) {
			logError("Error method " + methodName + " for class " + clazz.getCanonicalName(), e);
		}
		return null;
	}

	/**
	 * Checks for super class.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param superClass
	 *            the super class
	 * 
	 * @return true, if successful
	 */
	public static boolean hasSuperClass(Class<?> clazz, Class<?> superClass) {
		if (superClass == clazz)
			return true;
		if (clazz != Object.class && null != clazz.getSuperclass())
			return hasSuperClass(clazz.getSuperclass(), superClass);
		return false;
	}

	/**
	 * Checks for super class on a depth.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param superClass
	 *            the super class
	 * @param depth
	 *            the depth
	 * 
	 * @return true, if successful
	 */
	public static boolean hasSuperClass(Class<?> clazz, Class<?> superClass, int depth) {
		if (superClass == clazz)
			return true;
		if (clazz != Object.class)
			return 0 != depth && hasSuperClass(clazz.getSuperclass(), superClass, depth - 1);
		return false;
	}

	/**
	 * Checks for interface.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param interf
	 *            the interf
	 * 
	 * @return true, if successful
	 */
	public static boolean hasInterface(Class<?> clazz, Class<?> interf) {
		if (null == clazz)
			return false;
		if (clazz.getName().equals(interf.getName()))
			return true;
		for (Class<?> childInterface : clazz.getInterfaces()) {
			if (childInterface == interf)
				return true;
			if (hasInterface(childInterface, interf))
				return true;
		}
		if (clazz.getSuperclass() != Object.class)
			return hasInterface(clazz.getSuperclass(), interf);
		return false;

	}
}
