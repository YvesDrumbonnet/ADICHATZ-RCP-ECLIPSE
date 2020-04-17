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
package org.adichatz.engine.simulation;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.action.ButtonController;
import org.adichatz.engine.controller.action.ToolItemController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.CTabFolderController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.collection.TreeController;
import org.adichatz.engine.controller.field.AComboController;
import org.adichatz.engine.controller.nebula.PGroupToolItemController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.core.RootCore;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.viewer.ATreeContentProvider;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulationTools.
 * 
 * This class contains a list of static methods which simulates user actions on Adichatz components.
 */
public class SimulationTools {
	/**
	 * Handle cTabItem (fires selection on cTabItem).
	 * 
	 * Simulates selection of an CTabItem determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleCTabItem(film1Part.getGenCode(), "actorsItem");</code>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handleCTabItem(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		CTabItemController itemController = getController(rootCore, registerId, CTabItemController.class);
		if (null != itemController)
			((CTabFolderController) itemController.getParentController()).setSelection(registerId);
	}

	/**
	 * Handle pShelfItem (fires selection on pShelfItem).
	 * 
	 * Simulates selection of an PShelfItem determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handlePShelfItem(containerCore, "columnParameterItem");</code>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handlePShelfItem(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		PShelfItemController itemController = getController(rootCore, registerId, PShelfItemController.class);
		if (null != itemController)
			((PShelfController) itemController.getParentController()).setSelection(itemController.getItem());
	}

	/**
	 * Handle action (fires selection on action).
	 * 
	 * Simulates selection of an action determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleAction(containerCore, "launchManagedQueryAction");</code>
	 * 
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handleAction(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		ActionController actionController = getController(rootCore, registerId, ActionController.class);
		if (null != actionController)
			actionController.getControl().run();
	}

	/**
	 * Handle context menu action.
	 * 
	 * Simulates selection of an item determined by actionId of a contex menu determined by contextMenuId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleContextMenuAction(filmQueryCore, "tableCM:contextMenu", "launchQueryAction");</code><br>
	 *
	 * @param rootCore
	 *            the root core
	 * @param contextMenuId
	 *            the context menu id
	 * @param actionId
	 *            the action id
	 */
	public static void handleContextMenuAction(RootCore rootCore, String contextMenuId, String actionId) {
		checkNullArguments(rootCore, contextMenuId, actionId);
		MenuManagerController menuManagerController = getController(rootCore, contextMenuId, MenuManagerController.class);
		if (null != menuManagerController) {
			ICollectionController parentController = menuManagerController.getParentController();
			while (!(parentController.getControl() instanceof Control))
				parentController = parentController.getParentController();
			((Control) parentController.getControl()).setFocus();
			Menu menu = menuManagerController.getControl().getMenu();
			Event event = new Event();
			event.widget = menu;
			event.display = menu.getDisplay();
			menu.notifyListeners(SWT.Show, event);
			String controllerId = contextMenuId.substring(0, contextMenuId.lastIndexOf(':') + 1) + actionId;
			ActionController actionController = getController(rootCore, controllerId, ActionController.class);
			if (null != actionController)
				actionController.getControl().run();
		}
	}

