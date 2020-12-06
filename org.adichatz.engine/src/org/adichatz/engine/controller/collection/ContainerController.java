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

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.ADirtyContainerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IDirtyableForm;
import org.adichatz.engine.core.RootCore;
import org.adichatz.engine.extra.AdiFormInput;
import org.adichatz.engine.extra.FormMessageManager;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.FormBindingService;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ContainerController.
 */
public class ContainerController extends ADirtyContainerController implements IContainerController, IDirtyableForm {

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/** The toolkit. */
	private FormToolkit toolkit;

	/** The managed form. */
	private ManagedForm managedForm;

	/** The form message manager. */
	private FormMessageManager formMessageManager;

	/** The binding service. */
	private ABindingService bindingService;

	/** true if manage form message. */
	private boolean manageFormMessage = true;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	private Object context;

	private AdiFormInput formInput;

	public ContainerController(AdiFormInput formInput, ManagedForm mForm, String id, boolean isContainer) {
		this(formInput.getPluginResources(), mForm, mForm.getForm().getBody(), id, null);

		this.formInput = formInput;
		Object customizations = formInput.getParamMap().get(ParamMap.CUSTOMIZATION);
		if (null != customizations) {
			genCode.getContext().getParamMap().put(ParamMap.CUSTOMIZATION, customizations);
			getRootCore().injectCustomizations();
		}

		bindingService = new FormBindingService(this);
		if (isContainer)
			formInput.createContents(this);
	}

	public ContainerController(AdiPluginResources pluginResources, ManagedForm mForm, String id, Object context) {
		this(pluginResources, mForm, mForm.getForm().getBody(), id, context);
	}

	/**
	 * Instantiates a new self container controller.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param mForm
	 *            the m form
	 * @param parent
	 *            the parent
	 * @param id
	 *            the id
	 */
	public ContainerController(AdiPluginResources pluginResources, ManagedForm mForm, Composite parent, String id, Object context) {
		super(id, null, new RootCore(pluginResources, null));
		this.pluginResources = pluginResources;
		this.managedForm = mForm;
		dirtyContainer = scrolledForm;
		scrolledForm = mForm.getForm();
		toolkit = mForm.getToolkit();
		composite = toolkit.createComposite(parent);
		composite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AEntityManagerController#getControl()
	 */
	@Override
	public Composite getControl() {
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AEntityManagerController#getPluginResources()
	 */
	@Override
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.AEntityManagerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	@Override
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	/**
	 * Checks if is valid.
	 * 
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return valid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#startLifeCycle()
	 */
	@Override
	public void startLifeCycle() {
		if (isValid()) {
			createControl();
			for (AWidgetController controller : childControllers) {
				controller.startLifeCycle();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#getBindingService()
	 */
	public ABindingService getBindingService() {
		return bindingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AEntityManagerController#disposeControl()
	 */
	@Override
	public void disposeControl() {
		super.disposeControl();
		if (null != listenerMap)
			for (AListener listener : listenerMap.values())
				listener.dispose();
		composite.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#getFormMessageManager()
	 */
	@Override
	public FormMessageManager getFormMessageManager() {
		if (null == formMessageManager) {
			// Form Message Manager could be previously created because contents of could be dynamically created several times
			formMessageManager = (FormMessageManager) managedForm.getForm().getForm()
					.getData(FormMessageManager.FORM_MESSAGE_MANAGER);
			if (null == formMessageManager)
				formMessageManager = new FormMessageManager(managedForm.getForm().getForm());
		}
		return formMessageManager;
	}

	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		if (null != formMessageManager)
			formMessageManager.getForm().getMessageManager().update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#isFormMessageManager()
	 */
	@Override
	public boolean isFormMessageManager() {
		return manageFormMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IDirtyableForm#setMessageManager(boolean)
	 */
	@Override
	public void setMessageManager(boolean manageFormMessage) {
		this.manageFormMessage = manageFormMessage;
	}

	/**
	 * Gets the scrolled form.
	 * 
	 * @return the scrolled form
	 */
	public ScrolledForm getScrolledForm() {
		return scrolledForm;
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

	/**
	 * Returns the tool bar manager that is used to manage tool items in the form's title area.
	 * 
	 * @return form tool bar manager
	 */
	public IToolBarManager getToolBarManager() {
		return managedForm.getForm().getToolBarManager();
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

	@Override
	public Object getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
	}

	/**
	 * Gets the form input.
	 *
	 * @return the form input
	 */
	public AdiFormInput getFormInput() {
		return formInput;
	}
}
