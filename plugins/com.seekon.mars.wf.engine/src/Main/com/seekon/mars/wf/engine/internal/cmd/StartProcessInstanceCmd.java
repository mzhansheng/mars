package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.runtime.DraftModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.engine.util.WFConst;


/**
 * 
 * 启动流程实例
 * 1、创建工作流实例记录
 * 2、删除草稿记录
 * 3、更新业务数据中的instanceId为新生成的流程实例id
 * 4、发送流程启动事件
 * 
 */
public class StartProcessInstanceCmd extends AbstractCommand{

  private static final long serialVersionUID = -8887475465486926856L;

  public void doExecute(Environment env) {
    ////Assert.notNull(env.getContext().getTableData().getId(), "工作流上下文中tableData数据对象的id不能为空!");
    
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    
    if(WFConst.ACTION_TYPE_START_INSTANCE.equals(env.getContext().getAction())){
      DraftModel draft = runtimeDao.getDraftById(env.getContext().getInstanceId());
      ////Assert.isTrue(draft != null && draft.getCompoId() != null, "没有草稿，不能启动工作流!");
      runtimeDao.removeDraftById(env.getContext().getInstanceId());
    }
    
    InstanceModel instance = new InstanceModel();
    instance.setDescription((String)env.getVariable("instanceDesc"));
    instance.setEndTime(WFConst.INITIAL_END_TIME);
    instance.setName(env.getContext().getTableData().getTitle());
    instance.setOwner(env.getCurrentExecutor().getCode());
    instance.setStartTime(DateUtil.getTime());
    instance.setStatus(new Long(WFConst.INSTANCE_STATUS_ACTIVE));
    instance.setTemplateId(env.getContext().getTemplateId());
    instance = runtimeDao.createInscance(instance);
    
    Long draftId = env.getContext().getInstanceId();//启动流程前instanceId为draftId
    
    runtimeDao.updateBusinessInstanceId(draftId, instance.getInstanceId()
      , env.getContext().getTableData().getId());
    
    env.setInstance(instance);
    env.setCurrentNode(env.getTemplate().getStartNode());
    env.getContext().setInstanceId(instance.getInstanceId());
    
    //TODO:发送流程启动事件
    
  }

}
