package com.seekon.mars.wf.engine.internal.strategy.assignment;

import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;

/**
 * 
 * 根据人工选择来进行任务分配，人工选择的执行者信息从上下文中获取
 *
 */
public class AnyiAssignmentStrategy extends AbstractAssignmentStrategy{

  public AnyiAssignmentStrategy(LinkModel link){
    super();
    this.link = link;
  }
  
  public void doApply(Environment env) throws WorkflowException {    
    WorkflowContext ctx = env.getContext();
    List selectedNextExecutorList = ctx.getSelectedNextExecutorList();//人工选择的人，添加到env中

    for (int j = 0; j < selectedNextExecutorList.size(); j++) {
      StaffModel staff = null;
      Object selectedNextExecutor = selectedNextExecutorList.get(j);
      if (selectedNextExecutor instanceof StaffModel) {
        staff = (StaffModel) selectedNextExecutor;
      } else if (selectedNextExecutor instanceof Map) {
        staff = new StaffModel((Map) selectedNextExecutor);
      }
      
      if(staff != null){
        this.nextExecutorSet.add(staff);
      }
    }
  }
}
