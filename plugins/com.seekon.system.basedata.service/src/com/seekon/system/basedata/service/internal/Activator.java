package com.seekon.system.basedata.service.internal;

import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;

public class Activator implements BundleActivator {

  public void start(BundleContext bundleContext) throws Exception {
    Injector injector = GuiceContext.createChildInjector(
      Peaberry.osgiModule(bundleContext), new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          String packageName = "com/seekon/system/basedata/service/mapper/";
          ClassLoader cl = Activator.class.getClassLoader();
          this.addXmlMapperResource(packageName + "BasedataElementMapper.xml", cl);
          this.addXmlMapperResource(packageName + "GeneralBasedataMapper.xml", cl);
          this.addXmlMapperResource(packageName + "BasedataReferMapper.xml", cl);
        }
      });
    injector.injectMembers(this);
  }

  public void stop(BundleContext bundleContext) throws Exception {

  }

}
