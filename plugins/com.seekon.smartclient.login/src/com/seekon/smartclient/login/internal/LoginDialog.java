package com.seekon.smartclient.login.internal;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.seekon.smartclient.framework.component.JBaseDialog;
import com.seekon.system.auth.model.LoginInfo;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.spi.LoginService;

final class LoginDialog extends JBaseDialog {

  private static final long serialVersionUID = 5212508568272803067L;

  public static final String LAST_LOGIN_USERNAME = "last.login.user.name";

	/**
	 * 重试次数
	 */
	private int retryCount = 0;

//	private final JImageArea topImageArea = new JImageArea(new ImageIcon(
//			LoginDialog.class.getResource("topImage.gif")).getImage()) {
//		{
//			this.setBorder(BorderFactory.createEtchedBorder());
//		}
//	};

	private final JTextField userIdTextField = new JTextField() {
		{
			String lastLoginUserName = "";
			this.setText(lastLoginUserName);
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						okButton.doClick();
						break;
					case KeyEvent.VK_ESCAPE:
						cancelButton.doClick();
						break;
					}
				}
			});
		}
	};

	private final JPasswordField passwordField = new JPasswordField() {

		{
			setEchoChar('*');
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						okButton.doClick();
						break;
					case KeyEvent.VK_ESCAPE:
						cancelButton.doClick();
						break;
					}
				}
			});
		}
	};

	private LoginInfo retLoginInfo = null;

	private final JButton okButton = new JButton() {
		{
			Action action = new AbstractAction("确定") {
				public void actionPerformed(ActionEvent e) {

					String userId = userIdTextField.getText();
					//if(userId == null || userId.trim().length() == 0){
					//  userId = "admin";
					//}
					
					String passwd = new String(passwordField.getPassword());
					User usr = new User();
					usr.setUserCode(userId);
					// usr.setPasswd(passwd);
					// LoginEvent event = new LoginEvent(LoginPlugin.getDefault(), usr,
					// System
					// .currentTimeMillis());
					// for (LoginListener listener : LoginListenerRegistry.getInstance()
					// .getLoginListeners()) {
					// listener.logining(event);
					// }

					LoginInfo user = null;
					try {
						user = LoginPlugin.injector.getInstance(LoginService.class)
								.login(userId, passwd);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}

					// User user =
					// LoginPlugin.getInjector().getInstance(LoginService.class)
					// .login(userId, passwd);

					if (user != null) {
						retLoginInfo = user;
						LoginDialog.this.setVisible(false);
						// plugin.getPluginPreferences().setValue(LAST_LOGIN_USERNAME,
						// userId);
						// plugin.savePluginPreferences();

					} else {
						retryCount++;
						if (retryCount < 3) {
							JOptionPane.showMessageDialog(null, "输入的用户名或密码错误，请新输入!", "警告",
									JOptionPane.WARNING_MESSAGE);
							passwordField.requestFocus();
							passwordField.selectAll();
							return;
						} else {
							JOptionPane.showMessageDialog(null, "你没有登录本系统的权限！", "登录失败",
									JOptionPane.ERROR_MESSAGE);
							retLoginInfo = null;
							LoginDialog.this.setVisible(false);
						}
					}
				}
			};
			this.setAction(action);
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "actionOK");
			getActionMap().put("actionOK", action);
		}
	};

	private final JButton cancelButton = new JButton("取消") {

		private static final long serialVersionUID = -5025909858818944700L;
		{
			Action action = new AbstractAction("取消") {
				public void actionPerformed(ActionEvent e) {
					LoginDialog.this.setVisible(false);
					retLoginInfo = null;
				}
			};

			this.setAction(action);

			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), "actionCancel");
			getActionMap().put("actionCancel", action);
		}
	};

	public LoginDialog() {
		addComponents();

		this.setSize(300, 190); // 显示登录类型
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("登录");
		this.setResizable(false);
		addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				if (userIdTextField.getText().length() > 0) {
					userIdTextField.requestFocus(false);
					passwordField.requestFocusInWindow();
				}
			}
		});
	}

	/**
	 * 对组件时行布局
	 */
	private void addComponents() {
		this.setLayout(new GridBagLayout());
//		this.add(topImageArea, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
//				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0,
//						0), 0, 0));

		Insets insets = new Insets(8, 8, 4, 8);
		this.add(new JLabel("用户名："), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0));
		this.add(userIdTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets, 0,
				0));
		this.add(new JLabel("密　码："), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0));
		this.add(passwordField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets, 0,
				0));

		this.add(new JPanel() {
			{
				this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
				this.add(okButton);
				this.add(cancelButton);
			}
		}, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets, 0,
				0));
	}

	public LoginInfo execute() {
		this.moveToScreenCenter();
		this.toFront();
		setVisible(true);
		return retLoginInfo;
	}
}
