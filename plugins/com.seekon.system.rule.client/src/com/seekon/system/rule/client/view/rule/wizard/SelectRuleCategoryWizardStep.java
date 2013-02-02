package com.seekon.system.rule.client.view.rule.wizard;

import org.pietschy.wizard.InvalidStateException;

import com.seekon.system.common.client.category.AbstractSelectCategoryWizardStep;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.internal.ServiceFacade;

public class SelectRuleCategoryWizardStep extends AbstractSelectCategoryWizardStep {

  private static final long serialVersionUID = 329575043262787591L;

  public SelectRuleCategoryWizardStep() {
    super("规则类别", "选择创建规则的类别");
  }
  
  public SelectRuleCategoryWizardStep(String name, String summary) {
    super(name, summary);
  }

  @Override
  public void applyState() throws InvalidStateException {
    ((CreateRuleWizardModel)model).getRule().setCategory(categoryTreePage.getSelectedCategory());
  }

  @Override
  protected Category getCategory() {
    return ServiceFacade.getRuleService()
      .getCategory();
  }
}
