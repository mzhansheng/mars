package com.seekon.system.auth.client.view.role.resource;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.module.AuthResourceRegistry;
import com.seekon.system.auth.model.Role;

public class RoleResourcePage extends VESPage<Role> {

  private static final long serialVersionUID = -7570767435932384228L;

  @Override
  protected AbstractForm<Role> getForm() {
    RoleResourceForm form = new RoleResourceForm();
    return form;
  }

  @Override
  protected AbstractDetail<Role> getDetail() {
    RoleResourceDetail detail = new RoleResourceDetail();
    return detail;
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.ROLE_SELECTED) {
      showViewPage();
    }
  }

  @Override
  protected boolean isEditableByAuth() {
    return AuthUtil.isActionAuthorizedToUser(AuthResourceRegistry.KEY_ROLE_RESOURCE,
      ActionHelper.getEditAction());
  }

}
