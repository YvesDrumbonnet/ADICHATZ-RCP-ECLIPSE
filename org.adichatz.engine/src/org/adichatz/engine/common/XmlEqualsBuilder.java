package org.adichatz.engine.common;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class XmlEqualsBuilder {
	public static boolean reflectionEquals(Object object1, Object object2, String... excludedMethodName) {
		if (null == object1) {
			return null == object2;
		} else {
			if (null == object2)
				return false;
			Class<?> xmlClass = EngineTools.getChildXjcClass(object1);
			if (null == xmlClass)
				return false;
			if (!ReflectionTools.hasSuperClass(object2.getClass(), xmlClass))
				return false;
			return equalsElements(xmlClass, object1, object2, excludedMethodName);
		}
	}

	private static boolean equalsElements(Class<?> xmlClass, Object object1, Object object2, String... excludedMethodName) {
		for (Method method : xmlClass.getMethods()) {
			if (Object.class != method.getDeclaringClass() && 0 == method.getParameterTypes().length
					&& void.class != method.getReturnType()) {
				if (null != excludedMethodName)
					for (int i = 0; i < excludedMethodName.length; i++)
						if (excludedMethodName[i].equals(method.getName()))
							return true;
				try {
					Object value1 = method.invoke(object1);
					Object value2 = method.invoke(object2);
					if (null == value1) {
						if (null != value2)
							return false;
					} else {
						if (null == value2)
							return false;
						if (value1.getClass().isPrimitive()) {
							if (value1 != value2)
								return false;
						} else {
							if (value1 instanceof List) {
								List<?> list1 = (List<?>) value1;
								List<?> list2 = (List<?>) value2;
								int size = list1.size();
								if (size != list2.size())
									return false;
								if (0 < size) {
									Class<?> listElementClass = list1.get(0).getClass();
									if (listElementClass.isPrimitive() || listElementClass.isEnum()) {
										for (int i = 0; i < size; i++)
											if (list1.get(i) != list2.get(i))
												return false;
									} else {
										Class<?> valueXmlClass = EngineTools.getChildXjcClass(listElementClass);
										if (null != valueXmlClass)
											for (int i = 0; i < size; i++) {
												if (!equalsElements(valueXmlClass, list1.get(i), list2.get(i), excludedMethodName))
													return false;
											}
										else
											for (int i = 0; i < size; i++)
												if (!list1.get(i).equals(list2.get(i)))
													return false;
									}
								}
							} else {
								Class<?> valueClass = value1.getClass();
								if (valueClass.isPrimitive() || valueClass.isEnum()) {
									if (value1 != value2)
										return false;
								} else {
									Class<?> valueXmlClass = EngineTools.getChildXjcClass(value1);
									if (null != valueXmlClass && !valueXmlClass.equals(Object.class)) {
										if (!equalsElements(valueXmlClass, value1, value2, excludedMethodName))
											return false;
									} else {
										if (!value1.equals(value2))
											return false;
									}
								}
							}
						}
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					logError(e);
					return false;
				}
			}
		}
		return true;
	}
}
