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
package org.adichatz.tool.cacheManager;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.jpa.data.JPAEntity;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class BoundTabularControllerFormPage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class BoundTabularControllerFormPage implements ICachePage {

	/** The table controller ie. */
	private ICacheEditorTree treeControllerIE;

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/**
	 * Instantiates a new bound tree controller form page.
	 */
	public BoundTabularControllerFormPage(CacheManagerPart part) {
		pluginResources = part.getPluginResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#createContent(org.eclipse.ui.forms.IManagedForm, java.lang.String)
	 */
	@Override
	public void createContent(IManagedForm managedForm, String formText) {
		scrolledForm = managedForm.getForm();
		scrolledForm.setText(formText);
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		createTabularControllersSection(scrolledForm.getBody(), managedForm.getToolkit());

		EditorUtils.addPagesActions(this, scrolledForm, pluginResources);
		scrolledForm.updateToolBar();

	}

	@Override
	public ScrolledForm getScrolledForm() {
		return scrolledForm;
	}

	/**
	 * Creates the table controllers section.
	 * 
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 */
	private void createTabularControllersSection(Composite parent, FormToolkit toolkit) {
		final Section tabularControllerSection = EditorUtils.addTableSection(parent, toolkit,
				getFromToolBundle("cachedTabularControllers"));

		final TreeViewer tableTreeViewer = new TreeViewer((Composite) tabularControllerSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TreeViewerColumn> tableTreeViewerColumns = new ArrayList<TreeViewerColumn>();

		final List<ColumnLabelProvider> tableColumnProvider = new ArrayList<ColumnLabelProvider>();
		treeControllerIE = new ICacheEditorTree() {
			@Override
			public Composite getClient() {
				return (Composite) tabularControllerSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return getTabularControllerTree(pluginResources.getDataAccess().getDataCache().getTabularControllerByPKey())
						.entrySet();
			}

			@Override
			public TreeViewer getTreeViewer() {
				return tableTreeViewer;
			}

			@Override
			public List<TreeViewerColumn> getTreeViewerColumns() {
				return tableTreeViewerColumns;
			}

			@Override
			public void clear() {
				pluginResources.getDataAccess().getDataCache().getTabularControllerByPKey().clear();
				EditorUtils.addElements(this);
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return tableColumnProvider;
			}

		};

		// Entity type
		EditorUtils.addTreeColumn(treeControllerIE, getFromEngineBundle("entityType"), new ColumnLabelProvider() {
			@SuppressWarnings("rawtypes")
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry && ((Map.Entry) element).getKey() instanceof String)
					return (String) ((Map.Entry) element).getKey();
				return null;
			}
		}, 0);
		// MappedByField
		EditorUtils.addTreeColumn(treeControllerIE, getFromToolBundle("mappedByField"), new ColumnLabelProvider() {
			@SuppressWarnings("rawtypes")
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry && ((Map.Entry) element).getKey() instanceof MultiKey)
					return ((MultiKey) ((Map.Entry) element).getKey()).getString(0);
				return null;
			}
		}, 1);
		// Parent type
		EditorUtils.addTreeColumn(treeControllerIE, getFromToolBundle("parentType"), new ColumnLabelProvider() {
			@SuppressWarnings("rawtypes")
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry && ((Map.Entry) element).getKey() instanceof MultiKey) {
					JPAEntity<?> parentEntity = (JPAEntity) ((MultiKey) ((Map.Entry) element).getKey()).getKey(1);
					return parentEntity.getEntityMM().getEntityId();
				}
				return null;
			}

		}, 2);
		EditorUtils.addTreeColumn(treeControllerIE, getFromToolBundle("parentIdentifier"), new ColumnLabelProvider() {
			@SuppressWarnings("rawtypes")
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry && ((Map.Entry) element).getKey() instanceof MultiKey) {
					IEntity<?> parentEntity = (IEntity) ((MultiKey) ((Map.Entry) element).getKey()).getKey(1);
					return String.valueOf(parentEntity.getBeanId());
				}
				return null;
			}

		}, 3);
		EditorUtils.addTreeColumn(treeControllerIE, getFromToolBundle("editor"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController)
					return ((ATabularController<?>) element).getBindingService().getTitle();
				return null;
			}

		}, 4);
		// Editor page
		EditorUtils.addTreeColumn(treeControllerIE, getFromToolBundle("page"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController)
					return ((FormPageController) EditorUtils.getSpecificController((ATabularController<?>) element,
							FormPageController.class, ContainerController.class)).getRegisterId();
				return null;
			}
		}, 5);
		// Editor section
		EditorUtils.addTreeColumn(treeControllerIE, getFromToolBundle("section"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController) {
					return EditorUtils.getContainerText(element);
				}
				return null;
			}
		}, 6);

		ITreeContentProvider treeContentProvider = new ITreeContentProvider() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Map.Entry && ((Map.Entry) parentElement).getKey() instanceof String)
					return ((Map.Entry<String, Map<MultiKey, Set<ATabularController<?>>>>) parentElement).getValue().entrySet()
							.toArray();
				if (parentElement instanceof Map.Entry && ((Map.Entry) parentElement).getKey() instanceof MultiKey)
					return ((Map.Entry<MultiKey, List<ATabularController<?>>>) parentElement).getValue().toArray();
				else
					return null;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof Map.Entry)
					return true;
				else
					return false;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return getTabularControllerTree(pluginResources.getDataAccess().getDataCache().getTabularControllerByPKey())
						.entrySet().toArray();
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};

		ToolBarManager toolBarManager = EditorUtils.addToolBarManager(tabularControllerSection);
		EditorUtils.addClearAction(toolBarManager, treeControllerIE);
		EditorUtils.addRefreshAction(toolBarManager, treeControllerIE);
		toolBarManager.update(true);

		EditorUtils.addTree(treeControllerIE, "grow, height 100:100:n, w n:150:n", treeContentProvider);
		EditorUtils.addElements(treeControllerIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#refreshPage()
	 */
	@Override
	public void refreshPage() {
		EditorUtils.addElements(treeControllerIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#clearPage()
	 */
	@Override
	public void clearPage() {
		treeControllerIE.clear();
	}

	/**
	 * Gets the table controller tree.
	 * 
	 * @param tabularControllerMap
	 *            the table controller map
	 * 
	 * @return the table controller tree
	 */
	public static Map<String, Map<MultiKey, List<ATabularController<?>>>> getTabularControllerTree(
			Map<MultiKey, Set<ATabularController<?>>> tabularControllerMap) {
		Map<String, Map<MultiKey, List<ATabularController<?>>>> entityTypeTree = new HashMap<String, Map<MultiKey, List<ATabularController<?>>>>();
		for (MultiKey tabularControllerMK : tabularControllerMap.keySet()) {
			for (ATabularController<?> tabularController : tabularControllerMap.get(tabularControllerMK)) {
				String entityType = tabularController.getQueryManager().getEntityMM().getPluginEntity().getPluginEntityTree()
						.getEntityMM(tabularController.getBeanClass()).getEntityId();
				Map<MultiKey, List<ATabularController<?>>> multiKeyMap = entityTypeTree.get(entityType);
				if (null == multiKeyMap) {
					multiKeyMap = new HashMap<MultiKey, List<ATabularController<?>>>();
					entityTypeTree.put(entityType, multiKeyMap);
				}
				MultiKey multiKey = new MultiKey(tabularControllerMK.getKey(1), tabularController.getEntity());
				List<ATabularController<?>> tabularControllers = multiKeyMap.get(multiKey);
				if (null == tabularControllers) {
					tabularControllers = new ArrayList<ATabularController<?>>();
					multiKeyMap.put(multiKey, tabularControllers);
				}
				tabularControllers.add(tabularController);
			}
		}
		return entityTypeTree;

	}
}
