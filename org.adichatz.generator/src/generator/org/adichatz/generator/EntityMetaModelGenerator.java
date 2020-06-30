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
package org.adichatz.generator;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.adichatz.common.ejb.AEntityCallfore;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.data.ICompositeKeyStrategyFactory;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.model.OneToOneField;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.TransformTreeTools;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.EmbeddedFieldType;
import org.adichatz.generator.xjc.EmbeddedIdFieldType;
import org.adichatz.generator.xjc.EntityElementType;
import org.adichatz.generator.xjc.EntitySetType;
import org.adichatz.generator.xjc.ManyToManyType;
import org.adichatz.generator.xjc.NumericTextType;
import org.adichatz.generator.xjc.OneToManyType;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.jpa.data.CompositeKeyStrategyFactory;
import org.adichatz.jpa.data.ManyToManyEntitySet;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityMetaModelGenerator.
 */
public class EntityMetaModelGenerator extends AClassGenerator {

	/** The tree wrapper. */
	protected final EntityTreeWrapper entityTree;

	/** The set id method. */
	private Method setIdMethod;

	/** The get id method. */
	private Method getIdMethod;

	/** The get id string. */
	private String getIdString;

	/** The UI bean class. */
	protected Class<?> uiBeanClass;

	/** The bean class. */
	protected Class<?> beanClass;

	/** The first step. */
	protected boolean firstStep = true;

	private PluginEntityWrapper pluginEntityWrapper;

	protected Class<?> entitySetClass;

	/**
	 * Instantiates a new entity meta model generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public EntityMetaModelGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		this.entityTree = (EntityTreeWrapper) scenarioInput.getXmlElement();
		new TransformTreeTools(scenarioInput, entityTree);
		pluginEntityWrapper = scenarioInput.getPluginEntityWrapper(entityTree.getEntityURI());
		uiBeanClass = pluginEntityWrapper.getUIBeanClass();
		beanClass = pluginEntityWrapper.getBeanClass();
		setEntitySetClass();
		createClassFile(scenarioInput);
	}

	protected void setEntitySetClass() {
		entitySetClass = EntitySet.class;
	}

	protected void createClassFile(ScenarioInput scenarioInput) {
		createClassFile(scenarioInput,
				" extends " + getObjectName(AEntityMetaModel.class) + "<" + getObjectName(uiBeanClass) + ">");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates the MetaModel class for " + className + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(PluginEntity.class) + "<?> pluginEntity) {");
		classBodyBuffer.append("super(pluginEntity);");
		if (!EngineTools.isEmpty(entityTree.getSuperEntityURI())) {
			classBodyBuffer.append("pluginEntity.setSuperEntityURI("
					+ keyWordGenerator.evalExpr2Str(classBodyBuffer, entityTree.getSuperEntityURI(), false) + ");");
		}
		classBodyBuffer.append("create();");
		classBodyBuffer.appendMinus("}\n");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Create contents for MetaModel " + className + ".");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public void create(){");
		setIdMethod = null;
		getIdMethod = null;

		if (!EngineTools.isEmpty(entityTree.getCompositeKeyStrategyFactoryClassName())) {
			classBodyBuffer.append("cksFactory = (" + getObjectName(ICompositeKeyStrategyFactory.class)
					+ ") pluginEntity.getPluginEntityTree().getPluginResources().getGencodePath().instantiateClass("
					+ keyWordGenerator.evalExpr2Str(classBodyBuffer, entityTree.getCompositeKeyStrategyFactoryClassName(), false)
					+ ", new Class[]{}, new Object[]{});");
		}

		if (!EngineTools.isEmpty(entityTree.getIdFieldName()))
			classBodyBuffer.append("idFieldName = \"" + entityTree.getIdFieldName() + "\";");

		/*
		 * Add fields to Entity Model
		 */
		for (PropertyFieldType propertyField : entityTree.getPropertyField())
			addField(propertyField);

