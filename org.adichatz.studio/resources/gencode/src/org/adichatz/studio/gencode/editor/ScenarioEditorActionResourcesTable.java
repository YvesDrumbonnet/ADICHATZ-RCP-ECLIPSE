/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Fri Feb 07 15:54:09 CET 2020
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
import org.adichatz.generator.xjc.CopyResourceType;
import org.adichatz.generator.xjc.RemoveResourceType;
import org.adichatz.studio.xjc.controller.FeaturesTableController;
import org.eclipse.swt.graphics.Image;

@AdiResourceURI(URI="adi://org.adichatz.studio/editor/ScenarioEditor")
public class ScenarioEditorActionResourcesTable extends ATabularCore<RemoveResourceType> {
	// the TableColumnController actionWhenTCTC.
	private TableColumnController actionWhenTCTC;
	// the TableColumnController actionTypeTCTC.
	private TableColumnController actionTypeTCTC;
	// the TableColumnController targetURITCTC.
	private TableColumnController targetURITCTC;
	// the TableColumnController sourceURITCTC.
	private TableColumnController sourceURITCTC;
	public ScenarioEditorActionResourcesTable(final AdiContext context, IContainerController parentController) {
		super(context);
		tabularController = new FeaturesTableController<RemoveResourceType>("actionResourcesTable", parentController, this) {
			@Override
			public void createControl() {
				if (isValid()) {
					beanClass = RemoveResourceType.class;
				}
				super.createControl();
				if (isValid()) {
					ATabularContentProvider contentProvider$1 = new NativeContentProvider("allActionResources");
					contentProvider$1.setTabularController(tabularController);
					getControl().setLayoutData("h 0:n:n, w 0:n:n");
				}
			}
		};
		tabularController.setPluginResources(AdichatzApplication.getPluginResources("org.adichatz.studio"));
		coreController = tabularController;
		
		// Creates control for TableColumnController actionWhenTCTC
		actionWhenTCTC = new TableColumnController<RemoveResourceType>("actionWhenTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "removeResource", "actionWhen"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public Object getValue(RemoveResourceType row) {
				return row.getActionWhen();
			}
			@Override
			public String getColumnText(RemoveResourceType row) {
				return  null == row.getActionWhen() ? null : row.getActionWhen().value();
			}
		};
		// Creates control for TableColumnController actionTypeTCTC
		actionTypeTCTC = new TableColumnController<RemoveResourceType>("actionTypeTC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setImage(AdichatzApplication.getInstance().getImage("org.adichatz.studio", "xjc/action.png"));
				}
			}
			@Override
			public Image getColumnImage(RemoveResourceType row) {
				if (row instanceof org.adichatz.generator.xjc.CopyResourceType)
				    return AdichatzApplication.getInstance().getImage("org.adichatz.studio", "IMG_COPY.png");
				else
				    return AdichatzApplication.getInstance().getImage("org.adichatz.studio", "IMG_DELETE.gif");
			}
			@Override
			public String getValue(RemoveResourceType row) {
				return getColumnText(row);
			}
			@Override
			public String getColumnText(RemoveResourceType row) {
				if (row instanceof CopyResourceType)
				    return AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "action.resource.copy");
				else
				    return AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "action.resource.remove");
			}
		};
		// Creates control for TableColumnController targetURITCTC
		targetURITCTC = new TableColumnController<RemoveResourceType>("targetURITC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getColumn().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "removeResource", "targetURI"));
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(RemoveResourceType row) {
				return row.getTargetURI();
			}
			@Override
			public String getColumnText(RemoveResourceType row) {
				return row.getTargetURI();
			}
		};
		// Creates control for TableColumnController sourceURITCTC
		sourceURITCTC = new TableColumnController<RemoveResourceType>("sourceURITC", tabularController) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					setColumnViewerSorter(ColumnViewerSorter.ASC);
				}
			}
			@Override
			public String getValue(RemoveResourceType row) {
				return getColumnText(row);
			}
			@Override
			public String getColumnText(RemoveResourceType row) {
				return row instanceof CopyResourceType ? ((CopyResourceType) row).getSourceURI() : "";
			}
		};
	}
	
	@Override
	public Object getId(Object element) {
		return null;
	}
}