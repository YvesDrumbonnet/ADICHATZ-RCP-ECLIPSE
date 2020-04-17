/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Fri Feb 21 14:35:37 CET 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.query;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.controller.field.TableColumnController;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.extra.ColumnViewerSorter;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.jpa.xjc.ColumnPreferenceType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

@AdiResourceURI(URI="adi://org.adichatz.jpa/query/QueryToolContainer")
public class QueryToolContainerColumnPreferenceTable extends ATabularCore<ColumnPreferenceType> {
	// the TableColumnController visibleTCTC.
	private TableColumnController visibleTCTC;
	// the TableColumnController columnTitleTCTC.
	private TableColumnController columnTitleTCTC;
	// the TableColumnController widthTCTC.
	private TableColumnController widthTCTC;
	public QueryToolContainerColumnPreferenceTable(final AdiContext context, IContainerController parentController) {
		super(context);
		tabularController = new TableController<ColumnPreferenceType>("columnPreferenceTable", parentController, this) {
			@Override
			public void createControl() {
				if (isValid()) {
					beanClass = ColumnPreferenceType.class;
				}
				super.createControl();
				if (isValid()) {
					ATabularContentProvider contentProvider$1 = new NativeContentProvider("columnPrefs");
					contentProvider$1.setTabularController(tabularController);
					getControl().setLayoutData("h 100!");
				}
			}
			@Override
			public void endLifeCycle() {
				delegateAfterEndLifeCycleListener = true;
				super.endLifeCycle();
				if (isValid()) {
					refresh();
				}
			}
		};
		tabularController.setPluginResources(AdichatzApplication.getPluginResources("org.adichatz.jpa"));
		coreController = tabularController;
		
		// Creates control for TableColumnController visibleTCTC
		visibleTCTC = new TableColumnController<ColumnPreferenceType>("visibleTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "columnPreference", "visible"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public Image getColumnImage(ColumnPreferenceType row) {
			return row.isVisible() ? EngineTools.getCheckedImage() : EngineTools.getUncheckedImage();
			}
			@Override
			public Boolean getValue(ColumnPreferenceType row) {
				return Boolean.valueOf(row.isVisible());
			}
			@Override
			public String getColumnText(ColumnPreferenceType row) {
				return null;
			}
		};
		// Creates control for TableColumnController columnTitleTCTC
		columnTitleTCTC = new TableColumnController<ColumnPreferenceType>("columnTitleTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "adichatzJpa", "preference.column"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(ColumnPreferenceType row) {
				return getColumnText(row);
			}
			@Override
			public String getColumnText(ColumnPreferenceType row) {
				AColumnController columnController =  (AColumnController) ((QueryToolInput) ((AWidgetController) getFromRegister("queryTool")).getEntity().getBean()).getTabularController().getColumnMap().get(row.getId());
				return null == columnController ? "" : columnController.getText();
			}
		};
		// Creates control for TableColumnController widthTCTC
		widthTCTC = new TableColumnController<ColumnPreferenceType>("widthTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "columnPreference", "width"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public Integer getValue(ColumnPreferenceType row) {
				return Integer.valueOf(row.getWidth());
			}
			@Override
			public int getStyle() {
				return SWT.RIGHT;
			}
			@Override
			public String getColumnText(ColumnPreferenceType row) {
				return String.valueOf(row.getWidth());
			}
		};
	}
	
	@Override
	public Object getId(Object element) {
		return null;
	}
}