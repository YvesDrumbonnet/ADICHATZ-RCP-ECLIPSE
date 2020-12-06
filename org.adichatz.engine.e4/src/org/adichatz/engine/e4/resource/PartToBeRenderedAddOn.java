package org.adichatz.engine.e4.resource;

import javax.annotation.PostConstruct;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.e4.part.BoundedPart;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;

public class PartToBeRenderedAddOn {
	// AVISATZ landmark A Tester
	//	import org.eclipse.e4.ui.internal.workbench.addons.Event;
	//	import org.eclipse.e4.ui.internal.workbench.addons.EventTopic;
	//	@Inject
	//	public void partToBeRendered(@Optional @EventTopic(UIEvents.UILifeCycle.BRINGTOTOP) Event event) {
	//		
	//	}

	@PostConstruct
	public void postConstruct(IEventBroker eventBroker) {
		eventBroker.subscribe(UIEvents.UILifeCycle.BRINGTOTOP, (e) -> {
			Object part = e.getProperty(UIEvents.EventTags.ELEMENT);
			if (part instanceof MPart) {
				Object container = ((MPart) part).getObject();
				if (null != container) {
					E4AdichatzApplication.getInstance().partToBeRendered(container);
					AdichatzApplication.fireListener(new ApplicationEvent(ApplicationEvent.EVENT_TYPE.BRINGTOTOP, part));
					if (container instanceof BoundedPart)
						((BoundedPart) container).postCreate();
				}
			}
		});
		E4AdichatzApplication.fireListener(new ApplicationEvent(ApplicationEvent.EVENT_TYPE.POST_START_UP));
	}
}
