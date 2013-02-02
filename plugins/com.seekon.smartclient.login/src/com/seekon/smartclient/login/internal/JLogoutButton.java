package com.seekon.smartclient.login.internal;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.osgi.framework.ServiceReference;

import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.util.ToolBarButton;

public class JLogoutButton extends ToolBarButton {

  private static final long serialVersionUID = 651116395251417614L;

  public JLogoutButton() {
    super(new AbstractAction() {

      private static final long serialVersionUID = -9024867414992083260L;

      @Override
      public void actionPerformed(ActionEvent e) {
        ServiceReference sr = LoginPlugin.context
          .getServiceReference(Application.class.getName());
        Application application = (Application) LoginPlugin.context.getService(sr);
        application.terminate();
      }
    });

    this.setText("退出");
    this.setToolTipText("退出系统");
    this.setIcon(new ImageIcon(JLogoutButton.class
      .getResource("/resources/logout.gif")));
  }

}
