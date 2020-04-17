package org.adichatz.engine.extra;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AdiMessageDialog extends AMessageDialog {
	public AdiMessageDialog(Display display, int kind, Image labelImage, String... messages) {
		this(display.getActiveShell(), kind, new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, labelImage,
				messages);
	}

	public AdiMessageDialog(Shell shell, int kind, String[] dialogButtonLabels, Image labelImage, String... messages) {
		super(shell, kind, dialogButtonLabels, labelImage, messages);
	}

	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Composite buttonBar = toolkit.createComposite(parent);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 10;
		gridLayout.marginHeight = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		buttonBar.setLayout(gridLayout);
		buttonBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		createButtonsForButtonBar(buttonBar);
		return buttonBar;
	}

	@Override
	protected void createButtonsForButtonBar(Composite buttonBar) {
		// create OK and Cancel buttons by default
		Button button = createButton(buttonBar, IDialogConstants.OK_ID, dialogButtonLabels[0], true);
		switch (kind) {
		case MessageDialog.CONFIRM:
			button = createButton(buttonBar, IDialogConstants.CANCEL_ID, dialogButtonLabels[1], false);
			break;
		}
		button.setFocus();
	}

}
