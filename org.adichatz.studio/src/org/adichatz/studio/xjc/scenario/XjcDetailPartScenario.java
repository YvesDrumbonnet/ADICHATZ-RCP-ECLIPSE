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
package org.adichatz.studio.xjc.scenario;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.ContainerTreeWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.HyperlinkWrapper;
import org.adichatz.generator.wrapper.PShelfItemWrapper;
import org.adichatz.generator.wrapper.PShelfWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScrolledCompositeWrapper;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.generator.xjc.TextType;
import org.adichatz.generator.xjc.ValidElementType;
import org.adichatz.scenario.IDetailScenario;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.impl.AScenario;
import org.adichatz.studio.db4o.Db4oTools;
import org.adichatz.studio.db4o.model.Element;
import org.adichatz.studio.db4o.model.Item;
import org.adichatz.studio.db4o.model.Property;
import org.adichatz.studio.xjc.controller.ClassTextController;
import org.adichatz.studio.xjc.controller.CodeTextController;
import org.adichatz.studio.xjc.controller.MultiClassChoiceController;
import org.adichatz.studio.xjc.controller.OutlineHyperlinkController;
import org.adichatz.studio.xjc.editor.runnable.OpenClassEditorRunnable;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcDetailPartScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class XjcDetailPartScenario extends AScenario implements IDetailScenario {

	private List<ValidElementType> basics = new ArrayList<ValidElementType>();

	private List<ValidElementType> lifeCycles = new ArrayList<ValidElementType>();

	private List<ValidElementType> controls = new ArrayList<ValidElementType>();

	private Boolean usePshelf;

	private String labelSuffix;

	/**
	 * Instantiates a new a XJC detail scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public XjcDetailPartScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/** The Detail layout constraints. */
	public static String DETAIL_LC = "detailLayoutConstraints";

	/** The Detail column constraints. */
	public static String DETAIL_CC = "detailColumnConstraints";

	/** The Detail row constraints. */
	public static String DETAIL_RC = "detailRowConstraints";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IDetailScenario#createEntityDetailFields(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createEntityDetailFields(ScenarioInput scenarioInput) {
		labelSuffix = scenarioInput.getTreeId().concat("$1");

		PluginEntityWrapper pew = ((GenerationScenarioWrapper) scenarioInput.getScenarioResources().getScenarioTree()
				.getGenerationScenario()).getPluginEntity(scenarioInput.getPluginEntity().getEntityURI());
		IPluginEntityScenario pluginEntityScenario = scenarioInput.getScenarioResources().getPluginEntityScenario();
		scenarioInput.setTreeId(pluginEntityScenario.getTreeId(pew, GenerationEnum.DETAIL));
		scenarioInput.setSubPackage(pluginEntityScenario.getSubPackage(pew, GenerationEnum.DETAIL));

		this.scenarioInput = scenarioInput;
		ContainerTreeWrapper containerTree;
		if (scenarioInput.isGenerateXml()) {
			LogBroker.logTrace("Creating detail XML file for " + scenarioInput.getTreeId().concat("."));
			containerTree = new ContainerTreeWrapper();
			containerTree.setId("detailPart");
			containerTree.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());

			ScenarioInput entityInput = new ScenarioInput(scenarioInput.getScenarioResources(),
					scenarioInput.getPluginEntity().getEntityKeys()[2], scenarioInput.getPluginEntity().getEntityKeys()[1]);
			EntityTree entityTree = (EntityTree) new GeneratorUnit(entityInput).getTreeWrapperFromXml(true);
			AdiPluginResources studioPR = scenarioInput.getScenarioResources().getPluginResources();

			basics.clear();
			lifeCycles.clear();
			controls.clear();

			usePshelf = Db4oTools.getInstance().getElement(null, scenarioInput.getPluginEntity().getEntityId(), true)
					.getUsePshelf();
			if (null == usePshelf)
				usePshelf = false;

			Class<?> beanClass = scenarioInput.getPluginEntityWrapper().getBeanClass();
			PluginEntity extendedPE = studioPR.getPluginEntityTree().getPluginEntity(beanClass);
			while (null != extendedPE) {
				entityInput = new ScenarioInput(scenarioInput.getScenarioResources(), extendedPE.getEntityKeys()[2],
						extendedPE.getEntityKeys()[1]);
				EntityTree extendEntityTree = (EntityTree) new GeneratorUnit(entityInput).getTreeWrapperFromXml(true);
				for (PropertyFieldType propertyField : extendEntityTree.getPropertyField()) {
					if (!(propertyField instanceof RefFieldType)) {
						// label field must be specified
						addControlField(scenarioInput.getPluginEntity(), extendedPE, pew.getPluginEntityId(), propertyField, true);
					}
				}
				beanClass = beanClass.getSuperclass();
				if (XjcPluginEntityScenario.EXCLUDED_CLASSES.contains(beanClass.getName()))
					extendedPE = null;
				else
					extendedPE = studioPR.getPluginEntityTree().getPluginEntity(beanClass);
			}
			String entityId = EngineTools.getEntityId(EngineTools.getInstanceKeys(entityTree.getEntityURI())[2]);
			Element elementDb4o = Db4oTools.getInstance().getElement(null, entityId, true);
			Comparator<ElementType> elementComparator;
			if (null == elementDb4o || null == elementDb4o.getFieldRanks()) {
				elementComparator = new Comparator<ElementType>() {
					@Override
					public int compare(ElementType o1, ElementType o2) {
						if ("id".equals(o1.getId()))
							return -1;
						if ("id".equals(o2.getId()))
							return 1;
						if (isCode(o1) && !isCode(o2))
							return 1;
						if (!isCode(o1) && isCode(o2))
							return -1;
						if (o1.getId().equals(o2.getId().concat(labelSuffix)))
							return -1;
						if (o2.getId().equals(o1.getId().concat(labelSuffix)))
							return 1;
						return o1.getId().compareToIgnoreCase(o2.getId());
					}
				};
			} else {
				final Map<String, Integer> fieldRankMap = new HashMap<String, Integer>();
				StringTokenizer tokenizer = new StringTokenizer(elementDb4o.getFieldRanks(), ",");
				int index = 1;
				while (tokenizer.hasMoreTokens())
					fieldRankMap.put(tokenizer.nextToken().trim(), index++);
				elementComparator = new Comparator<ElementType>() {
					@Override
					public int compare(ElementType o1, ElementType o2) {
						Integer rank1 = getRank(fieldRankMap, o1.getId());
						Integer rank2 = getRank(fieldRankMap, o2.getId());
						if (null == rank1 || null == rank2) {
							if (null != rank1)
								return 1;
							if (null != rank2)
								return -1;
							return 0;
						}
						if (o1.getId().equals(o2.getId().concat(labelSuffix)))
							return -1;
						return rank1.compareTo(rank2);
					}

					private Integer getRank(final Map<String, Integer> fieldRankMap, String fieldName) {
						Integer rank = fieldRankMap.get(fieldName);
						if (null == rank && fieldName.endsWith(labelSuffix)) {
							rank = fieldRankMap.get(fieldName.substring(0, fieldName.length() - labelSuffix.length()));
						}
						return rank;
					}
				};

			}
			Collections.sort(basics, elementComparator);
			if (usePshelf) {
				Collections.sort(lifeCycles, elementComparator);
				Collections.sort(controls, elementComparator);
				PShelfItemWrapper basicItem = new PShelfItemWrapper();
				PShelfItemWrapper lifeCycleItem = new PShelfItemWrapper();
				PShelfItemWrapper controlItem = new PShelfItemWrapper();

				PShelfWrapper pshelf = new PShelfWrapper();
				pshelf.setDelayed("true");
				pshelf.setSelection("0");
				pshelf.setId("detailPShelf");
				containerTree.getElements().add(pshelf);
				addPShelfItem(pshelf, basicItem, "basicItem", basics);
				addPShelfItem(pshelf, lifeCycleItem, "lifeCycleItem", lifeCycles);
				addPShelfItem(pshelf, controlItem, "controlItem", controls);
			} else {
				ScrolledCompositeWrapper itemComposite = new ScrolledCompositeWrapper();
				itemComposite.setId("itemComposite");
				itemComposite.setLayout(addLayout("wrap 2", "[align right]10[fill,grow]", "[]"));
				itemComposite.getElements().addAll(basics);
				itemComposite.setDirtyManagement("false");

				containerTree.getElements().add(itemComposite);
			}
			// }
		} else
			containerTree = (ContainerTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		createXmlAndPrepare(scenarioInput.setXmlElement(containerTree));
	}

	private boolean isCode(ElementType element) {
		if (!(element instanceof TextType))
			return false;
		return CodeTextController.class.getName().equals(((TextType) element).getControllerClassName());
	}

	/**
	 * Adds the control field.
	 * 
	 * @param pluginEntity
	 *            the plugin entity
	 * @param collectionWrapper
	 *            the collection wrapper
	 * @param property
	 *            the property
	 * @param addLabel
	 *            the add label : True implies that label text must be specified
	 * @return the control field type
	 */
	private void addControlField(PluginEntity processedEntity, PluginEntity pluginEntity, String entityId,
			PropertyFieldType property, boolean addLabel) {
		if ("parentIndex".equals(property.getId()))
			return;
		ControlFieldType controlField = property.getControlField();
		controlField.setProperty(property.getId());
		controlField.setId(property.getId());
		controlField.setFieldBindManagerClassName("org.adichatz.studio.xjc.data.XjcFieldBindingManager");

		String controllerClassName = Db4oTools.getInstance().getControllerClassName(entityId, property.getId());
		if (null != controllerClassName) // when keep value from controlField.getControllerClassName()
			controlField.setControllerClassName(controllerClassName);

		String labelText = addLabel
				? "#MSG(" + EngineTools.lowerCaseFirstLetter(pluginEntity.getEntityId()) + ", " + controlField.getProperty()
						+ ").concat(\":\")"
				: null;

		if (usePshelf) {
			Method getGetMethod = FieldTools.getGetMethod(pluginEntity.getEntityMetaModel().getBeanClass(),
					property.getId().substring(property.getId().indexOf('#') + 1), true);
			Item itemDb4o = Db4oTools.getInstance().getItem(null, entityId, controlField.getId());
			if (null == itemDb4o) {
				LogBroker.logError("No item found for (" + entityId + "," + controlField.getId() + ") in base Db4o!");
				return;
			}
			String category = itemDb4o.getCategory();
			if (null == category) {
				Property propertyDb4o = Db4oTools.getInstance().getProperty(null, controlField.getId());
				if (null == propertyDb4o) {
					LogBroker.logError("No property found for " + controlField.getId() + " in base Db4o!");
					return;
				}
				category = propertyDb4o.getCategory();
				if (null == category) // Default category is 'BASIC'
					category = "BASIC";
			}

			if ("BASIC".equals(category))
				add2parent(pluginEntity, entityId, basics, controlField, labelText);
			else {
				ControlFieldType cloneElement = (ControlFieldType) EngineTools.cloneSerializable(controlField);
				if (!getGetMethod.getReturnType().isPrimitive()) // Valid clause will be used in clone of controlField
					cloneElement.setValid("null != #FV()");
				add2parent(pluginEntity, entityId, basics, cloneElement, labelText);
				if ("LIFE_CYCLE".equals(category))
					add2parent(pluginEntity, entityId, lifeCycles, controlField, labelText);
				else if ("CONTROL".equals(category))
					add2parent(pluginEntity, entityId, controls, controlField, labelText);
			}
		} else {
			add2parent(pluginEntity, entityId, basics, controlField, labelText);
			// controlField.setLabelText(labelText);
			// basics.add(controlField);
		}
	}

	private void add2parent(PluginEntity pluginEntity, String entityId, List<ValidElementType> parentCollection,
			ControlFieldType controlField, String labelText) {
		String runnableClassName = null;
		boolean isURI = false;
		if ((ClassTextController.class.getName().equals(controlField.getControllerClassName())
				|| MultiClassChoiceController.class.getName().equals(controlField.getControllerClassName()))
				&& !"Controller".equals(pluginEntity.getEntityId())) {
			runnableClassName = OpenClassEditorRunnable.class.getName();
		} else if ("org.adichatz.studio.xjc.controller.AdiResourceUriTextController"
				.equals(controlField.getControllerClassName())) {
			runnableClassName = "org.adichatz.studio.xjc.editor.runnable.OpenResourceURIRunnable";
			isURI = true;
		}
		if (null != runnableClassName) {
			controlField.setNoLabel(true);
			HyperlinkWrapper hyperlink = new HyperlinkWrapper();
			hyperlink.setId(controlField.getId() + labelSuffix);
			hyperlink.setRunnableClassName(runnableClassName);
			if (isURI) {
				String fieldName = EngineTools.upperCaseFirstLetter(controlField.getId());
				hyperlink.setEnabled("null != #BEAN().get" + fieldName + "()");
			}
			hyperlink.setControllerClassName(OutlineHyperlinkController.class.getName());
			String hlLabelText;
			if (null != labelText)
				hlLabelText = labelText;
			else
				hlLabelText = "#MSG(" + EngineTools.lowerCaseFirstLetter(pluginEntity.getEntityId()) + ", "
						+ controlField.getProperty() + ")";
			hyperlink.setText(hlLabelText);
			controlField.setLinkedControl(hyperlink.getId());
			parentCollection.add(hyperlink);
		} else
			controlField.setLabelForeground("#COLOR(IFormColors.TITLE)");
		controlField.setLabelText(labelText);
		parentCollection.add(controlField);
	}

	private void addPShelfItem(PShelfWrapper pshelf, PShelfItemWrapper pshelfItem, String itemId, List<ValidElementType> children) {
		if (!children.isEmpty()) {
			pshelfItem.setId(itemId);
			pshelfItem.setText("#MSG(adichatzStudio, studio.xjcEditor." + itemId + ")");

			ScrolledCompositeWrapper scrolledComposite = new ScrolledCompositeWrapper();
			scrolledComposite.setId(itemId + "SC");
			scrolledComposite.getElements().addAll(children);
			scrolledComposite.setLayout(addLayout("wrap 2", "[align right]10[fill,grow]", "[]"));
			scrolledComposite.setDirtyManagement("false");
			pshelfItem.getElements().add(scrolledComposite);

			pshelf.getElements().add(pshelfItem);
		}
	}
}
