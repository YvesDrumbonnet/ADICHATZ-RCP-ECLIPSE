package org.adichatz.engine.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class LimitedComposite extends Composite {

	public LimitedComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(SWT.DEFAULT, SWT.DEFAULT);
	}
}
