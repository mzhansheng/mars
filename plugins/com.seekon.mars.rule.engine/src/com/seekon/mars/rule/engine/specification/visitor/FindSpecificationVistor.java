package com.seekon.mars.rule.engine.specification.visitor;

import com.seekon.mars.rule.engine.specification.AndRuleSpecification;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.mars.rule.engine.specification.NotRuleSpecification;
import com.seekon.mars.rule.engine.specification.OrRuleSpecification;
import com.seekon.mars.rule.engine.specification.Visitor;
import com.seekon.mars.rule.engine.specification.ext.AbstractCustomRuleSpecification;

public class FindSpecificationVistor implements Visitor {

  private Object property;

  public FindSpecificationVistor(Object property) {
    super();
    this.property = property;
  }

  @Override
  public Object visitSpecification(IRuleSpecification specification) {
    if (specification == null) {
      return null;
    }

    if (specification instanceof AbstractCustomRuleSpecification) {
      if (property.equals(((AbstractCustomRuleSpecification) specification)
        .getProperty())) {
        return specification;
      }
    } else {
      return specification.accept(this);
    }
    return null;
  }

  @Override
  public Object visitAndRuleSpecification(AndRuleSpecification specification) {
    Object value = this.visitSpecification(specification.getOne());
    if (value == null) {
      value = this.visitSpecification(specification.getAnother());
    }
    return value;
  }

  @Override
  public Object visitOrRuleSpecification(OrRuleSpecification specification) {
    Object value = this.visitSpecification(specification.getOne());
    if (value == null) {
      value = this.visitSpecification(specification.getAnother());
    }
    return value;
  }

  @Override
  public Object visitNotRuleSpecification(NotRuleSpecification specification) {
    return this.visitSpecification(specification);
  }
}
