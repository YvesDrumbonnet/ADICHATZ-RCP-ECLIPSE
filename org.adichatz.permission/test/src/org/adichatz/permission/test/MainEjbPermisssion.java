package org.adichatz.permission.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.adichatz.permission.Session;
import org.adichatz.permission.ejb.PermissionService;

public class MainEjbPermisssion {

	private static Context initialContext;

	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";

	public static void main(String[] args) {
		System.out.println("DEBUT");
		try {
			Context context = getInitialContext();
			String permissionServiceLookup = "ejb:/PermissionServiceEJB/PermissionServiceBean!org.adichatz.permission.ejb.PermissionService";
			PermissionService permissionService = (PermissionService) context.lookup(permissionServiceLookup);
			Session session = permissionService.getSession("john.editor");
			System.out.println(session + ", " + session.getUsername());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static Context getInitialContext() throws NamingException {
		if (initialContext == null) {
			Properties properties = new Properties();
			properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
			properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

			initialContext = new InitialContext(properties);
		}
		return initialContext;
	}
}