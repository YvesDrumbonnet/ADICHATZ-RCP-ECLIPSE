package org.adichatz.engine.e4.resource;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MAdvancedFactory;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class PerspectiveProcessor {
	/** The full perspective. */
	private static String PLAIN_PERSPECTIVE = "adichatz.perspective.plain";

	public static String CONSOLE_PERSPECTIVE = "adichatz.perspective.console";

	public static String NAVIGATOR_STACK = "adichatz.navigator.stack";

	public static String PERSPECTIVE_STACK = "adichatz.perspective.stack";

	private static String TRIMMED_WINDOW = "adichatz.application.trimmedwindow";

	public static String EDITOR_PARTSTACK = "adichatz.editor.partstack";

	public static String OUTLINE_PARTSTACK = "adichatz.outline.partstack";

	public static String PERSPECTIVE_MANAGER = "adichatz.perspective.manager";

	public static String ACTIVE_PERSPECTIVE_ID = "adichatz.actrive.perspective.id";

	public static String MIN_MAX_MANAGER = "adichatz.min_max.manager";

	@Execute
	void init(MApplication application, EModelService modelService) {
		String contributorURI = application.getContributorURI();
		if ("platform:/plugin/org.eclipse.platform".equals(contributorURI)) {
			return; // Application is Main eclipse IDE (Normally occurs only for Adichatz Studio developers)
		}
		AdichatzApplication adichatzApplication = AdichatzApplication.getInstance();
		String gencodePackage = (String) adichatzApplication.getParam(EngineConstants.GENCODE_PACKAGE);
		if (null == gencodePackage)
			gencodePackage = "gencode";
		adichatzApplication.getPluginMap().put(EngineConstants.ENGINE_E4_BUNDLE, new AdiPluginResources(
				EngineConstants.ENGINE_E4_BUNDLE, EngineConstants.ENGINE_E4_BUNDLE, gencodePackage, getClass().getClassLoader()));

		MPerspectiveStack perspectiveStack = (MPerspectiveStack) modelService.find(PERSPECTIVE_STACK, application);
		MWindow window = (MWindow) modelService.find(TRIMMED_WINDOW, application);

		if (null != window && null == perspectiveStack) { // Only in E4 environment and when workspace is blank.
			perspectiveStack = MAdvancedFactory.INSTANCE.createPerspectiveStack();
			perspectiveStack.setElementId(PERSPECTIVE_STACK);

			window.getChildren().add(perspectiveStack);

			perspectiveStack.getChildren()
					.add(new ConsolePerspectiveManager(application, window, CONSOLE_PERSPECTIVE).getPerspective());
			perspectiveStack.getChildren()
					.add(new PlainPerspectiveManager(application, window, PLAIN_PERSPECTIVE).getPerspective());
		}
	}
}
