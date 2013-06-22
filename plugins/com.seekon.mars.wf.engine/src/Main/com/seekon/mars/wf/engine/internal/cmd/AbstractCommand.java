package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;

/**
 * 
 * Command类的抽象类，其他的实现类可继承于此类
 *
 */
public abstract class AbstractCommand implements Command{

  private static final long serialVersionUID = 3217982863660976054L;
  
  protected Command next;
  
  public void setNextCommand(Command next){
    this.next = next;
  }
  
  public Command getNextCommand(){
    return next;
  }
  
  public void execute(Environment env) throws CommandException{
    doExecute(env);
    if(next != null){
      next.execute(env);
    }
  }
  
  public abstract void doExecute(Environment env) throws CommandException;
}
