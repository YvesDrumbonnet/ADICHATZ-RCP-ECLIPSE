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
package org.adichatz.jpa.wrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.ARecentOutlinePage;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.extra.OutlineEvent.EVENT_TYPE;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.recent.IRecentOpenEditor;
import org.adichatz.jpa.recent.RecentPreferenceSet;
import org.adichatz.jpa.recent.RecentUtil;
import org.adichatz.jpa.xjc.ParamType;
import org.adichatz.jpa.xjc.ParamsType;
import org.adichatz.jpa.xjc.QueryContentProviderType;
import org.adichatz.jpa.xjc.RecentOpenEditorTree;
import org.adichatz.jpa.xjc.RecentOpenEditorType;
import org.adichatz.jpa.xjc.RecentOpenQueryEditorType;
import org.adichatz.jpa.xjc.RecentPreferenceType;
import org.adichatz.jpa.xjc.RecentPreferencesType;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentOpenEditorWrapper.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("serial")
public class RecentOpenEditorTreeWrapper extends RecentOpenEditorTree {
	private static RecentOpenEditorTreeWrapper THIS;

	public static RecentOpenEditorTreeWrapper getInstance() {
		if (null == THIS)
			new RecentOpenEditorTreeWrapper();
		return THIS;
	}

	private Map<String, RecentPreferenceSet> queryPreferenceMap;

	public RecentOpenEditorTreeWrapper() {
		THIS = this;
	}

	private File recentOpenEditorFile;

	public Map<String, RecentPreferenceSet> getQueryPreferenceMap() {
		if (null == queryPreferenceMap) {
			queryPreferenceMap = new HashMap<>();
			for (RecentOpenEditorType recentOpenEditor : getRecentOpenEditor()) {
				if (recentOpenEditor instanceof RecentOpenQueryEditorWrapper<?>
						&& null != ((RecentOpenQueryEditorWrapper<?>) recentOpenEditor)
								.getRecentPreferences(null == recentOpenEditorFile)
						&& null != recentOpenEditor.getParams()) {
					for (ParamType param : recentOpenEditor.getParams().getParam()) {
						if (param instanceof QueryContentProviderType) {
							String adiResourceURI = ((QueryContentProviderType) param).getAdiResourceURI();
							if (!EngineTools.isEmpty(adiResourceURI)) {
								RecentPreferenceSet recentPreferenceSet = queryPreferenceMap.get(adiResourceURI);
								if (null == recentPreferenceSet) {
									recentPreferenceSet = new RecentPreferenceSet(adiResourceURI);
									queryPreferenceMap.put(adiResourceURI, recentPreferenceSet);
								}
								for (RecentPreferenceType recentPreference : ((RecentOpenQueryEditorType) recentOpenEditor)
										.getRecentPreferences().getRecentPreference()) {
									RecentPreferenceWrapper<?> currentRP = recentPreferenceSet.getRecentPreferenceMap()
											.get(recentPreference.getPreferenceURI());
									RecentPreferenceWrapper<?> rp = (RecentPreferenceWrapper<?>) recentPreference;
									if (null == currentRP || 0 < rp.getUpdated().compare(currentRP.getUpdated())) {
										recentPreferenceSet.getRecentPreferenceMap().put(rp.getPreferenceURI(), rp);
										if (null != recentOpenEditorFile) {
											rp.setPreferenceFile(new File(recentOpenEditorFile.getParent().concat("/")
													.concat(JPAUtil.getPreferenceKey(rp.getPreferenceURI()))));
										}
									}
								}
								break;
							}
						}
					}
				}
			}
		}
		return queryPreferenceMap;
	}

	public void removeQPrefFromOpenEditor(RecentPreferencesType recentPreferences, RecentPreferenceWrapper<?> recentPreference) {
		for (final Iterator<RecentPreferenceType> iterator = recentPreferences.getRecentPreference().iterator(); iterator
				.hasNext();) {
			RecentPreferenceType nextRecentPreference = iterator.next();
			if (nextRecentPreference.getPreferenceURI().equals(recentPreference.getPreferenceURI()))
				iterator.remove();
		}
	}

	public void removeRecentPreference(String queryURI, RecentPreferenceWrapper<?> recentPreference) {
		for (RecentOpenEditorType recentOpenEditor : getRecentOpenEditor())
			if (recentOpenEditor instanceof RecentOpenQueryEditorType) {
				RecentPreferencesType rp = ((RecentOpenQueryEditorWrapper<?>) recentOpenEditor).getRecentPreferences(false);
				if (null != rp) {
					List<RecentPreferenceType> recentPreferences = rp.getRecentPreference();
					RecentPreferenceType[] recentPreferenceArray = recentPreferences
							.toArray(new RecentPreferenceType[recentPreferences.size()]);
					for (RecentPreferenceType recentPreferenceType : recentPreferenceArray) {
						if (recentPreferenceType.getPreferenceURI().equals(recentPreference.getPreferenceURI()))
							recentPreferences.remove(recentPreferenceType);
					}
					if (recentPreferences.isEmpty())
						((RecentOpenQueryEditorType) recentOpenEditor).setRecentPreferences(null);
				}
			}
		RecentPreferenceSet queryPreferenceSet = getQueryPreferenceMap().get(queryURI);
		if (null != queryPreferenceSet) {
			queryPreferenceSet.getRecentPreferenceMap().remove(recentPreference.getPreferenceURI());
			if (queryPreferenceSet.getRecentPreferenceMap().isEmpty())
				queryPreferenceMap.remove(queryURI);
		}
		recentPreference.getPreferenceFile().delete();

		ARecentOutlinePage.getInstance().fireListener(new OutlineEvent(EVENT_TYPE.REFRESH_OUTLINE));
	}

	/**
	 * Sets the params type.
	 *
	 * @param excludedKeys
	 *            the excluded keys
	 * @return the list or error messages
	 */
	public List<String> setParamTypes(IRecentOpenEditor recentOpenEditor, String... excludedKeys) {
		List<String> errorMessages = new ArrayList<String>();
		ParamsType params = new ParamsType();
		for (Map.Entry<String, Object> entry : recentOpenEditor.getParamMap().entrySet()) {
			if (0 == excludedKeys.length || !Arrays.asList(excludedKeys).contains(entry.getKey())) {
				Object value = entry.getValue() instanceof IMarshalledElement
						? ((IMarshalledElement) entry.getValue()).preMarshal(false)
						: entry.getValue();
				if (value instanceof ParamType) {
					ParamType param = (ParamType) value;
					param.setId(entry.getKey());
					params.getParam().add(param);
					if (value instanceof QueryContentProviderType) {
						RecentUtil.rewritePreferenceURI((QueryContentProviderType) value);
					}
				} else if (value instanceof String) {
					ParamType param = new ParamType();
					param.setId(entry.getKey());
					param.setValue((String) value);
					params.getParam().add(param);
				} else if (!(value instanceof Image) // Image cannot be marshalled, use ICON_URI param
						&& !(value instanceof CustomizationClassParamWrapper)) { // Change on Customization
					errorMessages.add(recentOpenEditor.getLabel().concat(": ").concat(entry.getKey()));
				}
			}
		}
		recentOpenEditor.setParams(params);
		return errorMessages;
	}

	public boolean isQueryPreferencesInitialized() {
		return null != queryPreferenceMap;
	}

	public File getRecentOpenEditorFile() {
		return recentOpenEditorFile;
	}

	public void setRecentOpenEditorFile(File recentOpenEditorFile) {
		this.recentOpenEditorFile = recentOpenEditorFile;
	}

}
