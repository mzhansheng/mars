package com.seekon.system.auth.client.view.datascope.wizard;

import com.seekon.system.common.client.category.AbstractSelectCategoryWizardStep;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.view.rule.wizard.CreateRuleWizardModel;

public class CreateDatascopeWizardModel extends CreateRuleWizardModel{

  public CreateDatascopeWizardModel(Category category) {
    super(category);
  }

  @Override
  protected AbstractSelectCategoryWizardStep getSelectCategoryWizardStep() {
    return new SelectDatascopeCategoryWizardStep();
  }
}
