package com.seekon.system.rule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seekon.system.common.model.Category;

public class Rule implements Serializable {

  private static final long serialVersionUID = 9157492791428755237L;

  private String id;

  private String ruleName;

  private Category category;

  private Map<String, RuleElement> ruleElementMap = new HashMap<String, RuleElement>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRuleName() {
    return ruleName;
  }

  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<RuleElement> getRuleElementList() {
    return new ArrayList<RuleElement>(ruleElementMap.values());
  }

  public void setRuleElementList(List<RuleElement> ruleElementList) {
    for (RuleElement ruleElement : ruleElementList) {
      ruleElementMap.put(ruleElement.getElementCode(), ruleElement);
    }
  }

  public void addRuleElement(RuleElement ruleElement, boolean replace) {
    boolean contain = this.ruleElementMap.keySet().contains(
      ruleElement.getElementCode());
    if ((!contain) || (contain && replace)) {
      this.ruleElementMap.put(ruleElement.getElementCode(), ruleElement);
      ruleElement.setRule(this);
    }
  }

  public RuleElement getRuleElementByElementCode(String elementCode) {
    return this.ruleElementMap.get(elementCode);
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
    Rule other = (Rule) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return this.ruleName;
  }
}
