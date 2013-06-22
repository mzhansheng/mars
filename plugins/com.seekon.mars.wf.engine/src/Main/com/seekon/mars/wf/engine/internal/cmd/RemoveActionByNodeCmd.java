package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 删除运行期间的执行动作，当进行回退、收回等操作时将执行此命令(前一节点的action)
 *
 */
public class RemoveActionByNodeCmd extends AbstractCommand {

  private static final long serialVersionUID = 620313651517534746L;

  public void doExecute(Environment env) throws CommandException {
    ActionModel action = new ActionModel();
    action.setInstanceId(env.getInstance().getInstanceId());
    action.setNodeId(env.getCurrentNode().getNodeId());
    String type = env.getContext().getAction();
    if (type.equals(WFConst.ACTION_TYPE_CALLBACK_FLOW)) {
      action.setExecutor(env.getCurrentExecutor().getCode());
    }
    else if(type.equals(WFConst.ACTION_TYPE_TERMINATED_INSTANCE)){
      action.setNodeId(null);
    }
    WFDaoFactory.getWFRuntimeDao().removeAction(action);
  }

}
