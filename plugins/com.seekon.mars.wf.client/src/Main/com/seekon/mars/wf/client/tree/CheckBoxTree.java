package com.seekon.mars.wf.client.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.seekon.mars.wf.model.Staff;

public class CheckBoxTree extends JTree {
  private CheckBoxTree self = this;

  private List<Staff> selectBusinessList = new ArrayList<Staff>();//选择的业务对象列表

  public CheckBoxTree(CheckBoxTreeNode node) {
    super();
    this.setRowHeight(18);
    addCheckingListener();
  }

  public CheckBoxTree(TreeModel newModel) {
    super(newModel);
    this.setRowHeight(18);
    this.setExpandsSelectedPaths(true);
    addCheckingListener();
  }

  public List getSelectBusinessList() {
    return selectBusinessList;
  }

  private void setSelectBusinessList(List selectUserObjectList) {
    Iterator itr = selectUserObjectList.iterator();
    while (itr.hasNext()) {
      Staff staff = (Staff) itr.next();
      if (!selectBusinessList.contains(staff)) {
        selectBusinessList.add(staff);
      }
    }
  }

  private void addCheckingListener() {
    addMouseListener(new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent e) {
        int row = getRowForLocation(e.getX(), e.getY());
        self.expandRow(row);
        TreePath treePath = getPathForRow(row);
        if (treePath == null) {
          return;
        }
        Object treeObj = treePath.getLastPathComponent();
        CheckBoxTreeNode node = ((CheckBoxTreeNode) treeObj);
        if (node.isCheckVisible) {
          boolean checking = !node.isChecked();

          if (checking) {
            node.setChecked(checking);
            CheckBoxTreeModel model = (CheckBoxTreeModel) self.getModel();
            CheckBoxTreeNode[] ts = null;
            model.processCheckedNode(selectBusinessList, node, ts);
            self.setModel(model);
            self.revalidate();

            //          treePath = getPathForRow(row);
            //          treeObj = treePath.getLastPathComponent();
            //          node = ((CheckBoxTreeNode) treeObj);
            self.repaint();
            //          int rows=self.getRowCount();
            //          for(int i=0;i<=rows;i++){
            //            self.expandPath(self.getPathForRow(i));
            //          }
          }
          checkNode(node, checking);
          self.repaint();
          //        setSelectBusinessList(((CheckBoxTreeModel)self.getModel()).getCurNode().getSelectBusinessList());
          //        System.out.println(selectBusinessList.toString());
        }
      }

      private void getLeafNode(CheckBoxTreeNode node, boolean checking, List selectBusinessList) {
        Staff obj = (Staff) node.getUserObject();
        System.out.println(obj.toString());
        if (checking) {
          if (!selectBusinessList.contains(obj))
            selectBusinessList.add(obj);
        } else {
          if (selectBusinessList.contains(obj))
            selectBusinessList.remove(obj);
        }
      }

      private void checkNode(CheckBoxTreeNode node, boolean checking) {
        node.setChecked(selectBusinessList, checking, checking);
      }
    });
  }

  //  private  void addTreeSelectionListener(){
  //    addTreeSelectionListener(new TreeSelectionListener() {
  //    @Override
  //    public void valueChanged(TreeSelectionEvent e) {
  //      TreePath newLeadSelectionPath = e.getNewLeadSelectionPath();
  //      if (newLeadSelectionPath != null
  //        && newLeadSelectionPath.getLastPathComponent() != getModel()
  //          .getRoot()) {
  //        Cursor cursor = getCursor();
  //        try {
  //          setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  //        } finally {
  //          setCursor(cursor);
  //        }
  //      }
  //    }
  //  });
  //  }

  //  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
  //    IllegalAccessException, UnsupportedLookAndFeelException {
  //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  //    SwingUtilities.invokeLater(new Runnable() {
  //      @Override
  //      public void run() {
  //        JFrame frame = new JFrame("frame");
  //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //        frame.setSize(280, 400);
  //        frame.setLocationRelativeTo(null);
  //        Company com = new Company();
  //        com.setCode(null);
  //        com.setName("公司");
  //        com.setNd(2009);
  //        CheckBoxTreeNode root = new CheckBoxTreeNode();
  //        root.setUserObject(com);
  //        CheckBoxTreeModel treeModel = new CheckBoxTreeModel(root) {
  //          @Override
  //          protected Object[] gets(CheckBoxTreeNode parent) {
  //            Object userObj = parent.getUserObject();
  //            List userObjList = null;
  //            Iterator itr=null;
  //            List treeNodeList = new ArrayList();
  //            if (userObj instanceof Company) {
  //              Company company = (Company) userObj;
  //              String comParentId = company.getCode();
  //              userObjList = BusinessDaoFactory.getWFRuntimeDao().getLevelCompany(comParentId);
  //              itr = userObjList.iterator();
  //              while (itr.hasNext()) {
  //                CheckBoxTreeNode treeNode = new CheckBoxTreeNode(itr.next());
  //                treeNodeList.add(treeNode);
  //              }
  //            } else if (userObj instanceof Org) {
  //              Org org= (Org)userObj;
  //              userObjList= BusinessDaoFactory.getWFRuntimeDao().getDownOrg(org.getCoCode(), org.getCode());
  //              itr = userObjList.iterator();
  //              while (itr.hasNext()) {
  //                CheckBoxTreeNode treeNode = new CheckBoxTreeNode(itr.next());
  //                treeNodeList.add(treeNode);
  //              }
  //            } else if (userObj instanceof Position) {
  //              Position pos= (Position)userObj;
  //              userObjList= BusinessDaoFactory.getWFRuntimeDao().getPosition(pos.getCode(),pos.getOrgCode(),pos.getOnOff());
  //              itr = userObjList.iterator();
  //              while (itr.hasNext()) {
  //                Position tempPos=(Position)itr.next();
  //                tempPos.setCoCode(pos.getCoCode());
  //                tempPos.setOrgCode(pos.getOrgCode());
  //                tempPos.setOnOff("1<>1");
  //                CheckBoxTreeNode treeNode = new CheckBoxTreeNode(tempPos);
  //                treeNodeList.add(treeNode);
  //              }
  //            } else if (userObj instanceof Staff) {
  //              Staff staff= (Staff)userObj;
  //              userObjList= BusinessDaoFactory.getWFRuntimeDao().getStaff(staff.getCode(),staff.getPosiCode(), staff.getCoCode(), staff.getOrgCode(),staff.getOnOff());
  //              itr = userObjList.iterator();
  //              while (itr.hasNext()) {
  //                Staff tempStaff=(Staff)itr.next();
  //                tempStaff.setOnOff("1<>1");
  //                CheckBoxTreeNode treeNode = new CheckBoxTreeNode(tempStaff);
  //                treeNodeList.add(treeNode);
  //              }
  //            } else {
  //              userObjList = BusinessDaoFactory.getWFRuntimeDao().getLevelCompany(null);
  //            }
  //            
  //            return treeNodeList.toArray(new CheckBoxTreeNode[treeNodeList.size()]);
  //          }
  //
  //        };
  //
  //        CheckBoxTree tree = new CheckBoxTree(treeModel);
  //        CheckBoxTreeCellRenderer render = new CheckBoxTreeCellRenderer();
  //
  //        tree.setCellRenderer(render);
  //        frame.getContentPane().add(new JScrollPane(tree));
  //        frame.setVisible(true);
  //      }
  //    });
  //    
  //  }
}
