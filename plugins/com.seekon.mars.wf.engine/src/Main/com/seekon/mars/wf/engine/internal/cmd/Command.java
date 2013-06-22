package com.seekon.mars.wf.engine.internal.cmd;

import java.io.Serializable;

import com.seekon.mars.wf.engine.internal.Environment;

/**
 * 
 * 工作流基本操作命令接口，Command实现类尽可能的是最小的操作命令单元
 *
 */
public interface Command extends Serializable{

  public void execute(Environment env) throws CommandException;

}
