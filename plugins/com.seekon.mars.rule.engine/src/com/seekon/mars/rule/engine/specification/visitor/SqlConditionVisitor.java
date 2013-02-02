package com.seekon.mars.rule.engine.specification.visitor;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.mars.dictionary.model.FieldMeta;
import com.seekon.mars.rule.engine.specification.AndRuleSpecification;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.mars.rule.engine.specification.NotRuleSpecification;
import com.seekon.mars.rule.engine.specification.OrRuleSpecification;
import com.seekon.mars.rule.engine.specification.Visitor;
import com.seekon.mars.rule.engine.specification.ext.EqualsRuleSpecification;
import com.seekon.mars.rule.engine.specification.ext.TrueSpecification;

public class SqlConditionVisitor implements Visitor {

  @Override
  public Object visitSpecification(IRuleSpecification specification) {
    if (specification instanceof TrueSpecification) {
      return "(1=1)";
    } else if (specification instanceof EqualsRuleSpecification) {
      EqualsRuleSpecification tmp = (EqualsRuleSpecification) specification;
      Object target = tmp.getTarget();
      if(target == null){
        return "(1=1)";
      }
      String value = target.toString();
      if(value.startsWith("@") && value.endsWith("@")){
        value = value.substring(1, value.length() - 1);
        target = SessionContext.getCurrentHttpSession().get(value);
        if(target != null){
          target = "'" + target + "'";
        }
      }
      Object property = tmp.getProperty();
      if(property instanceof FieldMeta){
        property = ((FieldMeta)property).getColCode();
      }
      StringBuffer sb = new StringBuffer();
      sb.append("(");
      sb.append(property);
      sb.append("=");
      sb.append(target);
      sb.append(")");
      return sb.toString();
    }
    return null;//default
  }

  @Override
  public Object visitAndRuleSpecification(AndRuleSpecification specification) {
    StringBuffer sb = new StringBuffer();
    sb.append(" (");
    sb.append(specification.getOne().accept(this));
    sb.append(" and ");
    sb.append(specification.getAnother().accept(this));
    sb.append(" ) ");
    return sb.toString();
  }

  @Override
  public Object visitOrRuleSpecification(OrRuleSpecification specification) {
    StringBuffer sb = new StringBuffer();
    sb.append(" (");
    sb.append(specification.getOne().accept(this));
    sb.append(" or ");
    sb.append(specification.getAnother().accept(this));
    sb.append(" ) ");
    return sb.toString();
  }

  @Override
  public Object visitNotRuleSpecification(NotRuleSpecification specification) {
    return null;
  }
}
