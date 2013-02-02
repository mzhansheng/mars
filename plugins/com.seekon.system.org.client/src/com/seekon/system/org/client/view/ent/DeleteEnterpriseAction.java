package com.seekon.system.org.client.view.ent;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.org.client.internal.ServiceFacade;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Enterprise;

public class DeleteEnterpriseAction extends AbstractTreeAction {

  private static final long serialVersionUID = 5225850363134980404L;

  private List<Enterprise> entList = new ArrayList<Enterprise>();

  public DeleteEnterpriseAction(JTree tree) {
    super(tree);

    this.putValue(Action.NAME, "删除单位");
    this.setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    entList.clear();
    boolean enable = paths != null && paths.size() > 0;
    for (TreePath path : paths) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path
        .getLastPathComponent();
      Object userObject = treeNode.getUserObject();
      if (userObject instanceof Enterprise) {
        Enterprise ent = (Enterprise) userObject;
        if (ent.getId() == null) {//根
          enable = false;
          entList.clear();
          break;
        }
        enable = true;
        entList.add((Enterprise) userObject);
      } else {
        enable = false;
        entList.clear();
        break;
      }
    }
    setEnabled(enable);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(tree, "确认删除选中的单位和其下级单位、内部机构？", "请确认",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
      UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<Enterprise>>() {

        @Override
        public List<Enterprise> execute() throws Exception {
          ServiceFacade.getOrgService().deleteEnterpriseList(entList);
          return entList;
        }

        @Override
        public void success(List<Enterprise> result) {
          ObserverUtil
            .notifyEvent(new Event(entList, OrgEventType.ENT_DELETE), tree);
          JOptionPane.showMessageDialog(tree, "删除成功！");
        }
      });

    }
  }

}
