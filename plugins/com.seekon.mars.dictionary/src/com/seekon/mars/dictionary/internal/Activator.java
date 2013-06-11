package com.seekon.mars.dictionary.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.dictionary.MetaService;

public class Activator implements BundleActivator {

  static Injector injector;

  @Override
  public void start(BundleContext context) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(context),
      new AbstractImportModule() {

        @Override
        protected void initialize() {
          importService(MetaService.class);
        }
      });
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    Activator.injector = null;
  }

}
