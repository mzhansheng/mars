package com.seekon.mars.wf.engine.internal;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.wf.engine.ExecutionService;
import com.seekon.mars.wf.engine.ManagementService;
import com.seekon.mars.wf.engine.MonitorService;
import com.seekon.mars.wf.engine.RepositoryService;
import com.seekon.mars.wf.engine.WFEngine;
import com.seekon.mars.wf.engine.event.ProcessInstanceListener;
import com.seekon.mars.wf.engine.event.TaskListener;
import com.seekon.mars.wf.engine.event.WorkflowEventBroker;
import com.seekon.mars.wf.engine.internal.cmd.CommandService;
import com.seekon.mars.wf.engine.internal.event.BasicWorkflowEventBroker;
import com.seekon.mars.wf.engine.internal.event.ProcessInstanceListenerSupport;
import com.seekon.mars.wf.engine.internal.event.TaskEventListenerSupport;
import com.seekon.mars.wf.engine.internal.mapper.WFMetaMapper;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.internal.svc.DefaultCommandService;
import com.seekon.mars.wf.engine.internal.svc.ExecutionServiceImpl;
import com.seekon.mars.wf.engine.internal.svc.ManagementServiceImpl;
import com.seekon.mars.wf.engine.internal.svc.MonitorServiceImpl;
import com.seekon.mars.wf.engine.internal.svc.RepositoryServiceImpl;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceHistoryModel;


public class Activator implements BundleActivator {

  static Injector injector = null;
  
  @Inject
  Export<WFEngine> handle;

  @Override
  public void start(BundleContext context) throws Exception {
    injector = GuiceContext.createChildInjector(
      Peaberry.osgiModule(context), new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addSimpleAliases(TemplateModel.class.getPackage().getName());
          this.addSimpleAliases(InstanceHistoryModel.class.getPackage().getName());
          this.addSimpleAliases(StaffModel.class.getPackage().getName());
          
          this.addMapperClass(WFMetaMapper.class);
          this.addMapperClass(WFRuntimeMapper.class);
                    
          this.bindInnerService(CommandService.class, DefaultCommandService.class);
          this.bindInnerService(RepositoryService.class, RepositoryServiceImpl.class);
          this.bindInnerService(ExecutionService.class, ExecutionServiceImpl.class);
          this.bindInnerService(ManagementService.class, ManagementServiceImpl.class);
          this.bindInnerService(MonitorService.class, MonitorServiceImpl.class);
          
          this.bindInnerService(WorkflowEventBroker.class, BasicWorkflowEventBroker.class);
          
          this.exportService(WFEngine.class, BasicWFEngine.class);
        }
      });
    injector.injectMembers(this);
    
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    handle.unput();
    Activator.injector = null; 
  }

  public static Injector getInjector(){
    return Activator.injector;
  }
}
