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
package org.adichatz.jpa.tabular;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.contentProvider.QueryContentProvider;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.tabular.ControllerPreferenceManager;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.wrapper.QueryContentProviderWrapper;
import org.adichatz.jpa.xjc.ControllerPreferenceType;
import org.adichatz.jpa.xjc.PreferenceTree;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.AbstractTableViewer;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryContentProvider.
 *
 * @author Yves Drumbonnet
 * @param <T>
 *            the
 */
public class JPAQueryContentProvider<T> extends QueryContentProvider<T> implements IMarshalledElement {

	/** The preference URI. */
	protected String preferenceURI;

	private ControllerPreferenceType injectedControllerPreference;

	private ControllerPreferenceType injectedUSerControllerPreference;

	/**
	 * Instantiates a new query content provider.
	 *
	 * @param uiPluginResources
	 *            the ui plugin resources
	 * @param adiQueryURI
	 *            the adi query uri
	 */
	@SuppressWarnings("unchecked")
	public JPAQueryContentProvider(AdiPluginResources uiPluginResources, String adiQueryURI, String preferenceURI) {
		this((AQueryManager<T>) AdichatzApplication.prepareAndInstantiateClass(adiQueryURI,
				new Class[] { AdiPluginResources.class }, new Object[] { uiPluginResources }), adiQueryURI, preferenceURI);
	}

	/**
	 * Instantiates a new query content provider.
	 *
	 * @param queryManager
	 *            the query manager
	 * @param adiQueryURI
	 *            the adi query uri
	 * @param preferenceURI
	 *            the preference uri
	 */
	public JPAQueryContentProvider(AQueryManager<T> queryManager, String adiQueryURI, String preferenceURI) {
		super(queryManager, adiQueryURI);
		this.preferenceURI = preferenceURI;
		if (null != preferenceURI)
			setPreferenceURI(preferenceURI);
	}

	/**
	 * Instantiates a new query content provider.
	 *
	 * @param queryManager
	 *            the query manager
	 */
	public JPAQueryContentProvider(AQueryManager<T> queryManager) {
		this(queryManager, null, null);
	}

	/**
	 * Gets the preference uri.
	 *
	 * @return the preference uri
	 */
	public String getPreferenceURI() {
		return preferenceURI;
	}

	/**
	 * Sets the preference uri.
	 *
	 * @param preferenceURI
	 *            the new preference uri
	 */
	public void setPreferenceURI(String preferenceURI) {
		this.preferenceURI = preferenceURI;
		String preferenceKey = JPAUtil.getPreferenceKey(preferenceURI);
		String preferenceFileKey = null;
		PreferenceTree preferenceTree = null;
		if (preferenceURI.startsWith(EngineConstants.PREF_NAME_URI_PREFIX)) {
			// Case: preferenceURI = preferenceName://preferenceTitle...
			preferenceTree = ((JPAQueryManager<T>) queryManager).getCustomizedPreferenceTreeMap().get(preferenceKey);
			if (null == preferenceTree) {
				EngineTools.openDialog(MessageDialog.ERROR, EngineTools.getFromEngineBundle("error.encounteredError"),
						JPAUtil.getFromJpaBundle("preference.invalid.uri", preferenceURI));
				return;
			}
			if (null != preferenceTree.getControllerPreference())
				if (null == tabularController)
					injectedControllerPreference = preferenceTree.getControllerPreference();
				else {
					ControllerPreferenceManager<T> controllerPreferenceManager = tabularController.getControllerPreferenceManager();
					controllerPreferenceManager.injectControllerPreference(preferenceTree.getControllerPreference());
					controllerPreferenceManager.setInitialControllerPreference();
				}
			if (null != preferenceTree.getQueryPreference()) {
				queryManager.getQueryPreferenceManager().injectQueryPreference(preferenceTree.getQueryPreference());
				queryManager.getQueryPreferenceManager().setInitialQueryPreference();
			}

			int suffixIndex = EngineConstants.PREF_NAME_URI_PREFIX.length() + preferenceKey.length() + 1;
			// Case: preferenceURI = preferenceName://preferenceTitle/JPAQuery_{timestamp}.xml
			if (preferenceURI.length() > suffixIndex)
				preferenceFileKey = preferenceURI.substring(suffixIndex);
		} else if (preferenceURI.startsWith(EngineConstants.PREF_FILE_URI_PREFIX)) {
			preferenceFileKey = preferenceKey;
			// Case: preferenceURI = preferenceFile://JPAQuery_{timestamp}.xml
			preferenceTree = JPAUtil.getPreferenceTree(preferenceKey, null);
		}
		if (null != preferenceFileKey) {
			PreferenceTree preferenceFileTree = JPAUtil.getPreferenceTree(preferenceFileKey, null);
			if (null != preferenceFileTree.getControllerPreference())
				if (null == tabularController)
					injectedControllerPreference = preferenceFileTree.getControllerPreference();
				else
					tabularController.getControllerPreferenceManager()
							.injectControllerPreference(preferenceFileTree.getControllerPreference());
			if (null != preferenceFileTree.getQueryPreference())
				queryManager.getQueryPreferenceManager().injectQueryPreference(preferenceFileTree.getQueryPreference());
		}
	}

