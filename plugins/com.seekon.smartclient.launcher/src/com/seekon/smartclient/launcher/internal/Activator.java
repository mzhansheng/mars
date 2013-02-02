package com.seekon.smartclient.launcher.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private AppletServiceTracker appletServiceTracker;
	
	private WebstarServiceTracker webstarServiceTracker;
	
	private DesktopServiceTracker desktopServiceTracker;
	
	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		appletServiceTracker = new AppletServiceTracker(bundleContext);
		appletServiceTracker.open();
		
		webstarServiceTracker = new WebstarServiceTracker(bundleContext);
		webstarServiceTracker.open();
		
		desktopServiceTracker = new DesktopServiceTracker(bundleContext);
		desktopServiceTracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		appletServiceTracker.close();
		webstarServiceTracker.close();
		desktopServiceTracker.close();
		
		Activator.context = null;
	}

}
