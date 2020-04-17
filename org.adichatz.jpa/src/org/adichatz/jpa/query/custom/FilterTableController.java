package org.adichatz.jpa.query.custom;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.renderer.BasicTableRenderer;
import org.adichatz.engine.tabular.ColumnViewerFilter;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.jpa.action.ColumnFilterFormDialog;
import org.adichatz.jpa.tabular.JPAControllerPreferenceManager;
import org.adichatz.jpa.xjc.FilterType;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import net.miginfocom.swt.MigLayout;

public class FilterTableController<T> extends TableController<T> {

	private ATabularController<?> tabularController;

	private NativeContentProvider<T> contentProvider;

	public FilterTableController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void internalCreateControl() {
		final AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		composite = new Composite(parentController.getComposite(), SWT.NONE);
		composite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "[grow,fill]"));

		CheckboxTableViewer checkboxTableViewer = CheckboxTableViewer.newCheckList(composite, 0);
		viewer = checkboxTableViewer;
		table = checkboxTableViewer.getTable();
		table.setHeaderVisible(true);

		final MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(table);
		menuManager.setRemoveAllWhenShown(true);
		table.setMenu(menu);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				if (!((IStructuredSelection) viewer.getSelection()).isEmpty()) {
					final FilterType filter = (FilterType) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
					menuManager.add(new Action(getFromEngineBundle("query.edit.filter", getColumnController(filter).getText()),
							toolkit.getRegisteredImageDescriptor("IMG_EDITOR")) {
						@Override
						public void run() {
							editFilter(toolkit, filter);
						}
					});
					menuManager.add(new Action(getFromEngineBundle("query.remove.filter", getColumnController(filter).getText()),
							toolkit.getRegisteredImageDescriptor("IMG_DELETE")) {
						@Override
						public void run() {
							tabularController.getControllerPreferenceManager().removeColumnFilter(filter.getColumn());
							if (!filter.isEnabled()) // Force refresh on EditorOutlinePage when filter is disabled
								refresh();
						}
					});
				}
			}
		});
		checkboxTableViewer.addDoubleClickListener((e) -> {
			editFilter(toolkit, (FilterType) ((IStructuredSelection) viewer.getSelection()).getFirstElement());
		});
		checkboxTableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				FilterType filterType = (FilterType) event.getElement();
				tabularController.getControl().setRedraw(false);
				if (event.getChecked()) {
					@SuppressWarnings("unchecked")
					ColumnViewerFilter<?> filter = ((JPAControllerPreferenceManager<T>) tabularController
							.getControllerPreferenceManager()).addViewerFilter(filterType);
					if (null == filter)
						logError(getFromEngineBundle("query.filter.no.filter", filterType.getColumn(), filterType.getText()));
					else {
						filterType.setEnabled(true);
						filter.enable();
					}
				} else {
					ColumnViewerFilter<?> filter = tabularController.getControllerPreferenceManager()
							.getColumnViewerFilter(filterType.getColumn());
					if (null != filter)
						tabularController.getViewer().removeFilter(filter);
					filterType.setEnabled(false);
				}
				tabularController.refreshInput();
				tabularController.getControl().setRedraw(true);
			}
		});
		contentProvider = new NativeContentProvider<T>("viewerFilters");
		contentProvider.setTabularController(this);
		setTableRenderer(new BasicTableRenderer<T>(this));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void editFilter(final AdiFormToolkit toolkit, final FilterType filter) {
		new ColumnFilterFormDialog(toolkit, tabularController, getColumnController(filter),
				getFromEngineBundle("query.edit.filter", getColumnController(filter).getText())).open();
		if (!filter.isEnabled()) // Force refresh on EditorOutlinePage when filter is disabled
			refresh();
	}

	@Override
	public void refresh() {
		// Object bean = parentController.getEntity().getBean();
		// if (bean instanceof QueryToolInput)
		// tabularController = ((QueryToolInput<?>) bean).getTabularController();
		// else if (bean instanceof QueryPreferenceWrapper)
		// tabularController = ((QueryPreferenceWrapper<?>) bean).getTabularController();

		contentProvider.launchQuery();
		refreshInput(false);
		for (Object row : getQueryManager().getQueryResult().getQueryResultList()) {
			FilterType filter = (FilterType) row;
			((CheckboxTableViewer) viewer).setChecked(filter, filter.isEnabled());
		}
		composite.layout();
	}

	public void setTabularController(ATabularController<?> tabularController) {
		this.tabularController = tabularController;
	}

	public boolean hasFilter(ATabularController<?> tabularController) {
		this.tabularController = tabularController;
		return null == tabularController || !tabularController.getControllerPreferenceManager().hasFilter(null);
	}

	private AColumnController<?> getColumnController(FilterType filter) {
		return tabularController.getColumnMap().get(filter.getColumn());
	}

}
