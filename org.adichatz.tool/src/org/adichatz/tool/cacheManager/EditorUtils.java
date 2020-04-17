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
package org.adichatz.tool.cacheManager;

import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.remote.IAdiLockManager;
import org.adichatz.common.ejb.util.AdiLock;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.ADirtyContainerController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.SectionController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.widgets.LimitedComposite;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.jpa.data.JPAEntity;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorUtils.
 * 
 * @author Yves Drumbonnet
 *
 */
public class EditorUtils {
	/** The CACHE d_ pages. */
	public static List<ICachePage> CACHED_PAGES = new ArrayList<ICachePage>();

	/**
	 * Adds the table.
	 * 
	 * @param cacheEditorTable
	 *            the cache editor table
	 * @param layoutData
	 *            the layout data
	 */
	public static void addTable(ICacheEditorTable cacheEditorTable, String layoutData) {
		cacheEditorTable.getTableViewer().getTable().setHeaderVisible(true);
		cacheEditorTable.getTableViewer().getTable().setLayoutData(layoutData);
		cacheEditorTable.getTableViewer().setContentProvider(new ArrayContentProvider());
		cacheEditorTable.getTableViewer().setSorter(new EditorViewerSorter(cacheEditorTable.getColumnLabelProviders()));
	}

	/**
	 * Adds the table column.
	 * 
	 * @param cacheEditorTable
	 *            the cache editor table
	 * @param text
	 *            the text
	 * @param columnLabelProvider
	 *            the column label provider
	 * @param column
	 *            the column
	 */
	public static void addTableColumn(final ICacheEditorTable cacheEditorTable, String text,
			ColumnLabelProvider columnLabelProvider, final int column) {
		TableViewerColumn tableViewerColumn = new TableViewerColumn(cacheEditorTable.getTableViewer(), SWT.FULL_SELECTION);
		tableViewerColumn.getColumn().setText(text);
		tableViewerColumn.setLabelProvider(columnLabelProvider);
		cacheEditorTable.getTableViewerColumns().add(tableViewerColumn);
		cacheEditorTable.getColumnLabelProviders().add(columnLabelProvider);
		tableViewerColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((EditorViewerSorter) cacheEditorTable.getTableViewer().getSorter()).doSort(column);
				cacheEditorTable.getTableViewer().refresh();
			}
		});
	}

	/**
	 * Adds the tree.
	 * 
	 * @param cacheEditorTree
	 *            the cache editor tree
	 * @param layoutData
	 *            the layout data
	 * @param contentProvider
	 *            the content provider
	 */
	public static void addTree(ICacheEditorTree cacheEditorTree, String layoutData, ITreeContentProvider contentProvider) {
		cacheEditorTree.getTreeViewer().getTree().setHeaderVisible(true);
		cacheEditorTree.getTreeViewer().getTree().setLayoutData(layoutData);
		cacheEditorTree.getTreeViewer().setContentProvider(contentProvider);
		cacheEditorTree.getTreeViewer().setSorter(new EditorViewerSorter(cacheEditorTree.getColumnLabelProviders()));
		cacheEditorTree.getTreeViewer().expandAll();
	}

	/**
	 * Adds the tree column.
	 * 
	 * @param cacheEditorTree
	 *            the cache editor tree
	 * @param text
	 *            the text
	 * @param columnLabelProvider
	 *            the column label provider
	 * @param column
	 *            the column
	 */
	public static void addTreeColumn(final ICacheEditorTree cacheEditorTree, String text, ColumnLabelProvider columnLabelProvider,
			final int column) {
		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(cacheEditorTree.getTreeViewer(), SWT.LEFT);
		treeViewerColumn.getColumn().setText(text);
		treeViewerColumn.getColumn().setWidth(200);
		treeViewerColumn.setLabelProvider(columnLabelProvider);
		cacheEditorTree.getTreeViewerColumns().add(treeViewerColumn);
		cacheEditorTree.getColumnLabelProviders().add(columnLabelProvider);
		treeViewerColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (null != cacheEditorTree.getTreeViewer().getSorter()) {
					((EditorViewerSorter) cacheEditorTree.getTreeViewer().getSorter()).doSort(column);
					cacheEditorTree.getTreeViewer().refresh();
				}
			}
		});
	}

	/**
	 * Adds the elements.
	 * 
	 * @param cacheEditorTable
	 *            the cache editor table
	 */
	public static void addElements(ICacheEditorTable cacheEditorTable) {
		cacheEditorTable.getTableViewer().setInput(cacheEditorTable.getElements());
		for (TableViewerColumn tableViewerColumn : cacheEditorTable.getTableViewerColumns())
			tableViewerColumn.getColumn().pack();
		cacheEditorTable.getTableViewer().refresh();

		cacheEditorTable.getClient().layout();
	}

	/**
	 * Adds the elements.
	 * 
	 * @param cacheEditorTree
	 *            the cache editor tree
	 */
	public static void addElements(ICacheEditorTree cacheEditorTree) {
		cacheEditorTree.getTreeViewer().setInput(cacheEditorTree.getElements());
		cacheEditorTree.getTreeViewer().refresh();
		cacheEditorTree.getTreeViewer().expandAll();
		for (TreeViewerColumn treeViewerColumn : cacheEditorTree.getTreeViewerColumns())
			treeViewerColumn.getColumn().pack();
	}

	/**
	 * Adds the table section.
	 * 
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 * @param sectionText
	 *            the section text
	 * 
	 * @return the section
	 */
	public static Section addTableSection(Composite parent, FormToolkit toolkit, String sectionText) {
		final Section tableSection = toolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		tableSection.setText(sectionText);
		// Composite client = toolkit.createComposite(tableSection, SWT.NONE);
		LimitedComposite client = new LimitedComposite(tableSection, SWT.NONE);
		toolkit.adapt(client);

		client.setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		tableSection.setClient(client);
		return tableSection;
	}

	/**
	 * Adds the tool bar manager.
	 * 
	 * @param section
	 *            the section
	 * 
	 * @return the tool bar manager
	 */
	public static ToolBarManager addToolBarManager(final Section section) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolBar = toolBarManager.createControl(section);
		final Cursor cursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolBar.setCursor(cursor);
		// Cursor needs to be explicitly disposed
		toolBar.addDisposeListener((e) -> {
			if ((cursor != null) && (cursor.isDisposed() == false)) {
				cursor.dispose();
			}
		});
		section.setTextClient(toolBar);
		return toolBarManager;

	}

	/**
	 * Adds the refresh action.
	 * 
	 * @param toolBarManager
	 *            the tool bar manager
	 * @param cacheEditorTree
	 *            the cache editor tree
	 */
	public static void addRefreshAction(final ToolBarManager toolBarManager, final ICacheEditorTree cacheEditorTree) {
		toolBarManager.add(new Action(getFromToolBundle("tool.refresh"),
				AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_REFRESH_SET")) {
			@Override
			public void run() {
				addElements(cacheEditorTree);
			}
		});

	}

	/**
	 * Adds the refresh action.
	 * 
	 * @param toolBarManager
	 *            the tool bar manager
	 * @param cacheEditorTable
	 *            the cache editor table
	 */
	public static Action addRefreshAction(final ToolBarManager toolBarManager, final ICacheEditorTable cacheEditorTable) {
		Action action = new Action(getFromToolBundle("tool.refresh"),
				AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_REFRESH_SET")) {
			@Override
			public void run() {
				addElements(cacheEditorTable);
			}
		};
		toolBarManager.add(action);
		return action;
	}

	/**
	 * Adds the clear action.
	 * 
	 * @param toolBarManager
	 *            the tool bar manager
	 * @param cacheEditorTable
	 *            the cache editor table
	 */
	public static Action addClearAction(final ToolBarManager toolBarManager, final ICacheEditorTable cacheEditorTable) {
		Action action = new Action(getFromToolBundle("tool.clearContents"),
				AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.TOOL_BUNDLE, "IMG_CLEAR_SET.gif")) {
			@Override
			public void run() {
				if (EngineTools.openDialog(MessageDialog.CONFIRM, getFromToolBundle("tool.clearContents"),
						getFromToolBundle("tool.clearContents.confirm")))
					cacheEditorTable.clear();
			}
		};
		toolBarManager.add(action);
		return action;

	}

	/**
	 * Adds the clear action.
	 * 
	 * @param toolBarManager
	 *            the tool bar manager
	 * @param cacheEditorTree
	 *            the cache editor tree
	 */
	public static void addClearAction(final ToolBarManager toolBarManager, final ICacheEditorTree cacheEditorTree) {
		toolBarManager.add(new Action(getFromToolBundle("tool.clearContents"),
				AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.TOOL_BUNDLE, "IMG_CLEAR_SET.gif")) {
			@Override
			public void run() {
				cacheEditorTree.clear();
			}
		});

	}

	/**
	 * Adds the pages actions.
	 * 
	 * @param cachePage
	 *            the cache page
	 * @param form
	 *            the form
	 */
	public static void addPagesActions(ICachePage cachePage, ScrolledForm form, final AdiPluginResources pluginResources) {
		CACHED_PAGES.add(cachePage);
		Action refreshPagesAction = new Action("refresh", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				for (ICachePage page : CACHED_PAGES)
					if (!page.getScrolledForm().isDisposed())
						page.refreshPage();
			}
		};
		refreshPagesAction.setToolTipText("refresh");
		refreshPagesAction.setImageDescriptor(
				AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_REFRESH_SET"));
		form.getToolBarManager().add(refreshPagesAction);

		Action clearPagesAction = new Action("clear", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				clear(pluginResources);
			}
		};
		clearPagesAction.setToolTipText("clear");
		clearPagesAction.setImageDescriptor(
				(AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.TOOL_BUNDLE, "IMG_CLEAR_SET.gif")));
		form.getToolBarManager().add(clearPagesAction);

	}

	/**
	 * Clear.
	 * 
	 * <B>-------</B><BR>
	 * <B>Caution</B><BR>
	 * <B>-------</B><BR>
	 * This method could be invoked dynamically -e.g. method.invoke(...) -<BR>
	 * So all invocations could not be found by Java search tools
	 */
	public static void clear(AdiPluginResources pluginResources) {
		// Clears Locks
		try {
			IAdiLockManager lm = pluginResources.getDataAccess().getGatewayConnector().getLockManager();
			for (AdiLock lock : lm.getLockMap().values())
				pluginResources.getDataAccess().getGatewayConnector().getLockManager().unlock(lock.getBeanClass().getName(),
						lock.getId());
		} catch (Exception e) {
			LogBroker.logError(e);
		}

		// Relationships
		ADataCache dataCache = pluginResources.getDataAccess().getDataCache();
		dataCache.getTabularControllerByPKey().clear();
		dataCache.getRUpdateMap().clear();

		// Table controllers by Cache Key
		dataCache.getTabularControllerByCKey().clear();

		// Clear entity cache
		for (IEntity<?> entity : dataCache.getCachedEntities().values()) {
			Set<ATabularController<?>> tabularControllers = dataCache.getTabularControllerByCKey(entity);
			if (null != tabularControllers)
				tabularControllers.clear();
			((JPAEntity<?>) entity).setLazyFetchManager(null);
			((JPAEntity<?>) entity).getRelationshipUpdateMap().clear();
		}
		dataCache.getCachedEntities().clear();

		// Clear created entity
		dataCache.getCreatedEntities().clear();
	}

	/**
	 * Gets the specific controller.
	 * 
	 * @param controller
	 *            the controller
	 * @param clazz
	 *            the clazz
	 * 
	 * @return the specific controller
	 */
	public static ICollectionController getSpecificController(ICollectionController controller, Class<?>... clazzes) {
		if (null == controller)
			return null;
		for (Class<?> clazz : clazzes)
			if (clazz.isAssignableFrom(controller.getClass()))
				return controller;
		return getSpecificController(controller.getParentController(), clazzes);
	}

	public static String getContainerText(final Object element) {
		if (element instanceof MultiKey)
			return element.toString();
		else if (element instanceof HashMap.Entry)
			return String.valueOf(((HashMap.Entry) element).getKey());
		ADirtyContainerController containerController = (ADirtyContainerController) EditorUtils
				.getSpecificController((ATabularController<?>) element, ADirtyContainerController.class);
		if (null != containerController) {
			if (containerController.getControl().isDisposed())
				return "*** disposed ***";
			if (containerController instanceof SectionController)
				return ((SectionController) containerController).getControl().getText();
			if (containerController instanceof PGroupController)
				return ((PGroupController) containerController).getControl().getText();
			return containerController.getRegisterId();
		}
		return null;
	}
}
