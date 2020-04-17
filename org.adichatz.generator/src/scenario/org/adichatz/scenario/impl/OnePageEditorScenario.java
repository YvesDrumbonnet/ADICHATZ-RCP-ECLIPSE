package org.adichatz.scenario.impl;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.e4.resource.PartBindingService;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.wrapper.CTabFolderWrapper;
import org.adichatz.generator.wrapper.CTabItemWrapper;
import org.adichatz.generator.wrapper.CompositeBagWrapper;
import org.adichatz.generator.wrapper.CompositeWrapper;
import org.adichatz.generator.wrapper.FormPageWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.SashFormWrapper;
import org.adichatz.generator.wrapper.ScrolledCompositeWrapper;
import org.adichatz.generator.wrapper.TabularWrapper;
import org.adichatz.generator.xjc.ClientCanvasType;
import org.adichatz.generator.xjc.ConfigType;
import org.adichatz.generator.xjc.EntitySetType;
import org.adichatz.generator.xjc.FieldContainerType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.ListenersType;
import org.adichatz.generator.xjc.NavigationPathType;
import org.adichatz.generator.xjc.NavigationPathsType;
import org.adichatz.generator.xjc.OneToManyType;
import org.adichatz.jpa.editor.EditorOutlinePage;

public class OnePageEditorScenario extends EditorScenario {

	public OnePageEditorScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	@Override
	protected void initPartTree() {
		partTree.setOutlinePageClassName(EditorOutlinePage.class.getName());

		config = new ConfigType();

		partTree.setImage("#PARAM(" + ParamMap.IMAGE + ")");
		partTree.setTitle("#PARAM(" + ParamMap.TITLE + ")");
		partTree.setToolTipText("#PARAM(" + ParamMap.TOOL_TIP_TEXT + ")");

		partTree.setConfig(config);
		partTree.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());
		partTree.setBindingServiceClassName(PartBindingService.class.getName());

