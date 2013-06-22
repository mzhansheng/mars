package com.seekon.mars.wf.engine.internal.strategy.assignment;

import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;

/**
 * 
 * 根据组织上下级分配任务
 *
 */
public class OrgJuniorAssignmentStrategy extends AbstractAssignmentStrategy{
  
  public OrgJuniorAssignmentStrategy(LinkModel link){
    this.link = link;
  }
  
  public void doApply(Environment env) throws WorkflowException {
    StaffModel currentExecutor = env.getCurrentExecutor();
    
    ////Assert.notNull(currentExecutor, "OrgJuniorAssignmentStrategy中的currentExecutor不能为空!");
  ////Assert.notNull(currentExecutor.getCode(), "OrgJuniorAssignmentStrategy中currentExecutor的staffCode不能为空!");
  ////Assert.notNull(currentExecutor.getStaffPosition().getOrg().getCode(), 
  ////    "OrgJuniorAssignmentStrategy中currentExecutor的orgCode不能为空!");
  ////  Assert.notNull(currentExecutor.getStaffPosition().getOrg().getCompany().getCode(), 
  ////    "OrgJuniorAssignmentStrategy中currentExecutor的coCode不能为空!");
  ////  Assert.notNull(currentExecutor.getStaffPosition().getPosition().getCode(), 
  ////    "OrgJuniorAssignmentStrategy中currentExecutor的posiCode不能为空!");
    
    List nextExecutorList = WFDaoFactory.getWFRuntimeDao().getOrgJuniorListByStaff(currentExecutor);
    for(int i = 0; i < nextExecutorList.size(); i++){
      Map executor = (Map)nextExecutorList.get(i);
      this.nextExecutorSet.add(new StaffModel(executor));
    }
  }

}
