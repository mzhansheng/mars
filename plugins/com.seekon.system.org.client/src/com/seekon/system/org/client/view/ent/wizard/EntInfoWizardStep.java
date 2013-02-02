package com.seekon.system.org.client.view.ent.wizard;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.jgoodies.validation.ValidationResultModel;
import com.seekon.system.org.client.view.ent.EnterpriseForm;
import com.seekon.system.org.model.Enterprise;

public class EntInfoWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = 3557825828057223898L;

  private CreateEntWizardModel wizardModel;

  private EnterpriseForm form;

  public EntInfoWizardStep() {
    super("规则基本信息", "请输入规则名称等信息");
  }

  @Override
  public void init(WizardModel model) {
    super.init(model);
    this.wizardModel = (CreateEntWizardModel) model;

    form = new EnterpriseForm();
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
    Enterprise ent = form.getModel().getBean().getEnterprise();
    ent.setParent(wizardModel.getEnterprise().getParent());
    wizardModel.setEnterprise(ent);
  }
}
