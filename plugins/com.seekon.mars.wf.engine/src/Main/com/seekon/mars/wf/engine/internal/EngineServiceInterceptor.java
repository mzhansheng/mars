package com.seekon.mars.wf.engine.internal;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Stack;

import com.seekon.mars.wf.engine.internal.interceptor.Invocation;

/**
 * 
 * 引擎服务的拦截器
 *
 */
public class EngineServiceInterceptor implements InvocationHandler, Serializable{

  private static final long serialVersionUID = -18635395241158861L;

  private Object targetSource;
  
  private Class[] proxiedInterfaces;
  
  private Stack interceptors;
  
  public EngineServiceInterceptor(Object targetSource){
    this(targetSource, targetSource.getClass().getInterfaces(), null);
  }
  
  public EngineServiceInterceptor(Object targetSource, Stack interceptors){
    this(targetSource, targetSource.getClass().getInterfaces(), interceptors);
  }
  public EngineServiceInterceptor(Object targetSource, Class[] proxiedInterfaces, Stack interceptors){
    this.targetSource = targetSource;
    this.proxiedInterfaces = proxiedInterfaces;
    this.interceptors = interceptors;
  }
  
  public Object getProxy(){
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    return Proxy.newProxyInstance(cl, proxiedInterfaces, this);
  }
  
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Invocation invocation = new Invocation(interceptors, targetSource, method, args);
    return invocation.invoke();
  }
    
}
