package com.seekon.mars.rule.engine.specification.ext;

import com.seekon.mars.rule.engine.entity.RuleEntity;
import com.seekon.mars.rule.engine.specification.CompositeRuleSpecification;
import com.seekon.mars.rule.engine.specification.Visitor;

public abstract class AbstractCustomRuleSpecification extends
  CompositeRuleSpecification {

  protected Object property;
  
  protected Object target;
  
  public AbstractCustomRuleSpecification() {
    super();
  }

  public AbstractCustomRuleSpecification(Object property, Object target) {
    super();
    this.property = property;
    this.target = target;
  }

  public Object getProperty() {
    return property;
  }

  public void setProperty(Object property) {
    this.property = property;
  }

  public Object getTarget() {
    return target;
  }

  public void setTarget(Object target) {
    this.target = target;
  }

  protected Object getEntityPropertyValue(RuleEntity candidate) {
    return candidate.getProperty(property.toString());
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visitSpecification(this);
  }
}
