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

import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.EntityInjection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ArgTabFolderController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ArgTabFolderController extends AEntityManagerController implements IContainerController {

	/** The c tab folder. */
	private CTabFolder cTabFolder;

	/** The label provider. */
	protected LabelProvider labelProvider;

	/** The layout. */
	protected MigLayout layout;

	private CTabItemController selectedItemController;

	/**
	 * Instantiates a new cTabFolder controller.
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
	public ArgTabFolderController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		cTabFolder = new CTabFolder(parentController.getComposite(), SWT.FLAT | SWT.TOP);
		toolkit.paintBordersFor(cTabFolder);
		toolkit.adapt(cTabFolder, true, true);
		createChildControllers();
		cTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CTabItem selectedItem = getControl().getSelection();
				for (AWidgetController controller : getChildControllers())
					if (controller instanceof CTabItemController
							&& ((CTabItemController) controller).getItem().equals(selectedItem)) {
						selectedItemController = (CTabItemController) controller;
						selectedItemController.getComposite().redraw();
					}

			}
		});
	}

	public void createChildControllers() {
		for (int index = 0; index < getValues().size(); index++) {
			CTabItemController cTabItemController = new CTabItemController(
					getRegisterId().concat("$$").concat(String.valueOf(index + 1)), this, genCode) {
				@Override
				public void synchronize() {
					super.synchronize();
					// Take getEntity().getBean() rather than object because getValues().get(i) because values cand change during
					// controller life.
					getItem().setText(labelProvider.getText(getEntity().getBean()));
					getItem().setImage(labelProvider.getImage(getEntity().getBean()));
					getControl().setLayout(layout);
				}
			};
			if (1 == index)
				selectedItemController = cTabItemController;
			genCode.setController(cTabItemController);
			((EntityManagerCore) genCode).createContents();
			cTabItemController.initialize();
		}
	}

	private void refreshChildEntities() {
		ADataAccess dataAccess = getEntity().getEntityMM().getDataAccess();
		int i = 0;
		for (AWidgetController controller : childControllers) {
			CTabItemController cTabItemController = (CTabItemController) controller;
			IEntity<?> itemEntity = dataAccess.getDataCache().fetchEntity(getValues().get(i++));
			new EntityInjection(cTabItemController, itemEntity);
		}
	}

	@Override
	public void disposeControl() {
		int itemCount = childControllers.size();
		CTabItemController[] items = new CTabItemController[itemCount];
		int k = 0;
		for (AWidgetController controller : childControllers)
			items[k++] = (CTabItemController) controller;
		for (int i = 0; i < itemCount; i++) {
			for (int j = i + 1; j < itemCount; j++) {
				EngineTools.reinitMiglayout(items[j].getControl());
			}
			items[i].disposeControl();
		}
		immutableValid = false;
	}

	@Override
	public void endLifeCycle() {
		refreshChildEntities();
		super.endLifeCycle();
		getComposite().layout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public CTabFolder getControl() {
		return cTabFolder;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	@Override
	public void setPluginResources(AdiPluginResources pluginResources) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getEntity()
	 */
	@Override
	public IEntity<?> getEntity() {
		return parentController.getEntity();
	}

	/**
	 * Sets the layout.
	 * 
	 * @param layout
	 *            the new layout
	 */
	public void setLayout(MigLayout layout) {
		this.layout = layout;
	}

	/**
	 * Gets the label provider.
	 * 
	 * @return the label provider
	 */
	public LabelProvider getLabelProvider() {
		return labelProvider;
	}

	public CTabItemController getSelectedItemController() {
		return selectedItemController;
	}

	/**
	 * Gets the values.
	 * 
	 * @return the values
	 */
	@SuppressWarnings("rawtypes")
	public abstract List getValues();

}
