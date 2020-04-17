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
package org.adichatz.generator.wrapper;

import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.tools.GeneratorErrorException;
import org.adichatz.generator.wrapper.internal.IParamsContainer;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.IconType;
import org.adichatz.generator.xjc.MessageType;
import org.adichatz.generator.xjc.MessagesType;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.OneToOneTypeEnum;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PartImageTypeEnum;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.PojoRewriterType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginEntityWrapper.
 */
public class PluginEntityWrapper extends PluginEntityType implements IParamsContainer {

	/** The entity keys. */
	private String[] entityKeys;

	/** The unit map. */
	private Map<GenerationEnum, GenerationUnitType> unitMap;

	private Map<String, PropertyFieldType> fieldMap;

	/** The bean class. */
	private Class<?> beanClass;

	/** The bean class. */
	private Class<?> uiBeanClass;

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The entity tree. */
	private EntityTreeWrapper entityTree;

	/** The overriden. */
	private boolean overriden;

	/**
	 * Instantiates a new plugin entity wrapper.
	 */
	public PluginEntityWrapper() {
	}

	/**
	 * Instantiates a new plugin entity wrapper.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public PluginEntityWrapper(ScenarioResources scenarioResources) {
		this();
		this.scenarioResources = scenarioResources;
	}

	/**
	 * Instantiates a new plugin entity wrapper.
	 * 
	 * @param pluginEntity
	 *            the plugin entity
	 */
	public PluginEntityWrapper(PluginEntityType pluginEntity) {
		this();
		ScenarioUtil.copyExtendedFields(pluginEntity, this);
	}

	/**
	 * Delete resources.
	 */
	public void deleteResources() {
		for (ScenarioInput scenarioInput : scenarioResources.getPluginEntityScenario().getDependingResources(this)) {
			scenarioResources.deleteResources(scenarioInput, false);
		}
	}

	/**
	 * Gets the entity keys.
	 * 
	 * @return the entity keys
	 */
	public String[] getEntityKeys() {
		if (null == entityKeys) {
			entityKeys = EngineTools.getInstanceKeys(entityURI);
		}
		return entityKeys;
	}

	/**
	 * Clear unit map.
	 */
	public void clearUnitMap() {
		this.unitMap = null;
	}

	/**
	 * Gets the image.
	 * 
	 * @param imageType
	 *            the image type
	 * @return the image
	 */
	public String getIcon(PartImageTypeEnum imageType) {
		if (null != icons)
			for (IconType icon : icons.getIcon())
				if (icon.getType().equals(imageType))
					return icon.getImage();
		return null;
	}

	/**
	 * Gets the generation unit.
	 * 
	 * @param generationEnum
	 *            the generation enum
	 * @return the generation unit
	 */
	public GenerationUnitType getGenerationUnit(GenerationEnum generationEnum) {
		if (null == unitMap) {
			unitMap = new HashMap<GenerationEnum, GenerationUnitType>();
			if (null != generationUnit)
				for (GenerationUnitType generationUnitType : generationUnit)
					unitMap.put(generationUnitType.getType(), generationUnitType);
		}
		return unitMap.get(generationEnum);
	}

	public PropertyFieldType getPropertyField(String id) {
		if (null == fieldMap) {
			fieldMap = new HashMap<String, PropertyFieldType>();
			for (PropertyFieldType propertyField : getPropertyField())
				fieldMap.put(propertyField.getId(), propertyField);
		}
		return fieldMap.get(id);
	}

