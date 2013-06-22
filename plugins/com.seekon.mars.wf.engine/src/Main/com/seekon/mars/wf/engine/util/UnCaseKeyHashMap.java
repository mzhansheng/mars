package com.seekon.mars.wf.engine.util;

import java.util.HashMap;

/**
 * 
 * 针对key为String类型的hashmap，不区分key的大小写
 *
 */
public class UnCaseKeyHashMap extends HashMap{

  private static final long serialVersionUID = -2063103396128556061L;
  
  public Object put(Object key, Object value){
    if(key instanceof String){
      return super.put(((String)key).toUpperCase(), value);
    }
    return super.put(key, value);
  }
  
  public Object get(Object key){
    if(key instanceof String){
      return super.get(((String)key).toUpperCase());
    }
    return super.get(key);
  }
}
