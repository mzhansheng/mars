package com.seekon.mars.db.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.MappedStatement;

public class MybatisInterceptorUtil {

  public static <T extends Annotation> Annotation getMapperClassMethodAnnotation(
    MappedStatement ms, Class<T> annotationClass) {
    try {
      String id = ms.getId();
      int pos = id.lastIndexOf(".");
      String className = id.substring(0, pos);
      String methodName = id.substring(pos + 1);

      Class<?> clazz = ms.getConfiguration().getClass();
      Field field = clazz.getDeclaredField("mapperRegistry");
      field.setAccessible(true);
      MapperRegistry mapperRegistry = (MapperRegistry) field.get(ms
        .getConfiguration());
      clazz = mapperRegistry.getClass();
      field = clazz.getDeclaredField("knownMappers");
      field.setAccessible(true);
      Set<Class<?>> knownMappers = (Set<Class<?>>) field.get(mapperRegistry);

      clazz = null;
      for (Class mapper : knownMappers) {
        if (mapper.getName().equals(className)) {
          clazz = mapper;
          break;
        }
      }
      if (clazz == null) {
        return null;
      }

      //      Class parameterType = ms.getParameterMap().getType();
      //      clazz.getDeclaredMethods();
      //      List<Class> paramList = new ArrayList<Class>();
      //      if (param instanceof Map) {
      //        Map paramMap = (Map) param;
      //        Iterator<String> iterator = paramMap.keySet().iterator();
      //        while (iterator.hasNext()) {
      //          String paramName = iterator.next();
      //          if (paramName.startsWith("param")) {
      //            continue;
      //          }
      //          
      //          Object value = paramMap.get(paramName);
      //          if(value == null){
      //            paramList.add(parameterType);
      //          }else{
      //            paramList.add(value.getClass());
      //          }
      //        }
      //      } else {
      //        paramList.add(param.getClass());
      //      }
      //      Method method = clazz.getDeclaredMethod(methodName,
      //        paramList.toArray(new Class[paramList.size()]));
      Method method = clazz.getDeclaredMethod(methodName, ms.getParameterMap()
        .getType());
      return method.getAnnotation(annotationClass);
    } catch (Throwable e) {
      // do nothing.
    }
    return null;
  }
}
