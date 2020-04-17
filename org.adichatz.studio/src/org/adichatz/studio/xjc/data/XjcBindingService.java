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
package org.adichatz.studio.xjc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.APageController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.collection.TreeController;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.indigo.editor.ADirtyFormEditor;
import org.adichatz.engine.indigo.editor.EditorBindingService;
import org.adichatz.engine.indigo.editor.FormEditorCore;
import org.adichatz.engine.indigo.editor.FormPageHeader;
import org.adichatz.engine.validation.EntityBindingDispatcher;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.engine.validation.ValidationPath;
import org.adichatz.engine.viewer.RootElement;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.studio.xjc.editor.AStudioFormEditor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.forms.IMessageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcBindingService.
 */
public class XjcBindingService extends EditorBindingService {

	/** The editor. */
	private AStudioFormEditor editor;

	/** The error beans. */
	private Set<Object> errorBeans = new HashSet<Object>();

	/** The tree error message map. */
	private Map<IEntity<?>, ErrorMessage> treeErrorMessageMap = new HashMap<IEntity<?>, ErrorMessage>();

	/** The changing entity. */
	private boolean changingEntity = false;

	/** The data cache. */
	private ADataCache dataCache;

	/**
	 * Instantiates a new xjc binding service.
	 * 
	 * @param boundedController
	 *            the bounded controller
	 */
	public XjcBindingService(IBoundedController boundedController) {
		super((AStudioFormEditor) boundedController);
		editor = (AStudioFormEditor) boundedController;
		dataCache = editor.getPluginResources().getDataAccess().getDataCache();
	}

	/**
	 * Gets the editor.
	 * 
	 * @return the editor
	 */
	public AStudioFormEditor getEditor() {
		return editor;
	}

