package org.adichatz.engine.e4.part;

import javax.inject.Inject;

import org.eclipse.e4.core.services.log.Logger;

@SuppressWarnings("restriction")
public class AAdiBasicPart {
	/** The logger. */
	@Inject
	protected Logger logger;

	/**
	 * Checks if is enabled.
	 * 
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Sets the enabled.
	 * 
	 * @param enabled
	 *            the new enabled
	 */
	public void setEnabled(boolean enabled) {
	}

}
