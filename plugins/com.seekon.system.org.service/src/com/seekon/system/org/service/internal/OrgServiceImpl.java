package com.seekon.system.org.service.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;

import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;
import com.seekon.system.org.service.mapper.DepartmentMapper;
import com.seekon.system.org.service.mapper.EnterpriseMapper;
import com.seekon.system.org.spi.OrgService;

public class OrgServiceImpl implements OrgService {

  @Inject
  private EnterpriseMapper entMapper;

  @Inject
  private DepartmentMapper deptMapper;

  @Override
  public List<AbstractOrgItem> getChildOrgItems(Enterprise ent) {
    List<AbstractOrgItem> result = new ArrayList<AbstractOrgItem>();
    result.addAll(entMapper.getEnterpriseListByParentId(ent.getId()));
    if(ent.getId() != null){
      result.addAll(deptMapper.getDepartmentListByEntId(ent.getId()));
    }
    return result;
  }
  
  @Override
  public Department getDepartmentById(String id) {
    return deptMapper.getDepartmentById(id);
  }
  
  @Override
  @Transactional
  public Enterprise addEnterprise(Enterprise enterprise) {
    enterprise.setId(UUID.randomUUID().toString());
    entMapper.addEnterprise(enterprise);
    return enterprise;
  }

  @Override
  @Transactional
  public void updateEnterprise(Enterprise enterprise) {
    entMapper.updateEnterprise(enterprise);
  }

  @Override
  @Transactional
  public void deleteEnterpriseList(List<Enterprise> entList) {
    for (Enterprise ent : entList) {
      deleteEnterprise(ent);
    }
  }

  @Transactional
  private void deleteEnterprise(Enterprise enterprise) {
    for (AbstractOrgItem item : enterprise.getChildOrgItemList()) {
      if (item instanceof Enterprise) {
        deleteEnterprise((Enterprise) item);
      } else if (item instanceof Department) {
        deleteDepartment((Department) item);
      }
    }
    entMapper.deleteEnterprise(enterprise);
  }

  @Override
  @Transactional
  public Department addDepartment(Department department) {
    department.setId(UUID.randomUUID().toString());
    deptMapper.addDepartment(department);
    return department;
  }

  @Override
  @Transactional
  public void updateDepartment(Department department) {
    deptMapper.updateDepartment(department);
  }

  @Override
  @Transactional
  public void deleteDepartmentList(List<Department> deptList) {
    for (Department dept : deptList) {
      deleteDepartment(dept);
    }
  }

  @Transactional
  private void deleteDepartment(Department department) {
    deptMapper.deleteDepartment(department);
  }
}
