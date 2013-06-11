package com.seekon.system.org.client.view.dept.wizard;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.jgoodies.validation.ValidationResultModel;
import com.seekon.system.org.client.view.dept.DepartmentForm;
import com.seekon.system.org.model.Department;

public class DeptInfoWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = 3557825828057223898L;

  private CreateDeptWizardModel wizardModel;

  private DepartmentForm form;

  public DeptInfoWizardStep() {
    super("规则基本信息", "请输入规则名称等信息");
  }

  @Override
  public void init(WizardModel model) {
    super.init(model);
    this.wizardModel = (CreateDeptWizardModel) model;

    form = new DepartmentForm();
    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);

    form
      .getModel()
      .getValidationResultModel()
      .addPropertyChangeListener(ValidationResultModel.PROPERTY_ERRORS,
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            setComplete(!(Boolean) evt.getNewValue());
          }
        });

    setComplete(false);
  }

  @Override
  public void applyState() throws InvalidStateException {
    Department ent = form.getModel().getBean().getDepartment();
    ent.setBelongedEnt(wizardModel.getDepartment().getBelongedEnt());
    wizardModel.setDepartment(ent);
  }
}
