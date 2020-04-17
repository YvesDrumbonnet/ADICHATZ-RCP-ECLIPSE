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
package org.adichatz.jpa.editor;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.data.AEntity;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.IControllerOutlinePage;
import org.adichatz.engine.e4.resource.APartNavigation;
import org.adichatz.engine.extra.ABindingOutlinePage;
import org.adichatz.engine.extra.AOutlineListener;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.extra.OutlineEvent.EVENT_TYPE;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.ACoreListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.ControllerEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.renderer.BasicTableRenderer;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.jpa.gencode.jpa.EditorToolContainer;
import org.adichatz.jpa.query.custom.FilterTableController;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.adichatz.jpa.tabular.JPAControllerPreferenceManager;
import org.adichatz.jpa.xjc.FiltersType;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorOutlinePage.
 *
 * @author Yves Drumbonnet
 */
public class EditorOutlinePage extends ABindingOutlinePage implements IControllerOutlinePage {

	/** The editor outline input. */
	private EditorOutlineInput editorOutlineInput;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/** The entities table. */
	private TableController<AEntity<?>> entitiesTable;

	/** The error paths table. */
	private TableController<ErrorPath> errorPathsTable;

	/** The navigation item. */
	private PShelfItem navigationItem;

	/** The error item. */
	private PShelfItemController errorItem;

	/** The filter item. */
	private PShelfItem filterItem;

	/** The hyperlinks. */
	private List<Hyperlink> hyperlinks = new ArrayList<Hyperlink>();

	/** The bounded part. */
	private BoundedPart boundedPart;

	/** The filter table controller. */
	private FilterTableController<?> filterTableController;

	/** The add filter table. */
	private boolean addFilterTable = true;

	/** The current tabular controller. */
	private ATabularController<?> currentTabularController;

	/** The editor tool container. */
	protected EditorToolContainer editorToolContainer;

	/**
	 * Instantiates a new editor outline page.
	 *
	 * @param editorPart
	 *            the editor part
	 */
	public EditorOutlinePage(BoundedPart editorPart) {
		this(editorPart, true);
	}

