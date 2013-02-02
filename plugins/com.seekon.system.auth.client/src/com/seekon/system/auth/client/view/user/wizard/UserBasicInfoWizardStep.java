package com.seekon.system.auth.client.view.user.wizard;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.jgoodies.validation.ValidationResultModel;
import com.seekon.system.auth.client.view.user.basicinfo.UserBasicInfoForm;

public class UserBasicInfoWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = -8393279107849359356L;

  public UserBasicInfoWizardStep() {
    super("基本信息", "录入用户姓名、性别、移动电话等基本信息");
  }

  private CreateUserWizardModel wizardModel;

  private UserBasicInfoForm form;

  @Override
  public void init(WizardModel model) {
    wizardModel = (CreateUserWizardModel) model;

    setLayout(new BorderLayout());

    form = new UserBasicInfoForm();
    form.setUser(wizardModel.getUser());
    add(form, BorderLayout.CENTER);
    form
      .getUserModel()
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
    wizardModel.setUser(form.getUserModel().getBean().getUser());
  }

}
