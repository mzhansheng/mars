package com.seekon.system.rule.model;

import java.io.Serializable;

import com.seekon.mars.rule.engine.specification.CompositeRuleSpecification;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.system.common.model.Element;

public class RuleElement implements Serializable {

  private static final long serialVersionUID = -5417868198212695437L;

  private String id;

  private String elementCode;

  private CompositeRuleSpecification ruleSpecification;

  private Element element;

  private Rule rule;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getElementCode() {
    return elementCode;
  }

  public void setElementCode(String elementCode) {
    this.elementCode = elementCode;
  }

  public IRuleSpecification getRuleSpecification() {
    return ruleSpecification;
  }

  public void setRuleSpecification(CompositeRuleSpecification ruleSpecification) {
    this.ruleSpecification = ruleSpecification;
  }

  public Element getElement() {
    return element;
  }

  public void setElement(Element element) {
    this.element = element;
  }

  public Rule getRule() {
    return rule;
  }

  public void setRule(Rule rule) {
    this.rule = rule;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RuleElement other = (RuleElement) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("要素:" + (element != null ? element.getName() : elementCode)
      + "定义规则如下:");
    if (ruleSpecification != null) {
      sb.append("\n  " + ruleSpecification.toString());
    }
    sb.append("\n");
    return sb.toString();
  }
}
