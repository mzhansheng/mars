package com.seekon.system.org.client.module;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.dictionary.MetaStore;
import com.seekon.system.common.ElementRegistry;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;

public class OrgElementRegistry implements ElementRegistry {

  private List<Element> elementList = new ArrayList<Element>();

  public OrgElementRegistry() {
    super();
    initElementList();
  }

  private void initElementList() {
    Element enterprise = new Element("ele_enterprise", "单位");    
    enterprise.addField(MetaStore.getFieldMeta("m_ele_enterprise", "ent_code"));
    enterprise.addField(MetaStore.getFieldMeta("m_ele_enterprise", "ent_type"));
    elementList.add(enterprise);

    Element department = new Element("ele_department", "科处室");
    department.addField(MetaStore.getFieldMeta("m_ele_department", "dept_code"));
    department.addField(MetaStore.getFieldMeta("m_ele_department", "dept_name"));
    elementList.add(department);
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
