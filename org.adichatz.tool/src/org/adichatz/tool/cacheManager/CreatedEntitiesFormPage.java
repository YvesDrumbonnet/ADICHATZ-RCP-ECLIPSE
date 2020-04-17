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
import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adichatz.common.ejb.remote.IAdiLockManager;
import org.adichatz.common.ejb.util.AdiLock;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.Utilities;
import org.adichatz.jpa.data.JPAEntity;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class CreatedEntitiesFormPage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class CreatedEntitiesFormPage implements ICachePage {

	/** The created entities ie. */
	private ICacheEditorTable createdEntitiesIE;

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	private ScrolledForm scrolledForm;

	/**
	 * Instantiates a new created entities form page.
	 * 
	 * @param editor
	 *            the editor
	 */
	public CreatedEntitiesFormPage(CacheManagerPart part) {
		pluginResources = part.getPluginResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#createContent(org.eclipse.ui.forms.IManagedForm, java.lang.String)
	 */
	@Override
	public void createContent(IManagedForm managedForm, String formText) {
		scrolledForm = managedForm.getForm();
		scrolledForm.setText(formText);
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		createTabularControllersSection(scrolledForm.getBody(), managedForm.getToolkit());

		EditorUtils.addPagesActions(this, scrolledForm, pluginResources);
		scrolledForm.updateToolBar();
	}

	@Override
	public ScrolledForm getScrolledForm() {
		return scrolledForm;
	}

	/**
	 * Creates the table controllers section.
	 * 
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 */
	private void createTabularControllersSection(Composite parent, FormToolkit toolkit) {
		final Section tableSection = EditorUtils.addTableSection(parent, toolkit, getFromToolBundle("createdEntitiesList"));
		final TableViewer tableViewer = new TableViewer((Composite) tableSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TableViewerColumn> tableViewerColumns = new ArrayList<TableViewerColumn>();
		final List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		createdEntitiesIE = new ICacheEditorTable() {
			@Override
			public Composite getClient() {
				return (Composite) tableSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return pluginResources.getDataAccess().getDataCache().getCreatedEntities().values();
			}

			@Override
			public TableViewer getTableViewer() {
				return tableViewer;
			}

			@Override
			public List<TableViewerColumn> getTableViewerColumns() {
				return tableViewerColumns;
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

			@Override
			public void clear() {
				try {
					IAdiLockManager lockManager = pluginResources.getDataAccess().getGatewayConnector().getLockManager();
					for (AdiLock lock : lockManager.getLockMap().values())
						lockManager.unlock(lock.getBeanClass().getName(), lock.getId());
				} catch (Exception e) {
					LogBroker.logError(e);
				}
				// EditorUtils.addElements(this);
			}
		};

		ToolBarManager toolBarManager = EditorUtils.addToolBarManager(tableSection);
		EditorUtils.addClearAction(toolBarManager, createdEntitiesIE);
		EditorUtils.addRefreshAction(toolBarManager, createdEntitiesIE);
		toolBarManager.update(true);

		EditorUtils.addTable(createdEntitiesIE, "grow, height 100:100:n, w n:150:n");
		// Entity
		EditorUtils.addTableColumn(createdEntitiesIE, getFromToolBundle("entity"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return pluginResources.getPluginEntityTree().getEntityMM(((IEntity<?>) element).getBeanClass()).getEntityId();
			}
		}, 0);

		// Identifier
		EditorUtils.addTableColumn(createdEntitiesIE, getFromEngineBundle("identifier"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((IEntity<?>) element).getBeanId());
			}
		}, 1);

		// Status
		EditorUtils.addTableColumn(createdEntitiesIE, getFromEngineBundle("status"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return Utilities.getStatusLabel(element);
			}
		}, 2);

		// Editor
		EditorUtils.addTableColumn(createdEntitiesIE, getFromToolBundle("editor"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return null == ((JPAEntity<?>) element).getLockingBindingService() ? ""
						: ((JPAEntity<?>) element).getLockingBindingService().getTitle();
			}

			@Override
			public Image getImage(Object element) {
				return null == ((JPAEntity<?>) element).getLockingBindingService() ? null
						: ((JPAEntity<?>) element).getLockingBindingService().getImage();
			}
		}, 3);

		EditorUtils.addElements(createdEntitiesIE);
	}

	//
	// ******************************************************************************************************
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#refreshPage()
	 */
	@Override
	public void refreshPage() {
		EditorUtils.addElements(createdEntitiesIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#clearPage()
	 */
	@Override
	public void clearPage() {
		createdEntitiesIE.clear();
	}
}
