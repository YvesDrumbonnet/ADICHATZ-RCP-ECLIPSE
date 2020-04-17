package org.adichatz.engine.viewer;

import java.util.Map;

import org.adichatz.common.ejb.MultiKey;

public interface IBoundContentProvider<T> {
	public Map<MultiKey, T> getBeanMap();

}
