package org.adichatz.generator.wrapper.internal;

import java.util.Map;

import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.xjc.ConfigType;

public interface IRootWrapper extends IEntityContainerWrapper, ITreeWrapper, IElementWrapper {
	/**
	 * Gets the element map.
	 * 
	 * @return the element map
	 */
	public Map<String, IElementWrapper> getElementMap();

	public ConfigType getConfig();

}
