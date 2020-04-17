package org.adichatz.studio.xjc.data;

import java.lang.reflect.Method;

import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.data.FieldBindingManager;

public class XjcFieldBindingManager extends FieldBindingManager {

	private ASetController setController;

	public XjcFieldBindingManager(AFieldController fieldController) {
		super(fieldController);
		this.setController = ((XjcEntity<?>) fieldController.getEntity()).getSetController();
	}

	/**
	 * Gets the sets the method.
	 * 
	 * @return the sets the method
	 */
	protected Method getSetMethod() {
		if (null == setMethod) {
			setMethod = FieldTools.getSetMethod(fieldController.getEntity().getBeanClass(), column, getField().getGetMethod()
					.getReturnType(), false);
			if (null == setMethod && getField().getGetMethod().getReturnType().equals(boolean.class))
				setMethod = FieldTools.getSetMethod(fieldController.getEntity().getBeanClass(), column, Boolean.class, true);
		}
		return setMethod;
	}

	@Override
	public void bindTargetToModel() {
		if (null != column) {
			XjcEntity<?> entity = (XjcEntity<?>) fieldController.getEntity();
			super.bindTargetToModel();
			if (null != setController) {
				XjcTreeElement treeElement = entity.getTreeElement();
				setController.update(entity, null == treeElement ? entity.getBean() : treeElement);
			}
		}
	}
}
