package com.seekon.system.org.client.view.dept;

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
import com.seekon.system.org.model.Department;

public class DepartmentForm extends AbstractForm<Department> {

  private static final long serialVersionUID = 6246030017283409392L;

  private DepartmentModel model;

  public DepartmentForm() {
    super();

    model = new DepartmentModel(new Department());
    initComponents();
  }

  private void initComponents() {
    JTextField codeField = BasicComponentFactory.createTextField(
      model.getModel(BindingDepartment.PROPERTYNAME_CODE), false);
    JTextField nameField = BasicComponentFactory.createTextField(
      model.getModel(BindingDepartment.PROPERTYNAME_NAME), false);

    ValidationComponentUtils.setMandatory(codeField, true);
    ValidationComponentUtils.setMandatory(nameField, true);

    JComponent validateResultView = ValidationResultViewFactory
      .createReportIconAndTextPane(model.getValidationResultModel());

    this.setLayout(new MigLayout("align center"));
    this.add(validateResultView, "span 2,growx,wrap");
    this.add(new JLabel("内部机构代码:"));
    this.add(codeField, "w 4cm:6cm:,wrap");
    this.add(new JLabel("内部机构名称"));
    this.add(nameField, "growx,wrap");

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
  public void edit(Department t) {
    this.model.setBean(new BindingDepartment(t));
  }

  @Override
  public Department save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Department>() {

      @Override
      public Department execute() throws Exception {
        Department enterprise = model.getBean().getDepartment();
        ServiceFacade.getOrgService().updateDepartment(enterprise);
        return enterprise;
      }

      @Override
      public void success(Department enterprise) {
        JOptionPane.showMessageDialog(DepartmentForm.this, "更新成功！");
        ObserverUtil.notifyEvent(
          new Event(enterprise, OrgEventType.DEPT_UPDATED), DepartmentForm.this);
      }
    });
    return this.model.getBean().getDepartment();
  }

  public DepartmentModel getModel() {
    return model;
  }

}
