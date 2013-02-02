package com.seekon.system.org.client.view.dept;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.system.org.client.view.OrganizationPage;
import com.seekon.system.org.client.view.dept.wizard.CreateDeptWizard;
import com.seekon.system.org.model.Enterprise;

public class CreateDepartmentAction extends AbstractTreeAction {

  private static final long serialVersionUID = 6168987888889064773L;

  private Enterprise parentEnt;

  public CreateDepartmentAction(JTree tree) {
    super(tree);

    this.putValue(Action.NAME, "新建内部机构...");
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
        if (this.parentEnt.getId() == null) {//根
          this.setEnabled(false);
          this.parentEnt = null;
        } else {
          this.setEnabled(true);
        }
      } else {
        this.setEnabled(false);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    OrganizationPage page = (OrganizationPage) SwingUtilities.getAncestorOfClass(
      OrganizationPage.class, tree);
    page.getOrgElementContainer().callback(new CreateDeptWizard(parentEnt));
  }

}
