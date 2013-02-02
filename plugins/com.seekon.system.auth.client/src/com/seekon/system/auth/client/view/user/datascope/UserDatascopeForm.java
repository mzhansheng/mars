package com.seekon.system.auth.client.view.user.datascope;

import java.util.List;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.view.AbstractDatascopeForm;
import com.seekon.system.auth.model.DatascopeOwner;
import com.seekon.system.auth.model.User;
import com.seekon.system.rule.model.Rule;

public class UserDatascopeForm extends AbstractDatascopeForm {

  private static final long serialVersionUID = 9077813997822716007L;

  @Override
  public DatascopeOwner save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<User>() {

      @Override
      public User execute() throws Exception {
        User user = (User) owner;
        List<Rule> userRuleList = getSelectedDatascopeRuleList();
        user.setDatascopeRuleList(userRuleList);
        ServiceFacade.getUserService().updateUserRule(user);
        return user;
      }

      @Override
      public void success(User result) {
        ObserverUtil.notifyEvent(
          new Event(result, AuthorityEventType.USER_DATASCOPE_RULE_UPDATED), UserDatascopeForm.this);
      }
    });

    return owner;
  }
}
