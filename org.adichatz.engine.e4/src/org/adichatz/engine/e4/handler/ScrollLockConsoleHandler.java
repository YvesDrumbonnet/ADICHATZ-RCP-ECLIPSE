package org.adichatz.engine.e4.handler;

import org.adichatz.engine.e4.part.AdiConsole;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.PartImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

@SuppressWarnings("restriction")
public class ScrollLockConsoleHandler {
	@Execute
	public void execute(EModelService modelService, MWindow window) {
		PartImpl partImpl = (PartImpl) modelService.find("adichatz.console", window); //$NON-NLS-1$
		((AdiConsole) partImpl.getObject()).scrollLock();
	}

}
