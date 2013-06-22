package com.seekon.mars.wf.engine;

import com.seekon.mars.wf.engine.internal.Activator;

public class WFClient {

  public static WFEngine getWFEngine(){
    return Activator.getInjector().getInstance(WFEngine.class);
  }
}
