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
package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IItemContainerController;
import org.adichatz.engine.controller.IItemController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.controller.collection.CompositeBagController;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.engine.validation.ValidationPath;
import org.adichatz.engine.viewer.IBoundContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

// TODO: Auto-generated Javadoc
/**
 * The Class PartBindingService.
 * 
 * @author Adichatz
 */
@SuppressWarnings("restriction")
public class PartBindingService extends ABindingService {

	private BoundedPart boundedPart;

	/**
	 * Instantiates a new editor binding service.
	 * 
	 * @param title
	 *            the title
	 * @param tooltipText
	 *            the tooltip text
	 * @param image
	 *            the image
	 */
	public PartBindingService(BoundedPart boundedPart) {
		super(boundedPart);
		this.boundedPart = boundedPart;
		addBindingListener(new ABindingListener("BoundedPart#DIRTY_VALIDATION", IEventType.DIRTY_VALIDATION) {
			@Override
			public void handleEvent(AdiEvent event) {
				// needed to fire method updateState() on Error, save and refresh actions
				((BoundedPart) boundedController).fireDirtyPropertyChange();
			}
		});
	}

	public BoundedPart getEditor() {
		return boundedPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getTitle()
	 */
	@Override
	public String getTitle() {
		return boundedPart.getInputPart().getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getMessage()
	 */
	@Override
	public String getMessage() {
		return getFromEngineBundle("editor").concat(":").concat(boundedPart.getInputPart().getLabel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getImage()
	 */
	@Override
	public Image getImage() {
		return (Image) boundedPart.getInputPart().getParamMap().get(ParamMap.IMAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.IBindingService#getErrorPaths()
	 */
	@Override
	public List<ErrorPath> getErrorPaths() {
		List<ErrorPath> errorPaths = new ArrayList<ErrorPath>();
		for (final ErrorMessage errorMessage : getErrorMessageMap().getErrorMessages()) {
			ErrorPath errorPath = new ErrorPath(errorMessage);
			String pageTitle = "";

			IValidableController validableController = errorMessage.getValidator().getHostingControllers()[0];
			ICollectionController parentController = validableController.getParentController();
			ICollectionController childController = null;
			// Stop when reaching editor controller (parent controller) or bindingService is not the same as current one
			while (null != parentController && this.equals(parentController.getBindingService())) {
				errorPath.getValidationsPaths().add(new ValidationPath(parentController));
				if (parentController instanceof CTabItemController) {
					errorPath.getValidationsPaths()
							.add(new ValidationPath(parentController.getParentController(), parentController));
					parentController = parentController.getParentController();
				} else if (parentController instanceof CompositeBagController) {
					CompositeBagController compositeBagController = (CompositeBagController) parentController;
					ICollectionController masterController = compositeBagController.getCurrentMasterController(childController);
					if (null != masterController) {
						errorPath.getValidationsPaths().add(new ValidationPath(masterController, errorMessage.getEntity()));
						parentController = masterController;
					}
				} else if (parentController instanceof FormPageController) {
					BoundedPart boundedPart = (BoundedPart) parentController.getParentController();
					PageManager pageManager = boundedPart.getPageManagerMap()
							.get(((FormPageController) parentController).getRegisterId());
					if (null == pageManager)
						pageManager = boundedPart.getActivePageManager();
					pageTitle = pageManager.getTitle();
				}
				childController = parentController;
				parentController = parentController.getParentController();
			}
			errorPath.setField(validableController.getValidation().getDirtyableForm().getManagedForm().getMessageManager()
					.getMessagePrefixProvider().getPrefix(validableController.getControl()));
			errorPath.setPage(pageTitle);

			errorPaths.add(errorPath);
		}
		return errorPaths;
	}

	@Override
	public boolean saveEntities() {
		if (super.saveEntities()) {
			fireListener(IEventType.CHANGE_STATUS);
			return true;
		}
		return false;
	}

	@Override
	public boolean refreshEntities(IEntity<?>... entities) {
		if (super.refreshEntities(entities)) {
			fireListener(IEventType.CHANGE_STATUS);
			return true;
		}
		return false;
	}

	public void activateControllers(ErrorPath errorPath) {
		ValidationPath validationPath;
		LinkedList<ValidationPath> validationPaths = new LinkedList<ValidationPath>();
		validationPaths.addAll(errorPath.getValidationsPaths());
		while (null != (validationPath = validationPaths.pollLast())) {
			if (validationPath.getController() instanceof FormPageController) {
				// Active page
				boundedPart.activatePage(((FormPageController) validationPath.getController()).getRegisterId());
			} else if (validationPath.getController() instanceof ATabularController<?>) {
				ATabularController<?> tableController = (ATabularController<?>) validationPath.getController();
				ATabularContentProvider<?> contentProvider = (ATabularContentProvider<?>) tableController.getViewer()
						.getContentProvider();
				Object validationSelection = null;
				if (contentProvider instanceof IBoundContentProvider<?>) {
					IEntity<?> entity = (IEntity<?>) validationPath.getValue();
					Object beanId = entity.getBeanId();
					AEntityMetaModel<?> entityMM = entity.getEntityMM();
					for (Object bean : tableController.getQueryManager().getQueryResult().getQueryResultList()) {
						if (beanId.equals(entityMM.getId(bean))) {
							validationSelection = bean;
							break;
						}
					}
					if (null == validationSelection) {
						EngineTools.openDialog(MessageDialog.INFORMATION, getFromEngineBundle("error.bean.not.found.title"),
								getFromEngineBundle("error.bean.not.found.message"));
						return;
					}
				} else
					validationSelection = validationPath.getValue();
				StructuredSelection structuredSelection = new StructuredSelection(new Object[] { validationSelection });
				tableController.getViewer().setSelection(structuredSelection);
			} else if (validationPath.getController() instanceof IItemContainerController) {
				((IItemContainerController) validationPath.getController())
						.setSelection(((IItemController) validationPath.getValue()).getItem());
			}
		}
		validationPaths = null;
		Control control = errorPath.getErrorMessage().getValidator().getHostingControllers()[0].getControl();
		control.setFocus();
		control.setFocus(); // Set focus twice because first time, focus is lost.
	}
}
