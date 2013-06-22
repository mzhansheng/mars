package com.seekon.mars.wf.engine.exception;

public class WorkflowException extends RuntimeException{

  private static final long serialVersionUID = 3512308806629519563L;

  public WorkflowException(String mess) {
    super(mess);
  }
  
  public WorkflowException(Exception ex) {
    super(ex);
  }
  
  public WorkflowException(String message, Exception ex) {
    super(message, ex);
  }
}
