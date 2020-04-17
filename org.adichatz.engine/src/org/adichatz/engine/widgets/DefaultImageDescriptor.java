package org.adichatz.engine.widgets;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

public class DefaultImageDescriptor extends AdiImageDescriptor {

	public DefaultImageDescriptor() {
		super((Bundle) null, (String) null);
	}

	public Image createImage(boolean returnMissingImageOnError, Device device) {
		return new Image(Display.getCurrent(), DEFAULT_IMAGE_DATA);
	}

	public ImageData getImageData() {
		return DEFAULT_IMAGE_DATA;
	}

}
