package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.List;

import org.adichatz.engine.e4.handler.ClearConsoleHandler;
import org.adichatz.engine.e4.handler.ScrollLockConsoleHandler;
import org.adichatz.engine.e4.part.AdiConsole;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.ItemType;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class ConsolePerspectiveManager extends PlainPerspectiveManager {
	private static String CONSOLE_PARTSTACK = "adichatz.console.stack";

	private MPartStack consolePartStack;

	public ConsolePerspectiveManager(MApplication application, MPerspectiveStack perspectiveStack, String perspectiveId) {
		super(application, perspectiveStack, perspectiveId);
	}

	public ConsolePerspectiveManager(MApplication application, MWindow window, String perspectiveId) {
		super(application, window, perspectiveId);
		E4AdichatzApplication.getInstance().setConsolePerspective(perspective);
	}

	protected void createContents(MWindow window) {
		super.createContents(window);
		MBasicFactory basicFactory = MBasicFactory.INSTANCE;
		consolePartStack = basicFactory.createPartStack();
		consolePartStack.setElementId(CONSOLE_PARTSTACK);
		centerPSC.getChildren().add(consolePartStack);
		consolePartStack.setContainerData("25");
	}

	protected void createParts() {
		super.createParts();

		MBasicFactory basicFactory = MBasicFactory.INSTANCE;
		// Create Console part
		MPart consolePart = basicFactory.createPart();
		consolePartStack.getChildren().add(consolePart);
		consolePart.setElementId("adichatz.console");
		consolePart.setLabel(getFromEngineE4Bundle("adichatz.console"));
		consolePart.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_CONSOLE.png"));
		consolePart.setContributionURI(CONTRIBUTION_URI_PREFIX.concat(AdiConsole.class.getName()));

		MMenuFactory menuFactory = MMenuFactory.INSTANCE;
		MToolBar toolBar = menuFactory.createToolBar();
		toolBar.setElementId("adichatz.console.toolbar");
		consolePart.setToolbar(toolBar);

		// Create menu for Console part
		MCommandsFactory commandsFactory = MCommandsFactory.INSTANCE;

		MCommand scrollLockConsoleCommand = commandsFactory.createCommand();
		scrollLockConsoleCommand.setElementId("adichatz.scrollLock.console.command");
		scrollLockConsoleCommand.setCommandName("scrollLock.console");
		application.getCommands().add(scrollLockConsoleCommand);

		MHandler scrollLockConsoleHandler = commandsFactory.createHandler();
		scrollLockConsoleHandler.setElementId("adichatz.scrollLock.console.handler");
		scrollLockConsoleHandler.setCommand(scrollLockConsoleCommand);
		scrollLockConsoleHandler.setContributionURI(CONTRIBUTION_URI_PREFIX.concat(ScrollLockConsoleHandler.class.getName()));
		application.getHandlers().add(scrollLockConsoleHandler);

		MHandledToolItem scrollLockConsoleHTI = menuFactory.createHandledToolItem();
		scrollLockConsoleHTI.setTooltip(getFromEngineE4Bundle("scrollLock.console.tooltip"));
		scrollLockConsoleHTI.setCommand(scrollLockConsoleCommand);
		scrollLockConsoleHTI.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_SCROLL_LOCK.png"));
		scrollLockConsoleHTI.setType(ItemType.CHECK);

		MCommand clearConsoleCommand = commandsFactory.createCommand();
		clearConsoleCommand.setElementId("adichatz.clear.console.command");
		clearConsoleCommand.setCommandName("clear.console");
		application.getCommands().add(clearConsoleCommand);

		MHandler clearConsoleHandler = commandsFactory.createHandler();
		clearConsoleHandler.setElementId("adichatz.clear.console.handler");
		clearConsoleHandler.setCommand(clearConsoleCommand);
		clearConsoleHandler.setContributionURI(CONTRIBUTION_URI_PREFIX.concat(ClearConsoleHandler.class.getName()));
		application.getHandlers().add(clearConsoleHandler);

		MHandledToolItem clearConsoleHTI = menuFactory.createHandledToolItem();
		clearConsoleHTI.setTooltip(getFromEngineE4Bundle("clear.console.tooltip"));
		clearConsoleHTI.setCommand(clearConsoleCommand);
		clearConsoleHTI.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_REMOVE_CONTENT.png"));

		toolBar.getChildren().add(clearConsoleHTI);
		toolBar.getChildren().add(scrollLockConsoleHTI);
	}

	@Override
	public void reset() {
		EModelService modelService = perspective.getContext().get(EModelService.class);
		preReset(modelService);

		MPerspective oldPerspective = perspective;
		consolePartStack = (MPartStack) modelService.find(CONSOLE_PARTSTACK, application);

		List<MStackElement> consolePartChildren = consolePartStack.getChildren();

		createContents(application.getChildren().get(0));
		consolePartStack.getChildren().addAll(consolePartChildren);

		MPerspectiveStack perspectiveStack = (MPerspectiveStack) modelService.find(PerspectiveProcessor.PERSPECTIVE_STACK,
				application);
		perspectiveStack.getChildren().add(perspective);
		application.getContext().get(EPartService.class).switchPerspective(perspective);
		perspectiveStack.getChildren().remove(oldPerspective);

	}

	protected void postCreatePerspective() {
		perspective.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_PERSPECTIVE_CONSOLE.png"));
		perspective.setLabel(getFromEngineE4Bundle("adichatz.perspective.console"));
		perspective.getPersistedState().put(PerspectiveProcessor.PERSPECTIVE_MANAGER,
				"bundleclass://org.adichatz.engine.e4/".concat(getClass().getName()));
	}

	public MPerspective getPerspective() {
		return perspective;
	}
}
