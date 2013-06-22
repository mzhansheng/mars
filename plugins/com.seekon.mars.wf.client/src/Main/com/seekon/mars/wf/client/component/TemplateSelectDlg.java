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

public class TemplateSelectDlg extends WfDialog{
  protected JButtonTextField triggerField;
  private JTextField searchTxt;
  private TableRowSorter tableSorter;
  private JGroupableTable templateTable;
  private JButton okBtn;
  private JButton clearBtn;
  private JButton cancelBtn;
  
  public TemplateSelectDlg(Dialog owner, boolean modal,
    JButtonTextField triggerField) {
    super(owner,"模板选择", modal);
    this.triggerField=triggerField;
    this.setSize(600,500);
    SwingUtilities.invokeLater(new Runnable(){

      @Override
      public void run() {
        initGUI();
      }
      
    });
    
    this.moveToScreenCenter();
  }
  
  private void initGUI(){
    this.setLayout(new BorderLayout());
    JPanel searchPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.add(new JLabel("查找："));
    searchTxt=new JTextField(20);
    searchPanel.add(searchTxt);
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
    add(searchPanel,BorderLayout.NORTH);
    JScrollPane scrollPane=new JScrollPane();
    templateTable=new JGroupableTable();
    tableSorter = new TableRowSorter();
    templateTable.setShowCheckedColumn(false);
    templateTable.getTableRowHeader().setPreferredSize(new Dimension(50,0));
    SwingUtilities.invokeLater(new Runnable(){

      @Override
      public void run() {
        initTabaleModel();
      }
      
    });
    scrollPane.getViewport().add(templateTable);
    add(scrollPane,BorderLayout.CENTER);
    JPanel cmdPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
    cmdPanel.add(okBtn=new JButton("确定"));
    okBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        int selRow=templateTable.getSelectedRow();
        int row=templateTable.convertRowIndexToModel(selRow);
        Object obj=((WorkflowTableModel)templateTable.getModel()).getRowObject(row);
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
    DefaultTableModel tableModel=WorkflowTableModelUtil.getTemplateListModel(null,v);
    templateTable.setModel(tableModel);
    tableSorter.setModel(tableModel);
    templateTable.setRowSorter(tableSorter);
    Iterator itr = v.iterator();
    while(itr.hasNext()){
      String key=itr.next().toString();
      templateTable.getColumn(key).setHeaderValue(fieldNameMap.get(key));
    }
  }
  
  private Vector<String> initFields(){
    Vector<String> v=new Vector<String>();
    v.add("templateId");
    v.add("name");
    v.add("description");
    v.add("templateType");
    v.add("version");
    v.add("startTime");
    v.add("expireTime");
    v.add("createTime");
    v.add("createStaffId");
    v.add("active");
    return v;
  }
  
  private Map<String, String> initFiledsNameMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("templateId", "模板ID");
    map.put("name", "模板名称");
    map.put("description", "描述");
    map.put("templateType", "模板类型");
    map.put("version", "版本");
    map.put("startTime", "开始时间");
    map.put("expireTime", "过期时间");
    map.put("createTime", "创建时间");
    map.put("createStaffId", "创建者");
    map.put("active", "是否活动");
    return map;
  }
}
