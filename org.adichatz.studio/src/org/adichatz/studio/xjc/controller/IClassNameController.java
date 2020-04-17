package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.controller.IControlController;
import org.adichatz.scenario.ScenarioResources;

public interface IClassNameController extends IControlController {
	public String getSuperTypeName();

	public ScenarioResources getScenarioResources();

	public void modifyValues(String value);

	public String getCurrentClassName();
}
