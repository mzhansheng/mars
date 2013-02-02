package com.seekon.mars.rule.engine.specification.ext;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class EqualsRuleSpecification extends AbstractCustomRuleSpecification {
  
  public EqualsRuleSpecification() {
    super();
  }

  public EqualsRuleSpecification(Object property, Object target) {
    super(property, target);
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    Object value = getEntityPropertyValue(candidate);
    return value != null && value.equals(target);
  }

  @Override
  public String toString() {
    return property + "的值 = " + target;
  }
}
