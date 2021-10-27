package org.adichatz.permission.ejb;

import javax.ejb.Remote;

import org.adichatz.permission.Session;

@Remote
public interface PermissionService {
	public Session getSession(String username);
}