package org.adichatz.permission.ejb;

import javax.ejb.Stateless;

import org.adichatz.permission.PermissionTools;
import org.adichatz.permission.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class PermissionServiceBean implements PermissionService {
	private static final transient Logger logger = LogManager.getLogger(PermissionTools.LOGGER_PERMISSION);

	public PermissionServiceBean() {
	}

	@Override
	public Session getSession(String username) {
		logger.info("Instantiate new session");
		return new Session(username);
	}
}