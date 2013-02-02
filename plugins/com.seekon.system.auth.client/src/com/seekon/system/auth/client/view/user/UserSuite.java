package com.seekon.system.auth.client.view.user;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;

import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.system.auth.client.view.user.basicinfo.UserBasicInfoPage;
import com.seekon.system.auth.client.view.user.datascope.UserDatascopePage;
import com.seekon.system.auth.client.view.user.role.UserRolePage;

public class UserSuite extends AbstractPage {

  private static final long serialVersionUID = -6980852815988177967L;

  private JTabbedPane jtp;

  private UserBasicInfoPage basicInfoPage;

  public UserSuite() {
    setLayout(new BorderLayout());
    jtp = new JTabbedPane();
    add(jtp, BorderLayout.CENTER);

    basicInfoPage = new UserBasicInfoPage();
    jtp.addTab("基础信息", basicInfoPage);

    UserRolePage userRolePage = new UserRolePage();
    jtp.addTab("角色信息", userRolePage);

    jtp.addTab("全局数据范围", new UserDatascopePage());
  }

}
