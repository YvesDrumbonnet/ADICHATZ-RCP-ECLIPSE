package org.adichatz.engine.e4.resource;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MAdvancedFactory;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class PerspectiveProcessor {
	/** The full perspective. */
	private static String PLAIN_PERSPECTIVE = "adichatz.perspective.plain";

	private static String TRIMMED_WINDOW = "adichatz.application.trimmedwindow";

	@Execute
	void init(MApplication application, EModelService modelService) {
		String contributorURI = application.getContributorURI();
		if ("platform:/plugin/org.eclipse.platform".equals(contributorURI)) {
			return; // Application is Main eclipse IDE (Normally occurs only for Adichatz Studio developers)
		}
		AdichatzApplication adichatzApplication = AdichatzApplication.getInstance();
		String gencodePackage = (String) adichatzApplication.getContextValue(EngineConstants.GENCODE_PACKAGE);
		if (null == gencodePackage)
			gencodePackage = "gencode";
		adichatzApplication.getPluginMap().put(EngineConstants.ENGINE_E4_BUNDLE, new AdiPluginResources(
				EngineConstants.ENGINE_E4_BUNDLE, EngineConstants.ENGINE_E4_BUNDLE, gencodePackage, getClass().getClassLoader()));

		MPerspectiveStack perspectiveStack = (MPerspectiveStack) modelService.find(EngineE4Util.PERSPECTIVE_STACK, application);
		MWindow window = (MWindow) modelService.find(TRIMMED_WINDOW, application);

		if (null != window && null == perspectiveStack) { // Only in E4 environment and when workspace is blank.
			MBasicFactory basicFactory = MBasicFactory.INSTANCE;
			MPartSashContainer globalPSC = basicFactory.createPartSashContainer();
			window.getChildren().add(globalPSC);
			globalPSC.setHorizontal(true);

			perspectiveStack = MAdvancedFactory.INSTANCE.createPerspectiveStack();
			perspectiveStack.setElementId(EngineE4Util.PERSPECTIVE_STACK);

			globalPSC.getChildren().add(perspectiveStack);

			perspectiveStack.getChildren()
					.add(new ConsolePerspectiveManager(application, window, EngineConstants.CONSOLE_PERSPECTIVE).getPerspective());
			perspectiveStack.getChildren()
					.add(new PlainPerspectiveManager(application, window, PLAIN_PERSPECTIVE).getPerspective());
		}
		adichatzApplication.getApplContext().put(MPerspectiveStack.class.getName(), perspectiveStack);
	}
}
