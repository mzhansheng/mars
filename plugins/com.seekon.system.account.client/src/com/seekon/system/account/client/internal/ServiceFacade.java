package com.seekon.system.account.client.internal;

import com.seekon.system.auth.spi.UserService;

public class ServiceFacade {

  public static UserService getUserService() {
    return AccountPlugin.injector.getInstance(UserService.class);
  }
}
