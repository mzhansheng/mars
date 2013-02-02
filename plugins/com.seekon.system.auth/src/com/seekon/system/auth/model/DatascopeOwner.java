package com.seekon.system.auth.model;

import java.util.List;

import com.seekon.system.rule.model.Rule;

public interface DatascopeOwner {

  public List<Rule> getDatascopeRuleList();

  public void setDatascopeRuleList(List<Rule> datascopeRuleList);

  public void addDatascopeRule(Rule datascope);
}
