package com.seekon.system.rule.client.view.ruleset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.model.Rule;

public class RuleTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = -5949178557735099302L;

  private Map<Category, List<Rule>> ruleMap = new HashMap<Category, List<Rule>>();

  public RuleTreeModel(Category ruleCategory) {
    this(ruleCategory, ServiceFacade.getRuleService().getRuleList());
  }

  public RuleTreeModel(Category ruleCategory, List<Rule> ruleList) {
    super(new DefaultMutableTreeNode(ruleCategory));
    buildRuleMap(ruleList);
  }

  public void addRule(Rule rule) {
    List<Rule> ruleList = ruleMap.get(rule.getCategory());
    if (ruleList == null) {
      ruleList = new ArrayList<Rule>();
    }
    if (!ruleList.contains(rule)) {
      ruleList.add(rule);
    }
    ruleMap.put(rule.getCategory(), ruleList);
  }

  public void deleteRule(Rule rule) {
    List<Rule> ruleList = ruleMap.get(rule.getCategory());
    if (ruleList == null || !ruleList.contains(rule)) {
      return;
    }
    ruleList.remove(rule);
    ruleMap.put(rule.getCategory(), ruleList);
  }

  @Override
  public Object getChild(Object parent, int index) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    DefaultMutableTreeNode node = null;
    try {
      node = (DefaultMutableTreeNode) parentNode.getChildAt(index);
    } catch (Exception e) {
    }
    if (node == null) {
      Object userObj = parentNode.getUserObject();
      if (userObj instanceof Category) {
        Object child = null;
        if (((Category) userObj).getChildCount() > 0) {
          child = ((Category) userObj).getChild(index);
        } else {
          List<Rule> ruleList = ruleMap.get(userObj);
          child = ruleList.get(index);
        }
        node = new DefaultMutableTreeNode(child);
        parentNode.add(node);
      }
    }
    return node;
  }

  @Override
  public int getChildCount(Object parent) {
    int count = 0;
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    Object userObj = parentNode.getUserObject();
    if (userObj instanceof Category) {
      Category category = (Category) userObj;
      count = category.getChildCount();
      if (count == 0) {
        List<Rule> ruleList = ruleMap.get(category);
        if (ruleList != null) {
          count = ruleList.size();
        }
      }
    }
    return count;
  }

  @Override
  public boolean isLeaf(Object node) {
    return getChildCount(node) == 0;
  }

  private void buildRuleMap(List<Rule> ruleList) {
    if (ruleList == null || ruleList.isEmpty()) {
      return;
    }
    for (Rule rule : ruleList) {
      Category category = rule.getCategory();
      List<Rule> cateRuleList = ruleMap.get(category);
      if (cateRuleList == null) {
        cateRuleList = new ArrayList<Rule>();
      }
      if (!cateRuleList.contains(rule)) {
        cateRuleList.add(rule);
      }
      ruleMap.put(category, cateRuleList);
    }
  }
}
