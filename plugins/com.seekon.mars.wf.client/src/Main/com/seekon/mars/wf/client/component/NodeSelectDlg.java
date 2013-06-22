package com.seekon.mars.wf.client.component;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.client.tablemodel.WorkflowTableModel;
import com.seekon.mars.wf.client.tablemodel.WorkflowTableModelUtil;
import com.seekon.smartclient.component.table.JGroupableTable;

public class NodeSelectDlg extends WfDialog{
  protected JButtonTextField triggerField;
  private JTextField searchTxt;
  private TableRowSorter tableSorter;
  private JGroupableTable nodeTable;
  private JButton okBtn;
  private JButton clearBtn;
  private JButton cancelBtn;
  
  public NodeSelectDlg(Dialog owner, boolean modal,
    JButtonTextField triggerField) {
    super(owner,"模板节点选择", modal);
    this.triggerField=triggerField;
    this.setSize(600,500);
    initGUI();
    this.moveToScreenCenter();
  }
  
  private void initGUI(){
    this.setLayout(new BorderLayout());
    JPanel searchPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.add(new JLabel("查找："));
    searchTxt=new JTextField(20);
    searchTxt.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        tableSorter.setRowFilter(new RowFilter(){
          @Override
          public boolean include(Entry entry) {
            int count = entry.getValueCount();
            for (int i = 0; i < count; i++) {
              if (entry.getStringValue(i).indexOf(searchTxt.getText()) >= 0) {
                return true;
              }
            }
            return false;
          }
          
        });
      }
    });
    searchPanel.add(searchTxt);
    add(searchPanel,BorderLayout.NORTH);
    JScrollPane scrollPane=new JScrollPane();
    nodeTable=new JGroupableTable();
    tableSorter = new TableRowSorter();
    SwingUtilities.invokeLater(new Runnable(){

      @Override
      public void run() {
        initTabaleModel();        
      }
      
    });
    
    nodeTable.setShowCheckedColumn(false);
    nodeTable.getTableRowHeader().setPreferredSize(new Dimension(50,0));
    scrollPane.getViewport().add(nodeTable);
    add(scrollPane,BorderLayout.CENTER);
    JPanel cmdPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
    cmdPanel.add(okBtn=new JButton("确定"));
    okBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        int selRow=nodeTable.getSelectedRow();
        int row=nodeTable.convertRowIndexToModel(selRow);
        Object obj=((WorkflowTableModel)nodeTable.getModel()).getRowObject(row);
        triggerField.setValue(obj);
        triggerField.setText(obj.toString());
        close();
      }
    });
    cmdPanel.add(clearBtn=new JButton("清除"));
    clearBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        triggerField.setText(null);
        triggerField.setValue(null);
        close();
      }
    });
    cmdPanel.add(cancelBtn=new JButton("取消"));
    cancelBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        close();
      }
      
    });
    add(cmdPanel,BorderLayout.SOUTH);
  }
  
  private void initTabaleModel(){
    Map fieldNameMap=initFiledsNameMap();
    Vector<String> v=initFields();
    DefaultTableModel tableModel=WorkflowTableModelUtil.getNodeListModel(null,v);
    nodeTable.setModel(tableModel);
    tableSorter.setModel(tableModel);
    nodeTable.setRowSorter(tableSorter);
    Iterator itr = v.iterator();
    while(itr.hasNext()){
      String key=itr.next().toString();
      nodeTable.getColumn(key).setHeaderValue(fieldNameMap.get(key));
    }
  }
  
  private Vector<String> initFields(){
    Vector<String> v=new Vector<String>();
    v.add("templateId");
    v.add("nodeId");
    v.add("name");
    v.add("description");
    v.add("type");
    v.add("businessType");
    v.add("executorsMethod");
    v.add("taskListener");
    v.add("limitExecuteTerm");
    v.add("remindExecuteTerm");
    return v;
  }
  
  private Map<String, String> initFiledsNameMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("templateId", "模板ID");
    map.put("nodeId", "节点ID");
    map.put("name", "节点名称");
    map.put("description", "描述");
    map.put("type", "节点类型");
    map.put("businessType", "节点业务类型");
    map.put("executorsMethod", "节点执行方式");
    map.put("taskListener", "任务监听器");
    map.put("limitExecuteTerm", "执行期限");
    map.put("remindExecuteTerm", "提醒期限");
    return map;
  }
}
