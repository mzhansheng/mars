package com.seekon.mars.wf.engine.internal.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 
 * 模板监控任务，定时的对模板进行监控，如果发现模板有所改动则发送模板相关事件，通知service重新加载模板
 *
 */
public class MonitorTemplateJob implements Job{

  public void execute(JobExecutionContext context) throws JobExecutionException {
    
  }

}
