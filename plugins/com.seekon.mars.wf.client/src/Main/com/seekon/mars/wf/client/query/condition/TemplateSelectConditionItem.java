package com.seekon.mars.wf.client.query.condition;

import javax.swing.JComponent;

import com.seekon.mars.wf.client.component.TemplateSelectText;
import com.seekon.mars.wf.engine.model.BaseTemplateModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

public class TemplateSelectConditionItem extends AbstractConditionItem{
  private QueryElement queryElement;
  private TemplateSelectText templateSelectText;
  public TemplateSelectConditionItem(QueryElement queryElement,String caption){
    super(caption);
    this.queryElement=queryElement;
  }
  
  @Override
  protected JComponent createEditorComponent() {
    if(templateSelectText==null){
      templateSelectText=new TemplateSelectText(30){
        public void setText(String t) {
          super.setText(t);
          Object obj=getValue();
          if(obj!=null){
            queryElement.setTemplateId(new Long(((BaseTemplateModel)obj).getTemplateId()));
          }
          else{
            queryElement.setTemplateId(null);
          }
        }
      };
    }
    return templateSelectText;
  }

  @Override
  public Object getValue() {
    return null;
  }

  @Override
  protected void putValueToCondition() {
    
  }

  @Override
  public void setValue(Object value) {
    
  }

}
