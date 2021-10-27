package org.adichatz.permission;

public class PermissionTools {
	public static final char ESCAPE_TOKEN = '\\';

	public static final String EMPTY_STRING = "";

	public static final String LOGGER_PERMISSION = "org-adichatz-permission";

	public static final String PART_DIVIDER_TOKEN = ":";

	public static final String WILDCARD_TOKEN = "*";

	public static boolean hasText(String str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}

	public static String clean(String in) {
		String out = in;

		if (in != null) {
			out = in.trim();
			if (out.equals(EMPTY_STRING)) {
				out = null;
			}
		}

		return out;
	}
}
