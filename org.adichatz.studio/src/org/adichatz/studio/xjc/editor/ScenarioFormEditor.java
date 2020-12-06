/*******************************************************************************
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

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.APageController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.collection.CTabFolderController;
import org.adichatz.engine.controller.field.CheckBoxController;
import org.adichatz.engine.indigo.controller.LegacyFormPageController;
import org.adichatz.engine.indigo.editor.AdiFormPage;
import org.adichatz.engine.indigo.editor.FormEditorCore;
import org.adichatz.engine.indigo.editor.FormPageHeader;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.controller.EntitiesTableController;
import org.adichatz.studio.xjc.controller.FeaturesTableController;
import org.adichatz.studio.xjc.controller.GenerationTreeController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcDataCache;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioFormEditor.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ScenarioFormEditor extends ATreeFormEditor {

	/** The Constant ID. */
	static final public String ID = ScenarioFormEditor.class.getName();

	static private Map<String, String> tbmIdMap = new HashMap<String, String>();

	static {
		tbmIdMap.put("modelPage", "modelTBM");
		tbmIdMap.put("generationPage", "generationTBM");
		tbmIdMap.put("featuresPage", "featuresTBM");
	}

	/** The updated pages. */
	private Set<LegacyFormPageController> updatedPages = new HashSet<LegacyFormPageController>();

	/** The dirty. */
	private boolean dirty;

	/** The generating. */
	private boolean generating;

	private boolean isChangingPage;

	private int currentPageIndex;

	/**
	 * True when PluginEntityType is updated or GenerationUnitType.adiResourceURI is changed
	 * (so confirm is asked for ComponentGeneration#generatePluginEntityTreeClass()) 
	 */
	private boolean proposePluginEntitiesGeneration = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		if (!(input instanceof FileEditorInput))
			throw new RuntimeException(getFromStudioBundle("studio.invalid.input", this.getClass().getSimpleName(), input));
		setInput(new ScenarioEditorInput(((FileEditorInput) input).getFile()));
		setPartName(input.getName());
		setTitleImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_EDIT_SCENARIO.png"));
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		pluginResources = StudioRcpPlugin.getDefault().getPluginResources();
		addPartListener(site);
		addPageChangedListener(new IPageChangedListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				IFormPage formPage = (IFormPage) event.getSelectedPage();
				if (formPage instanceof XmlTextEditor) {
					if (null != outlinePage) // when outline view is closed
						outlinePage.setSelection(null);
				} else {
					LegacyFormPageController pageController = ((AdiFormPage) formPage).getFormPageController();
					if ("featuresPage".equals(pageController.getRegisterId())) {
						CTabFolderController featuresTabFolder = (CTabFolderController) genCode
								.getFromRegister("featuresTabFolder");
						FeaturesTableController<?> featuresTableController = (FeaturesTableController<?>) featuresTabFolder
								.getSelectedController().getChildControllers().get(0);
						featuresTableController.selectOutlinePage();
					} else if ("modelPage".equals(pageController.getRegisterId())) {
						((EntitiesTableController<?>) genCode.getFromRegister("entitiesTable")).selectOutlinePage();
					} else if ("generationPage".equals(pageController.getRegisterId())) {
						((GenerationTreeController) genCode.getFromRegister("generationTree")).selectOutlinePage();
					}
					activePage(pageController);
				}
			}
		});
	}

	@Override
	protected void pageChange(int newPageIndex) {
		if (!isChangingPage && isDirty()) {
			isChangingPage = true;
			setActivePage(currentPageIndex);
			isChangingPage = false;
			String message = 4 == currentPageIndex ? "scenario.abort.chage.xml.page" : "scenario.abort.chage.page";
			LogBroker.displayError(getFromStudioBundle("scenario.invalid.action"), getFromStudioBundle(message));
		} else {
			super.pageChange(newPageIndex);
			currentPageIndex = newPageIndex;
		}
	}

	/**
	 * Active page.
	 * 
	 * @param formPageController
	 *            the form page controller
	 */
	private void activePage(LegacyFormPageController formPageController) {
		if (updatedPages.remove(formPageController)) {
			formPageController.getEntityInjection().initialize(null);
			refreshPage(formPageController);
			if ("modelPage".equals(formPageController.getRegisterId())) {
				refreshSetController((ASetController) genCode.getFromRegister("entitiesTable"));
			} else if ("generationPage".equals(formPageController.getRegisterId())) {
				refreshSetController((ASetController) genCode.getFromRegister("generationTree"));
			} else if ("featuresPage".equals(formPageController.getRegisterId())) {
				CTabFolderController featuresTabFolder = (CTabFolderController) genCode.getFromRegister("featuresTabFolder");
				ASetController setController = (ASetController) featuresTabFolder.getSelectedController().getChildControllers()
						.get(0);
				refreshSetController(setController);
			}
		}
	}

	/**
	 * Launch editor.
	 */
	protected void launchEditor() {
		entity = ((XjcDataCache) getPluginResources().getDataAccess().getDataCache()).fetchEntity(getTreeWrapper(true, true),
				getCacheKey());
		// launch the form Editor
		ParamMap paramMap = new ParamMap();
		paramMap.put(ParamMap.TITLE, getEditorInput().getToolTipText());
		paramMap.put("pluginName", pluginResources.getPluginName());
		genCode = (FormEditorCore) pluginResources.getNewInstance(IStudioConstants.SCENARIO_EDITOR, "editor", this, paramMap);
		genCode.createContents();
		genCode.injectCustomizations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#addPages()
	 */
	@Override
	public void addPages() {
		int index = 0;
		for (final FormPageHeader formPageHeader : genCode.getFormPageHeaders()) {
			emptyFile = false;
			formPageHeader.addPage(this, entity, index++);
		}
		addXMLEditor(index);
	}

	/**
	 * Post refresh.
	 */
	public void postRefresh() {
		dirty = false;
		getBindingService().close();
		if (null != getOutlinePage())
			getOutlinePage().setSelection(null);
		insideFileChanged = true;
		entity = ((XjcDataCache) getPluginResources().getDataAccess().getDataCache()).fetchEntity(getTreeWrapper(true, true),
				getCacheKey());
		insideFileChanged = false;
		updatedPages.addAll(getActivePageControllers());
		Object activePage = getSelectedPage();
		if (activePage instanceof AdiFormPage)
			activePage(((AdiFormPage) activePage).getFormPageController());
		generating = false;
		enableAction(true);
	}

	/**
	 * Refresh page.
	 * 
	 * @param pageController
	 *            the page controller
	 */
	private void refreshPage(LegacyFormPageController pageController) {
		if (null != pageController) { // Page might not be instantiated
			pageController.getControl().setRedraw(false);

			pageController.disposeEntityInjections();
			new EntityInjection(pageController, entity);

			pageController.endLifeCycleAndSync();

			pageController.getControl().setRedraw(true);
			pageController.reflowControllers();
		}
	}

	/**
	 * Refresh set controller.
	 * 
	 * @param setController
	 *            the set controller
	 */
	private void refreshSetController(ASetController setController) {
		if (null != setController) { // might not be instantiated
			setController.getViewer().setSelection(StructuredSelection.EMPTY);
			setController.refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.AStudioFormEditor#getTreeWrapper(boolean)
	 */
	@Override
	public Object getTreeWrapper(boolean reloadFile, boolean reinitTree) {
		if (null == treeWrapper || reloadFile)
			try {
				if (!getEditorInput().getFile().isSynchronized(IResource.DEPTH_ZERO))
					getEditorInput().getFile().refreshLocal(IResource.DEPTH_ZERO, null);
				if (null == cacheKey) {
					scenarioResources = ScenarioResources.getInstance(getEditorInput().getFile().getProject());
					String fileName = getEditorInput().getName();
					fileName = fileName.substring(0,
							fileName.length() - getEditorInput().getFile().getFileExtension().length() - 1);
					cacheKey = new MultiKey(scenarioResources.getPluginName(), fileName,
							ScenarioUtil.getSubPackage(getEditorInput().getFile()));
					treeWrapper = scenarioResources.getScenarioTree();
				}
				if (reloadFile) {
					scenarioResources.loadScenarioTree(getEditorInput().getFile().getContents());
					treeWrapper = scenarioResources.getScenarioTree();
				}
			} catch (CoreException | JAXBException e) {
				logError(e);
			}
		return treeWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.AStudioFormEditor#enableAction(boolean, boolean)
	 */
	@Override
	public void enableAction(boolean enabled) {
		if (!disposing) {
			boolean superDirty = super.isDirty();
			String includeId = null;
			APageController activePageController = getActivePageController();
			if (null != activePageController)
				includeId = tbmIdMap.get(activePageController.getRegisterId());
			if (null != includeId) {
				enabledAction(includeId.concat(":errorStudioAction"), !enabled);
				enabledAction(includeId.concat(":savePageAction"), enabled && superDirty);
				enabledAction(includeId.concat(":generateScenarioAction"), enabled && !superDirty);
			}
			if (enabled) {
				ASetController entitiesTable = (ASetController) genCode.getFromRegister("entitiesTable");
				if (null != entitiesTable) {
					QueryResult queryResult = ((ATabularContentProvider<?>) entitiesTable.getViewer().getContentProvider())
							.getQueryManager().getQueryResult();
					if (null != queryResult && queryResult.getQueryResultList().isEmpty()) {
						boolean hasModelPart = ((CheckBoxController) genCode.getFromRegister("modelPart")).getValue();
						if (!hasModelPart)
							enabledAction("modelTBM:generateScenarioAction", false);
					}
				}
			}
		}
	}

	/**
	 * Enabled action.
	 * 
	 * @param actionId
	 *            the action id
	 * @param enabled
	 *            the enabled
	 */
	private void enabledAction(String actionId, boolean enabled) {
		ActionController actionController = (ActionController) genCode.getFromRegister(actionId);
		if (null == actionController)
			actionController = (ActionController) genCode.getFromRegister(actionId);
		if (null != actionController && null != actionController.getControl()) {
			boolean enabledAction = enabled && !generating;
			actionController.setEnabled(enabledAction);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.AStudioFormEditor#doSave()
	 */
	@Override
	public void doSave() {
		GenerationScenarioWrapper generationScenario = (GenerationScenarioWrapper) ((ScenarioTreeWrapper) treeWrapper)
				.getGenerationScenario();
		CheckBoxController modelCB = (CheckBoxController) genCode.getFromRegister("modelPart");
		Object activePage = getSelectedPage();
		String page = null;
		if (activePage instanceof AdiFormPage) {
			page = ((AdiFormPage) activePage).getFormPageController().getRegisterId();
			if ("modelPage".equals(page)) {
				// Forces null value for model part or connector where no model or jse or jboss-7 connectors are specified.
				if (!modelCB.getValue()) {
					generationScenario.setModelPart(null);
				} else {
					generationScenario.initModelPart(scenarioResources);
				}

				// Add required bundles in Manifest file when PluginEntity were added.
				Set<String> requiredBundles = new HashSet<String>();
				try {
					for (PluginEntityType pluginEntity : generationScenario.getPluginEntity()) {
						ScenarioResources modelSR = ScenarioResources
								.getInstance(((PluginEntityWrapper) pluginEntity).getEntityKeys()[0], null);
						if (!scenarioResources.equals(modelSR)) {
							if (null == requiredBundles)
								requiredBundles = ScenarioUtil.getRequiredBundleNames(scenarioResources.getManifestChanger());
							if (!requiredBundles.contains(modelSR.getPluginName())) {
								scenarioResources.getManifestChanger().addAttributeElement(Constants.REQUIRE_BUNDLE,
										modelSR.getPluginName());
								requiredBundles.add(modelSR.getPluginName());
							}
						}
					}
					if (!requiredBundles.isEmpty()) // Changes on MANIFEST.MF
						scenarioResources.getManifestChanger().write();
				} catch (IOException | CoreException e) {
					logError(e);
				}
			} else if ("generationPage".equals(page)) {
				((GenerationTreeController) genCode.getFromRegister("generationTree")).setExpandedURIs();
				for (IEntity<?> entity : getBindingService().getUpdatedEntities().keySet()) {
					if (entity.getBean() instanceof GenerationUnitType) {
						GenerationUnitType gu = (GenerationUnitType) entity.getBean();
						GenerationUnitType beforeGu = (GenerationUnitType) ((XjcEntity<?>) entity).getBeforeBean();
						if (!Utilities.equals(gu.getAdiResourceURI(), beforeGu.getAdiResourceURI())) {
							proposePluginEntitiesGeneration = true;
							break;
						}
					}
				}
			}
		}
		super.doSave();
		scenarioResources.refresh();
		postRefresh();
		String generateModelMessage = null;
		if ("modelPage".equals(page)) {
			if (modelCB.getValue()) {
				modelCB.getControl().setEnabled(false);
				((Control) genCode.getFromRegister("datasourceHL").getControl()).setEnabled(false);
				((Control) genCode.getFromRegister("connectorDataSource").getControl()).setEnabled(false);
			}
			if (scenarioResources.hasModelPart())
				generateModelMessage = "scenario.generate.model.after.change";
			else
				generateModelMessage = "scenario.generate.api.model.after.change";
			boolean enableGenerateAction = false; // Do ot ask for generation if entity table is empty.
			if (null != scenarioResources.getGenerationScenario().getModelPart())
				enableGenerateAction = true;
			else {
				ASetController entitiesTable = (ASetController) genCode.getFromRegister("entitiesTable");
				enableGenerateAction = !((ATabularContentProvider<?>) entitiesTable.getViewer().getContentProvider())
						.getQueryManager().getQueryResult().getQueryResultList().isEmpty();
			}
			if (enableGenerateAction && EngineTools.openDialog(MessageDialog.CONFIRM, getFromStudioBundle("scenario.change"),
					getFromStudioBundle(generateModelMessage))) {
				ActionController generateScenarioAC = (ActionController) genCode.getFromRegister("modelTBM:generateScenarioAction");
				generateScenarioAC.getControl().runAction();
			}
		} else if ("generationPage".equals(page)) {
			((GenerationTreeController) genCode.getFromRegister("generationTree")).expandedURIs();
		} else if ("featuresPage".equals(page))
			scenarioResources.loadScenarioParameters();
		if (proposePluginEntitiesGeneration) {
			proposePluginEntitiesGeneration = false;
			if (EngineTools.openDialog(MessageDialog.CONFIRM, getFromStudioBundle("scenario.change"),
					getFromStudioBundle("scenario.regenerate.PluginEntityTree")))
				scenarioResources.getComponentGeneration().generatePluginEntityTreeClass();
		}
		setEditable(true);
		xmlTextEditor.setXmlEditable(true);
		if (outlinePage != null && outlinePage.getControl() != null && !outlinePage.getControl().isDisposed()) {
			outlinePage.enable(true);
		}
		try {
			getEditorInput().getFile().refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			logError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return dirty || super.isDirty();
	}

	/**
	 * Sets the force dirty.
	 * 
	 * @param dirty
	 *            the dirty
	 * @return true, if successful
	 */
	public boolean setForceDirty(boolean dirty) {
		this.dirty = dirty;
		return super.setDirty(dirty);
	}

	/**
	 * Sets the generating.
	 * 
	 * @param generating
	 *            the new generating
	 */
	public void setGenerating(boolean generating) {
		this.generating = generating;
		enableAction(true);
	}

	@Override
	public void fileChanged() {
		if (insideFileChanged || null == scenarioResources)
			// change in this editor, do nothing
			// If Scenario.xml file was changed outside eclipse, scenario resource ask for synchronize file 'do nothing)
			return;
		super.fileChanged();
		scenarioResources.loadScenarioParameters();
	}

	@Override
	protected void postFileChanged(XjcBindingService bindingService) throws CoreException, JAXBException {
		super.postFileChanged(bindingService);
		GenerationTreeController generationTree = (GenerationTreeController) genCode.getFromRegister("generationTree");
		if (null != generationTree) // Scenario.xml file was changed outside eclipse
			generationTree.expandedURIs();
	}

	public void setProposePluginEntitiesGeneration() {
		proposePluginEntitiesGeneration = true;
	}
}
