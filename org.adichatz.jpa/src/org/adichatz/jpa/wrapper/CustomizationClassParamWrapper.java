package org.adichatz.jpa.wrapper;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.xjc.CustomizationClassParamType;

@SuppressWarnings("serial")
public class CustomizationClassParamWrapper extends CustomizationClassParamType implements IMarshalledWrapper {
	// This class must extend org.Adichatz.jpa.xjc.ParamType
	@Override
	public boolean equals(Object someObject) {
		if (!(someObject instanceof CustomizationClassParamType))
			return false;
		CustomizationClassParamType other = (CustomizationClassParamType) someObject;
		if (!Utilities.equals(customizationClassName, other.getCustomizationClassName()))
			return false;
		return true;
	}

	@Override
	public Object postUnmarshal() {
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(pluginKey);
		return pluginResources.getGencodePath().instantiateClass(customizationClassName, new Class[] {}, new Object[] {});
	}
}
