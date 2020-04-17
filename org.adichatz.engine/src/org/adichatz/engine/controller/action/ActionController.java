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
package org.adichatz.engine.controller.action;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IActionController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.collection.ButtonBarController;
import org.adichatz.engine.controller.collection.ManagedToolBarController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.widgets.supplement.AAdiRunnable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Control;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionController.
 * 
 * Action Controller manages a JFace Action inside Adichatz.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ActionController extends AWidgetController implements IActionController {

	/** The runnable. */
	private AAdiRunnable runnable = new AAdiRunnable(this) {

		@Override
		public void run() {
			action.runAction();
		}
	};

	/** The parameters map. */
	private final Map<String, Object> params = new HashMap<String, Object>();

	/** The action. */
	protected AAction action;

	/** The rank. */
	protected int rank = -1;

	/**
	 * Instantiates a new action controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public ActionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		if (parentController instanceof ManagedToolBarController)
			((ManagedToolBarController) parentController).getToolBarManager().add(getControl());
		else if (parentController instanceof MenuActionController)
			((MenuActionController) parentController).addActionController(this);
		else if (parentController instanceof FormMenuController)
			((FormMenuController) parentController).addAction(getControl());
		else if (null == action)
			logError("No action defined for '" + getRegisterId() + "' action!? code or class controller must be set.");
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public IEntity<?> getEntity() {
		return parentController.getEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#synchronize()
	 */
	@Override
	public void synchronize() {
		// getControl().setEnabled(enabled && !locked);
		if (parentController instanceof ButtonBarController)
			action.addPropertyChangeListener(new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (!event.getNewValue().equals(event.getOldValue()))
						if (event.getProperty().equals(IAction.ENABLED)) {
							if (parentController instanceof ToolItemController)
								((ToolItemController) parentController).getControl().setEnabled((Boolean) event.getNewValue());
							else
								((Control) ((AActionContainerCtler) parentController).getControl())
										.setEnabled((Boolean) event.getNewValue());
						}
				}
			});
		if (null != action) {
			action.setEnabled(isEnabled() && !locked);
			action.postCreate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IRankedController#getRank()
	 */
	public int getRank() {
		return rank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IRankedController#setRank(int)
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Gets the binding service.
	 * 
	 * @return the binding service
	 */
	public ABindingService getBindingService() {
		return parentController.getBindingService();
	}

	/**
	 * Gets the param map.
	 * 
	 * @return the param map
	 */
	public Map<String, Object> getParamMap() {
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IActionController#getRunnable()
	 */
	public AAdiRunnable getRunnable() {
		return runnable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public AAction getControl() {
		return action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		action.setEnabled(enabled && !locked);
		super.setEnabled(enabled && !locked);
		if (parentController instanceof AActionContainerCtler)
			((AActionContainerCtler) parentController).setEnabled(enabled && !locked);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#setLocked(boolean)
	 */
	@Override
	public void setLocked(boolean locked) {
		action.setEnabled(!locked);
		if (parentController instanceof AActionContainerCtler)
			((AActionContainerCtler) parentController).setLocked(locked);
	}
};
