package com.seekon.mars.wf.engine.internal.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 自动提交代办任务，根据模板中所设置的执行期限和工作日历进行自动提交
 *
 */
public class AutoCommitTaskJob implements Job{

  public void execute(JobExecutionContext context) throws JobExecutionException {
   //TODO:暂不实现
  }

}