	/**
	 * Gets the plugin entity id.
	 * 
	 * Return a String value corresponding to the simple bean class name.<br>
	 * PluginEntityType.entityId is a unique key. A simple bean class name can be shared by 2 different entities i.e. if a UI
	 * AdiPluginResources points on several Model AdiPluginResources.
	 * 
	 * @return the plugin entity id
	 */
	public String getPluginEntityId() {
		return EngineTools.getEntityId(getEntityKeys()[2]);
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public String getEntityId() {
		return EngineTools.getEntityId(getEntityKeys()[2]);
	}

	/**
	 * Gets the bean class.
	 * 
	 * @return the bean class
	 */
	public Class<?> getBeanClass() {
		if (null == beanClass && null != scenarioResources) {
			PluginEntity<?> pluginEntity = scenarioResources.getPluginResources().getPluginEntity(entityURI);
			if (null == pluginEntity)
				return null;
			if (!EngineTools.isEmpty(beanClassName))
				beanClass = scenarioResources.getGencodePath().getClazz(beanClassName);
			else {
				String pluginKey = getEntityKeys()[0];
				ScenarioResources modelSR = ".".equals(pluginKey) ? scenarioResources
						: ScenarioResources.getInstance(pluginKey, scenarioResources.getPluginName());
				if (!modelSR.isParametersLoaded())
					modelSR.loadScenarioParameters();
				StringBuffer classNameSB = new StringBuffer(modelSR.getPluginResources().getModelPackageName());
				if (0 != classNameSB.length())
					classNameSB.append(".");
				classNameSB.append(pluginEntity.getEntityId());
				beanClass = modelSR.getGencodePath().getClazz(classNameSB.toString(), false);
			}
		}
		return beanClass;
	}

	public Class<?> getUIBeanClass() {
		if (null == uiBeanClass && null != scenarioResources) {
			if (EngineTools.isEmpty(uiBeanClassName)) {
				uiBeanClass = getBeanClass();
			} else {
				uiBeanClass = scenarioResources.getGencodePath().getClazz(uiBeanClassName);
			}
		}
		return uiBeanClass;
	}

	/**
	 * Sets the bean class.
	 * 
	 * @param beanClass
	 *            the new bean class
	 */
	public void setBeanClass(Class<?> beanClass) {
		if (null != beanClass || null != this.beanClass) {
			this.beanClass = beanClass;
		}
	}

	/**
	 * Checks if is overriden.
	 *
	 * @return true, if checks if is overriden
	 */
	public boolean isOverriden() {
		return overriden;
	}

	/**
	 * Sets the overriden.
	 *
	 * @param overriden
	 *            the overriden
	 */
	public void setOverriden(boolean overriden) {
		this.overriden = overriden;
	}

	/**
	 * Gets the scenario resources.
	 * 
	 * @return the scenario resources
	 */
	public ScenarioResources getScenarioResources() {
		return scenarioResources;
	}

	/**
	 * Sets the scenario resources.
	 * 
	 * @param scenarioResources
	 *            the new scenario resources
	 */
	public void setScenarioResources(ScenarioResources scenarioResources) {
		this.scenarioResources = scenarioResources;
	}

	/**
	 * Gets the entity tree.
	 * 
	 * @return the entity tree
	 */
	public EntityTreeWrapper getEntityTree() {
		if (null == entityTree && null != scenarioResources) {
			PluginEntity<?> pluginEntity = scenarioResources.getPluginResources().getPluginEntity(entityURI);
			if (null != pluginEntity)
				entityTree = (EntityTreeWrapper) new GeneratorUnit(new ScenarioInput(scenarioResources, pluginEntity))
						.getTreeWrapperFromXml(true);
		}
		return entityTree;
	}

	/**
	 * Sets the entity tree.
	 * 
	 * @param entityTree
	 *            the new entity tree
	 */
	public void setEntityTree(EntityTreeWrapper entityTree) {
		this.entityTree = entityTree;
	}

	/**
	 * Merge customization.
	 *
	 * @param customPluginEntitty
	 *            the custom plugin entitty
	 */
	public void mergeCustomization(PluginEntityType customPluginEntitty, boolean flagRCP, boolean flagEntity) {
		if (flagEntity) {
			if (null != customPluginEntitty.getPojoRewriters()) {
				if (null == pojoRewriters)
					pojoRewriters = customPluginEntitty.getPojoRewriters();
				else
					for (PojoRewriterType customPojoRewriter : customPluginEntitty.getPojoRewriters().getPojoRewriter())
						if (!EngineTools.isEmpty(customPojoRewriter.getRewriterClassName())) {
							PojoRewriterType currentPojoRewriter = null;
							for (PojoRewriterType pojoRewriter : pojoRewriters.getPojoRewriter())
								if (customPojoRewriter.getRewriterClassName().equals(pojoRewriter.getRewriterClassName())) {
									currentPojoRewriter = pojoRewriter;
									break;
								}
							if (null != currentPojoRewriter)
								pojoRewriters.getPojoRewriter().remove(currentPojoRewriter);
							pojoRewriters.getPojoRewriter().add(customPojoRewriter);
						}
			}
		}
		if (flagEntity || flagRCP) {
			if (null != customPluginEntitty.getPropertyField()) {
				Map<String, PropertyFieldType> customPropertyFieldMap = new HashMap<>();
				for (PropertyFieldType propertyField : customPluginEntitty.getPropertyField()) {
					customPropertyFieldMap.put(propertyField.getId(), propertyField);
				}
				for (PropertyFieldType propertyField : getPropertyField()) {
					PropertyFieldType customPropertyField = customPropertyFieldMap.get(propertyField.getId());
					if (null != customPropertyField) {
						customPropertyFieldMap.remove(propertyField.getId());
						propertyField.setMandatory(customPropertyField.isMandatory());
						propertyField.setColumnField(customPropertyField.getColumnField());
						propertyField.setControlField(customPropertyField.getControlField());
						propertyField.setPojoType(customPropertyField.getPojoType());
					}
				}
				for (PropertyFieldType propertyField : customPropertyFieldMap.values()) {
					getPropertyField().add(propertyField);
				}
				customPropertyFieldMap.clear();
			}
		}
		if (flagEntity | flagRCP)
			mergeParams(customPluginEntitty);
		if (flagRCP) {
			if (null != customPluginEntitty.getMessages()) {
				Map<MultiKey, MessageType> customizedMessagedMap = new HashMap<>();
				for (MessageType message : customPluginEntitty.getMessages().getMessage())
					customizedMessagedMap.put(new MultiKey(message.getKey(), message.getLanguage()), message);
				if (null != messages)
					for (MessageType message : messages.getMessage()) {
						MultiKey multiKey = new MultiKey(message.getKey(), message.getLanguage());
						MessageType customMessage = customizedMessagedMap.get(multiKey);
						if (null != customMessage) {
							customizedMessagedMap.remove(multiKey);
							message.setValue(customMessage.getValue());
						}
					}
				else
					messages = new MessagesType();
				for (MessageType message : customizedMessagedMap.values())
					messages.getMessage().add(message);
			}
			mergeGenerationUnits(customPluginEntitty);
		}
		if (null != customPluginEntitty.getRetrieveRoles())
			setRetrieveRoles(customPluginEntitty.getRetrieveRoles());
		if (null != customPluginEntitty.getPersistRoles())
			setPersistRoles(customPluginEntitty.getPersistRoles());
		if (null != customPluginEntitty.getMergeRoles())
			setMergeRoles(customPluginEntitty.getMergeRoles());
		if (null != customPluginEntitty.getRemoveRoles())
			setRemoveRoles(customPluginEntitty.getRemoveRoles());
	}

	private void mergeGenerationUnits(PluginEntityType customPluginEntitty) {
		Map<MultiKey, GenerationUnitType> customizedGenerationUnits = new HashMap<>();
		for (GenerationUnitType generationUnit : customPluginEntitty.getGenerationUnit()) {
			MultiKey multiKeyGU = null == generationUnit.getAdiResourceURI() ? new MultiKey(generationUnit.getType())
					: new MultiKey(generationUnit.getType(), generationUnit.getAdiResourceURI());
			customizedGenerationUnits.put(multiKeyGU, generationUnit);
		}
		for (GenerationUnitType generationUnit : getGenerationUnit()) {
			MultiKey multiKeyGU = null == generationUnit.getAdiResourceURI() ? new MultiKey(generationUnit.getType())
					: new MultiKey(generationUnit.getType(), generationUnit.getAdiResourceURI());
			GenerationUnitType customGenerationUnit = customizedGenerationUnits.get(multiKeyGU);
			if (null != customGenerationUnit) {
				customizedGenerationUnits.remove(multiKeyGU);
				generationUnit.setScenarioClassName(customGenerationUnit.getScenarioClassName());
			}
		}
		for (GenerationUnitType customGU : customizedGenerationUnits.values()) {
			getGenerationUnit().add(customGU);
		}
		customizedGenerationUnits.clear();
	}

	public void mergeParams(PluginEntityType customPluginEntitty) {
		if (null != customPluginEntitty.getParams()) {
			Map<String, ParamType> customParamMap = new HashMap<>();
			for (ParamType param : customPluginEntitty.getParams().getParam())
				customParamMap.put(param.getId(), param);
			if (null != params)
				for (ParamType param : getParams().getParam()) {
					ParamType customParam = customParamMap.get(param.getId());
					if (null != customParam) {
						customParamMap.remove(param.getId());
						param.setId(customParam.getId());
						param.setType(customParam.getType());
						param.setValue(customParam.getValue());
					}
				}
			else
				params = new ParamsType();
			for (ParamType param : customParamMap.values())
				params.getParam().add(param);
			customParamMap.clear();
		}
	}

	public boolean isOneToOne() {
		EntityTreeWrapper entityTree = getEntityTree();
		if (null == entityTree)
			throw new GeneratorErrorException(GeneratorErrorException.NO_ENTITY_TREE,
					getFromGeneratorBundle("generation.no.entity.tree.found", entityURI));
		if (entityTree.getOneToOne().isEmpty())
			return false;
		else {
			boolean onetoone = false;
			for (OneToOneType oneToOne : getEntityTree().getOneToOne())
				if (OneToOneTypeEnum.SLAVE == oneToOne.getOneToOneType())
					return true;
			return onetoone;
		}
	}
}
