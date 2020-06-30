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
package org.adichatz.generator.tools;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.MandatoryValidator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.ContainerTreeWrapper;
import org.adichatz.generator.wrapper.ControlFieldWrapper;
import org.adichatz.generator.wrapper.ExtendTreeWrapper;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.LabelWrapper;
import org.adichatz.generator.wrapper.PartTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.internal.IAdditionaCodeWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IColumnWrapper;
import org.adichatz.generator.wrapper.internal.IControlWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IEntityContainerWrapper;
import org.adichatz.generator.wrapper.internal.ILazyFetchesContainer;
import org.adichatz.generator.wrapper.internal.IRootWrapper;
import org.adichatz.generator.wrapper.internal.ITableColumnWrapper;
import org.adichatz.generator.xjc.AccessibilitiesType;
import org.adichatz.generator.xjc.AccessibilityType;
import org.adichatz.generator.xjc.AccessibilityTypeEnum;
import org.adichatz.generator.xjc.CTabItemType;
import org.adichatz.generator.xjc.CollectionType;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.ControlType;
import org.adichatz.generator.xjc.CustomizationsType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenersType;
import org.adichatz.generator.xjc.NavigatorTree;
import org.adichatz.generator.xjc.NodeType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.ValidatorType;
import org.adichatz.generator.xjc.ValidatorsType;
import org.adichatz.generator.xjc.WidgetType;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.util.ScenarioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class TransFormTreeTools.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TransformTreeTools {
	/** The skip spaces. */
	private static int[] skipSpaces = new int[6];

	static {
		skipSpaces[0] = 5;
		skipSpaces[1] = 13;
		skipSpaces[2] = 4;
		skipSpaces[3] = 6;
		skipSpaces[4] = 4;
		skipSpaces[5] = 7;
	}

	// map of set of lazyFetch by container
	/** The lazy fetch map. */
	static Map<ILazyFetchesContainer, Set<String>> lazyFetchMap;

	/** The ids. */
	static List<String> ids = new ArrayList<String>();

	/** The added labels. */
	static List<AddedLabel> addedLabels;

	/** The current collection. */
	@SuppressWarnings("rawtypes")
	private static ICollectionWrapper currentCollection;

	/** The current collection. */
	private static ILazyFetchesContainer currentLazyFetchesContainer;

	/** The current plugin entity. */
	private PluginEntity<?> currentPluginEntity;

	/** The current adichatz plugin resources. */
	private AdiPluginResources currentPluginResources;

	/** identifier index for missing identifier. */
	int idIndex;

	/** The part tree. */
	private static PartTreeWrapper partTree;

	// indicates if it is an include from an other treeWrapper
	/** The is include process. */
	boolean isIncludeProcess;

	/** The include trees. */
	public static Map<String, IncludeTreeWrapper> INCLUDE_TREES = new HashMap<String, IncludeTreeWrapper>();

	/** The scenario input. */
	private ScenarioInput scenarioInput;

	/** The entity tree. */
	private EntityTree entityTree;

	/** The root wrapper. */
	private IRootWrapper rootWrapper;

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param partTreeWrapper
	 *            the part tree wrapper
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, PartTreeWrapper partTreeWrapper) {
		partTree = partTreeWrapper;
		initCurrent(scenarioInput, partTreeWrapper, partTree.getEntityURI());
		isIncludeProcess = false;
		ids.clear();
		currentCollection = partTree;

		// initializes lazyFetchMap and addedLabels
		lazyFetchMap = new HashMap<ILazyFetchesContainer, Set<String>>();
		addedLabels = new ArrayList<AddedLabel>();

		// Process tree
		reprocessElement(null, partTree, "");

		// end
		endReprocessing();
		createTrace();
	}

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param includeTree
	 *            the include tree
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, IncludeTreeWrapper includeTree) {
		initCurrent(scenarioInput, includeTree, includeTree.getEntityURI());
		isIncludeProcess = false;
		ids.clear();

		// initializes lazyFetchMap and addedLabels
		lazyFetchMap = new HashMap<ILazyFetchesContainer, Set<String>>();
		addedLabels = new ArrayList<AddedLabel>();

		currentCollection = includeTree;

		reprocessElement(null, includeTree, "");
		endReprocessing();
		createTrace();
	}

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param extendTree
	 *            the extend tree
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, ExtendTreeWrapper extendTree) {
		initCurrent(scenarioInput, extendTree, extendTree.getEntityURI());
		isIncludeProcess = false;
		ids.clear();

		// initializes lazyFetchMap and addedLabels
		lazyFetchMap = new HashMap<ILazyFetchesContainer, Set<String>>();
		addedLabels = new ArrayList<AddedLabel>();

		reprocessElement(null, extendTree, "");
		endReprocessing();
		createTrace();
	}

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param containerTree
	 *            the container tree
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, ContainerTreeWrapper containerTree) {
		initCurrent(scenarioInput, containerTree, containerTree.getEntityURI());

		isIncludeProcess = false;
		ids.clear();

		// initializes lazyFetchMap and addedLabels
		lazyFetchMap = new HashMap<ILazyFetchesContainer, Set<String>>();
		addedLabels = new ArrayList<AddedLabel>();

		currentCollection = containerTree;

		if (null != containerTree.getId())
			containerTree.getElementMap().put(containerTree.getId(), containerTree);

		reprocessElement(null, containerTree, "");
		endReprocessing();
		createTrace();
	}

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param entityTree
	 *            the entity tree
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, EntityTree entityTree) {
		this.scenarioInput = scenarioInput;
		isIncludeProcess = false;
		ids.clear();

		if (null == scenarioInput.getPluginEntity())
			scenarioInput.setPluginEntity(getPluginEntity(null, entityTree.getEntityURI()));

		// initializes lazyFetchMap and addedLabels
		lazyFetchMap = new HashMap<ILazyFetchesContainer, Set<String>>();
		addedLabels = new ArrayList<AddedLabel>();

		ICollectionWrapper<?> oldCollection = currentCollection;
		currentCollection = null;

		for (PropertyFieldType propertyField : entityTree.getPropertyField()) {
			if (null != propertyField.getControlField()) {
				if (EngineTools.isEmpty(propertyField.getControlField().getProperty()))
					propertyField.getControlField().setProperty(propertyField.getId());
				if (null != propertyField.getControlField()) {
					if (null == propertyField.getControlField().getId())
						propertyField.getControlField().setId(propertyField.getId());
					reprocessElement(null, (IElementWrapper) propertyField.getControlField(), "");
				}
				if (null != propertyField.getColumnField())
					reprocessElement(null, (IElementWrapper) propertyField.getColumnField(), "");
			}
		}
		endReprocessing();
		currentCollection = oldCollection;
	}

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param menuTree
	 *            the menu tree
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, NavigatorTree menuTree) {
		this.scenarioInput = scenarioInput;
		isIncludeProcess = false;
		ids.clear();

		ICollectionWrapper<?> oldCollection = currentCollection;
		currentCollection = null;

		for (NodeType node : menuTree.getMenuOrItem()) {
			reprocessElement(null, (IElementWrapper) node, "");
		}
		currentCollection = oldCollection;
	}

	/**
	 * Instantiates a new transform tree tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param parentWrapper
	 *            the parent wrapper
	 * @param includeWrapper
	 *            the include wrapper
	 * @param prefix
	 *            the prefix
	 */
	public TransformTreeTools(ScenarioInput scenarioInput, IElementWrapper parentWrapper, IncludeWrapper includeWrapper,
			String prefix, AdiPluginResources currentPluginResources, PluginEntity<?> currentPluginEntity) {
		this.scenarioInput = scenarioInput;
		this.currentPluginEntity = currentPluginEntity;
		this.currentPluginResources = currentPluginResources;

		String adiResourceURI = includeWrapper.getAdiResourceURI();
		if (null == adiResourceURI) {
			if (!(currentCollection instanceof CustomizationsType))
				logError("Include whith id '" + includeWrapper.getId() + "' has no URI and cannot be processed!");
		} else {
			if (-1 == adiResourceURI.indexOf('#')) {
				// indicates if it is an include from an other treeWrapper
				String[] includeKeys = EngineTools.getInstanceKeys(adiResourceURI);
				isIncludeProcess = true;
				String newPrefix = prefix + includeWrapper.getId() + EngineConstants.INCLUDE_SEPARATOR;

				String oldSubpackage = scenarioInput.getSubPackage();
				scenarioInput.setSubPackage(includeKeys[1]);

				IncludeTreeWrapper includeTree = INCLUDE_TREES.get(adiResourceURI);
				if (null == includeTree) {
					ScenarioInput includeSI = new ScenarioInput(scenarioInput, null, adiResourceURI);
					Object treeWrapper = new GeneratorUnit(includeSI).getTreeWrapperFromXml(true);
					if (treeWrapper instanceof IncludeTreeWrapper) {
						includeTree = (IncludeTreeWrapper) treeWrapper;
					} else if (treeWrapper instanceof ExtendTreeWrapper) {
						ExtendTreeWrapper extendedTree = (ExtendTreeWrapper) treeWrapper;
						includeTree = new MergeExtendedTreeTool(
								new ScenarioInput(scenarioInput, null, extendedTree.getAdiResourceURI()), extendedTree)
										.extractIncludeTree();
					}
					INCLUDE_TREES.put(adiResourceURI, includeTree);
				}
				includeWrapper.setIncludeTree(includeTree);

				scenarioInput.setSubPackage(oldSubpackage);
				if (null == includeTree) {
					logError("No tree has been found for sub-package '" + includeKeys[1] + "' and tree identifier '"
							+ includeKeys[2] + "'!");
				} else {
					this.rootWrapper = includeTree;
					rootWrapper.setPluginEntity(includeTree.getPluginEntity());
					includeWrapper.getElements().addAll(includeTree.getElements());

					ICollectionWrapper<?> oldCollection = currentCollection;
					currentCollection = includeTree;
					reprocessElement(parentWrapper, includeTree, newPrefix);

					endReprocessing();
					currentCollection = oldCollection;
				}
			}
		}
	}

	private void initCurrent(ScenarioInput scenarioInput, IRootWrapper rootWrapper, String entityURI) {
		this.scenarioInput = scenarioInput;
		this.rootWrapper = rootWrapper;
		initCurrent(entityURI);
		rootWrapper.setPluginEntity(currentPluginEntity);
	}

	/**
	 * Inits the.
	 * 
	 * @param pluginKey
	 *            the plugin key
	 * @param entityURI
	 *            the entity id
	 */
	private void initCurrent(String entityURI) {
		currentPluginResources = scenarioInput.getScenarioResources().getPluginResources();
		if (!EngineTools.isEmpty(entityURI))
			currentPluginEntity = currentPluginResources.getPluginEntity(entityURI);
		else
			currentPluginEntity = scenarioInput.getPluginEntity();
	}

	/**
	 * Ends reprocessing.
	 */
	@SuppressWarnings("unchecked")
	private void endReprocessing() {
		for (AddedLabel addedLabel : addedLabels) {
			if (null != addedLabel.collection && addedLabel.rootWrapper.equals(rootWrapper))
				addedLabel.collection.getElements().add(addedLabel.collection.getElements().indexOf(addedLabel.linkedElement),
						addedLabel.labelWrapper);
			addedLabel.linkedElement.setLinkedControl(addedLabel.labelWrapper.getId());
		}
		for (Map.Entry<ILazyFetchesContainer, Set<String>> lazyFetchesEntry : lazyFetchMap.entrySet()) {
			ILazyFetchesContainer lazyFetchesContainer = lazyFetchesEntry.getKey();
			if (null != lazyFetchesContainer) {
				List<String> lazyFetches = new ArrayList<String>();
				if (!EngineTools.isEmpty(lazyFetchesContainer.getLazyFetches())) {
					StringTokenizer tokenizer = new StringTokenizer(lazyFetchesContainer.getLazyFetches(), ",");
					while (tokenizer.hasMoreElements()) {
						lazyFetches.add(tokenizer.nextToken().trim());
					}
				}
				lazyFetches.addAll(lazyFetchesEntry.getValue());
				if (!lazyFetches.isEmpty()) {
					int count = lazyFetches.size();
					int index = 0;
					StringBuffer finalLazyFetchesSB = new StringBuffer();
					for (String lazyFetch : lazyFetches) {
						boolean add = true;
						for (int i = 0; i < count; i++)
							if (i != index && lazyFetches.get(i).startsWith(lazyFetch)) {
								add = false;
								break;
							}
						if (add)
							finalLazyFetchesSB.append(", ").append(lazyFetch);
						index++;
					}
					lazyFetchesContainer.setLazyFetches(finalLazyFetchesSB.substring(2));
				}
			}
		}
	}

	/**
	 * Reprocess list.
	 * 
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 * @param prefix
	 *            the prefix
	 */
	private void reprocessList(IElementWrapper element, List<?> list, String prefix) {
		// use int i =0... to avoid ConcurrentModificationException
		for (int i = 0; i < list.size(); i++) {
			Object attribute = list.get(i);
			if (attribute instanceof IncludeWrapper) {
				IncludeWrapper includeWrapper = (IncludeWrapper) attribute;
				new TransformTreeTools(scenarioInput, element, includeWrapper, prefix, currentPluginResources, currentPluginEntity);
			}
			if (attribute instanceof IElementWrapper) {
				reprocessElement(element, (IElementWrapper) attribute, prefix);
			}
		}
	}

	/**
	 * Reprocess element.
	 * 
	 * @param parentWrapper
	 *            the parent wrapper
	 * @param element
	 *            the element
	 * @param prefix
	 *            the prefix
	 */
	@SuppressWarnings("unchecked")
	private void reprocessElement(IElementWrapper parentWrapper, IElementWrapper element, String prefix) {
		if (null == element.getId())
			element.setId(EngineTools.lowerCaseFirstLetter(element.getClass().getSimpleName()) + "$$" + idIndex++);
		if (null != parentWrapper && null != parentWrapper.getPluginEntity())
			element.setPluginEntity(parentWrapper.getPluginEntity());
		if (null != rootWrapper && !EngineTools.isEmpty(element.getId()))
			rootWrapper.getElementMap().put(element.getId(), element);
		PluginEntity<?> oldCurrentPluginEntity = currentPluginEntity;
		if (!EngineTools.isEmpty(element.getValid()))
			addAccessibility(element, AccessibilityTypeEnum.VALID, element.getValid());
		if (element instanceof CollectionType) {
			if (!EngineTools.isEmpty(((CollectionType) element).getVisible())) {
				if (element instanceof CTabItemType)
					logError(getFromGeneratorBundle("generation.must.not.set.visible", element.getClass().getSimpleName()));
				else
					addAccessibility(element, AccessibilityTypeEnum.VISIBLE, ((CollectionType) element).getVisible());
			}
			if (!EngineTools.isEmpty(((CollectionType) element).getEnabled()))
				addAccessibility(element, AccessibilityTypeEnum.ENABLED, ((CollectionType) element).getEnabled());
		}
		if (element instanceof ControlType) {
			if (!EngineTools.isEmpty(((ControlType) element).getVisible()))
				addAccessibility(element, AccessibilityTypeEnum.VISIBLE, ((ControlType) element).getVisible());
		}
		if (element instanceof WidgetType)
			if (!EngineTools.isEmpty(((WidgetType) element).getEnabled()))
				addAccessibility(element, AccessibilityTypeEnum.ENABLED, ((WidgetType) element).getEnabled());

		AdiPluginResources oldPluginResources = currentPluginResources;

		ICollectionWrapper<?> oldCollection = currentCollection;

		ILazyFetchesContainer oldCurrentLazyFetchesContainer = currentLazyFetchesContainer;

		/*
		 * ControlFieldWrapper and ColumnFieldWrapper must be replaced by Wrapper included in EntityTree for the same column
		 */
		if (element instanceof ControlFieldWrapper && null != currentCollection) {
			// collection is null, e.g. when current TreeWrapper is the EntityTree
			if (null == entityTree || !entityTree.getEntityURI().equals(currentPluginEntity.getEntityURI())) {
				PluginEntityWrapper pluginEntityWrapper = scenarioInput.getPluginEntityWrapper(currentPluginEntity.getEntityURI());
				entityTree = pluginEntityWrapper.getEntityTree();
			}

			for (PropertyFieldType propertyField : entityTree.getPropertyField()) {
				if (propertyField.getId().equals(((IColumnWrapper) element).getProperty())) {
					int index = currentCollection.getElements().indexOf(element);
					currentCollection.getElements().remove(index);
					ElementType fieldElement;
					if (element instanceof ControlFieldWrapper)
						fieldElement = propertyField.getControlField();
					else
						fieldElement = propertyField.getColumnField();
					currentCollection.getElements().add(index, fieldElement);
					fieldElement.setId(propertyField.getId());

					Class<?> elementClass = EngineTools.getChildXjcClass(element);
					while (EngineTools.isXmlType(elementClass)) {
						for (Field field : elementClass.getDeclaredFields())
							if (!Modifier.isStatic(field.getModifiers())) {
								try {
									boolean accessible = field.canAccess(element);
									field.setAccessible(true);
									Object value = field.get(element);
									if (null != value) {
										Method setMethod = FieldTools.getSetMethod(elementClass, field.getName(), field.getType(),
												true);
										ReflectionTools.invokeMethod(setMethod, fieldElement, new Object[] { value });
									}
									field.setAccessible(accessible);
								} catch (IllegalArgumentException | IllegalAccessException e) {
									logError(e);
								}
							}
						elementClass = elementClass.getSuperclass();
					}
					element = (IElementWrapper) fieldElement;
					break;
				}
			}

		}

		if (element instanceof IEntityContainerWrapper) {
			if (null != ((IEntityContainerWrapper) element).getEntityURI()) {
				currentPluginEntity = getPluginEntity(currentPluginResources, ((IEntityContainerWrapper) element).getEntityURI());
				((IElementWrapper) element).setPluginEntity(currentPluginEntity);
			}
		}
		if (element instanceof ICollectionWrapper)
			currentCollection = (ICollectionWrapper<?>) element;
		if (element instanceof ILazyFetchesContainer)
			currentLazyFetchesContainer = (ILazyFetchesContainer) element;
		element.setPluginEntity(currentPluginEntity);

		// adds include prefix to Identifier if needed.
		if (prefix.length() > 0)
			element.setId(prefix + element.getId());
		if (element instanceof ITableColumnWrapper && null == ((ITableColumnWrapper) element).getText()
				&& null != ((ITableColumnWrapper) element).getProperty() && null != currentPluginEntity) {
			((ITableColumnWrapper) element).setText("#MSG(" + EngineTools.lowerCaseFirstLetter(currentPluginEntity.getEntityId())
					+ ", " + ((ITableColumnWrapper) element).getProperty() + ")");
		}
		Class<?> elementClass = EngineTools.getChildXjcClass(element);
		while (EngineTools.isXmlType(elementClass)) {
			reprocessField(elementClass, element, prefix);
			elementClass = elementClass.getSuperclass();
		}
		/*
		 * element is a control field whith default label and a column property outside a customization process
		 */
		if (element instanceof ControlFieldType
				&& (null == ((ControlFieldType) element).isNoLabel() || !((ControlFieldType) element).isNoLabel())
				&& !(currentCollection instanceof CustomizationsType)) {
			ControlFieldType linkedField = (ControlFieldType) element;
			String labelText = null;
			if (null != linkedField.getLabelText())
				labelText = linkedField.getLabelText();
			else if (!EngineTools.isEmpty(linkedField.getProperty())) {
				if (null != currentPluginEntity) {
					labelText = "#MSG(" + EngineTools.lowerCaseFirstLetter(currentPluginEntity.getEntityId()) + ", "
							+ linkedField.getProperty() + ").concat(\"" + EngineConstants.INCLUDE_SEPARATOR + "\")";
				} else
					labelText = linkedField.getProperty();
			}
			if (null != labelText) {
				LabelWrapper labelWrapper = new LabelWrapper();
				labelWrapper.setLayoutData(linkedField.getLabelLayoutData());
				labelWrapper.setBackground(linkedField.getLabelBackground());
				labelWrapper.setForeground(linkedField.getLabelForeground());
				labelWrapper.setText(labelText);
				labelWrapper.setId(linkedField.getId() + "$1");
				if (null != linkedField.getParentIndex()) {
					labelWrapper.setParentIndex(linkedField.getParentIndex());
					linkedField.setParentIndex(linkedField.getParentIndex() + 1);
				}
				addedLabels.add(new AddedLabel(rootWrapper, currentCollection, labelWrapper, linkedField));
			}
		}
		currentCollection = oldCollection;
		currentLazyFetchesContainer = oldCurrentLazyFetchesContainer;
		currentPluginEntity = oldCurrentPluginEntity;
		currentPluginResources = oldPluginResources;
	}

	/**
	 * Adds the filter.
	 * 
	 * @param element
	 *            the element
	 * @param filterType
	 *            the filter type
	 * @param value
	 *            the value
	 */
	private void addAccessibility(IElementWrapper element, AccessibilityTypeEnum filterType, String value) {
		AccessibilityType filter = new AccessibilityType();
		filter.setType(filterType);
		filter.setId(element.getId().concat("$$" + filterType.value() + "Filter"));
		filter.setAccept("return " + value + ";");
		AccessibilitiesType accessibilities = element.getAccessibilities();
		if (null == accessibilities) {
			accessibilities = new AccessibilitiesType();
			element.setAccessibilities(accessibilities);
		}
		accessibilities.getAccessibility().add(filter);
	}

	/**
	 * Reprocess field.
	 * 
	 * @param elementClass
	 *            the element class
	 * @param element
	 *            the element
	 * @param prefix
	 *            the prefix
	 */
	private void reprocessField(Class<?> elementClass, IElementWrapper element, String prefix) {
		for (Field field : elementClass.getDeclaredFields())
			if (!Modifier.isStatic(field.getModifiers())) {
				boolean accessible = field.canAccess(element);
				field.setAccessible(true);

				Object fieldValue;
				try {
					fieldValue = field.get(element);
					if (fieldValue instanceof IElementWrapper) {
						reprocessElement(element, (IElementWrapper) fieldValue, prefix);
					} else if (fieldValue instanceof List) {
						reprocessList(element, (List<?>) fieldValue, prefix);
					} else if (fieldValue instanceof ListenersType) {
						ListenersType listenersType = (ListenersType) fieldValue;
						int listenerNb = 0;
						for (ListenerType listener : listenersType.getListener()
								.toArray(new ListenerType[listenersType.getListener().size()])) {
							if (!EngineTools.isEmpty(listener.getListenerTypes())) {
								String listenerType = listener.getListenerTypes();
								StringTokenizer tokenizer = new StringTokenizer(listenerType, "|");
								boolean first = true;
								Boolean reprocessListener = false;
								while (tokenizer.hasMoreElements()) {
									String token = tokenizer.nextToken().trim();
									if (first) {
										listener.setListenerTypes(token);
										first = false;
									} else {
										if (element instanceof IAdditionaCodeWrapper && null != reprocessListener
												&& !reprocessListener && null != listener.getCode()) {
											BufferedReader reader = new BufferedReader(new StringReader(listener.getCode()));
											String currentLine = null;
											int countLines = 0;
											try {
												reprocessListener = false;
												while ((currentLine = reader.readLine()) != null) {
													currentLine = currentLine.trim();
													if (!currentLine.startsWith("import ") && !currentLine.startsWith("//"))
														if (0 != countLines) {
															reprocessListener = true;
															break;
														} else
															countLines++;
												}
											} catch (IOException e) {
												logError(e);
											}
										}
										if (null != reprocessListener && reprocessListener) {
											String listenerName = CodeGenerationUtil.getNormalizedId(element.getId())
													+ "HandleListener" + ++listenerNb;
											StringBuffer additionalCodeSB = new StringBuffer("private void ");
											additionalCodeSB.append(listenerName).append("() {\n");
											additionalCodeSB.append(listener.getCode()).append("\n}");
											listener.setCode(listenerName + "();");
											((IAdditionaCodeWrapper) element).setAdditionalCode(additionalCodeSB.toString());
										}
										ListenerType newListener = new ListenerType();
										listenersType.getListener().add(newListener);
										newListener.setListenerTypes(token);
										newListener.setCode(listener.getCode());
										newListener.setId(listener.getId());
									}
									if (reprocessListener) {

									}
								}
							}

						}
					}

					if (null != fieldValue) {
						if (!isIncludeProcess && element instanceof ControlFieldType && field.getType().equals(String.class)
								&& (-1 != ((String) fieldValue).indexOf("#FV")
										|| -1 != ((String) fieldValue).indexOf("#FIELDVALUE"))) {
							ControlFieldType controlField = (ControlFieldType) element;
							ILazyFetchesContainer oldCurrentLazyFetchesContainer = currentLazyFetchesContainer;
							currentLazyFetchesContainer = (ILazyFetchesContainer) controlField;
							fieldValue = evalElement((IColumnWrapper) element, (String) fieldValue, false);
							currentLazyFetchesContainer = oldCurrentLazyFetchesContainer;
							field.set(element, fieldValue);
						} else if (!isIncludeProcess && element instanceof ITableColumnWrapper
								&& field.getType().equals(String.class)
								&& (-1 != ((String) fieldValue).indexOf("#R()") || -1 != ((String) fieldValue).indexOf("#ROW()"))) {
							fieldValue = evalElement((ITableColumnWrapper) element, (String) fieldValue, true);
							field.set(element, fieldValue);
						} else if (!isIncludeProcess && field.getType().equals(String.class)
								&& (-1 != ((String) fieldValue).indexOf("#B()")
										|| -1 != ((String) fieldValue).indexOf("#BEAN()"))) {
							fieldValue = evalBeanElement(element, (String) fieldValue, true);
							field.set(element, fieldValue);
						}
						if (field.getName().equals("mandatory") && element instanceof IControlWrapper && fieldValue.equals(true)) {
							ValidatorType validatorType = new ValidatorType();
							validatorType.setKey("mandatoryField");
							validatorType.setValidatorClassName(MandatoryValidator.class.getName());
							ValidatorsType validators = ((IControlWrapper) element).getValidators();
							if (null == validators) {
								validators = new ValidatorsType();
								((IControlWrapper) element).setValidators(validators);
							}

							((IControlWrapper) element).getValidators().getValidator().add(validatorType);
							LogBroker.logTrace(getFromGeneratorBundle("mandatoryFieldValidatorAutomaticallyCreated",
									((IControlWrapper) element).getId()));
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logError(e);
				}
				field.setAccessible(accessible);
			} // END for (Field field : collectionWrapper.getClass().getDeclaredFields())
	}

	/**
	 * Evaluate the field value containing #FV, #FIELDVALUE, #R or #ROW key word.
	 * 
	 * @param element
	 *            the element
	 * @param str
	 *            the original string value
	 * @param skipFirst
	 *            the skip first
	 * @return the string
	 */
	private String evalElement(IColumnWrapper element, String str, boolean skipFirst) {
		String column = element.getProperty();
		StringBuffer expressionSB = new StringBuffer(str);
		int[] nextFV = new int[4];
		PluginEntity<?> pluginEntity = element.getPluginEntity();
		if (null == element.getPluginEntity() && scenarioInput.getXmlElement() instanceof EntityTree)
			pluginEntity = scenarioInput.getPluginEntity(); // pluginEntity is null when processing Entity Meta Model xml File.
		try {
			PluginEntityWrapper pew = scenarioInput.getPluginEntityWrapper(pluginEntity.getEntityURI());
			Class<?> beanClass = pew.getBeanClass();
			if (!skipFirst && (null == column || null == getExtendedField(beanClass, column)))
				return str;
			else {
				int start = 0;
				while (true) {
					int min = expressionSB.length();
					int variable = -1;
					nextFV[0] = expressionSB.indexOf("#FV()", start);
					nextFV[1] = expressionSB.indexOf("#FIELDVALUE()", start);
					nextFV[2] = expressionSB.indexOf("#R()", start);
					nextFV[3] = expressionSB.indexOf("#ROW()", start);
					for (int i = 0; i < 4; i++)
						if (-1 != nextFV[i] && nextFV[i] < min) {
							variable = i;
							min = nextFV[i];
						}
					if (-1 == variable)
						break;
					start = nextFV[variable] + skipSpaces[variable];
					extractLazyField(element, pluginEntity, expressionSB, column, start, skipFirst);

				}
			}
		} catch (Exception e) {
			logError(e);
			return str;
		}
		return expressionSB.toString();
	}

	private String evalBeanElement(IElementWrapper element, String str, boolean skipFirst) {
		StringBuffer expressionSB = new StringBuffer(str);
		int[] nextFV = new int[2];
		PluginEntity<?> pluginEntity = element.getPluginEntity();
		if (null == element.getPluginEntity() && scenarioInput.getXmlElement() instanceof EntityTree)
			pluginEntity = scenarioInput.getPluginEntity(); // pluginEntity is null when processing Entity Meta Model xml File.
		try {
			int start = 0;
			while (true) {
				int min = expressionSB.length();
				int variable = -1;
				nextFV[0] = expressionSB.indexOf("#B()", start);
				nextFV[1] = expressionSB.indexOf("#BEAN()", start);
				for (int i = 0; i < 2; i++)
					if (-1 != nextFV[i] && nextFV[i] < min) {
						variable = i;
						min = nextFV[i];
					}
				if (-1 == variable)
					break;
				start = nextFV[variable] + skipSpaces[variable + 4];
				extractLazyField(element, pluginEntity, expressionSB, null, start, skipFirst);

			}
		} catch (Exception e) {
			logError(e);
			return str;
		}
		return expressionSB.toString();
	}

	/**
	 * Gets the extended field.
	 * 
	 * @param beanClass
	 *            the bean class
	 * @param fieldName
	 *            the field name
	 * @return the extended field
	 */
	private Field getExtendedField(Class<?> beanClass, String fieldName) {
		Class<?> superClass = beanClass;
		while (Object.class != superClass) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (SecurityException e) {
				throw e;
			} catch (NoSuchFieldException e) {
			}
			superClass = superClass.getSuperclass();
		}
		return null;
	}

	/**
	 * Extract lazy field.
	 * 
	 * @param element
	 *            the element
	 * @param pluginEntity
	 *            the plugin entity
	 * @param expressionSB
	 *            the expression sb
	 * @param column
	 *            the column
	 * @param start
	 *            the start
	 * @param skipFirst
	 *            the skip first
	 */
	private void extractLazyField(IElementWrapper element, PluginEntity<?> pluginEntity, StringBuffer expressionSB, String column,
			int start, boolean skipFirst) {
		// skipFirst = true when processing #ROW or #BEAN
		// skipFirst = false when processing #FIELDVALUE
		String fetchLazyMember = null == column ? "" : column;
		int length = expressionSB.length();
		boolean findLazyFetch = false;
		Class<?> beanClass;
		// if (null != pluginEntity.getEntityMetaModel())
		// beanClass = pluginEntity.getEntityMetaModel().getBeanClass();
		// else
		beanClass = scenarioInput.getPluginEntityWrapper(pluginEntity.getEntityURI()).getBeanClass();
		if (!skipFirst) {
			beanClass = FieldTools.getGetMethod(beanClass, column, true).getReturnType();
		}
		if (start < length && '.' == expressionSB.charAt(start)) {
			int i = ++start;
			while (true) {
				if (i == expressionSB.length() || GeneratorUtil.SPECIALCHARACTERS.contains(expressionSB.charAt(i))) {
					String originalMembers = expressionSB.substring(start, i);
					String resultMembers = null;
					StringTokenizer tokenizer = new StringTokenizer(originalMembers, ".");
					boolean toContinue = true;
					while (tokenizer.hasMoreElements()) {
						String token = tokenizer.nextToken().trim();
						if (toContinue) {
							toContinue = false;
							Class<?> superClass = beanClass;
							while (null != superClass && superClass != Object.class && superClass != QueryToolInput.class) {
								for (Field field : superClass.getDeclaredFields()) {
									if (token.equals(field.getName())) {
										Method method = FieldTools.getGetMethod(superClass, token, true);
										beanClass = method.getReturnType();
										resultMembers = addMemberWithDot(resultMembers, method.getName() + "()");
										toContinue = true;
										findLazyFetch = true;
										if (null != scenarioInput.getPluginEntityTree().getPluginEntity(field.getType()))
											fetchLazyMember += "." + token;
										break;
									}
								}
								superClass = superClass.getSuperclass();
							}
						}
						if (!toContinue) {
							if (token.endsWith("(") && ')' == expressionSB.charAt(i)) {
								i++;
								token += ")";
							}
							resultMembers = addMemberWithDot(resultMembers, token);
						}
					}
					expressionSB.delete(start, i);
					expressionSB.insert(start, resultMembers);
					break;
				}
				i++;
			} // if ('.' == expressionSB.charAt(start))
		}

		/*
		 * '.' could be use in Embedded Id whithout having lazy fetch process needed
		 */
		if (!(element instanceof ColumnFieldType) && findLazyFetch && !fetchLazyMember.isEmpty()) {
			String member = fetchLazyMember.substring(skipFirst ? fetchLazyMember.indexOf('.') + 1 : 0);
			Set<String> lazyFetchSet = lazyFetchMap.get(currentLazyFetchesContainer);
			if (null == lazyFetchSet) {
				lazyFetchSet = new HashSet<String>();
				lazyFetchMap.put(currentLazyFetchesContainer, lazyFetchSet);
			}
			lazyFetchSet.add(member);
		}
	}

	/**
	 * Adds token to member with dot if member was not null.
	 * 
	 * @param members
	 *            the members
	 * @param token
	 *            the token
	 * 
	 * @return the string
	 */
	private String addMemberWithDot(String members, String token) {
		return (null == members) ? token : members + "." + token;
	}

	/**
	 * Creates the trace.
	 * 
	 */
	public void createTrace() {
		boolean addTrace = null != rootWrapper;
		if (addTrace)
			if (null == GeneratorConstants.ADICHATZ_STUDIO_PREFERENCE_STORE)
				addTrace = "true".equals(scenarioInput.getScenarioResources().getParam(IScenarioConstants.CREATE_XML_TRACE));
			else
				addTrace = GeneratorConstants.ADICHATZ_STUDIO_PREFERENCE_STORE.getBoolean(IScenarioConstants.CREATE_XML_TRACE);
		if (addTrace) {
			File traceDirectory = new File(scenarioInput.getScenarioResources().getPluginHome() + "/resources/build/trace/"
					+ scenarioInput.getSubPackage().replace('.', '/'));
			if (traceDirectory.exists() || traceDirectory.mkdirs()) {
				File file = new File(traceDirectory.getAbsolutePath() + "/" + scenarioInput.getTreeId() + "TRACE.xml");
				ScenarioUtil.createXmlFile(file, rootWrapper);
				LogBroker.logTrace(getFromGeneratorBundle("xmlTraceCreated", file.getAbsolutePath()));
			}
		}
	}

	/**
	 * The Class AddedLabel.
	 * 
	 * @author Yves Drumbonnet
	 * 
	 */
	private class AddedLabel {

		/** The root wrapper. */
		IRootWrapper rootWrapper;

		/** The label wrapper. */
		LabelWrapper labelWrapper;

		/** The collection. */
		@SuppressWarnings("rawtypes")
		ICollectionWrapper collection;

		/** The linked element. */
		ControlFieldType linkedElement;

		/**
		 * Instantiates a new added label.
		 * 
		 * @param rootWrapper
		 *            the root wrapper
		 * @param collection
		 *            the collection
		 * @param labelWrapper
		 *            the label wrapper
		 * @param linkedElement
		 *            the linked element
		 */
		public AddedLabel(IRootWrapper rootWrapper, ICollectionWrapper<?> collection, LabelWrapper labelWrapper,
				ControlFieldType linkedElement) {
			this.collection = collection;
			this.labelWrapper = labelWrapper;
			this.linkedElement = linkedElement;
			this.rootWrapper = rootWrapper;
		}

	}

	/**
	 * Gets the plugin entity.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param entityURI
	 *            the current entity
	 * @return the plugin entity
	 */
	private PluginEntity<?> getPluginEntity(AdiPluginResources pluginResources, String entityURI) {
		if (null == pluginResources)
			pluginResources = this.scenarioInput.getScenarioResources().getPluginResources();
		return pluginResources.getPluginEntity(entityURI);
	}
}
