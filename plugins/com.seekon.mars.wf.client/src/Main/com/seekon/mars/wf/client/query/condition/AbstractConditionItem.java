package com.seekon.mars.wf.client.query.condition;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.seekon.mars.wf.client.panel.TraceSumConditionArea;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

public abstract class AbstractConditionItem extends JPanel{
  private JLabel conditionLbl;//条件标题
  protected JComponent editorComponent;
  private TraceSumConditionArea conditionArea;
  protected QueryElement queryElement;
  
  public AbstractConditionItem(){
    queryElement=new QueryElement();
  }
  
  public AbstractConditionItem(String caption){
    this();
    addComponent(caption);
  }
  
  private void addComponent(String caption) {
    setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
    conditionLbl=new JLabel(caption + "：");
    conditionLbl.setHorizontalAlignment(JLabel.RIGHT);
    add(conditionLbl);
    editorComponent = createEditorComponent();
    add(editorComponent);
  }
  
  public TraceSumConditionArea getConditionArea(){
    return this.conditionArea;
  }
  
  public void setConditionArea(TraceSumConditionArea conditionArea){
    this.conditionArea=conditionArea;
  }
  
  protected void fireSearch() {
    if (conditionArea != null)
      conditionArea.doSearch();
  }
  
  public QueryElement getQueryElement() {
    return queryElement;
  }

  public void setQueryElement(QueryElement queryElement) {
    this.queryElement = queryElement;
  }
  
  public JComponent getEditorComponent() {
    return editorComponent;
  }
  
  protected abstract JComponent createEditorComponent();
  
  protected abstract void putValueToCondition();
  
  public abstract void setValue(Object value);

  public abstract Object getValue();
  
//  public void addValueChangeListener(ValueChangeListener l) {
//    this.listenerList.add(ValueChangeListener.class, l);
//  }
//
//  public void removeValueChangeListener(ValueChangeListener l) {
//    this.listenerList.remove(ValueChangeListener.class, l);
//  }
//
//  protected void fireValueChanged() {
//    ValueChangeEvent e = null;
//    ValueChangeListener[] listeners = listenerList
//      .getListeners(ValueChangeListener.class);
//    for (ValueChangeListener l : listeners) {
//      if (e == null) {
//        e = new ValueChangeEvent(this);
//      }
//      l.valueChanged(e);
//    }
//  }

}
