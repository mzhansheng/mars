package com.seekon.system.auth.client.view.role.wizard;

import java.awt.BorderLayout;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.seekon.system.auth.client.view.role.resource.RoleResourceForm;

public class AssignResourceWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = 3659557059894225462L;

  private CreateRoleWizardModel model;

  private RoleResourceForm form;

  public AssignResourceWizardStep() {
    super("资源", "将功能、操作赋予角色");
  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateRoleWizardModel) model;

    form = new RoleResourceForm();
    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);

    setComplete(true);
  }

  @Override
  public void applyState() throws InvalidStateException {
    model.getRole().setResource(form.getAssignedResource());
  }

  @Override
  public void prepare() {

  }

}
