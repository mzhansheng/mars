package com.seekon.mars.wf.engine;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 工作流系统选项参数配置
 *
 */
public class Configuration implements Serializable{

  private static final long serialVersionUID = 8060444021120228462L;

  private static final Logger logger = Logger.getLogger(Configuration.class);

  private static Properties props = new Properties();
  
  public static final String FLAG_SEND_MAIL = "com.ufgov.workflow.engine.option.sendMail";
  
  public static final String MAIL_TEMPLATE_NAME = "com.ufgov.workflow.engine.option.mailTemplateName";
  
  public static final String ADMIN_MAIL_ADDRESS = "com.ufgov.workflow.engine.option.adminMailAddress";
  
  public static final String FLAF_AUTO_COMMIT = "com.ufgov.workflow.engine.option.autoCommit";
  
  public static final String PROCESS_EVENT_LISTENER_LIST = "com.ufgov.workflow.engine.processEventListenerList";
  
  public static final String TASK_EVENT_LISTENER_LIST = "com.ufgov.workflow.engine.taskEventListenerList";
  
  public Configuration() {
    try {
      props.load(this.getClass().getResourceAsStream("/wfengine.properties"));
    } catch (IOException e) {
      logger.error("could not read environment.properties file from classpath.", e);
      throw new RuntimeException(e);
    }
  }

  public static String get(String key) {
    return props.getProperty(key);
  }
}
