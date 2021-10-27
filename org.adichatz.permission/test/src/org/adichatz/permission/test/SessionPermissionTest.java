package org.adichatz.permission.test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.adichatz.permission.PermissionTools;
import org.adichatz.permission.Session;
import org.adichatz.permission.ejb.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class SessionPermissionTest {
	private static transient final Logger log = LoggerFactory.getLogger(PermissionTools.LOGGER_PERMISSION);

	@Test(priority = 1)
	public void testBasic() {
		log.info("Starts testBasic");
		Session session = new Session("john.editor");
		assertTrue(session.isPermitted("articles"));
		assertTrue(session.isPermitted("articles:create"));
		assertTrue(session.isPermitted("articles:edit"));
		session = new Session("zoe.author");
		assertFalse(session.isPermitted("articles:edit2"));
		assertFalse(session.isPermitted("article:edit"));
		assertTrue(session.isPermitted(""));
		log.info("Ends testBasic");
	}

	@Test(priority = 2)
	public void testEJB() {
		log.info("Starts ejbTest");
		String PKG_INTERFACES = "org.jboss.ejb.client.naming";
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

		try {
			InitialContext initialContext = new InitialContext(properties);
			String shiroServiceLookup = "ejb:/PermissionServiceEJB/PermissionServiceBean!org.adichatz.security.ejb.PermissionService";
			PermissionService permissionService = (PermissionService) initialContext.lookup(shiroServiceLookup);
			log.info(permissionService.getSession("john.editor").getUsername());
		} catch (NamingException e) {
			log.error(e.getLocalizedMessage());
			fail(e.getLocalizedMessage());
		}
		log.info("Ends ejbTest");
	}
}
