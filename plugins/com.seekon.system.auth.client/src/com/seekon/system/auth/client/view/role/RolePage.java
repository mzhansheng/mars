package com.seekon.system.auth.client.view.role;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.client.view.AuthResourcePage;

public class RolePage extends AuthResourcePage {

  private static final long serialVersionUID = -7284288882644102892L;

  private RoleTreePage roleTreePage;

  private RoleDisplayer roleDisplayer;
  
  public RolePage(Resource resource) {
    super(resource);
    
    JSplitPane jsp = new JSplitPane();
    jsp.setLeftComponent(getRoleTreePage());
    jsp.setRightComponent(getRoleDisplayer());

    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);
  }

  public RoleTreePage getRoleTreePage() {
    if (roleTreePage == null) {
      roleTreePage = new RoleTreePage(this);
    }
    return roleTreePage;
  }

  public RoleDisplayer getRoleDisplayer() {
    if (roleDisplayer == null) {
      roleDisplayer = new RoleDisplayer();
    }
    return roleDisplayer;
  }

}
