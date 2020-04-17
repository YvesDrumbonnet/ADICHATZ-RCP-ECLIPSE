package org.adichatz.engine.common;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public abstract class AAdichatzBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		init();
	}

	protected abstract void init() throws Exception;

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}
