package com.seekon.system.auth.client.view.role.datascope;

import java.util.List;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.view.AbstractDatascopeForm;
import com.seekon.system.auth.model.DatascopeOwner;
import com.seekon.system.auth.model.Role;
import com.seekon.system.rule.model.Rule;

public class RoleDatascopeForm extends AbstractDatascopeForm {

  private static final long serialVersionUID = 9077813997822716007L;

  @Override
  public DatascopeOwner save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Role>() {

      @Override
      public Role execute() throws Exception {
        Role role = (Role) owner;

        List<Rule> userRuleList = getSelectedDatascopeRuleList();
        owner.setDatascopeRuleList(userRuleList);
        ServiceFacade.getRoleService().updateRoleRuleList(role);
        return role;
      }

      @Override
      public void success(Role result) {
        ObserverUtil.notifyEvent(
          new Event(result, AuthorityEventType.ROLE_DATASCOPE_RULE_UPDATED), RoleDatascopeForm.this);
      }
    });

    return owner;
  }

}
