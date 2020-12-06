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
package org.adichatz.engine.controller.collection;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.common.DelayedThread;
import org.adichatz.engine.common.InjectionInspector;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IControlController;
import org.adichatz.engine.controller.IItemController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.ADataCache;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.engine.validation.ValidationPath;
import org.adichatz.engine.widgets.CompositeBag;
import org.eclipse.osgi.internal.loader.EquinoxClassLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IMessageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CompositeController.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("restriction")
public class CompositeBagController extends AEntityManagerController implements IControlController {

	/** The composite bag. */
	private CompositeBag compositeBag;

	/** The bag selection map. */
	private Map<ICollectionController, AEntityManagerController> bagSelectionMap = new HashMap<ICollectionController, AEntityManagerController>();

	private AEntityManagerController childController;

	/**
	 * Instantiates a new composite controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public CompositeBagController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		breakInjection = true; // Breaks chain of Entity Injection;
	}

	/**
	 * Creates the control.
	 * 
	 */
	public void createControl() {
		super.createControl();
		compositeBag = toolkit.createCompositeBag(parentController.getComposite(), SWT.NONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Composite getControl() {
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AEntityManagerController#endLifeCycle()
	 */
	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		if (isValid())
			compositeBag.showComposite(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.ACollectionController#addChildController(org.adichatz.engine.controller.AWidgetController)
	 */
	@Override
	public void addChildController(AWidgetController controller) {
		if (!(controller instanceof AEntityManagerController))
			super.addChildController(controller);
	}

	/**
	 * Gets the selected entity.
	 * 
	 * @param masterController
	 *            the master controller
	 * @return the selected entity
	 */
	protected IEntity<?> getSelectedEntity(ICollectionController masterController) {
		IEntity<?> selectedEntity = null;
		if (masterController instanceof ASetController && !((ASetController) masterController).getControl().isDisposed()) {
			ASetController setController = (ASetController) masterController;
			Object bean = ((ASetController) masterController).getSelectedObject();
			if (null != bean) {
				// 'getEntity().getEntityMM()...' doesn't work because Entity is not yet implemented
				// search entityMM in default pluginResources. If not found search in all adichatz plugins.
				AdiPluginResources modelPR = AdichatzApplication
						.getPluginResources(((EquinoxClassLoader) bean.getClass().getClassLoader()).getBundle().getSymbolicName());
				AEntityMetaModel<?> entityMM = null;
				Class<?> entityClass = bean.getClass();
				try {
					entityMM = modelPR.getPluginEntityTree().getEntityMM(entityClass);
				} catch (Exception e) {
					for (AdiPluginResources pluginResources : AdichatzApplication.getInstance().getPluginMap().values()) {
						try {
							entityMM = pluginResources.getPluginEntityTree().getEntityMM(entityClass);
							break;
						} catch (Exception e2) {
						}

					}
				}
				if (null != entityMM) {
					ADataCache dataCache = entityMM.getDataAccess().getDataCache();
					Set<String> lazyFetchMembers = setController.getLazyFetchMembers();
					selectedEntity = dataCache.fetchEntity(bean, setController.getBindingService(),
							lazyFetchMembers.toArray(new String[lazyFetchMembers.size()]));
				} else
					throw new AdichatzErrorException(getFromEngineBundle("noEntityModelFoundForClass", entityClass));
			}
		}
		return selectedEntity;
	}

	/**
	 * Gets the master controller.
	 * 
	 * @param collectionController
	 *            the collection controller
	 * @return the master controller
	 */
	private ICollectionController getMasterController(ICollectionController collectionController) {
		for (AWidgetController controller : collectionController.getChildControllers()) {
			if (controller instanceof AEntityManagerController) {
				if (bagSelectionMap.containsKey((AEntityManagerController) controller))
					return (ICollectionController) controller;
				ICollectionController masterController = getMasterController((ICollectionController) controller);
				if (null != masterController)
					return masterController;
			}
		}
		return null;
	}

	/**
	 * Show child controller.
	 * 
	 * @param itemController
	 *            the item controller
	 */
	public void showChildController(IItemController itemController) {
		ABindingService bindingService = itemController.getBindingService();
		ICollectionController parentCtlr = itemController.getParentController();
		for (ErrorPath errorPath : bindingService.getErrorPaths()) {
			for (ValidationPath validationPath : errorPath.getValidationsPaths()) {
				if (validationPath.getController().equals(parentCtlr)) {
					IMessageManager messageManager = null;
					while (null == messageManager && null != parentCtlr) {
						if (parentCtlr instanceof FormPageController) {
							messageManager = ((FormPageController) parentCtlr).getManagedForm().getMessageManager();
							break;
						}
						parentCtlr = parentCtlr.getParentController();
					}
					if (null != messageManager) {
						for (IValidableController controller : errorPath.getErrorMessage().getValidator().getHostingControllers()) {
							messageManager.removeMessages(controller.getControl());
							break;
						}
					}
				}
			}
		}

		ICollectionController masterController = getMasterController(itemController);
		if (null != masterController) {
			showChildController(masterController, bagSelectionMap.get(masterController));
			return;
		}
		compositeBag.showComposite(null);
	}

	/**
	 * Show child controller.
	 * 
	 * @param masterController
	 *            the master controller
	 * @param childController
	 *            the child controller
	 */
	public void showChildController(final ICollectionController masterController, final AEntityManagerController childController) {
		this.childController = childController;
		IEntity<?> currentEntity = getSelectedEntity(masterController);
		childController.checkbind(currentEntity);
		if (masterController instanceof ASetController && ((ASetController) masterController).doesSelectInNewThread()) {
			DelayedThread.newDelayedThread(new MultiKey(masterController.getControl(), "#COMPOSITE_BAG#"), 50, () -> {
				showChild(masterController, childController);
			});
		} else
			showChild(masterController, childController);
	}

	/**
	 * Show child.
	 * 
	 * @param masterController
	 *            the master controller
	 * @param childController
	 *            the child controller
	 */
	protected void showChild(ICollectionController masterController, AEntityManagerController childController) {
		InjectionInspector.inject(childController); // due to css theme possible changes
		bagSelectionMap.put(masterController, childController);
		IEntity<?> selectedEntity = getSelectedEntity(masterController);
		if (!Utilities.equals(selectedEntity, childController.getEntity())) {
			if (null != childController.getEntityInjection()) {
				childController.disposeEntityInjections();
			}

			if (null != selectedEntity) {
				new EntityInjection(childController, selectedEntity).setLocked(!selectedEntity.isLocked());
				/*
				 * Prevent that synchronization was done twice else creation process is not good
				 */
				if (null == childController.getControl()) {
					childController.initialize();
					childController.startLifeCycle();
				}
				childController.endLifeCycleAndSync();
				AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, childController));
				showChildComposite(childController);
			} else {
				EntityInjection entityInjection = childController.getEntityInjection();
				if (null != entityInjection)
					entityInjection.initialize(null);
				compositeBag.showComposite(null);
			}
		} else if (null == selectedEntity && !compositeBag.getDefaultComposite().equals(composite))
			compositeBag.showComposite(null);
		else if (null != childController.getControl() && !childController.getControl().isDisposed()
				&& !childController.getControl().isVisible()) {
			childController.validateFields();
			showChildComposite(childController);
		}
	}

	/**
	 * Show child composite.
	 * 
	 * @param childController
	 *            the child controller
	 */
	private void showChildComposite(AEntityManagerController childController) {
		compositeBag.showComposite((Composite) childController.getControl());
		childController.reflowControllers();
		compositeBag.getScrolledComposite().reflow(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#getComposite()
	 */
	@Override
	public Composite getComposite() {
		return null == compositeBag ? null : compositeBag.getContent();
	}

	/**
	 * Gets the current master controller.
	 * 
	 * @param childController
	 *            the child controller
	 * @return the current master controller
	 */
	public ICollectionController getCurrentMasterController(ICollectionController childController) {
		if (childController instanceof IncludeController) {
			for (AWidgetController controller : ((IncludeController) childController).getChildControllers()) {
				if (controller instanceof ICollectionController)
					for (Map.Entry<ICollectionController, AEntityManagerController> entry : bagSelectionMap.entrySet()) {
						if (entry.getValue().equals(controller))
							return entry.getKey();
					}
			}
		} else
			for (Map.Entry<ICollectionController, AEntityManagerController> entry : bagSelectionMap.entrySet()) {
				if (entry.getValue().equals(childController))
					return entry.getKey();
			}
		return null;
	}

	@Override
	public List<AWidgetController> getChildControllers() {
		List<AWidgetController> controllers = super.getChildControllers();
		if (null == childController)
			return controllers;
		List<AWidgetController> childControllers = new ArrayList<AWidgetController>(controllers.size() + 1);
		childControllers.addAll(controllers);
		childControllers.add(childController);
		return childControllers;
	}

	public Map<ICollectionController, AEntityManagerController> getBagSelectionMap() {
		return bagSelectionMap;
	}
}
