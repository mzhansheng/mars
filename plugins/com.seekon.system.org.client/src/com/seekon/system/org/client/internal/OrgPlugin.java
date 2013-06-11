package com.seekon.system.org.client.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.system.common.spi.ElementService;
import com.seekon.system.org.spi.OrgService;

public class OrgPlugin implements BundleActivator {

  static Injector injector;

  public void start(BundleContext bundleContext) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(bundleContext),
      new AbstractImportModule() {
        @Override
        protected void initialize() {
          importService(OrgService.class);
          importService(ElementService.class);
        }
      });
  }

  public void stop(BundleContext bundleContext) throws Exception {
    OrgPlugin.injector = null;
  }

}
