package com.seekon.mars.wf.engine.event;

import java.util.EventObject;

/**
 * 
 * 模板相关事件，当模板有所改动时发送此事件
 *
 */
public class TemplateEvent extends EventObject{

  private static final long serialVersionUID = -7412140263554973450L;
  
  public TemplateEvent(Object source) {
    super(source);
  }

}
