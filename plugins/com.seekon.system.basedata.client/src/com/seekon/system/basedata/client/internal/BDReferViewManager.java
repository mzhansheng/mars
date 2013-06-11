package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.model.TreeNodeMap;
import com.seekon.smartclient.gui.tags.MDataModel;
import com.seekon.smartclient.gui.tags.MDefaultDataModel;
import com.seekon.smartclient.gui.tags.MDefaultTableModel;
import com.seekon.smartclient.gui.tags.MDefaultTreeModel;

public class BDReferViewManager extends GuiEngine {

  private JTree basedataTree;

  private JTable referViewdataTable;

  private JButton addReord;

  private JButton delReord;

  private String selectedElementId = null;
  
  public Container getDbReferContainer() {
    Container bdReferContainer = null;
    try {
      bdReferContainer = render(BDGeneralManager.class
        .getResource("BDReferViewManager.xml"));
      bdReferContainer.setVisible(true);
      initListeners();
      initDefaultData();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bdReferContainer;
  }

  private void initListeners() {
    basedataTree.addTreeSelectionListener(new TreeSelectionListener() {

      @Override
      public void valueChanged(TreeSelectionEvent e) {
        MDefaultTableModel tableModel = (MDefaultTableModel) referViewdataTable
          .getModel();
        if (e.getNewLeadSelectionPath() != null) {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
            .getNewLeadSelectionPath().getLastPathComponent();
          TreeNodeMap treeNodeMap = (TreeNodeMap) node.getUserObject();
          if (treeNodeMap == null) {
            tableModel.setDataList(new ArrayList<Map>());
          } else {
            selectedElementId = (String) treeNodeMap.get("id");
            tableModel.addParam("elementId", selectedElementId);
            tableModel.refresh();
          }
        }
      }
    });

    referViewdataTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2){
          int rowIndex = referViewdataTable.getSelectedRow();
          MDefaultTableModel model = (MDefaultTableModel) referViewdataTable.getModel();
          Map data = model.getRowData(rowIndex);
          BDReferViewManager.this.showReferViewDialog(data);
        }
      }
    });

    addReord.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Map data = new HashMap();
        data.put("ele_id", selectedElementId);
        BDReferViewManager.this.showReferViewDialog(data);
      }
    });

    delReord.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        MDefaultTableModel model = (MDefaultTableModel) referViewdataTable.getModel();
        List ids = new ArrayList();
        int[] rowIndexes = referViewdataTable.getSelectedRows();
        for(int rowIndex : rowIndexes){
          Map data = model.getRowData(rowIndex);
          ids.add(data.get("id"));
        }
        
        MDataModel mDataModel = new MDefaultDataModel();
        mDataModel.setSqlid("deleteElementReferView");
        mDataModel.addParam("ids", ids);
        mDataModel.delete();
        
        model.refresh();
        model.fireTableDataChanged();
      }
    });

  }

  private void showReferViewDialog(Map data) {
    BDReferViewDialog dialog = new BDReferViewDialog(data);
    if(dialog.getReturnValue()){
      MDefaultTableModel tableModel = (MDefaultTableModel) referViewdataTable
        .getModel();
      tableModel.refresh();
    }
  }

  private void initDefaultData() {
    Map<String, String> rootData = new HashMap<String, String>();
    rootData.put("name", "基础数据");
    rootData.put("id", "-999");
    MDefaultTreeModel treeModel = (MDefaultTreeModel) basedataTree.getModel();
    treeModel.setRoot(new DefaultMutableTreeNode(new TreeNodeMap(rootData)));
  }
}
