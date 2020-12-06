/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.engine.listener;

import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IRootController;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.EntityInjection;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;

// TODO: Auto-generated Javadoc
/**
 * The Class AEntityListener.
 * 
 * Supports two event types (CHANGE_STATUS and LOCK_ENTITY...)<br>
 * Code will be executed just after event is fired.
 * 
 * @see AEntityEvent
 */
public abstract class AEntityListener extends AListener {

	/** The entity. */
	protected IEntity<?> entity;

	/** The binding service. */
	protected ABindingService bindingService;

	protected EntityInjection entityInjection;

	// Normally listener is triggered only when bindingService is currentBindingService or listener do not recognize bindingService
	// value true force listener to be triggered whatever is the bindingService value.
	protected boolean forceHandle;

	protected DisposeListener disposeListener;

	private ICollectionController parentController;

	/**
	 * Instantiates a new a entity listener.
	 * 
	 * Do not specify bindingService if process must spread whatever the bindingService.
	 * 
	 * @param widgetController
	 *            the widget controller
	 * @param eventType
	 *            the event type
	 */
	public AEntityListener(String id, int eventType, IEntity<?> entity) {
		super(id, eventType);
		this.entity = entity;
		entity.getListeners().add(this);
	}

	public AEntityListener(ICollectionController parentController, int eventType) {
		this(null, parentController, eventType);
	}

	public AEntityListener(String id, ICollectionController parentController, int eventType) {
		super(id, eventType);
		if (null != parentController && !(parentController.getControl() instanceof Control)
				&& !(parentController instanceof IRootController)) {
			logError("Listener (type=" + eventType + ") on controller '" + parentController.getClass().getName()
					+ "' is incompatible with type of controller!?");
			return;
		}
		this.parentController = parentController;
		// If EntityManagerController and entityInjection are not null, entity listener is considered as a transient listener.
		// That means that listener is disposed when current entity on EntityManagerController changes (another entity is set or
		// controller is disposed).
		if (null != parentController) {
			this.bindingService = parentController.getBindingService();
			if (parentController instanceof AEntityManagerController) {
				entityInjection = ((AEntityManagerController) parentController).getEntityInjection();
				if (null != entityInjection) {
					entityInjection.getTransientListeners().add(this);
				}
			}
			entity = parentController.getEntity();
			entity.getListeners().add(this);
			disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					disposeListener = null;
					dispose();
				}
			};
			if (parentController.getControl() instanceof Control) {
				Control control = (Control) parentController.getControl();
				if (!control.isDisposed()) // e.g. when editor is roughly closed due to error.
					control.addDisposeListener(disposeListener);
			} else
				((IRootController) parentController).getComposite().addDisposeListener(disposeListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.listener.AListener#dispose()
	 */
	public void dispose() {
		entity.disposeListener(this);
		if (null != disposeListener)
			if (parentController.getControl() instanceof Control) {
				Control control = (Control) parentController.getControl();
				if (control.isDisposed())
					control.removeDisposeListener(disposeListener);
			} else
				((IRootController) parentController).getComposite().removeDisposeListener(disposeListener);

	}

	/**
	 * Handle event.
	 * 
	 * @param event
	 *            the event
	 */
	public abstract void handleEntityEvent(AdiEntityEvent event);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.listener.AListener#handleEvent(org.adichatz.engine.listener.AdiEvent)
	 */
	@Override
	public void handleEvent(AdiEvent event) {
		if (forceHandle || null == ABindingService.getCurrentBinding() || null == bindingService
				|| ABindingService.getCurrentBinding().equals(bindingService))
			/**
			 * Launched if binding service is null or one is equals to the binding service hosting the change<br>
			 * see FieldBindingManager#bindTargetToModel
			 */
			handleEntityEvent((AdiEntityEvent) event);
	}

	/**
	 * Gets the entity binding service.
	 * 
	 * @return the entity binding service
	 */
	public ABindingService getEntityBindingService() {
		// getBindingService is not a correct name because it caould be confustion with the controller calling the listener
		return bindingService;
	}

	/**
	 * Sets the entity.
	 * 
	 * @param entity
	 *            the new entity
	 */
	public void setEntity(IEntity<?> entity) {
		this.entity = entity;
	}

	/**
	 * Sets the force handle.
	 *
	 * @param forceHandle the new force handle
	 */
	public void setForceHandle(boolean forceHandle) {
		this.forceHandle = forceHandle;
	}

	/**
	 * Gets the parent controller.
	 *
	 * @return the parent controller
	 */
	public ICollectionController getParentController() {
		return parentController;
	}
}
