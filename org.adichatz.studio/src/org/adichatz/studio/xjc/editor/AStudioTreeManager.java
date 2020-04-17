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
package org.adichatz.studio.xjc.editor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.collection.TreeController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.generator.tree.ATreeChild;
import org.adichatz.generator.tree.AXjcTreeManager;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class StudioTreeManager.
 */
public abstract class AStudioTreeManager extends AXjcTreeManager {
	public static Font BOLD;

	public static Font ITALIC;

	/** The binding service. */
	private ABindingService bindingService;

	/** The tree controller. */
	private TreeController treeController;

	/** The data access. */
	protected ADataAccess dataAccess;

	/** The children. */
	private Map<String, Set<ElementType>> childrenMap = new HashMap<String, Set<ElementType>>();

	/**
	 * Instantiates a new studio tree manager.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 */
	public AStudioTreeManager(AdiPluginResources pluginResources) {
		super(pluginResources);
	}

	/**
	 * Gets the tree controller.
	 * 
	 * @return the tree controller
	 */
	public TreeController getTreeController() {
		return treeController;
	}

	/**
	 * Sets the tree controller.
	 * 
	 * @param treeController
	 *            the new tree controller
	 */
	public void setTreeController(TreeController treeController) {
		this.treeController = treeController;
		dataAccess = treeController.getPluginResources().getDataAccess();
	}

	/**
	 * Gets the tree child.
	 * 
	 * @param element
	 *            the element
	 * @return the tree child
	 */
	public ATreeChild getTreeChild(Object element) {
		String entityURI;
		if (element instanceof String)
			entityURI = (String) element;
		else {
			Class<?> beanClass;
			if (element instanceof Class)
				beanClass = (Class<?>) element;
			else
				beanClass = element.getClass();
			entityURI = beanClass.getSimpleName();
			if (entityURI.endsWith("Type"))
				entityURI = entityURI.substring(0, entityURI.length() - 4);
			else if (entityURI.endsWith("Wrapper"))
				entityURI = entityURI.substring(0, entityURI.length() - 7);
			entityURI = "adi://org.adichatz.studio/model/".concat(entityURI).concat("MM");
		}
		return treeChildMap.get(entityURI);
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public Map<String, Set<ElementType>> getChildrenMap() {
		return childrenMap;
	}

	/**
	 * Update children map.
	 * 
	 * @param element
	 *            the element
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 */
	public void updateChildrenMap(ElementType element, String oldValue, String newValue) {
		if (!Utilities.equals(oldValue, newValue)) {
			if (null != oldValue) {
				Set<ElementType> elements = childrenMap.get(oldValue);
				if (null != elements) // Can be null, e.g. Accessibility...)
					elements.remove(element);
			}
			if (null != newValue) {
				Set<ElementType> set = childrenMap.get(newValue);
				if (null == set) {
					set = new HashSet<ElementType>();
					childrenMap.put(newValue, set);
				}
				set.add(element);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.StudioTreeManager#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		XjcTreeElement treeElement = (XjcTreeElement) element;
		return treeElement.getTreeChild().getText(treeElement);
	}

	/**
	 * New tree element.
	 * 
	 * @param parentElement
	 *            the parent element
	 * @param getMethod
	 *            the get method
	 * @param element
	 *            the element
	 * @param refField
	 *            the ref field
	 * @return the xjc tree element
	 */
	public XjcTreeElement newTreeElement(XjcTreeElement parentElement, Method getMethod, Object element, boolean refField) {
		return new XjcTreeElement(this, parentElement, element, getMethod, refField);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATreeManager#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATreeManager#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		XjcTreeElement treeElement = (XjcTreeElement) element;
		return treeElement.getTreeChild().getImage(treeElement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATreeManager#getFont(java.lang.Object)
	 */
	@Override
	public Font getFont(Object element) {
		XjcTreeElement treeElement = (XjcTreeElement) element;
		Font font = null == treeElement.getParentTreeChild() ? null : treeElement.getParentTreeChild().getFont(treeElement);
		if (IEntityConstants.RETRIEVE != treeElement.getEntity().getStatus())
			return BOLD;
		return font;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.viewer.ATreeManager#getForeground(java.lang.Object)
	 */
	@Override
	public Color getForeground(Object element) {
		XjcTreeElement treeElement = (XjcTreeElement) element;
		Color color = null == treeElement.getParentTreeChild() ? null : treeElement.getParentTreeChild().getForeground(treeElement);
		if (bindingService.getErrorMessageMap().contains(treeElement.getEntity()))
			// if (bindingService.hasError(treeElement.getElement()))
			return AController.ERROR_COLOR;
		return color;
	}

	/**
	 * Gets the binding service.
	 * 
	 * @return the binding service
	 */
	public ABindingService getBindingService() {
		return bindingService;
	}

	/**
	 * Sets the binding service.
	 * 
	 * @param bindingService
	 *            the new binding service
	 */
	public void setBindingService(ABindingService bindingService) {
		this.bindingService = bindingService;
	}
}