	/**
	 * Handle toolItem (fires selection on toolItem).
	 * 
	 * Simulates selection of a ToolItem (of ToolBar e.g. of a Section or a ManagedForm)determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleToolItem(rootCore, "masterDetailTBM:changeOrientationAction");</code><br>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handleToolItem(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		ToolItemController toolItemController = getController(rootCore, registerId, ToolItemController.class);
		if (null != toolItemController)
			toolItemController.getActionController().getControl().run();
	}

	/**
	 * Handle pgroupRoolItem (fires selection on pgroupRoolItem).
	 * 
	 * Simulates selection of a PGroup ToolItem determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handlePGroupToolItem(containerCore, "validateParameterItem");</code><br>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handlePGroupToolItem(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		PGroupToolItemController toolItemController = getController(rootCore, registerId, PGroupToolItemController.class);
		if (null != toolItemController)
			toolItemController.run();
	}

	/**
	 * Handle button (fires selection on button).
	 * 
	 * Simulates selection of a button determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleButton(film1Part.getGenCode(), "filmDetail:ok");</code><br>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handleButton(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		ButtonController buttonController = getController(rootCore, registerId, ButtonController.class);
		if (null != buttonController) {
			buttonController.getControl().setSelection(true);
			buttonController.getControl().notifyListeners(SWT.Selection, null);
		}
	}

	/**
	 * Handle select row in table viewer.
	 * 
	 * Simulates selection on a Set Controller (Tree, Table, Grid Controller) determined by registerId.<br>
	 * range starts with 0.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleSelectRowInTableViewer(film1Part.getGenCode(), "actorsTSI:table", 10);</code><br>
	 * Select range 10 in the TableController determined by registerId="actorsTSI:table".
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 * @param range
	 *            the range
	 */
	public static Object handleSelectRowInTableViewer(RootCore rootCore, String registerId, int range) {
		checkNullArguments(rootCore, registerId, range);
		ATabularController<?> tabularController = getController(rootCore, registerId, ATabularController.class);
		if (null != tabularController) {
			// Master controller must process selection in same thread otherwise, register is not complete (Selection Thread will be
			// launched after Tree is built)
			boolean selectInNewThread = tabularController.doesSelectInNewThread();
			if (selectInNewThread)
				tabularController.setSelectInNewThread(false);
			QueryResult queryResult = tabularController.getQueryManager().getQueryResult();
			Object selectedObject = handleSelectRowInTableViewer(tabularController.getViewer(), queryResult.getQueryResultList(),
					range, registerId);
			if (selectInNewThread)
				tabularController.setSelectInNewThread(false);
			return selectedObject;
		}
		return null;
	}

