/*******************************************************************************
 * * Copyright © Adichatz (2007-2020) - www.adichatz.org
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
package org.adichatz.engine.plugin;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.simulation.ARootCorePostOpen;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.wrapper.IMarshalledWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class ParamMap.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ParamMap extends HashMap<String, Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2431429519453341828L;

	/** The Constant FULLTEXT_RESOURCE_URI. */
	public static final String FULLTEXT_RESOURCE_URI = "FULLTEXT_RESOURCE_URI";

	/** The Constant POPUP_FORM_TEXT. */
	public static final String POPUP_FORM_TEXT = "POPUP_FORM_TEXT";

	/** The Constant POPUP_SIZE. */
	public static final String POPUP_SIZE = "POPUP_SIZE";

	/** The Constant POPUP_URI. */
	public static final String POPUP_URI = "POPUP_URI";

	/** The constant string for Editor Id. */
	public static final String EDITOR_ID = "EDITOR_ID";

	/** The Constant TOOL_BAR. */
	public static final String TOOL_BAR_TYPE = "TOOL_BAR_TYPE";

	/** The Constant PLUGIN_KEY. */
	public static final String PLUGIN_KEY = "PLUGIN_KEY";

	/** The Constant PLUGIN_RESOURCES. */
	public static final String PLUGIN_RESOURCES = "PLUGIN_RESOURCES";

	/** The Constant DEFAULT_PLUGIN_RESOURCES. */
	public static final String DEFAULT_PLUGIN_RESOURCES = "DEFAULT_PLUGIN_RESOURCES";

	/** The Constant BINDING_SERVICE. */
	public static final String BINDING_SERVICE = "BINDING_SERVICE";

	/** The Constant COMMAND. */
	public static final String COMMAND = "COMMAND";

	/** The constant string for Parent Bean. */
	public static String PARENT_BEAN = "PARENT_BEAN";

	/** The constant string for Parent Entity. */
	public static String PARENT_ENTITY = "PARENT_ENTITY";

	/** The constant string forMessage Bundle. */
	public static String MESSAGE_BUNDLE = "MESSAGE_BUNDLE";

	/** The constant string for Contribution URI. */
	public static String CONTRIBUTION_URI = "CONTRIBUTION_URI";

	/** The constant string for Customization. */
	public static String CUSTOMIZATION = "CUSTOMIZATION";

	/** The constant string for DEFAULT. */
	public static String DEFAULT = "DEFAULT";

	/** The Editor. */
	public static String EDITOR = "EDITOR";

	/** The Properties. */
	public static String PROPERTIES = "PROPERTIES";

	/** The constant string for Controller. */
	public static String CONTROLLER = "CONTROLLER";

	/** The constant string for form page Controller. */
	public static String FORM_PAGE_CONTROLLER = "FORM_PAGE_CONTROLLER";

	/** The constant string for Entity. */
	public static String ENTITY = "ENTITY";

	/** The constant string for CREATION. */
	public static String CREATION_ITEM = "CREATION_ITEM";

	/** The constant string for QUERY_ITEM. */
	public static String QUERY_ITEM = "QUERY_ITEM";

	/** The constant string for ADD_EDITOR_TOOLBAR. */
	public static String ADD_EDITOR_TOOLBAR = "ADD_EDITOR_TOOLBAR";

	/** The constant string for FORM_TEXT. */
	public static String FORM_TEXT = "FORM_TEXT";

	/** The constant string for Entity. */
	public static String ENTITY_ID = "ENTITY_ID";

	/** The constant string for IMAGE. */
	public static String IMAGE = "IMAGE";

	/** The CONTEX t_ menu. */
	public static String CONTEXT_MENU = "CONTEXT_MENU";

	/** The constant string for CONTENT_PROVIDER. */
	public static String CONTENT_PROVIDER = "CONTENT_PROVIDER";

	/** The constant string for KEY. */
	public static String KEY = "KEY";

	/** The constant string for QUERY. */
	public static String QUERY = "QUERY";

	/** The constant string for Title. */
	public static String TITLE = "TITLE";

	/** The constant string for IconURI. */
	public static String ICON_URI = "ICON_URI";

	/** The constant string for Tool selected object. */
	public static String SELECTED_OBJECT = "SELECTED_OBJECT";

	/** The constant string for Tabular Controller. */
	public static String TABULAR_CONTROLLER = "TABULAR_CONTROLLER";

	/** The constant string for Tree Controller. */
	public static String TREE_CONTROLLER = "TREE_CONTROLLER";

	/** The constant string for Tool tip text. */
	public static String TOOL_TIP_TEXT = "TOOL_TIP_TEXT";

	/** constant string for Adichatz resource URI. */
	public static String ADI_RESOURCE_URI = "ADI_RESOURCE_URI";

	/** constant string for Table resource URI. */
	public static String TABLE_RESOURCE_URI = "TABLE_RESOURCE_URI";

	/** constant string for Detail resource URI. */
	public static String DETAIL_RESOURCE_URI = "DETAIL_RESOURCE_URI";

	/** constant string for Entity resource URI. */
	public static String ENTITY_URI = "ENTITY_URI";

	/** constant string for Duplicate Editor (several editors whith same ParamMap can be open) . */
	public static String DUPLICATE_EDITOR = "DUPLICATE_EDITOR";

	/** The post open editor uri. */
	public static String POST_OPEN_EDITOR_URI = "POST_OPEN_EDITOR_URI";

	/** The post open editor instance. */
	public static String POST_OPEN_EDITOR_INSTANCE = "POST_OPEN_EDITOR_CLASS";

	/** The new entity popup customization (Customization read by EditNewEntityPopup). */
	public static String POPUP_CUSTOMIZATION = "POPUP_CUSTOMIZATION";

	/** A fatal error is encountered in BoundedPart process (e.g. bean no longer exists on database) */
	public static String BOUNDED_PART_ERROR = "BOUNDED_PART_ERROR";

	/** The save in recent list. */
	private Boolean saveInRecentList;

	/** The constant string outline page. */
	public static String OUTLINE_PAGE = "OUTLINE_PAGE";

	/**
	 * Gets the string.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the string
	 */
	public String getString(String key) {
		return (String) get(key);
	}

	/**
	 * Get and remove object.
	 * 
	 * @param key
	 *            the key
	 * @return the object
	 */
	public Object pop(String key) {
		Object result = get(key);
		remove(key);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object someObject) {
		if (null == someObject || !(someObject instanceof ParamMap))
			return false;
		ParamMap paramMap = (ParamMap) someObject;
		if (getSize(paramMap) != getSize(this))
			return false;
		for (Map.Entry<String, Object> entry : entrySet()) {
			Object value = entry.getValue();
			if (value instanceof AController || value instanceof ARootCorePostOpen)
				continue;
			Object someValue = paramMap.get(entry.getKey());
			if (null == someValue)
				return false;
			value = value instanceof IMarshalledElement ? ((IMarshalledElement) value).preMarshal(false) : value;
			someValue = someValue instanceof IMarshalledElement ? ((IMarshalledElement) someValue).preMarshal(false) : someValue;
			if (!Utilities.equals(value, someValue))
				return false;
		}
		return true;
	}

	/**
	 * Gets the size.
	 *
	 * @param paramMap
	 *            the param map
	 * @return the size
	 */
	private int getSize(ParamMap paramMap) {
		int size = 0;
		for (Object element : paramMap.values())
			if (!(element instanceof AController) && !(element instanceof ARootCorePostOpen))
				size++;
		return size;
	}

	/**
	 * Post unmarshal.
	 */
	public void postUnmarshal() {
		for (Map.Entry<String, Object> entry : entrySet()) {
			if (entry.getValue() instanceof IMarshalledWrapper)
				entry.setValue(((IMarshalledWrapper) entry.getValue()).postUnmarshal());
		}
	}

	/**
	 * Checks if is save in recent list.
	 *
	 * @return true, if is save in recent list
	 */
	public boolean isSaveInRecentList() {
		if (null == saveInRecentList) {
			saveInRecentList = true;
			Object customization = get(ParamMap.CUSTOMIZATION);
			if (null != customization && !ReflectionTools.hasInterface(customization.getClass(), IMarshalledElement.class))
				saveInRecentList = false;
		}
		return saveInRecentList;
	}

	/**
	 * Clone and marshal.
	 *
	 * @return the param map
	 */
	public ParamMap cloneAndMarshal() {
		ParamMap paramMap = new ParamMap();
		for (Map.Entry<String, Object> entry : entrySet())
			if (entry.getValue() instanceof IMarshalledWrapper)
				paramMap.put(entry.getKey(), ((IMarshalledWrapper) entry.getValue()).postUnmarshal());
			else
				paramMap.put(entry.getKey(), entry.getValue());
		return paramMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		if (null == value) {
			logError(getFromEngineBundle("error.param.value.null", key));
			return null;
		}
		return super.put(key, value);
	}

	/**
	 * Put optional.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the object
	 */
	public Object putOptional(String key, Object value) {
		if (null == value) {
			return null;
		}
		return super.put(key, value);
	}
}
