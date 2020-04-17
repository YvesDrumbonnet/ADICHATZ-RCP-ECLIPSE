/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.tool.cacheManager;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.FormPageController;
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
 * The Class TabularByCacheKeyFormPage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TabularByCacheKeyFormPage implements ICachePage {

	/** The table controller ie. */
	private ICacheEditorTree tabularControllerIE;

	/** The column label providers. */
	private List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/**
	 * Instantiates a new table by cache key form page.
	 * 
	 * @param editor
	 *            the editor
	 */
	public TabularByCacheKeyFormPage(CacheManagerPart part) {
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
		final Section tabularControllerSection = EditorUtils.addTableSection(parent, toolkit, getFromToolBundle("cacheKeyList"));

		final TreeViewer tableTreeViewer = new TreeViewer((Composite) tabularControllerSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TreeViewerColumn> tableTreeViewerColumns = new ArrayList<TreeViewerColumn>();

		tabularControllerIE = new ICacheEditorTree() {
			@Override
			public Composite getClient() {
				return (Composite) tabularControllerSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return pluginResources.getDataAccess().getDataCache().getTabularControllerByCKey().entrySet();
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
				pluginResources.getDataAccess().getDataCache().getTabularControllerByCKey().clear();
				EditorUtils.addElements(this);
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

		};

		// Entity type
		EditorUtils.addTreeColumn(tabularControllerIE, getFromEngineBundle("entityType"), new ColumnLabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry) {
					return ((Map.Entry<MultiKey, Set<ATabularController<?>>>) element).getKey().getKey(0).toString();
				}
				return null;
			}
		}, 0);
		EditorUtils.addTreeColumn(tabularControllerIE, getFromToolBundle("entityId"), new ColumnLabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry) {
					MultiKey cacheKey = ((Map.Entry<MultiKey, Set<ATabularController<?>>>) element).getKey();
					return String.valueOf(cacheKey.getKey(1).toString());
				}
				return null;
			}
		}, 1);

		// Editor
		EditorUtils.addTreeColumn(tabularControllerIE, getFromToolBundle("editor"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController)
					return ((ATabularController<?>) element).getBindingService().getTitle();
				return null;
			}

		}, 2);

		// Editor page
		EditorUtils.addTreeColumn(tabularControllerIE, getFromToolBundle("page"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController) {
					AWidgetController formPageController = (AWidgetController) EditorUtils.getSpecificController(
							(ATabularController<?>) element, FormPageController.class, ContainerController.class);
					if (null == formPageController)
						return null;
					return formPageController.getRegisterId();
				}
				return null;
			}
		}, 3);

		// Editor section
		EditorUtils.addTreeColumn(tabularControllerIE, getFromToolBundle("section"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return EditorUtils.getContainerText(element);
			}
		}, 4);

		EditorUtils.addTreeColumn(tabularControllerIE, getFromToolBundle("parentType"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController) {
					IEntity<?> entity = ((ATabularController<?>) element).getEntity();
					if (null == entity)
						return null;
					return entity.getCacheKey().getKey(1).toString();
				}
				return null;
			}

		}, 5);

		EditorUtils.addTreeColumn(tabularControllerIE, getFromToolBundle("parentIdentifier"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ATabularController) {
					IEntity<?> entity = ((ATabularController<?>) element).getEntity();
					if (null == entity)
						return null;
					return String.valueOf(((ATabularController<?>) element).getEntity().getBeanId());
				}
				return null;
			}

		}, 6);

		ITreeContentProvider treeContentProvider = new ITreeContentProvider() {

			@SuppressWarnings("unchecked")
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Map.Entry)
					return ((Map.Entry<MultiKey, Set<ATabularController<?>>>) parentElement).getValue().toArray();
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
				return pluginResources.getDataAccess().getDataCache().getTabularControllerByCKey().entrySet().toArray();
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};

		ToolBarManager toolBarManager = EditorUtils.addToolBarManager(tabularControllerSection);
		EditorUtils.addClearAction(toolBarManager, tabularControllerIE);
		EditorUtils.addRefreshAction(toolBarManager, tabularControllerIE);
		toolBarManager.update(true);

		EditorUtils.addTree(tabularControllerIE, "grow, height 100:100:n, w n:150:n", treeContentProvider);
		EditorUtils.addElements(tabularControllerIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#refreshPage()
	 */
	@Override
	public void refreshPage() {
		EditorUtils.addElements(tabularControllerIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#clearPage()
	 */
	@Override
	public void clearPage() {
		tabularControllerIE.clear();
	}
}
