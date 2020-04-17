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
package org.adichatz.jpa.recent;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.io.File;
import java.text.DateFormat;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.adichatz.common.ejb.Session;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.xjc.ParamType;
import org.adichatz.jpa.xjc.QueryContentProviderType;
import org.adichatz.jpa.xjc.RecentPreferenceType;
import org.eclipse.swt.graphics.Image;

/**
 * The Class RecentUtil.
 *
 * @author Yves Drumbonnet
 */
public class RecentUtil {

	/** The recent open editor. */
	public static String RECENT_OPEN_EDITOR = "ADICHATZ_RECENT_OPEN_EDITOR";

	/**
	 * Gets the image.
	 *
	 * @param recentOpenEditor the recent open editor
	 * @return the image
	 */
	public static Image getImage(IRecentOpenEditor recentOpenEditor) {
		String iconURI = recentOpenEditor.getParamMap().getString(ParamMap.ICON_URI);
		if (null == iconURI)
			return null;
		return EngineTools.getImageDescriptor(iconURI).createImage();
	}

	/** The SAVE_RECENTLY_OPEN_ON_COMPUTER. */
	public static String SAVE_RECENTLY_OPEN_ON_COMPUTER = "SAVE_RECENTLY_OPEN_ON_COMPUTER";

	/**
	 * Gets the recent tool tip text.
	 *
	 * @param recentOpenEditor the recent open editor
	 * @return the recent tool tip text
	 */
	public static String getRecentToolTipText(IRecentOpenEditor recentOpenEditor) {
		StringBuffer toolTipTextSB = new StringBuffer();
		String label;
		XMLGregorianCalendar date;
		if (null != recentOpenEditor.getLastClose()) {
			label = "recent.last.close";
			date = recentOpenEditor.getLastClose();
		} else {
			label = "recent.last.open";
			date = recentOpenEditor.getLastOpen();
		}
		toolTipTextSB.append(getFromJpaBundle(label, recentOpenEditor.getLabel(),
				DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(date.toGregorianCalendar().getTime())));
		Object value = recentOpenEditor.getParamMap().get(ParamMap.CONTENT_PROVIDER);
		if (null != value) {
			QueryContentProviderType contentProvider = (QueryContentProviderType) (value instanceof IMarshalledElement
					? ((IMarshalledElement) value).preMarshal(false)
					: value);
			toolTipTextSB.append("\n\t- ").append(getFromJpaBundle("recent.content.provider.uri"))
					.append(contentProvider.getAdiResourceURI());
			if (null != contentProvider.getPreferenceURI()) {
				String[] instanceKey = EngineTools.getInstanceKeys(contentProvider.getPreferenceURI());
				toolTipTextSB.append("\n\t- ").append(getFromJpaBundle("recent.preference.query")).append(instanceKey[2]);
			}
		}
		return toolTipTextSB.toString();
	}

	/**
	 * Gets the recent opened editor file.
	 * 
	 * @return the recent opened editor file
	 */
	public static File getRecentOpenEditorFile() {
		String fileName = "recentOpenEditor_".concat(getUserName()).concat(".xml");
		return new File(EngineConstants.getAdiPermanentDirName().concat("/").concat(fileName));
	}

	/**
	 * New recent query preference URI.
	 *
	 * @return the string
	 */
	public static String newRecentQueryPreferenceURI() {
		return EngineConstants.PREF_FILE_URI_PREFIX.concat(getRecentQueryPreferencePrefix())
				.concat(String.valueOf(System.currentTimeMillis())).concat(".xml");
	}

	/**
	 * Gets the recent query preference prefix.
	 *
	 * @return the recent query preference prefix
	 */
	public static String getRecentQueryPreferencePrefix() {
		return "querypref_".concat(getUserName()).concat("_");
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	private static String getUserName() {
		Session session = AdichatzApplication.getInstance().getSession();
		String username;
		if (null == session)
			username = "";
		else {
			username = session.getUsername();
			if (null == username)
				username = null;
		}
		return username;
	}

	/**
	 * Gets the param map.
	 * 
	 * @param params
	 *            the params
	 * @return the param map
	 */
	public static ParamMap getParamMap(List<ParamType> params) {
		ParamMap paramMap = new ParamMap();
		for (ParamType param : params) {
			Object value = param instanceof IMarshalledWrapper ? param : param.getValue();
			paramMap.put(param.getId(), value);
		}
		return paramMap;
	}

	/**
	 * Equal recent preference.
	 *
	 * @param recentPreference1 the recent preference 1
	 * @param recentPreference2 the recent preference 2
	 * @return true, if successful
	 */
	public static boolean equalRecentPreference(RecentPreferenceType recentPreference1, RecentPreferenceType recentPreference2) {
		if (null == recentPreference2 || null == recentPreference1
				|| !recentPreference1.getPreferenceURI().startsWith(EngineConstants.PREF_FILE_URI_PREFIX))
			return false;
		return recentPreference2.getPreferenceURI().equals(recentPreference1.getPreferenceURI());

	}

	/**
	 * Rewrite preference URI.
	 *
	 * @param contentProvider the content provider
	 */
	public static void rewritePreferenceURI(QueryContentProviderType contentProvider) {
		if (null != contentProvider && null != contentProvider.getPreferenceURI()
				&& contentProvider.getPreferenceURI().startsWith(EngineConstants.PREF_NAME_URI_PREFIX))
			contentProvider.setPreferenceURI(contentProvider.getPreferenceURI());
		else
			contentProvider.setPreferenceURI(null);
	}

}
