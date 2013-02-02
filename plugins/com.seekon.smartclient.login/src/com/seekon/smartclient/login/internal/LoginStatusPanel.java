package com.seekon.smartclient.login.internal;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.system.auth.model.User;

public class LoginStatusPanel extends JPanel {

  private static final long serialVersionUID = 8862878913253663485L;

  public LoginStatusPanel() {
    super();
    initComponents();
  }

  protected void initComponents() {
    User user = (User) SessionContext.getCurrentHttpSession().get(
      SessionContext.KEY_LOGINED_CURRENT_USER);
    if(user == null){
      return;
    }
    
    this.setLayout(new FlowLayout());
    this.add(new JLabel("登录账号:" + user.getUserCode()));
    this.add(new JLabel("   登录名:" + user.getUserName()));
  }
}
