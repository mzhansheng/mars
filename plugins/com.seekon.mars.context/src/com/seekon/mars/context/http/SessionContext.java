package com.seekon.mars.context.http;

import java.io.Serializable;
import java.util.Map;

public class SessionContext implements Serializable {

  private static final long serialVersionUID = 6323424280997646592L;

  public static final String KEY_LOGINED_CURRENT_USER = "sys.current.logined.user";

  public static final String KEY_USER_CODE = "svUserCode";

  public static final String KEY_USER_NAME = "svUserName";
  
  public static final String KEY_ENT_CODE = "svEntCode";

  public static final String KEY_ENT_NAME = "svEntName";
  
  public static final String KEY_DEPT_CODE = "svDeptCode";

  public static final String KEY_DEPT_NAME = "svDeptName";

  private static final ThreadLocal<Map<String, Object>> currentSession = new ThreadLocal<Map<String, Object>>();

  private static boolean serverRunning = serverRunning();

  private static Map<String, Object> singletonSession = null;

  public static Map<String, Object> getCurrentHttpSession() {
    Map<String, Object> session = currentSession.get();
    if (session == null && !serverRunning) {
      session = singletonSession;
    }
    return session;
  }

  public static void setCurrentHttpSession(Map<String, Object> httpSession) {
    currentSession.set(httpSession);
    if (!serverRunning) {
      singletonSession = httpSession;
    }
  }

  public static boolean serverRunning() {
    try {
      Class<?> clazz = Class.forName("javax.servlet.http.HttpSession");
      return clazz != null;
    } catch (Throwable e) {
    }
    return false;
  }
}
