package org.adichatz.engine.e4.handler;

import javax.inject.Named;

import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.SaveOrAbortChangesDialog;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.IDialogConstants;

/**
 * The Class CloseEditorHandler.
 *
 * @author Yves Drumbonnet
 */
public class CloseEditorHandler extends AEditorHandler {
	@CanExecute
	public boolean canExecute(MApplication application, IEclipseContext context, @Optional MItem callingItem) {
		return true;
	}

	@Execute
	void execute(EPartService partSerice, @Named(IServiceConstants.ACTIVE_PART) MPart mpart) {
		// See Key binding M1+W
		BoundedPart boundedPart = getBoundedPart(mpart);
		if (null != boundedPart) {
			if (boundedPart.isDirty()) {
				int saveReturn = new SaveOrAbortChangesDialog(partSerice, boundedPart).open();
				switch (saveReturn) {
				case IDialogConstants.YES_ID:
					boundedPart.getBoundedPartChangeManager().doSave();
					boundedPart.close();
					break;
				case IDialogConstants.NO_ID:
					boundedPart.getBoundedPartChangeManager().doRefresh(false);
					boundedPart.close();
					break;
				default:
				}
			} else
				boundedPart.close();
		}
	}
}