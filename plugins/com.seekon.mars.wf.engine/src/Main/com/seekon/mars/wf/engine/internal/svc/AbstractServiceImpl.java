package com.seekon.mars.wf.engine.internal.svc;

import org.apache.log4j.Logger;

import com.seekon.mars.wf.engine.internal.cmd.CommandService;

/**
 * 
 * 提供CommandService以及logger日志
 *
 */
public class AbstractServiceImpl {

  protected static Logger logger = Logger.getLogger(AbstractServiceImpl.class);
  
  protected CommandService commandService;

  public CommandService getCommandService() {
    return commandService;
  }

  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }
  
}
