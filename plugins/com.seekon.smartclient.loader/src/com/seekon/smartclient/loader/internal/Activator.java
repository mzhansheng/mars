package com.seekon.smartclient.loader.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private LauncherServiceTracker launcherServiceTracker;
	
	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		launcherServiceTracker = new LauncherServiceTracker(bundleContext);
		launcherServiceTracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		launcherServiceTracker.close();
		Activator.context = null;
	}

}
