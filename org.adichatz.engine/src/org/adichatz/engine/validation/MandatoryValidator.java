package org.adichatz.engine.validation;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.IValidableController;
import org.eclipse.jface.dialogs.IMessageProvider;

public class MandatoryValidator extends AValidator {

	public MandatoryValidator(IValidableController triggeringController, String key) {
		super(triggeringController, key);
	}

	@Override
	public int validate() {
		Object fieldValue = ((AFieldController) triggeringController).getValue();
		if (null == fieldValue) {
			return setLevel(IMessageProvider.ERROR, AdichatzApplication.getInstance().getMessage(EngineConstants.ENGINE_BUNDLE,
					"adichatzEngine", "mandatoryField"));
		} else
			return setLevel(IMessageProvider.NONE, null);
	};
}
