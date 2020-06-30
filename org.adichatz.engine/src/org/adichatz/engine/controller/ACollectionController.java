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
package org.adichatz.engine.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.collection.IncludeController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.EntityBindingDispatcher;
import org.adichatz.engine.validation.EntityInjection;
import org.eclipse.swt.widgets.Composite;

// TODO: Auto-generated Javadoc
/**
 * The Class AController.
 *
 * @author Yves Arpheuil
 */

/**
 * The Class AController.
 * 
 * @author Yves Drumbonnet
 */
public abstract class ACollectionController extends AWidgetController implements ICollectionController {

	/** The child controllers. */
	protected List<AWidgetController> childControllers = new ArrayList<AWidgetController>();

	/** The composite. */
	protected Composite composite;

	/** The short validation. */
	protected boolean shortValidation;

	/** The break injection: true means that current collection controller needs new EntityInjection. */
	protected boolean breakInjection;

	/** The plugin entity. */
	protected PluginEntity<?> pluginEntity;

	/**
	 * Instantiates a new a collection controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public ACollectionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/**
	 * Gets the parent controller.
	 *
	 * @return the parent controller
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IController#getParentController()
	 */
	public ICollectionController getParentController() {
		return parentController;
	}

	/**
	 * Gets the composite.
	 *
	 * @return the composite
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IController#getComposite()
	 */
	public Composite getComposite() {
		if (null == composite) {
			return parentController.getComposite();
		}
		return composite;
	}

	/**
	 * Gets the control list.
	 * 
	 * @return the control list
	 */
	public List<AWidgetController> getChildControllers() {
		return childControllers;
	}

	/**
	 * Checks if is short validation.
	 *
	 * @return true, if is short validation
	 */
	public boolean isShortValidation() {
		return shortValidation;
	}

	/**
	 * Sets the short validation.
	 *
	 * @param shortValidation the new short validation
	 */
	public void setShortValidation(boolean shortValidation) {
		this.shortValidation = shortValidation;
	}

	/**
	 * Initialize.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#initialize()
	 */
	@Override
	public void initialize() {
		super.initialize();
		if (isValid())
			for (AWidgetController controller : childControllers)
				if (controller.isValid() || (controller instanceof IActionController))
					controller.initialize();
	}

	/**
	 * Start life cycle.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#startLifeCycle()
	 */
	@Override
	public void startLifeCycle() {
		if (isValid()) {
			super.startLifeCycle();
			for (AWidgetController controller : childControllers) {
				controller.startLifeCycle();
			}
		}
	}

	/**
	 * End life cycle.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#endLifeCycle()
	 */
	@Override
	public void endLifeCycle() {
		if (isValid()) {
			AListener.fireListener(listenerMap, IEventType.BEFORE_END_LIFE_CYCLE);
			for (AWidgetController controller : childControllers) {
				controller.endLifeCycle();
			}
			super.endLifeCycle();
			if (delegateAfterEndLifeCycleListener)
				AListener.fireListener(listenerMap, IEventType.AFTER_END_LIFE_CYCLE);
		}
	}

	/**
	 * Dispose control.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#disposeControl()
	 */
	public void disposeControl() {
		for (AWidgetController controller : childControllers) {
			controller.disposeControl();
		}
		/*
		 * Controller are recreated when parent controller is a Include controller
		 */
		if (this instanceof IncludeController)
			childControllers.clear();
		else
			super.disposeControl();
	}

	/**
	 * Validate fields.
	 */
	public void validateFields() {
		if (!shortValidation)
			for (AWidgetController controller : getChildControllers()) {
				if (controller instanceof IValidableController && null != ((IValidableController) controller).getValidation())
					((IValidableController) controller).getValidation().validate();
				if (controller instanceof ACollectionController && null != getBindingService()
						&& getBindingService().equals(controller.getBindingService()) //
						&& null != ((ACollectionController) controller).getEntity())
					((ACollectionController) controller).validateFields();
			}
	}

	/**
	 * Lock field controllers.
	 * 
	 * @param locked
	 *            the locked
	 */
	protected void lockFieldControllers(boolean locked) {
		this.locked = locked;
		for (AWidgetController controller : childControllers) {
			if (controller instanceof ACollectionController
					&& (Utilities.equals(getEntity(), controller.getEntity()) || null == getEntity())) {
				// getEntity() could return null value when entity is removed from binding service during refresh process
				((ACollectionController) controller).lockFieldControllers(locked);
			} else if (controller instanceof AFieldController) {
				((AFieldController) controller).lockEntity(locked);
			}
		}
	}