	/**
	 * Gets the query result.
	 *
	 * @return the query result
	 */
	public QueryResult getQueryResult() {
		return queryResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.tabular.IMarshalledElement#preMarshal()
	 */
	@Override
	public IMarshalledWrapper preMarshal(boolean closingPart) {
		QueryContentProviderWrapper qcpWrapper = new QueryContentProviderWrapper();
		qcpWrapper.setId(ParamMap.CONTENT_PROVIDER);
		qcpWrapper.setAdiResourceURI(adiQueryURI);
		qcpWrapper.setPluginKey(
				((QueryPreferenceManager<T>) queryManager.getQueryPreferenceManager()).getUiPluginResources().getPluginName());
		qcpWrapper.setPreferenceURI(preferenceURI);
		return qcpWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.tabular.ATabularContentProvider#setTabularController(org.adichatz.engine.controller.collection.
	 * TabularController)
	 */
	@Override
	public void setTabularController(ATabularController<T> tabularController) {
		super.setTabularController(tabularController);
		ControllerPreferenceManager<T> controllerPreferenceManager = tabularController.forceControllerPreferenceManager();

		if (null != injectedControllerPreference) {
			controllerPreferenceManager.injectControllerPreference(injectedControllerPreference);
			injectedControllerPreference = null;
		}
		controllerPreferenceManager.setInitialControllerPreference();
		if (null != injectedUSerControllerPreference) {
			controllerPreferenceManager.injectControllerPreference(injectedUSerControllerPreference);
			injectedUSerControllerPreference = null;
		}
	}

	public void resetPreference(ATabularController<T> tabularController) {
		if (null != preferenceURI) {
			this.tabularController = null; //reinit
			setPreferenceURI(preferenceURI);
			this.tabularController = tabularController;
		} else {
			tabularController.setControllerPreference(new ControllerPreferenceManager<>(tabularController));
		}

	}

	@Override
	public JPAControllerPreferenceManager<T> getControllerPreferenceManager() {
		JPAControllerPreferenceManager<T> controllerPreferenceManager = (JPAControllerPreferenceManager<T>) tabularController
				.getControllerPreferenceManager(false);
		if (null == controllerPreferenceManager)
			controllerPreferenceManager = new JPAControllerPreferenceManager<T>(tabularController);
		return controllerPreferenceManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATabularContentProvider#removeEntity(org.adichatz.engine.cache.IEntity, boolean)
	 */
	@Override
	public void removeEntity(IEntity<T> entity, boolean refresh) {
		Object bean = entity.getBean();
		if (null != bean) {
			// queryManager.getQueryResult() is different from tabularController.getViewer()).getInput()
			queryResult.getQueryResultList().remove(bean);
			((AbstractTableViewer) tabularController.getViewer()).remove(bean);
			// if (refresh)
			tabularController.getViewer().refresh();
		}
		entity.getEntityMM().getDataAccess().getDataCache().removeTabularCtlerForCKey(entity.getCacheKey(), tabularController);
	}

}
