package org.adichatz.jpa.wrapper;

import org.adichatz.engine.common.Utilities;
import org.adichatz.jpa.xjc.QueryPaginationType;

public class QueryPaginationWrapper extends QueryPaginationType {
	private static final long serialVersionUID = -2185211236603555409L;

	/** The completed. */
	private boolean completed;

	private Integer currentMaxResults;

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Integer getCurrentMaxResults() {
		if (null == currentMaxResults)
			currentMaxResults = maxResults;
		return currentMaxResults;
	}

	public void setCurrentMaxResults(Integer currentMaxResults) {
		this.currentMaxResults = currentMaxResults;
	}

	@Override
	public boolean equals(Object someObject) {
		if (!(someObject instanceof QueryPaginationType))
			return false;
		QueryPaginationType other = (QueryPaginationType) someObject;
		if (!Utilities.equals(firstResult, other.getFirstResult()))
			return false;
		if (!Utilities.equals(maxResults, other.getMaxResults()))
			return false;
		return true;
	}
}
