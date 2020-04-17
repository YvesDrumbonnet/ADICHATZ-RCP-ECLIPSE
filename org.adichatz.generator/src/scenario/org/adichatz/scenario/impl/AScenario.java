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

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import javax.persistence.Id;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.ActionWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IParamsContainer;
import org.adichatz.generator.xjc.ConfigType;
import org.adichatz.generator.xjc.ControlType;
import org.adichatz.generator.xjc.CustomizationsType;
import org.adichatz.generator.xjc.FormatEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.LayoutType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.ResourceBundleType;
import org.adichatz.generator.xjc.ResourceBundlesType;
import org.adichatz.generator.xjc.SeparatorType;
import org.adichatz.generator.xjc.ValidElementType;
import org.adichatz.generator.xjc.WidgetType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioMap;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.internal.InternalPolicy;

// TODO: Auto-generated Javadoc
/**
 * The Class AScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AScenario {

	/** The end line characters. */
	// public static String END_LINE = "\r\n";
	public static String END_LINE = "\n"; // AVISATZ must be tested on other OS.

	/** The QUER y_ form. */
	public static String QUERY_FORM = "QueryForm";

	/** The TABL e_ sectio n_ include. */
	public static String TABLE_SECTION_INCLUDE = "TableSectionInclude";

	/** The DETAI l_ sectio n_ include. */
	public static String CONTAINER_SECTION_INCLUDE = "ContainerSectionInclude";

	public static int RETRIEVE_ROLE = 1 << 1;

	public static int MERGE_ROLE = 1 << 2;

	public static int PERSIST_ROLE = 1 << 3;

	public static int REMOVE_ROLE = 1 << 4;

	/**
	 * The Enum CUSTOMIZATION_TYPE.
	 * 
	 * @author Yves Drumbonnet
	 */
	public static enum CUSTOMIZATION_TYPE {

		/** The invalidate. */
		invalidate,
		/** The disable. */
		disable
	}

	/** The param map. */
	protected ScenarioMap scenarioMap = new ScenarioMap();

	/** The scenario input. */
	protected ScenarioInput scenarioInput;

	/** The generation unit. */
	protected GenerationUnitType generationUnit;

	/**
	 * Instantiates a new a scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	protected AScenario(GenerationUnitType generationUnit) {
		this.generationUnit = generationUnit;
	}

	/**
	 * Gets the param map.
	 * 
	 * @return the paramMap
	 */
	public ScenarioMap getParamMap() {
		return scenarioMap;
	}

	/**
	 * Adds the load bundle.
	 * 
	 * @param config
	 *            the config
	 * @param var
	 *            the var
	 * @param basename
	 *            the basename
	 */
	public void addLoadBundle(ConfigType config, String var, String basename) {
		boolean createBundle = true;
		if (null != config.getResourceBundles()) {
			for (ResourceBundleType bundle : config.getResourceBundles().getResourceBundle())
				if (bundle.getVar().equals(var)) {
					createBundle = false;
					break;
				}
		}
		if (createBundle) {
			ResourceBundleType loadBundle = new ResourceBundleType();
			loadBundle.setVar(var);
			loadBundle.setBasename(basename);
			ResourceBundlesType resourceBundles = config.getResourceBundles();
			if (null == resourceBundles) {
				resourceBundles = new ResourceBundlesType();
				config.setResourceBundles(resourceBundles);
			}
			resourceBundles.getResourceBundle().add(loadBundle);
		}
	}

	/**
	 * Adds the customization.
	 * 
	 * @param config
	 *            the config
	 * @param controllerId
	 *            the controller id
	 * @param element
	 *            the element
	 * @param typeCustomization
	 *            the type customization
	 */
	protected void addCustomization(ConfigType config, String controllerId, ValidElementType element,
			CUSTOMIZATION_TYPE typeCustomization) {
		addCustomElement(config, controllerId, element);
		switch (typeCustomization) {
		case invalidate:
			element.setValid("false");
			break;
		case disable:
			((ControlType) element).setEnabled("false");
			break;
		}
	}

	/**
	 * Adds the custom element.
	 *
	 * @param config
	 *            the config
	 * @param controllerId
	 *            the controller id
	 * @param element
	 *            the element
	 * @return the valid element type
	 */
	protected ValidElementType addCustomElement(ConfigType config, String controllerId, ValidElementType element) {
		element.setId(controllerId);
		if (null == config.getCustomizations())
			config.setCustomizations(new CustomizationsType());
		config.getCustomizations().getScrolledFormOrClientCanvasOrSection().add(element);
		return element;
	}

	/**
	 * Adds the layout (according to MigLayout principles).
	 * 
	 * @param layoutConstraints
	 *            the layout constraints
	 * @param columnConstraints
	 *            the column constraints
	 * @param rowConstraints
	 *            the row constraints
	 * 
	 * @return the layout type populated with provided parameters
	 */
	protected LayoutType addLayout(String layoutConstraints, String columnConstraints, String rowConstraints) {
		LayoutType layoutType = new LayoutType();
		layoutType.setLayoutConstraints(layoutConstraints);
		layoutType.setColumnConstraints(columnConstraints);
		layoutType.setRowConstraints(rowConstraints);
		return layoutType;
	}

	protected ParamType addParam(IParamsContainer container, String id, String value, boolean optional) {
		ParamType param = addParam(container, id, value);
		param.setOptional(optional);
		return param;
	}

	/**
	 * Adds the param.
	 * 
	 * @param container
	 *            the container
	 * @param id
	 *            the id
	 * @param value
	 *            the value
	 * 
	 * @return the param type
	 */
	protected ParamType addParam(IParamsContainer container, String id, String value) {
		ParamType param = addParam(id, value);
		ParamsType params = container.getParams();
		if (null == params) {
			params = new ParamsType();
			container.setParams(params);
		}
		container.getParams().getParam().add(param);
		return param;
	}

	/**
	 * Removes the param.
	 * 
	 * @param container
	 *            the container
	 * @param id
	 *            the id
	 */
	protected void removeParam(IParamsContainer container, String id) {
		if (null != container.getParams())
			for (ParamType param : container.getParams().getParam())
				if (param.getId().equals(id)) {
					container.getParams().getParam().remove(param);
					break;
				}
	}

	/**
	 * Adds the param.
	 * 
	 * @param id
	 *            the id
	 * @param value
	 *            the value
	 * 
	 * @return the param type
	 */
	protected ParamType addParam(String id, String value) {
		ParamType param = new ParamType();
		param.setValue(value);
		param.setId(id);

		return param;
	}

	/**
	 * Adds the include wrapper.
	 * 
	 * @param parentWrapper
	 *            the parent wrapper
	 * @param id
	 *            the id
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * 
	 * @return the include wrapper
	 */
	@SuppressWarnings("rawtypes")
	public IncludeWrapper addInclude(ICollectionWrapper parentWrapper, String id, String treeId, String subPackage) {
		return addInclude(parentWrapper, id, EngineTools.getAdiResourceURI(subPackage, treeId));
	}

	/**
	 * Adds the include.
	 *
	 * @param parentWrapper
	 *            the parent wrapper
	 * @param id
	 *            the id
	 * @param adiResourceURI
	 *            the adi resource uri
	 * @return the include wrapper
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IncludeWrapper addInclude(ICollectionWrapper parentWrapper, String id, String adiResourceURI) {
		IncludeWrapper include = new IncludeWrapper();
		include.setId(id);
		include.setAdiResourceURI(adiResourceURI);

		parentWrapper.getElements().add(include);
		return include;
	}

	/**
	 * Adds the action.
	 * 
	 * @param actionCollection
	 *            the action collection
	 * @param id
	 *            the id
	 * @param actionClass
	 *            the action class
	 * @param toolTipText
	 *            the tool tip text
	 * @param enabled
	 *            the enabled
	 * 
	 * @return the action wrapper
	 */
	protected ActionWrapper addAction(ICollectionWrapper<WidgetType> actionCollection, String id, Class<?> actionClass,
			String toolTipText, String enabled) {
		ActionWrapper action = addAction(id, actionClass, toolTipText, enabled);
		actionCollection.getElements().add(action);
		return action;
	}

	/**
	 * Adds the action.
	 * 
	 * @param id
	 *            the id
	 * @param actionClass
	 *            the action class
	 * @param toolTipText
	 *            the tool tip text
	 * @param enabled
	 *            the enabled
	 * 
	 * @return the action wrapper
	 */
	protected ActionWrapper addAction(String id, Class<?> actionClass, String toolTipText, String enabled) {
		ActionWrapper action = addAction(id, actionClass, toolTipText);
		action.setEnabled(enabled);

		return action;
	}

	/**
	 * Adds the action.
	 * 
	 * @param id
	 *            the id
	 * @param actionClass
	 *            the action class
	 * @param toolTipText
	 *            the tool tip text
	 * 
	 * @return the action wrapper
	 */
	protected ActionWrapper addAction(String id, Class<?> actionClass, String toolTipText) {
		ActionWrapper action = new ActionWrapper();
		action.setId(id + "Action");
		if (!EngineTools.isEmpty(toolTipText))
			action.setToolTipText(getEngineBundle() + toolTipText + ")");
		action.setActionClassName(actionClass.getCanonicalName());
		return action;
	}

	/**
	 * Gets the engine bundle.
	 * 
	 * @return the engine bundle
	 */
	public static String getEngineBundle() {
		return "#MSG(adi://org.adichatz.engine//adichatzEngine, ";
	}

	/**
	 * Adds the separator.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the separator type
	 */
	protected SeparatorType addSeparator(String id) {
		SeparatorType separator = new SeparatorType();
		separator.setId(id);
		return separator;
	}

	/**
	 * Gets the field name.
	 * 
	 * @param method
	 *            the method
	 * 
	 * @return the field name
	 */
	protected String getFieldName(Method method) {
		String fieldName;
		if (method.getName().startsWith("is"))
			fieldName = method.getName().substring(2);
		else if (method.getName().startsWith("get"))
			fieldName = method.getName().substring(3);
		else {
			logError("method '" + method.getName() + " for class " + method.getDeclaringClass().getName()
					+ "' is not a correct method!");
			return null;
		}
		try {
			method.getDeclaringClass().getDeclaredField(fieldName);
		} catch (SecurityException e) {
			logError(e);
			return null;
		} catch (NoSuchFieldException e) {
			try {
				fieldName = EngineTools.lowerCaseFirstLetter(fieldName);
				method.getDeclaringClass().getDeclaredField(fieldName);
			} catch (SecurityException e2) {
				logError(e2);
				return null;
			} catch (NoSuchFieldException e2) {
				logError("No field found for method '" + method.getName() + " for class!");
				return null;
			}
		}
		return fieldName;
	}

	/**
	 * Gets the referenced member for table.
	 * 
	 * @param firstFragment
	 *            the first fragment
	 * @param referencedClass
	 *            the referenced class
	 * @return the referenced member for table
	 */
	protected String getReferencedMemberForTable(String firstFragment, Class<?> referencedClass) {
		Method method;
		Method getIdMethod = null;
		for (Field referingField : referencedClass.getDeclaredFields())
			if (!Modifier.isStatic(referingField.getModifiers())) {
				method = FieldTools.getGetMethod(referencedClass, referingField.getName(), false);
				if (null != method) {
					if (method.getReturnType() == String.class) {
						return firstFragment + "." + referingField.getName();
					}
					if (null != method.getAnnotation(Id.class))
						getIdMethod = method;
				}
			}
		return (null != getIdMethod) ? "String.valueOf(" + firstFragment + "." + getIdMethod.getName() + "())" : ".toString()";
	}

	/**
	 * Creates the xml and prepare.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	@SuppressWarnings("restriction")
	public void createXmlAndPrepare(ScenarioInput scenarioInput) {
		ITreeWrapper treeWrapper = (ITreeWrapper) scenarioInput.getXmlElement();
		treeWrapper.setPluginName(scenarioInput.getScenarioResources().getPluginName());
		treeWrapper.setTreeId(scenarioInput.getTreeId());
		treeWrapper.setSubPackage(scenarioInput.getSubPackage());

		GeneratorUnit generatorUnit = new GeneratorUnit(scenarioInput);
		GencodePath gencodePath = scenarioInput.getScenarioResources().getGencodePath();

		File xmlFile = new File(generatorUnit.getXmlFileDir(gencodePath, scenarioInput.getSubPackage()) + "/"
				+ scenarioInput.getTreeId() + "GENERATED.axml");
		if (scenarioInput.isGenerateXml()) {
			LogBroker.logTrace("Creating XML file for " + xmlFile.getName());
			ScenarioUtil.createXmlFile(xmlFile, scenarioInput.getXmlElement());
			if (InternalPolicy.OSGI_AVAILABLE && scenarioInput.isFireResourceChangeEvent()) {
				String radix = EngineConstants.XML_FILES_PATH.concat("/").concat(scenarioInput.getSubPackage().replace('.', '/'))
						.concat("/").concat(scenarioInput.getTreeId());
				IFile axmlFile = scenarioInput.getScenarioResources().getProject().getFile(radix.concat("GENERATED.axml"));
				if (!axmlFile.exists()) {
					IContainer parent = axmlFile.getParent();
					IProject project = axmlFile.getProject();
					String parentName = parent.getFullPath().makeRelativeTo(project.getFullPath()).toString();
					if (!parent.exists()) {
						scenarioInput.getScenarioResources().createFolderIfNotExist(parentName);
						parent = project.getFolder(parentName);
					}
					if (parent.exists())
						try {
							parent.refreshLocal(IResource.DEPTH_INFINITE, null);
						} catch (CoreException e) {
							logError(e);
						}
				}
				if (axmlFile.exists())
					scenarioInput.getScenarioResources().getAffectedFiles().add(axmlFile);
			}
		}
		((ITreeWrapper) scenarioInput.getXmlElement()).setXmlFile(xmlFile);
		if (scenarioInput.isGenerateJava()) {
			generatorUnit.initialize();
			generatorUnit.generateCodeFromXMLTree(scenarioInput.isCompile());
		}
	}

	/**
	 * Gets the generic.
	 * 
	 * @param method
	 *            the method
	 * @return the generic
	 */
	public Class<?> getGeneric(Method method) {
		String generic = method.toGenericString();
		String referencedClassName = generic.substring(generic.indexOf('<') + 1, generic.indexOf('>'));
		return scenarioInput.getScenarioResources().getGencodePath().getClazz(referencedClassName);
	}

	/**
	 * Adds the params.
	 * 
	 * @param paramsContainer
	 *            the editor
	 * @return the params type
	 */
	protected ParamsType addParams(IParamsContainer paramsContainer) {
		ParamsType params = paramsContainer.getParams();
		if (null == params) {
			params = new ParamsType();
			paramsContainer.setParams(params);
		}
		return params;
	}

	/**
	 * Gets the entity tree.
	 * 
	 * @return the entity tree
	 */
	protected EntityTreeWrapper getEntityTree() {
		String pluginName = scenarioInput.getPluginEntity().getEntityKeys()[0];
		ScenarioResources scenarioResources = ScenarioResources.getInstance(pluginName, null);
		GeneratorUnit generatorUnit = new GeneratorUnit(new ScenarioInput(scenarioResources, scenarioInput.getPluginEntity()));
		return (EntityTreeWrapper) generatorUnit.getTreeWrapperFromXml(true);
	}

	/**
	 * Gets the format.
	 * 
	 * @param type
	 *            the type
	 * @return the format
	 */
	protected FormatEnum getFormat(Class<?> type) {
		if (type == byte.class || type == Byte.class)
			return FormatEnum.BYTE;
		if (type == short.class || type == Short.class)
			return FormatEnum.SHORT;
		if (type == long.class || type == Long.class)
			return FormatEnum.LONG;
		if (type == int.class || type == Integer.class)
			return FormatEnum.INTEGER;
		if (type == float.class || type == Float.class)
			return FormatEnum.FLOAT;
		if (type == double.class || type == Double.class || type == BigDecimal.class)
			return FormatEnum.DOUBLE;
		if (null != type.getSuperclass() && type.getSuperclass() != Object.class)
			return getFormat(type.getSuperclass());
		logError("No formatter found for type " + type + "?!");
		return null;
	}

	/**
	 * Gets the generation unit.
	 * 
	 * @return the generation unit
	 */
	public GenerationUnitType getGenerationUnit() {
		return generationUnit;
	}

	public boolean addAuthorization(PluginEntityType pluginEntity, int roles) {
		if (null != pluginEntity) {
			if (0 != (roles & RETRIEVE_ROLE) && null != pluginEntity.getRetrieveRoles())
				return true;
			if (0 != (roles & MERGE_ROLE) && null != pluginEntity.getMergeRoles())
				return true;
			if (0 != (roles & PERSIST_ROLE) && null != pluginEntity.getPersistRoles())
				return true;
			if (0 != (roles & REMOVE_ROLE) && null != pluginEntity.getRemoveRoles())
				return true;
		}
		return false;
	}
}
