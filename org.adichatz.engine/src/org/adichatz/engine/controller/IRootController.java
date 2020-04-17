package org.adichatz.engine.controller;

import org.adichatz.engine.extra.OutlineEvent;
import org.eclipse.swt.graphics.Image;

public interface IRootController extends IBoundedController {
	public Image getImage();

	/**
	 * Fire outline listener.
	 *
	 * @param eventType
	 *            the event type
	 */
	public void fireOutlineListener(OutlineEvent event);

}
