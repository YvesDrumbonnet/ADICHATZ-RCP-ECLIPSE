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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.Session;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.menu.NavigatorPath;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.AdiImageDescriptor;
import org.adichatz.engine.widgets.DefaultImageDescriptor;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

// TODO: Auto-generated Javadoc
/**
 * The Class AdichatzApplication.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class AdichatzApplication {

	/*
	 * S T A T I C
	 */

	/** The THIS. */
	protected static AdichatzApplication THIS;

	/**
	 * Gets the plugin resources.
	 * 
	 * @param pluginResourcesKey the plugin resources key
	 * @return the plugin resources
	 */
	public static AdiPluginResources getPluginResources(String pluginResourcesKey) {
		return THIS._getPluginResources(pluginResourcesKey);
	}

	/**
	 * Adds the plugin resources.
	 * 
	 * @param pluginResources the plugin resources
	 */
	public static void addPluginResources(AdiPluginResources pluginResources) {
		getInstance().getPluginMap().put(pluginResources.getPluginName(), pluginResources);
	}

	/**
	 * Gets the single instance of AdichatzApplication.
	 * 
	 * @return single instance of AdichatzApplication
	 */
	public static AdichatzApplication getInstance() {
		return THIS;
	}

	/*
	 * end S T A T I C
	 */

	/** The application plugin resources. */
	protected AdiPluginResources applicationPluginResources;

	/** The class loader. */
	private ClassLoader classLoader;

	/** The plugin map. */
	protected Map<String, AdiPluginResources> pluginMap = new HashMap<String, AdiPluginResources>();

	/** The SESSION. */
	private Session session;

	/** The application param map. */
	protected Map<String, Object> applicationParamMap = new HashMap<String, Object>();

	/** The early startup hooks. */
	protected List<Runnable> earlyStartupHooks = new ArrayList<>();

	/** The form toolkit. */
	protected AdiFormToolkit formToolkit;

	/** The config tree. */
	protected AdichatzRcpConfigTree configTree;

	/** The navigator map. */
	protected Map<String, NavigatorPath> navigatorMap = new HashMap<String, NavigatorPath>();

	/** The application listeners. */
	protected List<AApplicationListener> applicationListeners = new ArrayList<AApplicationListener>();

	/**
	 * Instantiates a new adichatz application.
	 * 
	 */
	public AdichatzApplication() {
		THIS = this;
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @param pluginResourcesKey the plugin resources key
	 * @return the plugin resources
	 */
	protected AdiPluginResources _getPluginResources(String pluginResourcesKey) {
		AdiPluginResources pluginResources = pluginMap.get(pluginResourcesKey);
		if (null == pluginResources) {
			Bundle bundle = Platform.getBundle(pluginResourcesKey);
			if (null == bundle) {
				throw new RuntimeException("Plugin '" + pluginResourcesKey + "' is unknown!");
			} else
				try {
					bundle.start();
					// PluginResources is added by pluginMap in bundle start process
					return pluginMap.get(pluginResourcesKey);
				} catch (BundleException e) {
					logError(e);
				}
		}
		return pluginResources;
	}

	/**
	 * Gets the plugin map.
	 * 
	 * @return the plugin map
	 */
	public Map<String, AdiPluginResources> getPluginMap() {
		return pluginMap;
	}

	/**
	 * Gets the session.
	 * 
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Sets the session.
	 * 
	 * @param session the new session
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Gets the config tree.
	 *
	 * @return the config tree
	 */
	public AdichatzRcpConfigTree getConfigTree() {
		return configTree;
	}

	/**
	 * Gets the navigator map.
	 * 
	 * @return the navigator map
	 */
	public Map<String, NavigatorPath> getNavigatorMap() {
		return navigatorMap;
	}

	/**
	 * Gets the image descriptor.
	 * 
	 * @param pluginKey          the plugin key
	 * @param imageDescriptorKey the image descriptor key
	 * @return the image descriptor
	 */
	public AdiImageDescriptor getImageDescriptor(String pluginKey, String imageDescriptorKey) {
		try {
			return getPluginResources(pluginKey).getImageManager().getImageDescriptor(imageDescriptorKey);
		} catch (NullPointerException e) {
			logError("No plugin '" + pluginKey + "'" + " or no image '" + imageDescriptorKey + "' were found!");
			return new DefaultImageDescriptor();
		}
	}

	/**
	 * Gets the image.
	 * 
	 * @param pluginKey the plugin key
	 * @param imageKey  the image key
	 * @return the image
	 */
	public Image getImage(String pluginKey, String imageKey) {
		return getImageDescriptor(pluginKey, imageKey).createImage();
	}

	/**
	 * Gets the message.
	 * 
	 * @param pluginKey   the plugin key
	 * @param resourceKey the resource key
	 * @param key         the key
	 * @param variables   the variables
	 * @return the message
	 */
	public String getMessage(String pluginKey, String resourceKey, String key, Object... variables) {
		try {
			return getPluginResources(pluginKey).getMessage(resourceKey, key, variables);
		} catch (NullPointerException e) {
			logError("No plugin '" + pluginKey + "'" + " or no properties file '" + resourceKey + ".properties' were found!");
			return "???".concat(key).concat("???");
		}
	}

	/**
	 * Gets the message resource bundle.
	 * 
	 * @param resourceBundleURI the message resource bundle URI
	 * @param key               the key
	 * @param variables         the variables
	 * @return the message resource bundle
	 */
	public String getMessageBundle(String resourceBundleURI, String key, Object... variables) {
		if (null == resourceBundleURI) {
			logError(getFromEngineBundle("error.no.message.bundle", key));
			return key;
		}
		String[] bundleKeys = EngineTools.getInstanceKeys(resourceBundleURI);
		String resourceKey = bundleKeys[2];
		if (!".".equals(bundleKeys[1]))
			resourceKey = bundleKeys[1].concat("/").concat(resourceKey);

		return getMessage(bundleKeys[0], resourceKey, key, variables);
	}

	/**
	 * Gets the application listeners.
	 *
	 * @return the application listener list
	 */
	public List<AApplicationListener> getApplicationListeners() {
		return applicationListeners;
	}

	/**
	 * Gets the early startup hooks.
	 *
	 * @return the early startup hooks
	 */
	public List<Runnable> getEarlyStartupHooks() {
		return earlyStartupHooks;
	}

	/**
	 * Fire listener.
	 *
	 * @param event the event
	 */
	public static void fireListener(ApplicationEvent event) {
		for (final Iterator<AApplicationListener> iterator = THIS.applicationListeners.iterator(); iterator.hasNext();) {
			AApplicationListener listener = iterator.next();
			if (listener.getEventType() == event.eventType) {
				listener.handleEvent(event);
				if (listener.isToBeRemoved())
					iterator.remove();
			}
		}
	}

	/**
	 * Gets the application plugin resource.
	 * 
	 * @return the application plugin resource
	 */
	public AdiPluginResources getApplicationPluginResources() {
		return applicationPluginResources;
	}

	/**
	 * Sets the application plugin resources.
	 * 
	 * @param applicationPluginResources the new application plugin resources
	 */
	public void setApplicationPluginResources(AdiPluginResources applicationPluginResources) {
		this.applicationPluginResources = applicationPluginResources;
	}

	/**
	 * Gets the class loader.
	 *
	 * @return the class loader
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * Prepare and instantiate class.
	 *
	 * @param pluginResources     the plugin resources
	 * @param containerController the container controller
	 * @param paramMap            the param map
	 * @return the object
	 */
	public static Object prepareAndInstantiateClass(AdiPluginResources pluginResources, IContainerController containerController,
			ParamMap paramMap) {
		String adiResourceURI = paramMap.getString(ParamMap.ADI_RESOURCE_URI);
		if (null == adiResourceURI)
			throw new RuntimeException(getFromEngineBundle("error.uri.missing.parameter", ParamMap.ADI_RESOURCE_URI));
		String[] instanceKeys = EngineTools.getInstanceKeys(adiResourceURI);
		if (null == pluginResources && null == instanceKeys[0])
			throw new RuntimeException(getFromEngineBundle("error.uri.no.plugin.key", adiResourceURI));
		if (null != instanceKeys[0] && (null == pluginResources || !instanceKeys[0].equals(pluginResources.getPluginName())))
			pluginResources = getPluginResources(instanceKeys[0]);
		return pluginResources.prepareAndInstantiateClass(instanceKeys[2], instanceKeys[1],
				new Class[] { AdiPluginResources.class, IContainerController.class, ParamMap.class },
				new Object[] { pluginResources, containerController, paramMap });
	}

	/**
	 * Prepare and instantiate class.
	 * 
	 * @param adiResourceURI the adi resource uri
	 * @param paramClasses   the param classes
	 * @param params         the params
	 * @return the object
	 */
	public static Object prepareAndInstantiateClass(String adiResourceURI, @SuppressWarnings("rawtypes") Class[] paramClasses,
			Object[] params) {
		if (!adiResourceURI.startsWith("bundleclass://")) {
			String[] instanceKeys = EngineTools.getInstanceKeys(adiResourceURI);
			AdiPluginResources pluginResources = getPluginResources(instanceKeys[0]);
			return pluginResources.prepareAndInstantiateClass(instanceKeys[2], instanceKeys[1], paramClasses, params);
		} else {
			String[] instanceKeys = EngineTools.getContributionURIToStrings(adiResourceURI);
			AdiPluginResources pluginResources = getPluginResources(instanceKeys[0]);
			if (null == pluginResources)
				throw new RuntimeException("Invalid plugin key '" + instanceKeys[0] + "' from URI '" + adiResourceURI + "'!");
			return pluginResources.getGencodePath().instantiateClass(instanceKeys[1], paramClasses, params);
		}

	}

	/**
	 * Gets the plugin entity.
	 * 
	 * @param entityURI the entity URI
	 * @return the entity MetaModel
	 */
	public PluginEntity<?> getPluginEntity(String entityURI) {
		String[] instanceKeys = EngineTools.getInstanceKeys(entityURI);
		AdiPluginResources pluginResources = _getPluginResources(instanceKeys[0]);
		if (null == pluginResources)
			return null;
		return pluginResources.getPluginEntity(entityURI);
	}

	/**
	 * Gets the form toolkit.
	 *
	 * @return the form toolkit
	 */
	public AdiFormToolkit getFormToolkit() {
		if (null == formToolkit) {
			formToolkit = new AdiFormToolkit(Display.getCurrent());
		}
		return formToolkit;
	}

	/**
	 * Sets the form toolkit.
	 *
	 * @param formToolkit the new form toolkit
	 */
	public void setFormToolkit(AdiFormToolkit formToolkit) {
		this.formToolkit = formToolkit;
	}

	/**
	 * Gets the application param map.
	 *
	 * @return the application param map
	 */
	public Map<String, Object> getApplicationParamMap() {
		return applicationParamMap;
	}

	public Object getParam(String key) {
		return applicationParamMap.get(key);
	}

	public Object popParam(String key) {
		Object value = applicationParamMap.get(key);
		applicationParamMap.remove(key);
		return value;
	}

	public void reapplyStyles(Control control) {
	}
}
