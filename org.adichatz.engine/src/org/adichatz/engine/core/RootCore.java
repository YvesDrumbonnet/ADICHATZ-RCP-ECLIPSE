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
package org.adichatz.engine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.listener.ACoreListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.ControllerEvent;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.CustomizationMap;
import org.adichatz.engine.plugin.ICustomisationContainer;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;

// TODO: Auto-generated Javadoc
/*
 * 
 * @author Yves Drumbonnet
 *
 */
/**
 * The Class RootCore.
 * 
 * @author Yves Drumbonnet
 */
public class RootCore extends AXmlTreeCore {

	/** the plugin resources. */
	private AdiPluginResources pluginResources;

	/** The register. */
	private Map<String, RegisterEntry<?>> register = new HashMap<String, RegisterEntry<?>>();

	/** The customization map. */
	private CustomizationMap customizationMap;

	private List<ACoreListener> coreListeners = new ArrayList<>();

	/** The listeners. */
	protected Map<MultiKey, AListener> listenerMap;

	/**
	 * Instantiates a new a form editor core.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param paramMap
	 *            the param map
	 */
	public RootCore(AdiPluginResources pluginResources, ParamMap paramMap) {
		this.context = AdiContext.addContext(this, paramMap);
		this.pluginResources = pluginResources;
		rootRegister = "";
	}

	/**
	 * Inject customizations.
	 */
	public void injectCustomizations() {
		// Inject Customizations contained inside param into customization map of the Root core.
		if (context.getParamMap().containsKey(ParamMap.CUSTOMIZATION)) {
			Object paramCustomizations = context.getParamMap().get(ParamMap.CUSTOMIZATION);
			if (paramCustomizations instanceof ICustomisationContainer)
				paramCustomizations = ((ICustomisationContainer) paramCustomizations).getCustomizationMap();
			if (null == customizationMap)
				customizationMap = (CustomizationMap) paramCustomizations;
			else {
				for (Map.Entry<String, List<AListener>> entry : ((CustomizationMap) paramCustomizations).entrySet())
					for (AListener listener : entry.getValue())
						addCustomization(entry.getKey(), listener);
			}
		}
	}

	/**
	 * Gets the register.
	 * 
	 * @return the register
	 */
	public Map<String, RegisterEntry<?>> getRegister() {
		return register;
	}

	/**
	 * Gets the entry.
	 * 
	 * @param genCode
	 *            the gen code
	 * @param key
	 *            the key
	 * 
	 * @return the entry
	 */
	public RegisterEntry<?> getEntry(ControllerCore genCode, String key) {
		return register.get(genCode.getContext().getXmlTreeGenCode().getRootRegister() + key);
	}

	/**
	 * Gets the from register.
	 * 
	 * @param genCode
	 *            the gen code
	 * @param key
	 *            the key
	 * 
	 * @return the from register
	 */
	public AWidgetController getFromRegister(ControllerCore genCode, String key) {
		return getFromRegister(genCode.getContext().getXmlTreeGenCode().getRootRegister().concat(key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.core.AGenCode#getFromRegister(java.lang.String)
	 */
	public AWidgetController getFromRegister(String key) {
		RegisterEntry<?> registerEntry = register.get(key);
		if (null != registerEntry)
			return registerEntry.getController();
		return null;
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @return the plugin resources
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/**
	 * Sets the plugin resources.
	 * 
	 * @param pluginResources
	 *            the new controller engine
	 */
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	/**
	 * Gets the customizations.
	 * 
	 * @param registerId
	 *            the register id
	 * @return the customizations
	 */
	public List<AListener> getCustomizationListeners(String registerId) {
		if (null == customizationMap)
			return null;
		return customizationMap.get(registerId);
	}

	/**
	 * Removes the customization listeners.
	 * 
	 * @param registerId
	 *            the register id
	 */
	public void removeCustomizationListeners(String registerId) {
		if (null != customizationMap)
			customizationMap.remove(registerId);
	}

	/**
	 * Adds the customization.
	 * 
	 * @param key
	 *            the key
	 * @param listener
	 *            the listener
	 */
	protected void addCustomization(String key, AListener listener) {
		if (null == customizationMap)
			customizationMap = new CustomizationMap();
		customizationMap.add(key, listener);
	}

	/**
	 * Adds the open part.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 */
	public void addOpenPart(AdiPluginResources pluginResources, String treeId, String subPackage) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.core.AXmlTreeCore#createContents()
	 */
	@Override
	public void createContents() {
	}

	/**
	 * Gets the core listeners.
	 *
	 * @return the core listener list
	 */
	public List<ACoreListener> getCoreListeners() {
		return coreListeners;
	}

	/**
	 * Fire listener.
	 *
	 * @param event
	 *            the event
	 */
	public void fireListener(ControllerEvent event) {
		for (final Iterator<ACoreListener> iterator = coreListeners.iterator(); iterator.hasNext();) {
			ACoreListener listener = iterator.next();
			if (listener.getEventType() == event.getType()) {
				listener.handleEvent(event);
			}
		}
	}

	public Map<MultiKey, AListener> getListenerMap() {
		return listenerMap;
	}

	/**
	 * Adds the listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(AListener listener) {
		if (null == listenerMap)
			listenerMap = new HashMap<MultiKey, AListener>();
		listenerMap.put(new MultiKey(listener.getId()), listener);
	}

}
