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
package org.adichatz.engine.controller.collection;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IItemContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;

// TODO: Auto-generated Javadoc
/**
 * The Class CompositeController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class CTabFolderController extends ACollectionController implements IItemContainerController {

	/** The c tab folder. */
	private CTabFolder cTabFolder;

	/** The creation of child controls is delayed to item selection. */
	private boolean delayed;

	/**
	 * Instantiates a new cTabFolder controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public CTabFolderController(String id, IContainerController parentController, ControllerCore genCode) {
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
		composite = cTabFolder;
		if (delayed)
			cTabFolder.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (null != e.item)
						for (AWidgetController controller : getChildControllers()) {
							CTabItemController cTabItemController = (CTabItemController) controller;
							if (e.item.equals(cTabItemController.getItem())) {
								cTabItemController.delayedLifeCycle();
							}
						}
				}
			});
	}

	public CTabItemController getSelectedController() {
		CTabItem selectedItem = cTabFolder.getSelection();
		if (null != selectedItem)
			for (AWidgetController controller : childControllers)
				if (selectedItem.equals(((CTabItemController) controller).getItem()))
					return (CTabItemController) controller;
		return null;
	}

	/**
	 * Sets the selection.
	 * 
	 * @param index
	 *            the new selection
	 */
	public void setSelection(int index) {
		CTabItem cTabItem = cTabFolder.getItem(index);
		if (null == cTabItem || !cTabItem.equals(cTabFolder.getSelection())) {
			cTabFolder.setSelection(index);
			Event event = new Event();
			event.item = cTabFolder.getItem(index);
			cTabFolder.notifyListeners(SWT.Selection, event);
		}
	}

	public void setSelection(String itemId) {
		for (AWidgetController childController : getChildControllers())
			if (childController instanceof CTabItemController && childController.getRegisterId().equals(itemId)) {
				CTabItem ctabItem = ((CTabItemController) childController).getItem();
				cTabFolder.setSelection(ctabItem);
				Event event = new Event();
				event.item = ctabItem;
				cTabFolder.notifyListeners(SWT.Selection, event);
				break;
			}
	}

	@Override
	public void setSelection(Object selectedObject) {
		cTabFolder.setSelection((CTabItem) selectedObject);
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

	public boolean isDelayed() {
		return delayed;
	}

	public void setDelayed(boolean delayed) {
		this.delayed = delayed;
	}

	@Override
	public Item getSelection() {
		return cTabFolder.getSelection();
	}

	@Override
	public void addSelectionListener(SelectionListener selectionListener) {
		cTabFolder.addSelectionListener(selectionListener);
	}

	@Override
	public void disposeControl() {
		/**
		 * When disposing CTabFolder process acts like this:<br>
		 * - dispose cTabitem(0) and select cTabItem(1) (instantiation of all controllers in delay mode) then dispose cTabItem(1)...
		 * 
		 * To avoid this behavior, dispose all non selected children (CTabItemController) during a first step.
		 */
		CTabItemController selectedItem = getSelectedController();
		if (null != selectedItem)
			for (AWidgetController controller : childControllers)
				if (!selectedItem.equals(controller))
					controller.disposeControl();

		super.disposeControl();
	}

	@Override
	public Object getContext() {
		return ((IContainerController) parentController).getContext();
	}

}
