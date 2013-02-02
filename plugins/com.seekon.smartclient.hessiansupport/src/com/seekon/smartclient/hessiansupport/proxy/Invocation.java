package com.seekon.smartclient.hessiansupport.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Stack;

import com.seekon.smartclient.hessiansupport.service.Interceptor;

/**
 * 
 * 使用拦截器堆栈对方法进行拦截
 *
 */
public class Invocation implements Serializable{

  private static final long serialVersionUID = -1781585963734690445L;
  
  private Stack<Interceptor> interceptors;
  
  private Object targetObject;
  
  private Method method;
  
  private Object[] args;
  
  public Invocation(Stack<Interceptor> interceptors, Object targetObject, Method method, Object[] args){
    this.targetObject = targetObject;
    this.method = method;
    this.args = args;
    this.interceptors = interceptors;
  }

  public Stack<Interceptor> getInterceptors() {
    return interceptors;
  }

  public void setInterceptors(Stack<Interceptor> interceptors) {
    this.interceptors = interceptors;
  }

  public Object getTargetObject() {
    return targetObject;
  }

  public void setTargetObject(Object targetObject) {
    this.targetObject = targetObject;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }
  
  public Object invoke() throws Throwable{
    if(interceptors == null || interceptors.isEmpty()){
      return method.invoke(targetObject, args);
    }
    
    Interceptor interceptor = null;
    synchronized(this){//是否会有多线程的问题？
      interceptor = (Interceptor)interceptors.pop();
    }
    return interceptor.invoke(this);
  }
}
