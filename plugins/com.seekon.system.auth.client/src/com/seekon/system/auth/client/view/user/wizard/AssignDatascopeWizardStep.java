package com.seekon.system.auth.client.view.user.wizard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.seekon.system.auth.client.view.datascope.wizard.CreateDatascopeWizard;
import com.seekon.system.auth.client.view.user.UserPage;
import com.seekon.system.auth.client.view.user.datascope.UserDatascopeForm;

public class AssignDatascopeWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = -8207660909710430490L;

  private CreateUserWizardModel model;

  private UserDatascopeForm form;

  public AssignDatascopeWizardStep() {
    super("数据权限", "给用户赋数据权限");
  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateUserWizardModel) model;

    this.form = new UserDatascopeForm();
    this.form.edit(this.model.getUser());

    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel.add(new JButton(new AbstractAction("创建数据范围...") {
      @Override
      public void actionPerformed(ActionEvent e) {
        UserPage userPage = (UserPage) SwingUtilities.getAncestorOfClass(
          UserPage.class, getView());
        userPage.getUserRightContainer().callback(new CreateDatascopeWizard(null));
      }
    }));
    add(panel, BorderLayout.NORTH);
    setComplete(true);
  }

  @Override
  public void applyState() throws InvalidStateException {
    this.model.getUser().setDatascopeRuleList(form.getSelectedDatascopeRuleList());
  }
}
