package com.seekon.mars.wf.engine.internal.interceptor;


/**
 * 
 * 合法性验证拦截器
 *
 */
public class AuthorizationInterceptor implements Interceptor{

  private static final long serialVersionUID = 2724900617934964235L;

  public Object invoke(Invocation invocation) throws Throwable{
    return invocation.invoke();
  }

}
