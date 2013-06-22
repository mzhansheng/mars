package com.seekon.mars.wf.engine.internal.cmd;

public interface CommandService {
  
  public void execute(Command command);
  
  public void execute(CommandChain chain);
}
