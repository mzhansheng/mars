package com.seekon.mars.wf.engine.internal.cmd;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 
 * 流程运行期动态增加执行者
 * 增加的执行者从context上下文中获取，节点为当前的节点
 *
 */
public class AddRuntimeExecutorCmd extends AbstractCommand{

  private static final long serialVersionUID = -7318458527626862679L;
  
  public void doExecute(Environment env) throws CommandException {
    Set currentTaskSet = new HashSet();
    NodeModel currentNode = env.getCurrentNode();
    StaffModel currentExecutor = env.getCurrentExecutor();
    
    WorkflowContext ctx = env.getContext();
    List selectedNextExecutorList = ctx.getSelectedNextExecutorList();

    for (int j = 0; j < selectedNextExecutorList.size(); j++) {
      StaffModel staff = null;
      Object selectedNextExecutor = selectedNextExecutorList.get(j);
      if (selectedNextExecutor instanceof StaffModel) {
        staff = (StaffModel) selectedNextExecutor;
      } else if (selectedNextExecutor instanceof Map) {
        staff = new StaffModel((Map) selectedNextExecutor);
      }
      
      if(staff != null){
        CurrentTaskModel task = new CurrentTaskModel();
        task.setCoCode(staff.getStaffPosition().getOrg().getCompany().getCode());
        task.setCreateTime(DateUtil.getTime());
        task.setCreator(currentExecutor.getCode());
        task.setExecutor(staff.getCode());
        task.setInstanceId(ctx.getInstanceId());
        task.setNd(staff.getStaffPosition().getOrg().getNd());
        task.setNodeId(currentNode.getNodeId());
        task.setOrgCode(staff.getStaffPosition().getOrg().getCode());
        task.setOwner(staff.getCode());
        task.setPosiCode(staff.getStaffPosition().getPosition().getCode());
        task.setResponsibility(staff.getResponsibility());
        
        currentTaskSet.add(task);
      }
    }
    
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    Iterator iterator = currentTaskSet.iterator();
    while(iterator.hasNext()){
      runtimeDao.createCurrentTask((CurrentTaskModel)iterator.next());
    }
  }

}
