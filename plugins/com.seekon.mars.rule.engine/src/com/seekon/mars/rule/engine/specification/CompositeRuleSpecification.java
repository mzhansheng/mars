package com.seekon.mars.rule.engine.specification;

/**
 * 
 * 组合规格，含有“与、或、非”的逻辑操作
 *
 */
public abstract class CompositeRuleSpecification implements IRuleSpecification {

  public CompositeRuleSpecification and(IRuleSpecification other) {
    return new AndRuleSpecification(this, other);
  }

  public CompositeRuleSpecification or(IRuleSpecification other) {
    return new OrRuleSpecification(this, other);
  }

  public CompositeRuleSpecification not() {
    return new NotRuleSpecification(this);
  }

}
