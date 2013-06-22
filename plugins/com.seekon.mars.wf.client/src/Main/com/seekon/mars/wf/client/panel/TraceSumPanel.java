package com.seekon.mars.wf.client.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.seekon.mars.wf.client.query.condition.AbstractConditionItem;
import com.seekon.mars.wf.client.query.condition.DateSelectConditionItem;
import com.seekon.mars.wf.client.query.condition.InstanceSelectConditionItem;
import com.seekon.mars.wf.client.query.condition.NodeSelectConditionItem;
import com.seekon.mars.wf.client.query.condition.StaffSelectConditionItem;
import com.seekon.mars.wf.client.query.condition.TemplateSelectConditionItem;
import com.seekon.mars.wf.client.tablemodel.WorkflowTableModelUtil;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.mars.wf.engine.model.runtime.SumResultRow;
import com.seekon.smartclient.component.table.JGroupableTable;
import com.seekon.smartclient.component.table.JGroupableTableHeader;

/**
 * 流程统计面板
 * @author Administrator
 *
 */
public class TraceSumPanel extends JPanel {
  private TraceSumPanel self = this;

  private JToolBar toolBar = new JToolBar();

  private JButton queryBtn;

  private JButton exportBtn;

  private JButton printBtn;

  private TraceSumConditionArea conditionArea;

  private JScrollPane conditionScrollPane;

  private JGroupableTable sumTable;

  private JScrollPane sumTableScrollPane;

  private QueryElement queryElement;

  public TraceSumPanel() {
    this.setLayout(new BorderLayout());
    queryElement = new QueryElement();
    ImageIcon icon = new ImageIcon(TraceSumPanel.class.getResource("load.gif"));
    icon.setImageObserver(self);
    JLabel loadLbl = new JLabel(icon);
    icon.setImageObserver(loadLbl);
//    JPanel loadPanel = new JPanel(new BorderLayout());
//    loadPanel.add(loadLbl, BorderLayout.CENTER);
    add(loadLbl, BorderLayout.CENTER);
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        initGUI();
      }
    });
  }

  public void initGUI() {
    initToolbar();
    conditionScrollPane = new JScrollPane();
    conditionArea = new TraceSumConditionArea(self, createConditionItems());
    conditionScrollPane.getViewport().add(conditionArea);
    sumTableScrollPane = new JScrollPane();
    sumTable = new JGroupableTable();
    sumTable.setShowCheckedColumn(false);
    sumTable.getTableRowHeader().setPreferredSize(new Dimension(50, 0));
    SwingUtilities.invokeLater(new Runnable(){

      @Override
      public void run() {
        initSumTable();
      }
      
    });
    initSumTable();
    sumTable.setPreferencesKey(TraceSumPanel.class.getName() + "_sumTable");
    sumTableScrollPane.getViewport().add(sumTable);
    JPanel panel = new JPanel(new GridBagLayout());
    panel.add(conditionScrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
      GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    panel.add(sumTableScrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
      GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    removeAll();
    add(toolBar, BorderLayout.NORTH);
    add(panel, BorderLayout.CENTER);
    self.revalidate();
    self.validate();
    self.repaint();
  }

  private void initToolbar() {
    toolBar.add(queryBtn = new JButton("查  询"));
    toolBar.add(exportBtn = new JButton("导  出"));
    toolBar.add(printBtn = new JButton("打  印"));
    queryBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleTableDisplayArea();
      }
    });

    exportBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    printBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void initSumTable() {
    sumTable.setModel(WorkflowTableModelUtil.getSumTemplateModel(queryElement));
    JGroupableTableHeader header = sumTable.getTableHeader();
    Map<String, List<String>> groups = initGroupsFieldMap();
    for (String each : groups.keySet()) {
      List<String> group = groups.get(each);
      header.addColumnGroup(each, group.toArray(new Object[group.size()]));
    }
    List<String> fields = WorkflowTableModelUtil.getObjectField(SumResultRow.class);
    Map fieldNameMap = initFiledsNameMap();
    for (String field : fields) {
      sumTable.getColumn(field).setHeaderValue(fieldNameMap.get(field));
    }
  }

  public void handleTableDisplayArea() {
    initSumTable();
    sumTable.revalidate();
    sumTable.validate();
    sumTable.repaint();
  }

  private Map<String, List<String>> initGroupsFieldMap() {
    Map<String, List<String>> groupMap = new HashMap<String, List<String>>();
    List<String> templateGroup = new ArrayList<String>();
    templateGroup.add("templateId");
    templateGroup.add("templateName");
    groupMap.put("模板", templateGroup);
    List<String> instanceGroup = new ArrayList<String>();
    instanceGroup.add("instanceId");
    instanceGroup.add("instanceName");
    groupMap.put("实例", instanceGroup);
    List<String> nodeGroup = new ArrayList<String>();
    nodeGroup.add("nodeId");
    nodeGroup.add("nodeName");
    groupMap.put("节点", nodeGroup);
    List<String> staffGroup = new ArrayList<String>();
    staffGroup.add("executor");
    staffGroup.add("executorName");
    groupMap.put("执行者", staffGroup);
    return groupMap;
  }

  private Map<String, String> initFiledsNameMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("coCode", "公司名称");
    map.put("compId", "部件名称");
    map.put("templateId", "模板ID");
    map.put("templateName", "模板名称");
    map.put("instanceId", "实例ID");
    map.put("instanceName", "实例名称");
    map.put("nodeId", "节点ID");
    map.put("nodeName", "节点名称");
    map.put("executor", "执行者ID");
    map.put("executorName", "执行者");
    map.put("arrivalTime", "发送时间");
    map.put("executeTime", "执行时间");
    map.put("dureTime", "消耗时间(小时)");
    map.put("averageTime", "业务平均消耗时间(小时)");
    map.put("nd", "年度");
    return map;
  }

  private List<AbstractConditionItem> createConditionItems() {
    List<AbstractConditionItem> condLst = new ArrayList<AbstractConditionItem>();
    condLst.add(new DateSelectConditionItem(queryElement));
    condLst.add(new TemplateSelectConditionItem(queryElement, "  模板"));
    condLst.add(new InstanceSelectConditionItem(queryElement, "  实例"));
    condLst.add(new NodeSelectConditionItem(queryElement, "    节点"));
    condLst.add(new StaffSelectConditionItem(queryElement, "执行者"));
    return condLst;
  }

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new JFrame("实例流程统计");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 720);
        frame.setLocationRelativeTo(null);
        TraceSumPanel tracePanel = new TraceSumPanel();
        frame.getContentPane().add(tracePanel);
        frame.setVisible(true);
      }
    });
  }
}
