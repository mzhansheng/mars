package com.seekon.system.auth.client.util;

import java.util.List;

import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;
import com.seekon.system.common.model.Option;
import com.seekon.system.common.util.ElementRegistryUtils;

public class ElementUtil {

  public static final String KEY_AUTH_ELEMENT_OPTION = "auth.element.option";

  private ElementUtil() {
  }

  public static List<Element> getAllRegisteredAuthElementList() {
    List<Element> elementList = ElementRegistryUtils.getInstance()
      .getAllRegistriedElementsByType(ElementType.ELE_TYPE_AUTH_DATA_SCOPE);
    return elementList;
  }

  public static Option getAuthElementOption() {
    return ServiceFacade.getAuthElementService().getOptionByCode(
      KEY_AUTH_ELEMENT_OPTION);
  }

  public static List<Element> getAuthElementList(Option authElementOption,
    boolean removeUnsettingElement) {
    List<Element> elementList = getAllRegisteredAuthElementList();
    if (authElementOption == null) {
      authElementOption = getAuthElementOption();
    }
    
    if(authElementOption != null){
      List<Element> authElementList = (List<Element>) authElementOption.getOptValue();
      if(authElementList != null && !authElementList.isEmpty()){
        if(removeUnsettingElement){
          return authElementList;
        }else{
          elementList.removeAll(authElementList);
          elementList.addAll(authElementList);
        }
      }
    }
    return elementList;
  }
}
