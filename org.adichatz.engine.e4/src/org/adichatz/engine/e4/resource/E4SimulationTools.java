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

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.menu.ANodeController;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.controller.utils.INavigator;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.RootCore;
import org.adichatz.engine.e4.part.AMultiOutlinePage;
import org.adichatz.engine.e4.part.AdiInputPart;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.IControllerOutlinePage;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.e4.part.PartCore;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.simulation.SimulationTools;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

// TODO: Auto-generated Javadoc
/**
 * The Class E4SimulationTools.
 * 
 * This class contains a list of static methods which simulates user actions on E4 components managed by Adichatz.
 */
public class E4SimulationTools {

	/**
	 * Handle activate part.
	 * 
	 * Sets active part (containing having focus) to part named 'partId'.<br>
	 * - Example: E4SimulationTools.handleActivatePart("groupNavigator")<br>
	 * For editors, it's better to use handleActivateEditor(String editorId).getObject() because elementId is undifferentiated for
	 * Adichatz editors.
	 *
	 * @param partId
	 *            the part id
	 */
	public static void handleActivatePart(String partId) {
		SimulationTools.checkNullArguments(partId);
		MPart part = findPart(partId);
		if (null != part) {
			IEclipseContext context = part.getContext().getParent(); // Use parent otherwise BindingDispacher could contained a disposed context
			MWindow win = context.get(MWindow.class);
			context.get(IPresentationEngine.class).run(win, context);
			context.get(EPartService.class).showPart(part, PartState.ACTIVATE);
		} else
			logError(getFromEngineE4Bundle("simulation.invalid.part.id", partId));
	}

	/**
	 * Handle activate editor.
	 *
	 * Sets active editor (containing having focus) to editor named 'label'.<br>
	 * - Example: <code>E4SimulationTools.handleActivateEditor("Film 1";)</code>>
	 * 
	 * @param label
	 *            the label
	 */
	public static BoundedPart handleActivateEditor(String label) {
		SimulationTools.checkNullArguments(label);
		BoundedPart part = handleEditorPartByLabel(label);
		if (null != part) {
			handleActivateEditor(part.getInputPart());
			return part;
		} else
			logError(getFromEngineE4Bundle("simulation.invalid.editor.label", label));
		return null;
	}

	@SuppressWarnings("restriction")
	public static void handleActivateEditor(AdiInputPart inputPart) {
		IEclipseContext context = inputPart.getContext().getParent(); // Use parent otherwise BindingDispacher could contained a disposed context
		MWindow win = context.get(MWindow.class);
		context.get(IPresentationEngine.class).run(win, context);
		context.get(EPartService.class).showPart(inputPart, PartState.ACTIVATE);
	}

	/**
	 * Handle navigator menu controller.
	 * 
	 * Emulates the selection of an item in a navigator.<br>
	 * - For example,select item "addressQUERY" in menu "queriesMenu" of navigator "groupNavigator":<br>
	 * <code>E4SimulationTools.handleNavigatorMenuController("groupNavigator","queriesMenu","addressQUERY")>;</code>
	 * 
	 *
	 * @param nodeIds
	 *            the node ids
	 */
	public static void handleNavigatorMenuController(String... nodeIds) {
		int lastNodeIndex = nodeIds.length - 1;
		if (lastNodeIndex < 1) {
			logError(getFromEngineE4Bundle("simulation.invalid.navigator.path", getItemPath(lastNodeIndex, nodeIds)));
		}
		SimulationTools.checkNullArguments((Object[]) nodeIds);

		Object navigator = findPart(nodeIds[0]).getObject();
		if (null == navigator || !(navigator instanceof INavigator))
			logError(getFromEngineE4Bundle("simulation.navigator.does.not.exist", nodeIds[0]));
		MenuController parentNodeController = ((INavigator) navigator).getMenuCore().getRootMenuController();
		for (int i = 1; i <= lastNodeIndex; i++) {
			List<ANodeController> nodeControllers = parentNodeController.getChildren();
			boolean foundChild = false;
			// Use toArray method to avoid ConcurrentModificationException
			for (ANodeController nodeController : nodeControllers.toArray(new ANodeController[nodeControllers.size()])) {
				if (nodeIds[i].equals(nodeController.getNodeId())) {
					if (i == lastNodeIndex && nodeController instanceof ItemController) {
						nodeController.getTransientData().put(IEclipseContext.class.getName(),
								E4AdichatzApplication.getInstance().getContext());
						((ItemController) nodeController).handleActivate();
						foundChild = true;
					} else if (nodeController instanceof MenuController) {
						foundChild = true;
						parentNodeController = (MenuController) nodeController;
						((INavigator) navigator).expand(nodeController);
					} else {
						logError(getFromEngineE4Bundle("simulation.invalid.navigator.menu", getItemPath(lastNodeIndex, nodeIds)));
					}
				}
			}
			if (!foundChild) {
				logError(getFromEngineE4Bundle("simulation.child.notfound", nodeIds[i], getItemPath(i, nodeIds)));
			}
		}
	}