	/**
	 * Lock field controllers.
	 *
	 * @param entity the entity
	 */
	public void lockFieldControllers(IEntity<?> entity) {
		if (null != entity)
			for (AWidgetController controller : childControllers) {
				if (controller instanceof ACollectionController
						&& (Utilities.equals(entity, controller.getEntity()) || null == entity)) {
					//entity could be null value when entity is removed from binding service during refresh process
					((ACollectionController) controller).lockFieldControllers(entity.isLocked());
				} else if (controller instanceof AFieldController) {
					((AFieldController) controller).lockEntity(entity.isLocked());
				}
			}
	}

	/**
	 * Adds the child controller.
	 *
	 * @param controller the controller
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.ICollectionController#addChildController(org.adichatz.engine.controller.AWidgetController)
	 */
	@Override
	public void addChildController(AWidgetController controller) {
		if (null == AdiContext.CHILD_INDEX)
			childControllers.add(controller);
		else {
			childControllers.add(AdiContext.CHILD_INDEX, controller);
			AdiContext.CHILD_INDEX = null;
		}
	}

	/**
	 * Gets the entity injection.
	 *
	 * @return the entity injection
	 */
	public EntityInjection getEntityInjection() {
		ICollectionController parentController = this;
		while (null != parentController) {
			if (parentController instanceof AEntityManagerController)
				return ((AEntityManagerController) parentController).getEntityInjection();
			parentController = parentController.getParentController();
		}
		return null;
	}

	/**
	 * Sets the entity injection.
	 *
	 * @param entityInjection the new entity injection
	 */
	public void setEntityInjection(EntityInjection entityInjection) {
	}

	/**
	 * Gets the plugin entity.
	 *
	 * @return the plugin entity
	 */
	public PluginEntity<?> getPluginEntity() {
		if (null == pluginEntity && null != parentController)
			return parentController.getPluginEntity();
		return pluginEntity;
	}

	public void setPluginEntity(PluginEntity<?> pluginEntity) {
		this.pluginEntity = pluginEntity;
	}

	/**
	 * Sets the plugin entity.
	 *
	 * @param entityURI the new plugin entity
	 */
	public void setPluginEntity(String entityURI) {
		if (null == pluginEntity) {
			IEntity<?> entity = getEntity();
			if (null != entity)
				pluginEntity = entity.getEntityMM().getPluginEntity();
			else if (null != entityURI) {
				pluginEntity = AdichatzApplication.getInstance().getPluginEntity(entityURI);
			}
		}
	}

	/**
	 * Dispose entity injections.
	 */
	public void disposeEntityInjections() {
		Set<EntityInjection> entityInjections = new HashSet<>();
		getEntityInjections(entityInjections);
		eraseEntityInjection();
		ABindingService bindingService = getBindingService();
		EntityBindingDispatcher bindingDispatcher = bindingService.getBindingDispatcher(getEntity());
		for (EntityInjection entityInjection : entityInjections) {
			bindingDispatcher.getEntityInjections().remove(entityInjection);
			entityInjection.clearTransientListeners();
			setEntityInjection(null);
		}
	}

	/**
	 * Erase entity injection.
	 */
	public void eraseEntityInjection() {
		for (AWidgetController controller : getChildControllers()) {
			if (controller instanceof ACollectionController) {
				if (controller instanceof AEntityManagerController) {
					AEntityManagerController entityManagerController = (AEntityManagerController) controller;
					if (null != entityManagerController.getEntityInjection())
						entityManagerController.setEntityInjection(null);
				}
				((ACollectionController) controller).eraseEntityInjection();
			}
		}
	}

	/**
	 * Gets the entity injections.
	 *
	 * @param entityInjections the entity injections
	 * @return the entity injections
	 */
	protected void getEntityInjections(Set<EntityInjection> entityInjections) {
		if (null != getEntityInjection()) {
			entityInjections.add(getEntityInjection());
		}
		for (AWidgetController controller : getChildControllers()) {
			if (controller instanceof ACollectionController)
				((ACollectionController) controller).getEntityInjections(entityInjections);
		}
	}

	/**
	 * Dispose entity injections.
	 *
	 * @param entity the entity
	 */
	public void disposeEntityInjections(IEntity<?> entity) {
		for (AWidgetController controller : getChildControllers()) {
			if (controller instanceof ACollectionController) {
				if (controller instanceof AEntityManagerController) {
					AEntityManagerController entityManagerController = (AEntityManagerController) controller;
					if (null != entityManagerController.getEntityInjection())
						entityManagerController.setEntityInjection(null);
				}
				((ACollectionController) controller).eraseEntityInjection();
			}
		}
	}

	/**
	 * Checks if is break injection.
	 *
	 * @return true, if is break injection
	 */
	public boolean isBreakInjection() {
		return breakInjection;
	}
}
