package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;

public class RemoveInstanceByNodeCmd extends AbstractCommand {

  private static final long serialVersionUID = -9095945250994955258L;

  public void doExecute(Environment env) throws CommandException {
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    Long instanceId=env.getInstance().getInstanceId();
    runtimeDao.removeInstanceById(instanceId);
  }

}
