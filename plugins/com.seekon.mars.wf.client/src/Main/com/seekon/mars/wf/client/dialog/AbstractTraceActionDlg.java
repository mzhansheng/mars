package com.seekon.mars.wf.client.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.Map;

import com.seekon.mars.wf.client.panel.AbstractDisplayPanel;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;

public abstract class AbstractTraceActionDlg extends WfDialog {
  protected  WfDialog self=this;
  
  protected Map<String, String> sessionMap;
  
  protected TableData tableData;
  
  protected String compId;
  
  protected Long templateId;

  protected Long instanceId;
  
  protected String curUserId;

  protected int treeType;

  protected BusinessType businessType;
  
  protected Map<String,String> vMap;
  
  protected String  vDesc;

  protected AbstractDisplayPanel operationPanel;
  
  public AbstractTraceActionDlg(Dialog owner, String title, boolean modal, 
                                Map<String, String> sessionMap,
                                TableData tableData, String compId, 
                                Long templateId, Long instanceId,
                                String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, modal);
    this.sessionMap=sessionMap;
    this.tableData=tableData;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instanceId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType0;
    this.businessType = businessObj;
    initOperationPanel();
    setOperationPanel();
  }

  public AbstractTraceActionDlg(Dialog owner, String title, int treeType, 
                                Map<String, String> sessionMap,
                                TableData tableData, String compId, 
                                Long templateId, Long instanceId,
                                String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, treeType);
    this.sessionMap=sessionMap;
    this.tableData=tableData;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instanceId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType0;
    this.businessType = businessObj;
    initOperationPanel();
    setOperationPanel();
  }

  public AbstractTraceActionDlg(Frame owner, String title, 
                                boolean modal,Map<String, String> sessionMap,
                                TableData tableData,String compId, 
                                Long templateId, Long instanceId,
                                String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, modal);
    this.sessionMap=sessionMap;
    this.tableData=tableData;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instanceId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType0;
    this.businessType = businessObj;
    initOperationPanel();
    setOperationPanel();
  }

  public AbstractTraceActionDlg(Frame owner, String title, int treeType,
    Map<String, String> sessionMap,TableData tableData,
    String compId, Long templateId, Long instanceId,
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, treeType);
    this.sessionMap=sessionMap;
    this.tableData=tableData;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instanceId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType0;
    this.businessType = businessObj;
    initOperationPanel();
    setOperationPanel();
  }

  public AbstractTraceActionDlg(Window owner, String title, 
    ModalityType modalityType,Map<String, String> sessionMap,
    TableData tableData,String compId, Long templateId,Long instanceId, 
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, modalityType);
    this.sessionMap=sessionMap;
    this.tableData=tableData;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instanceId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType0;
    this.businessType = businessObj;
    initOperationPanel();
    setOperationPanel();
  }

  public AbstractTraceActionDlg(Window owner, String title,
    Map<String, String> sessionMap,TableData tableData,
    String compId, Long templateId, Long instanceId, 
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0,BusinessType businessObj) {
    super(owner, title);
    this.sessionMap=sessionMap;
    this.tableData=tableData;
    this.compId=compId;
    this.templateId = templateId;
    this.instanceId = instanceId;
    this.curUserId = userId;
    this.vMap=vMap;
    this.vDesc=vDesc;
    this.treeType = treeType0;
    this.businessType = businessObj;
    initOperationPanel();
    setOperationPanel();
  }

  private void setOperationPanel() {
    this.setLayout(new BorderLayout());
    this.add(operationPanel, BorderLayout.CENTER);
    this.setSize(1000,700);
    this.moveToScreenCenter();
  }

  public abstract void initOperationPanel();

}
