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
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.common.AdiPluginResources;
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
 * The Class RelationshipUpdateFormPage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class RelationshipUpdateFormPage implements ICachePage {

	/** The relation ie. */
	private ICacheEditorTree relationIE;

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/**
	 * Instantiates a new relationship update form page.
	 * 
	 * @param editor
	 *            the editor
	 */
	public RelationshipUpdateFormPage(CacheManagerPart part) {
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
		createRelationshipUpdates(scrolledForm.getBody(), managedForm.getToolkit());

		EditorUtils.addPagesActions(this, scrolledForm, pluginResources);
		scrolledForm.updateToolBar();

	}

	@Override
	public ScrolledForm getScrolledForm() {
		return scrolledForm;
	}

	/**
	 * Creates the relationship updates.
	 * 
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 */
	private void createRelationshipUpdates(Composite parent, FormToolkit toolkit) {
		//
		// Item: List of relationship updates for the entity
		//
		final Section relationSection = EditorUtils.addTableSection(parent, toolkit,
				getFromToolBundle("cachedRelationshipUpdates"));
		relationSection.setLayout(new MigLayout(null, "fill"));

		final TreeViewer relationTreeViewer = new TreeViewer((Composite) relationSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TreeViewerColumn> relationTreeViewerColumns = new ArrayList<TreeViewerColumn>();

		final List<ColumnLabelProvider> relationColumnProvider = new ArrayList<ColumnLabelProvider>();
		relationIE = new ICacheEditorTree() {
			@Override
			public Composite getClient() {
				return (Composite) relationSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return pluginResources.getDataAccess().getDataCache().getRUpdateMap().entrySet();
			}

			@Override
			public TreeViewer getTreeViewer() {
				return relationTreeViewer;
			}

			@Override
			public List<TreeViewerColumn> getTreeViewerColumns() {
				return relationTreeViewerColumns;
			}

			@Override
			public void clear() {
				pluginResources.getDataAccess().getDataCache().getRUpdateMap().clear();
				EditorUtils.addElements(this);
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return relationColumnProvider;
			}
		};

		// Parent class
		EditorUtils.addTreeColumn(relationIE, getFromToolBundle("parentType"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry) {
					RelationshipUpdate relationshipUpdate = ((Map.Entry<MultiKey, List<RelationshipUpdate>>) element).getValue()
							.get(0);
					return pluginResources.getPluginEntityTree().getEntityMM(relationshipUpdate.getParentClass()).getEntityId();
				} else if (element instanceof RelationshipUpdate) {
					return pluginResources.getPluginEntityTree().getEntityMM(((RelationshipUpdate) element).getParentClass())
							.getEntityId();
				}
				return null;
			}
		}, 0);

		// Parent class
		EditorUtils.addTreeColumn(relationIE, getFromEngineBundle("identifier"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry) {
					return String.valueOf(
							((MultiKey) ((Map.Entry<MultiKey, List<RelationshipUpdate>>) element).getKey().getKey(0)).getKey(1));
				}
				return null;
			}
		}, 1);

		// Mapped by Field
		EditorUtils.addTreeColumn(relationIE, getFromToolBundle("fieldName"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry)
					return ((Map.Entry<MultiKey, List<RelationshipUpdate>>) element).getKey().getKey(2).toString();
				return null;
			}
		}, 2);

		// Mapped by Field
		EditorUtils.addTreeColumn(relationIE, getFromToolBundle("mappedByField"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				if (element instanceof Map.Entry)
					return ((Map.Entry<MultiKey, List<RelationshipUpdate>>) element).getKey().getKey(1).toString();
				return null;
			}
		}, 3);
		// old parent
		EditorUtils.addTreeColumn(relationIE, getFromToolBundle("oldParent"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof RelationshipUpdate<?>)
					return getColumnTextFromMultiKey(((RelationshipUpdate<?>) element).getOldParentMKey());
				return null;
			}
		}, 4);
		// new parent
		EditorUtils.addTreeColumn(relationIE, getFromToolBundle("newParent"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof RelationshipUpdate<?>)
					return getColumnTextFromMultiKey(((RelationshipUpdate<?>) element).getNewParentMKey());
				return null;
			}
		}, 5);

		// Entity type
		EditorUtils.addTreeColumn(relationIE, getFromEngineBundle("entityType"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof RelationshipUpdate<?>)
					return pluginResources.getPluginEntityTree()
							.getEntityMM(((RelationshipUpdate<?>) element).getEntity().getBeanClass()).getEntityId();
				return null;
			}
		}, 6);

		// Entity identifier
		EditorUtils.addTreeColumn(relationIE, getFromEngineBundle("identifier"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof RelationshipUpdate<?>)
					return String.valueOf(((RelationshipUpdate<?>) element).getEntity().getBeanId());
				;
				return null;
			}
		}, 7);

		ITreeContentProvider treeContentProvider = new ITreeContentProvider() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Map.Entry)
					return ((Map.Entry<MultiKey, List<RelationshipUpdate>>) parentElement).getValue().toArray();
				else
					return null;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof Map.Entry<?, ?>)
					return true;
				else
					return false;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return pluginResources.getDataAccess().getDataCache().getRUpdateMap().entrySet().toArray();
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};

		ToolBarManager toolBarManager = EditorUtils.addToolBarManager(relationSection);
		EditorUtils.addClearAction(toolBarManager, relationIE);
		EditorUtils.addRefreshAction(toolBarManager, relationIE);
		toolBarManager.update(true);

		EditorUtils.addTree(relationIE, "grow, height 100:100:n, w n:150:n", treeContentProvider);
		EditorUtils.addElements(relationIE);
	}

	/**
	 * Gets the column text from multi key.
	 * 
	 * @param multiKey
	 *            the multi key
	 * 
	 * @return the column text from multi key
	 */
	private String getColumnTextFromMultiKey(MultiKey multiKey) {
		if (null == multiKey)
			return null;
		return String.valueOf(((MultiKey) multiKey.getKey(0)).getKey(1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#refreshPage()
	 */
	@Override
	public void refreshPage() {
		EditorUtils.addElements(relationIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.tool.cacheManager.ICachePage#clearPage()
	 */
	@Override
	public void clearPage() {
		relationIE.clear();
	}
}
