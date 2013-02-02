package com.seekon.system.auth.client.view.datascope.wizard;

import org.pietschy.wizard.WizardModel;

import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.view.rule.wizard.CreateRuleWizard;

public class CreateDatascopeWizard extends CreateRuleWizard{

  private static final long serialVersionUID = -8612605261017797371L;
  
  public CreateDatascopeWizard(Category category) {
    super(category);
  }

  @Override
  protected WizardModel getWizardModel(Category category) {
    return new CreateDatascopeWizardModel(category);
  }
  
}
