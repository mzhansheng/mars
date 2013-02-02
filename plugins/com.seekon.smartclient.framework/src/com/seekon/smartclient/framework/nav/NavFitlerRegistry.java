package com.seekon.smartclient.framework.nav;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.framework.NavigationResourceFilter;

final class NavFitlerRegistry {

  private static final String NAV_FILTER_EXTENSION_POINT_ID = "com.seekon.smartclient.framework.navigationFilters";

  private static final String ATT_CLASS = "class";

  private List<NavigationResourceFilter> navFilterList = new ArrayList<NavigationResourceFilter>();

  private static NavFitlerRegistry instance = new NavFitlerRegistry();
  
  private NavFitlerRegistry() {
    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    if (extensionRegistry == null) {
      return;
    }
    IConfigurationElement[] extensions = extensionRegistry
      .getConfigurationElementsFor(NAV_FILTER_EXTENSION_POINT_ID);

    for (int i = 0; i < extensions.length; i++) {
      IConfigurationElement element = extensions[i];
      String className = element.getAttribute(ATT_CLASS);
      if (className != null) {
        try {
          navFilterList.add((NavigationResourceFilter) element
            .createExecutableExtension(ATT_CLASS));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public List<NavigationResourceFilter> getNavFilterList() {
    return navFilterList;
  }

  public static NavFitlerRegistry getInstance() {
    return instance;
  }
  
}
