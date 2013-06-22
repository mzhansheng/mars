package com.seekon.mars.wf.engine.internal.job;

import java.util.Iterator;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.seekon.mars.wf.engine.Configuration;

/**
 * 
 * 发送消息的任务
 *
 */
public class SendMailJob implements Job{

  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    Object model = jobDataMap.get("model");
    Set toAddressList = (Set)jobDataMap.get("toAddressList");
    
    String fromAddr = (String)jobDataMap.get("fromAddr");
    if(fromAddr == null){
      fromAddr = Configuration.get(Configuration.ADMIN_MAIL_ADDRESS);
    }
    String templateName = Configuration.get(Configuration.MAIL_TEMPLATE_NAME);
    
    Iterator iterator = toAddressList.iterator();
    while(iterator.hasNext()){
      ////MessageHelper.sendMail(templateName, model, (String)iterator.next(), fromAddr);
    }
    
  }

}
