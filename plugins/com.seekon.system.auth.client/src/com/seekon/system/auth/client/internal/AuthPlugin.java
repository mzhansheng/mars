package com.seekon.system.auth.client.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.system.auth.spi.AuthElementService;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.auth.spi.RoleService;
import com.seekon.system.auth.spi.UserService;
import com.seekon.system.common.spi.ElementService;

public class AuthPlugin implements BundleActivator {

  static Injector injector;

  public void start(BundleContext bundleContext) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(bundleContext),
      new AbstractImportModule() {
        @Override
        protected void initialize() {
          importService(RoleService.class);
          importService(UserService.class);
          importService(DataScopeService.class);
          importService(AuthElementService.class);
          importService(ElementService.class);
        }
      });
  }

  public void stop(BundleContext bundleContext) throws Exception {
    AuthPlugin.injector = null;
  }

}
