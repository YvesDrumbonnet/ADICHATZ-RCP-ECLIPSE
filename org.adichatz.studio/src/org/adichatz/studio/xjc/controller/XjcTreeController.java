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
package org.adichatz.studio.xjc.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.TreeController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.studio.gencode.editor.XjcTreeManager;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcDataCache;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.editor.AStudioTreeManager;
import org.adichatz.studio.xjc.editor.StudioOutlinePage;
import org.adichatz.studio.xjc.editor.action.CutXjcElementAction;
import org.adichatz.studio.xjc.editor.action.XjcSelectionTransfer;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcTreeController.
 */
public class XjcTreeController extends TreeController {

	/** The elected ref method. */
	private Method electedRefMethod;

	/** The elected entity set. */
	private EntitySet<?> electedEntitySet;

	/** The entity. */
	private XjcEntity<?> entity;

	private ISelectionChangedListener selectionChangedListener;

	/**
	 * Instantiates a new xjc tree controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public XjcTreeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/**
	 * Checks if is refFieldEligible for paste operation.
	 * 
	 * Selection is refFieldEligible when selection has only one element result of a RefField.
	 * 
	 * @param targetElement
	 *            the target element
	 * @param clipboardSelection
	 *            the clipboard selection
	 * @return true, if is ref field eligible
	 */
	public boolean isRefFieldEligible(XjcTreeElement targetElement, ITreeSelection clipboardSelection) {
		if (1 != clipboardSelection.size())
			return false;
		else {
			XjcTreeElement treeElement = (XjcTreeElement) clipboardSelection.getFirstElement();
			Method getMethod = treeElement.getGetMethod();
			if (null != getMethod) {
				Class<?> returnType = getMethod.getReturnType();
				if (null != targetElement.getTreeChild().getRefFieldNames())
					for (String refFieldName : targetElement.getTreeChild().getRefFieldNames()) {
						Method method = FieldTools.getGetMethod(targetElement.getEntity().getBeanClass(), refFieldName, true);
						if (ReflectionTools.hasSuperClass(method.getReturnType(), returnType)) {
							electedRefMethod = method;
							return true;
						}
					}
			}
			return false;
		}
	}

