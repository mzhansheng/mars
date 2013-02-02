package com.seekon.system.org.client.view.ent;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.system.org.client.view.OrganizationPage;
import com.seekon.system.org.client.view.ent.wizard.CreateEntWizard;
import com.seekon.system.org.model.Enterprise;

public class CreateEnterpriseAction extends AbstractTreeAction {

  private static final long serialVersionUID = 6168987888889064773L;

  private Enterprise parentEnt;

  public CreateEnterpriseAction(JTree tree) {
    super(tree);

    this.putValue(Action.NAME, "新建单位...");
    this.setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    setEnabled(false);

    if (paths.isEmpty()) {
      setEnabled(false);
    } else {
      TreePath tp = paths.get(0);
      Object userObject = ((DefaultMutableTreeNode) tp.getLastPathComponent())
        .getUserObject();
      if (userObject instanceof Enterprise) {
        this.parentEnt = (Enterprise) userObject;
        this.setEnabled(true);
      } else {
        this.setEnabled(false);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    OrganizationPage page = (OrganizationPage) SwingUtilities.getAncestorOfClass(
      OrganizationPage.class, tree);
    page.getOrgElementContainer().callback(new CreateEntWizard(parentEnt));
  }

}
