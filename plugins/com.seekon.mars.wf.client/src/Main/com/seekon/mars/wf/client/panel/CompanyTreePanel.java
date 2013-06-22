package com.seekon.mars.wf.client.panel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.client.tree.CheckBoxTree;
import com.seekon.mars.wf.client.tree.CheckBoxTreeCellRenderer;
import com.seekon.mars.wf.client.tree.CheckBoxTreeModel;
import com.seekon.mars.wf.client.tree.CheckBoxTreeNode;
import com.seekon.mars.wf.client.tree.TreeType;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.model.BusinessType;
import com.seekon.mars.wf.model.Company;
import com.seekon.mars.wf.model.Org;
import com.seekon.mars.wf.model.Position;
import com.seekon.mars.wf.model.Staff;
import com.seekon.mars.wf.service.BusinessService;

/**
 * 单位层次树对话框
 * @author Administrator
 *
 */
public class CompanyTreePanel extends JPanel {
  private BusinessService businessService;
  
  private int treeType;

  private Integer nd = new Integer(DateUtil.getYear());

  private BusinessType businessType;

  private CheckBoxTree tree;

  /**
   * @param type 面板类型
   * @param businesstype 业务节点类型
   */
  public CompanyTreePanel(int type, BusinessType businesstype) {
    this.treeType = type;
    this.businessType = businesstype;
    
    businessService= ServiceFacade.getBusinessService();
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        initGui();
      }
    });
  }

  public List getSelectBusinessList() {
    return tree.getSelectBusinessList();
  }

  private void initGui() {
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    CheckBoxTreeModel treeModel = null;
    CheckBoxTreeNode root = new CheckBoxTreeNode();
    root.setUserObject(businessType);
    root.setCheckVisible(false);
    switch (treeType) {
    case TreeType.ALL_LEVEL:
      treeModel = new AllLevelCompanyTreeModel(root);
      break;
    case TreeType.COMPANY_DOWN_LEVEL:
      treeModel = new CompanyDownLevelTreeModel(root);
      break;
    case TreeType.COMPENY_UP_LEVEL:
      treeModel = new CompanyUpLevelTreeModel(root);
      break;
    case TreeType.ORG_DOWN_LEVEL:
      treeModel = new OrgDownLevelTreeModel(root);
      break;
    case TreeType.ORG_UP_LEVEL:
      treeModel = new OrgUpLevelTreeModel(root);
      break;
    }

    tree = new CheckBoxTree(treeModel);
    CheckBoxTreeCellRenderer render = new CheckBoxTreeCellRenderer();
    tree.setCellRenderer(render);
    //      }
    //    });
    scrollPane.getViewport().add(tree);
    setLayout(new BorderLayout());
    add(scrollPane);

  }

  /**
   * 得到当前节点的所有子节点
   * @param parent 当前节点
   * @return
   */
  private Object[] getChildren(CheckBoxTreeNode parent) {
    Object userObj = parent.getUserObject();
    List userObjList = null;
    Iterator itr = null;
    List<CheckBoxTreeNode> treeNodeList = new ArrayList<CheckBoxTreeNode>();
    if (userObj instanceof Company) {
      Company company = (Company) userObj;
      String comParentId = company.getCode();
      //单位的下级单位
      userObjList = businessService.getLevelCompany(comParentId, nd);
      itr = userObjList.iterator();
      while (itr.hasNext()) {
        CheckBoxTreeNode treeNode = new CheckBoxTreeNode(itr.next());
        treeNodeList.add(treeNode);
      }
      //单位下的直接组织
      userObjList = businessService.getDownOrg(comParentId, null, nd);
      while (itr.hasNext()) {
        CheckBoxTreeNode treeNode = new CheckBoxTreeNode(itr.next());
        treeNodeList.add(treeNode);
      }
    } else if (userObj instanceof Org) {
      Org org = (Org) userObj;
      userObjList = businessService.getDownOrg(org.getCoCode(), org.getCode(), nd);
      itr = userObjList.iterator();
      while (itr.hasNext()) {
        CheckBoxTreeNode treeNode = new CheckBoxTreeNode(itr.next());
        treeNodeList.add(treeNode);
      }
    } else if (userObj instanceof Position) {
      Position pos = (Position) userObj;
      userObjList = businessService.getPosition(pos.getCode(), pos.getOrgCode(),
        pos.getOnOff(), nd);
      itr = userObjList.iterator();
      while (itr.hasNext()) {
        Position tempPos = (Position) itr.next();
        tempPos.setCoCode(pos.getCoCode());
        tempPos.setOrgCode(pos.getOrgCode());
        tempPos.setOnOff("1<>1");
        CheckBoxTreeNode treeNode = new CheckBoxTreeNode(tempPos);
        treeNodeList.add(treeNode);
      }
    } else if (userObj instanceof Staff) {
      Staff staff = (Staff) userObj;
      userObjList = businessService.getStaff(staff.getCode(), staff.getPosiCode(),
        staff.getCoCode(), staff.getOrgCode(), staff.getOnOff(), nd);
      itr = userObjList.iterator();
      while (itr.hasNext()) {
        Staff tempStaff = (Staff) itr.next();
        tempStaff.setOnOff("1<>1");
        CheckBoxTreeNode treeNode = new CheckBoxTreeNode(tempStaff);
        treeNodeList.add(treeNode);
      }
    } else {
      userObjList = businessService.getLevelCompany(null, nd);
    }
    if (userObjList.size() > 0)
      parent.setLeaf(false);
    return treeNodeList.toArray(new CheckBoxTreeNode[treeNodeList.size()]);
  }

  private CheckBoxTreeNode getTreeNodeStack(CheckBoxTreeNode curNode,
    Map<CheckBoxTreeNode, CheckBoxTreeNode[]> buffer) {
    Object userObj = curNode.getUserObject();

    CheckBoxTreeNode tmpNode = new CheckBoxTreeNode();
    if (userObj instanceof Company) {
      Company comp = (Company) userObj;
      Object parentObj = businessService.getUpLevelCompany(comp.getCode(), nd);
      tmpNode.setUserObject(parentObj);
      tmpNode.setCheckVisible(false);
      buffer.put(tmpNode, new CheckBoxTreeNode[] { curNode });
      if (comp.getParentId() != null) {
        getTreeNodeStack(tmpNode, buffer);
      }
    } else if (userObj instanceof Org) {
      Org org = (Org) userObj;
      if (org.getParentId() == null) {
        Object comObj = businessService.getCompanyById(org.getCoCode(), nd);
        Company compOrg = (Company) comObj;
        tmpNode.setUserObject(compOrg);
        tmpNode.setCheckVisible(false);
        buffer.put(tmpNode, new CheckBoxTreeNode[] { curNode });
        getTreeNodeStack(tmpNode, buffer);
      } else {
        Object orgObj = businessService.getUpOrg(org.getCoCode(), org.getCode(), nd);
        tmpNode.setUserObject(orgObj);
        buffer.put(tmpNode, new CheckBoxTreeNode[] { curNode });
        getTreeNodeStack(tmpNode, buffer);
      }
    }
    return tmpNode;
  }

  /**
   * 所有层次的单位树节点MODEL
   * @author Administrator
   *
   */
  private class AllLevelCompanyTreeModel extends CheckBoxTreeModel {
    public AllLevelCompanyTreeModel(CheckBoxTreeNode root) {
      super(root);
    }

    @Override
    protected Object[] gets(CheckBoxTreeNode parent) {
      return getChildren(parent);
    }

  }

  private class CompanyUpLevelTreeModel extends CheckBoxTreeModel {
    public CompanyUpLevelTreeModel(CheckBoxTreeNode curNode) {
      super();
      super.setCurNode(curNode);
      root = getCurNodeRoot();
    }

    /*
     * 计算当前节点的根节点
     */
    private CheckBoxTreeNode getCurNodeRoot() {
      return getTreeNodeStack(curNode, buffer);
    }

    @Override
    protected Object[] gets(CheckBoxTreeNode parent) {
      return getChildren(parent);
    }

  }

  private class OrgUpLevelTreeModel extends CheckBoxTreeModel {
    public OrgUpLevelTreeModel(CheckBoxTreeNode curNode) {
      super();
      super.setCurNode(curNode);
      root = getCurNodeRoot();
    }

    private CheckBoxTreeNode getCurNodeRoot() {
      return getTreeNodeStack(curNode, buffer);
    }

    @Override
    protected Object[] gets(CheckBoxTreeNode parent) {
      return getChildren(parent);
    }
  }

  private class CompanyDownLevelTreeModel extends CheckBoxTreeModel {
    public CompanyDownLevelTreeModel(CheckBoxTreeNode root) {
      super(root);
    }

    @Override
    protected Object[] gets(CheckBoxTreeNode parent) {
      return getChildren(parent);
    }
  }

  private class OrgDownLevelTreeModel extends CheckBoxTreeModel {
    public OrgDownLevelTreeModel(CheckBoxTreeNode root) {
      super(root);
    }

    @Override
    protected Object[] gets(CheckBoxTreeNode parent) {
      return getChildren(parent);
    }
  }

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new JFrame("frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(280, 400);
        frame.setLocationRelativeTo(null);
        //        Company com = new Company();
        //        com.setCode(null);
        //        com.setName("丹阳财政");
        //        //com.setParentId("029");
        //        com.setNd(2009);

        Org org = new Org();
        org.setCode("03");
        org.setCoCode("999");
        org.setName("预算科");
        org.setNd(2009);
        frame.getContentPane().add(new CompanyTreePanel(TreeType.ORG_DOWN_LEVEL, org));
        frame.setVisible(true);
      }
    });
  }
}
