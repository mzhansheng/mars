package com.seekon.smartclient.hessiansupport.service;

public class ServiceNotFoundException extends Exception {

	private static final long serialVersionUID = 6826547041157962972L;

	public ServiceNotFoundException() {
    super();
  }

  public ServiceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceNotFoundException(String message) {
    super(message);
  }

  public ServiceNotFoundException(Throwable cause) {
    super(cause);
  }
}
