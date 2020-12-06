/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 12:30:04 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.query;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.CompositeBagController;
import org.adichatz.engine.controller.collection.IncludeController;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.PartCore;
import org.adichatz.engine.e4.resource.PageManager;
import org.adichatz.engine.e4.resource.PartBindingService;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.jpa.gencode.common.contextMenu.QueryFormCM;
import org.adichatz.jpa.query.ListDetailOutlinePage;
import org.adichatz.jpa.tabular.JPAControllerPreferenceManager;
import org.adichatz.jpa.wrapper.ControllerPreferenceWrapper;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

@AdiResourceURI(URI="adi://org.adichatz.jpa/query/ListDetailQueryForm")
public class ListDetailQueryForm extends PartCore {
	// the IncludeController tableCMINCL.
	private IncludeController tableCMINCL;
	/**
	 * Instantiates a new ListDetailQueryForm.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param partController
	 *            the part controller
	 * @param paramMap
	 *            the param map
	 */
	public ListDetailQueryForm(AdiPluginResources pluginResources, IContainerController partController, ParamMap paramMap) {
		super(pluginResources, "ListDetailQueryForm", "query", paramMap);
		this.coreController = partController;
		
		
		addCustomization("tableInclude:table", new AControlListener("Customization#1", IEventType.AFTER_CREATE_CONTROL) {
			@Override
			public void handleEvent(AdiEvent event) {
				if (controller.isValid()) {
					ATabularController aTabularController$$1 = (ATabularController) controller;
					// Creates control for IncludeController tableCMINCL
					String tableCMIK = "adi://org.adichatz.jpa/common.contextMenu/QueryFormCM";
					if (!EngineTools.isEmpty(tableCMIK)) {
						final String[] tableCMKeys = EngineTools.getInstanceKeys(tableCMIK);
						tableCMINCL = new IncludeController("tableCM", aTabularController$$1, new EntityManagerCore(getContext())) {
							@Override
							public void createContents() {
								// loads and instantiates the class for 'tableCMINCL' include wrapper.
								if (null != EngineConstants.GENERATOR) {
									pluginResources = null == tableCMKeys[0] ? getPluginResources() : AdichatzApplication.getPluginResources(tableCMKeys[0]);
									instantiateIncludeClass(pluginResources, tableCMKeys[2], tableCMKeys[1], new Class[] {ParamMap.class, IContainerController.class, String.class, AdiContext.class}, new Object[] {getParamMap(), this, "tableCM", context});
								} else
									new QueryFormCM(getParamMap(), this, "tableCM", context).createContents();
							}
						};
					}
				}
			}
		});
		addCustomization("tableInclude:table", new AControlListener("Customization#2", IEventType.BEFORE_END_LIFE_CYCLE) {
			@Override
			public void handleEvent(AdiEvent event) {
				if (controller.isValid()) {
					((ATabularController) controller).getControllerPreferenceManager().setTableRendererKey(getTableRendererKey((ATabularController<?>) controller));
				}
			}
		});
		addCustomization("tableInclude:table", new AControlListener("Customization#3", IEventType.AFTER_END_LIFE_CYCLE) {
			@Override
			public void handleEvent(AdiEvent event) {
				if (controller.isValid()) {
					IDoubleClickListener tableInclude_tableLSTN$0 = new IDoubleClickListener() {
						@Override
						public void doubleClick(DoubleClickEvent event) {
							org.adichatz.jpa.extra.JPAUtil.openForm(getFromRegister("tableInclude:table"));
						}
					};
					((ATabularController) controller).getViewer().addDoubleClickListener(tableInclude_tableLSTN$0);
					ISelectionChangedListener tableInclude_tableLSTN$1 = new ISelectionChangedListener() {
						@Override
						public void selectionChanged(SelectionChangedEvent event) {
							((CompositeBagController) getFromRegister("DependenciesCompositeBag")).showChildController(((ATabularController) controller), ((AEntityManagerController) getFromRegister("detailInclude:detailContainer")));
						}
					};
					((ATabularController) controller).getViewer().addSelectionChangedListener(tableInclude_tableLSTN$1);
				}
			}
		});
		((BoundedPart) partController).setBindingService(new PartBindingService((BoundedPart) partController));
	}

	/**
	 * Create contents for FormPage
	 */
	public void createContents(){
		outlinePage = new ListDetailOutlinePage((BoundedPart) coreController);
		// adds formPage headers or includes.
		BoundedPart boundedPart = (BoundedPart) coreController;
		boundedPart.addPageManager(new PageManager("org.adichatz.jpa.gencode.query.ListDetailQueryFormPage1", "Page1", null));
		firePostOpenPart();
	}
	
	//* *****************
	//* Additional code *
	//*******************
	private String getTableRendererKey(ATabularController<?> tabularController) {
	    ControllerPreferenceWrapper<?> controllerPreference = ((JPAControllerPreferenceManager) tabularController.getControllerPreferenceManager()).getControllerPreference();
		if (null != controllerPreference && null != controllerPreference.getTableRendererKey())
		   return controllerPreference.getTableRendererKey();
	    return "Binding";
	}
}