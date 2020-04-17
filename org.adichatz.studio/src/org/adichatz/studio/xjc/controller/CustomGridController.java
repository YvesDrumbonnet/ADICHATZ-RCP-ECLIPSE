package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.nebula.GridController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.generator.xjc.CustomizedScenarioType;
import org.adichatz.studio.util.StudioUtil;

public class CustomGridController<T> extends GridController<T> {
	public CustomGridController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	public void selectOutlinePage() {
		Object selectedObject = getSelectedObject();
		if (null != selectedObject)
			StudioUtil.validateCustomizationFile(((CustomizedScenarioType) selectedObject).getScenarioFile(), genCode);
	}

	@Override
	public void endLifeCycle() {
		delegateAfterEndLifeCycleListener = true;
		super.endLifeCycle();
		if (isValid()) {
			if (null != getEntity())
				// return scenarioTree.getCustomizedScenarios().getCustomizedScenario() list
				refresh();
			else
				// parent entity could be null when scenarioTree.customizedScenarios is null
				getControl().disposeAllItems();
			AListener.fireListener(listenerMap, IEventType.AFTER_END_LIFE_CYCLE);
		}
	}
}
