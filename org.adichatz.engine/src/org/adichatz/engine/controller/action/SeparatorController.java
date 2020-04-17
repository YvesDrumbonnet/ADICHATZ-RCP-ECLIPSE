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

import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IRankedController;
import org.adichatz.engine.controller.collection.ButtonBarController;
import org.adichatz.engine.controller.collection.ManagedToolBarController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.jface.action.AbstractGroupMarker;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

// TODO: Auto-generated Javadoc
/**
 * The Class TextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class SeparatorController extends AWidgetController implements IRankedController {

	private Separator separator;

	protected int rank = -1;

	/**
	 * Instantiates a new separator controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public SeparatorController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		if (parentController instanceof ButtonBarController) {
			Label label = new Label(parentController.getComposite(), style
					| (0 != (((ButtonBarController) parentController).getStyle() & SWT.VERTICAL) ? SWT.HORIZONTAL : SWT.VERTICAL));
			label.setLayoutData("grow");
		} else if (parentController instanceof ManagedToolBarController) {
			if (style != SWT.SEPARATOR)
				((ManagedToolBarController) parentController).getToolBarManager().add(new AbstractGroupMarker() {
					/*
					 * (non-Javadoc) Method declared on IContributionItem. Fills the given tool bar with a SWT separator ToolItem.
					 */
					public void fill(ToolBar toolbar, int index) {
						ToolItem toolItem = new ToolItem(toolbar, style, index);
						toolItem.setEnabled(false);
					}
				});
			else {
				separator = new Separator();
				((ManagedToolBarController) parentController).getToolBarManager().add(separator);
			}
		} else if (parentController instanceof MenuManagerController) {
			separator = new Separator();
		} else if (parentController instanceof MenuActionController) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Separator getControl() {
		return separator;
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

	public void synchronize() {
		if (null != separator)
			separator.setVisible(isVisible());
	}

	@Override
	public void setStyle(int style) {
		if (SWT.SEPARATOR != style && SWT.NONE != style)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT, null, "Valid argument is SWT.NONE or SWT.SEPARATOR!");
		super.setStyle(style);
	}
}
