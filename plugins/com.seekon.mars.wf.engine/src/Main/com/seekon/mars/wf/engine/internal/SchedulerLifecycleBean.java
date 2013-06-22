package com.seekon.mars.wf.engine.internal;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * quartz中scheduler的生命期管理，包含scheduler的初始化、启动和停止
 *
 */
public class SchedulerLifecycleBean  {

  private final Logger log = Logger.getLogger(SchedulerLifecycleBean.class);

  public static final String WF_QUARTZ_CONF_FILE = "quartz/quartz-wf.properties";

  private Scheduler scheduler = null;

  public void afterPropertiesSet() throws Exception {
    SchedulerRepository repository = SchedulerRepository.getInstance();
    synchronized (repository) {
      SchedulerFactory factory = new StdSchedulerFactory(WF_QUARTZ_CONF_FILE);
      this.scheduler = factory.getScheduler();
    }
  }

  public Scheduler getScheduler(){
    return this.scheduler;
  }
  
  public boolean isRunning() {
    if (this.scheduler != null) {
      try {
        return !this.scheduler.isInStandbyMode();
      } catch (SchedulerException ex) {
        return false;
      }
    }
    return false;
  }

  public void start() {
    if (this.scheduler != null) {
      try {
        this.scheduler.start();
      } catch (SchedulerException ex) {
        log.error(ex);
        //TODO：抛出异常
      }
    }
  }

  public void stop() {
    if (this.scheduler != null) {
      try {
        this.scheduler.standby();
      } catch (SchedulerException ex) {
        log.error(ex);
        //TODO：抛出异常
      }
    }
  }

}
