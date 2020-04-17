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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

// TODO: Auto-generated Javadoc
/**
 * The Class AController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class AController implements IController {
	public static Color ERROR_COLOR;
	static {
		if (null != AReskinManager.getInstance())
			ERROR_COLOR = AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR,
					"error-foreground-color", null, null);
		else
			ERROR_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	}

	/** The accessibilities. */
	protected List<ElementaryAccessibility> accessibilities = new ArrayList<ElementaryAccessibility>();

	/** The valid. */
	protected boolean valid = true;

	/** The visible. */
	protected boolean visible = true;

	/** The enabled. */
	protected boolean enabled = true;

	/**
	 * The listeners. Use a multikey to avoid listener duplication. For listener coming from customization (config) duplication is
	 * accepted
	 */
	protected Map<MultiKey, AListener> listenerMap;

	/**
	 * Gets the accessibilities.
	 * 
	 * @return the accessibilities
	 */
	public List<ElementaryAccessibility> getAccessibilities() {
		return accessibilities;
	}

	/**
	 * Adds the accessibility.
	 * 
	 * @param accessibility
	 *            the accessibility
	 */
	public void addAccessibility(ElementaryAccessibility accessibility) {
		accessibilities.add(accessibility);
	}

	/**
	 * Checks if is valid.
	 * 
	 * @return true, if is valid
	 */
	public boolean isValid() {
		for (ElementaryAccessibility accessibility : accessibilities)
			if (ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID == accessibility.getAccessAttribute())
				if (!accessibility.accept())
					return false;
		return true;
	}

	/**
	 * Checks if is visible.
	 * 
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		for (ElementaryAccessibility accessibility : accessibilities)
			if (ElementaryAccessibility.ACCESS_ATTRIBUTE.VISIBLE == accessibility.getAccessAttribute())
				if (!accessibility.accept())
					return false;
		return true;
	}

	/**
	 * Checks if is enabled.
	 * 
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		if (!enabled)
			return false;
		for (ElementaryAccessibility accessibility : accessibilities)
			if (ElementaryAccessibility.ACCESS_ATTRIBUTE.ENABLED == accessibility.getAccessAttribute()) {
				boolean accepted = accessibility.accept();
				setEnabled(accepted);
			}
		return enabled;
	}

	/**
	 * Sets the enabled.
	 * 
	 * @param enabled
	 *            the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the listener map.
	 * 
	 * @return the listener map
	 */
	public Map<MultiKey, AListener> getListenerMap() {
		return listenerMap;
	}

	public void setListenerMap(Map<MultiKey, AListener> listenerMap) {
		this.listenerMap = listenerMap;
	}
}
