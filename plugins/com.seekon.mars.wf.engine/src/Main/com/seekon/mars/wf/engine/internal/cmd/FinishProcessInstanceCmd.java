package com.seekon.mars.wf.engine.internal.cmd;

import java.util.Iterator;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 流程实例正常结束，此command只会由引擎自身调用，外部应用程序不能调用它。
 * 正常结束必须是所有下一执行路径的下一节点为结束节点，而且此流程实例上没有代办任务
 * TODO：与InstanceManagerCmd合并
 * 
 */
public class FinishProcessInstanceCmd extends AbstractCommand {

  private static final long serialVersionUID = 1094424936145708857L;

  public void doExecute(Environment env) throws CommandException {

    NodeModel endNode = env.getTemplate().getEndNode();

    Iterator iterator = env.getNextLinkStaffSet().iterator();
    while (iterator.hasNext()) {
      LinkStaffModel linkStaff = (LinkStaffModel) iterator.next();
      if (!linkStaff.getLink().getNextNode().equals(endNode)) {
        return;
      }
    }

    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    int count = runtimeDao.getCurrentTaskCount(env.getContext().getInstanceId(), null, null);
    if (count > 0) {
      return;
    }
    
    runtimeDao.removeInstanceById(env.getInstance().getInstanceId());
    
    env.getInstance().setStatus(new Long(WFConst.INSTANCE_STATUS_FINISH));
  }

}
