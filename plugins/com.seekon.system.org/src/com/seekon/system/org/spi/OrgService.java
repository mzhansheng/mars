package com.seekon.system.org.spi;

import java.util.List;

import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;

public interface OrgService {

  public List<AbstractOrgItem> getChildOrgItems(Enterprise ent);
  
  public Department getDepartmentById(String id);
  
  public Enterprise addEnterprise(Enterprise enterprise);

  public void updateEnterprise(Enterprise enterprise);

  public void deleteEnterpriseList(List<Enterprise> entList);

  public Department addDepartment(Department department);

  public void updateDepartment(Department department);

  public void deleteDepartmentList(List<Department> deptList);

}
