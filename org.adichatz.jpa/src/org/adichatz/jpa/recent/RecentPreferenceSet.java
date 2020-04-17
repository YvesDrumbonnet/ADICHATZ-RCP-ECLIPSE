package org.adichatz.jpa.recent;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;

public class RecentPreferenceSet {
	private String queryURI;

	private Map<String, RecentPreferenceWrapper<?>> recentPreferenceMap;

	public RecentPreferenceSet(String queryURI) {
		this.queryURI = queryURI;
		this.recentPreferenceMap = new HashMap<>();
	}

	public String getQueryURI() {
		return queryURI;
	}

	public Map<String, RecentPreferenceWrapper<?>> getRecentPreferenceMap() {
		return recentPreferenceMap;
	}
}
