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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.action.AControlCollectionController;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.collection.SectionController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.engine.validation.ControllerValidation;
import org.adichatz.engine.validation.EntityBindingDispatcher;
import org.adichatz.engine.validation.EntityInjection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

// TODO: Auto-generated Javadoc
/**
 * The Class AEntityManagerController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AEntityManagerController extends AControlCollectionController
		implements IBoundedController, IValidableController {

	protected EntityInjection entityInjection;

	/** The dynamic creation. */
	private boolean dynamicCreation;

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The immutable valid. */
	protected boolean immutableValid = false;

	/** The validation. */
	protected ControllerValidation validation;

	/** The reflow controllers. */
	private List<AEntityManagerController> reflowControllers;

	private ScrolledForm parentScrolledForm;

	private boolean unbound;

	protected Set<String> lazyFetchMembers;

	protected boolean dirty;

	/**
	 * Instantiates a new a entity manager controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent conisEntityContaineraram genCode the generated code
	 * @param genCode
	 *            the gen code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public AEntityManagerController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		if (null != parentController)
			this.pluginResources = parentController.getPluginResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getEntity()
	 */
	public IEntity<?> getEntity() {
		EntityInjection entityInjection = getEntityInjection();
		return null == entityInjection ? null : entityInjection.getEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getPluginResources()
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	public boolean dynamicSwitchController(boolean token, boolean reflow) {
		if (token) {
			if (null == getControl() || getControl().isDisposed()) {
				dynamicCreateControl(true, reflow);
				return true;
			}
		} else if (null != getControl() && !getControl().isDisposed()) {
			Composite parent = getControl().getParent();
			disposeControl();
			EngineTools.reinitMiglayout(parent); // Avoid widget disposed error
			return true;
		}
		return false;
	}

	/**
	 * Dynamic create control.
	 * 
	 * Controller are already creates without having
	 * 
	 * @param forceEndLifeCycle
	 *            force end life cycle if true
	 * @param reflow
	 *            the reflow
	 */
	public void dynamicCreateControl(boolean forceEndLifeCycle, boolean reflow) {
		dynamicCreation = true;
		if (isValid() && null != getControl())
			disposeControl();
		valid = immutableValid = true;
		initialize();
		// Invoke super.startLifeCycle() rather than startLifeCycle()
		// startLifeCycle could be overriden in invoking class
		super.startLifeCycle();
		if (null != entityInjection)
			entityInjection.initialize(getEntity());
		if (forceEndLifeCycle) {
			endLifeCycleAndSync();
			AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, this));
		}
		dynamicCreation = true;
	}

	/**
	 * Bind GUI.
	 * 
	 * @param <T>
	 *            the
	 * @param collectionController
	 *            the collection controller
	 * @param entity
	 *            the entity
	 */
	protected <T> void bindGUI(ACollectionController collectionController, final IEntity<T> entity) {
		unbound = false;
		for (Object element : collectionController.getChildControllers()) {
			if (element instanceof AFieldController && null != ((AFieldController) element).getFieldBindManager()) {
				final AFieldController fieldController = (AFieldController) element;
				if (null != entity && null != fieldController.getControl() && null != fieldController.getProperty()) {
					AListener.fireListener(fieldController.getListenerMap(), IEventType.BEFORE_SYNCHRONIZE);
					fieldController.getFieldBindManager().addPropertyChangeListener(entity);
				}
			} else if (element instanceof ACollectionController && !(element instanceof AEntityManagerController)) {
				bindGUI((ACollectionController) element, entity);
				if (element instanceof AEntityManagerController)
					((AEntityManagerController) element).getBindingService().getEntityContainers().remove(element);
			}
		}
	}

	public void checkbind(IEntity<?> entity) {
		if (null == entity)
			unbindGUI(this);
		else if (unbound) {
			bindGUI(this, entity);
		}
	}

	/**
	 * Un bind gui.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param collectionController
	 *            the collection controller
	 * @param entity
	 *            the entity
	 */
	private void unbindGUI(ICollectionController collectionController) {
		unbound = true;
		for (Object element : collectionController.getChildControllers()) {
			if (element instanceof AFieldController && null != ((AFieldController) element).getFieldBindManager()) {
				final AFieldController fieldController = (AFieldController) element;
				fieldController.getFieldBindManager().unbind();
			} else if (element instanceof ACollectionController)
				unbindGUI((ACollectionController) element);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#bindEntity()
	 */
	@Override
	public void synchronize() {
		IEntity<?> entity = getEntity();
		AListener.fireListener(listenerMap, IEventType.BEFORE_SYNCHRONIZE);
		super.synchronize();
		if (null != entity) {
			bindGUI(this, entity);
		}
	}

	public void postSynchronize() {
		if (dynamicCreation || this instanceof APageController || null == parentController
				|| !Utilities.equals(getEntity(), parentController.getEntity()))
			validateFields();
		AListener.fireListener(listenerMap, IEventType.AFTER_SYNCHRONIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IBoundedController#lockFieldControllers(boolean)
	 */
	@Override
	public void lockFieldControllers(boolean locked) {
		super.lockFieldControllers(locked);
		this.locked = locked;
		if (!locked)
			validateFields();
		else if (null != getBindingService())
			getBindingService().removeMessages(this);
		//	}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#disposeControl()
	 */
	@Override
	public void disposeControl() {
		super.disposeControl();
		immutableValid = false;
	}

	/**
	 * Sets the immutable valid.
	 * 
	 * @param immutableValid
	 *            the new immutable valid
	 */
	public void setImmutableValid(boolean immutableValid) {
		this.immutableValid = immutableValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#endLifeCycle()
	 */
	@Override
	public void endLifeCycle() {
		if (null != getControl() && !getControl().isDisposed()) {
			super.endLifeCycle();
		}
		ABindingService bindingService = getBindingService();
		if (null != bindingService)
			bindingService.getEntityContainers().add(this);
		if (null != composite && !composite.isDisposed()) {
			if (null == composite.getMenu())
				composite.setMenu(composite.getParent().getMenu());
		}
	}

	public void endLifeCycleAndSync() {
		endLifeCycle();
		ABindingService bindingService = getBindingService();
		if (isValid()) {
			if (null != bindingService) {
				bindingService.synchronize();
				syncLocking(bindingService);
			}
		}
	}

	public void syncLocking(ABindingService bindingService) {
		IEntity<?> entity = getEntity();
		if (null != entity) {
			EntityBindingDispatcher bindingDispatcher = bindingService.getBindingDispatcher(entity);
			bindingDispatcher.handleLockingEvent(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#isValid()
	 */
	@Override
	public boolean isValid() {
		return immutableValid || super.isValid();
	}

	/**
	 * Adds the validator.
	 * 
	 * @param validator
	 *            the validator
	 */
	public void addValidator(AValidator validator) {
		getValidation().getValidators().add(validator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IValidableController#getValidation()
	 */
	public ControllerValidation getValidation() {
		if (null == validation)
			validation = new ControllerValidation(this);
		return validation;
	}

	/**
	 * Checks if is dirty.
	 *
	 * @return true, if is dirty
	 */
	public boolean isDirty() {
		return dirty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IBoundedController#setDirty(boolean)
	 */
	public boolean setDirty(boolean dirty) {
		this.dirty = dirty;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#validateFields()
	 */
	public void validateFields() {
		if (null != validation)
			validation.validate();
		super.validateFields();
	}

	/**
	 * Gets the lazy fetch members.
	 * 
	 * @return the lazy fetch members
	 */
	public Set<String> getLazyFetchMembers() {
		return lazyFetchMembers;
	}

	public void addLazyFetchMembers(String... lazyFetchMembers) {
		if (null != lazyFetchMembers) {
			if (null == this.lazyFetchMembers)
				this.lazyFetchMembers = new HashSet<>(lazyFetchMembers.length);
			for (String lazyFetchMember : lazyFetchMembers)
				this.lazyFetchMembers.add(lazyFetchMember);
		}
	}

	public void reflowControllers() {
		if (null == reflowControllers) {
			reflowControllers = new ArrayList<AEntityManagerController>();
			Composite parent = getComposite();
			while (null != parent && parentScrolledForm == null) {
				parent = parent.getParent();
				if (parent instanceof ScrolledForm)
					parentScrolledForm = (ScrolledForm) parent;
			}

			ACollectionController parentCtlr = this;
			while (true) {
				if (parentCtlr instanceof IDirtyableForm)
					break;
				ICollectionController grandParentCtlr = parentCtlr.getParentController();
				if (null == grandParentCtlr || !(grandParentCtlr instanceof ACollectionController))
					break;
				parentCtlr = (ACollectionController) grandParentCtlr;

			}
			getReflowControllers(parentCtlr);

			Collections.reverse(reflowControllers);
		}

		if (null != parentScrolledForm) {
			parentScrolledForm.reflow(true);
		} else
			getComposite().layout();
		for (AEntityManagerController controller : reflowControllers) {
			if (null != controller.getControl() && !controller.getControl().isDisposed()) {
				controller.getControl().setRedraw(false);
				if (controller instanceof SectionController) {
					((SectionController) controller).reflow();
				} else if (controller instanceof PGroupController) {
					((PGroupController) controller).reflow();
				} else if (controller.getControl() instanceof SharedScrolledComposite) {
					((SharedScrolledComposite) controller.getControl()).reflow(true);
				} else {
					((Composite) controller.getControl()).layout();
				}
			}
		}
		for (AEntityManagerController controller : reflowControllers)
			if (null != controller.getControl() && !controller.getControl().isDisposed())
				controller.getControl().setRedraw(true);
	}

	/**
	 * Gets the reflow controllers.
	 * 
	 * @param collectionController
	 *            the collection controller
	 * @return the reflow controllers
	 */
	private void getReflowControllers(ACollectionController collectionController) {
		if (collectionController instanceof AEntityManagerController && !(collectionController instanceof IItemController))
			reflowControllers.add((AEntityManagerController) collectionController);
		for (AWidgetController controller : collectionController.getChildControllers()) {
			if (controller instanceof ACollectionController)
				getReflowControllers((ACollectionController) controller);
		}
	}

	@Override
	public Object getContext() {
		return ((IContainerController) parentController).getContext();
	}

	public EntityInjection getEntityInjection() {
		return entityInjection;
	}

	public void setEntityInjection(EntityInjection entityInjection) {
		this.entityInjection = entityInjection;
	}

	@Override
	public void beforeEntityInjection() {
	}

	@Override
	public void afterEntityInjection() {
	}

	public String getTitle() {
		return getRegisterId();
	}

}
