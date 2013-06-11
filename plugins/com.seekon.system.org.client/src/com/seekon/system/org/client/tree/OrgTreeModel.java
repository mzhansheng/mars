package com.seekon.system.org.client.tree;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.seekon.system.org.client.internal.ServiceFacade;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Enterprise;
import com.seekon.system.org.spi.OrgService;

public class OrgTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = -38135261861861748L;

  private OrgService orgService;

  public OrgTreeModel(AbstractOrgItem orgItem) {
    super(new DefaultMutableTreeNode(orgItem));
    orgService = ServiceFacade.getOrgService();
  }

  @Override
  public Object getChild(Object parent, int index) {
    Object orgItem = (Object) ((DefaultMutableTreeNode) parent).getUserObject();
    if (orgItem instanceof Enterprise) {
      Enterprise ent = (Enterprise) orgItem;
      checkAndInitEnterpriseChildOrgItemList(ent);
      DefaultMutableTreeNode node = null;
      try {
        node = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) parent)
          .getChildAt(index);
      } catch (Exception e) {
      }
      if (node == null) {
        node = new DefaultMutableTreeNode(ent.getChild(index));
        ((DefaultMutableTreeNode) parent).add(node);
      }
      return node;
    } else {
      return null;
    }
  }

  @Override
  public int getChildCount(Object parent) {
    Object orgItem = (Object) ((DefaultMutableTreeNode) parent).getUserObject();
    if (orgItem instanceof Enterprise) {
      Enterprise ent = (Enterprise) orgItem;
      checkAndInitEnterpriseChildOrgItemList(ent);
      return ent.getChildCount();
    } else {
      return 0;
    }
  }

  @Override
  public boolean isLeaf(Object node) {
    return getChildCount(node) == 0;
  }

  private void checkAndInitEnterpriseChildOrgItemList(Enterprise ent) {
    List<AbstractOrgItem> orgItemList = ent.getChildOrgItemList();
    if (orgItemList == null) {
      orgItemList = orgService.getChildOrgItems(ent);
      ent.setChildOrgItemList(orgItemList);
    }
  }
}
