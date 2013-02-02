package com.seekon.smartclient.module.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ModulePlugin implements BundleActivator {

	static BundleContext bundleContext;
	//private ServiceRegistration serviceRegistration;
	
	public static BundleContext getBundconBundleContext(){
		return bundleContext;
	}
	public void start(BundleContext bundleContext) throws Exception {
		ModulePlugin.bundleContext = bundleContext;
//		serviceRegistration = bundleContext.registerService(
//				AbstractResourceTree.class.getName(), new JCompositeResourceTree(),
//				null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		//serviceRegistration.unregister();
		ModulePlugin.bundleContext = null;
	}

}
