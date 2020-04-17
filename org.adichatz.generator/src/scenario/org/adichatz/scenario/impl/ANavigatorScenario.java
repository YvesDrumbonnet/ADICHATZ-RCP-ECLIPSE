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
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EmbeddedId;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.wrapper.AdichatzRcpConfigTreeWrapper;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.engine.xjc.MenuPathType;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.engine.xjc.NavigatorsType;
import org.adichatz.engine.xjc.RcpConfigurationType;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.generator.wrapper.ActionWrapper;
import org.adichatz.generator.wrapper.CustomizationsWrapper;
import org.adichatz.generator.wrapper.ItemWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ItemType;
import org.adichatz.generator.xjc.MenuType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PartImageTypeEnum;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.QueryContentProviderType;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.scenario.INavigatorScenario;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.internal.InternalPolicy;

// TODO: Auto-generated Javadoc
/**
 * The Class ANavigatorScenario.
 * 
 * @author Yves Drumbonnet
 */
public abstract class ANavigatorScenario extends ABundleScenario implements INavigatorScenario {

	/** The plugin entity scenario. */
	protected IPluginEntityScenario pluginEntityScenario;

	/** The plugin key. */
	protected String pluginKey;

	protected String navigatorName;

	/**
	 * Instantiates a new a navigator scenario.
	 * 
	 * @param generationUnit the generation unit
	 */
	protected ANavigatorScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.scenario.INavigatorScenario#createNavigatorBundle(org.adichatz.
	 * scenario.ScenarioInput, java.util.List)
	 */
	@Override
	@SuppressWarnings("restriction")
	public void createNavigatorBundle(List<PluginEntityType> pluginEntities) {
		try {
			File menuBundle = new File(scenarioInput.getScenarioResources().getPluginHome() + "/"
					+ EngineConstants.RESOURCE_MESSAGE_DIR + "/" + getNavigatorName() + "GENERATED.properties");
			menuBundle.getParentFile().mkdirs();
			bundleWriter = new FileWriter(menuBundle, false);
			writeTitle("Bundle for menu");

			bundleWriter.append("root.menu = Menu for " + scenarioInput.getScenarioResources().getPluginName() + "\n");
			bundleWriter.append(getId() + " = " + getLabel() + "\n");
			for (PluginEntityType pluginEntity : pluginEntities) {
				String entityId = ((PluginEntityWrapper) pluginEntity).getEntityId();
				// Create bundle items
				String lower = EngineTools.lowerCaseFirstLetter(entityId);
				bundleWriter.append(lower + "Menu = " + entityId + "\n");
				bundleWriter.append(lower + "CREATION = Create " + entityId + "\n");
				bundleWriter.append(lower + "QUERY = Query " + entityId + "\n");
				bundleWriter.append(lower + "CreationEditorTitle = " + entityId + " Creation\n");
				bundleWriter.append(lower + "CreationEditorToolTipText = " + entityId + " Creation\n");
				bundleWriter.append(lower + "QueryEditorTitle = Query for " + entityId + "\n");
				bundleWriter.append(lower + "QueryEditorToolTipText = Query for " + entityId + "\n");
				bundleWriter.append(lower + "ListDetailEditorTitle = List/Detail for " + entityId + "\n");
				bundleWriter.append(lower + "ListDetailEditorToolTipText = List/Detail for " + entityId + "\n");
			}
			bundleWriter.flush();
			bundleWriter.close();
			if (InternalPolicy.OSGI_AVAILABLE)
				scenarioInput.getScenarioResources().getAffectedFiles().add(getFile(getNavigatorName()));
		} catch (IOException e) {
			logError(e);
		}
	}

	/**
	 * Adds the item menu.
	 * 
	 * @param menu          the menu
	 * @param lowerEntityId the lower entity id
	 * @param pluginEntity  the plugin entity
	 * @param mode          the mode
	 * @return the item type
	 */
	protected ItemType addItemMenu(MenuType menu, String lowerEntityId, PluginEntityWrapper pluginEntity, PartImageTypeEnum mode) {
		ItemWrapper item;
		String paramType;
		switch (mode) {
		case CREATION_ITEM:
			item = addItem(lowerEntityId.concat("CREATION"), pluginEntity, "IMG_CREATE", mode);
			addCreationItem(item, lowerEntityId, pluginEntity);
			paramType = ParamMap.CREATION_ITEM;
			break;
		default: // QUERY
			item = addItem(lowerEntityId.concat("QUERY"), pluginEntity, "IMG_QUERY", mode);
			addQueryItem(item, lowerEntityId, pluginEntity);
			paramType = ParamMap.QUERY_ITEM;
		}
		if (null != pluginEntity.getParams())
			for (ParamType param : pluginEntity.getParams().getParam())
				if (paramType.equals(param.getType()))
					ScenarioUtil.addParam(item, param.getId(), param.getValue(), null);
		menu.getMenuOrItem().add(item);

		return item;
	}

	/**
	 * Adds the query item. . *
	 * 
	 * @param item          the item
	 * @param lowerEntityId the lower entity id
	 * @param pluginEntity  the plugin entity
	 */
	protected void addQueryItem(ItemType item, String lowerEntityId, PluginEntityWrapper pluginEntity) {
		addParam(item, ParamMap.MESSAGE_BUNDLE,
				"adi://" + scenarioInput.getScenarioResources().getPluginName() + "/./" + lowerEntityId);
		ParamsType params = addParams(item);
		QueryContentProviderType queryContentProvider = new QueryContentProviderType();
		queryContentProvider.setId(ParamMap.CONTENT_PROVIDER);
		String treeId = pluginEntityScenario.getTreeId(pluginEntity, GenerationEnum.QUERY);
		String subPackage = pluginEntityScenario.getSubPackage(pluginEntity, GenerationEnum.QUERY);
		queryContentProvider.setAdiResourceURI(EngineTools.getAdiResourceURI(pluginKey, subPackage, treeId));
		params.getParam().add(queryContentProvider);
		addParam(item, ParamMap.ADI_RESOURCE_URI, EngineTools.getAdiResourceURI(EngineConstants.JPA_BUNDLE, "query", "QueryForm"));
		treeId = pluginEntityScenario.getTreeId(pluginEntity, GenerationEnum.TABLE);
		subPackage = pluginEntityScenario.getSubPackage(pluginEntity, GenerationEnum.TABLE);
		addParam(item, ParamMap.TABLE_RESOURCE_URI,
				EngineTools.getAdiResourceURI(scenarioInput.getScenarioResources().getPluginName(), subPackage, treeId));
		addParam(item, ParamMap.DUPLICATE_EDITOR, "true");
		addSupplQueryItem(item, lowerEntityId, pluginEntity);
		invalidEditNewEntityAction(item, pluginEntity);
	}

	protected void invalidEditNewEntityAction(ItemType item, PluginEntityWrapper pluginEntity) {
		AEntityMetaModel<?> entityMM = scenarioInput.getPluginEntityTree().getEntityMM(pluginEntity.getEntityURI());
		Method getIdMethod = entityMM.getFieldMap().get(entityMM.getIdFieldName()).getGetMethod();
		if (null != getIdMethod.getAnnotation(EmbeddedId.class)) {
			CustomizationsWrapper customizations = getCustomizations(item);
			ActionWrapper action = new ActionWrapper();
			action.setId("tableCM:editNewEntityAction");
			action.setValid("false");
			customizations.getElements().add(action);
		}
	}

	protected CustomizationsWrapper getCustomizations(ItemType item) {
		CustomizationsWrapper customizations = (CustomizationsWrapper) item.getCustomizations();
		if (null == customizations) {
			customizations = new CustomizationsWrapper();
			item.setCustomizations(customizations);
		}
		return customizations;
	}

	protected void addSupplQueryItem(ItemType item, String lowerEntityId, PluginEntityWrapper pluginEntity) {
		addParam(item, ParamMap.TITLE, "#MSG(" + getNavigatorName() + "," + lowerEntityId + "QueryEditorTitle)");
		addParam(item, ParamMap.TOOL_TIP_TEXT, "#MSG(" + getNavigatorName() + "," + lowerEntityId + "QueryEditorToolTipText)");
	}

	/**
	 * Adds the item.
	 * 
	 * @param lowerEntityId the lower entity id
	 * @param pluginEntity  the plugin entity
	 * @param defaultIcon   the default icon
	 * @param mode          the mode
	 * @return the item type
	 */
	protected ItemWrapper addItem(String id, PluginEntityWrapper pluginEntity, String defaultIcon, PartImageTypeEnum mode) {
		ItemWrapper item = new ItemWrapper();
		item.setId(id);
		item.setLabel("#MSG(" + getNavigatorName() + "," + item.getId() + ")");

		pluginEntityScenario = scenarioInput.getScenarioResources().getPluginEntityScenario();
		pluginKey = pluginEntity.getEntityKeys()[0];
		String icon = pluginEntity.getIcon(mode);
		if (null == icon)
			icon = defaultIcon;
		item.setImage("#IMG(" + icon + ")");
		if (-1 == icon.indexOf('.'))
			icon = "platform:/plugin/" + EngineConstants.ENGINE_BUNDLE + EngineConstants.ICON_FILES_PATH + icon + ".png";
		addParam(item, ParamMap.ICON_URI, icon);
		return item;
	}

	/**
	 * Adds the creation item.
	 * 
	 * @param item          the item
	 * @param lowerEntityId the lower entity id
	 * @param pluginEntity  the plugin entity
	 * @return the item type
	 */
	protected ItemType addCreationItem(ItemType item, String lowerEntityId, PluginEntityWrapper pluginEntity) {
		addParam(item, ParamMap.TITLE, "#MSG(" + getNavigatorName() + "," + lowerEntityId + "CreationEditorTitle)");
		addParam(item, ParamMap.TOOL_TIP_TEXT, "#MSG(" + getNavigatorName() + "," + lowerEntityId + "CreationEditorToolTipText)");
		String treeId = pluginEntityScenario.getTreeId(pluginEntity, GenerationEnum.EDITOR);
		String subPackage = pluginEntityScenario.getSubPackage(pluginEntity, GenerationEnum.EDITOR);
		addParam(item, ParamMap.ADI_RESOURCE_URI,
				EngineTools.getAdiResourceURI(scenarioInput.getScenarioResources().getPluginName(), subPackage, treeId));
		addParam(item, ParamMap.CONTRIBUTION_URI, JPAUtil.ENTITY_EDITOR_CONTRIBUTION_URI);
		addParam(item, ParamMap.ENTITY_URI, pluginEntity.getEntityURI());
		if (addAuthorization(pluginEntity, PERSIST_ROLE))
			item.setValid("#AUTHORIZATION(" + pluginEntity.getEntityURI() + ",IEntityConstants.PERSIST)");
		addSupplCreationItem(item, lowerEntityId, pluginEntity);
		return item;
	}

	protected void addSupplCreationItem(ItemType item, String lowerEntityId, PluginEntityWrapper pluginEntity) {
	}

	protected NavigatorType getNewNavigator(String id, String label, String contributionURI, String adiResourceURI) {
		NavigatorType navigator = new NavigatorType();
		navigator.setId(id);
		navigator.setMessageBundleURI("adi://" + scenarioInput.getScenarioResources().getPluginName() + "/./" + getNavigatorName());
		navigator.setLabel(getId());
		navigator.setContributionURI(contributionURI);

		navigator.setIconURI("platform:/plugin/" + EngineConstants.ENGINE_E4_BUNDLE + "/resources/icons/IMG_NAVIGATOR.png");
		MenuPathType menuPath = new MenuPathType();
		menuPath.setId(id);
		menuPath.setAdiResourceURI(adiResourceURI);
		navigator.getMenuPath().add(menuPath);

		menuPath = new MenuPathType();
		navigator.getMenuPath().add(menuPath);
		menuPath.setId("jpaMenu");
		menuPath.setAdiResourceURI("adi://" + EngineConstants.JPA_BUNDLE + "/./JpaMenu");
		return navigator;

	}

	protected void addNavigatorInRcpConfig(Integer pos) {
		ScenarioResources scenarioResources = scenarioInput.getScenarioResources();
		AdichatzRcpConfigTree configTree = (AdichatzRcpConfigTree) scenarioResources.getPluginResources()
				.getConfigTree("AdichatzRcpConfig.xml", true);
		if (null == configTree)
			configTree = new AdichatzRcpConfigTreeWrapper();
		RcpConfigurationType rcpConfiguration = configTree.getRcpConfiguration();
		if (null == rcpConfiguration) {
			rcpConfiguration = new RcpConfigurationType();
			configTree.setRcpConfiguration(rcpConfiguration);
		}
		NavigatorsType navigators = rcpConfiguration.getNavigators();
		if (null == navigators) {
			navigators = new NavigatorsType();
			rcpConfiguration.setNavigators(navigators);
		}
		boolean addNavigator = true;
		NavigatorType navigator = getNewNavigator(addPluginName(scenarioInput.getAdiResourceURI()));
		for (NavigatorType navigatorType : navigators.getNavigator()) {
			if (navigatorType.getId().equals(navigator.getId())) {
				addNavigator = false;
				navigatorType.setContributionURI(navigator.getContributionURI());
				navigatorType.setIconURI(navigator.getIconURI());
				navigatorType.setLabel(navigator.getLabel());
				navigatorType.setMessageBundleURI(navigator.getMessageBundleURI());
				navigatorType.getMenuPath().clear();
				for (MenuPathType menuPath : navigator.getMenuPath()) {
					if (null != menuPath.getId() && navigator.getId().equals(menuPath.getId()))
						menuPath.setAdiResourceURI(scenarioInput.getAdiResourceURI());
					navigatorType.getMenuPath().add(menuPath);
				}
			}
		}
		if (addNavigator) {
			if (null != pos)
				navigators.getNavigator().add(pos, navigator);
			else
				navigators.getNavigator().add(navigator);
		}
		if (InternalPolicy.OSGI_AVAILABLE) {
			IFile configFile = scenarioResources.getXmlFolder().getFile("AdichatzRcpConfig.xml");
			ScenarioUtil.createXmlFile(configFile, configTree, scenarioResources.getGenerationScenario());
			scenarioInput.getScenarioResources().getAffectedFiles().add(configFile);
		} else {
			File configFile = new File(
					scenarioResources.getPluginHome() + "/" + EngineConstants.XML_FILES_PATH + "/AdichatzRcpConfig.xml");
			ScenarioUtil.createXmlFile(configFile, configTree);
		}
	}

	private String addPluginName(String adiResourceURI) {
		if (EngineTools.isEmpty(adiResourceURI))
			return null;
		else {
			String[] keys = EngineTools.getInstanceKeys(adiResourceURI);
			if (null == keys[0] || ".".equals(keys[0]))
				keys[0] = scenarioInput.getScenarioResources().getPluginName();
			return EngineTools.getAdiResourceURI(keys);
		}

	}

	protected String getNavigatorName() {
		if (null == navigatorName) {
			navigatorName = EngineTools
					.lowerCaseFirstLetter(GeneratorUtil.getJavaName(getId(), FieldCaseEnum.UPPER_CASE_FIRST_LETTER, false));
		}
		return navigatorName;
	}

	protected abstract String getId();

	protected abstract String getLabel();

	protected abstract NavigatorType getNewNavigator(String adiResourceURI);
}
