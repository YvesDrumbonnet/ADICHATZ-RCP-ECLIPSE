package org.adichatz.permission;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Initialize PermissionsDomain from a '.ini' file
 * '.ini' files contains 3 sections:
 * 		- Section 'users':
 * 			- for each user, a list of roles is associated
 * 		- Section 'roles':
 * 			- for each role, associate either wildcard (*) meaning all is permitted or a list of pair (Item,sub Permission) defining permissions:
 * 					admin = *
 * 					editor = product:edit, product:create, product:visualize, item:*
 * 			here sub permission is action
 * 		- Section 'denials' 
 * 			- for each role, list of pair (Item,sub permission) defining denials:
 * 					author = articles:amount, articles:isbn
 * 			here sub permission is column
 */
public class Ini {

	private static transient final Logger log = LoggerFactory.getLogger(PermissionTools.LOGGER_PERMISSION);

	private static final String DEFAULT_SECTION_NAME = ""; // empty string means the first unnamed section

	private static final String DEFAULT_CHARSET_NAME = "UTF-8";

	private static final String SUBPART_DIVIDER_TOKEN = ",";

	private static final String COMMENT_POUND = "#";

	private static final String COMMENT_SEMICOLON = ";";

	private static final String SECTION_PREFIX = "[";

	private static final String SECTION_SUFFIX = "]";

	private static final String USER_ROLES = "user_roles";

	private static final String ROLE_PERMISSIONS = "role_permissions";

	private static final String ROLE_DENIALS = "role_denials";

	private static final String ESCAPE_TOKEN = "\\";

	private Map<String, Map<String, Set<String>>> currentMap;

	private PermissionDomain permissionDomain;

	private static String cleanName(String sectionName) {
		String name = PermissionTools.clean(sectionName);
		if (name == null) {
			log.trace("Specified name was null or empty.  Defaulting to the default section (name = \"\")");
			name = DEFAULT_SECTION_NAME;
		}
		return name;
	}

	public static PermissionDomain fromResourcePath(String resourcePath) throws ConfigurationException {
		if (!PermissionTools.hasLength(resourcePath)) {
			throw new IllegalArgumentException("Resource Path argument cannot be null or empty.");
		}
		Ini ini = new Ini();
		return ini.loadFromPath(resourcePath);
	}

	protected Set<String> getParts(String wildcardString) {
		wildcardString = PermissionTools.clean(wildcardString);

		if (wildcardString == null || wildcardString.isEmpty()) {
			throw new IllegalArgumentException(
					"Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
		}

		List<String> parts = Arrays.asList(wildcardString.split(PermissionTools.PART_DIVIDER_TOKEN));

		for (String part : parts) {

			String[] elements = part.split(SUBPART_DIVIDER_TOKEN);

			if (elements.length == 1)
				return Collections.singleton(elements[0]);
			else {
				LinkedHashSet<String> subparts = new LinkedHashSet<String>(elements.length * 4 / 3 + 1);
				Collections.addAll(subparts, elements);

				if (subparts.isEmpty()) {
					throw new IllegalArgumentException(
							"Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
				}
				return subparts;
			}
		}
		return null;
	}

	private PermissionDomain loadFromPath(String resourcePath) throws ConfigurationException {
		InputStream is;
		try {
			is = new FileInputStream(resourcePath);
			InputStreamReader reader;
			reader = new InputStreamReader(is, DEFAULT_CHARSET_NAME);
			Scanner scanner = new Scanner(reader);
			permissionDomain = PermissionDomain.getInstance();
			load(scanner);
			scanner.close();
			reader.close();
			is.close();
			return permissionDomain;
		} catch (IOException e) {
			throw new ConfigurationException(e);
		}
	}

	protected void load(Scanner scanner) {

		StringBuffer lineSB = new StringBuffer();
		String sectionName = null;
		boolean isSectionLine;
		while (scanner.hasNextLine()) {
			String line = PermissionTools.clean(scanner.nextLine());

			if (line == null || line.startsWith(COMMENT_POUND) || line.startsWith(COMMENT_SEMICOLON)) {
				// skip empty lines and comments:
				continue;
			}

			if (line != null && line.startsWith(SECTION_PREFIX) && line.endsWith(SECTION_SUFFIX)) {
				sectionName = cleanName(line.substring(1, line.length() - 1));
				isSectionLine = true;
			} else
				isSectionLine = false;
			if (isSectionLine) {
				switch (sectionName) {
				case USER_ROLES:
					break;
				case ROLE_PERMISSIONS: {
					currentMap = permissionDomain.getRoles();
					break;
				}
				case ROLE_DENIALS: {
					currentMap = permissionDomain.getDenials();
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected section: " + sectionName);
				}
			} else {
				lineSB.append(line);
				if (!line.endsWith(ESCAPE_TOKEN)) {
					if (USER_ROLES.equals(sectionName))
						addUserRoleline(lineSB);
					else
						addline(lineSB);
					lineSB = new StringBuffer();
				}
			}
		}
	}

	private void addUserRoleline(StringBuffer lineSB) {
		int index = lineSB.indexOf("=");
		if (-1 == index)
			throw new IllegalArgumentException("Invalid permission line. Line must contain '=' char!");
		String key = lineSB.substring(0, index).trim();
		String line = lineSB.substring(index + 1).trim();
		Set<String> set = new HashSet<>();
		for (String subPart : line.split(SUBPART_DIVIDER_TOKEN))
			set.add(subPart.trim());
		permissionDomain.getUsers().put(key, set);
	}

	private void addline(StringBuffer lineSB) {
		int index = lineSB.indexOf("=");
		if (-1 == index)
			throw new IllegalArgumentException("Invalid permission line. Line must contain '=' char!");
		String rightMember = lineSB.substring(0, index).trim(); // first member before '='
		Map<String, Set<String>> itemMap = new HashMap<>();
		currentMap.put(rightMember, itemMap);
		String line = lineSB.substring(index + 1).trim();
		List<String> parts = Arrays.asList(line.split(SUBPART_DIVIDER_TOKEN));

		for (String part : parts) {
			String[] elements = part.split(PermissionTools.PART_DIVIDER_TOKEN);
			String item = elements[0].trim();
			Set<String> subPermSet = itemMap.get(item);
			if (null == subPermSet) {
				subPermSet = new HashSet<>();
				itemMap.put(item, subPermSet);
			}
			if (1 == elements.length && PermissionTools.WILDCARD_TOKEN.equals(item))
				subPermSet.add(item);
			else
				subPermSet.add(elements[1].trim());
		}
	}

	@SuppressWarnings("serial")
	class ConfigurationException extends RuntimeException {
		public ConfigurationException(Throwable cause) {
			super(cause);
		}
	}
}
