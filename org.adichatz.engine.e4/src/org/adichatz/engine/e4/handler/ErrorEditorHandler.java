package org.adichatz.engine.e4.handler;

import javax.inject.Named;

import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.extra.ErrorsFoundDialog;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;

public class ErrorEditorHandler extends AEditorHandler {
	@Execute
	void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, @Named(IServiceConstants.ACTIVE_PART) MPart mpart) {
		BoundedPart boundedPart = getBoundedPart(mpart);
		if (null != boundedPart)
			ErrorsFoundDialog.openConfirm(shell, boundedPart.getBindingServices());
	}
}