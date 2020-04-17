/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 * 
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 * 
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.controller.collection;

import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IRankedController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuManagerController.
 *
 * @author Yves Drumbonnet
 */
public class MenuManagerController extends ACollectionController implements IRankedController {

	/** The menu manager. */
	private MenuManager menuManager;

	/** The text. */
	private String text;

	/** The image descriptor. */
	private ImageDescriptor imageDescriptor;

	/** The field controller. */
	private AWidgetController menuContainer;

	/** The controls. */
	private Control[] controls;

	/** The rank. */
	protected int rank = -1;

	protected boolean initialized;

	/**
	 * Instantiates a new tool bar controller. With a toolBarManager (for SectionControlle or FormPageController)
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public MenuManagerController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		menuContainer = (AWidgetController) parentController;
	}

	/**
	 * Instantiates a new menu manager controller.
	 *
	 * @param id
	 *            the id
	 * @param fieldController
	 *            the field controller
	 * @param genCode
	 *            the gen code
	 * @param controls
	 *            the controls
	 */
	public MenuManagerController(String id, AFieldController fieldController, ControllerCore genCode, Control... controls) {
		super(id, null, genCode);
		this.menuContainer = fieldController;
		if (0 == controls.length)
			this.controls = new Control[] { fieldController.getControl() };
		else
			this.controls = controls;
	}

	/**
	 * Creates the tool bar manager (which is not a control for SWT).
	 */
	public void createControl() {
		if (menuContainer instanceof MenuManagerController) {
			menuManager = new MenuManager(text, imageDescriptor, null);
			// Menu manager is a sub menu of a menu manager
			((MenuManagerController) menuContainer).getControl().add(menuManager);
			for (AWidgetController controller : childControllers)
				if (controller.isValid())
					controller.createControl();
		} else {
			if (null == controls && menuContainer instanceof ACollectionController)
				controls = new Control[] { (Control) ((ACollectionController) menuContainer).getControl() };
			menuManager = new MenuManager();
			// Menu manager is attached to a control
			menuManager.setRemoveAllWhenShown(true);
			for (Control control : controls) {
				Menu menu = menuManager.createContextMenu(control);
				control.setMenu(menu);
			}
			menuManager.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager mgr) {
					for (AWidgetController controller : childControllers) {
						// Add listenerMap in order to inject VALID, VISIBLE and ENABLED customizations
						Map<MultiKey, AListener> listenerMap = controller.getListenerMap();
						controller.disposeControl();
						if (null != listenerMap && !listenerMap.isEmpty())
							controller.setListenerMap(listenerMap);
					}
					// childControllers.clear();
					MenuManagerController.this.menuAboutToShow();
					endLifeCycle();
				}
			});
		}
	}

	/**
	 * Menu about to show.
	 */
	public void menuAboutToShow() {
		if (!initialized) {
			initialized = true;
			for (AWidgetController itemController : childControllers) {
				itemController.initialize();
			}
		}
		EngineTools.sortRankedControllers(childControllers);
		for (AWidgetController itemController : childControllers) {
			if (itemController.isValid()) {
				itemController.startLifeCycle();
				if (itemController.getControl() instanceof IAction)
					menuManager.add((IAction) itemController.getControl());
				else
					menuManager.add((IContributionItem) itemController.getControl());
				itemController.setEnabled(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getEntity()
	 */
	@Override
	public IEntity<?> getEntity() {
		if (null != parentController)
			return parentController.getEntity();
		else
			return menuContainer.getParentController().getEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	public MenuManager getControl() {
		return menuManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#synchronize()
	 */
	@Override
	public void synchronize() {
		getControl().setVisible(isVisible());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IRankedController#getRank()
	 */
	public int getRank() {
		return rank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IRankedController#setRank(int)
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Sets the image descriptor.
	 *
	 * @param imageDescriptor
	 *            the new image descriptor
	 */
	public void setImageDescriptor(ImageDescriptor imageDescriptor) {
		this.imageDescriptor = imageDescriptor;
	}

	/**
	 * Gets the menu container.
	 *
	 * @return the menu container
	 */
	public AWidgetController getMenuContainer() {
		AWidgetController menuContainer = this.menuContainer;
		while (menuContainer instanceof MenuManagerController || menuContainer instanceof IncludeController)
			menuContainer = (AWidgetController) menuContainer.getParentController();
		return menuContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#disposeControl()
	 */
	@Override
	public void disposeControl() {
		for (AWidgetController controller : childControllers) {
			controller.disposeControl();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#isValid()
	 */
	public boolean isValid() {
		return menuContainer.isValid() && internalIsValid();
	}

	/**
	 * Internal is valid.
	 *
	 * @return true, if successful
	 */
	private boolean internalIsValid() {
		for (ElementaryAccessibility accessibility : accessibilities)
			if (ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID == accessibility.getAccessAttribute())
				if (!accessibility.accept())
					return false;
		return true;
	}

	@Override
	public void endLifeCycle() {
		if (initialized)
			super.endLifeCycle();
	}
}
