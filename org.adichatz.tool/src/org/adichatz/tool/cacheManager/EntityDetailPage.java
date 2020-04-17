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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.action.AAction;
import org.adichatz.engine.cache.LazyFetchManager;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.IControlController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.PartCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.EntityBindingDispatcher;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.jpa.data.JPAEntity;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityDetailPage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class EntityDetailPage implements IDetailsPage {
	private static String CONTROLLERS = "controllers";

	private static String ERROR_MESSAGES = "errorsMessages";

	/** The lazy fetch tree viewer. */
	private TreeViewer lazyFetchTreeViewer;

	/** The binding service tree viewer. */
	private TreeViewer bindingServiceTreeViewer;

	/** The managed form. */
	private IManagedForm managedForm;

	/** The input entity. */
	private JPAEntity<?> inputEntity;

	/** The table controller ie. */
	private ICacheEditorTable tabularControllerIE;

	/** The relation ie. */
	private ICacheEditorTable relationIE;

	/** The lazy fetches IE. */
	private ICacheEditorTree lazyFetchesIE;

	/** The binding services IE. */
	private ICacheEditorTree bindingServicesIE;

	/** The editor ie. */
	private ICacheEditorTable editorIE;

	/** The listener ie. */
	private ICacheEditorTable listenerIE;

	/** The column label providers. */
	private List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

	/** The plugin resources. */
	private AdiPluginResources pluginResources;

	private ABindingService currentBindingService;

	private EntityBindingDispatcher currentBindingDispatcher;

	/**
	 * Instantiates a new entity detail page.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 */
	public EntityDetailPage(AdiPluginResources pluginResources, ScenarioResources scenarioResources) {
		this.pluginResources = pluginResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	public void initialize(IManagedForm managedForm) {
		this.managedForm = managedForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		ScrolledForm scrolledForm = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		parent.setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));
		parent.setLayoutData("");
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "[grow,fill]", "[grow,fill]"));

		CTabFolder cTabFolder = new CTabFolder(parent, SWT.FLAT | SWT.TOP);
		toolkit.adapt(cTabFolder, true, true);
		Color selectedColor = toolkit.getColors().getColor(IFormColors.SEPARATOR);
		cTabFolder.setSelectionBackground(new Color[] { selectedColor, toolkit.getColors().getBackground() }, new int[] { 50 });
		toolkit.paintBordersFor(cTabFolder);

		createTabularControllers(toolkit, cTabFolder);
		createRelationshipUpdates(toolkit, cTabFolder);
		createLazyFetches(toolkit, cTabFolder);
		createBindingServices(toolkit, cTabFolder);
		createEditors(toolkit, cTabFolder);
		createListeners(toolkit, cTabFolder);

		// --------------------------------------
		cTabFolder.setSelection(cTabFolder.getItem(0));

	}

	/**
	 * Creates the table controllers.
	 * 
	 * @param toolkit
	 *            the toolkit
	 * @param cTabFolder
	 *            the c tab folder
	 */
	private void createTabularControllers(FormToolkit toolkit, CTabFolder cTabFolder) {
		//
		// Item: List of tabularController for the entity
		//
		CTabItem tableCTabItem = new CTabItem(cTabFolder, SWT.NONE);
		tableCTabItem.setText(getFromToolBundle("table"));
		Composite itemComposite = new Composite(cTabFolder, SWT.NONE);
		itemComposite.setLayout(new FillLayout(SWT.VERTICAL));
		tableCTabItem.setControl(itemComposite);

		final Section tabularControllerSection = EditorUtils.addTableSection(itemComposite, toolkit,
				getFromToolBundle("cachedTabularControllers"));

		final TableViewer tabularTableViewer = new TableViewer((Composite) tabularControllerSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TableViewerColumn> tableTableViewerColumns = new ArrayList<TableViewerColumn>();
		final List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		tabularControllerIE = new ICacheEditorTable() {
			@Override
			public Composite getClient() {
				return (Composite) tabularControllerSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return pluginResources.getDataAccess().getDataCache().getTabularControllerByCKey().get(inputEntity.getCacheKey());
			}

			@Override
			public TableViewer getTableViewer() {
				return tabularTableViewer;
			}

			@Override
			public List<TableViewerColumn> getTableViewerColumns() {
				return tableTableViewerColumns;
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

			@Override
			public void clear() {
			}
		};

		EditorUtils.addTable(tabularControllerIE, "grow, height 100:100:n, w n:150:n");
		// Editor column
		EditorUtils.addTableColumn(tabularControllerIE, getFromToolBundle("editor"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return (((ATabularController<?>) element).getBindingService().getTitle());
			}

		}, 0);
		// Editor page
		EditorUtils.addTableColumn(tabularControllerIE, getFromToolBundle("page"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				AWidgetController formPageController = (AWidgetController) EditorUtils.getSpecificController(
						(ATabularController<?>) element, FormPageController.class, ContainerController.class);
				return formPageController.getRegisterId();
			}

		}, 1);
		// Editor section
		EditorUtils.addTableColumn(tabularControllerIE, getFromToolBundle("section"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return EditorUtils.getContainerText(element);
			}

		}, 2);
	}

	/**
	 * Creates the relationship updates.
	 * 
	 * @param toolkit
	 *            the toolkit
	 * @param cTabFolder
	 *            the c tab folder
	 */
	private void createRelationshipUpdates(FormToolkit toolkit, CTabFolder cTabFolder) {
		//
		// Item: List of relationship updates for the entity
		//
		CTabItem relationCTabItem = new CTabItem(cTabFolder, SWT.NONE);
		relationCTabItem.setText(getFromToolBundle("relationshipUpdate"));
		Composite itemComposite = new Composite(cTabFolder, SWT.NONE);
		itemComposite.setLayout(new FillLayout(SWT.VERTICAL));
		relationCTabItem.setControl(itemComposite);

		final Section relationSection = EditorUtils.addTableSection(itemComposite, toolkit,
				getFromToolBundle("cachedRelationshipUpdates"));

		final TableViewer relationTableViewer = new TableViewer((Composite) relationSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TableViewerColumn> relationTableViewerColumns = new ArrayList<TableViewerColumn>();
		final List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		relationIE = new ICacheEditorTable() {
			@Override
			public Composite getClient() {
				return (Composite) relationSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return inputEntity.getRelationshipUpdateMap().entrySet();
			}

			@Override
			public TableViewer getTableViewer() {
				return relationTableViewer;
			}

			@Override
			public List<TableViewerColumn> getTableViewerColumns() {
				return relationTableViewerColumns;
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

			@Override
			public void clear() {
			}
		};

		EditorUtils.addTable(relationIE, "grow, height 100:100:n, w n:150:n");
		// Mapped by Field
		EditorUtils.addTableColumn(relationIE, getFromToolBundle("mappedByField"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				return String.valueOf(((Map.Entry<MultiKey, RelationshipUpdate>) element).getKey().getString(0));
			}

		}, 0);
		// Mapped by Field
		EditorUtils.addTableColumn(relationIE, getFromToolBundle("fieldName"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				return String.valueOf(((Map.Entry<MultiKey, RelationshipUpdate>) element).getKey().getKey(1));
			}

		}, 1);
		// Parent class
		EditorUtils.addTableColumn(relationIE, getFromEngineBundle("entityType"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				Class<?> beanClass = ((Map.Entry<String, RelationshipUpdate>) element).getValue().getParentClass();
				if (null != beanClass)
					return pluginResources.getPluginEntityTree().getEntityMM(beanClass).getEntityId();
				return null;
			}

		}, 2);
		// old parent
		EditorUtils.addTableColumn(relationIE, getFromToolBundle("oldParent"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				return getColumnTextFromMultiKey(((Map.Entry<String, RelationshipUpdate>) element).getValue().getOldParentMKey());
			}

		}, 3);
		// new parent
		EditorUtils.addTableColumn(relationIE, getFromToolBundle("newParent"), new ColumnLabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(final Object element) {
				return getColumnTextFromMultiKey(((Map.Entry<String, RelationshipUpdate>) element).getValue().getNewParentMKey());
			}

		}, 4);
	}

	/**
	 * Creates the editors.
	 * 
	 * @param toolkit
	 *            the toolkit
	 * @param cTabFolder
	 *            the c tab folder
	 */
	private void createEditors(FormToolkit toolkit, CTabFolder cTabFolder) {
		//
		// Item: List of editors on the entity
		//
		CTabItem editorCTabItem = new CTabItem(cTabFolder, SWT.NONE);
		editorCTabItem.setText(getFromToolBundle("editor"));
		Composite itemComposite = new Composite(cTabFolder, SWT.NONE);
		itemComposite.setLayout(new FillLayout(SWT.VERTICAL));
		editorCTabItem.setControl(itemComposite);

		final Section editorSection = EditorUtils.addTableSection(itemComposite, toolkit, getFromToolBundle("cachedEditors"));

		final TableViewer editorTableViewer = new TableViewer((Composite) editorSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TableViewerColumn> editorTableViewerColumns = new ArrayList<TableViewerColumn>();
		final List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		editorIE = new ICacheEditorTable() {
			@Override
			public Composite getClient() {
				return (Composite) editorSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return inputEntity.getBindingServices();
			}

			@Override
			public TableViewer getTableViewer() {
				return editorTableViewer;
			}

			@Override
			public List<TableViewerColumn> getTableViewerColumns() {
				return editorTableViewerColumns;
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

			@Override
			public void clear() {
			}
		};

		EditorUtils.addTable(editorIE, "grow, height 100:100:n, w n:150:n");
		// Editor type
		EditorUtils.addTableColumn(editorIE, getFromToolBundle("listenerType"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((ABindingService) element).getTitle();
			}

		}, 0);
	}

	/**
	 * Creates the lazy fetches.
	 * 
	 * @param toolkit
	 *            the toolkit
	 * @param cTabFolder
	 *            the c tab folder
	 */
	private void createLazyFetches(FormToolkit toolkit, CTabFolder cTabFolder) {
		Composite itemComposite;
		//
		// Item: List of lazy fetches for the entity
		//
		CTabItem lazyFetchCTabItem = new CTabItem(cTabFolder, SWT.NONE);
		lazyFetchCTabItem.setText(getFromToolBundle("lazyFetch"));
		itemComposite = new Composite(cTabFolder, SWT.NONE);
		itemComposite.setLayout(new FillLayout(SWT.VERTICAL));
		lazyFetchCTabItem.setControl(itemComposite);

		final Section lazyFetchesSection = EditorUtils.addTableSection(itemComposite, toolkit, getFromToolBundle("lazyFetch"));

		final List<TreeViewerColumn> treeViewerColumns = new ArrayList<TreeViewerColumn>();
		lazyFetchesIE = new ICacheEditorTree() {

			@Override
			public Composite getClient() {
				return (Composite) lazyFetchesSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return inputEntity.getLazyFetchManager().getBindingServiceMap().entrySet();
			}

			@Override
			public TreeViewer getTreeViewer() {
				return lazyFetchTreeViewer;
			}

			@Override
			public List<TreeViewerColumn> getTreeViewerColumns() {
				return treeViewerColumns;
			}

			@Override
			public void clear() {
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}
		};

		lazyFetchTreeViewer = new TreeViewer(lazyFetchesIE.getClient(), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		EditorUtils.addTreeColumn(lazyFetchesIE, getFromToolBundle("editor"), new ColumnLabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				if (element instanceof Map.Entry) {
					@SuppressWarnings("rawtypes")
					ABindingService bindingService = ((Map.Entry<ABindingService, LazyFetchManager>) element).getKey();
					return getFromToolBundle("editor") + ": " + bindingService.getTitle();
				} else if (element instanceof LocalLazyNode) {
					return null == ((LocalLazyNode) element).getLazyFetchMember() ? ""
							: ((LocalLazyNode) element).getLazyFetchMember();
				}
				return null;
			}
		}, 0);

		EditorUtils.addTreeColumn(lazyFetchesIE, getFromToolBundle("method"), new ColumnLabelProvider() {
			@Override
			public Image getImage(Object element) {
				if (element instanceof LocalLazyNode) {
					if (((LocalLazyNode) element).getLazyField() instanceof EntitySet<?>)
						return AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_QUERY");
					if (((LocalLazyNode) element).getLazyField() instanceof RefField<?>)
						return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_ENTITY.png");
					return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_ALL_OBJECT");
				}
				return null;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof LocalLazyNode) {
					return ((LocalLazyNode) element).getLazyFetchMember();
				}
				return null;
			}
		}, 1);
		EditorUtils.addTreeColumn(lazyFetchesIE, getFromToolBundle("value"), new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof LocalLazyNode) {
					if (((LocalLazyNode) element).getLazyField() instanceof RefField<?>) {
						LocalLazyNode node = ((LocalLazyNode) element);
						@SuppressWarnings("rawtypes")
						AEntityMetaModel entityMM = node.getEntityMM();
						return null == node.getBean() ? "" : entityMM.getEntityId() + " (" + entityMM.getId(node.getBean()) + ")";
					} else
						return "???";
				}
				return null;
			}
		}, 2);
		ITreeContentProvider treeContentProvider = new ITreeContentProvider() {

			@SuppressWarnings("unchecked")
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof LocalLazyNode)
					return ((LocalLazyNode) parentElement).getChildrenMap().values().toArray();
				else if (parentElement instanceof Map.Entry)
					return ((Map.Entry<PartCore, Set<LocalLazyNode>>) parentElement).getValue().toArray();
				else
					return null;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof Map.Entry)
					return 0 < ((Map.Entry<PartCore, Set<LocalLazyNode>>) element).getValue().size();
				else if (element instanceof LocalLazyNode)
					return 0 < ((LocalLazyNode) element).getChildrenMap().size();
				else
					return false;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (null != lazyFetchesIE.getElements())
					return lazyFetchesIE.getElements().toArray();
				return new Object[] {};
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};

		EditorUtils.addTree(lazyFetchesIE, "grow, height 100:100:n, w n:150:n", treeContentProvider);
	}

	/**
	 * Creates the binding services.
	 * 
	 * @param toolkit
	 *            the toolkit
	 * @param cTabFolder
	 *            the c tab folder
	 */
	private void createBindingServices(FormToolkit toolkit, CTabFolder cTabFolder) {
		Composite itemComposite;
		//
		// Item: List of binding services for the entity
		//
		CTabItem bindingCTabItem = new CTabItem(cTabFolder, SWT.NONE);
		bindingCTabItem.setText(getFromToolBundle("bindingService"));
		itemComposite = new Composite(cTabFolder, SWT.NONE);
		itemComposite.setLayout(new FillLayout(SWT.VERTICAL));
		bindingCTabItem.setControl(itemComposite);

		final Section bindingServicesSection = EditorUtils.addTableSection(itemComposite, toolkit,
				getFromToolBundle("bindingService"));

		final List<TreeViewerColumn> treeViewerColumns = new ArrayList<TreeViewerColumn>();
		bindingServicesIE = new ICacheEditorTree() {

			@Override
			public Composite getClient() {
				return (Composite) bindingServicesSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return inputEntity.getBindingServices();
			}

			@Override
			public TreeViewer getTreeViewer() {
				return bindingServiceTreeViewer;
			}

			@Override
			public List<TreeViewerColumn> getTreeViewerColumns() {
				return treeViewerColumns;
			}

			@Override
			public void clear() {
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}
		};

		bindingServiceTreeViewer = new TreeViewer(bindingServicesIE.getClient(),
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		EditorUtils.addTreeColumn(bindingServicesIE, getFromToolBundle("editor"), new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ABindingService) {
					return getFromToolBundle("editor") + ": " + ((ABindingService) element).getTitle();
				} else if (element instanceof String) {
					return getFromToolBundle((String) element);
				} else if (element instanceof EntityInjection) {
					return getLabel(((EntityInjection) element).getEntityController());
				} else if (element instanceof IBoundedController) {
					if (element instanceof BoundedPart)
						return ((BoundedPart) element).getTitle();
					else
						return getLabel((IControlController) element);
				} else if (element instanceof ErrorMessage) {
					ErrorMessage errorMessage = (ErrorMessage) element;
					return errorMessage.getMessageStr() + " [" + getLabel(errorMessage.getValidator().getTriggeringController())
							+ "]";
				}
				return null;
			}
		}, 0);

		EditorUtils.addTreeColumn(bindingServicesIE, getFromToolBundle("value"), new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof LocalLazyNode) {
					if (((LocalLazyNode) element).getLazyField() instanceof RefField<?>) {
						LocalLazyNode node = ((LocalLazyNode) element);
						@SuppressWarnings("rawtypes")
						AEntityMetaModel entityMM = node.getEntityMM();
						return null == node.getBean() ? "" : entityMM.getEntityId() + " (" + entityMM.getId(node.getBean()) + ")";
					} else
						return "???";
				}
				return null;
			}
		}, 2);
		ITreeContentProvider treeContentProvider = new ITreeContentProvider() {

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof ABindingService) {
					currentBindingService = (ABindingService) parentElement;
					if (null != currentBindingService.getErrorMessageMap() && !currentBindingService.getErrorMessageMap().isEmpty())
						return new String[] { CONTROLLERS, ERROR_MESSAGES };
					else
						return new String[] { CONTROLLERS };
				} else if (parentElement instanceof String) {
					if (CONTROLLERS.equals(parentElement)) {
						currentBindingDispatcher = currentBindingService.getBindingDispatcher(inputEntity);
						return currentBindingDispatcher.getEntityInjections().toArray();
					} else {
						return currentBindingService.getErrorMessageMap().get(inputEntity).toArray();
					}
				} else if (parentElement instanceof EntityInjection) {
					return ((EntityInjection) parentElement).getBoundedControllers().toArray();
				}
				return null;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				return element instanceof ABindingService || element instanceof String;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (null != bindingServicesIE.getElements())
					return bindingServicesIE.getElements().toArray();
				return new Object[] {};
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};

		EditorUtils.addTree(bindingServicesIE, "grow, height 100:100:n, w n:150:n", treeContentProvider);
	}

	/**
	 * Creates the listeners.
	 * 
	 * @param toolkit
	 *            the toolkit
	 * @param cTabFolder
	 *            the c tab folder
	 */
	private void createListeners(FormToolkit toolkit, CTabFolder cTabFolder) {
		//
		// Item: List of listeners on the entity
		//
		CTabItem listenerCTabItem = new CTabItem(cTabFolder, SWT.NONE);
		listenerCTabItem.setText(getFromToolBundle("listener"));
		Composite itemComposite = new Composite(cTabFolder, SWT.NONE);
		itemComposite.setLayout(new FillLayout(SWT.VERTICAL));
		listenerCTabItem.setControl(itemComposite);

		final Section listenerSection = EditorUtils.addTableSection(itemComposite, toolkit, getFromToolBundle("cachedListeners"));

		final TableViewer listenerTableViewer = new TableViewer((Composite) listenerSection.getClient(),
				SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		final List<TableViewerColumn> listenerTableViewerColumns = new ArrayList<TableViewerColumn>();
		final List<ColumnLabelProvider> columnLabelProviders = new ArrayList<ColumnLabelProvider>();

		listenerIE = new ICacheEditorTable() {
			@Override
			public Composite getClient() {
				return (Composite) listenerSection.getClient();
			}

			@Override
			public Collection<?> getElements() {
				return inputEntity.getListeners();
			}

			@Override
			public TableViewer getTableViewer() {
				return listenerTableViewer;
			}

			@Override
			public List<TableViewerColumn> getTableViewerColumns() {
				return listenerTableViewerColumns;
			}

			@Override
			public List<ColumnLabelProvider> getColumnLabelProviders() {
				return columnLabelProviders;
			}

			@Override
			public void clear() {
			}
		};

		EditorUtils.addTable(listenerIE, "grow, height 100:100:n, w n:150:n");
		// Listener type
		EditorUtils.addTableColumn(listenerIE, getFromToolBundle("listenerType"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				switch (((AListener) element).getEventType()) {
				case IEventType.LOCK_ENTITY:
					return getFromToolBundle("changeLock");
				case IEventType.CHANGE_STATUS:
					return getFromToolBundle("changeStatus");
				case IEventType.BEFORE_PROPERTY_CHANGE:
					return getFromToolBundle("beforePropertyChange");
				case IEventType.WHEN_PROPERTY_CHANGE:
					return getFromToolBundle("whenPropertyChange");
				case IEventType.AFTER_PROPERTY_CHANGE:
					return getFromToolBundle("beforePropertyChange");
				case IEventType.PRE_SAVE:
					return getFromToolBundle("preSave");
				case IEventType.POST_SAVE:
					return getFromToolBundle("postSave");
				case IEventType.PRE_REFRESH:
					return getFromToolBundle("preRefresh");
				case IEventType.POST_REFRESH:
					return getFromToolBundle("postRefresh");
				case IEventType.BEFORE_ENTITY_INJECTION:
					return getFromToolBundle("injectEntity");
				case IEventType.POST_MESSAGE:
					return getFromToolBundle("postMessage");
				}
				return "Unknown event type:" + ((AListener) element).eventType;
			}
		}, 0);
		// Parent class
		EditorUtils.addTableColumn(listenerIE, getFromToolBundle("enclosingClass"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				Class<?> enclosingClass = ((AListener) element).getClass().getEnclosingClass();
				if (null == enclosingClass)
					return element.getClass().getName();
				return enclosingClass.getName();
			}

		}, 1);
		// Parent class
		EditorUtils.addTableColumn(listenerIE, getFromToolBundle("wording"), new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				try {
					// if (element instanceof LockEntityManagerListener) {
					// ACollectionController collectionController = ((LockEntityManagerListener) element)
					// .getCollectionController();
					// return collectionController.getRegisterId() + "("
					// + collectionController.getGenCode().getContext().getBindingService().getTitle() + ")";
					// }
					Field field = element.getClass().getDeclaredField("this$0");
					field.setAccessible(true);
					Object enclosingInstance = field.get(element);
					if (enclosingInstance instanceof PartCore)
						return ((PartCore) enclosingInstance).getController().getBindingService().getTitle();
					// else if (enclosingInstance instanceof ASectionCore) {
					// String className = ((ASectionCore) enclosingInstance).getClass().getSimpleName();
					// return className.substring(className.indexOf(scenarioResources.getContinuationClassStr()) + 1);
					// }
					// else if (enclosingInstance instanceof BindingDispatcher)
					// return ((BindingDispatcher) enclosingInstance).getBindingService().getTitle();
					else if (enclosingInstance instanceof AAction)
						return ((AAction) enclosingInstance).getDescription().substring(0, 40);
					return String.valueOf(enclosingInstance);
				} catch (Exception e) {
				}
				return "";
			}

		}, 1);
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

	/**
	 * Update.
	 */
	private void update() {
		EditorUtils.addElements(tabularControllerIE);
		EditorUtils.addElements(relationIE);
		EditorUtils.addElements(lazyFetchesIE);
		EditorUtils.addElements(bindingServicesIE);
		EditorUtils.addElements(editorIE);
		EditorUtils.addElements(listenerIE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#inputChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() == 1) {
			inputEntity = (JPAEntity<?>) ssel.getFirstElement();
		} else
			inputEntity = null;
		update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#commit()
	 */
	public void commit(boolean onSave) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#setFocus()
	 */
	public void setFocus() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#dispose()
	 */
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#isDirty()
	 */
	public boolean isDirty() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#isStale()
	 */
	public boolean isStale() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#refresh()
	 */
	public void refresh() {
		update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#setFormInput(java.lang.Object)
	 */
	public boolean setFormInput(Object input) {
		return false;
	}

	private String getLabel(IControlController controller) {
		StringBuffer labelSB = new StringBuffer(controller.getRegisterId());
		Class<?> controllerClass = controller.getClass();
		String className = null;
		while (null != controllerClass) {
			if (controllerClass.getName().startsWith("org.adichatz.engine.controller")) {
				className = controllerClass.getSimpleName();
				break;
			}
			controllerClass = controllerClass.getSuperclass();
		}
		labelSB.append(" - ").append(null == className ? controller.getClass() : className);

		return labelSB.toString();
	}
}
