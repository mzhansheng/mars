package com.seekon.system.auth.client.module;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.dictionary.model.FieldMeta;
import com.seekon.system.common.ElementRegistry;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;

public class AuthElementRegistry implements ElementRegistry {

  private List<Element> elementList = new ArrayList<Element>();

  public AuthElementRegistry() {
    super();
    initElementList();
  }

  private void initElementList() {
    Element user = new Element("sys_user", "用户");
    user.addField(new FieldMeta("creator", "创建者代码", "text"));
    user.addField(new FieldMeta("user_code", "用户代码", "text"));
    user.addField(new FieldMeta("user_name", "用户名称", "text"));
    elementList.add(user);
  }

  @Override
  public List<Element> getElementListByType(ElementType type) {
    List<Element> tempList = new ArrayList<Element>();
    for (Element element : elementList) {
      try {
        tempList.add((Element) element.clone());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }
    return tempList;
  }

}
