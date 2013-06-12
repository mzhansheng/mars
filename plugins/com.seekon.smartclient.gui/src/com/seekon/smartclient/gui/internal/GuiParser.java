package com.seekon.smartclient.gui.internal;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;
import org.swixml.Parser;
import org.swixml.SwingEngine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.gui.tags.MResourcePanel;
import com.seekon.smartclient.gui.util.ReflectUtil;

public class GuiParser extends Parser {

  public GuiParser(SwingEngine engine) {
    super(engine);
  }

  protected void setModel(JComponent component, Object model, boolean lazyRefresh) {
    if (!lazyRefresh) {
//      Method method = null;
//      try {
//        method = model.getClass().getDeclaredMethod("refresh", null);
//        method.invoke(model, null);
//      } catch (Throwable e) {
//        e.printStackTrace();
//        try{
//          method = model.getClass().getSuperclass().getDeclaredMethod("refresh", null);
//          method.invoke(model, null);
//        }catch(Throwable ee){
//          ee.printStackTrace();
//        }
//      }
      try{
        ReflectUtil.invoke(model, "refresh", null, null);
      }catch(Throwable e){
        
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
    } else if (component.getClass().isAssignableFrom(JList.class)
      && model instanceof ListModel) {
      ((JList) component).setModel((ListModel) model);
    }
  }

  @Override
  protected Component addButtonChild(Container parent, Component component,
    Object constrains, Element child) {
    boolean isAuthed = false;
    String actionId = child.getAttribute("actionId");
    if (actionId == null || actionId.trim().length() == 0) {
      isAuthed = true;
    } else {
      String resourceId = null;
      Iterator<Entry<String, Object>> iterator = this.engine.getIdMap().entrySet()
        .iterator();
      while (iterator.hasNext()) {
        Entry<String, Object> entry = iterator.next();
        if (entry.getValue() instanceof MResourcePanel) {
          resourceId = entry.getKey();
          break;
        }
      }
      if (resourceId == null || resourceId.trim().length() == 0) {
        isAuthed = true;
      } else {
        isAuthed = AuthUtil.isActionAuthorizedToUser(resourceId, actionId);
      }
    }

    if (!isAuthed) {
      component.setVisible(false);
    }

    return super.addButtonChild(parent, component, constrains, child);
  }

  @Override
  public Object parse(Document jdoc) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      return super.parse(jdoc);
    } finally {
      Thread.currentThread().setContextClassLoader(cl);
    }
  }

  @Override
  public void parse(Document jdoc, Container container) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      super.parse(jdoc, container);
    } finally {
      Thread.currentThread().setContextClassLoader(cl);
    }
  }
}
