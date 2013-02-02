package com.seekon.smartclient.hessiansupport.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Stack;

import com.seekon.smartclient.hessiansupport.service.Interceptor;

public class RemoteServiceInvocationHandler<T> implements InvocationHandler,
  Serializable {

  private static final long serialVersionUID = 4445523623127859705L;

  private T targetSource;

  private Class<?>[] proxiedInterfaces;

  private Stack<Interceptor> interceptors;

  public RemoteServiceInvocationHandler(T targetSource) {
    this(targetSource, targetSource.getClass().getInterfaces(), null);
  }

  public RemoteServiceInvocationHandler(T targetSource,
    Stack<Interceptor> interceptors) {
    this(targetSource, targetSource.getClass().getInterfaces(), interceptors);
  }

  public RemoteServiceInvocationHandler(T targetSource, Class<?>[] classes,
    Stack<Interceptor> interceptors) {
    this.targetSource = targetSource;
    this.proxiedInterfaces = classes;
    this.interceptors = interceptors;
  }

  public Object getProxy(ClassLoader cl) {
    return Proxy.newProxyInstance(cl, proxiedInterfaces, this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Invocation invocation = new Invocation(interceptors, targetSource, method, args);
    return invocation.invoke();
  }

}
