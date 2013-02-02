package com.seekon.smartclient.common;

public interface InvokeHandler<T> {
  /**
   * 该方法在单独线程中执行
   * @return 执行后的结果对象
   * @throws Exception 执行过程中出现的异常
   */
  public abstract T execute() throws Exception;

  /**
  /* execute执行成功后调用该方法，该方法在AWT EventDispatcThread中执行
   * @param result
   */
  public abstract void success(T result);

  /**
   * 调用失败后执行方法，该方法在AWT EventDispatcThread中执行
   * @param e 失败的异常对象
   */
  public abstract void failure(Exception e);

  /**
   * 执行前调用，该方法在AWT EventDispatcThread中执行
   */
  public void before();

  /**
   * 执行后调用，该方法在AWT EventDispatcThread中执行
   */
  public void after();

}