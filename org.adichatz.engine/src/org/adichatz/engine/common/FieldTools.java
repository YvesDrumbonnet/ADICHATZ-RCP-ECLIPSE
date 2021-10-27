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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.StringTokenizer;

import jakarta.persistence.Column;

import org.eclipse.swt.graphics.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldTools.
 */
public class FieldTools {

	/**
	 * Gets the get method.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param fieldName
	 *            the field name
	 * @param errorWhenNoSuchMethodException
	 *            the error when no such method exception
	 * 
	 * @return the get method
	 */
	public static Method getGetMethod(Class<?> clazz, String fieldName, boolean errorWhenNoSuchMethodException) {
		if (fieldName.startsWith("_"))
			fieldName = fieldName.substring(1);
		String fieldNameU = EngineTools.upperCaseFirstLetter(fieldName);
		try {
			return clazz.getMethod("get" + fieldNameU, new Class[] {});
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			try {
				return clazz.getMethod("is" + fieldNameU, new Class[] {});
			} catch (SecurityException e1) {
				logError("Error retrieving getter for class " + clazz.getName() + " and field " + fieldName, e1);
			} catch (NoSuchMethodException e1) {
				if (errorWhenNoSuchMethodException)
					logError("Error retrieving getter for class " + clazz.getName() + " and field " + fieldName, e1);
			}
		}
		return null;
	}

	/**
	 * Gets the set method.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param fieldName
	 *            the field name
	 * @param paramClass
	 *            the param class
	 * @param errorWhenNoSuchMethodException
	 *            the error when no such method exception
	 * 
	 * @return the set method
	 */
	public static Method getSetMethod(Class<?> clazz, String fieldName, Class<?> paramClass,
			boolean errorWhenNoSuchMethodException) {
		String methodName = "set" + EngineTools.upperCaseFirstLetter(fieldName);
		try {
			return clazz.getMethod(methodName, new Class[] { paramClass });
		} catch (SecurityException e) {
			logError(getFromEngineBundle("error.setter.class.field", clazz.getName(), fieldName), e);
		} catch (NoSuchMethodException e) {
			// getter return type could be boolean when setter parameter type is Boolean
			if (boolean.class.equals(paramClass)) {
				Method getSetMethod = getSetMethod(clazz, fieldName, Boolean.class, false);
				if (null != getSetMethod)
					return getSetMethod;
			}
			if (errorWhenNoSuchMethodException)
				logError(getFromEngineBundle("error.setter.class.field", clazz.getName(), fieldName), e);
		}
		return null;
	}

	/**
	 * Gets the refering method.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the refering method
	 */
	public static Method getReferingMethod(Class<?> clazz) {
		for (Field referingField : clazz.getDeclaredFields()) {
			Method method = FieldTools.getGetMethod(clazz, referingField.getName(), false);
			if (null != method && method.getReturnType() == String.class)
				return method;
		}
		return null;
	}

	/**
	 * Checks if field is date.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param column
	 *            the column
	 * 
	 * @return true, if field is date
	 * 
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchFieldException
	 *             the no such field exception
	 */
	public static boolean isDateField(Class<?> entityClass, String column) throws SecurityException, NoSuchFieldException {
		try {
			return isDateType(entityClass.getDeclaredField(column).getType());
		} catch (SecurityException | NoSuchFieldException e) {
			logError(e);
		}
		return false;
	}

	/**
	 * Checks if is boolean type.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return true, if is boolean type
	 */
	public static boolean isBooleanType(Class<?> type) {
		if (type == boolean.class || type == Boolean.class)
			return true;
		if (null != type.getSuperclass() && type.getSuperclass() != Object.class)
			return isBooleanType(type.getSuperclass());
		return false;
	}

	/**
	 * Checks if is image type.
	 * 
	 * @param returnType
	 *            the return type
	 * @return true, if is image type
	 */
	public static boolean isImageType(Class<?> returnType) {
		return returnType.isArray() && byte.class.getName().equals(returnType.getComponentType().getName());
	}

	/**
	 * Checks if is comparable.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param column
	 *            the column
	 * 
	 * @return true, if is comparable
	 */
	public static boolean isComparable(Class<?> clazz, String column) {
		try {
			return ReflectionTools.hasInterface(clazz.getDeclaredField(column).getType(), Comparable.class);
		} catch (SecurityException | NoSuchFieldException e) {
			logError(e);
		}
		return false;
	}

	/**
	 * Checks if is date type.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return true, if is date type
	 */
	public static boolean isDateType(Class<?> type) {
		if (type == Date.class)
			return true;
		if (null != type.getSuperclass() && type.getSuperclass() != Object.class)
			return isDateType(type.getSuperclass());
		return false;
	}

	/**
	 * Checks if is integer type.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return true, if is integer type
	 */
	public static boolean isIntegerType(Class<?> type) {
		if (type == short.class || type == Short.class || type == long.class || type == Long.class || type == int.class
				|| type == Integer.class)
			return true;
		return false;
	}

	/**
	 * Checks if is field numeric.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param column
	 *            the column
	 * @return true, if field is numeric
	 */
	public static boolean isNumericField(Class<?> entityClass, String column) {
		try {
			return isNumericType(entityClass.getDeclaredField(column).getType());
		} catch (SecurityException | NoSuchFieldException e) {
			logError(e);
		}
		return false;
	}

	/**
	 * Checks if is mandatory.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param column
	 *            the column
	 * @return true, if is mandatory
	 */
	public static boolean isMandatory(Class<?> entityClass, String column) {
		Method getMethod = getGetMethod(entityClass, column, true);
		Column columnAnnotation = getMethod.getAnnotation(Column.class);
		return (null != columnAnnotation && !columnAnnotation.nullable());
	}

	/**
	 * Checks if is number type.
	 * 
	 * @param type
	 *            the type
	 * @return true, if is number type
	 */
	public static boolean isNumberType(Class<?> type) {
		if (type == byte.class || type == Byte.class || type == short.class || type == Short.class || type == long.class
				|| type == Long.class || type == int.class || type == Integer.class || type == float.class || type == Float.class
				|| type == double.class || type == Double.class)
			return true;
		if (null != type.getSuperclass() && type.getSuperclass() != Object.class)
			return isNumberType(type.getSuperclass());
		return false;
	}

	/**
	 * Checks if is big decimal type.
	 * 
	 * @param type
	 *            the type
	 * @return true, if is big decimal type
	 */
	public static boolean isBigDecimalType(Class<?> type) {
		if (type == BigDecimal.class)
			return true;
		if (null != type.getSuperclass() && type.getSuperclass() != Object.class)
			return isBigDecimalType(type.getSuperclass());
		return false;
	}

	/**
	 * Checks if is numeric type.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return true, if is numeric type
	 */
	public static boolean isNumericType(Class<?> type) {
		return isNumberType(type) || isBigDecimalType(type);
	}

	public static Point getSize(String sizeString) {
		Point size = null;
		if (null != sizeString) {
			StringTokenizer tokenizer = new StringTokenizer(sizeString, ",");
			if (2 != tokenizer.countTokens())
				return null;
			try {
				size = new Point(Integer.parseInt(tokenizer.nextToken().trim()), Integer.parseInt(tokenizer.nextToken().trim()));
			} catch (NumberFormatException e) {
				logError(getFromEngineBundle("invalid.size.format", sizeString));
			}
		}
		return size;
	}
}
