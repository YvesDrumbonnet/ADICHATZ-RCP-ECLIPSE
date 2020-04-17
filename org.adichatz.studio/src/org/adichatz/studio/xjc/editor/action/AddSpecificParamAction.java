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
package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.xml.bind.annotation.XmlType;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.tree.ATreeChild;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.generator.xjc.ContentProviderType;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;

// TODO: Auto-generated Javadoc
/**
 * The Class AddContentProviderAction.
 */
public class AddSpecificParamAction extends AEditAction implements Comparable<AddSpecificParamAction> {

	/** The get method. */
	private Method getMethod;

	/** The bean class. */
	private Class<?> beanClass;

	/** The parent get method. */
	private Method parentGetMethod;

	/** The is collection. */
	private boolean isCollection;

	private String entityURI;

	private String[] entityKeys;

	/**
	 * Instantiates a new AddSpecificParamAction (content provider or query preference).
	 * 
	 * @param treeController
	 *            the tree controller
	 * @param getMethod
	 *            the get method
	 * @param beanClass
	 *            the bean class
	 */
	public AddSpecificParamAction(XjcTreeController treeController, Method getMethod, Class<?> contentProvideClass,
			boolean isCollection) {
		this.beanClass = contentProvideClass;
		entityURI = treeController.getPluginResources().getPluginEntityTree().getPluginEntity(beanClass).getEntityURI();
		entityKeys = EngineTools.getInstanceKeys(entityURI);
		init(treeController, getMethod, isCollection);
	}

	/**
	 * Instantiates a new AddSpecificParamAction (content provider or query preference) on refField.
	 * 
	 * @param treeController
	 *            the tree controller
	 * @param getMethod
	 *            the get method
	 */
	public AddSpecificParamAction(XjcTreeController treeController, Method getMethod, boolean isCollection) {
		this.beanClass = isCollection ? getGeneric(getMethod) : getMethod.getReturnType();
		PluginEntity pluginEntity = treeController.getPluginResources().getPluginEntityTree().getPluginEntity(beanClass);
		if (null != pluginEntity) {
			entityURI = treeController.getPluginResources().getPluginEntityTree().getPluginEntity(beanClass).getEntityURI();
			entityKeys = EngineTools.getInstanceKeys(entityURI);
			init(treeController, getMethod, false);
		}
	}

	/**
	 * Instantiates a new adds the xml element action (enittySet).
	 * 
	 * @param treeController
	 *            the tree controller
	 * @param getMethod
	 *            the get method
	 * @param entityMM
	 *            the entity MetaModel
	 */
	public AddSpecificParamAction(XjcTreeController treeController, Method getMethod, AEntityMetaModel<?> entityMM) {
		this.beanClass = entityMM.getBeanClass();
		entityURI = entityMM.getPluginEntity().getEntityURI();
		entityKeys = EngineTools.getInstanceKeys(entityURI);
		init(treeController, getMethod, true);
	}

	private void init(XjcTreeController treeController, Method getMethod, boolean isCollection) {
		this.isCollection = isCollection;

		setText(xjcElementRB.getString(entityKeys[2]));
		ATreeChild treeChild = treeController.getTreeManager().getTreeChild(entityURI);
		Image image = treeChild.getImage(null);
		if (null != image)
			setImageDescriptor(ImageDescriptor.createFromImage(image));
		this.getMethod = getMethod;
		this.treeController = treeController;
	}

	@Override
	public void runAction() {
		// Nothing to do
	}

	public void setParentGetMethod(Method parentGetMethod) {
		this.parentGetMethod = parentGetMethod;
		isCollection = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.Event)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void runWithEvent(Event event) {
		Object bean;
		XmlType xmlTypeAnnotation = beanClass.getAnnotation(XmlType.class);
		if (null != xmlTypeAnnotation.factoryMethod() && !xmlTypeAnnotation.factoryMethod().isEmpty()) {
			Method getMethod = ReflectionTools.getMethod(xmlTypeAnnotation.factoryClass(), xmlTypeAnnotation.factoryMethod());
			bean = ReflectionTools.invokeMethod(getMethod, null);
		} else
			bean = ReflectionTools.instantiateClass(beanClass);
		if (bean instanceof ContentProviderType)
			((ContentProviderType) bean).setId(ParamMap.CONTENT_PROVIDER);

		ITreeSelection selection = (ITreeSelection) treeController.getViewer().getSelection();
		XjcTreeElement treeElement = (XjcTreeElement) selection.getFirstElement();
		XjcTreeElement parentElement = null;
		if (isCollection) {
			Object parentObject;
			if (null == parentGetMethod) {
				parentObject = treeElement.getElement();
			} else {
				parentObject = ReflectionTools.invokeMethod(parentGetMethod, treeElement.getElement());
				if (null == parentObject) {
					parentObject = ReflectionTools.instantiateClass(parentGetMethod.getReturnType());
					invokeSetMethod(treeElement.getElement(), parentObject, parentGetMethod);
					parentElement = treeController.getTreeManager().newTreeElement(treeElement, parentGetMethod, parentObject,
							true);
					parentElement.getEntity().putStatus(IEntityConstants.PERSIST, true);
					parentElement.getChildren(); // initialize childElements
					treeElement.getChildElements().add(parentElement);
				} else {
					for (AXjcTreeElement intermediateElement : treeElement.getChildren())
						if (parentObject.equals(intermediateElement.getElement())) {
							parentElement = (XjcTreeElement) intermediateElement;
							parentElement.getEntity().putStatus(IEntityConstants.PERSIST, true);
						}
				}
			}
			Collection collection = (Collection) ReflectionTools.invokeMethod(getMethod, parentObject);
			collection.add(bean);
		} else {
			invokeSetMethod(treeElement.getElement(), bean, getMethod);
		}

		TreePath currentPath = selection.getPaths()[0];
		if (null != parentElement) {
			currentPath = currentPath.createChildPath(parentElement);
			// treeController.getViewer().setSelection(new TreeSelection(currentPath));
		} else
			parentElement = treeElement;
		XjcTreeElement childElement = treeController.getTreeManager().newTreeElement(parentElement, getMethod, bean, false);
		parentElement.getChildElements().add(childElement);
		childElement.getEntity().putStatus(IEntityConstants.PERSIST, true);
		setDirty();

		treeController.getViewer().refresh(treeElement);
		treeController.getViewer().setSelection(new TreeSelection(currentPath.createChildPath(childElement)));
	}

	/**
	 * Invoke set method.
	 * 
	 * @param parentObject
	 *            the parent object
	 * @param value
	 *            the value
	 * @param getMethod
	 *            the get method
	 */
	private void invokeSetMethod(Object parentObject, Object value, Method getMethod) {
		Method setMethod = ReflectionTools.getMethod(parentObject.getClass(), "s".concat(getMethod.getName().substring(1)),
				getMethod.getReturnType());
		ReflectionTools.invokeMethod(setMethod, parentObject, new Object[] { value });

	}

	/**
	 * Gets the generic.
	 * 
	 * @param method
	 *            the method
	 * @return the generic
	 */
	private Class<?> getGeneric(Method method) {
		String generic = method.toGenericString();
		try {
			String referencedClassName = generic.substring(generic.indexOf('<') + 1, generic.indexOf('>'));
			return ReflectionTools.getClazz(referencedClassName);
		} catch (StringIndexOutOfBoundsException e) {
			logError(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AddSpecificParamAction addAction) {
		return getText().compareTo(addAction.getText());
	}
}
