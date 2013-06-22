package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.runtime.DraftModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 
 * 创建工作流草稿操作
 *
 */
public class CreateDraftCmd extends AbstractCommand{

  private static final long serialVersionUID = 7777590760874649671L;

  private String draftName;
  private String compoId;
  private String masterTableId;
  
  public CreateDraftCmd(String draftName, String compoId, String masterTableId){
    this.draftName = draftName;
    this.compoId = compoId;
    this.masterTableId = masterTableId;
  }
  
  public void doExecute(Environment env) throws CommandException {
    DraftModel draft = new DraftModel();
    draft.setCompoId(compoId);
    draft.setDraftName(draftName);
    draft.setMasteTableId(masterTableId);
    draft.setUserId(env.getCurrentExecutor().getCode());
    draft.setUserName(env.getCurrentExecutor().getName());
    draft.setSaveTime(DateUtil.getTime());
    
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    runtimeDao.createDraft(draft);
    
  }

}