	/**
	 * Handle navigator menu controller.
	 * 
	 * Emulates the selection of an item in a navigator and return the part open by this action.<br>
	 * - For example,return the QueryForm editor open by action - select item "addressQUERY" in menu "queriesMenu" of navigator
	 * "groupNavigator":<br>
	 * <code>BoundedPart part = E4SimulationTools.handleNavigatorOpenPartItem("groupNavigator","queriesMenu","addressQUERY")>;</code>
	 * 
	 *
	 * @param nodeIds
	 *            the node ids
	 */
	public static BoundedPart handleNavigatorOpenPartItem(String... nodeIds) {
		handleNavigatorMenuController(nodeIds);
		return (BoundedPart) ((MPart) getSelectedEditor()).getObject();
	}

	/**
	 * Gets the selected editor.
	 * 
	 * Returns the editor displayed in Editor stack.<br>
	 * - Example: <code>E4SimulationTools.getSelectedEditor();</code>
	 *
	 * @return the selected editor
	 */
	public static MStackElement getSelectedEditor() {
		return E4AdichatzApplication.getInstance().getEditorPartStack().getSelectedElement();
	}

	/**
	 * Gets the selected navigator.
	 * 
	 * Return the navigator displayed in Navigator stack.<br>
	 * - Example: <code>E4SimulationTools.getSelectedNavigator();</code>
	 *
	 * @return the selected navigator
	 */
	public static MStackElement getSelectedNavigator() {
		IEclipseContext context = E4AdichatzApplication.getInstance().getContext();
		EModelService modelService = context.get(EModelService.class);
		MPerspectiveStack perspectiveStack = (MPerspectiveStack) modelService.find(PerspectiveProcessor.PERSPECTIVE_STACK,
				context.get(MApplication.class));
		MPerspective perspective = perspectiveStack.getSelectedElement();
		MPartStack navigatorPartStack = (MPartStack) modelService.find(PerspectiveProcessor.NAVIGATOR_STACK, perspective);

		return null == navigatorPartStack ? null : navigatorPartStack.getSelectedElement();
	}

	/**
	 * Handle navigation event.<br>
	 * Selects on option in Navigation item of Navigator part linked to and Entity editor.
	 *
	 * - Example:
	 * <code>E4SimulationTools.handleNavigationPart(rootCore,"Dependencies, DependenciesTabFolder, inventoriesItem");</code><br>
	 * In rootCore, go to page named 'Dependencies' and select 'inventoriesItem' in 'DependenciesTabFolder' CTabFolder.
	 * 
	 * @param rootCore
	 *            the root core
	 * @param path
	 *            the path
	 */
	public static void handleNavigationPart(RootCore rootCore, String path) {
		SimulationTools.checkNullArguments(rootCore, path);
		if (rootCore instanceof PartCore) {
			for (APartNavigation navigation : ((PartCore) rootCore).getNavigations())
				if (path.equals(navigation.getPath()))
					navigation.run();
		} else
			logError(getFromEngineE4Bundle("simulation.partCore.expected", EngineTools.getNormalizedClassName(rootCore)));

	}

