package com.seekon.smartclient.gui.util;

import java.lang.reflect.Method;

public class ReflectUtil {

  public static Object invoke(Object obj, String methodName, Class[] paramClasses
    , Object[] params) throws Exception{
    Method method = getMethodByLoop(obj.getClass(), methodName, paramClasses);
    if(method == null){
      throw new NoSuchMethodException(obj + methodName + params);
    }
    return method.invoke(obj, params);
  }
  
  public static Method getMethodByLoop(Class clazz, String methodName, Class[] paramClasses){
    Method method = null;
    try{
      method = clazz.getDeclaredMethod(methodName, paramClasses);
    }catch(Throwable e){
      method = getMethodByLoop(clazz.getSuperclass(), methodName, paramClasses);
    }
    return method;
  }
}
