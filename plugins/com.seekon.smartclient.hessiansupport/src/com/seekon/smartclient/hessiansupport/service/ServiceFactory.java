package com.seekon.smartclient.hessiansupport.service;

public abstract class ServiceFactory {

  /**
   * 当前工厂
   */
  private static ServiceFactory current = new RemoteServiceFactory();

  /**
   * 根据系统配置，返回系统当前的ServiceFactory实例对象
   * @return
   */
  public static ServiceFactory currentServiceFactory() {
    return current;
  }

  /**
   * 返回对应接口的Service对象
   * @param <T>
   * @param t
   * @return
   * @throws ServiceNotFoundException
   */
  public abstract <T> T getService(Class<T> t) throws ServiceNotFoundException;
  
  public abstract <T> T getService(Class<T> t, ClassLoader cl) throws ServiceNotFoundException;
}
