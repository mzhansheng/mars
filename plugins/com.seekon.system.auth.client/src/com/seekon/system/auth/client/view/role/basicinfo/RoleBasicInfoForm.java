package com.seekon.system.auth.client.view.role.basicinfo;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;
import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.util.UIUtil;
import com.seekon.system.auth.model.Role;

public class RoleBasicInfoForm extends AbstractForm<Role> {

  private static final long serialVersionUID = -6854937738462994015L;

  private RoleModel roleModel;

  private JTextField roleNameField;

  private JTextField roleCodeField;

  private JComponent validateResultView;

  public RoleBasicInfoForm() {
    roleModel = new RoleModel((new Role()));

    initComponents();

    initComponentAnnotations();

    initGUI();

    updateComponentTree(roleModel.getValidationResultModel().getResult());
    roleModel.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_RESULT, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          updateComponentTree((ValidationResult) evt.getNewValue());
        }
      });

    setComplete(!roleModel.getValidationResultModel().hasErrors());
    roleModel.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_ERRORS, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          setComplete(!(Boolean) evt.getNewValue());
        }
      });
  }

  public void setRole(Role role) {
    roleModel.setBean(new BindingRole(role));
  }

  public RoleModel getRoleModel() {
    return roleModel;
  }

  private void initComponentAnnotations() {
    ValidationComponentUtils.setMandatory(roleCodeField, true);
    ValidationComponentUtils.setMandatory(roleNameField, true);
  }

  private void initComponents() {
    roleCodeField = BasicComponentFactory.createTextField(
      roleModel.getModel(BindingRole.PROPERTYNAME_ROLECODE), false);
    roleNameField = BasicComponentFactory.createTextField(
      roleModel.getModel(BindingRole.PROPERTYNAME_ROLENAME), false);
  }

  private void initGUI() {
    validateResultView = ValidationResultViewFactory
      .createReportIconAndTextPane(roleModel.getValidationResultModel());

    JPanel pane = new JPanel(new MigLayout("align center"));
    pane.add(validateResultView, "span 3,growx,wrap");

    pane.add(new JLabel("角色编码"));
    pane.add(UIUtil.newColonLable());
    pane.add(roleCodeField, "w 4cm:6cm:,wrap");

    pane.add(new JLabel("角色名称"));
    pane.add(UIUtil.newColonLable());
    pane.add(roleNameField, "growx,wrap");

    this.setLayout(new BorderLayout());
    this.add(pane, BorderLayout.CENTER);
  }

  private void updateComponentTree(ValidationResult result) {
    ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(this);
    ValidationComponentUtils.updateComponentTreeSeverityBackground(this, result);
  }

  @Override
  public void edit(Role t) {
    roleModel.setBean(new BindingRole(t));
  }

  @Override
  public Role save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Role>() {

      @Override
      public Role execute() throws Exception {
        Role role = roleModel.getBean().getRole();
        ServiceFacade.getRoleService().updateRole(role);
        return role;
      }

      @Override
      public void success(Role role) {
        JOptionPane.showMessageDialog(RoleBasicInfoForm.this, "更新成功！");
        ObserverUtil.notifyEvent(
          new Event(roleModel.getBean().getRole(),
            AuthorityEventType.ROLE_BASIC_INFO_UPDATED), RoleBasicInfoForm.this);
      }

    });

    return (Role) roleModel.getBean().getRole();
  }
}
