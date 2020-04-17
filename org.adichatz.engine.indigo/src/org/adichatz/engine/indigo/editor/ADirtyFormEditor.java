/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.engine.indigo.editor;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.indigo.controller.LegacyFormPageController;
import org.adichatz.engine.extra.AbortChangesDialog;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorMessage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityFormEditor.
 */
public abstract class ADirtyFormEditor extends AFormEditor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime. IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		for (ABindingService bindingService : getBindingServices())
			if (!bindingService.getErrorMessageMap().isEmpty()) {
				String container = getFromEngineBundle("editor");
				String message = getFromEngineBundle("error.validationErrorEncountered", container, getTitle());
				LogBroker.displayError(getFromEngineBundle("error.encounteredErrors"), message);
				throw new RuntimeException(message);
			}
		doSave();
	}

	/**
	 * Do save.
	 */
	public void doSave() {
		int okBindingService = 0;
		for (ABindingService bindingService : getBindingServices()) {
			if (bindingService.saveEntities()) {
				okBindingService++;
			}
		}
		if (getBindingServices().size() == okBindingService) {
			setDirty(false);
			validateFields();
		} else
			throw new RuntimeException(getFromEngineBundle("error.doNotKnowHowToSaveEntities"));
	}

	/**
	 * Aborting changes.
	 * 
	 * @param updatedEntities
	 *            the updated entities
	 * 
	 * @return true, if successful
	 */
	protected boolean abortingChanges(Set<IEntity<?>> updatedEntities) {
		if (updatedEntities.isEmpty())
			return true;
		activate();
		AbortChangesDialog dialog = new AbortChangesDialog(updatedEntities, getPluginResources());
		return (IDialogConstants.NO_ID == dialog.open());
	}

	/**
	 * Activate.
	 */
	protected void activate() {
	}

	/**
	 * Do refresh.
	 */
	public void doRefresh() {
		if (abortingChanges(getBindingService().getUpdatedEntities().keySet())) {
			Set<AEntityManagerController> containerControllers = new HashSet<AEntityManagerController>();
			for (final ErrorMessage errorMessage : getBindingService().getErrorMessageMap().getErrorMessages()) {
				IValidableController controller = errorMessage.getValidator().getTriggeringController();
				if (controller instanceof AEntityManagerController)
					containerControllers.add((AEntityManagerController) controller);
				else
					containerControllers.add((AEntityManagerController) controller.getParentController());
			}

			if (editorBindingService.refreshEntities(getBindingService().getEntities())) {
				// getBindingService().clearMessages();
				setDirty(false);
			}
			for (AEntityManagerController controller : containerControllers)
				controller.validateFields();
		}
	}

	public boolean closeEditor() {
		Set<IEntity<?>> updateEntities = getBindingService().getUpdatedEntities().keySet();
		if (abortingChanges(updateEntities) && editorBindingService.refreshEntities(updateEntities)) {
			closeBindingServices();
			if (null != getBindingService())
				getBindingService().close();
			super.close(false);
			return true;
		}
		return false;
	}

	/**
	 * Checks if is saveable.
	 * 
	 * @return true, if is saveable
	 */
	public boolean isSaveable() {
		return null != getBindingService() && getBindingService().getErrorMessageMap().isEmpty() && getBindingService().isDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.IEditorController#fireDirtyPropertyChange()
	 */
	@Override
	public void fireDirtyPropertyChange() {
		firePropertyChange(PROP_DIRTY);
	}

	/**
	 * Sets the dirty.
	 * 
	 * @param dirty
	 *            the new dirty
	 * @return true, if successful
	 */
	public boolean setDirty(boolean dirty) {
		if (!dirty && isDirty())
			return false;
		firePropertyChange(PROP_DIRTY);
		return true;
	}

	/**
	 * Gets the active page controllers.
	 * 
	 * @return the active page controllers
	 */
	public List<LegacyFormPageController> getActivePageControllers() {
		List<LegacyFormPageController> activePageControllers = new ArrayList<LegacyFormPageController>();
		for (Object adiPage : pages)
			if (null != adiPage && adiPage instanceof AdiFormPage && null != ((AdiFormPage) adiPage).getFormPageController())
				activePageControllers.add(((AdiFormPage) adiPage).getFormPageController());
		return activePageControllers;
	}

}
