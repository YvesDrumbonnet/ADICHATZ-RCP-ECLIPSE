/*******************************************************************************
* Copyright © Adichatz (2007-2020) - www.adichatz.org
*
* yves@adichatz.org
*
* This software is a computer program whose purpose is to build easily
* Eclipse RCP applications using JPA in a JEE or JSE context.
*
* This software is governed by the CeCILL-C license under French law and
* abiding by the rules of distribution of free software.  You can  use,
* modify and/ or redistribute the software under the terms of the CeCILL
* license as circulated by CEA, CNRS and INRIA at the following URL
* "http://www.cecill.info".
*
* As a counterpart to the access to the source code and  rights to copy,
* modify and redistribute granted by the license, users are provided only
* with a limited warranty  and the software's author,  the holder of the
* economic rights,  and the successive licensors  have only  limited
* liability.
*
* In this respect, the user's attention is drawn to the risks associated
* with loading,  using,  modifying and/or developing or reproducing the
* software by the user in light of its specific status of free software,
* that may mean  that it is complicated to manipulate,  and  that  also
* therefore means  that it is reserved for developers  and  experienced
* professionals having in-depth computer knowledge. Users are therefore
* encouraged to load and test the software's suitability as regards their
* requirements in conditions enabling the security of their systems and/or
* data to be ensured and,  more generally, to use and operate it in the
* same conditions as regards security.
*
* The fact that you are presently reading this means that you have had
* knowledge of the CeCILL license and that you accept its terms.
*
*
********************************************************************************
*
* Copyright © Adichatz (2007-2020) - www.adichatz.org
*
* yves@adichatz.org
*
* Ce logiciel est un programme informatique servant à construire rapidement des
* applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
*
* Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
* respectant les principes de diffusion des logiciels libres. Vous pouvez
* utiliser, modifier et/ou redistribuer ce programme sous les conditions
* de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
* sur le site "http://www.cecill.info".
*
* En contrepartie de l'accessibilité au code source et des droits de copie,
* de modification et de redistribution accordés par cette licence, il n'est
* offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
* seule une responsabilité restreinte pèse sur l'auteur du programme,  le
* titulaire des droits patrimoniaux et les concédants successifs.
*
* A cet égard  l'attention de l'utilisateur est attirée sur les risques
* associés au chargement,  à l'utilisation,  à la modification et/ou au
* développement et à la reproduction du logiciel par l'utilisateur étant
* donné sa spécificité de logiciel libre, qui peut le rendre complexe à
* manipuler et qui le réserve donc à des développeurs et des professionnels
* avertis possédant  des  connaissances  informatiques approfondies.  Les
* utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
* logiciel à leurs besoins dans des conditions permettant d'assurer la
* sécurité de leurs systèmes et ou de leurs données et, plus généralement,
* à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
*
* Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
* pris connaissance de la licence CeCILL, et que vous en avez accepté les
* termes.
*******************************************************************************/
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

// TODO: Auto-generated Javadoc
/**
 * The Class ConsolePerspectiveManager.
 *
 * @author Yves Drumbonnet
 */
public class ConsolePerspectiveManager extends PlainPerspectiveManager {

	/** The console partstack. */
	private static String CONSOLE_PARTSTACK = "adichatz.console.stack";

	/** The console part stack. */
	private MPartStack consolePartStack;

	/**
	 * Instantiates a new console perspective manager.
	 *
	 * @param application the application
	 * @param perspectiveStack the perspective stack
	 * @param perspectiveId the perspective id
	 */
	public ConsolePerspectiveManager(MApplication application, MPerspectiveStack perspectiveStack, String perspectiveId) {
		super(application, perspectiveStack, perspectiveId);
	}

	/**
	 * Instantiates a new console perspective manager.
	 *
	 * @param application the application
	 * @param window the window
	 * @param perspectiveId the perspective id
	 */
	public ConsolePerspectiveManager(MApplication application, MWindow window, String perspectiveId) {
		super(application, window, perspectiveId);
	}

	/**
	 * Creates the contents.
	 *
	 * @param window the window
	 */
	protected void createContents(MWindow window) {
		super.createContents(window);
		MBasicFactory basicFactory = MBasicFactory.INSTANCE;
		consolePartStack = basicFactory.createPartStack();
		consolePartStack.setElementId(CONSOLE_PARTSTACK);
		centerPSC.getChildren().add(consolePartStack);
		consolePartStack.setContainerData("25");
	}

	/**
	 * Creates the parts.
	 */
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

	/**
	 * Reset.
	 */
	@Override
	public void reset() {
		EModelService modelService = perspective.getContext().get(EModelService.class);
		preReset(modelService);

		MPerspective oldPerspective = perspective;
		consolePartStack = (MPartStack) modelService.find(CONSOLE_PARTSTACK, application);

		List<MStackElement> consolePartChildren = consolePartStack.getChildren();

		createContents(application.getChildren().get(0));
		consolePartStack.getChildren().addAll(consolePartChildren);

		MPerspectiveStack perspectiveStack = (MPerspectiveStack) modelService.find(EngineE4Util.PERSPECTIVE_STACK, application);
		perspectiveStack.getChildren().add(perspective);
		application.getContext().get(EPartService.class).switchPerspective(perspective);
		perspectiveStack.getChildren().remove(oldPerspective);

	}

	/**
	 * Post create perspective.
	 */
	protected void postCreatePerspective() {
		perspective.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_PERSPECTIVE_CONSOLE.png"));
		perspective.setLabel(getFromEngineE4Bundle("adichatz.perspective.console"));
		perspective.getPersistedState().put(EngineE4Util.PERSPECTIVE_MANAGER,
				"bundleclass://org.adichatz.engine.e4/".concat(getClass().getName()));
	}

	/**
	 * Gets the perspective.
	 *
	 * @return the perspective
	 */
	public MPerspective getPerspective() {
		return perspective;
	}
}
