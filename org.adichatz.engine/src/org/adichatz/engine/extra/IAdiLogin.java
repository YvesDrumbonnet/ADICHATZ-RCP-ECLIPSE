package org.adichatz.engine.extra;

import org.adichatz.common.ejb.Session;

public interface IAdiLogin {
	public int open();

	public Session getSession();

}
