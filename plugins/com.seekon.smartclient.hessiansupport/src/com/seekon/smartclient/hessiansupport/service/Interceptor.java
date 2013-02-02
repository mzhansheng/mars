package com.seekon.smartclient.hessiansupport.service;

import java.io.Serializable;

import com.seekon.smartclient.hessiansupport.proxy.Invocation;

public interface Interceptor extends Serializable {

  public Object invoke(Invocation invocation) throws Throwable;
}
