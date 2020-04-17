package org.adichatz.generator.wrapper;

import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.generator.xjc.QueryContentProviderType;
import org.adichatz.generator.xjc.QueryPreferenceType;
import org.adichatz.scenario.ScenarioInput;

public class QueryPreferenceWrapper extends QueryPreferenceType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -447507687930256847L;

	private QueryTreeWrapper queryTreeWrapper;

	private String adiResourceURI;

	public QueryTreeWrapper getQueryTreeWrapper(AXjcTreeElement preferenceElement) {
		if (null != queryTreeWrapper)
			return queryTreeWrapper;
		AXjcTreeElement parentElement = preferenceElement.getParentElement();
		if (parentElement.getElement() instanceof QueryContentProviderType) {
			QueryContentProviderType contentProvider = (QueryContentProviderType) parentElement.getElement();
			if (null == queryTreeWrapper || !contentProvider.getAdiResourceURI().equals(adiResourceURI)) {
				adiResourceURI = contentProvider.getAdiResourceURI();
				queryTreeWrapper = (QueryTreeWrapper) new GeneratorUnit(new ScenarioInput(adiResourceURI))
						.getTreeWrapperFromXml(true);
			}
		}
		while (null != parentElement && !(parentElement.getElement() instanceof QueryTreeWrapper))
			parentElement = parentElement.getParentElement();
		queryTreeWrapper = null == parentElement ? null : (QueryTreeWrapper) parentElement.getElement();
		return queryTreeWrapper;
	}

}
