package org.adichatz.engine.extra;

import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Layout;

public abstract class ARecentOutlineItem {
	/** The toolkit. */
	protected AdiFormToolkit toolkit;

	protected Layout layout;

	protected Image image;

	protected String text;

	protected PShelfItem pshelfItem;

	protected boolean firstSelection = true;

	public Layout getLayout() {
		return layout;
	}

	public Image getImage() {
		return image;
	}

	public String getText() {
		return text;
	}

	public PShelfItem getPshelfItem() {
		return pshelfItem;
	}

	public void setPshelfItem(PShelfItem pshelfItem) {
		this.pshelfItem = pshelfItem;
	}

	public abstract void init();

	public abstract void selectItem(SelectionEvent e);

	public abstract void refresh();
}
