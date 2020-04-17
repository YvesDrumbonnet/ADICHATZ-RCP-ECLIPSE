package org.adichatz.jpa.query;

import org.adichatz.engine.e4.part.IControllerOutlinePage;

public interface IQueryOutlinePage extends IControllerOutlinePage {
	public QueryToolBindingService getBindingService();

	public QueryToolInput<?> getQueryToolInput();
}
