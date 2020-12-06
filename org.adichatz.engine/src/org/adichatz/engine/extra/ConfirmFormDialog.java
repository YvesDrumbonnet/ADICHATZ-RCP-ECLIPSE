package org.adichatz.engine.extra;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

public class ConfirmFormDialog extends AFormDialog {
	private static boolean result;

	protected String title;

	protected Image image;

	protected IConfirmContent confirmContent;

	protected List<Control> complementControls = new ArrayList<Control>();

	public ConfirmFormDialog(Shell shell, String title, Image image, IConfirmContent confirmContent) {
		super(shell);
		this.title = title;
		this.image = image;
		this.confirmContent = confirmContent;
		setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
	}

	@Override
	protected void createFormContent() throws AdichatzErrorException {
		getShell().setText(title);
		getShell().setImage(image);
		scrolledForm.setText(title);
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "grow,fill", "[]"));

		Composite parent = toolkit.createComposite(scrolledForm.getBody());

		confirmContent.createConfirmContent(parent, toolkit, complementControls);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		postCreateContents();
		AdichatzApplication.getInstance().reapplyStyles(scrolledForm);
		return control;
	}

	protected void postCreateContents() {
		scrolledForm.reflow(true);
		getButton(IDialogConstants.CANCEL_ID).setFocus();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			confirmContent.okPressed(complementControls);
			okPressed();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	public static boolean check(final Display display, final String title, final Image image,
			final IConfirmContent confirmContent) {
		result = false;
		display.syncExec(() -> {
			ConfirmFormDialog confirmFormDialog = new ConfirmFormDialog(display.getActiveShell(), title, image, confirmContent);
			if (Window.OK == confirmFormDialog.open())
				result = true;
		});
		return result;
	}

	public ScrolledForm getScrolledForm() {
		return scrolledForm;
	}

	@Override
	public Button getButton(int id) {
		return super.getButton(id);
	}
}
