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

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.indigo.editor.IAdiOutlinePage;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.studio.xjc.controller.AdiResourceUriTextController;
import org.adichatz.studio.xjc.controller.OutlineHyperlinkController;
import org.adichatz.studio.xjc.controller.ProposalTextController;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcDataCache;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.Page;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class StudioOutlinePage.
 */
public class StudioOutlinePage extends Page implements IAdiOutlinePage {

	/** The child controller. */
	private AWidgetController childController;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	/** The managed form. */
	private ManagedForm managedForm;

	/** The current entity. */
	private XjcEntity<?> entity;

	/** The tool composite. */
	private Composite toolComposite;

	/** The detail gencode. */
	private ControllerCore detailGencode;

	/** The toolkit. */
	private AdiFormToolkit toolkit;

	/** The tool container (for XjcToolPage). */
	private ContainerController toolContainer;

	/** The page composite. */
	private Composite pageComposite;

	/** The set controller. */
	private ASetController setController;

	/** The refresh outline. */
	private Action refreshOutline;

	private ImageManager imageManager = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getImageManager();

	private AEntityManagerController parentController;

	private ICollectionController rootController;

	private boolean editable;

	private boolean reedit;

	public StudioOutlinePage(AStudioFormEditor editor) {
		editable = editor.isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		if (null == setController || null == setController.getViewer())
			return null;
		return setController.getViewer().getSelection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.
	 * ISelectionChangedListener )
	 */
	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.Page#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		pageComposite = toolkit.createComposite(parent);
		pageComposite.setLayout(new FillLayout());

		scrolledForm = new ScrolledForm(pageComposite, SWT.NONE);
		scrolledForm.setExpandHorizontal(true);
		scrolledForm.setExpandVertical(true);
		scrolledForm.setFont(JFaceResources.getHeaderFont());

		managedForm = new ManagedForm(toolkit, scrolledForm);
		toolComposite = scrolledForm.getBody();
		toolComposite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