		/*
		 * Add One To One relationship to Entity Model
		 */
		for (OneToOneType oneToOneField : entityTree.getOneToOne()) {
			addOneToOne(oneToOneField);
		}
		/*
		 * Add One To Many set to Entity Model
		 */
		if (!entityTree.getOneToMany().isEmpty()) {
			firstStep = true;
			for (OneToManyType oneToMany : entityTree.getOneToMany()) {
				addOneToMany(oneToMany);
			}
		}
		/*
		 * Add Many To Many set to Entity Model
		 */
		if (!entityTree.getManyToMany().isEmpty()) {
			firstStep = true;
			for (ManyToManyType manyToMany : entityTree.getManyToMany()) {
				addManyToMany(manyToMany);
			}
		}
		if (!EngineTools.isEmpty(entityTree.getCallforeClassNames())) {
			StringTokenizer tokenizer = new StringTokenizer(entityTree.getCallforeClassNames(), ",");
			int size = tokenizer.countTokens();
			classBodyBuffer.append("callforeClasses = new " + getObjectName(ArrayList.class) + "<Class<? extends "
					+ getObjectName(AEntityCallfore.class) + "<" + uiBeanClass.getSimpleName() + ">>>(" + size + ");");
			while (tokenizer.hasMoreElements()) {
				classBodyBuffer.append(
						"callforeClasses.add((Class<" + getObjectName(AEntityCallfore.class) + "<" + uiBeanClass.getSimpleName()
								+ ">>) pluginEntity.getPluginEntityTree().getPluginResources().getGencodePath().getClazz(\""
								+ tokenizer.nextToken().trim() + "\"));");
			}
		}
		if (scenarioInput.getPluginEntityWrapper().isOneToOne())
			classBodyBuffer.append("oneToOne = true;");
		postCreate();
		classBodyBuffer.appendMinus("}");

		if (null != getIdMethod) {
			addGetIdMethod();
		}
		/*
		 * Implements method getBeanClass
		 */
		classBodyBuffer.append("");
		classBodyBuffer.append("/* (non-Javadoc)");
		classBodyBuffer.append(" * @see org.adichatz.engine.model.AEntityMetaModel#getBeanClass()");
		classBodyBuffer.append(" */");
		classBodyBuffer.append("@Override");
		classBodyBuffer.appendPlus("public Class<" + beanClass.getSimpleName() + "> getBeanClass() {");
		classBodyBuffer.append("return " + getObjectName(beanClass) + ".class;");
		classBodyBuffer.appendMinus("}");
		String interfaceClassName = pluginEntityWrapper.getUiBeanClassName();
		if (!EngineTools.isEmpty(interfaceClassName)) {
			/*
			 * Implements method getUIBeanClass
			 */
			imports.add(interfaceClassName);
			String simpleName = interfaceClassName.substring(interfaceClassName.lastIndexOf('.') + 1);
			classBodyBuffer.append("");
			classBodyBuffer.append("/* (non-Javadoc)");
			classBodyBuffer.append(" * @see org.adichatz.engine.model.AEntityMetaModel#getUIBeanClass()");
			classBodyBuffer.append(" */");
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public Class<" + simpleName + "> getUIBeanClass() {");
			classBodyBuffer.append("return " + simpleName + ".class;");
			classBodyBuffer.appendMinus("}");

		}
	}

	protected void postCreate() throws IOException {
	}

