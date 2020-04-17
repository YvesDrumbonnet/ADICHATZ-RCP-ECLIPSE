package org.adichatz.engine.widgets.supplement;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ButtonItem extends Label {
	public ButtonItem(Composite parent, int style) {
		super(parent, style);
		setLayoutData("w 16!,h 16!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Widget#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Button#computeSize(int, int, boolean)
	 */
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return super.computeSize(16, 16, changed);
	}

	public void addSelectionListener(final Runnable runnable) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.x < 17 && e.y < 17)
					runnable.run();
			}
		});
	}
}
