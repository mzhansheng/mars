package com.seekon.mars.rule.engine.specification;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class NotRuleSpecification extends CompositeRuleSpecification {

  private IRuleSpecification one;

  public NotRuleSpecification() {
    super();
  }

  public NotRuleSpecification(IRuleSpecification one) {
    super();
    this.one = one;
  }

  public IRuleSpecification getOne() {
    return one;
  }

  public void setOne(IRuleSpecification one) {
    this.one = one;
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    return !one.isSatisfiedBy(candidate);
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visitNotRuleSpecification(this);
  }

  @Override
  public String toString() {
    return " NOT (" + one.toString() + ")";
  }
}
