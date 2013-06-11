package com.seekon.system.auth.client.view.user;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.client.view.AuthResourcePage;

public class UserPage extends AuthResourcePage {

  private static final long serialVersionUID = 8421308509542977232L;

  private UserTreePage userTreePage;

  private UserRightContainer userRightContainer;

  public UserPage(Resource resource) {
    super(resource);
    
    userTreePage = new UserTreePage();
    userRightContainer = new UserRightContainer();

    JSplitPane jsp = new JSplitPane();
    jsp.setLeftComponent(userTreePage);
    jsp.setRightComponent(userRightContainer);

    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);
  }

  public UserTreePage getUserTreePage() {
    return userTreePage;
  }

  public UserRightContainer getUserRightContainer() {
    return userRightContainer;
  }

}
