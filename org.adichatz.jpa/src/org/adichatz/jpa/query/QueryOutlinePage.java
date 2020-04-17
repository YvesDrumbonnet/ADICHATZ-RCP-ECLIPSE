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

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.field.NumericTextController;
import org.adichatz.engine.controller.nebula.GridController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.extra.AOutlineListener;
import org.adichatz.engine.extra.AOutlinePage;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.extra.OutlineEvent.EVENT_TYPE;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.gencode.query.QueryToolContainer;
import org.adichatz.jpa.query.custom.FilterTableController;
import org.adichatz.jpa.wrapper.RecentOpenEditorTreeWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryOutlinePage.
 */
public class QueryOutlinePage extends AOutlinePage implements IQueryOutlinePage {

	/** The query tool input. */
	private QueryToolInput<?> queryToolInput;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/** The query tool binding service. */
	private QueryToolBindingService queryToolBindingService;

	/** The total query count. */
	private NumericTextController totalQueryCount;

	/** The filter table controller. */
	private FilterTableController<?> filterTableController;

	/** The filter item. */
	private PShelfItem filterItem;

	/** The preference item. */
	private PShelfItem preferenceItem;

	/** The bounded part. */
	private BoundedPart boundedPart;

	/** The status cmp. */
	private AEntityManagerController statusCmp;

	/** The query tool container. */
	private QueryToolContainer queryToolContainer;

	/**
	 * Instantiates a new query outline page.
	 *
	 * @param boundedPart
	 *            the bounded part
	 */
	@SuppressWarnings("rawtypes")
	public QueryOutlinePage(BoundedPart boundedPart) {
		this.boundedPart = boundedPart;
		this.queryToolInput = new QueryToolInput(boundedPart);
		getOutlinePageListeners().add(new AOutlineListener(EVENT_TYPE.FILTER_CHANGE) {
			@Override
			public void handleEvent(OutlineEvent event) {
				filterItem.getParent().setSelection(filterItem);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void createControl(Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		scrolledForm = toolkit.createScrolledForm(parent);
		ManagedForm managedForm = new ManagedForm(toolkit, scrolledForm);
		scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

		ContainerController containerController = new ContainerController(queryToolInput.getJPAPluginResources(), managedForm,
				"queryTool", boundedPart.getContext());
		queryToolBindingService = queryToolInput.getQueryToolBindingService(containerController);
		containerController.setBindingService(queryToolBindingService);

		queryToolContainer = new QueryToolContainer(queryToolInput.getJPAPluginResources(), containerController, null);
		queryToolContainer.getRegister().put(containerController.getRegisterId(),
				new RegisterEntry(containerController, containerController.getClass()));
		queryToolContainer
				.execLifeCycle(queryToolInput.getJPAPluginResources().getDataAccess().getDataCache().fetchEntity(queryToolInput));
		queryToolInput.setQueryToolContainer(queryToolContainer);

		totalQueryCount = (NumericTextController) queryToolContainer.getRegister().get("totalQueryCount").getController();
		statusCmp = (AEntityManagerController) queryToolContainer.getRegister().get("statusCmp").getController();
		filterTableController = (FilterTableController<?>) queryToolContainer.getRegister().get("filterTable").getController();
		filterItem = ((PShelfItemController) queryToolContainer.getFromRegister("filterItem")).getItem();
		preferenceItem = ((PShelfItemController) queryToolContainer.getFromRegister("preferenceItem")).getItem();
		final AControlListener controlListener = new AControlListener("QueryOutlinePage#refreshTable#1", IEventType.REFRESH,
				(AWidgetController) queryToolInput.getTabularController()) {
			@Override
			public void handleEvent(AdiEvent event) {
				refresh();
			}
		};
		((AWidgetController) queryToolInput.getTabularController()).addListener(controlListener);
		totalQueryCount.getControl().addDisposeListener((e) -> {
			controlListener.dispose();
		});
		if (null != queryToolInput.getQueryManager().getQueryResult())
			totalQueryCount.setValue(queryToolInput.getQueryManager().getQueryPreferenceManager().getPaginationQueryCount());
		filterTableController.refresh();
		filterItem.setVisible(!filterTableController.hasFilter(queryToolInput.getTabularController()));
		preferenceItem.setVisible(RecentOpenEditorTreeWrapper.getInstance().getQueryPreferenceMap()
				.containsKey(queryToolInput.getContentProvider().getQueryURI()));
		preferenceItem.getParent().computeItems();
		queryToolInput.getQueryManager().getQueryListeners().add(new AListener(null, IEventType.AFTER_PREFERENCE_CHANGE) {
			@Override
			public void handleEvent(AdiEvent event) {
				GridController<?> preferenceGridController = (GridController<?>) queryToolContainer
						.getFromRegister("preferenceGrid");
				preferenceGridController.refresh();
				RecentPreferenceWrapper recentPreference = queryToolInput.getControllerPreferenceManager()
						.getActiveRecentPreference();
				StructuredSelection selection = null == recentPreference ? StructuredSelection.EMPTY
						: new StructuredSelection(recentPreference);
				preferenceGridController.getViewer().setSelection(selection);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.query.IQueryOutlinePage#getQueryToolBindingService()
	 */
	public QueryToolBindingService getBindingService() {
		return queryToolBindingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.query.IQueryOutlinePage#getFilterItem()
	 */
	public PShelfItem getFilterItem() {
		return filterItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.IControllerOutlinePage#getComposite()
	 */
	public Composite getComposite() {
		return scrolledForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.IControllerOutlinePage#getContainerCore()
	 */
	@Override
	public AContainerCore getContainerCore() {
		return queryToolContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.IControllerOutlinePage#getLinkedPart()
	 */
	public BoundedPart getLinkedPart() {
		return boundedPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IOutlinePage#refresh()
	 */
	@Override
	public void refresh() {
		totalQueryCount.setValue(queryToolInput.getQueryManager().getQueryPreferenceManager().getPaginationQueryCount());
		totalQueryCount.getParentController().getEntity().firePropertyChanges();
		statusCmp.getEntity().firePropertyChanges();
		filterTableController.refresh();
		filterItem.setVisible(!filterTableController.hasFilter(queryToolInput.getTabularController()));
		preferenceItem.setVisible(RecentOpenEditorTreeWrapper.getInstance().getQueryPreferenceMap()
				.containsKey(queryToolInput.getContentProvider().getQueryURI()));
		preferenceItem.getParent().computeItems();
	}

	/**
	 * Gets the query tool input.
	 *
	 * @return the query tool input
	 */
	public QueryToolInput<?> getQueryToolInput() {
		return queryToolInput;
	}

	@Override
	public boolean hasError() {
		return !getBindingService().getErrorMessageMap().isEmpty();
	}
}
