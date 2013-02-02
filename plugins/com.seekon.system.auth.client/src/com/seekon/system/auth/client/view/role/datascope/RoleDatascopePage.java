package com.seekon.system.auth.client.view.role.datascope;

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
import com.seekon.system.auth.client.view.datascope.wizard.CreateDatascopeWizard;
import com.seekon.system.auth.client.view.role.RolePage;
import com.seekon.system.auth.client.view.user.UserPage;
import com.seekon.system.auth.model.DatascopeOwner;

public class RoleDatascopePage extends VESPage<DatascopeOwner> {

  private static final long serialVersionUID = -2321394245376022758L;

  @Override
  protected AbstractForm<DatascopeOwner> getForm() {
    return new RoleDatascopeForm();
  }

  @Override
  protected AbstractDetail<DatascopeOwner> getDetail() {
    return new RoleDatascopeDetail();
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.ROLE_SELECTED) {
      showViewPage();
    }
  }

  @Override
  protected Component editToolbar() {
    return new JButton(new AbstractAction("创建数据范围...") {
      @Override
      public void actionPerformed(ActionEvent e) {
        RolePage rolePage = (RolePage) SwingUtilities.getAncestorOfClass(
          RolePage.class, RoleDatascopePage.this);
        if (rolePage != null) {
          rolePage.getRoleDisplayer().callback(new CreateDatascopeWizard(null));
        }
      }
    });
  }
  
  @Override
  protected boolean isEditableByAuth() {
    return AuthUtil.isActionAuthorizedToUser(AuthResourceRegistry.KEY_ROLE_RESOURCE,
      ActionHelper.getEditAction());
  }
}
