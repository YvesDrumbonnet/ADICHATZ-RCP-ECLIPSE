package org.adichatz.engine.widgets;

import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.nebula.widgets.pgroup.PGroupToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

public class AdiPGroupToolItem extends PGroupToolItem {

	private Image disabledImage;

	private Image image;

	private boolean enabled = true;

	public AdiPGroupToolItem(PGroup parent, int style) {
		super(parent, style);
	}

	@Override
	public void setImage(Image image) {
		if (null == image) {
			disabledImage = null;
		} else if (!image.equals(this.image))
			disabledImage = new Image(image.getDevice(), image, SWT.IMAGE_DISABLE);
		this.image = image;
		super.setImage(enabled ? image : disabledImage);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		checkWidget();
		if (this.enabled != enabled) {
			this.enabled = enabled;
			super.setImage(enabled ? image : disabledImage);
		}
	}
}
