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
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.viewer.RootElement;
import org.adichatz.generator.PluginEntityTreeGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.tools.GeneratorErrorException;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.GenerationUnitWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RcpPartType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ActivatorGenerator;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.gencode.editor.XjcTreeManager;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.editor.AStudioTreeManager;
import org.adichatz.studio.xjc.editor.StudioOutlinePage;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;
import org.adichatz.studio.xjc.editor.action.GenerateScenarioActionController;
import org.adichatz.studio.xjc.editor.action.ShowOutlineViewAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcTreeController.
 *
 * @author Yves Drumbonnet
 */
public class GenerationTreeController extends ScenarioTreeController {

	/** The expanded UR is. */
	private List<String> expandedURIs = new ArrayList<String>(); // element impacted by last changes

	/** The image manager. */
	private ImageManager imageManager;

	/**
	 * Instantiates a new xjc tree controller.
	 *
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public GenerationTreeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.controller.ScenarioTreeController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		imageManager = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getImageManager();
		viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return !(((XjcTreeElement) element).getElement() instanceof RcpPartType)
						&& !(((XjcTreeElement) element).getElement() instanceof ModelPartType);
			}
		});

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(org.eclipse.jface.viewers.Viewer viewer, Object e1, Object e2) {
				Object bean1 = ((XjcTreeElement) e1).getEntity().getBean();
				Object bean2 = ((XjcTreeElement) e2).getEntity().getBean();
				if (bean1 instanceof GenerationUnitType) {
					if (bean2 instanceof GenerationUnitType) {
						GenerationUnitType generationUnit1 = (GenerationUnitType) bean1;
						GenerationUnitType generationUnit2 = (GenerationUnitType) bean2;
						if (null == generationUnit1.getType() || null == generationUnit2.getType())
							return null == generationUnit2.getType() ? 1 : -1;
						return ((GenerationUnitType) bean1).getType().toString()
								.compareTo(((GenerationUnitType) bean2).getType().toString());
					}
				} else
					return 1;
				if (bean1 instanceof PluginEntityType)
					if (bean2 instanceof PluginEntityType) {
						String uri1 = ((PluginEntityType) bean1).getEntityURI();
						String uri2 = ((PluginEntityType) bean2).getEntityURI();
						uri1 = null == uri1 ? "" : uri1;
						uri2 = null == uri2 ? "" : uri2;
						return (uri1.compareTo(uri2));
					} else
						return 1;

				return 2;
			};
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.controller.ScenarioTreeController#createViewer()
	 */
	protected ColumnViewer createViewer() {
		setShowRoot(true);
		return new ContainerCheckedTreeViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	/**
	 * Adds the editor menu.
	 */
	public void addEditorMenu() {
		final AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		final MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(((ContainerCheckedTreeViewer) viewer).getTree());
		menuManager.setRemoveAllWhenShown(true);
		((ContainerCheckedTreeViewer) viewer).getTree().setMenu(menu);
		menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager mgr) {
				if (!((IStructuredSelection) viewer.getSelection()).isEmpty()) {
					final XjcTreeElement treeElement = (XjcTreeElement) ((IStructuredSelection) viewer.getSelection())
							.getFirstElement();
					final IEntity<?> entity = treeElement.getEntity();
					final Object bean = entity.getBean();
					final ScenarioResources scenarioResources = ((XjcBindingService) getBindingService()).getEditor()
							.getScenarioResources();
					if (bean instanceof GenerationScenarioWrapper || bean instanceof PluginEntityWrapper) {
						boolean hasModel = scenarioResources.hasModelPart();
						boolean hasRCP = scenarioResources.hasRcpPart();
						Set<GenerationEnum> eligibleGenerations = getEligibleGenerations(hasModel, hasRCP);
						if (bean instanceof GenerationScenarioType) {
							computeEligibles(eligibleGenerations, ((GenerationScenarioType) bean).getGenerationUnit());
							if (scenarioResources.isApplication())
								eligibleGenerations.add(GenerationEnum.NAVIGATOR);
						} else
							computeEligibles(eligibleGenerations, ((PluginEntityType) bean).getGenerationUnit());

						if (!eligibleGenerations.isEmpty()) {
							MenuManager addGenerationUnitMM = new MenuManager(getFromStudioBundle("scenario.add.generationUnits"),
									toolkit.getRegisteredImageDescriptor("IMG_CREATE"), null);
							menuManager.add(addGenerationUnitMM);
							for (final GenerationEnum generationEnum : eligibleGenerations) {
								menuManager.add(new Action(getFromStudioBundle("scenario.add.generationUnit", generationEnum),
										imageManager.getImageDescriptor("xjc/generationUnit.png")) {
									@Override
									public void run() {
										GenerationUnitWrapper generationUnit = new GenerationUnitWrapper(generationEnum);
										Method getMethod = ReflectionTools.getMethod(bean.getClass(), "getGenerationUnit");
										if (bean instanceof GenerationScenarioType) {
											((GenerationScenarioType) bean).getGenerationUnit().add(generationUnit);
										} else {
											PluginEntityType pluginEntity = (PluginEntityType) bean;
											pluginEntity.getGenerationUnit().add(generationUnit);
											IPluginEntityScenario pluginEnityScenario = scenarioResources.getPluginEntityScenario();
											generationUnit.setAdiResourceURI(EngineTools.getAdiResourceURI(
													pluginEnityScenario.getSubPackage(pluginEntity, generationEnum),
													pluginEnityScenario.getTreeId(pluginEntity, generationEnum)));
										}
										TreePath currentPath = ((ITreeSelection) viewer.getSelection()).getPaths()[0];
										XjcTreeElement childElement = getTreeManager().newTreeElement(treeElement, getMethod,
												generationUnit, false);
										childElement.getEntity().putStatus(IEntityConstants.PERSIST, true);

										treeElement.getChildElements().add(childElement);
										viewer.refresh(treeElement);

										currentPath = currentPath.createChildPath(childElement);
										viewer.setSelection(new TreeSelection(currentPath));
										update(null, childElement);
									}
								});
							}
						}
					}
					if (bean instanceof GenerationUnitWrapper) {
						final GenerationUnitWrapper generationUnit = (GenerationUnitWrapper) bean;
						if (null != generationUnit.getAdiResourceURI()) {
							menuManager.add(new Action(getFromStudioBundle("scenario.open.resource"),
									imageManager.getImageDescriptor("IMG_XJC_EDITOR.png")) {
								@Override
								public void run() {
									ScenarioResources sr = ((XjcBindingService) getBindingService()).getEditor()
											.getScenarioResources();
									IFile resourceFile;
									if (GenerationEnum.MESSAGE_BUNDLE == generationUnit.getType()) {
										resourceFile = ScenarioUtil.getResourceFileFromURI(sr, generationUnit.getAdiResourceURI(),
												EngineConstants.RESOURCE_MESSAGE_DIR, "properties");
										if (null != resourceFile)
											try {
												IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
														resourceFile, true);
											} catch (PartInitException e) {
												logError(e);
											}
									} else {
										resourceFile = ScenarioUtil.getXmlFileFromURI(sr, generationUnit.getAdiResourceURI());
										if (null != resourceFile)
											try {
												FileEditorInput fileEditorInput = new FileEditorInput(resourceFile);
												PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
														.openEditor(fileEditorInput, XjcTreeFormEditor.ID);
											} catch (PartInitException e) {
												logError(e);
											}
									}
									if (null == resourceFile) {
										String message = getFromStudioBundle("studio.no.resource",
												generationUnit.getAdiResourceURI());
										EngineTools.openDialog(MessageDialog.WARNING, message, message);
									}
								}
							});
							menuManager.add(new Separator());
						}
					}
					if (bean instanceof GenerationScenarioWrapper) {
						if (!scenarioResources.hasRcpPart()
								&& !scenarioResources.getScenarioTree().getGenerationScenario().getPluginEntity().isEmpty())
							menuManager.add(new Action(getFromStudioBundle("scenario.generation.rcp"),
									imageManager.getImageDescriptor("xjc/masterDetailPage.png")) {
								@Override
								public void run() {
									GenerateScenarioActionController generateScenarioAC = (GenerateScenarioActionController) genCode
											.getFromRegister("generationTBM:generateScenarioAction");
									generateScenarioAC.runRcpGeneration(scenarioResources, false);
								}
							});
						else if (!scenarioResources.isApplication()) {
							menuManager.add(new Action(getFromStudioBundle("scenario.generate.rcp.application"),
									imageManager.getImageDescriptor("xjc/applicationMenu.png")) {
								@Override
								public void run() {
									GenerateScenarioActionController generateScenarioAC = (GenerateScenarioActionController) genCode
											.getFromRegister("generationTBM:generateScenarioAction");
									generateScenarioAC.runRcpGeneration(scenarioResources, true);
								}
							});
						}
						menuManager.add(new Action(getFromStudioBundle("studio.generate.activator"),
								imageManager.getImageDescriptor("IMG_ACTIVATOR.png")) {
							@Override
							public void run() {
								String path = "src/"
										+ scenarioResources.getPluginPackage().replace('.', '/').concat("/Activator.java");
								try {
									IFile file = scenarioResources.getProject().getFile(path);
									file.delete(true, null);
									new ActivatorGenerator().generateCU(scenarioResources);
									file.getParent().refreshLocal(IResource.DEPTH_ONE, null);
								} catch (CoreException e) {
									logError(e);
								}
							}
						});
						menuManager.add(new Action(getFromStudioBundle("studio.generate.plugin.entity.tree"),
								imageManager.getImageDescriptor("IMG_GENERATE_TREE.png")) {
							@Override
							public void run() {
								ScenarioInput scenarioInput = new ScenarioInput(scenarioResources,
										EngineConstants.PLUGIN_ENTITY_TREE, "", false, true, true, false);
								new PluginEntityTreeGenerator(scenarioInput);
								try {
									String path = scenarioResources.getGencodePath().getGencodePackage().replace('.', '/') + "/"
											+ EngineConstants.PLUGIN_ENTITY_TREE + ".java";
									IFile file = scenarioResources.getGencodeFolder().getFile(path);
									file.refreshLocal(IResource.DEPTH_ZERO, null);
								} catch (CoreException e) {
									logError(e);
								}
							}
						});

					}
					if (bean instanceof GenerationUnitWrapper || bean instanceof PropertyFieldType
							|| bean instanceof ControlFieldType || bean instanceof ColumnFieldType) {
						Object parentBean = treeElement.getParentElement().getEntity().getBean();
						menuManager.add(new Action(getFromStudioBundle("scenario.delete.record"),
								toolkit.getRegisteredImageDescriptor("IMG_DELETE")) {
							@Override
							public void run() {
								((ContainerCheckedTreeViewer) viewer).remove(treeElement);
								entity.putStatus(IEntityConstants.REMOVE, false);
								for (final ErrorMessage errorMessage : ((XjcBindingService) getBindingService())
										.getTreeErrorMessageMap().values()) {
									if (errorMessage.getEntity().equals(entity))
										getBindingService().removeMessage(errorMessage);
								}
								treeElement.getParentElement().getChildElements().remove(treeElement);
								if (bean instanceof GenerationUnitWrapper || bean instanceof PropertyFieldType) {
									if (bean instanceof GenerationUnitWrapper)
										if (parentBean instanceof GenerationScenarioType)
											((GenerationScenarioType) parentBean).getGenerationUnit().remove(bean);
										else
											((PluginEntityType) parentBean).getGenerationUnit().remove(bean);
									else
										((PluginEntityType) parentBean).getPropertyField().remove(bean);
								} else {
									if (bean instanceof ControlFieldType)
										((PropertyFieldType) parentBean).setControlField(null);
									else
										((PropertyFieldType) parentBean).setColumnField(null);
									viewer.refresh();
								}
								viewer.setSelection(StructuredSelection.EMPTY);
							}
						});
						if (bean instanceof GenerationUnitWrapper && parentBean instanceof GenerationScenarioWrapper) {
							GenerationEnum generationEnum = ((GenerationUnitWrapper) bean).getType();
							boolean addGenerationUnit = generationEnum != GenerationEnum.NAVIGATOR;
							for (PluginEntityType pluginEntity : ((GenerationScenarioWrapper) parentBean).getPluginEntity()) {
								if (null != ((PluginEntityWrapper) pluginEntity).getGenerationUnit(generationEnum)) {
									addGenerationUnit = false;
									break;
								}
							}
							if (addGenerationUnit)
								menuManager.add(new Action(getFromStudioBundle("scenario.add.record.all"),
										imageManager.getImageDescriptor("IMG_ADD_GENERATION_UNIT.png")) {
									@Override
									public void run() {
										for (PluginEntityType pluginEntity : ((GenerationScenarioWrapper) parentBean)
												.getPluginEntity()) {
											try {
												scenarioResources.getPluginEntityScenario().populatePluginEntity(scenarioResources,
														(PluginEntityWrapper) pluginEntity,
														IPluginEntityScenario.MODEL_PART | IPluginEntityScenario.RCP_PART,
														generationEnum);
												entity.putStatus(IEntityConstants.MERGE, true);
											} catch (GeneratorErrorException e) {
												if (GeneratorErrorException.NO_ENTITY_TREE == e.getMessageId()) {
													EngineTools.openDialog(tree.getDisplay(), MessageDialog.ERROR,
															getFromGeneratorBundle("generation.error"), e.getMessage());
													logError(e);
													break;
												} else
													throw e;
											}
										}
									}
								});
						}
					}
				}
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

			private Set<GenerationEnum> getEligibleGenerations(boolean hasModel, boolean hasRCP) {
				Set<GenerationEnum> eligibleGenerations = new HashSet<GenerationEnum>();
				if (hasModel) {
					eligibleGenerations.add(GenerationEnum.ENTITY);
					eligibleGenerations.add(GenerationEnum.QUERY);
				}
				if (hasRCP) {
					eligibleGenerations.add(GenerationEnum.DETAIL);
					eligibleGenerations.add(GenerationEnum.TABLE);
					eligibleGenerations.add(GenerationEnum.EDITOR);
					eligibleGenerations.add(GenerationEnum.QUERY);
					eligibleGenerations.add(GenerationEnum.MESSAGE_BUNDLE);
				}
				return eligibleGenerations;
			}
		});
	}

	/**
	 * Compute eligibles.
	 *
	 * @param eligibleGenerations
	 *            the eligible generations
	 * @param generationUnits
	 *            the generation units
	 */
	private void computeEligibles(Set<GenerationEnum> eligibleGenerations, List<GenerationUnitType> generationUnits) {
		for (GenerationUnitType generationUnit : generationUnits)
			eligibleGenerations.remove(generationUnit.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.TreeController#getTreeManager()
	 */
	@Override
	public AStudioTreeManager getTreeManager() {
		return (AStudioTreeManager) treeManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.controller.ScenarioTreeController#synchronize()
	 */
	@Override
	public void synchronize() {
		super.synchronize();
		refresh();
		((TreeViewer) viewer).expandToLevel(2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.controller.ScenarioTreeController#setRootELement()
	 */
	@Override
	protected void setRootELement() {
		XjcEntity<?> entity = (XjcEntity<?>) parentController.getEntity();
		if (null != entity) {
			GenerationScenarioType generationScenario = ((ScenarioTreeWrapper) entity.getBean()).getGenerationScenario();
			entity.setTreeElement(new XjcTreeElement(((XjcTreeManager) treeManager), null, generationScenario, null, false));
			setRootElement(entity.getTreeElement());
		}
	};

	/**
	 * Sets the expanded UR is.
	 */
	public void setExpandedURIs() {
		setExpandedURIs(tree.getItems());
	}

	/**
	 * Sets the expanded UR is.
	 *
	 * @param childItems
	 *            the new expanded UR is
	 */
	private void setExpandedURIs(TreeItem[] childItems) {
		if (null != childItems)
			for (TreeItem treeItem : childItems) {
				if (treeItem.getExpanded()
						&& ((XjcTreeElement) treeItem.getData()).getEntity().getBean() instanceof PluginEntityWrapper)
					expandedURIs.add(
							((PluginEntityWrapper) ((XjcTreeElement) treeItem.getData()).getEntity().getBean()).getEntityURI());
				setExpandedURIs(treeItem.getItems());
			}
	}

	/**
	 * Expanded Items wich had been expanded before saving.
	 */
	public void expandedURIs() {
		GenerationTreeController generationTree = (GenerationTreeController) genCode.getFromRegister("generationTree");
		TreeViewer treeViewer = (TreeViewer) generationTree.getViewer();
		treeViewer.expandToLevel(2);
		if (!expandedURIs.isEmpty()) {
			// elements impacted by last changes must be visible
			XjcTreeElement rootElement = (XjcTreeElement) ((RootElement) treeViewer.getInput()).getRoot();
			treeViewer.expandToLevel(rootElement, 1);
			for (AXjcTreeElement element : rootElement.getChildElements()) {
				XjcTreeElement xjcTreeElement = (XjcTreeElement) element;
				Object bean = xjcTreeElement.getEntity().getBean();
				if (bean instanceof PluginEntityWrapper) {
					if (expandedURIs.contains(((PluginEntityWrapper) xjcTreeElement.getEntity().getBean()).getEntityURI()))
						treeViewer.expandToLevel(xjcTreeElement, 1);
				}
			}
			expandedURIs.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.controller.ScenarioTreeController#selectOutlinePage()
	 */
	public void selectOutlinePage() {
		StudioOutlinePage outlinePage = ((XjcBindingService) getBindingService()).getEditor().getOutlinePage();
		if (null != outlinePage) {
			XjcTreeElement treeElement = (XjcTreeElement) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			outlinePage.setSelection(GenerationTreeController.this,
					viewer.getSelection().isEmpty() ? null : treeElement.getEntity(), !editableOutlinePage);
			if (!viewer.getSelection().isEmpty())
				outlinePage.disableColumnControllers(true);
		}

	}
}
