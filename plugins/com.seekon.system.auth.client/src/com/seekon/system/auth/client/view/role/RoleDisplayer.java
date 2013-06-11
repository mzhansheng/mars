package com.seekon.system.auth.client.view.role;

import com.seekon.smartclient.component.viewedit.LabelPage;
import com.seekon.smartclient.component.viewedit.SwitcherPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.common.client.category.CategoryEventType;
import com.seekon.system.common.client.category.CategoryPage;

public class RoleDisplayer extends SwitcherPage {

  private static final long serialVersionUID = 3541291833241681468L;

  private RoleSuite roleSuite;

  private CategoryPage categoryPage;

  private LabelPage emptyPage;

  private LabelPage rootPage;

  public RoleDisplayer() {
    roleSuite = new RoleSuite();
    categoryPage = new CategoryPage(ServiceFacade.getRoleService());
    emptyPage = new LabelPage();
    rootPage = new LabelPage("角色");
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == AuthorityEventType.ROLE_SELECTED) {
      setPage(roleSuite);
    } else if (type == CategoryEventType.CATEGORY_SELECTED) {
      setPage(categoryPage);
    } else if (type == AuthorityEventType.ROLE_ROOT_SELECTED) {
      setPage(rootPage);
    } else if (type == AuthorityEventType.NONE_ROLE_SELECTIED) {
      setPage(emptyPage);
    }
  }

}
