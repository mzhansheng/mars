package com.seekon.system.auth.client.view.role;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;

import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.system.auth.client.view.role.basicinfo.RoleBasicInfoPage;
import com.seekon.system.auth.client.view.role.datascope.RoleDatascopePage;
import com.seekon.system.auth.client.view.role.resource.RoleResourcePage;

public class RoleSuite extends AbstractPage {

  private static final long serialVersionUID = 1328589081537181019L;

  public RoleSuite() {
    setLayout(new BorderLayout());

    JTabbedPane jtp = new JTabbedPane();
    jtp.addTab("基础信息", new RoleBasicInfoPage());
    jtp.addTab("功能权限", new RoleResourcePage());
    jtp.addTab("数据范围", new RoleDatascopePage());

    add(jtp, BorderLayout.CENTER);
  }

}