		/*
		 * Add refresh element action
		 */
		IToolBarManager toolBarManager = getSite().getActionBars().getToolBarManager();
		refreshOutline = new Action() {
			@Override
			public void run() {
				if (IEntityConstants.RETRIEVE != entity.getStatus()) {
					int status = entity.getStatus();
					((XjcBindingService) setController.getBindingService()).refreshEntities(entity);
					if (IEntityConstants.PERSIST == status) { // Used only for XjcTreeController
						TreePath parentPath = ((ITreeSelection) setController.getViewer().getSelection()).getPaths()[0]
								.getParentPath();
						XjcTreeElement parentElement = (XjcTreeElement) parentPath.getLastSegment();
						((XjcTreeController) setController).refresh(parentElement);
						parentElement.getEntity().putStatus(IEntityConstants.MERGE, true);
						((TreeViewer) setController.getViewer()).expandToLevel(parentElement, 1);
						setController.getViewer().setSelection(new TreeSelection(parentPath));
					} else
						setController.getViewer().setSelection(setController.getViewer().getSelection());
				}
			}
		};
		refreshOutline.setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_REFRESH"));
		refreshOutline.setToolTipText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio",
				"studio.editor.refreshElement"));
		refreshOutline.setEnabled(false);
		toolBarManager.add(refreshOutline);
		toolBarManager.update(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.Page#getControl()
	 */
	@Override
	public Control getControl() {
		return pageComposite;
	}

	/**
	 * Gets the detail gencode.
	 * 
	 * @return the detail gencode
	 */
	public ControllerCore getDetailGencode() {
		return detailGencode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.Page#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void setSelection(ISelection selection) {
		setSelection((ASetController) null, (XjcEntity<?>) null, false);
	}

	public void setSelection(ASetController setController, ISelection selection) {
		XjcDataCache dataCache = (XjcDataCache) setController.getPluginResources().getDataAccess().getDataCache();
		Object bean = ((IStructuredSelection) selection).getFirstElement();
		MultiKey cacheKey = new MultiKey(bean);
		setSelection(setController, dataCache.fetchEntity(bean, cacheKey), false);
	}

	/**
	 * Sets the selection.
	 * 
	 * @param setController
	 *            the set controller
	 * @param selection
	 *            the selection
	 */
	public void setSelection(ASetController setController, XjcEntity<?> entity, boolean shortValidation) {
		this.entity = entity;
		if (null != entity)
			entity.setSetController(setController);
		this.setController = setController;
		/*
		 * Use only one page (removing previous page) to force validators to be fired during display process each time
		 */
		if (null != toolContainer) {

			XjcBindingService xjcBindingService = (XjcBindingService) toolContainer.getBindingService();
			xjcBindingService.changeEntity(toolContainer.getEntity());
			toolContainer.disposeControl();
			toolContainer = null;
			if (null == scrolledForm || scrolledForm.isDisposed())
				// scrolledForm is null when outline is closed after editor was open
				return;
			EngineTools.reinitMiglayout(toolComposite);
		}
		if (null == scrolledForm || scrolledForm.isDisposed())
			return;

		if (null == entity) {
			scrolledForm.setVisible(false);
			scrolledForm.setImage(null);
			scrolledForm.setText("");
			refreshOutline.setEnabled(false);
		} else {
			scrolledForm.setVisible(true);
			scrolledForm.setRedraw(false);
			scrolledForm.getToolBarManager().update(true);
			toolContainer = new ContainerController(setController.getPluginResources(), managedForm, toolComposite, "toolPart",
					null);

			toolContainer.setMessageManager(true);
			toolContainer.setBindingService(setController.getBindingService());

			String treeId = entity.getEntityMM().getEntityId().concat("Part");
			detailGencode = (ControllerCore) setController.getPluginResources().getNewInstance(treeId, "detail", toolContainer,
					null);

			// Specific container.postCreate(managedEntity)
			if (null == toolContainer.getEntityInjection())
				new EntityInjection(toolContainer, entity);
			else
				toolContainer.getEntityInjection().initialize(entity);
			toolContainer.initialize();
			toolContainer.startLifeCycle();

			childController = toolContainer.getChildControllers().get(0);
			parentController = null;
			if (childController instanceof PShelfController) {
				((PShelfController) childController).getControl().addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						PShelfController pshelfController = (PShelfController) childController;
						if (!pshelfController.isDisposing()) {
							PShelfItemController pshelfItemController = pshelfController.getSelectedController();
							rootController = pshelfItemController;
							if (!editable) {
								disableColumnControllers(rootController, false);
							}
							parentController = (AEntityManagerController) pshelfItemController.getChildControllers().get(0);
							Event event = new Event();
							event.gc = new GC(parentController.getControl());
							parentController.getControl().notifyListeners(SWT.Paint, event);
							pshelfItemController.getControl().layout();
						}
					}
				});
			} else {
				parentController = (AEntityManagerController) childController;
			}
			/*
			 * setImage and text before endLifeCycle elsewhere there is no available space for error in FormHeading
			 * form.getHead().getBounds().height == 0 in org.eclipse.ui.internal.forms.MessageManager.update(ArrayList)
			 */
			String imageKey = entity.getEntityMM().getPluginEntity().getImageKeyMap().get("ENTITY");
			Image image = (null == imageKey) ? null : imageManager.getImageDescriptor(imageKey).createImage();
			scrolledForm.setImage(image);
			scrolledForm.setText(entity.getEntityMM().getEntityId());

			toolContainer.setShortValidation(shortValidation);
			toolContainer.endLifeCycleAndSync();

			((XjcBindingService) setController.getBindingService()).updateSet(setController, entity);
			if (null != XjcFieldViewPart.getInstance())
				XjcFieldViewPart.getInstance().displayHelp(toolContainer, false);

			if (!editable && !(childController instanceof PShelfController)) {
				disableColumnControllers(parentController, false);
			}

			scrolledForm.setRedraw(true);
			scrolledForm.layout();
			parentController.reflowControllers();
			if (0 == scrolledForm.getBody().getChildren()[0].getSize().x)
				EngineTools.reinitMiglayout(toolComposite);
		}
		refreshOutline.setEnabled(editable && null != entity);
	}

	public void enable(boolean editable) {
		if (editable && !pageComposite.isEnabled())
			pageComposite.setEnabled(true);
		if (this.editable != editable) {
			this.editable = editable;
			refreshOutline.setEnabled(editable);
			if (null != rootController && !((Widget) rootController.getControl()).isDisposed())
				disableColumnControllers(rootController, editable);
		}
	}

	public void disableColumnControllers(boolean enabled) {
		ICollectionController collectionController = (null == rootController || null == rootController.getControl()
				|| ((Control) rootController.getControl()).isDisposed()) ? parentController : rootController;
		if (null != collectionController)
			disableColumnControllers(collectionController, enabled);
	}

	private void disableColumnControllers(ICollectionController collectionController, boolean enabled) {
		for (AWidgetController controller : collectionController.getChildControllers()) {
			if (null != controller.getControl()) {
				if (controller instanceof AFieldController) {
					if (null != ((AFieldController) controller).getProperty()) {
						((Control) controller.getControl()).setEnabled(enabled);
						if (!enabled)
							if (controller instanceof ProposalTextController)
								((ProposalTextController) controller).disposeTextProposal();
							else if (controller instanceof AdiResourceUriTextController)
								((AdiResourceUriTextController) controller).disposeProposal();
					} else if (controller instanceof OutlineHyperlinkController) {
						// HyperlinkController hyperlinkController = (HyperlinkController) controller;
						//						if (enabled && null != hyperlinkController.getLinkedController()) {
						//							AControlController controlController = hyperlinkController.getLinkedController();
						//							if (!((Control) collectionController.getControl()).isEnabled())
						//								((Control) controller.getControl()).setEnabled(enabled);
						//						} else
						Hyperlink hyperlink = ((OutlineHyperlinkController) controller).getControl();
						Object enabledFlag = hyperlink.getData(OutlineHyperlinkController.ENABLED_FLAG);
						if (null == enabledFlag || !enabled)
							hyperlink.setEnabled(enabled);
					}
				} else if (controller instanceof ICollectionController) {
					disableColumnControllers((ICollectionController) controller, enabled);
				}
			}
		}
	}

	/**
	 * Gets the tool container.
	 * 
	 * @return the tool container
	 */
	public ContainerController getToolContainer() {
		return toolContainer;
	}

	@Override
	public PShelfItem getFilterItem() {
		return null;
	}
}
