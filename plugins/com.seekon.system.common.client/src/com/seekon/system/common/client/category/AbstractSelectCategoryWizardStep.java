package com.seekon.system.common.client.category;

import java.awt.BorderLayout;

import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.seekon.system.common.client.category.tree.CategoryTreePage;
import com.seekon.system.common.model.Category;

public abstract class AbstractSelectCategoryWizardStep extends PanelWizardStep{

  private static final long serialVersionUID = 4714373866420707927L;

  protected CategoryTreePage categoryTreePage;
  
  protected WizardModel model;
  
  public AbstractSelectCategoryWizardStep(String name, String summary) {
    super(name, summary);
  }

  @Override
  public void init(WizardModel model) {
    super.init(model);
    this.model = model;
    
    categoryTreePage = new CategoryTreePage(getCategory());
    setLayout(new BorderLayout());
    add(categoryTreePage, BorderLayout.CENTER);

    setComplete(true);
  }
  
  abstract protected Category getCategory();
}
