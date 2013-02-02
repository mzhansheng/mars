package com.seekon.system.auth.client.view.role.basicinfo;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.util.UIUtil;
import com.seekon.system.auth.model.Role;

public class RoleBasicInfoDetail extends AbstractDetail<Role> {

  private static final long serialVersionUID = -5893501425670780151L;

  private RoleModel model;

  private JLabel codeLabel;

  private JLabel code;

  private JLabel nameLabel;

  private JLabel name;

  public RoleBasicInfoDetail() {
    model = new RoleModel(new Role());

    initComponents();

    JPanel pane = layoutComponents();
    setLayout(new BorderLayout());
    add(pane, BorderLayout.CENTER);
  }

  public void setRole(Role role) {
    model.setBean(new BindingRole(role));
  }

  private JPanel layoutComponents() {
    JPanel pane = new JPanel(new MigLayout("align center"));

    pane.add(codeLabel);
    pane.add(UIUtil.newColonLable());
    pane.add(code, "w 4cm:6cm:,wrap");

    pane.add(nameLabel);
    pane.add(UIUtil.newColonLable());
    pane.add(name, "growx,wrap");
    return pane;
  }

  private void initComponents() {
    codeLabel = new JLabel("角色代码");
    code = BasicComponentFactory.createLabel(model
      .getModel(BindingRole.PROPERTYNAME_ROLECODE));

    nameLabel = new JLabel("角色名称");
    name = BasicComponentFactory.createLabel(model
      .getModel(BindingRole.PROPERTYNAME_ROLENAME));
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.ROLE_SELECTED
      || event.getEventType() == AuthorityEventType.ROLE_BASIC_INFO_UPDATED) {
      try {
        Role source = (Role) event.getSource();
        this.setRole((Role) source);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Role edit() {
    try {
      return (Role) model.getBean().getRole().clone();// 需克隆一份用来进行修改保存
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
