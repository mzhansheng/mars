package com.seekon.mars.rule.engine.specification;

import java.io.Serializable;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public interface IRuleSpecification extends Serializable{

  public boolean isSatisfiedBy(RuleEntity candidate);

  public Object accept(Visitor visitor);
  
}
