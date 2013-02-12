package com.seekon.smartclient.gui.internal;

import java.lang.reflect.Method;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;
import org.swixml.Parser;
import org.swixml.SwingEngine;

public class GuiParser extends Parser {

  public GuiParser(SwingEngine engine) {
    super(engine);
  }

  /**
   * 
   * 2013-02-03 add model child
   * @param component
   * @param model
   */
  protected void setModel(JComponent component, Object model,
    boolean lazyRefresh) {
    if (!lazyRefresh) {
      try {
        Method method = model.getClass().getDeclaredMethod("refresh", null);
        method.invoke(model, null);
      } catch (Throwable e) {
      }
    }

    if (component.getClass().isAssignableFrom(JComboBox.class)
      && model instanceof ComboBoxModel) {
      ((JComboBox) component).setModel((ComboBoxModel) model);
    } else if (component.getClass().isAssignableFrom(JTree.class)
      && model instanceof TreeModel) {
      ((JTree) component).setModel((TreeModel) model);
    } else if (component.getClass().isAssignableFrom(JTable.class)
      && model instanceof TableModel) {
      ((JTable) component).setModel((TableModel) model);
    } else if (component.getClass().isAssignableFrom(JXTreeTable.class)
      && model instanceof TreeTableModel) {
      ((JXTreeTable) component).setTreeTableModel((TreeTableModel) model);
    }
  }
}
