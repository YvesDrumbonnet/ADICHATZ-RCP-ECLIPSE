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
package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.controller.IActionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.action.MenuActionController;
import org.adichatz.engine.controller.collection.ManagedToolBarController;
import org.adichatz.engine.indigo.controller.LegacyFormPageController;
import org.adichatz.engine.indigo.editor.FormEditorCore;
import org.adichatz.engine.indigo.editor.FormPageHeader;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.ErroneousFilesFormDialog;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.editor.action.GenerateAction;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.part.FileEditorInput;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcTreeFormEditor.
 */
public class XjcTreeFormEditor extends ATreeFormEditor {
	/** The Constant ID. */
	static final public String ID = XjcTreeFormEditor.class.getName();

	/** The error form page action. */
	private ActionController errorFormPageAC;

	/** The save action. */
	private ActionController savePageAC;

	/** The generate action. */
	private MenuActionController openJavaFileMAC;

	/** The refresh action. */
	private ActionController refreshPageAC;

	/** The switch to active file contribution item. */
	private IContributionItem switchToActiveFileCI;

	/** The compare files action ci. */
	private IContributionItem compareFilesActionCI;

	/** The tool bar manager. */
	private IToolBarManager toolBarManager;

	/** The tree controller. */
	private XjcTreeController treeController;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		if (input instanceof FileEditorInput) {
			fileEditorInput = (FileEditorInput) input;
		} else
			throw new RuntimeException("Don't know how to open " + this.getClass().getSimpleName() + " with input " + input + "?!");
		setInput(fileEditorInput);

		pluginResources = StudioRcpPlugin.getDefault().getPluginResources();

		scenarioResources = ScenarioResources.getInstance(fileEditorInput.getFile().getProject());
		String fileName = fileEditorInput.getName();
		cacheKey = new MultiKey(scenarioResources.getPluginName(),
				fileName.substring(0, fileName.length() - fileEditorInput.getFile().getFileExtension().length() - 1),
				ScenarioUtil.getSubPackage(fileEditorInput.getFile()));
		setPartName(fileName);

		setTitleImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XJC_EDITOR.png"));
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);

		addPartListener(site);
		addPageChangedListener(new IPageChangedListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				IFormPage formPage = getActivePageInstance();
				if (formPage instanceof XmlTextEditor) {
					if (null != outlinePage)
						outlinePage.setSelection(null);
				} else if (null != treeController && null != treeController.getViewer()) {
					ISelection selection = treeController.getViewer().getSelection();
					if (!selection.isEmpty())
						treeController.getViewer().setSelection(selection, false);
				}
			}
		});
	}

	/**
	 * Launch editor.
	 */
	protected void launchEditor() {
		ParamMap paramMap = new ParamMap();
		pageTitle = new StringBuffer(getCacheKey().getString(0)).append(": ").append(getCacheKey().getString(2).replace('.', '/'))
				.append("/").append(getCacheKey().getString(1)).toString();

		paramMap.put(ParamMap.TITLE, pageTitle);
		// launch the form Editor
		genCode = (FormEditorCore) pluginResources.getNewInstance(IStudioConstants.XJC_TREE_EDITOR, "editor", this, paramMap);
		genCode.createContents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#pageChange(int)
	 */
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (null == treeWrapper && !emptyFile) {// Start editor
			getTreeWrapper(true, true);
			checkJavaMarkers();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#addPages()
	 */
	@Override
	public void addPages() {
		try {
			IFileStore store = org.eclipse.core.filesystem.EFS.getStore(fileEditorInput.getFile().getLocationURI());
			if (0l < store.fetchInfo().getLength()) {
				for (final FormPageHeader formPageHeader : genCode.getFormPageHeaders()) {
					formPageHeader.addPage(this, entity, 0);
					if (emptyFile) {
						emptyFile = false;
						formPageHeader.addListener(new AControlListener("addPage#AI", IEventType.AFTER_INITIALIZE) {
							@Override
							public void handleEvent(AdiEvent event) {
								formPageHeader.getFormPageController()
										.addListener(new AControlListener("addPage#AELC#2", IEventType.AFTER_END_LIFE_CYCLE) {
											@Override
											public void handleEvent(AdiEvent event) {
												((XjcBindingService) formPageHeader.getFormPageController().getBindingService())
														.enableActions();
											}
										});
							}

							@Override
							public void dispose() {
							}
						});
					}
				}
			}
		} catch (CoreException e) {
			logError(e);
		}
		addXMLEditor(emptyFile ? 0 : 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.AStudioFormEditor#getTreeWrapper(boolean)
	 */
	@Override
	public ITreeWrapper getTreeWrapper(boolean reloadFile, boolean initTree) {
		IFile file = fileEditorInput.getFile();
		try {
			if ((null == treeWrapper || reloadFile) && !fileEditorInput.getFile().isSynchronized(IResource.DEPTH_ZERO))
				file.refreshLocal(IResource.DEPTH_ZERO, null);
			InputStream content = file.getContents();
			treeWrapper = (ITreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(), content);
			MultiKey cacheKey = getCacheKey();
			treeWrapper.setPluginName(cacheKey.getString(0));
			treeWrapper.setTreeId(cacheKey.getString(1));
			treeWrapper.setSubPackage(cacheKey.getString(2));
			treeWrapper.setXmlFile(fileEditorInput.getFile().getLocation().toFile());
			if (initTree) {
				treeController = (XjcTreeController) genCode.getFromRegister("xjcTree");
				treeController.initTreeManager(treeWrapper, getCacheKey());
			}
			// treeController.refresh();
			treeController.getControl()
					.setBackground(AdichatzApplication.getInstance().getFormToolkit().getColors().getBackground());

			checkJavaMarkers();
		} catch (CoreException | JAXBException e) {
			displayError(getFromStudioBundle("studio.invalid.axml.file", file.getName()), e);
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
		if (!emptyFile) {
			boolean dirty = isDirty();
			if (null == errorFormPageAC) {
				errorFormPageAC = (ActionController) genCode.getFromRegister("errorStudioAction");
				savePageAC = (ActionController) genCode.getFromRegister("savePageAction");
				refreshPageAC = (ActionController) genCode.getFromRegister("refreshFromXmlFileAction");
				openJavaFileMAC = (MenuActionController) genCode.getFromRegister("openJavaFileMenuAction");
				toolBarManager = ((ManagedToolBarController) genCode.getFromRegister("pageToolBar")).getToolBarManager();
				for (IContributionItem contributionItem : toolBarManager.getItems()) {
					if (contributionItem instanceof ActionContributionItem) {
						Action action = (Action) ((ActionContributionItem) contributionItem).getAction();
						if (action instanceof AAction) {
							IActionController actionController = ((AAction) action).getActionController();
							if ("switchToActiveFileAction".equals(actionController.getRegisterId())) {
								switchToActiveFileCI = contributionItem;
							} else if ("compareFilesAction".equals(actionController.getRegisterId())) {
								compareFilesActionCI = contributionItem;
							}
						}
					}
				}
			}
			if (outlinePage != null && outlinePage.getControl() != null && !outlinePage.getControl().isDisposed()) {
				outlinePage.enable(editable);
			}
			if (null != savePageAC.getControl()) {
				boolean updateToolBar = false;
				errorFormPageAC.setEnabled(!getBindingService().getErrorMessageMap().isEmpty());
				savePageAC.setEnabled(editable && dirty && enabled); // bug 0.8.8-14 add '&& enabled'
				refreshPageAC.setEnabled(editable);
				openJavaFileMAC.setEnabled(editable);
				boolean switchVisibleOld = switchToActiveFileCI.isVisible();
				boolean switchVisible = AdiPropertyTester.isSwitchable(fileEditorInput.getFile()) && editable;
				if (switchVisible != switchVisibleOld) {
					switchToActiveFileCI.setVisible(switchVisible);
					updateToolBar = true;
				}
				boolean compareVisibleOld = compareFilesActionCI.isVisible();
				boolean compareVisible = AdiPropertyTester.isComparable(fileEditorInput.getFile());
				if (compareVisible != compareVisibleOld) {
					compareFilesActionCI.setVisible(compareVisible);
					updateToolBar = true;
				}
				if (updateToolBar)
					toolBarManager.update(true);
			}
		}
	}

	/**
	 * Enable page actions.
	 */
	public void enablePageActions() {
		ManagedToolBarController pageToolBar = (ManagedToolBarController) genCode.getFromRegister("pageToolBar");
		for (IContributionItem contributionItem : pageToolBar.getToolBarManager().getItems()) {
			if (contributionItem instanceof ActionContributionItem) {
				AAction action = (AAction) ((ActionContributionItem) contributionItem).getAction();
				if ("switchToActiveFileAction".equals(action.getActionController().getRegisterId()))
					contributionItem.setVisible(AdiPropertyTester.isSwitchable(fileEditorInput.getFile()) && editable);
			}
		}
		pageToolBar.getToolBarManager().update(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.ADirtyFormEditor#doSave()
	 */
	@Override
	public void doSave() {
		//		if (StudioRcpPlugin.getDefault().getPreferenceStore().getBoolean(IStudioConstants.ASK_FOR_SWITCHING_FILE)
		//				&& AdiPropertyTester.isSwitchable(fileEditorInput.getFile())) {
		AskForSwitching askForSwithcing = new AskForSwitching(this, fileEditorInput.getFile(), null);
		int returnValue = askForSwithcing.getReturnValue();
		/*
		 return Value:
		  	# -1 (escape) or 1 (cancel) do nothing do nothing
		  	# 0 current file is Generated, copy changed content in active file
		  	# 2 save changes in current file
		 */
		if (2 == returnValue)
			saveChanges();
	}

	public void saveChanges() {
		try {
			insideFileChanged = true;
			final XjcTreeController treeController = (XjcTreeController) genCode.getRegister().get("xjcTree").getController();
			treeWrapper = (ITreeWrapper) treeController.getEntity().getBean();

			ScenarioUtil.createXmlFile(treeWrapper.getXmlFile(), treeWrapper);
			treeController.getBindingService().saveEntities();
			fileEditorInput.getFile().refreshLocal(IResource.DEPTH_ZERO, null);
			enableAction(true);
			if (StudioRcpPlugin.getDefault().getPreferenceStore().getBoolean(IStudioConstants.GENERATE_CLASS_AFTER_SAVING)) {
				// Generate automatically java class
				new GenerateAction(fileEditorInput.getFile(), treeController.getControl().getDisplay()).runAction();
			}
			treeController.getViewer().setSelection(TreeSelection.EMPTY);
			setEditable(true);
			setDirty(false);
			postFileChanged(treeController.getBindingService());
		} catch (CoreException | JAXBException e) {
			logError(e);
		} finally {
			insideFileChanged = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.ATreeFormEditor#postRefresh()
	 */
	@Override
	public void postRefresh() {
		getBindingService().close();
		// Case when file previously open was changed outside eclipse
		if (0 < getActivePageControllers().size()) {
			LegacyFormPageController pageController = getActivePageControllers().get(0);
			pageController.getControl().setRedraw(false);
			pageController.getEntityInjection().initialize(entity);
			pageController.endLifeCycleAndSync(); // refresh is included AFTER_END_LIFE_CYCLE listener (see addPages)
			pageController.getControl().setRedraw(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.ATreeFormEditor#postFileChanged(org.adichatz.studio.xjc.data.XjcBindingService)
	 */
	protected void postFileChanged(XjcBindingService bindingService) throws CoreException, JAXBException {
		bindingService.getEditor().getTreeWrapper(false, true);
		checkJavaFiles(false);
		checkJavaMarkers();
	}

	private void checkJavaFiles(boolean force) throws CoreException {
		String treeId = treeWrapper.getTreeId();
		if (treeId.endsWith("GENERATED"))
			treeId = treeId.substring(0, treeId.length() - 9);
		ScenarioInput scenarioInput = new ScenarioInput(scenarioResources, treeId, treeWrapper.getSubPackage());
		scenarioInput.setXmlFile(fileEditorInput.getFile());
		GeneratorUnit generatorUnit = new GeneratorUnit(scenarioInput);
		generatorUnit.initialize(true);
		if (generatorUnit.isJavaFilesExpired() || force) {
			if (scenarioResources.getPluginResources().getPluginEntityTree().getPluginEntityURIMap().isEmpty())
				scenarioResources.getPluginResources().loadPluginEntities();
			generatorUnit.generateCodeFromXMLTree(true);
			new ErroneousFilesFormDialog(this, Display.getCurrent(), scenarioResources);
			if (0 != fileEditorInput.getFile().findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO).length) {
				addErrorDecorator("DCR_ERROR.png");
			}
		}
	}

	public void checkJavaMarkers() {
		IFile file = fileEditorInput.getFile();
		long fileTime = file.getLocalTimeStamp();
		try {
			file.deleteMarkers(null, false, IResource.DEPTH_ZERO);
		} catch (ResourceException e) {
		} catch (CoreException e) {
			logError(e);
		}
		try {
			Display display = treeController.getControl().getDisplay();
			if (null != display)
				display.syncExec(() -> { // Could be called from a job (ErroneousFilesFormDialog)
					setTitleImage(
							AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XJC_EDITOR.png"));
				});
			for (IResource resource : ScenarioUtil.getJavaFiles(file)) {
				if (0L == resource.getRawLocation().makeAbsolute().toFile().length() //
						|| IMarker.SEVERITY_ERROR == resource.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_ZERO) //
						|| resource.getLocalTimeStamp() < fileTime //
				) {
					addErrorDecorator("DCR_WARNING.png");
					break;
				}
			}
		} catch (CoreException e) {
			logError(e);
		}
	}

	private void addMarker(IFile file, String message) {
		try {
			IMarker marker = file.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.MESSAGE, getFromStudioBundle("studio.invalid.axml.file", file.getName()));
			marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			addErrorDecorator("DCR_ERROR.png");
		} catch (ResourceException e) {
		} catch (CoreException e) {
			logError(e);
		}
		addErrorDecorator("DCR_ERROR.png");
	}

	/**
	 * Adds the error decorator.
	 */
	private void addErrorDecorator(String dcrName) {
		ImageDescriptor imageDescriptor = new DecorationOverlayIcon(getTitleImage(),
				AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, dcrName),
				IDecoration.BOTTOM_LEFT);
		treeController.getControl().getDisplay().syncExec(() -> { // Could be called from a job (ErroneousFilesFormDialog)
			setTitleImage(imageDescriptor.createImage());
		});
	}
}
