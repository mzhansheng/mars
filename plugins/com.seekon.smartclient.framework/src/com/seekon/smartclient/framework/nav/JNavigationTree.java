package com.seekon.smartclient.framework.nav;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.seekon.smartclient.framework.NavigationResourceFilter;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.ResourceFacade;
import com.seekon.smartclient.module.tree.JDefaultResourceTree;

public class JNavigationTree extends JDefaultResourceTree {

  private static final long serialVersionUID = -7622567257367027662L;

  public JNavigationTree() {
    super();
    this.expandRow(1);
  }

  @Override
  public TreeModel getResourceTreeModel() {
    AbstractResource root = ResourceFacade.getRegistriedResource();
    //removeActions(root);

    List<NavigationResourceFilter> navFilterList = NavFitlerRegistry.getInstance()
      .getNavFilterList();
    for (NavigationResourceFilter filter : navFilterList) {
      if (root == null) {
        break;
      }
      root = filter.doFilter(root);
    }

    return new NavigationTreeModel(new DefaultMutableTreeNode(root));
  }

  /**
   * @deprecated 使用NavigationTreeModel来对action进行过滤
   */
  //  private void removeActions(AbstractResource aResource) {
  //    if (aResource instanceof Resource) {
  //      Resource resource = (Resource) aResource;
  //      Iterator<AbstractResource> children = resource.getChildren().iterator();
  //      while (children.hasNext()) {
  //        AbstractResource child = children.next();
  //        if (child instanceof Action) {
  //          children.remove();
  //          child = null;
  //        } else {
  //          removeActions(child);
  //        }
  //      }
  //    } else {
  //      aResource = null;
  //    }
  //  }
}
