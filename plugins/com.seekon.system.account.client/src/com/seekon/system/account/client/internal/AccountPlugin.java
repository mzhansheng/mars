package com.seekon.system.account.client.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;

import com.seekon.system.auth.spi.UserService;

public class AccountPlugin implements BundleActivator{

  static Injector injector;
  
  @Override
  public void start(BundleContext context) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(context),
      new AbstractImportModule() {
        @Override
        protected void initialize() {
          importService(UserService.class);
        }
      });
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    AccountPlugin.injector = null;
  }

}
