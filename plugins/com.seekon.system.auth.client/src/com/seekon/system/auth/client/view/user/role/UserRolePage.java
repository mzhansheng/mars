package com.seekon.system.auth.client.view.user.role;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.module.AuthResourceRegistry;
import com.seekon.system.auth.client.view.role.wizard.CreateRoleWizard;
import com.seekon.system.auth.client.view.user.UserPage;
import com.seekon.system.auth.model.User;

public class UserRolePage extends VESPage<User> {

  private static final long serialVersionUID = -5313969634092756709L;

  @Override
  protected AbstractForm<User> getForm() {
    return new UserRolesForm();
  }

  @Override
  protected AbstractDetail<User> getDetail() {
    return new UserRolesDetail();
  }

  @Override
  protected Component editToolbar() {
    return new JButton(new AbstractAction("创建角色...") {
      @Override
      public void actionPerformed(ActionEvent e) {
        UserPage userPage = (UserPage) SwingUtilities.getAncestorOfClass(
          UserPage.class, UserRolePage.this);
        userPage.getUserRightContainer().callback(new CreateRoleWizard(null));
      }
    });
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
