package org.adichatz.jpa.query.custom;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.extra.CrossReference;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.query.QueryToolBindingService;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.jpa.query.action.CrossReferenceMenuActions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

public class CrossReferenceBarController extends CompositeController {

	private List<Button> crossButtons = new ArrayList<Button>();

	public CrossReferenceBarController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void createControl() {
		super.createControl();
		QueryToolInput queryToolInput = ((QueryToolBindingService) getBindingService()).getQueryToolInput();
		final ATabularController<?> tableController = queryToolInput.getTabularController();
		composite.setLayout(new MigLayout("wrap 1", "grow,fill"));
		composite.setLayoutData("push, grow");
		for (final CrossReference crossReference : ((ATabularCore<?>) tableController.getGenCode()).getCrossReferences()) {
			Button button = toolkit.createButton(composite, crossReference.getDescription(), SWT.PUSH);

			button.setEnabled(false);
			AReskinManager reskinManager = AReskinManager.getInstance();
			if (null == reskinManager)
				button.setBackground(toolkit.getColors().getColor(IFormColors.H_GRADIENT_START));
			else
				button.setBackground(reskinManager.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "button_background",
						AControlController.ADI_CSS_BACKGROUND, button));

			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					E4AdichatzApplication.openPart(((IContainerController) parentController).getContext(),
							CrossReferenceMenuActions.getCrossRefParamMap(tableController, crossReference));
				};
			});
			crossButtons.add(button);
		}
		if (!isEmpty()) {
			tableController.getViewer().addSelectionChangedListener((event) -> {
				boolean enabled = !event.getSelection().isEmpty();
				for (Button button : crossButtons)
					button.setEnabled(enabled);
			});
			composite.addDisposeListener((e) -> {
				crossButtons.clear();
			});
		}
	}

	public boolean isEmpty() {
		return 0 == crossButtons.size();
	}
}
