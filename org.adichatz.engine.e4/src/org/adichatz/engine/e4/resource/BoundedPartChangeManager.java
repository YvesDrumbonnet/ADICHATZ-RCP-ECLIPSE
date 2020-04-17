package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.extra.AbortChangesDialog;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorMessage;
import org.eclipse.jface.dialogs.IDialogConstants;

public class BoundedPartChangeManager {
	BoundedPart boundedPart;

	public BoundedPartChangeManager(BoundedPart boundedPart) {
		this.boundedPart = boundedPart;
	}

	public boolean isSaveable() {
		if (!hasError()) {
			for (ABindingService bindingService : boundedPart.getBindingServices())
				if (bindingService.isDirty())
					return true;
		}
		return false;
	}

	public boolean hasError() {
		for (ABindingService bindingService : boundedPart.getBindingServices())
			if (!bindingService.getErrorMessageMap().isEmpty())
				return true;
		return false;
	}

	public Set<IEntity<?>> getUpdatedEntities() {
		Set<IEntity<?>> entities = new HashSet<IEntity<?>>();
		for (ABindingService bindingService : boundedPart.getBindingServices())
			entities.addAll(bindingService.getUpdatedEntities().keySet());
		return entities;
	}

	public void doRefresh(boolean ask) {
		if (!ask || abortingChanges(getUpdatedEntities())) {
			boolean errorEncountered = false;
			Set<AEntityManagerController> containerControllers = new HashSet<AEntityManagerController>();
			for (ABindingService bindingService : boundedPart.getBindingServices()) {
				for (final ErrorMessage errorMessage : bindingService.getErrorMessageMap().getErrorMessages()) {
					IValidableController controller = errorMessage.getValidator().getTriggeringController();
					if (controller instanceof AEntityManagerController)
						containerControllers.add((AEntityManagerController) controller);
					else
						containerControllers.add((AEntityManagerController) controller.getParentController());
				}

				if (!bindingService.refreshEntities(bindingService.getEntities())) {
					errorEncountered = true;
				}
				bindingService.setDirty();
			}
			for (AEntityManagerController controller : containerControllers)
				if (null != controller.getEntity() && IEntityConstants.PERSIST != controller.getEntity().getStatus())
					// refresh new Entity (Cancel PERSIST)
					controller.validateFields();
			PageManager pageManager = boundedPart.getActivePageManager();
			if (null != pageManager)
				pageManager.getManagedForm().reflow(true);
			if (errorEncountered) {
				LogBroker.displayError(EngineTools.getFromEngineBundle("error.dialog.errorsWereFound"),
						EngineE4Util.getFromEngineE4Bundle("cannot.refresh"));
			}
		}
	}

	/**
	 * Aborting changes.
	 * 
	 * @param updatedEntities
	 *            the updated entities
	 * 
	 * @return true, if successful
	 */
	private boolean abortingChanges(Set<IEntity<?>> updatedEntities) {
		if (updatedEntities.isEmpty())
			return true;
		AbortChangesDialog dialog = new AbortChangesDialog(updatedEntities, boundedPart.getPluginResources());
		return (IDialogConstants.NO_ID == dialog.open());
	}

	@SuppressWarnings("restriction")
	public void doSave() {
		for (ABindingService bindingService : boundedPart.getBindingServices())
			if (!bindingService.getErrorMessageMap().isEmpty()) {
				String message = getFromEngineBundle("error.validationErrorEncountered", boundedPart.getInputPart().getLabel());
				LogBroker.displayError(getFromEngineBundle("error.encounteredErrors"), message);
				return;
			}
		int okBindingService = 0;
		for (ABindingService bindingService : boundedPart.getBindingServices()) {
			if (bindingService.saveEntities()) {
				okBindingService++;
			}
		}
		if (boundedPart.getBindingServices().size() == okBindingService) {
			boundedPart.setDirty(false);
			boundedPart.validateFields();
			PageManager pageManager = boundedPart.getActivePageManager();
			if (null != pageManager)
				pageManager.getManagedForm().reflow(true);
		} else
			throw new RuntimeException(getFromEngineBundle("error.doNotKnowHowToSaveEntities"));
	}

}
