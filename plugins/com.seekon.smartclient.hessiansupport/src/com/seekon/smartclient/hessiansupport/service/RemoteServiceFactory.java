package com.seekon.smartclient.hessiansupport.service;

import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.SerializerFactory;
import com.seekon.mars.osgi.util.CompositeClassLoader;
import com.seekon.smartclient.hessiansupport.proxy.InterceptorRegistry;
import com.seekon.smartclient.hessiansupport.proxy.RemoteServiceInvocationHandler;

public final class RemoteServiceFactory extends ServiceFactory {
  /**
   * url前缀
   */
  protected static String PREFIX = "http://localhost:8090/mars/proxy/";

  static {
    PREFIX = System.getProperty("host", PREFIX);
  }

  private static volatile Map<Class<?>, Object> cache = new Hashtable<Class<?>, Object>();

  @Override
  public <T> T getService(Class<T> t) throws ServiceNotFoundException {
    return this.getService(t,
      new CompositeClassLoader(new ClassLoader[] { t.getClassLoader(),
        HessianProxyFactory.class.getClassLoader() }));
  }

  @Override
  public <T> T getService(Class<T> t, ClassLoader cl)
    throws ServiceNotFoundException {
    Object service = cache.get(t);
    if (service != null) {
      return (T) service;
    } else {
      synchronized (cache) {
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        factory.setSerializerFactory(new SerializerFactory(cl));

        PREFIX = System.getProperty("host", PREFIX);
        String url = PREFIX + t.getName() + ".hessian";
        try {
          T newService = (T) factory.create(t, url, cl);
          RemoteServiceInvocationHandler<T> handler = new RemoteServiceInvocationHandler<T>(
            newService, new Class[] { t }, InterceptorRegistry.getInstance()
              .getInterceptorList());
          newService = (T) handler.getProxy(cl);
          cache.put(t, newService);
          return newService;
        } catch (MalformedURLException e) {
          throw new ServiceNotFoundException();
        }
      }
    }
  }

  public static String getPREFIX() {
    return PREFIX;
  }
}
