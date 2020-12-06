/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 18:46:55 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.query;

import net.miginfocom.swt.MigLayout;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.TableColumnController;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.jpa.query.custom.FilterTableController;
import org.adichatz.jpa.xjc.FilterType;

@AdiResourceURI(URI="adi://org.adichatz.jpa/query/QueryToolContainer")
public class QueryToolContainerFilterTable extends ATabularCore<FilterType> {
	// the TableColumnController textTCTC.
	private TableColumnController textTCTC;
	public QueryToolContainerFilterTable(final AdiContext context, IContainerController parentController) {
		super(context);
		tabularController = new FilterTableController<FilterType>("filterTable", parentController, this) {
			@Override
			public void createControl() {
				if (isValid()) {
					beanClass = FilterType.class;
				}
				super.createControl();
				if (isValid()) {
					getComposite().setLayout(new MigLayout("ins 0, wrap 1","grow, fill","[grow, fill]"));
				}
			}
		};
		tabularController.setPluginResources(AdichatzApplication.getPluginResources("org.adichatz.jpa"));
		coreController = tabularController;
		
		// Creates control for TableColumnController textTCTC
		textTCTC = new TableColumnController<FilterType>("textTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.jpa", "viewerFilter", "text"));
				}
			}
			@Override
			public String getValue(FilterType row) {
				return row.getText();
			}
			@Override
			public String getColumnText(FilterType row) {
				return row.getText();
			}
		};
	}
	
	@Override
	public Object getId(Object element) {
		return null;
	}
}