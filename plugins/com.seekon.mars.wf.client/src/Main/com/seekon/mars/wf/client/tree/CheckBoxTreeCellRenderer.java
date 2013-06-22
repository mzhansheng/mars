package com.seekon.mars.wf.client.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.text.Position;
import javax.swing.tree.TreeCellRenderer;

import com.seekon.mars.wf.model.Company;
import com.seekon.mars.wf.model.Org;
import com.seekon.mars.wf.model.Staff;

public class CheckBoxTreeCellRenderer extends CheckBoxNodePanel implements TreeCellRenderer {
  private static final long serialVersionUID = -2563362592787028423L;

  public CheckBoxTreeCellRenderer() {
    super();
    setPanelOpaque(false);
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
    boolean leaf, int row, boolean hasFocus) {
    CheckBoxTreeNode node = (CheckBoxTreeNode) value;
    Object userObj = node.getUserObject();
    setText(userObj != null ? node.getUserObject().toString() : "");
    setSelected(node.isChecked());
    if (userObj instanceof Company) {
      setIcon(new ImageIcon(CheckBoxTreeCellRenderer.class.getResource("company.gif")));
      hideCheckBox(false);
    } else if (userObj instanceof Org) {
      setIcon(new ImageIcon(CheckBoxTreeCellRenderer.class.getResource("org.gif")));
      Org orgTmp = (Org) userObj;
      if (orgTmp.getParentId() == null)
        hideCheckBox(false);
      else
        hideCheckBox(true);
    } else if (userObj instanceof Position) {
      setIcon(new ImageIcon(CheckBoxTreeCellRenderer.class.getResource("position.gif")));
      hideCheckBox(true);
    } else if (userObj instanceof Staff) {
      setIcon(new ImageIcon(CheckBoxTreeCellRenderer.class.getResource("staff.gif")));
      hideCheckBox(true);
    }
    //    if (node.isChecked()) {
    //      setSelected(true);
    //    } else {
    //      setSelected(false);
    //    }
    return this;
  }
}
