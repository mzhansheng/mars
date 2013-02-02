package com.seekon.mars.rule.engine.specification;

public interface Visitor {
  public Object visitSpecification(IRuleSpecification specification);
  
  public Object visitAndRuleSpecification(AndRuleSpecification specification);
  
  public Object visitOrRuleSpecification(OrRuleSpecification specification);
  
  public Object visitNotRuleSpecification(NotRuleSpecification specification);
}
