package com.seekon.smartclient.framework;

import org.osgi.framework.BundleContext;

public interface UIStartup {

	public static final String STARTUP_EXTENSION_POINT_ID = "com.seekon.smartclient.framework.startups";

	public void run(BundleContext context);
}
