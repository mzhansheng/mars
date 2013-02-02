package com.seekon.system.auth.client.view.user.basicinfo;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.util.UIUtil;
import com.seekon.system.auth.model.User;

public class UserBasicInfoDetail extends AbstractDetail<User> {

  private static final long serialVersionUID = 8429624961894743902L;

  private UserModel model;

  private JLabel userCodeLable;

  private JLabel userCode;

  private JLabel userNameLable;

  private JLabel userName;

  public UserBasicInfoDetail() {
    model = new UserModel((new User()));

    initComponents();

    JPanel pane = layoutComponents();
    setLayout(new BorderLayout());
    add(pane, BorderLayout.CENTER);
  }

  public void setUser(User user) {
    model.setBean(new BindingUser(user));
  }

  private JPanel layoutComponents() {
    JPanel pane = new JPanel(new MigLayout("align center"));
    pane.add(userCodeLable);
    pane.add(UIUtil.newColonLable());
    pane.add(userCode, "w 4cm:6cm:,wrap");

    pane.add(userNameLable);
    pane.add(UIUtil.newColonLable());
    pane.add(userName, "growx,wrap");

    pane.add(new JLabel("性别"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JLabel(), "growx,wrap");

    pane.add(new JLabel("出生日期"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JLabel(), "growx,wrap");

    pane.add(new JLabel("家庭住址"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JLabel(), "growx,wrap");

    pane.add(new JLabel("电话"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JLabel(), "growx,wrap");

    return pane;
  }

  private void initComponents() {
    userCodeLable = new JLabel("用户编码");
    userCode = BasicComponentFactory.createLabel(model
      .getModel(BindingUser.PROPERTYNAME_USERCODE));

    userNameLable = new JLabel("用户名称");
    userName = BasicComponentFactory.createLabel(model
      .getModel(BindingUser.PROPERTYNAME_USERNAME));
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.NEW_USER_SELECTED
      || event.getEventType() == AuthorityEventType.USER_BASIC_INFO_UPDATED) {
      User source = (User) event.getSource();
      try {
        setUser((User) source.clone());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public User edit() {
    try {
      return (User) model.getBean().getUser().clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

}
