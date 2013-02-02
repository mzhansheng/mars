package com.seekon.system.rule.client.view.rule.wizard;

import java.awt.BorderLayout;

import javax.swing.Action;

import org.pietschy.wizard.Wizard;
import org.pietschy.wizard.WizardEvent;
import org.pietschy.wizard.WizardListener;
import org.pietschy.wizard.WizardModel;

import com.seekon.smartclient.component.viewedit.Page;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.system.common.model.Category;

public class CreateRuleWizard extends CallbackPage {

  private static final long serialVersionUID = -2895134507922541663L;

  private Action backAction;

  public CreateRuleWizard(Category category) {
    super();
    setLayout(new BorderLayout());

    WizardModel wizardModel = getWizardModel(category);
    Wizard wizard = new Wizard(wizardModel);
    wizard.addWizardListener(new WizardListener() {
      @Override
      public void wizardCancelled(WizardEvent e) {
        backAction.actionPerformed(null);
      }

      @Override
      public void wizardClosed(WizardEvent e) {
        backAction.actionPerformed(null);
      }
    });
    add(wizard, BorderLayout.CENTER);
  }

  protected WizardModel getWizardModel(Category category) {
    return new CreateRuleWizardModel(category);
  }

  @Override
  public void setGoBackAction(Action action) {
    backAction = action;
  }

  @Override
  public void setPreviousPage(Page page) {

  }

}
