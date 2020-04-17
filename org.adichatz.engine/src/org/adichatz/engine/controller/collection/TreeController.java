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
package org.adichatz.engine.controller.collection;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.viewer.ATreeManager;
import org.adichatz.engine.viewer.RootElement;
import org.adichatz.engine.viewer.TreeContentProvider;
import org.adichatz.engine.viewer.TreeLabelProvider;
import org.adichatz.engine.widgets.LimitedComposite;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeController.
 */
public class TreeController extends ASetController {

	/** The tree. */
	protected Tree tree;

	/** The tree manager. */
	protected ATreeManager treeManager;

	/** The root element. */
	protected Object rootElement;

	/** The show root. */
	private boolean showRoot;

	/** The level to expand: -1 means expand all */
	protected Integer levelToExpand;

	/**
	 * Instantiates a new tree controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public TreeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		if (null != parentController)
			this.pluginResources = parentController.getPluginResources();
	}

	/**
	 * Creates the control.
	 */
	public void createControl() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		composite = new LimitedComposite(parentController.getComposite(), containerStyle);
		composite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));

		tree = toolkit.createTree(composite, style);
		tree.setHeaderVisible(true);
		tree.setData(SET_CONTROLLER, this);
		if (null != AReskinManager.getInstance())
			AReskinManager.getInstance().addSkinnedWidget(tree);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ASetController#getControl()
	 */
	@Override
	public Tree getControl() {
		return tree;
	}

	/**
	 * Checks if is show root.
	 * 
	 * @return true, if is show root
	 */
	public boolean isShowRoot() {
		return showRoot;
	}

	/**
	 * Sets the show root.
	 * 
	 * @param showRoot
	 *            the new show root
	 */
	public void setShowRoot(boolean showRoot) {
		this.showRoot = showRoot;
	}

	/**
	 * Gets the root element.
	 * 
	 * @return the root element
	 */
	public Object getRootElement() {
		return rootElement;
	}

	/**
	 * Sets the root element.
	 * 
	 * @param rootElement
	 *            the new root element
	 */
	public void setRootElement(Object rootElement) {
		if (showRoot)
			this.rootElement = rootElement instanceof RootElement ? rootElement : new RootElement(rootElement);
		else
			this.rootElement = rootElement instanceof RootElement ? ((RootElement) rootElement).getRoot() : rootElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.ASetController#refresh()
	 */
	@Override
	public void refresh() {
		tree.setRedraw(false);
		viewer.setInput(rootElement);
		AListener.fireListener(listenerMap, IEventType.REFRESH);

		if (null != levelToExpand)
			if (-1 == levelToExpand)
				((TreeViewer) viewer).expandAll();
			else
				((TreeViewer) viewer).expandToLevel(levelToExpand);
		tree.setRedraw(true);
		viewer.refresh();
	}

	/**
	 * Gets the tree manager.
	 * 
	 * @return the tree manager
	 */
	public ATreeManager getTreeManager() {
		return treeManager;
	}

	/**
	 * Sets the tree manager.
	 * 
	 * @param treeManager
	 *            the new tree manager
	 */
	public void setTreeManager(ATreeManager treeManager) {
		this.treeManager = treeManager;
		viewer.setLabelProvider(new TreeLabelProvider(treeManager));
		viewer.setContentProvider(new TreeContentProvider(treeManager));
	}

	@Override
	public Item getSelectedItem() {
		if (null == tree.getSelection())
			return null;
		return tree.getSelection()[0];
	}

	public void setManagedEntity(IEntity<?> managedEntity) {
	}

	public void update(IEntity<?> entity, Object bean) {
		viewer.update(bean, null);
	}

}