	/**
	 * Checks if is entitySetEligible for paste operation.
	 * 
	 * Selection is entitySetEligible when selection (of same parent @see AOneParentXjcAction) are result of and EntitySet.
	 * 
	 * @param targetElement
	 *            the target element
	 * @param clipboardSelection
	 *            the clipboard selection
	 * @return true, if is entity set eligible
	 */
	public boolean isEntitySetEligible(XjcTreeElement targetElement, ITreeSelection clipboardSelection) {
		if (null == targetElement)
			return false;
		/*
		 * All objects from clipboard must be eligible to the parent object
		 */
		for (Object clipboardObject : clipboardSelection.toArray()) {
			XjcTreeElement treeElement = (XjcTreeElement) clipboardObject;
			boolean selectionOk = false;
			AEntityMetaModel<?> entityMM = treeElement.getEntity().getEntityMM();
			PluginEntity<?> pluginEntity = targetElement.getEntity().getEntityMM().getPluginEntity();
			while (null != pluginEntity) {
				for (PropertyField propertyField : pluginEntity.getEntityMetaModel().getFieldMap().values()) {
					if (propertyField instanceof XjcEntitySet) {
						XjcEntitySet<?> entitySet = (XjcEntitySet<?>) propertyField;
						if (null != entitySet.getElementIds())
							for (String entityURI : entitySet.getElementIds()) {
								if (entityURI.equals(entityMM.getPluginEntity().getEntityURI())) {
									electedEntitySet = entitySet;
									selectionOk = true;
									break;
								}
								if (selectionOk)
									break;
							}
					}
				}
				if (null == pluginEntity.getSuperEntityURI())
					break;
				pluginEntity = pluginEntity.getPluginEntityTree().getPluginEntityURIMap().get(pluginEntity.getSuperEntityURI());
			}
			if (!selectionOk) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the elected ref method.
	 * 
	 * @return the elected ref method
	 */
	public Method getElectedRefMethod() {
		return electedRefMethod;
	}

	/**
	 * Gets the elected entity set.
	 * 
	 * @return the elected entity set
	 */
	public EntitySet<?> getElectedEntitySet() {
		return electedEntitySet;
	}

	@Override
	public IEntity<?> getEntity() {
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.TreeController#getTreeManager()
	 */
	@Override
	public XjcTreeManager getTreeManager() {
		return (XjcTreeManager) treeManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AWidgetController#getBindingService()
	 */
	@Override
	public XjcBindingService getBindingService() {
		return (XjcBindingService) super.getBindingService();
	}

	/**
	 * Refresh.
	 * 
	 * @param element
	 *            the element
	 */
	public void refresh(Object element) {
		((XjcTreeElement) element).clearChildren();
		viewer.refresh(element);
	}

	public void removeSelectionChangedListener() {
		viewer.removeSelectionChangedListener(selectionChangedListener);
	}

	public void addSelectionChangedListener() {
		if (null != selectionChangedListener)
			viewer.removeSelectionChangedListener(selectionChangedListener);
		selectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StudioOutlinePage outlinePage = getBindingService().getEditor().getOutlinePage();
				if (null != outlinePage) {
					XjcTreeElement treeElement = (XjcTreeElement) ((IStructuredSelection) event.getSelection()).getFirstElement();
					outlinePage.setSelection(XjcTreeController.this,
							event.getSelection().isEmpty() ? null : treeElement.getEntity(), false);
				}
			}
		};
		viewer.addSelectionChangedListener(selectionChangedListener);
	}

	/**
	 * Inits the tree manager.
	 */
	public void initTreeManager(ITreeWrapper treeWrapper, MultiKey cacheKey) {
		if (null == viewer) {
			viewer = new TreeViewer(tree); // create a new TreeViewer to avoid unwanted refresh when setting contentprovider
			addSelectionChangedListener();
			if (null == entity) { // First pass
				addDragAndDrop(); // Points on tree and not on viewer so launched uniquely for first pass.
			}

			treeManager = new XjcTreeManager(getPluginResources());
			if (null == AStudioTreeManager.BOLD) {
				AStudioTreeManager.BOLD = EngineTools.getModifiedFont(tree.getFont(), SWT.BOLD);
				AStudioTreeManager.ITALIC = EngineTools.getModifiedFont(tree.getFont(), SWT.ITALIC);
			}

			viewer.setLabelProvider(getTreeManager().getTreeLabelProvider());
			viewer.setContentProvider(getTreeManager().getTreeContentProvider());

			getTreeManager().setTreeController(this);
			getTreeManager().setBindingService(getBindingService());
		} else {
			getTreeManager().getChildrenMap().clear();
			entity.getTreeElement().clear(entity.getTreeElement().getTreeManager());
		}
		entity = ((XjcDataCache) getPluginResources().getDataAccess().getDataCache()).fetchEntity(treeWrapper, cacheKey);
		entity.setTreeElement(new XjcTreeElement(getTreeManager(), null, treeWrapper, null, false));
		getBindingService().getBindingDispatcher(entity);
		setRootElement(entity.getTreeElement());
		refresh();
		tree.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (SWT.DEL == e.keyCode)
					new CutXjcElementAction().cutTreeElement(XjcTreeController.this);
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	private void addDragAndDrop() {
		viewer.addDragSupport(DND.DROP_MOVE, new Transfer[] { XjcSelectionTransfer.getTransfer() }, new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				event.doit = !viewer.getSelection().isEmpty() && isDragEligible();
				if (event.doit)
					XjcSelectionTransfer.getTransfer().setSelection((ITreeSelection) viewer.getSelection());
				else
					XjcSelectionTransfer.getTransfer().setSelection(null);
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
			}

			/**
			 * Checks if is dragEligible.
			 * 
			 * Selection is eligible for drag support when selection has 1 or more tree elements and each tree element has the same
			 * getMethod
			 * 
			 * @return true, if is drag eligible
			 */
			private boolean isDragEligible() {
				if (((ITreeSelection) viewer.getSelection()).isEmpty()) {
					return false;
				} else {
					Method getMethod = null;
					for (Object selectedObject : ((ITreeSelection) viewer.getSelection()).toArray()) {
						if (null == getMethod) {
							getMethod = ((XjcTreeElement) selectedObject).getGetMethod();
						} else if (!getMethod.equals(((XjcTreeElement) selectedObject).getGetMethod())) {
							return false;
						}
					}
					return true;
				}
			}
		});

		ViewerDropAdapter viewerDropAdapter = new ViewerDropAdapter(viewer) {
			private XjcTreeElement currentTarget;

			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public boolean performDrop(Object data) {

				XjcTreeElement parentElement = currentTarget.getParentElement();
				List resultList = (List) ReflectionTools.invokeMethod(currentTarget.getGetMethod(), parentElement.getElement());
				int index = 0;
				for (Object child : resultList) {
					if (currentTarget.getElement().equals(child)) {
						List selList = new ArrayList();
						for (Object element : ((ITreeSelection) viewer.getSelection()).toList())
							selList.add(((XjcTreeElement) element).getElement());
						resultList.removeAll(selList);
						if (index > resultList.size())
							resultList.addAll(selList);
						else
							resultList.addAll(index, selList);
						update(null, parentElement);
						refresh(parentElement);
						TreePath parentPath = getTreePath(parentElement);
						viewer.setSelection(new TreeSelection(parentPath));
						parentElement.getEntity().putStatus(IEntityConstants.MERGE, true);
						getBindingService().enableActions();
						return true;
					}
					index++;
				}
				return false;
			}

			@Override
			public boolean validateDrop(Object target, int operation, TransferData transferType) {
				currentTarget = (XjcTreeElement) target;
				return XjcSelectionTransfer.getTransfer().isSupportedType(transferType) && isDropEligible();
			}

			/**
			 * Checks if is drop eligible.
			 * 
			 * @param target
			 *            the target
			 * @return true, if is drop eligible
			 */
			private boolean isDropEligible() {
				if (null == currentTarget || null == currentTarget.getParentElement())
					return false;
				XjcTreeElement treeElement = (XjcTreeElement) XjcSelectionTransfer.getTransfer().getSelection().getFirstElement();
				return currentTarget.getParentElement().equals(treeElement.getParentElement())
						&& currentTarget.getGetMethod().equals(treeElement.getGetMethod());
			}
		};

		viewer.addDropSupport(DND.DROP_MOVE, new Transfer[] { XjcSelectionTransfer.getTransfer() }, viewerDropAdapter);
	}

	private TreePath getTreePath(XjcTreeElement treeElement) {
		XjcTreeElement parentElement = treeElement;
		List<XjcTreeElement> elements = new ArrayList<XjcTreeElement>();
		while (null != parentElement) {
			elements.add(parentElement);
			parentElement = parentElement.getParentElement();
		}
		int i = elements.size();
		Object[] segments = new Object[i];
		for (Object element : elements)
			segments[--i] = element;
		return new TreePath(segments);
	}
}
