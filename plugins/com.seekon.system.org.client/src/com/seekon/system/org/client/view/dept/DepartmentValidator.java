package com.seekon.system.org.client.view.dept;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.seekon.system.org.model.Department;

public class DepartmentValidator implements Validator<Department> {

  @Override
  public ValidationResult validate(Department enterprise) {
    PropertyValidationSupport support = new PropertyValidationSupport(enterprise,
      "内部机构");
    String code = enterprise.getCode();
    if (code == null || code.trim().length() == 0) {
      support.addError("机构代码", "不能为空.");
    }
    String name = enterprise.getName();
    if (name == null || name.trim().length() == 0) {
      support.addError("机构名称", "不能为空.");
    }

    return support.getResult();
  }

}
