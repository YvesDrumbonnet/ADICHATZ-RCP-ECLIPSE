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
package org.adichatz.jpa.tabular;

import static org.adichatz.engine.common.LogBroker.logError;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ATabularController.METHOD_NAME;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.tabular.ColumnViewerFilter;
import org.adichatz.engine.tabular.ControllerPreferenceManager;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.wrapper.ControllerPreferenceWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.adichatz.jpa.xjc.ColumnPreferenceType;
import org.adichatz.jpa.xjc.ColumnPreferencesType;
import org.adichatz.jpa.xjc.FilterType;
import org.adichatz.jpa.xjc.FiltersType;
import org.eclipse.jface.viewers.ViewerFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerPreferenceManager.
 *
 * @param <T>
 *            the generic type
 */
public class JPAControllerPreferenceManager<T> extends ControllerPreferenceManager<T> {

	/** The active recent preference. */
	private RecentPreferenceWrapper<T> activeRecentPreference;

	/** The controller preference. */
	private ControllerPreferenceWrapper<T> controllerPreference;

	/** The initial controller preference. */
	private ControllerPreferenceWrapper<T> initialControllerPreference;

	/** The customized query preference map. */
	private Map<String, ControllerPreferenceWrapper<T>> controllerQueryPreferenceMap = new HashMap<>();

	/**
	 * Instantiates a new controller preference manager.
	 *
	 * @param tabularController
	 *            the tabular controller
	 */
	public JPAControllerPreferenceManager(ATabularController<T> tabularController) {
		super(tabularController);
		controllerPreference = new ControllerPreferenceWrapper<T>();
	}

	/**
	 * Gets the controller preference.
	 *
	 * @return the controller preference
	 */
	public ControllerPreferenceWrapper<T> getControllerPreference() {
		return controllerPreference;
	}

	/**
	 * Sets the controller preference.
	 *
	 * @param controllerPreference
	 *            the new controller preference
	 */
	public void setControllerPreference(ControllerPreferenceWrapper<T> controllerPreference) {
		this.controllerPreference = controllerPreference;
	}

