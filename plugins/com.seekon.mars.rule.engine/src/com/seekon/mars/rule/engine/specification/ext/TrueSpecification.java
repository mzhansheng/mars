package com.seekon.mars.rule.engine.specification.ext;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class TrueSpecification extends AbstractCustomRuleSpecification{

  public TrueSpecification() {
    super();
  }

  public TrueSpecification(String propertyName) {
    super(propertyName, true);
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    return true;
  }

  @Override
  public String toString() {
    return "true";
  }
}