	public static Object handleSelectRowInTableViewer(ColumnViewer columnViewer, Collection<?> collection, int range,
			String registerId) {
		if (collection.size() <= range) {
			logWarning(getFromEngineBundle("simulation.invalid.range", range, registerId, collection.size()));
			return null;
		}
		Object selectedObject = null;
		if (collection instanceof List)
			selectedObject = ((List<?>) collection).get(range);
		else {
			int i = 0;
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				selectedObject = iterator.next();
				if (range == i++)
					break;
			}
		}
		columnViewer.setSelection(new StructuredSelection(selectedObject));
		return selectedObject;
	}

	public static void handleSelectRowInTableViewer(AbstractTableViewer tableViewer, int range) {
		try {
			Object selectedElement = tableViewer.getElementAt(range);
			if (null != selectedElement)
				tableViewer.setSelection(new StructuredSelection(selectedElement));
		} catch (ArrayIndexOutOfBoundsException e) {
			Object input = tableViewer.getInput();
			String max;
			if (input instanceof Collection)
				max = String.valueOf(((Collection<?>) input).size());
			else
				max = getFromEngineBundle("unknown");
			logError(getFromEngineBundle("simulation.tableViewer.invalid.range", range, getFromEngineBundle("unknown"), max));
		}
	}

	public static void handleSelectRowInTreeViewer(RootCore rootCore, String registerId, int... ranges) {
		checkNullArguments(rootCore, registerId, ranges);
		TreeController treeController = getController(rootCore, registerId, TreeController.class);
		if (null != treeController) {
			boolean selectInNewThread = treeController.doesSelectInNewThread();
			if (selectInNewThread)
				treeController.setSelectInNewThread(false);
		}
	}

	public static void handleSelectRowInTreeViewer(TreeViewer treeViewer, String registerId, int... ranges) {
		ATreeContentProvider contentProvider = (ATreeContentProvider) treeViewer.getContentProvider();
		TreePath treePath = TreePath.EMPTY;
		Object inputElement = treeViewer.getInput();
		try {
			for (int range : ranges) {
				inputElement = contentProvider.getChildren(inputElement)[range];
				treePath = treePath.createChildPath(inputElement);
				treeViewer.expandToLevel(treePath, 1);
			}
			treeViewer.setSelection(new TreeSelection(treePath), true);
		} catch (ArrayIndexOutOfBoundsException e) {
			logError(getFromEngineBundle("simulation.invalid.ranges", Arrays.toString(ranges), registerId));
		}
	}

	/**
	 * Handle double click.
	 * 
	 * Simulates a double-click on a Set Controller (Tree, Table, Grid Controller) determined by registerId.<br>
	 * Example:<br>
	 * <code>PostOpenTools.handleDoubleClick(film1Part.getGenCode(), "actorsTSI:table");</code><br>
	 * Sends an double click events on selected row of TableController determined by registerId="actorsTSI:table".
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 */
	public static void handleDoubleClick(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		ASetController setController = getController(rootCore, registerId, ASetController.class);
		if (null != setController) {
			Item item = setController.getSelectedItem();
			handleDoubleClick(item, setController.getControl(), registerId);
		}
	}

	public static void handleDoubleClick(Item item, Control control, String registerId) {
		if (null == item)
			logError(getFromEngineBundle("simulation.no.item.selected", registerId));
		else {
			Event event = new Event();
			event.widget = control;
			event.item = item;
			control.notifyListeners(SWT.DefaultSelection, event);
		}
	}

	/**
	 * Handle select bean in table viewer.
	 * 
	 * Returns the value contained in the combo or a ccombo at position 'range'.<br>
	 * Example:<br>
	 * <code>String name = PostOpenTools.handleSelectBeanInTableViewer(filmQueryCore, "tableInclude:table", (short) 1);</code><br>
	 * Don't forget to cast beanId with the type of the id of the pojo.
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 * @param beanId
	 *            the bean id
	 */
	public static void handleSelectBeanInTableViewer(RootCore rootCore, String registerId, Object beanId) {
		checkNullArguments(rootCore, registerId, beanId);
		ATabularController<?> tabularController = getController(rootCore, registerId, ATabularController.class);
		if (null != tabularController) {
			// Master controller must process selection in same thread otherwise, register is not complete (Selection Thread will be
			// launched after Tree is built)
			boolean selectInNewThread = tabularController.doesSelectInNewThread();
			if (selectInNewThread)
				tabularController.setSelectInNewThread(false);
			QueryResult queryResult = tabularController.getQueryManager().getQueryResult();
			Object bean = null;
			Iterator<?> iterator = queryResult.getQueryResultList().iterator();
			while (iterator.hasNext()) {
				bean = iterator.next();
				if (tabularController.getQueryManager().getEntityMM().getId(bean).equals(beanId)) {
					tabularController.getViewer().setSelection(new StructuredSelection(bean));
					return;
				}
			}
			tabularController.getViewer().setSelection(StructuredSelection.EMPTY);
			if (selectInNewThread)
				tabularController.setSelectInNewThread(false);
		}
	}

	/**
	 * Handle select value in combo.
	 * 
	 * Returns the value contained in the combo or a ccombo at position 'range'.<br>
	 * Example:<br>
	 * <code>String name = PostOpenTools.handleSelectValueInCombo(film1Part.getGenCode(), "filmDetail:rating", 1);</code>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 * @param range
	 *            the range
	 */
	public static void handleSelectValueInCombo(RootCore rootCore, String registerId, int range) {
		checkNullArguments(rootCore, registerId, range);
		AComboController comboController = getController(rootCore, registerId, AComboController.class);
		if (null != comboController) {
			if (comboController.getValues().size() <= range)
				logError(getFromEngineBundle("simulation.invalid.range", range, registerId, comboController.getValues().size()));
			else {
				Object value = comboController.getValues().get(range);
				comboController.broadCastedSetValue(value);
			}
		}
	}

	/**
	 * Handle set value for field controller.
	 *
	 * Sets the value managed by the field controller<br>
	 * Example:<br>
	 * <code>String name = PostOpenTools.handleSetValueForFieldController(film1Part.getGenCode(), "filmDetail:name", "a new name for this film");</code>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 * @param value
	 *            the value
	 */
	public static void handleSetValueForFieldController(RootCore rootCore, String registerId, Object value) {
		checkNullArguments(rootCore, registerId, value);
		AFieldController fieldController = getController(rootCore, registerId, AFieldController.class);
		if (null != fieldController)
			fieldController.broadCastedSetValue(value);
	}

	/**
	 * Handle get value for field controller defined by the register id.
	 * 
	 * Returns the value contained in the field controller<br>
	 * Example:<br>
	 * <code>String name = PostOpenTools.handleGetValueForFieldController(film1Part.getGenCode(), "filmDetail:name");</code>
	 *
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 * @return the object
	 */
	public static Object handleGetValueForFieldController(RootCore rootCore, String registerId) {
		checkNullArguments(rootCore, registerId);
		AFieldController fieldController = getController(rootCore, registerId, AFieldController.class);
		if (null != fieldController)
			return fieldController.getValue();
		return null;
	}

	/**
	 * Gets the controller.
	 * 
	 * Finds in register of rootCore, a controller with specified registerId and extending specified class<br>
	 * Example:<br>
	 * <code>PGroupController pgroupController = PostOpenTools.getController(film1Part.getGenCode(), "filmDetail:activeGroup", PGroupController.class);</code>
	 *
	 * @param <T>
	 *            the generic type
	 * @param rootCore
	 *            the root core
	 * @param registerId
	 *            the register id
	 * @param superClass
	 *            the super class
	 * @return the controller
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AWidgetController> T getController(RootCore rootCore, String registerId, Class<T> superClass) {
		RegisterEntry<?> registerEntry = rootCore.getRegister().get(registerId);
		if (null == registerEntry) {
			logError(getFromEngineBundle("simulation.no.controller", registerId));
			return null;
		}
		AWidgetController controller = registerEntry.getController();
		if (!ReflectionTools.hasSuperClass(controller.getClass(), superClass))
			logError(getFromEngineBundle("simulation.incorrect.class", registerId, superClass.getName()));
		return (T) controller;
	}

	/**
	 * Return the normalized class name of a controller.
	 * 
	 * Example An instance of FilmEditor$1 return string "FilmEditor".
	 *
	 * @param controller
	 *            the controller
	 * @return the normalized class name
	 */
	public static String getNormalizedClassName(Object controller) {
		String className = controller.getClass().getName();
		if (-1 != className.indexOf('$'))
			className = controller.getClass().getSuperclass().getName();
		return className;

	}

	/**
	 * Check null arguments.
	 * 
	 * Throws an error with explicit messge if an argument is null.
	 * 
	 * Check if all passed arguments are not null.
	 *
	 * @param args
	 *            the args
	 */
	public static void checkNullArguments(Object... args) {
		for (Object arg : args) {
			if (null == arg) {
				StringBuffer paramsSB = new StringBuffer();
				for (Object param : args)
					paramsSB.append(", ").append(null == param ? "null" : ".");
				StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
				throw new RuntimeException(getFromEngineBundle("simulation.method.illegal.usage", stackTraceElement.getMethodName(),
						stackTraceElement.getClassName(), stackTraceElement.getLineNumber(), paramsSB.delete(0, 1).toString()));
			}
		}
	}

	/**
	 * Sleep.
	 *
	 * @param millis
	 *            the millis
	 */
	public static void sleep(int millis) {
		try {
			Display.getCurrent().getActiveShell().forceActive();
			Display.getCurrent().update();
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logError(e);
		}
	}

}
