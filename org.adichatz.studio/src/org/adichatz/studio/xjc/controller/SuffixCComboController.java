package org.adichatz.studio.xjc.controller;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.CComboController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.generator.wrapper.QueryPreferenceWrapper;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.wrapper.internal.IJointure;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.eclipse.jface.viewers.LabelProvider;

public class SuffixCComboController extends CComboController {
	List values = new ArrayList<String>(0);

	public SuffixCComboController(String id, IContainerController parentController, final ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getValues() {
		if (values.isEmpty()) {
			XjcTreeElement element = ((XjcEntity<?>) getEntity()).getTreeElement();
			XjcTreeElement preferenceElement = element.getParentElement();
			if (preferenceElement.getElement() instanceof QueryPreferenceWrapper) {
				QueryPreferenceWrapper preference = (QueryPreferenceWrapper) preferenceElement.getElement();
				QueryTreeWrapper queryTree = preference.getQueryTreeWrapper(preferenceElement);
				if (null != queryTree) {
					List<String> values = new ArrayList<String>(queryTree.getAllJointures().size());
					for (IJointure jointure : queryTree.getAllJointures())
						if (null != jointure.getSuffix())
							values.add(jointure.getSuffix());
					this.values = values;
				}
				getEntity().addEntityListener(new AEntityListener(parentController, IEventType.AFTER_PROPERTY_CHANGE) {
					@Override
					public void handleEntityEvent(AdiEntityEvent event) {
						values = new ArrayList<String>(0);
					}
				});
			}
		}
		return values;
	}

	@Override
	public void createControl() {
		labelProvider = new LabelProvider();
		super.createControl();
	}
}
