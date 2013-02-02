package com.seekon.system.rule.client.view.rule.basicinfo;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.seekon.system.rule.model.Rule;

public class RuleValidator implements Validator<Rule> {

  @Override
  public ValidationResult validate(Rule rule) {
    PropertyValidationSupport support = new PropertyValidationSupport(rule, "规则");
    String ruleName = rule.getRuleName();
    if (ruleName == null || ruleName.trim().length() == 0) {
      support.addError("名称", "不能为空！");
    }
    return support.getResult();
  }

}