	/**
	 * Gets the initial controller preference.
	 *
	 * @return the initial controller preference
	 */
	public ControllerPreferenceWrapper<T> getInitialControllerPreference() {
		if (null == initialControllerPreference)
			initialControllerPreference = controllerPreference.clone(null);
		return initialControllerPreference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.tabular.IControllerPreferenceManager#setInitialControllerPreference()
	 */
	@Override
	public void setInitialControllerPreference() {
		initialControllerPreference = controllerPreference.clone(null);
	}

	/**
	 * Sets the initial controller preference.
	 *
	 * @param initialControllerPreference
	 *            the new initial controller preference
	 */
	public void setInitialControllerPreference(ControllerPreferenceWrapper<T> initialControllerPreference) {
		this.initialControllerPreference = initialControllerPreference;
	}

	/**
	 * Gets the filter.
	 *
	 * @param columnId
	 *            the column id
	 * @return the filter
	 */
	public FilterType getFilter(String columnId) {
		if (null != controllerPreference.getFilters())
			for (FilterType filter : controllerPreference.getFilters().getFilter()) {
				if (columnId.equals(filter.getColumn()))
					return filter;
			}
		return null;
	}

	/**
	 * Adds the viewer filter.
	 *
	 * @param filter
	 *            the filter
	 * @return the column viewer filter
	 */
	public ColumnViewerFilter<T> addViewerFilter(FilterType filter) {
		AColumnController<T> columnController = tabularController.getColumnMap().get(filter.getColumn());
		if (null != columnController) {
			ColumnViewerFilter<T> columnViewerFilter = new ColumnViewerFilter<T>(columnController, filter.getSearchString());
			boolean exactString = filter.isExactString();
			boolean caseInsensitive = filter.isCaseInsensitive();
			try {
				columnViewerFilter.computePredicate(exactString, caseInsensitive);
				return columnViewerFilter;
			} catch (SecurityException | ParseException | NoSuchMethodException e) {
				logError(e);
			}
		}
		return null;
	}

	/**
	 * Removes the column filter.
	 *
	 * @param column
	 *            the column
	 */
	public void removeColumnFilter(String column) {
		if (null != controllerPreference.getFilters()) {
			Iterator<FilterType> filterIter = controllerPreference.getFilters().getFilter().iterator();
			while (filterIter.hasNext()) {
				FilterType filterType = filterIter.next();
				if (column.equals(filterType.getColumn()))
					filterIter.remove();
			}
		}
		ColumnViewerFilter<?> filter = getColumnViewerFilter(column);
		if (null != filter) { // Filter is null when ColumnFilter is disabled
			tabularController.getViewer().removeFilter(filter);
			tabularController.refreshInput();
		}
	}

	/**
	 * Compute column preferences (order and visible and width).
	 */
	public void computeColumnPreferences() {
		boolean addColumnOrder = false;
		StringBuffer columnOrderSB = new StringBuffer();
		int[] tableColumnOrders = (int[]) tabularController.invokeControlMethod(ATabularController.METHOD_NAME.getColumnOrder);
		for (int i = 0; i < tableColumnOrders.length; i++) {
			if (i != tableColumnOrders[i])
				addColumnOrder = true;
			columnOrderSB.append(", ").append(tableColumnOrders[i]);
		}
		controllerPreference.setColumnOrder(addColumnOrder ? columnOrderSB.substring(2) : null);
		ColumnPreferencesType columnPreferences = new ColumnPreferencesType();
		for (AColumnController<?> columnController : tabularController.getColumnControllers(false)) {
			if ((null != columnController.getWidth() && 0 == columnController.getWidth()) || columnController.isResized()) {
				ColumnPreferenceType columnPreference = new ColumnPreferenceType();
				columnPreferences.getColumnPreference().add(columnPreference);
				columnPreference.setId(columnController.getColumnId());
				if (null != columnController.getWidth() && 0 == columnController.getWidth())
					columnPreference.setVisible(false);
				else
					columnPreference.setWidth(columnController.getWidth());
			}
		}
		if (!columnPreferences.getColumnPreference().isEmpty()) {
			controllerPreference.setColumnPreferences(columnPreferences);
		}
	}

	/**
	 * Adds the column filter.
	 *
	 * @param columnViewerFilter
	 *            the column viewer filter
	 */
	public void addColumnFilter(ColumnViewerFilter<T> columnViewerFilter) {
		removeColumnFilter(columnViewerFilter.getColumnController().getColumnId());
		FiltersType filters = controllerPreference.getFilters();
		if (null == filters) {
			filters = new FiltersType();
			controllerPreference.setFilters(filters);
		}
		String column = columnViewerFilter.getColumnController().getColumnId();
		FilterType filterType = new FilterType();
		filterType.setText(columnViewerFilter.getText());
		filterType.setEnabled(true);
		filterType.setColumn(column);
		filterType.setExactString(columnViewerFilter.isExactString());
		filterType.setCaseInsensitive(columnViewerFilter.isCaseInsensitive());
		filterType.setSearchString(columnViewerFilter.getSearchString());

		filters.getFilter().add(filterType);

		ColumnViewerFilter<?> currentFilter = getColumnViewerFilter(columnViewerFilter.getColumnController().getColumnId());
		if (null != currentFilter)
			tabularController.getViewer().removeFilter(currentFilter);
		columnViewerFilter.enable();
	}

	/**
	 * Checks for filter.
	 *
	 * @param enabled
	 *            the enabled
	 * @return true, if successful
	 */
	public boolean hasFilter(Boolean enabled) {
		boolean result = null != controllerPreference.getFilters() && !controllerPreference.getFilters().getFilter().isEmpty();
		if (result && null != enabled) {
			for (FilterType filter : controllerPreference.getFilters().getFilter()) {
				if (filter.isEnabled() == enabled)
					return true;
			}
			return false;
		}
		return result;
	}

	/**
	 * Gets the column viewer filter.
	 *
	 * @param columnId
	 *            the column id
	 * @return the column viewer filter
	 */
	@SuppressWarnings("unchecked")
	public ColumnViewerFilter<T> getColumnViewerFilter(String columnId) {
		FiltersType filters = controllerPreference.getFilters();
		if (null == filters)
			return null;
		for (ViewerFilter filter : tabularController.getViewer().getFilters()) {
			if (filter instanceof ColumnViewerFilter
					&& columnId.equals(((ColumnViewerFilter<T>) filter).getColumnController().getColumnId()))
				return (ColumnViewerFilter<T>) filter;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.tabular.IControllerPreferenceManager#initialize()
	 */
	@Override
	public void initialize() {
		tabularController.invokeControlMethod(METHOD_NAME.setRedraw, false);
		tabularController.invokeControlMethod(METHOD_NAME.setColumnOrder,
				controllerPreference.getTabularColumnOrder(tabularController));

		if (null != controllerPreference.getColumnPreferences())
			for (ColumnPreferenceType columnPreference : controllerPreference.getColumnPreferences().getColumnPreference()) {
				AColumnController<?> columnController = tabularController.getColumnMap().get(columnPreference.getId());
				if (!columnPreference.isVisible())
					columnController.hide();
				else {
					columnController.reinitSize();
					columnController.setColumnWidth(columnPreference.getWidth());
					columnController.setResized(true);
				}
			}
		else {
			for (AColumnController<?> columnController : tabularController.getColumnMap().values()) {
				if (null != columnController.getControl())
					columnController.reinitSize();
			}
		}

		if (null != controllerPreference.getFilters()) {
			tabularController.getViewer().resetFilters();
			List<FilterType> filters = controllerPreference.getFilters().getFilter();
			for (FilterType filter : filters.toArray(new FilterType[filters.size()])) {
				AColumnController<T> columnController = tabularController.getColumnMap().get(filter.getColumn());
				if (null != columnController) {
					ColumnViewerFilter<T> columnViewerFilter = new ColumnViewerFilter<T>(columnController,
							filter.getSearchString());
					boolean exactString = filter.isExactString();
					boolean caseInsensitive = filter.isCaseInsensitive();
					try {
						columnViewerFilter.computePredicate(exactString, caseInsensitive);
						if (filter.isEnabled())
							addColumnFilter(columnViewerFilter);
					} catch (SecurityException | ParseException | NoSuchMethodException e) {
						logError(e);
					}
				}
				tabularController.refreshInput();
			}

		}
		tabularController.invokeControlMethod(METHOD_NAME.setRedraw, true);
	}

	/**
	 * Merge.
	 *
	 * @param mergePreference
	 *            the merge preference
	 */
	public void merge(ControllerPreferenceWrapper<T> mergePreference) {
		controllerPreference.setColumnOrder(mergePreference.getColumnOrder());
		controllerPreference.setColumnPreferences(mergePreference.getColumnPreferences());
		controllerPreference.setStatusBarKey(mergePreference.getStatusBarKey());
		controllerPreference.setTableRendererKey(mergePreference.getTableRendererKey());
		if (null != mergePreference.getFilters())
			controllerPreference.setFilters(mergePreference.getFilters());
	}

	/**
	 * Gets the customized controller preference map.
	 *
	 * @return the customized controller preference map
	 */
	public Map<String, ControllerPreferenceWrapper<T>> getCustomizedControllerPreferenceMap() {
		return controllerQueryPreferenceMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void injectControllerPreference(Object controllerPreference) {
		this.controllerPreference = getInitialControllerPreference().clone(null);
		merge((ControllerPreferenceWrapper<T>) controllerPreference);
	}

	/**
	 * Gets the active recent preference.
	 *
	 * @return the active recent preference
	 */
	public RecentPreferenceWrapper<T> getActiveRecentPreference() {
		return activeRecentPreference;
	}

	/**
	 * Sets the active recent preference.
	 *
	 * @param activeRecentPreference
	 *            the new active recent preference
	 */
	@SuppressWarnings("unchecked")
	public void setActiveRecentPreference(RecentPreferenceWrapper<?> activeRecentPreference) {
		this.activeRecentPreference = (RecentPreferenceWrapper<T>) activeRecentPreference;
	}

	/**
	 * Gets the preference title.
	 *
	 * @return the preference title
	 */
	public String getPreferenceTitle() {
		return null == activeRecentPreference ? "" : activeRecentPreference.getPreferenceTree().getTitle();
	}

	@SuppressWarnings("unchecked")
	public void addColumnPreferences(List<?> columnPreferences) {
		for (ColumnPreferenceType columnPreference : (List<ColumnPreferenceType>) columnPreferences) {
			AColumnController<?> columnController = tabularController.getColumnMap().get(columnPreference.getId());
			if (null == columnController)
				JPAUtil.getFromJpaBundle("preference.invalid.column.name", columnPreference.getId());
			else {
				if (!columnPreference.isVisible())
					columnController.hide();
				else {
					columnController.setColumnWidth(columnPreference.getWidth());
					columnController.setResized(true);
				}
			}
		}
	}

	public void addFilters(List<?> filters) {
		tabularController.getViewer().resetFilters();
		for (FilterType filter : filters.toArray(new FilterType[filters.size()])) {

			AColumnController<T> columnController = tabularController.getColumnMap().get(filter.getColumn());
			if (null != columnController) {
				ColumnViewerFilter<T> columnViewerFilter = new ColumnViewerFilter<T>(columnController, filter.getSearchString());
				boolean exactString = filter.isExactString();
				boolean caseInsensitive = filter.isCaseInsensitive();
				try {
					columnViewerFilter.computePredicate(exactString, caseInsensitive);
					if (filter.isEnabled())
						addColumnFilter(columnViewerFilter);
				} catch (SecurityException | ParseException | NoSuchMethodException e) {
					logError(e);
				}
			}
			tabularController.refreshInput();
		}
	}

	public String getTableRendererKey() {
		return controllerPreference.getTableRendererKey();
	}

	public String getStatusBarKey() {
		return controllerPreference.getStatusBarKey();
	}

	public void setStatusBarKey(String statusBarKey) {
		controllerPreference.setStatusBarKey(statusBarKey);
	}

	public void setTableRendererKey(String tableRendererKey) {
		controllerPreference.setTableRendererKey(tableRendererKey);
	}

	public void setColumnOrder(String columnOrder) {
		controllerPreference.setColumnOrder(columnOrder);
	}

	@Override
	public void setWidth(AColumnController<T> columnController, Integer width) {
		ColumnPreferenceType columnPreference = getColumnPreference(columnController.getColumnId());
		if (null != columnPreference) {
			columnPreference.setVisible(0 != width);
			columnPreference.setWidth(width);
		}
	}

	private ColumnPreferenceType getColumnPreference(String columnId) {
		if (null != controllerPreference.getColumnPreferences())
			for (ColumnPreferenceType columnPreference : controllerPreference.getColumnPreferences().getColumnPreference())
				if (columnPreference.getId().equals(columnId))
					return columnPreference;
		return null;
	}
}
