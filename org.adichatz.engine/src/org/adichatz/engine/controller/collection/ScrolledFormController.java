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
package org.adichatz.engine.controller.collection;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IDirtyableForm;
import org.adichatz.engine.controller.IToolBarContainerController;
import org.adichatz.engine.controller.action.FormMenuController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.FormMessageManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

// TODO: Auto-generated Javadoc
/**
 * The Class ScrolledFormController.
 */
public class ScrolledFormController extends AEntityManagerController implements IDirtyableForm, IToolBarContainerController {

	/** The form. */
	private ScrolledForm scrolledForm;

	/** The managed tool bar controller. */
	private ManagedToolBarController mToolBarCtler;

	/** The form message manager. */
	private FormMessageManager formMessageManager;

	/** The form menu controller. */
	private FormMenuController formMenuController;

	/** true if manage form message. */
	private boolean manageFormMessage = false;

	/** The binding service. */
	private ABindingService bindingService;

	private ManagedForm managedForm;

	/**
	 * Instantiates a new form controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public ScrolledFormController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.VERTICAL | SWT.V_SCROLL | SWT.H_SCROLL;
		bindingService = parentController.getBindingService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#getControl()
	 */
	@Override
	public ScrolledForm getControl() {
		return scrolledForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#createControl()
	 */
	@Override
	public void createControl() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		scrolledForm = new ScrolledForm(parentController.getComposite(), toolkit.getOrientation() | style);
		managedForm = new ManagedForm(toolkit, scrolledForm);
		scrolledForm.setExpandHorizontal(true);
		scrolledForm.setExpandVertical(true);
		scrolledForm.setFont(JFaceResources.getHeaderFont());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#getComposite()
	 */
	@Override
	public Composite getComposite() {
		return scrolledForm.getBody();
	}

	/**
	 * Gets the tool bar controller.
	 * 
	 * @return the tool bar controller
	 */
	public ManagedToolBarController getMToolBarCtler() {
		if (null == mToolBarCtler) {
			mToolBarCtler = new ManagedToolBarController("Internal$$FormToolBarController", this, genCode);
			// mToolBarCtler.createControl();
		}
		return mToolBarCtler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#getFormMessageManager()
	 */
	public FormMessageManager getFormMessageManager() {
		if (null == formMessageManager)
			formMessageManager = new FormMessageManager(scrolledForm.getForm());
		return formMessageManager;
	}

	/**
	 * Gets the form menu controller.
	 * 
	 * @return the form menu controller
	 */
	public FormMenuController getFormMenuController() {
		if (null == formMenuController)
			formMenuController = new FormMenuController("Internal$$FormMenu", this, genCode);
		return formMenuController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#getBindingService()
	 */
	@Override
	public ABindingService getBindingService() {
		return null == bindingService ? parentController.getBindingService() : bindingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#setBindingService(org.adichatz.engine.validation.ABindingService)
	 */
	@Override
	public void setBindingService(ABindingService bindingService) {
		this.bindingService = bindingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#hasFormMessageManager()
	 */
	@Override
	public boolean isFormMessageManager() {
		return manageFormMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#setFormMessageManager(boolean)
	 */
	public void setMessageManager(boolean manageFormMessage) {
		this.manageFormMessage = manageFormMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#getManagedForm()
	 */
	@Override
	public ManagedForm getManagedForm() {
		return managedForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IReflowController#reflow()
	 */
	public void reflow() {
		scrolledForm.reflow(true);
	}

}
