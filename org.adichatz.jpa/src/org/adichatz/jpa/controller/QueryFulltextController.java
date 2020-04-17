package org.adichatz.jpa.controller;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.tabular.QueryPreferenceManager;

import net.miginfocom.swt.MigLayout;

public class QueryFulltextController extends TextController {

	private ATabularController<?> tabularController;

	public QueryFulltextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void addListeners() {
		control.addModifyListener((e) -> {
			broadcastChange();
			boolean doit = true;
			if (doit) {
				doit = false;
				((QueryPreferenceManager<?>) getTabularController().getQueryManager().getQueryPreferenceManager())
						.getQueryPreference().setFullTextClause(getControl().getText());
				doit = true;
			}
		});
		control.addDisposeListener((e) -> {
			if (null != fieldBindManager) {
				fieldBindManager.unbind();
				fieldBindManager = null;
			}
		});
	}

	public ATabularController<?> getTabularController() {
		if (null == tabularController)
			tabularController = (ATabularController<?>) genCode.getContext().getRootCore().getFromRegister("tableInclude:table");
		return tabularController;
	}

	@Override
	public void initialize() {
		addListener(new AControlListener(null, IEventType.POST_CREATE_PART) {
			@Override
			public void handleEvent(AdiEvent event) {
				ABindingService bindingService = OutlinePart.getInstance().getCurrentPage().getBindingService();
				String fullTextClause = ((QueryPreferenceManager<?>) getTabularController().getQueryManager()
						.getQueryPreferenceManager()).getQueryPreference().getFullTextClause();
				if (null != fullTextClause)
					getControl().setText(fullTextClause);
				bindingService.addBindingListener(new ABindingListener(IEventType.POST_MESSAGE) {
					@Override
					public void handleEvent(AdiEvent event) {
						setEnabled(bindingService.getErrorMessageMap().isEmpty());
					}
				});
			}
		});
		addListener(new AControlListener("fullTextACC", IEventType.AFTER_END_LIFE_CYCLE) {
			@Override
			public void handleEvent(AdiEvent event) {
				parentController.getParentController().getComposite()
						.setLayout(new MigLayout("wrap, ins 0", "[grow,fill]", "[][grow,fill]"));
				final JPAQueryManager<?> queryManager = (JPAQueryManager<?>) getTabularController().getQueryManager();
				queryManager.getQueryListeners().add(new AListener(null, IEventType.AFTER_PREFERENCE_CHANGE) {
					@Override
					public void handleEvent(AdiEvent event) {
						String fullTextClause = ((QueryPreferenceManager<?>) getTabularController().getQueryManager()
								.getQueryPreferenceManager()).getQueryPreference().getFullTextClause();
						getControl().setText(null == fullTextClause ? "" : fullTextClause);
					}
				});
			}
		});
		super.initialize();
	}
}
