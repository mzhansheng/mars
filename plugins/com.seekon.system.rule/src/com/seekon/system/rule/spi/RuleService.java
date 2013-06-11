package com.seekon.system.rule.spi;

import java.util.List;

import com.seekon.system.common.spi.CategoryService;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;

public interface RuleService extends CategoryService {

  public List<Rule> getRuleList();

  public Rule addRule(Rule rule);

  public void updateRule(Rule rule);

  public void deleteRuleList(List<Rule> ruleList);

  public RuleElement addRuleElement(RuleElement ruleElement);

  public void updateRuleElement(RuleElement ruleElement);
}
