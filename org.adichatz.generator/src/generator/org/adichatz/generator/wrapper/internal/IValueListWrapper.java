package org.adichatz.generator.wrapper.internal;

import org.adichatz.generator.xjc.LabelProviderType;

public interface IValueListWrapper {
	public LabelProviderType getLabelProvider();

	public void setLabelProvider(LabelProviderType labelProvider);

	public String getInitValues();

	public String getValues();

	public String getDisplayedValues();

	public String getLabelProviderClassName();

}