		navigationPaths = new NavigationPathsType();
		NavigationPathType navigationPath = new NavigationPathType();
		navigationPath.setName(bundle(lowerEntity, "editFormTitle"));
		navigationPath.setPath(firstPageId);
		navigationPaths.getNavigationPath().add(navigationPath);
	}

	@Override
	protected FormPageWrapper addFirstPage() {
		String title = bundle(lowerEntity, "editFormText");
		firstPage = addPage(new FormPageWrapper(), firstPageId, title, "#IMG(adi://org.adichatz.engine/./IMG_DETAIL.png)", null);
		firstPage.setFormText(title);
		if (0 == tabItemNumber)
			addMasterDetailInclude(firstPage);
		return firstPage;
	}

	@Override
	protected void addDependenciesPage(String headerSectionText) {
		String path = null;
		boolean first = true;
		addInclude(firstPage, "masterDetailTBM", "adi://org.adichatz.jpa/common.toolBar/OnePageMasterDetailTBM");

		SashFormWrapper firstSashForm = new SashFormWrapper();
		firstSashForm.setId("firstSashForm");
		firstSashForm.setWeights("3,2");
		firstSashForm.setLayout(addLayout("wrap 2, ins 0", "[grow,fill][grow,fill]", "grow,fill"));
		firstPage.getElements().add(firstSashForm);

		SashFormWrapper secondSashForm = new SashFormWrapper();
		secondSashForm.setId("secondSashForm");
		secondSashForm.setWeights("1,1");
		secondSashForm.setOrientation("SWT.HORIZONTAL");
		firstSashForm.getElements().add(secondSashForm);

		ScrolledCompositeWrapper detailComposite = new ScrolledCompositeWrapper();
		detailComposite.setId("detailComposite");
		secondSashForm.getElements().add(detailComposite);
		addMasterDetailInclude(detailComposite);
		detailComposite.setLayout(addLayout("wrap 1, ins 0", "[grow,fill]", "grow,fill"));

		compositeBag = new CompositeBagWrapper();
		compositeBag.setId("dependenciesCompositeBag");
		secondSashForm.getElements().add(compositeBag);

		CompositeWrapper tableComposite = new CompositeWrapper();
		tableComposite.setId("tableComposite");
		firstSashForm.getElements().add(tableComposite);
		tableComposite.setLayout(addLayout("wrap 1, ins 0", "[grow,fill]", "grow,fill"));
		FieldContainerType parentWrapper;

		StringBuffer tabularIds = new StringBuffer();
		for (EntitySetType entitySet : entitySets) {
			if (1 == tabItemNumber) {
				parentWrapper = tableComposite;
			} else {
				if (first) {
					first = false;
					cTabFolderWrapper = new CTabFolderWrapper();
					cTabFolderWrapper.setId("DependenciesTabFolder");
					cTabFolderWrapper.setDelayed("true");
					tableComposite.getElements().add(cTabFolderWrapper);
					cTabFolderWrapper.setSelection("0"); // Add listener to CTabFolder
					ListenersType listeners = new ListenersType();
					ListenerType listener = new ListenerType();
					listener.setId(cTabFolderWrapper.getId() + "WSLsnr");
					listener.setListenerTypes(ListenerTypeEnum.WIDGET_SELECTED.name());
					listener.setCode("#CONTROLLER(dependenciesCompositeBag).showChildController(getSelectedController());");
					listeners.getListener().add(listener);
					cTabFolderWrapper.setListeners(listeners);

					path = new StringBuffer(firstPageId).append(", ").append(cTabFolderWrapper.getId()).append(", ").toString();
				}
				String dependencyText = bundle(lowerEntity, entitySet.getId());

				NavigationPathType tablePath = new NavigationPathType();
				tablePath.setName(dependencyText);

				CTabItemWrapper cTabItemWrapper = new CTabItemWrapper();
				cTabItemWrapper.setText(dependencyText);
				cTabItemWrapper.setId(entitySet.getId() + "Item");
				cTabItemWrapper.setEntityURI(entitySet.getEntityURI());
				cTabFolderWrapper.getCTabItem().add(cTabItemWrapper);
				parentWrapper = cTabItemWrapper;
				tablePath.setPath(path.concat(cTabItemWrapper.getId()));
				navigationPaths.getNavigationPath().add(tablePath);
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
			tabularIds.append(", \"")
					.append(entitySet.getId().concat("TSI").concat(EngineConstants.INCLUDE_SEPARATOR).concat("table\""));
		}
		if (0 != tabularIds.length()) {
			ListenersType listeners = new ListenersType();
			detailComposite.setListeners(listeners);
			ListenerType listener = new ListenerType();
			listeners.getListener().add(listener);
			listener.setListenerTypes(ListenerTypeEnum.CHANGE_STATUS.name());
			listener.setId("postPersist#tabulars");
			String code = "import org.adichatz.jpa.editor.EntityEditorPart;\n" //
					+ "import org.adichatz.common.ejb.util.IEntityConstants;\n"
					+ "if (IEntityConstants.PERSIST == event.getOldStatus())\n"
					+ "    ((EntityEditorPart) genCode.getContext().getRootCore().getController()).postPersist("
					+ tabularIds.substring(2) + ");";
			listener.setCode(code);
		}
	}

	protected void addChangeStatusListener() {
	}

	@Override
	protected TabularWrapper addTableWrapperCustomization(EntitySetType entitySet, String detailId, String includeTSIId) {
		TabularWrapper tabularWrapper = super.addTableWrapperCustomization(entitySet, detailId, includeTSIId);
		ClientCanvasType containerWrapper = new ClientCanvasType();
		containerWrapper.setLayout(addLayout("wrap 2", "[fill, align right]10[fill,grow]", null));
		addCustomElement(config, detailId + EngineConstants.INCLUDE_SEPARATOR + "detailContainer", containerWrapper);
		return tabularWrapper;
	}
}
