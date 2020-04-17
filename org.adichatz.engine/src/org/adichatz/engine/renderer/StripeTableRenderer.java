package org.adichatz.engine.renderer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.forms.IFormColors;

public class StripeTableRenderer<T> extends BasicTableRenderer<T> {
	/** The odd even render. odd and even line are differentiate (Stripe table render) */
	protected Color evenBackground;

	/** The odd even render. odd and even line are differentiate (Stripe table render) */
	protected Color oddBackground;

	/** The odd even render. odd and even line are differentiate (Stripe table render) */
	protected Color evenForeground;

	/** The odd even render. odd and even line are differentiate (Stripe table render) */
	protected Color oddForeground;

	protected Font evenFont;

	protected Font oddFont;

	public StripeTableRenderer(ATabularController<T> tabularController, String key) {
		super(tabularController, key);
		initResources();
	}

	@Override
	public void initResources() {
		if (null != AReskinManager.getInstance()) {
			evenBackground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-even", "background-color", null, null);
			oddBackground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-odd", "background-color", null, null);
			evenForeground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-even", "color", null, null);
			oddForeground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-odd", "color", null, null);
			evenFont = AReskinManager.getInstance().getFont("#adichatz-table-stripe-even", null);
			oddFont = AReskinManager.getInstance().getFont("#adichatz-table-stripe-odd", null);
		} else {
			evenBackground = AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.H_GRADIENT_END);
			oddBackground = AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.H_GRADIENT_START);
		}
	}
}
