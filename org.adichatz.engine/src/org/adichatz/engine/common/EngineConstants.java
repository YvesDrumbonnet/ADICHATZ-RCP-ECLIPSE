/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 * 
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 * 
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.common;

import java.io.File;
import java.util.Locale;

import org.adichatz.engine.extra.IGenerator;
import org.eclipse.core.runtime.Platform;

/**
 * The Class AdiConstants. Contains constants needed for the application Constant value can be initialized at the beginning
 */
public class EngineConstants {
	/** The Engine E4 Bundle. */
	public static String ENGINE_E4_BUNDLE = "org.adichatz.engine.e4";

	/** The Resource Bundle directory. */
	public static String RESOURCE_MESSAGE_DIR = "resources/messages";

	/** the Plugin Entity Tree string */
	public static final String PLUGIN_ENTITY_TREE = "PluginEntityTree";

	/** The path for generated code (java and class). */
	public static String GENCODE_DIR = "/resources/gencode/";

	/** The Icon Files directory. */
	public static String ICON_FILES_PATH = "/resources/icons/";

	public static String XML_FILES_PATH = "resources/xml";

	/** The GENERATOR. */
	public static IGenerator GENERATOR;

	/** Reflow (layout and pack) process could be synchronized */
	public static boolean SYNCHRONIZE_REFLOW_MODE = false;

	/** the parent name used */
	public static String PARENT_BEAN_PARAM = "$PARENT_BEAN$";

	public static String CONTENT_OUT_LINE = "org.eclipse.ui.views.ContentOutline";

	/**
	 * Login templates
	 */

	/** dialog template constant. */
	public static String LOGIN_TEMPLATE_DIALOG = "dialog";

	/** OS template constant. */
	public static String LOGIN_TEMPLATE_OS = "os";

	public static String RUNTIME = "RUNTIME";

	/**
	 * default table behavior
	 */
	public static String DEFAULT_QUERY_MAX_RESULT = "defaultQueryMaxResults";

	public static String DEFAULT_REF_TEXT_POPUP_URI = "defaultRefTextPopupURI";

	public static String INTRO_PART_URI = "introPartURI";

	public static String INTRO_OUTLINE_URI = "introOutlineURI";

	public static String POST_RESKIN_CLASS_NAME = "postReskinClassName";

	public static String APPLICATION_POST_OPEN_URI = "applicationPostOpenURI";

	public static String CHECK_BEFORE_OPEN_ENTITY_EDITOR = "checkBeanExistenceBeforeOpeningEditor";

	public static String INHIBIT_CONSOLE_ON_FATAL_ERROR = "inhibitConsoleOnfatalError";

	public static String ADICHATZ_GMAP_API_KEY = "adichatzGMapAPIKey";

	public static String ADICHATZ_AVOIDED_MESSAGES = "adichatzAvoidedMessages";

	/** The path for generated code (java and class). */
	public static String GENCODE_PACKAGE = "gencodeSubPackage";

	/**
	 * Adichatz Tool components
	 */

	/** The Generator class. */
	public static String GENERATOR_CLASS_NAME = "org.adichatz.generator.common.Generator";

	/** The Constant ENGINE_BUNDLE. */
	public static String ENGINE_BUNDLE = "org.adichatz.engine";

	/** The Constant JPA Bundle symbolic name. */
	public static String JPA_BUNDLE = "org.adichatz.jpa";

	/** The Constant COMMON_BUNDLE. */
	public static final String COMMON_BUNDLE = "org.adichatz.common";

	public static final String RESOURCES_BUNDLE = "org.adichatz.resources";

	public static String CSS_THEME_BUNDLE = "org.adichatz.css.theme";

	/** The EDITOR string. */
	public static String EDITOR = "editor";

	/** The Schema location. */
	public static String SCHEMA_LOCATION = "http://www.adichatz.org/xsd/";

	// Include separator use in register
	public static String INCLUDE_SEPARATOR = ":";

	public static String DIALOG_AUTOMATIC_RESPONSE = "adi#DialogAutomaticResponse#";

	/** The LANGUAGE. */
	public static String LANGUAGE = Locale.getDefault().getLanguage();

	private static String ADICHATZ_PERMANENT_LOCATION;

	public static String getAdiPermanentDirName() {

		if (null == ADICHATZ_PERMANENT_LOCATION) {
			ADICHATZ_PERMANENT_LOCATION = Platform.getInstallLocation().getURL().getFile().concat("/configuration/")
					.concat(AdichatzApplication.getInstance().getApplicationPluginResources().pluginName);
			new File(ADICHATZ_PERMANENT_LOCATION).mkdirs();
		}
		return ADICHATZ_PERMANENT_LOCATION;
	}

	/** The Query preference. */
	public static String PREF_FILE_URI_PREFIX = "preferenceFile://";

	public static String PREF_NAME_URI_PREFIX = "preferenceName://";

	public static String RESTORED_WIDTH = "RESTORED_WIDTH";

	public static String ADICHATZ_THEME_POINT = "org.adichatz.css.theme";

	public static String DEFAULT_STYLE_SHEET_URI = "platform:/plugin/".concat(EngineConstants.CSS_THEME_BUNDLE)
			.concat("/resources/css/blue-win.css");
}
