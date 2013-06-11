package com.seekon.system.org.client.tree;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import com.seekon.system.org.model.Enterprise;

public class JOrgTree extends JTree {

  private static final long serialVersionUID = 7744614288879219930L;

  public JOrgTree() {
    super();

    Enterprise ent = new Enterprise();
    ent.setName("组织机构");

    this.setModel(new OrgTreeModel(ent));
  }

  public JOrgTree(TreeModel newModel) {
    super(newModel);
  }

}
