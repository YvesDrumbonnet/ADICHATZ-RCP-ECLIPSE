package org.adichatz.studio.xjc.controller;

import java.util.Arrays;
import java.util.List;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.RadioGroupController;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.jface.viewers.LabelProvider;

public class Boolean2RadioGroupController extends RadioGroupController {

	public Boolean2RadioGroupController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		labelProvider = new LabelProvider() {
			public String getText(Object element) {
				return (String) getDisplayedValues().get(getValues().indexOf(element));
			}
		};

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getDisplayedValues() {
		return Arrays.asList("true", "false");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getValues() {
		return Arrays.asList(true, false);
	}
}
