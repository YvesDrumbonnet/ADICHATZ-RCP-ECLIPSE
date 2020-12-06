package org.adichatz.engine.extra;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public abstract class AFormInputDialog extends AFormDialog {

	protected String title;

	protected Image image;

	protected AdiFormInput formInput;

	public AFormInputDialog(Shell shell, String title, Image image, AdiFormInput formInput) {
		super(shell);
		setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
		this.title = title;
		this.image = image;
		this.formInput = formInput;
		if (null != formInput)
			formInput.setWindow(this);
	}

	@Override
	protected void createFormContent() {
		getShell().setText(title);
		getShell().setImage(image);
		scrolledForm.setText(title);

		postCreateContent();
	}

	protected abstract void postCreateContent();
}
