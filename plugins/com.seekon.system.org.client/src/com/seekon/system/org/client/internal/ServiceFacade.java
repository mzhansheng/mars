package com.seekon.system.org.client.internal;

import com.seekon.system.common.spi.ElementService;
import com.seekon.system.org.spi.OrgService;

public class ServiceFacade {

  public static OrgService getOrgService() {
    return OrgPlugin.injector.getInstance(OrgService.class);
  }
  
  public static ElementService getElementService(){
    return OrgPlugin.injector.getInstance(ElementService.class);
  }
}
