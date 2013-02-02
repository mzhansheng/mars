package com.seekon.system.org.client.view.ent;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.seekon.system.org.model.Enterprise;

public class EnterpriseValidator implements Validator<Enterprise> {

  @Override
  public ValidationResult validate(Enterprise enterprise) {
    PropertyValidationSupport support = new PropertyValidationSupport(enterprise,
      "单位");
    String code = enterprise.getCode();
    if (code == null || code.trim().length() == 0) {
      support.addError("单位代码", "不能为空.");
    }
    String name = enterprise.getName();
    if (name == null || name.trim().length() == 0) {
      support.addError("单位名称", "不能为空.");
    }

    return support.getResult();
  }

}
