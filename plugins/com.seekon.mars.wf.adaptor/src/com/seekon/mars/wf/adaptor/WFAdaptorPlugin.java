package com.seekon.mars.wf.adaptor;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.wf.adaptor.mapper.BusinessMapper;
import com.seekon.mars.wf.adaptor.service.impl.BusinessServiceImpl;
import com.seekon.mars.wf.model.AsCompoModel;
import com.seekon.mars.wf.model.BusinessType;
import com.seekon.mars.wf.model.Company;
import com.seekon.mars.wf.model.Org;
import com.seekon.mars.wf.model.Point;
import com.seekon.mars.wf.model.Position;
import com.seekon.mars.wf.model.Staff;
import com.seekon.mars.wf.service.BusinessService;

public class WFAdaptorPlugin implements BundleActivator {

  static Injector injector = null;
  
  @Inject
  Export<BusinessService> handle;

  @Override
  public void start(BundleContext context) throws Exception {
    injector = GuiceContext.createChildInjector(
      Peaberry.osgiModule(context), new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addSimpleAlias(AsCompoModel.class);
          this.addSimpleAlias(BusinessType.class);
          this.addSimpleAlias(Company.class);
          this.addSimpleAlias(Org.class);
          this.addSimpleAlias(Point.class);
          this.addSimpleAlias(Position.class);
          this.addSimpleAlias(Staff.class);
          
          this.addMapperClass(BusinessMapper.class);
                    
          this.exportService(BusinessService.class, BusinessServiceImpl.class);
        }
      });
    injector.injectMembers(this);
    
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    handle.unput();
    WFAdaptorPlugin.injector = null; 
  }

  public static Injector getInjector(){
    return WFAdaptorPlugin.injector;
  }
}

