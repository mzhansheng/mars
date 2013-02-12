package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.RowFilter;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;

import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.model.TreeNodeMap;
import com.seekon.smartclient.gui.tags.MDefaultComboBoxModel;
import com.seekon.smartclient.gui.tags.MDefaultTableModel;
import com.seekon.smartclient.gui.tags.MDefaultTreeModel;
import com.seekon.smartclient.gui.tags.MSearchField;

public class BDGeneralManager extends GuiEngine {

  private Container dbGeneralContainer = null;

  static BDGeneralManager dbGeneralManager = new BDGeneralManager();

  private JComboBox bdElementBox;

  private JTree basedataTree;

  private JTable basedataTable;

  private JCheckBox isUsedCheckBox;
  
  private MSearchField searchField;
  
  private JButton searchButton;
  
  private JButton addBasedataButton;
  
  private JButton delBasedataButton;
  
  private JButton addReord;
  
  private JButton delReord;
  
  private BDGeneralManager() {
    super();
    try {
      dbGeneralContainer = render(BDGeneralManager.class
        .getResource("BDGeneralListpage.xml"));
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Container getDbGeneralContainer() {
    dbGeneralContainer.setVisible(true);
    return dbGeneralContainer;
  }

  private void initListeners() {
    bdElementBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          Map selectedItem = (Map) bdElementBox.getSelectedItem();

          MDefaultTreeModel treeModel = (MDefaultTreeModel) basedataTree.getModel();
          treeModel.addParam("physical_table", selectedItem.get("physical_table"));
          treeModel.addParam("parent_id", null);
          treeModel.setRoot(new DefaultMutableTreeNode(new TreeNodeMap(selectedItem)));
        }
      }
    });

    basedataTree.addTreeSelectionListener(new TreeSelectionListener() {

      @Override
      public void valueChanged(TreeSelectionEvent e) {
        MDefaultTableModel tableModel = (MDefaultTableModel) basedataTable
          .getModel();
        if (e.getNewLeadSelectionPath() != null) {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
            .getNewLeadSelectionPath().getLastPathComponent();
          TreeNodeMap treeNodeMap = (TreeNodeMap) node.getUserObject();
          if (treeNodeMap == null) {
            tableModel.setDataList(new ArrayList<Map>());
          } else {
            tableModel.setDataList(treeNodeMap.getChildren());
          }
        }
      }
    });
    
    final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(basedataTable.getModel());  
    basedataTable.setRowSorter(sorter);
    
    searchButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        isUsedCheckBox.isSelected();
        sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));  
        sorter.setSortKeys(null); 
      }
    });
    
    addBasedataButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        BDGeneralEditPage page = new BDGeneralEditPage();
        if(page.getReturnValue()){
          MDefaultComboBoxModel model = (MDefaultComboBoxModel) bdElementBox.getModel();
          model.refresh();
        }
      }
    });
    
    delBasedataButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(bdElementBox, "确认删除选择的基础数据？", "请确认",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
          MDefaultComboBoxModel model = (MDefaultComboBoxModel) bdElementBox.getModel();
          model.removeElementAt(bdElementBox.getSelectedIndex());
          ///model.delete();
        }
      }
    });
    
    addReord.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        MDefaultTableModel tableModel = (MDefaultTableModel) basedataTable
          .getModel();
        tableModel.addRow(new HashMap<String, Object>());
      }
    });
    
    delReord.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        MDefaultTableModel tableModel = (MDefaultTableModel) basedataTable
          .getModel();
        int[] rows = basedataTable.getSelectedRows();
        if(rows == null){
          return;
        }
        for(int row = rows.length - 1; row >= 0; row--){
          tableModel.removeRow(rows[row]);
        }
      }
    });
  }

  public static void main(String[] args) {
    BDGeneralManager.dbGeneralManager.getDbGeneralContainer();
  }
}
