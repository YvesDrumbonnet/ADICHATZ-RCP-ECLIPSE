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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.tabular.TabularUtil;
import org.adichatz.engine.xjc.TableComponentType;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.xjc.ColumnPreferenceType;
import org.adichatz.jpa.xjc.ControllerPreferenceType;
import org.adichatz.jpa.xjc.FilterType;
import org.adichatz.jpa.xjc.FiltersType;
import org.adichatz.jpa.xjc.PreferenceTree;
import org.adichatz.jpa.xjc.QueryPaginationType;
import org.adichatz.jpa.xjc.QueryPreferenceType;
import org.adichatz.jpa.xjc.RecentPreferenceType;
import org.eclipse.jface.dialogs.MessageDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentOpenEditorWrapper.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            the generic type
 */
@SuppressWarnings("serial")
public class RecentPreferenceWrapper<T> extends RecentPreferenceType {
	/** The preference tree. */
	private PreferenceTree preferenceTree;

	/** The recent open query editor. */
	private RecentOpenQueryEditorWrapper<T> recentOpenQueryEditor;

	/** The preference file. */
	private File preferenceFile;

	public void reinitPreferenceTree() {
		preferenceTree = null;
	}

	/**
	 * Gets the preference tree.
	 *
	 * @return the preference tree
	 */
	public PreferenceTree getPreferenceTree() {
		if (null == preferenceTree) {
			if (null == preferenceURI) // occurs when problem is encountered when saving preference
				return null;
			String preferenceKey = JPAUtil.getPreferenceKey(preferenceURI);
			if (null != preferenceKey) {
				preferenceTree = JPAUtil.getPreferenceTree(preferenceKey, preferenceFile);
				if (!getPreferenceFile().exists()) {
					logError(getFromEngineBundle("error.file.does.not.exist", preferenceFile));
					return null;
				}
			} else
				return null;
			try {
				preferenceTree = (PreferenceTree) FileUtility.getTreeFromXmlFile(JPAUtil.getUnmarshaller(), preferenceFile);
			} catch (JAXBException e) {
				logError(getFromEngineBundle("preference.cannot.unmarshal", preferenceFile), e);
			}
		}
		return preferenceTree;
	}

	public void setPreferenceTree(PreferenceTree preferenceTree) {
		this.preferenceTree = preferenceTree;
	}

	/**
	 * Gets the preference file.
	 *
	 * @return the preference file
	 */
	public File getPreferenceFile() {
		if (null == preferenceFile)
			preferenceFile = new File(
					EngineConstants.getAdiPermanentDirName().concat("/").concat(JPAUtil.getPreferenceKey(preferenceURI)));
		return preferenceFile;
	}

	/**
	 * Sets the preference file.
	 *
	 * @param preferenceFile
	 *            the new preference file
	 */
	public void setPreferenceFile(File preferenceFile) {
		this.preferenceFile = preferenceFile;
	}

	public String getTitle() {
		PreferenceTree preferenceTree = getPreferenceTree();
		if (null != preferenceTree)
			return preferenceTree.getTitle();
		else {
			String preferenceTile = preferenceURI.substring(EngineConstants.PREF_FILE_URI_PREFIX.length());
			return getFromJpaBundle("recent.missing.file", preferenceTile);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecentPreferenceWrapper) {
			File paramPreferenceFile = ((RecentPreferenceWrapper<?>) obj).getPreferenceFile();
			return Utilities.equals(paramPreferenceFile, preferenceFile);
		}
		return false;
	}

	/**
	 * Gets the recent tool tip text.
	 *
	 * @return the recent tool tip text
	 */
	public String getRecentToolTipText() {
		StringBuffer toolTipTextSB = new StringBuffer();
		toolTipTextSB.append(getFromJpaBundle("preference.title")).append(' ').append(getTitle()).append("   -   ");
		toolTipTextSB.append(getFromJpaBundle("recent.preference.file.name")).append(' ').append(getPreferenceFile().getName());
		return toolTipTextSB.toString();
	}

	/**
	 * Gets the filters.
	 * 
	 * This method is used by reflection (see NativeQueryManager and QueryOutlinePage#refresh)
	 * 
	 * @return the filters
	 */
	public List<FilterType> getViewerFilters() {
		ControllerPreferenceType controllerPreference = getPreferenceTree().getControllerPreference();
		if (null == controllerPreference)
			return null;
		FiltersType filters = controllerPreference.getFilters();
		if (null == filters)
			return null;
		return filters.getFilter();
	}

