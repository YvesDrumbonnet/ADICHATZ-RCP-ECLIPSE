package org.adichatz.engine.e4.part;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.controller.utils.ElementaryAccessibility.ACCESS_ATTRIBUTE;
import org.adichatz.engine.plugin.ParamMap;

public class BoundedPartFatalError {
	private static ElementaryAccessibility INVALID_ACCESSIBILITY = new ElementaryAccessibility(ACCESS_ATTRIBUTE.VALID, () -> {
		return false;
	});

	public static enum ERROR_TYPE {
		mainEntityNoLongerExists
	};

	private ERROR_TYPE errorType;

	private Runnable runnable;

	public BoundedPartFatalError(BoundedPart part, ERROR_TYPE errorType, Runnable runnable) {
		this.errorType = errorType;
		this.runnable = runnable;
		part.getInputPart().getParamMap().put(ParamMap.BOUNDED_PART_ERROR, this);
		switch (errorType) {
		case mainEntityNoLongerExists:
			if (canConsoleBeInhibited())
				// Inhibit console messages.
				AdiConsole.getInstance().initNullStreams();
			if (null != part.getGenCode())
				invalidControllers(part.getGenCode().getController());
			break;
		default:
			break;
		}
	}

	private boolean canConsoleBeInhibited() {
		String inhibitConsoleOnfatalError = (String) AdichatzApplication.getInstance()
				.getContextValue(EngineConstants.INHIBIT_CONSOLE_ON_FATAL_ERROR);
		return null != inhibitConsoleOnfatalError && "true".equals(inhibitConsoleOnfatalError.toLowerCase());

	}

	private void invalidControllers(ICollectionController parentController) {
		parentController.getAccessibilities().add(INVALID_ACCESSIBILITY);
		for (AWidgetController controller : parentController.getChildControllers()) {
			if (controller instanceof ICollectionController)
				invalidControllers((ICollectionController) controller);
			else
				controller.getAccessibilities().add(INVALID_ACCESSIBILITY);
		}
	}

	public void displayError() {
		switch (errorType) {
		case mainEntityNoLongerExists:
			LogBroker.getLogger().flush();
			runnable.run();
			if (canConsoleBeInhibited())
				// Reinit console stream.
				AdiConsole.getInstance().initStreams();
			break;
		default:
			break;
		}
	}
}
