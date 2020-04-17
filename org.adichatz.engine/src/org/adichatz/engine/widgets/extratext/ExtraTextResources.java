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
package org.adichatz.engine.widgets.extratext;

import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtraTextResources.
 * 
 * @author Yves Drumbonnet
 */
public class ExtraTextResources {

	/** The BOLD tooltip label for tool item. */
	public static String TOOLTIP_BOLD;

	/** The ITALIC tooltip label for tool item. */
	public static String TOOLTIP_ITALIC;

	/** The UNDERLINE tooltip label for tool item. */
	public static String TOOLTIP_UNDERLINE;

	/** The STRIKE_THROUGH tooltip label for tool item. */
	public static String TOOLTIP_STRIKE_THROUGH;

	/** The FONT tooltip label for tool item. */
	public static String TOOLTIP_FONT;

	/** The BACKGROUND tooltip label for tool item. */
	public static String TOOLTIP_BACKGROUND;

	/** The FOREGROUND tooltip label for tool item. */
	public static String TOOLTIP_FOREGROUND;

	/** The CUT tooltip label for tool item. */
	public static String TOOLTIP_CUT;

	/** The COPY tooltip label for tool item. */
	public static String TOOLTIP_COPY;

	/** The PASTE tooltip label for tool item. */
	public static String TOOLTIP_PASTE;

	/** The HYPERLINK tooltip label for tool item. */
	public static String TOOLTIP_HYPERLINK;

	static {
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(EngineConstants.ENGINE_BUNDLE);
		AdiResourceBundle resourceBundle = pluginResources.getResourceBundleManager().getResourceBundle("adichatzEngine");
		TOOLTIP_BOLD = resourceBundle.getString("extraText.bold");
		TOOLTIP_ITALIC = resourceBundle.getString("extraText.italic");
		TOOLTIP_UNDERLINE = resourceBundle.getString("extraText.underline");
		TOOLTIP_STRIKE_THROUGH = resourceBundle.getString("extraText.strikethrough");
		TOOLTIP_CUT = resourceBundle.getString("cut");
		TOOLTIP_COPY = resourceBundle.getString("copy");
		TOOLTIP_PASTE = resourceBundle.getString("paste");
		TOOLTIP_HYPERLINK = resourceBundle.getString("extraText.hyperlink");
	}

	/**
	 * The Enum TagStyle.
	 */
	public enum TagStyle {

		/** The PARAGRAPH. */
		PARAGRAPH,
		/** The BOLD. */
		BOLD,
		/** The ITALIC. */
		ITALIC,
		/** The STRIKE THROUGH. */
		STRIKE_THROUGH,
		/** The UNDERLINE. */
		UNDERLINE,
		/** The HYPERLINK. */
		HYPERLINK
	}

	/**
	 * Error.
	 * 
	 * @param error
	 *            the error
	 */
	public static void error(Exception error) {
		logError(error);
	}
}
