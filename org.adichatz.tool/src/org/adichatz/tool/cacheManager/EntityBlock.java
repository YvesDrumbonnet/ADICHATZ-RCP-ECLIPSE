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
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.jpa.data.JPAEntity;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityBlock.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class EntityBlock extends MasterDetailsBlock implements ICacheEditorTable, ICachePage {

	/** The updated entities filter. */
	private boolean updatedEntitiesFilter = false;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/** The table section. */
	private Section tableSection;

	/** The table viewer. */
	private TableViewer tableViewer;

	/** The table viewer columns. */
	private List<TableViewerColumn> tableViewerColumns = new ArrayList<TableViewerColumn>();

	/** The column label providers. */
	private List<ColumnLabelProvider> columnLabelProviders;

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/**
	 * Instantiates a new entity block.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public EntityBlock(AdiPluginResources pluginResources, ScenarioResources scenarioResources) {
		this.pluginResources = pluginResources;
		this.scenarioResources = scenarioResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#createContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	public void createContent(IManagedForm managedForm) {
		super.createContent(managedForm);
		managedForm.getForm().getBody().setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		sashForm.setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		sashForm.setLayoutData("");

		sashForm.setWeights(new int[] { 2, 2 });
		sashForm.setOrientation(SWT.VERTICAL);
	}

	/**
	 * Creates the details part.
	 * 
	 * @param managedForm
	 *            the managed form
	 * @param parent
	 *            the parent
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		scrolledForm = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		parent.setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		parent.setLayoutData("");
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));

		tableSection = EditorUtils.addTableSection(parent, toolkit, getFromToolBundle("cacheManagerByEntity"));

		tableViewer = new TableViewer(getClient(), SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		ToolBarManager toolBarManager = EditorUtils.addToolBarManager(tableSection);
		EditorUtils.addClearAction(toolBarManager, this);
		toolBarManager.add(new CacheFilterAction(this));
		EditorUtils.addRefreshAction(toolBarManager, this);
		toolBarManager.update(true);

		EditorUtils.addTable(this, "grow, height 100:100:n, w n:150:n");
		// Entity
		EditorUtils.addTableColumn(this, getFromToolBundle("entity"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return pluginResources.getPluginEntityTree().getEntityMM(((IEntity<?>) element).getBeanClass()).getEntityId();
			}
		}, 0);

		// Identifier
		EditorUtils.addTableColumn(this, getFromEngineBundle("identifier"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((IEntity<?>) element).getBeanId());
			}
		}, 1);

		// Status
		EditorUtils.addTableColumn(this, getFromEngineBundle("status"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return Utilities.getStatusLabel(element);
			}
		}, 2);

		// Editor
		EditorUtils.addTableColumn(this, getFromToolBundle("editor"), new ColumnLabelProvider() {
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

		tableViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (updatedEntitiesFilter && ((IEntity<?>) element).getStatus() == IEntityConstants.RETRIEVE)
					return false;
				return true;
			}
		});
		EditorUtils.addElements(this);
		final SectionPart spart = new SectionPart(tableSection);
		managedForm.addPart(spart);
		tableViewer.addSelectionChangedListener((event) -> {
			managedForm.fireSelectionChanged(spart, event.getSelection());
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICacheEditorTable#getElements()
	 */
	public Collection<?> getElements() {
		Collection<IEntity<?>> elements = new ArrayList<IEntity<?>>();
		for (IEntity<?> entity : pluginResources.getDataAccess().getDataCache().getCachedEntities().values()) {
			elements.add(entity);
		}
		return elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICacheEditorTable#clear()
	 */
	@Override
	public void clear() {
		for (IEntity<?> entity : pluginResources.getDataAccess().getDataCache().getCachedEntities().values()) {
			Set<ATabularController<?>> tabularControllers = pluginResources.getDataAccess().getDataCache()
					.getTabularControllerByCKey(entity);
			if (null != tabularControllers)
				tabularControllers.clear();
			((JPAEntity<?>) entity).setLazyFetchManager(null);
			((JPAEntity<?>) entity).getRelationshipUpdateMap().clear();
		}
		pluginResources.getDataAccess().getDataCache().getCachedEntities().clear();
		EditorUtils.addElements(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICacheEditorTable#getClient()
	 */
	@Override
	public Composite getClient() {
		return (Composite) tableSection.getClient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICacheEditorTable#getTableViewer()
	 */
	@Override
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICacheEditorTable#getTableViewerColumns()
	 */
	@Override
	public List<TableViewerColumn> getTableViewerColumns() {
		return tableViewerColumns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICacheEditorTable#getColumnLabelProviders()
	 */
	@Override
	public List<ColumnLabelProvider> getColumnLabelProviders() {
		return columnLabelProviders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#registerPages(org.eclipse.ui.forms.DetailsPart)
	 */
	@Override
	protected void registerPages(DetailsPart detailsPart) {
		final IDetailsPage detailPage = new EntityDetailPage(pluginResources, scenarioResources);
		detailsPart.registerPage(JPAEntity.class, detailPage);
		detailsPart.setPageProvider(new IDetailsPageProvider() {

			@Override
			public IDetailsPage getPage(Object key) {
				return detailPage;
			}

			@Override
			public Object getPageKey(Object object) {
				return JPAEntity.class;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.MasterDetailsBlock#createToolBarActions(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		EditorUtils.addPagesActions(this, form, pluginResources);

		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		// haction.setChecked(true);
		haction.setToolTipText("Horizontal orientation");
		haction.setImageDescriptor(AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class)
				.getRegisteredImageDescriptor("IMG_HORIZONTAL"));

		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		// vaction.setChecked(false);
		vaction.setToolTipText("Vertical orientation");
		vaction.setImageDescriptor(AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class)
				.getRegisteredImageDescriptor("IMG_VERTICAL"));

		form.getToolBarManager().add(vaction);
		form.getToolBarManager().add(haction);

	}

	@Override
	public ScrolledForm getScrolledForm() {
		return scrolledForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#refreshPage()
	 */
	@Override
	public void refreshPage() {
		ISelection selection = tableViewer.getSelection();
		EditorUtils.addElements(this);
		if (!selection.isEmpty())
			tableViewer.setSelection(selection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#clearPage()
	 */
	@Override
	public void clearPage() {
		clear();
	}

	//
	// ******************************************************************************************************
	/**
	 * The Class CacheFilterAction.
	 * 
	 * @author Yves Drumbonnet
	 * 
	 */
	class CacheFilterAction extends Action implements IMenuCreator {

		/** The editor table. */
		private ICacheEditorTable editorTable;

		/** The menu. */
		Menu fMenu;

		/**
		 * Instantiates a new cache filter action.
		 * 
		 * @param editorTable
		 *            the editor table
		 */
		CacheFilterAction(ICacheEditorTable editorTable) {
			setText(getFromToolBundle("cacheFilter"));
			AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class).getRegisteredImageDescriptor("IMG_FILTER");
			setToolTipText(getFromToolBundle("cacheFilter"));
			setMenuCreator(this);
			this.editorTable = editorTable;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.IMenuCreator#dispose()
		 */
		@Override
		public void dispose() {
			fMenu.dispose();
			fMenu = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
		 */
		@Override
		public Menu getMenu(Control parent) {
			fMenu = new Menu(parent);
			Action action = new UpdatedEntityFilterAction(editorTable);
			addActionToMenu(fMenu, action);
			action.setChecked(updatedEntitiesFilter);
			return fMenu;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
		 */
		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

		/**
		 * Adds the action to menu.
		 * 
		 * @param parent
		 *            the parent
		 * @param action
		 *            the action
		 */
		void addActionToMenu(Menu parent, Action action) {
			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(parent, -1);
		}
	}

	/**
	 * The Class UpdatedEntityFilterAction.
	 * 
	 * @author Yves Drumbonnet
	 * 
	 */
	class UpdatedEntityFilterAction extends Action {

		/** The editor table. */
		private ICacheEditorTable editorTable;

		/**
		 * Instantiates a new updated entity filter action.
		 * 
		 * @param editorTable
		 *            the editor table
		 */
		UpdatedEntityFilterAction(ICacheEditorTable editorTable) {
			super("Updated entities", AS_CHECK_BOX);
			setImageDescriptor(
					AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.TOOL_BUNDLE, "IMG_UPDATED_ENTITY.png"));
			setToolTipText(getFromToolBundle("updatedEntitiesFilter"));
			this.editorTable = editorTable;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			updatedEntitiesFilter = !updatedEntitiesFilter;
			EditorUtils.addElements(editorTable);
		}
	}

	@Override
	public void createContent(IManagedForm managedForm, String formText) {
	}

}
