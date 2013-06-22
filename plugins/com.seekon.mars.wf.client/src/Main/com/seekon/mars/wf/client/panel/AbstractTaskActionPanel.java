package com.seekon.mars.wf.client.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.seekon.mars.wf.client.trace.TemplateToMemory;
import com.seekon.mars.wf.client.trace.TranceGraphProcessor;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;

public abstract class AbstractTaskActionPanel extends AbstractDisplayPanel {
  protected Map<String, String> sessionMap;
  
  protected Long templateId;

  protected Long instanceId;
  
  protected String curUserId;//当前节点用户ID
  
  protected TableData tableData;
  
  protected String compId;
  
  protected Map<String,String> vMap;
  
  protected String  vDesc;

  private int treeType;

  private BusinessType businessType;
  
  protected Long selectedRebackLinkId;

  protected CompanyTreePanel companyPanel;

  protected JTextArea contentDemoTxt;

  private JButton taskActionBtn;

  private JButton cancelBtn;

  public AbstractTaskActionPanel(Map<String, String> sessionMap,
                                  TableData data, String compId, 
                                  Long templateId, Long instId,String userId, 
                                  Map<String,String> vMap,String  vDesc,
                                  int treeType, BusinessType businessObj) {
    this.sessionMap=sessionMap;
    this.tableData=data;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType;
    this.businessType = businessObj;
    super.initGUI();
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          topGraphPanel.showTraceGraph(instanceId,curUserId);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    addGraphListener();
  }

  public TranceGraphProcessor createTopGraph() throws Exception {
    return new TranceGraphProcessor(new TemplateToMemory(templateId));
  }

  public JPanel createBottomPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    companyPanel = new CompanyTreePanel(treeType, businessType);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    contentDemoTxt = new JTextArea();
    contentDemoTxt.setColumns(126);
    contentDemoTxt.setAutoscrolls(true);
    scrollPane.getViewport().add(contentDemoTxt);

    JPanel cmdPanel = new JPanel(new GridBagLayout());
    taskActionBtn = new JButton();
    taskActionBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        taskActionEvent();
      }
    });
    cancelBtn = new JButton("取消");
    cancelBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        cancelActionEvent();
      }
    });
    cmdPanel.add(taskActionBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.5, GridBagConstraints.SOUTH,
      GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    cmdPanel.add(cancelBtn, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.5, GridBagConstraints.NORTH,
      GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panel.add(new JLabel(" 单位组织："), new GridBagConstraints(0, 0, 1, 1, 0.4, 0.0, GridBagConstraints.CENTER,
      GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel.add(new JLabel(" 意见："), new GridBagConstraints(1, 0, 1, 1, 0.4, 0.0, GridBagConstraints.CENTER,
      GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel.add(companyPanel, new GridBagConstraints(0, 1, 1, 1, 0.4, 1.0, GridBagConstraints.CENTER,
      GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel.add(scrollPane, new GridBagConstraints(1, 1, 1, 1, 0.4, 1.0, GridBagConstraints.CENTER,
      GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel.add(cmdPanel, new GridBagConstraints(2, 1, 1, 2, 0.2, 1.0, GridBagConstraints.CENTER,
      GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    return panel;
  }

  public void setActionButtonText(String caption) {
    taskActionBtn.setText(caption);
  }

  public abstract void taskActionEvent();

  public abstract void cancelActionEvent();
}
