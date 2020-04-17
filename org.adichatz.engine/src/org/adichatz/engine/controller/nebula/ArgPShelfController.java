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
package org.adichatz.engine.controller.nebula;

import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.validation.EntityInjection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.swt.SWT;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ArgPShelfController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ArgPShelfController extends AEntityManagerController implements IContainerController {

	/** The pshelf. */
	private PShelf pshelf;

	/** The label provider. */
	protected LabelProvider labelProvider;

	/** The layout. */
	protected MigLayout layout;

	/**
	 * Instantiates a new pshelf controller.
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
	public ArgPShelfController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		pshelf = new PShelf(parentController.getComposite(), SWT.FLAT | SWT.TOP);
		createChildControllers();
	}

	public void createChildControllers() {
		int i = 1;
		for (final Object object : getValues()) {
			PShelfItemController pshelfItemController = new PShelfItemController(
					getRegisterId().concat("$$").concat(String.valueOf(i++)), this, genCode) {
				@Override
				public void createControl() {
					super.createControl();
					getItem().setText(labelProvider.getText(object));
					getItem().setImage(labelProvider.getImage(object));
					getControl().setLayout(layout);
				}
			};

			genCode.setController(pshelfItemController);
			((EntityManagerCore) genCode).createContents();
			pshelfItemController.initialize();
		}
		ADataAccess dataAccess = getEntity().getEntityMM().getDataAccess();
		i = 0;
		for (AWidgetController controller : childControllers) {
			PShelfItemController pshelfItemController = (PShelfItemController) controller;
			IEntity<?> itemEntity = dataAccess.getDataCache().fetchEntity(getValues().get(i++));
			new EntityInjection(pshelfItemController, itemEntity);
		}
	}

	public void disposeChildControllers() {
		for (AWidgetController controller : childControllers)
			((PShelfItemController) controller).disposeControl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public PShelf getControl() {
		return pshelf;
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

	/**
	 * Gets the values.
	 * 
	 * @return the values
	 */
	@SuppressWarnings("rawtypes")
	public abstract List getValues();

}
