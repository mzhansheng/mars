package com.seekon.mars.rule.engine.specification;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class AndRuleSpecification extends CompositeRuleSpecification {

  private IRuleSpecification one;

  private IRuleSpecification another;

  public AndRuleSpecification() {
    super();
  }

  public AndRuleSpecification(IRuleSpecification one, IRuleSpecification another) {
    this.one = one;
    this.another = another;
  }

  public void setOne(IRuleSpecification one) {
    this.one = one;
  }

  public void setAnother(IRuleSpecification another) {
    this.another = another;
  }

  public IRuleSpecification getOne() {
    return one;
  }

  public IRuleSpecification getAnother() {
    return another;
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    return one.isSatisfiedBy(candidate) && another.isSatisfiedBy(candidate);
  }

  @Override
  public Object accept(Visitor visitor) {
    return visitor.visitAndRuleSpecification(this);
  }

  @Override
  public String toString() {
    return "(" + one.toString() + "( AND (" + another.toString() + ")";
  }
}
