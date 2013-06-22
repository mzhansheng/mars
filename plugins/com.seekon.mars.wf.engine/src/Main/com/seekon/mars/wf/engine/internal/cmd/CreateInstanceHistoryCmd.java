package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceTraceModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 创建流程实例历史记录， 主要用在流程正常结束或流程终止操作
 * 1、创建流程实例的历史记录
 * 2、删除对应流程的wf_pass记录
 * 3、删除对应流程的wf_action记录
 * 
 */
public class CreateInstanceHistoryCmd extends AbstractCommand{

  private static final long serialVersionUID = 693644773173881416L;

  public void doExecute(Environment env) throws CommandException {
    InstanceModel instance = env.getInstance();
    int status = instance.getStatus().intValue();
    if(WFConst.INSTANCE_STATUS_TERMINATED != status && WFConst.INSTANCE_STATUS_FINISH != status){
      return;
    }
    
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    Long instanceId = instance.getInstanceId();
    
    InstanceTraceModel instanceTrace = new InstanceTraceModel();
    instanceTrace.setInstanceId(instanceId);
    instanceTrace.setPassList(runtimeDao.getInstancePass(instance.getInstanceId()));
    ActionHistoryModel actionHistory = new ActionHistoryModel();
    actionHistory.setInstanceId(instanceId);
    instanceTrace.setActionList(runtimeDao.getActionHistory(actionHistory));
    
    InstanceHistoryModel instanceHistory = new InstanceHistoryModel();
    instanceHistory.setInstanceId(instanceId);
    instanceHistory.setDescription(instance.getDescription());
    instanceHistory.setName(instance.getName());
    instanceHistory.setTemplateId(instance.getTemplateId());
    instanceHistory.setOwner(instance.getOwner());
    instanceHistory.setStartTime(instance.getStartTime());
    instanceHistory.setEndTime(DateUtil.getTime());
    instanceHistory.setStatus(new Long(status));
    instanceHistory.setInstanceTrace(instanceTrace.toXml());
    instanceHistory.setParentInstanceId(instance.getParentInstanceId());
    
    runtimeDao.createInstanceHistory(instanceHistory);//创建wf_instance_history
    
    PassModel pass = new PassModel();
    pass.setInstanceId(instanceId);
    runtimeDao.removeInstancePass(pass);//删除wf_pass
    
    ActionModel action = new ActionModel();
    action.setInstanceId(instanceId);
    runtimeDao.removeAction(action);//删除wf_action
    
  }

}
