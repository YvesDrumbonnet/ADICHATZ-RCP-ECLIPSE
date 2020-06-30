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
package org.adichatz.engine.indigo.editor;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.APageController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IRootController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

// TODO: Auto-generated Javadoc
/**
 * The Class AFormEditor.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AFormEditor extends FormEditor implements IRootController {

	/** the plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The parent composite. */
	protected Composite parent;

	/** The generated code. */
	protected FormEditorCore genCode;

	/** The editor input. */
	// protected EditorInput editorInput;

	/** The children controller. */
	protected List<AWidgetController> childrenController = new ArrayList<AWidgetController>();

	/** The entity. */
	protected IEntity<?> entity;

	/** The plugin entity. */
	protected PluginEntity<?> pluginEntity;

	/** The binding service. */
	protected EditorBindingService editorBindingService;

	/** The binding services. */
	private List<ABindingService> bindingServices = new ArrayList<ABindingService>();

	/** The part input. */
	protected Object partInput;

	/** The outline page class name. */
	protected String outlinePageClassName;

	/** The outline page. */
	protected IAdiOutlinePage outlinePage;

	/**
	 * Sets the active page.
	 *
	 * @param formPageController
	 *            the new active page
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.IEditorController#setActivePage(org.adichatz.engine.controller.collection.FormPageController )
	 */
	public void setActivePage(APageController formPageController) {
		for (FormPageHeader formPageHeader : genCode.getFormPageHeaderMap().values())
			if (formPageController.equals(formPageHeader.getFormPageController())) {
				setActivePage(formPageHeader.getPageIndex());
				break;
			}
	}

	/**
	 * Gets the active page controller.
	 * 
	 * @return the active page controller
	 */
	public APageController getActivePageController() {
		int pageIndex = getActivePage();
		for (FormPageHeader formPageHeader : genCode.getFormPageHeaderMap().values())
			if (null != formPageHeader.getPageIndex() && pageIndex == formPageHeader.getPageIndex())
				return formPageHeader.getFormPageController();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#createPageContainer(org.eclipse .swt.widgets.Composite)
	 */
	@Override
	protected Composite createPageContainer(Composite parent) {
		this.parent = super.createPageContainer(parent);
		launchEditor();
		this.parent.setFocus();
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getEntity()
	 */
	public IEntity<?> getEntity() {
		return entity;
	}

	@Override
	public PluginEntity<?> getPluginEntity() {
		// Useful for authorization process only.
		// Not implemented here. If choice changed, set PluginEntity thru getTreeWrapper method
		return null;
	}

	/**
	 * Launch editor.
	 */
	protected abstract void launchEditor();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime. IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
	}

	/**
	 * Close binding services.
	 * 
	 * @return true, if successful
	 */
	public void closeBindingServices() {
		// toArray needed to avoid ConcurrentModificationException
		for (ABindingService bindingService : bindingServices.toArray(new ABindingService[bindingServices.size()]))
			bindingService.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#isDirty()
	 */
	@Override
	public boolean isDirty() {
		for (ABindingService bindingService : bindingServices)
			if (bindingService.isDirty())
				return true;
		return false;
	}

	/**
	 * Gets the binding services.
	 * 
	 * @return the binding services
	 */
	public List<ABindingService> getBindingServices() {
		return bindingServices;
	}

	/**
	 * Inits the binding service.
	 * 
	 * @param editorBindingService
	 *            the new binding service
	 */
	public void setBindingService(EditorBindingService editorBindingService) {
		this.editorBindingService = editorBindingService;
		bindingServices.add(editorBindingService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IEditorController#fireDirtyPropertyChange()
	 */
	/**
	 * Fire dirty property change.
	 */
	public void fireDirtyPropertyChange() {
	}

	/**
	 * Sets the dirty.
	 * 
	 * @param dirty
	 *            the new dirty
	 * @return true, if successful
	 */
	public boolean setDirty(boolean dirty) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getChildControllers()
	 */
	@Override
	public List<AWidgetController> getChildControllers() {
		return childrenController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.ICollectionController#addChildController(org.adichatz.engine.controller.AWidgetController)
	 */
	@Override
	public void addChildController(AWidgetController controller) {
		childrenController.add(controller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getPluginResources()
	 */
	@Override
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}

	/**
	 * Sets the outline page class name.
	 * 
	 * @param outlinePageClassName
	 *            the new outline page class name
	 */
	public void setOutlinePageClassName(String outlinePageClassName) {
		this.outlinePageClassName = outlinePageClassName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (null != outlinePageClassName && adapter.equals(IContentOutlinePage.class)) {
			if (outlinePage != null) {
				if (outlinePage.getControl() != null && outlinePage.getControl().isDisposed()) {
					outlinePage = null;
				} else {
					return outlinePage;
				}
			}
			outlinePage = (IAdiOutlinePage) ReflectionTools.instantiateClass(outlinePageClassName, new Class[] { Object.class },
					new Object[] { partInput });
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getComposite()
	 */
	@Override
	public Composite getComposite() {
		return parent;
	}

	/**
	 * Gets the outline page.
	 * 
	 * @return the outline page
	 */
	public IAdiOutlinePage getOutlinePage() {
		return outlinePage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getGenCode()
	 */
	@Override
	public FormEditorCore getGenCode() {
		return genCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getParentController()
	 */
	@Override
	public ICollectionController getParentController() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#isLocked()
	 */
	@Override
	public boolean isLocked() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IEditorController#getBindingService()
	 */
	public ABindingService getBindingService() {
		return editorBindingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getControl()
	 */
	@Override
	public Object getControl() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getAccessibilities()
	 */
	public List<ElementaryAccessibility> getAccessibilities() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IBoundedController#lockFieldControllers(boolean)
	 */
	@Override
	public void lockFieldControllers(boolean locked) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IBoundedController#displayValidation()
	 */
	@Override
	public void validateFields() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#addListener(org.adichatz.engine.listener.AListener)
	 */
	@Override
	public void addListener(AListener listener) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IRootController#getImage()
	 */
	@Override
	public Image getImage() {
		return getTitleImage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#getTitle()
	 */
	@Override
	public String getTitle() {
		return getPartName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IRootController#fireOutlineListener(org.adichatz.engine.extra.OutlineEvent)
	 */
	public void fireOutlineListener(OutlineEvent event) {
	}

	/**
	 * Checks if is enabled.
	 * 
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Sets the enabled.
	 * 
	 * @param enabled
	 *            the new enabled
	 */
	public void setEnabled(boolean enabled) {
	}
}
