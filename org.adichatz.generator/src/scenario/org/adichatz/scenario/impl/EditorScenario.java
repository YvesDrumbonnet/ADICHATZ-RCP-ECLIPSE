/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
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
package org.adichatz.scenario.impl;

// *********************
// *** C A U T I O N ***
// *********************
// 
// this class is dynamically loaded in the application No reference will be found in the application
//
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.e4.resource.PartBindingService;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.ButtonWrapper;
import org.adichatz.generator.wrapper.CTabFolderWrapper;
import org.adichatz.generator.wrapper.CTabItemWrapper;
import org.adichatz.generator.wrapper.CompositeBagWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.FormPageWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.PartTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.SashFormWrapper;
import org.adichatz.generator.wrapper.TableColumnWrapper;
import org.adichatz.generator.wrapper.TabularWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.xjc.ConfigType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.EntitySetContentProviderType;
import org.adichatz.generator.xjc.EntitySetType;
import org.adichatz.generator.xjc.FieldContainerType;
import org.adichatz.generator.xjc.FormPageType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.ListenersType;
import org.adichatz.generator.xjc.NavigationPathType;
import org.adichatz.generator.xjc.NavigationPathsType;
import org.adichatz.generator.xjc.OneToManyType;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.ValidElementType;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.jpa.editor.EditorOutlinePage;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.scenario.IEditorScenario;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class EditScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class EditorScenario extends AScenario implements IEditorScenario {

	/** The Editor layout constraints. */
	public static String EDITOR_LC = "editorLayoutConstraints";

	/** The Editor column constraints. */
	public static String EDITOR_CC = "editorColumnConstraints";

	/** The Editor row constraints. */
	public static String EDITOR_RC = "editorRowConstraints";

	/** The lower entity. */
	protected String lowerEntity;

	/** The form editor tree. */
	protected PartTreeWrapper partTree;

	/** The config. */
	protected ConfigType config;

	/** The navigation paths. */
	protected NavigationPathsType navigationPaths;

	/** The master valid clause. */
	protected String masterValidClause;

	/** The generation scenario. */
	protected GenerationScenarioWrapper generationScenario;

	/** The c tab folder wrapper. */
	protected CTabFolderWrapper cTabFolderWrapper;

	/** The sash form. */
	protected SashFormWrapper sashForm;

	/** The composite bag. */
	protected CompositeBagWrapper compositeBag;

	protected int tabItemNumber;

	protected List<EntitySetType> entitySets;

	protected PluginEntityWrapper pluginEntity;

	protected EntityTreeWrapper entityTree;

	protected String firstPageId = "Page1";

	protected FormPageWrapper firstPage;

	/**
	 * Instantiates a new editor scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public EditorScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IEditorScenario#createEditorScenario(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createEditorScenario(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		generationScenario = (GenerationScenarioWrapper) scenarioInput.getScenarioResources().getScenarioTree()
				.getGenerationScenario();
		if (scenarioInput.isGenerateXml()) {
			partTree = new PartTreeWrapper();
			LogBroker.logTrace(getFromGeneratorBundle("editScenario", scenarioInput.getPluginEntity().getEntityURI()));

			Class<?> entityClass = scenarioInput.getPluginEntityWrapper().getBeanClass();
			lowerEntity = EngineTools.lowerCaseFirstLetter(entityClass.getSimpleName());
			pluginEntity = generationScenario.getPluginEntity(scenarioInput.getPluginEntity().getEntityURI());
			entityTree = getEntityTree();
			entitySets = new ArrayList<EntitySetType>();
			entitySets.addAll(entityTree.getOneToMany());
			entitySets.addAll(entityTree.getManyToMany());
			setTabeItemNumber(scenarioInput, entitySets);

			initPartTree();

			/*
			 * Add pages
			 */

			// For master entity edit
			addFirstPage();

			if (0 != tabItemNumber) // add dependencies
				addDependenciesPage(entityClass.getSimpleName() + " header");
			// add one to one
			addOneToOnePages(scenarioInput);

			if (1 < navigationPaths.getNavigationPath().size())
				config.setNavigationPaths(navigationPaths);
			if (null == config.getNavigationPaths() && null == config.getCustomizations() && null == config.getResourceBundles()
					&& null == config.getParams())
				partTree.setConfig(null);
		} else
			partTree = (PartTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		createXmlAndPrepare(scenarioInput.setXmlElement(partTree));
	}

	protected void initPartTree() {
		boolean legacyVersion = ScenarioUtil.isLegacyVersion(scenarioInput);
		if (!legacyVersion)
			partTree.setOutlinePageClassName(EditorOutlinePage.class.getName());

		config = new ConfigType();

		partTree.setImage("#PARAM(" + ParamMap.IMAGE + ")");
		partTree.setTitle("#PARAM(" + ParamMap.TITLE + ")");
		partTree.setToolTipText("#PARAM(" + ParamMap.TOOL_TIP_TEXT + ")");

		partTree.setConfig(config);
		partTree.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());
		partTree.setBindingServiceClassName(
				legacyVersion ? GeneratorConstants.EDITOR_BINDING_SERVICE : PartBindingService.class.getName());

		navigationPaths = new NavigationPathsType();
		NavigationPathType navigationPath = new NavigationPathType();
		navigationPath.setName(bundle(lowerEntity, "editFormTitle"));
		navigationPath.setPath(firstPageId);
		navigationPaths.getNavigationPath().add(navigationPath);
	}

	protected FormPageWrapper addFirstPage() {
		String title = bundle(lowerEntity, "editFormTitle");
		firstPage = addPage(new FormPageWrapper(), firstPageId, title, "#IMG(IMG_DETAIL)", null);
		firstPage.setFormText(title);
		firstPage.setLayout(addLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		addMasterDetailInclude(firstPage);
		return firstPage;
	}

	protected void addMasterDetailInclude(ICollectionWrapper<?> collectionWrapper) {
		String includeId = EngineTools.lowerCaseFirstLetter(scenarioInput.getPluginEntity().getEntityId() + "Detail");
		addDetailInclude(collectionWrapper, pluginEntity, includeId, null, "DETAIL");

		if (addAuthorization(scenarioInput.getPluginEntityWrapper(), REMOVE_ROLE)) {
			ValidElementType element = addCustomElement(config,
					includeId + EngineConstants.INCLUDE_SEPARATOR + "deleteEntityAndClose", new ButtonWrapper());
			element.setValid("#AUTHORIZATION(" + scenarioInput.getPluginEntity().getEntityURI() + ",IEntityConstants.REMOVE)");
		}
	}

	protected void addDependenciesPage(String containerText) {
		AEntityMetaModel<?> entityMM = scenarioInput.getPluginEntity().getEntityMetaModel();
		FormPageType oneToManyFPW = createMasterDetailPage(entityMM, containerText, 1 != tabItemNumber);

		String path = null;
		if (1 != tabItemNumber) {
			setDependenciesFormText(entityMM, oneToManyFPW, "dependenciesFormText");
			path = new StringBuffer(oneToManyFPW.getId()).append(", ").append(cTabFolderWrapper.getId()).append(", ").toString();
		}
		boolean removeAuthorizationClause = false; // Remove Authorization if at least one entitySet has no AuthorisationClause; 
		for (EntitySetType entitySet : entitySets) {
			if (null == generationScenario.getPluginEntity(entitySet.getEntityURI()))
				// Skip field when reference do not belongs to pluginResources scope
				continue;

			FieldContainerType parentWrapper;

			NavigationPathType tablePath = new NavigationPathType();
			String dependencyText = bundle(lowerEntity, entitySet.getId());
			tablePath.setName(dependencyText);
			navigationPaths.getNavigationPath().add(tablePath);
			if (1 == tabItemNumber) {
				parentWrapper = sashForm;
				tablePath.setPath(oneToManyFPW.getId());
				setDependenciesFormText(entityMM, oneToManyFPW, "oneDependencyFormText", dependencyText);
			} else {
				CTabItemWrapper cTabItemWrapper = new CTabItemWrapper();
				cTabItemWrapper.setText(dependencyText);
				cTabItemWrapper.setId(entitySet.getId() + "Item");
				cTabItemWrapper.setEntityURI(entitySet.getEntityURI());
				cTabFolderWrapper.getCTabItem().add(cTabItemWrapper);
				parentWrapper = cTabItemWrapper;
				tablePath.setPath(path.concat(cTabItemWrapper.getId()));
			}
			PluginEntityWrapper childPluginEntity = generationScenario.getPluginEntity(entitySet.getEntityURI());
			String detailId = entitySet.getId() + "DIPart";
			addDependencyPage(parentWrapper, entitySet, childPluginEntity, detailId);

			String mappedBy, toolBarType;
			if (entitySet instanceof OneToManyType) {
				mappedBy = entitySet.getMappedBy();
				toolBarType = "CHILD";
			} else {
				mappedBy = null;
				toolBarType = "RELATIONSHIP";
			}
			addDetailInclude(compositeBag, childPluginEntity, detailId, mappedBy, toolBarType);
		}
		oneToManyFPW.setValid(removeAuthorizationClause ? masterValidClause : null);
		sashForm.getElements().add(compositeBag);
	}

	protected boolean addDependencyPage(FieldContainerType parentWrapper, EntitySetType entitySet,
			PluginEntityWrapper childPluginEntity, String detailId) {
		boolean addAuthorisationClause = false;
		if (addAuthorization(childPluginEntity, RETRIEVE_ROLE)) {
			String validClause = "#AUTHORIZATION(" + entitySet.getEntityURI() + ",IEntityConstants.RETRIEVE)";
			parentWrapper.setValid(validClause);
			masterValidClause = null == masterValidClause ? validClause : masterValidClause + " && " + validClause;
			addAuthorisationClause = true;
		}
		String treeId = scenarioInput.getScenarioResources().getPluginEntityScenario().getTreeId(childPluginEntity,
				GenerationEnum.TABLE);
		String subPackage = scenarioInput.getScenarioResources().getPluginEntityScenario().getSubPackage(childPluginEntity,
				GenerationEnum.TABLE);

		String includeTSIId = entitySet.getId() + "TSI";
		addInclude((ICollectionWrapper<?>) parentWrapper, includeTSIId, treeId, subPackage);
		ScenarioResources childSR = ScenarioResources.getInstance(childPluginEntity.getEntityKeys()[0],
				scenarioInput.getScenarioResources().getPluginName());
		addTableWrapperCustomization(entitySet, detailId, includeTSIId, childPluginEntity, childSR);
		return addAuthorisationClause;
	}

	protected TabularWrapper addTableWrapperCustomization(EntitySetType entitySet, String detailId, String includeTSIId) {
		addCustomization(config, includeTSIId + EngineConstants.INCLUDE_SEPARATOR + entitySet.getMappedBy() + "TC",
				new TableColumnWrapper(), CUSTOMIZATION_TYPE.invalidate);

		TabularWrapper tabularWrapper = new TabularWrapper();
		tabularWrapper.setRefreshAtStart("true");
		tabularWrapper.setTableRendererKey("Binding");
		addCustomElement(config, includeTSIId + EngineConstants.INCLUDE_SEPARATOR + "table", tabularWrapper);
		ListenersType listeners = new ListenersType();
		ListenerType listener = new ListenerType();
		listener.setId(includeTSIId + "DCLsnr");
		listener.setListenerTypes(ListenerTypeEnum.DOUBLE_CLICK.name());
		StringBuffer codeSB = new StringBuffer();
		codeSB.append(JPAUtil.class.getName()).append(".openForm(getFromRegister(\"").append(includeTSIId)
				.append(EngineConstants.INCLUDE_SEPARATOR).append("table\"));");
		listener.setCode(codeSB.toString());
		listeners.getListener().add(listener);

		listener = new ListenerType();
		listener.setId(includeTSIId + "SCLsnr");
		listener.setListenerTypes(ListenerTypeEnum.SELECTION_CHANGED.name());
		listener.setCode("#CONTROLLER(dependenciesCompositeBag).showChildController(#CONTROLLER(), #CONTROLLER(" + detailId
				+ ":detailContainer));");
		listeners.getListener().add(listener);
		tabularWrapper.setListeners(listeners);

		return tabularWrapper;
	}

	protected void addTableWrapperCustomization(EntitySetType entitySet, String detailId, String includeTSIId,
			PluginEntityWrapper childPluginEntity, ScenarioResources childSR) {
		TabularWrapper tabularWrapper = addTableWrapperCustomization(entitySet, detailId, includeTSIId);
		// Content provider
		EntitySetContentProviderType entitySetCP = new EntitySetContentProviderType();
		entitySetCP.setId(entitySet.getId() + "CP");
		entitySetCP.setFieldName(entitySet.getId());
		IPluginEntityScenario pluginEntityScenario = scenarioInput.getScenarioResources().getPluginEntityScenario();
		entitySetCP.setAdiResourceURI(EngineTools.getAdiResourceURI(pluginEntity.getEntityKeys()[0],
				pluginEntityScenario.getSubPackage(childPluginEntity, GenerationEnum.QUERY),
				pluginEntityScenario.getTreeId(childPluginEntity, GenerationEnum.QUERY)));
		tabularWrapper.setContentProvider(entitySetCP);

		// Context menu
		IncludeWrapper include = new IncludeWrapper();
		include.setId(entitySet.getId() + "CM");
		String contextMenuURI = EngineTools.getAdiResourceURI(EngineConstants.JPA_BUNDLE, "common.contextMenu",
				entitySet instanceof OneToManyType ? "ChildCM" : "RelationshipCM");
		include.setAdiResourceURI(contextMenuURI);
		tabularWrapper.getInclude().add(include);
	}

	protected void setTabeItemNumber(ScenarioInput scenarioInput, List<EntitySetType> entitySets) {
		tabItemNumber = 0;
		for (EntitySetType entitySet : entitySets) {
			if (null != scenarioInput.getScenarioResources().getPluginResources().getPluginEntity(entitySet.getEntityURI())) {
				tabItemNumber++;
			}
		}
	}

	protected void addOneToOnePages(ScenarioInput scenarioInput) {
		NavigationPathType navigationPath;
		for (OneToOneType oneToOne : entityTree.getOneToOne()) {
			PluginEntityWrapper otoPluginEntity = generationScenario.getPluginEntity(oneToOne.getEntityURI());
			if (null != otoPluginEntity) {
				FormPageWrapper otoPage = addPage(new FormPageWrapper(), oneToOne.getId() + "OTO",
						bundle(lowerEntity, oneToOne.getId()), null, null);
				AEntityMetaModel<?> entityMM = scenarioInput.getPluginEntity().getEntityMetaModel();
				otoPage.setFormText(bundle(lowerEntity, oneToOne.getId() + "OTOFormText", "getFormText()"));
				otoPage.setLayout(addLayout("wrap,  ins 0", "grow,fill", "grow,fill"));
				otoPage.setValidStatus("IEntityConstants.RETRIEVE, IEntityConstants.MERGE");

				String otoId = EngineTools.lowerCaseFirstLetter(oneToOne.getId()) + "Detail";

				IncludeWrapper include = addDetailInclude(otoPage, otoPluginEntity, otoId, null, "ONETOONE");
				addParam(include, ParamMap.PARENT_ENTITY, "#PARAM(" + ParamMap.ENTITY + ")");

				String entityMethodName = "get" + EngineTools.upperCaseFirstLetter(oneToOne.getId()) + "Entity";
				otoPage.setEntity(entityMethodName + "()");
				otoPage.setEntityURI(oneToOne.getEntityURI());

				StringBuffer additionalMethodSB = new StringBuffer();
				additionalMethodSB.append("import ").append(IEntity.class.getName()).append(";\n");
				additionalMethodSB.append("import ").append(entityMM.getBeanClass().getName()).append(";\n");
				// additionalMethodSB.append("import ").append(AdiPMException.class.getName()).append(";\n");
				additionalMethodSB.append("import ").append(JPADataAccess.class.getName()).append(";\n");
				additionalMethodSB.append("private IEntity<?> " + entityMethodName + "() {\n");
				additionalMethodSB.append("    IEntity<?> entity = coreController.getParentController().getEntity();\n");

				additionalMethodSB.append("    JPADataAccess dataAccess = (JPADataAccess) entity.getEntityMM().getDataAccess();\n");
				additionalMethodSB.append("    Object bean = dataAccess.findProxyEntity(\"" + oneToOne.getEntityURI()
						+ "\", entity.getBeanId(), true).getBean();\n");
				additionalMethodSB.append("    if (null == bean)\n");
				additionalMethodSB.append("        return null;\n");
				additionalMethodSB.append("    return entity.getEntityMM().getDataAccess().getDataCache().fetchEntity(bean);\n");
				additionalMethodSB.append("}\n");

				additionalMethodSB.append("private String getFormText() {\n");
				additionalMethodSB.append("    IEntity<?> parentEntity = coreController.getParentController().getEntity();\n");
				additionalMethodSB.append("    String id = parentEntity.getEntityMM().getIdString(parentEntity.getBean());\n");
				for (Field childField : entityMM.getBeanClass().getDeclaredFields())
					if (!Modifier.isStatic(childField.getModifiers())) {
						Method methodField = FieldTools.getGetMethod(entityMM.getBeanClass(), childField.getName(), false);
						if (String.class == methodField.getReturnType()) {
							String entityId = entityMM.getEntityId();
							additionalMethodSB.append("    String title = ((" + entityId + ") parentEntity.getBean())."
									+ methodField.getName() + "();\n");
							additionalMethodSB.append("    if (null != title)\n");
							additionalMethodSB.append("        id = id.concat(\" - \").concat(title);\n");
							break;
						}
					}
				additionalMethodSB.append("    return id;\n");
				additionalMethodSB.append("}\n");

				otoPage.setAdditionalCode(additionalMethodSB.toString());

				navigationPath = new NavigationPathType();
				navigationPath.setName(bundle(lowerEntity, oneToOne.getId()));
				navigationPath.setPath(otoPage.getId());
				navigationPaths.getNavigationPath().add(navigationPath);
			}
		}
	}

	/**
	 * Adds the detail include.
	 * 
	 * @param collectionWrapper
	 *            the collection wrapper
	 * @param pluginEntity
	 *            the plugin entity
	 * @param id
	 *            the id
	 * @param mappedBy
	 *            the mapped by
	 * @param toolBar
	 *            the tool bar
	 * @return the include wrapper
	 */
	@SuppressWarnings("rawtypes")
	protected IncludeWrapper addDetailInclude(ICollectionWrapper collectionWrapper, PluginEntityWrapper pluginEntity, String id,
			String mappedBy, String toolBarType) {
		IPluginEntityScenario pluginEntityScenario = scenarioInput.getScenarioResources().getPluginEntityScenario();
		String treeId = pluginEntityScenario.getTreeId(pluginEntity, GenerationEnum.DETAIL);
		String subPackage = pluginEntityScenario.getSubPackage(pluginEntity, GenerationEnum.DETAIL);
		IncludeWrapper include = addInclude(collectionWrapper, id, treeId, subPackage);
		addParam(include, ParamMap.TOOL_BAR_TYPE, toolBarType, true);
		if (!EngineTools.isEmpty(mappedBy))
			addCustomization(config, id + EngineConstants.INCLUDE_SEPARATOR + mappedBy, new ControlFieldType(),
					CUSTOMIZATION_TYPE.invalidate);
		return include;
	}

	/**
	 * Creates the master detail page.
	 * 
	 * @param entityMM
	 *            the entity meta model
	 * @param containerText
	 *            the container text
	 * @param multiDependencies
	 *            the multi dependencies
	 * @return the i form page wrapper
	 */
	protected FormPageType createMasterDetailPage(AEntityMetaModel<?> entityMM, String containerText, boolean multiDependencies) {
		String pageId = "Dependencies";
		FormPageWrapper dependenciesFP = addPage(new FormPageWrapper(), pageId, bundle(null, "dependenciesFormTitle"),
				"#IMG(IMG_DEPENDENCIES)", null);

		IncludeWrapper toolBar = new IncludeWrapper();
		toolBar.setId("masterDetailTBM");
		toolBar.setAdiResourceURI("adi://org.adichatz.jpa/common.toolBar/MasterDetailTBM");
		dependenciesFP.getElements().add(toolBar);

		dependenciesFP.setLayout(addLayout(scenarioMap.get(EDITOR_LC), scenarioMap.get(EDITOR_CC), scenarioMap.get(EDITOR_RC)));
		dependenciesFP.setValidStatus("IEntityConstants.RETRIEVE, IEntityConstants.MERGE");

		sashForm = new SashFormWrapper();
		sashForm.setId("DependenciesSashForm");
		sashForm.setWeights("1,1");
		if (multiDependencies) {
			cTabFolderWrapper = new CTabFolderWrapper();
			cTabFolderWrapper.setId("DependenciesTabFolder");
			cTabFolderWrapper.setDelayed("true");
			sashForm.getElements().add(cTabFolderWrapper);
			cTabFolderWrapper.setSelection("0"); // Add listener to CTabFolder
			ListenersType listeners = new ListenersType();
			ListenerType listener = new ListenerType();
			listener.setId(cTabFolderWrapper.getId() + "WSLsnr");
			listener.setListenerTypes(ListenerTypeEnum.WIDGET_SELECTED.name());
			listener.setCode("#CONTROLLER(dependenciesCompositeBag).showChildController(getSelectedController());");
			listeners.getListener().add(listener);
			cTabFolderWrapper.setListeners(listeners);

		}
		dependenciesFP.getElements().add(sashForm);

		compositeBag = new CompositeBagWrapper();
		compositeBag.setId("dependenciesCompositeBag");
		return dependenciesFP;
	}

	/**
	 * Sets the dependencies form text.
	 * 
	 * @param entityMM
	 *            the entity mm
	 * @param dependenciesFP
	 *            the dependencies fp
	 * @param formTextMembers
	 *            the form text members
	 */
	protected void setDependenciesFormText(AEntityMetaModel<?> entityMM, FormPageType dependenciesFP, String... formTextMembers) {
		String entityId = entityMM.getPluginEntity().getEntityId();
		String idString = "#ENTITY_MM(" + entityMM.getPluginEntity().getEntityURI() + ").getIdString(#BEAN())";

		String wording = null;
		for (Field childField : entityMM.getBeanClass().getDeclaredFields())
			if (!Modifier.isStatic(childField.getModifiers())) {
				Method methodField = FieldTools.getGetMethod(entityMM.getBeanClass(), childField.getName(), false);
				if (String.class == methodField.getReturnType()) {
					wording = childField.getName();
					break;
				}
			}

		String formText;
		if (1 == formTextMembers.length)
			formText = bundle(null, formTextMembers[0], "\"" + entityId + "\"", idString);
		else
			formText = bundle(null, formTextMembers[0], formTextMembers[1], "\"" + entityId + "\"", idString);
		if (null != wording)
			formText = formText + ".concat(\" - \").concat(" + "#FV(" + wording + ", \"\"))";
		dependenciesFP.setFormText(formText);
	}

	/**
	 * Bundle.
	 * 
	 * @param lowerEntity
	 *            the lower entity
	 * @param member
	 *            the member
	 * @param variables
	 *            the variables
	 * 
	 * @return the string
	 */
	protected String bundle(String lowerEntity, String member, String... variables) {
		StringBuffer bundle = new StringBuffer("#MSG(");
		if (null != lowerEntity)
			bundle.append(lowerEntity).append(", ");
		else
			bundle.append("adi://org.adichatz.engine/./adichatzEngine, ");
		bundle.append(member);
		for (int i = 0; i < variables.length; i++)
			bundle.append(", ").append(variables[i]);
		return bundle.append(")").toString();
	}

	/**
	 * Adds the page.
	 * 
	 * @param formPage
	 *            the form page
	 * @param pageId
	 *            the page id
	 * @param title
	 *            the title
	 * @param image
	 *            the image
	 * @param dataBinding
	 *            the data binding
	 * @return the form page wrapper
	 */
	protected FormPageWrapper addPage(FormPageWrapper formPage, String pageId, String title, String image, Boolean dataBinding) {
		formPage.setId(pageId);
		formPage.setTitle(title);
		formPage.setImage(image);
		partTree.getElements().add(formPage);

		return formPage;
	}
}
