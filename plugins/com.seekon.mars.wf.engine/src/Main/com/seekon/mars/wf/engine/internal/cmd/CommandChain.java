package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;



/**
 * 
 * 操作命令类执行链，依次执行此链中的命令
 *
 */
public class CommandChain {

  private AbstractCommand first = null;

  private AbstractCommand end = null;
    
  public CommandChain(){
  }
  
  public void execute(Environment env) {
    first.execute(env);
  }

  public void addCommand(AbstractCommand command) {
    if(first == null){
      first = command;
    }else if(end == null){
      end = command;
      first.setNextCommand(end);
    }else{
      end.setNextCommand(command);
      end = command;
    }
  }
}
