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
package org.adichatz.engine.tabular;

import java.util.Collection;
import java.util.Comparator;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.query.AQueryManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryContentProvider.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ATabularContentProvider<T> implements IStructuredContentProvider {

	/** The tabular controller. */
	protected ATabularController<T> tabularController;

	/** The query manager. */
	protected AQueryManager<T> queryManager;

	/** The comparator. */
	protected Comparator<T> comparator;

	public ATabularContentProvider() {
	}

	/**
	 * Instantiates a new a tabular content provider.
	 * 
	 * @param queryManager
	 *            the query manager
	 */
	public ATabularContentProvider(AQueryManager<T> queryManager) {
		this.queryManager = queryManager;
		queryManager.setContentProvider(this);
	}

	/**
	 * Gets the tabular controller.
	 *
	 * @return the tabular controller
	 */
	public ATabularController<T> getTabularController() {
		return tabularController;
	}

	@SuppressWarnings("unchecked")
	public void setUntypedTabularController(ATabularController<?> tabularController) {
		setTabularController((ATabularController<T>) tabularController);
	}

	/**
	 * Sets the content provider.
	 * 
	 * @param contentProvider
	 *            the new content provider
	 */
	public void setTabularController(ATabularController<T> tabularController) {
		this.tabularController = tabularController;
		tabularController.getViewer().setContentProvider(this);
	}

	public ControllerPreferenceManager<T> getControllerPreferenceManager() {
		ControllerPreferenceManager<T> controllerPreferenceManager = tabularController.getControllerPreferenceManager(false);
		if (null == controllerPreferenceManager)
			controllerPreferenceManager = new ControllerPreferenceManager<T>(tabularController);
		return controllerPreferenceManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * Launch query.
	 */
	public abstract void launchQuery();

	/**
	 * Post refresh.
	 */
	protected void postRefresh() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof QueryResult)
			return ((QueryResult) inputElement).getQueryResultList().toArray();
		else if (inputElement instanceof Object[])
			return (Object[]) inputElement;
		else if (inputElement instanceof Collection)
			return ((Collection) inputElement).toArray();
		else if (null == queryManager.getQueryResult())
			return null;
		return queryManager.getQueryResult().getQueryResultList().toArray();
	}

	/**
	 * Gets the query manager.
	 * 
	 * @return the query manager
	 */
	public AQueryManager<T> getQueryManager() {
		return queryManager;
	}

	/**
	 * Adds the entity when change on relationship (e.g. ONE_TO_MANY or MANY_TO_MANY).
	 * 
	 * @param entity
	 *            the entity
	 * @param refresh
	 *            the refresh
	 */
	public void addEntity(IEntity<T> entity, boolean refresh) {
	}

	/**
	 * Removes the entity when change on relationship (e.g. ONE_TO_MANY or MANY_TO_MANY).
	 * 
	 * @param entity
	 *            the entity
	 * @param refresh
	 *            the refresh
	 * 
	 * @return the object
	 */
	public void removeEntity(IEntity<T> entity, boolean refresh) {
	}

	public void endLifeCycle() {
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

}
