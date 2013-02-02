package com.seekon.system.rule.client.view.rule.element;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.Action;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.rule.client.module.RuleResourceRegistry;
import com.seekon.system.rule.model.RuleElement;

public class RuleElementPage extends VESPage<RuleElement> {

  private static final long serialVersionUID = 2597247051679155851L;

  private RuleElementDetail ruleDetail;

  public RuleElementPage(RuleElement ruleElement) {
    super();
    this.ruleDetail.initPage(ruleElement);
  }

  @Override
  protected AbstractForm<RuleElement> getForm() {
    return new RuleElementForm();
  }

  @Override
  protected AbstractDetail<RuleElement> getDetail() {
    ruleDetail = new RuleElementDetail();
    return ruleDetail;
  }

  @Override
  protected boolean isEditableByAuth() {
    Action editAction = ActionHelper.getEditAction();
    return AuthUtil.isActionAuthorizedToUser(RuleResourceRegistry.KEY_RULE_RESOURCE,
      editAction)
      || AuthUtil.isActionAuthorizedToUser("system.auth.data.scope", editAction);//TODO
  }

}
