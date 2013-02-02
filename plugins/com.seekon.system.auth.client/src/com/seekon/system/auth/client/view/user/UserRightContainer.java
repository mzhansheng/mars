package com.seekon.system.auth.client.view.user;

import com.seekon.smartclient.component.viewedit.LabelPage;
import com.seekon.smartclient.component.viewedit.SwitcherPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.auth.client.AuthorityEventType;

public class UserRightContainer extends SwitcherPage {

  private static final long serialVersionUID = 1254301240090150267L;

  private UserCategoryPage userCategoryPage;

  private UserSuite userSuitePage;

  public UserRightContainer() {
    userSuitePage = new UserSuite();
    userCategoryPage = new UserCategoryPage();
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == AuthorityEventType.NEW_USER_SELECTED) {
      setPage(userSuitePage);
    } else if (type == AuthorityEventType.USER_CATEGORY_SELECTED) {
      setPage(userCategoryPage);
    } else if (type == AuthorityEventType.USER_ROOT_SELECTED) {
      setPage(new LabelPage("用户"));
    } else if (type == AuthorityEventType.NONE_USER_SELECTIED) {
      setPage(new LabelPage());
    }
  }
}
