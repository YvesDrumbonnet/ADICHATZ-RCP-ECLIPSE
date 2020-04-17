package org.adichatz.generator.tools;

import java.util.Map;

import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.ElementType;

public class PostCreateTreeTool {

	public void buildTreeMap(Map<String, IElementWrapper> treeMap, ICollectionWrapper<?> collectionWrapper) {
		for (ElementType element : collectionWrapper.getElements()) {
			if (element instanceof ICollectionWrapper)
				buildTreeMap(treeMap, (ICollectionWrapper<?>) element);
			if (element instanceof IElementWrapper) {
				treeMap.put(element.getId(), (IElementWrapper) element);
			}
		}
	}
}
