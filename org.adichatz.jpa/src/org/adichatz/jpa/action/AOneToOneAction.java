package org.adichatz.jpa.action;

import org.adichatz.engine.action.ADetailAction;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.jpa.wrapper.EntityParamWrapper;

public abstract class AOneToOneAction extends ADetailAction {
	IEntity<?> parentEntity;

	@Override
	protected boolean enable(IEntity<?> entity) {
		return null != entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.action.ADetailAction#postCreate()
	 */
	@Override
	public void postCreate() {
		if (actionController.isValid()) {
			entityManagerController = (AEntityManagerController) getParam(ParamMap.CONTROLLER);
			Object parentObject = entityManagerController.getGenCode().getContext().getParam(ParamMap.PARENT_ENTITY);
			if (parentObject instanceof EntityParamWrapper)
				parentEntity = (IEntity<?>) ((EntityParamWrapper) parentObject).getEntity();
			else
				parentEntity = (IEntity<?>) parentObject;
			entityManagerController.addListener(new AControlListener("OneToOne#IE", IEventType.BEFORE_ENTITY_INJECTION) {
				@Override
				public void handleEvent(AdiEvent event) {
					actionController.setEnabled(enable(controller.getEntity()));
				}
			});
			actionController.setEnabled(enable(entityManagerController.getEntity()));
		}
	}

	protected void refreshNewOTO(IEntity<?> entity) {
		entityManagerController.getEntityInjection().initialize(null);
		entityManagerController.endLifeCycleAndSync();
		AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, entityManagerController));
		entity.getBeanInterceptor().adi$FireNullEntity();
	}

	protected AddOTOEntityAction<?> getAddOTOEntityAction() {
		return ((AddOTOEntityAction<?>) ((ActionController) actionController.getGenCode().getFromRegister("addOTOEntityAction"))
				.getControl());
	}
}
