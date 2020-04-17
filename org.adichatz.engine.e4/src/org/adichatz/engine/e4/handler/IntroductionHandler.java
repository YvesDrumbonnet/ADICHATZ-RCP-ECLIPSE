package org.adichatz.engine.e4.handler;

import org.adichatz.engine.e4.part.IntroPart;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

public class IntroductionHandler {

	@Execute
	void execute(final EPartService partService) {
		partService.showPart(IntroPart.ID, PartState.ACTIVATE);
	}
}
