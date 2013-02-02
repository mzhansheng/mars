package com.seekon.system.auth.client.view.user;

import com.seekon.smartclient.component.viewedit.LabelPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.auth.client.AuthorityEventType;

public class UserCategoryPage extends LabelPage {

  private static final long serialVersionUID = -5979839472026761305L;

  @Override
  public void update(Event event) {
    if (event.getEventType() != AuthorityEventType.USER_CATEGORY_SELECTED)
      return;

    setText(event.getSource().toString());
  }

}
