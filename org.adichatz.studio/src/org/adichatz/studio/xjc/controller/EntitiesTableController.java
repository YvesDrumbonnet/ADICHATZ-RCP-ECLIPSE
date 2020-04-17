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
package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.query.NativeQueryManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.renderer.BasicTableRenderer;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ManifestChanger;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.editor.StudioOutlinePage;
import org.adichatz.studio.xjc.editor.action.ShowOutlineViewAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class FeaturesTableController.
 * 
 * @param <T>
 *            the generic type
 */
public class EntitiesTableController<T> extends TableController<T> {

	private Map<String, PluginEntityWrapper> eligiblesPluginEntities = new HashMap<String, PluginEntityWrapper>();

	private boolean doit = true;

	private ScenarioResources scenarioResources;

	private GenerationScenarioWrapper generationScenario;

	/**
	 * Instantiates a new features table controller.
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
	public EntitiesTableController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		super.createControl();
		final AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
		table.setLayoutData("height 200:200:n, w 300:300:n");
		final MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(table);
		menuManager.setRemoveAllWhenShown(true);
		table.setMenu(menu);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				menuManager.add(new Action(getFromStudioBundle("scenario.model.add.entities"), AdichatzApplication.getInstance()
						.getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "IMG_ADD_ENTITIES.png")) {
					@Override
					public void run() {
						eligiblesPluginEntities.clear();
						final IConfirmContent confirmContent = getConfirmContent(
								getFromStudioBundle("scenario.generation.chooseEntities"),
								getFromStudioBundle("scenario.generation.chooseEntities"));
						final Shell shell = Display.getCurrent().getActiveShell();
						ConfirmFormDialog confirmFormDialog = new ConfirmFormDialog(shell,
								AdichatzApplication.getInstance().getFormToolkit(),
								getFromStudioBundle("scenario.generation.chooseEntities"),
								AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_ENTITY.gif"),
								confirmContent) {
							protected void buttonPressed(int buttonId) {
								if (IDialogConstants.OK_ID == buttonId) {
									confirmContent.okPressed(complementControls);
									okPressed();
								} else if (IDialogConstants.CANCEL_ID == buttonId) {
									cancelPressed();
								}
							}
						};
						confirmFormDialog.open();
					}
				});
				if (!((IStructuredSelection) viewer.getSelection()).isEmpty())
					menuManager.add(new Action(getFromStudioBundle("scenario.delete.record"),
							toolkit.getRegisteredImageDescriptor("IMG_DELETE")) {
						@Override
						@SuppressWarnings("unchecked")
						public void run() {
							Object bean = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
							((NativeContentProvider<T>) viewer.getContentProvider()).getQueryManager().getQueryResult()
									.getQueryResultList().remove(bean);
							refresh();
							lock();
							viewer.setSelection(StructuredSelection.EMPTY);
							scenarioResources.setImpactModel(true);
							scenarioResources.setImpactRcp(true);
						}
					});
				if (!((IStructuredSelection) viewer.getSelection()).isEmpty())
					menuManager.add(new Action(getFromStudioBundle("scenario.refresh.initial.pojo"),
							toolkit.getRegisteredImageDescriptor("IMG_REFRESH")) {
						@Override
						public void run() {
							PluginEntityWrapper pluginEntity = (PluginEntityWrapper) ((IStructuredSelection) viewer.getSelection())
									.getFirstElement();
							String fileName = pluginEntity.getBeanClass().getName().replace(".", "/").concat(".java");
							IFile sourceFile = scenarioResources.getProject().getFile("resources/build/pojo/" + fileName);
							IFile targetFile = scenarioResources.getProject().getFile("src/" + fileName);
							InputStream inputStream;
							try {
								inputStream = sourceFile.getContents();
								targetFile.setContents(inputStream, IResource.FORCE, null);
								inputStream.close();
								targetFile.refreshLocal(IResource.DEPTH_ZERO, null);
							} catch (CoreException | IOException e) {
								logError(e);
							}
							refresh();
							lock();
							viewer.setSelection(StructuredSelection.EMPTY);
							scenarioResources.setImpactModel(true);
							scenarioResources.setImpactRcp(true);
						}
					});
				if (ShowOutlineViewAction.isHidden()) {
					menuManager.add(new Separator());
					menuManager.add(new ShowOutlineViewAction() {
						@Override
						public void run() {
							runAction();
						}
					});
				}
			}
		});
		getViewer().addSelectionChangedListener((event) -> {
			selectOutlinePage();
		});
	}

	public void selectOutlinePage() {
		StudioOutlinePage outlinePage = ((XjcBindingService) getBindingService()).getEditor().getOutlinePage();
		if (null != outlinePage)
			if (viewer.getSelection().isEmpty())
				outlinePage.setSelection(EntitiesTableController.this, (XjcEntity<?>) null, true);
			else {
				outlinePage.setSelection(EntitiesTableController.this, viewer.getSelection());
				PluginEntityWrapper pluginEntity = (PluginEntityWrapper) ((IStructuredSelection) viewer.getSelection())
						.getFirstElement();
				outlinePage.getControl().setEnabled(scenarioResources.getPluginName().equals(pluginEntity.getEntityKeys()[0]));
			}

	}

	private void lock() {
		IEntity<?> entity = getEntity();
		entity.getEntityMM().getDataAccess().lock(getBindingService(), IEntityConstants.MERGE, entity);
	}

	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		((NativeQueryManager<?>) getQueryManager()).initQueryManager(getEntity());
		setTableRenderer(new BasicTableRenderer<T>(this));
		refresh();
	}

	@Override
	public void refresh(int selection, boolean addInput) {
		generationScenario = (GenerationScenarioWrapper) ((NativeQueryManager<?>) queryManager).getParentEntity().getBean();
		super.refresh(selection, addInput);
	}

	private IConfirmContent getConfirmContent(final String sectionText, final String columnText) {
		return new IConfirmContent() {
			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[][grow,fill]"));
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2", "[grow,fill][]"));
				Label title = toolkit.createLabel(composite, getFromStudioBundle("scenario.generation.chooseEntities"));
				title.setLayoutData("span 2");
				title.setFont(JFaceResources.getBannerFont());

				final CheckboxTableViewer tableViewer = CheckboxTableViewer.newCheckList(composite,
						SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				tableViewer.getTable().setLayoutData("hmin 200, wmin 500");
				tableViewer.getTable().setHeaderVisible(true);
				tableViewer.addCheckStateListener(new ICheckStateListener() {
					@Override
					public void checkStateChanged(CheckStateChangedEvent event) {
						if (doit) {
							PluginEntityWrapper pluginEntity = (PluginEntityWrapper) event.getElement();
							if (event.getChecked())
								eligiblesPluginEntities.put(pluginEntity.getEntityURI(), pluginEntity);
							else
								eligiblesPluginEntities.remove(pluginEntity.getEntityURI());
						}
					}
				});

				final List<TableViewerColumn> columns = new ArrayList<TableViewerColumn>();
				columns.add(newColumn(tableViewer, "entityURI", SWT.LEFT, new ColumnLabelProvider() {
					@Override
					public String getText(final Object element) {
						return ((PluginEntityWrapper) element).getEntityURI();
					}
				}));
				tableViewer.setContentProvider(new ArrayContentProvider());

				Composite buttonComposite = toolkit.createComposite(composite);
				buttonComposite.setLayout(new MigLayout("wrap"));
				buttonComposite.setLayoutData("top");
				toolkit.createLabel(buttonComposite, getFromStudioBundle("scenario.generation.choose.plugin"));

				String pluginName = scenarioResources.getPluginName();
				for (final String bundleName : getASPlugins(pluginName)) {
					Button button = toolkit.createButton(buttonComposite, bundleName, SWT.RADIO);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (((Button) e.widget).getSelection()) {
								doit = false;
								Map<String, PluginEntityWrapper> beanMap = new HashMap<String, PluginEntityWrapper>();
								// Add current pluginEntities in beanMap.
								for (Object element : ((NativeContentProvider<?>) getViewer().getContentProvider())
										.getQueryManager().getQueryResult().getQueryResultList()) {
									PluginEntityWrapper pluginEntity = (PluginEntityWrapper) element;
									beanMap.put(pluginEntity.getEntityURI(), pluginEntity);
								}
								List<PluginEntityWrapper> input = new ArrayList<PluginEntityWrapper>();

								ScenarioResources scenarioResources = ScenarioResources.getInstance(bundleName, null);
								scenarioResources.getPluginResources().loadPluginEntities();
								for (Map.Entry<String, PluginEntity<?>> entry : scenarioResources.getPluginResources()
										.getPluginEntityTree().getPluginEntityURIMap().entrySet()) {
									if (!beanMap.containsKey(entry.getKey())) {
										PluginEntityWrapper pew = new PluginEntityWrapper(scenarioResources);
										pew.setEntityURI(entry.getKey());
										pew.setPojoRewriters(null);
										input.add(pew);
									}

								}
								Collections.sort(input, new Comparator<PluginEntityWrapper>() {
									@Override
									public int compare(PluginEntityWrapper o1, PluginEntityWrapper o2) {
										return o1.getEntityURI().compareTo(o2.getEntityURI());
									}
								});

								tableViewer.setInput(input);
								for (TableItem tableItem : tableViewer.getTable().getItems()) {
									if (eligiblesPluginEntities
											.containsKey(((PluginEntityWrapper) tableItem.getData()).getEntityURI()))
										tableItem.setChecked(true);
								}
								for (TableViewerColumn column : columns)
									column.getColumn().pack();
								doit = true;
							}
						}
					});
					if (bundleName.equals(pluginName)) {
						button.setFont(JFaceResources.getBannerFont());
						button.setSelection(true);
						button.notifyListeners(SWT.Selection, null);
					}
				}
				createSelectButtons(toolkit, composite, tableViewer);
			}

			private TableViewerColumn newColumn(CheckboxTableViewer tableViewer, String columnName, int style,
					ColumnLabelProvider columnLabelProvider) {
				final TableViewerColumn column = new TableViewerColumn(tableViewer, style);
				column.getColumn().setText(AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE)
						.getMessage("pluginEntity", columnName));
				column.setLabelProvider(columnLabelProvider);
				return column;
			}

			private void createSelectButtons(FormToolkit toolkit, Composite patternComposite,
					final CheckboxTableViewer tableViewer) {
				Composite selectComposite = toolkit.createComposite(patternComposite);
				selectComposite.setLayout(new MigLayout("ins 0"));
				selectComposite.setLayoutData("align right");
				Button selectButton = toolkit.createButton(selectComposite, getFromStudioBundle("confirmContent.selectAll"),
						SWT.PUSH);
				selectButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						selectAll(tableViewer.getTable(), true);
					}
				});
				selectButton.setLayoutData("sg selectButtons, gapx 20!");
				selectButton.setFocus();

				Button deselectButton = toolkit.createButton(selectComposite, getFromStudioBundle("confirmContent.deselectAll"),
						SWT.PUSH);
				deselectButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						selectAll(tableViewer.getTable(), false);
					}
				});
				deselectButton.setLayoutData("sg selectButtons, gapx 20!");
			}

			private void selectAll(Table table, boolean state) {
				TableItem[] children = table.getItems();
				for (int i = 0; i < children.length; i++) {
					TableItem item = children[i];
					if (item.getData() != null) {
						if (item.getChecked() != state) {
							item.setChecked(state);
							Event event = new Event();
							event.item = item;
							event.detail = SWT.CHECK;
							table.notifyListeners(SWT.Selection, event);
						}
					}
				}
			}

			/**
			 * Ok pressed.
			 * 
			 * @param complementControls
			 *            the complement controls
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
				List<PluginEntityWrapper> addedPluginEntities = new ArrayList<PluginEntityWrapper>();
				ScenarioResources modelSR = null;
				List<PluginEntityWrapper> pluginEntities = new ArrayList<PluginEntityWrapper>(eligiblesPluginEntities.values());
				Collections.sort(pluginEntities, new Comparator<PluginEntityWrapper>() {
					@Override
					public int compare(PluginEntityWrapper o1, PluginEntityWrapper o2) {
						return o1.getEntityURI().compareTo(o2.getEntityURI());
					}
				});
				for (PluginEntityWrapper pluginEntity : pluginEntities) {
					modelSR = ScenarioResources.getInstance(pluginEntity.getEntityKeys()[0], null);
					int part = 0;
					if (modelSR.equals(scenarioResources))
						part = IPluginEntityScenario.MODEL_PART;
					if (null != generationScenario.getRcpPart())
						part = part | IPluginEntityScenario.RCP_PART;

					// Populates pluginEntity with pluginEntityScenario of entityPart
					// Entities and Queries are populated onlyg if scenarioResources has Model part
					scenarioResources.getPluginEntityScenario().populatePluginEntity(modelSR, pluginEntity, part, null);
					addedPluginEntities.add(pluginEntity);
				}
				if (null != modelSR) {
					scenarioResources.reinitManifestChanger();
					ManifestChanger manifestChanger = scenarioResources.getManifestChanger();
					try {
						if (!scenarioResources.getPluginName().equals(modelSR.getPluginName()) // bug 0.8.8-16
								&& !manifestChanger.hasValue(Constants.REQUIRE_BUNDLE, modelSR.getPluginName())) {
							manifestChanger.addAttributeElement(Constants.REQUIRE_BUNDLE, modelSR.getPluginName());
							manifestChanger.write();
							scenarioResources.reinitAccessibleAdiPlugins();
						}
					} catch (IOException | CoreException e) {
						logError(e);
					}
				}
				// Disjoin populatedPluginEntity and adding because errors may occur during first step.
				for (PluginEntityWrapper pluginEntity : addedPluginEntities) {
					generationScenario.addPluginEntity(pluginEntity);
					scenarioResources.setImpactModel(true);
					scenarioResources.setImpactRcp(true);
				}
				lock();
				refresh();
				if (!eligiblesPluginEntities.isEmpty()) {
					StructuredSelection selection = new StructuredSelection(eligiblesPluginEntities.values().iterator().next());
					viewer.setSelection(selection);
				}
			}
		};
	}

	private String[] getASPlugins(final String pluginName) {
		List<String> asPlugins = new ArrayList<String>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (project.isOpen() && AdiPropertyTester.hasModelAspect(project))
				asPlugins.add(project.getName());
		}

		for (Bundle bundle : StudioRcpPlugin.getDefault().getBundle().getBundleContext().getBundles()) {
			if (AdiPropertyTester.hasModelAspect(bundle) && !asPlugins.contains(bundle.getSymbolicName()))
				asPlugins.add(bundle.getSymbolicName());
		}
		Collections.sort(asPlugins, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (pluginName.equals(o1))
					return -1;
				return o1.compareToIgnoreCase(o2);
			}
		});
		return asPlugins.toArray(new String[asPlugins.size()]);
	}
}
