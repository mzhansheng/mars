package com.seekon.mars.wf.engine.internal.strategy.assignment;

import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.BusinessJuniorModel;
import com.seekon.mars.wf.engine.model.runtime.TableData;

/**
 * 
 * 根据业务上下级分配任务
 *
 */
public class BusinessJuniorAssignmentStrategy extends AbstractAssignmentStrategy{

  public BusinessJuniorAssignmentStrategy(LinkModel link){
    this.link = link;
  }
  
  public void doApply(Environment env) throws WorkflowException {
    StaffModel currentExecutor = env.getCurrentExecutor();
    
    ////Assert.notNull(currentExecutor, "BusinessJuniorAssignmentStrategy中的currentExecutor不能为空!");
    ////Assert.notNull(currentExecutor.getCode(), "BusinessJuniorAssignmentStrategy中currentExecutor的staffCode不能为空!");
    //Assert.notNull(currentExecutor.getEmpCode(), "BusinessJuniorAssignmentStrategy中currentExecutor的empCode不能为空!");
    ////Assert.notNull(currentExecutor.getStaffPosition().getOrg().getCode(), 
    ////  "BusinessJuniorAssignmentStrategy中currentExecutor的orgCode不能为空!");
    ////Assert.notNull(currentExecutor.getStaffPosition().getOrg().getCompany().getCode(), 
    ////  "BusinessJuniorAssignmentStrategy中currentExecutor的coCode不能为空!");
    ////Assert.notNull(currentExecutor.getStaffPosition().getPosition().getCode(), 
    ////  "BusinessJuniorAssignmentStrategy中currentExecutor的posiCode不能为空!");
    
    TableData tableData = env.getContext().getTableData();
    List businessJuniorList = WFDaoFactory.getWFRuntimeDao().getBusinessJuniorListByStaff(currentExecutor);
    Iterator iterator = businessJuniorList.iterator();
    while(iterator.hasNext()){
      BusinessJuniorModel businessJunior = (BusinessJuniorModel)iterator.next();
      if(!businessJunior.containJuniorStaff(currentExecutor) || !businessJunior.isBelow(tableData)){
        continue;
      }
      
      nextExecutorSet.addAll(businessJunior.transSuperStaffList());
    }
  }

}
