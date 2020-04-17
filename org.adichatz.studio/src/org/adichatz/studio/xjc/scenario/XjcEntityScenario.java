/*******************************************************************************
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
package org.adichatz.studio.xjc.scenario;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.CComboWrapper;
import org.adichatz.generator.wrapper.CheckBoxWrapper;
import org.adichatz.generator.wrapper.DateTextWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.NumericTextWrapper;
import org.adichatz.generator.wrapper.RadioGroupWrapper;
import org.adichatz.generator.wrapper.TextWrapper;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.EntityElementType;
import org.adichatz.generator.xjc.EntityElementsType;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.LabelProviderType;
import org.adichatz.generator.xjc.OneToManyType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RefControlType;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.generator.xjc.TableColumnType;
import org.adichatz.scenario.IEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.impl.AScenario;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.db4o.Db4oTools;
import org.adichatz.studio.xjc.controller.CodeTextController;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class XjcEntityScenario extends AScenario implements IEntityScenario {

	private static Set<String> BUILT_ENTITIES = new HashSet<String>();

	private static Set<String> UNMARSHALL_ENTITIES = new HashSet<String>();

	/**
	 * Instantiates a new xjc entity scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public XjcEntityScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	@Override
	public EntityTree createEntityTree(ScenarioInput scenarioInput) {
		this.scenarioInput = new XjcScenarioInput(scenarioInput);
		String treeId = this.scenarioInput.getPluginEntity().getEntityKeys()[2];
		scenarioInput.setTreeId(treeId);
		EntityTreeWrapper entityTree = null;
		if (!((XjcScenarioInput) this.scenarioInput).getGeneratedEntities()
				.contains(this.scenarioInput.getPluginEntity().getEntityURI())) {
			((XjcScenarioInput) this.scenarioInput).getGeneratedEntities().add(this.scenarioInput.getPluginEntity().getEntityURI());
			if (null == treeId)
				treeId = EngineTools.upperCaseFirstLetter(this.scenarioInput.getPluginEntity().getEntityKeys()[2]);
			if (this.scenarioInput.isGenerateXml()) {
				entityTree = new EntityTreeWrapper();
				entityTree.setEntityURI(this.scenarioInput.getPluginEntity().getEntityURI());

				/*
				 * Use canonical name rather than class to avoid difference due to class loader
				 */
				if (!XjcPluginEntityScenario.EXCLUDED_CLASSES
						.contains(this.scenarioInput.getPluginEntityWrapper().getBeanClass().getSuperclass().getCanonicalName())) {
					Class superClass = this.scenarioInput.getPluginEntityWrapper().getBeanClass().getSuperclass();
					PluginEntity superPluginEntity = this.scenarioInput.getPluginEntityTree().getPluginEntity(superClass);
					if (null != superPluginEntity) {
						entityTree.setSuperEntityURI(superPluginEntity.getEntityURI());
						if (!BUILT_ENTITIES.contains(superPluginEntity.getEntityURI())) {
							XjcScenarioInput xjcScenarioInput = new XjcScenarioInput((XjcScenarioInput) this.scenarioInput,
									superPluginEntity);
							xjcScenarioInput.setTreeId(xjcScenarioInput.getTreeId());
							new XjcEntityScenario(getGenerationUnit()).createEntityTree(xjcScenarioInput);

							BUILT_ENTITIES.add(superPluginEntity.getEntityURI());
						}
					}
				}

				create(entityTree);
			} else
				entityTree = (EntityTreeWrapper) new GeneratorUnit(this.scenarioInput).getTreeWrapperFromXml(true);

			if (!UNMARSHALL_ENTITIES.contains(this.scenarioInput.getPluginEntity().getEntityURI())) {
				createXmlAndPrepare(this.scenarioInput.setXmlElement(entityTree));
				UNMARSHALL_ENTITIES.add(this.scenarioInput.getPluginEntity().getEntityURI());
			}
		}
		return entityTree;
	}

	/**
	 * Creates the.
	 * 
	 * @param entityTree
	 *            the entity tree
	 */
	protected void create(EntityTreeWrapper entityTree) {
		Class<?> beanClass = scenarioInput.getPluginEntityWrapper().getBeanClass();
		for (Field field : beanClass.getDeclaredFields()) {
			String fieldName = getFieldName(field);
			if (field.getGenericType() instanceof ParameterizedType) {
				// Field is like List<Entity> entities and corresponds to and entity set
				OneToManyType oneToMany = new OneToManyType();
				entityTree.getOneToMany().add(oneToMany);
				oneToMany.setId(fieldName);
				Class<?> genericClass = ScenarioUtil.getGeneric(field);
				PluginEntity elementAE = scenarioInput.getPluginEntityTree().getPluginEntity(genericClass);
				if (null != elementAE) {
					oneToMany.setEntityURI(elementAE.getEntityURI());
					XmlElements xmlElements = field.getAnnotation(XmlElements.class);
					EntityElementsType entityElements = oneToMany.getEntityElements();
					if (null == entityElements) {
						entityElements = new EntityElementsType();
						oneToMany.setEntityElements(entityElements);
					}
					if (null == xmlElements) {
						PluginEntity xmlElementAE = scenarioInput.getPluginEntityTree().getPluginEntity(genericClass);
						EntityElementType entityElement = new EntityElementType();
						entityElement.setEntityURI(xmlElementAE.getEntityURI());
						entityElement.setName(oneToMany.getId());
						entityElements.getEntityElement().add(entityElement);
					} else {
						for (XmlElement xmlElement : xmlElements.value()) {
							EntityElementType entityElement = new EntityElementType();
							Class<?> elementClass = xmlElement.type();
							entityElement.setName(xmlElement.name());
							if (elementClass.getName().equals("javax.xml.bind.annotation.XmlElement$DEFAULT"))
								elementClass = genericClass;
							PluginEntity xmlElementAE = scenarioInput.getPluginEntityTree().getPluginEntity(elementClass);
							if (!BUILT_ENTITIES.contains(xmlElementAE.getEntityURI())) {
								BUILT_ENTITIES.add(xmlElementAE.getEntityURI());
								XjcScenarioInput xjcScenarioInput = new XjcScenarioInput((XjcScenarioInput) scenarioInput,
										xmlElementAE);
								xjcScenarioInput.setTreeId(xjcScenarioInput.getTreeId());
								new XjcEntityScenario(getGenerationUnit()).createEntityTree(xjcScenarioInput);
							}
							entityElement.setEntityURI(xmlElementAE.getEntityURI());
							entityElements.getEntityElement().add(entityElement);
						}
					}
				}

			} else if (null != field.getAnnotation(XmlAttribute.class)) {
				// Field is an attribute
				entityTree.getPropertyField().add(createField(beanClass, field));
			} else {
				if (String.class.equals(field.getType())) {
					PropertyFieldType fieldType = createField(beanClass, field);
					ControlFieldType control = fieldType.getControlField();
					control.setControllerClassName(CodeTextController.class.getName());
					control.setStyle("SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | AdiSWT.EXPANDABLE");
					entityTree.getPropertyField().add(fieldType);
				} else {
					// Field is an referenced field (ManyToOne relationship)
					PluginEntity pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntity(field.getType());
					if (null != pluginEntity) {
						if (!BUILT_ENTITIES.contains(pluginEntity.getEntityURI())) {
							BUILT_ENTITIES.add(pluginEntity.getEntityURI());
							XjcScenarioInput xjcScenarioInput = new XjcScenarioInput((XjcScenarioInput) scenarioInput,
									pluginEntity);
							xjcScenarioInput.setTreeId(xjcScenarioInput.getTreeId());
							new XjcEntityScenario(getGenerationUnit()).createEntityTree(xjcScenarioInput);
						}
						RefFieldType refField = new RefFieldType();
						refField.setId(getFieldName(field));
						refField.setEntityURI(pluginEntity.getEntityURI());
						entityTree.getPropertyField().add(refField);
					}
				}
			}
		}
	}

	private PropertyFieldType createField(Class<?> beanClass, Field field) {
		String fieldName = getFieldName(field);
		Method method = FieldTools.getGetMethod(beanClass, fieldName, false);
		if (null == method) {
			logError("No method found for field " + fieldName + " of class " + beanClass);
			return null;
		}
		PropertyFieldType propertyField = addField(beanClass, field, method);
		ControlFieldType controlField;
		String controllerClassName = Db4oTools.getInstance().getControllerClassName(scenarioInput.getPluginEntity().getEntityId(),
				field.getName());
		if (null != controllerClassName) {
			GencodePath gencodePath = scenarioInput.getScenarioResources().getGencodePath();
			String wrapperClassName = scenarioInput.getScenarioResources().getWrapper(gencodePath, controllerClassName);
			controlField = (ControlFieldType) gencodePath.instantiateClass(wrapperClassName, new Class[] {}, new Object[] {});
		} else {
			if (FieldTools.isBooleanType(method.getReturnType())) {
				if (method.getReturnType().isPrimitive())
					controlField = new CheckBoxWrapper();
				else {
					controlField = new RadioGroupWrapper();
					controlField.setControllerClassName("org.adichatz.studio.xjc.controller.BooleanRadioGroupController");
				}
			} else if (FieldTools.isNumericType(method.getReturnType())) {
				controlField = new NumericTextWrapper();
				((NumericTextWrapper) controlField)
						.setPattern(scenarioInput.getScenarioResources().getParam(IScenarioConstants.DEFAULT_INTEGER_PATTERN));
			} else if (method.getReturnType().isEnum()) {
				if (3 < method.getReturnType().getEnumConstants().length) {
					controlField = new CComboWrapper();
				} else
					controlField = new RadioGroupWrapper();
			} else if (method.getReturnType().equals(String.class))
				controlField = new TextWrapper();
			else if (method.getReturnType().equals(byte[].class)) {
				controlField = new TextWrapper();
				controlField.setConvertModelToTarget("return org.adichatz.common.encoding.Base64.decode(#FV());");
				controlField.setConvertTargetToModel("return org.adichatz.common.encoding.Base64.encode(#FV());");
			} else if (method.getReturnType().equals(XMLGregorianCalendar.class)) {
				controlField = new DateTextWrapper();
				controlField.setControllerClassName("org.adichatz.studio.xjc.controller.XMLGregorianCalendarTextController");
				TableColumnType columnField = new TableColumnType();
				columnField.setColumnText("return null == text ? \"\" : #Row().getMergeDate().toGregorianCalendar().getTime();");
				propertyField.setColumnField(columnField);
			} else
				throw new RuntimeException("Don't know how to build property field for class '" + beanClass.getName()
						+ "' and field '" + field.getName() + "'!");
		}
		if (FieldTools.isNumericType(method.getReturnType()))
			controlField.setLayoutData("width min:100:150, growx");
		else if (method.getReturnType().isEnum()) {
			StringBuffer initValueSB = new StringBuffer();
			initValueSB.append("import ").append(method.getReturnType().getName()).append(END_LINE);
			initValueSB.append("import ").append(Arrays.class.getName()).append(END_LINE);
			initValueSB.append("return Arrays.asList(").append(method.getReturnType().getSimpleName())
					.append(".class.getEnumConstants());");
			((RefControlType) controlField).setInitValues(initValueSB.toString());

			LabelProviderType labelProvider = new LabelProviderType();
			labelProvider.setTextCode("return ((" + method.getReturnType().getSimpleName() + ") element).value();");
			((RefControlType) controlField).setLabelProvider(labelProvider);
			if (controlField instanceof CComboWrapper) {
				controlField.setLayoutData("grow 0");
				controlField.setStyle("SWT.BORDER | SWT.READ_ONLY");
			}
		}
		propertyField.setControlField(controlField);
		XmlAttribute xmlAttribute = field.getAnnotation(XmlAttribute.class);
		if (null != xmlAttribute && xmlAttribute.required()) {
			propertyField.setMandatory(true);
			controlField.setMandatory(true);
		}
		return propertyField;
	}

	private String getFieldName(Field field) {
		if (field.getName().startsWith("_"))
			return field.getName().substring(1);
		return field.getName();
	}

	/**
	 * Adds the field.
	 * 
	 * @param beanClass
	 *            the bean class
	 * @param entityTree
	 *            the entity tree
	 * @param field
	 *            the field
	 * @param method
	 *            the method
	 * 
	 * @return the property field type
	 */
	protected PropertyFieldType addField(Class<?> beanClass, Field field, Method method) {
		PropertyFieldType propertyField = new PropertyFieldType();
		propertyField.setId(getFieldName(method));
		return propertyField;
	}

}