	/**
	 * Handle page.
	 * 
	 * Selects 'pageId' page in editor.<br>
	 * Example:<code>E4SimulationTools.handlePage(category1Part, "Dependencies");</code>
	 *
	 * @param boundedPart
	 *            the bounded part
	 * @param pageId
	 *            the page id
	 */
	public static void handlePage(BoundedPart boundedPart, String pageId) {
		SimulationTools.checkNullArguments(boundedPart, pageId);
		boundedPart.activatePage(pageId);
	}

	/**
	 * Handle editor part by label.
	 *
	 * Returns the BoundedPart of editor having displayed title = 'label'.<br>
	 * - Example: <code>BoundedPart part = E4SimulationTools.handleEditorPartByLabel("Film 1");</code>
	 *
	 * @param label
	 *            the label
	 * @return the bounded part
	 */
	@SuppressWarnings("restriction")
	public static BoundedPart handleEditorPartByLabel(String label) {
		SimulationTools.checkNullArguments(label);
		for (MStackElement element : getEditorPartStackChildren())
			if (element instanceof AdiInputPart) {
				if (label.equals(((AdiInputPart) element).getLabel()))
					return ((AdiInputPart) element).getObject();
			}
		return null;
	}

	/**
	 * Handle editor part.
	 * 
	 * Returns the first editor having same value in couple of parameters<br>
	 * Example: <code>
	 * 		String[][] film1Params = new String[][] { { ParamMap.TITLE, "Film 1" },
	 * 				{ ParamMap.CONTRIBUTION_URI, JPAUtil.ENTITY_EDITOR_CONTRIBUTION_URI }, //
	 * 				{ ParamMap.ADI_RESOURCE_URI, "adi://sakilaUI/model.film/FilmEDITOR" } //
	 * 		};
	 * 		BoundedPart film1Part = E4SimulationTools.handleEditorPart(film1Params);
	 * </code> Means: find editor having paramters (field paramMap of AdiInputPart)
	 * <ul>
	 * <li>paramMap.get(ParamMap.TITLE) equals to "Film 1"</li>
	 * <li>paramMap.get(ParamMap.CONTRIBUTION_URI) equals to JPAUtil.ENTITY_EDITOR_CONTRIBUTION_URI</li>
	 * <li>paramMap.get(ParamMap.ADI_RESOURCE_URI) equals to "adi://sakilaUI/model.film/FilmEDITOR"</li>
	 * </ul>
	 *
	 * @param params
	 *            the params
	 * @return the bounded part
	 */
	public static BoundedPart handleEditorPart(String[][] params) {
		SimulationTools.checkNullArguments();
		if (0 == params.length)
			return null;
		for (MStackElement element : getEditorPartStackChildren()) {
			if (element instanceof AdiInputPart) {
				boolean equality = true;
				for (int i = 0; i < params.length; i++)
					if (!Utilities.equals(params[i][1], ((AdiInputPart) element).getParamMap().get(params[i][0]))) {
						equality = false;
						break;
					}
				if (equality)
					return (BoundedPart) ((AdiInputPart) element).getObject();
			}
		}
		return null;
	}

	/**
	 * Gets the active outline page.
	 * 
	 * Returns the Ouline page of the selected editor<br>
	 * Ouline pages are dependant on selected editor. If Outline page contains several sub pages, return the acteive sub page.<br>
	 * - Example: <code>AContainerCore containerCore = E4SimulationTools.getActiveOutlinePage();</code>
	 *
	 * @return the active outline page
	 */
	public static IOutlinePage getActiveOutlinePage() {
		IOutlinePage outlinePage = OutlinePart.getInstance().getCurrentPage();
		return outlinePage instanceof AMultiOutlinePage ? ((AMultiOutlinePage) outlinePage).getActiveOutlinePage() : outlinePage;
	}

	/**
	 * Gets the active outline container core.<br>
	 * - Example: <code>AContainerCore containerCore = E4SimulationTools.getActiveOutlineContainerCore();</code>
	 *
	 * @return the active outline container core
	 */
	public static AContainerCore getActiveOutlineContainerCore() {
		IOutlinePage outlinePage = getActiveOutlinePage();
		if (outlinePage instanceof IControllerOutlinePage)
			return ((IControllerOutlinePage) getActiveOutlinePage()).getContainerCore();
		else {
			return null;
		}
	}

