package com.seekon.system.auth.client.view.user.basicinfo;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

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
import com.seekon.system.auth.model.User;

public class UserBasicInfoForm extends AbstractForm<User> {

  private static final long serialVersionUID = 2364217534376015304L;

  private UserModel userModel;

  //private JTextField		tf_email;
  private JPasswordField tf_password;

  private JTextField userNameField;

  private JTextField userCodeField;

  private JComponent validateResultView;

  public UserBasicInfoForm() {
    userModel = new UserModel((new User()));

    initComponents();

    initComponentAnnotations();

    initGUI();

    updateComponentTree(userModel.getValidationResultModel().getResult());
    userModel.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_RESULT, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          updateComponentTree((ValidationResult) evt.getNewValue());
        }
      });

    setComplete(!userModel.getValidationResultModel().hasErrors());
    userModel.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_ERRORS, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          setComplete(!(Boolean) evt.getNewValue());
        }
      });
  }

  public void setUser(User user) {
    userModel.setBean(new BindingUser(user));
  }

  public UserModel getUserModel() {
    return userModel;
  }

  private void initComponentAnnotations() {
    ValidationComponentUtils.setMandatory(userCodeField, true);
    // ValidationComponentUtils.setMessageKey(userCodeField, "用户.编码");

    ValidationComponentUtils.setMandatory(userNameField, true);
    // ValidationComponentUtils.setMessageKey(userNameField, "用户.名称");

    ValidationComponentUtils.setMandatory(tf_password, true);
  }

  private void initComponents() {
    userCodeField = BasicComponentFactory.createTextField(
      userModel.getModel(BindingUser.PROPERTYNAME_USERCODE), false);
    userNameField = BasicComponentFactory.createTextField(
      userModel.getModel(BindingUser.PROPERTYNAME_USERNAME), false);
    tf_password = BasicComponentFactory.createPasswordField(
      userModel.getModel(BindingUser.PROPERTYNAME_PASSWORD), false);
  }

  private void initGUI() {
    validateResultView = ValidationResultViewFactory
      .createReportIconAndTextPane(userModel.getValidationResultModel());

    JPanel pane = new JPanel(new MigLayout("align center", "[right]"));
    pane.add(validateResultView, "span 3,growx,wrap");
    pane.add(new JLabel("用户编码"));
    pane.add(UIUtil.newColonLable());
    pane.add(userCodeField, "w 4cm:6cm:,wrap");

    pane.add(new JLabel("用户名称"));
    pane.add(UIUtil.newColonLable());
    pane.add(userNameField, "growx,wrap");

    pane.add(new JLabel("密码"));
    pane.add(UIUtil.newColonLable());
    pane.add(tf_password, "growx,wrap");

    pane.add(new JLabel("性别"));
    pane.add(UIUtil.newColonLable());
    pane.add(new GenderChooser(), "growx,wrap");

    pane.add(new JLabel("出生日期"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JXDatePicker(), "growx,wrap");

    pane.add(new JLabel("家庭住址"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JTextField(), "growx,wrap");

    pane.add(new JLabel("电话"));
    pane.add(UIUtil.newColonLable());
    pane.add(new JTextField(), "growx,wrap");

    this.setLayout(new BorderLayout());
    this.add(pane, BorderLayout.CENTER);
  }

  private void updateComponentTree(ValidationResult result) {
    ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(this);
    ValidationComponentUtils.updateComponentTreeSeverityBackground(this, result);
  }

  @Override
  public void edit(User t) {
    userModel.setBean(new BindingUser(t));
  }

  @Override
  public User save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<User>() {

      @Override
      public User execute() throws Exception {
        ServiceFacade.getUserService().updateUserBasicInfo(
          userModel.getBean().getUser());
        return userModel.getBean().getUser();
      }

      @Override
      public void success(User user) {
        JOptionPane.showMessageDialog(UserBasicInfoForm.this, "更新成功！");
        ObserverUtil.notifyEvent(
          new Event(user, AuthorityEventType.USER_BASIC_INFO_UPDATED), UserBasicInfoForm.this);
      }
    });

    return userModel.getBean().getUser();

  }
}
