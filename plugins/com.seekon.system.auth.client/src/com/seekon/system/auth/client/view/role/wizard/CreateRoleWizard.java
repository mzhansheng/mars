package com.seekon.system.auth.client.view.role.wizard;

import java.awt.BorderLayout;

import javax.swing.Action;

import org.pietschy.wizard.Wizard;
import org.pietschy.wizard.WizardEvent;
import org.pietschy.wizard.WizardListener;

import com.seekon.smartclient.component.viewedit.Page;
import com.seekon.smartclient.component.viewedit.callback.CallbackPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.common.model.Category;

public class CreateRoleWizard extends CallbackPage {

  private static final long serialVersionUID = -354075466716996150L;

  private Action backAction;

  private Wizard wizard;

  private CreateRoleWizardModel wizardModel;

  public CreateRoleWizard(Category category) {
    setLayout(new BorderLayout());

    wizardModel = new CreateRoleWizardModel(category);
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
    
  }

  @Override
  public void update(Event event) {
   
  }
}
