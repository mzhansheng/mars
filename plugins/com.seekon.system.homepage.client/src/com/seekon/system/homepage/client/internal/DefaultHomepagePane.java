package com.seekon.system.homepage.client.internal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class DefaultHomepagePane extends JComponent{

  private static final long serialVersionUID = -1074331086164359560L;

  public DefaultHomepagePane() {
    super();
    init();
  }

  protected void init(){
    this.setLayout(new BorderLayout());
    
    JSplitPane split = new JSplitPane();
    split.setDividerLocation(600);
    //split.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    split.add(getAppExpressPane(), JSplitPane.LEFT);
    //split.setDividerSize(10);
    
    JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    split2.setDividerLocation(280);
    split2.add(getWorkItemsPane(), JSplitPane.TOP);
    split2.add(getMessagePane(), JSplitPane.BOTTOM);
    //split2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    //split2.setDividerSize(10);
    
    split.add(split2, JSplitPane.RIGHT);
    
    this.add(split);
  }
  
  protected JComponent getAppExpressPane(){
    JPanel appExpreePane = new JPanel(new BorderLayout());
    appExpreePane.add(new JLabel("快捷应用"), BorderLayout.NORTH);
    
    JPanel appExpressItem = new JPanel(new FlowLayout());
    appExpreePane.add(new JScrollPane(appExpressItem));
    
    return appExpreePane;
  }
  
  protected JComponent getWorkItemsPane(){
    JPanel workItemsPane = new JPanel(new BorderLayout());
    workItemsPane.add(new JLabel("待办事项"), BorderLayout.NORTH);
    workItemsPane.add(new JScrollPane());
    return workItemsPane;
  }
  
  protected JComponent getMessagePane(){
    JPanel messagePane = new JPanel(new BorderLayout());
    messagePane.add(new JLabel("系统消息"), BorderLayout.NORTH);
    messagePane.add(new JScrollPane());
    return messagePane;
  }
  
  
}
