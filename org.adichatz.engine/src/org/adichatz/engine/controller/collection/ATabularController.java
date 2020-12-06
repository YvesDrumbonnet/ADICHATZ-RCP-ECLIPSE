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
package org.adichatz.engine.controller.collection;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.contentProvider.QueryContentProvider;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.renderer.BasicTableRenderer;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.tabular.ATabularStatusBar;
import org.adichatz.engine.tabular.ControllerPreferenceManager;
import org.adichatz.engine.tabular.NullTabularStatusBar;
import org.adichatz.engine.tabular.TabularUtil;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.validation.ErrorMessageMap;
import org.adichatz.engine.widgets.supplement.MenuDetectAdapter;
import org.adichatz.engine.xjc.TableComponentType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ATabularController.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            the
 */
public abstract class ATabularController<T> extends ASetController {

	/**
	 * The Enum METHOD_NAME.
	 * 
	 * @author Yves Drumbonnet
	 * 
	 */
	public enum METHOD_NAME {

		/** Get column count. */
		getColumnCount,
		/** Get column order. */
		getColumnOrder,
		/** Get Header height. */
		getHeaderHeight,
		/** Set column order. */
		setColumnOrder,
		/** Get items. */
		getItems,
		/** Get item count. */
		getItemCount,

		/** Selection index. */
		getSelectionIndex,
		/** Top index. */
		setTopIndex,
		/** Copy to clip board. */
		copyToClipBoard,
		/** Add selection listener. */
		addSelectionListener,
		/** set redraw. */
		setRedraw
	}

	/** The table renderer. */
	protected BasicTableRenderer<T> tableRenderer;

	/** The status bar. */
	protected ATabularStatusBar tabularStatusBar;

	/** The controller preference. */
	protected ControllerPreferenceManager<T> controllerPreferenceManager;

	/** The bean. */
	protected Class<?> beanClass;

	/** The column map. */
	private Map<String, AColumnController<T>> columnMap = new HashMap<String, AColumnController<T>>();

	/** The column viewer sorter. */
	protected AColumnController<T> sortedTableColumn = null;

	/** The body menu. */
	protected Menu bodyMenu;

	/** The header menu. */
	protected Menu headerMenu;

	/** The right click column controller. */
	protected AColumnController<T> rightClickColumnController;

	/** The query manager. */
	protected AQueryManager<T> queryManager;

	/** The preference URI. */
	protected String preferenceURI;

