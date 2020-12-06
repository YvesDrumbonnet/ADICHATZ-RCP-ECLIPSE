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
package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdiBusyIndicator;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.indigo.editor.AdiParListener;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.INavigationHistory;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorSite;

// TODO: Auto-generated Javadoc
/**
 * The Class AStudioFormEditor.
 */
public abstract class ATreeFormEditor extends AStudioFormEditor implements IResourceChangeListener {

	/** The cache key. */
	protected MultiKey cacheKey;

	/** The xml text editor. */
	protected XmlTextEditor xmlTextEditor;

	protected boolean insideFileChanged;

	protected boolean activated;

	protected boolean emptyFile = true;

	/**
	 * Creates the site.
	 * 
	 * @param page
	 *            the page
	 * @return the i editor site
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createSite(org.eclipse.ui.IEditorPart)
	 */
	protected IEditorSite createSite(IEditorPart page) {
		editable = true;
		IEditorSite site = null;
		if (page == xmlTextEditor) {
			site = new MultiPageEditorSite(this, page) {
				public String getId() {
					// Sets this ID so nested editor is configured for XML source
					// return ContentTypeIdForXML.ContentTypeID_XML + ".source"; //$NON-NLS-1$;
					return "org.eclipse.core.runtime.xml.source"; //$NON-NLS-1$ ;
				}
			};
		} else {
			site = super.createSite(page);
		}
		return site;
	}

