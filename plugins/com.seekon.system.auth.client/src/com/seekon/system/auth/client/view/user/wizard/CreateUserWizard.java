package com.seekon.system.auth.client.view.user.wizard;

import java.awt.BorderLayout;

import javax.swing.Action;

import org.pietschy.wizard.Wizard;
import org.pietschy.wizard.WizardEvent;
import org.pietschy.wizard.WizardListener;

import com.seekon.smartclient.component.viewedit.Page;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.org.model.AbstractOrgItem;

public class CreateUserWizard extends CallbackPage {

  private static final long serialVersionUID = -6356475849191580512L;

  private Action backAction;

  private Wizard wizard;

  private CreateUserWizardModel wizardModel;

  ////private UserCategory			category;

  public CreateUserWizard(AbstractOrgItem orgItem) {
    ////this.category = category;

    setLayout(new BorderLayout());

    wizardModel = new CreateUserWizardModel(orgItem);
    wizard = new Wizard(wizardModel);
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
    this.backAction = action;
  }

  @Override
  public void setPreviousPage(Page page) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update(Event event) {
    // TODO Auto-generated method stub

  }
}
