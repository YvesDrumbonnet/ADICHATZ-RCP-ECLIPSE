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
package org.adichatz.scenario.impl;

// *********************
// *** C A U T I O N ***
// *********************
//
// this class is dynamically loaded in the application No reference will be found in the application
//

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.CheckBoxWrapper;
import org.adichatz.generator.wrapper.DateTextWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.FormattedTextWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.ImageViewerWrapper;
import org.adichatz.generator.wrapper.NumericTextWrapper;
import org.adichatz.generator.wrapper.RefTextWrapper;
import org.adichatz.generator.wrapper.TableColumnWrapper;
import org.adichatz.generator.wrapper.TextWrapper;
import org.adichatz.generator.xjc.CheckBoxType;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.DateTextType;
import org.adichatz.generator.xjc.EditableFormTextType;
import org.adichatz.generator.xjc.EmbeddedFieldType;
import org.adichatz.generator.xjc.EmbeddedIdFieldType;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ImageTypeEnum;
import org.adichatz.generator.xjc.ImageViewerType;
import org.adichatz.generator.xjc.JointureType;
import org.adichatz.generator.xjc.ManyToManyType;
import org.adichatz.generator.xjc.NumericTextType;
import org.adichatz.generator.xjc.OneToManyType;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.OneToOneTypeEnum;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.QueryReferenceType;
import org.adichatz.generator.xjc.QueryTree;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.generator.xjc.RefTextType;
import org.adichatz.generator.xjc.TextType;
import org.adichatz.jpa.data.AdiOneToOne;
import org.adichatz.jpa.data.AdiOneToOnes;
import org.adichatz.jpa.data.CompositeKeyStrategyFactory;
import org.adichatz.scenario.IEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.util.ScenarioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class EntityScenario extends AScenario implements IEntityScenario {

	/** The bean class. */
	protected Class<?> beanClass;

	/**
	 * Instantiates a new a entity scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public EntityScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.scenario.IEntityScenario#createEntitiesTree(java.util.Properties)
	 */
	@Override
	public EntityTreeWrapper createEntityTree(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;

		EntityTreeWrapper entityTree;
		if (scenarioInput.isGenerateXml()) {
			File entityDir = new File(scenarioInput.getScenarioResources().getGencodePath().getXmlFilesLocation() + "/"
					+ scenarioInput.getSubPackage().replace('.', '/'));
			if (!entityDir.exists())
				entityDir.mkdirs();
			entityTree = new EntityTreeWrapper();
			entityTree.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());

			create(entityTree);
		} else {
			entityTree = scenarioInput.getPluginEntityWrapper().getEntityTree();
		}

		createXmlAndPrepare(scenarioInput.setXmlElement(entityTree));
		return entityTree;
	}

	/**
	 * Creates the.
	 * 
	 * @param entityTree
	 *            the entity tree
	 */
	protected void create(EntityTreeWrapper entityTree) {
		beanClass = scenarioInput.getPluginEntityWrapper().getBeanClass();
		Set<String> otoFieldNames = new HashSet<>();
		for (Field field : beanClass.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())) {
				Method method = FieldTools.getGetMethod(beanClass, field.getName(), false);
				if (null != method) {
					if (null != method.getAnnotation(Id.class))
						entityTree.setIdFieldName(field.getName());
					if (null != method.getAnnotation(EmbeddedId.class)) {
						entityTree.setIdFieldName(field.getName());
						entityTree.getPropertyField().add(addEmbeddIdColumn(beanClass, method, field));
						Field[] embeddedIdFields = field.getType().getDeclaredFields();
						Field lastEmbeddedField = embeddedIdFields[embeddedIdFields.length - 1];
						if (FieldTools.isNumericType(lastEmbeddedField.getType()))
							entityTree.setCompositeKeyStrategyFactoryClassName(CompositeKeyStrategyFactory.class.getName());
					} else if (null != method.getAnnotation(OneToOne.class)) {
						addOneToOneColumn(entityTree, field, method);
						otoFieldNames.add(field.getName());
					} else if (null != method.getAnnotation(Column.class) || null != method.getAnnotation(JoinColumn.class)
							|| null != method.getAnnotation(JoinColumns.class)) {
						addPropertyField(entityTree, field, method);
					} else if (null != method.getAnnotation(OneToMany.class)) {
						addOneToManyColumn(entityTree, field);
					} else if (null != method.getAnnotation(ManyToMany.class)) {
						addManyToManyColumn(entityTree, field);
					}
				}
			}
		}
		AdiOneToOnes adiOTOs = beanClass.getAnnotation(AdiOneToOnes.class);
		if (null != adiOTOs) {
			for (AdiOneToOne oto : adiOTOs.oneToOnes()) {
				if (!otoFieldNames.contains(oto.fieldName())) {
					OneToOneType oneToOne = new OneToOneType();
					oneToOne.setId(oto.fieldName());
					oneToOne.setEntityURI(oto.entityURI());
					oneToOne.setMappedBy(oto.mappedBy());
				}
			}
		}
	}

	protected void addPropertyField(EntityTreeWrapper entityTree, Field field, Method method) {
		if (Blob.class != method.getReturnType() && Clob.class != method.getReturnType()
				&& Serializable.class != method.getReturnType()) {
			PropertyFieldType propertyField = addField(beanClass, entityTree, field, method);
			if (null != propertyField) // Null when the RefField does not correspond to an Entity managed by the MetaModel
				entityTree.getPropertyField().add(propertyField);
		} else {
			logWarning(getFromGeneratorBundle("generation.nothing.todo.for.column", entityTree.getEntityURI(), field.getName()));
		}
	}

	/**
	 * Adds the one to many column.
	 * 
	 * @param entityTree
	 *            the entity tree
	 * @param method
	 *            the method
	 */
	protected void addOneToManyColumn(EntityTree entityTree, Field field) {
		Method getMethod = FieldTools.getGetMethod(beanClass, field.getName(), true);
		Class<?> referencedClass = ScenarioUtil.getGeneric(field);
		PluginEntity pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntity(referencedClass);
		if (null == pluginEntity)
			logWarning(getFromGeneratorBundle("generation.entity.ref.noEntityFoundForPlugin", field.getName(),
					entityTree.getEntityURI(), referencedClass));
		else {
			String entityURI = pluginEntity.getEntityURI();
			String mappedBy = getMethod.getAnnotation(OneToMany.class).mappedBy();
			OneToManyType oneToMany = new OneToManyType();
			oneToMany.setId(field.getName());
			oneToMany.setMappedBy(mappedBy);
			oneToMany.setEntityURI(entityURI);
			PluginEntity genericPE = scenarioInput.getPluginEntityTree().getPluginEntity(ScenarioUtil.getGeneric(field));
			QueryReferenceType queryReference = new QueryReferenceType();
			String adiResourceURI = EngineTools.getAdiResourceURI(genericPE.getEntityKeys()[0], genericPE.getEntityKeys()[1],
					genericPE.getEntityId() + "QUERY");
			queryReference.setAdiQueryURI(adiResourceURI);
			QueryTree queryTree = (QueryTree) new GeneratorUnit(new ScenarioInput(scenarioInput, null, adiResourceURI))
					.getTreeWrapperFromXml(true);

			String parentClause = null;
			for (JointureType join : queryTree.getJointure()) {
				if (join.getFieldName().equals(mappedBy))
					parentClause = queryTree.getSuffix() + "." + join.getFieldName() + " = ?#";
			}
			if (null == parentClause)
				logError(getFromGeneratorBundle("scenario.cannot.generate.correct.entitySet", entityTree.getEntityURI(),
						field.getName(), adiResourceURI, mappedBy));
			oneToMany.setParentClause(parentClause);
			oneToMany.setQueryReference(queryReference);

			entityTree.getOneToMany().add(oneToMany);
		}
	}

	/**
	 * Adds the many to many column.
	 * 
	 * @param entityTree
	 *            the entity tree
	 * @param method
	 *            the method
	 */
	protected void addManyToManyColumn(EntityTree entityTree, Field field) {
		Method getMethod = FieldTools.getGetMethod(beanClass, field.getName(), true);
		String mappedBy = getMethod.getAnnotation(ManyToMany.class).mappedBy();
		String entityURI = null;
		Class<?> referencedClass = ScenarioUtil.getGeneric(field);
		PluginEntity pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntity(referencedClass);
		if (null == pluginEntity)
			logWarning(getFromGeneratorBundle("generation.entity.ref.noEntityFoundForPlugin", field.getName(),
					entityTree.getEntityURI(), referencedClass));
		else {
			entityURI = pluginEntity.getEntityURI();
			if (EngineTools.isEmpty(mappedBy)) {
				for (Field rField : referencedClass.getDeclaredFields())
					if (!Modifier.isStatic(rField.getModifiers())) {
						Method childGetMethod = FieldTools.getGetMethod(referencedClass, rField.getName(), true);
						ManyToMany manyToManyAnnotation = childGetMethod.getAnnotation(ManyToMany.class);
						if (null != manyToManyAnnotation && field.getName().equals(manyToManyAnnotation.mappedBy())) {
							mappedBy = rField.getName();
							break;
						}
					}
			}
			ManyToManyType manyToMany = new ManyToManyType();
			manyToMany.setId(field.getName());
			manyToMany.setMappedBy(mappedBy);
			manyToMany.setEntityURI(entityURI);
			PluginEntity genericPE = scenarioInput.getPluginEntityTree().getPluginEntity(ScenarioUtil.getGeneric(field));
			QueryReferenceType queryReference = new QueryReferenceType();
			String adiResourceURI = EngineTools.getAdiResourceURI(genericPE.getEntityKeys()[0], genericPE.getEntityKeys()[1],
					genericPE.getEntityId() + "QUERY");
			queryReference.setAdiQueryURI(adiResourceURI);
			QueryTree queryTree = (QueryTree) new GeneratorUnit(new ScenarioInput(scenarioInput, null, adiResourceURI))
					.getTreeWrapperFromXml(true);
			manyToMany.setParentClause("?# in elements (" + queryTree.getSuffix() + "." + mappedBy + ")");
			manyToMany.setQueryReference(queryReference);

			entityTree.getManyToMany().add(manyToMany);
		}
	}

	/**
	 * Adds the one to one column.
	 * 
	 * @param entityTree
	 *            the entity tree
	 * @param field
	 *            the field
	 */
	protected void addOneToOneColumn(EntityTree entityTree, Field field, Method method) {
		PluginEntity<?> pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntity(method.getReturnType());
		if (null == pluginEntity)
			throw new RuntimeException(
					"No entity found for class <" + method.getReturnType().getName() + "> when adding OneToOne aspect to entity <"
							+ entityTree.getEntityURI() + "> and field <" + field.getName() + ">!");
		OneToOneType oneToOne = new OneToOneType();
		oneToOne.setId(field.getName());
		oneToOne.setEntityURI(pluginEntity.getEntityURI());
		OneToOne oneToOneAnnotation = method.getAnnotation(OneToOne.class);
		if (!EngineTools.isEmpty(oneToOneAnnotation.mappedBy())) {
			oneToOne.setOneToOneType(OneToOneTypeEnum.MASTER);
			oneToOne.setMappedBy(oneToOneAnnotation.mappedBy());
		} else {
			if (method.isAnnotationPresent(PrimaryKeyJoinColumn.class)) {
				oneToOne.setOneToOneType(OneToOneTypeEnum.SLAVE);
			}
		}
		entityTree.getOneToOne().add(oneToOne);
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
	protected PropertyFieldType addField(Class<?> beanClass, EntityTreeWrapper entityTree, Field field, Method method) {
		PropertyFieldType propertyField;
		String fieldId = getFieldName(method);
		JoinColumn joinColumnAnnotation = method.getAnnotation(JoinColumn.class);
		JoinColumns joinColumnsAnnotation = method.getAnnotation(JoinColumns.class);
		Column columnAnnotation = method.getAnnotation(Column.class);
		if (null != joinColumnAnnotation || null != joinColumnsAnnotation) {
			boolean nullable;
			if (null != joinColumnAnnotation)
				nullable = joinColumnAnnotation.nullable();
			else
				nullable = joinColumnsAnnotation.value()[0].nullable();
			propertyField = new RefFieldType();
			if (!nullable)
				propertyField.setMandatory(true);
			String parentMappedBy = null;
			Method getMethod = FieldTools.getGetMethod(beanClass, fieldId, true);

			for (Method parentMethod : getMethod.getReturnType().getDeclaredMethods()) {
				OneToMany oneToManyAnnotation = parentMethod.getAnnotation(OneToMany.class);
				// equality on class doesn't work at any time so equality on class name is used.
				if (null != oneToManyAnnotation && oneToManyAnnotation.mappedBy().equals(fieldId) && scenarioInput
						.getPluginEntityWrapper().getBeanClass().getName().equals(getGeneric(parentMethod).getName())) {
					parentMappedBy = parentMethod.getName().substring(parentMethod.getName().startsWith("get") ? 3 : 2);
					parentMappedBy = parentMappedBy.substring(0, 1).toLowerCase() + parentMappedBy.substring(1);
					break;
				}
			}
			((RefFieldType) propertyField).setParentMappedBy(parentMappedBy);
			PluginEntity pluginEntity = scenarioInput.getPluginEntityTree().getPluginEntity(getMethod.getReturnType());
			if (null == pluginEntity) {
				logWarning(getFromGeneratorBundle("generation.entity.ref.noEntityFoundForPlugin", fieldId,
						entityTree.getEntityURI(), getMethod.getReturnType()));
				return null;
			}
			((RefFieldType) propertyField).setEntityURI(pluginEntity.getEntityURI());
		} else {
			propertyField = new PropertyFieldType();
			if (null != columnAnnotation && !columnAnnotation.nullable())
				propertyField.setMandatory(true);
		}
		propertyField.setId(fieldId);
		ControlFieldType controlField = createControlField(propertyField, method);
		if ((null != joinColumnAnnotation && !joinColumnAnnotation.updatable())
				|| (null != joinColumnsAnnotation && !joinColumnsAnnotation.value()[0].updatable())
				|| (null != columnAnnotation && !columnAnnotation.updatable()))
			controlField.setEnabled("false");

		if (null != controlField && !(controlField instanceof ImageViewerType) && !(controlField instanceof EditableFormTextType))
			propertyField.setColumnField(addColumnField(beanClass, propertyField, field, method));
		return propertyField;
	}

	protected PropertyFieldType addEmbeddIdColumn(Class<?> beanClass, Method method, Field field) {
		PropertyFieldType customizedPropertyField = scenarioInput.getPluginEntityWrapper().getPropertyField(field.getName());
		if (null != customizedPropertyField)
			return customizedPropertyField;
		EmbeddedIdFieldType idField = new EmbeddedIdFieldType();
		if ("getId".equals(method.getName())) {
			/*
			 * process Embedded Id field
			 */
			idField.setId("id");

			AttributeOverride[] attributeOverrides = method.getAnnotation(AttributeOverrides.class).value();
			for (int i = 0; i < attributeOverrides.length; i++) {
				EmbeddedFieldType embeddedField = new EmbeddedFieldType();
				embeddedField.setId("id#" + attributeOverrides[i].name());
				Class<?> idClass = method.getReturnType();

				Method getMethod = FieldTools.getGetMethod(idClass, attributeOverrides[i].name(), true);
				ControlFieldType controlField = createControlField(embeddedField, getMethod);
				if (null != controlField) {
					controlField.setLabelText("#MSG(" + EngineTools.lowerCaseFirstLetter(beanClass.getSimpleName()) + ", id."
							+ attributeOverrides[i].name() + ")");
					controlField.setConvertModelToTarget("return null == value ? null : #FV()." + getMethod.getName() + "();");
					controlField.setEnabled("false");
				}

				idField.getEmbeddedField().add(embeddedField);

				ColumnFieldType columnField = addColumnField(idClass, embeddedField, field, method);
				embeddedField.setColumnField(columnField);
				if (null != columnField)
					columnField.setColumnText(
							"return null == #ROW().getId() ? \"\" : String.valueOf(#ROW().getId()." + getMethod.getName() + "());");
			}
		} else
			logError(getFromGeneratorBundle("scenario.doNotKnowHowToCreateEmbeddedId"));
		return idField;
	}

	/**
	 * Adds the table column.
	 * 
	 * @param beanClass
	 *            the bean class
	 * @param propertyField
	 *            the property field
	 * @param field
	 *            the field
	 * @param method
	 *            the method
	 * 
	 * @return the table column type
	 */
	protected ColumnFieldType addColumnField(Class<?> beanClass, PropertyFieldType propertyField, Field field, Method method) {
		PropertyFieldType customizedPropertyField = scenarioInput.getPluginEntityWrapper().getPropertyField(propertyField.getId());
		if (null != customizedPropertyField && null != customizedPropertyField.getColumnField())
			return customizedPropertyField.getColumnField();
		customizedPropertyField = ((GenerationScenarioWrapper) scenarioInput.getScenarioResources().getScenarioTree()
				.getGenerationScenario()).getPropertyField(propertyField.getId());
		if (null != customizedPropertyField && null != customizedPropertyField.getColumnField())
			return customizedPropertyField.getColumnField();

		String columnId;
		Column columnAnnotation = null;
		JoinColumn joinColumnAnnotation = null;
		JoinColumns joinColumnsAnnotation = null;
		if (propertyField instanceof EmbeddedFieldType) {
			columnId = propertyField.getId().substring(propertyField.getId().lastIndexOf('#') + 1);
		} else {
			columnAnnotation = method.getAnnotation(Column.class);
			joinColumnAnnotation = method.getAnnotation(JoinColumn.class);
			joinColumnsAnnotation = method.getAnnotation(JoinColumns.class);
			columnId = propertyField.getId();
		}
		TableColumnWrapper tableColumn = null;
		if (propertyField instanceof EmbeddedFieldType || null != columnAnnotation || null != joinColumnAnnotation
				|| null != joinColumnsAnnotation) {
			Method getMethod = FieldTools.getGetMethod(beanClass, columnId, true);
			tableColumn = new TableColumnWrapper();
			if (FieldTools.isBooleanType(method.getReturnType()))
				tableColumn.setPattern("CHECK_ONLY");
			else if (FieldTools.isNumericType(method.getReturnType()))
				tableColumn.setPattern(getNumericPattern(field.getType(), columnAnnotation));
			if (null != joinColumnAnnotation || null != joinColumnsAnnotation) {
				String firstFragment = "#ROW()." + columnId;
				String refMember = getReferencedMemberForTable(firstFragment, getMethod.getReturnType());
				tableColumn.setColumnText("return null != " + firstFragment + " ? " + refMember + " : \"\";");
			}
			if (method.getReturnType().isArray())
				tableColumn.setColumnText("return getArrayColumnText(#ROW()." + columnId + ");");
			if (!(propertyField instanceof EmbeddedFieldType))
				tableColumn.setProperty(propertyField.getId());
			tableColumn.setSorted(true);
			tableColumn.setStyle(getTableStyle(getMethod));
		}
		return tableColumn;
	}

	/**
	 * Creates the control field.
	 * 
	 * @param field
	 *            the field
	 * @param method
	 *            the method
	 * 
	 * @return the control field type
	 */
	protected ControlFieldType createControlField(PropertyFieldType field, Method method) {
		PropertyFieldType customizedPropertyField = scenarioInput.getPluginEntityWrapper().getPropertyField(field.getId());
		if (null == customizedPropertyField) {
			customizedPropertyField = ((GenerationScenarioWrapper) scenarioInput.getScenarioResources().getScenarioTree()
					.getGenerationScenario()).getPropertyField(field.getId());
		}
		ControlFieldType controlField;
		if (null != customizedPropertyField && null != customizedPropertyField.getControlField()) {
			controlField = customizedPropertyField.getControlField();
			field.setControlField(controlField);
			return controlField;
		}
		if (field instanceof RefFieldType) {
			controlField = new RefTextWrapper();
		} else if (FieldTools.isDateType(method.getReturnType())) {
			controlField = new DateTextWrapper();
		} else if (FieldTools.isNumberType(method.getReturnType()) || FieldTools.isBigDecimalType(method.getReturnType())) {
			controlField = new FormattedTextWrapper();
			((FormattedTextWrapper) controlField).setFormat(getFormat(method.getReturnType()));
			if (method.getReturnType() == BigDecimal.class) {
				controlField.setConvertModelToTarget("return null == value ? null : ((java.math.BigDecimal) value).doubleValue();");
				controlField.setConvertTargetToModel("return null == value ? null : new java.math.BigDecimal((Double) value);");
			}
			((FormattedTextWrapper) controlField).setEditPattern(getPattern(method));
		} else if (FieldTools.isBigDecimalType(method.getReturnType())) {
			controlField = new NumericTextWrapper();
			((NumericTextWrapper) controlField).setPattern(getPattern(method));
		} else if (FieldTools.isBooleanType(method.getReturnType())) {
			controlField = new CheckBoxWrapper();
		} else if (FieldTools.isImageType(method.getReturnType())) {
			ImageViewerWrapper imageViewer = new ImageViewerWrapper();
			controlField = imageViewer;
			String toolStyle = "AdiSWT.EXPANDABLE";
			if (null == field.isMandatory() || !field.isMandatory())
				toolStyle += " | AdiSWT.EDITABLE | AdiSWT.DELETE_BUTTON";
			imageViewer.setToolBarStyle(toolStyle);
			imageViewer.setImageType(ImageTypeEnum.DATA);

		} else {
			/* ExtraTextWrapper or RichText must be specified manually or thru Customization Scenario Tree */
			// Column columnAnnotation = method.getAnnotation(Column.class);
			// if (null != columnAnnotation && 65535 == columnAnnotation.length()) {
			// controlField = new ExtraTextWrapper();
			// ((ExtraTextWrapper) controlField).setAddRefreshItem("true");
			// } else {
			controlField = new TextWrapper();
			addTextLimit(method, (TextType) controlField);
			// }
		}
		/**
		 * Do not set controlField when process from IDetailScenario (or other UI Scenario)<br>
		 * In this case
		 * <ul>
		 * <li>controlField is build to provide convertModelToTarget result.</li>
		 * <li>field is a reference of a field of the EntityTree and must not be changed.</li>
		 * </ul>
		 */
		field.setControlField(controlField);

		controlField.setStyle(getDetailStyle(field, controlField, method));
		if (null == controlField.getConvertModelToTarget())
			controlField.setConvertModelToTarget(getConvertModelToTarget(field, method));
		if (null != field.isMandatory() && field.isMandatory() && null == method.getAnnotation(Id.class))
			controlField.setMandatory(true);
		if (null != method.getAnnotation(Id.class)) {
			if (!String.class.equals(method.getReturnType()))
				// By default Id is computed except if type is String
				controlField.setEnabled("false");
			else
				controlField.setEnabled("2 == #ENTITY().getStatus()");
		}
		return controlField;
	}

	/**
	 * Gets the convert model to target.
	 * 
	 * @param field
	 *            the field
	 * @param method
	 *            the method
	 * 
	 * @return the convert model to target
	 */
	protected String getConvertModelToTarget(PropertyFieldType field, Method method) {
		if (field instanceof RefFieldType) {
			return "return null==value ? \"\" : " + getReferencedMemberForDetail((RefFieldType) field, method.getReturnType())
					+ ";";
		}
		return null;
	}

	/**
	 * Gets the referenced member for detail.
	 * 
	 * @param referencedClass
	 *            the referenced class
	 * 
	 * @return the referenced member for detail
	 */
	protected String getReferencedMemberForDetail(RefFieldType refField, Class<?> referencedClass) {
		Method method;
		String idField = null;
		Method idMethod = null;
		for (Field referingField : referencedClass.getDeclaredFields())
			if (!Modifier.isStatic(referingField.getModifiers())) {
				method = FieldTools.getGetMethod(referencedClass, referingField.getName(), false);
				if (null != method) {
					if (method.getReturnType() == String.class) {
						return "#FV()." + referingField.getName();
					}
					if (null != method.getAnnotation(Id.class)) {
						idField = referingField.getName();
						idMethod = method;
					}
				}
			}
		if (null != idField) {
			addLazyFetch(refField);
			return "String.valueOf(#FV()." + idMethod.getName() + "()" + ")";
		} else {
			return "#FV().toString()";
		}
	}

	protected void addLazyFetch(RefFieldType refField) {
		String lazyFetches = refField.getControlField().getLazyFetches();
		if (null == lazyFetches)
			lazyFetches = refField.getId();
		else
			lazyFetches = lazyFetches + ", " + refField.getId();
		refField.getControlField().setLazyFetches(lazyFetches);
	}

	/**
	 * Adds the text limit.
	 *
	 * @param method
	 *            the method
	 * @param text
	 *            the text
	 */
	protected void addTextLimit(Method method, TextType text) {
		Column columnAnnotation = method.getAnnotation(Column.class);
		if (null != columnAnnotation && 0 != columnAnnotation.length())
			text.setTextLimit(Integer.valueOf(columnAnnotation.length()));
	}

	/**
	 * Gets the detail style.
	 * 
	 * @param field
	 *            the field
	 * @param controlField
	 *            the data field
	 * @param method
	 *            the method
	 * 
	 * @return the detail style
	 */
	protected String getDetailStyle(PropertyFieldType field, ControlFieldType controlField, Method method) {
		if (controlField instanceof NumericTextType) {
			return "SWT.BORDER | SWT.RIGHT";
		} else if (controlField instanceof DateTextType) {
			Temporal temporalAnnotation = method.getAnnotation(Temporal.class);
			String style = "SWT.BORDER";
			if (null == temporalAnnotation || !temporalAnnotation.value().equals(TemporalType.DATE))
				style += " | SWT.TIME";
			if (null == field.isMandatory() || !field.isMandatory())
				style += " | AdiSWT.DELETE_BUTTON";
			return style;
		} else if (controlField instanceof RefTextType) {
			String style = "SWT.BORDER | AdiSWT.FIND_BUTTON | AdiSWT.EDITOR_BUTTON";
			if (null == field.isMandatory() || !field.isMandatory())
				style += " | AdiSWT.DELETE_BUTTON";
			return style;
		} else if (controlField instanceof CheckBoxType) {
			return "SWT.CHECK";
		} else if (controlField instanceof EditableFormTextType) {
			String style = "SWT.BORDER | AdiSWT.EXPANDABLE";
			if (null == field.isMandatory() || !field.isMandatory())
				style += " | AdiSWT.EDITABLE";
			return style;
		}
		return null;
	}

	/**
	 * Gets the table style.
	 * 
	 * @param method
	 *            the method
	 * 
	 * @return the table style
	 */
	protected String getTableStyle(Method method) {
		if (FieldTools.isNumericType(method.getReturnType())) {
			return "SWT.RIGHT";
		} else if (FieldTools.isDateType(method.getReturnType())) {
			return "SWT.CENTER";
		} else
			return null;
	}

	/**
	 * Gets the pattern.
	 * 
	 * @param method
	 *            the method
	 * 
	 * @return the pattern
	 */
	protected String getPattern(Method method) {
		Column columnAnnotation = method.getAnnotation(Column.class);
		String pattern = null;
		if (FieldTools.isIntegerType(method.getReturnType())) {
			if (null == columnAnnotation || 0 == columnAnnotation.precision())
				pattern = scenarioInput.getScenarioResources().getParam(IScenarioConstants.DEFAULT_INTEGER_PATTERN);
			else
				pattern = getDecimalPattern(columnAnnotation.precision(), 0);
		} else {
			if (0 != columnAnnotation.precision())
				if (0 == columnAnnotation.scale())
					pattern = getDecimalPattern(columnAnnotation.precision(), Integer
							.valueOf(scenarioInput.getScenarioResources().getParam(IScenarioConstants.DEFAULT_DECIMAL_SCALE)));
				else
					pattern = getDecimalPattern(columnAnnotation.precision(), columnAnnotation.scale());
		}
		return pattern;
	}

	/**
	 * Gets the numeric pattern.
	 * 
	 * @param field
	 *            the field
	 * @param columnAnnotation
	 *            the column annotation
	 * 
	 * @return the numeric pattern
	 */
	protected String getNumericPattern(Class<?> fieldClass, Column columnAnnotation) {
		String pattern = null;
		if (FieldTools.isIntegerType(fieldClass)) {
			if (0 == columnAnnotation.precision())
				pattern = scenarioInput.getScenarioResources().getParam(IScenarioConstants.DEFAULT_INTEGER_PATTERN);
			else
				pattern = getDecimalPattern(columnAnnotation.precision(), 0);
		} else {
			if (0 != columnAnnotation.precision())
				if (0 == columnAnnotation.scale())
					pattern = getDecimalPattern(columnAnnotation.precision(), Integer
							.valueOf(scenarioInput.getScenarioResources().getParam(IScenarioConstants.DEFAULT_DECIMAL_SCALE)));
				else
					pattern = getDecimalPattern(columnAnnotation.precision(), columnAnnotation.scale());
		}
		return pattern;
	}

	/**
	 * Gets the decimal pattern.
	 * 
	 * @param precision
	 *            the precision
	 * @param scale
	 *            the scale
	 * 
	 * @return the decimal pattern
	 */
	protected String getDecimalPattern(int precision, int scale) {
		String pattern = "";
		for (int i = precision - scale - 1; i >= 0; i--) {
			pattern += "#";
			if (i % 3 == 0 & 0 != i)
				pattern += ",";
		}
		if (0 < scale) {
			pattern += ".";
			for (int i = 0; i < scale; i++)
				pattern += "#";
		}
		return pattern;
	}
}
