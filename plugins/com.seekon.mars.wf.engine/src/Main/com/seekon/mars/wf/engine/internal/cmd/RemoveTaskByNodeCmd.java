package com.seekon.mars.wf.engine.internal.cmd;

import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 删除当前任务操作命令
 * 
 */
public class RemoveTaskByNodeCmd extends AbstractCommand {

  private static final long serialVersionUID = 3660715247293245183L;

  public void doExecute(Environment env) throws CommandException {
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    String type = env.getContext().getAction();
    if (type.equals(WFConst.ACTION_TYPE_CALLBACK_FLOW)) {
      List pnNodeList = env.getPreviousNodeList();
      Iterator itr = pnNodeList.iterator();
      while (itr.hasNext()) {
        CurrentTaskModel tsk = new CurrentTaskModel();
        tsk.setInstanceId(env.getInstance().getInstanceId());
        tsk.setNodeId(((NodeModel) itr.next()).getNodeId());
        runtimeDao.removeCurrentTask(tsk);
      }
    }
    else if(type.equals(WFConst.ACTION_TYPE_TERMINATED_INSTANCE)){
      CurrentTaskModel task = new CurrentTaskModel();
      task.setInstanceId(env.getInstance().getInstanceId());
      runtimeDao.removeCurrentTask(task);
    }
    else {
      NodeModel node = env.getCurrentNode();
      CurrentTaskModel task = new CurrentTaskModel();
      task.setInstanceId(env.getInstance().getInstanceId());
      task.setNodeId(node.getNodeId());
      task.setNd(DateUtil.getYear());
//      if(env.getNextLinkStaffSet().isEmpty()){//存在需要创建的下一结点的代办任务，删除本节点所有代办任务
//        task.setExecutor(env.getCurrentExecutor().getCode());
//      }
      List curTaskList=runtimeDao.getCurrentTask(task);
      if(curTaskList!=null && curTaskList.size()>0){
        String arrivalTime=((CurrentTaskModel)curTaskList.get(0)).getCreateTime();
        env.setTaskArrivalTime(arrivalTime);
      }
      runtimeDao.removeCurrentTask(task);
    }

    // TODO:增加事件

  }

}
