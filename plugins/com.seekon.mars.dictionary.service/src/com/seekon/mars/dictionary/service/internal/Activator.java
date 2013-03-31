package com.seekon.mars.dictionary.service.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.MetaService;
import com.seekon.mars.dictionary.TableMeta;
import com.seekon.mars.dictionary.service.MetaMapper;

public class Activator implements BundleActivator {

  @Inject
  Export<MetaService> handle;

  public void start(BundleContext bundleContext) throws Exception {
    Injector injector = GuiceContext.createChildInjector(osgiModule(bundleContext),
      new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addSimpleAlias(TableMeta.class);
          this.addSimpleAlias(FieldMeta.class);

          this.addMapperClass(MetaMapper.class);

          this.exportService(MetaService.class, MetaServiceImpl.class);
        }
      });
    injector.injectMembers(this);
  }

  public void stop(BundleContext bundleContext) throws Exception {
    handle.unput();
  }

}
