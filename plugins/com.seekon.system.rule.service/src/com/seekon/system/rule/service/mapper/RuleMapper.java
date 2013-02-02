package com.seekon.system.rule.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.model.Rule;

public interface RuleMapper {

  public List<Rule> getRuleList();

  @ControlDataUpdater(tableName = "m_sys_rule", keyField = "rule.id")
  public void addRule(@Param("rule")
  Rule rule);

  @ControlDataUpdater(tableName = "m_sys_rule", keyField = "rule.id")
  public void updateRule(@Param("rule")
  Rule rule);

  public void deleteRule(@Param("rule")
  Rule rule);

  public void deleteUserRule(@Param("rule")
  Rule rule);

  public void deleteRoleRule(@Param("rule")
  Rule rule);

  public void deleteRuleByCategory(@Param("category")
  Category category);

  public void deleteUserRuleByCategory(@Param("category")
  Category category);

  public void deleteRoleRuleByCategory(@Param("category")
  Category category);
}
