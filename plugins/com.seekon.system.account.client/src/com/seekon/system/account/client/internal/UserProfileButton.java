package com.seekon.system.account.client.internal;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.framework.util.ToolBarButton;

public class UserProfileButton extends ToolBarButton {

  private static final long serialVersionUID = -3176121788625065996L;

  public UserProfileButton() {
    super(new AbstractAction() {

      private static final long serialVersionUID = 7005096157362405840L;

      @Override
      public void actionPerformed(ActionEvent e) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
          Thread.currentThread().setContextClassLoader(
            UserProfileButton.class.getClassLoader());
          AccountAdapter.getUserProfile((String) SessionContext
            .getCurrentHttpSession().get(SessionContext.KEY_USER_CODE));
        } finally {
          Thread.currentThread().setContextClassLoader(cl);
        }
      }
    });

    this.setText("个人信息");
    this.setToolTipText("查询编辑个人档案信息");
    this.setIcon(new ImageIcon(UserProfileButton.class
      .getResource("/resources/user-profile.gif")));
  }

}
