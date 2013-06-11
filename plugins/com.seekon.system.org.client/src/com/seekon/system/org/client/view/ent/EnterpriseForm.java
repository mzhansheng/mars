package com.seekon.system.org.client.view.ent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.seekon.system.org.client.internal.ServiceFacade;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Enterprise;

public class EnterpriseForm extends AbstractForm<Enterprise> {

  private static final long serialVersionUID = 6246030017283409392L;

  private EnterpriseModel model;

  public EnterpriseForm() {
    super();

    model = new EnterpriseModel(new Enterprise());
    initComponents();
  }

  private void initComponents() {
    JTextField codeField = BasicComponentFactory.createTextField(
      model.getModel(BindingEnterprise.PROPERTYNAME_CODE), false);
    JTextField nameField = BasicComponentFactory.createTextField(
      model.getModel(BindingEnterprise.PROPERTYNAME_NAME), false);
    JTextField addressField = BasicComponentFactory.createTextField(
      model.getModel(BindingEnterprise.PROPERTYNAME_ADDRESS), false);
    JTextField postCodeField = BasicComponentFactory.createTextField(
      model.getModel(BindingEnterprise.PROPERTYNAME_POST_CODE), false);

    ValidationComponentUtils.setMandatory(codeField, true);
    ValidationComponentUtils.setMandatory(nameField, true);

    JComponent validateResultView = ValidationResultViewFactory
      .createReportIconAndTextPane(model.getValidationResultModel());

    this.setLayout(new MigLayout("align center"));
    this.add(validateResultView, "span 2,growx,wrap");
    this.add(new JLabel("单位代码:"));
    this.add(codeField, "w 4cm:6cm:,wrap");
    this.add(new JLabel("单位名称"));
    this.add(nameField, "growx,wrap");
    this.add(new JLabel("地址"));
    this.add(addressField, "growx,wrap");
    this.add(new JLabel("邮政编码"));
    this.add(postCodeField, "growx,wrap");

    updateComponentTree(model.getValidationResultModel().getResult());
    model.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_RESULT, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          updateComponentTree((ValidationResult) evt.getNewValue());
        }
      });

    setComplete(!model.getValidationResultModel().hasErrors());
    model.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_ERRORS, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          setComplete(!(Boolean) evt.getNewValue());
        }
      });
  }

  private void updateComponentTree(ValidationResult result) {
    ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(this);
    ValidationComponentUtils.updateComponentTreeSeverityBackground(this, result);
  }

  @Override
  public void edit(Enterprise t) {
    this.model.setBean(new BindingEnterprise(t));
  }

  @Override
  public Enterprise save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Enterprise>() {

      @Override
      public Enterprise execute() throws Exception {
        Enterprise enterprise = model.getBean().getEnterprise();
        ServiceFacade.getOrgService().updateEnterprise(enterprise);
        return enterprise;
      }

      @Override
      public void success(Enterprise enterprise) {
        JOptionPane.showMessageDialog(EnterpriseForm.this, "更新成功！");
        ObserverUtil.notifyEvent(
          new Event(enterprise, OrgEventType.ENT_UPDATED), EnterpriseForm.this);
      }
    });
    return this.model.getBean().getEnterprise();
  }

  public EnterpriseModel getModel() {
    return model;
  }

}
