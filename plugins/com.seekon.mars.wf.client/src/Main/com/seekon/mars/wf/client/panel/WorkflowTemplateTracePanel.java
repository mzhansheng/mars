package com.seekon.mars.wf.client.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableRowSorter;

import org.jgraph.graph.BasicMarqueeHandler;

import com.seekon.mars.wf.client.tablemodel.WorkflowTableModel;
import com.seekon.mars.wf.client.tablemodel.WorkflowTableModelUtil;
import com.seekon.mars.wf.client.trace.TemplateToMemory;
import com.seekon.mars.wf.client.trace.TraceGraphCell;
import com.seekon.mars.wf.client.trace.TraceGraphEdge;
import com.seekon.mars.wf.client.trace.TranceGraphProcessor;
import com.seekon.mars.wf.client.trace.unit.GraphUnit;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.smartclient.component.table.JGroupableTable;

/**
 * 流程跟踪面板
 * @author Administrator
 *
 */
public class WorkflowTemplateTracePanel extends AbstractDisplayPanel {
  private Long templateId;

  private TableRowSorter tableSort;
  
  private JGroupableTable historyTable;

  public WorkflowTemplateTracePanel(Long templateId) {
    this.templateId = templateId;
    super.initGUI();
    try {
      this.topGraphPanel.showTemplateGraph();
    } catch (IOException e) {
      e.printStackTrace();
    }
    addGraphListener();
  }

  private void initHistoryTable(Long nodeId) {
    tableSort=new TableRowSorter();
    WorkflowTableModel tableModel = WorkflowTableModelUtil.getTemplateHistoryTableModel(templateId, nodeId);
    historyTable.setModel(tableModel);
    tableSort.setModel(tableModel);
    historyTable.setRowSorter(tableSort);
    List<String> fields = WorkflowTableModelUtil.getObjectField(ActionModel.class);
    Map fieldNameMap = initFiledsNameMap();
    for (String field : fields) {
      Object value = fieldNameMap.get(field);
      if(value != null){
        historyTable.getColumn(field).setHeaderValue(value);
      }
    }
  }

  @Override
  public JPanel createBottomPanel() {
    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getViewport().add(historyTable = new JGroupableTable());
    historyTable.setShowCheckedColumn(false);
    historyTable.setPreferencesKey(WorkflowTemplateTracePanel.class.getName() + "_historyTable");
    historyTable.getTableRowHeader().setPreferredSize(new Dimension(50, 0));
    initHistoryTable(null);
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    return tablePanel;
  }

  @Override
  public TranceGraphProcessor createTopGraph() throws Exception {
    return new TranceGraphProcessor(new TemplateToMemory(templateId));
  }


  public void addGraphListener() {
    topGraphPanel.getTraceGraph().addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
          Object[] obj = BasicMarqueeHandler.getGraphForEvent(e).getSelectionCells();
          if (obj.length != 0) {
            Object nodeObj = obj[obj.length - 1];
            if (nodeObj instanceof TraceGraphCell) {
              TraceGraphCell nodeCell = (TraceGraphCell) nodeObj;
              GraphUnit unit = nodeCell.getUserGraphUnit();
              NodeModel nodeModel = (NodeModel) unit.getModel();
              initHistoryTable(nodeModel.getNodeId());
            } else if(nodeObj instanceof TraceGraphEdge){
              //添加双击edge时时间
              
            }
          } else {
            initHistoryTable(null);

          }
          historyTable.revalidate();
          historyTable.repaint();
        }
      }
    });
  }

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new JFrame("流程模板");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 720);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(new WorkflowTemplateTracePanel(new Long(2023)));
        frame.setVisible(true);
      }
    });
  }
}
