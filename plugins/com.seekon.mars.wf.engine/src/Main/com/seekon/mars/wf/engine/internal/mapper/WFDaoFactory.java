package com.seekon.mars.wf.engine.internal.mapper;

import com.seekon.mars.wf.engine.internal.Activator;

public class WFDaoFactory {

  public static WFRuntimeMapper getWFRuntimeDao(){
    return Activator.getInjector().getInstance(WFRuntimeMapper.class);
  }
  
  public static WFMetaMapper getWFMetaDao(){
    return Activator.getInjector().getInstance(WFMetaMapper.class);
  }

}
