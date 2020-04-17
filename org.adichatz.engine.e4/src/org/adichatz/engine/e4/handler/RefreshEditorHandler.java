package org.adichatz.engine.e4.handler;

import javax.inject.Named;

import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;

public class RefreshEditorHandler extends AEditorHandler {
	@Execute
	void execute(@Named(IServiceConstants.ACTIVE_PART) MPart mpart) {
		BoundedPart boundedPart = getBoundedPart(mpart);
		if (null != boundedPart) {
			boundedPart.getBoundedPartChangeManager().doRefresh(true);
			E4AdichatzApplication.getInstance().enableToolBar(boundedPart);
		}
	}
}