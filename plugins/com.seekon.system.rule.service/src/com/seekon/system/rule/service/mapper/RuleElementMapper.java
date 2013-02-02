package com.seekon.system.rule.service.mapper;

import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.model.RuleElement;


public interface RuleElementMapper {

  @ControlDataUpdater(tableName = "m_sys_rule_element", keyField = "id")
  public void updateRuleElement(RuleElement ruleElement);

  @ControlDataUpdater(tableName = "m_sys_rule_element", keyField = "id")
  public void addRuleElement(RuleElement ruleElement);

  public void deleteRuleElement(RuleElement ruleElement);
  
  public void deleteRuleElementByCategory(Category category);
}
