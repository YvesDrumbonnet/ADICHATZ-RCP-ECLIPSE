package org.adichatz.scenario.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.OneToManyType;
import org.adichatz.generator.xjc.PropertyFieldType;

public class PlainEntityScenario extends EntityScenario {

	public PlainEntityScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	@Override
	protected void create(EntityTreeWrapper entityTree) {
		beanClass = scenarioInput.getPluginEntityWrapper().getBeanClass();
		for (Method method : beanClass.getMethods()) {

			String methodName = method.getName();
			String fieldName = null;
			if (method.getParameterTypes().length == 0) {
				if (methodName.startsWith("get") || methodName.startsWith("is")) {
					fieldName = EngineTools.lowerCaseFirstLetter(method.getName().substring(methodName.startsWith("get") ? 3 : 2));
					try {
						Field field = method.getDeclaringClass().getDeclaredField(fieldName);
						if (null != field.getAnnotation(XmlAttribute.class)) {
							PropertyFieldType propertyField = new PropertyFieldType();
							propertyField.setId(fieldName);
							entityTree.getPropertyField().add(propertyField);
						}
					} catch (NoSuchFieldException | SecurityException e) {
					}
					Class<?> returnType = method.getReturnType();
					if (ReflectionTools.hasInterface(returnType, List.class)) {
						Class<?> genericClass = (Class<?>) ((ParameterizedType) method.getGenericReturnType())
								.getActualTypeArguments()[0];
						if (null != genericClass.getAnnotation(XmlType.class)
								|| null != genericClass.getSuperclass().getAnnotation(XmlType.class)) {
							OneToManyType oneToMany = new OneToManyType();
							oneToMany.setId(fieldName);
							entityTree.getOneToMany().add(oneToMany);
						}
					}
				}
			}
		}
	}

	protected PropertyFieldType addField(Class<?> beanClass, EntityTreeWrapper entityTree, Field field, Method method) {
		PropertyFieldType propertyField = new PropertyFieldType();
		String fieldId = getFieldName(method);
		propertyField.setId(fieldId);
		createControlField(propertyField, method);
		return propertyField;
	}
}
