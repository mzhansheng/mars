package com.seekon.mars.gui.service.internal;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.gui.service.ModelService;

public class Activator implements BundleActivator {

  @Inject
  Export<ModelService> handle;

  @Override
  public void start(BundleContext context) throws Exception {
    try {
      Class<?> clazz = Class.forName("org.apache.ibatis.session.SqlSession");
      if (clazz != null) {
        Injector injector = GuiceContext.createChildInjector(
          Peaberry.osgiModule(context), new AbstractMyBatisExportModule() {
            @Override
            protected void initialize() {
              this.exportService(ModelService.class, ModelServiceIbatisImpl.class);
            }
          });
        injector.injectMembers(this);
      }
    } catch (Throwable e) {
    }
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    if(handle != null){
      handle.unput();
    }
  }

}
