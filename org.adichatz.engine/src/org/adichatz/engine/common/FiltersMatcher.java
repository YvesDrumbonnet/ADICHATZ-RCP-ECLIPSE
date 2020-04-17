package org.adichatz.engine.common;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FiltersMatcher {
	String filters;

	public FiltersMatcher(String filters) {
		this.filters = filters;
	}

	public String checkFilters() {
		StringTokenizer tokenizer = new StringTokenizer(filters, ",");
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken().trim();
			try {
				Pattern pattern = Pattern.compile(token);
				Matcher matcher = pattern.matcher("");
				matcher.matches();
			} catch (PatternSyntaxException e) {
				return token;
			}
		}
		return null;
	}

	public boolean evaluate(String matchedString) {
		if (EngineTools.isEmpty(filters))
			return true;
		StringTokenizer tokenizer = new StringTokenizer(filters, ",");
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken().trim();
			Pattern pattern = Pattern.compile(token);
			Matcher matcher = pattern.matcher(matchedString);
			if (matcher.matches())
				return true;
		}
		return false;
	}
}
