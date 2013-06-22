package com.seekon.mars.wf.client.tree;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CheckNodeTreeTest {
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
  IllegalAccessException, UnsupportedLookAndFeelException {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run() {
      final CheckBoxTreeNode croot = new CheckBoxTreeNode("doc");
      CheckBoxTreeNode node3, child;
      CheckBoxTreeNode cnode = new CheckBoxTreeNode("node1");
      croot.add(cnode);
      child = new CheckBoxTreeNode("test1");
      cnode.add(child);
      child = new CheckBoxTreeNode("test2");
      cnode.add(child);
      child = new CheckBoxTreeNode("test3");
      cnode.add(child);
      node3 = new CheckBoxTreeNode("node3");
      child = new CheckBoxTreeNode("test4");
      node3.add(child);
      cnode.add(node3);
      cnode = new CheckBoxTreeNode("node2");
      child = new CheckBoxTreeNode("prop1");
      cnode.add(child);
      child = new CheckBoxTreeNode("prop2");
      cnode.add(child);
      child = new CheckBoxTreeNode("prop3");
      cnode.add(child);
      croot.add(cnode);

      CheckBoxTree tree = new CheckBoxTree(croot);
      tree.setCellRenderer(new CheckBoxTreeCellRenderer());
      final JFrame frm = new JFrame("带复选框的树");
      frm.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); //.DO_NOTHING_ON_CLOSE);

      frm.getContentPane().add(new JScrollPane(tree));
      frm.pack();
      frm.setVisible(true);
    }
  });
}
}
