package com.seekon.system.auth.client.view.role.basicinfo;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.module.AuthResourceRegistry;
import com.seekon.system.auth.model.Role;

public class RoleBasicInfoPage extends VESPage<Role> {

  private static final long serialVersionUID = -2542747736323273755L;

  private RoleBasicInfoDetail roleBasicInfoDetail;

  private RoleBasicInfoForm roleBasicInfoForm;

  @Override
  protected AbstractForm<Role> getForm() {
    roleBasicInfoForm = new RoleBasicInfoForm();
    return roleBasicInfoForm;
  }

  @Override
  protected AbstractDetail<Role> getDetail() {
    roleBasicInfoDetail = new RoleBasicInfoDetail();
    return roleBasicInfoDetail;
  }

  @Override
  protected boolean isEditableByAuth() {
    return AuthUtil.isActionAuthorizedToUser(AuthResourceRegistry.KEY_ROLE_RESOURCE,
      ActionHelper.getEditAction());
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.ROLE_SELECTED) {
      showViewPage();
    }
  }
}
