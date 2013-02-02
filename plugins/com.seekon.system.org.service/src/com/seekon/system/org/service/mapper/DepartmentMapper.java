package com.seekon.system.org.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.org.model.Department;

public interface DepartmentMapper {

  public List<Department> getDepartmentListByEntId(@Param("id")
  String entId);

  public Department getDepartmentById(String deptId);
  
  @ControlDataUpdater(tableName = "m_ele_department", keyField = "department.id")
  public void addDepartment(@Param("department")
  Department department);

  @ControlDataUpdater(tableName = "m_ele_department", keyField = "department.id")
  public void updateDepartment(@Param("department")
  Department department);

  public void deleteDepartment(@Param("department")
  Department department);
}
