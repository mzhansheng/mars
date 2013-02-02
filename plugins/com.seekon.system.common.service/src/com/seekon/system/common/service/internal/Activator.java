package com.seekon.system.common.service.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.system.common.mapper.ControlDataMapper;

public class Activator implements BundleActivator {

  static Injector injector = null;

  public void start(BundleContext bundleContext) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(bundleContext),
      new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addMapperClass(ControlDataMapper.class);
          this.addInterceptorClass(ControlDataUpdateInterceptor.class);
        }
      });
  }

  public void stop(BundleContext bundleContext) throws Exception {
    injector = null;
  }

}
