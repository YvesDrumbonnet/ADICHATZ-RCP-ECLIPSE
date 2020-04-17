package org.adichatz.engine.widgets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.LogBroker;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

public class AdiImageDescriptor extends ImageDescriptor {

	private ImageData imageData;

	private Bundle bundle;

	private String imageHome;

	private String imageName;

	private ImageData maskData;

	public AdiImageDescriptor(Bundle bundle, String imageName) {
		this.bundle = bundle;
		this.imageName = imageName;
	}

	public AdiImageDescriptor(String imageHome, String imageName) {
		this.imageHome = imageHome;
		this.imageName = imageName;
	}

	public Image createImage(boolean returnMissingImageOnError, Device device) {
		if (null == imageData) {
			imageData = getImageData();
			/*
			 * Try to create the supplied image. If there is an SWT Exception try and create the default image if that was
			 * requested. Return null if this fails.
			 */
			if (imageData.transparentPixel >= 0)
				maskData = imageData.getTransparencyMask();
		}
		Display display = Display.getCurrent();
		try {
			if (null != maskData)
				return new Image(display, imageData, maskData);
			return new Image(display, imageData);
		} catch (SWTException exception) {
			try {
				return new Image(display, DEFAULT_IMAGE_DATA);
			} catch (SWTException nextException) {
				LogBroker.logError(nextException);
				return null;
			}
		}
	}

	@Override
	public ImageData getImageData() {
		InputStream inputStream;
		try {
			if (null != bundle) {
				URL url = bundle.getEntry(EngineConstants.ICON_FILES_PATH.concat(imageName));
				if (null == url)
					return DEFAULT_IMAGE_DATA;
				inputStream = url.openStream();
			} else
				inputStream = new FileInputStream(new File(imageHome.concat(imageName)));
			ImageData imageData = new ImageData(inputStream);
			inputStream.close();
			return imageData;
		} catch (IOException e) {
			return DEFAULT_IMAGE_DATA;
		}
	}

}
