package com.seekon.mars.wf.client.internal;

import com.seekon.mars.wf.service.BusinessService;
import com.seekon.mars.wf.service.MetaService;
import com.seekon.mars.wf.service.RuntimeService;

public class ServiceFacade {

  public static MetaService getMetaService() {
    return WFClientPlugin.injector.getInstance(MetaService.class);
  }
  
  public static BusinessService getBusinessService(){
    return WFClientPlugin.injector.getInstance(BusinessService.class);
  }
  
  public static RuntimeService getRuntimeService(){
    return WFClientPlugin.injector.getInstance(RuntimeService.class);
  }
}
