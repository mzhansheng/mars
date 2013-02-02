package com.seekon.system.rule.service.impl;

import java.util.List;
import java.util.UUID;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.service.AbstractCategoryService;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;
import com.seekon.system.rule.service.mapper.RuleElementMapper;
import com.seekon.system.rule.service.mapper.RuleMapper;
import com.seekon.system.rule.spi.RuleService;

public class RuleServiceImpl extends AbstractCategoryService implements RuleService {

  @Inject
  RuleMapper ruleMapper;

  @javax.inject.Inject
  RuleElementMapper ruleElementMapper;

  @Override
  public Category getCategory() {
    return getCategoryById("sys.rule.set");
  }
  
  @Override
  public void deleteCategory(Category category) {
    super.deleteCategory(category);
    ruleElementMapper.deleteRuleElementByCategory(category);
    ruleMapper.deleteRoleRuleByCategory(category);
    ruleMapper.deleteUserRuleByCategory(category);
    ruleMapper.deleteRuleByCategory(category);
  }
  
  @Override
  public List<Rule> getRuleList() {
    return ruleMapper.getRuleList();
  }

  @Override
  @Transactional
  public Rule addRule(Rule rule) {
    addRuleBasicInfo(rule);
    for (RuleElement ruleElement : rule.getRuleElementList()) {
      addRuleElement(ruleElement);
    }
    return rule;
  }

  @Transactional
  private Rule addRuleBasicInfo(Rule rule){
    Category category = rule.getCategory();
    if(category.getId() == null || category.getId().trim().length() == 0){
      category.setId(UUID.randomUUID().toString());
      rule.setCategory(addCategory(category));
    }
    
    rule.setId(UUID.randomUUID().toString());
    ruleMapper.addRule(rule);
    return rule;
  }
  
  @Override
  @Transactional
  public void updateRule(Rule rule) {
    ruleMapper.updateRule(rule);
  }

  @Override
  @Transactional
  public void deleteRuleList(List<Rule> ruleList) {
    for (Rule rule : ruleList) {
      this.deleteRule(rule);
    }
  }

  @Transactional
  private void deleteRule(Rule rule) {
    ruleMapper.deleteRule(rule);
    ruleMapper.deleteUserRule(rule);
    ruleMapper.deleteRoleRule(rule);
    for (RuleElement ruleElement : rule.getRuleElementList()) {
      ruleElementMapper.deleteRuleElement(ruleElement);
    }
  }

  @Override
  @Transactional
  public RuleElement addRuleElement(RuleElement ruleElement) {
    ruleElement.setId(UUID.randomUUID().toString());
    ruleElementMapper.addRuleElement(ruleElement);
    return ruleElement;
  }

  @Override
  @Transactional
  public void updateRuleElement(RuleElement ruleElement) {
    ruleElementMapper.updateRuleElement(ruleElement);
  }
}
