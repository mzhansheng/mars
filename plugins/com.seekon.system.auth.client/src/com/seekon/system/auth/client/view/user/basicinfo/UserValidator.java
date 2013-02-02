package com.seekon.system.auth.client.view.user.basicinfo;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;
import com.seekon.system.auth.client.util.ValidationUtil;
import com.seekon.system.auth.model.User;

public class UserValidator implements Validator<User> {

  @Override
  public ValidationResult validate(User user) {
    PropertyValidationSupport support = new PropertyValidationSupport(user, "用户");

    if (ValidationUtil.isBlank(user.getUserCode()))
      support.addError("编码", "不能为空！");
    else if (!ValidationUtils.hasBoundedLength(user.getUserCode(), 4, 10))
      support.addError("编码", "长度大于4小于10！");

    if (ValidationUtil.isBlank(user.getUserName()))
      support.addError("名称", "不能为空！");
    else if (!ValidationUtils.hasMaximumLength(user.getUserName(), 10))
      support.addError("名称", "长度小于10！");

    if (ValidationUtil.isBlank(user.getPassword()))
      support.addError("密码", "不能为空！");
    else if (!ValidationUtils.hasMinimumLength(user.getPassword(), 6))
      support.addError("密码", "长度大于6！");

    return support.getResult();
  }

}
