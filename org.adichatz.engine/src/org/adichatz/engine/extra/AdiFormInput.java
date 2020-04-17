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
package org.adichatz.engine.extra;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.plugin.ParamMap;
import org.eclipse.jface.window.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiFormInput.
 */
public class AdiFormInput {

	/** The part core. */
	private AContainerCore partCore;

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The entity. */
	protected IEntity<?> entity;

	/** The part uri. */
	private String partURI;

	/** The param map. */
	protected ParamMap paramMap;

	/** The window. */
	private Window window;

	/** The window. */
	private Object part;

	/**
	 * Instantiates a new adi form input.
	 *
	 * @param partURI
	 *            the part uri
	 * @param paramMap
	 *            the param map
	 */
	public AdiFormInput(AdiPluginResources pluginResource, String partURI, ParamMap paramMap) {
		this.partURI = partURI;
		this.paramMap = null == paramMap ? new ParamMap() : paramMap;
		if (null != pluginResource)
			this.pluginResources = pluginResource;
		else if (null != partURI) {
			String pluginKey = null;
			if (partURI.startsWith("bundleclass://"))
				pluginKey = EngineTools.getContributionURIToStrings(partURI)[0];
			else
				pluginKey = EngineTools.getInstanceKeys(partURI)[0];
			if (null != pluginKey)
				this.pluginResources = AdichatzApplication.getPluginResources(pluginKey);
		}
	}

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public IEntity<?> getEntity() {
		return entity;
	}

	/**
	 * Sets the entity.
	 * 
	 * @param entity
	 *            the new entity
	 */
	public void setEntity(IEntity<?> entity) {
		this.entity = entity;
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
	 * Gets the part URI.
	 *
	 * @return the part URI
	 */
	public String getPartURI() {
		return partURI;
	}

	/**
	 * Gets the part core.
	 *
	 * @return the part core
	 */
	public AContainerCore getPartCore() {
		return partCore;
	}

	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	public Window getWindow() {
		return window;
	}

	/**
	 * Sets the window.
	 *
	 * @param window
	 *            the new window
	 */
	public void setWindow(Window window) {
		this.window = window;
	}

	/**
	 * Creates the contents.
	 *
	 * @param container
	 *            the container
	 */
	public void createContents(ContainerController container) {
		partCore = (AContainerCore) AdichatzApplication.prepareAndInstantiateClass(partURI,
				new Class[] { AdiPluginResources.class, IContainerController.class, ParamMap.class },
				new Object[] { pluginResources, container, paramMap });
		beforeExecLifeCycle();
		Object customizations = paramMap.get(ParamMap.CUSTOMIZATION);
		if (null != customizations) {
			partCore.getContext().getParamMap().put(ParamMap.CUSTOMIZATION, customizations);
			partCore.getContext().getRootCore().injectCustomizations();
		}
		// AListener.fireListener(partCore.getController().getL, IEventType.AFTER_INITIALIZE);
		partCore.execLifeCycle(getEntity());
	}

	/**
	 * Before exec life cycle.
	 */
	protected void beforeExecLifeCycle() {
	}

	public ParamMap getParamMap() {
		return paramMap;
	}

	public Object getPart() {
		return part;
	}

	public void setPart(Object part) {
		this.part = part;
	}
}
