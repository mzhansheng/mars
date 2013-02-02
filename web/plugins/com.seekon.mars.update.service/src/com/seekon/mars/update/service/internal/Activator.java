package com.seekon.mars.update.service.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.update.service.UpdateService;

public class Activator implements BundleActivator {

  private static BundleContext context;

  @Inject
  Export<UpdateService> handle;

  static BundleContext getContext() {
    return context;
  }

  public void start(BundleContext bundleContext) throws Exception {
    Activator.context = bundleContext;
    Class clazz = null;
    try {
      clazz = Class.forName("javax.servlet.ServletContext");
    } catch (Throwable e) {
    }
    if (clazz != null) {
      Injector injector = GuiceContext.createChildInjector(osgiModule(context),
        new AbstractExportModule() {
          @Override
          protected void initialize() {
            this.exportService(UpdateService.class, BundleUpdateService.class);
          }
        });
      injector.injectMembers(this);
    }
  }

  public void stop(BundleContext bundleContext) throws Exception {
    if(handle != null){
      handle.unput();
    }
    Activator.context = null;
  }

}
