package com.seekon.smartclient.login.internal;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.osgi.framework.ServiceReference;

import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.util.ToolBarButton;
import com.seekon.smartclient.login.LoginValidateor;
import com.seekon.system.auth.model.User;

public class JReloginButton extends ToolBarButton {

  private static final long serialVersionUID = -6325856211809458883L;

  public JReloginButton() {
    super(new AbstractAction() {

      private static final long serialVersionUID = 7214909712224941507L;

      @Override
      public void actionPerformed(ActionEvent e) {
        LoginValidateor loginValidateor = new DefaultLoginValidateor();
        User user = loginValidateor.login();
        if (user != null) {
          Startup.initApplicationContextSession(user);
        }

        ServiceReference sr = LoginPlugin.context
          .getServiceReference(Application.class.getName());
        Application application = (Application) LoginPlugin.context.getService(sr);
        application.reload();
      }
    });

    this.setText("注销");
    this.setToolTipText("切换登录用户");
    this.setIcon(new ImageIcon(JReloginButton.class
      .getResource("/resources/relogin.png")));
  }

}