	protected void addGetIdMethod() throws IOException {
		/*
		 * Implements method getId
		 */
		classBodyBuffer.append("");
		classBodyBuffer.append("/* (non-Javadoc)");
		classBodyBuffer.append(" * @see org.adichatz.engine.model.AEntityMetaModel#setId(java.lang.Object, java.lang.Object)");
		classBodyBuffer.append(" */");
		classBodyBuffer.append("@Override");
		Class<?> idClass;
		try {
			if (beanClass.equals(uiBeanClass))
				idClass = getIdMethod.getReturnType();
			else {
				idClass = uiBeanClass.getMethod(getIdMethod.getName(), new Class[] {}).getReturnType();
			}
			classBodyBuffer.appendPlus("public " + getCastType(idClass) + " getId(Object bean){");
			classBodyBuffer.append("return ((" + getObjectName(uiBeanClass) + ") bean)." + getIdMethod.getName() + "();");
			classBodyBuffer.appendMinus("}");
		} catch (NoSuchMethodException | SecurityException e) {
			logError(e);
		}

		/*
		 * Implements method setId
		 */
		classBodyBuffer.append("");
		classBodyBuffer.append("/* (non-Javadoc)");
		classBodyBuffer.append(" * @see org.adichatz.engine.model.AEntityMetaModel#setId(java.lang.Object, java.lang.Object)");
		classBodyBuffer.append(" */");
		classBodyBuffer.append("@Override");
		classBodyBuffer.appendPlus("public void setId(Object bean, Object beanId){");
		if (null != setIdMethod)
			classBodyBuffer.append("((" + getObjectName(uiBeanClass) + ") bean)." + setIdMethod.getName() + "(("
					+ getCastType(getIdMethod.getReturnType()) + ") beanId);");
		classBodyBuffer.appendMinus("}");

		/*
		 * Implements method getIdString
		 */
		if (null != getIdString) {
			classBodyBuffer.append("");
			classBodyBuffer.append("/* (non-Javadoc)");
			classBodyBuffer.append(" * @see org.adichatz.engine.model.AEntityMetaModel#getIdString()");
			classBodyBuffer.append(" */");
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public String getIdString(Object bean) {");
			classBodyBuffer.append("return " + getIdString + ";");
			classBodyBuffer.appendMinus("}");
		}
	}

	protected void addEntitySet(EntitySetType entitySet) throws IOException {
		// Avoid to precise generic object in entity set because EntityMM could not be created
		classBodyBuffer.append("");
		classBodyBuffer.append(
				"// add entity set for field '" + entitySet.getId() + "' (parent clause='" + entitySet.getParentClause() + "')");
		String entitySetHeader = "";
		if (null != entitySet.getEntityElements()) {
			if (firstStep) {
				firstStep = false;
				classBodyBuffer.append(getObjectName(entitySetClass) + " entitySet;");
			}
			entitySetHeader = "entitySet = ";
		}

		classBodyBuffer.append(entitySetHeader + "new " + getObjectName(entitySetClass) + "(this, \"" + entitySet.getId() + "\", "
				+ CodeGenerationUtil.betweenQuotes(entitySet.getMappedBy()) + ");");
		if (null != entitySet.getEntityElements())
			for (EntityElementType entityElement : entitySet.getEntityElements().getEntityElement()) {
				classBodyBuffer.append("entitySet.addElementEntity(\"" + entityElement.getEntityURI() + "\");");
			}
	}

	protected void addManyToMany(ManyToManyType manyToMany) throws IOException {
		// Avoid to precise generic object in entity set because EntityMM could not be created
		classBodyBuffer.append("");
		classBodyBuffer.append("// add many to many for field '" + manyToMany.getId() + "' (parent clause='"
				+ manyToMany.getParentClause() + "')");
		String entitySetHeader = "";
		if (null != manyToMany.getEntityElements() || null != manyToMany.getQueryReference() || null != manyToMany.getParams()) {
			if (firstStep) {
				firstStep = false;
				classBodyBuffer.append(getObjectName(ManyToManyEntitySet.class) + " manyToMany;");
			}
			entitySetHeader = "manyToMany = ";
		}

		classBodyBuffer.append(entitySetHeader + "new " + getObjectName(ManyToManyEntitySet.class) + "(this, \""
				+ manyToMany.getId() + "\", " + CodeGenerationUtil.betweenQuotes(manyToMany.getMappedBy()) + ", "
				+ CodeGenerationUtil.betweenQuotes(manyToMany.getParentClause()) + ");");

		CodeGenerationUtil.addParams(classBodyBuffer, "manyToMany.getParamMap().", manyToMany.getParams(), false);

		if (null != manyToMany.getEntityElements())
			for (EntityElementType entityElement : manyToMany.getEntityElements().getEntityElement()) {
				classBodyBuffer.append("manyToMany.addElementEntity(\"" + entityElement.getEntityURI() + "\");");
			}
		if (null != manyToMany.getQueryReference()) {
			classBodyBuffer.append("manyToMany.setAdiQueryURI("
					+ CodeGenerationUtil.betweenQuotes(manyToMany.getQueryReference().getAdiQueryURI()) + ");");
			if (null != manyToMany.getQueryReference().getPreferenceURI())
				classBodyBuffer.append("manyToMany.setPreferenceURI("
						+ CodeGenerationUtil.betweenQuotes(manyToMany.getQueryReference().getPreferenceURI()) + ");");
		}
	}

	protected void addOneToMany(OneToManyType oneToMany) throws IOException {
		// Avoid to precise generic object in entity set because EntityMM could not be created
		classBodyBuffer.append("");
		classBodyBuffer.append(
				"// add one to many for field '" + oneToMany.getId() + "' (parent clause='" + oneToMany.getParentClause() + "')");
		String entitySetHeader = "";
		if (null != oneToMany.getEntityElements() || null != oneToMany.getQueryReference()) {
			if (firstStep) {
				firstStep = false;
				classBodyBuffer.append(getObjectName(entitySetClass) + " oneToMany;");
			}
			entitySetHeader = "oneToMany = ";
		}

		classBodyBuffer.append(entitySetHeader + "new " + getObjectName(entitySetClass) + "(this, \"" + oneToMany.getId() + "\", "
				+ CodeGenerationUtil.betweenQuotes(oneToMany.getMappedBy()) + ");");
		if (null != oneToMany.getEntityElements())
			for (EntityElementType entityElement : oneToMany.getEntityElements().getEntityElement()) {
				classBodyBuffer.append("oneToMany.addElementEntity(\"" + entityElement.getEntityURI() + "\");");
			}
		if (!EngineTools.isEmpty(oneToMany.getParentClause()))
			classBodyBuffer
					.append("oneToMany.setParentClause(" + CodeGenerationUtil.betweenQuotes(oneToMany.getParentClause()) + ");");
		if (null != oneToMany.getQueryReference()) {
			classBodyBuffer.append("oneToMany.setAdiQueryURI("
					+ CodeGenerationUtil.betweenQuotes(oneToMany.getQueryReference().getAdiQueryURI()) + ");");
			if (null != oneToMany.getQueryReference().getPreferenceURI())
				classBodyBuffer.append("oneToMany.setPreferenceURI("
						+ CodeGenerationUtil.betweenQuotes(oneToMany.getQueryReference().getPreferenceURI()) + ");");
		}
	}

	protected void addOneToOne(OneToOneType oneToOneField) throws IOException {
		PluginEntityWrapper otoPluginEntity = scenarioInput.getPluginEntityWrapper(oneToOneField.getEntityURI());

		classBodyBuffer.append("");
		classBodyBuffer.append("// add One to OneToOne relationship for field '" + oneToOneField.getId() + "' (entityId="
				+ otoPluginEntity.getEntityId() + ")");
		String oneToOneType = "null";
		if (null != oneToOneField.getOneToOneType())
			switch (oneToOneField.getOneToOneType()) {
			case MASTER:
				oneToOneType = getObjectName(OneToOneField.class) + ".TYPE.MASTER";
				break;
			case SLAVE:
				oneToOneType = getObjectName(OneToOneField.class) + ".TYPE.SLAVE";
				break;
			}
		classBodyBuffer.append(
				"new " + getObjectName(OneToOneField.class) + "(this, \"" + oneToOneField.getId() + "\", " + oneToOneType + ");");
	}

	/**
	 * Adds the field.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param field
	 *            the field
	 * 
	 * @return the string
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected String addField(PropertyFieldType field) throws IOException {
		if (!(field instanceof EmbeddedFieldType)) {
			Method getGetMethod = FieldTools.getGetMethod(beanClass, field.getId(), true);
			if (field.getId().equals(entityTree.getIdFieldName())) {
				getIdMethod = getGetMethod;
				setIdMethod = FieldTools.getSetMethod(uiBeanClass, field.getId(), getGetMethod.getReturnType(), false);
				if ((field instanceof EmbeddedIdFieldType)) {
					boolean first = true;
					StringBuffer sb = new StringBuffer();
					for (Field idField : getIdMethod.getReturnType().getDeclaredFields()) {
						if (!Modifier.isStatic(idField.getModifiers())) {
							if (first)
								first = false;
							else
								sb.append("+ \" - \" + ");
							sb.append("((" + getObjectName(uiBeanClass) + ") bean).");
							sb.append(getIdMethod.getName()).append("().");
							sb.append(FieldTools.getGetMethod(getIdMethod.getReturnType(), idField.getName(), true).getName())
									.append("()");
						}
						getIdString = sb.toString();
					}
				}
			}
		}

		String columnName;
		if (field instanceof RefFieldType) {
			RefFieldType refField = (RefFieldType) field;
			addComment(" add a join column (ManyToOne) for field " + field.getId());
			String refFieldHeader = "";
			if (firstStep) {
				firstStep = false;
				classBodyBuffer.append(getObjectName(RefField.class) + "<?> refField;");
			}
			refFieldHeader = "refField = ";
			classBodyBuffer.append(refFieldHeader + "new " + getObjectName(RefField.class) + "(this, \"" + field.getId()//
					+ "\", " + keyWordGenerator.evalExpr2Str(classBodyBuffer, refField.getParentMappedBy(), false) + ", "
					+ keyWordGenerator.evalExpr2Str(classBodyBuffer, refField.getEntityURI(), false) + ");");
			columnName = field.getId().replace("#", "$$") + "COL";
		} else {
			if (field instanceof EmbeddedIdFieldType) {
				columnName = field.getId().replace("#", "$$") + "COL";

				EmbeddedFieldType lastEmbeddedField = null;
				for (EmbeddedFieldType embeddedField : ((EmbeddedIdFieldType) field).getEmbeddedField()) {
					lastEmbeddedField = embeddedField;
				}
				if (null != lastEmbeddedField && lastEmbeddedField.getControlField() instanceof NumericTextType) {
					classBodyBuffer.append("// Adds composite key strategie for the entity model");
					classBodyBuffer.append("cksFactory = new " + getObjectName(CompositeKeyStrategyFactory.class) + "();");
				}
				addStandardPropertyField(field);
			} else {
				columnName = addStandardPropertyField(field);
			}
		}

		return columnName;
	}

	private String addStandardPropertyField(PropertyFieldType field) throws IOException {
		String columnName;
		addComment(" add a property field for field " + field.getId());
		classBodyBuffer.append("new " + getObjectName(PropertyField.class) + "(this, \"" + field.getId() + "\");");
		columnName = field.getId().replace("#", "$$") + "COL";
		return columnName;
	}

	/**
	 * Adds the comment.
	 * 
	 * @param comment
	 *            the comment
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void addComment(String comment) throws IOException {
		classBodyBuffer.append("");
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * " + comment);
		classBodyBuffer.append(" */");
	}

	/**
	 * Gets the cast type.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return the cast type
	 */
	protected String getCastType(Class<?> type) {
		if (type.equals(byte.class) || type.equals(Byte.class))
			return "Byte";
		else if (type.equals(short.class) || type.equals(Short.class))
			return "Short";
		else if (type.equals(int.class) || type.equals(Integer.class))
			return "Integer";
		else if (type.equals(long.class) || type.equals(Long.class))
			return "Long";
		else if (type.equals(float.class) || type.equals(Float.class))
			return "Float";
		else if (type.equals(double.class) || type.equals(Double.class))
			return "Double";
		else if (type.equals(boolean.class) || type.equals(Boolean.class))
			return "Boolean";
		else if (type.equals(char.class) || type.equals(Character.class))
			return "Character";
		else if (type.equals(String.class))
			return "String";
		else if (type.equals(Object.class))
			return "Object";
		else
			return getObjectName(type);
	}
}