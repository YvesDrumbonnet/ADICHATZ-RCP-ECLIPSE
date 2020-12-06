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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IItemContainerController;
import org.adichatz.engine.controller.IItemController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.engine.validation.ValidationPath;
import org.adichatz.engine.viewer.IBoundContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorBindingService.
 * 
 * @author Adichatz
 */
public class EditorBindingService extends ABindingService {

	/** The title. */
	private String title;

	/** The image. */
	private Image image;

	private AFormEditor editor;

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
	public EditorBindingService(AFormEditor editor) {
		super(editor);
		this.editor = editor;
		this.title = editor.getPartName();
		this.image = editor.getTitleImage();
		addBindingListener(new ABindingListener("EditorBindingService#DIRTY_VALIDATION", IEventType.DIRTY_VALIDATION) {
			@Override
			public void handleEvent(AdiEvent event) {
				editor.fireDirtyPropertyChange();
			}
		});
	}

	public AFormEditor getEditor() {
		return editor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getMessage()
	 */
	@Override
	public String getMessage() {
		return getFromEngineBundle("editor") + ":" + title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#getImage()
	 */
	@Override
	public Image getImage() {
		return image;
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

			IValidableController validableController = errorMessage.getValidator().getHostingControllers()[0];
			ICollectionController parentController = validableController.getParentController();
			// Stop when reaching editor controller (parent controller) or bindingService is not the same as current one
			while (null != parentController && this.equals(parentController.getBindingService())) {
				errorPath.getValidationsPaths().add(new ValidationPath(parentController));
				if (parentController instanceof CTabItemController) {
					errorPath.getValidationsPaths()
							.add(new ValidationPath(parentController.getParentController(), parentController));
					parentController = parentController.getParentController();
				} else if (parentController instanceof FormPageController) {
					FormEditorCore editorgencode = (FormEditorCore) parentController.getParentController().getGenCode();
					for (FormPageHeader formPageHeader : editorgencode.getFormPageHeaderMap().values())
						if (parentController.equals(formPageHeader.getFormPageController())) {
							errorPath.setPage(formPageHeader.getTitle());
							break;
						}
				}
				parentController = parentController.getParentController();
			}
			errorPath.setField(validableController.getValidation().getDirtyableForm().getManagedForm().getMessageManager()
					.getMessagePrefixProvider().getPrefix(validableController.getControl()));

			errorPaths.add(errorPath);
		}
		return errorPaths;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.IBindingService#activateControllers(org.adichatz.engine.validation.ErrorPath)
	 */
	@Override
	public void activateControllers(ErrorPath errorPath) {
		ValidationPath validationPath;
		LinkedList<ValidationPath> validationPaths = new LinkedList<ValidationPath>();
		validationPaths.addAll(errorPath.getValidationsPaths());
		while (null != (validationPath = validationPaths.pollLast())) {
			if (validationPath.getController() instanceof FormPageController) {
				// Active page
				((ADirtyFormEditor) boundedController).setActivePage((FormPageController) validationPath.getController());
			} else if (validationPath.getController() instanceof ATabularController<?>) {
				ATabularController<?> tableController = (ATabularController<?>) validationPath.getController();
				ATabularContentProvider<?> contentProvider = (ATabularContentProvider<?>) tableController.getViewer()
						.getContentProvider();
				Object currentSelection = ((StructuredSelection) tableController.getViewer().getSelection()).getFirstElement();

				Object validationSelection;
				if (contentProvider instanceof IBoundContentProvider<?>) {
					validationSelection = ((IBoundContentProvider<?>) contentProvider).getBeanMap()
							.get(((IEntity<?>) validationPath.getValue()).getCacheKey());
				} else
					validationSelection = validationPath.getValue();
				if (null == currentSelection || !currentSelection.equals(validationSelection)) {
					tableController.getViewer().setSelection(new StructuredSelection(new Object[] { validationSelection }));
					((IBoundedController) errorPath.getValidationsPaths().pollLast().getController()).validateFields();
				}
			} else if (validationPath.getController() instanceof IItemContainerController) {
				((IItemContainerController) validationPath.getController())
						.setSelection(((IItemController) validationPath.getValue()).getItem());
			}
		}
		validationPaths = null;
		errorPath.getErrorMessage().getValidator().getHostingControllers()[0].getControl().setFocus();
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
}
