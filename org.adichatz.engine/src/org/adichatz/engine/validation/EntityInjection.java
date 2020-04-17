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
package org.adichatz.engine.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityInjection.
 * 
 * @author Yves Drumbonnet
 */
public class EntityInjection {

	/** The entity controller. */
	private AEntityManagerController entityController;

	/** The entity. */
	private IEntity<?> entity;

	/** The binding service. */
	private ABindingService bindingService;

	/** The binding dispatcher. */
	private EntityBindingDispatcher bindingDispatcher;

	private List<IBoundedController> boundedControllers = new ArrayList<IBoundedController>();

	private boolean locked;

	/** Set of transient listeners (listeners which must dispose when removing entity). */
	private Set<AEntityListener> transientListeners = new HashSet<AEntityListener>();

	private AControlListener injectEntityListener;

	/**
	 * Instantiates a new entity injection.
	 * 
	 * @param entityController
	 *            the entity controller
	 * @param ientity
	 *            the entity
	 */
	public EntityInjection(AEntityManagerController entityController, IEntity<?> ientity) {
		if (null != ientity) {
			ientity.initialize();
		}
		this.entityController = entityController;
		bindingService = entityController.getBindingService();
		entityController.setEntityInjection(this);
		initialize(ientity);
		ICollectionController parentController = entityController.getParentController();
		if (null != ientity && null != parentController && parentController instanceof AEntityManagerController
				&& ientity.equals(parentController.getEntity()))
			locked = ((AEntityManagerController) parentController).getEntityInjection().isLocked();
	}

	/**
	 * Initialize.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void initialize(final IEntity<?> entity) {
		if (null != this.entity) {
			clearTransientListeners();
			AListener.fireListener(bindingService.getListeners(), new AdiEntityEvent(IEventType.REMOVE_ENTITY, entity));
			boundedControllers.clear();
			bindingDispatcher.getEntityInjections().remove(this);
		}

		this.entity = entity;

		if (null != entity && null != bindingService) {
			bindingDispatcher = bindingService.getBindingDispatcher(entity);
			bindingDispatcher.getEntityInjections().add(this);
			entity.getBindingServices().add(bindingService);
			injectEntity(entityController);
			AListener.fireListener(bindingService.getListeners(), new AdiEntityEvent(IEventType.ADD_ENTITY, entity));
			if (IEntityConstants.PERSIST == entity.getStatus()) {
				entity.lock(true, bindingService);
			}
		}
	}

	/**
	 * Inject entity.
	 * 
	 * @param entity
	 *            the entity
	 * @param parentController
	 *            the parent controller
	 */
	public void injectEntity(ACollectionController parentController) {
		AListener.fireListener(parentController.getListenerMap(), IEventType.BEFORE_ENTITY_INJECTION);
		if (parentController instanceof IBoundedController) {
			boundedControllers.add((IBoundedController) parentController);
		}
		if (parentController instanceof IValidableController)
			addLazyFetchMembers(entity, (IValidableController) parentController);
		for (AWidgetController controller : parentController.getChildControllers()) {
			if (controller instanceof ACollectionController) {
				ACollectionController collectionController = (ACollectionController) controller;
				EntityInjection entityInjection = collectionController.getEntityInjection();
				if (!collectionController.isBreakInjection() && (null == entityInjection || this == entityInjection)) {
					entityInjection = this;
					collectionController.setEntityInjection(this);
					entityInjection.injectEntity(collectionController);
				}
			} else if (controller instanceof IValidableController) {
				addLazyFetchMembers(entity, (IValidableController) controller);
				((IValidableController) controller).afterEntityInjection();
			}
		}
		if (parentController instanceof IValidableController)
			((IValidableController) parentController).afterEntityInjection();
		AListener.fireListener(parentController.getListenerMap(), IEventType.AFTER_ENTITY_INJECTION);
	}

	/**
	 * Adds the lazy fetch members.
	 * 
	 * @param entity
	 *            the entity
	 * @param validableController
	 *            the validable controller
	 */
	private void addLazyFetchMembers(IEntity<?> entity, IValidableController validableController) {
		validableController.beforeEntityInjection();
		if (null != entity.getLazyFetchManager() && null != validableController.getLazyFetchMembers())
			/*
			 * Add lazy fetches needed for the field to the parent controller
			 */
			for (String member : validableController.getLazyFetchMembers()) {
				entity.addLazyFetch(bindingService, member);
			}
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public IEntity<?> getEntity() {
		return entity;
	}

	public AEntityManagerController getEntityController() {
		return entityController;
	}

	public List<IBoundedController> getBoundedControllers() {
		return boundedControllers;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * Gets the transient listeners.
	 * 
	 * @return the transient listeners
	 */
	public Set<AEntityListener> getTransientListeners() {
		return transientListeners;
	}

	/**
	 * Clear transient listeners.
	 */
	public void clearTransientListeners() {
		for (AEntityListener transientListener : transientListeners) {
			transientListener.dispose();
		}
		transientListeners.clear();
	}

	public AControlListener getInjectEntityListener() {
		return injectEntityListener;
	}
}
