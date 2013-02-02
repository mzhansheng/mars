package com.seekon.mars.rule.engine.specification.ext;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class CharLengthLessThanSpeicification extends
  AbstractCustomRuleSpecification {

  public CharLengthLessThanSpeicification() {
    super();
  }

  public CharLengthLessThanSpeicification(String propertyName, int length) {
    super(propertyName, length);
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    int length = (Integer) target;
    Object value = getEntityPropertyValue(candidate);
    return value == null || (value.toString()).length() < length;
  }

  @Override
  public String toString() {
    return property + "值的长度 < " + target;
  }
}