	/**
	 * Gets the tree error message map.
	 * 
	 * @return the tree error message map
	 */
	public Map<IEntity<?>, ErrorMessage> getTreeErrorMessageMap() {
		return treeErrorMessageMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#addMessage(org.adichatz.engine.validation.ErrorMessage)
	 */
	@Override
	public boolean addMessage(ErrorMessage errorMessage) {
		if (super.addMessage(errorMessage)) {
			errorBeans.add(errorMessage.getEntity().getBean());
			treeErrorMessageMap.put(errorMessage.getEntity(), errorMessage);
			enableActions();
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#removeMessage(org.adichatz.engine.validation.ErrorMessage)
	 */
	@Override
	public boolean removeMessage(ErrorMessage errorMessage) {
		boolean removed = super.removeMessage(errorMessage);
		if (!changingEntity && !errorMessageMap.contains(errorMessage.getEntity())) {
			errorBeans.remove(errorMessage.getEntity().getBean());
			treeErrorMessageMap.remove(errorMessage.getEntity());
			enableActions();
		}
		return removed;
	}

	/**
	 * Enable actions.
	 */
	public void enableActions() {
		editor.setDirty(isDirty());
		editor.enableAction(errorBeans.isEmpty());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#isDirty()
	 */
	@Override
	public boolean isDirty() {
		for (IEntity<?> entity : getEntities()) {
			if (IEntityConstants.RETRIEVE < entity.getStatus())
				return true;
		}
		return false;
	}

	/**
	 * Update set.
	 * 
	 * @param setController
	 *            the set controller
	 * @param entity
	 *            the entity
	 */
	public void updateSet(ASetController setController, XjcEntity<?> entity) {
		entity.getSetController().update(entity, null == entity.getTreeElement() ? entity.getBean() : entity.getTreeElement());
	}

	/**
	 * Checks for error.
	 * 
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	public boolean hasError(Object element) {
		return errorBeans.contains(element);
	}

	/**
	 * Gets the error beans.
	 * 
	 * @return the error beans
	 */
	public Set<Object> getErrorBeans() {
		return errorBeans;
	}

	/**
	 * Checks if is updated.
	 * 
	 * @param element
	 *            the element
	 * @return true, if is updated
	 */
	public boolean isUpdated(Object element) {
		return IEntityConstants.RETRIEVE != dataCache.fetchEntity(element).getStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#clearMessages()
	 */
	public void clearMessages() {
		getUpdatedEntities().clear();
		errorBeans.clear();
		treeErrorMessageMap.clear();
		for (IEntity<?> entity : getEntities().toArray(new IEntity<?>[entityServiceMap.size()])) {
			EntityBindingDispatcher bindingDispatcher = getBindingDispatcher(entity);
			if (null != bindingDispatcher) {
				bindingDispatcher.dispose();
				for (EntityInjection entityInjection : bindingDispatcher.getEntityInjections())
					entityInjection.clearTransientListeners();
			}
			entity.disposeFromCache();
		}
	}

	/**
	 * Change entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void changeEntity(IEntity<?> entity) {
		changingEntity = true;
		Set<ErrorMessage> errorMessages = getErrorMessageMap().get(entity);
		if (null != errorMessages) {
			// Use array to avoid ConcurrentModificationException
			for (ErrorMessage errorMessage : errorMessages.toArray(new ErrorMessage[errorMessages.size()])) {
				IMessageManager messageManager = errorMessage.getValidator().getFormMessageManager().getForm().getMessageManager();
				for (IValidableController controller : errorMessage.getValidator().getHostingControllers())
					messageManager.removeMessage(errorMessage.getValidator().getKey(), controller.getControl());
				removeMessage(errorMessage);
			}
		}
		changingEntity = false;
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#saveEntities()
	 */
	@Override
	public boolean saveEntities() {
		Set<IEntity<?>> updatedEntities = getUpdatedEntities().keySet(); // result is null after saveEntities()
		boolean result = super.saveEntities();
		for (IEntity<?> entity : updatedEntities) {
			XjcEntity<?> xjcEntity = (XjcEntity<?>) entity;
			ASetController setController = xjcEntity.getSetController();
			if (null != setController) {
				setController.getViewer().refresh(xjcEntity.getTreeElement(), true);
				setController.update(entity, null == xjcEntity.getTreeElement() ? entity.getBean() : xjcEntity.getTreeElement());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getUpdatedEntities()
	 */
	public Map<IEntity<?>, EntityBindingDispatcher> getUpdatedEntities() {
		Map<IEntity<?>, EntityBindingDispatcher> updatedEntities = new HashMap<IEntity<?>, EntityBindingDispatcher>();
		for (Map.Entry<IEntity<?>, EntityBindingDispatcher> entry : entityServiceMap.entrySet()) {
			if (IEntityConstants.RETRIEVE != entry.getKey().getStatus())
				updatedEntities.put(entry.getKey(), entry.getValue());
		}
		return updatedEntities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.EditorBindingService#getErrorPaths()
	 */
	public List<ErrorPath> getErrorPaths() {
		List<ErrorPath> errorPaths = new ArrayList<ErrorPath>();
		for (final ErrorMessage errorMessage : treeErrorMessageMap.values()) {
			ErrorPath errorPath = new ErrorPath(errorMessage);
			ICollectionController parentController = errorMessage.getValidator().getHostingControllers()[0].getParentController();
			// Stop when reaching editor controller (parent controller) or bindingService is not the same as current one
			while (null != parentController && this.equals(parentController.getBindingService())) {
				ValidationPath validationPath = new ValidationPath(parentController);
				errorPath.getValidationsPaths().add(validationPath);
				if (parentController instanceof ASetController) {
					validationPath.setValue(errorMessage.getEntity());
				}
				if (parentController instanceof ContainerController)
					parentController = ((XjcEntity<?>) errorMessage.getEntity()).getSetController();
				else {
					if (parentController instanceof APageController) {
						FormEditorCore editorgencode = editor.getGenCode();
						for (FormPageHeader formPageHeader : editorgencode.getFormPageHeaderMap().values())
							if (parentController.equals(formPageHeader.getFormPageController())) {
								errorPath.setPage(formPageHeader.getTitle());
								break;
							}
					}
					parentController = parentController.getParentController();
				}
			}
			errorPaths.add(errorPath);
		}
		return errorPaths;
	}

	/**
	 * Look into tree.
	 * 
	 * @param entity
	 *            the entity
	 * @param parentElement
	 *            the parent element
	 * @param parentTreePath
	 *            the parent tree path
	 * @return the tree path
	 */
	private TreePath lookIntoTree(IEntity<?> entity, AXjcTreeElement parentElement, TreePath parentTreePath) {
		if (entity.equals(((XjcTreeElement) parentElement).getEntity())) // Root element is selected
			return parentTreePath;
		if (null != parentElement.getChildElements())
			for (AXjcTreeElement treeElement : parentElement.getChildElements()) {
				TreePath childPath = parentTreePath.createChildPath(treeElement);
				if (entity.equals(((XjcTreeElement) treeElement).getEntity()))
					return childPath;
				TreePath selectedPath = lookIntoTree(entity, treeElement, childPath);
				if (null != selectedPath)
					return selectedPath;
			}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.EditorBindingService#activateControllers(org.adichatz.engine.validation.ErrorPath)
	 */
	@Override
	public void activateControllers(ErrorPath errorPath) {
		ValidationPath validationPath;

		while (null != (validationPath = errorPath.getValidationsPaths().pollLast())) {
			if (validationPath.getController() instanceof APageController) {
				// Active page
				((ADirtyFormEditor) boundedController).setActivePage((APageController) validationPath.getController());
			}
			if (validationPath.getController() instanceof TableController<?>) {
				TableController<?> tableController = (TableController<?>) validationPath.getController();
				tableController.getViewer()
						.setSelection(new StructuredSelection(((IEntity<?>) validationPath.getValue()).getBean()));
			} else if (validationPath.getController() instanceof TreeController) {
				RootElement rootElement = (RootElement) ((TreeController) validationPath.getController()).getRootElement();
				TreePath selectedPath = lookIntoTree((IEntity<?>) validationPath.getValue(), (XjcTreeElement) rootElement.getRoot(),
						new TreePath(new Object[] { rootElement.getRoot() }));
				TreeController treeController = (TreeController) validationPath.getController();
				treeController.getViewer().setSelection(new TreeSelection(selectedPath));
			}
		}
	}
}
