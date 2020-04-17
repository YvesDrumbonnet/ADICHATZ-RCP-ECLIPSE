package org.adichatz.engine.extra;

import org.adichatz.engine.validation.ABindingService;

public abstract class ABindingOutlinePage extends AOutlinePage {
	@Override
	public boolean hasError() {
		return false;
	}

	@Override
	public ABindingService getBindingService() {
		return null;
	}
}
