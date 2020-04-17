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

import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Control;

// TODO: Auto-generated Javadoc
/**
 * The Class AControlController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AControlController extends AWidgetController implements IControlController {
	public final static String ADI_CSS_BACKGROUND = "adiCssBackground";

	public final static String ADI_CSS_FOREGROUND = "adiCssForeground";

	public final static String ADI_CSS_FONT = "adiCssFont";

	protected Control childControl;

	/** The dispose listener. */
	private DisposeListener disposeListener;

	/**
	 * Instantiates a new a control controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public AControlController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/**
	 * Gets the control.
	 * 
	 * @return the control
	 */
	@Override
	public abstract Control getControl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IControlController#pack()
	 */
	public void pack() {
		getControl().pack();
	}

	@Override
	public void endLifeCycle() {
		if (null != getControl() && !getControl().isDisposed()) {
			super.endLifeCycle();
			if (null == getControl().getMenu())
				getControl().setMenu(getControl().getParent().getMenu());
			if (null != listenerMap && null == disposeListener) {
				disposeListener = new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent e) {
						listenerMap = null;
					}
				};
				getControl().addDisposeListener(disposeListener);
			}

		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (null != getControl() && !getControl().isDisposed() && enabled != getControl().isEnabled())
			getControl().setEnabled(enabled);
	}

	public void setCssBackground(String selector, String property) {
		Color color;
		if (null != selector)
			color = AReskinManager.getInstance().getColor(selector, property, ADI_CSS_BACKGROUND, getControl());
		else {
			color = null;
			AReskinManager.getInstance().removeBackgroundSkinnedWidget(getControl());
		}
		setBackground(color);
	}

	public void setCssForeground(String selector, String property) {
		Color color;
		if (null != selector)
			color = AReskinManager.getInstance().getColor(selector, property, ADI_CSS_FOREGROUND, getControl());
		else {
			color = null;
			AReskinManager.getInstance().removeForegroundSkinnedWidget(getControl());
		}
		setForeground(color);
	}

	public void setCssFont(String selector) {
		Font font;
		if (null != selector)
			font = AReskinManager.getInstance().getFont(selector, getControl());
		else {
			font = null;
			AReskinManager.getInstance().removeFontSkinnedWidget(getControl());
		}
		setFont(font);
	}

	public void setBackground(Color color) {
		setBackground(getControl(), color);
		if (null != childControl)
			setBackground(childControl, color);
	}

	private void setBackground(Control control, Color color) {
		control.setBackground(color);
		if (null == color)
			control.setData(ADI_CSS_BACKGROUND, null);
		else
			control.setData(ADI_CSS_BACKGROUND, "true");

	}

	public void setForeground(Color color) {
		setForeground(getControl(), color);
		if (null != childControl)
			setForeground(childControl, color);
	}

	private void setForeground(Control control, Color color) {
		control.setForeground(color);
		if (null == color)
			control.setData(ADI_CSS_FOREGROUND, null);
		else
			control.setData(ADI_CSS_FOREGROUND, "true");
	}

	public void setFont(Font font) {
		setFont(getControl(), font);
		if (null != childControl)
			setFont(childControl, font);
	}

	private void setFont(Control control, Font font) {
		control.setFont(font);
		if (null == font)
			control.setData(ADI_CSS_FONT, null);
		else
			control.setData(ADI_CSS_FONT, "true");
	}
}
