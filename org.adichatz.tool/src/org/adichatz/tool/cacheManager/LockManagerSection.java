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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adichatz.common.ejb.util.AdiLock;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

// TODO: Auto-generated Javadoc
/**
 * The Class LockManagerSection.
 * 
 * @author Yves Drumbonnet
 *
 */
public class LockManagerSection {

	/** The lock ie. */
	private ICacheEditorTable lockIE;

	/** The delete lock action. */
	private DeleteLockAction deleteLockAction;

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/** The clear action. */
	private Action clearAction;

	/** The refresh action. */
	private Action refreshAction;

	/**
	 * Sets the plugin resources.
	 * 
	 * @param pluginResources the new plugin resources
	 */
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	/**
	 * Creates the table controllers section.
	 * 
	 * @param parent  the parent
	 * @param toolkit the toolkit
	 */
	public void createTabularControllersSection(Composite parent, FormToolkit toolkit) {
		final Section tableSection = EditorUtils.addTableSection(parent, toolkit, getFromToolBundle("lockManagerList"));
		Table table = toolkit.createTable((Composite) tableSection.getClient(),
				SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		final TableViewer tableViewer = new TableViewer(table);
		final List<TableViewerColumn> lockTableViewerColumns = new ArrayList<TableViewerColumn>();
		final List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		lockIE = new ICacheEditorTable() {
			@Override
			public Composite getClient() {
				return (Composite) tableSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				deleteLockAction.setEnabled(false);
				try {
					return pluginResources.getDataAccess().getGatewayConnector().getLockManager().getLockMap().values();
				} catch (Exception e) {
					displayError(getFromEngineBundle("error.encounteredError"), e, e.getMessage());
					logError(e);
				}
				return null;
			}

			@Override
			public TableViewer getTableViewer() {
				return tableViewer;
			}

			@Override
			public List<TableViewerColumn> getTableViewerColumns() {
				return lockTableViewerColumns;
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

			@Override
			public void clear() {
				try {
					for (AdiLock lock : pluginResources.getDataAccess().getGatewayConnector().getLockManager().getLockMap()
							.values())
						pluginResources.getDataAccess().getGatewayConnector().getLockManager().unlock(lock.getBeanClass().getName(),
								lock.getId());
					EditorUtils.addElements(lockIE);
				} catch (Exception e) {
					logError(e);
				}
				// EditorUtils.addElements(this);
			}
		};

		deleteLockAction = new DeleteLockAction();
		deleteLockAction.setEnabled(false);
		ToolBarManager toolBarManager = EditorUtils.addToolBarManager(tableSection);
		toolBarManager.add(deleteLockAction);
		clearAction = EditorUtils.addClearAction(toolBarManager, lockIE);
		refreshAction = EditorUtils.addRefreshAction(toolBarManager, lockIE);
		toolBarManager.update(true);

		EditorUtils.addTable(lockIE, "h 0:n:n, w 0:n:n");

		// Entity Type
		EditorUtils.addTableColumn(lockIE, getFromToolBundle("entityId"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return pluginResources.getPluginEntityTree().getEntityMM(((AdiLock) element).getBeanClass()).getEntityId();
			}
		}, 0);

		// Identifier
		EditorUtils.addTableColumn(lockIE, getFromEngineBundle("identifier"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((AdiLock) element).getId());
			}
		}, 1);

		// Lock Date
		EditorUtils.addTableColumn(lockIE, getFromToolBundle("date"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((AdiLock) element).getLockDate());
			}
		}, 2);

		// UUID
		EditorUtils.addTableColumn(lockIE, "UUID", new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((AdiLock) element).getSession().getUuid());
			}
		}, 3);

		// Username
		EditorUtils.addTableColumn(lockIE, getFromToolBundle("username"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((AdiLock) element).getSession().getUsername());
			}
		}, 4);

		tableViewer.addSelectionChangedListener((event) -> {
			if (event.getSelection().isEmpty())
				deleteLockAction.setEnabled(false);
			else
				deleteLockAction.setEnabled(true);
		});

	}

	/**
	 * Gets the lock ie.
	 * 
	 * @return the lock ie
	 */
	public ICacheEditorTable getLockIE() {
		return lockIE;
	}

	/**
	 * Gets the refresh action.
	 * 
	 * @return the refresh action
	 */
	public Action getRefreshAction() {
		return refreshAction;
	}

	/**
	 * Gets the clear action.
	 * 
	 * @return the clear action
	 */
	public Action getClearAction() {
		return clearAction;
	}

	//
	// ******************************************************************************************************
	/**
	 * The Class DeleteLockAction.
	 * 
	 * @author Yves Drumbonnet
	 *
	 */
	class DeleteLockAction extends Action {

		/**
		 * Instantiates a new delete lock action.
		 */
		DeleteLockAction() {
			setText(getFromToolBundle("tool.deleteLock"));
			setImageDescriptor(AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class)
					.getRegisteredImageDescriptor("IMG_DELETE"));
			setToolTipText(getFromToolBundle("tool.deleteLock"));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			if (EngineTools.openDialog(MessageDialog.CONFIRM, getFromToolBundle("tool.deleteLock"),
					getFromToolBundle("tool.deleteLock.confirm"))) {
				AdiLock lock = (AdiLock) ((IStructuredSelection) lockIE.getTableViewer().getSelection()).getFirstElement();
				try {
					pluginResources.getDataAccess().getGatewayConnector().getLockManager().unlock(lock.getBeanClass().getName(),
							lock.getId());
					EditorUtils.addElements(lockIE);
				} catch (Exception e) {
					logError(e);
				}
			}
		}
	}
}
