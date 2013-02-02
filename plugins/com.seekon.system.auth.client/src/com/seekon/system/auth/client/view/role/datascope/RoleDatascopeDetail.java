package com.seekon.system.auth.client.view.role.datascope;

import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.view.AbstractDatascopeDetail;
import com.seekon.system.auth.model.Role;

public class RoleDatascopeDetail extends AbstractDatascopeDetail {

  private static final long serialVersionUID = -7120972535255127986L;

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == AuthorityEventType.ROLE_SELECTED
      || type == AuthorityEventType.ROLE_DATASCOPE_RULE_UPDATED) {
      setDatascopeOwner((Role) event.getSource());
    }
  }
}
