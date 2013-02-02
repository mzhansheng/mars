package com.seekon.mars.osgi.monitor.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;

public class Activator implements BundleActivator, BundleListener {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		context.addBundleListener(this);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		Bundle bundle = event.getBundle();
		switch (event.getType()) {
		case BundleEvent.RESOLVED: {
			if (bundle.getHeaders().get("Bundle-Activator") != null
					|| bundle.getHeaders().get("Context-Path") != null) {
				try {
					bundle.start();
				} catch (BundleException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		}
	}
}
