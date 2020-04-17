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

import static org.adichatz.engine.common.EngineTools.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class GenerationScenarioWrapper.
 */
@SuppressWarnings("serial")
public class GenerationScenarioWrapper extends GenerationScenarioType {

	/** The unit map. */
	private Map<GenerationEnum, GenerationUnitType> unitMap;

	/** The plugin entity map. */
	private Map<String, PluginEntityWrapper> pluginEntityMap;

	private Map<String, PropertyFieldType> propertyFieldMap;

	/** The delete unused resources. */
	private boolean deleteUnusedResources;

	private GenerationScenarioWrapper customGenerationScenario;

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
			for (GenerationUnitType generationUnitType : getGenerationUnit())
				unitMap.put(generationUnitType.getType(), generationUnitType);
		}
		return unitMap.get(generationEnum);
	}

	/**
	 * Adds the generation unit.
	 *
	 * @param scenarioResources
	 *            the scenario resources
	 * @param generationEnum
	 *            the generation enum
	 */
	public GenerationUnitWrapper addGenerationUnit(ScenarioResources scenarioResources, GenerationEnum generationEnum) {
		GenerationUnitWrapper generationUnit = (GenerationUnitWrapper) getGenerationUnit(generationEnum);
		if (null == generationUnit) {
			GenerationUnitWrapper gu = scenarioResources.getPluginEntityScenario().getDefaultGenerationUnit(generationEnum);
			getGenerationUnit().add(gu); // generationUnit could be null so use getGenerationUnit()
			unitMap.put(generationEnum, gu);
		}
		return generationUnit;
	}

	/**
	 * Removes the generation unit.
	 *
	 * @param generationEnum
	 *            the generation enum
	 */
	public void removeGenerationUnit(GenerationEnum generationEnum) {
		GenerationUnitType gu = getGenerationUnit(generationEnum);
		if (null != gu) {
			generationUnit.remove(gu);
			unitMap.remove(gu);
		}
	}

	/**
	 * Get plugin entity.
	 * 
	 * @param entityURI
	 *            the entity URI
	 * @return the plugin entity wrapper
	 */
	public PluginEntityWrapper getPluginEntity(String entityURI) {
		return getPluginEntityMap().get(entityURI);
	}

	private Map<String, PluginEntityWrapper> getPluginEntityMap() {
		if (null == pluginEntityMap) {
			pluginEntityMap = new HashMap<String, PluginEntityWrapper>();
			for (PluginEntityType pluginEntityType : getPluginEntity())
				pluginEntityMap.put(pluginEntityType.getEntityURI(), (PluginEntityWrapper) pluginEntityType);
		}
		return pluginEntityMap;
	}

	public PropertyFieldType getPropertyField(String fieldId) {
		if (null == propertyFieldMap) {
			propertyFieldMap = new HashMap<String, PropertyFieldType>();
			for (PropertyFieldType propertyFieldType : getPropertyField())
				propertyFieldMap.put(propertyFieldType.getId(), propertyFieldType);
		}
		return propertyFieldMap.get(fieldId);
	}

	/**
	 * Checks if is delete unused resources.
	 * 
	 * @return true, if is delete unused resources
	 */
	public boolean isDeleteUnusedResources() {
		return deleteUnusedResources;
	}

	/**
	 * Sets the delete unused resources.
	 * 
	 * @param deleteUnusedResources
	 *            the new delete unused resources
	 */
	public void setDeleteUnusedResources(boolean deleteUnusedResources) {
		this.deleteUnusedResources = deleteUnusedResources;
	}

	/**
	 * Delete java files.
	 *
	 * @param xmlFile
	 *            the xml file
	 * @throws CoreException
	 *             the core exception
	 */
	public void deleteJavaFiles(IFile xmlFile) throws CoreException {
		IWorkspaceRoot workspaceRoot = xmlFile.getProject().getWorkspace().getRoot();
		for (IResource javaFile : ScenarioUtil.getJavaFiles(xmlFile)) {
			workspaceRoot.findMember(javaFile.getFullPath()).delete(true, null);
		}
	}

	/**
	 * Inits the model part.
	 *
	 * @param pluginName
	 *            the plugin name
	 */
	public void initModelPart(ScenarioResources scenarioResources) {
		String lowerPluginName = EngineTools.lowerCaseFirstLetter(scenarioResources.getPluginName());
		if (null == modelPart.getDataSourceUnit())
			modelPart.setDataSourceUnit(lowerPluginName.concat("Unit"));
		if (IScenarioConstants.JSE.equals(modelPart.getConnectorASVersion())) {
			modelPart.setPersistenceManagerClassName("JSEPersistenceManager");
			modelPart.setLockManagerClassName("JSELockManager");
			modelPart.setEjbFileName(null);
			modelPart.setJndi(null);
		} else {
			// For AS connector
			if (null == modelPart.getEjbFileName())
				modelPart.setEjbFileName(scenarioResources.getPluginName().concat("EJB.jar"));
			modelPart.setJndi(GeneratorUtil.getJNDI(
					ScenarioUtil.getDatasource(scenarioResources, modelPart.getConnectorDataSource()).getDatasourceName()));
			String upperPluginName = EngineTools.upperCaseFirstLetter(lowerPluginName);
			if (null == modelPart.getLockManagerClassName() || "JSELockManager".equals(modelPart.getLockManagerClassName()))
				modelPart.setLockManagerClassName(
						GeneratorUtil.getJavaName(upperPluginName, FieldCaseEnum.UPPER_CASE_FIRST_LETTER, false).concat("LMBean"));
			if (null == modelPart.getPersistenceManagerClassName()
					|| "JSEPersistenceManager".equals(modelPart.getPersistenceManagerClassName()))
				modelPart.setPersistenceManagerClassName(
						GeneratorUtil.getJavaName(upperPluginName, FieldCaseEnum.UPPER_CASE_FIRST_LETTER, false).concat("PMBean"));
			if (null == modelPart.getPersistenceManagerClassName() || "JSELockManager".equals(modelPart.getLockManagerClassName()))
				modelPart.setLockManagerClassName(
						GeneratorUtil.getJavaName(upperPluginName, FieldCaseEnum.UPPER_CASE_FIRST_LETTER, false).concat("LMBean"));
		}

	}

	/**
	 * Gets the navigator generation unit.
	 *
	 * @return the navigator generation unit
	 */
	public List<GenerationUnitType> getNavigatorGenerationUnit() {
		List<GenerationUnitType> navigatorGenerationUnits = new ArrayList<GenerationUnitType>();
		for (GenerationUnitType generationUnit : getGenerationUnit())
			if (GenerationEnum.NAVIGATOR == generationUnit.getType())
				navigatorGenerationUnits.add(generationUnit);
		return navigatorGenerationUnits;
	}

	public GenerationScenarioWrapper getCustomGenerationScenario() {
		return customGenerationScenario;
	}

	public void setCustomGenerationScenario(GenerationScenarioWrapper customGenerationScenario) {
		this.customGenerationScenario = customGenerationScenario;
	}

	/**
	 * Merge customization.
	 *
	 * @param customGenerationScenario
	 *            the custom generation scenario
	 */
	public void mergeCustomization(GenerationScenarioType customGenerationScenario, String pluginName, int parts) {
		boolean flagRCP = (0 != (parts & IPluginEntityScenario.RCP_PART)) && null != getRcpPart();
		boolean flagEntity = (0 != (parts & IPluginEntityScenario.ENTITY));
		if ((flagEntity || flagRCP) && null != customGenerationScenario.getPropertyField()) {
			Map<String, PropertyFieldType> customizedPropertyFieldMap = new HashMap<>();
			for (PropertyFieldType propertyField : customGenerationScenario.getPropertyField()) {
				customizedPropertyFieldMap.put(propertyField.getId(), propertyField);
			}
			// Update new PropertyField (present into custom and current scenario.xml files
			for (PropertyFieldType propertyField : getPropertyField()) {
				PropertyFieldType customPropertyField = customizedPropertyFieldMap.get(propertyField.getId());
				if (null != customPropertyField) {
					customizedPropertyFieldMap.remove(propertyField.getId());
					propertyField.setMandatory(customPropertyField.isMandatory());
					propertyField.setPojoType(customPropertyField.getPojoType());
					propertyField.setControlField(customPropertyField.getControlField());
					propertyField.setColumnField(customPropertyField.getColumnField());
				}
			}
			// Adds new PropertyField (present into custom file and not in current scenario.xml file
			for (PropertyFieldType propertyField : customizedPropertyFieldMap.values()) {
				getPropertyField().add(propertyField);
			}
			customizedPropertyFieldMap.clear();
		}
		if (null != customGenerationScenario.getGenerationUnit()) {
			Map<MultiKey, GenerationUnitType> customizedGenerationUnits = new HashMap<>();
			for (GenerationUnitType generationUnit : customGenerationScenario.getGenerationUnit()) {
				MultiKey multiKey = null == generationUnit.getAdiResourceURI() ? new MultiKey(generationUnit.getType())
						: new MultiKey(generationUnit.getType(), generationUnit.getAdiResourceURI());
				customizedGenerationUnits.put(multiKey, generationUnit);
			}
			if (flagRCP) {
				// Update new GenerationUnit (present into custom and current scenario.xml files
				for (GenerationUnitType generationUnit : getGenerationUnit()) {
					MultiKey multiKey = null == generationUnit.getAdiResourceURI() ? new MultiKey(generationUnit.getType())
							: new MultiKey(generationUnit.getType(), generationUnit.getAdiResourceURI());
					GenerationUnitType customGenerationUnit = customizedGenerationUnits.get(multiKey);
					if (null != customGenerationUnit) {
						customizedGenerationUnits.remove(multiKey);
						generationUnit.setScenarioClassName(customGenerationUnit.getScenarioClassName());
					}
				}
				// Adds new GenerationUnit (present into custom file and not in current scenario.xml file
				for (GenerationUnitType generationUnit : customizedGenerationUnits.values()) {
					getGenerationUnit().add(generationUnit);
				}
				customizedGenerationUnits.clear();
			}
		}
		if (flagEntity) {
			if (null != customGenerationScenario.getModelPart()) {
				ModelPartType customModelPart = customGenerationScenario.getModelPart();
				if (null == modelPart)
					modelPart = customModelPart;
				else
					((ModelPartWrapper) modelPart).mergeCustomization(customModelPart);
			}
		}
		if (!customGenerationScenario.getPluginEntity().isEmpty()) {
			mergePluginEntities(customGenerationScenario, pluginName, flagRCP, flagEntity);
		}
	}

	public void mergePluginEntities(GenerationScenarioType customGenerationScenario, String pluginName, boolean flagRCP,
			boolean flagEntity) {
		Map<MultiKey, PluginEntityType> customizedPluginEntityMap = new HashMap<>();
		for (PluginEntityType pluginEntity : customGenerationScenario.getPluginEntity()) {
			customizedPluginEntityMap.put(getCustomMultiKey(pluginEntity), pluginEntity);
		}
		for (PluginEntityType pluginEntity : getPluginEntity()) {
			Object[] entityKeys = EngineTools.getInstanceKeys(pluginEntity.getEntityURI());
			PluginEntityType customPluginEntity = customizedPluginEntityMap.get(new MultiKey(entityKeys));
			if (null == customPluginEntity) {
				customPluginEntity = customizedPluginEntityMap.get(new MultiKey(entityKeys[1], entityKeys[2]));
				if (null == customPluginEntity)
					customPluginEntity = customizedPluginEntityMap.get(new MultiKey(entityKeys[2]));
			}
			if (null != customPluginEntity)
				((PluginEntityWrapper) pluginEntity).mergeCustomization(customPluginEntity, flagRCP, flagEntity);
		}
	}

	public MultiKey getCustomMultiKey(PluginEntityType pluginEntity) {
		String[] entityKeys = EngineTools.getInstanceKeys(pluginEntity.getEntityURI());
		List<Object> keys = new ArrayList<>(3);
		for (String key : entityKeys)
			if (!isEmpty(key) && !".".equals(key))
				keys.add(key);
		return new MultiKey(keys.toArray());
	}

	public void addPluginEntity(PluginEntityType pluginEntity) {
		getPluginEntity().add(pluginEntity);
		getPluginEntityMap().put(pluginEntity.getEntityURI(), (PluginEntityWrapper) pluginEntity);
	}

	public void reinit() {
		unitMap = null;
		pluginEntityMap = null;
		propertyFieldMap = null;
	}
}
