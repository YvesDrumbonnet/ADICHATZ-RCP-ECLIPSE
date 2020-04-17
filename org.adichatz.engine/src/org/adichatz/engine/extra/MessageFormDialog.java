package org.adichatz.engine.extra;

import org.adichatz.engine.common.AdichatzApplication;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MessageFormDialog extends ConfirmFormDialog {

	public MessageFormDialog(Shell shell, String title, Image image, IConfirmContent confirmContent) {
		super(shell, AdichatzApplication.getInstance().getFormToolkit(), title, image, confirmContent);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	public static void openInfo(final Display display, final String title, final Image image,
			final IConfirmContent confirmContent) {
		display.syncExec(() -> {
			new MessageFormDialog(display.getActiveShell(), title, image, confirmContent).open();
		});
	}
}
