package com.seekon.mars.wf.client.query.condition;

import javax.swing.JComponent;

import com.seekon.mars.wf.client.component.NodeSelectText;
import com.seekon.mars.wf.engine.model.BaseNodeModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

public class NodeSelectConditionItem extends AbstractConditionItem{
  private QueryElement queryElement;
  private NodeSelectText nodeSelectText;
  public NodeSelectConditionItem(QueryElement queryElement,String caption){
    super(caption);
    this.queryElement=queryElement;
  }
  
  @Override
  protected JComponent createEditorComponent() {
    if(nodeSelectText==null){
      nodeSelectText=new NodeSelectText(30){
        public void setText(String t) {
          super.setText(t);
          Object obj=getValue();
          if(obj!=null){
            queryElement.setNodeId(new Long(((BaseNodeModel)obj).getNodeId()));
          }
          else{
            queryElement.setNodeId(null);
          }
        }
      };
    }
    return nodeSelectText;
  }

  @Override
  protected void putValueToCondition() {
    String strNode=nodeSelectText.getText();
    queryElement.setNodeId(new Long(strNode));
  }

  @Override
  public Object getValue() {
    
    return null;
  }

  @Override
  public void setValue(Object value) {
  
  }
}
