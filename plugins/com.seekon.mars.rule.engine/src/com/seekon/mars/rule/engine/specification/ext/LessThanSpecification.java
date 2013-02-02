package com.seekon.mars.rule.engine.specification.ext;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class LessThanSpecification extends AbstractCustomRuleSpecification {

  public LessThanSpecification() {
    super();
  }

  public LessThanSpecification(Object property, Integer lng) {
    super(property, lng);
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    Integer lng = (Integer) target;
    Object value = getEntityPropertyValue(candidate);//TODO
    return value instanceof Integer && (Integer) value < lng;
  }
  
  @Override
  public String toString() {
    return property + "的值 <" + target;
  }
}