	/**
	 * Gets the column prefs.
	 *
	 * @return the column prefs
	 */
	public List<ColumnPreferenceType> getColumnPrefs() {
		ControllerPreferenceType controllerPreference = getPreferenceTree().getControllerPreference();
		if (null == controllerPreference || null == controllerPreference.getColumnPreferences())
			return new ArrayList<ColumnPreferenceType>(0);
		return controllerPreference.getColumnPreferences().getColumnPreference();
	}

	/**
	 * Gets the recent open query editor.
	 *
	 * @return the recent open query editor
	 */
	public RecentOpenQueryEditorWrapper<T> getRecentOpenQueryEditor() {
		return recentOpenQueryEditor;
	}

	/**
	 * Sets the recent open query editor.
	 *
	 * @param recentOpenQueryEditor
	 *            the new recent open query editor
	 */
	public void setRecentOpenQueryEditor(RecentOpenQueryEditorWrapper<T> recentOpenQueryEditor) {
		this.recentOpenQueryEditor = recentOpenQueryEditor;
	}

	/**
	 * Check.
	 *
	 * @param queryURI
	 *            the query uri
	 * @return true, if successful
	 */
	public boolean check(String queryURI) {
		if (null == getPreferenceTree()) {
			if (EngineTools.openDialog(MessageDialog.CONFIRM, getFromJpaBundle("preference.file.problem"),
					getFromJpaBundle("preference.recent.problem", preferenceURI))) {
				RecentOpenEditorTreeWrapper.getInstance().removeRecentPreference(queryURI, this);
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the pagination string.
	 *
	 * @return the pagination string
	 */
	public String getPaginationString(ATabularController<?> tabularController) {
		PreferenceTree preferenceTree = getPreferenceTree();
		ControllerPreferenceType controllerPreference = preferenceTree.getControllerPreference();
		StringBuffer paginationSB = new StringBuffer();
		QueryPaginationType pagination = null == preferenceTree ? null : preferenceTree.getQueryPreference().getPagination();
		if (null != pagination && null != pagination.getMaxResults()) {
			paginationSB.append(getFromJpaBundle("queryPreference.maxResults"));
			paginationSB.append(": ").append(pagination.getMaxResults());
		}
		String statusBarKey = null == controllerPreference ? null : controllerPreference.getStatusBarKey();
		if (null != statusBarKey) {
			if (0 < paginationSB.length())
				paginationSB.append("   -   ");
			paginationSB.append(getFromJpaBundle("table.statusBarKey"));
			paginationSB.append(": ").append(TabularUtil.getInstance().getTableComponentMap().get(statusBarKey).getText());
		}
		String tableRendererKey = controllerPreference.getTableRendererKey();
		if (null != tableRendererKey) {
			if (0 < paginationSB.length())
				paginationSB.append("   -   ");
			paginationSB.append(getFromJpaBundle("table.tableRendererKey"));
			TableComponentType tableComponent = TabularUtil.getInstance().getTableRenderer(tabularController, tableRendererKey);
			if (null != tableComponent)
				paginationSB.append(": ").append(tableComponent.getText());
		}
		return paginationSB.toString();
	}

	/**
	 * Gets the column order string.
	 *
	 * @return the column order string
	 */
	@SuppressWarnings("unchecked")
	public String getColumnOrderString(ATabularController<T> tabularController) {
		ControllerPreferenceWrapper<T> controllerPreference = (ControllerPreferenceWrapper<T>) getPreferenceTree()
				.getControllerPreference();
		int[] columnOrders = controllerPreference.getTabularColumnOrder(tabularController);
		if (null == columnOrders)
			return "";
		boolean addColumnOrder = false;
		StringBuffer columnOrderSB = new StringBuffer();
		for (int i = 0; i < columnOrders.length; i++) {
			AColumnController<?> columnController = tabularController.getColumnControllers(false)[columnOrders[i]];
			if (columnController.isValid() && 0 < columnController.getColumnWidth()) {
				if (i != columnOrders[i])
					addColumnOrder = true;
				columnOrderSB.append(", ").append(columnController.getText());
			}
		}
		if (addColumnOrder)
			return columnOrderSB.substring(2);
		else
			return "";
	}

	public String getOrderByClause() {
		QueryPreferenceType queryPreference = preferenceTree.getQueryPreference();
		return null == queryPreference ? "" : queryPreference.getOrderByClause();
	}

}
