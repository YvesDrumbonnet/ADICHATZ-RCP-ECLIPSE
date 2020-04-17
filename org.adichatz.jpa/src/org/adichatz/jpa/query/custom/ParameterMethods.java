package org.adichatz.jpa.query.custom;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.field.DateTextController;
import org.adichatz.engine.controller.field.DateTimeController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.controller.nebula.FormattedTextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.plugin.AdiContext;
import org.eclipse.nebula.widgets.formattedtext.ByteFormatter;
import org.eclipse.nebula.widgets.formattedtext.DoubleFormatter;
import org.eclipse.nebula.widgets.formattedtext.FloatFormatter;
import org.eclipse.nebula.widgets.formattedtext.IntegerFormatter;
import org.eclipse.nebula.widgets.formattedtext.LongFormatter;
import org.eclipse.nebula.widgets.formattedtext.NumberFormatter;
import org.eclipse.nebula.widgets.formattedtext.PercentFormatter;
import org.eclipse.nebula.widgets.formattedtext.ShortFormatter;
import org.eclipse.swt.SWT;

public class ParameterMethods extends EntityManagerCore {

	/**
	 * Creates ParameterMethods$composite.
	 * 
	 * @param context
	 *            The context of the root controller
	 * @param core
	 *            controller The collection controller linked to the class
	 */
	public ParameterMethods(final AdiContext context, IContainerController parentController) {
		super(context);
		// do not create contents when context is null because that means that class is invoked from dynamic part
		if (null != context) {
			coreController = new CompositeController("composite", parentController, this);
			createContents();
		} else
			coreController = parentController;
	}

	/**
	 * creates contents for controller
	 */
	public void createContents() {
		createInteger(this);
		createText(this);
	}

	private FormattedTextController createFormatted(ControllerCore genCode, final NumberFormatter formatter) {
		return new FormattedTextController("percent", coreController, genCode) {
			@Override
			public void createControl() {
				setStyle(SWT.BORDER | SWT.RIGHT);
				super.createControl();
				getFormattedText().setFormatter(formatter);
			}

		};
	}

	public FormattedTextController createInteger(ControllerCore genCode) {
		return createFormatted(genCode, new IntegerFormatter());
	}

	public FormattedTextController createDouble(ControllerCore genCode) {
		return createFormatted(genCode, new DoubleFormatter());
	}

	public FormattedTextController createFloat(ControllerCore genCode) {
		return createFormatted(genCode, new FloatFormatter());
	}

	public FormattedTextController createLong(ControllerCore genCode) {
		return createFormatted(genCode, new LongFormatter());
	}

	public FormattedTextController createPercent(ControllerCore genCode) {
		return createFormatted(genCode, new PercentFormatter());
	}

	public FormattedTextController createShort(ControllerCore genCode) {
		return createFormatted(genCode, new ShortFormatter());
	}

	public FormattedTextController createByte(ControllerCore genCode) {
		return createFormatted(genCode, new ByteFormatter());
	}

	public TextController createText(ControllerCore genCode) {
		// Creates control for TextController textTXT
		return new TextController("text", coreController, genCode);
	}

	public DateTextController createDateText(ControllerCore genCode) {
		// Creates control for DateTextController textTXT
		return new DateTextController("dateText", coreController, genCode);
	}

	public DateTimeController createDateTime(ControllerCore genCode) {
		// Creates control for DateTimeController textTXT
		return new DateTimeController("dateTime", coreController, genCode);
	}
}