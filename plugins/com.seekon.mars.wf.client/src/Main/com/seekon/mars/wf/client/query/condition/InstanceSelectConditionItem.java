package com.seekon.mars.wf.client.query.condition;

import javax.swing.JComponent;

import com.seekon.mars.wf.client.component.InstanceSelectText;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

public class InstanceSelectConditionItem extends AbstractConditionItem{
  private InstanceSelectText instanceSelectText;
  public InstanceSelectConditionItem(QueryElement queryElement,String caption){
    super(caption);
    this.queryElement=queryElement;
  }
  
  @Override
  protected JComponent createEditorComponent() {
    if (instanceSelectText == null){
      instanceSelectText=new InstanceSelectText(30){
        public void setText(String t) {
          super.setText(t);
          Object obj=getValue();
          if(obj!=null){
            queryElement.setInstanceId(new Long(((InstanceModel)obj).getInstanceId()));
          }
          else{
            queryElement.setInstanceId(null);
          }
        }
      };
    }
    return instanceSelectText;
  }

  @Override
  protected void putValueToCondition() {
    String strInstance=instanceSelectText.getText();
    queryElement.setInstanceId(new Long(strInstance));
  }

  @Override
  public Object getValue() {

    return null;
  }

  @Override
  public void setValue(Object value) {
    
  }

}
