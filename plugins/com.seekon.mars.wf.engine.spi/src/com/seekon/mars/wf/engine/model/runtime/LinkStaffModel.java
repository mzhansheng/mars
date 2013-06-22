package com.seekon.mars.wf.engine.model.runtime;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;

/**
 * 
 * 运行期的流向信息，包含在此节点上的执行者列表信息
 *
 */
public class LinkStaffModel implements java.io.Serializable{

  private static final long serialVersionUID = 7311530071736561938L;
  
  private LinkModel link;
  
  private Set staffList = new HashSet();
  
  public LinkStaffModel(){
  }
  
  public LinkStaffModel(LinkModel link){
    this.link = link;
  }
  
  public LinkStaffModel(LinkModel link, Set staffList){
    this.link = link;
    this.staffList = staffList;
  }
 
  public LinkModel getLink() {
    return link;
  }

  public void setLink(LinkModel link) {
    this.link = link;
  }

  public Set getStaffList() {
    return staffList;
  }

  public void setStaffList(Set staffList) {
    this.staffList = staffList;
  }
  
  public void addStaff(StaffModel staff){
    staffList.add(staff);
  }
  
  public boolean equals(Object obj){
    if(obj == null || !(obj instanceof LinkStaffModel)){
      return false;
    }
    
    LinkStaffModel tmp = (LinkStaffModel)obj;
    
    boolean result = ((this.link == null && tmp.link == null) || (this.link != null && this.link.equals(tmp.link)))
    && (this.staffList.size() == tmp.staffList.size());
    if(!result){
      return result;
    }
    
    
    Iterator iterator = staffList.iterator();
    Iterator iterator2 = tmp.staffList.iterator();
    while(iterator.hasNext()){
      result = iterator.next().equals(iterator2.next());
      if(!result){
        return result;
      }
    }
    
    return true;
  }
}
