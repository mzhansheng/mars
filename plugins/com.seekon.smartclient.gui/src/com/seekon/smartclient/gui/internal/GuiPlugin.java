package com.seekon.smartclient.gui.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.gui.service.ModelService;

public class GuiPlugin implements BundleActivator {

  static Injector injector;

  public void start(BundleContext bundleContext) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(bundleContext),
      new AbstractImportModule() {
        @Override
        protected void initialize() {
          importService(ModelService.class);
        }
      });
  }

  public void stop(BundleContext bundleContext) throws Exception {
    GuiPlugin.injector = null;
  }

}
