package com.seekon.mars.wf.client.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.wf.service.BusinessService;
import com.seekon.mars.wf.service.MetaService;
import com.seekon.mars.wf.service.RuntimeService;

public class WFClientPlugin implements BundleActivator{

  static Injector injector;

  public void start(BundleContext bundleContext) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(bundleContext),
      new AbstractImportModule() {
        @Override
        protected void initialize() {
          importService(MetaService.class);
          importService(RuntimeService.class);
          importService(BusinessService.class);
        }
      });
  }

  public void stop(BundleContext bundleContext) throws Exception {
    WFClientPlugin.injector = null;
  }

}
