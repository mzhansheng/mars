package com.seekon.mars.rule.engine;

import com.seekon.mars.rule.engine.entity.RuleEntity;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;

public class RuleEngine {

  private static RuleEngine engine = new RuleEngine();

  private RuleEngine() {
  }

  public static RuleEngine getEngine() {
    return engine;
  }

  public boolean validateRule(RuleEntity entity, IRuleSpecification specification) {
    boolean satisfiedBy = specification.isSatisfiedBy(entity);
    return satisfiedBy;
  }

}
