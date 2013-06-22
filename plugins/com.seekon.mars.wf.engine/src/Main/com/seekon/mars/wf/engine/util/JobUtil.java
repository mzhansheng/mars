package com.seekon.mars.wf.engine.util;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.seekon.mars.wf.engine.Configuration;
import com.seekon.mars.wf.engine.internal.SchedulerLifecycleBean;
import com.seekon.mars.wf.engine.internal.job.SendMailJob;

/**
 * 
 * 任务调度管理的工具类
 *
 */
public class JobUtil {

  private static final Logger logger = Logger.getLogger(JobUtil.class);
  
  /**
   * 使用默认的scheduler和trigger调度任务
   * trigger默认为立即执行，而且执行一次
   * @param jobClass
   * @param jobData
   */
  public static void scheduleJob(Class jobClass, Map jobData) {
    Date runTime = TriggerUtils.getEvenMinuteDate(new Date());
    SimpleTrigger trigger = new SimpleTrigger(
      "trigger" + System.currentTimeMillis(), null, runTime);
    scheduleJob(trigger, jobClass, jobData);
  }

  /**
   * 使用默认的scheduler调度任务
   * @param trigger
   * @param jobClass
   * @param jobData
   */
  public static void scheduleJob(Trigger trigger, Class jobClass, Map jobData) {
    Scheduler scheduler = new SchedulerLifecycleBean().getScheduler();
    scheduleJob(scheduler, trigger, jobClass, jobData);
  }

  /**
   * 
   * @param scheduler
   * @param trigger
   * @param jobClass
   * @param jobData
   */
  public static void scheduleJob(Scheduler scheduler, Trigger trigger, Class jobClass, Map jobData) {
    JobDetail job = new JobDetail(jobClass.getName() + System.currentTimeMillis(),
      null, jobClass);
    job.getJobDataMap().putAll(jobData);
    try {
      scheduler.scheduleJob(job, trigger);
      if(!scheduler.isStarted()){
        scheduler.start();
      }
    } catch (SchedulerException e) {
      logger.error(e);
    }
  }

  /**
   * 调度发送邮件的任务
   * 根据系统选项的配置来决定是否发送邮件
   * @param jobData
   */
  public static void scheduleMailJob(Map jobData) {
    boolean flagSendMail = Boolean.valueOf(
      Configuration.get(Configuration.FLAG_SEND_MAIL)).booleanValue();
    if(!flagSendMail){
      logger.debug("wfengine.properties中com.ufgov.workflow.engine.option.sendMail设置为false，不发送邮件！");
      return;
    }
    scheduleJob(SendMailJob.class, jobData);
  }
}
