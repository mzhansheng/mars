package com.seekon.system.auth.client.module;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.dictionary.MetaStore;
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
    user.setTable(MetaStore.getTableMetaByCode("m_sys_user"));
    user.addField(MetaStore.getFieldMeta("m_sys_user", "creator"));
    user.addField(MetaStore.getFieldMeta("m_sys_user", "user_code"));
    user.addField(MetaStore.getFieldMeta("m_sys_user", "user_name"));
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
