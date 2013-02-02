package com.seekon.system.org.client.view.ent.wizard;

import java.awt.BorderLayout;

import javax.swing.Action;

import org.pietschy.wizard.Wizard;
import org.pietschy.wizard.WizardEvent;
import org.pietschy.wizard.WizardListener;

import com.seekon.smartclient.component.viewedit.Page;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.system.org.model.Enterprise;

public class CreateEntWizard extends CallbackPage {

  private static final long serialVersionUID = -2895134507922541663L;

  private Action backAction;

  public CreateEntWizard(Enterprise parent) {
    super();
    setLayout(new BorderLayout());

    CreateEntWizardModel wizardModel = new CreateEntWizardModel(parent);
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

  @Override
  public void setGoBackAction(Action action) {
    backAction = action;
  }

  @Override
  public void setPreviousPage(Page page) {

  }

}
