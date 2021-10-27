package org.adichatz.permission.test;

import org.adichatz.permission.PermissionDomain;
import org.adichatz.permission.PermissionTools;
import org.adichatz.permission.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPermission {

	private static final transient Logger logger = LogManager.getLogger(PermissionTools.LOGGER_PERMISSION);

	public static void main(String[] args) {
		new MainPermission().doPerm();
	}

	private void doPerm() {
		System.getProperties();
		Session session = new Session("john.editor");
		logger.error("Error:" + session.getUsername() + ", " + PermissionDomain.getInstance());
	}
}
