/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue May 12 15:04:29 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.editor;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.TableColumnController;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.extra.ColumnViewerSorter;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.studio.xjc.controller.FeaturesTableController;

@AdiResourceURI(URI="adi://org.adichatz.studio/editor/ScenarioEditor")
public class ScenarioEditorParamsTable extends ATabularCore<ParamType> {
	// the TableColumnController idTCTC.
	private TableColumnController idTCTC;
	// the TableColumnController valueTCTC.
	private TableColumnController valueTCTC;
	public ScenarioEditorParamsTable(final AdiContext context, IContainerController parentController) {
		super(context);
		tabularController = new FeaturesTableController<ParamType>("paramsTable", parentController, this) {
			@Override
			public void createControl() {
				if (isValid()) {
					beanClass = ParamType.class;
				}
				super.createControl();
				if (isValid()) {
					ATabularContentProvider contentProvider$1 = new NativeContentProvider("param");
					contentProvider$1.setTabularController(tabularController);
					getControl().setLayoutData("h 0:n:n, w 0:n:n");
				}
			}
		};
		tabularController.setPluginResources(AdichatzApplication.getPluginResources("org.adichatz.studio"));
		coreController = tabularController;
		
		// Creates control for TableColumnController idTCTC
		idTCTC = new TableColumnController<ParamType>("idTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setImage(AdichatzApplication.getInstance().getImage("org.adichatz.studio", "xjc/param.png"));
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "element", "id"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(ParamType row) {
				return row.getId();
			}
			@Override
			public String getColumnText(ParamType row) {
				return row.getId();
			}
		};
		// Creates control for TableColumnController valueTCTC
		valueTCTC = new TableColumnController<ParamType>("valueTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "param", "value"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(ParamType row) {
				return row.getValue();
			}
			@Override
			public String getColumnText(ParamType row) {
				return row.getValue();
			}
		};
	}
	
	@Override
	public Object getId(Object element) {
		return null;
	}
}