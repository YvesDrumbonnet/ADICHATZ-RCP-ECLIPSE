package org.adichatz.engine.tabular;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

public class ShowColumnMenuManagerController<T> extends MenuManagerController {

	private ATabularController<T> tabularController;

	@SuppressWarnings("unchecked")
	public ShowColumnMenuManagerController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		MenuManagerController menuManagerController = (MenuManagerController) getParentController();
		tabularController = (ATabularController<T>) menuManagerController.getMenuContainer();
	}

	@Override
	public void createControl() {
		super.createControl();
		setImageDescriptor(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_SHOW_COLUMN"));
		setText(getFromEngineBundle("query.show.columns"));
		super.createControl();
		Action action;
		for (final AColumnController<T> columnController : tabularController.getColumnMap().values())
			if (null != columnController.getWidth() && 0 == columnController.getWidth()) {
				action = new Action(columnController.getText(), SWT.CHECK) {
					public void runWithEvent(Event event) {
						Integer width = (Integer) columnController.getData(EngineConstants.RESTORED_WIDTH);
						ControllerPreferenceManager<T> controllerPreferenceManager = columnController.getParentController()
								.getControllerPreferenceManager();
						controllerPreferenceManager.setWidth(columnController, width);
						columnController.setColumnWidth(width);
						if (SWT.DEFAULT == width) {
							columnController.pack();
							columnController.setResized(false);
						} else {
							columnController.setResized(true);
						}
						columnController.setResizable(true);
						columnController.setWidth(width);
					}
				};
				action.setChecked(null == columnController.getWidth() || 0 < columnController.getWidth());
				getControl().add(action);
			}
	}

	@Override
	public boolean isValid() {
		if (!super.isValid())
			return false;
		for (AColumnController<?> columnController : tabularController.getColumnMap().values())
			if (null != columnController.getWidth() && 0 == columnController.getWidth())
				return true;
		return false;
	}

}
