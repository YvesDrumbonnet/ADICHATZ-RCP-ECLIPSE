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
package org.adichatz.engine.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.utils.DataReferenceManager;
import org.adichatz.engine.controller.utils.IPredicate;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.PooledQueryResult;
import org.adichatz.engine.query.AQueryManager;
import org.eclipse.jface.viewers.LabelProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ARefController.
 */
public abstract class ARefController extends AFieldController {

	/** The plugin key. */
	private String pluginKey;

	@SuppressWarnings("rawtypes")
	protected Collection input;

	/** The data reference manager to which the controller refers (could be null). */
	protected DataReferenceManager dataReferenceManager;

	/** The label provider. */
	protected LabelProvider labelProvider;

	protected String adiQueryURI;

	protected String preferenceURI;

	/** The pool query result. */
	protected boolean poolQueryResult = true;

	protected IPredicate predicate;

	protected AQueryManager<?> queryManager;

	/**
	 * Instantiates a new referenced field controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public ARefController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#toString(java.lang.Object)
	 */
	@Override
	public String toString(Object bean) {
		return String.valueOf(convertModelToTarget(bean));
	}

	/**
	 * Gets the values.
	 * 
	 * @return the values
	 */
	@SuppressWarnings("rawtypes")
	public List getValues() {
		return null;
	}

	/**
	 * Gets the values.
	 * 
	 * @return the values
	 */
	@SuppressWarnings("rawtypes")
	public List getDisplayedValues() {
		return getValues();
	}

	/**
	 * Sets the pool query result.
	 * 
	 * @param poolQueryResult
	 *            the new pool query result
	 */
	public void setPoolQueryResult(boolean poolQueryResult) {
		this.poolQueryResult = poolQueryResult;
	}

	/**
	 * Compare.
	 * 
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * 
	 * @return true, if successful
	 */
	public boolean compare(Object a, Object b) {
		return a.equals(b);
	}

	/**
	 * Gets the input.
	 * 
	 * @return the input
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Collection getInput() {
		if (null == adiQueryURI)
			input = getValues();
		else
			input = getQueryManager().launchQueryResult().getQueryResultList();
		if (null == predicate)
			return input;
		List filteredInput = new ArrayList();
		for (Object element : input)
			if (predicate.apply(element))
				filteredInput.add(element);
		return filteredInput;
	}

	public boolean isPoolQueryResult() {
		return poolQueryResult;
	}

	/**
	 * Gets the plugin key.
	 * 
	 * @return the plugin key
	 */
	public String getPluginKey() {
		return pluginKey;
	}

	/**
	 * Sets the plugin key.
	 * 
	 * @param pluginKey
	 *            the new plugin key
	 */
	public void setPluginKey(String pluginKey) {
		this.pluginKey = pluginKey;
	}

	/**
	 * Gets the adi query URI.
	 *
	 * @return the adi query URI
	 */
	public String getAdiQueryURI() {
		return adiQueryURI;
	}

	/**
	 * Gets the preference URI.
	 *
	 * @return the preference URI
	 */
	public String getPreferenceURI() {
		return preferenceURI;
	}

	/**
	 * Sets the preference URI.
	 *
	 * @param preferenceURI
	 *            the new preference URI
	 */
	public void setPreferenceURI(String preferenceURI) {
		this.preferenceURI = preferenceURI;
	}

	public AQueryManager<?> getQueryManager() {
		if (null == queryManager) {
			if (poolQueryResult) {
				MultiKey multiKey = MultiKey.newMultiKey(adiQueryURI, preferenceURI);
				PooledQueryResult pooledQueryResult = ((IContainerController) parentController).getPluginResources()
						.getPluginEntityTree().getPooledQueryResult(multiKey);
				queryManager = pooledQueryResult.getQueryManager();
			} else {
				String[] instanceKeys = EngineTools.getInstanceKeys(adiQueryURI);
				AdiPluginResources pluginResources = null == instanceKeys[0] ? getParentController().getPluginResources()
						: AdichatzApplication.getPluginResources(instanceKeys[0]);
				queryManager = (AQueryManager<?>) pluginResources.getNewInstance(instanceKeys[2], instanceKeys[1]);
			}
		}
		return queryManager;
	}
}
