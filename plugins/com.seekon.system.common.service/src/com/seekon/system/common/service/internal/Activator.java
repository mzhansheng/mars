package com.seekon.system.common.service.internal;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.TableMeta;
import com.seekon.system.common.mapper.ControlDataMapper;
import com.seekon.system.common.mapper.ElementMapper;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.spi.ElementService;

public class Activator implements BundleActivator {

  static Injector injector = null;

  @Inject
  Export<ElementService> handle;

  public void start(BundleContext bundleContext) throws Exception {
    injector = GuiceContext.createChildInjector(Peaberry.osgiModule(bundleContext),
      new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addSimpleAlias(TableMeta.class);
          this.addSimpleAlias(FieldMeta.class);
          this.addSimpleAlias(Element.class);

          this.addMapperClass(ElementMapper.class);
          this.addMapperClass(ControlDataMapper.class);

          this.addInterceptorClass(ControlDataUpdateInterceptor.class);

          this.exportService(ElementService.class, ElementServiceImpl.class);
        }
      });
    injector.injectMembers(this);
  }

  public void stop(BundleContext bundleContext) throws Exception {
    injector = null;
    handle.unput();
  }

}