	/**
	 * Do save editor containing root core.
	 *
	 * Triggers the save action in editor.
	 * 
	 * @param rootCore
	 *            the root core
	 */
	public static void doSave(RootCore rootCore) {
		SimulationTools.checkNullArguments(rootCore);
		IContainerController collectionController = rootCore.getController();
		if (collectionController instanceof BoundedPart)
			((BoundedPart) collectionController).getBoundedPartChangeManager().doSave();
		else
			logError(getFromEngineE4Bundle("simulation.boundedPart.expected",
					EngineTools.getNormalizedClassName(collectionController)));
	}

	/**
	 * Close editor corresponding to root cores.
	 * 
	 * For each rootCore, Triggers the close action of the corresponding BoundedPart.
	 *
	 * @param rootCore
	 *            the root core
	 */
	public static void close(RootCore... rootCores) {
		for (RootCore rootCore : rootCores) {
			SimulationTools.checkNullArguments(rootCore);
			IContainerController collectionController = rootCore.getController();
			if (collectionController instanceof BoundedPart)
				((BoundedPart) collectionController).close();
			else
				logError(getFromEngineE4Bundle("simulation.boundedPart.expected",
						EngineTools.getNormalizedClassName(collectionController)));
		}
	}

	/**
	 * Close all boudned parts of Editor partstack.
	 * 
	 * For each rootCore, Triggers the close action of the corresponding BoundedPart.
	 *
	 * @param rootCore
	 *            the root core
	 */
	public static void closeAll() {
		List<RootCore> rootCores = new ArrayList<>();
		for (MStackElement element : E4AdichatzApplication.getInstance().getEditorPartStack().getChildren()) {
			if (element instanceof AdiInputPart && ((AdiInputPart) element).getObject() instanceof BoundedPart) {
				rootCores.add(((BoundedPart) ((AdiInputPart) element).getObject()).getGenCode());
			}
		}
		if (!rootCores.isEmpty())
			close(rootCores.toArray(new RootCore[rootCores.size()]));
	}

	/**
	 * Refresh editor containing root core.
	 * 
	 * Triggers the refresh action in editor.
	 *
	 * @param rootCore
	 *            the root core
	 * @param ask
	 *            the ask
	 */
	public static void doRefresh(RootCore rootCore, boolean ask) {
		SimulationTools.checkNullArguments(rootCore, ask);
		IContainerController collectionController = rootCore.getController();
		if (collectionController instanceof BoundedPart)
			((BoundedPart) collectionController).getBoundedPartChangeManager().doRefresh(ask);
		else
			logError(getFromEngineE4Bundle("simulation.boundedPart.expected",
					EngineTools.getNormalizedClassName(collectionController)));
	}

	/**
	 * Gets the item path.
	 *
	 * @param index
	 *            the index
	 * @param nodeIds
	 *            the node ids
	 * @return the item path
	 */
	private static String getItemPath(int index, String... nodeIds) {
		StringBuffer nodeIdsSB = new StringBuffer();
		for (int i = 0; i <= index; i++)
			nodeIdsSB.append('/').append(nodeIds[i]);
		return nodeIdsSB.delete(0, 0).toString();
	}

	/**
	 * Gets the editor part stack children.
	 *
	 * @return the editor part stack children
	 */
	private static List<MStackElement> getEditorPartStackChildren() {
		MPartStack editorPartStack = E4AdichatzApplication.getInstance().getEditorPartStack();
		return editorPartStack.getChildren();
	}

	/**
	 * Find part.
	 *
	 * @param partId
	 *            the part id
	 * @return the m part
	 */
	private static MPart findPart(String partId) {
		SimulationTools.checkNullArguments(partId);
		IEclipseContext context = E4AdichatzApplication.getInstance().getContext();
		MPart part = context.get(EPartService.class).findPart(partId);
		if (null == part)
			logError(getFromEngineE4Bundle("simulation.invalid.part.id", partId));
		return part;
	}

}
