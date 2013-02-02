package com.seekon.mars.rule.engine.specification.ext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.seekon.mars.rule.engine.entity.RuleEntity;

public class InCollectionSpecification extends AbstractCustomRuleSpecification {

  private Set set;

  public InCollectionSpecification() {
    super();
  }

  public InCollectionSpecification(Object property, List objs) {
    super(property, objs);
    set = new HashSet(objs);
  }

  @Override
  public boolean isSatisfiedBy(RuleEntity candidate) {
    return set.contains(getEntityPropertyValue(candidate));
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(property + "的值 在 ");
    for(Object obj : set){
      sb.append(obj + ",");
    }
    return sb.substring(0, sb.length()) + "之内";
  }
}
