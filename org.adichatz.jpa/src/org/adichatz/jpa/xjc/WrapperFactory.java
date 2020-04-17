/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.jpa.xjc;

import org.adichatz.jpa.wrapper.ControllerPreferenceWrapper;
import org.adichatz.jpa.wrapper.CustomizationClassParamWrapper;
import org.adichatz.jpa.wrapper.EntityParamWrapper;
import org.adichatz.jpa.wrapper.EntitySetContentProviderWrapper;
import org.adichatz.jpa.wrapper.ListDetailContentProviderWrapper;
import org.adichatz.jpa.wrapper.QueryContentProviderWrapper;
import org.adichatz.jpa.wrapper.QueryOpenClauseWrapper;
import org.adichatz.jpa.wrapper.QueryPaginationWrapper;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.wrapper.RecentOpenEditorTreeWrapper;
import org.adichatz.jpa.wrapper.RecentOpenEditorWrapper;
import org.adichatz.jpa.wrapper.RecentOpenQueryEditorWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Wrapper objects.
 */
public class WrapperFactory {

	/**
	 * Gets the controller preference type.
	 *
	 * @return the controller preference type
	 */
	@SuppressWarnings("rawtypes")
	public static ControllerPreferenceType getControllerPreferenceType() {
		return new ControllerPreferenceWrapper();
	}

	/**
	 * Gets the entity param type.
	 * 
	 * @return the entity param type
	 */
	public static EntityParamType getEntityParamType() {
		return new EntityParamWrapper(null);
	}

	/**
	 * Gets the entity set content provider type.
	 * 
	 * @return the entity set content provider type
	 */
	public static EntitySetContentProviderType getEntitySetContentProviderType() {
		return new EntitySetContentProviderWrapper();
	}

	/**
	 * Gets the list detail content provider type.
	 * 
	 * @return the list detail content provider type
	 */
	public static ListDetailContentProviderType getListDetailContentProviderType() {
		return new ListDetailContentProviderWrapper();
	}

	/**
	 * Gets the query content provider type.
	 * 
	 * @return the query content provider type
	 */
	public static QueryContentProviderType getQueryContentProviderType() {
		return new QueryContentProviderWrapper();
	}

	/**
	 * Gets the query preference type.
	 * 
	 * @return the query preference type
	 */
	@SuppressWarnings("rawtypes")
	public static QueryPreferenceType getQueryPreferenceType() {
		return new QueryPreferenceWrapper();
	}

	/**
	 * Gets the query parameter type.
	 * 
	 * @return the query parameter type
	 */
	public static QueryParameterType getQueryParameterType() {
		return new QueryParameterWrapper();
	}

	/**
	 * Gets the query open clause type.
	 *
	 * @return the query open clause type
	 */
	public static QueryOpenClauseType getQueryOpenClauseType() {
		return new QueryOpenClauseWrapper();
	}

	/**
	 * Gets the query pagination type.
	 * 
	 * @return the query pagination type
	 */
	public static QueryPaginationType getQueryPaginationType() {
		return new QueryPaginationWrapper();
	}

	/**
	 * Gets the recent open editor tree.
	 *
	 * @return the recent open editor tree
	 */
	public static RecentOpenEditorTree getRecentOpenEditorTree() {
		return new RecentOpenEditorTreeWrapper();
	}

	/**
	 * Gets the recent open editor type.
	 * 
	 * @return the recent open editor type
	 */
	public static RecentOpenEditorType getRecentOpenEditorType() {
		return new RecentOpenEditorWrapper();
	}

	/**
	 * Gets the recent open query editor type.
	 *
	 * @return the recent open query editor type
	 */
	public static RecentOpenQueryEditorType getRecentOpenQueryEditorType() {
		return new RecentOpenQueryEditorWrapper();
	}

	/**
	 * Gets the recent preference type.
	 *
	 * @return the recent preference type
	 */
	public static RecentPreferenceType getRecentPreferenceType() {
		return new RecentPreferenceWrapper();
	}

	/**
	 * Gets the customization param type.
	 * 
	 * @return the customization param type
	 */
	public static CustomizationClassParamType getCustomizationClassParamType() {
		return new CustomizationClassParamWrapper();
	}

}
