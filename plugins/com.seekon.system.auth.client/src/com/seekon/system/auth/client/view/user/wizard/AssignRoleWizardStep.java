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

import com.seekon.system.auth.client.view.role.wizard.CreateRoleWizard;
import com.seekon.system.auth.client.view.user.UserPage;
import com.seekon.system.auth.client.view.user.role.UserRolesForm;

public class AssignRoleWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = -6382655015791884755L;

  private CreateUserWizardModel model;

  private UserRolesForm form;

  public AssignRoleWizardStep() {
    super("角色授权", "给用户赋权限");
  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateUserWizardModel) model;

    form = new UserRolesForm();
    form.edit(this.model.getUser());

    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//    panel.add(new JButton(new AbstractAction("刷新") {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        form.reload();
//      }
//    }));
    panel.add(new JButton(new AbstractAction("创建角色...") {
      @Override
      public void actionPerformed(ActionEvent e) {
        UserPage userPage = (UserPage) SwingUtilities.getAncestorOfClass(
          UserPage.class, getView());
        userPage.getUserRightContainer().callback(new CreateRoleWizard(null));
      }
    }));
    add(panel, BorderLayout.NORTH);

    setComplete(true);
  }

  @Override
  public void applyState() throws InvalidStateException {
    this.model.getUser().setRoleList(form.getUserSelectedRoleList());
  }

}
