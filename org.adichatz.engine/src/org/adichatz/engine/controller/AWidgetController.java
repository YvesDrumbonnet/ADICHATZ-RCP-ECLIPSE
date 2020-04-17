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
package org.adichatz.engine.controller;

import java.util.HashMap;
import java.util.List;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.ControllerEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class AController.
 * 
 * Abstract parent class for all Adichatz controllers used in editors
 * 
 * @author Yves Drumbonnet
 */
public abstract class AWidgetController extends AComponentController {

	/** The parent controller. */
	protected ICollectionController parentController;

	/** The control listeners. */
	protected List<AControlListener> controlListeners;

	/** The locked. Locked is stronger than enabled. Listener could involve enables without effect if controller is locked */
	protected boolean locked = false;

	/** The register id. */
	private String registerId;

	protected boolean delegateAfterEndLifeCycleListener;

	/**
	 * Instantiates a new widget controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public AWidgetController(String id, ICollectionController parentController, ControllerCore genCode) {
		initializeController(id, parentController, genCode);
		afterInstantiateController();
	}

	/**
	 * Gets the register id.
	 * 
	 * @return the register id
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * Initialize controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initializeController(String id, ICollectionController parentController, ControllerCore genCode) {
		this.genCode = genCode;
		this.parentController = parentController;

		/*
		 * Initializes register
		 */
		if (null != id && null != genCode) {
			registerId = genCode.getContext().getXmlTreeGenCode().getRootRegister().concat(id);
			getRootCore().getRegister().put(registerId, new RegisterEntry(this, this.getClass()));
		}
		if (null != parentController)
			parentController.addChildController(this);
		List<AListener> configListeners = getRootCore().getCustomizationListeners(registerId);
		if (null != configListeners && !configListeners.isEmpty()) {
			if (null == listenerMap)
				listenerMap = new HashMap<MultiKey, AListener>();
			for (AListener listener : configListeners) {
				((AControlListener) listener).init(this);
				listenerMap.put(new MultiKey(listener), listener);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#initialize()
	 */
	@Override
	public void initialize() {
		super.initialize();
		if (null != getEntity() && !getEntity().isLocked() && !enabled) // means reinitialization and dynamic clause
			enabled = true;
	}

	/**
	 * Starts the life cycle of the controller.
	 * 
	 * This is the first part of the life cycle, launched just after controller is instanciated.
	 */
	public void startLifeCycle() {
		// Controller could be unvalidated by initialize
		if (isValid()) {
			AListener.fireListener(listenerMap, IEventType.BEFORE_CREATE_CONTROL);
			createControl();
			AListener.fireListener(listenerMap, IEventType.AFTER_CREATE_CONTROL);
		}
	}

	/**
	 * Ends the life cycle of the controller.
	 * 
	 * This is the second part of the life cycle, launched after first part is finished for all needed controllers and wraps data
	 * process (synchronize method) is synchronized.
	 */
	public void endLifeCycle() {
		if (isValid()) {
			if (!(this instanceof ACollectionController)) // listeners already fired before processing chidren 
				AListener.fireListener(listenerMap, IEventType.BEFORE_END_LIFE_CYCLE);
			synchronize();
			if (!delegateAfterEndLifeCycleListener)
				AListener.fireListener(listenerMap, IEventType.AFTER_END_LIFE_CYCLE);
			genCode.getContext().getRootCore().fireListener(new ControllerEvent(this, IEventType.AFTER_END_LIFE_CYCLE));
		}
	}

	/**
	 * Gets the current editor.
	 * 
	 * @return the current editor
	 */
	public ABindingService getBindingService() {
		return parentController.getBindingService();
	}

	/**
	 * Checks if is valid.
	 * 
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return parentController.isValid() && super.isValid();
	}

	/**
	 * Checks if is locked.
	 * 
	 * @return true, if is locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Sets the locked.
	 * 
	 * @param locked
	 *            the new locked
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
		if (getControl() instanceof Control)
			((Control) getControl()).setEnabled(!locked);
	}

	/**
	 * Synchronize.
	 */
	public void synchronize() {
		if (getControl() instanceof Control) {
			Control control = (Control) getControl();
			if (!control.isDisposed()) {
				if (control.isEnabled() != isEnabled())
					control.setEnabled(enabled);
				boolean visible = isVisible();
				if (control.getVisible() != visible) {
					control.setVisible(visible);
				}
			}
		}
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public IEntity<?> getEntity() {
		return parentController.getEntity();
	}

	/**
	 * Adds the listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(AListener listener) {
		if (IEventType.POST_CREATE_PART == listener.eventType && !(this instanceof IContainerController)) {
			IContainerController rootController = genCode.getContext().getRootCore().getController();
			rootController.addListener(listener);
		} else {
			if (null == listenerMap)
				listenerMap = new HashMap<MultiKey, AListener>();
			listenerMap.put(new MultiKey(listener.getEventType(), listener.getId()), listener);
			if (listener instanceof AControlListener)
				((AControlListener) listener).init(this);
		}
	}

	/**
	 * Removes the listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(AListener listener) {
		if (null != listenerMap) {
			listenerMap.remove(new MultiKey(listener.getEventType(), listener.getId()));
		}
	}

	/**
	 * Gets the parent controller.
	 * 
	 * @return the parent controller
	 */
	public ICollectionController getParentController() {
		return parentController;
	}

	/**
	 * Dispose control.
	 * 
	 * Disposes control of the controller so that control could be recreated for the controller if necessary.
	 */
	public void disposeControl() {
		AListener.fireListener(listenerMap, IEventType.BEFORE_DISPOSE);
		if (null != getControl() && getControl() instanceof Widget)
			((Widget) getControl()).dispose();
		if (null != registerId) {
			getRootCore().removeCustomizationListeners(registerId);
		}
		AListener.fireListener(listenerMap, IEventType.AFTER_DISPOSE);
		listenerMap = null;
	}

	/**
	 * Gets the data access.
	 * 
	 * @return the data access
	 */
	public ADataAccess getDataAccess() {
		ICollectionController parentController = this.parentController;
		while (null != parentController) {
			if (null != parentController.getEntity())
				return parentController.getEntity().getEntityMM().getDataAccess();
			parentController = parentController.getParentController();
		}
		return null;
	}

	public void afterInstantiateController() {
		AListener.fireListener(listenerMap, IEventType.AFTER_INSTANTIATE_CONTROLLER);
	}
}
