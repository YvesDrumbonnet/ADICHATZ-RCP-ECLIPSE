package org.adichatz.engine.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

public class CompositeBag extends Composite {

	private Composite currentComposite;

	private Composite defaultComposite;

	private SharedScrolledComposite scrolledComposite;

	private Composite content;

	public CompositeBag(Composite parent, int style) {
		super(parent, style);

		scrolledComposite = new SharedScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				return new Point(SWT.DEFAULT, SWT.DEFAULT);
			}
		};
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		content = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(content);
		defaultComposite = new Composite(content, SWT.NONE);
		currentComposite = defaultComposite;
	}

	public void showComposite(Composite composite) {
		if (null == composite)
			showComposite(defaultComposite);
		else {
			if (!composite.isDisposed() && !composite.equals(currentComposite)) {
				if (null != currentComposite)
					currentComposite.setVisible(false);
				currentComposite = composite;
				currentComposite.setVisible(true);
			}
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(SWT.DEFAULT, SWT.DEFAULT);
	}

	public Composite getContent() {
		return content;
	}

	public SharedScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}

	public Composite getDefaultComposite() {
		return defaultComposite;
	}
}
