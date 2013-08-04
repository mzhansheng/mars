package com.seekon.system.account.client.internal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.seekon.mars.osgi.util.MD5Util;
import com.seekon.smartclient.framework.component.JBaseDialog;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.spi.UserService;

final class ModifyPasswordDialog extends JBaseDialog {

  private static final long serialVersionUID = 1238035381187117820L;

  private User loginUser = null;

  private JTextField checkPassword = null;

  private JPasswordField newPassword1;

  private JPasswordField newPassword2;

  private JButton okButton;

  private JButton cancelButton;

  private JLabel stateInfo;

  public ModifyPasswordDialog(User user) {
    if (user == null)
      throw new IllegalArgumentException("user argument is null.");
    loginUser = user;

    buildComponents();
    layoutComponents();

    setSize(350, 240);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setTitle("修改密码");
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void updateInfo(String info) {
    if (info == null)
      stateInfo.setText(" ");
    else
      stateInfo.setText("<html><font color=red>" + info + "</font></html>");
  }

  private boolean isNull(String s) {
    return s == null || s.equals("");
  }

  private void buildComponents() {

    stateInfo = new JLabel(" ");
    //		stateInfo.setMaximumSize(new Dimension(0, 13));
    //		stateInfo.setMinimumSize(new Dimension(0, 13));

    //
    okButton = new JButton("确定");
    okButton.addActionListener(new AbstractAction() {
      private static final long serialVersionUID = -6144648089600778415L;

      @Override
      public void actionPerformed(ActionEvent e) {
        String userMD5Password = ModifyPasswordDialog.this.loginUser.getPassword();
        String checkPass = checkPassword.getText();
        String checkNew1 = new String(newPassword1.getPassword());
        String checkNew2 = new String(newPassword2.getPassword());
        if (isNull(checkPass) || isNull(checkNew1) || isNull(checkNew2)) {
          updateInfo("密码不能为空!"); //JOptionPane.showMessageDialog(null, "密码不能为空!", "", JOptionPane.WARNING_MESSAGE);
        } else {
          String checkMD5Password = MD5Util.getMD5HashHex32String(checkPass);
          if (!checkMD5Password.equals(userMD5Password)) {
            updateInfo("密码错误!"); //JOptionPane.showMessageDialog(null, "密码错误!", "", JOptionPane.WARNING_MESSAGE);
          } else {
            if (!checkNew1.equals(checkNew2)) {
              updateInfo("两次输入密码不一致!"); //JOptionPane.showMessageDialog(null, "两次输入密码不一致!", "", JOptionPane.WARNING_MESSAGE);
            } else {
              //							if (checkNew1.length() < User.PasswordMinimumLength || checkNew1.length() > User.PasswordMaximumLength) {
              //								updateInfo(String.format("密码长度应为%d至%d位", User.PasswordMinimumLength, User.PasswordMaximumLength));
              //								return;
              //							}
              loginUser.setPassword(MD5Util.getMD5HashHex16String(checkNew1));
              UserService service = AccountPlugin.injector
                .getInstance(UserService.class);
              service.updateUserBasicInfo(loginUser);
              loginUser = null;
              ModifyPasswordDialog.this.setVisible(false);
              JOptionPane.showMessageDialog(null, "密码已修改。");
            }
          }
        }
      }
    });

    //
    cancelButton = new JButton("取消");
    cancelButton.addActionListener(new AbstractAction() {
      private static final long serialVersionUID = -5557999636962873141L;

      @Override
      public void actionPerformed(ActionEvent e) {
        loginUser = null;
        ModifyPasswordDialog.this.setVisible(false);
      }
    });

    //
    KeyListener keyLis = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        updateInfo(null);
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
          okButton.doClick();
          break;
        case KeyEvent.VK_ESCAPE:
          cancelButton.doClick();
          break;
        }
      }
    };

    //
    checkPassword = new JTextField();
    checkPassword.setText("");
    checkPassword.addKeyListener(keyLis);

    //
    newPassword1 = new JPasswordField();
    newPassword2 = new JPasswordField();
    newPassword1.addKeyListener(keyLis);
    newPassword2.addKeyListener(keyLis);
    /////String tooltip = String.format("密码长度为%d至%d", User.PasswordMinimumLength, User.PasswordMaximumLength);
    ////newPassword1.setToolTipText(tooltip);
    ////newPassword2.setToolTipText(tooltip);
  }

  private void layoutComponents() {
    this.setLayout(new BorderLayout());

    setUndecorated(true);

    JPanel main = new JPanel(new GridBagLayout()) {
      @Override
      public Insets getInsets() {
        return new Insets(0, 15, 0, 15);
      }

      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c = g.getColor();
        g.setColor(new Color(0, 0, 255, 50));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(c);
      }
    };
    main.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255, 100)));

    Insets insets = new Insets(8, 8, 4, 8);

    main.add(stateInfo,
      new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
        GridBagConstraints.BOTH, new Insets(0, 8, 4, 8), 0, 0));

    main.add(new JLabel("  当前密码："), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
      GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0));
    main.add(checkPassword, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
      GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));

    main.add(new JLabel("设置新密码："), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
      GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0));
    main.add(newPassword1, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
      GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));

    main.add(new JLabel("重复新密码："), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
      GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, insets, 0, 0));
    main.add(newPassword2, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
      GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));

    main.add(new JPanel() {
      {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        this.add(okButton);
        this.add(cancelButton);
        this.setOpaque(false);
      }
    }, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
      GridBagConstraints.HORIZONTAL, insets, 0, 0));
    add(main);
  }

  //	private void msg(int code) {
  //		
  //	}
  //	
  //	private static final int Msg_Null_NewPassword = 0;
  //	private static final int Msg_Null_CkeckPassword = 1;
  //	
  //	private void action() {
  //		String userMD5Password = ResetPassword.this.loginUser.getPassword();
  //		String checkPass = checkPassword.getText();
  //		String checkNew1 = new String(newPassword1.getPassword());
  //		String checkNew2 = new String(newPassword2.getPassword());
  //		
  //		if (checkPass.equals("")) {
  //			msg(Msg_Null_CkeckPassword);
  //		}
  //		if (checkNew1.equals("") && checkNew2.equals("")) {
  //			msg(Msg_Null_NewPassword);
  //		}
  //		
  //		
  //	}

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        // TODO Auto-generated method stub
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton bt = new JButton("1");
        bt.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            User user = new User();
            user.setId("21");
            new ModifyPasswordDialog(user).setVisible(true);

          }
        });
        fr.getContentPane().add(bt);
        fr.pack();
        fr.setVisible(true);
      }
    });
  }
}
