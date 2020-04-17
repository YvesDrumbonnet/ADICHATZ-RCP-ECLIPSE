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
package org.adichatz.studio.util;

// TODO: Auto-generated Javadoc
/**
 * The Interface IStudioConstants.
 */
public interface IStudioConstants {

	/**
	 * ************************************************************************<br>
	 * P R E F E R E N C E S<br>
	 * ************************************************************************ .
	 */
	/** The Constant LEVEL_ON_DUPLICATE_IDENTIFIER. */
	public static final String LEVEL_ON_DUPLICATE_IDENTIFIER = "level.on.duplicate.identifier";

	/** The Constant LEVEL_IDENTIFIER_MANDATORY. */
	public static final String LEVEL_IDENTIFIER_MANDATORY = "level.identifier.mandatory";

	/** The Constant LEVEL_CONTROLLER_CLASS. */
	public static final String LEVEL_CONTROLLER_CLASS = "level.controller_class";

	/** The Constant LEVEL_COMBINED_CLASS. */
	public static final String LEVEL_COMBINED_CLASS = "level.combined_class";

	/** The Constant LEVEL_COLUMN_NAME. */
	public static final String LEVEL_PROPERTY_NAME = "level.column_name";

	/** The Constant LEVEL_ENTITY_URI. */
	public static final String LEVEL_ENTITY_URI = "level.entity_URI";

	/** The Constant LEVEL_ADI_RESOURCE_URI. */
	public static final String LEVEL_ADI_RESOURCE_URI = "level.adi_resource_URI";

	/** The Constant GENERATE_CLASS_AFTER_SAVING. */
	public static final String GENERATE_CLASS_AFTER_SAVING = "devl.generate.class.after.saving";

	public static final String ASK_FOR_SWITCHING_FILE = "devl.ask_for_switching_FILE";

	public static final String SCENARIO_TREE_GENERATION_URI = "devl.scenarioTree.generation.uri";

	/** The Constant CUSTOMIZATION_FILE_MODEL. */
	public static final String CUSTOMIZATION_FILE_MODEL = "devl.customization.file.model.project";

	/** The Constant CUSTOMIZATION_FILE_RCP. */
	public static final String CUSTOMIZATION_FILE_RCP = "devl.customization.file.rcp.project";

	/**
	 * ************************************************************************<br>
	 * Other elements<br>
	 * ************************************************************************ .
	 */
	/** The Constant STUDIO_PERSPECTIVE_ID. */
	public static final String STUDIO_PERSPECTIVE_ID = "org.adichatz.studio.ToolPerspective";

	/** The Studio MENU navigator id. */
	public static String STUDIO_MENU_NAVIGATOR_ID = "org.adichatz.studio.navigator.MenuNavigator";

	public final static String SCENARIO_EDITOR = "ScenarioEditor";

	public final static String DATASOURCE_PART = "DatasourceManagerPart";

	public final static String APPLICATION_SERVER_PART = "ApplicationServerManagerPart";

	public final static String XJC_TREE_SUB_PACKAGE = "tree";

	public final static String XJC_TREE_EDITOR = "XjcTreeEditor";

	public final static String EXTERNAL_RESOURCES_EDITOR = "ExternalResourcesEditor";

	public final static String XJC_FORM_PAGE_ID = "page";

	public final static String XJC_TREE_ELEMENTS = "xjcTreeElements";

	public final static String STUDIO_MODEL_PACKAGE = "org.adichatz.generator.xjc";

}
