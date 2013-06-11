package com.seekon.system.common.client.element;

import java.util.HashMap;
import java.util.Map;

import com.seekon.system.common.client.internal.CommonPlugin;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.spi.ElementService;

public class ElementStorage {

  private static Map<String, Element> elementMap = new HashMap<String, Element>();

  private ElementStorage() {
    super();
  }

  public static Element getElementById(String id){
    Element element = elementMap.get(id);
    if(element == null){
      ElementService elementService = CommonPlugin.getElementService();
      element = elementService.getElementById(id);
      elementMap.put(id, element);
    }
    return element;
  }
}
