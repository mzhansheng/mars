package com.seekon.smartclient.component.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class JCheckTree extends JTree {

  public enum CheckState {
    /**
     * 已经勾选
     */
    CHECKED,

    /**
     * 未勾选
     */
    UNCHECKED,

    /**
     * 未知，如果一个节点的子节点部分选定，则状态说是UNKNOWN
     */
    UNKNOWN
  }

  /**
   * 保存节点对应状态的map对象
   */
  private Map<TreePath, CheckState> checkStateMap = new HashMap<TreePath, CheckState>();

  public static class CheckBoxTreeCellRenderer extends DefaultTreeCellRenderer {

    protected TristateCheckBox checkBox = new TristateCheckBox();

    private JComponent parent = new JPanel(new BorderLayout());

    public CheckBoxTreeCellRenderer() {
      checkBox.setOpaque(false);
      parent.setOpaque(false);
      parent.add(checkBox, BorderLayout.WEST);
      parent.add(this);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
      boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
        hasFocus);

      JCheckTree jCheckTree = (JCheckTree) tree;

      switch (jCheckTree.getCheckState(tree.getPathForRow(row))) {
      case CHECKED:
        checkBox.setState(TristateCheckBox.SELECTED);
        break;
      case UNKNOWN:
        checkBox.setState(TristateCheckBox.DONT_CARE);
        break;
      default:
        checkBox.setState(TristateCheckBox.NOT_SELECTED);
      }

      setIcon(null);
      Dimension preferredSize = getPreferredSize();
      preferredSize.width += checkBox.getPreferredSize().width;
      parent.setPreferredSize(preferredSize);
      checkBox.updateUI();
      return parent;
    }
  }

  public JCheckTree() {
    setCellRenderer(new CheckBoxTreeCellRenderer());
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
          TreePath path = getPathForLocation(e.getX(), e.getY());
          if (path != null) {
            Rectangle rect = getPathBounds(path);

            rect.width = rect.height;

            rect.y -= 2;
            rect.height += 4;
            rect.x -= 2;
            rect.width += 4;

            if (rect.contains(e.getPoint())) {
              Object node = path.getLastPathComponent();
              if (node != null) {
                CheckState state = getCheckState(path);
                if (state == CheckState.UNCHECKED) {
                  state = CheckState.CHECKED;
                } else {
                  state = CheckState.UNCHECKED;
                }
                setCheckeState(path, state);
              }
            }
          }
        }
      }

      @Override
      public void mouseClicked(MouseEvent e) {

      }
    });
    addPropertyChangeListener(new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        if (TREE_MODEL_PROPERTY.equals(evt.getPropertyName())
          && !evt.getOldValue().equals(evt.getNewValue())) {
          checkStateMap.clear();
        }
      }
    });
  }

  /**
   * 选定指定的节点
   * @param node
   * @param state 指定勾选状态
   */
  private void setCheckeState0(TreePath path, CheckState state) {
    if (state == CheckState.UNCHECKED) {
      checkStateMap.remove(path);
    } else {
      checkStateMap.put(path, state);
    }
  }

  /**
   * 设置是指定path的选定状态
   * @param path
   * @param state
   */
  public void setCheckeState(TreePath path, CheckState state) {
    if (path == null)
      throw new IllegalArgumentException();
    Object node = path.getLastPathComponent();
    CheckState oldCheckState = getCheckState(path);
    setCheckeState0(path, state);
    Rectangle pathBounds = getPathBounds(path);
    if (pathBounds != null)
      repaint(pathBounds);

    //不是叶子节点
    if (!getModel().isLeaf(node)/* && state != CheckState.UNKNOWN*/) {
      int count = getModel().getChildCount(node);
      for (int i = 0; i < count; i++) {
        Object o = getModel().getChild(node, i);

        setCheckeState(createPath(path, o), state);
      }
    }
    if (oldCheckState != state) {
      fireCheckStateChanged(new TreeCheckEvent(this, node, oldCheckState, state));
    }

    for (TreePath p = path.getParentPath(); p != null;) {
      updatePathCheckState(p);
      p = p.getParentPath();
    }
  }

  private TreePath createPath(TreePath parentPath, Object comp) {
    Object[] pathArray = new Object[parentPath.getPathCount() + 1];
    for (int j = 0; j < parentPath.getPathCount(); j++) {
      pathArray[j] = parentPath.getPathComponent(j);
    }
    pathArray[pathArray.length - 1] = comp;
    return new TreePath(pathArray);
  }

  /**
   * 更新指定path的钩选状态
   * @param path
   */
  private void updatePathCheckState(TreePath path) {
    Object lastPathComponent = path.getLastPathComponent();
    int count = getModel().getChildCount(lastPathComponent);

    int checkCount = 0; //子节点钩选的总数
    int unCheckCount = 0;//未钩选总数
    for (int i = 0; i < count; i++) {
      Object o = getModel().getChild(lastPathComponent, i);
      if (getCheckState(createPath(path, o)) == CheckState.CHECKED) {
        checkCount++;
      } else if (getCheckState(createPath(path, o)) == CheckState.UNCHECKED) {
        unCheckCount++;
      }
    }

    CheckState oldState = getCheckState(path);
    CheckState newState;
    if (checkCount == count) {
      //全部选择
      newState = CheckState.CHECKED;
    } else if (unCheckCount == count) {
      //未选
      newState = CheckState.UNCHECKED;
    } else {
      //选择了一部分
      newState = CheckState.UNKNOWN;
    }

    if (oldState != newState) {
      setCheckeState0(path, newState);
      Rectangle pathBounds = getPathBounds(path);
      if (pathBounds != null) {
        repaint(pathBounds);
      }
      fireCheckStateChanged(new TreeCheckEvent(this, lastPathComponent, oldState,
        newState));
    }
  }

  /**
   * 添加钩选状态监听器
   * @param l
   */
  public void addTreeCheckListener(TreeCheckListener l) {
    listenerList.add(TreeCheckListener.class, l);
  }

  /**
   * 移除钩选状态监听器
   * @param l
   */
  public void removeTreeCheckListener(TreeCheckListener l) {
    listenerList.remove(TreeCheckListener.class, l);
  }

  private CheckState getCheckState(TreePath path) {
    CheckState s = checkStateMap.get(path);
    return s == null ? CheckState.UNCHECKED : s;
  }

  /**
   * 引发钩选事件改变
   * @param e
   */
  protected void fireCheckStateChanged(TreeCheckEvent e) {
    TreeCheckListener[] listeners = listenerList
      .getListeners(TreeCheckListener.class);
    for (TreeCheckListener l : listeners) {
      l.checkStateChanged(e);
    }
  }

  /**
   * @return 已经钩选择对象数组
   */
  public Object[] getCheckedNodes() {
    List l = new ArrayList();
    for (Map.Entry<TreePath, CheckState> e : checkStateMap.entrySet()) {
      if (e.getValue() == CheckState.CHECKED) {
        l.add(e.getKey().getLastPathComponent());
      }
    }
    return l.toArray();
  }

  /**
   * @return 已经钩选择TreePath对象数组
   */
  public TreePath[] getCheckedPaths() {
    List<TreePath> l = new ArrayList();
    for (Map.Entry<TreePath, CheckState> e : checkStateMap.entrySet()) {
      if (e.getValue() == CheckState.CHECKED) {
        l.add(e.getKey());
      }
    }
    return l.toArray(new TreePath[l.size()]);
  }

}
