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
package org.adichatz.engine.e4.part;

import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.ParamMap;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.impl.PartImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiInputPart.
 * 
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class AdiInputPart extends PartImpl implements MPart {
	/*
	 * S T A T I C
	 */
	public static String ELEMENT_ID = "org.adichatz.ui.part";

	private static int EDITOR_PART = 1;

	public static String getNewPartId() {
		return ELEMENT_ID.concat(String.valueOf(EDITOR_PART++));
	}
	/*
	 * end S T A T I C
	 */

	/** The param map. */
	private ParamMap paramMap;

	/** The stack. */
	private MPartStack stack;

	/** The saveable. */
	private boolean saveInRecentList = true;

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	private String initialLabel;

	private boolean duplicateEditor;

	/**
	 * Instantiates a new adi input part.
	 * 
	 * @param paramMap
	 *            the param map
	 * @param stack
	 *            the stack
	 */
	public AdiInputPart(ParamMap paramMap, MPartStack stack) {
		super();
		this.paramMap = paramMap;
		this.stack = stack;
		Object pluginResourcesParam = paramMap.get(ParamMap.PLUGIN_RESOURCES);
		if (null == pluginResourcesParam) {
			String adiResoureUri = paramMap.getString(ParamMap.ADI_RESOURCE_URI);
			if (null != adiResoureUri) {
				String pluginKey = EngineTools.getInstanceKeys(adiResoureUri)[0];
				if (null != pluginKey)
					pluginResources = AdichatzApplication.getPluginResources(pluginKey);
			}
		} else {
			if (pluginResourcesParam instanceof String)
				pluginResources = AdichatzApplication.getPluginResources((String) pluginResourcesParam);
			else
				pluginResources = (AdiPluginResources) pluginResourcesParam;

		}
		if (!paramMap.isSaveInRecentList())
			logWarning(getFromEngineE4Bundle("customisation.cannot.store.recent", label));
	}

	/**
	 * Initialize label.
	 * 
	 * @param label
	 *            the label
	 */
	public void initializeLabel(String label) {
		initialLabel = label;
		int max = 2;
		boolean found = false;
		for (MStackElement element : stack.getChildren()) {
			if (element instanceof AdiInputPart) {
				String usedPartName = ((AdiInputPart) element).getLabel();
				if (null != usedPartName && usedPartName.startsWith(label)) {
					String radix = usedPartName.substring(label.length());
					if (0 == radix.length())
						found = true;
					else if (radix.startsWith(" (") && radix.endsWith(")")) {
						try {
							Integer number = Integer.valueOf(radix.substring(2, radix.length() - 1));
							max = number >= max ? number + 1 : max;
							found = true;
						} catch (NumberFormatException e) {
						}
					}
				}
			}
		}
		if (found)
			label = label + " (" + max + ")";
		setLabel(label);
	}

	/**
	 * Gets the param map.
	 * 
	 * @return the param map
	 */
	public ParamMap getParamMap() {
		return paramMap;
	}

	/**
	 * Gets the plugin resources.
	 * 
	 * @return the plugin resources
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AdiInputPart))
			return false;
		AdiInputPart inputPart = (AdiInputPart) obj;
		boolean exactLabel = duplicateEditor ? label.equals(inputPart.label) : initialLabel.equals(inputPart.initialLabel);
		return exactLabel && paramMap.equals(inputPart.getParamMap());
	}

	public boolean isSaveInRecentList() {
		// If entity status is PERSIST or paramMap use non marshallable customization return false
		return saveInRecentList && paramMap.isSaveInRecentList();
	}

	public void setSaveInRecentList(boolean saveInRecentList) {
		this.saveInRecentList = saveInRecentList;
	}

	public String getInitialLabel() {
		return initialLabel;
	}

	public boolean isDuplicateEditor() {
		return duplicateEditor;
	}

	public void setDuplicateEditor(boolean duplicateEditor) {
		this.duplicateEditor = duplicateEditor;
	}

	@Override
	public BoundedPart getObject() {
		return (BoundedPart) super.getObject();
	}
}
