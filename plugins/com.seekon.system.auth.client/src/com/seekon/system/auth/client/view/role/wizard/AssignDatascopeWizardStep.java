package com.seekon.system.auth.client.view.role.wizard;

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
import com.seekon.system.auth.client.view.role.RolePage;
import com.seekon.system.auth.client.view.role.datascope.RoleDatascopeForm;
import com.seekon.system.auth.client.view.user.UserPage;

public class AssignDatascopeWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = -8207660909710430490L;

  private CreateRoleWizardModel model;

  private RoleDatascopeForm form;

  public AssignDatascopeWizardStep() {
    super("数据权限", "给角色赋数据权限");
  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateRoleWizardModel) model;

    this.form = new RoleDatascopeForm();
    this.form.edit(this.model.getRole());

    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel.add(new JButton(new AbstractAction("创建数据范围...") {
      @Override
      public void actionPerformed(ActionEvent e) {
        RolePage rolePage = (RolePage) SwingUtilities.getAncestorOfClass(
          RolePage.class, getView());
        if (rolePage != null) {
          rolePage.getRoleDisplayer().callback(new CreateDatascopeWizard(null));
        } else {
          UserPage userPage = (UserPage) SwingUtilities.getAncestorOfClass(
            UserPage.class, getView());
          if (userPage != null) {
            userPage.getUserRightContainer().callback(
              new CreateDatascopeWizard(null));
          }
        }
      }
    }));
    add(panel, BorderLayout.NORTH);

    setComplete(true);
  }

  @Override
  public void applyState() throws InvalidStateException {
    this.model.getRole().setDatascopeRuleList(form.getSelectedDatascopeRuleList());
  }
}
