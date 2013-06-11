package com.seekon.system.auth.client.view.role.wizard;

import org.pietschy.wizard.InvalidStateException;

import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.common.client.category.AbstractSelectCategoryWizardStep;
import com.seekon.system.common.model.Category;

public class SelectRoleCategoryWizardStep extends AbstractSelectCategoryWizardStep {

  private static final long serialVersionUID = 329575043262787591L;

  public SelectRoleCategoryWizardStep() {
    super("角色类别", "选择创建角色的类别");
  }

  @Override
  public void applyState() throws InvalidStateException {
    ((CreateRoleWizardModel) model).getRole().setCategory(
      categoryTreePage.getSelectedCategory());
  }

  @Override
  protected Category getCategory() {
    return ServiceFacade.getRoleService().getCategory();
  }
}
