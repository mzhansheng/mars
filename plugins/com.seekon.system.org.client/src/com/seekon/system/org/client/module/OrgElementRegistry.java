package com.seekon.system.org.client.module;

import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.dictionary.model.FieldMeta;
import com.seekon.mars.dictionary.model.TableMeta;
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
    TableMeta entTable = new TableMeta("m_ele_enterprise", "单位表");
    enterprise.addField(new FieldMeta("ent_code", "单位代码", "text", entTable));
    enterprise.addField(new FieldMeta("ent_type", "单位类型", "text", entTable));
    elementList.add(enterprise);

    Element department = new Element("ele_department", "科处室");
    TableMeta deptTable = new TableMeta("m_ele_department", "科处室表");
    department.addField(new FieldMeta("dept_code", "科处室代码", "text", deptTable));
    department.addField(new FieldMeta("dept_name", "科处室名称", "text", deptTable));
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
