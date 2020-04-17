package org.adichatz.generator.tools;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.ExtendTreeWrapper;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.scenario.ScenarioInput;

public class MergeExtendedTreeTool extends PostCreateTreeTool {

	private ExtendTreeWrapper extendedTree;

	private ScenarioInput scenarioInput;

	private Map<String, IElementWrapper> includeTreeMap;

	public MergeExtendedTreeTool(ScenarioInput scenarioInput, ExtendTreeWrapper extendedTree) {
		this.extendedTree = extendedTree;
		this.scenarioInput = scenarioInput;
	}

	public IncludeTreeWrapper extractIncludeTree() {
		ScenarioInput includeSI = new ScenarioInput(scenarioInput, null, extendedTree.getAdiResourceURI());
		IncludeTreeWrapper includeTreeWrapper = (IncludeTreeWrapper) new GeneratorUnit(includeSI).getTreeWrapperFromXml(true);
		includeTreeMap = new HashMap<String, IElementWrapper>();
		buildTreeMap(includeTreeMap, includeTreeWrapper);
		addExtendTreeMap(extendedTree);
		return includeTreeWrapper;
	}

	@SuppressWarnings("unchecked")
	private void addExtendTreeMap(ICollectionWrapper<?> collectionWrapper) {
		IElementWrapper includeCollectionWrapper = includeTreeMap.get(collectionWrapper.getId());
		for (ElementType element : collectionWrapper.getElements()) {
			if (includeCollectionWrapper instanceof ICollectionWrapper && element instanceof IElementWrapper) {
				if (null == element.getParentIndex())
					((ICollectionWrapper) includeCollectionWrapper).getElements().add(element);
				else
					((ICollectionWrapper) includeCollectionWrapper).getElements().add(element.getParentIndex(), element);
			}
			if (element instanceof ICollectionWrapper)
				addExtendTreeMap((ICollectionWrapper<?>) element);
		}
	}
}
