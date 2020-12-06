package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class AChooserDialog extends ConfirmFormDialog {

	protected AdiResourceBundle resourceBundle;

	protected boolean isColumn;

	protected Text resultText;

	protected AChooserController chooserController;

	public AChooserDialog(Shell shell, String messageBundle, String imageStr, AChooserController chooserController) {
		super(shell, null, AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, imageStr), null);
		this.chooserController = chooserController;
		resourceBundle = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getResourceBundleManager()
				.getResourceBundle(messageBundle);
		title = resourceBundle.getString("title");
		isColumn = chooserController.getEntity().getBean() instanceof ColumnFieldType;
		confirmContent = getConfirmContent();
	}

	@Override
	public void create() {
		super.create();
		Point originalLocation = chooserController.getFindButton().toDisplay(0, 0);
		if (null != originalLocation) {
			Point size = getShell().getSize();
			Point newPoint = new Point(Math.max(originalLocation.x - size.x, 0), Math.max(originalLocation.y - size.y, 0));
			getShell().setLocation(newPoint);
		}
	}

	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			confirmContent.okPressed(complementControls);
			okPressed();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	protected String getReturn(String value) {
		return isColumn ? "return ".concat(value).concat(";") : value;
	}

	protected abstract IConfirmContent getConfirmContent();

}
