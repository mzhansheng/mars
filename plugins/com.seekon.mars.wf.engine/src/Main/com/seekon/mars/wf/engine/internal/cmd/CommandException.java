package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.exception.WorkflowException;

public class CommandException extends WorkflowException{

  private static final long serialVersionUID = -1330100821179500691L;

  public CommandException(String msg) {
    super(msg);
  }

  public CommandException(Exception ex) {
    super(ex);
  }
  
  public CommandException(String message, Exception ex) {
    super(message, ex);
  }
}
