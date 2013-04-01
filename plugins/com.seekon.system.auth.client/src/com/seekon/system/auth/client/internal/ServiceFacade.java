package com.seekon.system.auth.client.internal;

import com.seekon.system.auth.spi.AuthElementService;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.auth.spi.RoleService;
import com.seekon.system.auth.spi.UserService;
import com.seekon.system.common.spi.ElementService;

public class ServiceFacade {

  public static UserService getUserService() {
    return AuthPlugin.injector.getInstance(UserService.class);
  }

  public static RoleService getRoleService() {
    return AuthPlugin.injector.getInstance(RoleService.class);
  }

  public static DataScopeService getDataScopeService() {
    return AuthPlugin.injector.getInstance(DataScopeService.class);
  }

  public static AuthElementService getAuthElementService() {
    return AuthPlugin.injector.getInstance(AuthElementService.class);
  }
  
  public static ElementService getElementService(){
    return AuthPlugin.injector.getInstance(ElementService.class);
  }
}
