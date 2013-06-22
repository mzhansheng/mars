package com.seekon.mars.wf.client.query.condition;

import javax.swing.JComponent;

import com.seekon.mars.wf.client.component.StaffSelectText;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.mars.wf.model.Staff;

public class StaffSelectConditionItem extends AbstractConditionItem{
  private QueryElement queryElement;
  private StaffSelectText staffSelectText;
  public StaffSelectConditionItem(QueryElement queryElement,String caption){
    super(caption);
    this.queryElement=queryElement;
  }
  
  @Override
  protected JComponent createEditorComponent() {
    if(staffSelectText==null){
      staffSelectText=new StaffSelectText(30){
        public void setText(String t) {
          super.setText(t);
          Object obj=getValue();
          if(obj!=null){
            queryElement.setStaffId(((Staff)obj).getCode());
          }
          else{
            queryElement.setStaffId(null);
          }
        }
      };
    }
    return staffSelectText;
  }

  @Override
  protected void putValueToCondition() {
    String strStaff=staffSelectText.getText();
    queryElement.setStaffId(strStaff);
  }

  @Override
  public Object getValue() {
    return null;
  }

  @Override
  public void setValue(Object value) {
    
  }
}
