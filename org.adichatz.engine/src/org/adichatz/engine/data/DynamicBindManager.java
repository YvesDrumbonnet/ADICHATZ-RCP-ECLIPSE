package org.adichatz.engine.data;

import java.lang.reflect.Method;

import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.controller.AFieldController;

public class DynamicBindManager extends FieldBindingManager {

	public DynamicBindManager(AFieldController fieldController) {
		super(fieldController);
	}

	protected Method getSetMethod() {
		Method getGetMethod = FieldTools.getGetMethod(fieldController.getEntity().getBeanClass(), column, true);
		return FieldTools.getSetMethod(fieldController.getEntity().getBeanClass(), column, getGetMethod.getReturnType(), true);
	}

}
