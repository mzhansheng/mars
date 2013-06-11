package com.seekon.smartclient.component.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class TreePopupUtil {

  public static void addPopup(final JTree tree, final JPopupMenu popup) {
    tree.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
          showMenu(e);
        }
      }

      public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
          showMenu(e);
        }
      }

      private void showMenu(MouseEvent e) {
        TreePath[] paths = tree.getSelectionPaths();
        List pathsList = paths == null || paths.length == 0 ? Collections.EMPTY_LIST
          : Arrays.asList(paths);
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());

        if (path == null) {
          tree.setSelectionPath(null);
        } else if (path != null && pathsList.contains(path)) {

        } else if (path != null && !pathsList.contains(path)) {
          tree.setSelectionPath(path);
        }

        popup.show(e.getComponent(), e.getX(), e.getY());
      }
    });
  }
}
