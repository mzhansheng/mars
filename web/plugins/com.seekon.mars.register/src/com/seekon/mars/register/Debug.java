package com.seekon.mars.register;

public final class Debug {

  public static boolean IS_DEBUG = true;

//  private static Log log = LogFactory.getLog(Debug.class);

  static {
    IS_DEBUG = true;
  }

  public static void trace(Object msg) {
    if (IS_DEBUG)
      synchronized (System.out) {
        System.out.print("AppLoader INFO - ");
        System.out.println(msg);
      }
  }

  private Debug() {
  }
}
