package com.seekon.mars.wf.engine.internal.interceptor;

import java.io.Serializable;

/**
 * 
 * 拦截器
 * 
 */
public interface Interceptor extends Serializable{

  public Object invoke(Invocation invocation) throws Throwable;
  
}
