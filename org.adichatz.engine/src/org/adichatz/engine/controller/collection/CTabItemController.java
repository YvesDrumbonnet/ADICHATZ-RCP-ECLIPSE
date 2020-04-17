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

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IDirtyableForm;
import org.adichatz.engine.controller.IItemController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.validation.EntityInjection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class TextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class CTabItemController extends AEntityManagerController implements IItemController {

	/** The control. */
	private CTabItem item;

	/**
	 * Instantiates a new c tab item controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public CTabItemController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		CTabFolder cTabFolder = (CTabFolder) parentController.getControl();
		item = new CTabItem(cTabFolder, SWT.NONE);
		composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(cTabFolder);

		item.setControl(composite);
		composite.setLayout(new MigLayout("ins 0", "grow,fill", "grow,fill"));
		composite.setLayoutData("w 0:n:n, h 0:0:0");
	}

	public void delayedLifeCycle() {
		if (getChildControllers().isEmpty()) {
			((EntityManagerCore) genCode).createContents();
			new EntityInjection(this, parentController.getEntity());
			for (AWidgetController controller : childControllers)
				controller.initialize();
			for (AWidgetController controller : childControllers)
				controller.startLifeCycle();
			endLifeCycleAndSync();
			AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, this));
			entityInjection.getEntity().firePropertyChanges(); // Synchronize field values

			composite.layout();
			parentController.getComposite().layout();

			ICollectionController parentCtlr = getParentController();
			while (null != parentCtlr && !(parentCtlr instanceof IDirtyableForm)) {
				parentCtlr = parentCtlr.getParentController();
			}
			if (null != parentCtlr) {
				((AEntityManagerController) parentCtlr).reflowControllers();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Composite getControl() {
		return composite;
	}

	/**
	 * Gets the cTabItem.
	 * 
	 * @return the cTabItem
	 */
	@Override
	public CTabItem getItem() {
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getPluginResources()
	 */
	@Override
	public AdiPluginResources getPluginResources() {
		return ((IContainerController) parentController).getPluginResources();
	}

	@Override
	public void disposeControl() {
		super.disposeControl();
		item.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	@Override
	public void setPluginResources(AdiPluginResources pluginResources) {
	}

	@Override
	public EntityInjection getEntityInjection() {
		if (null == entityInjection && parentController instanceof ArgTabFolderController) {
			return new EntityInjection(this, null) {
				@Override
				public void initialize(IEntity<?> entity) {
				}
			};
		}
		return entityInjection;
	}

	/**
	 * Visibilty must be manager by CTabFolder selection
	 *
	 * @return composite#isVisible()
	 */
	@Override
	public boolean isVisible() {
		return composite.isVisible();
	}
}
