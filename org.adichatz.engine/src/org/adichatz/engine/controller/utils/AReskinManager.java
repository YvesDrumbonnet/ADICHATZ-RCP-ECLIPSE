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
package org.adichatz.engine.controller.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class AReskinManager.
 *
 * @author Yves Drumbonnet
 */
public abstract class AReskinManager {

	/*
	 * S T A T I C
	 */

	/** The this. */
	protected static AReskinManager THIS;

	/**
	 * Gets the single instance of AdiReskinManager.
	 *
	 * @return single instance of AdiReskinManager
	 */
	public static AReskinManager getInstance() {
		return THIS;
	}

	/** The Post-Reskin listeners. */
	protected List<IPostReskinListener> postReskinListeners = new ArrayList<>();

	/** The color properties map. */
	protected Map<String, Set<String>> colorPropertiesMap = new HashMap<>();

	/**
	 * Gets the rgb.
	 *
	 * @param selector
	 *            the selector
	 * @param property
	 *            the property
	 * @return the rgb
	 */
	public abstract RGB getRGB(String selector, String property);

	/**
	 * Gets the color.
	 *
	 * @param selector the selector
	 * @param property the property
	 * @param element the element
	 * @return the color
	 */
	public abstract Color getColor(String selector, String property, Object element);

	/**
	 * Gets the color.
	 *
	 * @param selector
	 *            the selector
	 * @param property
	 *            the property
	 * @param colorType
	 *            the color type
	 * @param element
	 *            the element
	 * @return the color
	 */
	public abstract Color getColor(String selector, String property, String colorType, Object element);

	/**
	 * Gets the font.
	 *
	 * @param selector
	 *            the selector
	 * @param element
	 *            the element
	 * @return the font
	 */
	public abstract Font getFont(String selector, Object element);

	/**
	 * Adds the theme.
	 *
	 * @param themeEngine
	 *            the theme engine
	 * @param theme
	 *            the theme
	 */
	public abstract void addTheme(Object themeEngine, Object theme);

	/**
	 * Adds the skinned widget.
	 *
	 * @param widget
	 *            the widget
	 */
	public abstract void addSkinnedWidget(Widget widget);

	/**
	 * Removes the background skinned widget.
	 *
	 * @param widget the widget
	 */
	public abstract void removeBackgroundSkinnedWidget(Widget widget);

	/**
	 * Removes the foreground skinned widget.
	 *
	 * @param widget the widget
	 */
	public abstract void removeForegroundSkinnedWidget(Widget widget);

	/**
	 * Removes the fontground skinned widget.
	 *
	 * @param widget the widget
	 */
	public abstract void removeFontSkinnedWidget(Widget widget);

	/**
	 * Post reskin.
	 *
	 */
	public abstract void postReskin();

	/**
	 * Adds the reskin listener.
	 *
	 * @param listener
	 *            the listener
	 * @param widget
	 *            the widget
	 */
	public void addReskinListener(IPostReskinListener listener, Widget widget) {
		postReskinListeners.add(listener);
		if (null != widget)
			widget.addDisposeListener((e) -> {
				postReskinListeners.remove(listener);
			});
	}
}
