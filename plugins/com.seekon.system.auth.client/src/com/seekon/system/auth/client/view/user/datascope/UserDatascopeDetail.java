package com.seekon.system.auth.client.view.user.datascope;

import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.view.AbstractDatascopeDetail;
import com.seekon.system.auth.model.User;

public class UserDatascopeDetail extends AbstractDatascopeDetail {

  private static final long serialVersionUID = -7120972535255127986L;

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == AuthorityEventType.NEW_USER_SELECTED
      || type == AuthorityEventType.USER_DATASCOPE_RULE_UPDATED) {
      this.setDatascopeOwner((User) event.getSource());
    }
  }
}