	/**
	 * Adds the xml editor.
	 */
	protected void addXMLEditor(int pageIndex) {
		try {
			xmlTextEditor = new XmlTextEditor(this, pageIndex);
			xmlTextEditor.setXmlEditable(true);
			xmlTextEditor.addPropertyListener(new IPropertyListener() {
				@Override
				public void propertyChanged(Object source, int propId) {
					ATreeFormEditor.this.firePropertyChange(PROP_DIRTY);
					if (activated && outlinePage != null && outlinePage.getControl() != null
							&& !outlinePage.getControl().isDisposed()) {
						setEditable(false);
					}
				}
			});
			int fSourcePageIndex = addPage(xmlTextEditor, getEditorInput());
			setPageText(fSourcePageIndex, "XML");
			firePropertyChange(PROP_TITLE);
		} catch (PartInitException e) {
			logError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#getEditorInput()
	 */
	@Override
	public FileEditorInput getEditorInput() {
		return (FileEditorInput) super.getEditorInput();
	}

	/**
	 * Gets the cache key.
	 * 
	 * @return the cache key
	 */
	public MultiKey getCacheKey() {
		return cacheKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		disposing = true;
		if (null != editorBindingService)
			editorBindingService.close();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	private long localTimeStamp;

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		if (null != delta) {// e.g. project is deleted
			long currentLocalTimeStamp = getEditorInput().getFile().getLocalTimeStamp();
			if (currentLocalTimeStamp == localTimeStamp)
				return;
			localTimeStamp = currentLocalTimeStamp;
			IResourceDelta fileDelta = delta.findMember(getEditorInput().getFile().getFullPath());
			if (null != fileDelta) {
				IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta delta) {
						switch (delta.getKind()) {
						case IResourceDelta.REMOVED:

							try {
								getSite().getWorkbenchWindow().getShell().getDisplay().syncExec(() -> {
									closeEditor();
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case IResourceDelta.CHANGED:
							fileChanged();
							break;
						}
						return true;
					}
				};
				try {
					fileDelta.accept(visitor);
				} catch (CoreException e) {
					logError(e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.ADirtyFormEditor#doSave()
	 */
	@Override
	public void doSave() {
		for (ABindingService bindingService : getBindingServices())
			if (bindingService.isDirty())
				doSave(bindingService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.indigo.editor.AFormEditor#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return super.isDirty() || xmlTextEditor.isDirty();
	}

	/**
	 * Do save.
	 * 
	 * @param bindingService
	 *            the binding service
	 */
	public void doSave(ABindingService bindingService) {
		bindingService.saveEntities();
		// Launched when closing a dirty editor or by org.adichatz.studio.xjc.editor.action.SaveActionController
		AdiBusyIndicator.showWhile(() -> {
			insideFileChanged = true;
			ScenarioUtil.createXmlFile(getEditorInput().getFile().getLocation().toFile(), treeWrapper);
			insideFileChanged = false;
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.indigo.editor.ADirtyFormEditor#setDirty(boolean)
	 */
	@Override
	public boolean setDirty(boolean dirty) {
		if (xmlTextEditor.isEditable() == dirty) {
			xmlTextEditor.setXmlEditable(!dirty);
			ISourceViewer sourceViewer = xmlTextEditor.getXmlViewer();
			if (null != sourceViewer) // sourceViewer is null if saving process in closing process.
				sourceViewer.setEditable(!dirty);
		}
		return super.setDirty(dirty);
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		if (xmlTextEditor.getXmlViewer().isEditable())
			xmlTextEditor.doSave(progressMonitor);
		else
			doSave();
		enableAction(true);
	}

	@Override
	public void fileChanged() {
		if (emptyFile || insideFileChanged) // change in this editor, do nothing
			return;
		insideFileChanged = true;
		final XjcBindingService bindingService = (XjcBindingService) getBindingService();
		// binding service could be null when change are made outside editor
		if (null != bindingService && bindingService.isDirty()) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(ATreeFormEditor.this);
			if (!EngineTools.openDialog(MessageDialog.CONFIRM, getFromStudioBundle("studio.editor.refreshFormPage"),
					getFromStudioBundle("studio.editor.confirmRefreshFormPage")))
				return;
		}
		parent.getDisplay().syncExec(() -> { // Avoid Invalid thread access
			Set<AEntityManagerController> containerControllers = new HashSet<AEntityManagerController>();
			for (final ErrorMessage errorMessage : bindingService.getErrorMessageMap().getErrorMessages()) {
				IValidableController controller = errorMessage.getValidator().getTriggeringController();
				if (controller instanceof AEntityManagerController)
					containerControllers.add((AEntityManagerController) controller);
				else
					containerControllers.add((AEntityManagerController) controller.getParentController());
			}
			// see POST_REFRESH and POST_REFRESH_ENTITIES for added code
			if (bindingService.refreshEntities(bindingService.getEntities())) { // refresh all entities and not only updated
																				// entities because changes could come from
																				// outside
				bindingService.clearMessages();
				bindingService.getEditor().setDirty(false);
			}
			for (AEntityManagerController controller : containerControllers)
				controller.validateFields();

			StudioOutlinePage studioOutlinePage = bindingService.getEditor().getOutlinePage();
			if (null != studioOutlinePage && null != studioOutlinePage.getToolContainer()) {
				studioOutlinePage.getToolContainer().setDirty(false);
			}
			try {
				if (null != pages) {// pages is null if saving process in included in closing process.
					getEditorInput().getFile().refreshLocal(IResource.DEPTH_ZERO, null);
					bindingService.getEditor().postRefresh();
				}
				postFileChanged(bindingService);
				editable = true;
			} catch (Exception e) {
				logError(e);
			} finally {
				insideFileChanged = false;
			}
		});
	}

	protected void postFileChanged(XjcBindingService bindingService) throws CoreException, JAXBException {
	}

	protected void addPartListener(final IEditorSite site) {
		AdiParListener partListener = new AdiParListener() {
			@Override
			public void partActivated(IWorkbenchPart part) {
				activated = part.equals(ATreeFormEditor.this) || part.equals(outlinePage);

				// Navigation history could not work. (markEditor is launched on old editor first and not on this editor).
				if (null != site.getPage()) { // page could be null when eroor occurs on opening
					INavigationHistory navigationHistory = site.getPage().getNavigationHistory();
					INavigationLocation navigationLocation = navigationHistory.getCurrentLocation();
					if (null != navigationLocation) {
						if (!getEditorInput().equals(navigationHistory.getCurrentLocation().getInput()))
							navigationHistory.markLocation(ATreeFormEditor.this);
					}
				}
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
				if (part.equals(ATreeFormEditor.this)) {
					if (null != XjcFieldViewPart.getInstance())
						XjcFieldViewPart.getInstance().showEmptyPage();
				}
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
				if (part.equals(ATreeFormEditor.this))
					site.getPage().removePartListener(this);
			}
		};
		site.getPage().addPartListener(partListener);
	}

	/**
	 * Sets the inside file changed.
	 * 
	 * @param insideFileChanged
	 *            the new inside file changed
	 */
	public void setInsideFileChanged(boolean insideFileChanged) {
		this.insideFileChanged = insideFileChanged;
	}
}
