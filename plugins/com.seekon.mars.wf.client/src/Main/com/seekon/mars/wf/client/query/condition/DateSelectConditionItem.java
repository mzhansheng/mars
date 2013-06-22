package com.seekon.mars.wf.client.query.condition;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.smartclient.component.JDateTextField;

public class DateSelectConditionItem extends AbstractConditionItem{
  private JDateTextField startDateText;
  
  private JDateTextField endDateText;
  
  public DateSelectConditionItem(QueryElement queryElement){
    super();
    this.queryElement=queryElement;
    addComponent();
  }
  
  private void addComponent(){
    setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
    JLabel startLbl=new JLabel("开始日期：");
    startLbl.setHorizontalAlignment(JLabel.RIGHT);
    add(startLbl);
    if(startDateText==null)startDateText=new JDateTextField(){
      public void setText(String t) {
        super.setText(t);
        String sDate=startDateText.getText();
        if(sDate!=null && sDate.length()>0){
          queryElement.setStartTime(startDateText.getText());
        }
        else{
          queryElement.setStartTime(null);
        }
      }
    };
    add(startDateText);
    
    JLabel endLbl=new JLabel("结束日期：");
    add(endLbl);
    if(endDateText==null)endDateText=new JDateTextField(){
      public void setText(String t) {
        super.setText(t);
        String eDate=endDateText.getText();
        if(eDate!=null && eDate.length()>0){
          queryElement.setEndTime(eDate);
        }
        else{
          queryElement.setEndTime(null);
        }
      }
    };
    add(endDateText);
  }
  
  @Override
  protected JComponent createEditorComponent() {
    return null;
  }
  
  @Override
  protected void putValueToCondition() {
    queryElement.setStartTime(startDateText.getText());
    queryElement.setEndTime(endDateText.getText());
  }

  @Override
  public Object getValue() {

    return null;
  }

  @Override
  public void setValue(Object value) {
    
  }

}
