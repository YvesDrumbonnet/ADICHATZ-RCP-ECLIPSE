/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 08:03:24 CEST 2020
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
import org.adichatz.generator.xjc.ControllerType;
import org.adichatz.studio.xjc.controller.FeaturesTableController;

@AdiResourceURI(URI="adi://org.adichatz.studio/editor/ScenarioEditor")
public class ScenarioEditorControllersTable extends ATabularCore<ControllerType> {
	// the TableColumnController wrapperClassNameTCTC.
	private TableColumnController wrapperClassNameTCTC;
	// the TableColumnController controllerClassNameTCTC.
	private TableColumnController controllerClassNameTCTC;
	public ScenarioEditorControllersTable(final AdiContext context, IContainerController parentController) {
		super(context);
		tabularController = new FeaturesTableController<ControllerType>("controllersTable", parentController, this) {
			@Override
			public void createControl() {
				if (isValid()) {
					beanClass = ControllerType.class;
				}
				super.createControl();
				if (isValid()) {
					ATabularContentProvider contentProvider$1 = new NativeContentProvider("controller");
					contentProvider$1.setTabularController(tabularController);
					getControl().setLayoutData("h 0:n:n, w 0:n:n");
				}
			}
		};
		tabularController.setPluginResources(AdichatzApplication.getPluginResources("org.adichatz.studio"));
		coreController = tabularController;
		
		// Creates control for TableColumnController wrapperClassNameTCTC
		wrapperClassNameTCTC = new TableColumnController<ControllerType>("wrapperClassNameTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setImage(AdichatzApplication.getInstance().getImage("org.adichatz.studio", "xjc/controlField.png"));
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controller", "wrapperClassName"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(ControllerType row) {
				return row.getWrapperClassName();
			}
			@Override
			public String getColumnText(ControllerType row) {
				return row.getWrapperClassName();
			}
		};
		// Creates control for TableColumnController controllerClassNameTCTC
		controllerClassNameTCTC = new TableColumnController<ControllerType>("controllerClassNameTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controller", "controllerClassName"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(ControllerType row) {
				return row.getControllerClassName();
			}
			@Override
			public String getColumnText(ControllerType row) {
				return row.getControllerClassName();
			}
		};
	}
	
	@Override
	public Object getId(Object element) {
		return null;
	}
}