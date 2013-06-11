package com.seekon.smartclient.hessiansupport.proxy;

import java.util.Comparator;
import java.util.Stack;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.hessiansupport.service.Interceptor;

public class InterceptorRegistry {

  private static final String EXTENSION_POINT_ID = "com.seekon.smartclient.hessiansupport.interceptors";

  private static final String ATT_CLASS = "class";

  private static final String ATT_INDEX = "index";

  Stack<Interceptor> interceptorList = new Stack<Interceptor>();

  private static final InterceptorRegistry instance = new InterceptorRegistry();

  public static InterceptorRegistry getInstance() {
    return instance;
  }

  private InterceptorRegistry() {
    super();

    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    if (extensionRegistry == null) {
      return;
    }

    IConfigurationElement[] extensions = extensionRegistry
      .getConfigurationElementsFor(EXTENSION_POINT_ID);

    if (extensions != null && extensions.length > 0) {
      java.util.Arrays.sort(extensions, new InterceptorElementComparator());
    }

    for (IConfigurationElement element : extensions) {
      try {
        interceptorList.push((Interceptor) element
          .createExecutableExtension(ATT_CLASS));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public Stack<Interceptor> getInterceptorList() {
    return interceptorList;
  }

  class InterceptorElementComparator implements Comparator<IConfigurationElement> {

    @Override
    public int compare(IConfigurationElement o1, IConfigurationElement o2) {
      int index1 = 0;
      try {
        index1 = Integer.parseInt(o1.getAttribute(ATT_INDEX));
      } catch (Exception e) {
      }
      int index2 = 0;
      try {
        index2 = Integer.parseInt(o2.getAttribute(ATT_INDEX));
      } catch (Exception e) {
      }

      return index2 - index1;
    }

  }
}
