package com.seekon.smartclient.updater.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.update.service.UpdateService;
import com.seekon.smartclient.loader.JarFileLoader;

public class Activator implements BundleActivator{

	static Injector injector;
	
	@Override
	public void start(BundleContext context) throws Exception {
		injector = GuiceContext.createChildInjector(osgiModule(context),
				new AbstractImportModule() {
					@Override
					protected void initialize() {
						importService(UpdateService.class);
						importService(JarFileLoader.class);
					}
				});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Activator.injector = null;
	}

}
