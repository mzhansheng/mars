package com.seekon.system.auth.client.view.role.wizard;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.jgoodies.validation.ValidationResultModel;
import com.seekon.system.auth.client.view.role.basicinfo.RoleBasicInfoForm;

public class RoleBasicInfoWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = 3636052270796372499L;

  private CreateRoleWizardModel wizardModel;

  private RoleBasicInfoForm form;

  public RoleBasicInfoWizardStep() {
    super("角色基本信息", "请输入代码、名称等基本信息");
  }

  @Override
  public void init(WizardModel model) {
    wizardModel = (CreateRoleWizardModel) model;

    form = new RoleBasicInfoForm();
    form.setRole(wizardModel.getRole());
    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);
    form
      .getRoleModel()
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
    wizardModel.setRole(form.getRoleModel().getBean().getRole());
  }

}
