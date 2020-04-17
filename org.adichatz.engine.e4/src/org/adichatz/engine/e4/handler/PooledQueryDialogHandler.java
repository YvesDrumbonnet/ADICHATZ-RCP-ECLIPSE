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
package org.adichatz.engine.e4.handler;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.data.PooledQueryResult;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class PooledQueryDialogHandler.
 */
public class PooledQueryDialogHandler {

	/** The shell. */
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	protected Shell shell;

	/**
	 * Execute.
	 */
	@Execute
	public void execute() {
		new TrayDialog(shell) {
			private TableViewer pooledQueryResultTV;

			private ComboViewer comboViewer;

			private AdiPluginResources pluginResources;

			@Override
			public void create() {
				setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
				super.create();
				EngineTools.centerWindow(getShell());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected Control createDialogArea(Composite parent) {
				setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
				getShell().setImage(
						AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_E4_BUNDLE, "IMG_QUERY_MANAGER.png"));
				getShell().setText(getFromEngineE4Bundle("pooled.queries"));
				initializeBounds();
				AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
				if (null == toolkit)
					toolkit = new AdiFormToolkit(Display.getCurrent());

				parent.setBackground(toolkit.getColors().getBackground());
				ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
				scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
				Composite body = scrolledForm.getBody();

				body.setLayout(new MigLayout("wrap 1", "[grow, fill]", "[][grow, fill]"));

				Composite comboComposite = toolkit.createComposite(body);
				comboComposite.setLayout(new MigLayout("wrap 2"));
				toolkit.createLabel(comboComposite, getFromEngineE4Bundle("pooled.query.plugin.resources"));
				comboViewer = new ComboViewer(comboComposite, SWT.READ_ONLY);
				comboViewer.setContentProvider(new ArrayContentProvider());
				comboViewer.getCombo().setLayoutData("wmin 200");
				comboViewer.setLabelProvider(new LabelProvider() {
					public String getText(Object element) {
						return ((AdiPluginResources) element).getPluginName();
					}
				});
				List<AdiPluginResources> input = new ArrayList<AdiPluginResources>();
				for (AdiPluginResources pr : AdichatzApplication.getInstance().getPluginMap().values())
					if (null != pr.getDataAccess() && !pr.getPluginEntityTree().getPooledQueryResultMap().isEmpty())
						input.add(pr);
				comboViewer.setInput(input);
				final Section section = toolkit.createSection(body, Section.TITLE_BAR | Section.EXPANDED);
				section.setLayoutData("w 700!, h 350!");

				comboViewer.addSelectionChangedListener((event) -> {
					if (!event.getSelection().isEmpty()) {
						pluginResources = (AdiPluginResources) ((StructuredSelection) event.getSelection()).getFirstElement();
						pooledQueryResultTV.setInput(pluginResources.getPluginEntityTree().getPooledQueryResultMap().entrySet());
						pooledQueryResultTV.getTable().layout();
						section.setText(getFromEngineE4Bundle("pooled.query.section.text", pluginResources.getPluginName()));
						packColumns();
					}
				});

				Composite composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(section, SWT.NONE);
				composite.setLayout(new MigLayout("", "grow, fill", "grow, fill"));
				section.setClient(composite);

				ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
				final ToolBar toolBar = toolBarManager.createControl(section);
				section.addExpansionListener(new ExpansionAdapter() {
					@Override
					public void expansionStateChanged(ExpansionEvent e) {
						toolBar.setVisible((Boolean) e.data);
					}
				});
				final Cursor cursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
				toolBar.setCursor(cursor);
				// Cursor needs to be explicitly disposed
				toolBar.addDisposeListener((e) -> {
					if ((cursor != null) && (cursor.isDisposed() == false)) {
						cursor.dispose();
					}
				});

				/*
				 * Refresh action
				 */
				final Action refreshAction = new Action(getFromEngineE4Bundle("pooled.query.refresh"), AdichatzApplication
						.getInstance().getImageDescriptor(EngineConstants.ENGINE_BUNDLE, "IMG_REFRESH_SET.png")) {
					@Override
					public void run() {
						Map.Entry<MultiKey, PooledQueryResult> entry = getSelectedEntry();
						entry.getValue().refresh();
						pooledQueryResultTV.update(entry, null);
					}
				};
				refreshAction.setEnabled(false);
				toolBarManager.add(refreshAction);

				/*
				 * Delete action
				 */
				final Action deleteAction = new Action(getFromEngineE4Bundle("pooled.query.delete"),
						toolkit.getRegisteredImageDescriptor("IMG_DELETE")) {
					@Override
					public void run() {
						Map.Entry<MultiKey, PooledQueryResult> entry = getSelectedEntry();
						AdiPluginResources pr = entry.getValue().getQueryManager().getEntityMM().getPluginEntity()
								.getPluginResources();
						pr.getPluginEntityTree().getPooledQueryResultMap().remove(entry.getKey());
						pooledQueryResultTV.remove(entry);
					}
				};
				deleteAction.setEnabled(false);
				toolBarManager.add(deleteAction);
				toolBarManager.update(true);
				section.setTextClient(toolBar);

				pooledQueryResultTV = new TableViewer(composite,
						SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
				pooledQueryResultTV.setContentProvider(new ArrayContentProvider());
				pooledQueryResultTV.addSelectionChangedListener((event) -> {
					boolean enable = !event.getSelection().isEmpty();
					refreshAction.setEnabled(enable);
					deleteAction.setEnabled(enable);
				});

				Table table = pooledQueryResultTV.getTable();
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				table.setLayout(new MigLayout("wrap", "grow,fill", "[grow,fill]"));
				table.setLayoutData("h 0:n:n, w 0:n:n");

				TableViewerColumn col = createTableViewerColumn(getFromEngineE4Bundle("pooled.query.resource.uri"), 0, SWT.NONE);
				col.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						@SuppressWarnings("unchecked")
						MultiKey multiKey = ((Map.Entry<MultiKey, PooledQueryResult>) element).getKey();
						return multiKey.getString(0);
					}
				});
				col = createTableViewerColumn(getFromEngineE4Bundle("pooled.query.preference.uri"), 1, SWT.NONE);
				col.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						@SuppressWarnings("unchecked")
						MultiKey multiKey = ((Map.Entry<MultiKey, PooledQueryResult>) element).getKey();
						return multiKey.size() > 1 ? multiKey.getString(1) : "";
					}
				});
				col = createTableViewerColumn(getFromEngineE4Bundle("pooled.query.count"), 2, SWT.RIGHT);
				col.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						@SuppressWarnings("unchecked")
						PooledQueryResult pooledQueryResult = ((Map.Entry<MultiKey, PooledQueryResult>) element).getValue();
						QueryResult queryResult = pooledQueryResult.getQueryResult();
						return null == queryResult ? "" : String.valueOf(queryResult.getQueryCount());
					}
				});
				if (1 == input.size())
					comboViewer.setSelection(new StructuredSelection(input.get(0)));
				else
					comboViewer.getCombo().notifyListeners(SWT.Selection, null);
				packColumns();

				scrolledForm.reflow(true);
				applyDialogFont(scrolledForm.getBody());
				parent.layout();
				return scrolledForm;
			}

			@SuppressWarnings("unchecked")
			private Map.Entry<MultiKey, PooledQueryResult> getSelectedEntry() {
				return (Map.Entry<MultiKey, PooledQueryResult>) ((StructuredSelection) pooledQueryResultTV.getSelection())
						.getFirstElement();
			}

			private TableViewerColumn createTableViewerColumn(String title, final int colNumber, int style) {
				final TableViewerColumn viewerColumn = new TableViewerColumn(pooledQueryResultTV, style);
				final TableColumn column = viewerColumn.getColumn();
				column.setText(title);
				column.setResizable(true);
				column.setMoveable(true);
				return viewerColumn;
			}

			private void packColumns() {
				for (TableColumn tableColumn : pooledQueryResultTV.getTable().getColumns())
					tableColumn.pack();
			}
		}.open();
	};

}
