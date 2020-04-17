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
package org.adichatz.engine.validation;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.extra.FormMessageManager;
import org.adichatz.engine.listener.IEventType;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.ui.forms.IMessageManager;

// TODO: Auto-generated Javadoc
/**
 * The abstract class AValidator parent of all validators.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AValidator {

	/** The controller triggering the validator. */
	protected IValidableController triggeringController;

	/** The controller hosting the message triggered by the validator. */
	protected IValidableController[] hostingControllers;

	protected String key;

	protected FormMessageManager formMessageManager;

	/**
	 * Instantiates a new a validator.
	 * 
	 * @param triggeringController
	 *            the triggering controller
	 */
	public AValidator(IValidableController triggeringController, String key) {
		this(triggeringController, key, triggeringController);
	}

	/**
	 * Instantiates a new a validator.
	 * 
	 * @param triggeringController
	 *            the triggering controller
	 * @param hostingController
	 *            the hosting controller
	 */
	public AValidator(IValidableController triggeringController, String key, IValidableController... hostingControllers) {
		this(hostingControllers[0].getValidation().getDirtyableForm().getFormMessageManager(), key);
		this.triggeringController = triggeringController;
		this.hostingControllers = hostingControllers;
	}

	public AValidator(FormMessageManager formMessageManager, String key) {
		if (null == key) {
			key = "missingKey";
			logError(getFromEngineBundle("error.missing.key", getClass().getName()));
		} else
			this.key = key;
		this.formMessageManager = formMessageManager;
	}

	/**
	 * Gets the triggering controller.
	 * 
	 * @return the triggering controller
	 */
	public IValidableController getTriggeringController() {
		return triggeringController;
	}

	/**
	 * Gets the hosting controllers.
	 * 
	 * @return the hosting controllers
	 */
	public IValidableController[] getHostingControllers() {
		return hostingControllers;
	}

	public String getKey() {
		return key;
	}

	public FormMessageManager getFormMessageManager() {
		return formMessageManager;
	}

	/**
	 * Validate.
	 * 
	 * @param validation
	 *            the validation
	 */
	public abstract int validate();

	public int setLevel(int level, String messageStr) {
		ABindingService bindingService = null == hostingControllers ? null : hostingControllers[0].getBindingService();
		if (null != bindingService) {
			ErrorMessage errorMessage = new ErrorMessage(this, messageStr, bindingService);

			if (hostingControllers[0].isLocked())
				level = IMessageProvider.NONE;

			if (IMessageProvider.ERROR == level) {
				if (bindingService.addMessage(errorMessage)) {
					bindingService.fireListener(IEventType.POST_MESSAGE);
				}
				bindingService.fireListener(IEventType.DIRTY_VALIDATION);
			} else {
				removeMessage(formMessageManager);
				if (bindingService.removeMessage(errorMessage)) {
					bindingService.fireListener(IEventType.POST_MESSAGE);
					if (bindingService.getErrorMessageMap().isEmpty())
						bindingService.fireListener(IEventType.DIRTY_VALIDATION);
				}
			}
		}
		if (null != hostingControllers)
			if (IMessageProvider.NONE == level)
				removeMessage(formMessageManager);
			else {
				for (IValidableController controller : hostingControllers)
					formMessageManager.getForm().getMessageManager().addMessage(key, messageStr, null, level,
							controller.getControl());
			}
		return level;
	}

	private void removeMessage(FormMessageManager formMessageManager) {
		IMessageManager messageManager = formMessageManager.getForm().getMessageManager();
		for (IValidableController controller : hostingControllers) {
			messageManager.removeMessage(key, controller.getControl());
		}
	}
}