package com.seekon.system.org.client.view.dept;

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
import com.seekon.system.org.model.Department;

public class DeleteDepartmentAction extends AbstractTreeAction {

  private static final long serialVersionUID = 5225850363134980404L;

  private List<Department> deptList = new ArrayList<Department>();

  public DeleteDepartmentAction(JTree tree) {
    super(tree);

    this.putValue(Action.NAME, "删除内部机构");
    this.setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    deptList.clear();
    boolean enable = paths != null && paths.size() > 0;
    for (TreePath path : paths) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path
        .getLastPathComponent();
      Object userObject = treeNode.getUserObject();
      if (userObject instanceof Department) {
        enable = true;
        deptList.add((Department) userObject);
      } else {
        enable = false;
        deptList.clear();
        break;
      }
    }
    setEnabled(enable);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(tree, "确认删除选中的内部机构？", "请确认",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
      UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<Department>>() {

        @Override
        public List<Department> execute() throws Exception {
          ServiceFacade.getOrgService().deleteDepartmentList(deptList);
          return deptList;
        }

        @Override
        public void success(List<Department> result) {
          ObserverUtil.notifyEvent(
            new Event(deptList, OrgEventType.ENT_DELETE), tree);
          JOptionPane.showMessageDialog(tree, "删除成功！");
        }
      });

    }
  }

}
