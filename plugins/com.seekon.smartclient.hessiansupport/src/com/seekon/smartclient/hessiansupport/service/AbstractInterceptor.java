package com.seekon.smartclient.hessiansupport.service;

import com.seekon.smartclient.hessiansupport.proxy.Invocation;

public abstract class AbstractInterceptor implements Interceptor {

  private static final long serialVersionUID = 8807167650620457317L;

  @Override
  public Object invoke(Invocation invocation) throws Throwable {
    before(invocation);
    Object invokeResult = invocation.invoke();
    return after(invokeResult);
  }

  public abstract void before(Invocation invocation);

  public abstract Object after(Object invokeResult);
}
