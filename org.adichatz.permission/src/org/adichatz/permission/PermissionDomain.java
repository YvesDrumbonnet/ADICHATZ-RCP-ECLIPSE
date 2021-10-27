package org.adichatz.permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermissionDomain {
	private static final transient Logger logger = LogManager.getLogger(PermissionTools.LOGGER_PERMISSION);

	private static PermissionDomain THIS;

	public static PermissionDomain getInstance() {
		if (null == THIS)
			new PermissionDomain();
		return THIS;
	}

	private Map<String, Set<String>> users;

	private Map<String, Map<String, Set<String>>> roles;

	private Map<String, Map<String, Set<String>>> denials;

	public PermissionDomain() {
		roles = new HashMap<>();
		users = new HashMap<>();
		denials = new HashMap<>();
		THIS = this;
		String configDir = System.getProperty("jboss.server.config.dir");
		if (null == configDir)
			configDir = ".";
		String fileName = configDir.concat("/permissions.ini");
		logger.info("FileName2:" + fileName);
		Ini.fromResourcePath(fileName);
	}

	public Map<String, Map<String, Set<String>>> getRoles() {
		return roles;
	}

	public Map<String, Set<String>> getUsers() {
		return users;
	}

	public Map<String, Map<String, Set<String>>> getDenials() {
		return denials;
	}
}
