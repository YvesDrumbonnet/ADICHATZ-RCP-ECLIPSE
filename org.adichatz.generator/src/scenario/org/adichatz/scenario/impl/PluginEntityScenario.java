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

import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.wrapper.GenerationUnitWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.resources.IFile;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginEntityScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class PluginEntityScenario extends AScenario implements IPluginEntityScenario {

	protected ScenarioResources scenarioResources;

	public PluginEntityScenario(GenerationUnitType generationUnit, ScenarioResources scenarioResources) {
		super(generationUnit);
		this.scenarioResources = scenarioResources;
		postCreate();
	}

	protected void postCreate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPluginEntityScenario#populatePluginEntity(org.adichatz.scenario.ScenarioResources,
	 * org.adichatz.scenario.ScenarioResources, org.adichatz.generator.wrapper.PluginEntityWrapper)
	 */
	@Override
	public void populatePluginEntity(ScenarioResources modelSR, PluginEntityWrapper pluginEntity, int part,
			GenerationEnum generationEnum) {
		scenarioInput = new ScenarioInput(scenarioResources, true, true, true);
		ModelPartType modelPart = modelSR.getGenerationScenario().getModelPart();
		if (null == modelPart)
			throw new RuntimeException(getFromGeneratorBundle("scenario.missing.modelPart"));
		String entityId = pluginEntity.getEntityId();

		if (0 != (part & MODEL_PART)) {
			if (null == generationEnum || GenerationEnum.ENTITY == generationEnum)
				addEntityGenerationUnit(pluginEntity, entityId);
			if (null == generationEnum || GenerationEnum.QUERY == generationEnum)
				addQueryGenerationUnit(pluginEntity, entityId);
		}
		if (0 != (part & RCP_PART)) {
			if (null == generationEnum || GenerationEnum.DETAIL == generationEnum)
				addDetailGenerationUnit(pluginEntity, entityId);
			if (null == generationEnum || GenerationEnum.MESSAGE_BUNDLE == generationEnum)
				addMessageGenerationUnit(pluginEntity, entityId);
			// If bean id is a computed as a generated oneToOne key, check if no other bean has relationship with OnetTOmany or
			// ManyToone. In this case, do not create EDITOR and TABLE parts.
			boolean onlyOneToOne = pluginEntity.isOneToOne();
			if (onlyOneToOne) {
				Class<?> beanClass = pluginEntity.getBeanClass();
				for (Method method : beanClass.getDeclaredMethods()) {
					if (null != method.getAnnotation(ManyToMany.class) || null != method.getAnnotation(ManyToOne.class))
						onlyOneToOne = false;
				}
			}
			if (!onlyOneToOne) {
				if (null == generationEnum || GenerationEnum.EDITOR == generationEnum)
					addEditorGenerationUnit(pluginEntity, entityId);
				if (null == generationEnum || GenerationEnum.TABLE == generationEnum)
					addTableGenerationUnit(pluginEntity, entityId);
			}
		}
	}

	protected void addQueryGenerationUnit(PluginEntityWrapper pluginEntity, String entityId) {
		addGenerationUnit(pluginEntity, entityId.concat("QUERY"), GenerationEnum.QUERY);
	}

	protected void addTableGenerationUnit(PluginEntityWrapper pluginEntity, String entityId) {
		addGenerationUnit(pluginEntity, entityId + "TI", GenerationEnum.TABLE);
	}

	protected void addEditorGenerationUnit(PluginEntityWrapper pluginEntity, String entityId) {
		addGenerationUnit(pluginEntity, entityId + "EDITOR", GenerationEnum.EDITOR);
	}

	protected void addMessageGenerationUnit(PluginEntityWrapper pluginEntity, String entityId) {
		addGenerationUnit(pluginEntity, entityId, GenerationEnum.MESSAGE_BUNDLE);
	}

	protected void addDetailGenerationUnit(PluginEntityWrapper pluginEntity, String entityId) {
		addGenerationUnit(pluginEntity, entityId + "DI", GenerationEnum.DETAIL);
	}

	protected void addEntityGenerationUnit(PluginEntityWrapper pluginEntity, String entityId) {
		addGenerationUnit(pluginEntity, entityId.concat("MM"), GenerationEnum.ENTITY);
	}

	/**
	 * Adds the generation unit.
	 * 
	 * @param pluginEntity
	 *            the plugin entity
	 * @param treeId
	 *            the tree id
	 * @param generationType
	 *            the generation type
	 */
	protected void addGenerationUnit(PluginEntityType pluginEntity, String treeId, GenerationEnum generationEnum) {
		for (GenerationUnitType generationUnit : pluginEntity.getGenerationUnit())
			if (generationUnit.getType() == generationEnum)
				return;
		GenerationUnitType generationUnit = new GenerationUnitWrapper(generationEnum);
		String subPackage;
		if (GenerationEnum.MESSAGE_BUNDLE == generationEnum) {
			subPackage = ".";
			treeId = EngineTools.lowerCaseFirstLetter(treeId);
		} else {
			subPackage = getSubPackage(pluginEntity, generationEnum);
		}
		generationUnit.setAdiResourceURI(EngineTools.getAdiResourceURI(subPackage, treeId));
		pluginEntity.getGenerationUnit().add(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPluginEntityScenario#getSubPackage(org.adichatz.scenario.ScenarioResources,
	 * org.adichatz.generator.xjc.ModuleTypeEnum, org.adichatz.generator.xjc.PluginEntityType)
	 */
	@Override
	public String getSubPackage(PluginEntityType pluginEntity, GenerationEnum generationEnum) {
		if (GenerationEnum.MESSAGE_BUNDLE == generationEnum)
			return ".";
		return EngineTools.lowerCaseFirstLetter(((PluginEntityWrapper) pluginEntity).getEntityKeys()[1]);
	}

	public String getTreeId(PluginEntityType pluginEntity, GenerationEnum generationEnum) {
		String entityId = null;
		if (null != pluginEntity)
			entityId = ((PluginEntityWrapper) pluginEntity).getEntityId();
		switch (generationEnum) {
		case ENTITY:
			return entityId.concat("MM");
		case QUERY:
			return entityId.concat("QUERY");
		case MESSAGE_BUNDLE:
			return EngineTools.lowerCaseFirstLetter(entityId);
		case DETAIL:
			return entityId.concat("DI");
		case TABLE:
			return entityId.concat("TI");
		case EDITOR:
			return entityId.concat("EDITOR");
		default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
			break;
		}
		return null;
	}

	/**
	 * Gets keys for retrieving depending resources automatically generated for a PluginEntity.
	 * <ul>
	 * <li>ModuleTypeEnum</li>
	 * <li>treeId</li>
	 * <li>subPackage</li>
	 * </ul>
	 * 
	 * @param pluginEntity
	 *            the plugin entity
	 * @return the depending resources
	 */
	public List<ScenarioInput> getDependingResources(PluginEntityWrapper pluginEntity) {
		List<ScenarioInput> dependingResources = new ArrayList<ScenarioInput>();
		dependingResources.add(new ScenarioInput(scenarioResources, getTreeId(pluginEntity, GenerationEnum.ENTITY),
				getSubPackage(pluginEntity, GenerationEnum.ENTITY)));
		dependingResources.add(new ScenarioInput(scenarioResources, getTreeId(pluginEntity, GenerationEnum.QUERY),
				getSubPackage(pluginEntity, GenerationEnum.QUERY)));
		dependingResources.add(new ScenarioInput(scenarioResources, getTreeId(pluginEntity, GenerationEnum.DETAIL),
				getSubPackage(pluginEntity, GenerationEnum.DETAIL)));
		dependingResources.add(new ScenarioInput(scenarioResources, getTreeId(pluginEntity, GenerationEnum.TABLE),
				getSubPackage(pluginEntity, GenerationEnum.TABLE)));
		dependingResources.add(new ScenarioInput(scenarioResources, getTreeId(pluginEntity, GenerationEnum.EDITOR),
				getSubPackage(pluginEntity, GenerationEnum.EDITOR)));
		dependingResources.add(new ScenarioInput(scenarioResources, getTreeId(pluginEntity, GenerationEnum.MESSAGE_BUNDLE),
				getSubPackage(pluginEntity, GenerationEnum.MESSAGE_BUNDLE)));
		return dependingResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPluginEntityScenario#getDefaultGenerationUnit(org.adichatz.generator.xjc.GenerationEnum)
	 */
	@Override
	public GenerationUnitWrapper getDefaultGenerationUnit(GenerationEnum generationEnum) {
		GenerationUnitWrapper generationUnit = new GenerationUnitWrapper();
		generationUnit.setType(generationEnum);
		String scenarioClassName = scenarioResources.getScenarioTree().getScenarioClassName(generationEnum);
		if (null == scenarioClassName)
			switch (generationEnum) {
			case PLUGIN_ENTITY:
				scenarioClassName = PluginEntityScenario.class.getName();
				break;
			case ENTITY:
				scenarioClassName = EntityScenario.class.getName();
				break;
			case NAVIGATOR:
				scenarioClassName = GroupNavigatorScenario.class.getName();
				break;
			case MESSAGE_BUNDLE:
				scenarioClassName = MessageBundleScenario.class.getName();
				break;
			case EDITOR:
				scenarioClassName = EditorScenario.class.getName();
				break;
			case DETAIL:
				scenarioClassName = SectionDetailScenario.class.getName();
				break;
			case TABLE:
				scenarioClassName = TableScenario.class.getName();
				break;
			case QUERY:
				scenarioClassName = QueryScenario.class.getName();
				break;
			default:
				scenarioClassName = null;
			}
		generationUnit.setScenarioClassName(scenarioClassName);
		if (generationEnum.equals(GenerationEnum.NAVIGATOR))
			setNavigatorResourceURI(generationUnit);
		return generationUnit;
	}

	public void setNavigatorResourceURI(GenerationUnitWrapper generationUnit) {
		generationUnit.setAdiResourceURI(
				EngineTools.getAdiResourceURI(scenarioResources.getPluginName(), "groupNavigator", "GroupNavigatorContent"));
	}

	@Override
	public String getEntityURI(ScenarioResources scenarioResources, String entityId) {
		String subPackage = "model.".concat(EngineTools.lowerCaseFirstLetter(entityId));
		return "adi://".concat(scenarioResources.getPluginName()).concat("/").concat(subPackage).concat("/").concat(entityId)
				.concat("MM");
	}

	@Override
	public String getEntityIdFromBundleFile(ScenarioResources scenarioResources, IFile bundleFile) {
		String fileName = bundleFile.getName();
		String entityId = fileName.substring(0, fileName.length() - 11);
		return EngineTools.upperCaseFirstLetter(
				(entityId.endsWith("GENERATED")) ? entityId = entityId.substring(0, entityId.length() - 9) : entityId);
	}
}
