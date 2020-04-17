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

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.DelayedThread;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.controller.utils.ElementaryAccessibility.ACCESS_ATTRIBUTE;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.FieldBindingManager;
import org.adichatz.engine.data.IFieldBindManager;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.engine.validation.ControllerValidation;
import org.adichatz.engine.validation.ErrorMessage;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IMessageProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AFieldController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AFieldController extends AControlController implements IValidableController, IControlController {

	/** The bind manager assuming databindind between pojo and control. */
	protected IFieldBindManager fieldBindManager;

	/** The linked controller. */
	protected AControlController linkedController;

	/** The force binding (when column is null). */
	protected boolean forceBinding;

	/** The validation. */
	private ControllerValidation validation;

	/** The has listeners been added. */
	private boolean hasListenersBeenAdded = false;

	/** The property. */
	private String property;

	/** The menu manager. */
	private MenuManager menuManager;

	protected Set<String> lazyFetchMembers;

	/**
	 * Instantiates a new a field controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public AFieldController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#startLifeCycle()
	 */
	@Override
	public void startLifeCycle() {
		if (isValid() && null != getBindingService() && (property != null || forceBinding)) {
			fieldBindManager = new FieldBindingManager(this);
		}
		super.startLifeCycle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#disposeControl()
	 */
	@Override
	public void disposeControl() {
		hasListenersBeenAdded = false;
		if (null != getBindingService()) {
			if (null != validation) {
				Set<ErrorMessage> errorMessages = getBindingService().getErrorMessageMap().get(this);
				if (null != errorMessages)
					// Use array to avoid ConcurrentModificationException
					for (ErrorMessage errorMessage : errorMessages.toArray(new ErrorMessage[errorMessages.size()])) {
						errorMessage.getValidator().setLevel(IMessageProvider.NONE, null);
					}

				validation.getValidators().clear();
				if (null != getControl() && !getControl().isDisposed())
					validation.getDirtyableForm().getManagedForm().getMessageManager().removeMessages(getControl());
			}
		}
		super.disposeControl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AControlController#synchronize()
	 */
	@Override
	public void synchronize() {
		super.synchronize();
		if (!hasListenersBeenAdded) {
			hasListenersBeenAdded = true;
			addListeners();
		}
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return null;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the value
	 */
	public abstract void setValue(Object value);

	/**
	 * Gets the bind manager.
	 * 
	 * @return the bind manager
	 */
	public IFieldBindManager getFieldBindManager() {
		return fieldBindManager;
	}

	/**
	 * Sets the bind manager.
	 * 
	 * @param bindManager
	 *            the new bind manager
	 */
	public void setFieldBindManager(IFieldBindManager bindManager) {
		this.fieldBindManager = bindManager;
	}

	/**
	 * Adds the listeners.
	 */
	public abstract void addListeners();

	/**
	 * Gets the validation.
	 * 
	 * @return the validation
	 */
	public ControllerValidation getValidation() {
		if (null == validation)
			validation = new ControllerValidation(this);
		return validation;
	}

	/**
	 * Sets the property.
	 *
	 * @param property
	 *            the new property
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * Gets the linked controller.
	 * 
	 * @return the linked controller
	 */
	public AControlController getLinkedController() {
		return linkedController;
	}

	/**
	 * Sets the linked controller.
	 * 
	 * @param linkedController
	 *            the linked controller to set
	 */
	public void setLinkedController(AControlController linkedController) {
		this.linkedController = linkedController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#addAccessibility(org.adichatz.engine.accessibility.AAccessibility)
	 */
	@Override
	public void addAccessibility(ElementaryAccessibility accessibility) {
		super.addAccessibility(accessibility);
		if (null != linkedController && ACCESS_ATTRIBUTE.ENABLED != accessibility.getAccessAttribute())
			linkedController.addAccessibility(accessibility);
	}

	/**
	 * Lock entity.
	 * 
	 * @param locked
	 *            the locked
	 */
	public void lockEntity(boolean locked) {
		this.locked = locked;
		if (null != getControl() && !getControl().isDisposed()) { // control could be null or disposed when using dynamic process
			if (locked) {
				enabled = false;
				if (null != getControl()) {
					getControl().setEnabled(false);
				}
				if (null != getBindingService())
					getBindingService().removeMessages(this);
			} else {
				enabled = true;
				if (null != getControl())
					getControl().setEnabled(isEnabled());
			}
		}
	}

	/**
	 * To string.
	 * 
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public String toString(Object bean) {
		return String.valueOf(bean);
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

	/**
	 * Reflow.
	 */
	protected void reflow() {
		if (!ABindingService.isSynchronizing() && !getControl().isDisposed()) {
			if (EngineConstants.SYNCHRONIZE_REFLOW_MODE) {
				pack();
				getParentController().reflowControllers();
			} else {
				// new PackControlThread(this, getParentController()).start();
				Runnable reflowRunnable = new Runnable() {
					@Override
					public void run() {
						if (!getControl().isDisposed()) { // delayed thread makes disposed control possible (ie in testing or automatic processes) 
							pack();
							getParentController().reflowControllers();
						}
					}
				};
				DelayedThread.newDelayedThread(new MultiKey(getControl(), "#REFLOW#"), 100, reflowRunnable);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AControlController#pack()
	 */
	public void pack() {
		getControl().pack();
	}

	/**
	 * ************************ All the following methods could be overidden by generated class ************************.
	 * 
	 * /** Gets the property.
	 * 
	 * @return the property
	 */
	public String getProperty() {
		return property;
	};

	/**
	 * Convert model to target.
	 * 
	 * @param value
	 *            the from object
	 * 
	 * @return the object
	 */
	public Object convertModelToTarget(Object value) {
		return value;
	}

	/**
	 * Convert target to model.
	 * 
	 * @param value
	 *            the from object
	 * 
	 * @return the object
	 */
	public Object convertTargetToModel(Object value) {
		return value;
	}

	/**
	 * Gets the lazy fetch members.
	 * 
	 * @return the lazy fetch members
	 */
	public Set<String> getLazyFetchMembers() {
		return lazyFetchMembers;
	}

	public void addLazyFetchMembers(String lazyFetchMembers) {
		if (null != lazyFetchMembers) {
			StringTokenizer tokenizer = new StringTokenizer(lazyFetchMembers, ",");
			int length = tokenizer.countTokens();
			if (null == this.lazyFetchMembers)
				this.lazyFetchMembers = new HashSet<>(length);
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken().trim();
				this.lazyFetchMembers.add(token);
			}
		}
	}

	/**
	 * Sets the force binding.
	 *
	 * @param forceBinding
	 *            the new force binding
	 */
	public void setForceBinding(boolean forceBinding) {
		this.forceBinding = forceBinding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#getParentController()
	 */
	@Override
	public AEntityManagerController getParentController() {
		return (AEntityManagerController) super.getParentController();
	}

	/**
	 * Gets the menu manager.
	 *
	 * @return the menu manager
	 */
	public MenuManager getMenuManager() {
		if (null == menuManager) {
			menuManager = new MenuManager();
			getControl().setMenu(menuManager.createContextMenu(getControl()));
		}
		return menuManager;
	}

	/**
	 * Broad casted set value.
	 *
	 * @param value
	 *            the value
	 */
	public void broadCastedSetValue(Object value) {
		setValue(value);
		broadcastChange();
	}

	/**
	 * Broadcast change.
	 */
	public abstract void broadcastChange();

	@Override
	public void beforeEntityInjection() {
	}

	@Override
	public void afterEntityInjection() {
	}
}
