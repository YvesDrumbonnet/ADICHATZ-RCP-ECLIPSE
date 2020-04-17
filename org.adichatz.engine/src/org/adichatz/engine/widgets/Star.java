/*******************************************************************************
 * Copyright (c) 2012 Laurent CARON.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Laurent CARON (laurent.caron@gmail.com) - initial API and implementation
 *******************************************************************************/
package org.adichatz.engine.widgets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Instances of this class represent a star displayed by the StarRating component
 */
class Star {
	boolean hover;

	boolean marked;

	Rectangle bounds;

	Image defaultImage;

	Image hoverImage;

	Image selectedImage;

	Image selectedHoverImage;

	Image currentImage;

	private StarRating parent;

	void dispose() {
		defaultImage.dispose();
		hoverImage.dispose();
		selectedImage.dispose();
		selectedHoverImage.dispose();
	}

	void draw(final GC gc, final int x, final int y) {
		if (!parent.isEnabled()) {
			currentImage = defaultImage;
		} else {
			if (marked) {
				if (hover) {
					currentImage = selectedHoverImage;
				} else {
					currentImage = selectedImage;
				}
			} else {
				if (hover) {
					currentImage = hoverImage;
				} else {
					currentImage = defaultImage;
				}
			}
		}

		gc.drawImage(currentImage, x, y);
		bounds = new Rectangle(x, y, currentImage.getBounds().width, currentImage.getBounds().height);
	}

	void partialDraw(final int x, final int y, int dw) {
		GC gc = new GC(currentImage);
		Image image = defaultImage;
		gc.drawImage(image, x, y);
	}
}
