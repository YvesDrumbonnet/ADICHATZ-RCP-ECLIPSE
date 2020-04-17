package org.adichatz.engine.widgets.supplement;

import java.text.DecimalFormat;

public class AdiDecimalFormat extends DecimalFormat {

	private static final long serialVersionUID = 2945416109582019333L;

	private String pattern;

	public AdiDecimalFormat() {
		super();
	}

	public AdiDecimalFormat(String pattern) {
		super(pattern);
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}
}
