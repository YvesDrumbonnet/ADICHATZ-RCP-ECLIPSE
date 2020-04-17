package org.adichatz.engine.e4.handler;

import javax.inject.Named;

import org.adichatz.engine.e4.part.BoundedPart;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class SaveEditorHandler extends AEditorHandler {
	@CanExecute
	public boolean canExecute(MApplication application, IEclipseContext context, @Optional MItem callingItem) {
		if (null != callingItem)
			return callingItem.isEnabled();
		MItem item = (MItem) context.get(EModelService.class).find("adichatz.editor.save.toolItem", application);
		return item.isVisible() && item.isEnabled();
	}

	@Execute
	void execute(@Named(IServiceConstants.ACTIVE_PART) MPart mpart) {
		BoundedPart boundedPart = getBoundedPart(mpart);
		if (null != boundedPart)
			boundedPart.getBoundedPartChangeManager().doSave();
	}
}