package com.seekon.system.rule.client.view.rule.basicinfo;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.Action;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.rule.client.module.RuleResourceRegistry;
import com.seekon.system.rule.model.Rule;

public class RuleBasicPage extends VESPage<Rule> {

  private static final long serialVersionUID = 1584894781454241477L;

  private RuleBasicDetail ruleBasicDetail;

  public RuleBasicPage(Rule rule) {
    super();
    ruleBasicDetail.initPage(rule);
  }

  @Override
  protected AbstractForm<Rule> getForm() {
    return new RuleBasicForm();
  }

  @Override
  protected AbstractDetail<Rule> getDetail() {
    ruleBasicDetail = new RuleBasicDetail();
    return ruleBasicDetail;
  }

  @Override
  protected boolean isEditableByAuth() {
    Action editAction = ActionHelper.getEditAction();
    return AuthUtil.isActionAuthorizedToUser(RuleResourceRegistry.KEY_RULE_RESOURCE,
      editAction)
      || AuthUtil.isActionAuthorizedToUser("system.auth.data.scope", editAction);//TODO
  }
}
