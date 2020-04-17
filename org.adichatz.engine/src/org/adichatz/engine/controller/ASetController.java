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
package org.adichatz.engine.controller;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.action.AControlCollectionController;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;

// TODO: Auto-generated Javadoc
/**
 * The Class ASetController.
 *
 * @author Yves Drumbonnet
 */
public abstract class ASetController extends AControlCollectionController implements IContainerController {

	/** The set controller. */
	public static String SET_CONTROLLER = "#ASETCONTROLLER#";

	/** The viewer. */
	protected ColumnViewer viewer;

	/** The plugin resources . */
	protected AdiPluginResources pluginResources;

	/** The special composite containing the set. */
	protected Composite composite;

	/** The bold font. */
	protected Font boldFont;

	/** The container style. */
	protected int containerStyle = SWT.NONE;

	/** The select in new thread (a mark that can be uqed for pointing out that select process is a new thread or not ). */
	private boolean selectInNewThread = true;

	protected Set<String> lazyFetchMembers;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#getControl()
	 */
	@Override
	public abstract Composite getControl();

	/**
	 * Instantiates a new set controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public ASetController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.FULL_SELECTION;
	}

	/**
	 * Gets the bold font.
	 *
	 * @return the bold font
	 */
	public Font getBoldFont() {
		return boldFont;
	}

	/**
	 * Sets the bold font.
	 */
	public void setBoldFont() {
		boldFont = EngineTools.getModifiedFont(getControl().getFont(), SWT.BOLD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getPluginResources()
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	@Override
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	/**
	 * Refresh.
	 */
	public abstract void refresh();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IControlController#pack()
	 */
	/**
	 * Pack.
	 */
	public void pack() {
		getControl().pack();
	}

	/**
	 * Gets the viewer.
	 * 
	 * @return the viewer
	 */
	public ColumnViewer getViewer() {
		return viewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#getComposite()
	 */
	@Override
	public Composite getComposite() {
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getEntity()
	 */
	public IEntity<?> getEntity() {
		return parentController.getEntity();
	}

	/**
	 * Gets the selected object.
	 * 
	 * @return the selected object
	 */
	public Object getSelectedObject() {
		if (null == viewer)
			return null;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.isEmpty())
			return null;
		return selection.getFirstElement();
	}

	/**
	 * Gets the selected item.
	 *
	 * @return the selected item
	 */
	public abstract Item getSelectedItem();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#lockFieldControllers(boolean)
	 */
	@Override
	public void lockFieldControllers(boolean locked) {
	}

	/**
	 * Update.
	 * 
	 * @param entity
	 *            the entity
	 * @param bean
	 *            the bean
	 */
	public abstract void update(IEntity<?> entity, Object bean);

	/**
	 * Sets the select in new thread mark.
	 *
	 * @param selectInNewThread
	 *            the new select in new thread
	 * @See (org.adichatz.jpa.action.AddEntityAction) for example
	 */
	public void setSelectInNewThread(boolean selectInNewThread) {
		this.selectInNewThread = selectInNewThread;
	}

	/**
	 * Does select in new thread.
	 *
	 * @return true, if successful
	 */
	public boolean doesSelectInNewThread() {
		return selectInNewThread;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getContext()
	 */
	@Override
	public Object getContext() {
		return ((IContainerController) parentController).getContext();
	}

	/**
	 * Gets the container style.
	 *
	 * @return the container style
	 */
	public int getContainerStyle() {
		return containerStyle;
	}

	/**
	 * Sets the container style.
	 *
	 * @param containerStyle
	 *            the new container style
	 */
	public void setContainerStyle(int containerStyle) {
		this.containerStyle = containerStyle;
	}

	/**
	 * Gets the lazy fetch members.
	 * 
	 * @return the lazy fetch members
	 */
	public Set<String> getLazyFetchMembers() {
		return lazyFetchMembers;
	}

	public void addLazyFetchMembers(String... lazyFetchMembers) {
		if (null != lazyFetchMembers) {
			if (null == this.lazyFetchMembers)
				this.lazyFetchMembers = new HashSet<>(lazyFetchMembers.length);
			for (String lazyFetchMember : lazyFetchMembers)
				this.lazyFetchMembers.add(lazyFetchMember);
		}
	}

}
