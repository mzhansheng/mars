package com.seekon.system.account.client.internal;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.framework.util.ToolBarButton;
import com.seekon.system.auth.model.User;

public class JModifyPassword extends ToolBarButton {

	private static final long serialVersionUID = -1009299991004150330L;

	public JModifyPassword() {
		super(new AbstractAction() {
			private static final long serialVersionUID = 4086801783839518899L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// 当前用户
				Map<String, Object> session = SessionContext.getCurrentHttpSession();
				User user = null;
				user = (User) session.get(SessionContext.KEY_LOGINED_CURRENT_USER);
				if (user != null) {
					 new ModifyPasswordDialog(user);
				}
			}
		});

		this.setText("修改密码");
		this.setToolTipText("修改密码");
		this.setIcon(new ImageIcon(JModifyPassword.class.getResource("/resources/modifyUserInfo.png")));
	}

}
