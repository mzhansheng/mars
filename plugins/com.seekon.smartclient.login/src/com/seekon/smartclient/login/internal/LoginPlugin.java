package com.seekon.smartclient.login.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.system.auth.spi.LoginService;

public class LoginPlugin implements BundleActivator {

	static Injector injector;
	
	static BundleContext context;
	
	public void start(BundleContext bundleContext) throws Exception {
	  LoginPlugin.context = bundleContext;
	  LoginPlugin.injector = GuiceContext.createChildInjector(osgiModule(bundleContext),
				new AbstractImportModule() {
					@Override
					protected void initialize() {
						importService(LoginService.class);
					}
				});
	}

	public void stop(BundleContext bundleContext) throws Exception {
		LoginPlugin.injector = null;
		LoginPlugin.context = null;
	}

}