	/**
	 * Instantiates a new table controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public ATabularController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		if (null != parentController)
			this.pluginResources = parentController.getPluginResources();
	}

	/**
	 * Creates the control.
	 */
	@SuppressWarnings("unchecked")
	public void createControl() {
		super.createControl();
		internalCreateControl();

		composite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "[grow,fill][]"));
		composite.setLayoutData("h 0:64:n, w 0:64:n");

		Composite tabular = getControl();

		tabular.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				Point pt = tabular.getDisplay().map(null, tabular, new Point(event.x, event.y));
				Rectangle clientArea = tabular.getClientArea();
				boolean header = clientArea.y <= pt.y
						&& pt.y < (clientArea.y + (Integer) invokeControlMethod(ATabularController.METHOD_NAME.getHeaderHeight));
				if (null == headerMenu)
					headerMenu = tabular.getMenu();
				if (null == bodyMenu)
					bodyMenu = tabular.getMenu();
				tabular.setMenu(header ? headerMenu : bodyMenu);
			}
		});

		/* IMPORTANT: Dispose the menus (only the current menu, set with setMenu(), will be automatically disposed) */
		tabular.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				if (null != headerMenu)
					headerMenu.dispose();
				if (null != bodyMenu)
					bodyMenu.dispose();
			}
		});
		ATabularContentProvider<T> contentProvider = (ATabularContentProvider<T>) genCode.getContext().getParamMap()
				.get(ParamMap.CONTENT_PROVIDER);
		if (null != contentProvider) {
			viewer.setContentProvider(contentProvider);
			contentProvider.setTabularController(this);
		}
		if (null != AReskinManager.getInstance()) {
			tabular.setData(SET_CONTROLLER, this);
			AReskinManager.getInstance().addSkinnedWidget(getControl());
		}
	}

	/**
	 * Gets the bean.
	 * 
	 * @return the bean
	 */
	public Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * Sets the sorted column.
	 * 
	 * @param columnName
	 *            the new sorted column
	 */
	public void setSortedColumn(String columnName) {
		if (EngineTools.isEmpty(columnName))
			sortedTableColumn = null;
		else {
			AColumnController<T> tableColumnController = columnMap.get(columnName);
			if (null == tableColumnController) {
				sortedTableColumn = null;
				logWarning("Find no column with name <" + columnName + "> when setting column sorter!!!");
			} else {
				if (tableColumnController.isValid())
					sortedTableColumn = tableColumnController;
				else
					sortedTableColumn = null;
			}
		}
	}

	/**
	 * Gets the column map.
	 * 
	 * @return the column map
	 */
	public Map<String, AColumnController<T>> getColumnMap() {
		return columnMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.ASetController#refresh()
	 */
	@SuppressWarnings("unchecked")
	public void refresh() {
		// printTime(" - Before refresh " + getRegisterId(), 0, false);
		// Set table renderer, if no change do nothing
		String tableRendererKey = forceControllerPreferenceManager().getTableRendererKey();
		String currentTableRendererKey = null == tableRenderer ? null : tableRenderer.getKey();
		if (!Utilities.equals(currentTableRendererKey, tableRendererKey) || null == tableRenderer) {
			TableComponentType tableRendererType;
			if (null == tableRendererKey)
				tableRendererType = TabularUtil.getInstance().getTabularRenderers(this, true).get(0);
			else {
				tableRendererType = TabularUtil.getInstance().getTableRenderer(this, tableRendererKey);
				if (null == tableRendererType) {
					displayError(getFromEngineBundle("error.encounteredError"),
							getFromEngineBundle("table.invalid.tableRenderer", tableRendererKey));
					tableRendererType = TabularUtil.getInstance().getTabularRenderers(this, true).get(0);
				}
			}
			tableRenderer = (BasicTableRenderer<T>) ReflectionTools.instantiateClass(tableRendererType.getClassName(),
					new Class[] { ATabularController.class, String.class }, new Object[] { this, tableRendererType.getId() });
			Set<IEntity<?>> updatedEntities = null;
			if (null != getBindingService())
				updatedEntities = getBindingService().getUpdatedEntities().keySet();
			tableRenderer.init(updatedEntities);
		}

		if (null != getQueryManager() && null != queryManager.getQueryPreferenceManager() && queryManager.isPaginated()) {
			queryManager.getQueryPreferenceManager().setCurrentMaxResults();

			// Set table renderer, if no change do nothing
			String statusBarKey = controllerPreferenceManager.getStatusBarKey();
			String currentStatusBarKey = null == tabularStatusBar ? null : tabularStatusBar.getKey();
			if (!Utilities.equals(currentStatusBarKey, statusBarKey)) {
				if (null != tabularStatusBar)
					tabularStatusBar.dispose();
				if (null == statusBarKey || null == queryManager || !queryManager.isPaginated())
					tabularStatusBar = new NullTabularStatusBar(this);
				else {
					TableComponentType tabularStatusBarType = TabularUtil.getInstance().getStatusBar(statusBarKey);
					if (null == tabularStatusBarType) {
						displayError(getFromEngineBundle("error.encounteredError"),
								getFromEngineBundle("table.invalid.statusBar", statusBarKey));
						tabularStatusBar = new NullTabularStatusBar(this);
					} else {
						tabularStatusBar = (ATabularStatusBar) getPluginResources().getGencodePath().instantiateClass(
								tabularStatusBarType.getClassName(), new Class[] { ATabularController.class, String.class },
								new Object[] { this, statusBarKey });
					}
				}
			}
		}

		refresh(0, false);
	}

	/**
	 * Refresh.
	 *
	 * @param selection
	 *            the selection
	 * @param addInput
	 *            the add input
	 */
	@SuppressWarnings("unchecked")
	public void refresh(final int selection, final boolean addInput) {
		/**
		 * Line is deleted because putting the inside a syncExecShowWhile block, could generate strange behavior.<br>
		 * - DoubleClickEvent can generate a event(type == SWT.DefaultSelection) which fire a doubleClick event (see
		 * org.eclipse.jface.viewers.StructuredViewer.hookControl(Control))
		 */
		setBoldFont();

		// Remove errors coming directly from server witout chages made by user actions.
		if (null != getBindingService()) {
			ErrorMessageMap errorMessageMap = getBindingService().getErrorMessageMap();
			for (IEntity<?> entity : errorMessageMap.getEntities().toArray(new IEntity<?>[errorMessageMap.getEntities().size()]))
				if (IEntityConstants.RETRIEVE == entity.getStatus()) {
					Set<ErrorMessage> errorMessages = errorMessageMap.get(entity);
					for (ErrorMessage errorMessage : errorMessages.toArray(new ErrorMessage[errorMessages.size()]))
						getBindingService().getErrorMessageMap().remove(errorMessage);
					getBindingService().fireListener(IEventType.POST_MESSAGE);
				}
		}
		if (null != getQueryManager() && null != queryManager.getQueryPreferenceManager())
			queryManager.getQueryPreferenceManager().setFirstResult(selection);
		forceControllerPreferenceManager().initialize();
		((ATabularContentProvider<T>) viewer.getContentProvider()).launchQuery();
		refreshInput(addInput);
		if (viewer.getContentProvider() instanceof QueryContentProvider)
			((QueryContentProvider<T>) viewer.getContentProvider()).postRefresh();
		composite.layout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#synchronize()
	 */
	@Override
	public void synchronize() {
		super.synchronize();
		packColumns();
	}

	/**
	 * Refresh input.
	 */
	public void refreshInput() {
		refreshInput(false);
	}

	/**
	 * Refresh input.
	 *
	 * @param addInput
	 *            the add input
	 */
	@SuppressWarnings("unchecked")
	protected void refreshInput(boolean addInput) {
		if (null != getQueryManager()) { // refreshInput could be called before query was launched
			QueryResult queryResult = queryManager.getQueryResult();
			getControl().setRedraw(false);
			if (null != queryResult) {
				if (addInput) {
					QueryResult fetchedQueryResult = queryResult;
					queryResult = (QueryResult) viewer.getInput();
					if (!fetchedQueryResult.getQueryResultList().isEmpty())
						queryResult.getQueryResultList().addAll(fetchedQueryResult.getQueryResultList());
				}
				if (!queryResult.getQueryResultList().isEmpty()
						&& queryResult.getQueryResultList().iterator().next() instanceof Object[]) {
					// A table of object could be sent (e.g. using createSqlQuery with jointures).
					// First element of the table is the whished bean
					List<Object> elements = new ArrayList<Object>(queryResult.getQueryResultList().size());
					for (Object element : queryResult.getQueryResultList())
						elements.add(((Object[]) element)[0]);

					queryResult = new QueryResult(elements, queryResult.getQueryCount());
				}
				Comparator<T> comparator = queryManager.getContentProvider().getComparator();
				if (null != comparator)
					Collections.sort((List<T>) queryResult.getQueryResultList(), comparator);
				queryManager.setQueryResult(queryResult);
				AListener.fireListener(listenerMap, IEventType.BEFORE_REFRESH);
				viewer.setInput(queryResult);
				packColumns();

				AListener.fireListener(listenerMap, IEventType.AFTER_REFRESH);
				if (null != sortedTableColumn)
					sortedTableColumn.sort();
			}

			if (null != queryManager.getQueryPreferenceManager())
				showStatusBar();
			getControl().setRedraw(true);
			if (null != tableRenderer)
				tableRenderer.postRefreshRendering();

		}
	}

	/**
	 * Pack.
	 */
	public void packColumns() {
		for (AColumnController<T> tableColumnController : columnMap.values()) {
			tableColumnController.resize();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.ACollectionController#addChildController(org.adichatz.engine.controller.AWidgetController)
	 */
	@Override
	public void addChildController(AWidgetController controller) {
		super.addChildController(controller);
		if (controller instanceof AColumnController) {
			@SuppressWarnings("unchecked")
			AColumnController<T> tableColumnController = (AColumnController<T>) controller;
			columnMap.put(tableColumnController.getColumnId(), tableColumnController);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.ASetController#update(org.adichatz.engine.cache.IEntity, java.lang.Object)
	 */
	public void update(IEntity<?> entity, Object bean) {
		if (tableRenderer.isRenderedDirty()) {
			if (IEntityConstants.RETRIEVE == entity.getStatus())
				tableRenderer.removeUpdatedBean(bean);
			else if (null != getBindingService() && getBindingService().equals(entity.getLockingBindingService()))
				tableRenderer.addUpdatedBean(bean);
		}
		if (null != getBindingService() && tableRenderer.isRenderedError()) {
			if (getBindingService().getErrorMessageMap().contains(entity))
				tableRenderer.addErrorBean(bean);
			else
				tableRenderer.removeErrorBean(bean);
		}
		viewer.update(bean, null);
		getTableRenderer().postRefreshRendering();
	}

	/**
	 * Sets the header menu.
	 * 
	 * @param headerMenu
	 *            the new header menu
	 */
	public void setHeaderMenu(Menu headerMenu) {
		if (null != headerMenu)
			getControl().addListener(SWT.MenuDetect, new MenuDetectAdapter(this));
		this.headerMenu = headerMenu;
	}

	/**
	 * Gets the column controllers.
	 *
	 * @param onlyVisible
	 *            the only visible
	 * @return the column controllers
	 */
	@SuppressWarnings("unchecked")
	public AColumnController<T>[] getColumnControllers(boolean onlyVisible) {
		int index = 0;
		AColumnController<T>[] columnControllers = new AColumnController[columnMap.size()];
		for (AWidgetController childController : childControllers) {
			if (childController instanceof AColumnController) {
				AColumnController<T> columnController = (AColumnController<T>) childController;
				if ((!onlyVisible || (columnController.isValid() && 0 != columnController.getColumnWidth())))
					columnControllers[index++] = columnController;
			}
		}
		return columnControllers;
	}

	/**
	 * Gets the right click column controller.
	 * 
	 * @return the right click column controller
	 */
	public AColumnController<T> getRightClickColumnController() {
		return rightClickColumnController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.ASetController#getSelectedObject()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T getSelectedObject() {
		return (T) super.getSelectedObject();
	}

	/**
	 * Process right click mouse event.
	 * 
	 * @param point
	 *            the point
	 */
	public abstract void processRightClickMouseEvent(Point point);

	/**
	 * Internal create control.
	 */
	protected abstract void internalCreateControl();

	/**
	 * Gets the composite wrapping the tabular.
	 * 
	 * @return the composite wrapping the tabular
	 */
	@Override
	public abstract Composite getControl();

	/**
	 * Gets the query manager.
	 * 
	 * @return the query manager
	 */
	@SuppressWarnings("unchecked")
	public AQueryManager<T> getQueryManager() {
		if (null == queryManager) {
			if (null != viewer.getContentProvider()) {
				ATabularContentProvider<T> contentProvider = (ATabularContentProvider<T>) viewer.getContentProvider();
				queryManager = contentProvider.getQueryManager();
			}
		}
		return queryManager;
	}

	/**
	 * Gets the table renderer.
	 * 
	 * @return the table renderer
	 */
	public BasicTableRenderer<T> getTableRenderer() {
		return tableRenderer;
	}

	/**
	 * Sets the table renderer.
	 *
	 * @param tableRenderer
	 *            the new table renderer
	 */
	public void setTableRenderer(BasicTableRenderer<T> tableRenderer) {
		this.tableRenderer = tableRenderer;
	}

	/**
	 * Gets the tabular status bar.
	 *
	 * @return the tabular status bar
	 */
	public ATabularStatusBar getTabularStatusBar() {
		return tabularStatusBar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ASetController#getLazyFetchMembers()
	 */
	@Override
	public Set<String> getLazyFetchMembers() {
		Set<String> lazyFetchMemberList = getQueryManager().getLazyFetchMembers();
		if (null != lazyFetchMemberList) {
			if (null == lazyFetchMembers)
				lazyFetchMembers = lazyFetchMemberList;
			else
				for (String lazyFetchMember : lazyFetchMemberList)
					this.lazyFetchMembers.add(lazyFetchMember);
		}
		return lazyFetchMembers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#endLifeCycle()
	 */
	@Override
	public void endLifeCycle() {
		if (null != getQueryManager() && queryManager.initPagination()) {
			String statusBarKey = forceControllerPreferenceManager().getStatusBarKey();
			String currentStatusBarKey = null == tabularStatusBar ? null : tabularStatusBar.getKey();
			if (null == currentStatusBarKey && null == statusBarKey) {
				statusBarKey = TabularUtil.getInstance().getDefaultStatusBar().getId();
				controllerPreferenceManager.setStatusBarKey(statusBarKey);
			}
		}
		super.endLifeCycle(); // Query could launched so init pagination before 
		if (null != viewer.getContentProvider())
			((ATabularContentProvider<?>) viewer.getContentProvider()).endLifeCycle();
	}

	/**
	 * Show status bar.
	 */
	public void showStatusBar() {
		if (queryManager.isPaginated()) {
			if (null != queryManager.getQueryResult())
				tabularStatusBar.afterRefreshTabularController();
			tabularStatusBar.showNavigation(queryManager.isQueryCompleted());
			tabularStatusBar.setLayoutData("h ".concat(String.valueOf(tabularStatusBar.getHeight())).concat("!"));
		}
		getComposite().layout();
	}

	/**
	 * Refresh after change.
	 *
	 * @param refresh
	 *            the refresh
	 * @param increment
	 *            the increment
	 */
	public void refreshAfterChange(boolean refresh, int increment) {
		QueryResult queryResult = queryManager.getQueryResult();
		queryResult.setQueryCount(queryResult.getQueryCount() + increment);
		if (refresh)
			viewer.refresh();
		tabularStatusBar.afterRefreshTabularController();
		queryManager.getQueryPreferenceManager().setCursorPagination(queryManager.getQuery(), queryResult, false);

	}

	/**
	 * Invoke control method.
	 * 
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 * @return the object
	 */
	public abstract Object invokeControlMethod(METHOD_NAME methodName, Object... params);

	/**
	 * Gets the controller preference manager.
	 *
	 * @return the controller preference manager
	 */
	public ControllerPreferenceManager<T> getControllerPreferenceManager() {
		return getControllerPreferenceManager(true);
	}

	/**
	 * Gets the controller preference manager.
	 *
	 * @param logError
	 *            the log error
	 * @return the controller preference manager
	 */
	public ControllerPreferenceManager<T> getControllerPreferenceManager(boolean logError) {
		if (logError && null == controllerPreferenceManager) {
			controllerPreferenceManager = forceControllerPreferenceManager();
			if (null == controllerPreferenceManager)
				logError(getFromEngineBundle("table.controllerPreferenceManager.not.defined", getRegisterId()));
		}
		return controllerPreferenceManager;
	}

	/**
	 * Force controller preference manager.
	 *
	 * @return the controller preference manager
	 */
	@SuppressWarnings("unchecked")
	public ControllerPreferenceManager<T> forceControllerPreferenceManager() {
		if (null == controllerPreferenceManager && null != viewer && null != viewer.getContentProvider()
				&& viewer.getContentProvider() instanceof ATabularContentProvider)
			controllerPreferenceManager = ((ATabularContentProvider<T>) viewer.getContentProvider())
					.getControllerPreferenceManager();
		return controllerPreferenceManager;
	}

	/**
	 * Sets the controller preference.
	 *
	 * @param controllerPreferenceManager
	 *            the new controller preference
	 */
	public void setControllerPreference(ControllerPreferenceManager<T> controllerPreferenceManager) {
		this.controllerPreferenceManager = controllerPreferenceManager;
	}
}
