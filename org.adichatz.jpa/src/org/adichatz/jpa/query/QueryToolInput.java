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
package org.adichatz.jpa.query;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.contentProvider.QueryContentProvider;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ArgTabFolderController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.extra.ARecentOutlinePage;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.gencode.query.QueryToolContainer;
import org.adichatz.jpa.recent.RecentPreferenceSet;
import org.adichatz.jpa.recent.RecentUtil;
import org.adichatz.jpa.tabular.JPAControllerPreferenceManager;
import org.adichatz.jpa.tabular.QueryPreferenceManager;
import org.adichatz.jpa.wrapper.ControllerPreferenceWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.wrapper.RecentOpenEditorTreeWrapper;
import org.adichatz.jpa.wrapper.RecentOpenQueryEditorWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.adichatz.jpa.xjc.FilterType;
import org.adichatz.jpa.xjc.FiltersType;
import org.adichatz.jpa.xjc.PreferenceTree;
import org.adichatz.jpa.xjc.RecentPreferenceType;
import org.adichatz.jpa.xjc.RecentPreferencesType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IMessageManager;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryToolInput.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class QueryToolInput<T> {
	private static Image IMG_UNUSED = AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE, "IMG_UNUSED.png");

	private static Image IMG_CURRENT = AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE, "IMG_CURRENT.gif");

	private static Image IMG_USED = AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE, "IMG_USED.gif");

	/** The query tool binding service. */
	private QueryToolBindingService queryToolBindingService;

	/** The query manager. */
	private JPAQueryManager<T> queryManager;

	/** The table controller. */
	private ATabularController<T> tabularController;

	/** The content provider. */
	private QueryContentProvider<?> contentProvider;

	/** The outline part. */
	private BoundedPart boundedPart;

	/** The table id. */
	private String tableId;

	/** The plugin resources for model of query manager. */
	private AdiPluginResources adichatzJpaPR;

	/** The query entity. */
	private IEntity<?> queryManagerEntity;

	/** The changed. */
	private boolean changed;

	private QueryToolContainer queryToolContainer;

	private ConfirmFormDialog confirmFormDialog;

	private IMessageManager messageManager;

	/**
	 * Instantiates a new query tool input.
	 * 
	 * needed for interceptor bean
	 */
	public QueryToolInput() {
	}

	/**
	 * Instantiates a new query tool input.
	 * 
	 * @param outlinePart
	 *            the outline part
	 */
	public QueryToolInput(BoundedPart boundedPart) {
		this.boundedPart = boundedPart;
		this.tableId = "tableInclude:table";
		adichatzJpaPR = AdichatzApplication.getPluginResources(EngineConstants.JPA_BUNDLE);
	}

	/**
	 * Gets the query manager entity.
	 * 
	 * @param force
	 *            the force
	 * @return the query manager entity
	 */
	public IEntity<?> getQueryManagerEntity(boolean force) {
		if (force || null == queryManagerEntity)
			queryManagerEntity = adichatzJpaPR.getDataAccess().getDataCache().fetchEntity(queryManager);
		return queryManagerEntity;
	}

	/**
	 * Gets the query manager.
	 * 
	 * @return the query manager
	 */
	@SuppressWarnings("unchecked")
	public JPAQueryManager<T> getQueryManager() {
		if (null == queryManager) {
			initTabularController();
			contentProvider = (QueryContentProvider<T>) tabularController.getViewer().getContentProvider();
			queryManager = (JPAQueryManager<T>) tabularController.getQueryManager();
		}
		return queryManager;
	}

	@SuppressWarnings({ "unchecked", "restriction" })
	public RecentOpenQueryEditorWrapper<T> getActiveRecentQueryEditor() {
		return (RecentOpenQueryEditorWrapper<T>) boundedPart.getInputPart().getTransientData().get(RecentUtil.RECENT_OPEN_EDITOR);
	}

	public List<RecentPreferenceWrapper<?>> getRecentPreferences() {
		RecentPreferenceSet recentPreferenceSet = RecentOpenEditorTreeWrapper.getInstance().getQueryPreferenceMap()
				.get(contentProvider.getQueryURI());
		List<RecentPreferenceWrapper<?>> currentRecentSet = new ArrayList<>();
		if (null == recentPreferenceSet)
			return currentRecentSet;
		Collection<RecentPreferenceWrapper<?>> recentPreferences = recentPreferenceSet.getRecentPreferenceMap().values();
		if (null == recentPreferences)
			return currentRecentSet;
		List<RecentPreferenceWrapper<?>> recentPreferencesList = new ArrayList<>(recentPreferences);
		Collections.sort(recentPreferencesList, new Comparator<RecentPreferenceWrapper<?>>() {
			@Override
			public int compare(RecentPreferenceWrapper<?> c1, RecentPreferenceWrapper<?> c2) {
				return c1.getPreferenceURI().compareTo(c2.getPreferenceURI());
			}
		});
		for (RecentPreferenceWrapper<?> recentPreference : recentPreferencesList)
			if (null != recentPreference.getPreferenceTree())
				currentRecentSet.add(recentPreference);
		return currentRecentSet;
	}

	@SuppressWarnings("unchecked")
	public ATabularController<T> initTabularController() {
		if (null == tabularController)
			tabularController = (ATabularController<T>) boundedPart.getGenCode().getFromRegister(tableId);
		return tabularController;
	}

	/**
	 * Gets the table controller.
	 * 
	 * @return the table controller
	 */
	public ATabularController<?> getTabularController() {
		return tabularController;
	}

	/**
	 * Gets the content provider.
	 * 
	 * @return the content provider
	 */
	public QueryContentProvider<?> getContentProvider() {
		return contentProvider;
	}

	/**
	 * Gets the filters.
	 *
	 * This method is used by reflection (see NativeQueryManager and QueryOutlinePage#refresh)
	 * ---------------------------------------------------------------------------------------
	 *
	 * @return the filters
	 */
	public List<FilterType> getViewerFilters() {
		FiltersType filters = getControllerPreference().getFilters();
		if (null == filters)
			return null;
		return filters.getFilter();
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @return the plugin resources
	 */
	public AdiPluginResources getJPAPluginResources() {
		return adichatzJpaPR;
	}

	/**
	 * Gets the query form text.
	 * 
	 * @return the query form text
	 */
	public String getQueryFormText() {
		IJPAQueryBuilder<T> queryBuilder = queryManager.getQueryBuilder();
		if (null == queryBuilder)
			queryBuilder = new JPAQueryBuilder<>(queryManager);
		return queryBuilder.buildQueryText().replaceAll("&", "&amp;");
	}

	/**
	 * Gets the query part binding service.
	 * 
	 * @param boundedController
	 *            the bounded controller
	 * @return the query part binding service
	 */
	public QueryToolBindingService getQueryToolBindingService(ContainerController containerController) {
		if (null == queryToolBindingService)
			queryToolBindingService = new QueryToolBindingService(containerController, this);
		return queryToolBindingService;
	}

	/**
	 * Gets the query part binding service.
	 * 
	 * @return the query part binding service
	 */
	public QueryToolBindingService getQueryToolBindingService() {
		return queryToolBindingService;
	}

	/**
	 * Checks if is changed.
	 * 
	 * @return true, if is changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the changed.
	 * 
	 * @param changed
	 *            the new changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public QueryToolContainer getQueryToolContainer() {
		return queryToolContainer;
	}

	public void setQueryToolContainer(QueryToolContainer queryToolContainer) {
		this.queryToolContainer = queryToolContainer;
	}

	public void refreshPreferences(boolean relaunchQuery) {
		QueryPreferenceWrapper<?> queryPreference = queryManager.getQueryPreferenceManager().getQueryPreference();
		refreshContainer(queryToolContainer, "paginationCmp", queryPreference.getPagination());
		refreshContainer(queryToolContainer, "statusCmp", getControllerPreference());
		refreshContainer(queryToolContainer, "columnParameterCmp", queryPreference);
		refreshContainer(queryToolContainer, "openClauseItem", queryPreference);
		refreshContainer(queryToolContainer, "jpqlItem", queryManager);
		refreshContainer(queryToolContainer, "orderByCMP", queryPreference);

		TableController<?> parameterTable = (TableController<?>) queryToolContainer.getFromRegister("parameterTable");
		if (parameterTable.isValid()) {
			parameterTable.getViewer().setSelection(StructuredSelection.EMPTY);
			parameterTable.getControl().notifyListeners(SWT.Selection, null);
			parameterTable.refresh();
		}

		ArgTabFolderController openClauseFolder = (ArgTabFolderController) queryToolContainer.getFromRegister("openClauseFolder");
		CTabItemController itemController = openClauseFolder.getSelectedItemController();
		if (null != itemController) {
			CompositeController openParameterDetail = (CompositeController) JPAUtil.getChildController(itemController, 0, 2, 0);
			if (openParameterDetail.isValid()) {
				openParameterDetail.getControl().setVisible(false);
			}
		}

		((IOutlinePage) OutlinePart.getInstance().getCurrentPage()).refresh();
		if (relaunchQuery)
			tabularController.refresh();
	}

	private void refreshContainer(QueryToolContainer queryToolContainer, String registerId, Object bean) {
		AEntityManagerController parentController = (AEntityManagerController) queryToolContainer.getFromRegister(registerId);
		if (parentController.isValid()) {
			ADataCache dataCache = parentController.getPluginResources().getDataAccess().getDataCache();
			IEntity<?> entity = dataCache.fetchEntity(bean);
			parentController.getEntityInjection().initialize(entity);
			parentController.endLifeCycleAndSync();
			AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, parentController));
		}
	}

	public boolean isActivePreference(RecentPreferenceWrapper<?> recentPreference) {
		return RecentUtil.equalRecentPreference(getControllerPreferenceManager().getActiveRecentPreference(), recentPreference);
	}

	public boolean isCurrentPreferenceLinked() {
		RecentPreferenceWrapper<?> recentPreference = getControllerPreferenceManager().getActiveRecentPreference();
		RecentOpenQueryEditorWrapper<T> activeRecentQueryEditor = getActiveRecentQueryEditor();
		if (null != activeRecentQueryEditor.getRecentPreferences())
			for (RecentPreferenceType recentQueryPreferenceType : activeRecentQueryEditor.getRecentPreferences()
					.getRecentPreference())
				if (RecentUtil.equalRecentPreference(recentQueryPreferenceType, recentPreference))
					return true;
		return false;
	}

	public Image getImage(RecentPreferenceWrapper<?> recentPreference) {
		if (isActivePreference(recentPreference))
			return IMG_CURRENT;
		else {
			RecentOpenQueryEditorWrapper<T> activeRecentQueryEditor = getActiveRecentQueryEditor();
			if (null != activeRecentQueryEditor.getRecentPreferences(true))
				for (RecentPreferenceType recentQueryPreferenceType : activeRecentQueryEditor.getRecentPreferences()
						.getRecentPreference())
					if (RecentUtil.equalRecentPreference(recentQueryPreferenceType, recentPreference))
						return IMG_USED;
			return IMG_UNUSED;
		}
	}

	public void chooseRecentPreference(RecentPreferenceWrapper<T> recentPreference) {
		tabularController.getViewer().resetFilters();
		JPAControllerPreferenceManager<T> controllerPreferenceManager = getControllerPreferenceManager();
		if (!Utilities.equals(recentPreference.getPreferenceTree().getTitle(), controllerPreferenceManager.getPreferenceTitle())) {

			recentPreference.reinitPreferenceTree();
			getQueryManager().getQueryPreferenceManager()
					.injectQueryPreference(recentPreference.getPreferenceTree().getQueryPreference());
			controllerPreferenceManager.injectControllerPreference(recentPreference.getPreferenceTree().getControllerPreference());
			controllerPreferenceManager.setActiveRecentPreference(recentPreference);
			refreshPreferences(true);
			getQueryManager().fireListener(IEventType.AFTER_PREFERENCE_CHANGE);
		}
	}

	public void linkCurrentPreference() {
		RecentPreferenceWrapper<?> activeRecentPreference = getControllerPreferenceManager().getActiveRecentPreference();
		addRecentPreference(activeRecentPreference);
		getQueryManager().fireListener(IEventType.AFTER_PREFERENCE_CHANGE);
	}

	public void saveCurrentPreference() {
		if (EngineTools.openDialog(tabularController.getControl().getDisplay(), MessageDialog.CONFIRM,
				AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE, "IMG_PREFERENCE_SAVE.png"), (Image) null,
				getFromJpaBundle("table.preference.save.current.title"),
				getFromJpaBundle("table.preference.save.current.message", getControllerPreferenceManager().getPreferenceTitle()))) {
			marshalPreference(null);
		}
	}

	private void addRecentPreference(RecentPreferenceWrapper<?> recentPreference) {
		RecentPreferencesType recentPreferences = getActiveRecentQueryEditor().getRecentPreferences(true);
		if (null == recentPreferences) {
			recentPreferences = new RecentPreferencesType();
			getActiveRecentQueryEditor().setRecentPreferences(recentPreferences);
		} else {
			RecentOpenEditorTreeWrapper.getInstance().removeQPrefFromOpenEditor(recentPreferences, recentPreference);
		}

		recentPreferences.getRecentPreference().add(recentPreference);
		String queryURI = getContentProvider().getQueryURI();
		Map<String, RecentPreferenceSet> recentPreferenceMap = RecentOpenEditorTreeWrapper.getInstance().getQueryPreferenceMap();
		RecentPreferenceSet recentPreferenceSet = recentPreferenceMap.get(queryURI);
		if (null == recentPreferenceSet) {
			recentPreferenceSet = new RecentPreferenceSet(queryURI);
			recentPreferenceMap.put(queryURI, recentPreferenceSet);
		}
		recentPreferenceSet.getRecentPreferenceMap().put(recentPreference.getPreferenceURI(), recentPreference);
		ARecentOutlinePage.getInstance().refresh();
	}

	private void marshalPreference(String newTitle) {
		getQueryPreference().beforeMarshalling();
		RecentPreferenceWrapper<T> activeRecentPreference;
		PreferenceTree preferenceTree;
		if (null != newTitle) {
			// User chooses to create a new preference
			activeRecentPreference = new RecentPreferenceWrapper<T>();
			activeRecentPreference.setPreferenceURI(RecentUtil.newRecentQueryPreferenceURI());
			activeRecentPreference.setUpdated(EngineTools.getXMLGregorianCalendar(new Date()));
			preferenceTree = new PreferenceTree();
			preferenceTree.setTitle(newTitle);
			activeRecentPreference.setPreferenceTree(preferenceTree);
			getControllerPreferenceManager().setActiveRecentPreference(activeRecentPreference);
			addRecentPreference(activeRecentPreference);
		} else {
			activeRecentPreference = getControllerPreferenceManager().getActiveRecentPreference();
			preferenceTree = activeRecentPreference.getPreferenceTree();
		}
		preferenceTree.setQueryPreference(getQueryPreference());
		preferenceTree.setControllerPreference(getControllerPreference());
		getControllerPreferenceManager().computeColumnPreferences();
		File preferenceFile = activeRecentPreference.getPreferenceFile();
		EngineTools.createXmlFile(preferenceFile, activeRecentPreference.getPreferenceTree(),
				EngineConstants.SCHEMA_LOCATION.concat("preferenceTree.xsd"));
		getQueryManager().fireListener(IEventType.AFTER_PREFERENCE_CHANGE);
	}

	@SuppressWarnings("unchecked")
	public void savePreferenceAs() {
		SaveAsConfirmContent confirmContent = new SaveAsConfirmContent();
		confirmFormDialog = new ConfirmFormDialog(Display.getCurrent().getActiveShell(),
				getFromJpaBundle("table.preference.save.as"),
				AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE, "IMG_PREFERENCE_NEW.png"), confirmContent) {
			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				super.createButtonsForButtonBar(parent);
				messageManager = scrolledForm.getMessageManager();
				validTitle(((SaveAsConfirmContent) confirmContent).titleText, messageManager);
			}

			@Override
			protected void constrainShellSize() {
				super.constrainShellSize();
				((SaveAsConfirmContent) confirmContent).titleText.setFocus();
			}
		};
		confirmFormDialog.open();
	}

	private void validTitle(Text titleText, final IMessageManager messageManager) {
		boolean valid = true;
		messageManager.removeMessage("unicity", titleText);
		messageManager.removeMessage("containsSlash", titleText);
		if (titleText.getText().isEmpty()) {
			messageManager.addMessage("mandatoryField", EngineTools.getFromEngineBundle("mandatoryField"), null,
					IMessageProvider.ERROR, titleText);
			valid = false;
		} else if (-1 != titleText.getText().indexOf('/')) {
			messageManager.addMessage("containsSlash", getFromJpaBundle("queryPreference.containsSlash"), null,
					IMessageProvider.ERROR, titleText);
			valid = false;
		} else {
			messageManager.removeMessage("mandatoryField", titleText);

			RecentPreferenceSet recentPreferenceSet = RecentOpenEditorTreeWrapper.getInstance().getQueryPreferenceMap()
					.get(contentProvider.getQueryURI());
			if (null != recentPreferenceSet)
				for (RecentPreferenceWrapper<?> recentPreference : recentPreferenceSet.getRecentPreferenceMap().values())
					if (titleText.getText().trim().equals(recentPreference.getPreferenceTree().getTitle().trim())) {
						messageManager.addMessage("unicity",
								getFromJpaBundle("queryPreference.title.unicity", titleText.getText().trim()), null,
								IMessageProvider.ERROR, titleText);
						valid = false;
					}
		}
		confirmFormDialog.getButton(IDialogConstants.OK_ID).setEnabled(valid);
	}

	class SaveAsConfirmContent implements IConfirmContent {
		private Text titleText;

		@Override
		public void okPressed(List<Control> complementControls) {
			marshalPreference(titleText.getText());
			refreshPreferences(false);
			((PShelfItemController) queryToolContainer.getFromRegister("preferenceItem")).getControl().setVisible(true);
		}

		@Override
		public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
			parent.setLayout(new MigLayout("wrap 2", "[]10[grow, fill]", "[]30[]"));
			Label label = toolkit.createLabel(parent, getFromJpaBundle("preference.title"));
			label.setFont(JFaceResources.getBannerFont());
			AReskinManager reskinManager = AReskinManager.getInstance();
			if (null != reskinManager)
				label.setForeground(reskinManager.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "title-color",
						AControlController.ADI_CSS_FOREGROUND, label));
			else
				label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

			String title = getControllerPreferenceManager().getPreferenceTitle();
			titleText = toolkit.createText(parent, title, SWT.BORDER);
			titleText.setLayoutData("wmin 300");
			titleText.setSelection(title.length());
			titleText.addModifyListener((e) -> {
				validTitle(titleText, messageManager);
			});
		}
	}

	public void cancelPreference() {
		JPAControllerPreferenceManager<T> controllerPreferenceManager = getControllerPreferenceManager();
		controllerPreferenceManager.setControllerPreference(controllerPreferenceManager.getInitialControllerPreference());
		QueryPreferenceManager<T> queryPreferenceManager = getQueryManager().getQueryPreferenceManager();
		queryPreferenceManager.setQueryPreference(queryPreferenceManager.getInitialQueryPreference().clone(null));
		refreshPreferences(false);
		controllerPreferenceManager.setActiveRecentPreference(null);
		getQueryManager().fireListener(IEventType.AFTER_PREFERENCE_CHANGE);
		tabularController.getViewer().setInput(new QueryResult(Collections.EMPTY_LIST, 0l));
		AListener.fireListener(tabularController.getListenerMap(), IEventType.AFTER_REFRESH);
	}

	public JPAControllerPreferenceManager<T> getControllerPreferenceManager() {
		return (JPAControllerPreferenceManager<T>) initTabularController().getControllerPreferenceManager();
	}

	public ControllerPreferenceWrapper<T> getControllerPreference() {
		return getControllerPreferenceManager().getControllerPreference();
	}

	public QueryPreferenceWrapper<T> getQueryPreference() {
		return getQueryManager().getQueryPreferenceManager().getQueryPreference();
	}
}
