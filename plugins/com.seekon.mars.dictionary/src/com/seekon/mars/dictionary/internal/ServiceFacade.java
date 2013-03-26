package com.seekon.mars.dictionary.internal;

import com.seekon.mars.dictionary.MetaService;

public class ServiceFacade {
  
  public static MetaService getMetaService(){
    return Activator.injector.getInstance(MetaService.class);
  }
}
