package com.seekon.system.auth.client.view.datascope.wizard;

import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.view.rule.wizard.SelectRuleCategoryWizardStep;

public class SelectDatascopeCategoryWizardStep extends SelectRuleCategoryWizardStep{

  private static final long serialVersionUID = -8999210786927326572L;

  public SelectDatascopeCategoryWizardStep() {
    super("数据范围类别", "选择创建数据范围的类别");
  }

  @Override
  protected Category getCategory() {
    return ServiceFacade.getDataScopeService().getDateScopeCategory();
  }

}
