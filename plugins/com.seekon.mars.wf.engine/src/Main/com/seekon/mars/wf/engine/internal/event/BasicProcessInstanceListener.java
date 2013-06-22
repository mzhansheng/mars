package com.seekon.mars.wf.engine.internal.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.seekon.mars.wf.engine.event.ProcessInstanceListener;
import com.seekon.mars.wf.engine.event.WorkflowEvent;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.util.JobUtil;

public class BasicProcessInstanceListener implements ProcessInstanceListener{

  public void onEvent(WorkflowEvent event) {
    Environment env = event.getEnvironment();
    
    Set toAddressList = new HashSet();
    Iterator iterator = env.getNextLinkStaffSet().iterator();
    while(iterator.hasNext()){
      LinkStaffModel linkStaff = (LinkStaffModel)iterator.next();
      Iterator tmp = linkStaff.getStaffList().iterator();
      while(tmp.hasNext()){
        String email = ((StaffModel)tmp.next()).getEmail();
        if(email != null && email.length() > 0){
          toAddressList.add(email);
        }
      }
    }
    
    Map jobData = new HashMap();
    jobData.put("model", event.getSource());
    jobData.put("toAddressList", toAddressList);
    
    JobUtil.scheduleMailJob(jobData);
  }

}
