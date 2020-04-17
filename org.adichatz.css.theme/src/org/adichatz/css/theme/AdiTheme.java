package org.adichatz.css.theme;

import org.eclipse.e4.ui.css.swt.theme.ITheme;

@SuppressWarnings("restriction")
public class AdiTheme implements ITheme {

	private String id;

	private String label;

	private String osVersion;

	public AdiTheme(String id, String label) {
		this.id = id;
		this.label = label;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOsVersion(String version) {
		this.osVersion = version;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	@Override
	public String toString() {
		return "Theme [id=" + id + ", label='" + label + "', osVersion=" + osVersion + "]";
	}

}