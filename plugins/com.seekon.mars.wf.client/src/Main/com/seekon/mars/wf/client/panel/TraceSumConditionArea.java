package com.seekon.mars.wf.client.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.seekon.mars.wf.client.query.condition.AbstractConditionItem;

public class TraceSumConditionArea extends JComponent{
  private TraceSumConditionArea self=this;
  
  private List<AbstractConditionItem> conditionItemList=new ArrayList<AbstractConditionItem>();
  
  private TraceSumPanel traceSumPanel;
  
  private JPanel panel;
  
  public TraceSumConditionArea(TraceSumPanel traceSumPanel,List<AbstractConditionItem> conditionItemList){
    this.traceSumPanel=traceSumPanel;
    this.conditionItemList=conditionItemList;
    initGUI();
  }
  
  public void initGUI(){
    setLayout(new BorderLayout());
    if (panel == null) {
      panel = new JPanel(new GridBagLayout());
    }
    refreshConditionArea();
    this.add(panel, BorderLayout.CENTER);
  }
  
  public void refreshConditionArea(){
    for (AbstractConditionItem item : conditionItemList) {
      item.setConditionArea(this);
    }
    panel.removeAll();
    int row = 1;
    int col = 1;
    for (int i = 0; i < conditionItemList.size(); i++) {
      AbstractConditionItem comp = (AbstractConditionItem) conditionItemList.get(i);
      panel.add(comp, new GridBagConstraints(col - 1, row - 1, 1, 1, 0.3, 1.0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
      if (col % 3 == 0) {
        row++;
        col = 1;
      } else {
        col++;
      }
    }
    this.invalidate();
    this.validate();
    this.repaint();
  }
  
  public TraceSumPanel getTraceSumPanel() {
    return traceSumPanel;
  }

  public void setTraceSumPanel(TraceSumPanel traceSumPanel) {
    this.traceSumPanel = traceSumPanel;
  }

  public AbstractConditionItem[] getShowingConditionItems() {
    return conditionItemList.toArray(new AbstractConditionItem[conditionItemList.size()]);
  }

  //  /**
  //   * 执行搜索
  //   */
  //  public void doSearch() {
  //    doSearch(getShowingConditionItems());
  //  }

  public void doSearch() {
    if (traceSumPanel != null) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          traceSumPanel.handleTableDisplayArea();
        }
      });
    }
  }
  
  public void conditionItemList(List<AbstractConditionItem> conditionItemList){
    this.conditionItemList=conditionItemList;
  }
}
