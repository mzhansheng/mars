package com.seekon.smartclient.launcher;

import org.osgi.framework.BundleContext;

public interface Startup {

	public static final String STARTUP_EXTENSION_POINT_ID = "com.seekon.smartclient.launcher.startups";
	
	public void run(BundleContext context);
}
