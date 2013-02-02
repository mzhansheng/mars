package com.seekon.system.auth.client.view.user.basicinfo;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.module.AuthResourceRegistry;
import com.seekon.system.auth.model.User;

public class UserBasicInfoPage extends VESPage<User> {

  private static final long serialVersionUID = -6291816947091521097L;

  private UserBasicInfoDetail userBasicInfoDetail;

  private UserBasicInfoForm userBasicInfoForm;

  @Override
  protected AbstractForm<User> getForm() {
    userBasicInfoForm = new UserBasicInfoForm();
    return userBasicInfoForm;
  }

  @Override
  protected AbstractDetail<User> getDetail() {
    userBasicInfoDetail = new UserBasicInfoDetail();
    return userBasicInfoDetail;
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.NEW_USER_SELECTED) {
      showViewPage();
    }
  }

  @Override
  protected boolean isEditableByAuth() {
    return AuthUtil.isActionAuthorizedToUser(AuthResourceRegistry.KEY_USER_RESOURCE,
      ActionHelper.getEditAction());
  }
}
