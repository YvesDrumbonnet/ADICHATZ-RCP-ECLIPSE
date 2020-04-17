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
package org.adichatz.engine.e4.part;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IRootController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.core.AFormPageCore;
import org.adichatz.engine.e4.resource.BoundedPartChangeManager;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.PageManager;
import org.adichatz.engine.e4.resource.PartBindingService;
import org.adichatz.engine.extra.IOutlineContainerPart;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

// TODO: Auto-generated Javadoc
/**
 * The Class ABoundedPart.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("restriction")
public class BoundedPart extends AAdiBasicPart implements IRootController, IBoundedController, IOutlineContainerPart {

	/** The Part service. */
	@Inject
	protected EPartService partService;

	/** The context. */
	@Inject
	protected IEclipseContext context;

	/** The generated code. */
	private Image image;

	/** The generated code. */
	protected PartCore genCode;

	/** The input part. */
	protected AdiInputPart inputPart;

	protected Map<String, PageManager> pageManagerMap = new HashMap<String, PageManager>();

	/** The active page manager. */
	protected PageManager activePageManager;

	/** The binding service. */
	protected PartBindingService partBindingService;

	/** The binding services. */
	private List<ABindingService> bindingServices = new ArrayList<ABindingService>();

	protected IEntity<?> entity;

	protected BoundedPartChangeManager changeManager;

	@Inject
	protected Composite parent;

	private String firstPage;

	protected boolean editorToolBar;

	protected boolean hasSeveralPages = false;

	protected List<AListener> listeners;

	protected Composite container;

	@PostConstruct
	public void createControl() {
		// DO NOT CHANGE layout for parent
		inputPart = context.get(AdiInputPart.class);
		context.remove(AdiInputPart.class);
		entity = (IEntity<?>) inputPart.getParamMap().get(ParamMap.ENTITY);
		String addEditorToolBar = inputPart.getParamMap().getString(ParamMap.ADD_EDITOR_TOOLBAR);
		if (null != addEditorToolBar && addEditorToolBar.toLowerCase().equals("true"))
			editorToolBar = true;
		changeManager = new BoundedPartChangeManager(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getBindingService()
	 */
	@Override
	public PartBindingService getBindingService() {
		return partBindingService;
	}

	/**
	 * Inits the binding service.
	 * 
	 * @param partBindingService
	 *            the new binding service
	 */
	public void setBindingService(PartBindingService partBindingService) {
		this.partBindingService = partBindingService;
		bindingServices.add(partBindingService);
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
	 * Activate page.
	 * 
	 * @param pageManager
	 *            the page manager
	 */
	public void activatePage(PageManager pageManager) {
		setActivePageManager(pageManager);
		if (null == pageManager.getFormPageController()) {
			AdichatzApplication.fireListener(new ApplicationEvent(ApplicationEvent.EVENT_TYPE.POST_OPEN_PART, inputPart));
			parent.addDisposeListener((e) -> {
				for (ABindingService bindingService : bindingServices.toArray(new ABindingService[bindingServices.size()]))
					try {
						bindingService.setClosing(true);
						if (!bindingService.refreshEntities(bindingService.getUpdatedEntities().keySet())) {
							ABindingService.setForceRefreshing(false);
							throw new RuntimeException(getFromEngineBundle("error.doNotKnowHowToRefreshEntities"));
						}
						bindingService.close();
					} catch (Exception exception) {
						LogBroker.logError(exception);
					} finally {
						bindingService.setClosing(false);
					}
				AdichatzApplication.fireListener(new ApplicationEvent(ApplicationEvent.EVENT_TYPE.POST_CLOSE_PART, inputPart));
			});

			if (null == inputPart.getParamMap().get(ParamMap.BOUNDED_PART_ERROR)) {
				AFormPageCore formPageGenCode = (AFormPageCore) genCode.getPluginResources().getGencodePath().instantiateClass(
						pageManager.getClassName(), new Class[] { AdiContext.class, IContainerController.class },
						new Object[] { genCode.getContext(), this });
				pageManager.init(formPageGenCode.getController().getInitialEntity(), formPageGenCode);
				pageManager.getParent().layout();
				pageManager.getManagedForm().reflow(true);
				parent.layout();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IBoundedController#getEntity()
	 */
	@Override
	public IEntity<?> getEntity() {
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#getPluginResources()
	 */
	@Override
	public AdiPluginResources getPluginResources() {
		return inputPart.getPluginResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IContainerController#setPluginResources(org.adichatz.engine.common.AdiPluginResources)
	 */
	@Override
	public void setPluginResources(AdiPluginResources pluginResources) {
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
	 * @see org.adichatz.engine.controller.ICollectionController#getGenCode()
	 */
	@Override
	public PartCore getGenCode() {
		return genCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#getChildControllers()
	 */
	@Override
	public List<AWidgetController> getChildControllers() {
		List<AWidgetController> childControllers = new ArrayList<AWidgetController>();
		for (PageManager pageManager : pageManagerMap.values()) {
			if (null != pageManager.getFormPageController())
				childControllers.add(pageManager.getFormPageController());
		}
		return childControllers;
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
	 * @see org.adichatz.engine.controller.ICollectionController#isLocked()
	 */
	@Override
	public boolean isLocked() {
		return false;
	}

	/**
	 * Gets the active page manager.
	 * 
	 * @return the active page manager
	 */
	public PageManager getActivePageManager() {
		return activePageManager;
	}

	public void setActivePageManager(PageManager activePageManager) {
		this.activePageManager = activePageManager;
	}

	/**
	 * Gets the input part.
	 * 
	 * @return the input part
	 */
	public AdiInputPart getInputPart() {
		return inputPart;
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
	@Override
	public List<ElementaryAccessibility> getAccessibilities() {
		return null;
	}

	public Image getImage() {
		if (null == image)
			image = EngineTools.getImage(inputPart.getIconURI());
		return image;
	}

	public String getTitle() {
		return inputPart.getLabel();
	}

	@Override
	public IEclipseContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IBoundedController#setDirty(boolean)
	 */
	@Override
	public boolean setDirty(boolean dirty) {
		if (!dirty && isDirty())
			return false;
		if (dirty != inputPart.isDirty()) {
			inputPart.setDirty(dirty);
			E4AdichatzApplication.getInstance().enableToolBar(this);
			return true;
		}
		return false;
	}

	public boolean isDirty() {
		for (ABindingService bindingService : bindingServices)
			if (bindingService.isDirty())
				return true;
		return false;
	}

	@Override
	public Composite getComposite() {
		return parent;
	}

	public Map<String, PageManager> getPageManagerMap() {
		return pageManagerMap;
	}

	public void activatePage(String pageId) {
		PageManager pageManager = pageManagerMap.get(pageId);
		if (null == pageManager)
			throw new RuntimeException("Cannot activate page '" + pageId + "' for part '" + inputPart.getLabel() + "'!");
		activatePage(pageManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ICollectionController#addListener(org.adichatz.engine.listener.AListener)
	 */
	@Override
	public void addListener(AListener listener) {
		if (null == listeners)
			listeners = new ArrayList<>();
		listeners.add(listener);
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
	 * @see org.adichatz.engine.controller.IBoundedController#validateFields()
	 */
	@Override
	public void validateFields() {
	}

	@PreDestroy
	protected void preDestroy(MApplication application, EModelService modelService, EPartService partService) {
		if (isDirty())
			throw new RuntimeException("Error preDestroy");
		// Disposed when closing application
		if (!OutlinePart.getInstance().getParent().isDisposed() && null != genCode) {
			IOutlinePage outlinePage = genCode.getOutlinePage();
			if (null != outlinePage) {
				if (outlinePage.equals(OutlinePart.getInstance().getCurrentPage()))
					OutlinePart.getInstance().showPage(null);
				if (null != outlinePage.getComposite()) // Could be null when part is closed during startup process
					outlinePage.getComposite().dispose();
			}
		}
	}

	public void partActivation() {
		if (null == activePageManager) {
			genCode = (PartCore) AdichatzApplication.prepareAndInstantiateClass(inputPart.getPluginResources(), this,
					inputPart.getParamMap());
			genCode.createContents();
			genCode.injectCustomizations();
			activatePage(firstPage);
			OutlinePart.getInstance().showPage(genCode.getOutlinePage());
		} else if (!Utilities.equals(genCode.getOutlinePage(), OutlinePart.getInstance().getCurrentPage()))
			OutlinePart.getInstance().showPage(genCode.getOutlinePage());
	}

	public void fireDirtyPropertyChange() {
		E4AdichatzApplication.getInstance().enableToolBar(this);
	}

	public void addPage(PageManager pageManager) {
		pageManager.setParent(parent);
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		ScrolledForm scrolledForm = toolkit.createScrolledForm(pageManager.getParent());
		pageManager.setManagedForm(new ManagedForm(toolkit, scrolledForm));
	}

	/**
	 * Adds the page manager.
	 * 
	 * @param pageManager
	 *            the page manager
	 */
	public void addPageManager(PageManager pageManager) {
		pageManagerMap.put(pageManager.getPageId(), pageManager);
		pageManager.addPage(entity, this);
		if (null == firstPage && null != pageManager.getManagedForm()) // Page creation can be postponed
			firstPage = pageManager.getPageId();
	}

	public BoundedPartChangeManager getBoundedPartChangeManager() {
		return changeManager;
	}

	@Persist
	public void persist() {
		changeManager.doSave();
	}

	public void setSeveralPages(boolean severalPages) {
		this.hasSeveralPages = severalPages;
	}

	public void addChildController(AWidgetController controller) {
	};

	public boolean hasEditorToolBar() {
		return editorToolBar;
	}

	public void close() {
		inputPart.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
		partService.hidePart(inputPart, true);
		BoundedPartFatalError fatalError = (BoundedPartFatalError) inputPart.getParamMap().get(ParamMap.BOUNDED_PART_ERROR);
		if (null != fatalError) {
			fatalError.displayError();
		}
	}

	/**
	 * Gets the part service.
	 *
	 * @return the part service
	 */
	public EPartService getPartService() {
		return partService;
	}

	/**
	 * Post create.
	 * 
	 * Launch list of Post create listener on the part (only once), listener are destroy after.<br>
	 * postCreate is called from PartToBeRenderedAddOn
	 */
	public void postCreate() {
		if (null != listeners) {
			AListener.fireListener(listeners, new AdiEvent(IEventType.POST_CREATE_PART));
			for (AListener listener : listeners.toArray(new AListener[listeners.size()]))
				if (IEventType.POST_CREATE_PART == listener.eventType)
					listeners.remove(listener);
			if (listeners.isEmpty())
				listeners = null;
		}
	}

	public void fireOutlineListener(OutlineEvent event) {
		OutlinePart.getInstance().getCurrentPage().fireListener(event);
	}

	@Override
	public Composite getContainer() {
		return container;
	}
}
