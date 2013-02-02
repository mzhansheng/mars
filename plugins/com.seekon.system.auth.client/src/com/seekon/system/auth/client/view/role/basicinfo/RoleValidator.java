package com.seekon.system.auth.client.view.role.basicinfo;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.seekon.system.auth.client.util.ValidationUtil;
import com.seekon.system.auth.model.Role;

public class RoleValidator implements Validator<Role> {

  @Override
  public ValidationResult validate(Role role) {
    PropertyValidationSupport support = new PropertyValidationSupport(role, "角色");

    if (ValidationUtil.isBlank(role.getRoleCode())) {
      support.addError("代码", "不能为空！");
    }

    if (ValidationUtil.isBlank(role.getRoleName())) {
      support.addError("名称", "不能为空！");
    }

    return support.getResult();
  }

}