	/**
	 * Instantiates a new editor outline page.
	 * 
	 * addFilterTable is false when EditorOutlinePage is call from ListDetailOutlinePage
	 *
	 * @param editorPart
	 *            the editor part
	 * @param addFilter
	 *            the add filter
	 */
	public EditorOutlinePage(BoundedPart editorPart, boolean addFilter) {
		this.editorOutlineInput = new EditorOutlineInput(this, editorPart);
		this.addFilterTable = addFilter;
		if (addFilter) {
			getOutlinePageListeners().add(new AOutlineListener(EVENT_TYPE.FILTER_CHANGE) {
				@Override
				public void handleEvent(OutlineEvent event) {
					filterItem.getParent().setSelection(filterItem);
				}
			});
			editorPart.getGenCode().getCoreListeners().add(new ACoreListener(IEventType.AFTER_END_LIFE_CYCLE) {
				@Override
				public void handleEvent(ControllerEvent event) {
					if (event.getController() instanceof ATabularController) {
						final ATabularController<?> tabularController = (ATabularController<?>) event.getController();
						if (tabularController.getQueryManager().getContentProvider() instanceof EntitySetContentProvider) {
							final AControlListener controlListener = new AControlListener("#refreshEditorTable#1",
									IEventType.REFRESH, tabularController) {
								@Override
								public void handleEvent(AdiEvent event) {
									refresh();
								}
							};
							tabularController.addListener(controlListener);
							final Control control = tabularController.getControl();
							final PaintListener paintListener = new PaintListener() {
								@Override
								public void paintControl(PaintEvent e) {
									if (!tabularController.equals(currentTabularController)) {
										currentTabularController = tabularController;
										refresh();
									}
								}
							};
							control.addPaintListener(paintListener);
						}
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.resource.IOutlinePage#createControl(org.eclipse.swt.widgets.Composite,
	 * org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createControl(Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		boundedPart = (BoundedPart) editorOutlineInput.getBoundedPart();

		scrolledForm = toolkit.createScrolledForm(parent);
		ManagedForm managedForm = new ManagedForm(toolkit, scrolledForm);
		scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

		AdiPluginResources jpaPluginResources = AdichatzApplication.getPluginResources(EngineConstants.JPA_BUNDLE);
		ContainerController containerController = new ContainerController(jpaPluginResources, managedForm, "editorOutline",
				boundedPart.getContext());
		containerController.setBindingService(new EditorOutlineBindingService(containerController));

		editorToolContainer = new EditorToolContainer(jpaPluginResources, containerController, null);
		editorToolContainer.execLifeCycle(jpaPluginResources.getDataAccess().getDataCache().fetchEntity(editorOutlineInput));

		entitiesTable = (TableController<AEntity<?>>) editorToolContainer.getRegister().get("entitiesTable").getController();
		errorPathsTable = (TableController<ErrorPath>) editorToolContainer.getRegister().get("errorPathsTable").getController();
		navigationItem = ((PShelfItemController) editorToolContainer.getRegister().get("navigationItem").getController()).getItem();
		errorItem = (PShelfItemController) editorToolContainer.getRegister().get("errorItem").getController();
		filterTableController = (FilterTableController<?>) editorToolContainer.getRegister().get("filterTable").getController();
		addEntitiesListeners(boundedPart, entitiesTable, errorPathsTable);
		// addFilterTableListener(filterTableController);
		boundedPart = (BoundedPart) editorOutlineInput.getBoundedPart();
		CompositeController navigationCmp = (CompositeController) editorToolContainer.getRegister().get("navigationCmp")
				.getController();
		List<APartNavigation> navigations = boundedPart.getGenCode().getNavigations();
		if (null != navigations) {
			for (final APartNavigation navigation : navigations) {
				final Hyperlink hyperlink = AdichatzApplication.getInstance().getFormToolkit()
						.createHyperlink(navigationCmp.getControl(), navigation.getName(), SWT.WRAP);
				hyperlink.setFont(JFaceResources.getBannerFont());
				hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						navigation.run();
					}
				});
				hyperlink.setData(navigation);
				hyperlinks.add(hyperlink);
			}
		}
	}

	/**
	 * Gets the container core.
	 *
	 * @return the container core
	 */
	@Override
	public AContainerCore getContainerCore() {
		return editorToolContainer;
	}

	/**
	 * Adds the entities listeners.
	 *
	 * @param boundedPart
	 *            the bounded part
	 * @param entitiesTable
	 *            the entities table
	 * @param errorPathsTable
	 *            the error paths table
	 */
	protected void addEntitiesListeners(final BoundedPart boundedPart, final TableController<AEntity<?>> entitiesTable,
			final TableController<ErrorPath> errorPathsTable) {
		entitiesTable.setTableRenderer(new BasicTableRenderer<AEntity<?>>(entitiesTable) {
			@Override
			public Font getFont(Object element) {
				return IEntityConstants.RETRIEVE != ((AEntity<?>) element).getStatus() ? entitiesTable.getBoldFont() : null;
			}

			@Override
			public Color getForeground(Object element) {
				for (ABindingService bindingService : boundedPart.getBindingServices()) {
					if (bindingService.getErrorMessageMap().contains((AEntity<?>) element))
						return AController.ERROR_COLOR;
				}
				return null;
			}
		});

		errorPathsTable.setTableRenderer(new BasicTableRenderer<ErrorPath>(errorPathsTable) {
			@Override
			public Font getFont(Object element) {
				return errorPathsTable.getBoldFont();
			}

			@Override
			public Color getForeground(Object element) {
				return AController.ERROR_COLOR;
			}
		});

		for (ABindingService bindingService : boundedPart.getBindingServices()) {
			bindingService.addBindingListener(new ABindingListener(IEventType.POST_MESSAGE) {
				@Override
				public void handleEvent(AdiEvent event) {
					refreshEntities();
				}
			});
			bindingService.addBindingListener(new ABindingListener(IEventType.CHANGE_STATUS) {
				@Override
				public void handleEvent(AdiEvent event) {
					refreshEntities();
				}
			});
			bindingService.addBindingListener(new ABindingListener(IEventType.ADD_ENTITY) {
				@Override
				public void handleEvent(AdiEvent event) {
					refreshEntities();
				}
			});
			bindingService.addBindingListener(new ABindingListener(IEventType.REMOVE_ENTITY) {
				@Override
				public void handleEvent(AdiEvent event) {
					refreshEntities();
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.resource.IOutlinePage#getComposite()
	 */
	public Composite getComposite() {
		return scrolledForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.resource.IOutlinePage#refresh()
	 */
	@Override
	public void refresh() {
		refreshEntities();
		refreshFilters();
		if (null != filterItem)
			filterItem.getParent().computeItems();
	}

	private void refreshEntities() {
		if (null == entitiesTable || entitiesTable.getControl().isDisposed()) // entitiesTable is null when page is not yet created
			return;

		entitiesTable.refresh();

		boolean showErrotItem = false;
		for (ABindingService bindingService : boundedPart.getBindingServices())
			if (!bindingService.getErrorMessageMap().isEmpty()) {
				showErrotItem = true;
				break;
			}

		errorItem.getItem().setVisible(showErrotItem);
		errorItem.getItem().getParent().computeItems();
		errorPathsTable.refresh();
		for (Hyperlink hyperlink : hyperlinks)
			hyperlink.setEnabled(((APartNavigation) hyperlink.getData()).isEnabled());
		navigationItem.setVisible(0 < hyperlinks.size());
		navigationItem.getParent().computeItems();
	}

	private void refreshFilters() {
		if (null != editorToolContainer && null == filterItem) // filterItem could be null at initialization
			filterItem = ((PShelfItemController) editorToolContainer.getRegister().get("filterItem").getController()).getItem();
		if (null != filterItem) {
			if (addFilterTable && null != currentTabularController) {
				FiltersType filters = ((JPAControllerPreferenceManager<?>) currentTabularController
						.getControllerPreferenceManager()).getControllerPreference().getFilters();
				if (null != filters && !filters.getFilter().isEmpty()) {
					filterTableController.refresh();
					filterItem.setVisible(true);
				} else
					filterItem.setVisible(false);
			}
			if (null != currentTabularController) {
				filterTableController.refresh();
				filterItem.setVisible(!filterTableController.hasFilter(currentTabularController));
			} else
				filterItem.setVisible(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.resource.IOutlinePage#getLinkedPart()
	 */
	public BoundedPart getLinkedPart() {
		return boundedPart;
	}

	public ATabularController<?> getCurrentTabularController() {
		return currentTabularController;
	}
}
