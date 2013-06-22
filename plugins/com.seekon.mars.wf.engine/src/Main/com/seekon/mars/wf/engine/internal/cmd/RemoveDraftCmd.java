package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;

/**
 * 
 * 删除工作流草稿操作
 *
 */
public class RemoveDraftCmd extends AbstractCommand{
  
  private static final long serialVersionUID = -3388219416438049241L;

  private Long draftId;
  
  public RemoveDraftCmd(Long draftId){
    this.draftId = draftId;
  }
  
  public void doExecute(Environment env) throws CommandException {
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    runtimeDao.removeDraftById(draftId);
   
  }

}
