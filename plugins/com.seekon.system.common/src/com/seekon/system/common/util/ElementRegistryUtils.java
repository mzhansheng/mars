package com.seekon.system.common.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.system.common.ElementRegistry;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;

public class ElementRegistryUtils {

  private static final String EXTENSION_POINT_ID = "com.seekon.system.common.elementRegistry";

  private static final String ATT_CLASS = "class";

  List<ElementRegistry> elementRegistryList = new ArrayList<ElementRegistry>();

  private static final ElementRegistryUtils instance = new ElementRegistryUtils();

  public static ElementRegistryUtils getInstance() {
    return instance;
  }

  private ElementRegistryUtils() {
    super();

    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    if (extensionRegistry == null) {
      return;
    }
    IConfigurationElement[] extensions = extensionRegistry
      .getConfigurationElementsFor(EXTENSION_POINT_ID);
    for (IConfigurationElement element : extensions) {
      try {
        elementRegistryList.add((ElementRegistry) element
          .createExecutableExtension(ATT_CLASS));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public List<ElementRegistry> getElementRegistryList() {
    return elementRegistryList;
  }

  public List<Element> getAllRegistriedElementsByType(ElementType type) {
    List<Element> elementList = new ArrayList<Element>();
    for (ElementRegistry registry : elementRegistryList) {
      elementList.addAll(registry.getElementListByType(type));
    }
    return elementList;
  }
}
