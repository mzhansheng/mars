package com.seekon.system.common.client.category.tree;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.seekon.system.common.model.Category;

public class CategoryTreePage extends JPanel{

  private static final long serialVersionUID = 2849079864591111931L;

  private JTree tree;

  public CategoryTreePage(Category category) {
    super();
    
    tree = new JTree(new CategoryTreeModel(category));
    this.setLayout(new BorderLayout());
    this.add(new JScrollPane(tree), BorderLayout.CENTER);
  }
  
  public Category getSelectedCategory(){
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath()
      .getLastPathComponent();
    return (Category) node.getUserObject();
  }
}
